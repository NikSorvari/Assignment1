
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import sage.event.IEventListener;
import sage.event.IGameEvent;
import sage.scene.TriMesh;

public class MyPlant extends TriMesh implements IEventListener
{
	private int crashCount;
	private static boolean collected=false;
	
	private static float[] vrts = new float[] {0,-1,0,
											   -1,0,0,
												0,0,1,
												1,0,0,
												0,0,-1,
												0,1,0,};
	
	private static float[] cl = new float[] {0,1,0,1,
											0,1,0,1,
											0,1,0,1,
											0,1,0,1,
											0,1,0,1,
											0,1,0,1,};
	
	private static int[] triangles = new int[] {0,1,2, 0,2,3,
												0,3,4, 0,4,1,
												5,1,2, 5,2,3,
												5,3,4, 5,4,1,
												};
	public MyPlant(Color col)
	{ int i;
		FloatBuffer vertBuf =
				com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrts);
		FloatBuffer colorBuf =
				com.jogamp.common.nio.Buffers.newDirectFloatBuffer(cl);
		IntBuffer triangleBuf =
				com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
		this.setVertexBuffer(vertBuf);
		this.setColorBuffer(colorBuf);
		this.setIndexBuffer(triangleBuf); 
	}
	
	@Override
	public boolean handleEvent(IGameEvent event) {
		Matrix3D m = new Matrix3D();
		m = MyGame.getTruckPos();
		m.translate(0, MyGame.getScore(), 0);
		CrashEvent cevent = (CrashEvent) event;
		crashCount = cevent.getWhichCrash();
		
		if (this.getWorldBound().contains(new Point3D(MyGame.getP1().getWorldTranslation().elementAt(0, 3),
				MyGame.getP1().getWorldTranslation().elementAt(1, 3),
				MyGame.getP1().getWorldTranslation().elementAt(2, 3))))
			{
				this.setLocalTranslation(m);
				this.collected=true;
			}
		else 
		{/*
			if (this.getWorldBound().contains(new Point3D(MyGame.getP2().getLocalTranslation().elementAt(0, 0),
					MyGame.getP2().getLocalTranslation().elementAt(1, 1),
					MyGame.getP2().getLocalTranslation().elementAt(2, 2))))
				{
					this.setLocalTranslation(m);
					this.collected=true;
				}*/
		};
		return true;
		
	}
	
	public boolean getCollected() {
		return this.collected;
	}
}
