package DataAccess;

import domain.Game;
import domain.Notification;
import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlertDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    AlertDBAccess alertDBAccessTest;
    Notification notification;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));

        notification = new Notification(1111, "test");
        alertDBAccessTest = AlertDBAccess.getInstance();
        connection = DBConnector.getConnection();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('Alert_test', 'name_1', 'password_1', 'testMail_1@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the Notification to DB
        preparedStatement = connection.prepareStatement("insert into [Notification] values ('1111', 'Notif test for AlertDBAccess')");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the Notification from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Notification] where NotificationId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();

        notification = null;
        System.setOut(originalOut);
        alertDBAccessTest = null;
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
    void save() throws SQLException {
        // roles == null
        alertDBAccessTest.save(null);
        assertEquals("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null\r\n", outContent.toString());
        outContent.reset();

        // roles.getValue() == null
        alertDBAccessTest.save(new Pair<>("User", null));
        assertEquals("Couldn't execute 'save(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair.getValue() is null\r\n", outContent.toString());
        outContent.reset();

        // delete the roles from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the roles does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Alert] where username = 'Alert_test'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save roles to DB
        alertDBAccessTest.save(new Pair<>("Alert_test", new ArrayList<Notification>(){{add(notification);}}));

        // check the roles saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Alert] where username = 'Alert_test'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Alert_test", resultSet.getString(1));
        assertEquals(1000, resultSet.getInt(2));
        assertFalse(resultSet.getBoolean(3));

        // delete the roles from DB
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void update() throws SQLException {
        // roles == null
        alertDBAccessTest.update(null);
        assertEquals("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair is null\r\n", outContent.toString());
        outContent.reset();

        // roles.getValue() == null
        alertDBAccessTest.update(new Pair<>("User", null));
        assertEquals("Couldn't execute 'update(Pair<String, AlertNotification> userNotificationPair)' in AlertDBAccess: the userNotificationPair.getValue() is null\r\n", outContent.toString());
        outContent.reset();

        // delete the roles from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the roles does not exist in DB
        preparedStatement = connection.prepareStatement("insert into [Alert] values ('Alert_test', '1111', 'false')" );
        preparedStatement.executeUpdate();
        connection.commit();

        notification.setSeen(true);
        alertDBAccessTest.update(new Pair<>("Alert_test", new ArrayList<Notification>(){{add(notification);}}));

        // check the roles saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Alert] where username = 'Alert_test'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Alert_test", resultSet.getString(1));
        assertEquals(1111, resultSet.getInt(2));
        assertTrue(resultSet.getBoolean(3));

        // delete the roles from DB
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void delete() {
    }

    @Test
    void select() throws SQLException {
        Pair<String, ArrayList<Notification>> alertEmpty = alertDBAccessTest.select("");
        assertEquals(0, alertEmpty.getValue().size());

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [Alert] values ('Alert_test', '1111', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the role exists in DB
        preparedStatement = connection.prepareStatement("select * from [Alert] where username = 'Alert_test'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Alert_test", resultSet.getString(1));

        // check select
        ArrayList<Notification> selectedAlert = alertDBAccessTest.select("Alert_test").getValue();
        assertEquals(1111, selectedAlert.get(0).getId());

        // delete the role from DB
        preparedStatement = connection.prepareStatement("delete from [Alert] where username = 'Alert_test'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
    }
}