package com.sprinters.bullzx.activity;


import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class ExpandableListMainActivity extends ExpandableListActivity {

	
	// Create ArrayList to hold parent Items and Child Items
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

	protected void Create() {
		//super.onCreate(savedInstanceState);


        
        // Create Expandable List and set it's properties
        ExpandableListView expandableList = getExpandableListView(); 
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        // Set the Items of Parent
        setGroupParents();
        // Set The Child Data
        setChildData();

        // Create the Adapter
        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        
        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }
	// method to add parent Items
    public void setGroupParents() 
    {
        parentItems.add("Timeframe");
        
    }

    // method to set child data of each parent
    public void setChildData() 
    {

        // Add Child Items for Fruits
        ArrayList<String> child = new ArrayList<String>();
        child.add("Intraday");
        child.add("Five days");
        child.add("One month");
        child.add("Six months");
        child.add("One year");
        
        
        childItems.add(child);

        
    }

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expandable_list_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	*/
	
}
