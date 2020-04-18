package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Referee extends Subscriber {
    private int qualification; // From 1 to 5 (5 is the best league....)
    private RefereeType refereeType;
    private boolean acceptedRequest;

    /////////// Constructor ///////////
    public Referee(String userName, String mail) {
        super(userName, mail);
        this.qualification = 0;
        this.refereeType = null;
        this.acceptedRequest = false;
    }

/////////// Functionality ///////////

    public boolean isAcceptedRequest() {
        return acceptedRequest;
    }

    public void setAcceptedRequest(boolean acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }

    // UC 10.1 - get and set referee details
    public String getRefereeDetails() {
        return "User name: " + getUserName() + "\n" +
                "Mail: " + getMail() + "\n" +
                "Qualification: " + getQualification() + "\n" +
                "Type: " + getRefereeType();
    }

    public void setRefereeDetails(String newMail, int qualification, RefereeType refereeType) {
        setMail(newMail);
        setRefereeType(refereeType);

        if (qualification > 0 && qualification < 6) {
            setQualification(qualification);
        } else {
            System.out.println("The qualification is not valid");
        }
    }

    /////////// Getters and Setters ///////////

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        if (qualification < 1 || qualification > 5) {
            System.out.println("Qualification must be between 1 to 5, the qualification is not changed");
            return;
        }
        this.qualification = qualification;
    }

    public RefereeType getRefereeType() {
        return refereeType;
    }

    public void setRefereeType(RefereeType refereeType) {
        if (refereeType == null) {
            System.out.println("RefereeType is empty");
            return;
        }

        this.refereeType = refereeType;
    }
}
