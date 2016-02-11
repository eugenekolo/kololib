package com.eugenekolo.tipcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderScreen extends AppCompatActivity {
    private ListView menuListView;
    private ArrayAdapter<String> menuListAdapter ;
    Double billAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        menuListView = (ListView) findViewById(R.id.menuListView);

        menuListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE); // Add some check marks

        // Fill up the ListView with some stuff
        final ArrayList<String> menuItems = new ArrayList<String>();
        menuListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, menuItems);
        menuListAdapter.add("Chicken Wings - $5.99");
        menuListAdapter.add("Chicken and Ziti - $14.99");
        menuListAdapter.add("Lasagna - $15.99");
        menuListAdapter.add("Chocolate Cake - $4.99");
        menuListView.setAdapter(menuListAdapter);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                CheckedTextView item = (CheckedTextView) view;
                Object o = menuListView.getItemAtPosition(position);
                String pen = o.toString();
                if (item.isChecked()) {
                    billAmount += Double.parseDouble(pen.substring(pen.lastIndexOf('$') + 1));
                } else {
                    billAmount -= Double.parseDouble(pen.substring(pen.lastIndexOf('$') + 1));
                }
                System.out.println(billAmount.toString());
            }
        });

    }

    public void launchTipCalc(View view) {
        Intent i = new Intent(OrderScreen.this, MainActivity.class);
        i.putExtra("billAmount", billAmount);
        startActivity(i);
    }
}
