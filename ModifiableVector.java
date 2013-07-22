import processing.core.*;

class ModifiableVector{
	Shape s;
	int vindex;
	PVector v;

	ModifiableVector(Shape s, int index) {
		this.s=s;
		this.vindex=index;
		v=s.getVertex(index);
	}

	float getX() {
		return v.x;
	}

	float getY() {
		return v.y;
	}
	
	void setPos(int x, int y){
		v.x=x;
		v.y=y;
		s.update();
}

	PVector getPreviousPoint(){
		return s.getPrevious();
	}	

	PVector getPoint(){
		return v;
	}

	int getIndex(){
		return vindex;
	}
};
 
