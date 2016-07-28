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
import nl.ibe.hex.game.HexRandomAiPlayer;
import nl.ibe.hex.supply.ColorSupplier;
import nl.ibe.hex.game.HexMultiStepAiPLayer;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.game.player.TeamManager;
import nl.ibe.hex.supply.ID;
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
        
        if(nifty.getCurrentScreen().getScreenId().equals("player selection")) {
            NiftySupplier.addTeams(nifty, "radioBig");
        }
        
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
            
            Team team = null;
            Team oppTeam = null;
            
            if(currentSelectedRadio.equals("radio0")) {
                team = TeamManager.get(0);
                oppTeam = TeamManager.get(1);
            } else if(currentSelectedRadio.equals("radio1")) {
                team = TeamManager.get(1);
                oppTeam = TeamManager.get(2);
            } else if(currentSelectedRadio.equals("radio2")) {
                team = TeamManager.get(2);
                oppTeam = TeamManager.get(0);
            } else {
                team = TeamManager.get(2);
                oppTeam = TeamManager.get(1);
            }
            
            TextField textField = nifty.getCurrentScreen().findNiftyControl("namingTextField", TextField.class);
            String name = "ita";
            
            HexPlayer human = new HexRandomAiPlayer(name, team);
            HexPlayer ai = new HexMultiStepAiPLayer("multi-step-ai", oppTeam);
            
            Main m = (Main) app;
            m.startGame(human, ai);
        }
        
    }
    
    @NiftyEventSubscriber(id="TeamRadio")
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
        
        System.out.println("mouse!");
        
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
