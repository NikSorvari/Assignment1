import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class RightRotate extends AbstractInputAction {
	 private ICamera camera;
	 private float speed;
	 
	 public RightRotate(ICamera c, float s)
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
		 
		 
		 if (e.getValue() < -0.2)
		 	{ rotationAmt.rotate(speed,  ud); }
		else { if (e.getValue() > 0.2)
			{ rotationAmt.rotate(-speed,  ud); }
			else {}
		}
		 
		 vd = vd.mult(rotationAmt);
		 rd = rd.mult(rotationAmt);
		 camera.setRightAxis(rd.normalize());
		 camera.setViewDirection(vd.normalize());
	 }
}

