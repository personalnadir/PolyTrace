import processing.core.*;

class Image {
	static PApplet pa;
	static PImage img=null;
	static String path;

	static void init(PApplet p){
		pa=p;
	}

	static void draw() {
		if (img==null) {
			return;
		}
		pa.imageMode(PApplet.CENTER);
		pa.image(img,0,0);
		pa.imageMode(PApplet.CORNER);
	}

	static void setImage(PImage i, String p){
		img=i;
		path=p;
	}

	static String getPath() {
		return path;
	}
}