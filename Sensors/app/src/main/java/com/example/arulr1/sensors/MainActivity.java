package com.example.arulr1.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager mSensorManager;
    List<Sensor> mSensor;
    Sensor LightSensor, ProximitySensor, accelerometerSensor;
    TextView lightsensorView,proximitysensorView,accesensorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        lightsensorView = (TextView)findViewById(R.id.lightsensor);
        proximitysensorView = (TextView)findViewById(R.id.proximitysensor);
        accesensorView = (TextView)findViewById(R.id.accesensor);


        String[] sensorarray = new String[mSensor.size()];

        for (int i=0;i<mSensor.size();i++){
            sensorarray[i] = mSensor.get(i).getName();
        }
        ArrayAdapter<String> InfoGroupAdapter=new ArrayAdapter<String>(this,R.layout.listview,sensorarray);
        ListView infoList=(ListView) findViewById(R.id.InfoList);
        infoList.setAdapter(InfoGroupAdapter);

        LightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        ProximitySensor= mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accelerometerSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener lightlistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_LIGHT) {
                    lightsensorView.setText("illumination level : " + String.valueOf(event.values[0]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        SensorEventListener proximitylistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if(event.values[0]<5.0)
                        proximitysensorView.setText("Proximity : Near ");
                    if(event.values[0]>5.0)
                        proximitysensorView.setText("Proximity : Far ");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorEventListener  accelerometerlistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    accesensorView.setText("X : " + String.valueOf(event.values[0])+" Y : " + String.valueOf(event.values[1])+" Z : " + String.valueOf(event.values[2]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(lightlistener, LightSensor, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(proximitylistener, ProximitySensor, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(accelerometerlistener,accelerometerSensor , SensorManager.SENSOR_DELAY_UI);

    }
}
