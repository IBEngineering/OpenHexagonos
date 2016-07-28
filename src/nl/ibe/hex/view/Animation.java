/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import nl.ibe.hex.game.HexChange;
import nl.ibe.hex.view.update.UpdateListener;
import nl.ibe.hex.view.update.UpdateProvider;

/**
 *
 * @author MisterCavespider
 */
public class Animation implements UpdateListener {
    
    private HexChange change;
    private Vector3f start, end, mov;
    private Spatial spatial;
    private long StartTime;
    private AnimationQeue aq;

    public Animation(HexChange change, Vector3f start, Vector3f end, Spatial s) {
        this.change = change;
        this.start = start;
        this.end = end;
        this.mov = end.subtract(start);
        this.spatial = s;
    }

    public HexChange getChange() {
        return change;
    }

    public Vector3f getStart() {
        return start;
    }

    public Vector3f getEnd() {
        return end;
    }

    public Vector3f getMov() {
        return mov;
    }
    
    public void launch() {
        UpdateProvider.qeueAdd(this);
        StartTime = System.currentTimeMillis();
    }
    
    public void launch(AnimationQeue aq) {
        launch();
        this.aq = aq;
    }

    @Override
    public void provUpdate(float tpf) {
        if(System.currentTimeMillis() - StartTime < 1000) {
            spatial.move(mov.mult(tpf));
        } else {
            aq.onEndAnim(this);
//            UpdateProvider.qeueRemove(this);
        }
        
    }
    
    
    
}
