/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 *
 * @author b0rg3rt, MisterCavespider
 */
public interface IHexGame {
    
    public void register(IHexGameListener listener);
    public HexPlayer getCurrentPlayer();
    public boolean move(HexMove move); 
    public ArrayList<HexCoordinate> getCloneTiles(HexCoordinate c);
    public ArrayList<HexCoordinate> getMoveTiles(HexCoordinate c);
    public void undoMove();
    public HexBoard getBoard();
    public void nextTurn();

}
