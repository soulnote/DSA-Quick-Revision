### 40\. Implement a Basic Hash Map

**Key Components:**

1.  **`HashNode<K, V>`:** Represents a single key-value pair within the map. Each bucket in our hash table will store a linked list of these nodes to handle collisions.
2.  **`BasicHashMap<K, V>`:** The main hash map class.
      * **`buckets` Array:** An array where each index is a "bucket" that can hold the head of a linked list of `HashNode`s.
      * **`capacity`:** The current size of the `buckets` array.
      * **`size`:** The number of key-value pairs currently stored in the map.
      * **`LOAD_FACTOR_THRESHOLD`:** A constant that determines when the hash map should resize. If `size / capacity` exceeds this, the `buckets` array is expanded.
      * **`_hash(K key)`:** A custom hash function to convert a key into an array index.
      * **`put(K key, V value)`:** Inserts or updates a key-value pair. Handles collision by adding to a linked list and triggers resizing if needed.
      * **`get(K key)`:** Retrieves the value associated with a key.
      * **`remove(K key)`:** Removes a key-value pair from the map.

<!-- end list -->

```java
import java.util.ArrayList;
import java.util.Objects; // For Objects.equals and Objects.hashCode

public class BasicHashMap<K, V> {

    // --- HashNode Class ---
    static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next; // Pointer to the next node in case of collision (separate chaining)

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "(" + key + "=" + value + ")";
        }
    }

    // --- BasicHashMap Class Fields ---
    private ArrayList<HashNode<K, V>> buckets; // Array of linked lists (buckets)
    private int capacity; // Current size of the buckets array
    private int size; // Current number of key-value pairs stored
    private static final double LOAD_FACTOR_THRESHOLD = 0.75; // Threshold for resizing

    // --- Constructor ---
    public BasicHashMap() {
        this(16); // Default initial capacity
    }

    public BasicHashMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive.");
        }
        this.capacity = initialCapacity;
        this.buckets = new ArrayList<>(capacity);
        // Initialize all buckets to null
        for (int i = 0; i < capacity; i++) {
            buckets.add(null);
        }
        this.size = 0;
    }

    // --- Custom Hash Function ---
    // Maps a key to an index in the buckets array
    private int _hash(K key) {
        // Use Java's built-in hashCode(), then modulo by capacity
        // Ensure non-negative result
        return Math.abs(Objects.hashCode(key)) % capacity;
    }

    // --- put(K key, V value) Operation ---
    public void put(K key, V value) {
        int bucketIndex = _hash(key);
        HashNode<K, V> head = buckets.get(bucketIndex);

        // Traverse the linked list at this bucket
        // If key already exists, update its value
        HashNode<K, V> current = head;
        while (current != null) {
            if (Objects.equals(current.key, key)) { // Use Objects.equals for safe comparison
                current.value = value;
                return; // Key found and value updated
            }
            current = current.next;
        }

        // If key does not exist, insert new node at the head of the list
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = head; // New node points to the old head
        buckets.set(bucketIndex, newNode); // Update bucket to point to new node
        size++;

        // Check load factor and resize if necessary
        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            System.out.println("Resizing: Current load factor " + (double) size / capacity + " exceeds " + LOAD_FACTOR_THRESHOLD);
            resize();
        }
    }

    // --- get(K key) Operation ---
    public V get(K key) {
        int bucketIndex = _hash(key);
        HashNode<K, V> head = buckets.get(bucketIndex);

        // Traverse the linked list
        HashNode<K, V> current = head;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    // --- remove(K key) Operation ---
    public V remove(K key) {
        int bucketIndex = _hash(key);
        HashNode<K, V> head = buckets.get(bucketIndex);
        HashNode<K, V> prev = null; // Keep track of the previous node for deletion

        HashNode<K, V> current = head;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                // Found the node to remove
                if (prev == null) {
                    // Node to remove is the head of the linked list
                    buckets.set(bucketIndex, current.next);
                } else {
                    // Node to remove is in the middle or end
                    prev.next = current.next;
                }
                size--;
                return current.value; // Return the removed value
            }
            prev = current;
            current = current.next;
        }
        return null; // Key not found
    }

    // --- resize() Operation ---
    // Doubles the capacity and rehashes all existing entries
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2; // Double the capacity
        ArrayList<HashNode<K, V>> oldBuckets = buckets;
        
        // Create new buckets array with new capacity
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(null);
        }
        size = 0; // Reset size, as we'll re-add all elements

        // Re-hash all nodes from old buckets to new buckets
        for (HashNode<K, V> head : oldBuckets) {
            HashNode<K, V> current = head;
            while (current != null) {
                // Store next node before moving current to new map
                HashNode<K, V> nextNode = current.next;
                // Important: clear next pointer of current node
                current.next = null;
                // Use the put method to re-insert the node into the new larger map
                put(current.key, current.value);
                current = nextNode;
            }
        }
        System.out.println("Resizing complete. New capacity: " + capacity);
    }

    // --- Helper Methods ---
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void display() {
        System.out.println("--- Hash Map Contents (Size: " + size + ", Capacity: " + capacity + ") ---");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            HashNode<K, V> current = buckets.get(i);
            if (current == null) {
                System.out.println("null");
            } else {
                StringBuilder sb = new StringBuilder();
                while (current != null) {
                    sb.append(current).append(" -> ");
                    current = current.next;
                }
                sb.append("null");
                System.out.println(sb.toString());
            }
        }
        System.out.println("----------------------------------------------");
    }

    public static void main(String[] args) {
        BasicHashMap<String, Integer> myMap = new BasicHashMap<>(4); // Small initial capacity to demonstrate resizing

        System.out.println("Initial Map:");
        myMap.display();

        System.out.println("\n--- Putting elements ---");
        myMap.put("apple", 10);
        myMap.put("banana", 20);
        myMap.put("cherry", 30);
        myMap.display(); // Should trigger resize after cherry due to small capacity

        myMap.put("date", 40);
        myMap.put("elderberry", 50); // Should trigger another resize
        myMap.put("fig", 60);
        myMap.put("grape", 70);
        myMap.put("honeydew", 80);
        myMap.put("apple", 100); // Update existing key
        myMap.display();

        System.out.println("\n--- Getting elements ---");
        System.out.println("Value for 'banana': " + myMap.get("banana")); // Expected: 20
        System.out.println("Value for 'apple': " + myMap.get("apple"));   // Expected: 100
        System.out.println("Value for 'kiwi': " + myMap.get("kiwi"));     // Expected: null

        System.out.println("\n--- Removing elements ---");
        System.out.println("Removing 'cherry'. Value: " + myMap.remove("cherry")); // Expected: 30
        System.out.println("Removing 'kiwi'. Value: " + myMap.remove("kiwi"));     // Expected: null
        myMap.display();

        System.out.println("Removing 'apple'. Value: " + myMap.remove("apple")); // Expected: 100
        myMap.display();

        System.out.println("\nIs map empty? " + myMap.isEmpty()); // Expected: false

        // Remove all to test isEmpty
        myMap.remove("banana");
        myMap.remove("date");
        myMap.remove("elderberry");
        myMap.remove("fig");
        myMap.remove("grape");
        myMap.remove("honeydew");
        myMap.display();
        System.out.println("Is map empty? " + myMap.isEmpty()); // Expected: true

        myMap.put("test", 1);
        myMap.display();
    }
}
```
