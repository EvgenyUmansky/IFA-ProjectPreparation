package domain;

import java.util.Date;
import java.util.HashSet;

public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamManager> managerAppointments;
    private HashSet<TeamOwner> ownerAppointments;


    // =================== Constructors ====================

    public TeamOwner(String userName, String mail, Team team, HashSet<TeamManager> managerAppointments) {
        super(userName, mail);
        this.team = team;
        this.managerAppointments = managerAppointments;
        this.ownerAppointments = new HashSet<>();
    }

    public TeamOwner(String userName, String mail) {
        super(userName, mail);
        this.managerAppointments = new HashSet<>();
        this.ownerAppointments = new HashSet<>();
    }


    // =================== Getters and Setters ====================

    public void setTeam(Team team){
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public HashSet<TeamManager> getManagerAppointments() {
        return managerAppointments;
    }

    public HashSet<TeamOwner> getOwnerAppointments() {return ownerAppointments;}


    // =================== Functionality ====================

    //add Team Owner
    public void addToOwnerAppointments(TeamOwner owner) {
        this.ownerAppointments.add(owner);
    }

    //add Manager Owner
    public void addToManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.add(teamManager);
    }
    //remove Team Owner
    public void removeFromOwnerAppointments(TeamOwner ownerToRemove) {
        this.ownerAppointments.remove(ownerToRemove);
    }

    //remove Manager Owner
    public void removeFromManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.remove(teamManager);
    }

}