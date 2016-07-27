/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import com.sun.corba.se.impl.orbutil.HexOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christien
 */
public class HexStateTreeNode {
    
    private HexBoard board;
    private int points = 0;
    private HexMove move;
    private HexPlayer player;
    private HexPlayer opponent;
    private boolean isOpponent;
    public int id;
    public Integer accumulatedPoints = null;
    
    private ArrayList<HexStateTreeNode> children = new ArrayList<>();
    private HexStateTreeNode parent;

    public HexStateTreeNode(HexBoard board, HexPlayer player, HexPlayer opponent, HexStateTreeNode parent, boolean isOpponent) {
        this.board = board;
        this.player = player;
        this.opponent = opponent;
        this.parent = parent;
        this.isOpponent = isOpponent;
        this.id = (int) (Math.random() * Integer.MAX_VALUE);
    }
    
    public void generateChildren()
    {        
        ArrayList<HexMove> moves = HexMoveValidator.getPossibleMoves(player, board);
        // We assume our opponent will pick the best move 
        if (!isOpponent)
        {
            for (HexMove m: moves)
            {
                HexBoard childBoard;

                childBoard = board.getDuplicate();
                //Create a child node and switch the player and opponent and negate the isOpponent;
                HexStateTreeNode child = new HexStateTreeNode(childBoard, opponent, player, this, !isOpponent);
                child.setPoints(HexMoveValidator.getMoveValue(m, child.board));
                
                HexMoveValidator.executeMove(m, childBoard, player);
                child.setMove(m);
                children.add(child);

            }
        }
        else
        {

            //Get the bestMove for our opponent;
            HexMove bestMove = HexMoveValidator.getBestMove(moves, board);
            HexBoard childBoard;           
            childBoard = board.getDuplicate();
            HexStateTreeNode child = new HexStateTreeNode(childBoard, opponent, player, this, !isOpponent);
            child.setPoints(HexMoveValidator.getMoveValue(bestMove, child.board));
            
            HexMoveValidator.executeMove(bestMove, childBoard, player);
            child.setMove(bestMove);
            children.add(child);

        }

    }
    
    public int accumulatePoints(HexStateTreeNode startNode)
    {
        if (!startNode.getChildren().isEmpty())
        {
            int maxPoints = Integer.MIN_VALUE;
            boolean childopponent = false;
            for (HexStateTreeNode currNode: startNode.getChildren())
            {   
                int chldPts;
                if (currNode.accumulatedPoints == null)
                {
                    chldPts = accumulatePoints(currNode);
                }
                chldPts = currNode.accumulatedPoints;
                if (chldPts > maxPoints)
                {
                    childopponent = currNode.isIsOpponent();
                    maxPoints = chldPts;
                }
               
            }
            
            if (!childopponent)
            {
                maxPoints = -1 * maxPoints;
                startNode.accumulatedPoints =startNode.getPoints() + maxPoints;
            }
            else
            {
                startNode.accumulatedPoints = -1 * startNode.getPoints() + maxPoints;
            }
            
            
            //return maxPoints;
            return startNode.accumulatedPoints;
            
        } else {
            
            if (!isOpponent)
            {
                startNode.accumulatedPoints = -1 * startNode.getPoints();
                return -1 * startNode.getPoints();
            }
            startNode.accumulatedPoints = startNode.getPoints();
            return startNode.getPoints();
        }
        
    }
    
    
    
    public ArrayList<HexStateTreeNode> getChildren()
    {
        return children;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public HexPlayer getOpponent() {
        return opponent;
    }

    public boolean isIsOpponent() {
        return isOpponent;
    }

    public HexMove getMove() {
        return move;
    }

    public void setMove(HexMove move) {
        this.move = move;
    }

   
    public String dumpTree()
    {
        String str = "";
        
        String colorString = "";
        if (!this.isOpponent)
        {
            colorString = " [color=red] ";
        }
        else
        {
            colorString = " [color=blue] ";
        }
        
        str += "a"+this.id + " [label=\"a" + this.id + " points:" + this.points + " acc: " + this.accumulatedPoints + "\" ] " + colorString + " ;\n";
        
        
        for (HexStateTreeNode c : children)
        {
            str += "a"+this.id + " -> " + "a"+c.id + ";\n";
            str = str + c.dumpTree();
        }
        return str;
    }
    
    private String printChildren()
    {
        
        if (children.size() > 0)
        {
            String str = "";
            for (HexStateTreeNode c : children)
            {
                str = str + c.toString();
            }

            return str;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public String toString() {
        
        if (!children.isEmpty())
        {
            return "HexStateTreeNode{" + "points=" + points + ", move=" + move + ", isOpponent=" + isOpponent + ", children=" + printChildren() + "}\n";
        }
        else
        {
            return "HexStateTreeNode{ Leaf " + ", move=" + move +  "points=" + points + ", isOpponent=" + isOpponent  + "}\n";
        }
    }
    
    
}
