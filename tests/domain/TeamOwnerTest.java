package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class TeamOwnerTest {

    private Field fieldHapoel;
    private Field fieldMacabi;
    private Field fieldView;
    private Field fieldTam;
    private Team teamHapoel;
    private Team teamViewer;
    private Team teamTam;
    private Team teamMacabi;
    private TeamOwner teamOwnerHapoel;
    private TeamOwner teamOwnerMacabi;
    private TeamOwner teamOwnerView;
    private TeamOwner teamOwnerTam;
    private TeamManager teamManagerHapoel;
    private TeamManager teamManagerMacabi;
    private TeamManager teamManagerView;
    private TeamManager teamManagerTam;
    private HashSet<TeamManager> managerAppointments;
    private HashSet<TeamOwner> ownerAppointments;




    @Before
    public void setUp() throws Exception {
        fieldHapoel = new Field("Tedi",10000.001);
        fieldMacabi = new Field("blomfild",100);
        fieldView = new Field("fieldView",102310);
        fieldTam = new Field("fieldTam",1054560);
        teamHapoel = new Team("Hapoel",fieldHapoel);
        teamMacabi = new Team("Macaci",fieldMacabi);
        teamViewer = new Team("teamView",fieldView);
        teamTam = new Team("teamTam",fieldTam);
        teamOwnerHapoel = new TeamOwner("aboxis","h@gmail.com", teamHapoel, new HashSet<>());
        teamOwnerMacabi = new TeamOwner("dego","m@gmail.com", teamMacabi, new HashSet<>());
        teamOwnerView = new TeamOwner("acker","v@gmail.com", teamViewer, new HashSet<>());
        teamOwnerTam = new TeamOwner("shoko","t@gmail.com", teamTam, new HashSet<>());
        teamManagerHapoel = new TeamManager("Maboxis","Mh@gmail.com");
        teamManagerMacabi = new TeamManager("Mdego","Mm@gmail.com");
        teamManagerView = new TeamManager("Macker","Mvh@gmail.com");
        teamManagerTam = new TeamManager("Mshoko","Mt@gmail.com");
    }

    @After
    public void tearDown() throws Exception {
          fieldHapoel = null;
          fieldMacabi= null;
          fieldView= null;
          fieldTam= null;
          teamHapoel= null;
          teamViewer= null;
          teamTam= null;
          teamMacabi= null;
          teamOwnerHapoel= null;
          teamOwnerMacabi= null;
          teamOwnerView= null;
          teamOwnerTam= null;
          teamManagerHapoel = null;
          teamManagerMacabi = null;
          teamManagerView = null;
          teamManagerTam = null;
          managerAppointments = null;
          ownerAppointments = null;

    }

    @Test
    public void setTeam() {
        assertTrue(teamOwnerHapoel.getTeam() == teamHapoel);
        teamOwnerHapoel.setTeam(teamMacabi);
        assertTrue(teamOwnerHapoel.getTeam() == teamMacabi);

        teamOwnerHapoel.setTeam(null);
        assertNull(teamOwnerHapoel.getTeam());
    }

    @Test
    public void getTeam() {
        assertEquals(teamOwnerHapoel.getTeam().getTeamName(),"Hapoel");
        assertEquals(teamOwnerHapoel.getTeam().getStadium().getFieldName(),"Tedi");
        assertTrue(teamOwnerHapoel.getTeam() == teamHapoel);
    }

    @Test
    public void getManagerAppointments() {
        assertEquals(0,teamOwnerHapoel.getManagerAppointments().size());
        teamOwnerHapoel.addToManagerAppointments(teamManagerHapoel);
        assertEquals(1,teamOwnerHapoel.getManagerAppointments().size());
        assertTrue(teamOwnerHapoel.getManagerAppointments().contains(teamManagerHapoel));

    }

    @Test
    public void getOwnerAppointments() {
        assertEquals(0,teamOwnerHapoel.getOwnerAppointments().size());
        teamOwnerHapoel.addToOwnerAppointments(teamOwnerHapoel);
        assertEquals(1,teamOwnerHapoel.getOwnerAppointments().size());
        assertTrue(teamOwnerHapoel.getOwnerAppointments().contains(teamOwnerHapoel));
    }



    @Test
    public void addToOwnerAppointments() {
        assertEquals(0,teamOwnerHapoel.getOwnerAppointments().size());
        teamOwnerHapoel.addToOwnerAppointments(teamOwnerHapoel);
        assertEquals(1,teamOwnerHapoel.getOwnerAppointments().size());
        assertTrue(teamOwnerHapoel.getOwnerAppointments().contains(teamOwnerHapoel));
    }

    @Test
    public void addToManagerAppointments() {
        assertEquals(0,teamOwnerHapoel.getManagerAppointments().size());
        teamOwnerHapoel.addToManagerAppointments(teamManagerHapoel);
        assertEquals(1,teamOwnerHapoel.getManagerAppointments().size());
        assertTrue(teamOwnerHapoel.getManagerAppointments().contains(teamManagerHapoel));
    }

    @Test
    public void removeFromOwnerAppointments() {
        teamOwnerHapoel.addToOwnerAppointments(teamOwnerMacabi);
        assertTrue(teamOwnerHapoel.getOwnerAppointments().contains(teamOwnerMacabi));
        teamOwnerMacabi.addToOwnerAppointments(teamOwnerView);
        assertTrue(teamOwnerMacabi.getOwnerAppointments().contains(teamOwnerView));
        teamOwnerHapoel.removeFromOwnerAppointments(teamOwnerMacabi);
        assertFalse(teamOwnerHapoel.getOwnerAppointments().contains(teamOwnerMacabi));


    }

    @Test
    public void removeFromManagerAppointments() {
        teamOwnerHapoel.addToManagerAppointments(teamManagerMacabi);
        assertTrue(teamOwnerHapoel.getManagerAppointments().contains(teamManagerMacabi));
        teamOwnerMacabi.addToManagerAppointments(teamManagerView);
        assertTrue(teamOwnerMacabi.getManagerAppointments().contains(teamManagerView));
        teamOwnerHapoel.removeFromManagerAppointments(teamManagerMacabi);
        assertFalse(teamOwnerHapoel.getOwnerAppointments().contains(teamManagerMacabi));

    }


}