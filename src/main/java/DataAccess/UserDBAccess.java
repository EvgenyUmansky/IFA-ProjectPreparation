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
        Connection connection = DBConnector.getConnection();
        String query = "insert into [User] values (?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,user.getUserName());
            statement.setString(2,user.getName());
            statement.setString(3,hash(user.getPassword()));
            statement.setString(4,user.getMail());
            statement.setBoolean(5,user.isClosed());

            statement.executeUpdate();
            connection.commit();

            statement.close();
        }
        catch (SQLException | NullPointerException e){
            System.out.println("Fuck You");
        }


    }


    /**
     * Encrypts the user's password
     * @param password the original password
     * @return
     */
    private String hash(String password) {
        int hash = 7;
        for (int i = 0; i < password.length(); i++) {
            hash = hash * 31 + password.charAt(i);
        }
        return Integer.toString(hash);
    }



    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void select(String id) {

    }
}
