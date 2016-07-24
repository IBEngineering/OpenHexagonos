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
        game.register(view);
        view.construct(p1, p2);
    }
    
    
    
    
    
}
