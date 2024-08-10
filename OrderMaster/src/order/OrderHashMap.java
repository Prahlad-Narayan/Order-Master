package order;

import java.util.ArrayList;
import java.util.LinkedList;

interface Map<K, V> {
    // Method to put a key-value pair into the map
    void put(K key, V value);

    // Method to get the value associated with a key
    V get(K key);
}

public class OrderHashMap<K, V> implements Map<K, V> {

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private LinkedList<Entry<K, V>>[] buckets;
    private int size;
    private static final int INITIAL_CAPACITY = 10; // Initial capacity of the HashMap

    // Constructor
    public OrderHashMap() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        size = 0;
        // Initialize each bucket with an empty linked list
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    public ArrayList<V> getValues() {
    	ArrayList<V> valueArrList = new ArrayList<>();
    	for (int i = 0; i < INITIAL_CAPACITY; i++) {
    		for (Entry<K, V> entry : buckets[i]) {
    			valueArrList.add(entry.getValue());
            }
        }
    	
    	return valueArrList;
    }

    // Method to put a key-value pair into the map
    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        // Check if the key already exists in the bucket
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                // Update the value if the key exists
                entry = new Entry<>(key, value);
                return;
            }
        }
        // Add the key-value pair to the bucket
        bucket.add(new Entry<>(key, value));
        size++;
    }

    // Method to get the value associated with a key
    @Override
    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];
        // Search for the key in the bucket
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue(); // Return the value if found
            }
        }
        return null; // Return null if key not found
    }

    // Method to get the index of the bucket for a given key
    private int getIndex(K key) {
        // Simple hash function: key.hashCode() mod the number of buckets
        return Math.abs(key.hashCode()) % buckets.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last ", "
        }
        sb.append("}");
        return sb.toString();
    }
}