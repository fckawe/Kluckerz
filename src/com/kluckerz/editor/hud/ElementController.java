package com.kluckerz.editor.hud;

import com.kluckerz.editor.KeyboardControl;

/**
 * Controller class for the HUD element controls.
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
    
    public void triggerInsertElement(final String className) {
        if(className == null) {
            return;
        }
        
        String actionCode = KeyboardControl.INSERT_ELEMENT.toString();
        actionCode += ":"; // TODO: Konstante
        actionCode += className;
        
        triggerEvent(actionCode);
    }
    
}
