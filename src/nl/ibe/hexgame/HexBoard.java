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
    
    protected ConcurrentHashMap<HexCoordinate, Hexagon> board;
    private final int diameter;
    
    public HexBoard(int diameter)
    {
        this.diameter = diameter;
        
        generateField();
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

                    System.out.println("Coord:" + coord.toString());
                    
                    //Place it
                    Hexagon h = new Hexagon(coord);
                    board.put(coord, h);
                }
                y++;
            }
            
            
            x++;
        }
        
    }
}
