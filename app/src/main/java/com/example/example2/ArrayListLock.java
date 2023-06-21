package com.example.example2;
import android.net.wifi.ScanResult;

import java.util.ArrayList;
import java.util.List;

public class ArrayListLock {
    private List<String[]> lockedArrayList = new ArrayList<>();

    public void add(String[] item) {
        synchronized (lockedArrayList) {
            lockedArrayList.add(item);
        }
    }

    public String[] get(int index) {
        synchronized (lockedArrayList) {
            return lockedArrayList.get(index);
        }
    }

    // Other methods...

    public int size() {
        synchronized (lockedArrayList) {
            return lockedArrayList.size();
        }
    }

    public void clear() {
        synchronized (lockedArrayList) {
            lockedArrayList.clear();
        }
    }

    public void copyFrom(ArrayList<String[]> arrayList) {
        for (String[] item : arrayList) {
            lockedArrayList.add(item);
        }
    }
}
