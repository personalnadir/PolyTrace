class Camera {
	static int x=0;
	static int y=0;
	static boolean dragging=false;

	static void mouseDragged(int mouseX, int mouseY, int pmouseX, int pmouseY) {
		int vx=mouseX-pmouseX;
		int vy=mouseY-pmouseY;
		
		x+=vx;
		y+=vy;

		dragging=dragging || vx*vx+vy*vy>4*4;
	}

	static void mouseReleased(){
		dragging=false;
	}

	static int getX() {
		return x;
	}

	static int getY() {
		return y;
	}

	static boolean isDragging() {
		return dragging;
	}

	static void reset() {
		x=0;
		y=0;
		dragging=false;
	}
}