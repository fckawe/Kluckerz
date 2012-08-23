package com.kluckerz.editor.hud;

import com.kluckerz.editor.KeyboardControl;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class ElementController extends AbstractController {
    
    public void triggerCCLeft() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Y_CCW);
    }
    
    public void triggerCCRight() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Y_CW);
    }
    
    public void triggerCCBack() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Z_CCW);
    }
    
    public void triggerCCFore() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Z_CW);
    }
    
    public void triggerCCUp() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_X_CCW);
    }
    
    public void triggerCCDown() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_X_CW);
    }
    
    public void triggerInsertElement() {
        triggerEvent(KeyboardControl.INSERT_ELEMENT);
    }
    
}
