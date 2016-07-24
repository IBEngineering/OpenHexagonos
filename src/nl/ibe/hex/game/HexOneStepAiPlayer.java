/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 *
 * @author Christien
 */
public class HexOneStepAiPlayer extends HexPlayer implements IHexGameAiPlayer {
    
    public HexOneStepAiPlayer(String name, Type type) {
        super("Hex oneStep AI", type);
        this.human = false;
    }
    
    @Override
    public HexMove getNexHexMove(HexBoard b)
    {
        ArrayList<HexMove> allMoves = HexMoveValidator.getPossibleMoves(this, b);
              
        int bestMoveVal = -1;
        HexMove bestMove = null;
        
        for (HexMove m : allMoves)
        {
            int moveVal = HexMoveValidator.getMoveValue(m, b);
            System.out.print("MoveVal: " + moveVal);
            System.out.println(" bestVal: " + bestMoveVal);
            if (moveVal > bestMoveVal)
            {
                bestMove = m;
                bestMoveVal = moveVal;
            }
        }
        
        //int random = (int) ( Math.random() * moves.size() );
         System.out.println("Points: " + bestMoveVal + " move:" + bestMove);
        return bestMove;
    }
}