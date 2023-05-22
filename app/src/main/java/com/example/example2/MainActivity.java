package com.example.example2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

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
    private TextView currentX, currentY, currentZ, titleAcc, textRssi;

    Button startRssi, startAcc, saveToFile;
    TextInputEditText fileName;

    ArrayList<float[]> sensorData;
    ArrayList<String[]> wifiData;
    int wifiDataLength = 0;
    int wifiDataTimes = 0;

    private boolean accIsToggleOn = false;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the text views.
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);
        titleAcc = (TextView) findViewById(R.id.titleAcc);
        textRssi = (TextView) findViewById(R.id.textRSSI);

        // Create the button
        startRssi = (Button) findViewById(R.id.startRSSI);
        startAcc = (Button) findViewById(R.id.startAcc);
        saveToFile = (Button) findViewById(R.id.saveToFile);

        fileName = (TextInputEditText) findViewById(R.id.fileName);

        // Set the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = new ArrayList<>();
        wifiData = new ArrayList<>();

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

        // Create a click listener for our button.
//        startRssi.setOnClickListener(v -> {
//            // get the wifi info.
//            wifiInfo = wifiManager.getConnectionInfo();
//            // update the text.
//            textRssi.setText("\n\tSSID = " + wifiInfo.getSSID()
//                    + "\n\tRSSI = " + wifiInfo.getBSSID()
//                    + "\n\tRSSI = " + wifiInfo.getRssi()
//                    + "\n\tLocal Time = " + System.currentTimeMillis());
//        });

        startRssi.setOnClickListener(view -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int interval = 5000;
                    int times = 10;

                    for (int i = 0; i < times; i++) {
                        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.startScan();
                        List<ScanResult> scanResults = wifiManager.getScanResults();
                        if (wifiDataChange(wifiData, scanResults, wifiDataLength)) {
                            wifiDataLength = scanResults.size();
                            wifiDataTimes += 1;

                            for (ScanResult scanResult : scanResults) {
//                              textRssi.setText(textRssi.getText() + "\n\tBSSID = "
//                                + scanResult.BSSID + "    RSSI = "
//                                + scanResult.level + "dBm");
                                String[] wifi = new String[2];
                                wifi[0] = scanResult.BSSID;
                                wifi[1] = String.valueOf(scanResult.level);
                                wifiData.add(wifi);
                            }

                            textRssi.setText("Done!\nTimes = " + wifiDataTimes + "\nTotal wifi data collected = " + wifiData.size());
                        }
                        else {
                            textRssi.setText("Wifi data is the same! Did not record!\nTimes = " + wifiDataTimes + "\nTotal wifi data collected = " + wifiData.size());
                        }

                        try {
                            Thread.sleep(interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        });

        startAcc.setOnClickListener(view -> {
            if (accIsToggleOn) {
                accIsToggleOn = false;
                startAcc.setText("START ACC");
            } else {
                accIsToggleOn = true;
                startAcc.setText("STOP ACC");
            }
        });

        saveToFile.setOnClickListener(view -> {
            if (wifiData.size() != 0) {
                wifiSaveToFile(wifiData, String.valueOf(fileName.getText()));
                fileName.setText("");
                wifiData.clear();
            }

            if (sensorData.size() != 0) {
                accSaveToFile(sensorData, String.valueOf(fileName.getText()));
                fileName.setText("");
                sensorData.clear();
            }
        });
    }

    private boolean wifiDataChange(ArrayList<String[]> wifiData, List<ScanResult> scanResults, int wifiDataLength) {
        if (wifiDataLength != scanResults.size()) {
            return true;
        }
        else {
            if (Integer.parseInt(wifiData.get(wifiData.size() - 1)[1]) != scanResults.get(scanResults.size() - 1).level) {
                return true;
            }
        }

        return false;
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
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");

        // get the the x,y,z values of the accelerometer
        float aX = event.values[0];
        float aY = event.values[1];
        float aZ = event.values[2];

        if (accIsToggleOn) {
            sensorData.add(new float[]{aX, aY, aZ});
        }
        // display the current x,y,z accelerometer values
        currentX.setText(Float.toString(aX));
        currentY.setText(Float.toString(aY));
        currentZ.setText(Float.toString(aZ));

        if ((Math.abs(aX) > Math.abs(aY)) && (Math.abs(aX) > Math.abs(aZ))) {
            titleAcc.setTextColor(Color.RED);
        }
        if ((Math.abs(aY) > Math.abs(aX)) && (Math.abs(aY) > Math.abs(aZ))) {
            titleAcc.setTextColor(Color.BLUE);
        }
        if ((Math.abs(aZ) > Math.abs(aY)) && (Math.abs(aZ) > Math.abs(aX))) {
            titleAcc.setTextColor(Color.GREEN);
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

    private void wifiSaveToFile(ArrayList<String[]> wifiData, String fileName) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            for (int i = 0; i < wifiData.size(); i++) {
                String[] arr = new String[3];
                arr[0] = wifiData.get(i)[0];
                arr[1] = wifiData.get(i)[1];
                arr[2] = "\n";
                String line = String.join(",", arr);
                outputStreamWriter.write(line);
            }

            outputStreamWriter.close();

            Toast.makeText(getApplicationContext(), "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            System.out.println("Error written data in CSV file: " + e.getMessage());
        }
    }
}