package ru.mirea.makarov.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int countStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(savedInstanceState == null){
            countStudents = 0;
        }else{
            countStudents = savedInstanceState.getDouble(COUNT_STUDENTS);
        }*/
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (mCamera == null) {
            initializeCamera(); // Local method to handle camera init
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (mCamera != null) {
            mCamera.release()
            mCamera = null;
        }*/
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);/*
        outState.putInt(COUNT_STUDENTS, 2);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}