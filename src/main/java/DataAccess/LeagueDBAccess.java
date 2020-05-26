package DataAccess;

import domain.Field;
import domain.League;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LeagueDBAccess implements DBAccess<League>{

    private static final LeagueDBAccess instance = new LeagueDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private LeagueDBAccess(){

    }

    public static LeagueDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(League league) {

    }

    @Override
    public void update(League league) {

    }

    @Override
    public void delete(League league) {

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
                        query += ",";
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
                        case "winPoints":
                        case "drawPoints":
                        case "losePoints":
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
            e.printStackTrace();
        }

        return leagues;
    }
}
