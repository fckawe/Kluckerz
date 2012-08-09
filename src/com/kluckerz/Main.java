package com.kluckerz;

import com.jme3.app.SimpleApplication;
import com.kluckerz.editor.Editor;

/**
 * Main class of the game.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Main extends SimpleApplication {
    
    private Editor editor;

    public static void main(final String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        editor = createEditor();
        editor.start();
    }
    
    protected Editor createEditor() {
        return new Editor(this);
    }
    
    public void simpleUpdate(final float f) {
        editor.update();
    }
    
}
