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
	private String filename;
	private Integer chunkNo;

	public StoredListener(int Port, InetAddress INET_ADDR, String filename, int chunkNo) {

		address = INET_ADDR;
		this.PORT = Port;
		this.filename = filename;
		this.chunkNo = chunkNo;

	}

	public void run() {
		Parser parseMsg;

		boolean success = true;
		boolean repeat;
		do
		{

			try (MulticastSocket clientMC = new MulticastSocket(PORT)) {

				clientMC.joinGroup(address);



				DatagramPacket msgPacket = new DatagramPacket(buffer, buffer.length);
				clientMC.receive(msgPacket);

				String msg = new String(buffer, 0, buffer.length);

				parseMsg = new Parser(msg);
				if(!  (Main.stored.get("chunkNo").equals(chunkNo.toString()) && Main.stored.get("filename").equals(filename)))
				{
					System.out.println("StoredListener: CHANGED PARAMETERS");
					success = false;
					break;
				}
				repeat = !(parseMsg.confirmStored());

				

				buffer = new byte[256];
			} catch (IOException ex) {
				System.out.println("Error in Stored Listener:");
				ex.printStackTrace();

				repeat = false;
			}

		}while(repeat);

		System.out.println("NOT REPEATING");
		if(success)
		{
			Integer count = Integer.parseInt(Main.stored.get("receivedCount"));
			count++;
			Main.stored.replace("receivedCount", count.toString());
		}
	}

}
