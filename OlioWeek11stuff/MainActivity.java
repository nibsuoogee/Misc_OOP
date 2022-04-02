package com.example.olioweek11;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Fragment2 frag = new Fragment2();
    TextView textViewKirj;
    TextView textViewDisplay;
    ArrayList<Integer> settingsArray;
    EditText editText;
    String text;
    String displaytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textViewKirj = (TextView) findViewById(R.id.textViewKirj);
        textViewDisplay = (TextView) findViewById(R.id.textViewDisplay);
        settingsArray = new ArrayList<>();
        settingsArray.add(0);
        settingsArray.add((int) (textViewKirj.getTextSize()*0.2));
        ViewGroup.LayoutParams layoutParams = textViewKirj.getLayoutParams();
        settingsArray.add((int) (layoutParams.width));
        settingsArray.add((int) (layoutParams.height));
        settingsArray.add((int) (textViewKirj.getMaxLines()));
        settingsArray.add(0);
        editText = (EditText) findViewById(R.id.editText);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                sendValueToFragment(view);
            }
        };

        Button btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(listener);
    }

    public void sendValueToFragment (View view) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("key", settingsArray);
        text = editText.getText().toString();
        bundle.putString("textKey", text);
        displaytext = textViewDisplay.getText().toString();
        bundle.putString("displayKey", displaytext);
        frag.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentWindow,frag);
        transaction.commit();
    }

    public void onStart () {
        super.onStart();
        if (getIntent().getExtras() != null) {
            settingsArray = (ArrayList) getIntent().getExtras().get("key");
            if (settingsArray.get(5).equals(1)) {
                setLocale("fi");
            } else if (settingsArray.get(5).equals(0)) {
                setLocale("en");
            }
            textViewKirj.setTextSize((float) settingsArray.get(1));
            textViewKirj.setMaxLines(settingsArray.get(4));
            if (settingsArray.get(0).equals(1)) {
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
                editText.setClickable(false);
            } else {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setClickable(true);
            }
            if (getIntent().getExtras().get("textKey") != null) {
                editText.setText((String) getIntent().getExtras().get("textKey"));
                if (settingsArray.get(0).equals(1)) {
                    textViewKirj.setText((String) getIntent().getExtras().get("textKey"));
                }
            }
            if (getIntent().getExtras().get("displayKey") != null) {
                textViewDisplay.setText((String) getIntent().getExtras().get("displayKey"));
            }
        }
        ViewGroup.LayoutParams layoutParams = textViewKirj.getLayoutParams();
        layoutParams.width = settingsArray.get(2);
        layoutParams.height = settingsArray.get(3);
        textViewKirj.setLayoutParams(layoutParams);
    }

    private void setLocale(String language) {
        Resources resources =getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*public void loadActivity (View w) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }*/
}