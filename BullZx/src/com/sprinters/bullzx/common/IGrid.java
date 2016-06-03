package com.sprinters.bullzx.common;

import android.graphics.Color;

public interface IGrid {
	 //default numbers of grid‘s latitude line
	public static final int DEFAULT_LATITUDE_NUM = 4;

	 //default numbers of grid‘s longitude line
	public static final int DEFAULT_LONGITUDE_NUM = 3;

	 //Should display longitude line?
	public static final boolean DEFAULT_DISPLAY_LONGITUDE = Boolean.TRUE;

	 //Should display longitude as dashed line
	public static final boolean DEFAULT_DASH_LONGITUDE = Boolean.TRUE;

	 //Should display longitude line
	public static final boolean DEFAULT_DISPLAY_LATITUDE = Boolean.TRUE;

	 //Should display latitude as dashed line
	public static final boolean DEFAULT_DASH_LATITUDE = Boolean.TRUE;

	 //Should display the degrees in X axis?
	public static final boolean DEFAULT_DISPLAY_LONGITUDE_TITLE = Boolean.TRUE;
	
	public static final float DEFAULT_LONGITUDE_WIDTH = 1f;
	
	 //Should display the degrees in Y axis?
	public static final boolean DEFAULT_DISPLAY_LATITUDE_TITLE = Boolean.TRUE;
	
	public static final float DEFAULT_LATITUDE_WIDTH = 1f;
	
	 //default color of text for the longitude　degrees display
	public static final int DEFAULT_LONGITUDE_FONT_COLOR = Color.WHITE;

	 //default font size of text for the longitude　degrees display
	public static final int DEFAULT_LONGITUDE_FONT_SIZE = 12;

	 //default color of text for the latitude　degrees display
	public static final int DEFAULT_LATITUDE_FONT_COLOR = Color.RED;

	 //default font size of text for the latitude　degrees display
	public static final int DEFAULT_LATITUDE_FONT_SIZE = 12;
}
