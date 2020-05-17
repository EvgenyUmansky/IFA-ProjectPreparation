package DataAccess;

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
    void select(String id);
}
