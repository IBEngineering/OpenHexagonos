/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author b0rg3rt
 */
public class HexBoard {
    
    protected ConcurrentHashMap<HexCoordinate, HexTile> board;
    private final int radius;
    
    public HexBoard(int radius)
    {
        board = new ConcurrentHashMap<>();
        this.radius = radius;
        
        generateField();
        populateNeigbors();
    }
    
    /**
     * It uses the "+x-y line" algorythm.
     */
    protected final void generateField() {
        
        //Loop the x (i) from -size to size
        
        int x = -1*radius;
        while(x<radius+1) {
            
            int y = -1*radius;
            //Loop the y
            while(y<radius+1) {
                
                
                if (x+y <= radius && x+y >= -1*radius) {

                    //Loop the z
                    int z = -1*x -y;

                    HexCoordinate coord = new HexCoordinate(x, y, z);
                    
                    //Place it
                    HexTile h = new HexTile(coord);
                    board.put(coord, h);
                }
                y++;
            }
            
            
            x++;
        }
        
    }

    public ConcurrentHashMap<HexCoordinate, HexTile> getBoard() {
        return board;
    }
    
    /*
    * Fill the list of neighbors of each hexagon in the grid.
    */
    private void populateNeigbors()
    {
         Collection<HexTile> allTiles = board.values();
         Iterator<HexTile> it = allTiles.iterator();
         while (it.hasNext())
         {
             setHexNeighbors(it.next());
         }
         
    }
    
    private void setHexNeighbors(HexTile h) {
        
        /*
        var directions = [
            Cube(+1, -1,  0), Cube(+1,  0, -1), Cube( 0, +1, -1),
            Cube(-1, +1,  0), Cube(-1,  0, +1), Cube( 0, -1, +1)
            ]
        */
        // THIS IS VERY BROKEN LETS JUST REMOVE IT
        HexCoordinate[] neighs = { 
            new HexCoordinate(+1, -1,  0), 
            new HexCoordinate(+1,  0, -1), 
            new HexCoordinate( 0, +1, -1),
            new HexCoordinate(-1, +1,  0), 
            new HexCoordinate(-1,  0, +1), 
            new HexCoordinate( 0, -1, +1) 
        };        
                
        HexCoordinate c = h.getCoordinate();
        
        for (int i = 0; i < neighs.length; i++)
        {
            h.setNeighbor(i, board.get(c.add(neighs[i])) );
        }

    }

    public int getRadius() {
        return radius;
    }
    
    public ArrayList<HexCoordinate> getPlayerPositions(HexPlayer p)
    {
        ArrayList<HexCoordinate> coords = new ArrayList<>();
        
        Collection<HexTile> c = board.values();
        Iterator<HexTile> it = c.iterator();
        while (it.hasNext())
        {
            HexTile t = it.next();
            if (t.getOwner() == p)
            {
                coords.add(t.getCoordinate());
            }
        }
        return coords;
    }
    
    @Override
    public HexBoard clone() throws CloneNotSupportedException
    {
        super.clone();
        HexBoard b = new HexBoard(radius);
        b.board = null;
        
        ConcurrentHashMap<HexCoordinate, HexTile> nBoard = new ConcurrentHashMap();
         
        for (Map.Entry<HexCoordinate, HexTile> entry : board.entrySet()) {
            HexCoordinate key = entry.getKey();
            HexTile value = entry.getValue();
            nBoard.put(key.clone(), value.clone());
        }
       
        b.board = nBoard;
        return b;
    }
    
}
