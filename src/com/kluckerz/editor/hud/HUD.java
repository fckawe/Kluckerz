package com.kluckerz.editor.hud;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.kluckerz.Main;
import com.kluckerz.lmnts.AbstractElement;
import com.kluckerz.lmnts.ElementLoader;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class HUD implements ScreenController {
    
    private Nifty nifty;
    
    private ActionListener actionListener;
    
    private Element hudPanelControl;
    
    private Element hudPanelShowHide;
    
    private ResourceBundle resourceBundle;
    
    public HUD() {
    }
    
    public void init(final Main app, final ActionListener actionListener) {
        this.actionListener = actionListener;
        
        resourceBundle = ResourceBundle.getBundle("Interface/Language/HUD");
        
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
        linkControllerForElement(screen, "element-cc");
        linkControllerForElement(screen, "element-list");
        
        Element elementListPanel = screen.findElementByName("element-list");
        List<Element> elements = getElementList(screen, elementListPanel);
        for(Element e : elements) {
            elementListPanel.add(e);
        }
        
        hudPanelControl = screen.findElementByName("hud-panel-control");
        hudPanelShowHide = screen.findElementByName("hud-panel-showhide");
        refreshSwitchLabel();
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
    
    public void triggerShowHideHUD() {
        if(hudPanelControl.isEnabled()) {
            hudPanelControl.disable();
        } else {
            hudPanelControl.enable();
        }
        refreshSwitchLabel();
    }
    
    protected void refreshSwitchLabel() {
        String label = resourceBundle.getString("hud");
        label += ": ";
        if(hudPanelControl.isEnabled()) {
            label += resourceBundle.getString("on");
        } else {
            label += resourceBundle.getString("off");
        }
        hudPanelShowHide.getRenderer(TextRenderer.class).setText(label);
    }
    
    protected List<Element> getElementList(final Screen screen,
            final Element panel) {
        List<Element> list = new ArrayList<Element>();
        
        ElementLoader loader = new ElementLoader();
        List<AbstractElement> elements = loader.loadAll();
        
        for(AbstractElement element : elements) {
            final String label = element.getLabel();
            final String elementClass = element.getClass().getName();
            final String id = getElementIdByClassName(elementClass);
            final String trigger = "triggerInsertElement(" + elementClass + ")";
            
            Element e = new ControlBuilder(id, "label") {{
                    parameter("text", label);
                    parameter("style", "hud-element-list-entry");
                    interactOnClick(trigger);
                }}.build(nifty, screen, panel);
            list.add(e);
        }
        
        return list;
    }
    
    protected String getElementIdByClassName(final String className) {
        return "element#" + className;
    }
    
}
