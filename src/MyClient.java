import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.networking.client.GameConnectionClient;
import sage.scene.SceneNode;

public class MyClient extends GameConnectionClient
{
	private MyGame game;
	 private UUID id;
	 private Vector<GhostAvatar> ghostAvatars;
	 private static InetAddress hostIP;
	 private static int port;
	 
	 public MyClient(InetAddress remAddr, int remPort, ProtocolType pType, MyGame game) throws IOException {
		 super(remAddr, remPort, pType);
		 this.game = game;
		 this.id = UUID.randomUUID();
		 this.ghostAvatars = new Vector<GhostAvatar>();
		 this.hostIP = remAddr;
		 this.port = remPort;
		 
	}

	public MyClient(InetAddress byName, int serverPort, Object serverProtocol, MyNetworkingClient game2) throws IOException {
		super(byName, serverPort, (ProtocolType) serverProtocol);
	}

	 protected void processPacket (Object msg) // override
	 { // extract incoming message into substrings. Then process:
		 String[] msgTokens = ((String) msg).split(",");
		 if(msgTokens[0].compareTo("join") == 0) // receive “join”
		 { // format: join, success or join, failure
			 if(msgTokens[1].compareTo("success") == 0)
			 { game.setIsConnected(true);
			 	sendCreateMessage(game.getPlayerPosition());
			 }
			 if(msgTokens[1].compareTo("failure") == 0)
				 game.setIsConnected(false);
		 }
		 if(msgTokens[0].compareTo("bye") == 0) // receive “bye”
		 { // format: bye, remoteId
			 UUID ghostID = UUID.fromString(msgTokens[1]);
			 removeGhostAvatar(ghostID);
		 }
		 if (msgTokens[0].compareTo("create") == 0 ) // receive “create”
		 { // format: create, remoteId, x,y,z
			 UUID ghostID = UUID.fromString(msgTokens[1]);
			 String[] ghostPosition = {msgTokens[2], msgTokens[3], msgTokens[4]};
			// extract ghost x,y,z, position from message, then:
			 createGhostAvatar(ghostID, ghostPosition );
		 }
		 if(msgTokens[0].compareTo("dfsr") == 0) // receive “details for”
		 { // format: dsfr, remoteId, x,y,z
			 UUID ghostID = UUID.fromString(msgTokens[1]);
			 String[] ghostPosition = {msgTokens[2], msgTokens[3], msgTokens[4]};
			
		 }
		 if(msgTokens[0].compareTo("wsds") == 0) // receive “wants…”
		 { 
			 sendDetailsForMessage(game.getPlayerPosition());
			 
		 }
		 if(msgTokens[0].compareTo("move") == 0) // receive “move”
		 { 
			 UUID ghostID = UUID.fromString(msgTokens[1]);
			 String[] ghostPosition = {msgTokens[2], msgTokens[3], msgTokens[4]};
			 moveGhostAvatar(ghostID, ghostPosition);
		 }
	}
	 
	private void createGhostAvatar(UUID ghostID, String[] ghostPosition) {
		
		String[] coordX = ghostPosition[0].split(".");
		String[] coordY = ghostPosition[1].split(".");
		String[] coordZ = ghostPosition[2].split(".");
		int cx = Integer.parseInt(coordX[0]);
		int cy = Integer.parseInt(coordY[0]);
		int cz = Integer.parseInt(coordZ[0]);
		Vector3D ghostPos = new Vector3D(cx, cy, cz);
		game.createGhost(ghostID, ghostPos);
		GhostAvatar ghost = new GhostAvatar();
		ghost.id = ghostID;
		ghostAvatars.add(ghost);
		
	}

	private void removeGhostAvatar(UUID ghostID) {
		// TODO Auto-generated method stub
		
	}
	
	private void moveGhostAvatar(UUID ghostID, String[] ghostPosition) {
		String[] coordX = ghostPosition[0].split(".");
		String[] coordY = ghostPosition[1].split(".");
		String[] coordZ = ghostPosition[2].split(".");
		int cx = Integer.parseInt(coordX[0]);
		int cy = Integer.parseInt(coordY[0]);
		int cz = Integer.parseInt(coordZ[0]);
		Vector3D ghostPos = new Vector3D(cx, cy, cz);
		
		game.moveGhostAvatar(ghostID, ghostPos);
	}

	private void sendCreateMessage(Vector3D playerPosition) {
		// format: (create, localId, x,y,z)
		 try
		 { String message = new String("create," + id.toString());
		message += "," + playerPosition.getX()+"," + playerPosition.getY() + "," + playerPosition.getZ();
		 sendPacket(message);
		 }
		 catch (IOException e) { e.printStackTrace(); }
		
	}

	public void sendJoinMessage() 
	{ // format: join, localId
		 try
		 { sendPacket(new String("join," + id.toString())); }
		 catch (IOException e) { e.printStackTrace(); }
	}

	public void sendByeMessage() {

		try
		{ sendPacket(new String("bye," + id.toString())); }
		catch (IOException e) { e.printStackTrace(); }
		game.setIsConnected(false);
	}
	
	public void sendDetailsForMessage(Vector3D pos)
	{ 
		try
		 { sendPacket(new String("dfsr,"+this.id+"," +pos.getX()+"," +pos.getY()+"," +pos.getZ() )); }
		 catch (IOException e) { e.printStackTrace(); }
	}
	
	public void sendMoveMessage(Vector3D pos)
	{
		try
		 { sendPacket(new String("move,"+this.id+"," +pos.getX()+"," +pos.getY()+"," +pos.getZ() )); }
		 catch (IOException e) { e.printStackTrace(); }
	}
	

}
