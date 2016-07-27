/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author b0rg3rt
 */
public class HexMoveValidator {
    
    public static boolean isValidMove(HexMove move, HexBoard board)
    {
        ConcurrentHashMap<HexCoordinate, HexTile> bord = board.getBoard();
        // Check if the distance between a and b is < 3
        if (move.getFrom().distance(move.getTo()) > 2 )
        {
            System.out.println("Distance to big");
            return false;
        }

        // Check if the source is a valid position
        HexTile fromTile = bord.get(move.getFrom());
        if (fromTile == null)
        {
            System.out.println("Source invalid");
            return false;
        }
        // Check if we are moving our piece
        if (fromTile.getOwner() != move.getPlayer())
        {
            if (fromTile.getOwner() == null)
            {
                System.out.println("Tile not owned by anyone !");
                return false;
            }
            System.out.println("Owner invalid Tile:" + fromTile.getOwner().toString() + " move owner: " + move.getPlayer().toString());
            return false;
        }
        
        // Check if the destination is a valid position
        HexTile toTile = bord.get(move.getTo());
        if (toTile == null)
        {
            System.out.println("Destination invalid");
            return false;
        }
        if (toTile.getOwner() != null)
        {
            System.out.println("Destination occupied");
            return false;
        }
        System.out.println("This is a valid move !");
        return true;
    }
    
    /*
    * Returns all possible moves for player P
    * Maybe change this to a list of moves that we prune during the game.
    * That would be more efficient :)
    */
    
    public static ArrayList<HexMove> getPossibleMoves(HexPlayer p, HexBoard board)
    {
        ArrayList<HexMove> moves = new ArrayList<>();
        ConcurrentHashMap<HexCoordinate, HexTile> bord = board.getBoard();
        
        
        // Get all tiles owned by P
        ArrayList<HexTile> pTiles = new ArrayList<>();        
        for (Map.Entry<HexCoordinate, HexTile> entry : bord.entrySet()) {
            HexCoordinate c = entry.getKey();
            HexTile t = entry.getValue();
            
            if (t.getOwner() == p)
            {
                pTiles.add(t);
            }
        }
        
        // Get All possible moves
        for (HexTile ourTile: pTiles)
        {
            HexCoordinate c = ourTile.getCoordinate();
            ArrayList<HexCoordinate> ringlist = c.ring(1);
            ringlist.addAll(c.ring(2));
            
            for (HexCoordinate coord: ringlist)
            {
                HexTile tile = board.board.get(coord);
                if (tile != null)
                {
                    if (tile.getOwner() == null)
                    {
                        moves.add(new HexMove(p, c, coord));
                    }
                }
            }
        }
        
        //Dump moves !
        /*
        System.out.println("Dumping moves for player " + p.toString());
        for (HexMove m : moves)
        {
            System.out.println(m.toString());
        }
        */
        
        return moves;
    }
    
    
    public static HexMove getBestMove(ArrayList<HexMove> moves, HexBoard board)
    {
        int bestMoveVal = -1;
        HexMove bestMove = null;
        for(HexMove m : moves)
        {
            Integer moveVal = new Integer(getMoveValue(m, board));
            if (moveVal > bestMoveVal)
            {
                bestMoveVal = moveVal;
                bestMove = m;
            }
        }
        
        return bestMove;
    }
    
    public static int getMoveValue(HexMove move, HexBoard board)
    {
        ConcurrentHashMap<HexCoordinate, HexTile> bord = board.getBoard();
        if (move == null) { return Integer.MIN_VALUE; }
            
        int points = 0;
        
        //If we do a clone we get 1 point
        if (move.getFrom().distance(move.getTo()) == 1)
        {
            points = points + 1;
        }

        ArrayList<HexCoordinate> neighs = move.getTo().ring(1);
        
        for(HexCoordinate c : neighs)
        {
            HexTile t = bord.get(c);
            if (t != null)
            {
                if (t.getOwner() != null)
                {
                    if (t.getOwner() != move.getPlayer())
                    {
                        // We duplicate the score for Captures
                        points = points + 1;
                    }
                }
            }
        }
        return points;

    }
    
    
    
    public static void executeMove(HexMove move, HexBoard board, HexPlayer currPlayer)
    {
        ConcurrentHashMap<HexCoordinate,HexTile> bord = board.getBoard();
        ArrayList<HexChange> changedTiles = new ArrayList<>();
        
        if(move == null) { return; }
        if(move.getFrom() == null) { return; }
        if(move.getTo() == null) { return; }
        
        //Determine if we do a move or clone
        if (move.getFrom().distance(move.getTo()) < 2)
        {
            //Clone
            HexTile src = bord.get(move.getFrom());
            HexTile dst = bord.get(move.getTo());
            dst.setOwner(move.getPlayer());
            
            //increment points by 1
            //move.getPlayer().incrementPoints();
            
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
            if (neighTile != null &&
                    neighTile.getOwner() != null && 
                    neighTile.getOwner() != move.getPlayer() )
            {
                //neighTile.getOwner().decrementPoints();
                //move.getPlayer().incrementPoints();
                
                neighTile.setOwner(currPlayer);
                
                HexTile dst = bord.get(move.getTo());
                HexChange change = new HexChange(dst, neighTile, HexChange.Type.CONQUEST);
                changedTiles.add(change);
                
            }
        }
    }
    
}
