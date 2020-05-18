package domain;

/**
 *
 */
public interface DBAccess {
    // TODO: Each class need to implement this interface when we connect it to DB.

    /**
     *
     * @param o
     */
    void save(Object o);

    /**
     *
     * @param o
     */
    void update(Object o);

    /**
     *
     * @param o
     */
    void delete(Object o);

    /**
     *
     * @param o
     */
    void select(Object o);
}
