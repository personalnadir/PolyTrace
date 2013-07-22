import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import processing.core.*;

class ShapeManager{
	static PApplet pa;
	static Vector<Shape> shapes=new Vector<Shape>();
	static boolean translating=false;
	static ModifiableVector dragPoint=null;
	static Shape current=null;
	static boolean limitedPlacement=false;

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
		for (Shape s:shapes) {
			s.inFocus(false);
		}
		inFocus.inFocus(true);
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

					s.setPointSelected(dragPoint.getPoint());
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

				if (limitedPlacement){
					PVector prev=dragPoint.getPreviousPoint();
					float dx=x-prev.x;
					float dy=y-prev.y;

					float angle=PApplet.atan2(dy,dx);
					angle=PApplet.round((angle+(PConstants.QUARTER_PI)/2)/PConstants.QUARTER_PI)*PConstants.QUARTER_PI;

					float r=PApplet.sqrt(dx*dx+dy*dy);

					float rx=1*r;
					float ry=0*r;
					x=(int)(prev.x+(PApplet.cos(angle)*rx-PApplet.sin(angle)*ry));
					y=(int)(prev.y+(PApplet.cos(angle)*ry+PApplet.sin(angle)*rx));
				}

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
						sh.inFocus(false);
					}

					s.setPointSelected(v.getPoint());
 s.inFocus(true);
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

	static void limitDegrees(boolean on){
		limitedPlacement=on;
	}

	static Shape getCurrentShape() {
		return current;
	}

	static void newShape(){
		if (current!=null && current.getVertexCount()==0) {
			return;
		}
		Shape s=new Shape(pa);
		shapes.add(s);
		setFocus(s);
	}

	static PVector[][] getShapeCoordinates(){
		int toExport=0;
		for (Shape s: shapes) {
			if (s.getVertexCount()>0) toExport++;
		}
		
		PVector[][] verticiesByShape=new PVector[toExport][];

		int index=0;
		for (Shape s: shapes) {
			if (s.getVertexCount()==0)  continue;
			verticiesByShape[index]=s.getVerticies();
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
