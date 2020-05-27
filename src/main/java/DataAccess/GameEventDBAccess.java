package DataAccess;

import domain.GameEvent;
import org.apache.log4j.Logger;

import javax.validation.constraints.Null;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        String query = "insert into [GameEvent] values (?, ?, ?, ?, ?)";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1,gameEvent.getId());
            statement.setInt(2,gameEvent.getGameID());
            statement.setTimestamp(3, Timestamp.valueOf(gameEvent.getDateTime()));
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

    /**
     * @param conditions
     * @return
     */
    @Override
    public HashMap<String, GameEvent> conditionedSelect(String[] conditions) {
        return null;
    }
}
