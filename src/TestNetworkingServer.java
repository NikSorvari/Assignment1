import java.io.IOException;

public class TestNetworkingServer {
	public static void main(String[] args)
	{ 
		String number = args[1];
		int arg = Integer.parseInt(number);
		try {
			GameServerTCP testTCPServer = new GameServerTCP(arg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
