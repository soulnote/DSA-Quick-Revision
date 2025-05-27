
# **Copy List with Random Pointer**

Given a linked list where each node contains an additional **random** pointer which could point to any node in the list or `null`, create a **deep copy** of the list. The copied list should have all new nodes with the same values and the same structure of `next` and `random` pointers as the original.

---

## Example

Input:
`head = [[7,null],[13,0],[11,4],[10,2],[1,0]]`

Output:
A deep copy with the same structure:
`[[7,null],[13,0],[11,4],[10,2],[1,0]]`

---

## Intuition

1. For each original node, create its copy and insert it right after the original node, forming an interleaved list.

2. Assign the `random` pointers for the copied nodes using the interleaved structure.

3. Separate the copied nodes to form the new list and restore the original list to its initial structure.

---

## Code with Comments

```java
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        Node iter = head, next;

        // 1. Create copy nodes and interleave them with original nodes
        while (iter != null) {
            next = iter.next;
            Node copy = new Node(iter.val);  // Copy of current node
            iter.next = copy;                // Insert copy right after original
            copy.next = next;                // Link copy to next original node
            iter = next;
        }

        // 2. Assign random pointers for copied nodes
        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                iter.next.random = iter.random.next; // Copy's random = original.random's copy
            }
            iter = iter.next.next;  // Move to next original node
        }

        // 3. Separate copied list from original and restore original list
        iter = head;
        Node pseudoHead = new Node(0);
        Node copyIter = pseudoHead;

        while (iter != null) {
            next = iter.next.next;  // Next original node

            // Extract the copy node
            Node copy = iter.next;
            copyIter.next = copy;
            copyIter = copy;

            // Restore original list's next pointer
            iter.next = next;

            iter = next;
        }

        return pseudoHead.next;  // Head of deep copied list
    }
}
```
---
# **Find the Duplicate Number**

Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive. There is only one repeated number in `nums`. Return this repeated number.

You must solve the problem **without modifying the array** and using **only constant extra space**.

---

## Example

Input:
`nums = [1,3,4,2,2]`

Output:
`2`

---

## Intuition

* The array values can be interpreted as pointers to indices, which forms a linked list structure with a cycle due to the duplicate number.
* Use Floyd's Tortoise and Hare (Cycle Detection) algorithm to find the cycle's entry point.
* The entry point of the cycle corresponds to the duplicate number.

---

## Code with Comments

```java
class Solution {
    public int findDuplicate(int[] nums) {
        if (nums.length == 0)
            return 0;

        // Initialize slow and fast pointers
        int slow = nums[0];
        int fast = nums[0];

        // Move slow by 1 step and fast by 2 steps to find intersection point
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Reset fast to start to find the entrance of the cycle (duplicate number)
        fast = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        // slow (or fast) is now at the duplicate number
        return slow;
    }
}
```

---
# **LRU Cache**

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the `LRUCache` class:

* `LRUCache(int capacity)` Initializes the cache with positive size `capacity`.
* `int get(int key)` Returns the value of the key if it exists, otherwise returns `-1`.
* `void put(int key, int value)` Updates the value if the key exists, otherwise adds the key-value pair. If the cache exceeds capacity, evict the least recently used key.

Both `get` and `put` must run in **O(1)** average time complexity.

---

## Example

Input:
`["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]`
`[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]`

Output:
`[null, null, null, 1, null, -1, null, -1, 3, 4]`

---

## Intuition

* Use a **HashMap** for O(1) access to nodes by key.
* Use a **Doubly Linked List** to maintain the order of usage:

  * Most recently used node at the head.
  * Least recently used node at the tail.
* On `get`, move the accessed node to the head.
* On `put`, update node if present, else add new node to head.
* If capacity exceeded, remove node at tail (least recently used).

---

## Code with Comments

```java
import java.util.HashMap;

class LRUCache {

    // Node class for doubly linked list
    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> map; // key to node mapping
    private int capacity; // max capacity of cache
    private Node head, tail; // dummy head and tail for doubly linked list

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0); // dummy head
        tail = new Node(0, 0); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1; // key not found
        }
        Node node = map.get(key);
        remove(node);          // remove from current position
        insertToHead(node);    // move to head (most recently used)
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; 
            remove(node);       // remove current node
            insertToHead(node); // re-insert at head
        } else {
            if (map.size() == capacity) {
                // remove least recently used node
                map.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertToHead(newNode);
        }
    }

    // Remove node from linked list
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert node right after head
    private void insertToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

---
# **LRU Cache**

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the `LRUCache` class:

* `LRUCache(int capacity)` Initializes the cache with positive size `capacity`.
* `int get(int key)` Returns the value of the key if it exists, otherwise returns `-1`.
* `void put(int key, int value)` Updates the value if the key exists, otherwise adds the key-value pair. If the cache exceeds capacity, evict the least recently used key.

Both `get` and `put` must run in **O(1)** average time complexity.

---

## Example

Input:
`["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]`
`[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]`

Output:
`[null, null, null, 1, null, -1, null, -1, 3, 4]`

---

## Intuition

* Use a **HashMap** for O(1) access to nodes by key.
* Use a **Doubly Linked List** to maintain the order of usage:

  * Most recently used node at the head.
  * Least recently used node at the tail.
* On `get`, move the accessed node to the head.
* On `put`, update node if present, else add new node to head.
* If capacity exceeded, remove node at tail (least recently used).

---

## Code with Comments

```java
import java.util.HashMap;

class LRUCache {

    // Node class for doubly linked list
    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> map; // key to node mapping
    private int capacity; // max capacity of cache
    private Node head, tail; // dummy head and tail for doubly linked list

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0); // dummy head
        tail = new Node(0, 0); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1; // key not found
        }
        Node node = map.get(key);
        remove(node);          // remove from current position
        insertToHead(node);    // move to head (most recently used)
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; 
            remove(node);       // remove current node
            insertToHead(node); // re-insert at head
        } else {
            if (map.size() == capacity) {
                // remove least recently used node
                map.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertToHead(newNode);
        }
    }

    // Remove node from linked list
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert node right after head
    private void insertToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

---
# **Merge k Sorted Lists**

You are given an array of `k` linked lists, each sorted in ascending order.

Merge all the linked lists into one sorted linked list and return it.

---

## Example

Input:
`lists = [[1,4,5], [1,3,4], [2,6]]`

Output:
`[1,1,2,3,4,4,5,6]`

Explanation:
The linked lists are:

```
[
  1->4->5,
  1->3->4,
  2->6
]
```

Merging them results in:
`1->1->2->3->4->4->5->6`

---

## Intuition

* Use a **min-heap (priority queue)** to efficiently get the smallest current node among all lists.
* Initially, insert the head of each non-empty list into the priority queue.
* Extract the smallest node from the queue, add it to the merged list, then insert its next node into the queue if it exists.
* Repeat until all nodes are processed.

This guarantees O(N log k) time complexity, where N is total nodes and k is number of lists.

---

## Code with Comments

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

import java.util.PriorityQueue;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(-1);  // Dummy head for merged list
        ListNode prev = dummy;

        // Min-heap to store nodes, sorted by node value
        PriorityQueue<ListNode> minPQ = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Add the head of each list to the priority queue
        for (ListNode node : lists) {
            if (node != null) {
                minPQ.offer(node);
            }
        }

        // Process nodes until heap is empty
        while (!minPQ.isEmpty()) {
            ListNode curr = minPQ.poll();  // Get node with smallest value
            prev.next = curr;              // Add to merged list
            prev = prev.next;              // Move pointer
            
            if (curr.next != null) {       // If next node exists, add to heap
                minPQ.offer(curr.next);
            }
        }

        return dummy.next;  // Return merged list head
    }
}
```

---
# **Reverse Nodes in k-Group**

Given the head of a linked list, reverse the nodes of the list `k` at a time, and return the modified list.

* Only nodes themselves may be changed, not the node values.
* If the number of nodes isn't a multiple of `k`, the remaining nodes at the end should stay as-is.

---

## Example

**Input:**
`head = [1,2,3,4,5], k = 2`
**Output:**
`[2,1,4,3,5]`

![image](https://assets.leetcode.com/uploads/2020/10/03/reverse_ex1.jpg)
---

## Intuition

1. First, count the total number of nodes.
2. For every group of `k` nodes:

   * Reverse the group in-place.
   * Reconnect the reversed group back to the list.
3. If fewer than `k` nodes remain at the end, keep them unchanged.

The approach ensures in-place reversal using constant extra space.

---

## Code with Comments

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    
    // Helper function to count the length of the list
    static int lengthOfLinkedList(ListNode head) {
        int length = 0;
        while(head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || head.next == null) return head;

        int length = lengthOfLinkedList(head); // Count total nodes
        
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        
        ListNode pre = dummyHead;
        ListNode cur, nex;

        // Reverse in groups of k
        while(length >= k) {
            cur = pre.next;
            nex = cur.next;
            // Reverse nodes in the current group
            for(int i = 1; i < k; i++) {
                cur.next = nex.next;
                nex.next = pre.next;
                pre.next = nex;
                nex = cur.next;
            }
            pre = cur;        // Move pre to the end of the reversed group
            length -= k;      // Decrease count by k
        }
        return dummyHead.next;  // Return new head
    }
}
```

---

