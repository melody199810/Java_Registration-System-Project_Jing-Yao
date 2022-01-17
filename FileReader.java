
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This class has one function that will read a file (based on the given FileName) and will return an ArrayList of String objects
 * where each string represents one line from the file. 
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * ITP 265, 
 * Assignment ##
 */
public class FileReader {
	/**
	 * This function will read from the given file (provided it is found) and return an 
	 * ArrayList of Strings, with each spot in the list holding one line from the file
	 * @param fileName
	 * @return array of Strings, each String is one line from the file.
	 */
	public static ArrayList<String> readFile(String fileName) {

		ArrayList<String> fileLines = new ArrayList<>();
		int num = 0;

		try(FileInputStream fis = new FileInputStream(fileName);
				Scanner scan = new Scanner(fis))
		{ 
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				fileLines.add(line);
			}
		}
		catch (FileNotFoundException e) {
			//System.err.println("File not found: " + fileName);
			//e.printStackTrace();
			//System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} 
		return fileLines;
	}
}

