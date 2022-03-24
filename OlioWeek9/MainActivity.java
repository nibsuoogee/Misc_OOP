package com.example.viikko9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Documented;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editTextStart;
    EditText editTextEnd;
    EditText editTextDate;

    Context context = null;
    //Spinner spinner = null;
    ScrollView scrollView;
    TextView textViewElokuvat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        editTextStart = (EditText) findViewById(R.id.editTextStart);
        editTextEnd = (EditText) findViewById(R.id.editTextEnd);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textViewElokuvat = (TextView) findViewById(R.id.textViewElokuvat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Spinner spinner = findViewById(R.id.spinner);
        CharSequence[] fillist = getResources().getStringArray(R.array.spinner_names);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, fillist);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        Teatterit teatterit = Teatterit.getInstance();
        paaluokka pl = paaluokka.getInstance();
        pl.readXML(spinner,fillist);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String start = editTextStart.getText().toString();
        String end = editTextEnd.getText().toString();
        String date = editTextDate.getText().toString();
        paaluokka pl = paaluokka.getInstance();
        String name = adapterView.getItemAtPosition(i).toString();
        pl.readXMLteatteri(name, textViewElokuvat, start, end, date);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
/*
    public void readJSON (View v) {
        String json = getJSON();
        System.out.println("JSON: " + json);

        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    System.out.println("#########" + (i+i) + "########");
                    System.out.println(jobject.getString("ID"));
                    System.out.println(jobject.getString("Name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getJSON() {
        String response = null;
        try {
            URL url = new URL("https://www.finnkino.fi/xml/TheatreAreas/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
*/
}