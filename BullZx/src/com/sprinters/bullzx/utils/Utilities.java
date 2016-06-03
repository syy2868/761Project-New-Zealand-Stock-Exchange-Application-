/**
 * 
 */
package com.sprinters.bullzx.utils;

import java.util.List;

import com.sprinters.bullzx.model.Stock;

/**
 * @author Chid
 *
 */
public class Utilities {
	/**
	 * This method returns the list of stocks for which details are
	 * required as a string, which inturn forms
	 * the request parameters for Call to Yahoo
	 * @param listOfStocks
	 * @return StringlistofStock
	 */
	public static String getStockListasString(List<Stock> listOfStocks){
		
		String stringListOfStocks = "";		
		for(int i=0; i<listOfStocks.size();i++){
			
			if(i==0){
				stringListOfStocks = listOfStocks.get(i).getStockCode()+".NZ";
				
			}else{		
				
				stringListOfStocks = stringListOfStocks+"+"+listOfStocks.get(i).getStockCode()+".NZ";
			}
			
		}
		
		
		return stringListOfStocks;	 
	}

}
