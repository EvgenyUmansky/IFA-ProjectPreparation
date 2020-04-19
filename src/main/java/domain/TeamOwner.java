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
    }

    public TeamOwner(String userName, String mail) {
        super(userName, mail);
        this.managerAppointments = new HashSet<>();
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


    // =================== Functionality ====================

    //add Team Owner
    public void addToOwnerAppointments(TeamOwner Owner) {
        this.ownerAppointments.add(Owner);
    }

    //add Manager Owner
    public void addToManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.add(teamManager);
    }
    //remove Team Owner
    public void removeFromOwnerAppointments(TeamOwner OwnerToRemove) {
        this.ownerAppointments.remove(OwnerToRemove);
    }

    //remove Manager Owner
    public void removeFromManagerAppointments(TeamManager teamManager) {
        this.managerAppointments.remove(teamManager);
    }



}
