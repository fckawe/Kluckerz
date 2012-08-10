package com.kluckerz.editor;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.kluckerz.Direction;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Cursor {
    
    private SimpleApplication app;
    
    private Node node;
    
    private float sizeX;
    
    private float sizeY;

    private float sizeZ;
    
    /**
     * Creates a new editing cursor object.
     * @param app The main application.
     */
    public Cursor(final SimpleApplication app) {
        this.app = app;
        node = createNode();
        
        Node dummy = createDummySimpleElement();
        BoundingBox bb = getBoundingBox(dummy);
        sizeX = bb == null ? 0 : bb.getXExtent() * 2;
        sizeY = bb == null ? 0 : bb.getYExtent() * 2;
        sizeZ = bb == null ? 0 : bb.getZExtent() * 2;
    }
    
    private Node createNode() {
        return (Node)app.getAssetManager().loadModel(
                "Models/Cursor/Cursor.j3o");
    }
    
    private Node createDummySimpleElement() {
        return (Node)app.getAssetManager().loadModel(
                "Models/Elements/Simple.j3o");
    }
    
    private BoundingBox getBoundingBox(final Node node) {
        BoundingVolume vol = node.getWorldBound();
        if(vol instanceof BoundingBox) {
            return (BoundingBox)vol;
        }
        return null;
    }
    
    /**
     * Returns the node which corresponds to the cursor.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Moves the cursor to the given relative direction (depending on the camera view).
     * @param direction The relative direction.
     * @param camView The direction in which the camera looks.
     */
    public void moveRelative(final Direction direction, final Direction camView) {
        Direction moveDirection = direction.translateFromView(camView);
        switch(moveDirection) {
            case NORTH:
                moveNorth();
                break;
            case EAST:
                moveEast();
                break;
            case SOUTH:
                moveSouth();
                break;
            case WEST:
                moveWest();
                break;
        }
    }
    
    /**
     * Moves the cursor up by one cursor y size.
     */
    public void moveUp() {
        move(0, sizeY, 0);
    }
    
    /**
     * Moves the cursor down by one cursor y size.
     */
    public void moveDown() {
        move(0, sizeY * -1, 0);
    }

    /**
     * Moves the cursor northward by one cursor z size.
     */
    public void moveNorth() {
        move(0, 0, sizeZ * -1);
    }

    /**
     * Moves the cursor eastward by one cursor x size.
     */
    public void moveEast() {
        move(sizeX, 0, 0);
    }

    /**
     * Moves the cursor southward by one cursor z size.
     */
    public void moveSouth() {
        move(0, 0, sizeZ);
    }
    
    /**
     * Moves the cursor westward by one cursor x size.
     */
    public void moveWest() {
        move(sizeX * -1, 0, 0);
    }
    
    private void move(final float x, final float y, final float z) {
        Vector3f pos = node.getLocalTranslation();
        Vector3f newPos = pos.add(x, y, z);
        node.setLocalTranslation(newPos);
    }
    
}
