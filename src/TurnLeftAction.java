import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class TurnLeftAction extends AbstractInputAction {
	 private SceneNode avatar;
	 private float speed = 0.1f; // it would be better to use axis value
	 
	 public TurnLeftAction(SceneNode n) { avatar = n; }
	 
	 public void performAction(float time, Event e)
	 {
		 //Matrix3D rot = avatar.getLocalRotation();
		 float dir = 0;
		 
		 if (MyGame.getBehindAvatar()==true)
		 {
			 { dir = speed; }
			 
			 
			 avatar.rotate(dir*time, new Vector3D(0,1,0));
		 }
		 else
		 {
			 
		 }
		 
		 
	 } 
}
