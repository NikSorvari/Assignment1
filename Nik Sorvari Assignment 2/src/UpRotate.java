import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class UpRotate extends AbstractInputAction {
	 private ICamera camera;
	 private float speed;
	 
	 public UpRotate(ICamera c, float s)
	 { 
		 camera = c;
		 speed = s;
	 }
	 public void performAction(float time, Event e)
	 { 
		 Matrix3D rotationAmt = new Matrix3D();
		 Vector3D vd = camera.getViewDirection();
		 Vector3D ud = camera.getUpAxis();
		 Vector3D rd = camera.getRightAxis();
		 
		 rotationAmt.rotate(speed,  rd);
		 
		 vd = vd.mult(rotationAmt);
		 ud = ud.mult(rotationAmt);
		 camera.setUpAxis(ud.normalize());
		 camera.setViewDirection(vd.normalize());
	 }
}

