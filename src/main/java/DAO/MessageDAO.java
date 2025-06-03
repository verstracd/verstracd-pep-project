package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
