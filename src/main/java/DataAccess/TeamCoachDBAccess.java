package DataAccess;
import domain.TeamCoach;

import java.sql.*;
import java.util.HashMap;


public class TeamCoachDBAccess implements DBAccess<TeamCoach> {

    private static final TeamCoachDBAccess instance = new TeamCoachDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private TeamCoachDBAccess(){

    }

    public static TeamCoachDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(TeamCoach teamCoach) {
        if(teamCoach == null){
            System.out.println("Couldn't execute 'save(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Coaches] values (?, ?, ?, ?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1,teamCoach.getUserName());
            if(teamCoach.getCurrentTeam() != null) {
                statement.setString(2, teamCoach.getCurrentTeam().getTeamName());
            }
            else{
                statement.setString(2, null);
            }
            statement.setString(3,teamCoach.getRole());
            statement.setString(4,teamCoach.getQualification());


            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Couldn't execute 'save(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'save(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
            }
        }
    }


    @Override
    public void update(TeamCoach teamCoach) {
        if(teamCoach == null){
            System.out.println("Couldn't execute 'update(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null");
            return;
        }

        String query = "update [Coaches] " +
                "set TeamName = ?, Role = ?, Qualification = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,teamCoach.getCurrentTeam().getTeamName());
            statement.setString(2,teamCoach.getRole());
            statement.setString(3,teamCoach.getQualification());
            statement.setString(4,teamCoach.getUserName());


            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'update(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'update(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
            }
        }
    }


    @Override
    public void delete(TeamCoach teamCoach) {
        if(teamCoach == null){
            System.out.println("Couldn't execute 'delete(TeamCoach teamCoach)' in TeamCoachDBAccess: the teamCoach is null");
            return;
        }

        String query = "delete from [Coaches] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,teamCoach.getUserName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'delete(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public TeamCoach select(String username) {
        String query = "select * from [Coaches] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        TeamCoach teamCoach = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUser = statement.executeQuery();

            if(retrievedUser.next()){
                String teamName = retrievedUser.getString(2);
                String role =  retrievedUser.getString(3);
                String qualification =  retrievedUser.getString(4);


                teamCoach = new TeamCoach(username, qualification);
                teamCoach.setRole(role);
                // TODO: 19/05/2020 set current team of teamCoach??
            }
        }
        catch (SQLException e){
            assert false;
            System.out.println("Couldn't execute 'select(TeamCoach teamCoach)' in TeamCoachDBAccess for " + teamCoach.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedUser != null) {
                    retrievedUser.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(TeamCoach teamCoach)' in UserDBAccess for " + teamCoach.getUserName());
            }
        }
        return teamCoach;
    }

    @Override
    public HashMap<String, TeamCoach> conditionedSelect(String[] conditions) {
        return null;
    }

}