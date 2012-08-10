package com.kluckerz.editor;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import com.kluckerz.Direction;

/**
 * Extension for the default camera to be used in editor mode.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class Cam {
    
    private Camera cam;
    
    private Node target;
    
    private float currentPhiAngle;
    
    private float phiChange;
    
    private float currentThetaAngle;
    
    private float targetThetaAngle;
    
    private float currentDistance;
    
    private float targetDistance;
    
    private boolean topView;
    
    private Delay delay;
    
    /**
     * Creates a new editor camera.
     * @param cam The default camera.
     * @param target The target to observe (usually the cursor).
     */
    public Cam(final Camera cam, final Node target) {
        this.cam = cam;
        this.target = target;
        
        currentPhiAngle = 0;
        phiChange = 0;
        currentDistance = 50;
        targetDistance = currentDistance;
        currentThetaAngle = 0;
        targetThetaAngle = currentThetaAngle;
        delay = new Delay(4);
    }
    
    /**
     * Turn the camera view clockwise around the observed target.
     */
    public void turnClockwise() {
        phiChange -= 90;
    }
    
    /**
     * Turn the camera view counter-clockwise around the observed target.
     */
    public void turnCounterClockwise() {
        phiChange += 90;
    }
    
    /**
     * Zoom the camera view in - towards the observed target.
     */
    public void zoomIn() {
        if(currentDistance > 10) {
            targetDistance = currentDistance - 5;
        }
    }
    
    /**
     * Zoom the camera view out - away from the observed target.
     */
    public void zoomOut() {
        targetDistance = currentDistance + 5;
    }
    
    /**
     * Switches the camera top view on/off.
     * @param active True, to switch the camera top view on, otherwise false.
     */
    public void setTopView(final boolean active) {
        if(active) {
            targetThetaAngle = 90;
        } else {
            targetThetaAngle = 0;
        }
    }

    /**
     * Returns the direction in which the camera "looks".
     * @return A value of the Direction enumeration that specifies the direction.
     */
    public Direction getViewDirection() {
        if(currentPhiAngle > 45 && currentPhiAngle <= 135) {
            return Direction.WEST;
        } else if(currentPhiAngle > 135 && currentPhiAngle <= 225) {
            return Direction.SOUTH;
        } else if(currentPhiAngle > 225 && currentPhiAngle <= 315) {
            return Direction.EAST;
        }
        return Direction.NORTH;
    }
    
    /**
     * Update the camera's position, if necessary. Usually called per frame from
     * the game's update method.
     */
    public void update() {
        update(false);
    }
    
    /**
     * Update the camera's position. Usually called per frame from the game's
     * update method.
     * @param forceUpdate True, to force update, false to update only if necessary.
     */
    public void update(final boolean forceUpdate) {
        if(!forceUpdate
        && phiChange == 0
        && currentThetaAngle == targetThetaAngle
        && currentDistance == targetDistance
        && !delay.isReached()) {
            return;
        }
        
        if(phiChange > 0) {
            currentPhiAngle++;
            phiChange--;
        } else if(phiChange < 0) {
            currentPhiAngle--;
            phiChange++;
        }

        if(currentPhiAngle < 0) {
            currentPhiAngle += 360;
        } else if(currentPhiAngle > 360) {
            currentPhiAngle -= 360;
        }
        
        if(currentThetaAngle > targetThetaAngle) {
            currentThetaAngle--;
        } else if(currentThetaAngle < targetThetaAngle) {
            currentThetaAngle++;
        }
        
        if(currentDistance > targetDistance) {
            currentDistance--;
        } else if(currentDistance < targetDistance) {
            currentDistance++;
        }
        
        float thetaRad = currentThetaAngle * FastMath.DEG_TO_RAD;
        float phiRad = currentPhiAngle * FastMath.DEG_TO_RAD;
        
        Vector3f targetPos = target.getLocalTranslation();
        float x = currentDistance * FastMath.sin(phiRad);
        float y = currentDistance * thetaRad;
        float z = currentDistance * FastMath.cos(phiRad);

        cam.setLocation(targetPos.add(new Vector3f(x, y, z)));
        cam.lookAt(targetPos, Vector3f.UNIT_Y);
    }

    /**
     * This internal class handles the delay of the camera's movement (to not
     * move the position with every frame).
     */
    protected class Delay {
        
        private int x;
        
        private int delay;
        
        /**
         * Creates a new delay handler.
         * @param delay The number of frames to skip before next update.
         */
        protected Delay(final int delay) {
            this.delay = delay;
            x = 0;
        }
        
        protected boolean isReached() {
            x++;
            if(x >= delay) {
                x = 0;
                return true;
            }
            return false;
        }
        
    }
    
}
