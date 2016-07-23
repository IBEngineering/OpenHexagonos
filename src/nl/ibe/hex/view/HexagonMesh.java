/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 * A mesh for hexagons.
 * 
 * WARNING: THERE IS NO TEXTURE SUPPORT YET.
 * 
 * @author MisterCavespider
 */
public class HexagonMesh extends Mesh {
    
    protected float radius;
    
    /**
     * Standard constructor.
     * 
     * The mesh actually creates 7 corners, as the center 
     * should also be accounted.
     * 
     * @param radius    The radius of the hexagon
     */
    public HexagonMesh(float radius) {
        this.radius = radius;
        
        setAllBuffers();
        
        updateBound();
    } 
    
    /**
     * Sets the Position, TexCoord and Index buffers.
     */
    protected final void setAllBuffers() {
        Vector3f[] corners = generateCorners();
        Vector2f[] texCoords = generateTexCoords();
        int[] indeces = generateIndeces();
        
        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(corners));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indeces));
    }
    
    /**
     * Simply loops the loopHexagonVectors().
     * 
     * top-pointing.
     * 
     * @return  An array with Vector3f.
     */
    protected Vector3f[] generateCorners() {
        Vector3f[] corners = new Vector3f[7];
        
        //A loop to get the corners.
        int i = 0;
        while(i<6) {
            Vector3f vec = loopHexagonVectors(i);
            corners[i] = vec;
            i++;
        }
        
        corners[6] = Vector3f.ZERO;
        return corners;
    }
    
    /**
     * Gets the corners of a hexagon from a square.
     * 
     * Not even begun.
     * 
     * @return  An array with Vector2f.
     */
    protected Vector2f[] generateTexCoords() {
        Vector2f[] texCoords = new Vector2f[7];
        
        //A loop to get the texture
        int j = 0;
        while(j<7) {
            texCoords[j] = new Vector2f(j*(1/7), j*(1/7));
            j++;
        }
        return texCoords;
    }
    
    /**
     * Makes the indeces (triangles).
     * 
     * The corners are made 0-5, a-f. The center is 6.
     * It makes it a triangle fan.
     * 
     * @return
     */
    protected int[] generateIndeces() {
        //Set the indeces, without loop
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int e = 4;
        int f = 5;
        int o = 6;        //Mid
        
        int[] indeces = {
            b,a,o,
            c,b,o,
            d,c,o,
            e,d,o,
            f,e,o,
            a,f,o
        };
        return indeces;
    }
    
    /**
     * Gets one of the vectors.
     * 
     * top-pointing.
     * 
     * First, it calculates the angle in degrees.
     * Then, it converts to radians. It uses the radians
     * in the formulas.
     * 
     * x = size * cos(angle).
     * y - size * sin(angle).
     * 
     * @param i How far in the loop are we?
     * @return  One vector for in the corners.
     */
    protected Vector3f loopHexagonVectors(int i) {
        float angle_deg = 60 * i + 30;      //For top-pointing
        float angle_rad = (float) (Math.PI / 180 * angle_deg);
        
        float x = (float) (this.radius * Math.cos(angle_rad));
        float z = (float) (this.radius * Math.sin(angle_rad));
        Vector3f vec = new Vector3f(x, 0, z);
        
        return vec;
    }
    
}
