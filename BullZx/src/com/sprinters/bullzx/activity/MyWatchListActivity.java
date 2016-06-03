package com.sprinters.bullzx.activity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sprinters.bullzx.R;
import com.sprinters.bullzx.R.color;
import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.WatchList;
import com.sprinters.bullzx.service.RetreiveStockService;
import com.sprinters.bullzx.service.RetreiveStockServiceImpl;
import com.sprinters.bullzx.utils.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MyWatchListActivity extends Activity {
	private TableLayout tableLayout;
	private PullStockParser parser;
	private RemoveStockParser remover;
	private List<Stock> stockList;
	private List<Stock> responseStockList;
	private WatchList watchList;
	private InputStream stream;
	private Drawable drawable;
	private int screenWidth;
	private int screenHeight;
	private MyApp application;
	public static TableRow tableRow;
	RetreiveStockService retreiveStockService = new RetreiveStockServiceImpl();
	Button button1;
	Button button2;
	Button button3;
	String dataFileName;
	List<String> toDelete;
	List<Stock> watchlistStocks;

private Menu mymenu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Add our menu
		
		super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.watchlist_menu, menu);
         
        // We should save our menu so we can use it to reset our updater.
        mymenu = menu;
		return true;
	}


	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // action with ID action_refresh was selected
	    case R.id.action_refresh:
	      Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    // action with ID action_settings was selected
	    case R.id.action_settings:
	      Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  } 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mywatchlist);
		
		application = (MyApp)getApplication();
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button2.setVisibility(View.INVISIBLE);
		button3.setVisibility(View.INVISIBLE);

		watchList = application.getAppWatchList();
		//stockList=watchList.getStockList();
		
		
		//Call to fetch Real time data
		responseStockList = retreiveStockService.retreiveStockDetails(Utilities.getStockListasString(watchList.getStockList()));			
		if(!responseStockList.isEmpty()){
			watchList.setStockList(responseStockList);
		}
		
		
		watchlistStocks=watchList.getStockList();
		/*try {
			File inFile = new File(this.getFilesDir() + "/NZ50.xml");
			if (!inFile.exists()) {
				InputStream stream = null;
				OutputStream output = null;

				stream = this.getAssets().open("NZX50.xml");
				output = new BufferedOutputStream(new FileOutputStream(
						this.getFilesDir() + "/NZ50.xml"));

				byte data[] = new byte[1024];
				int count;

				while ((count = stream.read(data)) != -1) {
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				stream.close();

				output = null;
			}
			dataFileName = this.getFilesDir() + "/NZ50.xml";
			stream = new BufferedInputStream(new FileInputStream(dataFileName));
			parser = new PullStockParser();
			stockList = parser.parse(stream);
			
			//Call to fetch Real time data
			responseStockList = retreiveStockService.retreiveStockDetails(Utilities.getStockListasString(stockList));			
			
			watchList.setStockList(responseStockList);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

//		if (watchList.getStockList().isEmpty() && stockList.isEmpty()) {
//			System.out.println("Watchlist is empty");
////			 TextView textView = new TextView(this);
////			 textView.setText("Your watch list is currently empty.\\nPlease click on + sign to add.");
////			 textView.setPadding(10, 35, 10, 55);
////			 textView.setBackgroundColor(color.AliceBlue);
//			TextView textview = (TextView) this.findViewById(R.id.textview);
//			textview.setVisibility(View.VISIBLE);
//		}
//		// Goes into this else if block, when real time data is empty. It just populates the
//		// watchlist with the favourite stocks and default blue arrows.(We could add any other symbol in this case!! what say?)
//		else if(!responseStockList.isEmpty()) {
//			watchList.setStockList(stockList);
//			watchlistStocks=watchList.getStockList();
//			generateMyWatchListTable();
//		} else {
//			generateMyWatchListTable();
//		}
		
		if (watchList.getStockList().isEmpty()) {
			System.out.println("Watchlist is empty");
//			 TextView textView = new TextView(this);
//			 textView.setText("Your watch list is currently empty.\\nPlease click on + sign to add.");
			 
			TextView textview = (TextView) this.findViewById(R.id.textview);
			textview.setGravity(Gravity.CENTER_VERTICAL);
			textview.setVisibility(View.VISIBLE);
//			textview.setPadding(10, 35, 10, 55);
			
			textview.setBackgroundColor(color.AliceBlue);
		} else {
			generateMyWatchListTable();
		}
	}

	protected void onDestroy() {
		application.SaveWatchListToXML();
		super.onDestroy();
		
	}

	public void generateMyWatchListTable() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		tableLayout = (TableLayout) this.findViewById(R.id.tablelayout);
		tableLayout.setStretchAllColumns(true);

		TextView textview = (TextView) this.findViewById(R.id.textview);
		textview.setVisibility(View.INVISIBLE);

		StockCodeToNameMapper mapper = new StockCodeToNameMapper();
		for (int i = 0; i < watchlistStocks.size(); i++) {
			Stock stock = watchlistStocks.get(i);

			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.rgb(255, 255, 255));

			TextView textView1 = new TextView(this);
			textView1.setText(stock.getStockCode());
			textView1.setSingleLine(true);
			textView1.setGravity(Gravity.CENTER);
			textView1.setBackgroundResource(R.drawable.shape_mywatchlist);

			TextView textView2 = new TextView(this);
			textView2.setText(mapper.getStockName(stock.getStockCode()));
			textView2.setWidth((int) (screenWidth * 0.7));
			textView2.setSingleLine(true);
			textView2.setEllipsize(TruncateAt.END);
			textView2.setBackgroundResource(R.drawable.shape_mywatchlist);

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
			textView3.setBackgroundResource(R.drawable.shape_mywatchlist);

			tableRow.addView(textView1);
			tableRow.addView(textView2);
			tableRow.addView(textView3);

			// Enabling check box for long press to enable user delete a single
			// or list of stocks
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String stockCode = "";
					MyWatchListActivity.tableRow = (TableRow) v;
					TextView textView = (TextView)MyWatchListActivity.tableRow.getChildAt(0);
					if (textView != null) {
						stockCode = (String) textView.getText();
					}
					// application = (MyApp)getApplication();

					// Toast.makeText(MyWatchListActivity.this, " Navigating ",
					// Toast.LENGTH_SHORT).show();

					// After 2 seconds redirect to another intent
					Intent i = new Intent(getBaseContext(),
							StockDetailActivity.class);// Passing intent to
														// watchlist activity
					i.putExtra("stockCode", stockCode);
					startActivity(i);
					// Intent activityChangeIntent = new
					// Intent(MyWatchListActivity.this,
					// StockDetailActivity.class);

					// currentContext.startActivity(activityChangeIntent);
					// MyWatchListActivity.this.startActivity(activityChangeIntent);
				}
			});

			// Enabling check box for long press to enable user delete a single or list of stocks
			tableRow.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					final String stockCodes[] = new String[100];
					toDelete = new LinkedList<String>();
					int chkBoxId = 12345;
					button1.setVisibility(View.INVISIBLE);
					button2.setVisibility(View.VISIBLE);
					button3.setVisibility(View.VISIBLE);
					Toast.makeText(MyWatchListActivity.this,
							"Please select stocks you want to remove.",
							Toast.LENGTH_SHORT).show();

					for (int i = 0; i < tableLayout.getChildCount(); i++) {
						TableRow tableRow = (TableRow) tableLayout
								.getChildAt(i);

						if (tableRow.getChildCount() != 3)
							return true;

						TextView textView1 = (TextView) tableRow.getChildAt(0);
						TextView textView2 = (TextView) tableRow.getChildAt(1);
						TextView textView3 = (TextView) tableRow.getChildAt(2);
						textView2.setWidth((int) (screenWidth * 0.6));

						final CheckBox checkBox = new CheckBox(getBaseContext());
						checkBox.setChecked(false);
						stockCodes[chkBoxId - 12345] = textView1.getText()
								.toString();
						checkBox.setId(chkBoxId++);
						CheckBox.OnClickListener ocl = new CheckBox.OnClickListener() {

							@Override
							public void onClick(View v) {
								String stockCode = stockCodes[((CheckBox) v)
										.getId() - 12345];
								if (((CheckBox) v).isChecked()) {
									toDelete.add(stockCode);
								} else {
									toDelete.remove(stockCode);
								}
							}
						};
						checkBox.setOnClickListener(ocl);
						tableRow.removeAllViews();
						tableRow.addView(checkBox);
						tableRow.addView(textView1);
						tableRow.addView(textView2);
						tableRow.addView(textView3);

					}

					return true;
				}
			});

			tableLayout.addView(tableRow);
		}
	}

	public void navigateToNZX50(View view) {
		Intent i = new Intent(this, StockListActivity.class);    //  navigate to NZX50 screen to add stocks to the watchlist
		startActivity(i);
	}

	public void deleteAndReloadMyWatchList(View view) {
		Iterator delIterator=toDelete.iterator();
		while(delIterator.hasNext()){
			String todel=(String)delIterator.next();
			Iterator<Stock> stockIterator=watchlistStocks.iterator();
			while(stockIterator.hasNext()){
				Stock s=stockIterator.next();
				String sCode=s.getStockCode();
				if(sCode.equalsIgnoreCase(todel)){
					stockIterator.remove();
				}
			}
		}
		
		watchList.setStockList(watchlistStocks);
//		application.SaveWatchListToXML();
		onResume();
		/*remover = new RemoveStockParser();
		try {
			
			 * Toast.makeText(MyWatchListActivity.this, stockCode +
			 * " is removed.", Toast.LENGTH_SHORT) .show();
			 
			remover.removeStock(toDelete, dataFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent i = new Intent(this, MyWatchListActivity.class);
		startActivity(i);*/
	}

	public void reloadMyWatchList(View view) {
		//onResume();
			Intent i = new Intent(this, MyWatchListActivity.class);
			startActivity(i);
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
	
	@Override
	protected void onResume() {

	   super.onResume();
	   this.onCreate(null);
	}
}
