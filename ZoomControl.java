class ZoomControl {
	static float zoomLevel=1f;

	static void increase() {
		zoomLevel-=0.1f;
		zoomLevel=Math.max(0.1f,zoomLevel);
	}

	static void decrease() {
		zoomLevel+=0.1f;
	}

	static float getZoom(){
		return zoomLevel;
	}

	static void reset() {
		zoomLevel=1f;
	}
}