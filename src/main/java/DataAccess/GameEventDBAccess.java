package DataAccess;

import domain.GameEvent;
import org.apache.log4j.Logger;

import javax.validation.constraints.Null;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class GameEventDBAccess implements DBAccess<GameEvent>{

    static Logger logger = Logger.getLogger(AssAgentDBAccess.class.getName());
    private static final GameEventDBAccess instance = new GameEventDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private GameEventDBAccess(){ }

    public static GameEventDBAccess getInstance() {
        return instance;
    }

    /**
     * @param gameEvent
     */
    @Override
    public void save(GameEvent gameEvent) {
        if(gameEvent == null){
            logger.error("gameEvent == null in GameEventDBAccess save(GameEvent gameEvent)");
            System.out.println("gameEvent == null in GameEventDBAccess save(GameEvent gameEvent)");
            return;
        }
        // String query = "insert into [GameEvent] values (?, ?, ?, ?, ?)";
        String query = "insert into [GameEvent] values (?, ?, ?, ?, ?)";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            //statement.setInt(1,gameEvent.getId());
            statement.setInt(1,gameEvent.getGameID());
            statement.setTimestamp(2, Timestamp.valueOf(gameEvent.getDateTime()));
            statement.setInt(3, gameEvent.getGameMinutes());
            statement.setString(4,gameEvent.getEventName().toString());
            statement.setString(5,gameEvent.getDescription());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
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
     * @param gameEvent
     */
    @Override
    public void update(GameEvent gameEvent) {

    }

    /**
     * @param gameEvent
     */
    @Override
    public void delete(GameEvent gameEvent) {
        if(gameEvent == null){
            throw new NullPointerException();
        }

        String query = "delete from [GameEvent] where EventID = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,gameEvent.getId());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            e.printStackTrace();        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                e3.printStackTrace();
            }
        }
    }

    /**
     * @param id
     */
    @Override
    public GameEvent select(String id) {
        return null;
    }

//    public int selectGameEventId(String gameId, String eventDate, String eventName) {
//        String query = "select * from [GameEvent] where GameId = ? and EventDate = ? and EventName = ?";
//        Connection connection = DBConnector.getConnection();
//        PreparedStatement statement = null;
//        ResultSet retrievedGame = null;
//        int gameEventId = 0;
//
//        try{
//            statement = connection.prepareStatement(query);
//            statement.setString(1, gameId);
//            statement.setString(2, eventDate);
//            statement.setString(3, eventName);
//            retrievedGame = statement.executeQuery();
//
//            if(retrievedGame.next()){
//                gameEventId = retrievedGame.getInt(1);
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
//        return gameEventId;
//    }


    /**
     * @param conditions
     * @return
     */
    @Override
    public HashMap<String, GameEvent> conditionedSelect(String[] conditions) {
        String query = "select * from GameEvent "; //where GameId = ?";

        if(conditions == null || conditions.length == 0){
            // no need
        }

        query += "where ";

        for(int i = 0; i < conditions.length; i++){
            if(i % 2 == 0){
                query += " " + conditions[i];
            }
            else {
                query += " = ?";
            }
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedEvent = null;

        HashMap<String, GameEvent> events = new HashMap<>();
        try{
            statement = connection.prepareStatement(query);

            int index = 1;
            for(int i = 0; i < conditions.length; i++){
                if(i % 2 != 0){
                    statement.setString(index, conditions[i]);
                    index++;
                }
            }

            retrievedEvent = statement.executeQuery();

            while (retrievedEvent.next()){
                int eventId = retrievedEvent.getInt(1);
                int gameIdInt = retrievedEvent.getInt(2);
                LocalDateTime eventDate = retrievedEvent.getTimestamp(3).toLocalDateTime();
                int minutes = retrievedEvent.getInt(4);
                String eventName = retrievedEvent.getString(5);
                String description = retrievedEvent.getString(6);

                events.put(String.valueOf(eventId), new GameEvent(eventId, gameIdInt, eventDate, minutes, eventName, description));
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
                if (retrievedEvent != null) {
                    retrievedEvent.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return events;
    }


}
