package com.sprinters.bullzx.event;
import android.view.MotionEvent;

public class OnZoomGestureListener {
	public void onZoomIn(IZoomable zoomable, MotionEvent event){
		if (zoomable != null) {
			zoomable.zoomIn();
		}
	}
	
	public void onZoomOut(IZoomable zoomable, MotionEvent event){
		if (zoomable != null) {
			zoomable.zoomOut();
		}
	}
}
