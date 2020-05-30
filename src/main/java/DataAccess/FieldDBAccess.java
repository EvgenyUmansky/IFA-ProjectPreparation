package DataAccess;

import domain.Field;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FieldDBAccess implements DBAccess<Field>{
    static Logger logger = Logger.getLogger(FieldDBAccess.class.getName());

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

//    @Override
//    public HashMap<String, Field> conditionedSelect(String[] conditions) {
//        String query;
//        Connection connection = DBConnector.getConnection();
//        PreparedStatement statement = null;
//        ResultSet retrievedFields = null;
//        HashMap<String, Field> fields = new HashMap<>();
//
//
//        if(conditions.length == 0){
//            query = "select * from [Fields]";
//        }
//        else {
//            query = "select * from [Fields] where";
//
//            for (int i = 0; i < conditions.length; i++) {
//                if (i % 2 == 0) {
//                    query += " " + conditions[i];
//                } else {
//                    query += " = ?";
//                    if (i < conditions.length - 1)
//                        query += ",";
//                }
//            }
//        }
//        try {
//            statement = connection.prepareStatement(query);
//
//            if(conditions.length > 0) {
//                int i = 0;
//                while (i < conditions.length) {
//                    switch (conditions[i].toLowerCase()) {
//                        case "fieldName":
//                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
//                            break;
//
//                        case "propertycost":
//                            statement.setDouble((int) (i / 2) + 1, Double.valueOf(conditions[i + 1]));
//                            break;
//
//                        default:
//                            break;
//                    }
//                    i += 2;
//                }
//            }
//
//            retrievedFields = statement.executeQuery();
//
//
//            while(retrievedFields.next()){
//                fields.put(retrievedFields.getString(1),new Field(retrievedFields.getString(1),retrievedFields.getDouble(2)));
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//
//        return fields;
//    }


    @Override
    public HashMap<String, Field> conditionedSelect(String[] conditions) {
        String query;
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement;
        ResultSet retrievedFields;
        HashMap<String, Field> fields = new HashMap<>();

        if(conditions.length == 0) {
            query = "select * " +
                    "from Fields " +
                    "full join TeamFields on Fields.FieldName = TeamFields.FieldName";
        }

        else {
            query = "select * " +
                    "from Fields " +
                    "full join TeamFields on Fields.FieldName = TeamFields.FieldName where ";


            for (int i = 0; i < conditions.length; i++) {
                if (i % 2 == 0) {
                    query += " " + conditions[i];
                } else {
                    if (conditions[i].equals("null")) {
                        query += " is null";
                        continue;
                    }
                    query += " = ?";
                    if (i < conditions.length - 1)
                        query += ",";
                }
            }
        }
        try {
            statement = connection.prepareStatement(query);
            int i = 0;
            while (i < conditions.length) {
                switch (conditions[i].toLowerCase()) {
                    case "fields.fieldname":
                        statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                        break;
                    case "propertycost":
                    case "teamname":
                        if (!conditions[i + 1].equals("null")) {
                            statement.setString((int) (i / 2) + 1, conditions[i + 1]);
                        }
                        break;
                }
                i += 2;
            }

            retrievedFields = statement.executeQuery();


            while (retrievedFields.next()) {
                String fieldName = retrievedFields.getString(1);
                double propertyCost = retrievedFields.getDouble(2);

                fields.put(fieldName, new Field(fieldName, propertyCost));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return fields;
    }
}
