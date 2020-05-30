package DataAccess;

import domain.League;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LeagueSeasonDBAccess implements DBAccess<ArrayList<League>> {

    static Logger logger = Logger.getLogger(LeagueSeasonDBAccess.class.getName());
    private static final LeagueSeasonDBAccess instance = new LeagueSeasonDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private LeagueSeasonDBAccess() {

    }

    public static LeagueSeasonDBAccess getInstance() {
        return instance;
    }

    /**
     * Saves an object as a record in the matching table in the database
     *
     * @param leagues the object
     */
    @Override
    public void save(ArrayList<League> leagues) {

    }

    /**
     * Updates the object's matching record in the database
     *
     * @param leagues the object
     */
    @Override
    public void update(ArrayList<League> leagues) {

    }

    /**
     * Deletes the object's matching record from the database
     *
     * @param leagues the object
     */
    @Override
    public void delete(ArrayList<League> leagues) {

    }

    /**
     * Retrieves an object that matches the given id from the database
     *
     * @param id the id of the object
     */
    @Override
    public ArrayList<League> select(String id) {
        return null;
    }

    /**
     * Retrieves one or more objects that fit the given conditions in the database
     *
     * @param conditions the wanted values of the fields in the table
     * @return the matching objects
     */
    @Override
    public HashMap<String, ArrayList<League>> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedLeagues = null;
        HashMap<String, ArrayList<League>> leagues = new HashMap<>();

        if (conditions.length == 0) {
            query = "select * from [Leagues]";
        } else {
            query = "select * from [Leagues] where";

            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += ",";
                }
            }
        }
        try {
            statement = connection.prepareStatement(query);

            if (conditions.length > 0) {
                int i = 0;
                while (i < conditions.length) {
                    switch (conditions[i].toLowerCase()) {
                        case "leaguename":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        case "year":
                        case "winPoints":
                        case "drawPoints":
                        case "losePoints":
                            statement.setInt((int) (i / 2) + 1, Integer.valueOf(conditions[i + 1]));
                            break;

                        case "schedulingmethod":
                            statement.setBoolean((int) (i / 2) + 1, conditions[i + 1].equals("1"));

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedLeagues = statement.executeQuery();


            while (retrievedLeagues.next()) {
                String leagueName = retrievedLeagues.getString(1);
                int season = retrievedLeagues.getInt(2);
                boolean scheduling = retrievedLeagues.getBoolean(3);
                int winPoints = retrievedLeagues.getInt(4);
                int losePoints = retrievedLeagues.getInt(5);
                int drawPoints = retrievedLeagues.getInt(6);

                League league = new League(leagueName, season, scheduling, winPoints, drawPoints, losePoints);
                String seasonString = Integer.toString(season);
                ArrayList<League> leaguesList;

                if (leagues.containsKey(seasonString)) {
                    leaguesList = leagues.get(seasonString);
                } else {
                    leaguesList = new ArrayList<>();
                    leagues.put(seasonString, leaguesList);
                }

                leaguesList.add(league);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return leagues;
    }
}

