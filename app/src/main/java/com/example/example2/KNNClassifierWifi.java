package com.example.example2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNClassifierWifi {
    private int[][] feature; // feature vectors
    private int[] label; // class labels

    public KNNClassifierWifi(int[][] X, int[] y) {
        this.feature = X;
        this.label = y;
    }

    public int predict(int[] x, int k) {
        // Calculate the Euclidean distance between x and each instance in X
        Map<Integer, Integer> distances = new HashMap<>();
        for (int i = 0; i < feature.length; i++) {
            int dist = euclideanDistance(x, feature[i]);
            distances.put(i, dist);
        }

        // Sort the distances in ascending order
        List<Map.Entry<Integer, Integer>> sortedDistances = new ArrayList<>(distances.entrySet());
        sortedDistances.sort(Map.Entry.comparingByValue());

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
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                prediction = entry.getKey();
            }
        }

        return prediction;
    }

    private int euclideanDistance(int[] a, int[] b) {
        int dist = 0;
        for (int i = 0; i < a.length; i++) {
            dist += Math.pow(a[i] - b[i], 2);
        }
        return (int) Math.sqrt(dist);
    }
}
