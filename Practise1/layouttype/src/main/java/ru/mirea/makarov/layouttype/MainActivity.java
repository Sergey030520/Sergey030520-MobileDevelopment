package ru.mirea.makarov.layouttype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_6);
        TextView myTextView = (TextView) findViewById(R.id.textView2);
        myTextView.setText("New Text in MIREA");
        Button button = (Button) findViewById(R.id.btnTest);
        button.setText("MIREA Button");
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(true);
    }
}