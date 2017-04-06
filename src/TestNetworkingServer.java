import java.io.IOException;

public class TestNetworkingServer
{ 
	public static void main(String[] args) throws IOException
	{ 
		String number = args[0];
		int arg = Integer.parseInt(number);
		GameServerTCP testTCPServer = new GameServerTCP(arg); 
	}
}