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

    public Message addMessage(Message message) {
        // Validate Message
        String message_text = message.getMessage_text();
        if (message_text == null || message_text.isEmpty() || message_text.length() > 255){
            return null;
        }

        Message addedMessage = messageDAO.insertMessage(message);
        return addedMessage;
    }

    public Message deleteMessageByID(int message_id) {
        return messageDAO.deleteMessageByID(message_id);
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
