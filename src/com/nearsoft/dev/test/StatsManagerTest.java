package com.nearsoft.dev.test;

import com.nearsoft.dev.StatsManager;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;


public class StatsManagerTest {

    private StatsManager _statsManager;

    @Before
    public void setUp() {
        _statsManager = new StatsManager();
        _statsManager.loadStatsFromFile("england_premier_league_2000.xml");
    }

    @Test
    public void getTeamWithMostWonMatches() {
        String expectedTeamName = "Manchester Utd";
        assertEquals(expectedTeamName, _statsManager.getTeamNameWithMostWon());
    }

    @Test
    public void getTeamWithMostLostMatches() {
        String expectedTeamName = "Bradford";
        assertEquals(expectedTeamName, _statsManager.getTeamNameWithMostLost());
    }

    @Test
    public void statsToString() {
        String expectedSummary = loadTextFile("summary_england_premier_league_2000.txt");
        assertEquals(expectedSummary, _statsManager.toString());
    }

    private String loadTextFile(String filename) {
        String workingDir = System.getProperty("user.dir");
        String path = workingDir + "\\src\\com\\nearsoft\\dev\\test\\" + filename;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }


}
