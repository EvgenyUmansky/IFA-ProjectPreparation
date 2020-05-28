package DataAccess;

import domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class UserDBAccess implements DBAccess<User> {

    static Logger logger = Logger.getLogger(NotificationDBAccess.class.getName());
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
            logger.error("Couldn't execute 'save(User user)' in UserDBAccess: the user is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [User] values (?, ?, ?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,user.getUserName());
            statement.setString(2,user.getName());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getMail());
            statement.setBoolean(5,user.isClosed());
            statement.setBoolean(6,user.isMail());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
    }


    @Override
    public void update(User user) {
        if(user == null){
            logger.error("Couldn't execute 'update(User user)' in UserDBAccess: the user is null");
            return;
        }

        String query = "update [User] " +
                "set Name = ?, Password = ?, Mail = ?, IsClosed = ?, IsMail = ? " +
                "where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,user.getName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getMail());
            statement.setBoolean(4,user.isClosed());
            statement.setBoolean(5,user.isMail());
            statement.setString(6,user.getUserName());


            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
    }

    @Override
    public void delete(User user) {
        if(user == null){
            logger.error("Couldn't execute 'delete(User user)' in UserDBAccess: the user is null");
            return;
        }

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
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                logger.error("Couldn't close 'delete(User user)' in UserDBAccess for " + user.getUserName());
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
                boolean isMail = retrievedUser.getBoolean(6);

                user = new User(username, password, name, mail, isClosed, isMail);
            }
        }
        catch (SQLException e){
            assert false;
            logger.error(e.getMessage());
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
            } catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
        return user;
    }

    @Override
    public HashMap<String, User> conditionedSelect(String[] conditions) {
        String query = "select * from [User] where";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUsers = null;
        HashMap<String, User> users = new HashMap<>();

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
                    case "name":
                    case "mail":
                    case "password":
                        statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                        break;

                    case "ismail":
                    case "isclosed":
                        statement.setBoolean((int) (i / 2) + 1, conditions[i + 1].equals("true"));
                        break;

                    default:
                        break;
                }
                i += 2;
            }

            retrievedUsers = statement.executeQuery();


            while(retrievedUsers.next()){
                String username = retrievedUsers.getString(1);
                String name = retrievedUsers.getString(2);
                String password =  retrievedUsers.getString(3);
                String mail =  retrievedUsers.getString(4);
                boolean isClosed = retrievedUsers.getBoolean(5);
                boolean isMail = retrievedUsers.getBoolean(6);

                users.put(username,new User(username, password, name, mail, isClosed, isMail));
            }
        } catch (SQLException e) {
            // TODO: Alert window
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return users;
    }


}