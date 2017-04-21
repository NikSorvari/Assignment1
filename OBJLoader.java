import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.model.loader.IModelLoader;
import sage.scene.TriMesh;

public class OBJLoader implements IModelLoader { // Receives a String that
													// specifies a file
													// containing a WaveFront
													// OBJ file.
													// Reads the OBJ file and
													// returns a corresponding
													// “TriMesh” object.

	public TriMesh loadModel(String modelFilename) {
		TriMesh theModel = new TriMesh();
		BufferedReader br = null; // get a BufferedReader wrapping the file
		try {
			br = new BufferedReader(new FileReader(modelFilename));
		} catch (FileNotFoundException e)

		{
			throw new RuntimeException("FileNotFound: '" + modelFilename + "'");
		}
		String inputLine = null;
		try {
			inputLine = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (inputLine != null) {
			switch (getCommand(inputLine)) {
			case "v":
				addVertex(inputLine, theModel);
				break;
			case "vt":
				addTextureCoord(inputLine, theModel);
				break;
			case "vn":
				addNormal(inputLine, theModel);
				break;
			case "f":
				addFace(inputLine, theModel);
				break;
			case "mtllib":
				processMaterialLibCommand(inputLine);
				break;
			case "usemtl":
				processUseMaterialCommand(inputLine);
				break;
			default:
				;
			}
			try {
				inputLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ...code here to do OBJ-specific post-processing to finalize the
		// TriMesh
		return theModel;
	}

	private String getCommand(String inputLine) {
		String[] messageTokens = inputLine.split(" ");
		return messageTokens[0];
	}

	private void processUseMaterialCommand(String inputLine) {
		String[] messageTokens = inputLine.split(" ");

	}

	private void processMaterialLibCommand(String inputLine) {
		String[] messageTokens = inputLine.split(" ");
		

	}

	private void addFace(String inputLine, TriMesh theModel) {
		String[] messageTokens = inputLine.split(" ");
		int[] fBuff = new int[3];
		IntBuffer vBuff;

		String vstring = messageTokens[1];
		fBuff[0] = Integer.parseInt(vstring);

		vstring = messageTokens[2];
		fBuff[1] = Integer.parseInt(vstring);

		vstring = messageTokens[3];
		fBuff[2] = Integer.parseInt(vstring);

		vBuff = IntBuffer.wrap(fBuff);
		if (theModel.getFaceMaterialIndices() == null)
			theModel.setFaceMaterialIndices(vBuff);
		else {
			IntBuffer oldVerts = theModel.getFaceMaterialIndices();
			oldVerts.position(0);
			vBuff.position(0);
			theModel.setFaceMaterialIndices(
					theModel.getFaceMaterialIndices().allocate(theModel.getFaceMaterialIndices().capacity() + vBuff.capacity()));
			theModel.getFaceMaterialIndices().put(oldVerts);
			theModel.getFaceMaterialIndices().put(vBuff);
		}

	}

	private void addNormal(String inputLine, TriMesh theModel) {
		String[] messageTokens = inputLine.split(" ");
		float[] fBuff = new float[3];
		FloatBuffer vBuff;

		String vstring = messageTokens[1];
		fBuff[0] = Float.parseFloat(vstring);

		vstring = messageTokens[2];
		fBuff[1] = Float.parseFloat(vstring);

		vstring = messageTokens[3];
		fBuff[2] = Float.parseFloat(vstring);

		vBuff = FloatBuffer.wrap(fBuff);
		if (theModel.getNormalBuffer() == null)
			theModel.setNormalBuffer(vBuff);
		else {
			FloatBuffer oldVerts = theModel.getNormalBuffer();
			oldVerts.position(0);
			vBuff.position(0);
			theModel.setNormalBuffer(
					theModel.getNormalBuffer().allocate(theModel.getNormalBuffer().capacity() + vBuff.capacity()));
			theModel.getNormalBuffer().put(oldVerts);
			theModel.getNormalBuffer().put(vBuff);
		}

	}

	private void addTextureCoord(String inputLine, TriMesh theModel) {
		String[] messageTokens = inputLine.split(" ");
		float[] fBuff = new float[3];
		FloatBuffer vBuff;

		String vstring = messageTokens[1];
		fBuff[0] = Float.parseFloat(vstring);

		vstring = messageTokens[2];
		fBuff[1] = Float.parseFloat(vstring);

		vstring = messageTokens[3];
		fBuff[2] = Float.parseFloat(vstring);

		vBuff = FloatBuffer.wrap(fBuff);
		if (theModel.getTextureBuffer() == null)
			theModel.setTextureBuffer(vBuff);
		else {
			FloatBuffer oldVerts = theModel.getTextureBuffer();
			oldVerts.position(0);
			vBuff.position(0);
			theModel.setTextureBuffer(
					theModel.getTextureBuffer().allocate(theModel.getTextureBuffer().capacity() + vBuff.capacity()));
			theModel.getTextureBuffer().put(oldVerts);
			theModel.getTextureBuffer().put(vBuff);
		}

	}

	@SuppressWarnings("static-access")
	private void addVertex(String inputLine, TriMesh theModel) {
		String[] messageTokens = inputLine.split(" ");
		float[] fBuff = new float[3];
		FloatBuffer vBuff;

		String vstring = messageTokens[1];
		fBuff[0] = Float.parseFloat(vstring);

		vstring = messageTokens[2];
		fBuff[1] = Float.parseFloat(vstring);

		vstring = messageTokens[3];
		fBuff[2] = Float.parseFloat(vstring);

		vBuff = FloatBuffer.wrap(fBuff);
		if (theModel.getVertexBuffer() == null)
			theModel.setVertexBuffer(vBuff);
		else {
			FloatBuffer oldVerts = theModel.getVertexBuffer();
			oldVerts.position(0);
			vBuff.position(0);
			theModel.setVertexBuffer(
					theModel.getVertexBuffer().allocate(theModel.getVertexBuffer().capacity() + vBuff.capacity()));
			theModel.getVertexBuffer().put(oldVerts);
			theModel.getVertexBuffer().put(vBuff);
		}

	}
}