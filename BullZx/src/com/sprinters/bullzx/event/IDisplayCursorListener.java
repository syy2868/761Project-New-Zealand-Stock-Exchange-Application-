package com.sprinters.bullzx.event;
import com.sprinters.bullzx.common.IDataCursor;

public interface IDisplayCursorListener {
	void onCursorChanged(IDataCursor dataCursor,int displayFrom, int displayNumber);
}
