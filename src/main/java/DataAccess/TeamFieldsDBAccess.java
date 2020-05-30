package DataAccess;

import domain.Game;
import domain.Team;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class TeamFieldsDBAccess implements DBAccess<Pair<String,String>>{
    static Logger logger = Logger.getLogger(TeamFieldsDBAccess.class.getName());
    private static final TeamFieldsDBAccess instance = new TeamFieldsDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private TeamFieldsDBAccess() {

    }

    public static TeamFieldsDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Pair<String, String> teamFieldPair) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [TeamFields] values (?, ?)";

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, teamFieldPair.getKey());
            statement.setString(2, teamFieldPair.getValue());

            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e){
            logger.error(e.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }

    }

    @Override
    public void update(Pair<String, String> stringStringPair) {

    }

    @Override
    public void delete(Pair<String, String> stringStringPair) {

    }

    @Override
    public Pair<String, String> select(String id) {
        return null;
    }

    @Override
    public HashMap<String, Pair<String, String>> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedTeams = null;
        HashMap<String, Pair<String, String>> teamFields = new HashMap<>();

        if(conditions.length == 0){
            query = "select * from [TeamFields]";
        }
        else {
            query = "select * from [TeamFields] where";

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
                        case "teamname":
                        case "fieldname":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedTeams = statement.executeQuery();


            while(retrievedTeams.next()){
                String teamName = retrievedTeams.getString(1);
                String fieldName = retrievedTeams.getString(2);

                teamFields.put(teamName+fieldName,new Pair<>(teamName,fieldName));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return teamFields;
    }
}
