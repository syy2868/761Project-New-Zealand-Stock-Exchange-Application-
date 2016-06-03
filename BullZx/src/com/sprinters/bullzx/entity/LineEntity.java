package com.sprinters.bullzx.entity;

import java.util.ArrayList;
import java.util.List;

public class LineEntity<T> {

	 //Data for draw this line
	private List<T> lineData;

	 //Title for this line
	private String title;

	 //Line Color
	private int lineColor;

	 //Should display this line
	private boolean display = true;

	public LineEntity() {
		super();
	}

	public LineEntity(List<T> lineData, String title, int lineColor) {
		super();
		this.lineData = lineData;
		this.title = title;
		this.lineColor = lineColor;
	}

	/**
	 * @param value
	 */
	public void put(T value) {
		if (null == lineData) {
			lineData = new ArrayList<T>();
		}
		lineData.add(value);
	}

	/**
	 * @return the lineData
	 */
	public List<T> getLineData() {
		return lineData;
	}

	/**
	 * @param lineData
	 *            the lineData to set
	 */
	public void setLineData(List<T> lineData) {
		this.lineData = lineData;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the lineColor
	 */
	public int getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor
	 *            the lineColor to set
	 */
	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @return the display
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}
}
