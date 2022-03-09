package com.example.olioapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.view.KeyEvent;
import android.text.TextWatcher;
import android.view.View.OnKeyListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Context  context = null;

    TextView text;
    TextView kenttaText;
    EditText syotto;
    EditText teksti;

    //String tiedosto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        kenttaText = (TextView) findViewById(R.id.kirjoitaKentta);
        syotto = (EditText) findViewById(R.id.syotto);
        teksti = (EditText) findViewById(R.id.teksti);
        context = MainActivity.this;
        //System.out.println("KANSION SIJAINTI " + context.getFilesDir());
        EditText edittext = (EditText) findViewById(R.id.syotto);
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    TextView t = findViewById(R.id.syotto);
                    String input = t.getText().toString();
                    Log.d("info", input);
                    text.setText(input);
                    //tiedosto = input;
                    return true;
                }
                return false;
            }
        });

    }

    public void testFunction (View w) {
        System.out.println("Hello World!");
        text.setText("Hello World!");
    }

    public void handleText (View v) {
        TextView t = findViewById(R.id.syotto);
        String input = t.getText().toString();
        Log.d("info", input);
        text.setText(input);
    }

    public void readFile(View v) {
        String s = syotto.getText().toString();
        System.out.println("s on: " + s);
        try {
            InputStream ins = context.openFileInput(s); //TODO Tälle arvo!

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String n = "";

            while ((n = br.readLine()) != null) {
                System.out.println(n);
                teksti.setText(n);
            }
            ins.close();
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
        } finally {
            System.out.println("LUETTU");
        }
    }

    public void writeFile(View v) {
        String s = syotto.getText().toString();
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(s, Context.MODE_PRIVATE));

            String n = teksti.getText().toString();
            //s = "Tämä tulee tiedostoon \n Lue tiedosto jotta näet tämän \n tai sitten et näe mitään";
            ows.write(n);

            ows.close();

        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
        } finally {
            System.out.println("KIRJOITETTU");
        }
    }
}