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

	private InetAddress address;
	private int PORT;


	public Sender(int Port,String INET_ADDR) throws UnknownHostException {

		address = InetAddress.getByName(INET_ADDR);
		this.PORT = Port;

	}


	public void cli()
	{
		Scanner sc = new Scanner(System.in);
		boolean stayLoop = true;
		String fileName;
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
				System.out.println("Filename?");
				fileName = sc.next();
				backUp(fileName);
				break;
			case 2:
				System.out.println("Filename?");
				fileName = sc.nextLine();				
				break;
			case 3:
				System.out.println("Filename?");
				fileName = sc.nextLine();				
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
	
	private void backUp(String file) 
	{
		try {
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
						msg.getBytes().length, address, PORT);
				serverSocket.send(msgPacket);

				System.out.println("Server sent packet with msg: " + msg);
				//Thread.sleep(500);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}