package com.kluckerz.editor.hud;

import com.kluckerz.editor.KeyboardControl;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class CamController extends AbstractController {
    
    public void triggerCCLeft() {
        triggerEvent(KeyboardControl.TURN_CAM_CLOCKWISE);
    }
    
    public void triggerCCRight() {
        triggerEvent(KeyboardControl.TURN_CAM_COUNTERCLOCKWISE);
    }
    
    public void triggerCCBack() {
        triggerEvent(KeyboardControl.ZOOM_CAM_IN);
    }
    
    public void triggerCCFore() {
        triggerEvent(KeyboardControl.ZOOM_CAM_OUT);
    }
    
    public void triggerCCUp() {
        triggerEvent(KeyboardControl.CAM_TOP_VIEW_ON);
    }
    
    public void triggerCCDown() {
        triggerEvent(KeyboardControl.CAM_TOP_VIEW_OFF);
    }

}
