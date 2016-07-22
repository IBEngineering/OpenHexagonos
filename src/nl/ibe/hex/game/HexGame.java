/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 *
 * @author mihaita
 */
public class HexGame implements IHexGame {
    
    private HexBoard board;
    private HexPlayer[] players = new HexPlayer[2];
    private ArrayList<HexMove> gameHist = new ArrayList<>();
    
    public HexGame (HexPlayer p1, HexPlayer p2)
    {
        
    }

    @Override
    public void register(IHexGameListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HexPlayer getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean move(HexMove move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<HexCoordinate> getCloneTiles(HexCoordinate c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<HexCoordinate> getMoveTiles(HexCoordinate c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undoMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HexBoard getBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
