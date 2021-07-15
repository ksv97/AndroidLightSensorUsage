package com.example.playmusiconlightchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    boolean isMediaServiceRunning = false;
    SensorManager sensorManager;
    Sensor lightSensor;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        data = (TextView)findViewById(R.id.data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float sensorData = event.values[0];
            data.setText(sensorData + " ");
            if (sensorData > 10) {
                if (isMediaServiceRunning)
                    return;

                isMediaServiceRunning = true;
                Intent i = new Intent(MainActivity.this, MediaService.class);
                startService(i);
            }
            else if (isMediaServiceRunning) {
                isMediaServiceRunning = false;
                Intent i = new Intent(MainActivity.this, MediaService.class);
                stopService(i);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}