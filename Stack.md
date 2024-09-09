# Stack Implementation

Here’s a simple implementation of a **Stack** in Java using arrays, which is the most basic way to implement a stack:

### Stack Implementation Using Arrays

```java
class Stack {
    private int[] stack;
    private int top;
    private int maxSize;

    // Constructor to initialize stack
    public Stack(int size) {
        stack = new int[size];
        maxSize = size;
        top = -1; // Empty stack
    }

    // Push operation (adds element to the stack)
    public void push(int value) {
        if (isFull()) {
            System.out.println("Stack is full! Cannot push " + value);
        } else {
            stack[++top] = value; // Increment top and insert the value
            System.out.println("Pushed " + value);
        }
    }

    // Pop operation (removes and returns the top element)
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty! Cannot pop.");
            return -1;
        } else {
            return stack[top--]; // Return the top element and decrement top
        }
    }

    // Peek operation (returns the top element without removing it)
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty! Nothing to peek.");
            return -1;
        } else {
            return stack[top];
        }
    }

    // Check if the stack is full
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Display all elements of the stack
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
        } else {
            System.out.print("Stack elements: ");
            for (int i = 0; i <= top; i++) {
                System.out.print(stack[i] + " ");
            }
            System.out.println();
        }
    }
}

public class StackDemo {
    public static void main(String[] args) {
        Stack stack = new Stack(5); // Create a stack of size 5

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top element is: " + stack.peek());

        stack.display();

        System.out.println("Popped element is: " + stack.pop());

        stack.display();
    }
}
```

### Key Operations:
1. **Push**: Adds an element to the top of the stack.
2. **Pop**: Removes and returns the top element from the stack.
3. **Peek**: Returns the top element without removing it.
4. **isFull**: Checks if the stack is full.
5. **isEmpty**: Checks if the stack is empty.
6. **Display**: Prints all the elements in the stack.

### Sample Output:

```
Pushed 10
Pushed 20
Pushed 30
Top element is: 30
Stack elements: 10 20 30 
Popped element is: 30
Stack elements: 10 20 
```

This implementation demonstrates how a basic stack operates using arrays, where the stack has a fixed size and supports the core stack operations (`push`, `pop`, `peek`).


# Java inbuilt Stack class

Java provides a built-in `Stack` class as part of the Java Collections Framework, located in the `java.util` package. This `Stack` class extends `Vector` and represents a last-in, first-out (LIFO) stack of objects. 

Here’s a quick guide on how to use the built-in `Stack` class:

### Basic Operations Using Java's `Stack` Class

1. **Import the Stack Class**
   ```java
   import java.util.Stack;
   ```

2. **Create a Stack Instance**
   ```java
   Stack<Integer> stack = new Stack<>();
   ```

3. **Push Operation**
   Adds an element to the top of the stack.
   ```java
   stack.push(10);
   stack.push(20);
   stack.push(30);
   ```

4. **Pop Operation**
   Removes and returns the top element of the stack.
   ```java
   int top = stack.pop();
   System.out.println("Popped element: " + top);
   ```

5. **Peek Operation**
   Returns the top element without removing it.
   ```java
   int top = stack.peek();
   System.out.println("Top element: " + top);
   ```

6. **Check if Stack is Empty**
   Checks if the stack is empty.
   ```java
   boolean isEmpty = stack.isEmpty();
   System.out.println("Is stack empty? " + isEmpty);
   ```

7. **Check the Size of the Stack**
   Gets the number of elements in the stack.
   ```java
   int size = stack.size();
   System.out.println("Stack size: " + size);
   ```

8. **Display Stack Elements**
   Prints all elements of the stack.
   ```java
   System.out.println("Stack elements: " + stack);
   ```

### Example Code

Here's a complete example demonstrating the use of the `Stack` class:

```java
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        // Push elements
        stack.push(10);
        stack.push(20);
        stack.push(30);

        // Peek at the top element
        System.out.println("Top element is: " + stack.peek());

        // Pop an element
        System.out.println("Popped element is: " + stack.pop());

        // Check if stack is empty
        System.out.println("Is stack empty? " + stack.isEmpty());

        // Display all elements
        System.out.println("Stack elements: " + stack);
        
        // Check stack size
        System.out.println("Stack size: " + stack.size());
    }
}
```

### Output

```
Top element is: 30
Popped element is: 30
Is stack empty? false
Stack elements: [10, 20]
Stack size: 2
```

### Notes
- The `Stack` class in Java is part of the Collections Framework but is considered somewhat outdated. For most new code, consider using `Deque` (specifically `ArrayDeque`) if you need a stack implementation.
- `Stack` is synchronized, which means it's thread-safe but can be slower compared to `ArrayDeque` if synchronization is not needed.

  ## ArrayDeque
  In Java, the `Deque` (Double-ended Queue) interface is part of the Java Collections Framework and represents a queue that allows insertion and removal of elements from both ends. The `Deque` interface extends `Queue` and provides methods to work with both ends of the queue.

### Common Implementations of `Deque`

- **`ArrayDeque`**: A resizable array implementation of the `Deque` interface. It is typically used as a stack or queue and does not have capacity limitations other than memory.
- **`LinkedList`**: Implements the `Deque` interface as well as other interfaces like `List`. It is a doubly-linked list that supports constant-time insertion or removal of elements from both ends.

### Basic Operations Using `ArrayDeque`

1. **Import the `ArrayDeque` Class**
   ```java
   import java.util.ArrayDeque;
   import java.util.Deque;
   ```

2. **Create a `Deque` Instance**
   ```java
   Deque<Integer> deque = new ArrayDeque<>();
   ```

3. **Add Elements**
   - **Add to the Front**:
     ```java
     deque.addFirst(10);
     ```
   - **Add to the End**:
     ```java
     deque.addLast(20);
     ```

4. **Remove Elements**
   - **Remove from the Front**:
     ```java
     int first = deque.removeFirst();
     ```
   - **Remove from the End**:
     ```java
     int last = deque.removeLast();
     ```

5. **Peek Elements**
   - **Peek at the Front**:
     ```java
     int first = deque.peekFirst();
     ```
   - **Peek at the End**:
     ```java
     int last = deque.peekLast();
     ```

6. **Check if Deque is Empty**
   ```java
   boolean isEmpty = deque.isEmpty();
   ```

7. **Display Deque Elements**
   ```java
   System.out.println("Deque elements: " + deque);
   ```

### Example Code

Here's a complete example demonstrating the use of the `ArrayDeque` class:

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DequeDemo {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();

        // Add elements to both ends
        deque.addFirst(10);
        deque.addLast(20);
        deque.addFirst(5);
        deque.addLast(25);

        // Display all elements
        System.out.println("Deque elements: " + deque);

        // Peek at the front and end
        System.out.println("Front element: " + deque.peekFirst());
        System.out.println("End element: " + deque.peekLast());

        // Remove elements from both ends
        System.out.println("Removed from front: " + deque.removeFirst());
        System.out.println("Removed from end: " + deque.removeLast());

        // Check if deque is empty
        System.out.println("Is deque empty? " + deque.isEmpty());

        // Display all elements after removals
        System.out.println("Deque elements after removals: " + deque);
    }
}
```

### Output

```
Deque elements: [5, 10, 20, 25]
Front element: 5
End element: 25
Removed from front: 5
Removed from end: 25
Is deque empty? false
Deque elements after removals: [10, 20]
```

### Notes
- **Performance**: `ArrayDeque` is generally faster than `LinkedList` for most operations due to its array-based implementation, which avoids the overhead of linked nodes.
- **Thread Safety**: `ArrayDeque` is not thread-safe. If you need thread-safe operations, consider using `ConcurrentLinkedDeque` or `Collections.synchronizedDeque`.

Using `Deque`, you can implement various data structures like stacks, queues, and double-ended queues efficiently.

# Interview questions and answers

### Common Stack Interview Questions and Answers

Stacks are a fundamental data structure with various applications in algorithms and computer science. Here are some common interview questions about stacks, along with detailed answers and code examples in Java.

---

#### **1. Implement a Stack Using an Array**

**Question**: Implement a basic stack using an array in Java and demonstrate basic operations like push, pop, and peek.

**Answer**:

Here’s how you can implement a stack using an array:

```java
class Stack {
    private int[] stack;
    private int top;
    private int maxSize;

    public Stack(int size) {
        stack = new int[size];
        maxSize = size;
        top = -1;
    }

    public void push(int value) {
        if (top == maxSize - 1) {
            System.out.println("Stack is full!");
        } else {
            stack[++top] = value;
        }
    }

    public int pop() {
        if (top == -1) {
            System.out.println("Stack is empty!");
            return -1;
        } else {
            return stack[top--];
        }
    }

    public int peek() {
        if (top == -1) {
            System.out.println("Stack is empty!");
            return -1;
        } else {
            return stack[top];
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }
    
    public boolean isFull() {
        return top == maxSize - 1;
    }
}

public class StackDemo {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top element is: " + stack.peek());

        System.out.println("Popped element is: " + stack.pop());

        System.out.println("Is stack empty? " + stack.isEmpty());
    }
}
```

**Explanation**: The stack implementation includes methods for pushing, popping, peeking, and checking if the stack is full or empty. 

---

#### **2. Reverse a String Using a Stack**

**Question**: Given a string, reverse it using a stack.

**Answer**:

Here’s a solution to reverse a string using a stack:

```java
import java.util.Stack;

public class ReverseString {
    public static String reverse(String str) {
        Stack<Character> stack = new Stack<>();
        
        // Push all characters of the string into the stack
        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }

        // Pop all characters from the stack and form the reversed string
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    public static void main(String[] args) {
        String str = "Hello, World!";
        System.out.println("Reversed string: " + reverse(str));
    }
}
```

**Explanation**: This approach uses a stack to reverse the characters of the string. Characters are pushed onto the stack and then popped off, which effectively reverses the order.

---

#### **3. Evaluate a Postfix Expression Using a Stack**

**Question**: Given a postfix expression, evaluate it using a stack. For example, evaluate the postfix expression `2 3 4 * +`.

**Answer**:

Here’s how you can evaluate a postfix expression:

```java
import java.util.Stack;

public class PostfixEvaluation {
    public static int evaluatePostfix(String expression) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (token.matches("\\d+")) {
                stack.push(Integer.parseInt(token));
            } else {
                int val2 = stack.pop();
                int val1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(val1 + val2);
                        break;
                    case "-":
                        stack.push(val1 - val2);
                        break;
                    case "*":
                        stack.push(val1 * val2);
                        break;
                    case "/":
                        stack.push(val1 / val2);
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String postfixExpression = "2 3 4 * +";
        System.out.println("Postfix evaluation result: " + evaluatePostfix(postfixExpression));
    }
}
```

**Explanation**: The algorithm uses a stack to evaluate the postfix expression. Numbers are pushed onto the stack, and operators cause the stack to pop and compute the result.

---

#### **4. Check for Balanced Parentheses**

**Question**: Given a string containing parentheses, check if the parentheses are balanced. For example, `{[()()]}` is balanced, but `[{(])}` is not.

**Answer**:

Here’s a method to check for balanced parentheses:

```java
import java.util.Stack;

public class BalancedParentheses {
    public static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            switch (ch) {
                case '(':
                case '[':
                case '{':
                    stack.push(ch);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                    break;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String expression = "{[()()]}";
        System.out.println("Is balanced? " + isBalanced(expression));
    }
}
```

**Explanation**: This algorithm uses a stack to ensure that every opening parenthesis has a corresponding closing parenthesis in the correct order.

---

#### **5. Implement a Stack Using Two Queues**

**Question**: Implement a stack using two queues.

**Answer**:

Here’s an implementation using two queues:

```java
import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueues {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public StackUsingQueues() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int value) {
        queue2.add(value);

        while (!queue1.isEmpty()) {
            queue2.add(queue1.poll());
        }

        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
    }

    public int pop() {
        if (queue1.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return queue1.poll();
    }

    public int peek() {
        if (queue1.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return queue1.peek();
    }

    public boolean isEmpty() {
        return queue1.isEmpty();
    }
    
    public static void main(String[] args) {
        StackUsingQueues stack = new StackUsingQueues();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top element is: " + stack.peek());
        System.out.println("Popped element is: " + stack.pop());
        System.out.println("Is stack empty? " + stack.isEmpty());
    }
}
```

**Explanation**: This approach uses two queues to simulate stack operations. By rotating elements between the queues, the stack behavior is achieved.

---

These questions cover fundamental stack operations, practical applications, and more complex scenarios. Mastering these concepts is crucial for technical interviews and real-world problem-solving.

