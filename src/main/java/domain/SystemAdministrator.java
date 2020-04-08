package domain;

public class SystemAdministrator extends Subscriber {
    Alert alert;

/////////// Constructor ///////////
    public SystemAdministrator(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
    }
}
