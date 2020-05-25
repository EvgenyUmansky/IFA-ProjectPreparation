package DataAccess;

import domain.Field;
import domain.TeamPlayer;

import java.sql.*;
import java.util.HashMap;

public class FieldDBAccess implements DBAccess<Field>{

    private static final FieldDBAccess instance = new FieldDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private FieldDBAccess() {

    }

    public static FieldDBAccess getInstance() {
        return instance;
    }

    @Override
    public void save(Field field) {

    }

    @Override
    public void update(Field field) {

    }

    @Override
    public void delete(Field field) {

    }

    @Override
    public Field select(String fieldName) {
        return null;
    }

    @Override
    public HashMap<String, Field> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedFields = null;
        HashMap<String, Field> fields = new HashMap<>();


        if(conditions.length == 0){
            query = "select * from [Field]";
        }
        else {
            query = "select * from [Field] where";

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
                        case "fieldName":
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                            break;

                        case "propertycost":
                            statement.setDouble((int) (i / 2) + 1, Double.valueOf(conditions[i + 1]));
                            break;

                        default:
                            break;
                    }
                    i += 2;
                }
            }

            retrievedFields = statement.executeQuery();


            while(retrievedFields.next()){
                fields.put(retrievedFields.getString(1),new Field(retrievedFields.getString(1),retrievedFields.getDouble(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fields;
    }
}
