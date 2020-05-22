package DataAccess;

import domain.Field;
import domain.Team;

import domain.TeamOwner;
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

class TeamDBAccessTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); // prints log - for exception
    private final PrintStream originalOut = System.out;

    Team team;
    Field field;
    TeamDBAccess teamDBAccess;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @BeforeEach
    public void insertBeforeTest() throws SQLException {
        System.setOut(new PrintStream(outContent));
        team = new Team("TeamName_1", new Field("Field_1", 100), new TeamOwner("Owner_1", ""));
        field = new Field("Field_2", 200);
        teamDBAccess = TeamDBAccess.getInstance();

        connection = DBConnector.getConnection();
        preparedStatement = null;
        resultSet = null;

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fields] where FieldName = 'Filed_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert team mock to DB for FK "teamName" in Managers
        preparedStatement = connection.prepareStatement("insert into [Fields] values ('Field_1', 100)");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @AfterEach
    public void deleteAfterTest() throws SQLException {
        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Fields] where FieldName = 'Field_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        System.setOut(originalOut);
        team = null;
        field = null;
        teamDBAccess = null;

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
        // team == null
        teamDBAccess.save(null);
        assertEquals("Couldn't execute 'save(Team team)' in TeamDBAccess: the team is null\r\n", outContent.toString());
        outContent.reset();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the team does not exist in DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        assertFalse(preparedStatement.executeQuery().next());

        // save team to DB
        teamDBAccess.save(team);

        // check the team saved in the DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(team.getTeamName(), resultSet.getString(1));

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void update() throws SQLException {
        // team == null
        teamDBAccess.update(null);
        assertEquals("Couldn't execute 'update(Team team)' in TeamDBAccess: the team is null\r\n", outContent.toString());
        outContent.reset();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the team to DB
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('TeamName_1', 'Field_1', 'testmail@gmail.com',  'Test_Status')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the team exists in DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("TeamName_1", resultSet.getString(1));

        // update team in DB
        teamDBAccess.update(team);

        // check the team updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(team.getTeamName(), resultSet.getString(1));
        assertNull(resultSet.getString(3));
        assertEquals(team.getTeamStatus().toString(), resultSet.getString(4));

        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    @Test
    public void delete() throws SQLException {
        // team == null
        teamDBAccess.delete(null);
        assertEquals("Couldn't execute 'delete(Team team)' in TeamDBAccess: the team is null\r\n", outContent.toString());
        outContent.reset();

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the team to DB
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('TeamName_1', 'Field_1', 'testmail@gmail.com',  'Test_Status')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the team exists in DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("TeamName_1", resultSet.getString(1));

        // delete user team DB
        teamDBAccess.delete(team);

        // check the team updated in the DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void select() throws SQLException {
        assertNull(teamDBAccess.select(""));

        // delete the team from DB if exists
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

        // insert the team to DB
        preparedStatement = connection.prepareStatement("insert into [Teams] values ('TeamName_1', 'Field_1', 'testmail@gmail.com',  'open')");
        preparedStatement.executeUpdate();
        connection.commit();

        // check the team exists in DB
        preparedStatement = connection.prepareStatement("select * from [Teams] where TeamName = 'TeamName_1'" );
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("TeamName_1", resultSet.getString(1));

        // check select
        Team selectedTeam = teamDBAccess.select("TeamName_1");
        assertEquals("TeamName_1", selectedTeam.getTeamName());
        assertEquals("Field_1", selectedTeam.getStadium().getFieldName());
        assertEquals("testmail@gmail.com", selectedTeam.getTeamEmail());
        assertEquals("open", selectedTeam.getTeamStatus().toString());
        // delete the team from DB
        preparedStatement = connection.prepareStatement("delete from [Teams] where TeamName = 'TeamName_1'");
        preparedStatement.executeUpdate();
        connection.commit();

    }

    @Test
    void conditionedSelect() {
        teamDBAccess.conditionedSelect(null);
    }
}