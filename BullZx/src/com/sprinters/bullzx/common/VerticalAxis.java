package com.sprinters.bullzx.common;

import com.sprinters.bullzx.view.GridChart;

public class VerticalAxis extends Axis {

	protected float width;
	 
	public VerticalAxis(GridChart inChart, int position , float width) {
		super(inChart,position);
		this.width = width;
	}
	
	public float getQuadrantWidth() {
		return width;
	}

	public float getQuadrantHeight() {
		return inChart.getHeight() - 2 * inChart.getBorderWidth();
	}
}
