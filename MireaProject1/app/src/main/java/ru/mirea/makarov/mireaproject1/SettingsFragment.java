package ru.mirea.makarov.mireaproject1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SharedPreferences preferences;
    private final String SAVE_NAME_FILE = "saved_name_file";
    private String  settingsTxt;
    private Context context;
    private Activity activity;
    SharedPreferences.Editor editor;
    private TextView[] array_textView;
    private final String NO_FIND = "no_find";
    private final int ERROR = -1;


    public SettingsFragment() {
        // Required empty public constructor
    }
    public SettingsFragment(TextView[] array_textV, Context context, Activity activity){
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.activity = activity;
        this.context = context;
        this.array_textView = array_textV;
        onLoadText();
        onSaveText();
    }
    public void onSaveText(){
        String newSettingText = "";
        if (!array_textView[0].getText().toString().isEmpty()){
            if(ERROR != getColor(array_textView[1].getText().toString())) {
                newSettingText += "backgroundColor " + array_textView[0].getText().toString();
                array_textView[2].setBackgroundColor(getColor(array_textView[0].getText().toString()));
            }else{
                newSettingText += "backgroundColor " + getParamSettings(settingsTxt,"backgroundColor");
                array_textView[2].setBackgroundColor(getColor(getParamSettings(settingsTxt,"backgroundColor")));
                array_textView[1].setText(getParamSettings(settingsTxt,"backgroundColor"));
            }

        }else{
            settingsTxt += "backgroundColor ".concat(getParamSettings(settingsTxt,"backgroundColor"));
            array_textView[2].setBackgroundColor(getColor(getParamSettings(settingsTxt,"backgroundColor")));
        }
        if (!array_textView[1].getText().toString().isEmpty()){
            if (ERROR != getColor(array_textView[1].getText().toString())){
                newSettingText += " colorText " + array_textView[1].getText().toString();
                array_textView[2].setTextColor(getColor(array_textView[1].getText().toString()));
            }else{
                newSettingText += " colorText ".concat(getParamSettings(settingsTxt,"colorText"));;
                array_textView[2].setTextColor(getColor(getParamSettings(settingsTxt,"colorText")));
                array_textView[1].setText(getParamSettings(settingsTxt,"colorText"));
            }
        }else{
            settingsTxt += "colorText ".concat(getParamSettings(settingsTxt,"colorText"));
            array_textView[2].setTextColor(getColor(getParamSettings(settingsTxt,"colorText")));
            array_textView[2].setTextColor(Color.WHITE);
        }
        settingsTxt = "";
        settingsTxt = newSettingText;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVE_NAME_FILE, settingsTxt);
        editor.apply();
        Toast.makeText(context, "Setting saved", Toast.LENGTH_SHORT).show();

    }
    public void onLoadText(){
        String text = preferences.getString(SAVE_NAME_FILE, "backgroundColor red colorText green");
        String statusBackgroundColor = getParamSettings(text, "backgroundColor"),
                statusColorText = getParamSettings(text, "colorText");
        settingsTxt = "";
        if (!statusBackgroundColor.isEmpty() && !statusBackgroundColor.equals(NO_FIND)){
            array_textView[0].setText(statusBackgroundColor);
            array_textView[2].setBackgroundColor(getColor(statusBackgroundColor));
        }else{
            array_textView[0].setText(text);
            settingsTxt += "backgroundColor White";
            array_textView[2].setTextColor(getColor("white"));
        }
        if (!statusColorText.isEmpty() && !statusBackgroundColor.equals(NO_FIND)){
            array_textView[1].setText(statusColorText);
            settingsTxt += " colorText " + statusColorText;
            array_textView[2].setTextColor(getColor(statusColorText));
        }else{
            array_textView[1].setText("white");
            settingsTxt += " colorText Black";
            array_textView[2].setTextColor(getColor("black"));
        }
    }
    public String getParamSettings(String text, String param){
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i+=2){
            if (words[i].toLowerCase().equals(param.toLowerCase())){
                return words[i+1];
            }
        }
        return NO_FIND;
    }

    public int getColor(String text){
        switch (text.toLowerCase()){
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "black":
                return Color.BLACK;
            case "green":
                return Color.GREEN;
        }
        return ERROR;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}