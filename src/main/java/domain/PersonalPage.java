package domain;

import java.util.HashSet;
import java.util.Set;

public class PersonalPage {
    private Alert alert;

    /////////// Constructor ///////////
    public PersonalPage() {
        this.alert = new Alert();
    }

/////////// Functionality ///////////

    // UC 3.2
    public void addSubscriber(Subscriber user, boolean isMail) {
        if(isMail) {
            this.alert.addToMailSet(user);
        }
        else{
            this.alert.addToSystemSet(user);
        }
    }


}
