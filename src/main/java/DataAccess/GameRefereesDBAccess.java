package DataAccess;

import domain.Game;
import domain.Referee;
import domain.RefereeType;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameRefereesDBAccess implements DBAccess<Pair<String, ArrayList<Referee>>>{
    static Logger logger = Logger.getLogger(AssAgentDBAccess.class.getName());

    private static final GameRefereesDBAccess instance = new GameRefereesDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private GameRefereesDBAccess() {

    }

    public static GameRefereesDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Pair<String, ArrayList<Referee>> stringArrayListPair) {

    }

    @Override
    public void update(Pair<String, ArrayList<Referee>> stringArrayListPair) {

    }

    @Override
    public void delete(Pair<String, ArrayList<Referee>> stringArrayListPair) {

    }

    public Pair<String, ArrayList<Referee>> select(String gameId) {
        String query = "select [RefereesInGames].GameId, [User].Username, [User].[Name], [User].Mail, [User].IsMail, [Referee].Qualification, [Referee].[Type] " +
                "from [RefereesInGames] " +
                "join [User] on [RefereesInGames].username = [User].username " +
                "join [Referee] on [RefereesInGames].username = [Referee].username " +
                "where GameId = ?";


        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedReferees = null;
        ArrayList<Referee> referees = new ArrayList<>();

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, gameId);
            retrievedReferees = statement.executeQuery();

            while(retrievedReferees.next()){
                String userName = retrievedReferees.getString(2);
                String name = retrievedReferees.getString(3);
                String mail = retrievedReferees.getString(4);
                boolean isMail = retrievedReferees.getBoolean(5);
                int qualification = retrievedReferees.getInt(6);
                String type = retrievedReferees.getString(7);

                Referee referee = new Referee(userName, mail, name);
                referee.setMail(isMail);
                referee.setQualification(qualification);
                referee.setRefereeType(RefereeType.valueOf(type));

                referees.add(referee);
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
                if (retrievedReferees != null) {
                    retrievedReferees.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return new Pair<>(gameId, referees);
    }

    @Override
    public HashMap<String, Pair<String, ArrayList<Referee>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
