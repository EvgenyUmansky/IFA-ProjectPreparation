package domain;

import java.util.Date;
import java.util.HashSet;

/**
 * This class represents a team owner user in the system
 */
public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamManager> managerAppointments;
    private HashSet<TeamOwner> ownerAppointments;


    // =================== Constructors ====================

    /**
     * Constructor
     * @param userName the username of the team owner
     * @param mail the mail of the team owner
     * @param team the team that the owner owns
     * @param managerAppointments a list of managers that this owner has appointed as managers in the team he owns
     */
    public TeamOwner(String userName, String mail, Team team, HashSet<TeamManager> managerAppointments) {
        super(userName, mail);
        this.team = team;
        this.managerAppointments = managerAppointments;
        this.ownerAppointments = new HashSet<>();
    }

    /**
     * Constructor
     * @param userName the username of the team owner
     * @param mail the mail of the team owner
     */
    public TeamOwner(String userName, String mail) {
        super(userName, mail);
        this.managerAppointments = new HashSet<>();
        this.ownerAppointments = new HashSet<>();
    }


    // =================== Getters and Setters ====================

    /**
     * Updates the team this owner owns
     * @param team the team this owner owns
     */
    public void setTeam(Team team){
        this.team = team;
    }

    /**
     * Returns the team this owner owns
     * @return the team this owner owns
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Returns the list of managers this owner has appointed as managers in the team he owns
     * @return the list of managers this owner has appointed as managers in the team he owns
     */
    public HashSet<TeamManager> getManagerAppointments() {
        return managerAppointments;
    }

    /**
     * Returns the list of owners this owner has appointed as owners in the team he owns
     * @return the list of owners this owner has appointed as owners in the team he owns
     */
    public HashSet<TeamOwner> getOwnerAppointments() {return ownerAppointments;}


    // =================== Functionality ====================


    /**
     * UC 6.2
     * Adds a team owner to the list of owners this owner has appointed as owners in the team
     * @param owner a new team owner appointed by this owner
     */
    public void addToOwnerAppointments(TeamOwner owner) {
        this.ownerAppointments.add(owner);
    }


    /**
     * UC 6.4
     * Adds a team manager to the list of managers this owner has appointed as managers in the team
     * @param teamManager  a new team manager appointed by this owner
     */
    public void addToManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.add(teamManager);
    }


    /**
     * UC 6.3
     * Removes a team owner from the list of owners this owner has appointed as owners in the team
     * @param ownerToRemove the removed team owner
     */
    public void removeFromOwnerAppointments(TeamOwner ownerToRemove) {
        this.ownerAppointments.remove(ownerToRemove);
    }


    /**
     * UC 6.5
     * Removes a team manager from the list of managers this owner has appointed as managers in the team
     * @param teamManager the removed manager
     */
    public void removeFromManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.remove(teamManager);
    }

}