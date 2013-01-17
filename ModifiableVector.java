import processing.core.*;

class ModifiableVector{
	PShape s;
	PShape handle;
	int vindex;

	ModifiableVector(PShape s, int index, PShape handle) {
		this.s=s;
		this.handle=handle;
		this.vindex=index;
	}

	float getX() {
		return s.getVertex(vindex).x;
	}

	float getY() {
		return s.getVertex(vindex).y;
	}
	
	void setPos(int x, int y){
		PVector v=s.getVertex(vindex);
		int vx=(int)(x-v.x);
		int vy=(int)(y-v.y);
		
		v.x=x;
		v.y=y;

		s.setVertex(vindex,v);

		handle.translate(vx,vy);

		if (!Geometry.isConvex(s)){
			setFillColour(255,0,0);
		} else {
			setFillColour(42,71,94);
		}
	}

	void setFillColour(int r, int g, int b) {
		s.fill(r,g,b,64);
	}

	PShape getHandle() {
		return handle;
	}
};
 
