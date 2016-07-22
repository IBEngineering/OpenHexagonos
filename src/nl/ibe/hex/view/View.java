/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.Clicker;
import nl.ibe.hex.game.HexBoard;
import nl.ibe.hex.game.HexCoordinate;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.HexTile;
import nl.ibe.hex.game.IHexGame;
import nl.ibe.hex.game.IHexGameListener;

/**
 *
 * @author MisterCavespider
 */
public class View implements IHexGameListener{
    
    //contains all the HexSpatials
    public ViewGrid grid;
    
    //who to send the moves to
    private IHexGame game;
    
    //where to find rootNodes
    private SimpleApplication app;
    
    //getting the input
    private Clicker click;
    
    public View(SimpleApplication app) {
        //Is there a game?
        if(game == null) {
            //No
            Logger.getLogger("View").log(Level.SEVERE, "There is no game in the view!");
        } else {
            //Yes
            ConcurrentHashMap<HexCoordinate, HexTile> board = HexBoard.getBoard();
            grid = new ViewGrid(board, app.getRootNode());
            
            click = new Clicker(app);
            
            app.getInputManager().addMapping(Clicker.mapping, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            app.getInputManager().addListener(click, Clicker.mapping);
        }
        
    }
    
    
    
    @Override
    public void tilesChanged(ArrayList<HexTile> hexagons) {
        //The tiles have changed owner
        //Loop over them and change the blobs
    }

    @Override
    public void playerChanged(HexPlayer player) {
        //Info for whom's turn it is
    }

    @Override
    public void registeredAt(IHexGame game) {
        this.game = game;
    }
}
