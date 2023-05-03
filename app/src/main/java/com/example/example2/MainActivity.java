package com.example.example2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Smart Phone Sensing Example 2. Working with sensors.
 */
public class MainActivity extends Activity implements SensorEventListener {

    /**
     * The sensor manager object.
     */
    private SensorManager sensorManager;
    /**
     * The accelerometer.
     */
    private Sensor accelerometer;
    /**
     * The wifi manager.
     */
    private WifiManager wifiManager;
    /**
     * The wifi info.
     */
    private WifiInfo wifiInfo;

    /**
     * Text fields to show the sensor values.
     */
    private TextView activity;
    private LinearLayout C1,C2,C3,C4;

    Button start;

    float[] sensorData;
    Integer wifiData;

    private boolean actIsToggleOn = false;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the text views.
        activity = (TextView) findViewById(R.id.textActivity);

        // Create the button
        start = (Button) findViewById(R.id.start);

        // Set the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = new float[3];

        // if the default accelerometer exists
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // set accelerometer
            accelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // register 'this' as a listener that updates values. Each time a sensor value changes,
            // the method 'onSensorChanged()' is called.
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }  // No accelerometer!

        // Set the wifi manager
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        start.setOnClickListener(view -> {
            if (actIsToggleOn) {
                actIsToggleOn = false;
                start.setText("START");
            } else {
                actIsToggleOn = true;
                start.setText("STOP");
            }
        });
    }

    // onResume() registers the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // onPause() unregisters the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 1 for stand, 2 for walk, 3 for jump
        int act = 0;

        // get the the x,y,z values of the accelerometer
        float aX = event.values[0];
        float aY = event.values[1];
        float aZ = event.values[2];

        // do classification


        // show the activity
        switch(act) {
            case 1:
                activity.setText("Stand Still");
                break;
            case 2:
                activity.setText("Walking");
                break;
            case 3:
                activity.setText("Jumping Jacks");
                break;
            default:
                activity.setText("No activity");
        }

    }

    private void accSaveToFile(ArrayList<float[]> sensorData, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            for (int i = 0; i < sensorData.size(); i++) {
                String[] arr = {Float.toString(sensorData.get(i)[0]),
                        Float.toString(sensorData.get(i)[1]),
                        Float.toString(sensorData.get(i)[2]),
                        "\n"};

                String line = String.join(",", arr);
                outputStreamWriter.write(line);
            }
            outputStreamWriter.close();
            Toast.makeText(getApplicationContext(), "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            System.out.println("Error written data in CSV file: " + e.getMessage());
        }
    }

    private void wifiSaveToFile(ArrayList<Integer> wifiData, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            String joinedString = String.join(",", wifiData.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "")
                    .split(","));

            outputStreamWriter.write(joinedString);
            outputStreamWriter.close();

            Toast.makeText(getApplicationContext(), "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            System.out.println("Error written data in CSV file: " + e.getMessage());
        }
    }
}