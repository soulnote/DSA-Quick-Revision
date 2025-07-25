# Queue implementation

In Java, a queue can be implemented in various ways depending on the specific needs of the application. Here are a few common implementations:

### 1. **Using LinkedList**
`LinkedList` is a versatile implementation of the `Queue` interface, providing methods to enqueue (add elements) and dequeue (remove elements) efficiently.

```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        // Enqueue
        queue.add("Alice");
        queue.add("Bob");
        queue.add("Charlie");

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());  // Removes and returns the head of the queue

        // Peek
        System.out.println("Head of the queue: " + queue.peek());  // Returns the head of the queue without removing it

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### 2. **Using PriorityQueue**
`PriorityQueue` is a queue that orders its elements according to their natural ordering or by a comparator provided at queue construction time.

```java
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>();

        // Enqueue
        queue.add(5);
        queue.add(1);
        queue.add(3);

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());  // Returns the smallest element

        // Peek
        System.out.println("Head of the queue: " + queue.peek());  // Returns the smallest element

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### 3. **Using ArrayDeque**
`ArrayDeque` is a resizable array implementation of the `Deque` interface, which can be used as a queue.

```java
import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayDequeExample {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();

        // Enqueue
        queue.add("John");
        queue.add("Jane");
        queue.add("Doe");

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());  // Removes and returns the head of the queue

        // Peek
        System.out.println("Head of the queue: " + queue.peek());  // Returns the head of the queue without removing it

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### Key Methods
- `add(E e)`: Adds the specified element to the end of the queue. Throws `IllegalStateException` if the element cannot be added.
- `offer(E e)`: Adds the specified element to the end of the queue. Returns `false` if the element cannot be added.
- `poll()`: Retrieves and removes the head of the queue, or returns `null` if the queue is empty.
- `peek()`: Retrieves, but does not remove, the head of the queue, or returns `null` if the queue is empty.

### Choosing the Implementation
- **`LinkedList`**: Suitable for queues that require fast insertion and removal of elements.
- **`PriorityQueue`**: Useful if you need to process elements based on priority.
- **`ArrayDeque`**: Generally preferred over `LinkedList` for implementing a queue due to its better performance in most cases.

## Inbubilt java Queue

In Java, the `Queue` interface is part of the `java.util` package and has several in-built implementations. Here’s a summary of the main implementations you can use directly:

### 1. **`LinkedList`**
`LinkedList` implements the `Queue` interface and provides a flexible and efficient queue implementation.

**Example:**
```java
import java.util.LinkedList;
import java.util.Queue;

public class LinkedListQueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        // Enqueue
        queue.add("Alice");
        queue.add("Bob");
        queue.add("Charlie");

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());

        // Peek
        System.out.println("Head of the queue: " + queue.peek());

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### 2. **`PriorityQueue`**
`PriorityQueue` provides a priority-based queue where elements are ordered according to their natural ordering or a provided comparator.

**Example:**
```java
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>();

        // Enqueue
        queue.add(5);
        queue.add(1);
        queue.add(3);

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());

        // Peek
        System.out.println("Head of the queue: " + queue.peek());

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### 3. **`ArrayDeque`**
`ArrayDeque` is a resizable array implementation of the `Deque` interface, which can be used as a queue. It is generally preferred over `LinkedList` for queues due to its performance benefits.

**Example:**
```java
import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayDequeQueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();

        // Enqueue
        queue.add("John");
        queue.add("Jane");
        queue.add("Doe");

        // Dequeue
        System.out.println("Dequeued: " + queue.poll());

        // Peek
        System.out.println("Head of the queue: " + queue.peek());

        // Print all elements
        System.out.println("Queue contents: " + queue);
    }
}
```

### Common Methods in `Queue` Interface
- **`boolean add(E e)`**: Adds the specified element to the end of the queue. Throws `IllegalStateException` if the element cannot be added.
- **`boolean offer(E e)`**: Adds the specified element to the end of the queue. Returns `true` if the element is added successfully.
- **`E remove()`**: Retrieves and removes the head of the queue. Throws `NoSuchElementException` if the queue is empty.
- **`E poll()`**: Retrieves and removes the head of the queue, or returns `null` if the queue is empty.
- **`E peek()`**: Retrieves, but does not remove, the head of the queue, or returns `null` if the queue is empty.
- **`E element()`**: Retrieves, but does not remove, the head of the queue. Throws `NoSuchElementException` if the queue is empty.

### Choosing the Right Implementation
- **`LinkedList`**: Good for general-purpose queues with flexible operations.
- **`PriorityQueue`**: Ideal for scenarios requiring priority-based ordering of elements.
- **`ArrayDeque`**: Preferred for high-performance queue operations with minimal overhead.

# Interview questions and answers 

Certainly! Here are some common interview questions related to queues in Java, along with their answers:

### 1. **What is a Queue in Java?**

**Answer:**
A `Queue` in Java is a data structure that represents a collection of elements in a first-in, first-out (FIFO) order. It supports operations such as adding elements to the end, removing elements from the front, and peeking at the front element without removing it. The `Queue` interface in Java is part of the `java.util` package and is implemented by various classes such as `LinkedList`, `PriorityQueue`, and `ArrayDeque`.

### 2. **What is the difference between `offer()` and `add()` methods in a Queue?**

**Answer:**
- **`offer(E e)`**: Adds the specified element to the queue and returns `true` if the element was added successfully. It returns `false` if the queue is full and cannot accept more elements.
- **`add(E e)`**: Adds the specified element to the queue and returns `true`. It throws an `IllegalStateException` if the queue is full and cannot accept more elements.

**Example:**
```java
Queue<String> queue = new LinkedList<>();
queue.offer("Alice"); // returns true
queue.add("Bob");    // returns true

// When the queue is full
Queue<String> boundedQueue = new ArrayDeque<>(2);
boundedQueue.add("A");
boundedQueue.add("B");
// boundedQueue.add("C"); // Throws IllegalStateException
```

### 3. **Explain the `poll()` and `remove()` methods in a Queue.**

**Answer:**
- **`poll()`**: Retrieves and removes the head of the queue, or returns `null` if the queue is empty.
- **`remove()`**: Retrieves and removes the head of the queue, but throws a `NoSuchElementException` if the queue is empty.

**Example:**
```java
Queue<String> queue = new LinkedList<>();
queue.add("Alice");
queue.add("Bob");

System.out.println(queue.poll()); // Output: Alice
System.out.println(queue.poll()); // Output: Bob
System.out.println(queue.poll()); // Output: null (queue is empty)

// Using remove()
Queue<String> queue2 = new LinkedList<>();
queue2.add("Alice");
queue2.add("Bob");

System.out.println(queue2.remove()); // Output: Alice
System.out.println(queue2.remove()); // Output: Bob
System.out.println(queue2.remove()); // Throws NoSuchElementException (queue is empty)
```

### 4. **What is the difference between `peek()` and `element()` methods in a Queue?**

**Answer:**
- **`peek()`**: Retrieves, but does not remove, the head of the queue. Returns `null` if the queue is empty.
- **`element()`**: Retrieves, but does not remove, the head of the queue. Throws a `NoSuchElementException` if the queue is empty.

**Example:**
```java
Queue<String> queue = new LinkedList<>();
queue.add("Alice");
queue.add("Bob");

System.out.println(queue.peek());    // Output: Alice
System.out.println(queue.element()); // Output: Alice

Queue<String> emptyQueue = new LinkedList<>();
System.out.println(emptyQueue.peek());    // Output: null
System.out.println(emptyQueue.element()); // Throws NoSuchElementException
```

### 5. **How would you implement a queue using two stacks?**

**Answer:**
You can implement a queue using two stacks by using one stack for enqueue operations and the other stack for dequeue operations. When dequeuing, if the second stack is empty, you transfer all elements from the first stack to the second stack. This way, the oldest elements end up on top of the second stack and can be removed.

**Example:**
```java
import java.util.Stack;

class QueueUsingStacks {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public void enqueue(int item) {
        stack1.push(item);
    }

    public int dequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return stack2.pop();
    }
}

public class Main {
    public static void main(String[] args) {
        QueueUsingStacks queue = new QueueUsingStacks();
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println(queue.dequeue()); // Output: 1
        System.out.println(queue.dequeue()); // Output: 2
    }
}
```

### 6. **What is a circular queue and how does it differ from a linear queue?**

**Answer:**
A **circular queue** is a linear data structure where the last position is connected back to the first position to form a circle. It uses a fixed-size array and is efficient in terms of space utilization because it reuses the empty slots created by dequeue operations.

In contrast, a **linear queue** does not wrap around, and once elements are dequeued, the space is wasted if not shifted. 

**Example of Circular Queue Implementation:**
```java
class CircularQueue {
    private int[] queue;
    private int front, rear, size, capacity;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(int item) {
        if (size == capacity) {
            throw new RuntimeException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public int dequeue() {
        if (size == 0) {
            throw new RuntimeException("Queue is empty");
        }
        int item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }
}

public class Main {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(5);
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println(queue.dequeue()); // Output: 1
        queue.enqueue(3);
        System.out.println(queue.dequeue()); // Output: 2
    }
}
```

### 7. **How would you use `PriorityQueue` in Java?**

**Answer:**
`PriorityQueue` is a queue that orders its elements based on their natural ordering or by a comparator. It is used when you need elements to be processed according to their priority rather than FIFO order.

**Example:**
```java
import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(10);
        pq.add(5);
        pq.add(20);

        // Elements are ordered by their natural ordering
        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // Output: 5, 10, 20
        }
    }
}
```

### 8. **What is the time complexity of enqueue and dequeue operations in different queue implementations?**

**Answer:**
- **`LinkedList`**: Both `enqueue` (add) and `dequeue` (poll) operations have a time complexity of O(1).
- **`ArrayDeque`**: Both `enqueue` and `dequeue` operations have a time complexity of O(1).
- **`PriorityQueue`**: The `enqueue` operation (offer) has a time complexity of O(log n), while the `dequeue` operation (poll) also has a time complexity of O(log n) due to the need to maintain heap properties.

### 9. **What are some real-world use cases for queues?**

**Answer:**
- **Task Scheduling**: Managing tasks or processes in a round-robin manner.
- **Print Job Management**: Handling print jobs sent to a printer.
- **Message Queuing**: Handling messages in distributed systems and asynchronous communication.
- **Breadth-First Search (BFS)**: Traversing graphs or trees level by level.

### 10. **What are the potential pitfalls of using a Queue and how can they be avoided?**

**Answer:**
- **Queue Overflow**: When using a fixed-size queue, you might encounter overflow if the queue becomes full. This can be managed by checking the queue size before adding elements or by using a circular queue.
- **Queue Underflow**: Attempting to dequeue from an empty queue results in underflow. Always check if the queue is empty before attempting to dequeue.
- **Performance Issues**: For certain queue implementations (like `PriorityQueue`), operations might have higher time complexity. Choose the appropriate implementation based on performance requirements and usage patterns.

These questions cover various aspects of queues in Java, from basic concepts and operations to advanced implementations and use cases.
