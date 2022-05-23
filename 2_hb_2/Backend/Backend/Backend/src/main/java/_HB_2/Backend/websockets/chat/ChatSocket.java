package _HB_2.Backend.websockets.chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import _HB_2.Backend.user.User;
import _HB_2.Backend.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{username}")  // this is Websocket url
public class ChatSocket {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static MessageRepository msgRepo;

    private static UserService userService;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        msgRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Send chat history to the newly connected user
//        sendMessageToPArticularUser(username, getChatHistory());

        User enter = userService.getUserByEmail(username);
        // broadcast that new user joined
        String message = "User: " + enter.getFirstName() + " " + enter.getLastName() + " has Joined the Chat";
        broadcast(message);
    }


    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);
        //get user first name based on email and then return that first name
        User sender = userService.getUserByEmail(username);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            String destUsername = message.split(" ")[0].substring(1);
            User userReceived = userService.getUserByEmail(destUsername);

            if (sessionUsernameMap.containsValue(destUsername)) {

                String newMessage = message.replace("@" + destUsername, "");

                // send the message to the sender and receiver
                sendMessageToPArticularUser(destUsername, sender.getFirstName() + ": " + newMessage);
                sendMessageToPArticularUser(username, sender.getFirstName() + ": " + newMessage);

                User findUser = userService.getUserByEmail(username);
                // Saving chat history to repository
                msgRepo.save(new Message(findUser, userReceived, newMessage));
            }
            else{
                String newMessage = message.replace("@" + destUsername, "");
                User findUser = userService.getUserByEmail(username);
                msgRepo.save(new Message(findUser, userReceived, newMessage));
            }

        }
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcast that the user disconnected
        String message = username + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Message> messages = msgRepo.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (Message message : messages) {
                sb.append(message.getUserSent() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }

}

