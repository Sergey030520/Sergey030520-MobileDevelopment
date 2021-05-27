package ru.mirea.makarov.clickbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv_out;
    private Button btnOk;
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_out = (TextView) findViewById(R.id.tvOut);
        btnOk = (Button)findViewById(R.id.btnOK);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        View.OnClickListener oclBtnOk  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_out.setText("Нажата кнопка ОК");
            }
        };
        btnOk.setOnClickListener(oclBtnOk);
    }
    public void onMyButtonClick(View view){
        Toast.makeText(this, "Еще один способ!", Toast.LENGTH_SHORT).show();
        tv_out.setText("Нажата кнопка Cancel");
    }
}