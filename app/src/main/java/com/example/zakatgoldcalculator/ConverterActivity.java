package com.example.zakatgoldcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConverterActivity extends AppCompatActivity implements View.OnClickListener {
    //unit converter part
    EditText text_inputWeight, text_inputPrice;
    Button btn_Convert;
    RadioButton radio_Keep, radio_Wear;
    TextView text_Weight, text_Type, text_Price;
    TextView text_OutputZakatPayable, text_OutputPrice, text_OutputGoldValueForZakat,text_OutputTotalZakat;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        Log.d("fail","It's not working");

        //get toolbar make class label object
        Toolbar converterToolbar = (Toolbar) findViewById(R.id.converterToolbar);
        setSupportActionBar(converterToolbar);
        getSupportActionBar().setTitle("Gold Zakat Calculator");

        //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //convert unit part
        text_inputWeight = (EditText) findViewById(R.id.text_inputWeight);
        text_inputPrice = (EditText) findViewById(R.id.text_inputPrice);
        btn_Convert = (Button) findViewById (R.id.btn_Convert);
        radio_Keep = (RadioButton) findViewById (R.id.radio_Keep);
        radio_Wear = (RadioButton) findViewById (R.id.radio_Wear);
        text_Weight = (TextView) findViewById (R.id.text_Weight);
        text_Type = (TextView) findViewById (R.id.text_Type);
        text_Price = (TextView) findViewById (R.id.text_Price);
        text_OutputZakatPayable = (TextView) findViewById(R.id.text_OutputZakatPayable);
        text_OutputPrice =  (TextView) findViewById(R.id.text_OutputPrice);
        text_OutputTotalZakat = (TextView) findViewById(R.id.text_OutputTotalZakat);
        text_OutputGoldValueForZakat = (TextView) findViewById(R.id.text_OutputGoldValueForZakat);

        btn_Convert.setOnClickListener(this);

    }
    public void disable ( View click){
        click.setEnabled(false);
        //log.d logcat
        Log.d("success","Button Disabled");
        //changing button text
        Button button = (Button) click;
        button.setText("Disabled");

    }

    @Override
    public void onClick(View v) {
        if (v == btn_Convert) {
            calculateValues(); // Call the calculation method when the button is clicked
        }
    }

    public void calculateValues() {
        try {
            double weight = Double.parseDouble(text_inputWeight.getText().toString());
            double value = Double.parseDouble(text_inputPrice.getText().toString());

            if (weight == 0 || value == 0) {
                showErrorDialog("Please enter non-zero weight and price values.");
                return;
            }

        double totalValue = weight * value;
        double goldValueForZakat;

        if (radio_Keep.isChecked()) {
            goldValueForZakat = Math.max(weight - 85, 0);
        } else if (radio_Wear.isChecked()) {
            goldValueForZakat = Math.max(weight - 200, 0);
        } else {
            // Handle default case or show an error message for no type selected
            // You can set a default value or display an error message in case no radio button is selected
            goldValueForZakat = 0;
        }

        double zakatPayable = goldValueForZakat * value;
        double totalZakat = 0.025 * zakatPayable;

        text_OutputPrice.setText(String.format("Total Value : RM %.2f", totalValue));
        text_OutputGoldValueForZakat.setText(String.format("Zakat Payable Weight : %.2f g", goldValueForZakat ));
        text_OutputZakatPayable.setText(String.format("Zakat Payable : RM %.2f", zakatPayable));
        text_OutputTotalZakat.setText(String.format("Total Zakat : RM %.2f", totalZakat));

        } catch (NumberFormatException e) {
            showErrorDialog("Please enter valid numeric weight and price values.");
        }
    }



    //for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }

        if (item.getItemId() == R.id.item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, " Please use my application - https://github.com/profnina/GoldZakatCalculator");
            startActivity(Intent.createChooser(shareIntent, null));

            return true;
        } else if (item.getItemId() == R.id.item_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }
        return false;
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Add functionality here if needed
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();

        // Apply the style for the "OK" button separately
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                // Get the "OK" button from the AlertDialog
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                if (alertDialog != null) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                }
            }
        });

        dialog.show();
    }




}

