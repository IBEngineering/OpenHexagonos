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
import nl.ibe.hex.game.player.Team;

/**
 *
 * @author b0rg3rt
 * TODO: implement this
 */
public class HexTwoStepAiPLayer extends HexPlayer implements IHexGameAiPlayer {
    
    private HexStateTreeNode root;
    private static int depth = 2;
    
    public HexTwoStepAiPLayer(String name, Team team) {
        super(name, team);
        this.human = false;
    }   
    
    @Override
    public HexMove getNextHexMove(HexBoard b,HexPlayer currentPlayer, HexPlayer otherPlayer)
    {
        root = new HexStateTreeNode(b, currentPlayer, otherPlayer, null, true);
        root.generateChildren();
        
        
        ArrayList<HexStateTreeNode> nodes = root.getChildren();
        for (int i = 0; i < depth; i++)
        {
            for(HexStateTreeNode node : nodes)
            {
                node.generateChildren();
                nodes = node.getChildren();
            }
        }

        //System.out.println(root.toString());
        
        int bestMoveVal = Integer.MIN_VALUE;
        HexMove bestMove = null;
        nodes = root.getChildren();
        for(HexStateTreeNode node : nodes)
        {
            int val = node.accumulatePoints(node);
            //System.out.println("int val " + val);
            if (val > bestMoveVal)
            {
                bestMoveVal = val;
                bestMove = node.getMove();
            }
        }
        System.out.println(root.toString());

       // System.out.println("bestMove: " + bestMove);
        return bestMove;
    }
}
