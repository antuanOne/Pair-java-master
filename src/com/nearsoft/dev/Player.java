package com.nearsoft.dev;

import java.util.Collections;
import java.util.List;

/**
 * Created by Usuario on 08/07/2016.
 */
public class Player implements Comparable{
    private String userName;
    private String firstName;
    private String lastName;
    private Team team = new Team();

    public Player(){

    }

    public int getTeamId(){
        return team.getId();
    }

    public void setTeamId(int id){
        this.team.setId(id);
    }

    public static List<Player> sortByUserNameAsc(List<Player> listPlayers){

        Collections.sort(listPlayers);
        return listPlayers;
    }

    public static List<Player> sortByUserNameDesc(List<Player> listPlayers){
        Collections.sort(listPlayers);
        Collections.reverse(listPlayers);
        return listPlayers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (userName != null ? !userName.equals(player.userName) : player.userName != null) return false;
        if (firstName != null ? !firstName.equals(player.firstName) : player.firstName != null) return false;
        if (lastName != null ? !lastName.equals(player.lastName) : player.lastName != null) return false;
        return !(team != null ? !team.equals(player.team) : player.team != null);

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int compareTo(Object o) {
        Player playerTmp = (Player) o;
        return this.userName.compareTo(playerTmp.getUserName());
    }
}
