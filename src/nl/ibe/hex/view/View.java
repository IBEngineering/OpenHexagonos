/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import de.jarnbjo.oggtools.Player;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.Clicker;
import mygame.Main;
import nl.ibe.hex.game.HexBoard;
import nl.ibe.hex.game.HexCoordinate;
import nl.ibe.hex.game.HexMove;
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
    private HexPlayer player;
    
    //where to find rootNodes
    private SimpleApplication app;
    
    //getting the input
    private Clicker click;
    
    //selected stuff
    private HexSpatial selection;
    private boolean selected;
    private HexSpatial goal;
    
    public View(SimpleApplication app) {
        
        this.app = app;
    }
    
    public void construct() {
        //Is there a game?
        if(game == null) {
            //No
            Logger.getLogger("View").log(Level.SEVERE, "There is no game in the view!");
        } else {
            //Yes
            ConcurrentHashMap<HexCoordinate, HexTile> board = HexBoard.getBoard();
            grid = new ViewGrid(board, app.getRootNode());
            
            click = new Clicker(app, grid.node, this);
            
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
        this.player = player;
    }

    @Override
    public void registeredAt(IHexGame game) {
        this.game = game;
    }

    public void onClick(HexSpatial hex) {
        
        //Was something seleccted?
        if(selected) {
            System.out.println(hex.getCoord() + " - selected");
            //Clicked on the same thing?
            if(hex.getCoord() == selection.getCoord()) {
                return;
            }
            
            goal = hex;
            
            //Move
            HexCoordinate c1 = selection.getCoord();
            HexCoordinate c2 = goal.getCoord();
            
            HexPlayer p = checkPlayer();
            
            HexMove move = new HexMove(p, c1, c2);
            
            boolean canItMove = game.move(move);
            
            Logger.getLogger("View").log(Level.INFO, "Can it move? {0}", canItMove);
            
        } else {
            //Select what we have here
            selection = hex;
            hex.setMaterial(Main.getColoredMaterial(ColorRGBA.Green));
            selected = true;
            
            System.out.println(hex.getCoord() + " - !selected");
        }
    }

    private HexPlayer checkPlayer() {
        if(player != null) {
            return player;
        } else {
            return game.getCurrentPlayer();
        }
    }
}
