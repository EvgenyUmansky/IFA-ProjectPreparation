package DataAccess;
import domain.User;

import java.sql.*;


public class UserDBAccess implements DBAccess<User> {

    private static final UserDBAccess instance = new UserDBAccess();
  /*  private DBConnector dbc = DBConnector.getInstance();*/

    private UserDBAccess(){

    }

    public static UserDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(User user) {
        if(user == null){
            System.out.println("Couldn't execute 'save(User user)' in UserDBAccess: the user is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [User] values (?, ?, ?, ?, ?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1,user.getUserName());
            statement.setString(2,user.getName());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getMail());
            statement.setBoolean(5,user.isClosed());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Couldn't execute 'save(User user)' in UserDBAccess for " + user.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'save(User user)' in UserDBAccess for " + user.getUserName());
            }
        }
    }


    @Override
    public void update(User user) {
        String query = "update [User] " +
                "set Name = ?, Password = ?, Email = ?, Activated = ?" +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,user.getName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getMail());
            statement.setBoolean(4,user.isClosed());
            statement.setString(5,user.getUserName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'update(User user)' in UserDBAccess for " + user.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'update(User user)' in UserDBAccess for " + user.getUserName());
            }
        }
    }

    @Override
    public void delete(User user) {
        String query = "delete from [User] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,user.getUserName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'delete(User user)' in UserDBAccess for " + user.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(User user)' in UserDBAccess for " + user.getUserName());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public User select(String username) {
        String query = "select * from [User] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        User user = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUser = statement.executeQuery();
            
            if(retrievedUser.next()){
                String name = retrievedUser.getString(2);
                String password =  retrievedUser.getString(3);
                String mail =  retrievedUser.getString(4);
                boolean isClosed = retrievedUser.getBoolean(5);
                user = new User(username,password,name,mail,isClosed);
            }
        }
        catch (SQLException e){
            assert false;
            System.out.println("Couldn't execute 'select(User user)' in UserDBAccess for " + user.getUserName());
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
                System.out.println("Couldn't close 'delete(User user)' in UserDBAccess for " + user.getUserName());
            }
        }
        return user;
    }





}
