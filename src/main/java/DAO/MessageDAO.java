package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/*
 * message_id integer primary key auto_increment,
 * posted_by integer,
 * message_text varchar(255),
 * time_posted_epoch long,
 * foreign key (posted_by) references Account(account_id)
 */

public class MessageDAO {
    
    // @return updated message
    public Message updateMessage(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            int rowsEffected = preparedStatement.executeUpdate();
            if (rowsEffected > 0) {
                return getMessageByID(message_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // @return newly created message
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated_message_id = (int) resultSet.getLong(1);
                return getMessageByID(generated_message_id);
            } 
            
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // @return Delete message by ID then return message
    public Message deleteMessageByID(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageByID(id);

        if (message != null) {
            try {
                String sql = "DELETE FROM message WHERE message_id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();

            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return message;
    }

    // @return message by message_id
    public Message getMessageByID(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql  = "SELECT * FROM message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt("message_id"), 
                                            resultSet.getInt("posted_by"),
                                            resultSet.getString("message_text"),
                                            resultSet.getLong("time_posted_epoch"));
                return message;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // @return all messages
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"), 
                                            resultSet.getInt("posted_by"),
                                            resultSet.getString("message_text"),
                                            resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
