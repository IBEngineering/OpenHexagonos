/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import nl.ibe.hex.game.HexOneStepAiPlayer;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.HexRandomAiPlayer;
import nl.ibe.hex.game.HexTwoStepAiPLayer;
import nl.ibe.hex.supply.ModelSupplier;
import nl.ibe.hex.util.Random;
import nl.ibe.hex.view.update.UpdateListener;
import nl.ibe.hex.view.update.UpdateProvider;
import nl.ibe.hex.view.update.UpdateTickHandler;
import nl.ibe.hex.view.update.UpdateTicker;

/**
 * 
 * @author MisterCavespider
 */
public class StartState extends AbstractAppState implements ScreenController, UpdateListener {
    
    private SimpleApplication app;
    private Nifty nifty;
    private String currentSelectedRadio;
    private NiftyJmeDisplay fakeNifty;
    private Spatial thing = ModelSupplier.getRandomHexagon(60);
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        
        //Create the nifty
        fakeNifty = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = fakeNifty.getNifty();
        nifty.fromXml("Interface/StartNifty.xml", "start", this);
        app.getGuiViewPort().addProcessor(fakeNifty);
        
        this.app.getGuiNode().attachChild(thing);
        UpdateProvider.listeners.add(this);
//        thing.setLocalTranslation(300, 300, -1000);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        
        app.getGuiViewPort().removeProcessor(fakeNifty);
        
        app.getGuiNode().detachChild(thing);
    }
    
    
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        
    }

    @Override
    public void onStartScreen() {
        
    }

    @Override
    public void onEndScreen() {
        
    }
    
    
    public void gotoScreen(String screen) {
        nifty.gotoScreen(screen);
    }
    
    public String getSplashText() {
        return Random.chooseRandomText("assets/Interface/Text/Splashtext.txt");
    }
    
    public void onNiftyClick(String s) {
        
        if(s.equals("lastToGame")) {
            
            HexPlayer.Type type = null;
            HexPlayer.Type oppType = null;
            
            if(currentSelectedRadio.equals("cell")) {
                type = HexPlayer.Type.CELL;
                oppType = HexPlayer.Type.BACTERIA;
            } else if(currentSelectedRadio.equals("bac")) {
                type = HexPlayer.Type.BACTERIA;
                oppType = HexPlayer.Type.CELL;
            }
            
            TextField textField = nifty.getCurrentScreen().findNiftyControl("namingTextField", TextField.class);
            String name = textField.getDisplayedText();
            
//            HexPlayer human = new HexPlayer(name, type);
            HexPlayer human = new HexOneStepAiPlayer(name, type);
            HexPlayer ai = new HexTwoStepAiPLayer("smart ai", oppType);
//            HexPlayer ai = new HexOneStepAiPlayer("smart ai", oppType);
            Main m = (Main) app;
            m.startGame(human, ai);
        }
        
    }
    
    @NiftyEventSubscriber(id="PlayerButtons1")
    public void onRadioGroup1Changed(final String id, final RadioButtonGroupStateChangedEvent event) {
        System.out.println("RadioButton [" + event.getSelectedId() + "] is now selected");
        
        currentSelectedRadio = event.getSelectedId();
    }

    @Override
    public void provUpdate(float tpf) {
        
        thing.rotate(2*tpf, 3*tpf, 1.5f*tpf);
        
    }
    
}
