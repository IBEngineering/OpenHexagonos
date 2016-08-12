/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.elements.Element;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;
import nl.ibe.hex.app.Clicker;
import nl.ibe.hex.app.GameNiftyController;
import nl.ibe.hex.app.GameState;
import nl.ibe.hex.game.*;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.supply.*;
import nl.ibe.hex.view.anim.Animation;
import nl.ibe.hex.view.anim.AnimationFactory;
import nl.ibe.hex.view.update.TickerListener;

/**
 * This contains everything for the scene.
 * 
 * It has a grid, a game, an app, a clicker,
 * a logger, and more.
 * 
 * @author MisterCavespider
 */
public class View implements IHexGameListener {
    
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
    private GameNiftyController niftyGuy;
    
    //Should there be clicked?
    public boolean openForInput = true;
    private boolean currCanItMove;
    
    /**
     * Standard constructor.
     * 
     * Doesn't do much, only starts the SupplyRouter.
     * The view won't work without constructing it first.
     * You can construct it through another method.
     * 
     * @param app   The application to give to the SupplyRouter.
     */
    public View(SimpleApplication app) {
        this.app = app;
        
        //Start off the ModelSupplier by giving it the app
        SupplyRouter.startRouting(app, new SupplyRouter.SupplySettings(true));
    }
    
    /**
     * This will start constructing everything.
     * Register the view to the HexGame before you call this method.
     * 
     * It will populate the ViewGrid using the board from 
     * the game. It will also create and register a clicker.
     */
    public void construct(HexPlayer p1, HexPlayer p2, GameNiftyController niftyGuy) {
        this.niftyGuy = niftyGuy;
        
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
            
            ModelSupplier.announcement(app.getRootNode(), game.getCurrentPlayer().getTeam());
        }
        
        //Load the starters.
        ArrayList<HexCoordinate> p1POS = game.getBoard().getPlayerPositions(p1);
        for (HexCoordinate hexCoordinate : p1POS) {
            grid.getGrid().get(hexCoordinate).setOwner(p1.getTeam());
        }
        ArrayList<HexCoordinate> p2POS = game.getBoard().getPlayerPositions(p2);
        for (HexCoordinate hexCoordinate : p2POS) {
            grid.getGrid().get(hexCoordinate).setOwner(p2.getTeam());
        }
    }
    
    /**
     * Shows the changes in the scene graph.
     * @param hexagons  A list with all the changes.
     */
    @Override
    public void tilesChanged(ArrayList<HexChange> hexagons) {
        //The tiles have changed owner
        //Loop over them and change the blobs
        
        niftyGuy.sync();
        
        for (HexChange change : hexagons) {
            
            
            System.out.println(Animation.silent());
            AnimationFactory.buildAnimation(change, this, true);
            System.out.println(Animation.silent());
            
            /*
            while(!Animation.silent())
            {
                System.out.println(Animation.silent());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
            
            switch (change.getType()) {
                case DUPLICATION: {
                    //A certain blob has to create another blob.
                    //The second blob should move to the correct position.

                    HexCoordinate c = change.getEnd().getCoordinate();
                    grid.getGrid().get(c).setOwner(change.getStart().getOwner().getTeam());
                    break;
                }
                case JUMP: {
                    //A certain blob has to jump (through the air)
                    //To the end position.
                    
                    
                    Team jumper = change.getEnd().getOwner().getTeam();
                    
                    HexCoordinate s = change.getStart().getCoordinate();
                    HexCoordinate e = change.getEnd().getCoordinate();
                    
                    ///test
                    grid.getGrid().get(s).setOwner(null);
                    grid.getGrid().get(e).setOwner(jumper);
                    
                    break;
                }
                case CONQUEST: {
                    //Some blob has been conquered by another blob.
                    
                    Team conqueror = change.getStart().getOwner().getTeam();
                    System.out.println(conqueror);
                    
                    HexSpatial s = grid.getGrid().get(change.getEnd().getCoordinate());
                    System.out.println("spatial" + s.getOwnerTeam());
                    
                    s.setOwner(conqueror);
                    
                    break;
                }   //End of case
                    
            }   //End of switch
            
            Vector3f start = CoordinateConverter.toVector(change.getStart().getCoordinate());
            Vector3f end = CoordinateConverter.toVector(change.getEnd().getCoordinate());
            
            HexSpatial s = grid.getGrid().get(change.getStart().getCoordinate());
            Spatial sp = s.getSpatial();
            

        }
        
    }

    
    /**
     * Updates the player.
     * @param player    The current player
     */
    @Override
    public void playerChanged(HexPlayer player) {
        //Info for whom's turn it is
        this.player = player;
    }

    /**
     * Tells the View what it's attached to.
     * This way it can call game-specific methods.
     * 
     * @see IHexGame
     * @param game  The HexGame this is registered to.
     */
    @Override
    public void registeredAt(IHexGame game) {
        this.game = game;
    }

    /**
     * The clicker calls this method.
     * 
     * The clicker first sorts the results of a click,
     * only HexSpatials get in.
     * 
     * This methods looks wether something is selected or not,
     * and wether to send a move to the game.
     * 
     * @see HexSpatial
     * @see Clicker
     * @param hex   The HexSpatial clicked.
     */
    public void onClick(HexSpatial hex) {
        

        if(!Animation.silent()) {
            return;
        } 
        
        ModelSupplier.changePlayer(app.getRootNode(), game.getCurrentPlayer().getTeam());
        
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
            
            //Wait for the animator
           if(game.move(move)) {
                
                game.nextTurn();
           }
            
            LOG.log(Level.INFO, "Can it move? {0}", currCanItMove);
            selected = false;
            
            //Reset the colors
            for (Map.Entry<HexCoordinate, HexSpatial> en : grid.grid.entrySet()) {
                HexCoordinate key = en.getKey();
                HexSpatial value = en.getValue();
                
                value.setMaterial(MaterialSupplier.getColoredMaterial(ColorSupplier.getBoardColor()));
            }
            
            
        } else {    //!selected
            //Select what we have here
            selection = hex;
            hex.setMaterial(MaterialSupplier.getColoredMaterial(ColorRGBA.Green));
            
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
                    spatial.setMaterial(MaterialSupplier.getColoredMaterial(ColorRGBA.White));
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
                    spatial.setMaterial(MaterialSupplier.getColoredMaterial(ColorRGBA.Gray));
                }
            }
            
            LOG.log(Level.INFO, "HexCoord(!selected): {0}", hex.getCoord());
        }
        
        
        
    }
    
    /**
     * Checks if the player isn't null.
     * 
     * If it is, it'll get the current player
     * from the game.
     * 
     * @see HexPlayer
     * @return The current HexPlayer.
     */
    private HexPlayer checkPlayer() {
        if(player != null) {
            return player;
        } else {
            return game.getCurrentPlayer();
        }
    }

    @Override
    public void gameEnd(HexPlayer winner) {
        System.out.println("-------------------------- WINNER: " + winner.getName());
        niftyGuy.gameEnd(winner);
    }
    
    public Node getRootNode() {
        return app.getRootNode();
    }
}
