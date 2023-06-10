package com.example.example6;

public class RingBuffer<T> {
    private T[] buffer;
    private int size;
    private int head;
    private int tail;
    private int count;

    public RingBuffer(int size) {
        // Create an array with the specified size
        buffer = (T[]) new Object[size];
        this.size = size;
        head = 0;
        tail = 0;
        count = 0;
    }

    public void enqueue(T item) {
        if (count == size) {
            // Buffer is full, overwrite the oldest item
            head = (head + 1) % size;
        } else {
            count++;
        }

        buffer[tail] = item;
        tail = (tail + 1) % size;
    }

    public T dequeue() {
        if (count == 0) {
            throw new IllegalStateException("Buffer is empty");
        }

        T item = buffer[head];
        head = (head + 1) % size;
        count--;

        return item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == size;
    }

    public int size() {
        return count;
    }

    public float[] readAll() {
        float[] result = new float[size];

        int currentHead = head;
        for (int i = 0; i < count; i++) {
            result[i] = (float) buffer[currentHead];
            currentHead = (currentHead + 1) % size;
        }

        return result;
    }
}