package DataAccess;

import domain.League;
import domain.OneGameSchedulingMethod;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LeagueDBAccess implements DBAccess<League>{
    static Logger logger = Logger.getLogger(LeagueDBAccess.class.getName());
    private static final LeagueDBAccess instance = new LeagueDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private LeagueDBAccess(){ }

    public static LeagueDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(League league) {
        if(league == null){
            logger.error("league object is null");
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [Leagues] values (?, ?, ?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1,league.getLeagueName());
            statement.setInt(2,league.getSeason());
            statement.setBoolean(3, !(league.getSchedulingMethod() instanceof OneGameSchedulingMethod));
            statement.setInt(4,league.getRankingMethod().getWinPoints());
            statement.setInt(5,league.getRankingMethod().getLosePoints());
            statement.setInt(6,league.getRankingMethod().getDrawPoints());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                logger.error(e3.getMessage());
            }
        }
    }

    @Override
    public void update(League league) {
    }

    @Override
    public void delete(League league) {
        if(league == null){
            logger.error("Couldn't execute 'delete(League league)' in LeagueDBAccess: the league is null");
            return;
        }

        String query = "delete from [Leagues] where leagueName = ? and year = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,league.getLeagueName());
            statement.setInt(2,league.getSeason());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            }
            catch (SQLException e3) {
                logger.error("Couldn't execute 'delete(League league)' in LeagueDBAccess for " + league.getLeagueName() + " and season " + league.getSeason());
            }
        }
    }

    @Override
    public League select(String id) {
        return null;
    }

    @Override
    public HashMap<String, League> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedLeagues = null;
        HashMap<String, League> leagues = new HashMap<>();

        if(conditions.length == 0){
            query = "select * from [Leagues]";
        }
        else {
            query = "select * from [Leagues] where";

            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += " and ";
                }
            }
        }
        try {
            statement = connection.prepareStatement(query);

            if(conditions.length > 0) {
                int i = 0;
                while (i < conditions.length) {
                    switch (conditions[i].toLowerCase()) {
                        case "leaguename":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        case "year":
                        case "winpoints":
                        case "drawpoints":
                        case "losepoints":
                            statement.setInt((int) (i / 2) + 1, Integer.valueOf(conditions[i + 1]));
                            break;

                        case "schedulingmethod":
                            statement.setBoolean((int) (i / 2) + 1,conditions[i + 1].equals("1"));

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedLeagues = statement.executeQuery();


            while(retrievedLeagues.next()){
                String leagueName = retrievedLeagues.getString(1);
                int season = retrievedLeagues.getInt(2);
                boolean scheduling = retrievedLeagues.getBoolean(3);
                int winPoints = retrievedLeagues.getInt(4);
                int losePoints = retrievedLeagues.getInt(5);
                int drawPoints = retrievedLeagues.getInt(6);

                leagues.put(leagueName,new League(leagueName,season,scheduling,winPoints,drawPoints,losePoints));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return leagues;
    }
}
