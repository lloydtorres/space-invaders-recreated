package client;

import java.util.LinkedList;

public class FixedSizeQueue<T> {
    private final int maxSize;
    private final LinkedList<T> queue = new LinkedList<>();

    public FixedSizeQueue(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be greater than 0");
        }
        this.maxSize = maxSize;
    }

    public void enqueue(T element) {
        if (queue.size() == maxSize) {
            // If the queue is at its maximum size, remove the oldest element
            queue.removeFirst();
        }
        // Add the new element to the end of the queue
        queue.addLast(element);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        // Remove and return the element from the front of the queue
        return queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
