
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;

import sage.scene.TriMesh;

public class MyTruck extends TriMesh
{
	private static float[] vrts = new float[] {-1,1,-1, 
												-1,1,1, 
												1,0.5f,-1, 
												1,0.5f,1,
												-1,-1,1, 
												1,-1,1, 
												1,-1,-1, 
												-1,-1,-1};
	
	private static float[] cl = new float[] {1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											1,0,0,1,
											};
	
	private static int[] triangles = new int[] {0,1,2, 1,2,3, 
												0,1,4, 0,4,7,
												1,4,5, 1,3,5,
												2,3,5, 2,5,6,
												0,2,6, 0,6,7,
												4,5,6, 4,6,7};
	public MyTruck(Color col)
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
}
