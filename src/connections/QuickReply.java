package connections;


public class QuickReply extends Thread {
	
	
	private String confirm;
	private Sender client;

	public QuickReply(String confirm,Sender client) {
		this.client = client;
		this.confirm = confirm;
	}

	public void run()
	{	
		
		client.sendConfirm(confirm);
		
	}

}
