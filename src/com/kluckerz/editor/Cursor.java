package com.kluckerz.editor;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Cursor {
    
    private SimpleApplication app;
    
    private Node node;
    
    private float sizeX;
    
    private float sizeY;
    
    public Cursor(final SimpleApplication app) {
        this.app = app;
        node = createNode();
        
        BoundingBox bb = getBoundingBox(node);
        sizeX = bb == null ? 0 : bb.getXExtent();
        sizeY = bb == null ? 0 : bb.getYExtent();
    }
    
    private Node createNode() {
        return (Node)app.getAssetManager().loadModel(
                "Models/BlockSimple/BlockSimple.j3o");
    }
    
    private BoundingBox getBoundingBox(final Node node) {
        BoundingVolume vol = node.getWorldBound();
        if(vol instanceof BoundingBox) {
            return (BoundingBox)vol;
        }
        return null;
    }
    
    public Node getNode() {
        return node;
    }
    
    public void moveUp() {
        Vector3f pos = node.getLocalTranslation();
        float stepY = sizeY * 2;
        Vector3f newPos = pos.add(0, stepY, 0);
        node.setLocalTranslation(newPos);
    }
    
    public void moveDown() {
        Vector3f pos = node.getLocalTranslation();
        float stepY = sizeY * -2;
        Vector3f newPos = pos.add(0, stepY, 0);
        node.setLocalTranslation(newPos);
    }
    
    public void moveLeft() {
        Vector3f pos = node.getLocalTranslation();
        float stepX = sizeX * -2;
        Vector3f newPos = pos.add(stepX, 0, 0);
        node.setLocalTranslation(newPos);
    }
    
    public void moveRight() {
        Vector3f pos = node.getLocalTranslation();
        float stepX = sizeX * 2;
        Vector3f newPos = pos.add(stepX, 0, 0);
        node.setLocalTranslation(newPos);
    }
    
}
