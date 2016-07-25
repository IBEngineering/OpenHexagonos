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
    private int points;
    private HexMove move;
    private HexPlayer player;
    private HexPlayer opponent;
    private boolean isOpponent;
    
    private ArrayList<HexStateTreeNode> children = new ArrayList<>();
    private HexStateTreeNode parent;

    public HexStateTreeNode(HexBoard board, HexPlayer player, HexPlayer opponent, HexStateTreeNode parent, boolean isOpponent) {
        this.board = board;
        this.player = player;
        this.opponent = opponent;
        this.parent = parent;
        this.isOpponent = isOpponent;
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
                HexMoveValidator.executeMove(m, childBoard, player);
                //Create a child node and switch the player and opponent and negate the isOpponent;
                HexStateTreeNode child = new HexStateTreeNode(childBoard, opponent, player, this, !isOpponent);
                child.setPoints(new Integer(HexMoveValidator.getMoveValue(m, board)));
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
            HexMoveValidator.executeMove(bestMove, childBoard, player);
            HexStateTreeNode child = new HexStateTreeNode(childBoard, opponent, player, this, !isOpponent);
            child.setPoints(new Integer(HexMoveValidator.getMoveValue(bestMove, board)));
            child.setMove(bestMove);
            children.add(child);

        }

    }
    
    public int accumulatePoints(HexStateTreeNode startNode)
    {
        if (!startNode.getChildren().isEmpty())
        {
            int maxPoints = Integer.MIN_VALUE;
            for (HexStateTreeNode currNode: startNode.getChildren())
            {
                    int chldPts = accumulatePoints(currNode);
                    if (chldPts > maxPoints)
                    {
                        maxPoints = chldPts;
                    }
            }
            
            if (isOpponent)
            {
                maxPoints = -1 * maxPoints;
            }
            startNode.points += maxPoints;
            return maxPoints;
            
        } else {
            if (isOpponent)
            {
                return -1 * startNode.points;
            }
            return startNode.points;
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
