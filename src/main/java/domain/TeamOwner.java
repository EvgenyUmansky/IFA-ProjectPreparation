package domain;

import java.util.Date;
import java.util.HashSet;

public class TeamOwner extends  Subscriber {

    private Team team;
    private HashSet<TeamManager> managerAppointments;



    // Constructor


    public TeamOwner(String userName, String mail, boolean isMail, Team team, HashSet<TeamManager> managerAppointments) {
        super(userName, mail, isMail);
        this.team = team;
        this.managerAppointments = managerAppointments;
    }
    public TeamOwner(String userName, String mail, boolean isMail) {
        super(userName, mail, isMail);
        this.managerAppointments = new HashSet<>();
    }

    public void setTeam(Team team){
        this.team = team;
    }


    public boolean addProperty(Object property){
        team.addProperty(property);
        return true;
    }

    public boolean removeProperty(Object property){
        if(property instanceof TeamManager){
            if(!(managerAppointments.contains((TeamManager)property))){
                return false;
            }
        }

        team.removeProperty(property);
        return true;
    }


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
