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
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import nl.ibe.hex.game.HexGame;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.supply.ModelSupplier;
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
        
        //Wow!
        Spatial[][][] arr = new Spatial[4][4][4];
        
        for (int a=0;a<4;a++) {
            
            for (int b=0;b<4;b++) {
                
                for (int c=0;c<4;c++) {
                    
                    Spatial spatial = ModelSupplier.getPlayerModel(p2);
                    this.app.getRootNode().attachChild(spatial);
                    spatial.setLocalTranslation(a, b, c);
                    arr[a][b][c] = spatial;
                    
                }
                
            }
            
        }
        
        //Do the last things
        game.register(view);
        view.construct(p1, p2, gnc);
    }
}
