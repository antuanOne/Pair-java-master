package com.nearsoft.dev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Usuario on 08/07/2016.
 */
public class PlayerRepository {
    Connection conn;
    public PlayerRepository(Connection conn){
        this.conn = conn;
    }

    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        try {
            PreparedStatement prStmt = conn.prepareStatement("Select user_name,last_name,first_name , id ,name " +
                                                            "from player p left join team t on t.id = p.team");
            ResultSet rSet = prStmt.executeQuery();
            playerList = fillPlayerList(rSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Player.sortByUserNameDesc(playerList);
        return playerList;
    }

    public List<Player> getPlayersByTeamId(int idTeam) {
        List<Player> playerList;
        try {
            PreparedStatement prStmt = conn.prepareStatement("Select user_name,last_name,first_name , id ,name " +
                    "from player p left join team t on t.id = p.team where t.id=?");
            prStmt.setInt(1,idTeam);
            ResultSet rSet = prStmt.executeQuery();

            playerList = fillPlayerList(rSet);
            Player.sortByUserNameDesc(playerList);
            return playerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<Player> fillPlayerList(ResultSet rSet) throws SQLException {
        List<Player> playerList = new ArrayList<Player>();
        while (rSet.next()) {
            Player playerTmp = new Player();
            playerTmp.setUserName(rSet.getString("user_name").trim());
            playerTmp.setLastName(rSet.getString("last_name").trim());
            playerTmp.setFirstName(rSet.getString("first_name").trim());
            Team team = new Team();
            team.setId(rSet.getInt("id"));
            if(rSet.getString("name") != null){
                team.setName(rSet.getString("name").trim());
            }


            playerTmp.setTeam(team);

            playerList.add(playerTmp);

        }
        return playerList;
    }
}
