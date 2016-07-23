package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import nl.ibe.hex.game.HexGame;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.supply.ModelSupplier;
import nl.ibe.hex.view.View;

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
        
    }
    private Clicker click;

    @Override
    public void simpleInitApp() {
        shortcut = getAssetManager();
        
        flyCam.setMoveSpeed(50);
        flyCam.setDragToRotate(true);
        
        //Create the Game
        HexPlayer p1 = new HexPlayer("One", HexPlayer.Type.CELL);
        HexPlayer p2 = new HexPlayer("Two", HexPlayer.Type.BACTERIA);
        HexGame g = new HexGame(p1, p2);
        
        //Create the View
        View v = new View(this);
        g.register(v);
        v.construct(p1, p2);
    }
    
    public void initMappings() {
        inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(click, "Pick");
    }
    
    
}
