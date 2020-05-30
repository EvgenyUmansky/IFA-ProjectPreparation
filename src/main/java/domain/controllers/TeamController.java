package domain.controllers;

import DataAccess.*;
import domain.*;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import service.pojos.TeamDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TeamController {
    static Logger logger = Logger.getLogger(TeamController.class.getName());
    DBAccess<User> uda = UserDBAccess.getInstance();
    DBAccess<Field> fda = FieldDBAccess.getInstance();
    DBAccess<Team> tda = TeamDBAccess.getInstance();
    DBAccess<Pair<String, String>> tfda = TeamFieldsDBAccess.getInstance();
    DBAccess<TeamOwner> oda = OwnerDBAccess.getInstance();
    DBAccess<TeamPlayer> pda = TeamPlayerDBAccess.getInstance();
    DBAccess<TeamManager> mda = ManagerDBAccess.getInstance();
    DBAccess<TeamCoach> cda = TeamCoachDBAccess.getInstance();
    DBAccess< Pair<String, ArrayList<String>>> urda = UserRolesDBAccess.getInstance();


    public TeamDTO createTeam(String teamOwner, String name, String stadium, String coachUserName, TeamPlayer[] players) {
        User user = uda.select(teamOwner);
        Field field = fda.select(stadium);
        user.addRoleToUser(Role.TEAM_OWNER);
        TeamOwner owner = (TeamOwner) user.getRoles().get(Role.TEAM_OWNER);
        Team newTeam = new Team(name, field, owner);

        tda.save(newTeam);
        uda.update(user);
        oda.save(owner);
        ArrayList<String> ownerRole = new ArrayList<>();
        ownerRole.add("TEAM_OWNER");
        urda.save(new Pair<>(teamOwner,ownerRole));
        tfda.save(new Pair<>(name, stadium));

        for(TeamPlayer player : players){
            player = pda.select(player.getUserName());
            newTeam.addPlayer(player);
            player.setCurrentTeam(newTeam.getTeamName());
            pda.update(player);
        }

        TeamCoach coach = cda.select(coachUserName);
        newTeam.addCoach(coach);
        coach.setCurrentTeam(newTeam.getTeamName());
        cda.update(coach);

        logger.info(name + " team was created");
        return new TeamDTO(
                newTeam.getTeamName(),
                newTeam.getStadium(),
                //newTeam.getFields().keySet().toArray(new String[0]),
                new ArrayList<>(newTeam.getFields().values()),
                //newTeam.getPlayers().keySet().toArray(new String[0]),
                new ArrayList<>(newTeam.getPlayers().values()),
                //newTeam.getCoaches().keySet().toArray(new String[0]),
                new ArrayList<>(newTeam.getCoaches().values()),
                //newTeam.getManagers().keySet().toArray(new String[0]),
                new ArrayList<>(newTeam.getManagers().values()),
                //newTeam.getOwners().keySet().toArray(new String[0]),
                new ArrayList<>(newTeam.getOwners().values()),
                newTeam.getTeamStatus().name()
        );
    }

    public ArrayList<TeamDTO> getTeams() {
        /*// TODO: get all teams from DB
        ArrayList<Team> array = new ArrayList<>();
        Team mockTeam = new Team("testTeam", new Field("testField", 1), new TeamOwner("alona123","owner@gmail.com", "Alona Barkat"));
        mockTeam.addPlayer(new TeamPlayer("noga123", "noga@gmail.com", "Noga Zohar"));
        mockTeam.addCoach(new TeamCoach("roy123", "roy@gmail.com", "Roy Judes"));
        mockTeam.addManager(new TeamManager("mohsen123", "mohsen@gmail.com", "Mohsen Abdalla"));
        array.add(mockTeam);*/

        HashMap<String, TeamOwner> allOwners = oda.conditionedSelect(new String[0]);
        HashMap<String, TeamPlayer> allPlayers = pda.conditionedSelect(new String[0]);
        HashMap<String, TeamManager> allManagers = mda.conditionedSelect(new String[0]);
        HashMap<String, TeamCoach> allCoaches = cda.conditionedSelect(new String[0]);
        HashMap<String, Field> allFields = fda.conditionedSelect(new String[0]);
        HashMap<String, Pair<String, String>> teamFields = tfda.conditionedSelect(new String[0]);
        HashMap<String, Team> allTeams = tda.conditionedSelect(new String[0]);
        HashMap<String, User> allUsers = uda.conditionedSelect(new String[0]);


        ArrayList<String> players = new ArrayList<>(allPlayers.keySet());
        for (String playerUserName : players) {
            TeamPlayer player = allPlayers.get(playerUserName);
            User playerUser = allUsers.get(player.getUserName());
            player.setName(playerUser.getName());
            player.setMail(playerUser.getMail());

            if(player.getCurrentTeam() != null) {
                Team team = allTeams.get(player.getCurrentTeam());
                team.addPlayer(player);
            }
        }

        ArrayList<String> owners = new ArrayList<>(allOwners.keySet());
        for (String ownerUserName : owners) {
            TeamOwner owner = allOwners.get(ownerUserName);
            User ownerUser = allUsers.get(owner.getUserName());
            owner.setName(ownerUser.getName());
            owner.setMail(ownerUser.getMail());

            if(owner.getTeam() != null) {
                Team team = allTeams.get(owner.getTeam());
                team.addOwner(owner);
            }
        }

        ArrayList<String> coaches = new ArrayList<>(allCoaches.keySet());
        for (String coachUserName : coaches) {
            TeamCoach coach = allCoaches.get(coachUserName);
            User coachUser = allUsers.get(coach.getUserName());
            coach.setName(coachUser.getName());
            coach.setMail(coachUser.getMail());

            if(coach.getCurrentTeam() != null) {
                Team team = allTeams.get(coach.getCurrentTeam());
                team.addCoach(coach);
            }
        }

        ArrayList<String> managers = new ArrayList<>(allManagers.keySet());
        for (String managerUserName : managers) {
            TeamManager manager = allManagers.get(managerUserName);
            User managerUser = allUsers.get(manager.getUserName());
            manager.setName(managerUser.getName());
            manager.setMail(managerUser.getMail());

            if(manager.getCurrentTeam() != null) {
                Team team = allTeams.get(manager.getCurrentTeam());
                team.addManager(manager);
            }
        }

        ArrayList<Pair<String, String>> teamFieldPairs = new ArrayList<>(teamFields.values());
        for (Pair<String, String> teamField : teamFieldPairs) {
            String teamName = teamField.getKey();
            String fieldName = teamField.getValue();

            Team team = allTeams.get(teamName);
            Field field = allFields.get(fieldName);
            team.addField(field);
            if (fieldName.equals(team.getStadium().getFieldName())) {
                team.setStadium(field);
            }
        }

        ArrayList<Team> array = new ArrayList<>(allTeams.values());
        ArrayList<TeamDTO> response = new ArrayList<>();
        for (Team team : array) {
            response.add(new TeamDTO(
                    team.getTeamName(),
                    team.getStadium(),
                    //team.getFields().keySet().toArray(new String[0]),
                    new ArrayList<>(team.getFields().values()),
                    //team.getPlayers().keySet().toArray(new String[0]),
                    new ArrayList<>(team.getPlayers().values()),
                    //team.getCoaches().keySet().toArray(new String[0]),
                    new ArrayList<>(team.getCoaches().values()),
                    //team.getManagers().keySet().toArray(new String[0]),
                    new ArrayList<>(team.getManagers().values()),
                    new ArrayList<>(team.getOwners().values()),
                    team.getTeamStatus().name()
            ));
        }
        return response;
    }


    // ========================= Guest functions ============================
    // ====================================================================

    /**
     * UC 2.4
     * Returns the team instance by the team's name
     *
     * @param teamName the team's name
     * @return the team instance by the team's name
     */
    public Team getTeamDetails(String teamName) {
        logger.info(teamName + " get details");
        return tda.select(teamName);
    }


    // =================== Team Owner functions ====================
    // =============================================================

    /**
     * UC 6.1
     * Adds a player to a team
     *
     * @param teamName the team
     * @param userName the player's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addPlayer(String teamName, String userName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = tda.select(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = pda.select(userName);
        if (player == null) {
            throw new Exception("This user is not a player");
        }

        team.addPlayer(player);
        logger.info(userName + " player was added to team " + teamName);
    }

    /**
     * UC 6.1
     * Adds a coach to a team
     *
     * @param teamName the team
     * @param userName the coach's username
     * @throws Exception if the addition was unsuccessful
     */
    public void addCoach(String teamName, String userName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = tda.select(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = cda.select(userName);
        if (coach == null) {
            throw new Exception("This user is not a coach");
        }
        team.addCoach(coach);
        logger.info(userName + " coach was added to team " + teamName);
    }

    /**
     * UC 6.1
     * Adds a field to a team
     *
     * @param teamName  the team
     * @param fieldName the field's name
     * @throws Exception if the addition was unsuccessful
     */
    public void addField(String teamName, String fieldName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = tda.select(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        team.addField(fda.select(fieldName));

        logger.info(fieldName + " field was added to team " + teamName);
    }

    /**
     * UC 6.1
     * Removes a player from the team
     *
     * @param teamName the team
     * @param userName the player's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removePlayer(String teamName, String userName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = tda.select(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        TeamPlayer player = (TeamPlayer) (User.getUserByID(userName).getRoles().get(Role.TEAM_PLAYER));
        if (player == null) {
            throw new Exception("This user is not a player");
        }
        team.removePlayer(player);

        logger.info(userName + " player was removed from team " + teamName);
    }

    /**
     * UC 6.1
     * Removes a coach from the team
     *
     * @param teamName the team
     * @param userName the coach's username
     * @throws Exception if the removal was unsuccessful
     */
    public void removeCoach(String teamName, String userName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        TeamCoach coach = (TeamCoach) (User.getUserByID(userName).getRoles().get(Role.COACH));
        if (coach == null) {
            throw new Exception("This user is not a coach");
        }
        team.removeCoach(coach);

        logger.info(userName + " coach was removed from team " + teamName);
    }

    /**
     * UC 6.1
     * Removes a field from the team
     *
     * @param teamName  the team
     * @param fieldName the field's name
     * @throws Exception if the removal was unsuccessful
     */
    public void removeField(String teamName, String fieldName) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        team.removeField(Field.getFieldByName(fieldName));

        logger.info(fieldName + " field was removed from team " + teamName);
    }

    /**
     * UC 6.2
     * Adds a new owner to a team
     *
     * @param teamName             the team
     * @param userNameNewTeamOwner the username of the new owner
     * @param userNameTeamOwner    the username of the owner that appoints the new one
     * @throws Exception if the appointment was unsuccessful
     */
    public void addOwner(String teamName, String userNameNewTeamOwner, String userNameTeamOwner) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newOwnerUser = User.getUserByID(userNameNewTeamOwner);
        TeamOwner owner = ((TeamOwner) ownerUser.getRoles().get(Role.TEAM_OWNER));
        newOwnerUser.getRoles().put(Role.TEAM_OWNER, new TeamOwner(userNameNewTeamOwner, newOwnerUser.getMail(), team.getTeamName(), new HashSet<>()));
        owner.addToOwnerAppointments((TeamOwner) newOwnerUser.getRoles().get(Role.TEAM_OWNER));
        team.addOwner(ownerUser, newOwnerUser);

        logger.info(userNameNewTeamOwner + " owner was added to team " + teamName);
    }

    /**
     * UC 6.3
     * Removes an owner from the team.
     * This operation is possible only if the removed owner was appointed by the removing owner
     *
     * @param teamName                 the team
     * @param userNameTeamOwner        the owner that removes
     * @param userNameRemovedTeamOwner the owner that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeOwner(String teamName, String userNameTeamOwner, String userNameRemovedTeamOwner) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedOwnerUser = User.getUserByID(userNameRemovedTeamOwner);
        TeamOwner owner = (TeamOwner) ownerUser.getRoles().get(Role.TEAM_OWNER), removedOwner = (TeamOwner) removedOwnerUser.getRoles().get(Role.TEAM_OWNER);
        owner.removeFromOwnerAppointments(removedOwner);
        team.removeOwner(removedOwnerUser);
        removedOwnerUser.removeRoleFromUser(Role.TEAM_OWNER);

        logger.info(userNameRemovedTeamOwner + " owner was removed from team " + teamName);
    }

    /**
     * UC 6.4
     * Adds a team manager to the team
     *
     * @param teamName               the team
     * @param userNameNewTeamManager the new manager
     * @param userNameTeamOwner      the owner
     * @throws Exception if the addition was unsuccessful
     */
    public void addManager(String teamName, String userNameNewTeamManager, String userNameTeamOwner) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), newManagerUser = User.getUserByID(userNameNewTeamManager);
        TeamOwner owner = ((TeamOwner) ownerUser.getRoles().get(Role.TEAM_OWNER));
        newManagerUser.getRoles().put(Role.TEAM_MANAGER, new TeamManager(userNameNewTeamManager, newManagerUser.getMail()));
        owner.addToManagerAppointments((TeamManager) newManagerUser.getRoles().get(Role.TEAM_MANAGER));
        team.addManager(ownerUser, newManagerUser);

        logger.info(userNameNewTeamManager + " manager was added to team " + teamName);
    }

    /**
     * UC 6.5
     * Removes a manager from the team.
     * This operation is possible only if the removed manager was appointed by the removing owner
     *
     * @param teamName                   the team
     * @param userNameTeamOwner          the owner that removes
     * @param userNameRemovedTeamManager the manager that is being removed
     * @throws Exception if the removal was unsuccessful
     */
    public void removeManager(String teamName, String userNameRemovedTeamManager, String userNameTeamOwner) throws Exception {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        if (team.getTeamStatus() != TeamStatus.Open) {
            throw new Exception("This team is currently closed");
        }
        User ownerUser = User.getUserByID(userNameTeamOwner), removedManagerUser = User.getUserByID(userNameRemovedTeamManager);
        TeamOwner owner = (TeamOwner) ownerUser.getRoles().get(Role.TEAM_OWNER);
        TeamManager manager = (TeamManager) removedManagerUser.getRoles().get(Role.TEAM_MANAGER);
        owner.removeFromManagerAppointments(manager);
        team.removeManager(removedManagerUser);
        removedManagerUser.removeRoleFromUser(Role.TEAM_MANAGER);

        logger.info(userNameRemovedTeamManager + " manager was removed from team " + teamName);
    }

    /**
     * UC 6.6, 8.1
     * Closes a team
     *
     * @param userName the username of the user that closes the team
     * @param teamName the team
     */
    public void closeTeam(String userName, String teamName) {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);

        team.closeTeam(User.getUserByID(userName));
        logger.info(userName + " closed the team " + teamName);
    }

    /**
     * UC 6.6
     * Reopens a team
     *
     * @param teamName the team
     */
    public void openTeam(String teamName) {
        // TODO: get team from DB by teamName
        Team team = Team.getTeamByName(teamName);
        team.openTeam();

        logger.info(teamName + " was opened " + teamName);
    }


    /**
     * UC 6.7
     */
    public void manageFinance() {
    }

    // =================== Team Manager functions ====================
    // ===============================================================

    /**
     * UC 7.1
     */
    public void setPermissionsToManager() {
        // UC 7.1 - set permissions to team manager
        //responsible of Team Owner!
    }

    // =================== Association Agent functions ====================
    // ====================================================================

    /**
     * UC 9.8
     */
    public void setRulesForBudgetControl() {
    }

    /**
     * UC 9.9
     */
    public void setTeamBudget() {
    }


    // =================================== General =======================================

    /**
     * Returns a team instance by its name
     *
     * @param teamName the team's name
     * @return the team instance that matches the name
     */
    public Team getTeamByName(String teamName) {
        return Team.getTeamByName(teamName);
    }


}
