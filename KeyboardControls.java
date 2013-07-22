import processing.core.*;
import java.awt.event.*;

class KeyboardControls {
	static boolean[] keys = new boolean[526];
	static PApplet pa;

	static void init(PApplet p) {
		pa=p;
	}

	static boolean checkKey(int k) {
		if (keys.length >= k) {
			return keys[k];  
		}
		return false;
	}

	static void keyPressed(int keyCode){ 
		keys[keyCode] = true;
		if(checkKey(157) && checkKey(KeyEvent.VK_I)) {
			FileChooser.showOpen(new ImageLoader(pa));
		}
		if(checkKey(157) && checkKey(KeyEvent.VK_O)) {
			ExportImport.importData();
		}
		if(checkKey(157) && checkKey(KeyEvent.VK_S)) {
			ExportImport.export(ShapeManager.getShapeCoordinates());
		}
		if(checkKey(157) && checkKey(KeyEvent.VK_N)) {
			ShapeManager.reset();
			ShapeManager.newShape();
			Image.setImage(null,null);
		}

		if (checkKey(KeyEvent.VK_CONTROL)){
			ShapeManager.limitDegrees(true);
		}
		if (checkKey(KeyEvent.VK_BACK_SPACE)){
			ShapeManager.getCurrentShape().removeSelectedPoint();
		}
		if (checkKey(KeyEvent.VK_P)){
			ShapeManager.newShape();
		}
	}

	static void keyReleased(int keyCode) {
		keys[keyCode] = false; 
			ShapeManager.limitDegrees(false);
	}
}