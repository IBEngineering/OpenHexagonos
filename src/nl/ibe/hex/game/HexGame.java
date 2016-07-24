/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author b0rg3rt, MisterCavespider
 */
public class HexGame implements IHexGame {
    
    private HexBoard board;
    private HexPlayer[] players = new HexPlayer[2];
    private HexPlayer currPlayer; 
    private HexPlayer otherPlayer;
    private ArrayList<HexMove> gameHist = new ArrayList<>();
    private ArrayList<IHexGameListener> listeners = new ArrayList<>();
    
    public HexGame (HexPlayer p1, HexPlayer p2)
    {
        players[0] = p1;
        players[1] = p2;
        currPlayer = p1;
        otherPlayer = p2;
        this.board = new HexBoard(4);
        this.setStartPositions();
        
        this.setBlockFields();
    }

    private void setStartPositions()
    {
        int bSize = board.getRadius();
        // 3 for p1
        HexCoordinate p1_a = new HexCoordinate(bSize, 0, -bSize);
        HexCoordinate p1_b = new HexCoordinate(-bSize, bSize, 0);
        HexCoordinate p1_c = new HexCoordinate(0, -bSize, bSize);
       
        // 3 for p2
        HexCoordinate p2_a = new HexCoordinate(0, bSize, -bSize);
        HexCoordinate p2_b = new HexCoordinate(-bSize, 0, bSize);
        HexCoordinate p2_c = new HexCoordinate(bSize, -bSize, 0);        
        
        board.getBoard().get(p1_a).setOwner(players[0]);
        board.getBoard().get(p1_b).setOwner(players[0]);
        board.getBoard().get(p1_c).setOwner(players[0]);
        
        players[0].setPoints(3);
        
        board.getBoard().get(p2_a).setOwner(players[1]);
        board.getBoard().get(p2_b).setOwner(players[1]);
        board.getBoard().get(p2_c).setOwner(players[1]);
        
        players[1].setPoints(3);

    }
    
    private void setBlockFields()
    {
        HexPlayer blocker = new HexPlayer("blocker", HexPlayer.Type.BLOCKER);


        HexCoordinate b1 = new HexCoordinate(1, 0, -1);
        HexCoordinate b2 = new HexCoordinate(-1, 1, 0);
        HexCoordinate b3 = new HexCoordinate(0, -1, 1);
        
        board.getBoard().get(b1).setOwner(blocker);
        board.getBoard().get(b2).setOwner(blocker);
        board.getBoard().get(b3).setOwner(blocker);
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
    
    private void notifyListenersTilesChanged(ArrayList<HexChange> hexagons)
    {
        Iterator<IHexGameListener> it = listeners.iterator();
        while(it.hasNext())
        {
            it.next().tilesChanged(hexagons);
        }           
    }
    
    private void notifyListenersGameEnd(HexPlayer player)
    {
        Iterator<IHexGameListener> it = listeners.iterator();
        while(it.hasNext())
        {
            it.next().gameEnd(player);
        }           
    }

    private void decideWinner()
    {
        if (players[0].getPoints() == players[1].getPoints())
        {
            notifyListenersGameEnd(null);
        }
        else if (players[0].getPoints() > players[1].getPoints())
        {
            notifyListenersGameEnd(players[0]);
        }
        else
        {
            notifyListenersGameEnd(players[1]);
        }
    }
    
    private void nextPlayer()
    {
        
        if (currPlayer == players[0])
        {
            currPlayer = players[1];
            otherPlayer = players[0];
        }
        else
        {
            currPlayer = players[0];
            otherPlayer = players[1];
        }
        
        notifyListenersPlayerChanged(currPlayer);
        boolean isLastMove = HexMoveValidator.getPossibleMoves(currPlayer, board).isEmpty();
        if (isLastMove)
        {
            decideWinner();
        }
        
        // Decide on how to do this with ita 
        if (!currPlayer.isHuman() && !isLastMove && currPlayer instanceof IHexGameAiPlayer)
        {
           HexMove aiMove = ((IHexGameAiPlayer)currPlayer).getNextHexMove(board, currPlayer, otherPlayer);
           this.move(aiMove);
           nextPlayer();

        }
    }
    
    private ArrayList<HexCoordinate> pruneImpossibleLocations(ArrayList<HexCoordinate> input)
    {
        ArrayList<HexCoordinate> result = new ArrayList<>();
        Iterator<HexCoordinate> it = input.iterator();
        HexCoordinate curr = null;
        while (it.hasNext())
        {
            curr = it.next();
            HexTile tile = board.board.get(curr);
            
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
        if (move.getPlayer() != getCurrentPlayer())
        {
            System.out.println("Not " + move.getPlayer() + "'s turn");
            return false;
        }
        
        if (!HexMoveValidator.isValidMove(move, board))
        {
            System.out.println("Invalid move");
            return false;
        }
        
        executeMove(move);       
        // View now does this
        //nextPlayer();
        return true;
    }
    
    /*
    * TODO: remove this and use the static function in HexMoveValidator
    */
    private void executeMove(HexMove move)
    {
        ConcurrentHashMap<HexCoordinate,HexTile> bord = board.getBoard();
        ArrayList<HexChange> changedTiles = new ArrayList<>();
        
        //Determine if we do a move or clone
        if (move.getFrom().distance(move.getTo()) < 2)
        {
            //Clone
            HexTile src = bord.get(move.getFrom());
            HexTile dst = bord.get(move.getTo());
            dst.setOwner(move.getPlayer());
            
            //increment points by 1
            move.getPlayer().incrementPoints();
            
            HexChange change = new HexChange(src, dst, HexChange.Type.DUPLICATION);
            changedTiles.add(change);
            
        }
        else
        {
            //Jump
            HexTile src = bord.get(move.getFrom());
            HexTile dst = bord.get(move.getTo());
            dst.setOwner(move.getPlayer());
            
            src.setOwner(null);
            
            HexChange change = new HexChange(src, dst, HexChange.Type.JUMP);
            changedTiles.add(change);

        }
        
        //Defeat Neigbors
        for (int i = 0; i < 6; i++)
        {
            HexCoordinate neigh = move.getTo().getNeighbor(i);
            HexTile neighTile = bord.get(neigh);
            
            // If an existing tile
            // and If not an empty tile
            // and if not our tile
            // and if not a blocker
            if (neighTile != null &&
                    neighTile.getOwner() != null && 
                    neighTile.getOwner() != move.getPlayer() && 
                    !neighTile.getOwner().getType().equals(HexPlayer.Type.BLOCKER))
            {
                neighTile.getOwner().decrementPoints();
                move.getPlayer().incrementPoints();
                
                neighTile.setOwner(currPlayer);
                
                HexTile dst = bord.get(move.getTo());
                HexChange change = new HexChange(dst, neighTile, HexChange.Type.CONQUEST);
                changedTiles.add(change);
                
            }
        }
        
        this.notifyListenersTilesChanged(changedTiles);
    }

    @Override
    public ArrayList<HexCoordinate> getCloneTiles(HexCoordinate c) {
        return pruneImpossibleLocations(c.ring(1));
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

    @Override
    public void nextTurn() {
        nextPlayer();
    }
    
    
}
