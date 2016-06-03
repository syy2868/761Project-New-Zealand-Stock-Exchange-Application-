package com.sprinters.bullzx.mole;

import com.sprinters.bullzx.common.IChart;
import com.sprinters.bullzx.entity.IMeasurable;
import com.sprinters.bullzx.view.DataGridChart;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public abstract class StickMole extends RectMole {

	public static final int DEFAULT_STICK_SPACING = 1;
	public static final int DEFAULT_STICK_STROKE_WIDTH = 0;
	
	
	 //default color for display stick border
	public static final int DEFAULT_STICK_BORDER_COLOR = Color.YELLOW;

	 //default color for display stick
	public static final int DEFAULT_STICK_FILL_COLOR = Color.WHITE;

	 //Color for display stick border
	protected int stickBorderColor = DEFAULT_STICK_BORDER_COLOR;

	 //Color for display stick
	protected int stickFillColor = DEFAULT_STICK_FILL_COLOR;
	
	protected int stickStrokeWidth = DEFAULT_STICK_STROKE_WIDTH;
	protected int stickSpacing = DEFAULT_STICK_SPACING;
	
	private IMeasurable stickData;
	
	public void setUp(IChart chart ,IMeasurable data, float from , float width) {
		super.setUp(chart);
		this.setPro();
		setStickData(data);
		left = from;
		right = from + width - stickSpacing;
	}

	public void draw(Canvas canvas) {	
		Paint mPaintFill = new Paint();
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(stickFillColor);
		
		if (width() >= 2f && width() >= 2 * stickStrokeWidth) {
			if (stickStrokeWidth  > 0) {
				Paint mPaintBorder = new Paint();
				mPaintBorder.setStyle(Style.STROKE);
				mPaintBorder.setStrokeWidth(stickStrokeWidth);
				mPaintBorder.setColor(stickBorderColor);
				
				canvas.drawRect(left + stickStrokeWidth, top + stickStrokeWidth, right - stickStrokeWidth, bottom - stickStrokeWidth, mPaintFill);
				canvas.drawRect(left + stickStrokeWidth, top + stickStrokeWidth, right - stickStrokeWidth, bottom - stickStrokeWidth, mPaintBorder);
			}else{
				canvas.drawRect(left, top, right, bottom, mPaintFill);
			}
		} else {
			canvas.drawLine(left, top, left, bottom, mPaintFill);	
		}
	}

	/**
	 * @return the stickData
	 */
	public IMeasurable getStickData() {
		return stickData;
	}

	/**
	 * @param stickData the stickData to set
	 */
	public void setStickData(IMeasurable stickData) {
		this.stickData = stickData;
		DataGridChart chart = (DataGridChart)getInChart();
		float highY = (float) ((1f - (stickData.getHigh() - chart.getMinValue()) / (chart.getMaxValue() - chart.getMinValue()))
				* (chart.getDataQuadrant().getQuadrantPaddingHeight()) + chart.getDataQuadrant().getQuadrantPaddingStartY());
		float lowY = (float) ((1f - (stickData.getLow() - chart.getMinValue()) / (chart.getMaxValue() - chart.getMinValue()))
				* (chart.getDataQuadrant().getQuadrantPaddingHeight()) + chart.getDataQuadrant().getQuadrantPaddingStartY());
		
		top = highY;
		bottom = lowY;
	}
	
	public abstract void setPro();
}
