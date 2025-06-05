package Service;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;


import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountService accountService;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO, AccountService accountService) {
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    public Message updateMessage(int message_id, String message_text) {
        //Validate text
        if (message_text == null || message_text.isEmpty() || message_text.length() > 255){
            return null;
        }
        Message updatedMessage = messageDAO.updateMessage(message_id, message_text);
        return updatedMessage;
    }

    public Message addMessage(Message message) {
        // Validate Message
        String message_text = message.getMessage_text();
        if (message_text == null || message_text.isEmpty() || message_text.length() > 255){
            return null;
        }
        //Validate user
        Account user = accountService.getAccountByID(message.getPosted_by());
        if (user == null) {
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
