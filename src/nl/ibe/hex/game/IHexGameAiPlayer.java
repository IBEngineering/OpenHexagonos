/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

/**
 *
 * @author Christien
 */
public interface IHexGameAiPlayer {
     public HexMove getNextHexMove(HexBoard b, HexPlayer currentPlayer, HexPlayer otherPlayer);
    
}
