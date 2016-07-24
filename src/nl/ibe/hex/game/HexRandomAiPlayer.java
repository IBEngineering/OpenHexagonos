/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 *
 * @author b0rg3rt
 */
public class HexRandomAiPlayer extends HexPlayer implements IHexGameAiPlayer {
    
    public HexRandomAiPlayer(String name, Type type) {
        super(name, type);
        this.human = false;
    }
    
    @Override
    public HexMove getNexHexMove(HexBoard b)
    {
        ArrayList<HexMove> moves = HexMoveValidator.getPossibleMoves(this, b);
              
        int random = (int) ( Math.random() * moves.size() );

        return moves.get(random);
    }
    
    
}
