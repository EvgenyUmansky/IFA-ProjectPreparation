package domain;

import java.util.Date;
import java.util.HashSet;

public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamManager> managerAppointments;


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

}
