/**
 * 
 */
package com.sprinters.bullzx.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chid
 * This class is created temporarily to store the names of
 * stock. The name which is received in response from 
 * contains only 17 Characters. 
 * This would be changed in Sprint 3, by having a direct call
 * separately to NZX website to get the list of Top 50 stocks with name.
 */
public class StockCodeToNameMapper {
	
	public Map<String, String> stockName = new HashMap<String, String>();
	
	public StockCodeToNameMapper(){
		stockName.put("AIR", "Air New Zealand Ltd");
		stockName.put("ARG", "Argosy Property Ltd");
		stockName.put("ATM", "The a2 Milk Company Ltd");
		stockName.put("CEN", "Contact Energy Ltd");
		stockName.put("CNU", "Chorus Ltd");
		stockName.put("DNZ", "DNZ Property Fund Ltd");
		stockName.put("EBO", "Ebos Group Ltd");
		stockName.put("FBU", "Fletcher Building Ltd");
		stockName.put("FPH", "Fisher & Paykel Healthcare Corp Ltd");
		stockName.put("FRE", "Freightways Ltd");
		stockName.put("FSF", "Fonterra Shareholders Fund");
		stockName.put("GMT", "Goodman Property Trust");
		stockName.put("GNE", "Genesis Energy Ltd");
		stockName.put("KMD", "Kathmandu Holdings Ltd");
		stockName.put("MRP", "Mighty River Power Ltd");
		stockName.put("NZO", "New Zealand Oil & Gas Ltd");
		stockName.put("NZX", "NZX Ltd");
		stockName.put("PEB", "Pacific Edge Ltd");
		
	}
	
	public String getStockName(String stockCode )
	{		
		return stockName.get(stockCode);		
	}

}
