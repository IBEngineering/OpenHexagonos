/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game.player;

import java.util.ArrayList;

/**
 *
 * @author MisterCavespider
 */
public class TeamManager {
    
    protected static ArrayList<Team> allTeams = new ArrayList<>();
    protected static ArrayList<Team> visibleTeams = new ArrayList<>();
    
    public static void loadDefaults() {
        Team cell = new Team("Cell", 0);
        Team bacteria = new Team("Bacteria", 1);
        Team virus = new Team("Virus", 2);
    }

    public static void add(Team team) {
        allTeams.add(team);
        if(team.isVisible()) {
            visibleTeams.add(team);
        }
    }
    
    public static Team get(long id) {
        for (Team t : allTeams) {
            if(t.getNumericalID() == id) {
                return t;
            }
        }
        
        //somehow not found
        return null;
    }
    
    public static void sync(Team team) {
        if(!allTeams.contains(team)) {
            allTeams.add(team);
        }
        if(team.isVisible() && !visibleTeams.contains(team)) {
            visibleTeams.add(team);
        }
    }
    
    public static void remove(Team team) {
        allTeams.remove(team);
        if(team.isVisible()) {
            visibleTeams.remove(team);
        }
    }
    
    public static int getVisibleCount() {
        return visibleTeams.size();
    }

    public static ArrayList<Team> getVisibleTeams() {
        return visibleTeams;
    }
}
