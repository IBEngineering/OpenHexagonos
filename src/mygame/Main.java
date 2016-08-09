package mygame;

import nl.ibe.hex.app.GameState;
import nl.ibe.hex.app.StartState;
import nl.ibe.hex.app.Clicker;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.settings.SettingsHQ;
import nl.ibe.hex.supply.SupplyRouter;
import nl.ibe.hex.util.Encryptor;
import nl.ibe.hex.util.TextReader;
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
        
        SettingsHQ.readBase();
        
        try {
            String what = (TextReader.readFile("assets/Interface/Text/txt.txt")).get(0);
            String answer = Encryptor.decryptString("settings", what);
            System.out.println(answer);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private Clicker click;
    private StartState startState;
    private GameState gameState;

    @Override
    public void simpleInitApp() {
        shortcut = getAssetManager();
        
        cam.setLocation(new Vector3f(0.0f, 18f, 15.f));
        cam.setRotation(new Quaternion(0.00439f, 0.9f, -0.428f, 0.00927f));
        
        flyCam.setZoomSpeed(100);
        
        flyCam.setMoveSpeed(30);
        flyCam.setRotationSpeed(0.3f);
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