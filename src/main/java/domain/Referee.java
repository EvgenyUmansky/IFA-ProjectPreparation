package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * This class represents a referee user in the system
 */
public class Referee extends Subscriber {

    private int qualification; // From 1 to 5 (5 is the best league....)
    private RefereeType refereeType;
    private boolean acceptedRequest;

    //====================== Constructor ======================//

    /**
     * Constructor
     * @param userName the referee's username
     * @param mail the referee's mail
     */
    public Referee(String userName, String mail) {
        super(userName, mail);
        this.qualification = 0;
        this.refereeType = null;
        this.acceptedRequest = false;
    }

    //====================== Functionality ======================//

    /**
     * Indicates whether the invitation was accepted
     * @return true if the invitation was accepted, false otherwise
     */
    public boolean isAcceptedRequest() {
        return acceptedRequest;
    }

    /**
     * Indicates whether the invitation was accepted.
     * True is sent if it was, false if not.
     * @param acceptedRequest true is sent if it was, false if not
     */
    public void setAcceptedRequest(boolean acceptedRequest) {
        this.acceptedRequest = acceptedRequest;
    }


    /**
     * UC 10.1
     * Returns a String with the referee's details
     * @return a String with the referee's details
     */
    public String getRefereeDetails() {
        return  "Qualification: " + getQualification() + "\n" +
                "Type: " + getRefereeType();
    }

    /**
     * UC 10.1
     * Updates the referee's details
     * @param newMail the new mail
     * @param qualification the updated qualification
     * @param refereeType the updated type
     */
    public void setRefereeDetails(String newMail, int qualification, RefereeType refereeType) {
        setMail(newMail);
        if (refereeType == null) {
            System.out.println("RefereeType is empty");
        }
        else {
            setRefereeType(refereeType);
        }

        if (qualification > 0 && qualification < 6) {
            setQualification(qualification);
        } else {
            System.out.println("The qualification is not valid");
        }
    }

    //====================== Getters and Setters ======================//

    /**
     * Returns the referee's qualification
     * @return the referee's qualification
     */
    public int getQualification() {
        return qualification;
    }

    /**
     * Updates the referee's qualification
     * @param qualification the updated qualification
     */
    public void setQualification(int qualification) {
        if (qualification < 1 || qualification > 5) {
            System.out.println("Qualification must be between 1 to 5, the qualification is not changed");
            return;
        }
        this.qualification = qualification;
    }

    /**
     * Returns the referee's type
     * @return the referee's type
     */
    public RefereeType getRefereeType() {
        return refereeType;
    }

    /**
     * Updates the referee's type
     * @param refereeType the updated referee's type
     */
    public void setRefereeType(RefereeType refereeType) {
        if (refereeType == null) {
            System.out.println("RefereeType is empty");
            return;
        }

        this.refereeType = refereeType;
    }
}
