package DataAccess;

import domain.Notification;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AlertDBAccess implements DBAccess<Pair<String, ArrayList<Notification>>> {
    static Logger logger = Logger.getLogger(AlertDBAccess.class.getName());

    private static final AlertDBAccess instance = new AlertDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private AlertDBAccess() {

    }

    public static AlertDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Pair<String, ArrayList<Notification>> userNotificationPair) {
        if(userNotificationPair == null){
            logger.error("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            System.out.println("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            return;
        }

        if(userNotificationPair.getValue() == null){
            logger.error("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            System.out.println("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair.getValue() is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Alert] values (?, ?, ?)";

        String userName = userNotificationPair.getKey();
        ArrayList<Notification> notifications = userNotificationPair.getValue();

        try {
            for (Notification notification : notifications){
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setInt(2, notification.getId());
                statement.setBoolean(3, notification.isSeen());

                statement.executeUpdate();
                connection.commit();
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
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Pair<String, ArrayList<Notification>> userNotificationPair) {
        if(userNotificationPair == null){
            logger.error("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            System.out.println("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            return;
        }

        if(userNotificationPair.getValue() == null){
            logger.error("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null");
            System.out.println("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair.getValue() is null");
            return;
        }

        String query = "update [Alert] " +
                "set isSeen = ? " +
                "where username = ? and NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        String userName = userNotificationPair.getKey();
        ArrayList<Notification> notifications = userNotificationPair.getValue();

        try {
            for (Notification notification : notifications) {
                statement = connection.prepareStatement(query);
                statement.setBoolean(1, notification.isSeen());
                statement.setString(2, userName);
                statement.setInt(3, notification.getId());

                statement.executeUpdate();
                connection.commit();
            }
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
    public void delete(Pair<String, ArrayList<Notification>> userNotificationPair) {
        if(userNotificationPair == null){
            System.out.println("Couldn't execute 'delete(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the roles is null");
            return;
        }

        if(userNotificationPair.getValue() == null){
            System.out.println("Couldn't execute 'delete(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the roles.getValue() is null");
            return;
        }

        String query = "delete from [Alert] where username = ? and NotificationId = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        String userName = userNotificationPair.getKey();
        ArrayList<Notification> notifications = userNotificationPair.getValue();

        try{
            for(Notification notification : notifications) {
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setInt(2, notification.getId());

                statement.executeUpdate();
                connection.commit();
            }
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            System.out.println("Couldn't execute 'delete(Pair<String, ArrayList<AlertNotification>> userNotificationPair)' in AlertDBAccess for " + userName);
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
                System.out.println("Couldn't close 'delete(Pair<String, ArrayList<AlertNotification>> userNotificationPair)' in AlertDBAccess for " + userName);
            }
        }
    }

    @Override
    public Pair<String, ArrayList<Notification>> select(String username) {
        String query = "select Alert.NotificationId, [Subject], isSeen\n" +
                "from Alert \n" +
                "inner join [Notification] on Alert.NotificationId = [Notification].NotificationId\n" +
                "where Alert.Username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        ArrayList<Notification> notifications = new ArrayList<>();
        Pair<String, ArrayList<Notification>> userNotifications = null;
        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUsers = statement.executeQuery();

            while(retrievedUsers.next()){
                int notificationId = Integer.parseInt(retrievedUsers.getString(1));
                String subject = retrievedUsers.getString(2);
                boolean isSeen = retrievedUsers.getBoolean(3);
                Notification notification = new Notification(notificationId, subject);
                notification.setSeen(isSeen);

                notifications.add(notification);
            }
            userNotifications = new Pair<>(username,notifications);
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

        return userNotifications;
    }


    @Override
    public HashMap<String, Pair<String, ArrayList<Notification>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
