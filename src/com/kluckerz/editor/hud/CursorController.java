package com.kluckerz.editor.hud;

import com.kluckerz.editor.KeyboardControl;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class CursorController extends AbstractController {

    public void triggerCCLeft() {
        triggerEvent(KeyboardControl.CURSOR_WEST);
    }
    
    public void triggerCCRight() {
        triggerEvent(KeyboardControl.CURSOR_EAST);
    }
    
    public void triggerCCBack() {
        triggerEvent(KeyboardControl.CURSOR_NORTH);
    }
    
    public void triggerCCFore() {
        triggerEvent(KeyboardControl.CURSOR_SOUTH);
    }
    
    public void triggerCCUp() {
        triggerEvent(KeyboardControl.CURSOR_UP);
    }
    
    public void triggerCCDown() {
        triggerEvent(KeyboardControl.CURSOR_DOWN);
    }
    
}
