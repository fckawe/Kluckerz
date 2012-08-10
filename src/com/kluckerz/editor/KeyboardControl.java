package com.kluckerz.editor;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

public enum KeyboardControl {

    /** The key to move the cursor up. */
    CURSOR_UP(KeyInput.KEY_R),

    /** The key to move the cursor down. */
    CURSOR_DOWN(KeyInput.KEY_F),

    /** The key to move the cursor northward. */
    CURSOR_NORTH(KeyInput.KEY_W),

    /** The key to move the cursor southward. */
    CURSOR_SOUTH(KeyInput.KEY_S),

    /** The key to move the cursor westward. */
    CURSOR_WEST(KeyInput.KEY_A),

    /** The key to move the cursor eastward. */
    CURSOR_EAST(KeyInput.KEY_D),
    
    /** The key to insert an object/element. */
    INSERT_OBJECT(KeyInput.KEY_SPACE),

    /** The key to turn the camera clockwise. */
    TURN_CAM_CLOCKWISE(KeyInput.KEY_NUMPAD4),

    /** The key to turn the camera counter-clockwise. */
    TURN_CAM_COUNTERCLOCKWISE(KeyInput.KEY_NUMPAD6),

    /** The key to zoom the camera in. */
    ZOOM_CAM_IN(KeyInput.KEY_NUMPAD8),

    /** The key to zoom the camera out. */
    ZOOM_CAM_OUT(KeyInput.KEY_NUMPAD2),

    /** The key to activate the camera top view. */
    CAM_TOP_VIEW_ON(KeyInput.KEY_PGUP),

    /** The key to deactivate the camera top view. */
    CAM_TOP_VIEW_OFF(KeyInput.KEY_PGDN);
    
    private KeyTrigger trigger;
        
    private int key;

    /**
     * Creates a new keyboard control value.
     * @param key The key code.
     */
    KeyboardControl(final int key) {
        this.key = key;
        trigger = new KeyTrigger(key);
    }

    /**
     * Returns the key code for this key.
     * @return The key code for this key.
    public int getKey() {
        return key;
    }

    /**
     * Returns the KeyTrigger for this key.
     * @return The KeyTrigger for this key.
     */
    public KeyTrigger getTrigger() {
        return trigger;
    }
        
    /**
     * Finds the value corresponding to the given id.
     * @param id The string representation of the searched value.
     * @return The enum value corresponding to the given id.
     */
    public static KeyboardControl fromId(final String id) {
        for(KeyboardControl c : values()) {
            if(c.toString().equals(id)) {
                return c;
            }
        }
        return null;
    }

}

