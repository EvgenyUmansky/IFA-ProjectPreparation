package DataAccess;

import domain.Team;
import domain.TeamPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class TeamPlayerDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    TeamPlayer teamPlayer;
    Team team;
    TeamPlayerDBAccess teamPlayerDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));
        teamPlayer = new TeamPlayer("UserName_1", "TestMail@gmail.com");
        team = new Team("Team_1", null, null, "Open");
        teamPlayerDBAccess = TeamPlayerDBAccess.getInstance();

        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Managers
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('Team_1', null, null, 'open')");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'Team_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        System.setOut(originalOut);
        teamPlayer = null;
        team = null;
        teamPlayerDBAccess = null;

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
        // teamPlayer == null
        teamPlayerDBAccess.save(null);
        assertEquals("Couldn't execute 'save(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null\r\n", outContent.toString());
        outContent.reset();

       // delete the teamPlayer from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamPlayer does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save teamPlayer to DB
        teamPlayerDBAccess.save(teamPlayer);

        // check the teamPlayer saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamPlayer.getUserName(), resultSet.getString(1));
        assertNull(teamPlayer.getCurrentTeam());

        // delete the teamPlayer from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // teamPlayer == null
        teamPlayerDBAccess.update(null);
        assertEquals("Couldn't execute 'update(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamPlayer from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamPlayer to DB
        preparedStatement = connection.prepareStatement("insert into [Players] values ('UserName_1', null, null, null, null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamPlayer exists in DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // update teamPlayer in DB
        teamPlayer.setBirthDate(new Date(195236));
        teamPlayer.setPosition("Posa_Test");
        teamPlayer.setSquadNumber("10");
        teamPlayerDBAccess.update(teamPlayer);

        // check the teamCoach updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamPlayer.getUserName(), resultSet.getString(1));
        assertEquals(teamPlayer.getBirthDate().toString(), resultSet.getString(2));
        assertEquals(teamPlayer.getPosition(), resultSet.getString(4));
        assertEquals(teamPlayer.getSquadNumber(), resultSet.getString(5));


        // delete the teamCoach from DB
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // teamCoach == null
        teamPlayerDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(TeamPlayer teamPlayer)' in TeamPlayerDBAccess: the teamPlayer is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamCoach to DB
        preparedStatement = connection.prepareStatement("insert into [Players] values ('UserName_1', null, null, null, null)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamCoach exists in DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete user teamCoach DB
        teamPlayerDBAccess.delete(teamPlayer);

        // check the teamCoach updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(teamPlayerDBAccess.select(""));

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamCoach to DB
        preparedStatement = connection.prepareStatement("insert into [Players] values ('UserName_1', null, null, 'Posa_test', '666')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamCoach exists in DB
        preparedStatement = connection.prepareStatement("select * from [Players] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        TeamPlayer selectedPlayer = teamPlayerDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedPlayer.getUserName());
        assertEquals("Posa_test", selectedPlayer.getPosition());
        assertEquals("666", selectedPlayer.getSquadNumber());

        // delete the teamCoach from DB
        preparedStatement = connection.prepareStatement("delete from [Players] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    void conditionedSelect() {
        teamPlayerDBAccess.conditionedSelect(null);
    }
}