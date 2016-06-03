package com.sprinters.bullzx.view;

import java.util.ArrayList;
import java.util.List;

import com.sprinters.bullzx.entity.IMeasurable;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;

public abstract class PeriodDataGridChart extends DataGridChart {
	

	public static final int DEFAULT_ALIGN_TYPE = ALIGN_TYPE_CENTER;
	public static final int DEFAULT_BIND_CROSS_LINES_TO_STICK = BIND_TO_TYPE_BOTH;
	
	protected int gridAlignType = DEFAULT_ALIGN_TYPE;
	protected int bindCrossLinesToStick = DEFAULT_BIND_CROSS_LINES_TO_STICK;

	public PeriodDataGridChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PeriodDataGridChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public PeriodDataGridChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	protected void initAxisX() {
		List<String> titleX = new ArrayList<String>();
		if (null != stickData && stickData.size() > 0) {
			float average = getDisplayNumber() / this.getLongitudeNum();
			for (int i = 0; i < this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average);
				if (index > getDisplayNumber() - 1) {
					index = getDisplayNumber() - 1;
				}
				titleX.add(formatAxisXDegree(stickData.get(index).getDate()));
			}
			titleX.add(formatAxisXDegree(stickData.get(getDisplayNumber() - 1).getDate()));
		}
		super.setLongitudeTitles(titleX);
	}

	protected void initAxisY() {
		this.calcValueRange();
		List<String> titleY = new ArrayList<String>();
		double average = (maxValue - minValue) / this.getLatitudeNum();
		;
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = formatAxisYDegree(minValue + i * average);
			if (value.length() < super.getLatitudeMaxTitleLength()) {
				while (value.length() < super.getLatitudeMaxTitleLength()) {
					value = " " + value;
				}
			}
			titleY.add(value);
		}
		// calculate last degrees by use max value
		String value = formatAxisYDegree(maxValue);
		if (value.length() < super.getLatitudeMaxTitleLength()) {
			while (value.length() < super.getLatitudeMaxTitleLength()) {
				value = " " + value;
			}
		}
		titleY.add(value);

		super.setLatitudeTitles(titleY);
	}
	

	
	public float longitudePostOffset(){
		if (gridAlignType == ALIGN_TYPE_CENTER) {
			float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / getDisplayNumber();
			return (this.dataQuadrant.getQuadrantPaddingWidth() - stickWidth)/ (longitudeTitles.size() - 1);
	    }else{
			return this.dataQuadrant.getQuadrantPaddingWidth()/ (longitudeTitles.size() - 1);
	    }
	}
	
	public float longitudeOffset(){
		if (gridAlignType == ALIGN_TYPE_CENTER) {
			float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / getDisplayNumber();
			return dataQuadrant.getQuadrantPaddingStartX() + stickWidth / 2;
		}else{
			return dataQuadrant.getQuadrantPaddingStartX();
		}
	}
	
	
	protected PointF calcTouchedPoint(float x ,float y) {
		if (!isValidTouchPoint(x,y)) {
			return new PointF(0,0);
		}
		if (bindCrossLinesToStick == BIND_TO_TYPE_NONE) {
			return new PointF(x, y);
		} else if (bindCrossLinesToStick == BIND_TO_TYPE_BOTH) {
			PointF bindPointF = calcBindPoint(x, y);
			return bindPointF;
		} else if (bindCrossLinesToStick == BIND_TO_TYPE_HIRIZIONAL) {
			PointF bindPointF = calcBindPoint(x, y);
			return new PointF(bindPointF.x, y);
		} else if (bindCrossLinesToStick == BIND_TO_TYPE_VERTICAL) {
			PointF bindPointF = calcBindPoint(x, y);
			return new PointF(x, bindPointF.y);
		} else {
			return new PointF(x, y);
		}	
	}
	
	protected PointF calcBindPoint(float x ,float y) {
		float calcX = 0;
		float calcY = 0;
		
		int index = calcSelectedIndex(x,y);
		
		float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / getDisplayNumber();
		IMeasurable stick = stickData.get(index);
		calcY = (float) ((1f - (stick.getHigh() - minValue)
				/ (maxValue - minValue))
				* (dataQuadrant.getQuadrantPaddingHeight()) + dataQuadrant.getQuadrantPaddingStartY());
		calcX = dataQuadrant.getQuadrantPaddingStartX() + stickWidth * (index - getDisplayFrom()) + stickWidth / 2;
		
		return new PointF(calcX,calcY);
	}
	
	protected float calcDistance(MotionEvent event) {
		if(event.getPointerCount() <= 1) {
			return 0f;
		}else{
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}
	}
	
	/**
	 * @return the gridAlignType
	 */
	public int getStickAlignType() {
		return gridAlignType;
	}

	/**
	 * @param gridAlignType the gridAlignType to set
	 */
	public void setStickAlignType(int stickAlignType) {
		this.gridAlignType = stickAlignType;
	}
}
