package com.nearsoft.dev.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.nearsoft.dev.Player;
import com.nearsoft.dev.PlayerRepository;
import com.nearsoft.dev.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerRepositoryTest {


    private Connection _connection;

    @Before public void setUp() throws Exception {
        _connection = getConnection();
    }

    private Connection getConnection() {
        String userName = "pair2";
        String password = "pair";
        Connection conn = null;

        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=pair";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    @Test
    public void testGetPlayers() throws Exception {
        PlayerRepository PlayerRepository = new PlayerRepository(_connection);
        List<Player> Players = PlayerRepository.getPlayers();
        assertEquals(3, Players.size());

        Player john = Players.get(0);
        assertEquals("john@nearsoft.com", john.getUserName());
        assertEquals("John", john.getFirstName());
        assertEquals("Doe", john.getLastName());
        assertEquals(100, john.getTeamId());

        Player jane = Players.get(1);
        assertEquals("jane@nearsoft.com", jane.getUserName());
        assertEquals("Jane", jane.getFirstName());
        assertEquals("Doe", jane.getLastName());
        assertEquals(100, jane.getTeamId());

        Player bob = Players.get(2);
        assertEquals("bob@nearsoft.com", bob.getUserName());
        assertEquals("Bob", bob.getFirstName());
        assertEquals("Ross", bob.getLastName());
    }

    @Test
    public void testGetPlayersByTeamId() throws Exception {
        PlayerRepository PlayerRepository = new PlayerRepository(_connection);
        List<Player> Players = PlayerRepository.getPlayersByTeamId(100);
        assertEquals(2, Players.size());

        Player john = Players.get(0);
        assertEquals("john@nearsoft.com", john.getUserName());
        assertEquals("John", john.getFirstName());
        assertEquals("Doe", john.getLastName());
        assertEquals(100, john.getTeamId());

        Team softball = john.getTeam();
        assertEquals(100, softball.getId());
        assertEquals("Softball", softball.getName());

        Player jane = Players.get(1);
        assertEquals("jane@nearsoft.com", jane.getUserName());
        assertEquals("Jane", jane.getFirstName());
        assertEquals("Doe", jane.getLastName());
        assertEquals(100, jane.getTeamId());

        assertEquals(softball, jane.getTeam());
    }



}
