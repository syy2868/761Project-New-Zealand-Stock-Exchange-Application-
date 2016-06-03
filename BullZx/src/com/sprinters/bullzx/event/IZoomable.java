package com.sprinters.bullzx.event;

public interface IZoomable extends ITouchable {
	
	static final int ZOOM_BASE_LINE_CENTER = 0;
	static final int ZOOM_BASE_LINE_LEFT = 1;
	static final int ZOOM_BASE_LINE_RIGHT = 2;
	
	static final int ZOOM_NONE = 0;
	static final int ZOOM_IN = 1;
	static final int ZOOM_OUT = 2;

	static final int ZOOM_STEP = 4;

	void zoomIn();

	void zoomOut();
	
	void setOnZoomGestureListener(OnZoomGestureListener listener);
	OnZoomGestureListener getOnZoomGestureListener();
}
