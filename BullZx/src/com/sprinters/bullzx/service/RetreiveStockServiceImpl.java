/**
 * 
 */
package com.sprinters.bullzx.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.os.StrictMode;
import android.util.Log;

import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.StockList;
import com.sprinters.bullzx.utils.Constants;

/**
 * @author Chid
 * 
 * An Implementation for RetreiveStockService 
 * to get real time data from Yahoo Service
 *
 */
public class RetreiveStockServiceImpl implements RetreiveStockService {

	@Override
	public List<Stock> retreiveStockDetails(String stockCodes) {

		List<Stock> stockList = new ArrayList<Stock>();
		
		// TBD: Remove Strict thread and create a Async Task
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			
			String csvString;
			URL url = null;
			URLConnection urlConn = null;
			InputStreamReader inStream = null;
			Log.i(Constants.LOG, "Building String for Url to be invoked");
			try {
				url = new URL(Constants.yahooServiceUrl
						+ stockCodes						
						+ Constants.RequestParameters);

				urlConn = url.openConnection();				
				inStream = new InputStreamReader(urlConn.getInputStream());				
				BufferedReader buff = new BufferedReader(inStream);				
							
				while ((csvString = buff.readLine()) != null) {

					Stock stock = new Stock();
					Log.d(Constants.LOG,"Quote " + csvString);
					
					// parse the csv string
					StringTokenizer tokenizer = new StringTokenizer(csvString,",");
					String name = tokenizer.nextToken();
					String ticker = tokenizer.nextToken();
					String price = tokenizer.nextToken();
					String tradeDate = tokenizer.nextToken();
					String tradeTime = tokenizer.nextToken();
					String StockVariation = tokenizer.nextToken();
					String StockVariation1 = tokenizer.nextToken();
					
					stock.setStockCode(ticker.replace("\"", "").replace(".NZ", ""));
					stock.setStockName(name.replace("\"", ""));
					stock.setStockVariation(StockVariation);
					
					Log.i(Constants.LOG, "Symbol: " + ticker + " Price: "
							+ price + " Date: " + tradeDate + " Time: "
							+ tradeTime + "Change 1: " + StockVariation
							+ "Change 2: " + StockVariation1);
					//At times in input stream, random characters come. Check added to validate
					//if the String is valid
					if(ticker.length() <= 8){
					stockList.add(stock);
					}
				}
			} catch (MalformedURLException e) {
				Log.e(Constants.LOG,"Please check the spelling of the URL:"
						+ e.toString());
			} catch (IOException e1) {
				Log.e(Constants.LOG,"Can't read from the Internet: "
						+ e1.toString());
			} catch (Exception e1) {
				Log.e(Constants.LOG,"Can't read from the Internet: "
						+ e1.toString());
			} finally {
				try {
					inStream.close();
					// buff.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return stockList;
	}

}
