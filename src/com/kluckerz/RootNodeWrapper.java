package com.kluckerz;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.kluckerz.lmnts.AbstractElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for the default root node that has special handles for elements.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class RootNodeWrapper {
    
    private Node node;
    
    private final List<AbstractElement> lmnts;
    
    private final List<AbstractElement> animatingLmnts;
    
    /**
     * Creates a new root node wrapper.
     * @param rootNode The default root node to wrap.
     */
    public RootNodeWrapper(final Node rootNode) {
        node = rootNode;
        lmnts = new ArrayList<AbstractElement>();
        animatingLmnts = new ArrayList<AbstractElement>();
    }
    
    /**
     * Returns the default root node which is wrapped by this wrapper.
     * @return The default root node.
     */
    public Node getNode() {
        return node;
    }
    
    /**
     * Attaches a child to this root node.
     * @param spatial The child to attach.
     */
    public void attachChild(final Spatial spatial) {
        this.node.attachChild(spatial);
    }
    
    /**
     * Attaches an element child to this root node.
     * @param lmnt The element to attach as child.
     */
    public void attachChild(final AbstractElement lmnt) {
        lmnts.add(lmnt);
        this.node.attachChild(lmnt.getNode());
    }
    
    /**
     * Adds a light to the wrapped root node.
     * @param light The light to add.
     */
    public void addLight(final Light light) {
        this.node.addLight(light);
    }
    
    /**
     * Adds a pending element animation (rotation).
     * @param lmnt The element that has a pending animation.
     */
    public void addAnimatingElement(final AbstractElement lmnt) {
        animatingLmnts.add(lmnt);
    }
    
    /**
     * Returns a list of all child elements.
     * @return A list that contains all child elements.
     */
    public List<AbstractElement> getElements() {
        return lmnts;
    }
    
    /**
     * Updates the root node wrapper (usually called per frame).
     */
    public void update() {
        updateAnimatingElements();
    }
    
    private void updateAnimatingElements() {
        List<AbstractElement> finished = new ArrayList<AbstractElement>();
        for(AbstractElement lmnt : animatingLmnts) {
            lmnt.updateAnimation();
            if(lmnt.isAnimationFinished()) {
                finished.add(lmnt);
            }
        }
        if(!finished.isEmpty()) {
            animatingLmnts.removeAll(finished);
        }
    }
    
}
