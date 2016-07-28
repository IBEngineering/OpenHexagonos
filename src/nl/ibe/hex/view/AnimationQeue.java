/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import java.util.ArrayList;

/**
 *
 * @author MisterCavespider
 */
public class AnimationQeue {
    
    boolean going;
    
    int counter = 0;
    
    ArrayList<Animation> qeue;
    
    private final View v;

    public AnimationQeue(View v) {
        this.v = v;
        qeue = new ArrayList<>();
    }
    
    public void add(Animation a) {
        qeue.add(a);
    }
    
    public void launch() {
        if(qeue.size() > 0) {
            qeue.get(counter).launch(this);
        }
    }
    
    public void onEndAnim(Animation a) {
        counter++;
        if(counter >= qeue.size()) {
            onDead();
            return;
        }
        launch();
    }

    private void onDead() {
        v.onEndAnimQeue();
    }
    
}
