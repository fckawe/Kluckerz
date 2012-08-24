package com.kluckerz.editor;

import com.kluckerz.editor.hud.HUD;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import com.kluckerz.Direction;
import com.kluckerz.Main;
import com.kluckerz.lmnts.AbstractElement;
import java.util.ArrayList;
import java.util.List;

/**
 * The subapplication for the editor mode.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Editor implements ActionListener {
    
    private Main app;
    
    private Cursor cursor;
    
    private Spatial scene;
    
    private Cam camera;
    
    private BulletAppState bulletAppState;
    
    private HUD hud;
    
    /**
     * Creates a new editor.
     * @param app The main application.
     */
    public Editor(final Main app) {
        this.app = app;
    }
    
    /**
     * Start the editor.
     */
    public void start() {
        bulletAppState = new BulletAppState();
        app.getStateManager().attach(bulletAppState);
                    
        cursor = createCursor();
        Node cursorNode = cursor.getNode();
        app.getRootNodeWrapper().attachChild(cursorNode);
        
        scene = createScene();
        app.getRootNodeWrapper().attachChild(scene);
        
        camera = createCamera(cursorNode);
        app.getFlyByCamera().setEnabled(false);
        
        hud = createHUD();
        hud.init(app, this);
        
        initKeyboardControls(hud);
        setupLight();
    }
    
    private Cursor createCursor() {
        return new Cursor(app);
    }
    
    private Spatial createScene() {
        Spatial sceneModel = app.getAssetManager().loadModel("Scenes/TestScene.j3o");
        sceneModel.setLocalScale(2f);
        RigidBodyControl rbc = new RigidBodyControl(0f);
        sceneModel.addControl(rbc);
        bulletAppState.getPhysicsSpace().add(rbc);
        return sceneModel;
    }
    
    private Cam createCamera(final Node target) {
        Camera cam = app.getCamera();
        return new Cam(cam, target);
    }
    
    private void initKeyboardControls(final HUD hud) {
        InputManager inputManager = app.getInputManager();
        List<String> ids = new ArrayList<String>();
        for(KeyboardControl kc : KeyboardControl.values()) {
            String id = kc.toString();
            inputManager.addMapping(id, kc.getTrigger());
            ids.add(id);
        }
        inputManager.addListener(this, ids.toArray(new String[0]));
    }
    
    private void setupLight() {
        AmbientLight light = new AmbientLight();
        light.setColor(ColorRGBA.White.mult(0.7f));
        app.getRootNodeWrapper().addLight(light);
    }
    
    private HUD createHUD() {
        return new HUD();
    }
    
    /**
     * Update the editor. Usually called per frame by the application's update
     * method.
     * @param tpf
     */
    public void update(final float tpf) {
        camera.update();
        app.getRootNodeWrapper().update();
        bulletAppState.getPhysicsSpace().update(tpf);
    }
    
    /**
     * The action handler which handles the keyboard events.
     * @param actionId The id of the fired action.
     * @param isPressed True, if the key is pressed.
     * @param tpf 
     */
    @Override
    public void onAction(final String actionId, final boolean isPressed,
            final float tpf) {
        if(!isPressed) {
            return;
        }
        
        String keyId = actionId;
        String[] params = null;
        int keyParamsSepPos = actionId.indexOf(':'); // TODO: Konstante
        if(keyParamsSepPos > 0) {
            keyId = actionId.substring(0, keyParamsSepPos);
            String paramsStr = actionId.substring(keyParamsSepPos + 1);
            params = paramsStr.split(","); // TODO: Konstante
        }
        
        KeyboardControl kc = KeyboardControl.fromId(keyId);
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
                case INSERT_ELEMENT:
                    if(!cursorHasElementSelected() && params != null && params.length >= 1) {
                        insertElement(params[0]);
                        cursor.update();
                    }
                    break;
                case TURN_ELEMENT_X_CW:
                case TURN_ELEMENT_X_CCW:
                case TURN_ELEMENT_Y_CW:
                case TURN_ELEMENT_Y_CCW:
                case TURN_ELEMENT_Z_CW:
                case TURN_ELEMENT_Z_CCW:
                    if(cursorHasElementSelected()) {
                        turnSelectedElement(kc);
                    }
                    break;
                case TESTER:
                    // TODO: falling ball - only for testing physics
                    Material stone_mat = new Material(app.getAssetManager(),
                            "Common/MatDefs/Misc/Unshaded.j3md");
                    TextureKey key2 = new TextureKey("Interface/Icons/icon-32.png");
                    key2.setGenerateMips(true);
                    Texture tex2 = app.getAssetManager().loadTexture(key2);
                    stone_mat.setTexture("ColorMap", tex2);
                    Sphere sphere = new Sphere(32, 32, 0.4f, true, false);
                    sphere.setTextureMode(TextureMode.Projected);
                    Geometry ball_geo = new Geometry("falling ball", sphere);
                    ball_geo.setMaterial(stone_mat);
                    ball_geo.setLocalTranslation(cursor.getInsertPos());
                    app.getRootNodeWrapper().attachChild(ball_geo);
                    RigidBodyControl ball_phy = new RigidBodyControl(1f);
                    ball_geo.addControl(ball_phy);
                    bulletAppState.getPhysicsSpace().add(ball_phy);
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
    
    private boolean cursorHasElementSelected() {
        return cursor.getSelectedElement() != null;
    }
    
    private void insertElement(final String elementClass) {
        try {
            Class cls = Class.forName(elementClass);
            Object elementObj = cls.newInstance();
            if(elementObj instanceof AbstractElement) {
                AbstractElement lmnt = (AbstractElement)elementObj;
                lmnt.init(app.getAssetManager());
                Node lmntNode = lmnt.getNode();
                Vector3f insertPos = cursor.getInsertPos();
                lmntNode.setLocalTranslation(insertPos);
                app.getRootNodeWrapper().attachChild(lmnt);
                lmnt.addPhysics(bulletAppState);
            }
        } catch (ClassNotFoundException e) {
            // TODO: Fehlerbehandlung
        } catch (IllegalAccessException e) {
            // TODO: Fehlerbehandlung
        } catch (InstantiationException e) {
            // TODO: Fehlerbehandlung
        }
    }
    
    private void turnSelectedElement(final KeyboardControl kc) {
        AbstractElement lmnt = cursor.getSelectedElement();
        switch(kc) {
            case TURN_ELEMENT_X_CW:
                lmnt.rotateXClockwise();
                break;
            case TURN_ELEMENT_X_CCW:
                lmnt.rotateXCounterClockwise();
                break;
            case TURN_ELEMENT_Y_CW:
                lmnt.rotateYClockwise();
                break;
            case TURN_ELEMENT_Y_CCW:
                lmnt.rotateYCounterClockwise();
                break;
            case TURN_ELEMENT_Z_CW:
                lmnt.rotateZClockwise();
                break;
            case TURN_ELEMENT_Z_CCW:
                lmnt.rotateZCounterClockwise();
                break;
        }
        app.getRootNodeWrapper().addAnimatingElement(lmnt);
    }
    
}
