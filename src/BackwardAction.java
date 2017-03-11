import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class BackwardAction extends AbstractInputAction {
	 private ICamera camera;
	 private float speed;
	 
	 public BackwardAction(ICamera c, float s)
	 { 
		 camera = c;
		 speed = s;
	 }
	 public void performAction(float time, Event e)
	 { 
		 Vector3D newLocVector = new Vector3D();
		 Vector3D viewDir = camera.getViewDirection().normalize();
		 Vector3D curLocVector = new Vector3D(camera.getLocation());
		 
		//conditions for gamepad axis in both directions
		//keyboard S always goes back
		 
		 
		 if (e.getValue() < -0.2)
		 { newLocVector = curLocVector.add(viewDir.mult(speed * time)); }
		 else { if (e.getValue() > 0.2)
		 { newLocVector = curLocVector.minus(viewDir.mult(speed * time)); }
		 else { newLocVector = curLocVector; }
		 }
		 
		 
		 //create a point for the new location
		 double newX = newLocVector.getX();
		 double newY = newLocVector.getY();
		 double newZ = newLocVector.getZ();
		 Point3D newLoc = new Point3D(newX, newY, newZ);
		 camera.setLocation(newLoc);
	 }
}

