/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author b0rg3rt
 * TODO: implement this
 */
public class HexTwoStepAiPLayer extends HexPlayer implements IHexGameAiPlayer {
    
    public HexTwoStepAiPLayer(String name, Type type) {
        super(name, type);
        this.human = false;
    }   
    
    @Override
    public HexMove getNextHexMove(HexBoard b,HexPlayer currentPlayer, HexPlayer otherPlayer)
    {
        //Get all the moves
        ArrayList<HexMove> allMoves = HexMoveValidator.getPossibleMoves(this, b);
     
        //Create boards for them
        ArrayList<HexBoard> hexBoards = new ArrayList<>();
        for (int i = 0; i < allMoves.size(); i++)
        {
            try {
                hexBoards.add(b.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(HexTwoStepAiPLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            HexBoard currBoard = hexBoards.get(i);
            HexMoveValidator.executeMove(allMoves.get(i), currBoard, this);
        }

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
