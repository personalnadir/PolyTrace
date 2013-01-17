import java.io.File;
import java.io.IOException;

class JsonFileWriter implements FileSelectionHandler {
	final String json;

	JsonFileWriter(String data) {
		json=data;
	}

	public boolean open (File f) {
		if (!f.getName().endsWith("json")) {
			return false;
		}

		try {
			ReadWriteTextFile rwtf=new ReadWriteTextFile(f.getCanonicalPath());
			rwtf.write(json);
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}
}

