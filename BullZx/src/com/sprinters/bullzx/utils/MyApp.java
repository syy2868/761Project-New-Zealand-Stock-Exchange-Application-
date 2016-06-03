package com.sprinters.bullzx.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.WatchList;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Application object to store a users watchlist. Provides
 * activities access to the same watchlist object. Also able
 * to save this watchlist object to an xml file.
 * @author john
 *
 */

public class MyApp extends Application {
	
	private WatchList watchList;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onCreate() {
		this.LoadWatchListFromXML();
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	
	@Override
	public void onTerminate() {
		this.SaveWatchListToXML();
		super.onTerminate();
	}
	
	public WatchList getAppWatchList() {
		return this.watchList;
	}
	
	public void setAppWatchList(WatchList watchList) {
		this.watchList = watchList;
	}
	
	public void LoadWatchListFromXML() {
		try {
			this.watchList = new WatchList();
			
			System.out.println("Checking for watchlist.xml");
			
			//check if there's watchlist.xml
			File inFile = new File(getFilesDir(), "watchlist.xml");
			
			//if there is, load it into the application's watchList
			//if it doesn't exist, load an empty watchList
			if (inFile.exists()) {
				System.out.println("Watchlist.xml was found.");
				InputStream stream = new BufferedInputStream(new FileInputStream(inFile));
				PullStockParser parser = new PullStockParser();
				List<Stock> stockList = parser.parse(stream);
				
				watchList.setStockList(stockList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SaveWatchListToXML() {
		System.out.println("Saving watchlist to watchlist.xml");
		
		File file = new File(getFilesDir(), "watchlist.xml");
		
		Document dom;
		Element e = null;
		
		//Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// create instance of DOM
			dom = db.newDocument();
			
			// create the root element
			Element rootEle = dom.createElement("watchlist");
			
			// create data elements and place them under root
			for (Stock stock : watchList.getStockList()) {
				Element stockElement = dom.createElement("stock");
				
				//save code
				e = dom.createElement("code"); //Edit @Praveen
				e.appendChild(dom.createTextNode(stock.getStockCode()));
				stockElement.appendChild(e);
				//save name
				e = dom.createElement("name");
				e.appendChild(dom.createTextNode(stock.getStockName()));
				stockElement.appendChild(e);
				//save move (variation)
				e = dom.createElement("move");
				e.appendChild((dom.createTextNode(stock.getStockVariation())));
				stockElement.appendChild(e);
				
				rootEle.appendChild(stockElement);
			}
			
			dom.appendChild(rootEle);
			
			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//				tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "watchlist.dtd");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				
				// send DOM to file
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));
				
			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage()); 
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
	}
}
