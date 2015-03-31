package connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import fileManagment.Parser;

public class Listener extends Thread {


	private InetAddress address;
	private int PORT;
	private byte[] buffer = new byte[256];

	public Listener(int Port,String INET_ADDR) throws UnknownHostException {

		address = InetAddress.getByName(INET_ADDR);
		this.PORT = Port;

	}

	public void run()
	{		
		Parser parseMsg;
		
		try (MulticastSocket clientMC = new MulticastSocket(PORT)) {

			clientMC.joinGroup(address);

			while (true) {

				DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
				clientMC.receive(msgPacket);

				String msg = new String(buffer, 0, buffer.length);
				
				
				parseMsg = new Parser(msg);
				
				parseMsg.assignChunk();
				
				buffer = new byte[256];
			}
		} catch (IOException ex) {
			System.out.println("Error in Listener:");
			ex.printStackTrace();
		}
	}
	
	
	
}
