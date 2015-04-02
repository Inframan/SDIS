package connections;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Sender {

	private InetAddress mcAddress;
	private int mcPORT;


	public Sender(int mcPort,String mcAdd,int mdbPort, String mdbAdd,int mdrPort,String mdrAdd) throws UnknownHostException {

		mcAddress = InetAddress.getByName(mcAdd);
		this.mcPORT = mcPort;

	}


	public void cli()
	{
		Scanner sc = new Scanner(System.in);
		boolean stayLoop = true;
		do
		{		

			System.out.println("What do you to do?\n"
					+ "1 - Backup File\n"
					+ "2 - Restore File\n"
					+ "3 - Delete File\n"
					+ "4 - Space Reclaming\n"
					+ "5 - Exit");

			int input = sc.nextInt();

			switch (input) {
			case 1:
				backUp(sc);
				break;
			case 2:			
				break;
			case 3:	
				break;
			case 4:			
				break;			
			case 5:
				stayLoop = false;
				break;
			default:
				break;
			}

			
		}while(stayLoop);

		sc.close();

	}


	private String convertHashToString(byte[] hash)
	{
		String ret = "";
		for(int i = 0; i < hash.length;i++)
		{


		}
		return ret;
	}

	private void backUp(Scanner sc) 
	{
		String fileName;
		String version;
		String repDeg;
		String header;
		/*try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(file.getBytes("ASCII")); // Change this to "UTF-16" if needed
			byte[] hashed = md.digest();

			System.out.println(hashed);





		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */

		//<MessageType> <Version> <FileId> <ChunkNo> <ReplicationDeg> <CRLF
		System.out.println("File Name?");
		fileName = sc.next();	
		System.out.println("Version?");
		version = sc.next();
		System.out.println("Replication Degree?");
		repDeg = sc.next();

		header = "PUTCHUNK " + version + " " + fileName+ " "  + "0"+ " " + repDeg + " "+ 0xD + 0xA;


		try (DatagramSocket serverSocket = new DatagramSocket()) {
		//	for (int i = 0; i < 3; i++) {
			

				// Create a packet that will contain the data
				// (in the form of bytes) and send it.
				DatagramPacket msgPacket = new DatagramPacket(header.getBytes(),
						header.getBytes().length, mcAddress, mcPORT);
				serverSocket.send(msgPacket);

				System.out.println("Server sent packet with msg: " + header);
				//Thread.sleep(500);
			//}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}


	public void run() {
		// Get the address that we are going to connect to.

		// Open a new DatagramSocket, which will be used to send the data.
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			for (int i = 0; i < 3; i++) {
				String msg = "vtf huehhue " + i;

				// Create a packet that will contain the data
				// (in the form of bytes) and send it.
				
				DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
						msg.getBytes().length, mcAddress, mcPORT);
				serverSocket.send(msgPacket);

				System.out.println("Server sent packet with msg: " + msg);
				//Thread.sleep(500);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}