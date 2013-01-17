
import java.io.*;
import java.util.Scanner;

/** 
* Read and write a file using an explicit encoding.
* Removing the encoding from this code will simply cause the 
* system's default encoding to be used instead.  
*/
public final class ReadWriteTextFile {
	// PRIVATE 
	private final String fFileName;
	private final String fEncoding;

	/** Constructor. */
	ReadWriteTextFile(String aFileName){
		fEncoding = "UTF-8";
		fFileName = aFileName;
	}
	
	/** Write fixed content to the given file. */
	void write(String content) throws IOException  {
		log("Writing to file named " + fFileName + ". Encoding: " + fEncoding);
		Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding);
		try {
			out.write(content);
		}
		finally {
			out.close();
		}
	}
	
	/** Read the contents of the given file. */
	String read() throws IOException {
		log("Reading from file.");
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
		try {
			while (scanner.hasNextLine()){
				text.append(scanner.nextLine() + NL);
			}
		}
		finally{
			scanner.close();
		}
		log("Text read in: " + text);

		return text.toString();
	}

	private void log(String aMessage){
		System.out.println(aMessage);
	}
}
