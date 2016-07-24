/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Christien
 */
public class HexOneStepAiPlayer extends HexPlayer implements IHexGameAiPlayer {
    
    public HexOneStepAiPlayer(String name, Type type) {
        super(name, type);
        this.human = false;
    }
    
    @Override
    public HexMove getNextHexMove(HexBoard b,HexPlayer currentPlayer, HexPlayer otherPlayer)
    {
        ArrayList<HexMove> allMoves = HexMoveValidator.getPossibleMoves(this, b);
     
        HashMap <Integer,ArrayList<HexMove>> moveMap = new HashMap<Integer,ArrayList<HexMove>>();
        
        for (HexMove m : allMoves)
        {
            Integer moveVal = HexMoveValidator.getMoveValue(m, b);

            ArrayList moves = moveMap.get(moveVal);
            if (moves == null)
            {
                moves = new ArrayList();
            }
            moves.add(m);
            moveMap.put(moveVal, moves);
        }
        
        Integer[] moveValues = moveMap.keySet().toArray(new Integer[moveMap.size()]);
        Arrays.sort(moveValues);
        
        Integer highest = moveValues[moveValues.length -1];
        
        ArrayList<HexMove> bestMoves = moveMap.get(highest);
        
        int random = (int) ( Math.random() * bestMoves.size() );
        
        return bestMoves.get(random);
    }
}