package com.example.example6;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class StepCounter {
    // Constants
    private static final int WINDOW_SIZE = 150;  // Adjust the window size as needed
    private static final float THRESHOLD = (float) 0.8; // Adjust the threshold as needed

    // Variables
    private float[] referenceSignal;
    private RingBuffer<Float> currentWindow;
    private int stepCount;
    private float[] correlation;

    private int count;

    public StepCounter() {
        // Initialize variables
        referenceSignal = new float[WINDOW_SIZE];
        currentWindow = new RingBuffer<Float>(WINDOW_SIZE);
        stepCount = 0;
        correlation = new float[WINDOW_SIZE];
        count = 0;
    }

    public void collectReferenceSignal(float[] accelerometerData) {
        // Assuming accelerometerData contains the collected data while walking
        // Select a portion of the data as the reference signal
        referenceSignal = Arrays.copyOfRange(accelerometerData, 0, WINDOW_SIZE);
    }

    private float calculateAutocorrelation() {
        // Check if the signals have the same length
        if (referenceSignal.length != currentWindow.size()) {
            throw new IllegalArgumentException("Signal lengths do not match");
        }

        float sumSignal1 = 0.0F;
        float sumSignal2 = 0.0F;

        float[] signal2 = currentWindow.readAll();

        // Calculate the means of the two signals
        for (int i = 0; i < WINDOW_SIZE; i++) {
            sumSignal1 += referenceSignal[i];
            sumSignal2 += signal2[i];
        }
        float meanSignal1 = sumSignal1 / WINDOW_SIZE;
        float meanSignal2 = sumSignal2 / WINDOW_SIZE;

        float numerator = 0.0F;
        float denominatorSignal1 = 0.0F;
        float denominatorSignal2 = 0.0F;

        // Calculate the numerator and denominators of the autocorrelation formula
        for (int i = 0; i < WINDOW_SIZE; i++) {
            numerator += (referenceSignal[i] - meanSignal1) * (signal2[i] - meanSignal2);
            denominatorSignal1 += Math.pow(referenceSignal[i] - meanSignal1, 2);
            denominatorSignal2 += Math.pow(signal2[i] - meanSignal2, 2);
        }

        // Calculate the autocorrelation value
        float autocorrelation = (float) (numerator / Math.sqrt(denominatorSignal1 * denominatorSignal2));
        return autocorrelation;
    }
    public void processIncomingData(float accelerometerData) {
        // Assuming accelerometerData contains the new incoming data
        float autocorrelation ;
        // Append the new data to the current window
        currentWindow.enqueue(accelerometerData);

        if (currentWindow.isFull()) {
            autocorrelation = calculateAutocorrelation();
            correlation[count] = autocorrelation;
            count++;
        }

        if (count == 150) {
        // Check if there is a peak within the window that exceeds the threshold

            if (isPeakWithinWindow(correlation, THRESHOLD)) {
                // Count a step
                stepCount++;
            }
            count = 0;
        }

    }

    public int getStepCount() {
        return stepCount;
    }

    private boolean isPeakWithinWindow(float[] window, float threshold) {
        // Check if there is a peak within the window that exceeds the threshold
        float maxValue = Float.MIN_VALUE;
        for (float value : window) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue > threshold;
    }


}

