package com.kluckerz.editor.hud;

import com.jme3.input.controls.ActionListener;
import com.kluckerz.editor.KeyboardControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 * Abstract controller class for HUD controls.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public abstract class AbstractController implements Controller {
    
    private ActionListener actionListener;
  
    public void initialize(final ActionListener actionListener) {
        this.actionListener = actionListener;
    }
    
    protected void triggerEvent(final KeyboardControl kc) {
        triggerEvent(kc.toString());
    }
    
    protected void triggerEvent(final String actionCode) {
        if(actionListener != null) {
            actionListener.onAction(actionCode, true, 0f);
        }
    }
  
    @Override
    public void onStartScreen() {
    }
    
    @Override
    public void bind(final Nifty nifty, final Screen screen,
        final Element element, final Properties parameter,
        final Attributes controlDefinitionAttributes) {
    }

    @Override
    public void init(final Properties parameter,
        final Attributes controlDefinitionAttributes) {
    }

    @Override
    public void onFocus(final boolean getFocus) {
    }

    @Override
    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        return true;
    }

}
