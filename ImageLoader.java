import java.io.File;
import processing.core.*;

class ImageLoader implements FileSelectionHandler {
	PApplet pa;

	ImageLoader(PApplet p) {
		pa=p;
	}

	public boolean open (File f) {
		if (!f.getName().endsWith("png")) {
			return false;
		}

		// load the image using the given f path
		PImage img = pa.loadImage(f.getPath()); 
		if (img != null) {
			// size the window and show the image 
			// pa.size(img.width,img.height); 
			Image.setImage(img,f.getPath());
			return true;
		} else {
			return false;
		}
	}
}

