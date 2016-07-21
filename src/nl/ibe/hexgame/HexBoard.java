/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hexgame;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mihaita
 */
public class HexBoard {
    
    protected static ConcurrentHashMap<HexCoordinate, Hexagon> board;
    private final int diameter;
    
    public HexBoard(int diameter)
    {
        this.diameter = diameter;
        
        generateField();
        populateNeigbors();
    }
    
    /**
     * It uses the "+x-y line" algorythm.
     */
    protected final void generateField() {
        
        //Loop the x (i) from -size to size
        
        int x = -1*diameter;
        while(x<diameter+1) {
            
            int y = -1*diameter;
            //Loop the y
            while(y<diameter+1) {
                
                
                if (x+y <= diameter && x+y >= -1*diameter) {

                    //Loop the z
                    int z = -1*x -y;

                    HexCoordinate coord = new HexCoordinate(x, y, z);
                    
                    //Place it
                    Hexagon h = new Hexagon(coord);
                    board.put(coord, h);
                }
                y++;
            }
            
            
            x++;
        }
        
    }
    
    
    
    /*
    * Fill the list of neighbors of each hexagon in the grid.
    */
    private void populateNeigbors()
    {
         board.forEachValue(board.size(),HexBoard::setHexNeighbors);
    }
    
    public static void setHexNeighbors(Hexagon h) {
        
        /*
        var directions = [
            Cube(+1, -1,  0), Cube(+1,  0, -1), Cube( 0, +1, -1),
            Cube(-1, +1,  0), Cube(-1,  0, +1), Cube( 0, -1, +1)
            ]
        */
        
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
    
    
}
