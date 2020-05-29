package DataAccess;

import domain.Fan;
import domain.Referee;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameFansDBAccess implements DBAccess<Pair<String, ArrayList<Fan>>>{
    static Logger logger = Logger.getLogger(AssAgentDBAccess.class.getName());

    private static final GameFansDBAccess instance = new GameFansDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private GameFansDBAccess() {

    }

    public static GameFansDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Pair<String, ArrayList<Fan>> stringArrayListPair) {

    }

    @Override
    public void update(Pair<String, ArrayList<Fan>> stringArrayListPair) {

    }

    @Override
    public void delete(Pair<String, ArrayList<Fan>> stringArrayListPair) {

    }

    public Pair<String, ArrayList<Fan>> select(String gameId) {
        String query = "select [FansInGames].GameId, [User].Username, [User].[Name], [User].Mail, [User].IsMail " +
                "from [FansInGames] " +
                "join [User] on [FansInGames].username = [User].username " +
                "where GameId = ?";


        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedFans = null;
        ArrayList<Fan> fans = new ArrayList<>();

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, gameId);
            retrievedFans = statement.executeQuery();

            while(retrievedFans.next()){
                String userName = retrievedFans.getString(2);
                String name = retrievedFans.getString(3);
                String mail = retrievedFans.getString(4);
                boolean isMail = retrievedFans.getBoolean(5);

                Fan fan = new Fan(userName, mail, name);
                fan.setMail(isMail);

                fans.add(fan);
            }
        }
        catch (SQLException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedFans != null) {
                    retrievedFans.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return new Pair<>(gameId, fans);
    }

    @Override
    public HashMap<String, Pair<String, ArrayList<Fan>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
