package ru.mirea.makarov.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        /*String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        */
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    public void onClickShowDialog(View view){
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }
    public void onClickShowDatePickerDialog(View view){
        DialogFragment datePicker = new MyDateDialogFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }
    public void onClickShowTimePickerDialog(View view){
        DialogFragment dialogFragment = new MyTimeDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "time picker");
    }
    public void onClickShowProgressDialog(View view){
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment(this);
    }

    public void onOkClicked(){
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку\"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked(){
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку\"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked(){
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку\"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }
}