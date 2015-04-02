package connections;

import java.net.UnknownHostException;


public class Main {


	public static void main(String Args[]) throws UnknownHostException {

		if(Args.length != 6)
			System.out.println("Function args:  <MCAddr> <MCPort> <MDBAddr> <MDBPort> <MDRAddr> <MDRPort>");
		else
		{
			
			String MCaddress = Args[0];
			int MCPort = Integer.parseInt(Args[1]);
			String MDBaddress = Args[2];
			int MDBPort = Integer.parseInt(Args[3]);
			String MDRaddress = Args[4];
			int MDRPort = Integer.parseInt(Args[5]);

			Sender client = new Sender(MCPort, MCaddress,MDBPort,MDBaddress,MDRPort,MDRaddress);

			Listener mcThread = new Listener(MCPort,MCaddress, client);
			Listener mdbThread = new Listener(MDBPort,MDBaddress, client);
			//Listener mdrThread = new Listener(MDRPort,MDRaddress, client);
			
			mcThread.start();
			mdbThread.start();
			
			
			//mdbThread.start();
			//mdrThread.start();
			client.cli();
			
		}

		

	}

}
