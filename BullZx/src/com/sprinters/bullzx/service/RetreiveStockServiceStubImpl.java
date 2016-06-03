/**
 * 
 */
package com.sprinters.bullzx.service;

import java.util.ArrayList;
import java.util.List;

import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.StockList;

/**
 * @author Chid
 * Stubbed implementation of RetreiveStockService
 * untill the Webservice call is up	
 */
public class RetreiveStockServiceStubImpl implements RetreiveStockService {

	/* (non-Javadoc)
	 * @see com.sprinters.bullzx.service.RetreiveStockService#retreiveStockDetails(java.lang.String)
	 */
		
	public List<Stock> retreiveStockDetails(String stockCode) {
		
		List<Stock> listOfstock = new ArrayList<Stock>();
				
		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		Stock stock3 = new Stock();
		
		stock1.setAskValue("20");
		stock1.setBidValue("19");
		stock1.setStockCode("ANZ");
		stock1.setStockName("Air New Zealand");
		stock1.setStockOpenValue("21");
		stock1.setStockVariation("0.508");
		stock1.setTradingVolume("55000");
		
		stock2.setAskValue("0.012");
		stock2.setBidValue("0.011");
		stock2.setStockCode("NTL");
		stock2.setStockName("New Talisman Gold mines Limited");
		stock2.setStockOpenValue("21");
		stock2.setStockVariation("-1.064");
		stock2.setTradingVolume("55000");
		
		stock3.setAskValue( "2.40");
		stock3.setBidValue( "2.39");
		stock3.setStockCode("MRP");
		stock3.setStockName("Mighty River Power Limited");
		stock3.setStockOpenValue("2.55");
		stock3.setStockVariation("0.000");
		stock3.setTradingVolume("55000");		
		
		listOfstock.add(stock1);
		listOfstock.add(stock2);
		listOfstock.add(stock3);
				
		return listOfstock;
	}

}
