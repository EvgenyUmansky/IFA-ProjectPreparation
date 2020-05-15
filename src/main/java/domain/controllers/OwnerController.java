package domain.controllers;

import domain.*;

import java.util.HashSet;

public class OwnerController {
    // =================== Team Owner functions ====================
    // =============================================================


    /**
     * UC 6.1
     * Adds a player to a team
     * @param team the team
     * @param userName the player's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addPlayer(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = (TeamPlayer)(User.getUserByID(userName).getRoles().get(Role.TEAM_PLAYER));
        if(player == null){
            throw new Exception("This user is not a player");
        }
        team.addPlayer(player);
    }

    /**
     * UC 6.1
     * Adds a coach to a team
     * @param team the team
     * @param userName the coach's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addCoach(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = (TeamCoach)(User.getUserByID(userName).getRoles().get(Role.COACH));
        if(coach == null){
            throw new Exception("This user is not a coach");
        }
        team.addCoach(coach);
    }

    /**
     * UC 6.1
     * Adds a field to a team
     * @param team the team
     * @param fieldName the field's name
     * @throws Exception if the addition was unsuccessful
     */
    public void addField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.addField(Field.getFieldByName(fieldName));
    }


    /**
     * UC 6.1
     * Removes a player from the team
     * @param team the team
     * @param userName the player's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removePlayer(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = (TeamPlayer)(User.getUserByID(userName).getRoles().get(Role.TEAM_PLAYER));
        if(player == null){
            throw new Exception("This user is not a player");
        }
        team.removePlayer(player);
    }


    /**
     * UC 6.1
     * Removes a coach from the team
     * @param team the team
     * @param userName the coach's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removeCoach(Team team, String userName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = (TeamCoach)(User.getUserByID(userName).getRoles().get(Role.COACH));
        if(coach == null){
            throw new Exception("This user is not a coach");
        }
        team.removeCoach(coach);
    }

    /**
     * UC 6.1
     * Removes a field from the team
     * @param team the team
     * @param fieldName the field's name
     * @throws Exception if the removal was unsuccessful
     */
    public void removeField(Team team, String fieldName) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        team.removeField(Field.getFieldByName(fieldName));
    }


    /**
     * UC 6.2
     * Adds a new owner to a team
     * @param team the team
     * @param userNameNewTeamOwner the username of the new owner
     * @param userNameTeamOwner the username of the owner that appoints the new one
     * @throws Exception if the appointment was unsuccessful
     */
    public void addOwner(Team team, String userNameNewTeamOwner, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newOwnerUser = User.getUserByID(userNameNewTeamOwner);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newOwnerUser.getRoles().put(Role.TEAM_OWNER, new TeamOwner(userNameNewTeamOwner, newOwnerUser.getMail(), team, new HashSet<>()));
        owner.addToOwnerAppointments((TeamOwner) newOwnerUser.getRoles().get(Role.TEAM_OWNER));
        team.addOwner(ownerUser,newOwnerUser);
    }


    /**
     * UC 6.3
     * Removes an owner from the team.
     * This operation is possible only if the removed owner was appointed by the removing owner
     * @param team the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamOwner the owner that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeOwner(Team team, String userNameTeamOwner, String userNameRemovedTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedOwnerUser = User.getUserByID(userNameRemovedTeamOwner);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER), removedOwner = (TeamOwner)removedOwnerUser.getRoles().get(Role.TEAM_OWNER);
        owner.removeFromOwnerAppointments(removedOwner);
        team.removeOwner(removedOwnerUser);
        removedOwnerUser.removeRoleFromUser(Role.TEAM_OWNER);
    }


    /**
     * UC 6.4
     * Adds a team manager to the team
     * @param team the team
     * @param userNameNewTeamManager the new manager
     * @param userNameTeamOwner the owner
     * @throws Exception if the addition was unsuccessful
     */
    public void addManager(Team team, String userNameNewTeamManager, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newManagerUser = User.getUserByID(userNameNewTeamManager);
        TeamOwner owner = ((TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER));
        newManagerUser.getRoles().put(Role.TEAM_MANAGER,new TeamManager(userNameNewTeamManager, newManagerUser.getMail()));
        owner.addToManagerAppointments((TeamManager) newManagerUser.getRoles().get(Role.TEAM_MANAGER));
        team.addManager(ownerUser,newManagerUser);
    }

    /**
     * UC 6.5
     * Removes a manager from the team.
     * This operation is possible only if the removed manager was appointed by the removing owner
     * @param team the team
     * @param userNameTeamOwner the owner that removes
     * @param userNameRemovedTeamManager the manager that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeManager(Team team, String userNameRemovedTeamManager, String userNameTeamOwner) throws Exception {
        if(team.getTeamStatus() != TeamStatus.Open){
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedManagerUser = User.getUserByID(userNameRemovedTeamManager);
        TeamOwner owner = (TeamOwner)ownerUser.getRoles().get(Role.TEAM_OWNER);
        TeamManager manager = (TeamManager)removedManagerUser.getRoles().get(Role.TEAM_MANAGER);
        owner.removeFromManagerAppointments(manager);
        team.removeManager(removedManagerUser);
        removedManagerUser.removeRoleFromUser(Role.TEAM_MANAGER);
    }


    /**
     * UC 6.6, 8.1
     * Closes a team
     * @param userName the username of the user that closes the team
     * @param team the team
     */
    public void closeTeam(String userName, Team team) {
        team.closeTeam(User.getUserByID(userName));
    }

    /**
     * UC 6.6
     * Reopens a team
     * @param team the team
     */
    public void openTeam(Team team) {
        team.openTeam();
    }


    /**
     * UC 6.7
     *
     */
    public void manageFinance(){
    }
}
