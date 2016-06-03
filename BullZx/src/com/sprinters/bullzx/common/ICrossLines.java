package com.sprinters.bullzx.common;

import android.graphics.Color;

public interface ICrossLines {
	static final int BIND_TO_TYPE_NONE = 0;
	static final int BIND_TO_TYPE_HIRIZIONAL = 1;
	static final int BIND_TO_TYPE_VERTICAL = 2;
	static final int BIND_TO_TYPE_BOTH = 3;
	
	static final int DISPLAY_NONE = 0;
	static final int DISPLAY_HIRIZIONAL = 1;
	static final int DISPLAY_VERTICAL = 2;
	static final int DISPLAY_BOTH = 3;
	
	static final int DEFAULT_CROSS_LINES_COLOR = Color.CYAN;
	static final int DEFAULT_CROSS_LINES_FONT_COLOR = Color.CYAN;
	
	 //Should display the Y cross line if grid is touched
	public static final boolean DEFAULT_DISPLAY_CROSS_X_ON_TOUCH = true;

	
	 //Should display the Y cross line if grid is touched?
	public static final boolean DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH = true;
	
}
