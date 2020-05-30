package DataAccess;

import domain.Game;
import domain.GameAlert;
import domain.GameEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    Game game;
    GameDBAccess gameDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() {
        System.setOut(new PrintStream(outContent));
        // int gameID, String hostTeam, String guestTeam, String fieldName, LocalDateTime gameDate, int hostTeamScore, int guestTeamScore, String leagueName, int season
        game = new Game(1111, "Team_1", "Team_2", "Field_1", LocalDateTime.now(), 0, 0, "League_1", 2020);
        gameDBAccess = GameDBAccess.getInstance();
        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        System.setOut(originalOut);
        game = null;
        gameDBAccess = null;
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
        // user == null
        gameDBAccess.save(null);
        assertEquals("game == null in GameDBAccess save(Game game)\r\n", outContent.toString());
        outContent.reset();

//        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Game] where GameId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the user does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Game] where GameId = '1111'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save user to DB
        gameDBAccess.save(game);

        // check the user saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Game] where GameId = '1111'");
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(game.getId(), resultSet.getInt(1));


        // delete the user from DB
        preparedStatement = connection.prepareStatement("delete from [Game] where GameId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
//        // user == null
//        gameDBAccess.update(null);
//        assertEquals("Couldn't execute 'update(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
//        outContent.reset();
//
//        // delete the user from DB if exists
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // insert the user to DB
//        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // check the user exists in DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals("name_2", resultSet.getString(2));
//
//        // update user in DB
//        gameDBAccess.update(game);
//
//        // check the user updated in the DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals(game.getUserName(), resultSet.getString(1));
//        assertEquals(game.getName(), resultSet.getString(2));
//        assertEquals(game.getPassword(), resultSet.getString(3));
//        assertEquals(game.getMail(), resultSet.getString(4));
//
//
//        // delete the user from DB
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
//        // user == null
//        gameDBAccess.delete(null);
//        assertEquals("Couldn't execute 'delete(User user)' in UserDBAccess: the user is null\r\n", outContent.toString());
//        outContent.reset();
//
//        // delete the user from DB if exists
//        preparedStatement = connection.prepareStatement("delete from [User] where username = 'UserName_1'");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // insert the user to DB
//        preparedStatement = connection.prepareStatement("insert into [User] values ('UserName_1', 'name_2', 'password_2', 'testMail_2@gmail.com', 'false', 'false')");
//        preparedStatement.executeUpdate();
//        connection.commit();
//
//        // check the user exists in DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        assertEquals("UserName_1", resultSet.getString(1));
//
//        // delete user from DB
//        gameDBAccess.delete(game);
//
//        // check the user updated in the DB
//        preparedStatement = connection.prepareStatement("select * from [User] where username = 'UserName_1'" );
//        resultSet = preparedStatement.executeQuery();
//        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(gameDBAccess.select(""));

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Game] where GameId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the user to DB
        preparedStatement = connection.prepareStatement("insert into [Game] values ('1111', 'Team_1', 'Team_2', 'Field_1', '2020-05-27 22:56:04.347', '0', '0', 'League_1', '2020')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the user exists in DB
        preparedStatement = connection.prepareStatement("select * from [Game] where GameId = '1111'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("1111", resultSet.getString(1));

        // check select
        Game selectedGame = gameDBAccess.select("1111");
        assertEquals(1111, selectedGame.getId());
        assertEquals("Team_2", selectedGame.getGuestTeam().getTeamName());

        // delete the user from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Game] where GameId = '1111'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    /*
    @Test
    void selectGamesByUser() throws SQLException {
        assertNull(gameDBAccess.selectGamesByUser(""));
        // check select
        ArrayList<Integer> selectGamesByUser = gameDBAccess.selectGamesByUser("Referee_1");
        assertEquals(1000, selectGamesByUser.get(0));
    }

     */

    @Test
    void conditionedSelect() {
    }
}