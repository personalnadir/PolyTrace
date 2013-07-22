import processing.core.*;
import java.util.Stack;
import java.util.ArrayList;

class Shape {
	PShape s;
	PShape handles;
	PApplet pa;
	Stack<PVector> handleStack=new Stack<PVector>();
	ArrayList<PVector> points=new ArrayList<PVector>();
	boolean drawInFocus=false;

	Shape(PApplet p){
		pa=p;
		createPolygon();
		createHandles();
	}

	void draw(boolean current){
		pa.shape(s);
		pa.shape(handles);
	}

	void inFocus(boolean yes){
		drawInFocus=yes;
		update();
	}

	private void colourFocussed(){
		s.stroke(255,245,230);
	}

	private void colourOutOfFocus(){
		s.stroke(69,23,47,64);
	}

	private void colourConcave(){
		s.fill(255,0,0,64);
	}

	private void colourConvex(){
		s.fill(42,71,94, 64);
	}

	private void colourShapeType(){
		if (Geometry.isConvex(points)){
			colourConvex();
		} else {
			colourConcave();
		}
	}

	void setPointSelected(PVector p) {
		while (handleStack.indexOf(p)>=0) {
			handleStack.remove(handleStack.indexOf(p));
		}

		handleStack.push(p);
	}	

	private void createPolygon(){
		s=pa.createShape();
		s.beginShape();

		s.strokeWeight(2);
	
		if (drawInFocus) colourFocussed();
		else colourOutOfFocus();
		colourShapeType();

		for (PVector v : points) {
			s.vertex(v.x,v.y);
		}
		s.endShape(PShape.CLOSE);
	}

	private void createHandles(){
		handles=pa.createShape(PShape.GROUP);
		pa.ellipseMode(PShape.CENTER);
		pa.strokeWeight(2);
		pa.fill(42,71,94, 64);

    PVector selected=handleStack.isEmpty()? null : handleStack.peek();
    PVector previous=getPrevious();
    for (PVector v : points) {
			if (selected==v && drawInFocus) pa.stroke(16,159,145);
			else if (previous==v && drawInFocus) pa.stroke(16/2,159/2,145/2);
			else pa.stroke(42,71,94, 64);
			
			PShape handle=pa.createShape(PShape.ELLIPSE,v.x-5,v.y-5,10,10);
			handles.addChild(handle);
		}
	}

	public void update(){
		createPolygon();
		createHandles();
	}

	void addPoint(int x, int y) {
		int index=-1;

		if (!handleStack.isEmpty()) {
			index=points.indexOf(handleStack.peek());
		}
		
		float zoom=ZoomControl.getZoom();
		x=(int)(x/zoom+0.5f);
		y=(int)(y/zoom+0.5f);

		int cx=Camera.getX();
		int cy=Camera.getY();
		x-=cx;
		y-=cy;

		PVector p=new PVector(x,y);
		handleStack.push(p);
		
		if (index==-1) points.add(p);
		else points.add(index,p);
		
		createPolygon();
		createHandles();
	}		
		
	void removeSelectedPoint() {
		if (points.size()==0) return;
		if (handleStack.isEmpty()) {
			points.remove(points.size()-1);
		}
		else {
			PVector remove=handleStack.pop();
			points.remove(points.indexOf(remove));
		}

		createPolygon();
		createHandles();
	}

	ModifiableVector intersectsPoint(int x, int y) {
		float zoom=ZoomControl.getZoom();
		x=(int)(x/zoom+0.5f);
		y=(int)(y/zoom+0.5f);

		int cx=Camera.getX();
		int cy=Camera.getY();
		x-=cx;
		y-=cy;

		for (int i=0; i<points.size();i++) {
			PVector v = points.get(i);
			float dx=x-v.x;
			float dy=y-v.y;
			if (dx*dx+dy*dy<5*5) {
				return new ModifiableVector(this,i);
			}
		}

		return null;
	}

	PVector[] getVerticies() {
		return points.toArray(new PVector[0]);
	}

	PVector getVertex(int i) {
		return points.get(i);
	}

	int getVertexCount(){
		return points.size();
	}

	PVector getPrevious(){
		return handleStack.size()<=1? null : handleStack.get(handleStack.size()-2);
	}
}
