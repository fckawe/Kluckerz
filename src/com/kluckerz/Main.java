package com.kluckerz;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.kluckerz.editor.Editor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Main class of the game.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Main extends SimpleApplication {
    
    private RootNodeWrapper rootNodeWrapper;
    
    private Editor editor;
    
    public static void main(final String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void setSettings(AppSettings settings) {
        settings.setTitle("Kluckerz");
        BufferedImage[] icons = getApplicationIcons();
        settings.setIcons(icons);
        
        super.setSettings(settings);
    }
    
    private BufferedImage[] getApplicationIcons() {
        try {
            // TODO: find a better way to find the icons
            return new BufferedImage[] {
                ImageIO.read(Main.class.getResourceAsStream("../../Interface/icon-128.png")),
                ImageIO.read(Main.class.getResourceAsStream("../../Interface/icon-32.png")),
                ImageIO.read(Main.class.getResourceAsStream("../../Interface/icon-16.png"))
            };
        } catch(IOException ioException) {
            // TODO: log error
            return null;
        }
    }

    @Override
    public void simpleInitApp() {
        rootNodeWrapper = createRootNodeWrapper();
        editor = createEditor();
        editor.start();
    }
    
    protected RootNodeWrapper createRootNodeWrapper() {
        return new RootNodeWrapper(rootNode);
    }
    
    public RootNodeWrapper getRootNodeWrapper() {
        return rootNodeWrapper;
    }
    
    protected Editor createEditor() {
        return new Editor(this);
    }
    
    @Override
    public void simpleUpdate(final float tpf) {
        editor.update();
    }
    
}
