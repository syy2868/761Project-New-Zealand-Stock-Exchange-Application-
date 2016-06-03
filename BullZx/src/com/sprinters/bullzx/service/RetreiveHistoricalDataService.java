/**
 * 
 */
package com.sprinters.bullzx.service;

import java.util.List;

import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.StockHistory;

/**
 * @author Ganesh
 *
 */
public interface RetreiveHistoricalDataService {

	public List <StockHistory> retreiveStockHistoryDetails(String stockCode, String timeFrame);
}
