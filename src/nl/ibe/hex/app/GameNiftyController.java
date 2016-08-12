/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.app;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.controls.label.LabelControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.IHexGame;
import nl.ibe.hex.game.IHexGameListener;
import nl.ibe.hex.supply.ColorSupplier;
import nl.ibe.hex.view.update.TickerListener;
import nl.ibe.hex.view.update.UpdateListener;
import nl.ibe.hex.view.update.UpdateProvider;
import nl.ibe.hex.view.update.UpdateTickListener;
import nl.ibe.hex.view.update.UpdateTicker;

/**
 *
 * @author MisterCavespider
 */
@TickerListener(ticker = "nifty")
public class GameNiftyController implements ScreenController, UpdateTickListener {
    
    private Nifty nifty;
    private SimpleApplication app;
    private IHexGame game;
    private IHexGameListener view;
    private HexPlayer winner;

    public GameNiftyController(Nifty nifty, SimpleApplication app, IHexGame game, IHexGameListener view) {
        this.nifty = nifty;
        this.app = app;
        this.game = game;
        this.view = view;
        
        UpdateTicker ticker = new UpdateTicker("niftyTicker", 30, System.currentTimeMillis());
        UpdateProvider.tickers.put("niftyTicker", ticker);
        ticker.addListener(this);
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
    
    public void sync() {
        
        if(nifty.getCurrentScreen().getScreenId().equals("hud")) {
            Label test;
            
            Label score0 = nifty.getCurrentScreen().findNiftyControl("c_score0", Label.class);
            score0.setText(getPlayerInfo("c_score0"));

            Label score1 = nifty.getCurrentScreen().findNiftyControl("c_score1", Label.class);
            score1.setText(getPlayerInfo("c_score1"));
            
            Label currPl = nifty.getCurrentScreen().findNiftyControl("c_playerCurrent", Label.class);
            currPl.setText(getPlayerInfo("current"));
        }
    }
    
    public String getPlayerInfo(String type) {
        switch (type) {
            case "current":
                return "Current Player: " + getCurrentPlayer();
            case "c_score0":
                return game.getPlayer(0).getName() + "'s Score: " + getScore("0");
            case "c_score1":
                return game.getPlayer(1).getName() + "'s Score: " + getScore("1");
            default:
                return "No string found";
        }
    }
    
    public String getScore(String playerIndex) {
        HexPlayer player = game.getPlayer(Integer.parseInt(playerIndex));
        String score = player.getPoints() + "";
        
        return score;
    }
    
    public String getCurrentPlayer() {
        HexPlayer player = game.getCurrentPlayer();
        String name = player.getName();
        
        return name;
    }

    public String getColor(String colorId) {
        
        switch(colorId) {
            case "score":
                
                ColorRGBA colour = ColorSupplier.getPlayerColor(winner.getTeam());
                
                colour.a = 0.6f;
                
                return ColorSupplier.niftyFormat(colour);
            default:
                return ColorSupplier.niftyFormat(ColorRGBA.Gray);
        }
        
    }
    
    @Override
    public void tickUpdate(double hertz) {
        sync();
    }

    public void gameEnd(HexPlayer winner) {
        this.winner = winner;
        
        Element popupInstance = nifty.createPopup("scoreScreen");
        
        nifty.showPopup(nifty.getCurrentScreen(), popupInstance.getId(), popupInstance);
        
        Label title = nifty.findPopupByName(popupInstance.getId()).findNiftyControl("label_winner", Label.class);
        title.setText(winner.getName() + " won!");
        
        Label score = nifty.findPopupByName(popupInstance.getId()).findNiftyControl("label_scores", Label.class);
        score.setText(game.getPlayer(0).getPoints() + ":" + game.getPlayer(1).getPoints());
    }
    
}
