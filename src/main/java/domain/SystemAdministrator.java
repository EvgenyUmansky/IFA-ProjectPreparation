package domain;

public class SystemAdministrator extends Subscriber {
    Alert alert;

/////////// Constructor ///////////

    public SystemAdministrator(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
    }
}
