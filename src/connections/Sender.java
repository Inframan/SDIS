package connections;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Sender extends Thread{
    
    private InetAddress address;
    private int PORT;
    

	public Sender(int Port,String INET_ADDR) throws UnknownHostException {

		address = InetAddress.getByName(INET_ADDR);
		this.PORT = Port;

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