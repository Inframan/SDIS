package fileManagment;
public class Parser {
	
	public enum Headers{
		 PUTCHUNK, 
		 GETCHUNK, 
		 STORED,
		 DELETE, 
		 CHUNK, 
		 REMOVED,
		 TRASH};
	
	private Headers subPro;
	private String message;

	public Parser(String message) {
		this.message = message;
		System.out.println(message);
		if(message.startsWith("PUTCHUNK"))
			this.subPro = Headers.valueOf("PUTCHUNK");
		else if(message.startsWith("GETCHUNK"))
			this.subPro = Headers.valueOf("GETCHUNK");
		else if(message.startsWith("STORED"))
			this.subPro = Headers.valueOf("STORED");
		else if(message.startsWith("DELETE"))
			this.subPro = Headers.valueOf("DELETE");
		else if(message.startsWith("CHUNK"))
			this.subPro = Headers.valueOf("CHUNK");
		else if(message.startsWith("REMOVED"))
			this.subPro = Headers.valueOf("REMOVED");
		else
			this.subPro = Headers.valueOf("TRASH");

		
	}	 
		 
	public String assignChunk()
	{
		String confirmMessage = "";
		switch(subPro)
		{
		case PUTCHUNK:
			System.out.println("Worked");
			confirmMessage = confirmBackup(message);
			break;
		case GETCHUNK:
			break;
		case STORED:
			break;
		case DELETE:
			break;
		case CHUNK:
			break;
		case REMOVED:
			break;
		case TRASH:
			System.out.println("Message doesn't fit in Protocol");
			break;
		default:
			System.out.println("Work in progress...");
			break;
		
		
		}
		
		return confirmMessage;
	}
	 
	
	private String confirmBackup(String msg){
		String stored =  "STORED";
		String[] tokens= msg.split(" ");
		stored += " " + tokens[1] +" " + tokens[2] +" " + tokens[3] + " " + tokens[5];
		System.out.println(stored);
		return stored;
	}
	

}
