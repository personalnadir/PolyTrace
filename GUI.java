import processing.core.*;

class GUI {
	static PApplet pa;
	
	static int[][] coords = {{10,10},{10,50},{10,90},{10,130},{10,170},{10,210}}; 
	static PImage[] icons=new PImage[coords.length];
	
	static void init(PApplet p){
		pa=p;
		icons[0]=pa.loadImage("folder.png");
		icons[1]=pa.loadImage("save.png");
		icons[2]=pa.loadImage("picture.png");
		icons[3]=pa.loadImage("polygon.png");
		icons[4]=pa.loadImage("zoomin.png");
		icons[5]=pa.loadImage("zoomout.png");
	}

	static void draw() {
		for (int i=0;i<icons.length;i++) {
			pa.image(icons[i],coords[i][0],coords[i][1]);
		}
	}

	static boolean clicked(int x, int y){
		int selected=-1;
		for (int i=0;i<icons.length;i++) {
			if (x>coords[i][0] && x<coords[i][0]+icons[i].width && y>coords[i][1] && y<coords[i][1]+icons[i].height) {
				selected=i;
				break;
			}
		}
			
		switch (selected) {
			case 0:
				ExportImport.importData();
				break;
			case 1: 
				ExportImport.export(ShapeManager.getShapeCoordinates());
				break;
			case 2:
				FileChooser.showOpen(new ImageLoader(pa));
				break;
			case 3:
				ShapeManager.newShape();
				break;
			case 4:
				ZoomControl.decrease();
				break;
			case 5:
				ZoomControl.increase();
				break;
		}

		return selected!=-1;
	}
}