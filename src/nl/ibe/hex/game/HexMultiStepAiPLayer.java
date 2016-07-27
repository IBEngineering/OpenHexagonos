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
public class HexMultiStepAiPLayer extends HexPlayer implements IHexGameAiPlayer {
    
    private HexStateTreeNode root;
    private static final int DEPTH = 3;
    
    public HexMultiStepAiPLayer(String name, Team team) {
        super(name, team);
        this.human = false;
    }
    
    private void recurse(HexStateTreeNode node, int levels)
    {
        if (levels > 0)
        {
            ArrayList<HexStateTreeNode> children = node.getChildren();
            for (HexStateTreeNode child : children)
            {
                child.generateChildren();
                recurse(child, levels -1);
            }
            
        }
        
    }
    
    @Override
    public HexMove getNextHexMove(HexBoard b,HexPlayer currentPlayer, HexPlayer otherPlayer)
    {
        root = new HexStateTreeNode(b, currentPlayer , otherPlayer, null, false);
        root.generateChildren();
        

        recurse(root,DEPTH);
        ArrayList<HexStateTreeNode> nodes = root.getChildren();

        //System.out.println(root.toString());
        
//        System.out.println("digraph g {");
//        System.out.println(root.dumpTree());
//        System.out.println("}");
        
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
        
        System.out.println("digraph g {");
        System.out.println(root.dumpTree());
        System.out.println("}");
       // System.out.println("bestMove: " + bestMove);
        return bestMove;
    }
}
