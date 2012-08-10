package com.kluckerz.editor;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.kluckerz.Direction;
import java.util.ArrayList;
import java.util.List;

/**
 * The subapplication for the editor mode.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Editor implements ActionListener {
    
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
        
        app.getFlyByCamera().setEnabled(false);
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
                case CURSOR_DOWN:
                case CURSOR_NORTH:
                case CURSOR_SOUTH:
                case CURSOR_WEST:
                case CURSOR_EAST:
                    Direction camView = camera.getViewDirection();
                    moveCursor(kc, camView);
                    break;
                case TURN_CAM_CLOCKWISE:
                case TURN_CAM_COUNTERCLOCKWISE:
                case ZOOM_CAM_IN:
                case ZOOM_CAM_OUT:
                case CAM_TOP_VIEW_ON:
                case CAM_TOP_VIEW_OFF:
                    moveCamera(kc);
                    break;
            }
        }
    }

    private void moveCursor(final KeyboardControl kc, final Direction camView) {
        switch(kc) {
            case CURSOR_UP:
                cursor.moveUp();
                break;
            case CURSOR_DOWN:
                cursor.moveDown();
                break;
            case CURSOR_NORTH:
                cursor.moveRelative(Direction.NORTH, camView);
                break;
            case CURSOR_SOUTH:
                cursor.moveRelative(Direction.SOUTH, camView);
                break;
            case CURSOR_WEST:
                cursor.moveRelative(Direction.WEST, camView);
                break;
            case CURSOR_EAST:
                cursor.moveRelative(Direction.EAST, camView);
                break;
        }
        camera.update(true);
    }

    private void moveCamera(final KeyboardControl kc) {
        switch(kc) {
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
