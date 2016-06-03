/**
 * 
 */
package com.sprinters.bullzx.model;

import java.util.Date;

/**
 * @author Ganesh
 *
 */
public class StockHistory {

private String date;
/**
 * @return the date
 */
public String getDate() {
	return date;
}
/**
 * @param date the date to set
 */
public void setDate(String date) {
	this.date = date;
}
/**
 * @return the stockCode
 */
public String getStockCode() {
	return stockCode;
}
/**
 * @param stockCode the stockCode to set
 */
public void setStockCode(String stockCode) {
	this.stockCode = stockCode;
}
/**
 * @return the openValue
 */
public String getOpenValue() {
	return openValue;
}
/**
 * @param openValue the openValue to set
 */
public void setOpenValue(String openValue) {
	this.openValue = openValue;
}
/**
 * @return the highValue
 */
public String getHighValue() {
	return highValue;
}
/**
 * @param highValue the highValue to set
 */
public void setHighValue(String highValue) {
	this.highValue = highValue;
}
/**
 * @return the lowValue
 */
public String getLowValue() {
	return lowValue;
}
/**
 * @param lowValue the lowValue to set
 */
public void setLowValue(String lowValue) {
	this.lowValue = lowValue;
}
/**
 * @return the closeValue
 */
public String getCloseValue() {
	return closeValue;
}
/**
 * @param closeValue the closeValue to set
 */
public void setCloseValue(String closeValue) {
	this.closeValue = closeValue;
}
/**
 * @return the tradingVolume
 */
public String getTradingVolume() {
	return tradingVolume;
}
/**
 * @param tradingVolume the tradingVolume to set
 */
public void setTradingVolume(String tradingVolume) {
	this.tradingVolume = tradingVolume;
}
/**
 * @return the adjustedClosingValue
 */
public String getAdjustedClosingValue() {
	return adjustedClosingValue;
}
/**
 * @param adjustedClosingValue the adjustedClosingValue to set
 */
public void setAdjustedClosingValue(String adjustedClosingValue) {
	this.adjustedClosingValue = adjustedClosingValue;
}
private String stockCode;
private String openValue;
private String highValue;
private String lowValue;
private String closeValue;
private String tradingVolume;
private String adjustedClosingValue;

}
