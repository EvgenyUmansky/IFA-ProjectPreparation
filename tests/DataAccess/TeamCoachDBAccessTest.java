package DataAccess;

import domain.Referee;
import domain.RefereeType;
import domain.Team;
import domain.TeamCoach;
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

class TeamCoachDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    TeamCoach teamCoach;
    Team team;
    TeamCoachDBAccess teamCoachDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));
        teamCoach = new TeamCoach("UserName_1", "TestMail@gmail.com");
        team = new Team("Team_1", null, null, "Open");
        teamCoachDBAccess = TeamCoachDBAccess.getInstance();

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
        teamCoach = null;
        team = null;
        teamCoachDBAccess = null;

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
        // teamCoach == null
        teamCoachDBAccess.save(null);
        assertEquals("Couldn't execute 'save(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null\r\n", outContent.toString());
        outContent.reset();

//        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
//
//        // check the teamCoach does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save user to DB: team == null
        teamCoachDBAccess.save(teamCoach);

        // check the teamCoach saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamCoach.getUserName(), resultSet.getString(1));
        assertNull(teamCoach.getCurrentTeam());

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // save user to DB: team != null
        teamCoach.setCurrentTeam(team.getTeamName());
        teamCoachDBAccess.save(teamCoach);

        // check the teamCoach saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamCoach.getUserName(), resultSet.getString(1));
        assertEquals(teamCoach.getCurrentTeam(), resultSet.getString(2));


        // delete the teamCoach from DB
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // teamCoach == null
        teamCoachDBAccess.update(null);
        assertEquals("Couldn't execute 'update(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamCoach to DB
        preparedStatement = connection.prepareStatement("insert into [Coaches] values ('UserName_1', 'Team_1', 'Role',  2)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamCoach exists in DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Team_1", resultSet.getString(2));

        // update teamCoach in DB
        teamCoach.setQualification("5");
        teamCoachDBAccess.update(teamCoach);

        // check the teamCoach updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(teamCoach.getUserName(), resultSet.getString(1));
        assertNull(resultSet.getString(3));
        assertEquals(String.valueOf(teamCoach.getQualification()), resultSet.getString(4));

        // delete the teamCoach from DB
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // teamCoach == null
        teamCoachDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null\r\n", outContent.toString());
        outContent.reset();

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamCoach to DB
        preparedStatement = connection.prepareStatement("insert into [Coaches] values ('UserName_1', 'Team_1', 'ASSISTANT', 0)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamCoach exists in DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // delete user teamCoach DB
        teamCoachDBAccess.delete(teamCoach);

        // check the teamCoach updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(teamCoachDBAccess.select(""));

        // delete the teamCoach from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the teamCoach to DB
        preparedStatement = connection.prepareStatement("insert into [Coaches] values ('UserName_1', 'Team_1', 'Role', 4)");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the teamCoach exists in DB
        preparedStatement = connection.prepareStatement("select * from [Coaches] where username = 'UserName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("UserName_1", resultSet.getString(1));

        // check select
        TeamCoach selectedCoach = teamCoachDBAccess.select("UserName_1");
        assertEquals("UserName_1", selectedCoach.getUserName());
        assertEquals("Team_1", selectedCoach.getCurrentTeam());
        assertEquals("Role", selectedCoach.getRole());
        assertEquals("4", String.valueOf(selectedCoach.getQualification()));
        // delete the teamCoach from DB
        preparedStatement = connection.prepareStatement("delete from [Coaches] where username = 'UserName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

    }
}