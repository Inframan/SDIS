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

			subPro = findProtocol(message);
		}	

		public Headers findProtocol(String message)
		{
			Headers ret;
			if(message.startsWith("PUTCHUNK"))
				ret = Headers.valueOf("PUTCHUNK");
			else if(message.startsWith("GETCHUNK"))
				ret = Headers.valueOf("GETCHUNK");
			else if(message.startsWith("STORED"))
				ret = Headers.valueOf("STORED");
			else if(message.startsWith("DELETE"))
				ret = Headers.valueOf("DELETE");
			else if(message.startsWith("CHUNK"))
				ret = Headers.valueOf("CHUNK");
			else if(message.startsWith("REMOVED"))
				ret = Headers.valueOf("REMOVED");
			else
				ret = Headers.valueOf("TRASH");

			return ret;
		}


		public String assignChunk()
		{
			String confirmMessage = "";
			switch(subPro)
			{
			case PUTCHUNK:
				System.out.println("Received PUTCHUNK");
				confirmMessage = confirmBackup(message);
				break;
			case GETCHUNK:
				System.out.println("Received GETCHUNK");
				confirmMessage = confirmRestore(message);
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
			
			if(tokens.length < 6)//NOT IN PROTOCOL
			{
				System.out.println("BackUp Message Not In Accordance Protocol: " + msg);
				return "";
			}
			
			stored += " " + tokens[1] +" " + tokens[2] +" " + tokens[3] + " " + tokens[5];
			System.out.println(stored);
			return stored;
		}


		private String confirmRestore(String msg)
		{
			//CHUNK <Version> <FileId> <ChunkNo> <CRLF><CRLF><Body>

			String stored =  "CHUNK";
			String[] tokens= msg.split(" ");
			if(tokens.length < 5)//NOT IN PROTOCOL
			{
				System.out.println("BackUp Message Not In Accordance Protocol: " + msg);
				return "";
			}
			stored += " " + tokens[1] +" " + tokens[2] +" " + tokens[3]+ " "+tokens[4];
			System.out.println("STORED MESSAGE: " + stored);

			return stored;
		}


}
