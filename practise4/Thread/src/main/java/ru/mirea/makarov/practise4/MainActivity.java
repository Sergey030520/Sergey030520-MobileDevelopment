package ru.mirea.makarov.practise4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    TextView infoTextView;
    int total_lessons = 24, total_days = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Текущий поток: " + mainThread.getName());
        mainThread.setName("MireaThread");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());*/
    }
    public void onClick(View view) {
        infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        mainThread.run();
        infoTextView.setText( "Среднее количество пар в день:".concat(String.valueOf(getMiddleCountLessons())));
    }
    public void OnClickThread(View view){
        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public float getMiddleCountLessons(){
        return total_lessons/total_days;
    }
}
