package ru.mirea.makarov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText fileName;
    private EditText output_text;
    private SharedPreferences preferences;
    private final String SAVE_NAME_FILE = "saved_name_file";
    private String name_file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileName = (EditText) findViewById(R.id.nameFile);
        output_text = (EditText) findViewById(R.id.output_text);
        preferences = getPreferences(MODE_PRIVATE);
        name_file = preferences.getString(SAVE_NAME_FILE, "Empty");
        if (!name_file.isEmpty()){
            fileName.setText(name_file);
            onClickLoadFile(null);
        }
    }
        public void onClickLoadFile(View view){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(SAVE_NAME_FILE, fileName.getText().toString());
            editor.apply();
            Toast.makeText(this, "Text Load", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                public void run() {
                    output_text.post(new Runnable() {
                        public void run() {
                            output_text.setText(getTextFromFile());
                        }
                    });
                }
            }).start();
        }
        public void onClickUpdate(View view){
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName.getText().toString(), Context.MODE_PRIVATE);
                outputStream.write(output_text.getText().toString().getBytes());
                outputStream.close();
                Toast.makeText(this, "Text Update", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public String getTextFromFile() {
            FileInputStream fin = null;
            try {
                fin = openFileInput(fileName.getText().toString());
                byte[] bytes = new byte[fin.available()];
                fin.read(bytes);
                String text = new String(bytes);
                return text;
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (fin != null)
                        fin.close();
                } catch (IOException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }
}