package com.example.example2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    Button start;

    RatingBar c1,c2,c3,c4;

    float[][] sensorData;
    float[][] acc_x;
    int[] acc_y;

    int[][] wifi_x;
    int[] wifi_y;

    int windowSize = 20;
    int sampleNum = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the text views.
        activity = (TextView) findViewById(R.id.textActivity);

        // Create the button
        start = (Button) findViewById(R.id.start);

        c1 = (RatingBar) findViewById(R.id.ratingBar1);
        c2 = (RatingBar) findViewById(R.id.ratingBar2);
        c3 = (RatingBar) findViewById(R.id.ratingBar3);
        c4 = (RatingBar) findViewById(R.id.ratingBar4);

        // Set the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = new float[20][3];

        acc_x = new float[90][3];
        acc_y = new int[90];

        wifi_x = new int[320][2];
        wifi_y = new int[320];

        readFromAccFile(acc_x, acc_y);
        KNNClassifierAcc knnClassifierAcc = new KNNClassifierAcc(acc_x, acc_y);

        readFromWifiFile(wifi_x, wifi_y);
        KNNClassifierWifi knnClassifierWifi = new KNNClassifierWifi(wifi_x, wifi_y);

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
            new Thread(() -> {
                sampleNum = 0;
                while (sampleNum < windowSize - 1) {
                    activity.setText("Collecting data!");
                }
                float[] accFeature = new float[3];
                extractAccFeature(sensorData, accFeature);
                int predict = knnClassifierAcc.predict(accFeature, 5);
                switch(predict) {
                    case 0:
                        activity.setText("Stand Still");
                        break;
                    case 1:
                        activity.setText("Walking");
                        break;
                    case 2:
                        activity.setText("Jumping Jacks");
                        break;
                    default:
                        activity.setText("No activity");
                }
            }).start();

            new Thread(() -> {
                c1.setRating(0.0f);
                c2.setRating(0.0f);
                c3.setRating(0.0f);
                c4.setRating(0.0f);

                wifiInfo = wifiManager.getConnectionInfo();
                int[] wifiData = new int[2];
                int bssid = 0;

                String[] parts = wifiInfo.getBSSID().split(":");
                for (String part : parts) {
                    bssid += Integer.parseInt(part, 16);
                }

                wifiData[0] = bssid;
                wifiData[1] = wifiInfo.getRssi();

//                 c1
//                wifiData[0] = 882;
//                wifiData[1] = -50;
//
//                 c2
//                wifiData[0] = 882;
//                wifiData[1] = -75;
//
//                 c3
//                wifiData[0] = 968;
//                wifiData[1] = -47;
//
//                 c4
//                wifiData[0] = 1026;
//                wifiData[1] = -65;

                int predict = knnClassifierWifi.predict(wifiData, 5);
                switch(predict) {
                    case 1:
                        c1.setRating(10.0f);
                        break;
                    case 2:
                        c2.setRating(10.0f);
                        break;
                    case 3:
                        c3.setRating(10.0f);
                        break;
                    case 4:
                        c4.setRating(10.0f);
                        break;
                    default:
                }
            }).start();
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
        // get the the x,y,z values of the accelerometer
        float aX = event.values[0];
        float aY = event.values[1];
        float aZ = event.values[2];

        if (sampleNum < windowSize) {
            sensorData[sampleNum][0] = aX;
            sensorData[sampleNum][1] = aY;
            sensorData[sampleNum][2] = aZ;

            sampleNum = sampleNum + 1;
        }

    }

    private void readFromAccFile(float[][] acc_x, int[] acc_y) {
        String accXFile = "acc_x.csv";
        String accYFile = "acc_y.csv";

        try {
            readCsvFileX(accXFile, acc_x);
            readCsvFileY(accYFile,acc_y);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromWifiFile(int[][] wifi_x, int[] wifi_y) {
        String wifiXFile = "wifi_x.csv";
        String wifiYFile = "wifi_y.csv";

        try {
            readCsvFileX(wifiXFile, wifi_x);
            readCsvFileY(wifiYFile,wifi_y);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCsvFileX(String file, float[][] acc_x) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int row = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < values.length; col++) {
                acc_x[row][col] = Float.parseFloat(values[col]);
            }
            row++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    public void readCsvFileX(String file, int[][] wifi_x) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int row = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < values.length; col++) {
                wifi_x[row][col] = Integer.parseInt(values[col]);
            }
            row++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    public void readCsvFileY(String file, int[] y) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int row = 0;
        while ((line = bufferedReader.readLine()) != null) {
            line = line.replace(",", "");
            y[row] = Integer.parseInt(line);

            row++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    public void extractAccFeature(float[][] sensorData, float[] accFeature) {
        for (int i=0; i<3; i++) {
            accFeature[i] = findMax(sensorData, i) - findMin(sensorData, i);
        }
    }

    public float findMax(float[][] sensorData, int col) {
        float max = sensorData[0][col];

        for (int i=0; i< windowSize; i++) {
            if (sensorData[i][col] > max) {
                max = sensorData[i][col];
            }
        }

        return max;
    }

    public float findMin(float[][] sensorData, int col) {
        float min = sensorData[0][col];

        for (int i=0; i< windowSize; i++) {
            if (sensorData[i][col] < min) {
                min = sensorData[i][col];
            }
        }

        return min;
    }
}