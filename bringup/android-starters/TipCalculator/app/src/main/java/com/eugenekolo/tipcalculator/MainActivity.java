package com.eugenekolo.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double percent = 0.15;
    private double taxPercent = .05;
    private Integer numPeople = 1;
    boolean isAfterTax = false;

    private TextView amountTextView;
    private TextView percentTextView;
    private TextView numPeopleTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inflate the GUI

        Bundle bundle = getIntent().getExtras();
        billAmount = bundle.getDouble("billAmount");

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        numPeopleTextView = (TextView) findViewById(R.id.numPeopleTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));
        amountTextView.setText(currencyFormat.format(billAmount));

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set afterTaxSwitch's OnCheckedChangeListener
        Switch afterTaxSwitch = (Switch) findViewById(R.id.afterTaxSwitch);
        afterTaxSwitch.setOnCheckedChangeListener(afterTaxSwitchListener);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(percentSeekBarListener);

        // set numPeopleSeekBar's OnSeekBarChangeListener
        SeekBar numPeopleSeekBar = (SeekBar) findViewById(R.id.numPeopleSeekBar);
        numPeopleSeekBar.setOnSeekBarChangeListener(numPeopleSeekBarListener);

        calculate();
    }

    // calculate and display tip and total amounts
    private void calculate() {
        percentTextView.setText(percentFormat.format(percent));
        numPeopleTextView.setText(numPeople.toString());

        // Calculate the preTaxAmount, account for the fact that user can input an after tax amnt
        Double preTaxAmount = billAmount;
        if (isAfterTax) {
            preTaxAmount = billAmount / (1.00 + taxPercent);
        }

        // Calculate the tip
        double tip = preTaxAmount * percent;
        tip = tip / numPeople; // Calculates per person, and accounts for 5% tax, if relevant

        double total = billAmount + tip;

        tipTextView.setText(currencyFormat.format(tip) + " each");
        totalTextView.setText(currencyFormat.format(total));
    }


    private final OnCheckedChangeListener afterTaxSwitchListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                isAfterTax = true;
                calculate();
            } else {
                isAfterTax = false;
                calculate();
            }
        }
    };

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener percentSeekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            calculate(); // calculate and display tip and total
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { /*empty*/ }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { /*empty*/ }
    };

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener numPeopleSeekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            numPeople = progress;
            calculate(); // calculate and display tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { /*empty*/ }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { /*empty*/ }
    };


    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try { // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate(); // update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s) { /*empty*/ }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*empty*/ }
    };
}