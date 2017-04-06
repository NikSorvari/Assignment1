import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import graphicslib3D.Vector3D;
import sage.networking.server.GameConnectionServer;
import sage.networking.server.IClientInfo;

public class GameServerTCP extends GameConnectionServer<UUID>
{
	private static final int BUFSIZE = 0;
	private static int port;
	
	public GameServerTCP(int arg) throws IOException {
		
		super(arg, ProtocolType.TCP);
		this.port = arg;
	}


	public void acceptClient(IClientInfo ci, Object o) // override
	 { 
		String message = (String)o;
		String[] messageTokens = message.split(",");
		if(messageTokens.length > 0)
		{ 
			if(messageTokens[0].compareTo("join") == 0) // received “join”
			{ // format: join,localid
				UUID clientID = UUID.fromString(messageTokens[1]);
				addClient(ci, clientID);
				sendJoinedMessage(clientID, true);
			} 
	
		}	
	 }
	
	private void sendJoinedMessage(UUID clientID, boolean b) {
		// format: join, success or join, failure
		 try
		 { String message = new String("join,");
		 if(b) message += "success";
		 else message += "failure";
		 sendPacket(message, clientID);
		 }
		 catch (IOException e) { e.printStackTrace();
		}
		
	}

	public void processPacket(Object o,  InetAddress senderIP, int sndPort)
	{
		 String message = (String) o;
		 String[] msgTokens = message.split(",");
		 if(msgTokens.length > 0)
		 { 
			 if(msgTokens[0].compareTo("bye") == 0) // receive “bye”
			 { // format: bye,localid
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 sendByeMessages(clientID);
				 removeClient(clientID);
			 }
			 if(msgTokens[0].compareTo("create") == 0) // receive “create”
			 { // format: create,localid,x,y,z
				 UUID clientID = UUID.fromString(msgTokens[1]);
				 String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
				 sendCreateMessages(clientID, pos);
				 sendWantsDetailsMessages(clientID, senderIP, sndPort);
			 }
			 if(msgTokens[0].compareTo("dsfr") == 0) // receive “details for”
			 { // etc….. 
				 
			 }
			if(msgTokens[0].compareTo("move") == 0) // receive “move”
			{
				UUID clientID = UUID.fromString(msgTokens[1]);
				String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
				sendMoveMessages(clientID, pos);
			} 
		 }
	}


	private void sendWantsDetailsMessages(UUID clientID, InetAddress senderIP, int sndPort) {
		// format: wsds, senderIP, sndPort 
		try
		 { String message = new String("wsds," + senderIP +","+ sndPort);
		 forwardPacketToAll(message, clientID);
		 }
		 catch (IOException e) { e.printStackTrace();
		} 
	}

	private void sendCreateMessages(UUID clientID, String[] pos) {
		// format: create, remoteId, x, y, z
		 try
		 { String message = new String("create," + clientID.toString());
		 message += "," + pos[0];
		 message += "," + pos[1];
		 message += "," + pos[2];
		 forwardPacketToAll(message, clientID);
		 }
		 catch (IOException e) { e.printStackTrace();
		} 
		
	}
	
	private void sendMoveMessages(UUID clientID, String[] pos) {
		// format: move, remoteId, x, y, z
		try
		 { String message = new String("move," + clientID.toString());
		 message += "," + pos[0];
		 message += "," + pos[1];
		 message += "," + pos[2];
		 forwardPacketToAll(message, clientID);
		 }
		 catch (IOException e) { e.printStackTrace(); }
	}

	private void sendByeMessages(UUID clientID) {
		try
		{ 
			String message = new String("bye," + clientID.toString()); 
			forwardPacketToAll(message, clientID);
		}
		catch (IOException e) { e.printStackTrace(); }
		
	}
	public static void main(String[] args) throws IOException
	{
		ServerSocket serverSock = new ServerSocket(port);
		Socket clientSock = serverSock.accept();
		handleClient(clientSock); //talk to client until done
		clientSock.close();

		
	}


	private static void handleClient(Socket clientSock) throws IOException {
		InputStream inStream = clientSock.getInputStream();
		OutputStream outStream = clientSock.getOutputStream();
		byte[] data = null;
		inStream.read(data);
		outStream.write(data);
		
	}

}
