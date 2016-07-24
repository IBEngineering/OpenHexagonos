package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.system.AppSettings;
import nl.ibe.hex.game.HexGame;
import nl.ibe.hex.game.HexOneStepAiPlayer;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.supply.SupplyRouter;
import nl.ibe.hex.view.View;
import nl.ibe.hex.view.update.UpdateProvider;

/**
 * test
 * @author MisterCavespider
 */
public class Main extends SimpleApplication {
    
    private static AssetManager shortcut;
    
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("OpenHex v0.0.1");
        settings.setResolution(640, 640);
        
        Main app = new Main();
        app.setSettings(settings);
        app.showSettings = false;
        app.start();
        
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        
    }
    private Clicker click;
    private StartState startState;
    private GameState gameState;

    @Override
    public void simpleInitApp() {
        shortcut = getAssetManager();
        
        flyCam.setMoveSpeed(50);
        flyCam.setDragToRotate(true);
        
        SupplyRouter.startRouting(this, new SupplyRouter.SupplySettings(true));
        
        //Create 2 appStates and attach 1.
        startState = new StartState();
        
        stateManager.attach(startState);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        UpdateProvider.update(tpf);
        
    }
    
    
    
    public void initMappings() {
        inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(click, "Pick");
    }
    
    public void startGame(HexPlayer p1, HexPlayer p2) {
        stateManager.detach(startState);
        gameState = new GameState(p1, p2);
        stateManager.attach(gameState);
    }
}