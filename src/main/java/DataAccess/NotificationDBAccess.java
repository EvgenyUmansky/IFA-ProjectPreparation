package DataAccess;

import domain.AlertNotification;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationDBAccess implements DBAccess<AlertNotification> {
    static Logger logger = Logger.getLogger(NotificationDBAccess.class.getName());

    private static final NotificationDBAccess instance = new NotificationDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private NotificationDBAccess() {

    }

    public static NotificationDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(AlertNotification notification) {
        if(notification == null){
            logger.error("Couldn't execute 'save(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            System.out.println("Couldn't execute 'save(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Notification] values (?, ?, ?)";

        try {
            statement = connection.prepareStatement(query);

            statement.setInt(1, notification.getId());
            statement.setString(2, notification.getTitle());
            statement.setString(3, notification.getSubject());

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
    public void update(AlertNotification notification) {
        if(notification == null){
            logger.error("Couldn't execute 'update(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            System.out.println("Couldn't execute 'update(AlertNotification notification)' in NotificationDBAccess: the notification is null");
            return;
        }

        String query = "update [Notification] " +
                "set Title = ?, Subject = ? " +
                "where NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, notification.getTitle());
            statement.setString(2, notification.getSubject());
            statement.setInt(3, notification.getId());

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
    public void delete(AlertNotification notification) {
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
    public AlertNotification select(String id) {
        String query = "select * \n" +
                "from [Notification] \n" +
                "where NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        AlertNotification alertNotification = null;
        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            retrievedUsers = statement.executeQuery();

            if(retrievedUsers.next()){
                String title = retrievedUsers.getString(2);
                String subject = retrievedUsers.getString(3);
                alertNotification = new AlertNotification(Integer.parseInt(id), title, subject);
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

        return alertNotification;
    }

    @Override
    public HashMap<String, AlertNotification> conditionedSelect(String[] conditions) {
        return null;
    }
}
