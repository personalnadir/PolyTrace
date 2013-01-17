import processing.core.*;
import java.util.Stack;

class Shape {
	PShape s;
	PShape handles;
	PApplet pa;
	Stack<PShape> handleStack=new Stack();
	PShape selectedPoint=null;

	Shape(PApplet p){
		pa=p;
		handles=pa.createShape(PShape.GROUP);
		initS();
	}

	private void initS(){
		s=pa.createShape();
		s.fill(42,71,94, 64);
		s.stroke(255,245,230);
		s.strokeWeight(2);
	}

	void draw(boolean current){
		pa.shape(s);
		pa.shape(handles);
	}

	void colourFocussed(){
		s.stroke(255,245,230);
	}

	void colourOutOfFocus(){
		s.stroke(69,23,47,64);
	}

	void colourConcave(){
		s.fill(255,0,0,64);
	}

	void colourConvex(){
		s.fill(42,71,94, 64);
	}

	void colourShapeType(){
		if (Geometry.isConvex(s)){
			colourConvex();
		} else {
			colourConcave();
		}
	}

	void colourPointsDeselected() {
		for (PShape p:handleStack) {
			p.stroke(42,71,94);
		}
	}

	void setPointSelected(PShape p) {
		colourPointsDeselected();
		p.stroke(16,159,145);
		selectedPoint=p;
	}

	void addPoint(int x, int y) {
		int index=-1;

		if (!handleStack.isEmpty()) {
			index=handleStack.indexOf(selectedPoint);
		}
		
		PShape oldS=s;
		initS();
		float zoom=ZoomControl.getZoom();
		x=(int)(x/zoom+0.5f);
		y=(int)(y/zoom+0.5f);

		int cx=Camera.getX();
		int cy=Camera.getY();
		x-=cx;
		y-=cy;

		for (int i = 0; i < oldS.getVertexCount(); i++) {
			PVector v = oldS.getVertex(i);
			s.vertex(v.x,v.y);

			if (i==index) {
				s.vertex(x,y);
			}
		}

		if (index==-1) {
			s.vertex(x,y);
		}
		s.end(PShape.CLOSE);

		pa.ellipseMode(PShape.CENTER);
		pa.fill(42,71,94, 64);
		pa.strokeWeight(2);
		PShape handle=pa.createShape(PShape.ELLIPSE,x-5,y-5,10,10);
		handles.addChild(handle);

		if (index==-1) {
			handleStack.push(handle);
		} else {
			handleStack.insertElementAt(handle,index+1);
		}

		setPointSelected(handle);
		colourShapeType();
	}

	void removeSelectedPoint() {
		if (selectedPoint==null) return;

		int index=handleStack.indexOf(selectedPoint);
		handleStack.removeElement(selectedPoint);

		PShape oldS=s;
		initS();

		for (int i = 0; i < oldS.getVertexCount(); i++) {
			if (i==index) continue;

			PVector v = oldS.getVertex(i);
			s.vertex(v.x,v.y);
		}
		s.end(PShape.CLOSE);

		handles=pa.createShape(PShape.GROUP);

		if (handleStack.isEmpty()) {
			return;
		}

		for (PShape p : handleStack){
			handles.addChild(p);
		}

		colourPointsDeselected();
		PShape next;
		if (index==0) {
			next=handleStack.peek();
		} else {
			next=handleStack.elementAt(index-1);
		}

		setPointSelected(next);
		colourShapeType();
	}

	ModifiableVector intersectsPoint(int x, int y) {
		float zoom=ZoomControl.getZoom();
		x=(int)(x/zoom+0.5f);
		y=(int)(y/zoom+0.5f);

		int cx=Camera.getX();
		int cy=Camera.getY();
		x-=cx;
		y-=cy;

		for (int i = 0; i < s.getVertexCount(); i++) {
			PVector v = s.getVertex(i);
			float dx=x-v.x;
			float dy=y-v.y;
			if (dx*dx+dy*dy<5*5) {
				return new ModifiableVector(s,i,handleStack.elementAt(i));
			}
		}

		return null;
	}

	PVector[] getVerticies() {
		PVector[] verticies=new PVector[s.getVertexCount()];

		for (int i = 0; i < s.getVertexCount(); i++) {
			PVector v = s.getVertex(i);
			verticies[i]=v;
		}
		return verticies;
	}
}