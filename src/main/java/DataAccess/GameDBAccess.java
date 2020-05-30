package DataAccess;

import domain.Game;
import domain.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class GameDBAccess implements DBAccess<Game>{
    static Logger logger = Logger.getLogger(GameDBAccess.class.getName());

    private static final GameDBAccess instance = new GameDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private GameDBAccess(){

    }

    public static GameDBAccess getInstance(){
        return instance;
    }


    /**
     * Saves a game as a record in the matching table in the database
     * @param game the game
     */
    @Override
    public void save(Game game) {
        if(game == null){
            logger.error("game == null in GameDBAccess save(Game game)");
            System.out.println("game == null in GameDBAccess save(Game game)");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Game] values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            statement = connection.prepareStatement(query);
           // statement.setInt(1,game.getId());
            statement.setString(2,game.getHostTeam().getTeamName());
            statement.setString(3,game.getGuestTeam().getTeamName());
            statement.setString(4,game.getField().getFieldName());
            statement.setTimestamp(5, Timestamp.valueOf(game.getGameDate()));
            statement.setInt(6,game.getHostTeamScore());
            statement.setInt(7,game.getGuestTeamScore());
            statement.setString(8,game.getLeague().getLeagueName());
            statement.setInt(9,game.getLeague().getSeason());

            statement.executeUpdate();
            connection.commit();
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
                connection.close();
            }
            catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game's matching record in the database
     * @param game the game
     */
    @Override
    public void update(Game game) {

    }

    /**
     * Deletes the game's matching record from the database
     * @param game the game
     */
    @Override
    public void delete(Game game) {

    }

    /**
     * Retrieves a game that matches the given id from the database
     * @param gameIDString the id of the game
     */
    @Override
    public Game select(String gameIDString) {
        if(gameIDString.isEmpty()){
            return null;
        }
        int gameID = Integer.valueOf(gameIDString);
        String query = "select * from [Game] where gameID = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedGame = null;
        Game game = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,gameID);
            retrievedGame = statement.executeQuery();

            if(retrievedGame.next()){
                String hostTeamName = retrievedGame.getString(2);
                String guestTeamName = retrievedGame.getString(3);
                String fieldName = retrievedGame.getString(4);
                LocalDateTime gameDate = retrievedGame.getTimestamp(5).toLocalDateTime();
                int hostScore = retrievedGame.getInt(6);
                int guestScore = retrievedGame.getInt(7);
                String leagueName = retrievedGame.getString(8);
                int season = retrievedGame.getInt(9);

                game = new Game(gameID,hostTeamName,guestTeamName,fieldName,gameDate,hostScore,guestScore,leagueName,season);
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
                if (retrievedGame != null) {
                    retrievedGame.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return game;
    }


//    /**
//     * Retrieves a games that user follows
//     * @param username the id of the game
//     */
//    public ArrayList<Integer> selectGamesByUser(String username) {
//        String query = "select Game.GameId, RefereesInGames.UserName as RefereeName, FansInGames.UserName as FanName " +
//                "from Game " +
//                "join RefereesInGames on Game.GameId = RefereesInGames.GameId " +
//                "join FansInGames on Game.GameId = FansInGames.GameId " +
//                "where RefereesInGames.UserName = ? or FansInGames.UserName = ?";
//        Connection connection = DBConnector.getConnection();
//        PreparedStatement statement = null;
//        ResultSet retrievedGame = null;
//        ArrayList<Integer> gameIds = new ArrayList<>();
//
//        try{
//            statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            statement.setString(2, username);
//            retrievedGame = statement.executeQuery();
//
//            if(retrievedGame.next()){
//                int gameId = Integer.parseInt(retrievedGame.getString(1));
//                gameIds.add(gameId);
//            }
//        }
//        catch (SQLException e){
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (retrievedGame != null) {
//                    retrievedGame.close();
//                }
//                connection.close();
//            } catch (SQLException e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        return gameIds;
//    }
//
//
//
//    public int selectGameId(String hostTeam, String guestTeam, String gameDate) {
//        String query = "select * from [Game] where HostTeam = ? and GuestTeam = ? and GameDate = ?";
//        Connection connection = DBConnector.getConnection();
//        PreparedStatement statement = null;
//        ResultSet retrievedGame = null;
//        int gameId = 0;
//
//        try{
//            statement = connection.prepareStatement(query);
//            statement.setString(1, hostTeam);
//            statement.setString(2, guestTeam);
//            statement.setString(3, gameDate);
//            retrievedGame = statement.executeQuery();
//
//            if(retrievedGame.next()){
//                gameId = retrievedGame.getInt(1);
//            }
//        }
//        catch (SQLException e){
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (retrievedGame != null) {
//                    retrievedGame.close();
//                }
//                connection.close();
//            } catch (SQLException e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        return gameId;
//    }
//    */

    /**
     * Retrieves one or more games that fit the given conditions in the database
     * @param conditions the wanted values of the fields in the table
     * @return the matching games
     */
    @Override
    public HashMap<String, Game> conditionedSelect(String[] conditions) {
        String query = "select * from [Game]";
        if(conditions != null && conditions.length > 0){
            // no need
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedGame = null;
        HashMap<String, Game> games = new HashMap<>();

        try{
            statement = connection.prepareStatement(query);
            retrievedGame = statement.executeQuery();

            while (retrievedGame.next()){
                int gameId = retrievedGame.getInt(1);
                String hostTeamName = retrievedGame.getString(2);
                String guestTeamName = retrievedGame.getString(3);
                String fieldName = retrievedGame.getString(4);
                LocalDateTime gameDate = retrievedGame.getTimestamp(5).toLocalDateTime();
                int hostScore = retrievedGame.getInt(6);
                int guestScore = retrievedGame.getInt(7);
                String leagueName = retrievedGame.getString(8);
                int season = retrievedGame.getInt(9);

                Game game = new Game(gameId, hostTeamName, guestTeamName, fieldName, gameDate, hostScore, guestScore, leagueName, season);
                games.put(String.valueOf(gameId), game);
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
                if (retrievedGame != null) {
                    retrievedGame.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return games;

    }

}
