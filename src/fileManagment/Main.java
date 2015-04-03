package fileManagment;

import java.io.FileNotFoundException;

public class Main {

	static public void main(String args[]) {

		FileSplit split;
		try {
			split = new FileSplit("C:/Users/José/Desktop/SDIS.txt");
			split.makeMap();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
