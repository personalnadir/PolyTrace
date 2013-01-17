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

	Data(String path, PVector[][] v) {
		imgPath=path;
		vertices=v;
	}
}

class LoadData implements Command {
	public void execute(Object data) {
		ShapeManager.reset();
				
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
		Data d=new Data(Image.getPath(),vertices);
		String json=gson.toJson(d);

		FileChooser.showSave(new JsonFileWriter(json));
	}

	static void importData() {
		FileChooser.showOpen(new JsonFileReader(new LoadData()));
	}
}
