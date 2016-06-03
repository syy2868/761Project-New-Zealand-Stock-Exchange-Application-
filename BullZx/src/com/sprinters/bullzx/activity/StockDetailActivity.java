package com.sprinters.bullzx.activity;

import java.util.ArrayList;
import java.util.List;

import com.sprinters.bullzx.R;
import com.sprinters.bullzx.model.StockHistory;
import com.sprinters.bullzx.service.RetreiveHistoricalDataService;
import com.sprinters.bullzx.service.RetreiveHistoricalDataServiceImpl;
import com.sprinters.bullzx.utils.MyApp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.graphics.Color;

import com.sprinters.bullzx.entity.DateValueEntity;
import com.sprinters.bullzx.entity.IStickEntity;
import com.sprinters.bullzx.entity.LineEntity;
import com.sprinters.bullzx.entity.ListChartData;
import com.sprinters.bullzx.entity.OHLCEntity;
import com.sprinters.bullzx.entity.StickEntity;
import com.sprinters.bullzx.view.GridChart;
import com.sprinters.bullzx.view.LineChart;
import com.sprinters.bullzx.view.StickChart;

public class StockDetailActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private MyApp application;
	private static View dialogView;
	private static String stockCode;
	static List<IStickEntity> ohlcList;
	static List<IStickEntity> volList;
	static LineChart linechart;
	static StickChart stickchart;
	private LayoutInflater factory;

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);

		Intent intent = this.getIntent();
		stockCode = intent.getStringExtra("stockCode");

		factory = LayoutInflater.from(this);
		dialogView = factory.inflate(R.layout.fragment_section_dummy, null);

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}

				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	private static List<DateValueEntity> initMA(int days) {

		if (days == 0) {
			return null;
		}

		List<DateValueEntity> MA5Values = new ArrayList<DateValueEntity>();

		for (int i = 0; i < ohlcList.size(); i++) {
			float close = (float) ((OHLCEntity) ohlcList.get(i)).getClose();
			MA5Values.add(new DateValueEntity(close, ohlcList.get(i).getDate()));
		}

		return MA5Values;
	}

	private static void initLineChart(List<StockHistory> stockHisList, String cmd) {
		linechart = (LineChart) dialogView.findViewById(R.id.linechart);
		List<LineEntity<DateValueEntity>> line = new ArrayList<LineEntity<DateValueEntity>>();
		LineEntity<DateValueEntity> MA = new LineEntity<DateValueEntity>();
		
		if (Integer.parseInt(cmd) == 1) {
			MA.setTitle("30days");
			MA.setLineColor(Color.WHITE);
			MA.setLineData(initMA(stockHisList.size()));
			line.add(MA);
		} else if (Integer.parseInt(cmd) == 2) {
			MA.setTitle("6months");
			MA.setLineColor(Color.WHITE);
			MA.setLineData(initMA(stockHisList.size()));
			line.add(MA);
		} else if (Integer.parseInt(cmd) == 3) {
			MA.setTitle("1year");
			MA.setLineColor(Color.WHITE);
			MA.setLineData(initMA(stockHisList.size()));
			line.add(MA);
		} else if (Integer.parseInt(cmd) == 4) {
			MA.setTitle("5days");
			MA.setLineColor(Color.WHITE);
			MA.setLineData(initMA(stockHisList.size()));
			line.add(MA);
		}

		linechart.setAxisXColor(Color.LTGRAY);
		linechart.setAxisYColor(Color.LTGRAY);
		linechart.setBorderColor(Color.LTGRAY);
		linechart.setLongitudeFontSize(14);
		linechart.setLongitudeFontColor(Color.WHITE);
		linechart.setLatitudeColor(Color.GRAY);
		linechart.setLatitudeFontColor(Color.WHITE);
		linechart.setLongitudeColor(Color.GRAY);
		linechart.setMaxValue(280);
		linechart.setMinValue(240);
		linechart.setMaxPointNum(stockHisList.size());
		linechart.setDisplayLongitudeTitle(true);
		linechart.setDisplayLatitudeTitle(true);
		linechart.setDisplayLatitude(true);
		linechart.setDisplayLongitude(true);
		linechart.setLatitudeNum(5);
		linechart.setLongitudeNum(5);
		linechart.setDataQuadrantPaddingTop(5);
		linechart.setDataQuadrantPaddingBottom(5);
		linechart.setDataQuadrantPaddingLeft(5);
		linechart.setDataQuadrantPaddingRight(5);
		linechart.setAxisYTitleQuadrantWidth(50);
		linechart.setAxisXTitleQuadrantHeight(20);
		linechart.setAxisXPosition(GridChart.AXIS_X_POSITION_BOTTOM);
		linechart.setAxisYPosition(GridChart.AXIS_Y_POSITION_RIGHT);
		linechart.setBackgroundColor(Color.BLACK);

		linechart.setLinesData(line);
	}

	private static void initStickChart(List<StockHistory> stockHisList) {
		stickchart = (StickChart) dialogView.findViewById(R.id.stickchart);

		stickchart.setAxisXColor(Color.LTGRAY);
		stickchart.setAxisYColor(Color.LTGRAY);
		stickchart.setLatitudeColor(Color.GRAY);
		stickchart.setLongitudeColor(Color.GRAY);
		stickchart.setBorderColor(Color.LTGRAY);
		stickchart.setLongitudeFontColor(Color.WHITE);
		stickchart.setLatitudeFontColor(Color.WHITE);
		stickchart.setDataQuadrantPaddingTop(6);
		stickchart.setDataQuadrantPaddingBottom(1);
		stickchart.setDataQuadrantPaddingLeft(1);
		stickchart.setDataQuadrantPaddingRight(1);
		stickchart.setAxisYTitleQuadrantWidth(50);
		stickchart.setAxisXTitleQuadrantHeight(20);
		stickchart.setAxisXPosition(GridChart.AXIS_X_POSITION_BOTTOM);
		stickchart.setAxisYPosition(GridChart.AXIS_Y_POSITION_RIGHT);

		stickchart.setMaxSticksNum(stockHisList.size());
		stickchart.setLatitudeNum(2);
		stickchart.setLongitudeNum(3);
		stickchart.setMaxValue(10000);
		stickchart.setMinValue(100);

		stickchart.setDisplayLongitudeTitle(true);
		stickchart.setDisplayLatitudeTitle(true);
		stickchart.setDisplayLatitude(true);
		stickchart.setDisplayLongitude(true);
		stickchart.setBackgroundColor(Color.BLACK);

		stickchart.setDataMultiple(100);
		stickchart.setAxisYDecimalFormat("#,##0.00");
		stickchart.setAxisXDateTargetFormat("yyyy/MM/dd");
		stickchart.setAxisXDateSourceFormat("yyyyMMdd");

		stickchart.setStickData(new ListChartData<IStickEntity>(volList));
	}

	private static void initVOL(List<StockHistory> stockHisList) {
		List<IStickEntity> stick = new ArrayList<IStickEntity>();

		for (int i = 0; i < stockHisList.size(); i++) {
			StockHistory stockHistory = stockHisList.get(i);
			int date = Integer.parseInt(stockHistory.getDate().replaceAll("-",
					""));
			double tradingVolume = Double.valueOf(stockHistory
					.getTradingVolume());

			stick.add(new StickEntity(tradingVolume, 0, date));
		}

		volList = new ArrayList<IStickEntity>();
		for (int i = stick.size(); i > 0; i--) {
			volList.add(stick.get(i - 1));
		}

		// this.vol.addAll(stick);
	}

	private static void initOHLC(List<StockHistory> stockHisList) {
		List<IStickEntity> ohlc = new ArrayList<IStickEntity>();

		for (int i = stockHisList.size()-1; i >= 0; i--) {
			StockHistory stockHistory = stockHisList.get(i);
			int date = Integer.parseInt(stockHistory.getDate().replaceAll("-",
					""));
			double open = Double.valueOf(stockHistory.getOpenValue());
			double high = Double.valueOf(stockHistory.getHighValue());
			double low = Double.valueOf(stockHistory.getLowValue());
			double close = Double.valueOf(stockHistory.getCloseValue());

			ohlc.add(new OHLCEntity(open, high, low, close, date));
		}
		
		ohlcList = new ArrayList<IStickEntity>();
		ohlcList.addAll(ohlc);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				// The first section of the app is the most interesting -- it
				// offers
				// a launchpad into the other demonstrations in this example
				// application.
				return new LaunchpadSectionFragment();

			default:
				// The other sections of the app are dummy placeholders.
				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			if (position == 0)
				return "Realtime Data";
			// return "Realtime Data " + (position + 1);
			else
				return "Historical Data ";
			// return "Historical Data " + (position + 1);
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class LaunchpadSectionFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_section_launchpad, container, false);

			// Demonstration of a collection-browsing activity.
			/*
			 * rootView.findViewById(R.id.demo_collection_button)
			 * .setOnClickListener(new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View view) { Intent intent = new
			 * Intent(getActivity(), CollectionDemoActivity.class);
			 * startActivity(intent); } });
			 */

			// Demonstration of navigating to external activities.
			rootView.findViewById(R.id.demo_external_activity)
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// Create an intent that asks the user to pick a
							// photo, but using
							// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that
							// relaunching
							// the application from the device home screen does
							// not return
							// to the external activity.
							Intent externalActivityIntent = new Intent(
									Intent.ACTION_PICK);
							externalActivityIntent.setType("image/*");
							externalActivityIntent
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(externalActivityIntent);
						}
					});

			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			dialogView = inflater.inflate(R.layout.fragment_section_dummy,
					container, false);

			String cmd = "3";
			
			if (StockDetailActivity.stockCode.length() > 0) {
				RetreiveHistoricalDataService retrieveHisData = new RetreiveHistoricalDataServiceImpl();
				List<StockHistory> stockHisList = retrieveHisData
						.retreiveStockHistoryDetails(
								StockDetailActivity.stockCode, cmd);

				initOHLC(stockHisList);
				initLineChart(stockHisList, cmd);
				initVOL(stockHisList);
				initStickChart(stockHisList);
			}

			// ExpandableListMainActivity expandableList = new
			// ExpandableListMainActivity();

			// Set the Items of Parent
			// expandableList.Create();

			/*
			 * Bundle args = getArguments(); ((TextView)
			 * rootView.findViewById(android.R.id.text1)).setText(
			 * getString(R.string.dummy_section_text,
			 * args.getInt(ARG_SECTION_NUMBER)));
			 */

			return dialogView;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}