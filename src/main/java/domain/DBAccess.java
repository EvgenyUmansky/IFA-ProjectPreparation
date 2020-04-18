package domain;

public interface DBAccess {
    // TODO: Each class need to implement this interface when we connect it to DB.
    void save(Object o);
    void update(Object o);
    void delete(Object o);
    void select(Object o);
}
