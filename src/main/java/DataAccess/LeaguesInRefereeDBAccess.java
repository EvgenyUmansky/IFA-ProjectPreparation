package DataAccess;

import domain.League;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LeaguesInRefereeDBAccess implements DBAccess<Pair<String, ArrayList<League>>> {
    static Logger logger = Logger.getLogger(RefereeGamesDBAccess.class.getName());

    private static final LeaguesInRefereeDBAccess instance = new LeaguesInRefereeDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private LeaguesInRefereeDBAccess() {

    }

    public static LeaguesInRefereeDBAccess getInstance() {
        return instance;
    }


    @Override
    public void save(Pair<String, ArrayList<League>> stringArrayListPair) {

    }

    @Override
    public void update(Pair<String, ArrayList<League>> stringArrayListPair) {

    }

    @Override
    public void delete(Pair<String, ArrayList<League>> stringArrayListPair) {

    }

    @Override
    public Pair<String, ArrayList<League>> select(String id) {
        return null;
    }


    @Override
    public HashMap<String, Pair<String, ArrayList<League>>> conditionedSelect(String[] conditions) {
        String query = "select * " +
                "from [RefereesInLeague] ";

        HashMap<String, Pair<String, ArrayList<League>>> leaguesByReferee = new HashMap<>();
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
            ArrayList<League> leagues = new ArrayList<>();
            while(retrievedGame.next()){
                String userName = retrievedGame.getString(1);
                leagueName =  retrievedGame.getString(2);
                int year = retrievedGame.getInt(3);
                leagues.add(new League(leagueName, year));

            }

            leaguesByReferee.put(leagueName, new Pair<>(leagueName, leagues));
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
        return leaguesByReferee;
    }
}
