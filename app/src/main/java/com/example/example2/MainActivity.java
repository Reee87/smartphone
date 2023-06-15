package com.example.example2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.net.wifi.ScanResult;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Smart Phone Sensing Example 2. Working with sensors.
 */
public class MainActivity extends Activity implements SensorEventListener {
    public static final int cellNum = 16;

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

    private TextView debugInfo;

    RatingBar c1,c2,c3,c4;
    RatingBar c5,c6,c7,c8;
    RatingBar c9,c10,c11,c12;
    RatingBar c13,c14,c15,c16;

    ArrayList<String> Bssid = new ArrayList<>();
    ArrayList<String> bssidList = new ArrayList<>();
    List<ScanResult> scanResults;
    ArrayList<String[]> wifiData;

    Map<String, int[]> indexList;
    Map<String, float[][]> valueList;
    int wifiDataLength;

    int threads = 0;

    int windowSize = 20;
    int sampleNum = 0;

    int predict = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the button
        start = (Button) findViewById(R.id.start);
        debugInfo = (TextView) findViewById(R.id.debugInfo);

        indexList = new HashMap<>();
        valueList = new HashMap<>();

        wifiData = new ArrayList<>();
        wifiDataLength = 0;


        c1 = (RatingBar) findViewById(R.id.ratingBar1);
        c2 = (RatingBar) findViewById(R.id.ratingBar2);
        c3 = (RatingBar) findViewById(R.id.ratingBar3);
        c4 = (RatingBar) findViewById(R.id.ratingBar4);

        c5 = (RatingBar) findViewById(R.id.ratingBar5);
        c6 = (RatingBar) findViewById(R.id.ratingBar6);
        c7 = (RatingBar) findViewById(R.id.ratingBar7);
        c8 = (RatingBar) findViewById(R.id.ratingBar8);

        c9 = (RatingBar) findViewById(R.id.ratingBar9);
        c10 = (RatingBar) findViewById(R.id.ratingBar10);
        c11 = (RatingBar) findViewById(R.id.ratingBar11);
        c12 = (RatingBar) findViewById(R.id.ratingBar12);

        c13 = (RatingBar) findViewById(R.id.ratingBar13);
        c14 = (RatingBar) findViewById(R.id.ratingBar14);
        c15 = (RatingBar) findViewById(R.id.ratingBar15);
        c16 = (RatingBar) findViewById(R.id.ratingBar16);


        readBssid(bssidList);

        try {
            readRssiTables(bssidList,indexList,valueList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         //Set the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


         //if the default accelerometer exists
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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    c1.setRating(0.0f);
                    c2.setRating(0.0f);
                    c3.setRating(0.0f);
                    c4.setRating(0.0f);
                    c5.setRating(0.0f);
                    c6.setRating(0.0f);
                    c7.setRating(0.0f);
                    c8.setRating(0.0f);
                    c9.setRating(0.0f);
                    c10.setRating(0.0f);
                    c11.setRating(0.0f);
                    c12.setRating(0.0f);
                    c13.setRating(0.0f);
                    c14.setRating(0.0f);
                    c15.setRating(0.0f);
                    c16.setRating(0.0f);

                    wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.startScan();
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    if (wifiDataChange(wifiData, scanResults, wifiDataLength)) {
                        wifiDataLength = scanResults.size();
                        wifiData.clear();
                        for (ScanResult scanResult : scanResults) {
                            String[] wifi = new String[2];
                            wifi[0] = scanResult.BSSID;
                            wifi[1] = String.valueOf(scanResult.level);
                            wifiData.add(wifi);
                        }
                        formatWifiData(wifiData);
                        //debugInfo.setText(wifiData.get(0)[0] +" + " + wifiData.get(0)[1] + " + " + wifiData.get(1)[0] +" + " + wifiData.get(1)[1]);
//                        wifiData.get(0)[0] = "d0_4d_c6_f2_43";
//                        wifiData.get(0)[1] = "61";
//                        wifiData.get(1)[0] = "d0_4d_c6_f2_fc";
//                        wifiData.get(1)[1] = "62";
//                        wifiData.get(2)[0] = "d0_4d_c6_f2_fc";
//                        wifiData.get(2)[1] = "62";


                        try {
                            if (classify(wifiData, indexList, valueList)) {
                                switch (predict) {
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
                                    case 5:
                                        c5.setRating(10.0f);
                                        break;
                                    case 6:
                                        c6.setRating(10.0f);
                                        break;
                                    case 7:
                                        c7.setRating(10.0f);
                                        break;
                                    case 8:
                                        c8.setRating(10.0f);
                                        break;
                                    case 9:
                                        c9.setRating(10.0f);
                                        break;
                                    case 10:
                                        c10.setRating(10.0f);
                                        break;
                                    case 11:
                                        c11.setRating(10.0f);
                                        break;
                                    case 12:
                                        c12.setRating(10.0f);
                                        break;
                                    case 13:
                                        c13.setRating(10.0f);
                                        break;
                                    case 14:
                                        c14.setRating(10.0f);
                                        break;
                                    case 15:
                                        c15.setRating(10.0f);
                                        break;
                                    case 16:
                                        c16.setRating(10.0f);
                                        break;
                                    default:
                                }
                            }
                            else {
                                //Toast.makeText(getApplicationContext(), "Can not classify, please scan again! " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }


                    }
                    else {
                        //Toast.makeText(getApplicationContext(), "Same data, please scan again! " , Toast.LENGTH_SHORT).show();
                    }
                }
            }).start();
        });
    }

    private boolean classify(ArrayList<String[]> wifiData, Map<String,int[]> indexList, Map<String,float[][]> valueList) throws Exception {
        float[] prior = new float[cellNum];
        Arrays.fill(prior, (float) (1.0 /cellNum));
        for (int i = 0; i < wifiData.size(); i++) {
            String bssid = wifiData.get(i)[0];
            if (indexList.get(bssid) != null) {

                float[][] table = valueList.get(bssid);
                if (Integer.valueOf(wifiData.get(i)[1]) > indexList.get(bssid)[1]){
                    wifiData.get(i)[1] = Integer.toString(indexList.get(bssid)[1]);
                }

                if (Integer.valueOf(wifiData.get(i)[1]) < indexList.get(bssid)[0]){
                    wifiData.get(i)[1] = Integer.toString(indexList.get(bssid)[0]);
                }

                int index = Integer.valueOf(wifiData.get(i)[1]) - indexList.get(bssid)[0];
                float[] prob = extractCol(table, index);
                float[] post = dotProduct(prob, prior);
//                debugInfo.setText(Integer.toString(index) + "+" + Float.toString(prob[9]) + "+" + Float.toString(post[9]));
                if (arraySum(post) != 0) {
                    prior = arrayDivide(post,arraySum(post));
                }
                else {
                    break;
                }

                if (findMax(prior)) {
                    return true;
                }
            }
            else {
                //debugInfo.setText("Can not locate");
            }
        }
        return false;
    }

    private boolean findMax(float[] prior) {
        for (int i = 0; i < prior.length; i++) {
            if (prior[i] > 0.95 ) {
                predict = i;
                return true;
            }
        }
        return false;
    }

    private float arraySum(float[] post) {
        float sum = 0;
        for (int i = 0; i < post.length; i++) {
            sum += post[i];
        }
        return sum;
    }

    private float[] arrayDivide(float[] post, float sum) {
        for (int i = 0; i < post.length; i++) {
            post[i] = post[i] / sum;
        }
        return post;
    }

    private float[] dotProduct(float[] prob, float[] prior) {
        float[] result = new float[prob.length];
        for (int i = 0; i < prob.length; i++) {
            result[i] = prob[i] * prior[i];
        }
    return result;
    }

    private float[] extractCol(float[][] table, int index) {
        float[] columnValues = new float[table.length];
        for (int i = 0; i < table.length; i++) {
            columnValues[i] = table[i][index];
        }
        return columnValues;
    }


    private void readRssiTables(ArrayList<String> bssidList, Map<String,int[]> indexList, Map<String,float[][]> valueList) throws IOException {
        for (String bssid: bssidList) {
            String indexFileName = "rssi_table/" + bssid + "_index.csv";
            String valueFileName = "rssi_table/" + bssid + "_data.csv";
            int[] index = readFromIndexFile(indexFileName);
            indexList.put(bssid, index);
            float[][] value = readFromValueFile(valueFileName, index);
            valueList.put(bssid, value);
        }
    }

    private float[][] readFromValueFile(String fileName, int[] index) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int row = 0;
        float[][] value = new float[16][index[1] - index[0]];
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < index[1] - index[0]; col++) {
                value[row][col] = Float.parseFloat(values[col]);
            }
            row++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return value;
    }

    private int[] readFromIndexFile(String fileName) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int[] index = new int[2];
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            index[i] =  Integer.parseInt(line);
            i++;
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return index;
    }

    private void formatWifiData(ArrayList<String[]> wifiData) {
        for (int i = 0; i< wifiData.size(); i++) {
            wifiData.get(i)[0] = wifiData.get(i)[0].substring(0, wifiData.get(i)[0].length()-3);
            wifiData.get(i)[0] = wifiData.get(i)[0].replace(':','_');
            wifiData.get(i)[1] = wifiData.get(i)[1].substring(1);
        }
    }

    private boolean wifiDataChange(ArrayList<String[]> wifiData, List<ScanResult> scanResults, int wifiDataLength) {
        if (wifiDataLength != scanResults.size()) {
            return true;
        }
        else {
            for (int i = 0; i < wifiDataLength; i ++ ) {
                if (Integer.parseInt(wifiData.get(i)[1]) != scanResults.get(i).level) {
                    return true;
                }
            }
        }
        return false;
    }

    private void readBssid(ArrayList<String> bssidList) {
        AssetManager assetManager = getAssets();
        String[] fileList;
        try {
            fileList = assetManager.list("rssi_table");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String bssid : fileList) {
            if (bssid.contains("_data.csv")) {
                bssidList.add(bssid.substring(0,bssid.length()-9));
            }
        }
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
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing.
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        // get the the x,y,z values of the accelerometer
//        float aX = event.values[0];
//        float aY = event.values[1];
//        float aZ = event.values[2];
//
//        if (sampleNum < windowSize) {
//            sensorData[sampleNum][0] = aX;
//            sensorData[sampleNum][1] = aY;
//            sensorData[sampleNum][2] = aZ;
//
//            sampleNum = sampleNum + 1;
//        }
//
//    }
}