package com.nearsoft.dev.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.nearsoft.dev.Player;
import com.nearsoft.dev.Team;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void testPlayerAttributes() {
		Player player = new Player();
		assertNull(player.getUserName());
		assertNull(player.getFirstName());
		assertNull(player.getLastName());
		assertEquals(0, player.getTeamId());

		player.setUserName("doo@nearsoft.com");
		player.setFirstName("John");
		player.setLastName("Doe");
		Team team = new Team();
		team.setId(100);
		team.setName("Baseball");
		player.setTeam(team);
		player.setTeamId(100);

		assertEquals("doo@nearsoft.com", player.getUserName());
		assertEquals("John", player.getFirstName());
		assertEquals("Doe", player.getLastName());
		assertEquals(100, player.getTeamId());
		assertEquals(team, player.getTeam());
	}

	@Test
	public void testSortByUserNameAsc() {
		Player playerFoo = new Player();
		playerFoo.setUserName("doo@nearsoft.com");

		Player playerBar = new Player();
		playerBar.setUserName("bar@nearsoft.com");

		List<Player> Players = new ArrayList<Player>();
		Players.add(playerFoo);
		Players.add(playerBar);

		List<Player> sorted = Player.sortByUserNameAsc(Players);
		assertEquals(2, sorted.size());
		assertEquals("bar@nearsoft.com", sorted.get(0).getUserName());
		assertEquals("doo@nearsoft.com", sorted.get(1).getUserName());
	}

	@Test
	public void testSortByUserNameDesc() {
		Player playerFoo = new Player();
		playerFoo.setUserName("doo@nearsoft.com");

		Player PlayerBar = new Player();
		PlayerBar.setUserName("bar@nearsoft.com");

		List<Player> Players = new ArrayList<Player>();
		Players.add(playerFoo);
		Players.add(PlayerBar);

		List<Player> sorted = Player.sortByUserNameDesc(Players);
		assertEquals(2, sorted.size());
		assertEquals("doo@nearsoft.com", sorted.get(0).getUserName());
		assertEquals("bar@nearsoft.com", sorted.get(1).getUserName());
	}

}
