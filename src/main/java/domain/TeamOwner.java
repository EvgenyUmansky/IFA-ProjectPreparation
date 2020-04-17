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

    // Add property use case

    public boolean addProperty(Object property){
        team.addProperty(property);
        return true;
    }


    // Remove property use case

    public boolean removeProperty(Object property){
        if(property instanceof TeamManager){
            if(!(managerAppointments.contains((TeamManager)property))){
                return false;
            }
        }

        team.removeProperty(property);
        return true;
    }


    // Update details use case

    public boolean updatePlayerDetails(String userName, String squadNumber, Date birthDate, String position){
        TeamPlayer player = this.team.getPlayer(userName);
       player.updateDetails(birthDate,position,squadNumber);
        return true;
    }

    public boolean updateCoachDetails(String userName,  String validation, String role){
        TeamCoach coach = this.team.getCoach(userName);
        coach.updateDetails(validation,role);
        return true;
    }

}
