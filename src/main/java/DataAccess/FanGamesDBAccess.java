package DataAccess;

import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class FanGamesDBAccess implements DBAccess<Pair<String, ArrayList<Game>>>{
    static Logger logger = Logger.getLogger(AssAgentDBAccess.class.getName());

    private static final FanGamesDBAccess instance = new FanGamesDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private FanGamesDBAccess() {

    }

    public static FanGamesDBAccess getInstance() {
        return instance;
    }

    /**
     * @param stringArrayListPair
     */
    @Override
    public void save(Pair<String, ArrayList<Game>> stringArrayListPair) {

    }

    /**
     * @param stringArrayListPair
     */
    @Override
    public void update(Pair<String, ArrayList<Game>> stringArrayListPair) {

    }

    /**
     * @param stringArrayListPair
     */
    @Override
    public void delete(Pair<String, ArrayList<Game>> stringArrayListPair) {

    }

    /**
     * @param username
     */
    @Override
    public Pair<String, ArrayList<Game>> select(String username) {
        String query = "select [Game].*, [GameEvent].EventID, [GameEvent].EventDate,[GameEvent].EventName, [GameEvent].Description " +
                "from [FansInGames] " +
                "join [Game] on [Game].gameID = [FansInGames].gameID " +
                "join [GameEvent]  on [Game].gameID = [FansInGames].gameID " +
                "where username = '' order by [Game].GameID";

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedGames = null;
        ArrayList<Game> games = new ArrayList<>();
        ArrayList<Integer> gameIDs = new ArrayList<>();
        Game game = null;


        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedGames = statement.executeQuery();

            while(retrievedGames.next()){
                int gameID = retrievedGames.getInt(1);
                String hostTeam = retrievedGames.getString(2);
                String guestTeam = retrievedGames.getString(3);
                String fieldName = retrievedGames.getString(4);
                LocalDateTime gameDate = retrievedGames.getTimestamp(5).toLocalDateTime();
                int hostTeamScore = retrievedGames.getInt(6);
                int guestTeamScore = retrievedGames.getInt(7);
                String league = retrievedGames.getString(8);
                int season = retrievedGames.getInt(9);
                int eventID = retrievedGames.getInt(10);
                LocalDateTime eventDate = retrievedGames.getTimestamp(11).toLocalDateTime();
                String eventName =retrievedGames.getString(12);
                String description = retrievedGames.getString(13);

                if(!(gameIDs.contains(gameID))){
                    gameIDs.add(gameID);
                    game = new Game(gameID, new League(league, season), hostTeam,guestTeam,fieldName,gameDate,hostTeamScore,guestTeamScore);
                    games.add(game);
                }

                game.addEvent(new GameEvent(eventID, gameID, gameDate,(int) ChronoUnit.MINUTES.between(eventDate, gameDate), eventName,description));
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
                if (retrievedGames != null) {
                    retrievedGames.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return new Pair<>(username,games);
    }

    public Pair<String, ArrayList<Fan>> selectFansOfGame(String gameId) {
        String query = "select [FansInGames].GameId, [User].Username, [User].Mail, [User].IsMail " +
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
                String mail = retrievedFans.getString(3);
                boolean isMail = retrievedFans.getBoolean(4);

                Fan referee = new Fan(userName, mail);
                referee.setMail(isMail);

                fans.add(referee);
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

    /**
     * @param conditions
     * @return
     */
    @Override
    public HashMap<String, Pair<String, ArrayList<Game>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
