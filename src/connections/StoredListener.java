package connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


import fileManagment.Parser;

public class StoredListener extends Thread {

	private InetAddress address;
	private int PORT;
	private byte[] buffer = new byte[256];

	public StoredListener(int Port, InetAddress INET_ADDR) {

		address = INET_ADDR;
		this.PORT = Port;

	}

	public void run() {
		Parser parseMsg;

		boolean repeat;
		do
		{
		
		try (MulticastSocket clientMC = new MulticastSocket(PORT)) {

			clientMC.joinGroup(address);
			
		

			DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
			clientMC.receive(msgPacket);

			String msg = new String(buffer, 0, buffer.length);

			parseMsg = new Parser(msg);

			repeat = !(parseMsg.confirmStored());

			buffer = new byte[256];
		} catch (IOException ex) {
			System.out.println("Error in Stored Listener:");
			ex.printStackTrace();
			
			repeat = false;
		}
		
		}while(repeat);
	}

}
