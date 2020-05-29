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
        if(stringArrayListPair == null){
            logger.error("Couldn't execute 'save(Pair<String, ArrayList<Game>> stringArrayListPair)' in FanGamesDBAccess: the stringArrayListPair is null");
            System.out.println("Couldn't execute 'save(Pair<String, ArrayList<Game>> stringArrayListPair)' in FanGamesDBAccess: the stringArrayListPair is null");
            return;
        }

        if(stringArrayListPair.getValue() == null){
            logger.error("Couldn't execute 'save(Pair<String, ArrayList<Game>> stringArrayListPair)' in FanGamesDBAccess: the stringArrayListPair.getValue() is null");
            System.out.println("Couldn't execute 'save(Pair<String, ArrayList<Game>> stringArrayListPair)' in FanGamesDBAccess: the stringArrayListPair.getValue() is null");
            return;
        }


        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        String userName = stringArrayListPair.getKey();
        ArrayList<Game> games = stringArrayListPair.getValue();

        String query = "insert into [FansInGames] values (?, ?)";
        try {
            for(Game game : games) {
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setInt(2, game.getId());

                statement.executeUpdate();
                connection.commit();
            }
        }
        catch (SQLException | NullPointerException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
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
                "full join [Game] on [Game].gameID = [FansInGames].gameID " +
                "full join [GameEvent]  on [Game].gameID = [FansInGames].gameID " +
                "where username = ? order by [Game].GameID";

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
                if(!(gameIDs.contains(gameID))){
                    gameIDs.add(gameID);
                    game = new Game(gameID, new League(league, season), hostTeam,guestTeam,fieldName,gameDate,hostTeamScore,guestTeamScore);
                    games.add(game);
                }

                if(eventID != 0) {
                    LocalDateTime eventDate = retrievedGames.getTimestamp(11).toLocalDateTime();
                    String eventName = retrievedGames.getString(12);
                    String description = retrievedGames.getString(13);
                    game.addEvent(new GameEvent(eventID, gameID, gameDate, (int) ChronoUnit.MINUTES.between(eventDate, gameDate), eventName, description));
                }
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


    /**
     * @param conditions
     * @return
     */
    @Override
    public HashMap<String, Pair<String, ArrayList<Game>>> conditionedSelect(String[] conditions) {
        return null;
    }
}
