package com.sprinters.bullzx.common;

import com.sprinters.bullzx.view.GridChart;

public class HorizontalAxis extends Axis {
	protected float height;

	public HorizontalAxis(GridChart inChart, int position , float height) {
		super(inChart, position);
		this.height = height;
	}

	public float getQuadrantWidth() {
		return inChart.getWidth() - 2 * inChart.getBorderWidth();
	}

	public float getQuadrantHeight() {
		return height;
	}

}
