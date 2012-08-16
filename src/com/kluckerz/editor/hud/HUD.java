package com.kluckerz.editor.hud;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.kluckerz.Main;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class HUD implements ScreenController {
    
    private Nifty nifty;
    
    private ActionListener actionListener;
    
    public HUD() {
    }
    
    public void init(final Main app, final ActionListener actionListener) {
        this.actionListener = actionListener;
        
        AssetManager assetManager = app.getAssetManager();
        InputManager inputManager = app.getInputManager();
        AudioRenderer audioRenderer = app.getAudioRenderer();
        ViewPort guiViewPort = app.getGuiViewPort();
        
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        
        nifty.fromXml("/Interface/HUD/HUD.xml", "start", this);
        
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    @Override
    public void bind(final Nifty nifty, final Screen screen) {
        linkControllerForElement(screen, "cam-cc");
        linkControllerForElement(screen, "cursor-cc");
    }
    
    protected void linkControllerForElement(final Screen screen,
            final String elementId) {
        Element e = screen.findElementByName(elementId);
        Controller c = e.getControl(Controller.class);
        if(c instanceof AbstractController) {
            ((AbstractController)c).initialize(actionListener);
        }
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
    
}
