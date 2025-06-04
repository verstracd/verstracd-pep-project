package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message deleteMessageByID(int message_id) {
        return messageDAO.deleteMessageByID(message_id);
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessaageByID(message_id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
