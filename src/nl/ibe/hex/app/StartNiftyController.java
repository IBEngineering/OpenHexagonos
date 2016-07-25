/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.app;

import com.jme3.app.SimpleApplication;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.RadioButtonGroupStateChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;
import nl.ibe.hex.game.HexOneStepAiPlayer;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.supply.ColorSupplier;
import nl.ibe.hex.game.HexTwoStepAiPLayer;
import nl.ibe.hex.supply.NiftySupplier;
import nl.ibe.hex.util.Random;

/**
 *
 * @author MisterCavespider
 */
public class StartNiftyController implements ScreenController, RawInputListener {
    
    private SimpleApplication app;
    
    private Nifty nifty;
    private Object currentSelectedRadio;
    
    
    public StartNiftyController(SimpleApplication app, Nifty nifty) {
        this.app = app;
        this.nifty = nifty;
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
    
    public String getText(String type) {
        
        if(type.equals("splash")) {
            
            return Random.chooseRandomText("assets/Interface/Text/Splashtext.txt");
        } else 
        if(type.equals("title")) {
            
            return "openHex";
        } else {
            
            return "Error 0: no text found";
        }
        
    }
    
    public String getColor(String id) {
        return ColorSupplier.getNiftyBackground(id);
    }
    
    public String getSize(String id) {
        return "20%";
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
            
            HexPlayer human = new HexRandomAiPlayer(name, type);
            HexPlayer ai = new HexOneStepAiPlayer("smart ai", oppType);
            
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
    public void beginInput() {
        
    }

    @Override
    public void endInput() {
        
    }

    @Override
    public void onJoyAxisEvent(JoyAxisEvent evt) {
        
    }

    @Override
    public void onJoyButtonEvent(JoyButtonEvent evt) {
        
    }

    @Override
    public void onMouseMotionEvent(MouseMotionEvent evt) {
        
    }

    @Override
    public void onMouseButtonEvent(MouseButtonEvent evt) {
        //Some key has been pressed
        
        System.out.println("key!");
        
        if(nifty.getCurrentScreen().getScreenId().equals("start")) {
            //Any key has been pressed!
            //Goto following screen
            
            gotoScreen("firstMenu");
        }
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        //Some key has been pressed
        
        System.out.println("key!");
        
        if(nifty.getCurrentScreen().getScreenId().equals("start")) {
            //Any key has been pressed!
            //Goto following screen
            
            gotoScreen("firstMenu");
        }
    }

    @Override
    public void onTouchEvent(TouchEvent evt) {
        
    }
    
}
