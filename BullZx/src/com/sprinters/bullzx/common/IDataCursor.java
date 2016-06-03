package com.sprinters.bullzx.common;

public interface IDataCursor {
	
	static final int MINI_DISPLAY_NUM = 10;
	
	 int getDisplayFrom();
	 int getDisplayNumber();
	 int getDisplayTo();
	 int getMinDisplayNumber();
	
	 void setDisplayFrom(int displayFrom);
	 void setDisplayNumber(int displayNumber);
	 void setMinDisplayNumber(int minDisplayNumber);
}
