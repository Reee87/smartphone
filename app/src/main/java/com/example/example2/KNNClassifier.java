package com.example.example2;

import java.util.*;
import java.util.Map.Entry;

public class KNNClassifier {
    private float[][] feature; // feature vectors
    private int[] label; // class labels

    public KNNClassifier(float[][] X, int[] y) {
        this.feature = X;
        this.label = y;
    }

    public int predict(float[] x, int k) {
        // Calculate the Euclidean distance between x and each instance in X
        Map<Integer, Float> distances = new HashMap<>();
        for (int i = 0; i < feature.length; i++) {
            float dist = euclideanDistance(x, feature[i]);
            distances.put(i, dist);
        }

        // Sort the distances in ascending order
        List<Entry<Integer, Float>> sortedDistances = new ArrayList<>(distances.entrySet());
        sortedDistances.sort(Entry.comparingByValue());

        // Get the k-nearest neighbors
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            neighbors.add(sortedDistances.get(i).getKey());
        }

        // Count the class labels of the k-nearest neighbors
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i : neighbors) {
            int labels = label[i];
            counts.put(labels, counts.getOrDefault(labels, 0) + 1);
        }

        // Get the most common class label among the k-nearest neighbors
        int prediction = -1;
        int maxCount = -1;
        for (Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                prediction = entry.getKey();
            }
        }

        return prediction;
    }

    private float euclideanDistance(float[] a, float[] b) {
        float dist = 0;
        for (int i = 0; i < a.length; i++) {
            dist += Math.pow(a[i] - b[i], 2);
        }
        return (float) Math.sqrt(dist);
    }
}