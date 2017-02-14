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


import graphicslib3D.Point3D;

import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.display.*;
import sage.input.*;
import sage.input.action.*;
import sage.scene.HUDString;
import sage.scene.SceneNode;
import sage.scene.shape.Rectangle;
import sage.scene.TriMesh;
import sage.display.DisplaySystem;

import net.java.games.input.*;


public class MyGame extends BaseGame
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
		
		//add some game objects
		

		
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
		// TODO Auto-generated method stub
		
	}
	

	public static void main (String [] args)
	{
		new MyGame().start();
	}

}
