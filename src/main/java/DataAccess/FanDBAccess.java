package DataAccess;

import domain.Fan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        Connection connection = DBConnector.getConnection();
        String query = "insert into Fan values (?,?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,fan.getUserName());
            statement.setBoolean(2,fan.isMail());


            statement.executeUpdate();
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Couldn't execute");
        }
    }


    @Override
    public void update(Fan fan) {
        String query = "update Fan " +
                "set Username = ?, IsMail = ?" +
                "where Username = ?";
        Connection connection = DBConnector.getConnection();

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,fan.getUserName());
            statement.setBoolean(2,fan.isMail());

            statement.executeUpdate();
        }
        catch(SQLException e){

        }
    }

    @Override
    public void delete(Fan fan) {
        String query = "delete from Fan where username = ?";
        Connection connection = DBConnector.getConnection();

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,fan.getUserName());
            statement.executeUpdate();
        }
        catch(SQLException e){

        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public Fan select(String username) {
        String query = "select * from Fan where username = ?";
        Connection connection = DBConnector.getConnection();
        Fan fan = null;

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);
            ResultSet retrievedFan = statement.executeQuery();

            if(retrievedFan.next()){
                boolean isMail = retrievedFan.getBoolean(2);
                fan = new Fan(username,"sdl;");
            }
        }
        catch (SQLException e){

        }
        return fan;
    }


}
