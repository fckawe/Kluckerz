package com.kluckerz.lmnts;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;

/**
 * Generic element class.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public abstract class AbstractElement {
    
    private Node node;
    
    private int rotationXChange;
    
    private int rotationYChange;
    
    private int rotationZChange;
    
    /**
     * Creates a new generic element.
     * @param assetManager The application's assetmanager, used to load models.
     */
    public AbstractElement(final AssetManager assetManager) {
        node = createNode(assetManager);
    }
    
    private Node createNode(final AssetManager assetManager) {
        String modelPath = getModelPath();
        return (Node)assetManager.loadModel(modelPath);
    }
    
    /**
     * Returns the node representing the element in the 3D scene.
     * @return The node.
     */
    public Node getNode() {
        return node;
    }
    
    /**
     * Rotates the element clockwise on x-axis.
     */
    public void rotateXClockwise() {
        rotationXChange += 90;
    }
    
    /**
     * Rotates the element counter-clockwise on x-axis.
     */
    public void rotateXCounterClockwise() {
        rotationXChange -= 90;
    }
    
    /**
     * Rotates the element clockwise on y-axis.
     */
    public void rotateYClockwise() {
        rotationYChange += 90;
    }
    
    /**
     * Rotates the element counter-clockwise on y-axis.
     */
    public void rotateYCounterClockwise() {
        rotationYChange -= 90;
    }
    
    /**
     * Rotates the element clockwise on z-axis.
     */
    public void rotateZClockwise() {
        rotationZChange += 90;
    }
    
    /**
     * Rotates the element counter-clockwise on z-axis.
     */
    public void rotateZCounterClockwise() {
        rotationZChange -= 90;
    }
    
    /**
     * Used to check, if the rotation animation has finished.
     * @return True, if no more animation is pending.
     */
    public boolean isAnimationFinished() {
        return rotationXChange == 0 && rotationYChange == 0 && rotationZChange == 0;
    }
    
    /**
     * Used to update the animation state (go forward one step).
     */
    public void updateAnimation() {
        if(rotationXChange > 0) {
            node.rotate(1 * FastMath.DEG_TO_RAD, 0, 0);
            rotationXChange -= 1;
        } else if(rotationXChange < 0) {
            node.rotate(-1 * FastMath.DEG_TO_RAD, 0, 0);
            rotationXChange += 1;
        }
        
        if(rotationYChange > 0) {
            node.rotate(0, 1 * FastMath.DEG_TO_RAD, 0);
            rotationYChange -= 1;
        } else if(rotationYChange < 0) {
            node.rotate(0, -1 * FastMath.DEG_TO_RAD, 0);
            rotationYChange += 1;
        }
        
        if(rotationZChange > 0) {
            node.rotate(0, 0, 1 * FastMath.DEG_TO_RAD);
            rotationZChange -= 1;
        } else if(rotationZChange < 0) {
            node.rotate(0, 0, -1 * FastMath.DEG_TO_RAD);
            rotationZChange += 1;
        }
    }
    
    @Override
    public String toString() {
        return getTypeName() + "[" + super.toString() + "]";
    }
    
    protected abstract String getModelPath();
    
    protected abstract String getTypeName();
    
}
