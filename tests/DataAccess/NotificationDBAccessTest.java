package DataAccess;

import domain.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    Notification notification;
    NotificationDBAccess notificationDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        notification = new Notification(1, "subject_1");
        notificationDBAccess = NotificationDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        notification = null;
        notificationDBAccess = null;

        if(connection != null) {
            connection.close();
            connection = null;
        }

        if(preparedStatement != null) {
            preparedStatement.close();
            preparedStatement = null;
        }

        if(resultSet != null) {
            resultSet.close();
            resultSet = null;
        }
    }

    @Test
    public void save() throws SQLException {
        // notification == null
        notificationDBAccess.save(null);
        assertEquals("Couldn't execute 'save(AlertNotification notification)' in NotificationDBAccess: the notification is null\r\n", outContent.toString());
        outContent.reset();

        // delete the notification from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the notification does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save notification to DB
        notificationDBAccess.save(notification);

        // check the notification saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(notification.getSubject(), resultSet.getString(2));


        // delete the notification from DB
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // notification == null
        notificationDBAccess.update(null);
        assertEquals("Couldn't execute 'update(AlertNotification notification)' in NotificationDBAccess: the notification is null\r\n", outContent.toString());
        outContent.reset();

        // delete the notification from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the notification to DB
        preparedStatement = connection.prepareStatement("insert into [Notification] values ('1', 'subject_2')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the notification exists in DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("title_2", resultSet.getString(2));

        // update notification in DB
        notificationDBAccess.update(notification);

        // check the notification updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(notification.getSubject(), resultSet.getString(2));

        // delete the notification from DB
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // notification == null
        notificationDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(AlertNotification notification)' in NotificationDBAccess: the notification is null\r\n", outContent.toString());
        outContent.reset();

        // delete the notification from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the notification to DB
        preparedStatement = connection.prepareStatement("insert into [Notification] values ('1', 'subject_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the notification exists in DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("title_1", resultSet.getString(2));

        // delete notification from DB
        notificationDBAccess.delete(notification);

        // check the notification updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(notificationDBAccess.select(""));

        // delete the notification from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the notification to DB
        preparedStatement = connection.prepareStatement("insert into [Notification] values ('1', 'subject_1')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the notification exists in DB
        preparedStatement = connection.prepareStatement("select * from [Notification] where NotificationId = '1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("title_1", resultSet.getString(2));

        // check select
        Notification selectedNotification = notificationDBAccess.select("1");
        assertEquals(1, selectedNotification.getId());
        assertEquals("subject_1", selectedNotification.getSubject());
    }

    @Test
    void conditionedSelect() {
        notificationDBAccess.conditionedSelect(null);
    }
}