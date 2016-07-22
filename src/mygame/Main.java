package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import nl.ibe.hex.view.ViewGrid;
import nl.ibe.hex.view.HexagonMesh;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private static AssetManager shortcut;
    
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("OpenHex v0.0.1");
        
        Main app = new Main();
        app.setSettings(settings);
        app.start();
        
    }
    private Clicker click;

    @Override
    public void simpleInitApp() {
        shortcut = getAssetManager();
        
        flyCam.setMoveSpeed(50);
        flyCam.setDragToRotate(true);
        
        HexagonMesh h = new HexagonMesh(1);
        Geometry geom = new Geometry("Box", h);
        
        HexagonMesh h2 = new HexagonMesh(1);
        Geometry geom2 = new Geometry("Hexagon 2", h2);
        
        Torus t = new Torus(6, 6, 0.05f, 1f);
        Geometry cyl = new Geometry("Tor", t);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        cyl.setMaterial(mat);
        
        Material red = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        red.setColor("Color", ColorRGBA.Red);
        //red.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        geom.setMaterial(red);
        
        Material green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        green.setColor("Color", ColorRGBA.Green);
        //green.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        geom2.setMaterial(green);
        
//        rootNode.attachChild(cyl);
//        rootNode.attachChild(geom);
//        rootNode.attachChild(geom2);
        geom.setLocalTranslation(0, 0, 0);
        
        float height = 2;
        float width = (float) (Math.sqrt(3)/2 * height);
        geom2.move(width/2, 0, height * 0.75f);
        
        cyl.move(-(width/2), 0, height * 0.75f);
        
        //Create the grid
        ViewGrid grid = new ViewGrid(4, rootNode);
    }
    
    public void initMappings() {
        inputManager.addMapping("Pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(click, "Pick");
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public static Material getRandomMat() {
        Material mat =  new Material(shortcut, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        return mat;
    }
}
