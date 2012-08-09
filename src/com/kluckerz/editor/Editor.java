package com.kluckerz.editor;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

/**
 * The subapplication for the editor mode.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Editor implements ActionListener {
    
    private enum KeyboardControl {
        CURSOR_UP(KeyInput.KEY_W), CURSOR_DOWN(KeyInput.KEY_S),
        CURSOR_LEFT(KeyInput.KEY_A), CURSOR_RIGHT(KeyInput.KEY_D),
        TURN_CAM_CLOCKWISE(KeyInput.KEY_NUMPAD4),
        TURN_CAM_COUNTERCLOCKWISE(KeyInput.KEY_NUMPAD6),
        ZOOM_CAM_IN(KeyInput.KEY_NUMPAD8),
        ZOOM_CAM_OUT(KeyInput.KEY_NUMPAD2),
        CAM_TOP_VIEW_ON(KeyInput.KEY_PGUP), CAM_TOP_VIEW_OFF(KeyInput.KEY_PGDN);
        
        private KeyTrigger trigger;
        
        private int key;
        
        KeyboardControl(final int key) {
            this.key = key;
            trigger = new KeyTrigger(key);
        }
        
        public int getKey() {
            return key;
        }
        
        public KeyTrigger getTrigger() {
            return trigger;
        }
        
        public static KeyboardControl fromId(final String id) {
            for(KeyboardControl c : values()) {
                if(c.toString().equals(id)) {
                    return c;
                }
            }
            return null;
        }
    }
    
    private SimpleApplication app;
    
    private Cursor cursor;
    
    private Spatial scene;
    
    private Cam camera;
    
    /**
     * Creates a new editor.
     * @param app The main application.
     */
    public Editor(final SimpleApplication app) {
        this.app = app;
    }
    
    /**
     * Start the editor.
     */
    public void start() {
        cursor = createCursor();
        Node cursorNode = cursor.getNode();
        app.getRootNode().attachChild(cursorNode);
        
        cursor.moveUp();
        
        scene = createScene();
        app.getRootNode().attachChild(scene);
        
        camera = createCamera(cursorNode);
        
        initKeyboardControls();
    }
    
    private Cursor createCursor() {
        return new Cursor(app);
    }
    
    private Spatial createScene() {
        Spatial sceneModel = app.getAssetManager().loadModel("Scenes/TestScene.j3o");
        sceneModel.setLocalScale(2f);
        return sceneModel;
    }
    
    private Cam createCamera(final Node target) {
        Camera cam = app.getCamera();
        return new Cam(cam, target);
    }
    
    private void initKeyboardControls() {
        InputManager inputManager = app.getInputManager();
        List<String> ids = new ArrayList<String>();
        for(KeyboardControl kc : KeyboardControl.values()) {
            String id = kc.toString();
            inputManager.addMapping(id, kc.getTrigger());
            ids.add(id);
        }
        inputManager.addListener(this, ids.toArray(new String[0]));
    }
    
    /**
     * Update the editor. Usually called per frame by the application's update
     * method.
     */
    public void update() {
        camera.update();
    }
    
    /**
     * The action handler which handles the keyboard events.
     * @param name The id of the fired action.
     * @param isPressed True, if the key is pressed.
     * @param tpf 
     */
    @Override
    public void onAction(final String name, final boolean isPressed,
            final float tpf) {
        if(!isPressed) {
            return;
        }
        
        KeyboardControl kc = KeyboardControl.fromId(name);
        if(kc != null) {
            switch(kc) {
                case CURSOR_UP:
                    cursor.moveUp();
                    camera.update(true);
                    break;
                case CURSOR_DOWN:
                    cursor.moveDown();
                    camera.update(true);
                    break;
                case CURSOR_LEFT:
                    cursor.moveLeft();
                    camera.update(true);
                    break;
                case CURSOR_RIGHT:
                    cursor.moveRight();
                    camera.update(true);
                    break;
                case TURN_CAM_CLOCKWISE:
                    camera.turnClockwise();
                    break;
                case TURN_CAM_COUNTERCLOCKWISE:
                    camera.turnCounterClockwise();
                    break;
                case ZOOM_CAM_IN:
                    camera.zoomIn();
                    break;
                case ZOOM_CAM_OUT:
                    camera.zoomOut();
                    break;
                case CAM_TOP_VIEW_ON:
                    camera.setTopView(true);
                    break;
                case CAM_TOP_VIEW_OFF:
                    camera.setTopView(false);
                    break;
            }
        }
    }
    
}
