import processing.core.*;

class Geometry {
	static boolean isConvex(PShape s) {
		boolean positive=false;
		for (int i=0; i<s.getVertexCount(); i++){
			PVector v1=s.getVertex(i);
			PVector v2=s.getVertex((i+1)%s.getVertexCount());
			PVector v3=s.getVertex((i+2)%s.getVertexCount());
			
			float e1x=v2.x-v1.x;
			float e1y=v2.y-v1.y;

			float e2x=v3.x-v2.x;
			float e2y=v3.y-v2.y;
			
			// perp dot
			float vertexSign=e1x*e2y - e1y*e2x;

			boolean sense=0<vertexSign;
			if (i>0) {
				if (sense!=positive) {
					return false;
				}
			}
			positive=sense;
		}
		
		for (int i=1; i<s.getVertexCount(); i++){
			PVector v1=s.getVertex(i);
			PVector v2=s.getVertex((i+1)%s.getVertexCount());
			
			for (int k=i+2; k<s.getVertexCount(); k++) {
				PVector v3=s.getVertex(k);
				PVector v4=s.getVertex((k+1)%s.getVertexCount());

				if (lineSegmentsIntersect(v1.x,v1.y,v2.x,v2.y,v3.x,v3.y,v4.x,v4.y)) {
					return false;
				}
			}
		}

		return true;
	}

	static float[] linesIntersect(float x1,float y1,float x2, float y2,float x3,float y3,float x4, float y4) {
		float denominator=determinant(x1-x2,y1-y2,x3-x4,y3-y4);
		if (denominator*denominator<0.001){
			return null;
		}
		float numerator_a=determinant(x1,y1,x2,y2);
		float numerator_c=determinant(x3,y3,x4,y4);

		float xnumerator=determinant(numerator_a,x1-x2,numerator_c,x3-x4);
		float ynumerator=determinant(numerator_a,y1-y2,numerator_c,y3-y4);
		
		float[] coord=new float[2];
		coord[0]=xnumerator/denominator;
		coord[1]=ynumerator/denominator;
		
		return coord;
	}

	static float determinant(float a, float b, float c, float d) {
		return a*d-b*c;
	}

	static boolean lineSegmentsIntersect(float x1,float y1,float x2, float y2,float x3,float y3,float x4, float y4){
	
		float denominator=determinant(y4-y3,x4-x3, y2-y1,x1-x2);
		float ua=determinant(x4-x3,y4-y3,x1-x3,y1-y3)/denominator;
		float ub=determinant(x2-x1,y2-y1,x1-x3,y1-y3)/denominator;

		if (ua<0 || ua>1 || ub<0 || ub>1) {
			return false;
		}

		return true;
	}
}
