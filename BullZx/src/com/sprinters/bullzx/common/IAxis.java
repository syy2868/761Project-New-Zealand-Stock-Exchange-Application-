package com.sprinters.bullzx.common;

import android.graphics.Canvas;

public interface IAxis {
	static final int AXIS_X_POSITION_BOTTOM = 1 << 0;
	static final int AXIS_X_POSITION_TOP = 1 << 1;
	static final int AXIS_Y_POSITION_LEFT = 1 << 2;
	static final int AXIS_Y_POSITION_RIGHT = 1 << 3;
	
	void drawAxis(Canvas canvas);
}
