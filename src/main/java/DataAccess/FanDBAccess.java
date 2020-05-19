package DataAccess;
import domain.Fan;

import java.sql.*;


public class FanDBAccess implements DBAccess<Fan> {

    private static final FanDBAccess instance = new FanDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private FanDBAccess(){

    }

    public static FanDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(Fan fan) {
        if(fan == null){
            System.out.println("Couldn't execute 'save(Fan fan)' in FanDBAccess: the fan is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Fan] values (?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1,fan.getUserName());


            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Couldn't execute 'save(Fan fan)' in FanDBAccess for " + fan.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'save(Fan fan)' in UserDBAccess for " + fan.getUserName());
            }
        }
    }


    @Override
    public void update(Fan fan) {

    }

    @Override
    public void delete(Fan fan) {
        if(fan == null){
            System.out.println("Couldn't execute 'delete(Fan fan)' in FanDBAccess: the fan is null");
            return;
        }

        String query = "delete from [Fan] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,fan.getUserName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            System.out.println("Couldn't execute 'delete(Fan fan)' in FanDBAccess for " + fan.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                System.out.println("Couldn't close 'delete(Fan fan)' in FanDBAccess for " + fan.getUserName());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public Fan select(String username) {
        String query = "select * from [Fan] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        Fan fan = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUser = statement.executeQuery();

            if(retrievedUser.next()){


                fan = new Fan(username, "");
            }
        }
        catch (SQLException e){
            assert false;
            System.out.println("Couldn't execute 'select(Fan fan)' in FanDBAccess for " + fan.getUserName());
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
                System.out.println("Couldn't close 'delete(Fan fan)' in FanDBAccess for " + fan.getUserName());
            }
        }
        return fan;
    }
}