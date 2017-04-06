import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import sage.app.BaseGame;
import sage.networking.IGameConnection.ProtocolType;

public class MyNetworkingClient extends BaseGame
{
	private String serverAddress;
	private int serverPort;
	private Object serverProtocol;
	MyClient thisClient;
	private Matrix3D playerPos = new Matrix3D();

	public MyNetworkingClient(String serverAddr, int sPort)
	 { 
		super();
		this.serverAddress = serverAddr;
		this.serverPort = sPort;
		this.serverProtocol = ProtocolType.TCP;
	 }
	
	protected void initGame()
	{
		// items as before, plus initializing network:
		 try
		 { thisClient = new MyClient(InetAddress.getByName(serverAddress),
		 serverPort, serverProtocol, this); }
		 catch (UnknownHostException e) { e.printStackTrace(); }
		 catch (IOException e) { e.printStackTrace(); }
		 
		 if (thisClient != null) { thisClient.sendJoinMessage(); }
	}
	
	protected void update(float time)
	{
		if (thisClient != null)
		{
			thisClient.processPackets();
			thisClient.sendMoveMessage(getPlayerPosition());
		}
	}
	public void setIsConnected(boolean b) {
		if (!b)
		{
			this.serverAddress = null;
			this.serverPort = 0;
			this.serverProtocol = null;
		}
		
	}
	
	 protected void shutdown()
	 {
		 super.shutdown();
		 if(thisClient != null)
		 { thisClient.sendByeMessage();
		 	try
		 	{ thisClient.shutdown(); } // shutdown() is inherited
		 	catch (IOException e) { e.printStackTrace(); }
		 } 
	 }

	public Vector3D getPlayerPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
