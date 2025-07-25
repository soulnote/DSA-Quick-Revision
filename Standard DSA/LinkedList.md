## implementation of a singly linked list in Java:

```java
class LinkedList {
    // Node class to represent each element in the linked list
    static class Node {
        int data;
        Node next;

        // Constructor
        Node(int data) {
            this.data = data;
            next = null;
        }
    }

    // Head of the linked list
    private Node head;

    // Method to insert a new node at the end of the linked list
    public void insert(int data) {
        Node newNode = new Node(data);

        // If the linked list is empty, set the new node as the head
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            // Traverse to the end of the list
            while (current.next != null) {
                current = current.next;
            }
            // Link the last node to the new node
            current.next = newNode;
        }
    }

    // Method to display all nodes in the linked list
    public void display() {
        if (head == null) {
            System.out.println("The list is empty.");
            return;
        }

        Node current = head;
        // Traverse the list and print each node's data
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Method to delete a node with a given key
    public void delete(int key) {
        // If the head is the node to be deleted
        if (head != null && head.data == key) {
            head = head.next; // Remove head
            return;
        }

        Node current = head;
        Node previous = null;

        // Traverse the list to find the node to delete
        while (current != null && current.data != key) {
            previous = current;
            current = current.next;
        }

        // If the node was not found
        if (current == null) {
            System.out.println("Key not found.");
            return;
        }

        // Unlink the node to be deleted
        previous.next = current.next;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);

        System.out.println("Linked List:");
        list.display();

        System.out.println("\nDeleting node with value 20:");
        list.delete(20);
        list.display();
    }
}
```

### Key methods:
1. **Insert**: Adds a node to the end of the list.
2. **Display**: Prints all the elements of the list.
3. **Delete**: Deletes a node by its value.

## Inbuilt java LinkedList class

Java provides an inbuilt `LinkedList` class as part of the Java Collections Framework. It implements the `List` and `Deque` interfaces, making it a doubly-linked list that can be used as both a list and a queue.

Here's an example of using Java's inbuilt `LinkedList`:

```java
import java.util.LinkedList;

public class InbuiltLinkedListExample {
    public static void main(String[] args) {
        // Creating a LinkedList of integers
        LinkedList<Integer> list = new LinkedList<>();

        // Adding elements to the LinkedList
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        // Displaying the elements of the LinkedList
        System.out.println("LinkedList: " + list);

        // Adding an element at the first position
        list.addFirst(5);
        System.out.println("After adding 5 at the start: " + list);

        // Adding an element at the last position
        list.addLast(50);
        System.out.println("After adding 50 at the end: " + list);

        // Removing the first element
        list.removeFirst();
        System.out.println("After removing the first element: " + list);

        // Removing the last element
        list.removeLast();
        System.out.println("After removing the last element: " + list);

        // Getting the first element
        System.out.println("First element: " + list.getFirst());

        // Getting the last element
        System.out.println("Last element: " + list.getLast());
    }
}
```

### Key Features of Java’s Inbuilt `LinkedList`:
1. **Doubly Linked**: It is implemented as a doubly linked list, meaning each node has references to both the previous and next node.
2. **Implements `List` Interface**: So it can be used like a standard list.
3. **Implements `Deque` Interface**: So it supports queue operations like `addFirst()`, `addLast()`, `removeFirst()`, and `removeLast()`.

### Methods:
- `add(E element)`: Adds an element at the end of the list.
- `addFirst(E element)`: Inserts the element at the beginning.
- `addLast(E element)`: Inserts the element at the end.
- `removeFirst()`: Removes the first element.
- `removeLast()`: Removes the last element.
- `getFirst()`: Returns the first element.
- `getLast()`: Returns the last element.

The `LinkedList` class is versatile for both queue and list operations, making it a flexible choice for many use cases.

# common intrview questions

Here are some common LinkedList-related interview questions, along with brief explanations of what you could focus on while answering them:

### 1. **What is a LinkedList, and how does it differ from an ArrayList?**
   - **Key Focus**: 
     - **LinkedList** is a doubly-linked list where each node contains data and a reference to the next and previous nodes.
     - **ArrayList** is a dynamic array that stores elements in contiguous memory locations.
     - **Difference**:
       - Insertion/Deletion: LinkedList is better for frequent insertions and deletions (O(1) for insertion at the beginning or end).
       - Random access: ArrayList is faster for accessing elements by index (O(1) vs. O(n) for LinkedList).
       - Memory overhead: LinkedList has additional memory overhead due to storing next and previous node references.

### 2. **How do you detect a cycle in a LinkedList?**
   - **Key Focus**: Use **Floyd’s Cycle Detection Algorithm (Tortoise and Hare)**.
     - Maintain two pointers: a slow pointer that moves one step at a time and a fast pointer that moves two steps at a time.
     - If the two pointers meet, a cycle exists; otherwise, if the fast pointer reaches the end, the list has no cycle.
     
   ```java
   public boolean hasCycle(Node head) {
       if (head == null) return false;
       Node slow = head;
       Node fast = head.next;
       while (slow != fast) {
           if (fast == null || fast.next == null) return false;
           slow = slow.next;
           fast = fast.next.next;
       }
       return true; // Cycle detected
   }
   ```

### 3. **How do you find the middle element of a LinkedList?**
   - **Key Focus**: Use the **slow and fast pointer approach**:
     - Move one pointer (slow) by one node and another pointer (fast) by two nodes. When the fast pointer reaches the end, the slow pointer will be at the middle.
   
   ```java
   public Node findMiddle(Node head) {
       if (head == null) return null;
       Node slow = head;
       Node fast = head;
       while (fast != null && fast.next != null) {
           slow = slow.next;
           fast = fast.next.next;
       }
       return slow; // Slow is now at the middle
   }
   ```

### 4. **How do you reverse a LinkedList?**
   - **Key Focus**: Use three pointers: `prev`, `current`, and `next`. Traverse the list and reverse the links between nodes.
   
   ```java
   public Node reverseList(Node head) {
       Node prev = null;
       Node current = head;
       while (current != null) {
           Node next = current.next; // Save the next node
           current.next = prev; // Reverse the link
           prev = current; // Move prev forward
           current = next; // Move current forward
       }
       return prev; // New head of the reversed list
   }
   ```

### 5. **How do you remove the nth node from the end of a LinkedList?**
   - **Key Focus**: Use a two-pointer technique:
     - Move the first pointer `n` steps ahead, then move both pointers until the first pointer reaches the end. The second pointer will be at the nth node from the end.
   
   ```java
   public Node removeNthFromEnd(Node head, int n) {
       Node dummy = new Node(0);
       dummy.next = head;
       Node first = dummy;
       Node second = dummy;

       // Move first pointer `n` nodes ahead
       for (int i = 0; i <= n; i++) {
           first = first.next;
       }

       // Move both pointers
       while (first != null) {
           first = first.next;
           second = second.next;
       }

       // Remove nth node
       second.next = second.next.next;
       return dummy.next;
   }
   ```

### 6. **How would you merge two sorted linked lists into one sorted list?**
   - **Key Focus**: Compare the nodes of both lists and build the new sorted list node by node.
   
   ```java
   public Node mergeTwoLists(Node l1, Node l2) {
       Node dummy = new Node(0);
       Node current = dummy;
       
       while (l1 != null && l2 != null) {
           if (l1.data <= l2.data) {
               current.next = l1;
               l1 = l1.next;
           } else {
               current.next = l2;
               l2 = l2.next;
           }
           current = current.next;
       }
       
       // Add remaining nodes
       if (l1 != null) current.next = l1;
       if (l2 != null) current.next = l2;
       
       return dummy.next;
   }
   ```

### 7. **What is the time complexity of various operations on a LinkedList?**
   - **Key Focus**: 
     - **Insertions**: O(1) if inserting at the head or tail, O(n) if inserting in the middle.
     - **Deletions**: O(1) for removing head, O(n) for removing in the middle or end.
     - **Accessing Elements**: O(n), since you need to traverse the list to find an element by index.

### 8. **How do you check if two linked lists intersect?**
   - **Key Focus**: 
     - Traverse both lists to find their lengths. Move the pointer of the longer list by the difference in lengths, then traverse both lists together. If the nodes are the same at any point, the lists intersect.
     
   ```java
   public Node getIntersectionNode(Node headA, Node headB) {
       if (headA == null || headB == null) return null;
       Node a = headA;
       Node b = headB;
       
       // When a and b are null or meet at the intersection
       while (a != b) {
           a = (a == null) ? headB : a.next;
           b = (b == null) ? headA : b.next;
       }
       return a;
   }
   ```

### 9. **How do you remove duplicates from a LinkedList?**
   - **Key Focus**: Use a `HashSet` to track the seen elements and remove duplicates by adjusting the next pointers.
   
   ```java
   public void removeDuplicates(Node head) {
       if (head == null) return;
       Set<Integer> seen = new HashSet<>();
       Node current = head;
       Node prev = null;
       while (current != null) {
           if (seen.contains(current.data)) {
               prev.next = current.next; // Remove duplicate
           } else {
               seen.add(current.data);
               prev = current;
           }
           current = current.next;
       }
   }
   ```

### 10. **How would you implement a stack using a LinkedList?**
   - **Key Focus**: 
     - Use a singly linked list where the head is the top of the stack. Insertion and deletion both occur at the head for O(1) complexity.
     - Push: Insert at the head.
     - Pop: Remove from the head.

These questions will test your understanding of how LinkedLists work, both conceptually and practically, as well as your ability to implement and optimize algorithms involving them.


# Doubly linked list implementation

A **Doubly Linked List** is a type of linked list in which each node contains a reference to both the previous node and the next node. This allows traversal in both directions (forward and backward).

Here’s a basic implementation of a **Doubly Linked List** in Java:

### Doubly Linked List Implementation:

```java
class DoublyLinkedList {

    // Node class for the doubly linked list
    static class Node {
        int data;
        Node prev;
        Node next;

        // Constructor
        Node(int data) {
            this.data = data;
            prev = null;
            next = null;
        }
    }

    // Head (first node) of the doubly linked list
    private Node head;

    // Method to insert a new node at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);

        // If the list is empty, make the new node the head
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            // Traverse to the last node
            while (current.next != null) {
                current = current.next;
            }

            // Link the new node to the last node
            current.next = newNode;
            newNode.prev = current;  // Set the previous pointer of the new node
        }
    }

    // Method to insert a node at the beginning
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);

        // If the list is empty, make the new node the head
        if (head == null) {
            head = newNode;
        } else {
            // Set the new node's next pointer to the current head
            newNode.next = head;
            head.prev = newNode;  // Update the previous pointer of the current head
            head = newNode;  // Make the new node the head
        }
    }

    // Method to display the linked list in forward direction
    public void displayForward() {
        Node current = head;
        System.out.print("Forward: ");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Method to display the linked list in reverse direction
    public void displayBackward() {
        if (head == null) return;

        // Go to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        // Traverse backward from the last node
        System.out.print("Backward: ");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
        System.out.println();
    }

    // Method to delete a node from the doubly linked list
    public void delete(int data) {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = head;

        // If the node to be deleted is the head
        if (current.data == data) {
            head = current.next;  // Move the head pointer
            if (head != null) {
                head.prev = null;  // Set the previous pointer of the new head to null
            }
            return;
        }

        // Traverse the list to find the node to delete
        while (current != null && current.data != data) {
            current = current.next;
        }

        // If node was not found
        if (current == null) {
            System.out.println("Node not found.");
            return;
        }

        // Update the pointers to remove the node
        if (current.next != null) {
            current.next.prev = current.prev;
        }

        if (current.prev != null) {
            current.prev.next = current.next;
        }
    }

    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();

        // Inserting nodes at the end
        dll.insertAtEnd(10);
        dll.insertAtEnd(20);
        dll.insertAtEnd(30);

        // Displaying list forward and backward
        dll.displayForward();
        dll.displayBackward();

        // Inserting nodes at the beginning
        dll.insertAtBeginning(5);
        dll.displayForward();

        // Deleting a node
        dll.delete(20);
        dll.displayForward();
        dll.displayBackward();
    }
}
```

### Key Points:

1. **Node Structure**:
   - Each node contains three fields: `data`, `prev` (points to the previous node), and `next` (points to the next node).

2. **Insert at End**:
   - Traverse to the last node and insert the new node by updating the `next` of the last node and the `prev` of the new node.

3. **Insert at Beginning**:
   - Insert a new node before the head by updating the `prev` pointer of the current head and setting the new node as the head.

4. **Delete Node**:
   - Traverse the list to find the node, then adjust the `next` and `prev` pointers of the neighboring nodes to remove the node from the list.

5. **Display Forward and Backward**:
   - **Forward**: Start from the head and traverse using the `next` pointer.
   - **Backward**: Start from the last node and traverse using the `prev` pointer.

### Advantages of Doubly Linked List:
- Efficient for **bidirectional traversal**.
- Insertion and deletion are easier and more flexible compared to singly linked lists, especially for nodes in the middle of the list since you can move backward with the `prev` pointer.

### Time Complexity:
- **Insertion/Deletion at head or tail**: O(1)
- **Insertion/Deletion at any other position**: O(n) for traversal to the node.
- **Traversal**: O(n) for traversing forward or backward.

  # Doubly linked list in java inbuilt class

  Java provides an inbuilt `LinkedList` class as part of the `java.util` package, and it can be used as a doubly linked list. It implements both the `List` and `Deque` interfaces, which means it supports operations such as adding elements to both ends, removing elements, and retrieving elements from both ends, among other features.

Here is an example of using Java’s inbuilt `LinkedList`:

```java
import java.util.LinkedList;

public class InbuiltDoublyLinkedListExample {
    public static void main(String[] args) {
        // Creating an inbuilt LinkedList (Doubly Linked List)
        LinkedList<Integer> list = new LinkedList<>();

        // Adding elements at the end
        list.add(10);
        list.add(20);
        list.add(30);

        // Adding an element at the start
        list.addFirst(5);

        // Adding an element at the end
        list.addLast(40);

        // Displaying the LinkedList
        System.out.println("LinkedList: " + list);

        // Accessing elements
        System.out.println("First element: " + list.getFirst());
        System.out.println("Last element: " + list.getLast());

        // Removing the first and last element
        list.removeFirst();
        list.removeLast();
        System.out.println("After removing first and last: " + list);

        // Inserting at a specific position
        list.add(1, 15);
        System.out.println("After adding 15 at index 1: " + list);

        // Removing an element by value
        list.remove(Integer.valueOf(20));
        System.out.println("After removing element with value 20: " + list);

        // Traversing through the list
        System.out.print("Traversing the LinkedList: ");
        for (int value : list) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
```

### Key Methods of `LinkedList`:

1. **Adding Elements**:
   - `add(E element)`: Adds an element to the end of the list.
   - `addFirst(E element)`: Inserts the element at the beginning of the list.
   - `addLast(E element)`: Inserts the element at the end of the list.
   - `add(int index, E element)`: Inserts the element at the specified index.

2. **Accessing Elements**:
   - `get(int index)`: Returns the element at the specified index.
   - `getFirst()`: Returns the first element.
   - `getLast()`: Returns the last element.

3. **Removing Elements**:
   - `remove()`: Removes the first element.
   - `removeFirst()`: Removes the first element.
   - `removeLast()`: Removes the last element.
   - `remove(int index)`: Removes the element at the specified index.
   - `remove(Object o)`: Removes the first occurrence of the specified element.

4. **Traversing**:
   - The `LinkedList` can be traversed using an enhanced for-loop or an iterator.

### Characteristics of Java's `LinkedList`:

1. **Doubly Linked**: Each node in the `LinkedList` has references to the next and previous nodes.
2. **Implements `List` and `Deque`**: Supports operations like insertion, deletion, and access from both ends.
3. **Efficient for Insertions and Deletions**: Adding or removing elements at the beginning or end of the list is O(1) in terms of time complexity.

Java’s `LinkedList` is versatile and commonly used when frequent insertions or deletions are required in both the front and back of the list.

# interview questions
Here are some common interview questions related to **Doubly Linked Lists**:

### 1. **What is a Doubly Linked List, and how does it differ from a Singly Linked List?**
   - **Key Focus**: 
     - A **Doubly Linked List** has nodes that contain three fields: `data`, a reference to the next node (`next`), and a reference to the previous node (`prev`).
     - A **Singly Linked List** contains only a reference to the next node, making traversal only possible in one direction.
     - Doubly Linked Lists can be traversed in both directions (forward and backward), while Singly Linked Lists can only be traversed forward.

### 2. **How do you reverse a Doubly Linked List?**
   - **Key Focus**: Traverse through the list, swapping the `next` and `prev` pointers for each node.
   
   ```java
   public Node reverseDoublyLinkedList(Node head) {
       Node current = head;
       Node temp = null;

       // Traverse the list and swap next and prev for each node
       while (current != null) {
           temp = current.prev;
           current.prev = current.next;
           current.next = temp;
           current = current.prev; // Move to the next node in original list
       }

       // Adjust head if list is not empty
       if (temp != null) {
           head = temp.prev;
       }

       return head;
   }
   ```

### 3. **How do you insert a node at the beginning of a Doubly Linked List?**
   - **Key Focus**: Create a new node, set its `next` to the current head, and adjust the `prev` pointer of the current head to the new node.
   
   ```java
   public void insertAtBeginning(Node head, int data) {
       Node newNode = new Node(data);

       // If list is empty, make the new node the head
       if (head == null) {
           head = newNode;
           return;
       }

       // Set the new node's next to the current head
       newNode.next = head;
       head.prev = newNode;  // Update the prev pointer of the current head
       head = newNode;       // Update the head to be the new node
   }
   ```

### 4. **How do you delete a node in a Doubly Linked List?**
   - **Key Focus**: Traverse the list to find the node, then adjust the `prev` and `next` pointers of the adjacent nodes to bypass the node to be deleted.
   
   ```java
   public void deleteNode(Node head, Node del) {
       // If head or del is null
       if (head == null || del == null) return;

       // If node to be deleted is the head
       if (head == del) {
           head = del.next;
       }

       // Change next only if the node to be deleted is NOT the last node
       if (del.next != null) {
           del.next.prev = del.prev;
       }

       // Change prev only if the node to be deleted is NOT the first node
       if (del.prev != null) {
           del.prev.next = del.next;
       }
   }
   ```

### 5. **How do you find the middle element of a Doubly Linked List?**
   - **Key Focus**: Use two pointers, one (`slow`) that moves one step at a time and another (`fast`) that moves two steps at a time. When the fast pointer reaches the end, the slow pointer will be at the middle.
   
   ```java
   public Node findMiddle(Node head) {
       if (head == null) return null;

       Node slow = head;
       Node fast = head;

       // Move fast pointer by 2 and slow pointer by 1
       while (fast != null && fast.next != null) {
           slow = slow.next;
           fast = fast.next.next;
       }

       return slow; // Slow is now at the middle
   }
   ```

### 6. **How would you implement a stack using a Doubly Linked List?**
   - **Key Focus**: Use a Doubly Linked List where the top of the stack is the head of the list.
     - **Push**: Insert an element at the beginning of the list.
     - **Pop**: Remove an element from the beginning of the list.
   
   ```java
   class StackUsingDoublyLinkedList {
       private Node head;
       
       public void push(int data) {
           Node newNode = new Node(data);
           if (head != null) {
               head.prev = newNode;
               newNode.next = head;
           }
           head = newNode;
       }
       
       public int pop() {
           if (head == null) {
               throw new RuntimeException("Stack underflow");
           }
           int data = head.data;
           head = head.next;
           if (head != null) {
               head.prev = null;
           }
           return data;
       }
   }
   ```

### 7. **How would you merge two sorted Doubly Linked Lists into one sorted list?**
   - **Key Focus**: Traverse through both lists and compare the data at each node, adding the smaller node to the merged list.
   
   ```java
   public Node mergeSortedLists(Node head1, Node head2) {
       if (head1 == null) return head2;
       if (head2 == null) return head1;

       if (head1.data <= head2.data) {
           head1.next = mergeSortedLists(head1.next, head2);
           head1.next.prev = head1;
           head1.prev = null;
           return head1;
       } else {
           head2.next = mergeSortedLists(head1, head2.next);
           head2.next.prev = head2;
           head2.prev = null;
           return head2;
       }
   }
   ```

### 8. **How do you detect if a Doubly Linked List is a palindrome?**
   - **Key Focus**: Use two pointers, one starting at the head and the other starting at the tail, and check if the data at both pointers match as you move inward.
   
   ```java
   public boolean isPalindrome(Node head) {
       if (head == null) return true;

       Node tail = head;

       // Move tail to the last node
       while (tail.next != null) {
           tail = tail.next;
       }

       // Compare elements from start and end
       while (head != tail && head.prev != tail) {
           if (head.data != tail.data) {
               return false;
           }
           head = head.next;
           tail = tail.prev;
       }

       return true;
   }
   ```

### 9. **How do you remove duplicates from a Doubly Linked List?**
   - **Key Focus**: Use a `HashSet` to track elements that have already been seen and remove duplicates by adjusting the pointers of adjacent nodes.
   
   ```java
   public void removeDuplicates(Node head) {
       if (head == null) return;

       Set<Integer> seen = new HashSet<>();
       Node current = head;

       while (current != null) {
           if (seen.contains(current.data)) {
               // Remove the current node
               current.prev.next = current.next;
               if (current.next != null) {
                   current.next.prev = current.prev;
               }
           } else {
               seen.add(current.data);
           }
           current = current.next;
       }
   }
   ```

### 10. **What are the advantages and disadvantages of Doubly Linked Lists over Singly Linked Lists?**
   - **Advantages**:
     - Easier to traverse in both directions (forward and backward).
     - Easier to delete a node since we have access to the previous node as well.
   - **Disadvantages**:
     - More memory usage due to the extra `prev` pointer in each node.
     - More complex to implement and maintain compared to singly linked lists.

These questions test not only the candidate’s understanding of how doubly linked lists work but also their ability to think algorithmically and solve problems related to linked lists.
