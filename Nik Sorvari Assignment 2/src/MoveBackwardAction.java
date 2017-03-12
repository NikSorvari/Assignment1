import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class MoveBackwardAction extends AbstractInputAction  {
	 private SceneNode avatar;
	 private float speed = 0.01f; // it would be better to use axis value
	 
	 public MoveBackwardAction(SceneNode n) { avatar = n; }
	 
	 public void performAction(float time, Event e)
	 {
		 Matrix3D rot = avatar.getLocalRotation();
		 Vector3D dir;
		 if (e.getValue() < -0.2)
		 { dir = new Vector3D(0,0,1); }
		 else { if (e.getValue() > 0.2)
		 { dir = new Vector3D(0,0,-1); }
		 else { dir = new Vector3D(0,0,0); }
		 }
		 
		 
		 dir = dir.mult(rot);
		 dir.scale((double)(speed * time));
		 avatar.translate((float)dir.getX(),(float)dir.getY(),(float)dir.getZ());
	 } 
}
