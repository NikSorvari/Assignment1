//Nik Sorvari
//02/14/2017

//todo:


//support input actions handled by Action classes implementing IAction,
//link device components with action classes using InputManager.associateAction()




import java.awt.Color;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.camera.ICamera;
import sage.camera.JOGLCamera;
import sage.display.*;
import sage.input.*;
import sage.input.action.*;
import sage.renderer.IRenderer;
import sage.scene.Group;
import sage.scene.HUDString;
import sage.scene.SceneNode;
import sage.scene.SkyBox;
import sage.scene.shape.Cube;
import sage.scene.shape.Line;
import sage.scene.shape.Pyramid;
import sage.scene.shape.Rectangle;
import sage.scene.shape.Teapot;
import sage.texture.Texture;
import sage.texture.TextureManager;
import sage.scene.TriMesh;
import sage.display.DisplaySystem;
import sage.event.EventManager;
import sage.event.IEventManager;
import net.java.games.input.*;


public class MyGame extends BaseGame implements MouseListener
{
	Random rn = new Random();
	private static int score = 0;
	private float time = 0;
	private HUDString scoreString;
	private HUDString timeString;
	private IRenderer renderer;
	IDisplaySystem display;
	static ICamera camera1, camera2;
	Camera3Pcontroller cc1, cc2;
	IEventManager eventMgr;
	IInputManager im;
	String gpName;
	String kbName;
	String mouseName;
	
	SkyBox skybox;
	Cube ground;
	private static SceneNode player1;
	private static SceneNode player2;
	Pyramid aPyr;
	
	MyTruck truck;
	private static Matrix3D trucm;
	protected Group rootNode;
	MyPlant plant;
	
	int numCrashes=0;
	private static boolean behindAvatar=true;
	
	
	//initGame() is called once at startup by BaseGame.start()
	
	protected void initGame()
	{
		eventMgr = EventManager.getInstance();
		display = getDisplaySystem();
		renderer = getDisplaySystem().getRenderer();
		im = getInputManager();
		initGameObjects();
		initInputs();

		//a HUD
		
		timeString = new HUDString("Time = " + time);
		timeString.setLocation(0, 0.05);	//(0,0) [lower-left] to (1,1)
		addGameWorldObject(timeString);
		scoreString = new HUDString("Score = " + score); //default is (0,0)
		addGameWorldObject(scoreString);
		
		//configure game display
		
		display.setTitle("Water Blasters 3D");
		display.addMouseListener(this);
		super.update(0);
	}







	protected void initInputs() {
		gpName = im.getFirstGamepadName();
		kbName = im.getKeyboardName();
		mouseName = im.getMouseName();
		
		cc1 = new Camera3Pcontroller(camera1, player1, im, kbName);
		cc2 = new Camera3Pcontroller(camera2, player2, im, gpName);
		
		IAction quitGame = new QuitGameAction(this);
		IAction toggleCam = new toggleCamType();
		
		ForwardAction mvForward = new ForwardAction(camera1, 0.01f);
		RightAction mvRight = new RightAction(camera1, 0.005f);
		LeftAction mvLeft = new LeftAction(camera1, 0.005f);
		BackwardAction mvBackward = new BackwardAction(camera1, 0.01f);
		
		UpRotate rotUp = new UpRotate(camera1, 0.1f);
		DownRotate rotDown = new DownRotate(camera1, 0.1f);
		RightRotate rotRight = new RightRotate(camera1, 0.1f);
		LeftRotate rotLeft = new LeftRotate(camera1, 0.1f);
		RightRoll rolRight = new RightRoll(camera1, 0.1f);
		LeftRoll rolLeft = new LeftRoll(camera1, 0.1f);
		
		MoveForwardAction stepForward1 = new MoveForwardAction(player1);
		MoveBackwardAction stepBackward1 = new MoveBackwardAction(player1);
		MoveRightAction stepRight1 = new MoveRightAction(player1);
		MoveLeftAction stepLeft1 = new MoveLeftAction(player1);
		
		MoveBackwardAction stepBackward2 = new MoveBackwardAction(player2);
		MoveRightAction stepRight2 = new MoveRightAction(player2);
		//TurnRightAction turnRight2 = new TurnRightAction(player2);

		
		//classes BackwardAction, LeftAction, DownRotate, RightRotate handle
		//both directions on the axis for gamepad
		
		//gamepad actions
		im.associateAction(gpName,
				net.java.games.input.Component.Identifier.Axis.Y,
				stepBackward2,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		im.associateAction(gpName,
				net.java.games.input.Component.Identifier.Axis.X,
				stepRight2,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		//keyboard actions
		
		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.ESCAPE, 
				quitGame,
				IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		
		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.Z, 
				toggleCam,
				IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.W, 
				stepForward1,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.S, 
				stepBackward1,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.D, 
				stepRight1,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		
		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.A, 
				stepLeft1,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		
		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.E, 
				rolRight,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		im.associateAction(kbName, 
				net.java.games.input.Component.Identifier.Key.Q, 
				rolLeft,
				IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
	}

	private void initGameObjects() {
		IDisplaySystem display = getDisplaySystem();
		 display.setTitle("Space Farming 3D");
		 /*
		 camera1 = display.getRenderer().getCamera();
		 camera1.setPerspectiveFrustum(45, 1, 0.01, 1000);
		 camera1.setLocation(new Point3D(1, 1, 20));
		 camera2 = display.getRenderer().getCamera();
		 camera2.setPerspectiveFrustum(45, 1, 0.01, 1000);
		 camera2.setLocation(new Point3D(1, 1, 20));
		 */
		 
		 rootNode = new Group("rootNode");
		 
		 Texture skyTex=TextureManager.loadTexture2D("webtreats-seamless-cloud-1.jpg");
		 skybox = new SkyBox("SkyBox", 200.0f, 200.0f, 200.0f);
		 skybox.setTexture(SkyBox.Face.North, skyTex);
		 skybox.setTexture(SkyBox.Face.South, skyTex);
		 skybox.setTexture(SkyBox.Face.East, skyTex);
		 skybox.setTexture(SkyBox.Face.West, skyTex);
		 skybox.setTexture(SkyBox.Face.Up, skyTex);
		 rootNode.addChild(skybox);
		 
		 Texture groundTex=TextureManager.loadTexture2D("GrassGreenTexture0001.jpg");
		 ground = new Cube("GROUND");
		 ground.translate(0, -1, 0);
		 ground.scale(100, 1,  100);
		 addGameWorldObject(ground);
		 
		 player1 = new Pyramid("PLAYER1");
		 player1.translate(0, 1, 50);
		 player1.rotate(180, new Vector3D(0, 1, 0));
		 rootNode.addChild(player1);
		 
		 camera1 = new JOGLCamera(renderer);
		 camera1.setPerspectiveFrustum(60, 2, 1, 1000);
		 camera1.setViewport(0.0, 1.0, 0.0, 0.45);
		 
		 player2 = new Cube("PLAYER2");
		 player2.translate(50, 1, 0);
		 player2.rotate(-90, new Vector3D(0, 1, 0));
		 rootNode.addChild(player2);
		 
		 camera2 = new JOGLCamera(renderer);
		 camera2.setPerspectiveFrustum(60, 2, 1, 1000);
		 camera2.setViewport(0.0, 1.0, 0.55, 1.0);
		 createPlayerHUDs();
		 
		 truck = new MyTruck(Color.red);
		 trucm = truck.getLocalTranslation();
		 trucm.translate(2,1,-8);
		 truck.setLocalTranslation(trucm);
		 
		 MyTranslateController tc1 = new MyTranslateController();
		 tc1.addControlledNode(truck);
		 truck.addController(tc1);
		 rootNode.addChild(truck);
		 
		 
		 for (int i=0; i<10; i++)
		 {
			 
			 plant = new MyPlant(Color.green);
			 Matrix3D plantm = plant.getLocalTranslation();
			 plantm.translate(rn.nextInt(20)-10, 1, rn.nextInt(20)-10);
			 plant.setLocalTranslation(plantm);
			 eventMgr.addListener(plant,  CrashEvent.class);
			 
			 RotationController rc1 = new RotationController();
			 rc1.addControlledNode(plant);
			 plant.addController(rc1);
			 rootNode.addChild(plant);
		 }
		 
		 
		 
		 addGameWorldObject(rootNode);
		 
		 
		 
		 Point3D origin = new Point3D(0,0,0);
		 Point3D xEnd = new Point3D(100,0,0);
		 Point3D yEnd = new Point3D(0,100,0);
		 Point3D zEnd = new Point3D(0,0,100);
		 Line xAxis = new Line (origin, xEnd, Color.red, 2);
		 Line yAxis = new Line (origin, yEnd, Color.green, 2);
		 Line zAxis = new Line (origin, zEnd, Color.blue, 2);
		 addGameWorldObject(xAxis); addGameWorldObject(yAxis);
		 addGameWorldObject(zAxis);
		 rootNode.updateGeometricState(0,  true);
		
		 
	}

	private void createPlayerHUDs() {
		 HUDString player1ID = new HUDString("Player1");
		 player1ID.setName("Player1ID");
		 player1ID.setLocation(0.01, .8);
		 player1ID.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
		 player1ID.setColor(Color.red);
		 player1ID.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
		 camera1.addToHUD(player1ID);
		 HUDString player2ID = new HUDString("Player2");
		 player2ID.setName("Player2ID");
		 player2ID.setLocation(0.01, .8);
		 player2ID.setRenderMode(sage.scene.SceneNode.RENDER_MODE.ORTHO);
		 player2ID.setColor(Color.yellow);
		 player2ID.setCullMode(sage.scene.SceneNode.CULL_MODE.NEVER);
		 camera2.addToHUD(player2ID);
	}



	//update is called by BaseGame once each time around game loop
	
	protected void update(float elapsedTimeMS)
	{
		Point3D camLoc = camera1.getLocation();
		Matrix3D camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);
		
		//collision detection
		for (SceneNode s : getGameWorld())
		{
			if (s instanceof MyPlant)
			{
				if (((MyPlant) s).getCollected()==true)
				{
					s.setLocalTranslation(trucm);
					s.translate(0, MyGame.getScore(), 0);
				}
				else
				{
					if  (((MyPlant)s).getWorldBound().contains(new Point3D(player1.getWorldTranslation().elementAt(0, 3),
																player1.getWorldTranslation().elementAt(1, 3),
																player1.getWorldTranslation().elementAt(2, 3)))
							&& ((MyPlant) s).getCollected()==false)
					{ 
							
						numCrashes++;
						score++;
						CrashEvent newCrash = new CrashEvent(numCrashes);
						eventMgr.triggerEvent(newCrash);
							
						
					}
					else
					{
						if (s.getWorldBound().contains(new Point3D(player2.getLocalTranslation().elementAt(0, 0),
								player2.getLocalTranslation().elementAt(1, 1),
								player2.getLocalTranslation().elementAt(2, 2)))
								&& ((MyPlant) s).getCollected()==false)
						{ 

							numCrashes++;
							score++;
							CrashEvent newCrash = new CrashEvent(numCrashes);
							eventMgr.triggerEvent(newCrash);
						}
					}
				}
			}
		}
		
		System.out.println(player1.getWorldTranslation());
		
		//update the HUD
		
		scoreString.setText("Score = "+ score);
		time += elapsedTimeMS;
		DecimalFormat df = new DecimalFormat("0.0");
		timeString.setText("Time = " + df.format(time/1000));
		
		//tell BaseGame to update game world state
		cc1.update(elapsedTimeMS);
		cc2.update(elapsedTimeMS);
		super.update(elapsedTimeMS);
		rootNode.updateGeometricState(time,  true);
	}
	
	
	protected void render()
	{
		 renderer.setCamera(camera1);
		 super.render();
		 renderer.setCamera(camera2);
		 super.render();
	}
	
	public static int getScore() {
		return score;
	}
	public static boolean getBehindAvatar() {
		return behindAvatar;
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

	private class toggleCamType extends AbstractInputAction
	{

		
		public void performAction(float arg0, Event arg1) {
			behindAvatar=!behindAvatar;
		}
		
	}
	
	public static Matrix3D getTruckPos()
	{
		return trucm;
	}
	public static SceneNode getP1()
	{
		return player1;
	}
	public static SceneNode getP2()
	{
		return player2;
	}
}
