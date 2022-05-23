package _HB_2.Backend.websockets.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;


    public List<Message> getMessagesForPairOfUser(int user1Id, int user2Id) {

//        List<Message> list = messageRepository.findMessagesForPairOfUsers(user1Id, user2Id);

        List<Message> list = new ArrayList<>();
        List<Message> allMessages = messageRepository.findAll();

        for (Message message : allMessages){
            if ((message.getUserReceived() != null) &&
                    (message.getUserSent() != null) &&
                    (message.getUserReceived().getId() == user1Id && message.getUserSent().getId() == user2Id ||
                    message.getUserReceived().getId() == user2Id && message.getUserSent().getId() == user1Id)) {
                list.add(message);
            }
        }
        return list;
    }

    public List<Message> deleteMessagesForPairOfUsers(int user1Id, int user2Id) {

        //get all the messages for the two users and delete them
        List<Message> list = getMessagesForPairOfUser(user1Id, user2Id);
        for(Message message : list) {
            Long id = message.getId();
            messageRepository.deleteById(id);
        }

        //return the empty list of messages
        list = getMessagesForPairOfUser(user1Id, user2Id);
        return list;
    }
}
