package com.example.arulr1.movementanimation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager mSensorManager;
    Sensor accelerometer;
    ImageView car;
    public static int x = 0, y = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        car = (ImageView) findViewById(R.id.car);

        mSensorManager.registerListener(accelerolistener, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(accelerolistener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    };

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(accelerolistener);
    };

    SensorEventListener accelerolistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                x -= (int) event.values[0];
                y += (int) event.values[1];

                car.setY(y);
                car.setX(x);
            }
        }



        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


}
