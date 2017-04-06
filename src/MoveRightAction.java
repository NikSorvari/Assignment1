import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;
import sage.terrain.*;

public class MoveRightAction extends AbstractInputAction {
	 private SceneNode avatar;
	 private float speed = 0.01f; // it would be better to use axis value
	 private TerrainBlock terrain;
	 
	 
	 public MoveRightAction(SceneNode n, TerrainBlock ter) { 
		 avatar = n; 
		 terrain = ter;
	 }
	 
	 public void performAction(float time, Event e)
	 {
		 move(time, e);
		 updateVerticalPosition();

	 }
	 public void move(float time, Event e)
	 {
		 Matrix3D rot = avatar.getLocalRotation();
		 Vector3D dir;
		 
		 if (e.getValue() < -0.2)
		 { dir = new Vector3D(1,0,0); }
		 else { if (e.getValue() > 0.2)
		 { dir = new Vector3D(-1,0,0); }
		 else { dir = new Vector3D(0,0,0); }
		 }
		 
		 dir = dir.mult(rot);
		 dir.scale((double)(speed * time));
		 avatar.translate((float)dir.getX(),(float)dir.getY(),(float)dir.getZ());
	 } 
	 public void updateVerticalPosition()
	 {
		 Point3D avLoc = new Point3D(avatar.getLocalTranslation().getCol(3));
		 float x = (float) avLoc.getX();
		 float z = (float) avLoc.getZ();
		 float terHeight = Math.max(terrain.getHeight(x,z), 0);
		 float desiredHeight = terHeight + (float)terrain.getOrigin().getY() + 0.5f;
		 avatar.getLocalTranslation().setElementAt(1, 3, desiredHeight);
	 }
}
