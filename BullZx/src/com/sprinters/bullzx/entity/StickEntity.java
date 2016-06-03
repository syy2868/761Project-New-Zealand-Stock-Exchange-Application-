package com.sprinters.bullzx.entity;

public class StickEntity implements IStickEntity {

	private double high;
	private double low;
	private int date;

	public StickEntity(double high, double low, int date) {
		super();
		this.high = high;
		this.low = low;
		this.date = date;
	}

	public StickEntity() {
		super();
	}

	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}
}
