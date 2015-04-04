package fileManagment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class FileSplit {

	String fileData;
	Map<Integer, byte[]> fileMap;

	public FileSplit(String fileName) throws FileNotFoundException {

		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			fileData = in.readLine();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void makeMap()
	{
		
		byte[] chunk = fileData.getBytes();
		
		System.out.println(chunk.length);
		
		
		int j = 0;
		for(; j < chunk.length/64000 ; j++)
		{
			fileMap.put(j, Arrays.copyOfRange(chunk, j*64000 , j*64000 +64000));
		}	
		
		if(chunk.length%64000 == 0)
			fileMap.put(j,new byte[0]);
	
		
	}
	
	

}
