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

public class UserRolesDBAccess implements DBAccess<Pair<String,ArrayList<String>>>{

    private static final UserRolesDBAccess instance = new UserRolesDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private UserRolesDBAccess(){

    }

    public static UserRolesDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(Pair<String,ArrayList<String>> roles) {

    }

    @Override
    public void update(Pair<String,ArrayList<String>> roles) {

    }

    @Override
    public void delete(Pair<String,ArrayList<String>> roles) {

    }

    @Override
    public Pair<String,ArrayList<String>> select(String username) {
        String query = "select * from [UserRoles] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        ArrayList<String> roles = new ArrayList<>();
        Pair<String,ArrayList<String>> userRoles = null;


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
    public HashMap<String, Pair<String,ArrayList<String>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
