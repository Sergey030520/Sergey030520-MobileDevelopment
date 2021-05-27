package ru.mirea.makarov.mireaproject1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorStatusFragment extends Fragment implements SensorEventListener {

    private TextView topSensor;
    private TextView centerSensor;
    private TextView bottomSensor;

    private SensorManager sensorManager;
    private Sensor sensor;
    private Context context;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SensorStatusFragment() {
        // Required empty public constructor
    }
    public void CreateSensorStatus(TextView[] array_textView, String copy_sensor, Context copy_context){
        topSensor = array_textView[0];
        centerSensor = array_textView[1];
        bottomSensor = array_textView[2];
        sensorManager = (SensorManager) copy_context.getSystemService(Context.SENSOR_SERVICE);
        switch (copy_sensor){
            case "Accelerometr":
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                break;
            case "AmbientTemperature":
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                break;
            case "Hydroscop":
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
                break;
            case "Proximity":
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                break;
            default:
                break;
        }
        context = copy_context;
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            topSensor.setText("Azimuth: " + valueAzimuth);
            centerSensor.setText("Pitch: " + valuePitch);
            bottomSensor.setText("Roll: " + valueRoll);
            topSensor.setVisibility(View.VISIBLE);
            bottomSensor.setVisibility(View.VISIBLE);
        }
        else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            centerSensor.setText("Temperature: " + event.values[0]);
            topSensor.setVisibility(View.INVISIBLE);
            bottomSensor.setVisibility(View.INVISIBLE);
        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            topSensor.setText("X: " + x);
            centerSensor.setText("Y: " + y);
            bottomSensor.setText("Z: " + z);
            topSensor.setVisibility(View.VISIBLE);
            bottomSensor.setVisibility(View.VISIBLE);
        }
        else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            topSensor.setVisibility(View.INVISIBLE);
            bottomSensor.setVisibility(View.INVISIBLE);
            centerSensor.setText("Type proximity: " + event.values[0]);
        }
    }
    public void DestroySensor(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorStatusFragment.
     */
    public static SensorStatusFragment newInstance(String param1, String param2) {
        SensorStatusFragment fragment = new SensorStatusFragment();
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
        return inflater.inflate(R.layout.fragment_sensor_status, container, false);
    }
}