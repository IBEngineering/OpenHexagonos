/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 *
 * @author Mihaita
 */
public class HexagonMesh extends Mesh {
    
    protected float size;
    
    public HexagonMesh(float size) {
        this.size = size;
        
        setAllBuffers();
        updateBound();
        
        // setMode(Mode.LineLoop);
    } 
    
    protected final void setAllBuffers() {
        Vector3f[] corners = generateCorners();
        Vector2f[] texCoords = generateTexCoords();
        int[] indeces = generateIndeces();
        
        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(corners));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
        setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indeces));
    }
    
    protected Vector3f[] generateCorners() {
        Vector3f[] corners = new Vector3f[6];
        
        //A loop to get the corners.
        int i = 0;
        while(i<6) {
            Vector3f vec = loopHexagonVectors(i);
            corners[i] = vec;
            i++;
        }
        return corners;
    }
    
    protected Vector2f[] generateTexCoords() {
        Vector2f[] texCoords = new Vector2f[6];
        
        //A loop to get the texture
        int j = 0;
        while(j<6) {
            texCoords[j] = new Vector2f(j*(1/6), j*(1/6));
            j++;
        }
        return texCoords;
    }
    
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
    
    protected Vector3f loopHexagonVectors(int i) {
        float angle_deg = 60 * i + 30;      //For top-pointing
        float angle_rad = (float) (Math.PI / 180 * angle_deg);
        
        float x = (float) (this.size * Math.cos(angle_rad));
        float z = (float) (this.size * Math.sin(angle_rad));
        Vector3f vec = new Vector3f(x, 0, z);
        
        return vec;
    }
    
}
