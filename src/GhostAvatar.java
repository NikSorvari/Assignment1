import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;
import java.util.UUID;

import sage.event.IEventListener;
import sage.event.IGameEvent;
import sage.renderer.IRenderer;
import sage.scene.SceneNode;
import sage.scene.TriMesh;
import sage.scene.bounding.BoundingVolume;
import sage.scene.state.RenderState;

public class GhostAvatar extends TriMesh implements IEventListener {

	public UUID id;
	
	private static float[] vrts = new float[] {0,1,0,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,-1};
	private static float[] cl = new float[] {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,0,1,1};
	private static int[] triangles = new int[] {0,1,2,0,2,3,0,3,4,0,4,1,1,4,2,4,3,2};
	public GhostAvatar(Color col)
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
	
	public GhostAvatar()
	{
		
	}

	@Override
	protected void applyRenderStatesToSceneNode(Stack<RenderState>[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(IRenderer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalBound(BoundingVolume arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLocalBound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateWorldBound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleEvent(IGameEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
