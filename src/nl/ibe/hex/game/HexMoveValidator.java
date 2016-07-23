/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author b0rg3rt
 */
public class HexMoveValidator {
    
    private HexBoard board;
    
    public HexMoveValidator(HexBoard board)
    {
        this.board = board;
    }
    
    public boolean isValidMove(HexMove move)
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

        return true;
    }
    
    
    
    
}
