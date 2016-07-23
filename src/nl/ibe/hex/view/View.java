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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.Clicker;
import nl.ibe.hex.game.HexCoordinate;
import nl.ibe.hex.game.HexMove;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.HexTile;
import nl.ibe.hex.game.IHexGame;
import nl.ibe.hex.game.IHexGameListener;

/**
 * This contains everything for the view.
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
    
    //A logger!
    private static final Logger LOG = Logger.getLogger("View");
    
    public View(SimpleApplication app) {
        this.app = app;
        
        //Start off the ModelSupplier by giving it the app
        ModelSupplier.start(app, new ModelSupplierSettings(true));
    }
    
    public void construct() {
        //Is there a game?
        if(game == null) {
            //No
            LOG.log(Level.SEVERE, "There is no game in the view!");
        } else {
            //Yes
            ConcurrentHashMap<HexCoordinate, HexTile> board = game.getBoard().getBoard();
            grid = new ViewGrid(board, app.getRootNode());
            
            click = new Clicker(app, grid.gridNode, this);
            
            app.getInputManager().addMapping(Clicker.mapping, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            app.getInputManager().addListener(click, Clicker.mapping);
        }
    }
    
    
    @Override
    public void tilesChanged(ArrayList<HexTile> hexagons) {
        //The tiles have changed owner
        //Loop over them and change the blobs
        
        for (HexTile tile : hexagons) {
            HexPlayer owner = tile.getOwner();
            
            System.out.println(owner.getType());
            grid.grid.get(tile.getCoordinate()).setOwner(owner.getType());
        }
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
            LOG.log(Level.FINE, "HexCoord(selected): {0}", hex.getCoord());
            //Clicked on the same thing?
            if(hex.getCoord() == selection.getCoord()) {
                return;
            }
            
            goal = hex;
            
            //Move
            HexCoordinate c1 = selection.getCoord();
            HexCoordinate c2 = goal.getCoord();
            
            //HexPlayer p = checkPlayer();
            HexPlayer p = game.getCurrentPlayer();
            
            HexMove move = new HexMove(p, c1, c2);
            
            boolean canItMove = game.move(move);
            
            LOG.log(Level.INFO, "Can it move? {0}", canItMove);
            selected = false;
            
            //Reset the colors
            for (Map.Entry<HexCoordinate, HexSpatial> en : grid.grid.entrySet()) {
                HexCoordinate key = en.getKey();
                HexSpatial value = en.getValue();
                
                value.setMaterial(ModelSupplier.getBoardMaterial());
            }
            
        } else {    //!selected
            //Select what we have here
            selection = hex;
            hex.setMaterial(ModelSupplier.getColoredMaterial(ColorRGBA.Green));
            
            selected = true;
            
            
            ArrayList<HexCoordinate> list = game.getCloneTiles(hex.getCoord());
            Iterator<HexCoordinate> it = list.iterator();
            while (it.hasNext())
            {
                HexCoordinate c = it.next();
                LOG.log(Level.FINE,c.toString());
                HexSpatial spatial = grid.grid.get(c);
                if (spatial != null)
                {
                    spatial.setMaterial(ModelSupplier.getColoredMaterial(ColorRGBA.White));
                }
            }
            
            list = game.getMoveTiles(hex.getCoord());
            it = list.iterator();
            while (it.hasNext())
            {
                HexCoordinate c = it.next();
                LOG.log(Level.FINE,c.toString());
                HexSpatial spatial = grid.grid.get(c);
                if (spatial != null)
                {
                    spatial.setMaterial(ModelSupplier.getColoredMaterial(ColorRGBA.Gray));
                }
            }
            
            LOG.log(Level.INFO, "HexCoord(!selected): {0}", hex.getCoord());
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
