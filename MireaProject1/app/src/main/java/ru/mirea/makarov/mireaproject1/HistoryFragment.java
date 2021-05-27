package ru.mirea.makarov.mireaproject1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences preferences;
    private final String SAVE_NAME_FILE = "history_file";

    private String mParam1;
    private String mParam2;
    private ArrayList<History> histories;
    private Context context;

    public HistoryFragment() {
        // Required empty public constructor
    }
    TextView textView;
    public HistoryFragment(Context context, Activity activity, TextView copytextView){
        textView = copytextView;
        histories = new ArrayList<>();
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        this.context = context;
        setInitialData();
    }
    private void setInitialData(){
        String text = preferences.getString(SAVE_NAME_FILE, "");
        String[] new_text = text.split(" ");
        ArrayList<String> new_new_text = new ArrayList<String>();
        for (int i = 0; i < new_text.length; ++i){
            if (new_text[i] != " ") new_new_text.add(new_text[i]);
        }
        for (int i = 0; (i < new_new_text.size() && new_new_text.size()%2 ==0 )|| (new_new_text.size()%2 != 0 && new_new_text.size()-2 >= i); i+=2){
            String name = new_new_text.get(i);
            String content = new_new_text.get(i+1);
            histories.add(new History(name, content));
        }
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public void addHistory(String name, String content){
        name = (name.equals(" ") ? "Empty" : name);
        content = (content.isEmpty() ? "Empty" : content);
        histories.add(new History(name, content));
        SharedPreferences.Editor editor = preferences.edit();
        String data = "";
        for (int i = 0; i < histories.size(); ++i){
            data += histories.get(i).getName_history() + " "+ histories.get(i).getContent_history();
            if (i+1 != histories.size()/2){
                data += " ";
            }
        }
        editor.putString(SAVE_NAME_FILE, data);
        editor.apply();
        Toast.makeText(context, "Setting saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}