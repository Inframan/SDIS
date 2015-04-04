package connections;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Sender {

	private InetAddress mcAddress;
	private InetAddress mdbAddress;
	private InetAddress mdrAddress;
	private int mcPORT;
	private int mdbPORT;
	private int mdrPORT;


	public Sender(int mcPort,String mcAdd,int mdbPort, String mdbAdd,int mdrPort,String mdrAdd) throws UnknownHostException {

		mcAddress = InetAddress.getByName(mcAdd);
		this.mcPORT = mcPort;
		mdbAddress = InetAddress.getByName(mdbAdd);
		this.mdbPORT = mdbPort;
		mdrAddress = InetAddress.getByName(mdrAdd);
		this.mdrPORT = mdrPort;

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

	private void backUp(Scanner sc) 
	{
		String fileName;
		Integer repDeg;
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
		fileName = sc.nextLine();	
		if (fileName.equals(""))
			fileName = sc.nextLine();
		System.out.println("Replication Degree?");
		repDeg = sc.nextInt();

		byte[] ascii = {0xD, 0xA};
		String crlf = new String(ascii);
		

		////////////////////////NEED TO IMPLEMENT CHUNKNO

		header = "PUTCHUNK 1.0 " + fileName+ " "  + "0"/*CHUNKNO HERE*/+ " " + repDeg.toString() + " " + crlf;


		for(int i = 0; i < 3; i++)///tries do get the desired rep degree 3 times, otherwise moves on
		{
			Main.stored.replace("filename", fileName);
			Main.stored.replace("chunkNo", "0");////CHUNKNO HERE

			System.out.println("I CICLES: " + i);

			try (DatagramSocket serverSocket = new DatagramSocket()) {
				DatagramPacket msgPacket = new DatagramPacket(header.getBytes(),
						header.getBytes().length, mdbAddress, mdbPORT);
				serverSocket.send(msgPacket);

				System.out.println("Server sent packet with msg: " + header);
				//Thread.sleep(500);
				if(receivedStoredChunk(repDeg, fileName, 0/*CHUNKNO HERE*/))
				{
					System.out.println("SENDER: obtained the desired rep degree");
					Main.stored.replace("confirmationCount", "0");
					break;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} 

		}
	}

	public void sendConfirm(String confirmMessage){

		if(confirmMessage.startsWith("STORED"))
			sendConfirmMc(confirmMessage);
		else if(confirmMessage.startsWith("CHUNK"))
			sendConfirmMdr(confirmMessage);


	}

	public void sendConfirmMc(String confirmMessage){
		// Open a new DatagramSocket, which will be used to send the data.
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			DatagramPacket msgPacket = new DatagramPacket(confirmMessage.getBytes(),
					confirmMessage.getBytes().length, mcAddress, mcPORT);
			serverSocket.send(msgPacket);
			System.out.println("Server sent packet with msg: " + confirmMessage);



		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void sendConfirmMdb(String confirmMessage){
		// Open a new DatagramSocket, which will be used to send the data.
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			DatagramPacket msgPacket = new DatagramPacket(confirmMessage.getBytes(),
					confirmMessage.getBytes().length, mdbAddress, mdbPORT);
			serverSocket.send(msgPacket);
			System.out.println("Server sent packet with msg: " + confirmMessage);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void sendConfirmMdr(String confirmMessage){
		// Open a new DatagramSocket, which will be used to send the data.
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			DatagramPacket msgPacket = new DatagramPacket(confirmMessage.getBytes(),
					confirmMessage.getBytes().length, mdrAddress, mdrPORT);
			serverSocket.send(msgPacket);
			System.out.println("Server sent packet with msg: " + confirmMessage);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}


	public boolean receivedStoredChunk(int repDegree,String filename, int chunkNo)
	{
		System.out.println("Waiting for confirmation...");
		int confirmationCount = 0;

		try {




			StoredListener confirmListener = new StoredListener(mcPORT, mcAddress,filename,chunkNo);

			confirmListener.start();
			System.out.println("before");
			synchronized (confirmListener) {
				confirmListener.wait(500);
			}
			System.out.println("after");
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		confirmationCount = Integer.parseInt(Main.stored.get("receivedCount"));
		if(confirmationCount < repDegree)
			return false;
		else
		{
			System.out.println("OBTAINED DESIRED REP DEG");
			return true;
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