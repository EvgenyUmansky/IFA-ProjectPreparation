package DataAccess;

import domain.League;
import domain.Team;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TeamDBAccess implements DBAccess<Team>{
    static Logger logger = Logger.getLogger(TeamDBAccess.class.getName());
    private static final TeamDBAccess instance = new TeamDBAccess();

    public TeamDBAccess() {
    }

    public static TeamDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(Team team) {
        if(team == null){
            //TODO: change this to an alert
            logger.error("Couldn't execute 'save(Team team)' in TeamDBAccess: the team is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into Teams values (?, ?, ?, ?)";

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,team.getTeamName());
            statement.setString(2,team.getStadium().getFieldName());
            statement.setString(3,team.getTeamEmail());
            statement.setString(4,team.getTeamStatus().toString());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e){
            //TODO: throw an exception and catch it outside and pop an alert
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
            catch (SQLException e3) {
                //TODO: throw an exception and catch it outside and pop an alert
                logger.error(e3.getMessage());
                e3.printStackTrace();
            }
        }
    }

    @Override
    public void update(Team team) {
        if(team == null){
            //TODO: change this to an alert
            logger.error("Couldn't execute 'update(Team team)' in TeamDBAccess: the team is null");
            return;
        }

        String query = "update Teams " +
                "set FieldName = ?, Email = ?, Status = ? " +
                "where TeamName = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,team.getStadium().getFieldName());
            statement.setString(2,team.getTeamEmail());
            statement.setString(3,team.getTeamStatus().toString());
            statement.setString(4,team.getTeamName());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e){
            //TODO: change this to an alert
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
            catch (SQLException e3) {
                //TODO: change this to an alert
                logger.error(e3.getMessage());
                e3.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Team team) {
        if(team == null){
            //TODO: change this to an alert
            logger.error("Couldn't execute 'delete(Team team)' in TeamDBAccess: the team is null");
            return;
        }

        String query = "delete from Teams where TeamName = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,team.getTeamName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e) {
            //TODO: change this to an alert
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
            catch (SQLException e3) {
                //TODO: change this to an alert
                logger.error(e3.getMessage());
                e3.printStackTrace();
            }
        }

    }

    @Override
    public Team select(String teamName) {
        String query = "select * from Teams where TeamName = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedTeam = null;
        Team team = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,teamName);
            retrievedTeam = statement.executeQuery();

            if(retrievedTeam.next()){
                String fieldName = retrievedTeam.getString(2);
                String mail =  retrievedTeam.getString(3);
                String status = retrievedTeam.getString(4);

                team = new Team(teamName, mail, fieldName, status);
            }
        }
        catch (SQLException e){
            //TODO: change this to an alert
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedTeam != null) {
                    retrievedTeam.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                //TODO: change this to an alert
                logger.error(e3.getMessage());
                e3.printStackTrace();
            }
        }
        return team;
    }

    @Override
    public HashMap<String, Team> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedTeams = null;
        HashMap<String, Team> teams = new HashMap<>();

        if(conditions.length == 0){
            query = "select * from [Teams]";
        }
        else {
            query = "select * from [Teams] where";

            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += ",";
                }
            }
        }
        try {
            statement = connection.prepareStatement(query);

            if(conditions.length > 0) {
                int i = 0;
                while (i < conditions.length) {
                    switch (conditions[i].toLowerCase()) {
                        case "teamname":
                        case "fieldname":
                        case "email":
                        case "status":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedTeams = statement.executeQuery();


            while(retrievedTeams.next()){
                String teamName = retrievedTeams.getString(1);
                String fieldName = retrievedTeams.getString(2);
                String email = retrievedTeams.getString(3);
                String status = retrievedTeams.getString(4);


                teams.put(teamName,new Team(teamName,fieldName,email,status));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return teams;
    }
}