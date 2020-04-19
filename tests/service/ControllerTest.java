package service;

import domain.CoachPage;
import domain.PersonalPage;
import domain.TeamCoach;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller = new Controller();
    @Test
    void connectToExternalSystems() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void register() {
    }

    
    // =================== User functions ===========================
    // ==============================================================

    @Test
    void getPagesByUsername() {
    }


    // =================== Personal Pages Tests =================
    // ==============================================================

    //T4.2, T5.2
    @Test
    void updateInfo() {
    }

    // =================== Team Player functions ====================
    // ==============================================================

    //T4.1
    @Test
    void updatePlayerDetails() {
    }

    // ======================= Coach Tests ============================
    // ====================================================================

    //T5.1
    @Test
    void updateCoachDetails() {
    }

    // ========================= Guest Tests ============================
    // ====================================================================
    //T2.4A
    @Test
    void getTeamDetails() {
        assertEquals("team", controller.getTeamDetails("team").getTeamName());
    }

    //T2.4B
    @Test
    void getPlayersDetails() {
        assertEquals("userName", controller.getPlayersDetails("userName").getUserName());

    }

    //T2.4C
    @Test
    void getCoachDetails() {
        assertEquals("userName", controller.getCoachDetails("userName").getUserName());

    }

    //T2.4D
    @Test
    void getLeagueDetails() {
        assertEquals("leagueName", controller.getLeagueDetails("leagueName").getLeagueName());
    }

    //T2.4E
    @Test
    void getSeasonDetails() {
        //TODO: change test accordingly
        assertEquals(0 , controller.getSeasonDetails(1998).size());
    }


    //T2.5
    @Test
    void searchByKeyWord() {
        //TODO: next iteration
    }


    // ========================= Fan Tests ============================
    // ====================================================================

    @Test
    void addFanSubscriptionToPersonalPage() {
    }

    @Test
    void addFanSubscriptionToGame() {
    }

    @Test
    void sendComplaintToSysAdmin() {
    }

    @Test
    void getFanHistory() {
    }

    @Test
    void getFanProfileDetails() {
    }

    @Test
    void setFanProfileDetails() {
    }

    @Test
    void getRefereeDetails() {
    }

    @Test
    void setRefereeProfileDetails() {
    }

    @Test
    void getRefereeGames() {
    }

    @Test
    void addGameEventToGame() {
    }

    @Test
    void changeGameEvent() {
    }

    @Test
    void setLeague() {
    }

    @Test
    void updateSeasonForLeague() {
    }

    @Test
    void addNewReferee() {
    }

    @Test
    void removeReferee() {
    }

    @Test
    void addRefereeToLeaguePerSeason() {
    }

    @Test
    void setRankingMethod() {
    }

    @Test
    void setSchedulingMethod() {
    }

    @Test
    void sceduleGamesInLeagues() {
    }

    @Test
    void setRulesForBudgetControl() {
    }

    @Test
    void setTeamBudget() {
    }

    @Test
    void addField() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void addCoach() {
    }

    @Test
    void addManager() {
    }

    @Test
    void removeField() {
    }

    @Test
    void removePlayer() {
    }

    @Test
    void removeCoach() {
    }

    @Test
    void removeManager() {
    }

    @Test
    void testUpdatePlayerDetails() {
    }

    @Test
    void testUpdateCoachDetails() {
    }

    @Test
    void updateManagerDetails() {
    }

    @Test
    void setPermissionsToManager() {
    }

    @Test
    void closeTeam() {
    }

    @Test
    void removeUserFromSystem() {
    }

    @Test
    void showComplain() {
    }

    @Test
    void commentToComplaint() {
    }

    @Test
    void showLogDocument() {
    }

    @Test
    void startModelRecommendationSystem() {
    }

    @Test
    void getTeamByName() {
    }

    @Test
    void getUserRoles() {
    }

    @Test
    void main() {
    }

    @Test
    void showLoginPanel() {
    }

    @Test
    void runSystem() {
    }

    @Test
    void init() {
    }
}