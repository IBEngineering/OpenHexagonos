/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.Objects;
import nl.ibe.hex.game.player.Team;

/**
 *
 * @author b0rg3rt
 */
public class HexPlayer {
    
    private String name;
    boolean human = true;
    private int points = 0;
    private Team team;
    
    public static enum Type {
        
        CELL,
        BACTERIA,
        BLOCKER;
        
    }

    public HexPlayer(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return human;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public void incrementPoints() {
        points = points + 1;
    }
    
    public void decrementPoints() {
        points = points - 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (this.human ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.team);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HexPlayer other = (HexPlayer) obj;
        if (this.human != other.human) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.team != other.team) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HexPlayer{" + "name=" + name + ", human=" + human + ", team=" + team + ", points=" + points + '}';
    }

    public Team getTeam() {
        return team;
    }
    
}
