package fileManagment;

import connections.Main;

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
				confirmMessage = confirmBackup();
				break;
			case GETCHUNK:
				System.out.println("Received GETCHUNK");
				confirmMessage = confirmRestore();
				break;
			case STORED:
				//Stored Listener wil handle this
				//System.out.println("Received STORED");
				//confirmStored();
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


		public boolean confirmStored()
		{
			String[] tokens= message.split(" ");
			byte ascii[] = {0xD, 0xA}; 
			String crlf = new String(ascii); 


			if( tokens[2].equals(Main.stored.get("filename"))
					&& tokens[3].equals(Main.stored.get("chunkNo"))
					&& tokens[4].equals(crlf)
					)
			{
				//	notify();
				return true;

			}		
			else
			{
				if(! (tokens[2].equals(Main.stored.get("filename"))))
				{
					System.out.println("FAILED FILENAME: " + Main.stored.get("filename"));
					System.out.println("TOKENS2: " +tokens[2]);
				}	
				if( ! ( tokens[3].equals(Main.stored.get("chunkNo"))))
				{
					System.out.println("FALIED CHUNKNO: "+ Main.stored.get("chunkNo"));
					System.out.println("TOKENS 3: " + tokens[3]);

				}

				if(! (tokens[4].equals(crlf)))
				{
					byte[] b = crlf.getBytes();
					byte[] b2 = tokens[4].getBytes();
					System.out.println("FAILED CRLF: "+ b[0] +" " + b[1]);
					System.out.println("TOKENS 4: "+ b2[0] + " " +b[1]);
				}
				return false;
			}

		}


		private String confirmBackup(){
			String stored =  "STORED";
			String[] tokens= message.split(" ");

			if(tokens.length < 6)//NOT IN PROTOCOL
			{
				System.out.println("BackUp Message Not In Accordance Protocol: " + message);
				return "";
			}

			stored += " " + tokens[1] +" " + tokens[2] +" " + tokens[3] + " " + tokens[5];
			System.out.println(stored);
			return stored;
		}


		private String confirmRestore()
		{
			//CHUNK <Version> <FileId> <ChunkNo> <CRLF><CRLF><Body>

			String stored =  "CHUNK";
			String[] tokens= message.split(" ");
			if(tokens.length < 5)//NOT IN PROTOCOL
			{
				System.out.println("BackUp Message Not In Accordance Protocol: " + message);
				return "";
			}
			stored += " " + tokens[1] +" " + tokens[2] +" " + tokens[3]+ " "+tokens[4];
			System.out.println("STORED MESSAGE: " + stored);

			return stored;
		}


}
