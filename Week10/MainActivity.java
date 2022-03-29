package com.example.olioweek10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    WebView web;
    String urlName = "https://www.google.com";
    ArrayList<String> historia;
    ListIterator<String> litr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        historia = new ArrayList<String>();
        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(urlName);
        historia.add(urlName);
        litr = historia.listIterator();
    }

    public void executeJavascript(View w) {
        web.evaluateJavascript("javascript:shoutOut()",null);
    }

    public void palautaJS(View w) {
        web.evaluateJavascript("javascript:initialize()",null);
    }

    public ListIterator<String> verkkoSivuTaakse(View w) {
        if (!litr.hasPrevious()) {
            Toast.makeText(getApplicationContext(),"Taakse Eiole", Toast.LENGTH_SHORT).show();
            return(litr);
        }
        Toast.makeText(getApplicationContext(),"Taakse", Toast.LENGTH_SHORT).show();
        String sprev = (String) litr.previous();
        web.loadUrl(sprev);
        return(litr);
    }

    public ListIterator<String> verkkoSivuEteen(View w) {
        if (!litr.hasNext()) {
            Toast.makeText(getApplicationContext(),"Eteen Eiole", Toast.LENGTH_SHORT).show();
            return(litr);
        }
        Toast.makeText(getApplicationContext(),"Eteen", Toast.LENGTH_SHORT).show();
        String snext = (String) litr.next();
        web.loadUrl(snext);
        return(litr);
    }

    public ListIterator<String> searchBar(View w) {
        Toast.makeText(getApplicationContext(),"Search", Toast.LENGTH_SHORT).show();
        TextView t = findViewById(R.id.editText);
        String input = t.getText().toString();
        tulostus.tulostaString(litr.nextIndex());
        tulostus.tulostaString(historia.size());
        /*for (int i = litr.nextIndex(); i < historia.size()-1; i++) {
            historia.remove(i);
        }*/
        int next = litr.nextIndex();
        //if (litr.hasNext()) { litr.next();}
        if (litr.hasNext()) { litr.next();}
        while (litr.hasNext()) {
            litr.next();
            litr.remove();
        }

        if (historia.size() > 9) {
            while(litr.hasPrevious()) {
                litr.previous();
            }
            litr.remove();
        }
        if (input.equals("index.html")) {
            web.loadUrl("file:///android_asset/index.html");
            historia.add("file:///android_asset/index.html");
        } else {
            urlName = "https://"+input;
            web.loadUrl(urlName);
            historia.add(urlName);
        }
        tulostus.tulosta(historia);
        litr = historia.listIterator();
        while(litr.hasNext()) { litr.next();}
        litr.previous();
        return(litr);
    }

    public void refresh(View w) {
        web.loadUrl(urlName);
    }
}