package com.example.olioweek11;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class Fragment2 extends Fragment {

    View view;
    Context context = null;
    private EditText editTextDisplay;
    private TextView textViewFont;
    private TextView textViewWidth;
    private TextView textViewHeight;
    private TextView textViewLines;
    private SeekBar seekBarfont;
    private SeekBar seekBarwidth;
    private SeekBar seekBarheight;
    private SeekBar seekBarlines;
    private Spinner spinner;
    ArrayList<Integer> settingsArray;
    CheckBox checkBox;
    String text;
    String displaytext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        editTextDisplay = (EditText) view.findViewById(R.id.editTextDisplay);
        textViewFont = (TextView) view.findViewById(R.id.textViewFont);
        textViewWidth = (TextView) view.findViewById(R.id.textViewWidth);
        textViewHeight = (TextView) view.findViewById(R.id.textViewHeight);
        textViewLines = (TextView) view.findViewById(R.id.textViewLines);
        seekBarfont = (SeekBar) view.findViewById(R.id.seekBarFont);
        seekBarwidth = (SeekBar) view.findViewById(R.id.seekBarWidth);
        seekBarheight = (SeekBar) view.findViewById(R.id.seekBarHeight);
        seekBarlines = (SeekBar) view.findViewById(R.id.seekBarLines);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        Button btn = getView().findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToActivity();
            }
        });

        CheckBox cb = getView().findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingsArray.set(0,1);
                } else {
                    settingsArray.set(0,0);
                }
            }
        });

        if (getArguments() != null) {
            settingsArray = getArguments().getIntegerArrayList("key");
            seekBarfont.setProgress(settingsArray.get(1));
            text = getArguments().getString("textKey");
            editTextDisplay.setText(getArguments().getString("displayKey"));
        }

        if (settingsArray.get(5).equals(1)) {
            setLocale("fi");
        } else if (settingsArray.get(5).equals(0)) {
            setLocale("en");
        }

        if (settingsArray.get(0).equals(1)) {
            cb.setChecked(true);
        }
        seekBarfont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewFont.setText(String.format("%s: %d",getString(R.string.font_size) ,progress));
                settingsArray.set(1,progress*5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarwidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewWidth.setText(String.format("%s: %d", getString(R.string.width), progress));
                settingsArray.set(2,progress*120);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewHeight.setText(String.format("%s: %d", getString(R.string.height), progress));
                settingsArray.set(3,progress*80);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarlines.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewLines.setText(String.format("%s: %d", getString(R.string.lines), progress));
                settingsArray.set(4,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner spinner = view.findViewById(R.id.spinner);
        CharSequence[] fillist = getResources().getStringArray(R.array.spinner_names);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_dropdown_item, fillist);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("suomi")) {
                    System.out.println("%%%%%%%%%%%%%%%%");
                    setLocale("fi");
                    settingsArray.set(5,1);
                } else if (adapterView.getItemAtPosition(i).toString().equals("englanti")) {
                    System.out.println("!!!!!!!!!!!!!!!!!!");
                    setLocale("en");
                    settingsArray.set(5,0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    public void sendToActivity() {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        intent.putExtra("textKey", text);
        intent.putExtra("key", settingsArray);
        displaytext = editTextDisplay.getText().toString();
        intent.putExtra("displayKey", displaytext);
        startActivity(intent);
    }

}
