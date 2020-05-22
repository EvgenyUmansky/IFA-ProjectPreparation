package DataAccess;

import java.util.HashMap;

/**
 *
 */
public interface DBAccess<T> {

    /**
     *
     * @param
     */
    void save(T t);

    /**
     *
     * @param
     */
    void update(T t);

    /**
     *
     * @param
     */
    void delete(T t);

    /**
     *
     * @param
     */
    T select(String id);

    /**
     *
     * @param conditions
     * @return
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
