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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import android.os.StrictMode;
import android.util.Log;

import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.StockHistory;
import com.sprinters.bullzx.utils.Constants;

/**
 * @author Ganesh
 *
 */
public class RetreiveHistoricalDataServiceImpl implements RetreiveHistoricalDataService {

	@Override
	public List <StockHistory> retreiveStockHistoryDetails(String stockCode, String timeFrame){

		// TODO Auto-generated method stub

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		List <StockHistory>stockhis=new ArrayList<StockHistory>();
		try {

			String csvString;
			String requestStockCode=stockCode;
			URL url=null;
			URLConnection urlConn = null;
			InputStreamReader inStream = null;
			Log.i(Constants.LOG, "Building historical data Url to be invoked");
			int timePeriod=Integer.parseInt(timeFrame);
			url=constructUrl(timePeriod,requestStockCode);


			try {

				urlConn = url.openConnection();				
				inStream = new InputStreamReader(urlConn.getInputStream());				
				BufferedReader buff = new BufferedReader(inStream);				
				int lineNumber=1;			
				while ((csvString = buff.readLine()) != null) {

					if (lineNumber>1)
					{
						Log.d(Constants.LOG,"QuoteHistory " + csvString);
						StockHistory stockhistory = new StockHistory();
						// parse the csv string
						StringTokenizer tokenizer = new StringTokenizer(csvString,",");
						String hisDate= tokenizer.nextToken();

						String open = tokenizer.nextToken();
						String high = tokenizer.nextToken();
						String low = tokenizer.nextToken();
						String close = tokenizer.nextToken();
						String volume = tokenizer.nextToken();
						String adjClose = tokenizer.nextToken();

						stockhistory.setDate(hisDate);
						stockhistory.setStockCode(stockCode);
						stockhistory.setOpenValue(open);
						stockhistory.setHighValue(high);
						stockhistory.setLowValue(low);
						stockhistory.setCloseValue(close);
						stockhistory.setTradingVolume(volume);
						stockhistory.setAdjustedClosingValue(adjClose);
						stockhistory.setStockCode(stockCode);

						stockhis.add(stockhistory);
					}
					lineNumber=lineNumber+1;	
				}

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

		}			 catch (Exception e) {
			e.printStackTrace();
		}		

		return stockhis;

	}

	public URL constructUrl(int timeFrame, String requestStockCode)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		String date=sdf.format(cal.getTime());
		cal.clear();
		String day=date.substring(0,2);	
		String month=date.substring(3,5);
		month=Integer.toString(Integer.parseInt(month)-1);
		String year=date.substring(6,10);


		URL url = null;

		switch(timeFrame)
		{
		case 1:
			try {
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -30);
				String dateMonthly=sdf.format(cal.getTime());
				cal.clear();
				String pastday=dateMonthly.substring(0,2);
				String pastmonth=dateMonthly.substring(3,5);
				pastmonth=Integer.toString(Integer.parseInt(pastmonth)-1);
				String pastyear=dateMonthly.substring(6,10);
				String period="&a="+pastmonth+"&b="+pastday+"&c="+pastyear+"&d="+month+"&e="+day+"&f="+year;


				url = new URL(Constants.yahooServiceHistoricalData
						+ requestStockCode + ".NZ"						
						+ period);

			}
			catch (MalformedURLException e) {
				Log.e(Constants.LOG,"Please check the spelling of the URL:"
						+ e.toString());

			}
			break;
		case 2:
			try {
				cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -6);
				String dateMonthly=sdf.format(cal.getTime());
				cal.clear();

				String pastday=dateMonthly.substring(0,2);
				String pastmonth=dateMonthly.substring(3,5);	
				pastmonth=Integer.toString(Integer.parseInt(pastmonth)-1);
				String pastyear=dateMonthly.substring(6,10);

				String period="&a="+pastmonth+"&b="+pastday+"&c="+pastyear+"&d="+month+"&e="+day+"&f="+year;


				url = new URL(Constants.yahooServiceHistoricalData
						+ requestStockCode + ".NZ"						
						+ period);
			}
			catch (MalformedURLException e) {
				Log.e(Constants.LOG,"Please check the spelling of the URL:"
						+ e.toString());

			}
			break;
		case 3:
			try {
				cal=Calendar.getInstance();
				cal.add(Calendar.YEAR, -1);
				String dateMonthly=sdf.format(cal.getTime());
				cal.clear();

				String pastday=dateMonthly.substring(0,2);
				String pastmonth=dateMonthly.substring(3,5);
				pastmonth=Integer.toString(Integer.parseInt(pastmonth)-1);
				String pastyear=dateMonthly.substring(6,10);


				String period="&a="+pastmonth+"&b="+pastday+"&c="+pastyear+"&d="+month+"&e="+day+"&f="+year;

				url = new URL(Constants.yahooServiceHistoricalData
						+ requestStockCode + ".NZ"						
						+ period);
			}
			catch (MalformedURLException e) {
				Log.e(Constants.LOG,"Please check the spelling of the URL:"
						+ e.toString());
			}
			break;
		case 4:
			try {
				cal=Calendar.getInstance();
				cal.add(Calendar.DATE, -7);
				String dateMonthly=sdf.format(cal.getTime());
				cal.clear();

				String pastday=dateMonthly.substring(0,2);
				String pastmonth=dateMonthly.substring(3,5);	
				pastmonth=Integer.toString(Integer.parseInt(pastmonth)-1);
				String pastyear=dateMonthly.substring(6,10);


				String period="&a="+pastmonth+"&b="+pastday+"&c="+pastyear+"&d="+month+"&e="+day+"&f="+year;


				url = new URL(Constants.yahooServiceHistoricalData
						+ requestStockCode + ".NZ"					
						+ period);


			}
			catch (MalformedURLException e) {
				Log.e(Constants.LOG,"Please check the spelling of the URL:"
						+ e.toString());
			}
			break;

		}
		return url;
	}

}


