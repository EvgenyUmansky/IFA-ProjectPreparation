package DataAccess;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class TeamFieldsDBAccess implements DBAccess<Pair<String,String>>{

    private static final TeamFieldsDBAccess instance = new TeamFieldsDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private TeamFieldsDBAccess() {

    }

    public static TeamFieldsDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Pair<String, String> teamFieldPair) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [TeamFields] values (?, ?)";

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, teamFieldPair.getKey());
            statement.setString(2, teamFieldPair.getValue());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void update(Pair<String, String> stringStringPair) {

    }

    @Override
    public void delete(Pair<String, String> stringStringPair) {

    }

    @Override
    public Pair<String, String> select(String id) {
        return null;
    }

    @Override
    public HashMap<String, Pair<String, String>> conditionedSelect(String[] conditions) {
        return null;
    }
}
