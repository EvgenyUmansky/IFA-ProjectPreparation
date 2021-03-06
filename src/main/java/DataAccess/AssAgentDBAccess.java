package DataAccess;
import domain.AssociationAgent;
import domain.controllers.GameController;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;


public class AssAgentDBAccess implements DBAccess<AssociationAgent> {
    static Logger logger = Logger.getLogger(AssAgentDBAccess.class.getName());

    private static final AssAgentDBAccess instance = new AssAgentDBAccess();
    /*  private DBConnector dbc = DBConnector.getInstance();*/

    private AssAgentDBAccess(){

    }

    public static AssAgentDBAccess getInstance(){
        return instance;
    }

    @Override
    public void save(AssociationAgent associationAgent) {
        if(associationAgent == null){
            logger.error("Couldn't execute 'save(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null");
            System.out.println("Couldn't execute 'save(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null");
            return;
        }

        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        String query = "insert into [AssociationAgent] values (?)";

        try {
            //TODO: make sure that the NullPointerException warning disappears when getConnection() is implemented
            statement = connection.prepareStatement(query);
            statement.setString(1,associationAgent.getUserName());


            statement.executeUpdate();
            connection.commit();
        }
        catch (SQLException | NullPointerException e){
            logger.error(e.getMessage());
            System.out.println("Couldn't execute 'save(AssociationAgent associationAgent)' in AssAgentDBAccess for " + associationAgent.getUserName());
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
                System.out.println("Couldn't close 'save(AssociationAgent associationAgent)' in UserDBAccess for " + associationAgent.getUserName());
            }
        }
    }


    @Override
    public void update(AssociationAgent associationAgent) {
        // There is only Prime Key in the AssociationAgent table, forbidden to change
    }

    @Override
    public void delete(AssociationAgent associationAgent) {
        if(associationAgent == null){
            logger.error("Couldn't execute 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null");
            System.out.println("Couldn't execute 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess: the associationAgent is null");
            return;
        }

        String query = "delete from [AssociationAgent] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,associationAgent.getUserName());

            statement.executeUpdate();
            connection.commit();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            System.out.println("Couldn't execute 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess for " + associationAgent.getUserName());
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
                System.out.println("Couldn't close 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess for " + associationAgent.getUserName());
            }
        }
    }

    //TODO: fix the roles assignment to the user - User's constructor automatically assigns a Fan to the roles, but we need to check which roles
    // the user really has according to the DB
    @Override
    public AssociationAgent select(String username) {
        String query = "select * from [AssociationAgent] where username = ?";
        Connection connection = DBConnector.getConnection();
        PreparedStatement statement = null;
        ResultSet retrievedUser = null;
        AssociationAgent associationAgent = null;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,username);
            retrievedUser = statement.executeQuery();

            if(retrievedUser.next()){


                associationAgent = new AssociationAgent(username, "");
            }
        }
        catch (SQLException e){
            logger.error(e.getMessage());
            assert false;
            System.out.println("Couldn't execute 'select(AssociationAgent associationAgent)' in AssAgentDBAccess for " + associationAgent.getUserName());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (retrievedUser != null) {
                    retrievedUser.close();
                }
                connection.close();
            }
            catch (SQLException e) {
                logger.error(e.getMessage());
                System.out.println("Couldn't close 'delete(AssociationAgent associationAgent)' in AssAgentDBAccess for " + associationAgent.getUserName());
            }
        }
        return associationAgent;
    }

    @Override
    public HashMap<String, AssociationAgent> conditionedSelect(String[] conditions) {
        return null;
    }
}