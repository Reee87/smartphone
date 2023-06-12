package com.example.example6;
import java.util.ArrayList;
import java.util.List;

public class ArrayListLock {
    private List<Float> lockedArrayList = new ArrayList<>();

    public void addItem(float item) {
        synchronized (lockedArrayList) {
            lockedArrayList.add(item);
        }
    }

    public float getItem(int index) {
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
}
