package com.nearsoft.dev;

import java.util.Date;

/**
 * Created by Usuario on 08/07/2016.
 */
public class TeamStaticsDataTransfer {
    //<matchResult><date>2001-05-19</date><local>Charlton</local><visitor>Liverpool</visitor><score>0-4</score></matchResult>
    private Date gameDate;
    private String localTeam;
    private String visitorTeam;
    private int localScore;
    private int visitorScore;

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getLocalScore() {
        return localScore;
    }

    public void setLocalScore(int localScore) {
        this.localScore = localScore;
    }

    public int getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(int visitorScore) {
        this.visitorScore = visitorScore;
    }
}
