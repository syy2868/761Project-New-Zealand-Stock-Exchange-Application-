package com.sprinters.bullzx.event;

import android.graphics.PointF;
import android.view.MotionEvent;

public class OnTouchGestureListener {

	public void onTouchDown(ITouchable touchable,MotionEvent event){
		if (touchable != null) { 
			touchable.touchDown(new PointF(event.getX(),event.getY()));
		}
	}
	
	public void onTouchMoved(ITouchable touchable,MotionEvent event){
		if (touchable != null) {
			touchable.touchMoved(new PointF(event.getX(),event.getY()));
		}
	}
	
	public void onTouchUp(ITouchable touchable,MotionEvent event){
		if (touchable != null) {
			touchable.touchUp(new PointF(event.getX(),event.getY()));
		}
	}
}
