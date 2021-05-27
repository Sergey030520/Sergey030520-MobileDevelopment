package ru.mirea.makarov.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MyLooper myLooper;
    int age = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLooper = new MyLooper();
        myLooper.start();
    }
    public void onClick(View view) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        age = (int) (((System.currentTimeMillis() / 1000) / 60) % 60);
        bundle.putString("KEY", String.valueOf(age).concat(" Программист!"));
        msg.setData(bundle);
        if (myLooper != null) {
            myLooper.handler.sendMessage(msg);
        }
    }
}