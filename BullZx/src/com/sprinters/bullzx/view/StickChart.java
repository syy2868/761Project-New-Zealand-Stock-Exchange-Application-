package com.sprinters.bullzx.view;

import com.sprinters.bullzx.entity.IChartData;
import com.sprinters.bullzx.entity.IMeasurable;
import com.sprinters.bullzx.entity.IStickEntity;
import com.sprinters.bullzx.event.IGestureDetector;
import com.sprinters.bullzx.event.IZoomable;
import com.sprinters.bullzx.event.IDisplayCursorListener;
import com.sprinters.bullzx.event.OnZoomGestureListener;
import com.sprinters.bullzx.event.ZoomGestureDetector;
import com.sprinters.bullzx.mole.IMole;
import com.sprinters.bullzx.mole.IMoleProvider;
import com.sprinters.bullzx.mole.StickMole;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class StickChart extends PeriodDataGridChart implements IZoomable{		
	
	protected IMoleProvider provider = new IMoleProvider() {
		public IMole getMole() {
			return new StickMole() {
				@Override
				public void setPro() {
					stickFillColor = Color.BLUE;
					stickBorderColor = Color.WHITE;
					stickStrokeWidth = 1;
					stickSpacing = 1;
				}
			};
		}
	};
	
	public static final int DEFAULT_STICK_SPACING = 1;
	
	protected int maxSticksNum;
	
	protected int minDisplayNum = MINI_DISPLAY_NUM;

	protected int stickSpacing = DEFAULT_STICK_SPACING;
	
	protected OnZoomGestureListener onZoomGestureListener = new OnZoomGestureListener();
	protected IGestureDetector zoomGestureDetector = new ZoomGestureDetector<IZoomable>(this);
	
	protected IDisplayCursorListener onDisplayCursorListener;
	
	public StickChart(Context context) {
		super(context);
	}

	public StickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public StickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (this.autoCalcValueRange) {
			calcValueRange();
		}
		initAxisY();
		initAxisX();
		super.onDraw(canvas);
		drawSticks(canvas);
	}

	protected void drawSticks(Canvas canvas) {
		if (null == stickData) {
			return;
		}
		if (stickData.size() <= 0) {
			return;
		}

		float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / getDisplayNumber();

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {

			float stickX = dataQuadrant.getQuadrantPaddingStartX();

			for (int i = 0; i < stickData.size(); i++) {
				IMeasurable stick = stickData.get(i);
				StickMole mole = (StickMole)provider.getMole();
				mole.setUp(this,stick,stickX,stickWidth);
				mole.draw(canvas);
				// next x
				stickX = stickX + stickWidth;
			}
		} else {
			float stickX = dataQuadrant.getQuadrantPaddingEndX() - stickWidth;
			for (int i = stickData.size() - 1; i >= 0; i--) {
				IMeasurable stick = stickData.get(i);
				StickMole mole = (StickMole)provider.getMole();
				mole.setUp(this,stick,stickX,stickWidth);
				mole.draw(canvas);
				// next x
				stickX = stickX - stickWidth;
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//valid
		if (!isValidTouchPoint(event.getX(),event.getY())) {
			return false;
		}
		if (null == stickData || stickData.size() == 0) {
			return false;
		}
		
		return zoomGestureDetector.onTouchEvent(event);
	}

	public void zoomIn() {
		if (getDisplayNumber() > getMinDisplayNumber()) {
			setDisplayNumber(getDisplayNumber() - ZOOM_STEP);
			this.postInvalidate();
		}
		
		//Listener
		if (onDisplayCursorListener != null) {
			onDisplayCursorListener.onCursorChanged(this,getDisplayFrom(), getDisplayNumber());
		}
	}

	public void zoomOut() {		
		if (getDisplayNumber() < stickData.size() - 1 - ZOOM_STEP) {
			setDisplayNumber(getDisplayNumber() + ZOOM_STEP);
			this.postInvalidate();
		}
		
		//Listener
		if (onDisplayCursorListener != null) {
			onDisplayCursorListener.onCursorChanged(this,getDisplayFrom(), getDisplayNumber());
		}
	}

	/**
	 * @return the stickData
	 */
	public IChartData<IStickEntity> getStickData() {
		return stickData;
	}

	/**
	 * @param stickData
	 *            the stickData to set
	 */
	public void setStickData(IChartData<IStickEntity> stickData) {
		this.stickData = stickData;
	}

	/**
	 * @return the maxSticksNum
	 */
	@Deprecated
	public int getMaxSticksNum() {
		return maxSticksNum;
	}

	/**
	 * @param maxSticksNum
	 *            the maxSticksNum to set
	 */
	@Deprecated
	public void setMaxSticksNum(int maxSticksNum) {
		this.maxSticksNum = maxSticksNum;
	}

	/**
	 * @return the stickSpacing
	 */
	public int getStickSpacing() {
		return stickSpacing;
	}

	/**
	 * @param stickSpacing the stickSpacing to set
	 */
	public void setStickSpacing(int stickSpacing) {
		this.stickSpacing = stickSpacing;
	}

	/**
	 * @return the bindCrossLinesToStick
	 */
	public int getBindCrossLinesToStick() {
		return bindCrossLinesToStick;
	}

	/**
	 * @param bindCrossLinesToStick the bindCrossLinesToStick to set
	 */
	public void setBindCrossLinesToStick(int bindCrossLinesToStick) {
		this.bindCrossLinesToStick = bindCrossLinesToStick;
	}
	
	public int getDisplayFrom() {
//		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
//			return 0;
//		}else{
//			return stickData.size() - maxSticksNum;
//		}
		return 0;
	}

	public int getDisplayNumber() {
		return maxSticksNum;
	}

	public int getDisplayTo() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return maxSticksNum;
		}else{
			return stickData.size() - 1;
		}
	}

	public void setDisplayFrom(int displayFrom) {
		//TODO
	}

	public void setDisplayNumber(int displayNumber) {
		maxSticksNum = displayNumber;
	}

	public void setDisplayTo(int displayTo) {
		//TODO
	}

	public int getMinDisplayNumber() {
		return minDisplayNum;
	}

	public void setMinDisplayNumber(int minDisplayNumber) {
		this.minDisplayNum = minDisplayNumber;
	}

	public OnZoomGestureListener getOnZoomGestureListener() {
		return onZoomGestureListener;
	}

	public void setOnZoomGestureListener(OnZoomGestureListener listener) {
		this.onZoomGestureListener = listener;
	}

	public IDisplayCursorListener getOnDisplayCursorListener() {
		return onDisplayCursorListener;
	}

	public void setOnDisplayCursorListener(
			IDisplayCursorListener onDisplayCursorListener) {
		this.onDisplayCursorListener = onDisplayCursorListener;
	}
}
