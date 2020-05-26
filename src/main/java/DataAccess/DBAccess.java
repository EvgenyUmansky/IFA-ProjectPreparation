package DataAccess;

import java.util.HashMap;

/**
 * Represents an object that accesses the database
 */
public interface DBAccess<T> {

    /**
     * Saves an object as a record in the matching table in the database
     * @param t the object
     */
    void save(T t);

    /**
     * Updates the object's matching record in the database
     * @param t the object
     */
    void update(T t);

    /**
     * Deletes the object's matching record from the database
     * @param t the object
     */
    void delete(T t);

    /**
     * Retrieves an object that matches the given id from the database
     * @param id the id of the object
     */
    T select(String id);

    /**
     * Retrieves one or more objects that fit the given conditions in the database
     * @param conditions the wanted values of the fields in the table
     * @return the matching objects
     */
   HashMap<String,T> conditionedSelect(String[] conditions);

       /* String query = "select * from User where";
        for(int i=0; i<conditions.length; i++){
            if(i%2 == 0){
                query += " " + conditions[i];
            }
            else {
                query += " = ?";
            }
        }

    }*/
}
