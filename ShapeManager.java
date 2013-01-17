import java.util.Iterator;
import java.util.Vector;
import processing.core.*;

class ShapeManager{
	static PApplet pa;
	static Vector<Shape> shapes=new Vector<Shape>();
	static boolean translating=false;
	static ModifiableVector dragPoint=null;
	static Shape current=null;

	static void init(PApplet p){
		pa=p;
		newShape();
	}

	static void draw() {	
		Iterator<Shape> i=shapes.iterator();

		while (i.hasNext()) {
			Shape s=i.next();
			s.draw(!i.hasNext());
		}
	}

	static void setFocus(Shape inFocus) {
		if (current!=null&&current!=inFocus) {
			current.colourPointsDeselected();
		}
		for (Shape s:shapes) {
			s.colourOutOfFocus();
		}
		inFocus.colourFocussed();
		current=inFocus;
	}

	static boolean mouseDragged(int mouseX, int mouseY, int pmouseX, int pmouseY) {
		translating=false;

		int vx=mouseX-pmouseX;
		int vy=mouseY-pmouseY;
		
		for (Shape s: shapes) {
			if (dragPoint==null) {
				ModifiableVector v=s.intersectsPoint(pmouseX,pmouseY);
				if (v!=null) {
					dragPoint=v;
					setFocus(s);

					s.setPointSelected(dragPoint.getHandle());
				}
			}

			if (dragPoint!=null) {
				translating=true;
				float zoom=ZoomControl.getZoom();
				int x=(int)(mouseX/zoom+0.5f);
				int y=(int)(mouseY/zoom+0.5f);

				int cx=Camera.getX();
				int cy=Camera.getY();
				x-=cx;
				y-=cy;

				dragPoint.setPos(x,y);
				return true;
			}
		}
		return false;
	}

	static void mouseReleased(int mouseX, int mouseY, int pmouseX, int pmouseY) {
		dragPoint=null;

		if (GUI.clicked(mouseX,mouseY)) {
			return;
		}
		int vx=mouseX-pmouseX;
		int vy=mouseY-pmouseY;
		
		if (!translating && vx*vx+vy*vy<5*5) {
			Shape clicked=null;
			for (Shape s: shapes) {
				ModifiableVector v=s.intersectsPoint(mouseX,mouseY);
				if (v!=null) {
					for (Shape sh:shapes){
						sh.colourPointsDeselected();
					}

					s.setPointSelected(v.getHandle());
					clicked=s;
					break;
				}
			}

			if (clicked!=null) {
				setFocus(clicked);
			} else {
				current.addPoint(mouseX,mouseY);
			}
		}
		translating=false;
	}

	static Shape getCurrentShape() {
		return current;
	}

	static void newShape(){
		if (current!=null && current.getVerticies().length==0) {
			return;
		}
		Shape s=new Shape(pa);
		shapes.add(s);
		setFocus(s);
	}

	static PVector[][] getShapeCoordinates(){
		int toExport=0;
		for (Shape s: shapes) {
			PVector[] verticies=s.getVerticies();
			if (verticies.length>0) toExport++;
		}
		
		PVector[][] verticiesByShape=new PVector[toExport][];

		int index=0;
		for (Shape s: shapes) {
			PVector[] verticies=s.getVerticies();
			if (verticies.length==0) continue;
			verticiesByShape[index]=verticies;
			index++;
		}

		return verticiesByShape;
	}

	static void reset(){
		current=null;
		dragPoint=null;
		translating=false;
		shapes.clear();
	}
}