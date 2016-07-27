/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import nl.ibe.hex.game.HexGame;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.view.View;

/**
 *
 * @author mihaita
 */
public class GameState extends AbstractAppState {

    private SimpleApplication app;
    
    private HexPlayer p1, p2;
    
    //Nifties
    private NiftyJmeDisplay fakeNifty;
    private Nifty nifty;
    
    public GameState(HexPlayer p1, HexPlayer p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        
        HexGame game = new HexGame(p1, p2);
        View view = new View(this.app);
        
        //Init the nifty
        fakeNifty = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getViewPort());
        nifty = fakeNifty.getNifty();
        
        //Create the screencontrol and load the nifty
        GameNiftyController gnc = new GameNiftyController(nifty, this.app, game, view);
        nifty.fromXml("Interface/HUDNifty.xml", "hud", gnc);
        
        //Tell jme to see it
        app.getGuiViewPort().addProcessor(fakeNifty);
        
        
        //Do the last things
        game.register(view);
        view.construct(p1, p2, gnc);
    }
}
