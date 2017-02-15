//Nik Sorvari
//02/14/2017

//todo:

//create truck with TriMesh
//support input actions handled by Action classes implementing IAction,
//link device components with action classes using InputManager.associateAction()




import java.awt.Color;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Random;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;

import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.display.*;
import sage.input.*;
import sage.input.action.*;
import sage.scene.HUDString;
import sage.scene.SceneNode;
import sage.scene.shape.Line;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Teapot;
import sage.scene.TriMesh;
import sage.display.DisplaySystem;

import net.java.games.input.*;


public class MyGame extends BaseGame implements MouseListener
{
	private int score = 0;
	private float time = 0;
	private HUDString scoreString;
	private HUDString timeString;
	IDisplaySystem display;
	ICamera camera;
	
	
	//initGame() is called once at startup by BaseGame.start()
	
	public void initGame()
	{
		display = getDisplaySystem();
		initGameObjects();
		IInputManager im = getInputManager();
		String gpName = im.getFirstGamepadName();
		
		SetSpeedAction setSpeed = new SetSpeedAction();
		ForwardAction mvForward = new ForwardAction(camera, 0.01f);
		im.associateAction(gpName,
				net.java.games.input.Component.Identifier.Axis.Y,
				mvForward,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(gpName,
				net.java.games.input.Component.Identifier.Button._3,
				setSpeed,
				IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		

		//a HUD
		
		timeString = new HUDString("Time = " + time);
		timeString.setLocation(0, 0.05);	//(0,0) [lower-left] to (1,1)
		addGameWorldObject(timeString);
		scoreString = new HUDString("Score = " + score); //default is (0,0)
		addGameWorldObject(scoreString);
		
		//configure game display
		
		display.setTitle("Space Farming 3D");
		display.addMouseListener(this);
	}
	
	

	//update is called by BaseGame once each time around game loop
	
	public void update(float elapsedTimeMS)
	{
		
		for (SceneNode s : getGameWorld())
		{

		}
		
		//update the HUD
		
		scoreString.setText("Score = "+ score);
		time += elapsedTimeMS;
		DecimalFormat df = new DecimalFormat("0.0");
		timeString.setText("Time = " + df.format(time/1000));
		
		//tell BaseGame to update game world state
		
		super.update(elapsedTimeMS);
	}
	
	private void initGameObjects() {
		IDisplaySystem display = getDisplaySystem();
		 display.setTitle("My Cool Game");
		 camera = display.getRenderer().getCamera();
		 camera.setPerspectiveFrustum(45, 1, 0.01, 1000);
		 camera.setLocation(new Point3D(1, 1, 20));
		 MyPyramid aPyr = new MyPyramid(Color.red);
		 Matrix3D pyrM = aPyr.getLocalTranslation();
		 pyrM.translate(2,-2,-8);
		 aPyr.setLocalTranslation(pyrM);
		 addGameWorldObject(aPyr);
		 Teapot teap = new Teapot(Color.blue);
		 Matrix3D teaM = teap.getLocalTranslation();
		 teaM.translate(-1,1,-5);
		 teap.setLocalTranslation(teaM);
		 addGameWorldObject(teap);
		 Point3D origin = new Point3D(0,0,0);
		 Point3D xEnd = new Point3D(100,0,0);
		 Point3D yEnd = new Point3D(0,100,0);
		 Point3D zEnd = new Point3D(0,0,100);
		 Line xAxis = new Line (origin, xEnd, Color.red, 2);
		 Line yAxis = new Line (origin, yEnd, Color.green, 2);
		 Line zAxis = new Line (origin, zEnd, Color.blue, 2);
		 addGameWorldObject(xAxis); addGameWorldObject(yAxis);
		 addGameWorldObject(zAxis);
		
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	public static void main (String [] args)
	{
		new MyGame().start();
		
	}

}
