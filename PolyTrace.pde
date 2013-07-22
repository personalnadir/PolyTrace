void setup(){
	size(1000, 1000,P2D);
	stroke(255);
	ShapeManager.init(this);
	FileChooser.init(this);
	GUI.init(this);
	Image.init(this);
	ExportImport.init(this);
	KeyboardControls.init(this);
	Runtime r=Runtime.getRuntime();
}

void draw(){
	background(192, 65, 0);
	
	pushMatrix();

	translate(width/2,height/2);
	float zoom=ZoomControl.getZoom();
	scale(zoom,zoom);
	
	translate(Camera.getX(), Camera.getY());
	Image.draw();
	ShapeManager.draw();
	popMatrix();
	
	GUI.draw();
}

void mouseDragged() {
	int cx=Camera.getX();
	int cy=Camera.getY();
	
	boolean handled=ShapeManager.mouseDragged(mouseX-width/2,mouseY-height/2,pmouseX-width/2,pmouseY-height/2);
	if (!handled) {
		Camera.mouseDragged(mouseX,mouseY,pmouseX,pmouseY);
	}
}

void mouseReleased() {
	if (Camera.isDragging()) {
		Camera.mouseReleased();
		return;
	}
	if (GUI.clicked(mouseX,mouseY)) {
		return;
	}
	ShapeManager.mouseReleased(mouseX-width/2,mouseY-height/2,pmouseX-width/2,pmouseY-height/2);
}

void keyPressed(){
	KeyboardControls.keyPressed(keyCode);
}


void keyReleased(){
	KeyboardControls.keyReleased(keyCode);
}

void mousePressed(){
}
