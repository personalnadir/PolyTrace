/**
 * filechooser taken from http://processinghacks.com/hacks:filechooser
 * @author Tom Carden
 */

import javax.swing.*;
import processing.core.*;
import java.io.*;

// set system look and feel 
class FileChooser {
	static PApplet pa;
	static void init(PApplet p) {
		pa=p;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	private static void handleReturnValue(int returnVal, FileSelectionHandler fsh, JFileChooser fc) {
		if (returnVal == JFileChooser.APPROVE_OPTION) { 
			File file = fc.getSelectedFile(); 
			// see if it's an image 
			// (better to write a function and check for all supported extensions) 
			if (fsh.open(file)) {
				return;
			} else { 
				// just print the contents to the console 
				// note: loadStrings can take a Java File Object too 
				String lines[] = pa.loadStrings(file); 
				for (int i = 0; i < lines.length; i++) { 
					System.out.println(lines[i]);  
				} 
			} 
		} else { 
			System.out.println("Open command cancelled by user."); 
		}
	}

	static void showOpen(FileSelectionHandler fsh) {
		// create a file chooser 
		final JFileChooser fc = new JFileChooser(); 
		// in response to a button click: 
		int returnVal = fc.showOpenDialog(pa); 
		
		handleReturnValue(returnVal,fsh,fc);
		return;
	}

	static void showSave(FileSelectionHandler fsh) {
		// create a file chooser 
		final JFileChooser fc = new JFileChooser(); 
		// in response to a button click: 
		int returnVal = fc.showSaveDialog(pa); 
		
		handleReturnValue(returnVal,fsh,fc);
		return;
	}
}