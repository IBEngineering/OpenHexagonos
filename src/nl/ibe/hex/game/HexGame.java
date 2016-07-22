/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author mihaita
 */
public class HexGame implements IHexGame {
    
    private HexBoard board;
    private HexPlayer[] players = new HexPlayer[2];
    private HexPlayer currPlayer; 
    private ArrayList<HexMove> gameHist = new ArrayList<>();
    private ArrayList<IHexGameListener> listeners = new ArrayList<>();
    
    public HexGame (HexPlayer p1, HexPlayer p2)
    {
        players[0] = p1;
        players[1] = p2;
        currPlayer = p1;
    }

    @Override
    public void register(IHexGameListener listener) {
       listeners.add(listener);
       listener.registeredAt(this);
    }
    
    private void notifyListenersPlayerChanged(HexPlayer p)
    {
        Iterator<IHexGameListener> it = listeners.iterator();
        while(it.hasNext())
        {
            it.next().playerChanged(p);
        }        
    }
    
    private void notifyListenersTilesChanged(ArrayList<HexTile> hexagons)
    {
        Iterator<IHexGameListener> it = listeners.iterator();
        while(it.hasNext())
        {
            it.next().tilesChanged(hexagons);
        }           
    }

    @Override
    public HexPlayer getCurrentPlayer() {
        return currPlayer;
    }

    @Override
    public boolean move(HexMove move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<HexCoordinate> getCloneTiles(HexCoordinate c) {
        return null;
    }

    @Override
    public ArrayList<HexCoordinate> getMoveTiles(HexCoordinate c) {
        return null;
    }

    @Override
    public void undoMove() {
        
    }

    @Override
    public HexBoard getBoard() {
       return board;
    }
    
    
}
