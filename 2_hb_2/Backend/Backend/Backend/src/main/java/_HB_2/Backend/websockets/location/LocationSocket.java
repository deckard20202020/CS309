package _HB_2.Backend.websockets.location;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import _HB_2.Backend.rider.Rider;
import _HB_2.Backend.trip.Trip;
import _HB_2.Backend.trip.TripService;
import _HB_2.Backend.user.User;
import _HB_2.Backend.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/location/{username}/{tripId}")  // this is Websocket url
public class LocationSocket {

    private static UserService userService;

    private static TripService tripService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;  // we are setting the static variable
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private int tripId;

    private final Logger logger = LoggerFactory.getLogger(LocationSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("tripId") String id)
            throws IOException {

        logger.info("Entered into Open");

        tripId = Integer.valueOf(id);

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        // broadcast that new user joined
        String message = "You have joined the ride";
        broadcastDisconnect(message);
    }


    @OnMessage
    public void onMessage(Session session, String location) throws IOException {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + location);

        String username = sessionUsernameMap.get(session);

        String[] coords = location.split(":");
        String lon = coords[0];
        String lat = coords[1];

        String message = lon + ":" + lat;

        Trip ongoingTrip = tripService.getTripById(tripId);
        Set<User> ridersOnTrip = ongoingTrip.getRiders();

        sendMessageToPArticularUser(username,message);
        for(User rider : ridersOnTrip) {
            String email = rider.getEmail();
            if (sessionUsernameMap.containsValue(email)) {
                sendMessageToPArticularUser(email, message);
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
        String message = "You have left the ride";
        broadcastDisconnect(message);
    }

    private void broadcastDisconnect(String message) {
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


}


