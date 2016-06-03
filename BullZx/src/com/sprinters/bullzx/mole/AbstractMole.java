package com.sprinters.bullzx.mole;

import com.sprinters.bullzx.common.IChart;
import android.graphics.RectF;

public abstract class AbstractMole  extends RectF implements IMole {
	private IChart inChart;
	
	public void setUp(IChart chart){
		setInChart(chart);
	} 

	/**
	 * @return the inChart
	 */
	public IChart getInChart() {
		return inChart;
	}

	/**
	 * @param inChart the inChart to set
	 */
	public void setInChart(IChart inChart) {
		this.inChart = inChart;
	}
}
