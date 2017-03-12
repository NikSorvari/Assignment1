import graphicslib3D.Matrix3D;
import sage.scene.Controller;
import sage.scene.SceneNode;

public class MyTranslateController extends Controller
{
	 private double translationRate = 0 ; 
	 private double cycleTime = 2000.0; 
	 private double totalTime=0;
	 private double direction = 1.0;
	 
	 public void setCycleTime(double c)
	 { cycleTime = c; }
	 
	 public void update(double time) // example controller
	 {
		 time-=totalTime;
		 totalTime = time;
		 double transAmount = translationRate * time ;
		 if (Math.floor(totalTime/cycleTime)%2 == 1)
		 {
			 direction = -1.0;
		 }
		 else
		 {
			 direction = 1.0;
		 }
		 transAmount = direction * transAmount;
		 Matrix3D newTrans = new Matrix3D();
		 newTrans.translate(transAmount,0,0);
		 for (SceneNode node : controlledNodes)
		 {
			 Matrix3D curTrans = node.getLocalTranslation();
			 curTrans.concatenate(newTrans);
			 node.setLocalTranslation(curTrans);
		 }
	 }
}
