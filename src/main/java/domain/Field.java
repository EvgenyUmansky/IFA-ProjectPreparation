package domain;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a football field
 */
public class Field {

    private String fieldName;
    private double propertyCost;


/////////// Constructors ///////////

    /**
     * Constructor
     * @param fieldName the name of the field
     * @param propertyCost the field's cost
     */
    public Field(String fieldName, double propertyCost) {
        this.fieldName = fieldName;
        this.propertyCost = propertyCost;
    }


/////////// Getters and Setters ///////////

    /**
     * Returns the name of the field
     * @return the name of the field
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * Updates the name of the field to the given name
     * @param fieldName the given name
     * @return true if the update was successful, false otherwise
     */
    public boolean setFieldName(String fieldName) {
        if(fieldName.isEmpty()){
            System.out.println("The name of a field cannot be empty");
            return false;
        }

        this.fieldName = fieldName;
        return true;
    }

    /**
     * Returns the cost of the field
     * @return the cost of the field
     */
    public double getPropertyCost() {
        return this.propertyCost;
    }

    /**
     * Updates the cost of the field to the given amount
     * @param propertyCost the given amount
     * @return true if the update was successful, false otherwise
     */
    public boolean setPropertyCost(double propertyCost) {
        if (propertyCost < 0){
            System.out.println("The cost of a field cannot be negative");
            return false;
        }

        this.propertyCost = propertyCost;
        return true;
    }


    /////////// DB access functions ///////////

    /**
     * Retrieves the field object from the DB according to the given name
     * @param fieldName the given name of the field
     * @return the matching field object
     */
    public static Field getFieldByName(String fieldName){
        //TODO - change this to a query for DB
        return new Field(fieldName,0);
    }

}
