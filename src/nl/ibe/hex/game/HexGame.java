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
 * @author b0rg3rt
 */
public class HexGame implements IHexGame {
    
    private HexBoard board;
    private HexPlayer[] players = new HexPlayer[2];
    private HexPlayer currPlayer; 
    private ArrayList<HexMove> gameHist = new ArrayList<>();
    private ArrayList<IHexGameListener> listeners = new ArrayList<>();
    private HexMoveValidator validator;
    
    public HexGame (HexPlayer p1, HexPlayer p2)
    {
        players[0] = p1;
        players[1] = p2;
        currPlayer = p1;
        this.board = new HexBoard(4);
        this.validator = new HexMoveValidator(this.board);
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

    private void nextPlayer()
    {
        if (currPlayer == players[0])
        {
            currPlayer = players[1];
        }
        else
        {
            currPlayer = players[0];
        }
        notifyListenersPlayerChanged(currPlayer);
    }
    
    private ArrayList<HexCoordinate> pruneImpossibleLocations(ArrayList<HexCoordinate> input)
    {
        ArrayList<HexCoordinate> result = new ArrayList<>();
        Iterator<HexCoordinate> it = input.iterator();
        HexCoordinate curr = null;
        while (it.hasNext())
        {
            curr = it.next();
            HexTile tile = HexBoard.board.get(curr);
            
            if (  tile != null)
            {
                if (tile.getOwner() == null)
                {
                    result.add(curr);
                }
            }
        }
        return result;
        
    }
    
    @Override
    public HexPlayer getCurrentPlayer() {
        return currPlayer;
    }

    @Override
    public boolean move(HexMove move) {
        nextPlayer();
        return true;
    }

    @Override
    public ArrayList<HexCoordinate> getCloneTiles(HexCoordinate c) {
        return c.ring(1);
    }

    @Override
    public ArrayList<HexCoordinate> getMoveTiles(HexCoordinate c) {
        return pruneImpossibleLocations(c.ring(2));
    }

    @Override
    public void undoMove() {
        
    }

    @Override
    public HexBoard getBoard() {
       return board;
    }
    
    
}
