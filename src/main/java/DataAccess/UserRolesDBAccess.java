package DataAccess;

import domain.Role;
import domain.User;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRolesDBAccess implements DBAccess< Pair<String, ArrayList<String>> >{

    private static final UserRolesDBAccess instance = new UserRolesDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private UserRolesDBAccess(){

    }

    public static UserRolesDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(Pair<String,ArrayList<String>> roles) {
        if(roles == null){
            System.out.println("Couldn't execute 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles is null");
            return;
        }

        if(roles.getValue() == null){
            System.out.println("Couldn't execute 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles.getValue() is null");
            return;
        }


        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        String userName = roles.getKey();
        ArrayList<String> rawRoles = roles.getValue();

        String query = "insert into [UserRoles] values (?, ?)";
        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            for(String role : rawRoles) {
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setString(2, role);

                statement.executeUpdate();
                connection.commit();
            }
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Couldn't execute 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess for " + userName);
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'save(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess for " + userName);
            }
        }
    }

    @Override
    public void update(Pair<String,ArrayList<String>> roles) {

    }

    @Override
    public void delete(Pair<String,ArrayList<String>> roles) {
        if(roles == null){
            System.out.println("Couldn't execute 'delete(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles is null");
            return;
        }

        if(roles.getValue() == null){
            System.out.println("Couldn't execute 'delete(Pair<String,ArrayList<String>> roles)' in UserRolesDBAccess: the roles.getValue() is null");
            return;
        }

        String query = "delete from [UserRoles] where username = ? and Role = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        String userName = roles.getKey();
        ArrayList<String> rawRoles = roles.getValue();

        try{
            for(String role : rawRoles) {
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setString(2, role);

                statement.executeUpdate();
                connection.commit();
            }
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'delete(User user)' in UserDBAccess for " + userName);
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(User user)' in UserDBAccess for " + userName);
            }
        }

    }

    @Override
    public Pair<String,ArrayList<String>> select(String username) {
        String query = "select * from [UserRoles] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        ArrayList<String> roles = new ArrayList<>();
        Pair<String, ArrayList<String>> userRoles = null;


        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUsers = statement.executeQuery();

            while(retrievedUsers.next()){
                roles.add(retrievedUsers.getString(2));
            }
            userRoles = new Pair<>(username,roles);
        }
        catch (SQLException e){
            //FIXME: change this to exception
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedUsers != null) {
                    retrievedUsers.close();
                }
                connection.close();
            } catch (SQLException e3) {
                //FIXME: change this to exception
                e3.printStackTrace();
            }
        }
        return userRoles;
    }

    @Override
    public HashMap<String, Pair<String, ArrayList<String>>> conditionedSelect(String[] conditions) {
        return null;
    }
}