import java.io.File;
import java.io.IOException;

class JsonFileReader implements FileSelectionHandler {
	private final Command whenLoaded;

	JsonFileReader(Command c) {
		whenLoaded=c;
	}

	public boolean open (File f) {
		if (!f.getName().endsWith("json")) {
			return false;
		}

		try {
			ReadWriteTextFile rwtf= new ReadWriteTextFile(f.getCanonicalPath());
			String json = rwtf.read();
			whenLoaded.execute(json);
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}
}

