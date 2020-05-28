package DataAccess;

import domain.Fan;
import domain.Game;
import domain.Referee;
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

class FanGamesDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    FanGamesDBAccess fanGamesDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));

        fanGamesDBAccess = FanGamesDBAccess.getInstance();
        connection = DBConnector.getConnection();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [User] values ('Fan_test', 'name_1', 'password_1', 'testMail_1@gmail.com', 'false', 'false')");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the Referee to DB
        preparedStatement = connection.prepareStatement("insert into [Fan] values ('Fan_test')");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [User] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // delete the Referee from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fan] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        System.setOut(originalOut);
        fanGamesDBAccess = null;
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
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void select() throws SQLException {
        Pair<String, ArrayList<Game>> gamesEmpty = fanGamesDBAccess.select("");
        assertEquals(0, gamesEmpty.getValue().size());

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [FansInGames] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [FansInGames] values ('Fan_test', '1000')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the role exists in DB
        preparedStatement = connection.prepareStatement("select * from [FansInGames] where username = 'Fan_test'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Fan_test", resultSet.getString(1));

        // check select
        ArrayList<Game> games = fanGamesDBAccess.select("Fan_test").getValue();
        assertEquals(1000, games.get(0).getId());

        // delete the role from DB
        preparedStatement = connection.prepareStatement("delete from [FansInGames] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void selectFansOfGame() throws SQLException {
        Pair<String, ArrayList<Fan>> fansEmpty = fanGamesDBAccess.selectFansOfGame("");
        assertEquals(0, fansEmpty.getValue().size());

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [FansInGames] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the role to DB
        preparedStatement = connection.prepareStatement("insert into [FansInGames] values ('Fan_test', '1000')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the role exists in DB
        preparedStatement = connection.prepareStatement("select * from [FansInGames] where username = 'Fan_test'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Fan_test", resultSet.getString(1));

        // check select
        ArrayList<Fan> fans = fanGamesDBAccess.selectFansOfGame("1000").getValue();
        assertEquals("Fan_test", fans.get(fans.size() - 1).getUserName());

        // delete the role from DB
        preparedStatement = connection.prepareStatement("delete from [FansInGames] where username = 'Fan_test'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
    }
}