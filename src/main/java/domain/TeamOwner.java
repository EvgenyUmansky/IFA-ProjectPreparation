package domain;

import java.util.Date;
import java.util.HashSet;

public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamAdmin> managerAppointments;



    // Constructor
    public TeamOwner(String userName, String password, String name, String mail) {
        super(userName, password, name, mail);
        managerAppointments = new HashSet<>();
    }


    public void setTeam(Team team){
        this.team = team;
    }


    public boolean addProperty(Object property){
        team.addProperty(property);
        return true;
    }

    public boolean removeProperty(Object property){
        if(property instanceof TeamAdmin){
            if(!(managerAppointments.contains((TeamAdmin)property))){
                return false;
            }
        }

        team.removeProperty(property);
        return true;
    }


    public boolean updatePlayerDetails(String userName, String name, Date birthDate, String position){
        TeamPlayer player = this.team.getPlayer(userName);
        player.updateDetails(name,birthDate,position);
        return true;
    }


    public boolean updateCoachDetails(String userName, String name, String validation, String role){
        TeamCoach coach = this.team.getCoach(userName);
        coach.updateDetails(name,validation,role);
        return true;
    }
}
