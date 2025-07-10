### 15\. Implement a Singly Linked List

First, we define the `Node` class. Then, we implement a `SinglyLinkedList` class with methods for `insert` (at head, tail, or specific position), `delete` (by value or position), `search`, and `print`.

```java
public class LinkedListOperations {

    // --- Node Class for Singly Linked List ---
    static class SLLNode {
        int data;
        SLLNode next;

        SLLNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // --- Singly Linked List Class ---
    static class SinglyLinkedList {
        SLLNode head;

        public SinglyLinkedList() {
            this.head = null;
        }

        // Insert at the beginning (head)
        public void insertAtHead(int data) {
            SLLNode newNode = new SLLNode(data);
            newNode.next = head;
            head = newNode;
        }

        // Insert at the end (tail)
        public void insertAtTail(int data) {
            SLLNode newNode = new SLLNode(data);
            if (head == null) {
                head = newNode;
                return;
            }
            SLLNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        // Insert at a specific position (0-indexed)
        public void insertAtPosition(int data, int position) {
            if (position < 0) {
                System.out.println("Position cannot be negative.");
                return;
            }
            if (position == 0) {
                insertAtHead(data);
                return;
            }

            SLLNode newNode = new SLLNode(data);
            SLLNode current = head;
            int currentPos = 0;

            while (current != null && currentPos < position - 1) {
                current = current.next;
                currentPos++;
            }

            if (current == null) {
                System.out.println("Position out of bounds.");
                return;
            }

            newNode.next = current.next;
            current.next = newNode;
        }

        // Delete by value
        public void deleteByValue(int data) {
            if (head == null) {
                System.out.println("List is empty. Cannot delete.");
                return;
            }

            // If head contains the value
            if (head.data == data) {
                head = head.next;
                return;
            }

            SLLNode current = head;
            while (current.next != null && current.next.data != data) {
                current = current.next;
            }

            if (current.next == null) {
                System.out.println(data + " not found in the list.");
            } else {
                current.next = current.next.next;
            }
        }

        // Delete by position (0-indexed)
        public void deleteAtPosition(int position) {
            if (head == null) {
                System.out.println("List is empty. Cannot delete.");
                return;
            }
            if (position < 0) {
                System.out.println("Position cannot be negative.");
                return;
            }
            if (position == 0) {
                head = head.next;
                return;
            }

            SLLNode current = head;
            int currentPos = 0;
            while (current != null && currentPos < position - 1) {
                current = current.next;
                currentPos++;
            }

            if (current == null || current.next == null) {
                System.out.println("Position out of bounds. Cannot delete.");
                return;
            }
            current.next = current.next.next;
        }

        // Search for an element
        public boolean search(int data) {
            SLLNode current = head;
            while (current != null) {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        // Print the list
        public void printList() {
            SLLNode current = head;
            if (current == null) {
                System.out.println("List is empty.");
                return;
            }
            while (current != null) {
                System.out.print(current.data + " -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    // --- Doubly Linked List Node ---
    static class DLLNode {
        int data;
        DLLNode prev;
        DLLNode next;

        DLLNode(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    // --- Doubly Linked List Class ---
    static class DoublyLinkedList {
        DLLNode head;
        DLLNode tail;

        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        // Insert at the beginning
        public void insertAtHead(int data) {
            DLLNode newNode = new DLLNode(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        }

        // Insert at the end
        public void insertAtTail(int data) {
            DLLNode newNode = new DLLNode(data);
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }

        // Insert at a specific position (0-indexed)
        public void insertAtPosition(int data, int position) {
            if (position < 0) {
                System.out.println("Position cannot be negative.");
                return;
            }
            if (position == 0) {
                insertAtHead(data);
                return;
            }

            DLLNode newNode = new DLLNode(data);
            DLLNode current = head;
            int currentPos = 0;

            while (current != null && currentPos < position) {
                current = current.next;
                currentPos++;
            }

            if (current == null) { // Insert at end if position is out of bounds
                insertAtTail(data);
            } else {
                newNode.next = current;
                newNode.prev = current.prev;
                if (current.prev != null) {
                    current.prev.next = newNode;
                } else { // Inserting at what was the head
                    head = newNode;
                }
                current.prev = newNode;
            }
        }

        // Delete by value
        public void deleteByValue(int data) {
            if (head == null) {
                System.out.println("List is empty. Cannot delete.");
                return;
            }

            DLLNode current = head;
            while (current != null && current.data != data) {
                current = current.next;
            }

            if (current == null) {
                System.out.println(data + " not found in the list.");
                return;
            }

            if (current.prev != null) {
                current.prev.next = current.next;
            } else { // Deleting the head
                head = current.next;
            }

            if (current.next != null) {
                current.next.prev = current.prev;
            } else { // Deleting the tail
                tail = current.prev;
            }
        }

        // Delete by position (0-indexed)
        public void deleteAtPosition(int position) {
            if (head == null) {
                System.out.println("List is empty. Cannot delete.");
                return;
            }
            if (position < 0) {
                System.out.println("Position cannot be negative.");
                return;
            }

            DLLNode current = head;
            int currentPos = 0;
            while (current != null && currentPos < position) {
                current = current.next;
                currentPos++;
            }

            if (current == null) {
                System.out.println("Position out of bounds. Cannot delete.");
                return;
            }

            if (current.prev != null) {
                current.prev.next = current.next;
            } else { // Deleting the head
                head = current.next;
            }

            if (current.next != null) {
                current.next.prev = current.prev;
            } else { // Deleting the tail
                tail = current.prev;
            }
        }


        // Search for an element
        public boolean search(int data) {
            DLLNode current = head;
            while (current != null) {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        // Print the list (forward)
        public void printListForward() {
            DLLNode current = head;
            if (current == null) {
                System.out.println("List is empty.");
                return;
            }
            while (current != null) {
                System.out.print(current.data + " <-> ");
                current = current.next;
            }
            System.out.println("null");
        }

        // Print the list (backward)
        public void printListBackward() {
            DLLNode current = tail;
            if (current == null) {
                System.out.println("List is empty.");
                return;
            }
            while (current != null) {
                System.out.print(current.data + " <-> ");
                current = current.prev;
            }
            System.out.println("null");
        }
    }


    public static void main(String[] args) {
        // --- Singly Linked List Example ---
        System.out.println("--- Singly Linked List Operations ---");
        SinglyLinkedList sll = new SinglyLinkedList();
        sll.insertAtHead(3);
        sll.insertAtTail(5);
        sll.insertAtHead(1);
        sll.insertAtPosition(4, 2); // Insert 4 at index 2
        sll.printList(); // Expected: 1 -> 3 -> 4 -> 5 -> null

        System.out.println("Searching for 4: " + sll.search(4)); // Expected: true
        System.out.println("Searching for 10: " + sll.search(10)); // Expected: false

        sll.deleteByValue(4);
        sll.printList(); // Expected: 1 -> 3 -> 5 -> null

        sll.deleteAtPosition(0);
        sll.printList(); // Expected: 3 -> 5 -> null

        sll.deleteByValue(5);
        sll.printList(); // Expected: 3 -> null

        sll.deleteByValue(3);
        sll.printList(); // Expected: List is empty.
        System.out.println();

        // --- Doubly Linked List Example ---
        System.out.println("--- Doubly Linked List Operations ---");
        DoublyLinkedList dll = new DoublyLinkedList();
        dll.insertAtHead(30);
        dll.insertAtTail(50);
        dll.insertAtHead(10);
        dll.insertAtPosition(40, 2); // Insert 40 at index 2
        dll.printListForward(); // Expected: 10 <-> 30 <-> 40 <-> 50 <-> null
        dll.printListBackward(); // Expected: 50 <-> 40 <-> 30 <-> 10 <-> null

        System.out.println("Searching for 40: " + dll.search(40)); // Expected: true
        System.out.println("Searching for 100: " + dll.search(100)); // Expected: false

        dll.deleteByValue(40);
        dll.printListForward(); // Expected: 10 <-> 30 <-> 50 <-> null

        dll.deleteAtPosition(0); // Delete head (10)
        dll.printListForward(); // Expected: 30 <-> 50 <-> null

        dll.deleteByValue(50); // Delete tail (50)
        dll.printListForward(); // Expected: 30 <-> null

        dll.deleteByValue(30);
        dll.printListForward(); // Expected: List is empty.
        System.out.println();
    }
}
```

-----

### 16\. Reverse a Linked List

We'll implement both iterative and recursive approaches for singly linked lists.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Reverses a singly linked list iteratively.
     * @param head The head of the list to reverse.
     * @return The new head of the reversed list.
     */
    public static SLLNode reverseListIterative(SLLNode head) {
        SLLNode prev = null;
        SLLNode current = head;
        SLLNode nextTemp = null;

        while (current != null) {
            nextTemp = current.next; // Store next node
            current.next = prev;     // Reverse current node's pointer
            prev = current;          // Move prev to current node
            current = nextTemp;      // Move current to next node
        }
        return prev; // prev will be the new head
    }

    /**
     * Reverses a singly linked list recursively.
     * @param head The head of the list to reverse.
     * @return The new head of the reversed list.
     */
    public static SLLNode reverseListRecursive(SLLNode head) {
        // Base case: empty list or single node list
        if (head == null || head.next == null) {
            return head;
        }

        // Recursively reverse the rest of the list
        SLLNode restReversed = reverseListRecursive(head.next);

        // Make the next node point to the current node
        head.next.next = head;
        // Make the current node point to null (it will be the new tail)
        head.next = null;

        return restReversed; // The head of the reversed list
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Reverse a Linked List ---
        System.out.println("--- Reverse a Linked List ---");
        SinglyLinkedList sllToReverse = new SinglyLinkedList();
        sllToReverse.insertAtTail(1);
        sllToReverse.insertAtTail(2);
        sllToReverse.insertAtTail(3);
        sllToReverse.insertAtTail(4);
        System.out.print("Original List: ");
        sllToReverse.printList();

        // Iterative Reverse
        sllToReverse.head = reverseListIterative(sllToReverse.head);
        System.out.print("Reversed (Iterative): ");
        sllToReverse.printList(); // Expected: 4 -> 3 -> 2 -> 1 -> null

        // Reset for recursive reverse
        sllToReverse = new SinglyLinkedList();
        sllToReverse.insertAtTail(1);
        sllToReverse.insertAtTail(2);
        sllToReverse.insertAtTail(3);
        sllToReverse.insertAtTail(4);
        System.out.print("Original List (for Recursive): ");
        sllToReverse.printList();
        sllToReverse.head = reverseListRecursive(sllToReverse.head);
        System.out.print("Reversed (Recursive): ");
        sllToReverse.printList(); // Expected: 4 -> 3 -> 2 -> 1 -> null
        System.out.println();
    }
}
```

-----

### 17\. Detect Cycle in a Linked List

We'll use Floyd's Cycle-Finding Algorithm (Tortoise and Hare). A slow pointer moves one step at a time, and a fast pointer moves two steps at a time. If they meet, there's a cycle. If the fast pointer reaches `null` (or its `next` is `null`), there's no cycle.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Detects if a cycle exists in a singly linked list using Floyd's Cycle-Finding Algorithm.
     * @param head The head of the list.
     * @return true if a cycle is detected, false otherwise.
     */
    public static boolean hasCycle(SLLNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        SLLNode slow = head;
        SLLNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;        // Moves by 1
            fast = fast.next.next;   // Moves by 2

            if (slow == fast) {
                return true; // Cycle detected
            }
        }
        return false; // No cycle
    }

    /**
     * Finds the starting node of the cycle in a linked list.
     * Assumes a cycle exists.
     * @param head The head of the list.
     * @return The starting node of the cycle, or null if no cycle.
     */
    public static SLLNode detectCycleStart(SLLNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        SLLNode slow = head;
        SLLNode fast = head;
        boolean cycleDetected = false;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycleDetected = true;
                break;
            }
        }

        if (!cycleDetected) {
            return null; // No cycle
        }

        // If cycle detected, move slow to head and both pointers by 1 until they meet again
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // This is the start of the cycle
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Detect Cycle in a Linked List ---
        System.out.println("--- Detect Cycle in a Linked List ---");
        SinglyLinkedList cycleList = new SinglyLinkedList();
        SLLNode node1 = new SLLNode(1);
        SLLNode node2 = new SLLNode(2);
        SLLNode node3 = new SLLNode(3);
        SLLNode node4 = new SLLNode(4);
        SLLNode node5 = new SLLNode(5);

        cycleList.head = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2; // Create a cycle: 5 -> 2

        System.out.println("List has a cycle: " + hasCycle(cycleList.head)); // Expected: true
        SLLNode cycleStart = detectCycleStart(cycleList.head);
        System.out.println("Cycle starts at node with data: " + (cycleStart != null ? cycleStart.data : "N/A")); // Expected: 2

        SinglyLinkedList noCycleList = new SinglyLinkedList();
        noCycleList.insertAtTail(1);
        noCycleList.insertAtTail(2);
        noCycleList.insertAtTail(3);
        System.out.println("List has a cycle (no cycle): " + hasCycle(noCycleList.head)); // Expected: false
        System.out.println("Cycle starts at node with data (no cycle): " + (detectCycleStart(noCycleList.head) != null ? detectCycleStart(noCycleList.head).data : "N/A")); // Expected: N/A
        System.out.println();
    }
}
```

-----

### 18\. Merge Two Sorted Linked Lists

To merge two sorted linked lists, we'll create a new dummy head for the merged list. Then, we iterate through both input lists, comparing their current nodes and appending the smaller one to the merged list.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Merges two sorted singly linked lists into a new sorted list.
     * @param l1 The head of the first sorted list.
     * @param l2 The head of the second sorted list.
     * @return The head of the new merged sorted list.
     */
    public static SLLNode mergeTwoSortedLists(SLLNode l1, SLLNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        SLLNode dummyHead = new SLLNode(0); // Dummy node to simplify logic
        SLLNode currentMerged = dummyHead;

        while (l1 != null && l2 != null) {
            if (l1.data <= l2.data) {
                currentMerged.next = l1;
                l1 = l1.next;
            } else {
                currentMerged.next = l2;
                l2 = l2.next;
            }
            currentMerged = currentMerged.next;
        }

        // Append remaining nodes from either list
        if (l1 != null) {
            currentMerged.next = l1;
        } else if (l2 != null) {
            currentMerged.next = l2;
        }

        return dummyHead.next; // The actual head of the merged list
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Merge Two Sorted Linked Lists ---
        System.out.println("--- Merge Two Sorted Linked Lists ---");
        SinglyLinkedList list1 = new SinglyLinkedList();
        list1.insertAtTail(1);
        list1.insertAtTail(3);
        list1.insertAtTail(5);
        System.out.print("List 1: ");
        list1.printList();

        SinglyLinkedList list2 = new SinglyLinkedList();
        list2.insertAtTail(2);
        list2.insertAtTail(4);
        list2.insertAtTail(6);
        list2.insertAtTail(7);
        System.out.print("List 2: ");
        list2.printList();

        SLLNode mergedHead = mergeTwoSortedLists(list1.head, list2.head);
        SinglyLinkedList mergedList = new SinglyLinkedList();
        mergedList.head = mergedHead;
        System.out.print("Merged List: ");
        mergedList.printList(); // Expected: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null
        System.out.println();
    }
}
```

-----

### 19\. Find the Middle of a Linked List

We'll use the Tortoise and Hare approach again. The fast pointer moves two steps for every one step of the slow pointer. When the fast pointer reaches the end, the slow pointer will be at the middle.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Finds the middle node of a singly linked list.
     * If the list has an even number of nodes, it returns the second middle node.
     * @param head The head of the list.
     * @return The middle node, or null if the list is empty.
     */
    public static SLLNode findMiddle(SLLNode head) {
        if (head == null) {
            return null;
        }

        SLLNode slow = head;
        SLLNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // slow is at the middle
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Find the Middle of a Linked List ---
        System.out.println("--- Find the Middle of a Linked List ---");
        SinglyLinkedList middleListOdd = new SinglyLinkedList();
        middleListOdd.insertAtTail(1);
        middleListOdd.insertAtTail(2);
        middleListOdd.insertAtTail(3);
        middleListOdd.insertAtTail(4);
        middleListOdd.insertAtTail(5);
        System.out.print("List (Odd): ");
        middleListOdd.printList();
        SLLNode middleOdd = findMiddle(middleListOdd.head);
        System.out.println("Middle node data: " + (middleOdd != null ? middleOdd.data : "N/A")); // Expected: 3

        SinglyLinkedList middleListEven = new SinglyLinkedList();
        middleListEven.insertAtTail(1);
        middleListEven.insertAtTail(2);
        middleListEven.insertAtTail(3);
        middleListEven.insertAtTail(4);
        System.out.print("List (Even): ");
        middleListEven.printList();
        SLLNode middleEven = findMiddle(middleListEven.head);
        System.out.println("Middle node data: " + (middleEven != null ? middleEven.data : "N/A")); // Expected: 3 (second middle)
        System.out.println();
    }
}
```

-----

### 20\. Nth Node from End of Linked List

Again, a two-pointer approach is efficient. We move the `first` pointer `n` steps ahead. Then, we move both `first` and `second` pointers simultaneously until `first` reaches the end. When `first` is at the end, `second` will be at the Nth node from the end.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Finds the Nth node from the end of a singly linked list.
     * @param head The head of the list.
     * @param n The position from the end (1-indexed).
     * @return The Nth node from the end, or null if N is out of bounds.
     */
    public static SLLNode findNthFromEnd(SLLNode head, int n) {
        if (head == null || n <= 0) {
            return null;
        }

        SLLNode first = head;
        SLLNode second = head;

        // Move first pointer n steps ahead
        for (int i = 0; i < n; i++) {
            if (first == null) {
                return null; // N is greater than the length of the list
            }
            first = first.next;
        }

        // Move both pointers until first reaches the end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        return second;
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Nth Node from End of Linked List ---
        System.out.println("--- Nth Node from End of Linked List ---");
        SinglyLinkedList nthList = new SinglyLinkedList();
        nthList.insertAtTail(10);
        nthList.insertAtTail(20);
        nthList.insertAtTail(30);
        nthList.insertAtTail(40);
        nthList.insertAtTail(50);
        System.out.print("List: ");
        nthList.printList();

        SLLNode nthNode = findNthFromEnd(nthList.head, 2); // 2nd from end (40)
        System.out.println("2nd node from end: " + (nthNode != null ? nthNode.data : "N/A")); // Expected: 40

        nthNode = findNthFromEnd(nthList.head, 5); // 5th from end (10)
        System.out.println("5th node from end: " + (nthNode != null ? nthNode.data : "N/A")); // Expected: 10

        nthNode = findNthFromEnd(nthList.head, 6); // Out of bounds
        System.out.println("6th node from end: " + (nthNode != null ? nthNode.data : "N/A")); // Expected: N/A
        System.out.println();
    }
}
```

-----

### 21\. Remove Nth Node from End of Linked List

Similar to finding the Nth node, we use two pointers. The `first` pointer moves `n+1` steps ahead. Then, `first` and `second` move together. When `first` reaches the end, `second` will be at the node *before* the Nth node from the end, allowing us to delete the Nth node. We use a dummy head to handle cases where the head itself needs to be removed.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Removes the Nth node from the end of a singly linked list.
     * @param head The head of the list.
     * @param n The position from the end (1-indexed) to remove.
     * @return The head of the list after removal.
     */
    public static SLLNode removeNthFromEnd(SLLNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }

        SLLNode dummy = new SLLNode(0); // Dummy node to handle edge case of removing head
        dummy.next = head;

        SLLNode first = dummy;
        SLLNode second = dummy;

        // Move first pointer n + 1 steps ahead
        for (int i = 0; i <= n; i++) {
            if (first == null) { // N is greater than or equal to list length
                return head; // Or throw an exception
            }
            first = first.next;
        }

        // Move both pointers until first reaches the end
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        // Now second.next is the Nth node from the end, so remove it
        second.next = second.next.next;

        return dummy.next; // Return the actual head
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Remove Nth Node from End of Linked List ---
        System.out.println("--- Remove Nth Node from End of Linked List ---");
        SinglyLinkedList removeNthList = new SinglyLinkedList();
        removeNthList.insertAtTail(10);
        removeNthList.insertAtTail(20);
        removeNthList.insertAtTail(30);
        removeNthList.insertAtTail(40);
        removeNthList.insertAtTail(50);
        System.out.print("Original List: ");
        removeNthList.printList(); // Expected: 10 -> 20 -> 30 -> 40 -> 50 -> null

        // Remove 2nd from end (40)
        removeNthList.head = removeNthFromEnd(removeNthList.head, 2);
        System.out.print("After removing 2nd from end: ");
        removeNthList.printList(); // Expected: 10 -> 20 -> 30 -> 50 -> null

        // Remove 4th from end (10 - which is now the head)
        removeNthList.head = removeNthFromEnd(removeNthList.head, 4);
        System.out.print("After removing 4th from end: ");
        removeNthList.printList(); // Expected: 20 -> 30 -> 50 -> null

        // Remove 1st from end (50)
        removeNthList.head = removeNthFromEnd(removeNthList.head, 1);
        System.out.print("After removing 1st from end: ");
        removeNthList.printList(); // Expected: 20 -> 30 -> null

        // Remove 2nd from end (20 - which is now the head)
        removeNthList.head = removeNthFromEnd(removeNthList.head, 2);
        System.out.print("After removing 2nd from end: ");
        removeNthList.printList(); // Expected: 30 -> null

        // Remove 1st from end (30 - which is now the head)
        removeNthList.head = removeNthFromEnd(removeNthList.head, 1);
        System.out.print("After removing 1st from end: ");
        removeNthList.printList(); // Expected: List is empty.

        // Test with empty list or invalid N
        System.out.print("Removing from empty list (N=1): ");
        SinglyLinkedList emptyList = new SinglyLinkedList();
        emptyList.head = removeNthFromEnd(emptyList.head, 1);
        emptyList.printList(); // Expected: List is empty.
        System.out.println();
    }
}
```

-----

### 22\. Intersection of Two Linked Lists

To find the intersection point, several approaches exist. A common one is to calculate the lengths of both lists, then advance the longer list's pointer by the difference in lengths. After that, traverse both lists simultaneously; the first common node is the intersection.

```java
public class LinkedListOperations {
    // ... (SLLNode, SinglyLinkedList, DLLNode, DoublyLinkedList classes from above)

    /**
     * Finds the intersection node of two singly linked lists.
     * @param headA The head of the first list.
     * @param headB The head of the second list.
     * @return The intersection node, or null if no intersection.
     */
    public static SLLNode getIntersectionNode(SLLNode headA, SLLNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        // Calculate lengths of both lists
        int lenA = 0;
        SLLNode currentA = headA;
        while (currentA != null) {
            lenA++;
            currentA = currentA.next;
        }

        int lenB = 0;
        SLLNode currentB = headB;
        while (currentB != null) {
            lenB++;
            currentB = currentB.next;
        }

        // Reset pointers to head
        currentA = headA;
        currentB = headB;

        // Advance the longer list's pointer by the difference in lengths
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; i++) {
                currentA = currentA.next;
            }
        } else if (lenB > lenA) {
            for (int i = 0; i < lenB - lenA; i++) {
                currentB = currentB.next;
            }
        }

        // Traverse both lists simultaneously until they meet
        while (currentA != null && currentB != null) {
            if (currentA == currentB) {
                return currentA; // Intersection found
            }
            currentA = currentA.next;
            currentB = currentB.next;
        }

        return null; // No intersection
    }

    public static void main(String[] args) {
        // ... (previous main method code)

        // --- Intersection of Two Linked Lists ---
        System.out.println("--- Intersection of Two Linked Lists ---");

        // Create lists with an intersection
        SLLNode intersectionNode = new SLLNode(70);
        SLLNode commonNode2 = new SLLNode(80);
        SLLNode commonNode3 = new SLLNode(90);
        intersectionNode.next = commonNode2;
        commonNode2.next = commonNode3;

        SinglyLinkedList listInterA = new SinglyLinkedList();
        listInterA.insertAtTail(10);
        listInterA.insertAtTail(20);
        listInterA.head.next.next = intersectionNode; // List A: 10 -> 20 -> 70 -> 80 -> 90 -> null
        System.out.print("List A: ");
        listInterA.printList();

        SinglyLinkedList listInterB = new SinglyLinkedList();
        listInterB.insertAtTail(30);
        listInterB.insertAtTail(40);
        listInterB.insertAtTail(50);
        listInterB.head.next.next.next = intersectionNode; // List B: 30 -> 40 -> 50 -> 70 -> 80 -> 90 -> null
        System.out.print("List B: ");
        listInterB.printList();

        SLLNode inter = getIntersectionNode(listInterA.head, listInterB.head);
        System.out.println("Intersection Node Data: " + (inter != null ? inter.data : "N/A")); // Expected: 70

        // Create lists with no intersection
        SinglyLinkedList listNoInterA = new SinglyLinkedList();
        listNoInterA.insertAtTail(1);
        listNoInterA.insertAtTail(2);
        System.out.print("List No Inter A: ");
        listNoInterA.printList();

        SinglyLinkedList listNoInterB = new SinglyLinkedList();
        listNoInterB.insertAtTail(3);
        listNoInterB.insertAtTail(4);
        System.out.print("List No Inter B: ");
        listNoInterB.printList();

        SLLNode noInter = getIntersectionNode(listNoInterA.head, listNoInterB.head);
        System.out.println("Intersection Node Data (no intersection): " + (noInter != null ? noInter.data : "N/A")); // Expected: N/A
        System.out.println();
    }
}
```
