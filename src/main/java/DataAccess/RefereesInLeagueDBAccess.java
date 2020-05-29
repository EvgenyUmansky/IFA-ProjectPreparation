package DataAccess;

import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RefereesInLeagueDBAccess implements DBAccess<Pair<String, ArrayList<Referee>>>{
    static Logger logger = Logger.getLogger(RefereeGamesDBAccess.class.getName());

    private static final RefereesInLeagueDBAccess instance = new RefereesInLeagueDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private RefereesInLeagueDBAccess() {

    }

    public static RefereesInLeagueDBAccess getInstance() {
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

    @Override
    public Pair<String, ArrayList<Referee>> select(String id) {
        return null;
    }

    @Override
    public HashMap<String, Pair<String, ArrayList<Referee>>> conditionedSelect(String[] conditions) {
        String query = "select * " +
                "from [RefereesInLeague] ";

        HashMap<String, Pair<String, ArrayList<Referee>>> refereesByLeagues = new HashMap<>();
        if(conditions == null || conditions.length == 0){
            // no need
            return null;
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
        ResultSet retrievedGame = null;

        try{
            statement = connection.prepareStatement(query);

            int index = 1;
            for(int i = 0; i < conditions.length; i++){
                if(i % 2 != 0){
                    statement.setString(index, conditions[i]);
                    index++;
                }
            }

            retrievedGame = statement.executeQuery();
            String leagueName = "";
            ArrayList<Referee> referees = new ArrayList<>();
            while(retrievedGame.next()){
                String userName = retrievedGame.getString(1);
                leagueName =  retrievedGame.getString(2);
                referees.add(new Referee(userName, ""));

            }

            refereesByLeagues.put(leagueName, new Pair<>(leagueName, referees));
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
        return refereesByLeagues;
    }
}
