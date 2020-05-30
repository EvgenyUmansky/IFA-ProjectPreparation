package DataAccess;

import domain.Notification;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NotificationDBAccess implements DBAccess<Notification> {
    static Logger logger = Logger.getLogger(NotificationDBAccess.class.getName());

    private static final NotificationDBAccess instance = new NotificationDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private NotificationDBAccess() {

    }

    public static NotificationDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Notification notification) {
        if(notification == null){
            logger.error("Couldn't execute 'save(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            System.out.println("Couldn't execute 'save(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        // String query = "insert into [Notification] values (?, ?)";
        String query = "insert into [Notification] values (?)";

        try {
            statement = connection.prepareStatement(query);

            //statement.setInt(1, notification.getId());
            statement.setString(1, notification.getSubject());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Notification notification) {
        if(notification == null){
            logger.error("Couldn't execute 'update(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            System.out.println("Couldn't execute 'update(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            return;
        }

        String query = "update [Notification] " +
                "set Subject = ? " +
                "where NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, notification.getSubject());
            statement.setInt(2, notification.getId());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }


    @Override
    public void delete(Notification notification) {
        if(notification == null){
            logger.error("Couldn't execute 'delete(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            System.out.println("Couldn't execute 'delete(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            return;
        }

        String query = "delete from [Notification] where NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,notification.getId());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Notification select(String id) {
        String query = "select * \n" +
                "from [Notification] \n" +
                "where NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        Notification notification = null;
        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            retrievedUsers = statement.executeQuery();

            if(retrievedUsers.next()){
                String subject = retrievedUsers.getString(2);
                notification = new Notification(Integer.parseInt(id), subject);
            }
        }
        catch (SQLException e){
            //FIXME: change this to exception
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedUsers != null) {
                    retrievedUsers.close();
                }
                connection.close();
            } catch (SQLException e) {
                //FIXME: change this to exception
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return notification;
    }

    @Override
    public HashMap<String, Notification> conditionedSelect(String[] conditions) {
        String query = "select * from [Notification] "; // where Subject = ?";
        HashMap<String, Notification> notificationMap = new HashMap<>();
        if(conditions == null || conditions.length == 0){
            // no need
            return null;
        }

        query += "where ";
        for(int i = 0; i < conditions.length; i++){
            if(i % 2 == 0){
                query += " " + conditions[i];
            }
            else {
                query += " = ?";
            }
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedGame = null;
        Notification notification = null;

        try{
            statement = connection.prepareStatement(query);

            int index = 1;
            for(int i = 0; i < conditions.length; i++){
                if(i % 2 != 0){
                    statement.setString(index, conditions[i]);
                    index++;
                }
            }

            retrievedGame = statement.executeQuery();

            if(retrievedGame.next()){
                int id = retrievedGame.getInt(1);
                String subject =  retrievedGame.getString(2);
                notification = new Notification(id, subject);
                notificationMap.put(String.valueOf(id), notification);

            }
        }
        catch (SQLException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedGame != null) {
                    retrievedGame.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return notificationMap;
    }
}
