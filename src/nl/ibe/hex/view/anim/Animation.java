/*
 * Do what you want with the code.
 * (This is an open-source project).
 */
package nl.ibe.hex.view.anim;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import nl.ibe.hex.view.update.UpdateListener;
import nl.ibe.hex.view.update.UpdateProvider;

/**
 *
 * @author MisterCavespider
 */
public class Animation implements UpdateListener {
    
    public static int runningAnimations = 0;
    
    public static boolean silent() {
        return runningAnimations >= 0;
    }
    
    protected Vector3f startPos, endPos, moveVec;
    protected Spatial ghost;
    protected Node root;
    protected long startTime;
    
    protected Animation(Vector3f startPos, Vector3f endPos, Vector3f moveVec, Node root, Spatial ghost) {
        
        this.startPos = startPos;
        this.endPos = endPos;
        this.moveVec = moveVec;
        this.ghost = ghost;
        this.root = root;
        
    }
    
    public void go() {
        UpdateProvider.qeueAdd(this);
        runningAnimations++;
        root.attachChild(ghost);
        startTime = System.currentTimeMillis();
        
        ghost.setLocalTranslation(startPos);
        ghost.move(0, 10, 0);
    }

    @Override
    public void provUpdate(float tpf) {
        long deltaT = System.currentTimeMillis() - startTime;
        if(deltaT < 10000) {
            ghost.move(moveVec.mult(0.1f));
        } else {
            stop();
        }
        
    }
    
    private void stop() {
        UpdateProvider.qeueRemove(this);
        runningAnimations--;
        root.detachChild(ghost);
    }
    
    public void emergencyRequestedStop(int status) {
        
        switch (status) {
            default: {
                if(System.currentTimeMillis() - startTime > 4000) {
                    stop();
                }
            }
            case 1: stop();
        }
        
    }
}
