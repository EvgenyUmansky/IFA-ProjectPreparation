package DataAccess;

import domain.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TeamDBAccess implements DBAccess<Team>{

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
            }
        }
    }

    @Override
    public void update(Team team) {
        if(team == null){
            //TODO: change this to an alert
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
            }
        }
    }

    @Override
    public void delete(Team team) {
        if(team == null){
            //TODO: change this to an alert
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

                team = new Team(teamName,mail,fieldName,status);
            }
        }
        catch (SQLException e){
            //TODO: change this to an alert
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
            }
        }
        return team;
    }

    @Override
    public HashMap<String, Team> conditionedSelect(String[] conditions) {
        return null;
    }
}