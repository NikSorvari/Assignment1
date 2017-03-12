import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class RotationController extends Controller
{
	private double rotationRate = 0.01 ;
	private Vector3D rotationAxis = new Vector3D(1,1,1) ;
	
	public void update(double time)
	{ 
	
	double rotAmount = rotationRate/1000.0 * time ;
	
	Matrix3D newRot = new Matrix3D(rotAmount, rotationAxis);
	
		for (SceneNode node : controlledNodes)
		{ 
			Matrix3D curRot = node.getLocalRotation();
	
			curRot.concatenate(newRot);
			node.setLocalRotation(curRot);
		}
	}

}
