package com.sprinters.bullzx.utils;

import com.sprinters.bullzx.model.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullStockParser {
	
	public List<Stock> parse(InputStream stream) throws Exception {
		List<Stock> stocks = null;
		Stock stock = null;

		XmlPullParser pullparse = XmlPullParserFactory.newInstance()
				.newPullParser();
		pullparse.setInput(stream, "UTF-8");

		int eventType = pullparse.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String str = pullparse.getName();
			
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				stocks = new ArrayList<Stock>();
				break;
			case XmlPullParser.START_TAG:
				if (str.equals("stock")) {
					stock = new Stock();
				} else if (str.equals("code")) {
					stock.setStockCode(pullparse.nextText());
				} else if (str.equals("name")) {
					stock.setStockName(pullparse.nextText());
				} else if (str.equals("move")) {
					stock.setStockVariation(pullparse.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if (str.equals("stock") && stock != null ) {
					stocks.add(stock);
				}
				break;
			default:
				break;
			}
			eventType = pullparse.next();
		}
		stream.close();
		return stocks;
	}
}