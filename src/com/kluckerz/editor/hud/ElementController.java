package com.kluckerz.editor.hud;

import com.kluckerz.editor.KeyboardControl;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class ElementController extends AbstractController {
    
    public void triggerTurnYCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Y_CW);
    }
    
    public void triggerTurnYCCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Y_CCW);
    }
    
    public void triggerTurnXCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_X_CW);
    }
    
    public void triggerTurnXCCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_X_CCW);
    }
    
    public void triggerTurnZCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Z_CW);
    }
    
    public void triggerTurnZCCW() {
        triggerEvent(KeyboardControl.TURN_ELEMENT_Z_CCW);
    }
    
    public void triggerInsertElement() {
        triggerEvent(KeyboardControl.INSERT_ELEMENT);
    }
    
}
