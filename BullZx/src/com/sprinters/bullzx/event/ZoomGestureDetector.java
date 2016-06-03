package com.sprinters.bullzx.event;
import android.view.MotionEvent;

public class ZoomGestureDetector<T extends IZoomable> extends TouchGestureDetector<ITouchable>{
	
	public static final int TOUCH_MODE_NONE = 0;
	public static final int TOUCH_MODE_SINGLE = 1;
	public static final int TOUCH_MODE_MULTI = 2;
	
	public static final int MIN_DISTANCE = 5;
	
	protected int touchMode = TOUCH_MODE_NONE;

	protected float olddistance;
	protected float newdistance;
	
	protected OnZoomGestureListener onZoomGestureListener;
	
	public ZoomGestureDetector(IZoomable zoomable){
		super(zoomable);
		if (zoomable != null) {
			onZoomGestureListener = zoomable.getOnZoomGestureListener();
		}
	}
	
	protected float calcDistance(MotionEvent event) {
		if(event.getPointerCount() <= 1) {
			return 0f;
		}else{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return (float) Math.sqrt(x * x + y * y);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (event.getPointerCount() == 1) {
				touchMode = TOUCH_MODE_SINGLE;
			}
			break;
		case MotionEvent.ACTION_UP:
			touchMode = TOUCH_MODE_NONE;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = TOUCH_MODE_NONE;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_DISTANCE) {
				touchMode = TOUCH_MODE_MULTI;
			}
			return true;
			//break;
		case MotionEvent.ACTION_MOVE:
			if (touchMode == TOUCH_MODE_MULTI) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_DISTANCE
						&& Math.abs(newdistance - olddistance) > MIN_DISTANCE) {
					if (onZoomGestureListener != null) {
						if (newdistance > olddistance) {
							onZoomGestureListener.onZoomIn((IZoomable)instance,event);
						} else {
							onZoomGestureListener.onZoomOut((IZoomable)instance,event);
						}
					}
					olddistance = newdistance;
				}
				
				return true;
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * @return the onZoomGestureListener
	 */
	public OnZoomGestureListener getOnZoomGestureListener() {
		return onZoomGestureListener;
	}

	/**
	 * @param onZoomGestureListener the onZoomGestureListener to set
	 */
	public void setOnZoomGestureListener(OnZoomGestureListener onZoomGestureListener) {
		this.onZoomGestureListener = onZoomGestureListener;
	}
}
