package com.sprinters.bullzx.event;

import android.graphics.PointF;

public interface ITouchable {
	
	static final int TOUCH_NO_SELECTED_INDEX = -1;
	
	static final int TOUCH_MODE_NONE = 0;
	static final int TOUCH_MODE_SINGLE = 1;
	static final int TOUCH_MODE_MULTI = 2;
	
	static final int TOUCH_MOVE_MIN_DISTANCE = 6;
	
	void touchDown(PointF pt);
	void touchMoved(PointF pt);
	void touchUp(PointF pt);

	void setOnTouchGestureListener(OnTouchGestureListener listener);
	OnTouchGestureListener getOnTouchGestureListener();
}
