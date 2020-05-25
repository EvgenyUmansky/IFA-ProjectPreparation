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
        String query = "insert into [Coaches] values (?, ?, ?, ?,?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1,teamCoach.getUserName());
            if(teamCoach.getCurrentTeam() != null) {
                statement.setString(2, teamCoach.getCurrentTeam());
            }
            else{
                statement.setString(2, null);
            }
            statement.setString(3,teamCoach.getRole());
            statement.setString(4,teamCoach.getQualification());
            statement.setString(5,teamCoach.getName());


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
                "set TeamName = ?, Role = ?, Qualification = ?, Name = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,teamCoach.getCurrentTeam());
            statement.setString(2,teamCoach.getRole());
            statement.setString(3,teamCoach.getQualification());
            statement.setString(4,teamCoach.getName());
            statement.setString(5,teamCoach.getUserName());


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
                String name =  retrievedUser.getString(5);


                teamCoach = new TeamCoach(username, "",role, qualification,name);
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
        String query = "select * from [Coaches] where";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedCoaches = null;
        HashMap<String, TeamCoach> coaches = new HashMap<>();

        for (int i = 0; i < conditions.length; i++) {
            if (i % 2 == 0) {
                query += " " + conditions[i];
            } else {
                query += " = ?";
                if (i < conditions.length - 1)
                    query += ",";
            }
        }
        try {
            statement = connection.prepareStatement(query);
            int i = 0;
            while (i < conditions.length) {
                switch (conditions[i].toLowerCase()) {
                    case "username":
                    case "teamname":
                    case "role":
                    case "qualification":
                    case "name":
                        statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                        break;


                    default:
                        break;
                }
                i += 2;
            }

            retrievedCoaches = statement.executeQuery();


            while(retrievedCoaches.next()){
                String username = retrievedCoaches.getString(1);
                String teamname =  retrievedCoaches.getString(2);
                String role =  retrievedCoaches.getString(3);
                String qualification =  retrievedCoaches.getString(4);
                String name =  retrievedCoaches.getString(5);

                coaches.put(username,new TeamCoach(username, "",role, qualification,name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coaches;
    }

}