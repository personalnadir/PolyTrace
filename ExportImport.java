import com.google.gson.reflect.*;
import com.google.gson.internal.*;
import com.google.gson.stream.*;
import com.google.gson.annotations.*;
import com.google.gson.internal.bind.*;
import com.google.gson.*;
import processing.core.*;

class Data {
	String imgPath;
	PVector[][] vertices;

	Data(PApplet pa,String path, PVector[][] v) {
		imgPath=path;
		vertices=v;

		for (int i=0;i<v.length;i++){
			if (!shapeClockwise(v[i])) {
				v[i]=(PVector[])pa.reverse(v[i]);
			}
		}
	}

	private boolean shapeClockwise(PVector[] list) {
			float area = 0;
			
			for (int i=0; i<list.length; i++) {
				PVector c=list[i];
				PVector n=list[(i+1)%list.length];
				area += (c.x * n.y) - (n.x * c.y);
			}

			return area > 0;
		}
}

class LoadData implements Command {
	public void execute(Object data) {
		ShapeManager.reset();
		ZoomControl.reset();
		Camera.reset();
				
		String json=(String)data;
		Gson gson = new Gson();
		Data d=gson.fromJson(json, Data.class);

		if (d.imgPath!=null) {
			PImage img = ExportImport.pa.loadImage(d.imgPath); 
			if (img!=null) {
				Image.setImage(img,d.imgPath);
			}
		}

		for (int i=0; i<d.vertices.length; i++) {
			ShapeManager.newShape();
			Shape current=ShapeManager.getCurrentShape();

			for (int k=0; k<d.vertices[i].length; k++) {
				current.addPoint((int)d.vertices[i][k].x,(int)d.vertices[i][k].y);
			}
		}
	}
}

class ExportImport {
	static PApplet pa;

	static void init(PApplet p) {
		pa=p;
	}

	static void export(PVector[][] vertices) {
		Gson gson = new Gson();
		Data d=new Data(pa,Image.getPath(),vertices);
		String json=gson.toJson(d);

		FileChooser.showSave(new JsonFileWriter(json));
	}

	static void importData() {
		FileChooser.showOpen(new JsonFileReader(new LoadData()));
	}
}
