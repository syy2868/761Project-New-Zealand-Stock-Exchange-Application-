package com.sprinters.bullzx.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sprinters.bullzx.R;
import com.sprinters.bullzx.service.RetreiveStockService;
import com.sprinters.bullzx.service.RetreiveStockServiceImpl;
import com.sprinters.bullzx.service.RetreiveStockServiceStubImpl;
import com.sprinters.bullzx.model.*;
import com.sprinters.bullzx.utils.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class StockListActivity extends Activity {
	private TableLayout tableLayout;
	private List<com.sprinters.bullzx.model.Stock> stockList;
	private Drawable drawable;
	private int screenWidth;
	private int screenHeight;
	private AutoCompleteTextView actv;
	Button button;
	private MyApp application;
	private WatchList watchlist;
	List<Stock> watchlistStocks;
	private RemoveStockParser remover;
	String dataFileName;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.stocklist50);
		
		//setting the bookmark variable value for the stocks in watchlist
		// author @Praveen
		application=(MyApp)getApplication();
		watchlist=application.getAppWatchList();
		watchlistStocks=watchlist.getStockList();
		
		Iterator<Stock> stockIterator=watchlistStocks.iterator();
		while(stockIterator.hasNext()){
			stockIterator.next().setBookmarked(true);
		}
		
		//TBD: Remove Strict thread and create a Async Task
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 

		String requestCodes="";		

		try {
			RetreiveStockService retrieveData = new RetreiveStockServiceImpl();
			//Temporarily Commented till Add functionality is completed
			/*stockList = retrieveData.retreiveStockDetails(requestCodes);*/
			stockList = retrieveData.retreiveStockDetails(Constants.RequestCodesString);
			generateStockListTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> autoCompleteStockList = new ArrayList<String>();
		for(Stock item :stockList){
			autoCompleteStockList.add(item.getStockName()+":"+item.getStockCode());
		}			
	   
	   ArrayAdapter adapter = new ArrayAdapter
	   (this,android.R.layout.simple_list_item_1,autoCompleteStockList.toArray());
	   
	   actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);	   
	   actv.setAdapter(adapter);	
	   AdapterView.OnItemClickListener adOc =  actv.getOnItemClickListener();
	   
	   btnClick(actv) ;
	   
	   
	}

	protected void onDestroy() {
		super.onDestroy();
	}
	
	//On CLick of GO button of AutoComplete SUggestion
	public void btnClick(final AutoCompleteTextView actv2) {
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                	Intent i = new Intent(getBaseContext(), StockDetailActivity.class);    //  navigate to NZX50 screen to add stocks to the watchlist
                	//Create a bundle object
                    Bundle b = new Bundle();
                     
                    //Inserts a String value into the mapping of this Bundle
                    String code = actv2.getText().toString();
                    b.putString("name", code.substring(code.indexOf(':'), code.length()));                                 
                     
                    //Add the bundle to the intent.
                    i.putExtras(b);
                	
                	startActivity(i);
                }
        });
	}


	// generating stocklist table layout
	// generating stocklist table layout
		public void generateStockListTable() {
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;

			tableLayout = (TableLayout) this.findViewById(R.id.tablelayout);
			tableLayout.setStretchAllColumns(true);
//			tableLayout.setBackgroundResource(R.drawable.shape_nzx50);
	//
//			TextView textview = (TextView) this.findViewById(R.id.textview);
//			textview.setVisibility(View.INVISIBLE);
			StockCodeToNameMapper mapper = new StockCodeToNameMapper();
			for (int i = 0; i < stockList.size(); i++) {
				final Stock stock = stockList.get(i);

				TableRow tableRow = new TableRow(this);
				tableRow.setBackgroundColor(Color.rgb(255, 255, 255));
				

				TextView textView1 = new TextView(this);
				textView1.setText(stock.getStockCode());
				textView1.setSingleLine(true);
				textView1.setGravity(Gravity.CENTER);
				textView1.setBackgroundResource(R.drawable.shape_nzx50);
				textView1.setHeight((int) (screenHeight * 0.088));

				TextView textView2 = new TextView(this);
				textView2.setText(mapper.getStockName(stock.getStockCode()));
				textView2.setWidth((int) (screenWidth * 0.5));
				textView2.setSingleLine(true);
				textView2.setEllipsize(TruncateAt.END);
				textView2.setGravity(Gravity.CENTER_VERTICAL);
				textView2.setBackgroundResource(R.drawable.shape_nzx50);
				textView2.setHeight((int) (screenHeight * 0.088));
				

				TextView textView3 = new TextView(this);
				if (stock.getStockVariation().contains("+")) {
					drawable = getResources().getDrawable(R.drawable.up);
				} else if (stock.getStockVariation().contains("-")) {
					drawable = getResources().getDrawable(R.drawable.down);
				} else {
					drawable = getResources().getDrawable(R.drawable.unchanged);
				}
				textView3.setCompoundDrawables(setDrawbleBound(drawable), null,
						null, null);
				textView3.setSingleLine(true);
				textView3.setGravity(Gravity.CENTER);
				textView3.setBackgroundResource(R.drawable.shape_nzx50);
				textView3.setHeight((int) (screenHeight * 0.088));
				


				final ImageButton iButton=new ImageButton(this,null,android.R.attr.buttonStyleSmall);
				iButton.setVisibility(View.VISIBLE);
//				iButton.setBackgroundColor(Color.rgb(255, 255, 255));
				iButton.setImageResource(R.drawable.bookmark_no);
				
				Iterator<Stock> stockIterator=watchlistStocks.iterator();
				while(stockIterator.hasNext()){
					Stock s=stockIterator.next();
					String sCode=s.getStockCode();
					
					if(sCode.equalsIgnoreCase(stock.getStockCode())){
						stock.setBookmarked(true);
						iButton.setImageResource(R.drawable.bookmark_yes);
						break;
					}
					else{
						stock.setBookmarked(false);
						iButton.setImageResource(R.drawable.bookmark_no);
					}
				}
				
//				iButton.setImageResource(R.drawable.bookmark_no);
//				iButton.setImageResource(R.drawable.bookmark_yes);
				iButton.setBackgroundResource(R.drawable.shape_nzx50);
				
				iButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(!stock.isBookmarked()){
							Toast.makeText(StockListActivity.this, stock.getStockCode().toString()+" added to Watchlist ",Toast.LENGTH_SHORT).show

	();
							stock.setBookmarked(true);
							addToWatchlist(stock);
							iButton.setImageResource(R.drawable.bookmark_yes);
						}
						else{
							Toast.makeText(StockListActivity.this, stock.getStockCode().toString()+" removed from Watchlist ",Toast.LENGTH_SHORT).show();
							stock.setBookmarked(false);
							removeFromWatchlist(stock);
							iButton.setImageResource(R.drawable.bookmark_no);
						}
						
						
					}
				});
				
				
				
				
				tableRow.addView(textView1);
				tableRow.addView(textView2);
				tableRow.addView(textView3);
				tableRow.addView(iButton);

				//tableRow.setBackgroundResource(R.drawable.shape_nzx50);


				tableLayout.addView(tableRow);// adding rows to table
			}
		}


	public Drawable setDrawbleBound(Drawable drawable) {
		if ((screenWidth == 320) && (screenHeight == 480)) {
			drawable.setBounds((int) (screenWidth * 0.01), 0,
					(int) (screenWidth * 0.06), (int) (screenHeight * 0.04));
		} else if ((screenWidth == 480) && (screenHeight == 800)) {
			drawable.setBounds((int) (screenWidth * 0.01), 0,
					(int) (screenWidth * 0.06), (int) (screenHeight * 0.035));
		} else if ((screenWidth == 480) && (screenHeight == 854)) {
			drawable.setBounds((int) (screenWidth * 0.022), 0,
					(int) (screenWidth * 0.06), (int) (screenHeight * 0.023));
		} else if ((screenWidth == 240) && (screenHeight == 320)) {
			drawable.setBounds((int) (screenWidth * 0.01), 0,
					(int) (screenWidth * 0.06), (int) (screenHeight * 0.048));
		} else {
			drawable.setBounds((int) (screenWidth * 0.01), 0,
					(int) (screenWidth * 0.06), (int) (screenHeight * 0.025));
		}

		return drawable;
	}
	
	public void addToWatchlist(Stock fnStock){
		Stock localStock=fnStock;
		watchlistStocks.add(localStock);
		watchlist.setStockList(watchlistStocks);
//		application.setAppWatchList(watchlist);
//		application.SaveWatchListToXML();
	
		
		
	}
	
	public void removeFromWatchlist(Stock fnStock){
		Stock localStock=fnStock;
		Iterator<Stock> stockIterator=watchlistStocks.iterator();
		while(stockIterator.hasNext()){
			Stock s=stockIterator.next();
			String sCode=s.getStockCode();
			if(sCode.equalsIgnoreCase(localStock.getStockCode().toString())){
				stockIterator.remove();
				break;
			}
			
		}
		
		
		watchlist.setStockList(watchlistStocks);
//		application.SaveWatchListToXML();
		
//		List<String> toDelete=new ArrayList<String>();
//		toDelete.add(fnStock.getStockCode());
//		remover = new RemoveStockParser();
//		dataFileName = this.getFilesDir() + "/NZ50.xml";
//		try {
//			remover.removeStock(toDelete, dataFileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
}
