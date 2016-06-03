package com.sprinters.bullzx.common;

public class CrossLines implements ICrossLines {
	
	
	// Color of cross line inside grid when touched
	private int crossLinesColor = DEFAULT_CROSS_LINES_COLOR;
	
	 // Color of cross line degree text when touched
	private int crossLinesFontColor = DEFAULT_CROSS_LINES_FONT_COLOR;
	
	 //Should display the Y cross line if grid is touched
	private boolean displayCrossXOnTouch = DEFAULT_DISPLAY_CROSS_X_ON_TOUCH;

	 //Should display the Y cross line if grid is touched?
	private boolean displayCrossYOnTouch = DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH;
	
	/**
	 * @return the displayCrossXOnTouch
	 */
	public boolean isDisplayCrossXOnTouch() {
		return displayCrossXOnTouch;
	}

	/**
	 * @param displayCrossXOnTouch
	 *            the displayCrossXOnTouch to set
	 */
	public void setDisplayCrossXOnTouch(boolean displayCrossXOnTouch) {
		this.displayCrossXOnTouch = displayCrossXOnTouch;
	}

	/**
	 * @return the displayCrossYOnTouch
	 */
	public boolean isDisplayCrossYOnTouch() {
		return displayCrossYOnTouch;
	}

	/**
	 * @param displayCrossYOnTouch
	 *            the displayCrossYOnTouch to set
	 */
	public void setDisplayCrossYOnTouch(boolean displayCrossYOnTouch) {
		this.displayCrossYOnTouch = displayCrossYOnTouch;
	}
	
	/**
	 * @return the crossLinesColor
	 */
	public int getCrossLinesColor() {
		return crossLinesColor;
	}

	/**
	 * @param crossLinesColor
	 *            the crossLinesColor to set
	 */
	public void setCrossLinesColor(int crossLinesColor) {
		this.crossLinesColor = crossLinesColor;
	}

	/**
	 * @return the crossLinesFontColor
	 */
	public int getCrossLinesFontColor() {
		return crossLinesFontColor;
	}

	/**
	 * @param crossLinesFontColor
	 *            the crossLinesFontColor to set
	 */
	public void setCrossLinesFontColor(int crossLinesFontColor) {
		this.crossLinesFontColor = crossLinesFontColor;
	}
}
