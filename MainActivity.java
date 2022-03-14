package com.example.bottledispenserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Context  context = null;
    TextView tulosteet;
    BottleDispenser machine = null;
    private TextView seekRaha;
    private SeekBar seekBar;
    private TextView saldo;
    private double lastHinta;
    private String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        tulosteet = (TextView) findViewById(R.id.tulosteText);
        seekRaha = (TextView) findViewById(R.id.seekRaha);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        saldo = (TextView) findViewById(R.id.saldo);
        Spinner spinner = findViewById(R.id.spinner);
        BottleDispenser machine = BottleDispenser.getInstance();
        //machine.listBottles(tulosteet);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_names));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekRaha.setText(String.format("+ %,.2f €", progress*0.05));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BottleDispenser machine = BottleDispenser.getInstance();
        String text = adapterView.getItemAtPosition(i).toString();
        String[] name = text.split(" [0|1].5l$");
        String[] size = text.split("^[a-zA-Z -]*");
        String[] size2 = size[1].split("l$");
        int ostotapahtuma = machine.getBottle(name[0], Double.parseDouble(size2[0]));
        if (ostotapahtuma == 1) {
            tulosteet.setText("Ostettu! \n" + tulosteet.getText().toString());
        } else if (ostotapahtuma == 2) {
            tulosteet.setText("Lisää rahaa ensin. \n" + tulosteet.getText().toString());
        } else {
            tulosteet.setText("Tuotetta ei saatavilla. \n" + tulosteet.getText().toString());
        }
        saldo.setText(String.format("SALDO: %,.2f €", machine.getMoney()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addMoney (View w) {
        BottleDispenser machine = BottleDispenser.getInstance();
        machine.addMoney(tulosteet, seekBar.getProgress());
        saldo.setText(String.format("SALDO: %,.2f €", machine.getMoney()));
    }

    public void kuitti (View w) {

        BottleDispenser machine = BottleDispenser.getInstance();
        String s = "";
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("kuitti.txt", Context.MODE_PRIVATE));
            ows.write("--- Kuitti ---\n");
            ows.write(machine.lastPurchase());
            ows.close();

        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
        } finally {
            tulosteet.setText("Kuitti tulostettu \n" + tulosteet.getText().toString());
        }

    }

    public void returnMoney(View w) {
        BottleDispenser machine = BottleDispenser.getInstance();
        machine.returnMoney(tulosteet);
        saldo.setText(String.format("SALDO: %,.2f €", 0.00));
    }


}