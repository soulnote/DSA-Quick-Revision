Here's a comprehensive list of Java data structures with their basic syntax and most commonly used methods:

### **1. Linear Data Structures**

-   **Arrays**
    Arrays are fixed-size sequential collections of elements of the same data type. They offer direct access to elements by index.
    ```java
    int[] arr = new int[10]; // Declaring an array of integers with size 10
    arr[0] = 1; // Setting value at index 0
    int value = arr[0]; // Accessing value at index 0
    int length = arr.length; // Getting the length of the array
    ```
    **Most Used Methods (implicit through array properties/manipulation):**
    * `arr[index] = value;` (Assignment)
    * `value = arr[index];` (Access)
    * `arr.length` (Get length)

-   **LinkedList**
    A `LinkedList` is a doubly-linked list implementation of the `List` and `Deque` interfaces. It's efficient for insertions and deletions in the middle of the list.
    ```java
    import java.util.LinkedList;

    LinkedList<Integer> linkedList = new LinkedList<>();
    linkedList.add(1); // Adds element to the end
    linkedList.addFirst(0); // Adds element to the beginning
    linkedList.add(1, 2); // Adds element at a specific index
    int element = linkedList.get(0); // Accesses element at index 0
    int removedElement = linkedList.remove(0); // Removes and returns element at index 0
    boolean contains = linkedList.contains(1); // Checks if element exists
    int size = linkedList.size(); // Gets the number of elements
    linkedList.clear(); // Removes all elements
    ```
    **Most Used Methods:**
    * `add(E e)`: Appends the specified element to the end of this list.
    * `addFirst(E e)`: Inserts the specified element at the beginning of this list.
    * `addLast(E e)`: Appends the specified element to the end of this list.
    * `get(int index)`: Returns the element at the specified position in this list.
    * `remove(int index)`: Removes the element at the specified position in this list.
    * `removeFirst()`: Removes and returns the first element from this list.
    * `removeLast()`: Removes and returns the last element from this list.
    * `contains(Object o)`: Returns `true` if this list contains the specified element.
    * `size()`: Returns the number of elements in this list.
    * `clear()`: Removes all of the elements from this list.

-   **ArrayList**
    An `ArrayList` is a resizable array implementation of the `List` interface. It's efficient for random access but can be slow for insertions and deletions in the middle.
    ```java
    import java.util.ArrayList;

    ArrayList<Integer> arrayList = new ArrayList<>();
    arrayList.add(1); // Adds element to the end
    arrayList.add(0, 0); // Adds element at a specific index
    int element = arrayList.get(0); // Accesses element at index 0
    int removedElement = arrayList.remove(0); // Removes and returns element at index 0
    boolean contains = arrayList.contains(1); // Checks if element exists
    int size = arrayList.size(); // Gets the number of elements
    arrayList.clear(); // Removes all elements
    ```
    **Most Used Methods:**
    * `add(E e)`: Appends the specified element to the end of this list.
    * `add(int index, E element)`: Inserts the specified element at the specified position in this list.
    * `get(int index)`: Returns the element at the specified position in this list.
    * `remove(int index)`: Removes the element at the specified position in this list.
    * `remove(Object o)`: Removes the first occurrence of the specified element from this list, if it is present.
    * `set(int index, E element)`: Replaces the element at the specified position in this list with the specified element.
    * `contains(Object o)`: Returns `true` if this list contains the specified element.
    * `size()`: Returns the number of elements in this list.
    * `clear()`: Removes all of the elements from this list.

-   **Vector**
    `Vector` is a legacy class similar to `ArrayList` but is synchronized, making it thread-safe. It's generally less preferred than `ArrayList` due to performance overhead unless thread-safety is explicitly required.
    ```java
    import java.util.Vector;

    Vector<Integer> vector = new Vector<>();
    vector.add(1); // Adds element to the end
    int element = vector.get(0); // Accesses element at index 0
    vector.removeElement(1); // Removes the first occurrence of the argument from this vector
    ```
    **Most Used Methods:**
    * `add(E e)`: Appends the specified element to the end of this Vector.
    * `get(int index)`: Returns the element at the specified position in this Vector.
    * `remove(int index)`: Removes the element at the specified position in this Vector.
    * `removeElement(Object obj)`: Removes the first occurrence of the argument from this vector.
    * `size()`: Returns the number of components in this vector.
    * `elementAt(int index)`: Returns the component at the specified index. (Legacy)

### **2. Stack-Based Data Structures**

-   **Stack**
    The `Stack` class represents a last-in-first-out (LIFO) stack of objects. It extends `Vector` and thus is synchronized.
    ```java
    import java.util.Stack;

    Stack<Integer> stack = new Stack<>();
    stack.push(1); // Pushes element onto the stack
    stack.push(2);
    int element = stack.pop(); // Pops the top element from the stack (returns 2)
    int peekedElement = stack.peek(); // Looks at the top element of the stack without removing it (returns 1)
    boolean isEmpty = stack.empty(); // Checks if the stack is empty
    int position = stack.search(1); // Returns the 1-based position where an object is on this stack (returns 1 for 1)
    ```
    **Most Used Methods:**
    * `push(E item)`: Pushes an item onto the top of this stack.
    * `pop()`: Removes the object at the top of this stack and returns that object as the value of this function.
    * `peek()`: Looks at the object at the top of this stack without removing it from the stack.
    * `empty()`: Tests if this stack is empty.
    * `search(Object o)`: Returns the 1-based position where an object is on this stack.

### **3. Queue-Based Data Structures**

-   **Queue** (via `LinkedList`)
    The `Queue` interface represents a collection designed for holding elements prior to processing. `LinkedList` is a common implementation. It's typically FIFO (First-In, First-Out).
    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    Queue<Integer> queue = new LinkedList<>();
    queue.add(1); // Adds element to the queue (throws exception if full)
    queue.offer(2); // Adds element to the queue (returns false if full)
    int element = queue.poll(); // Retrieves and removes the head of the queue (returns null if empty)
    int head = queue.peek(); // Retrieves but does not remove the head of the queue (returns null if empty)
    int removedElement = queue.remove(); // Retrieves and removes the head of the queue (throws exception if empty)
    ```
    **Most Used Methods:**
    * `add(E e)`: Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions.
    * `offer(E e)`: Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions.
    * `remove()`: Retrieves and removes the head of this queue.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `element()`: Retrieves, but does not remove, the head of this queue.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.

-   **PriorityQueue**
    A `PriorityQueue` is an unbounded priority queue based on a priority heap. Elements are ordered according to their natural ordering, or by a `Comparator` provided at queue construction time. The element with the highest priority (smallest value by default) is at the head.
    ```java
    import java.util.PriorityQueue;

    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    priorityQueue.add(3); // Adds element to the queue
    priorityQueue.add(1);
    priorityQueue.add(2);
    int element = priorityQueue.poll(); // Removes and returns the highest priority element (returns 1)
    int peekedElement = priorityQueue.peek(); // Retrieves but does not remove the highest priority element (returns 2)
    boolean contains = priorityQueue.contains(2); // Checks if element exists
    int size = priorityQueue.size(); // Gets the number of elements
    ```
    **Most Used Methods:**
    * `add(E e)`: Inserts the specified element into this priority queue.
    * `offer(E e)`: Inserts the specified element into this priority queue.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.
    * `remove(Object o)`: Removes a single instance of the specified element from this queue, if it is present.
    * `size()`: Returns the number of elements in this collection.

-   **Deque** (via `ArrayDeque`)
    A `Deque` (double-ended queue) is a linear collection that supports element insertion and removal at both ends. `ArrayDeque` is a resizable-array implementation.
    ```java
    import java.util.ArrayDeque;
    import java.util.Deque;

    Deque<Integer> deque = new ArrayDeque<>();
    deque.addFirst(1); // Adds element to the front
    deque.addLast(2); // Adds element to the end
    int firstElement = deque.removeFirst(); // Removes and returns the first element (returns 1)
    int lastElement = deque.removeLast(); // Removes and returns the last element (returns 2)
    int peekFirst = deque.peekFirst(); // Retrieves, but does not remove, the first element
    int peekLast = deque.peekLast(); // Retrieves, but does not remove, the last element
    ```
    **Most Used Methods:**
    * `addFirst(E e)`: Inserts the specified element at the front of this deque.
    * `addLast(E e)`: Inserts the specified element at the end of this deque.
    * `offerFirst(E e)`: Inserts the specified element at the front of this deque.
    * `offerLast(E e)`: Inserts the specified element at the end of this deque.
    * `removeFirst()`: Retrieves and removes the first element of this deque.
    * `removeLast()`: Retrieves and removes the last element of this deque.
    * `pollFirst()`: Retrieves and removes the first element of this deque, or returns `null` if this deque is empty.
    * `pollLast()`: Retrieves and removes the last element of this deque, or returns `null` if this deque is empty.
    * `peekFirst()`: Retrieves, but does not remove, the first element of this deque, or returns `null` if this deque is empty.
    * `peekLast()`: Retrieves, but does not remove, the last element of this deque, or returns `null` if this deque is empty.

### **4. Set-Based Data Structures**

Sets are collections that do not allow duplicate elements.

-   **HashSet**
    A `HashSet` stores elements in a hash table. It offers constant-time performance for basic operations (add, remove, contains) assuming the hash function disperses elements properly. It does not guarantee any order.
    ```java
    import java.util.HashSet;

    HashSet<Integer> hashSet = new HashSet<>();
    hashSet.add(1); // Adds element
    hashSet.add(2);
    boolean contains = hashSet.contains(1); // Checks if element exists (returns true)
    boolean removed = hashSet.remove(2); // Removes element (returns true)
    int size = hashSet.size(); // Gets the number of elements
    boolean isEmpty = hashSet.isEmpty(); // Checks if the set is empty
    ```
    **Most Used Methods:**
    * `add(E e)`: Adds the specified element to this set if it is not already present.
    * `remove(Object o)`: Removes the specified element from this set if it is present.
    * `contains(Object o)`: Returns `true` if this set contains the specified element.
    * `size()`: Returns the number of elements in this set (its cardinality).
    * `isEmpty()`: Returns `true` if this set contains no elements.
    * `clear()`: Removes all of the elements from this set.

-   **LinkedHashSet**
    A `LinkedHashSet` maintains a doubly-linked list running through all of its entries. This insertion-order is preserved when iterating over the set. It combines the benefits of `HashSet` (fast lookups) with the predictability of iteration order.
    ```java
    import java.util.LinkedHashSet;

    LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
    linkedHashSet.add(1); // Adds element
    linkedHashSet.add(2);
    // Iteration order will be 1, 2
    ```
    **Most Used Methods:**
    * `add(E e)`: Adds the specified element to this set if it is not already present.
    * `remove(Object o)`: Removes the specified element from this set if it is present.
    * `contains(Object o)`: Returns `true` if this set contains the specified element.
    * `size()`: Returns the number of elements in this set (its cardinality).
    * `clear()`: Removes all of the elements from this set.

-   **TreeSet**
    A `TreeSet` stores elements in a sorted order (natural ordering or custom `Comparator`). It uses a Red-Black tree for storage, providing guaranteed $O(\log n)$ time cost for basic operations.
    ```java
    import java.util.TreeSet;

    TreeSet<Integer> treeSet = new TreeSet<>();
    treeSet.add(3); // Adds element in sorted order
    treeSet.add(1);
    treeSet.add(2);
    // Elements are stored as 1, 2, 3
    int first = treeSet.first(); // Returns the first (lowest) element currently in this set (returns 1)
    int last = treeSet.last(); // Returns the last (highest) element currently in this set (returns 3)
    int ceiling = treeSet.ceiling(1); // Returns the least element in this set greater than or equal to the given element (returns 1)
    int floor = treeSet.floor(2); // Returns the greatest element in this set less than or equal to the given element (returns 2)
    ```
    **Most Used Methods:**
    * `add(E e)`: Adds the specified element to this set if it is not already present.
    * `remove(Object o)`: Removes the specified element from this set if it is present.
    * `contains(Object o)`: Returns `true` if this set contains the specified element.
    * `first()`: Returns the first (lowest) element currently in this set.
    * `last()`: Returns the last (highest) element currently in this set.
    * `ceiling(E e)`: Returns the least element in this set greater than or equal to the given element, or `null` if there is no such element.
    * `floor(E e)`: Returns the greatest element in this set less than or equal to the given element, or `null` if there is no such element.
    * `size()`: Returns the number of elements in this set (its cardinality).

### **5. Map-Based Data Structures**

Maps store key-value pairs, where each key is unique.

-   **HashMap**
    A `HashMap` stores key-value pairs in a hash table. It provides constant-time performance for basic operations (put, get, remove) assuming the hash function disperses elements properly. It does not guarantee any order.
    ```java
    import java.util.HashMap;

    HashMap<String, Integer> hashMap = new HashMap<>();
    hashMap.put("key1", 1); // Adds key-value pair
    hashMap.put("key2", 2);
    int value = hashMap.get("key1"); // Retrieves value for a key (returns 1)
    boolean containsKey = hashMap.containsKey("key2"); // Checks if key exists (returns true)
    boolean containsValue = hashMap.containsValue(1); // Checks if value exists (returns true)
    int removedValue = hashMap.remove("key1"); // Removes key-value pair (returns 1)
    int size = hashMap.size(); // Gets the number of key-value pairs
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `containsKey(Object key)`: Returns `true` if this map contains a mapping for the specified key.
    * `containsValue(Object value)`: Returns `true` if this map maps one or more keys to the specified value.
    * `size()`: Returns the number of key-value mappings in this map.
    * `isEmpty()`: Returns `true` if this map contains no key-value mappings.
    * `keySet()`: Returns a `Set` view of the keys contained in this map.
    * `values()`: Returns a `Collection` view of the values contained in this map.
    * `entrySet()`: Returns a `Set` view of the mappings contained in this map.

-   **LinkedHashMap**
    A `LinkedHashMap` maintains a doubly-linked list running through all of its entries, preserving the insertion order of key-value pairs. It provides predictable iteration order while still offering good performance for basic map operations.
    ```java
    import java.util.LinkedHashMap;

    LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("keyA", 1);
    linkedHashMap.put("keyB", 2);
    // Iteration order will be keyA, keyB
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `containsKey(Object key)`: Returns `true` if this map contains a mapping for the specified key.
    * `size()`: Returns the number of key-value mappings in this map.

-   **TreeMap**
    A `TreeMap` stores key-value pairs in a sorted order based on the natural ordering of its keys or by a `Comparator` provided at map creation time. It uses a Red-Black tree for storage, providing guaranteed $O(\log n)$ time cost for basic operations.
    ```java
    import java.util.TreeMap;

    TreeMap<String, Integer> treeMap = new TreeMap<>();
    treeMap.put("banana", 3);
    treeMap.put("apple", 1);
    treeMap.put("cherry", 2);
    // Keys are stored in sorted order: apple, banana, cherry
    String firstKey = treeMap.firstKey(); // Returns the first (lowest) key currently in this map (returns "apple")
    String lastKey = treeMap.lastKey(); // Returns the last (highest) key currently in this map (returns "cherry")
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `containsKey(Object key)`: Returns `true` if this map contains a mapping for the specified key.
    * `firstKey()`: Returns the first (lowest) key currently in this map.
    * `lastKey()`: Returns the last (highest) key currently in this map.
    * `ceilingKey(K key)`: Returns the least key greater than or equal to the given key, or `null` if there is no such key.
    * `floorKey(K key)`: Returns the greatest key less than or equal to the given key, or `null` if there is no such key.
    * `size()`: Returns the number of key-value mappings in this map.

### **6. Specialized Data Structures**

-   **Hashtable**
    `Hashtable` is a legacy class similar to `HashMap` but is synchronized (thread-safe) and does not allow `null` keys or values. Generally, `ConcurrentHashMap` is preferred for concurrent use cases.
    ```java
    import java.util.Hashtable;

    Hashtable<String, Integer> hashtable = new Hashtable<>();
    hashtable.put("key", 1); // Adds key-value pair
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Maps the specified `key` to the specified `value` in this hashtable.
    * `get(Object key)`: Returns the value to which the specified `key` is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the key (and its corresponding value) from this hashtable.
    * `containsKey(Object key)`: Tests if the specified object is a key in this hashtable.
    * `size()`: Returns the number of keys in this hashtable.

-   **ConcurrentHashMap**
    A `ConcurrentHashMap` is a thread-safe version of `HashMap`. It provides much better concurrency performance than `Hashtable` or `Collections.synchronizedMap()` by allowing multiple concurrent readers and a limited number of concurrent writers.
    ```java
    import java.util.concurrent.ConcurrentHashMap;

    ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    concurrentHashMap.put("key", 1); // Adds key-value pair
    concurrentHashMap.putIfAbsent("newKey", 2); // Adds if key is not already present
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `putIfAbsent(K key, V value)`: If the specified key is not already associated with a value, associates it with the given value.
    * `replace(K key, V value)`: Replaces the entry for the specified key only if it is currently mapped to some value.
    * `replace(K key, V oldValue, V newValue)`: Replaces the entry for the specified key only if currently mapped to the specified old value.
    * `size()`: Returns the number of key-value mappings in this map.

-   **WeakHashMap**
    A `WeakHashMap` stores keys as `WeakReference` objects. This means that if a key is no longer strongly referenced elsewhere, it can be garbage collected, and its entry will be automatically removed from the `WeakHashMap`. Useful for caching.
    ```java
    import java.util.WeakHashMap;

    WeakHashMap<String, Integer> weakHashMap = new WeakHashMap<>();
    weakHashMap.put("key", 1); // Adds key-value pair
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `size()`: Returns the number of key-value mappings in this map.

-   **IdentityHashMap**
    An `IdentityHashMap` uses reference equality (`==`) instead of object equality (`.equals()`) when comparing keys. This is useful in scenarios where you need to distinguish between different objects that might have the same content.
    ```java
    import java.util.IdentityHashMap;

    IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<>();
    String s1 = new String("key");
    String s2 = new String("key");
    identityHashMap.put(s1, 1);
    identityHashMap.put(s2, 2); // Both s1 and s2 will be distinct keys
    ```
    **Most Used Methods:**
    * `put(K key, V value)`: Associates the specified value with the specified key in this map.
    * `get(Object key)`: Returns the value to which the specified key is mapped, or `null` if this map contains no mapping for the key.
    * `remove(Object key)`: Removes the mapping for the specified key from this map if it is present.
    * `size()`: Returns the number of key-value mappings in this map.

### **7. Tree-Based Data Structures (Reiteration for Clarity)**

These are already covered under Set-Based and Map-Based Data Structures but are highlighted for their tree-like underlying structure.

-   **TreeMap** (See Map-Based Data Structures for details)
    ```java
    import java.util.TreeMap;

    TreeMap<Integer, String> treeMap = new TreeMap<>();
    treeMap.put(1, "value"); // Adding key-value pair in sorted order by keys
    ```

-   **TreeSet** (See Set-Based Data Structures for details)
    ```java
    import java.util.TreeSet;

    TreeSet<Integer> treeSet = new TreeSet<>();
    treeSet.add(1); // Adding element in sorted order
    ```

### **8. Priority-Based Data Structures (Reiteration for Clarity)**

This is already covered under Queue-Based Data Structures but is highlighted for its specific prioritization behavior.

-   **PriorityQueue** (See Queue-Based Data Structures for details)
    ```java
    import java.util.PriorityQueue;

    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    priorityQueue.add(1); // Adding element
    ```

### **9. Concurrent Data Structures**

These data structures are designed for use in multi-threaded environments, providing thread-safety and often better performance than synchronized versions of their non-concurrent counterparts.

-   **ConcurrentLinkedQueue**
    An unbounded, thread-safe, non-blocking queue. Elements are added to the tail and removed from the head.
    ```java
    import java.util.concurrent.ConcurrentLinkedQueue;

    ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    concurrentLinkedQueue.add(1); // Adds element
    concurrentLinkedQueue.offer(2); // Adds element
    int element = concurrentLinkedQueue.poll(); // Retrieves and removes the head
    int peeked = concurrentLinkedQueue.peek(); // Retrieves but does not remove the head
    ```
    **Most Used Methods:**
    * `add(E e)`: Inserts the specified element at the tail of this queue.
    * `offer(E e)`: Inserts the specified element at the tail of this queue.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.
    * `isEmpty()`: Returns `true` if this queue contains no elements.
    * `size()`: Returns the number of elements in this queue.

-   **ConcurrentLinkedDeque**
    An unbounded, thread-safe, non-blocking double-ended queue. Supports element insertion and removal at both ends.
    ```java
    import java.util.concurrent.ConcurrentLinkedDeque;

    ConcurrentLinkedDeque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    concurrentLinkedDeque.addFirst(1); // Adds element to the front
    concurrentLinkedDeque.addLast(2); // Adds element to the end
    int first = concurrentLinkedDeque.pollFirst(); // Removes and returns the first element
    int last = concurrentLinkedDeque.pollLast(); // Removes and returns the last element
    ```
    **Most Used Methods:**
    * `addFirst(E e)`: Inserts the specified element at the front of this deque.
    * `addLast(E e)`: Inserts the specified element at the end of this deque.
    * `pollFirst()`: Retrieves and removes the first element of this deque, or returns `null` if this deque is empty.
    * `pollLast()`: Retrieves and removes the last element of this deque, or returns `null` if this deque is empty.
    * `peekFirst()`: Retrieves, but does not remove, the first element of this deque, or returns `null` if this deque is empty.
    * `peekLast()`: Retrieves, but does not remove, the last element of this deque, or returns `null` if this deque is empty.

-   **CopyOnWriteArrayList**
    A thread-safe variant of `ArrayList` in which all mutative operations (add, set, remove, etc.) are implemented by making a fresh copy of the underlying array. This is efficient for read-heavy operations but expensive for writes.
    ```java
    import java.util.concurrent.CopyOnWriteArrayList;

    CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    copyOnWriteArrayList.add(1); // Adds element (creates a new array internally)
    int element = copyOnWriteArrayList.get(0); // Accesses element (reads from the current array)
    ```
    **Most Used Methods:**
    * `add(E e)`: Appends the specified element to the end of this list.
    * `get(int index)`: Returns the element at the specified position in this list.
    * `remove(Object o)`: Removes the first occurrence of the specified element from this list, if it is present.
    * `size()`: Returns the number of elements in this list.

-   **CopyOnWriteArraySet**
    A thread-safe variant of `HashSet` that uses a `CopyOnWriteArrayList` internally. Similar performance characteristics to `CopyOnWriteArrayList` (read-heavy optimization).
    ```java
    import java.util.concurrent.CopyOnWriteArraySet;

    CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
    copyOnWriteArraySet.add(1); // Adds element
    boolean contains = copyOnWriteArraySet.contains(1); // Checks if element exists
    ```
    **Most Used Methods:**
    * `add(E e)`: Adds the specified element to this set if it is not already present.
    * `remove(Object o)`: Removes the specified element from this set if it is present.
    * `contains(Object o)`: Returns `true` if this set contains the specified element.
    * `size()`: Returns the number of elements in this set (its cardinality).

### **10. Blocking Queues**

Blocking queues support operations that wait for the queue to become non-empty when retrieving an element, and wait for space to become available in the queue when storing an element. Useful for producer-consumer patterns.

-   **ArrayBlockingQueue**
    A bounded blocking queue backed by an array. Once created, the capacity cannot be changed.
    ```java
    import java.util.concurrent.ArrayBlockingQueue;

    ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
    try {
        arrayBlockingQueue.put(1); // Adds element (blocks if full)
        int element = arrayBlockingQueue.take(); // Removes element (blocks if empty)
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    boolean offered = arrayBlockingQueue.offer(2); // Adds element (returns false if full)
    int polled = arrayBlockingQueue.poll(); // Removes element (returns null if empty)
    ```
    **Most Used Methods:**
    * `put(E e)`: Inserts the specified element at the tail of this queue, waiting if the queue is full.
    * `take()`: Retrieves and removes the head of this queue, waiting if the queue is empty.
    * `offer(E e)`: Inserts the specified element at the tail of this queue if it is possible to do so immediately without exceeding the queue's capacity.
    * `offer(E e, long timeout, TimeUnit unit)`: Inserts the specified element at the tail of this queue, waiting up to the specified wait time if necessary for space to become available.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `poll(long timeout, TimeUnit unit)`: Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.
    * `size()`: Returns the number of elements in this queue.

-   **LinkedBlockingQueue**
    An optionally bounded blocking queue backed by linked nodes. Capacity can be specified, or it can be unbounded.
    ```java
    import java.util.concurrent.LinkedBlockingQueue;

    LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(); // Unbounded
    // LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(10); // Bounded
    try {
        linkedBlockingQueue.put(1); // Adds element (blocks if full, if bounded)
        int element = linkedBlockingQueue.take(); // Removes element (blocks if empty)
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    ```
    **Most Used Methods:**
    * `put(E e)`: Inserts the specified element at the tail of this queue, waiting if the queue is full.
    * `take()`: Retrieves and removes the head of this queue, waiting if the queue is empty.
    * `offer(E e)`: Inserts the specified element at the tail of this queue if it is possible to do so immediately without exceeding the queue's capacity.
    * `offer(E e, long timeout, TimeUnit unit)`: Inserts the specified element at the tail of this queue, waiting up to the specified wait time if necessary for space to become available.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `poll(long timeout, TimeUnit unit)`: Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.
    * `size()`: Returns the number of elements in this queue.

-   **PriorityBlockingQueue**
    An unbounded blocking queue that orders elements according to their natural ordering, or by a `Comparator` provided at queue construction time. It's similar to `PriorityQueue` but with blocking operations.
    ```java
    import java.util.concurrent.PriorityBlockingQueue;

    PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
    try {
        priorityBlockingQueue.put(3);
        priorityBlockingQueue.put(1);
        priorityBlockingQueue.put(2);
        int element = priorityBlockingQueue.take(); // Removes and returns the highest priority element (returns 1)
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    ```
    **Most Used Methods:**
    * `put(E e)`: Inserts the specified element into this priority queue.
    * `take()`: Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
    * `offer(E e)`: Inserts the specified element into this priority queue.
    * `offer(E e, long timeout, TimeUnit unit)`: Inserts the specified element into this priority queue.
    * `poll()`: Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
    * `poll(long timeout, TimeUnit unit)`: Retrieves and removes the head of this queue, waiting up to the specified wait time if necessary for an element to become available.
    * `peek()`: Retrieves, but does not remove, the head of this queue, or returns `null` if this queue is empty.
    * `size()`: Returns the number of elements in this queue.
