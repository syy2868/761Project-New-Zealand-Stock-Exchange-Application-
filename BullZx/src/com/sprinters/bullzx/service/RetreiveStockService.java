/**
 * Interface for RetreiveStockService
 */
package com.sprinters.bullzx.service;

import java.util.List;

import com.sprinters.bullzx.model.Stock;

/**
 * @author Chid
 *
 */
public interface RetreiveStockService {
	
	public List<Stock> retreiveStockDetails(String stockCodes);

}
