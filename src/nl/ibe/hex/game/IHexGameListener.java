/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 *
 * @author mihaita
 */
public interface IHexGameListener {
    
    public void tilesChanged(ArrayList<HexTile> hexagons);
    public void playerChanged(HexPlayer player);
    public void registeredAt(IHexGame game);
       
}
