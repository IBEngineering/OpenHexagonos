/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextFormatter;

/**
 * Just a helper class.
 * 
 * It has extensive random functions.
 * 
 * DISCLAIMER: It does not randomly generate numbers,
 * it only uses Math.random() in a smart way.
 * 
 * @author MisterCavespider
 */
public class Random {
    
    public static final Logger LOG = Logger.getLogger(Random.class.getName());
    
    /**
     * Generates a random number between min and max.
     * 
     * @param min   The minimum value.
     * @param max   The maximum value.
     * @return      The random value.
     */
    public static double rdmD(double min, double max) {
        double delta = max - min;
        java.util.Random r = new java.util.Random();
        double rand = r.nextDouble();
        
        double randomNr = min + rand*delta;
        return randomNr;
    }
    
    /**
     * Calls and casts the other rdmD().
     */
    public static float rdmF(float min, float max) {
        return (float) rdmD(min, max);
    }
    
    /**
     * Calls and casts the other rdm().
     */
    public static int rdmI(int min, int max) {
        return (int) rdmD((double) min, (double) max);
    }
    
    
    public static float approximate(float original, float distort) {
        float min = original - distort/2.0f;
        float max = original + distort/2.0f;
        
        float returner = rdmF(min, max);
        return returner;
    }
    
    public static String chooseRandomText(String pathToFile) {
        if(!pathToFile.endsWith(".txt")) {
            LOG.log(Level.WARNING, "Trying to get text from a non-text file," + "/n" +
                    "suspicious...");
        }
        
        List<String> list = null;
        try {
            list = TextReader.readFile(pathToFile);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        int size = list.size();
        int index = rdmI(0, size);
        
        return list.get(index);
    }
}
