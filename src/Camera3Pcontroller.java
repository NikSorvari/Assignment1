import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import net.java.games.input.Component.Identifier.Axis;
import sage.camera.ICamera;
import sage.input.IInputManager;
import sage.input.IInputManager.INPUT_ACTION_TYPE;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;
import sage.util.MathUtils;

public class Camera3Pcontroller {
	 private ICamera cam; //the camera being controlled
	 private SceneNode target; //the target the camera looks at
	 private float cameraAzimuth; //rotation of camera around target Y axis
	 private float cameraElevation; //elevation of camera above target
	 private float cameraDistanceFromTarget;
	 private Point3D targetPos; // avatar’s position in the world
	 private Vector3D worldUpVec;
	 
	 public Camera3Pcontroller(ICamera cam, SceneNode target, 
			 					IInputManager inputMgr, String controllerName)
	 { 
		 this.cam = cam;
		 this.target = target;
		 worldUpVec = new Vector3D(0,1,0);
		 cameraDistanceFromTarget = 5.0f;
		 cameraAzimuth = 180; // start from BEHIND and ABOVE the target
		 cameraElevation = 20.0f; // elevation is in degrees
		 update(0.0f); // initialize camera state
		 setupInput(inputMgr, controllerName);
	 }
	private void setupInput(IInputManager im, String cn) {
		 IAction orbitAction = new OrbitAroundAction();
		 IAction zoomOut1 = new ZoomOutAction();
		 IAction zoomIn1 = new ZoomInAction();
		 IAction zoomOut2 = new ZoomOutAction();
		 im.associateAction(cn, Axis.RX, orbitAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		 
		 im.associateAction(cn, Axis.RY, zoomOut2, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		 im.associateAction(cn, net.java.games.input.Component.Identifier.Key.UP, zoomOut1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		 im.associateAction(cn, net.java.games.input.Component.Identifier.Key.DOWN, zoomIn1, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	}
	public void update(float time) {
		updateTarget();
		updateCameraPosition();
		cam.lookAt(targetPos, worldUpVec);
	}
	private void updateCameraPosition() {
		 double theta = cameraAzimuth;
		 double phi = cameraElevation ;
		 double r = cameraDistanceFromTarget;
		 // calculate new camera position in Cartesian coords
		 Point3D relativePosition = MathUtils.sphericalToCartesian(theta, phi, r);
		 Point3D desiredCameraLoc = relativePosition.add(targetPos);
		 cam.setLocation(desiredCameraLoc);
	}
	private void updateTarget() {
		targetPos = new Point3D(target.getWorldTranslation().getCol(3)); 
	}
	
	public class OrbitAroundAction extends AbstractInputAction {

		@Override
		public void performAction(float time, Event evt) {
			 float rotAmount=0;
			 if (MyGame.getBehindAvatar()==false)
			 {
				 if (evt.getValue() < -0.2) { rotAmount=-0.1f; }
				 else { if (evt.getValue() > 0.2) { rotAmount=0.1f; }
				 else { rotAmount=0.0f; }
				 }
			 }
			else
			{
				cameraAzimuth = (float) target.getWorldRotation().elementAt(0,1);
			}
				 
		cameraAzimuth -= rotAmount ;
		cameraAzimuth = cameraAzimuth % 360 ;

		}
	}
	
	public class ZoomOutAction extends AbstractInputAction
	{
		public void performAction(float time, Event evt) {
			float zoom = 0;
			if (evt.getValue() < -0.2 && cameraDistanceFromTarget>1) { zoom=-0.02f; }
			 else { if (evt.getValue() > 0.2) { zoom=0.02f; }
			 else { zoom=0.0f; }
			 }
			
			cameraDistanceFromTarget+=zoom;
		}
	}
	
	public class ZoomInAction extends AbstractInputAction
	{
		public void performAction(float time, Event evt) {
			float zoom = 0;
			if (cameraDistanceFromTarget>1) { zoom=-0.02f; }

			
			cameraDistanceFromTarget+=zoom;
		}
	}
}
	
