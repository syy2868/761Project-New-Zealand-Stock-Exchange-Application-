package com.sprinters.bullzx.common;

public interface IFlexableGrid extends IGrid{
	static final int ALIGN_TYPE_CENTER = 0;
	static final int ALIGN_TYPE_JUSTIFY = 1;
	
	float longitudePostOffset();
	float longitudeOffset();
}
