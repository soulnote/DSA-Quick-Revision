# tree
### 1. Reverse Linked List (LeetCode #206)

**Intuition Summary**: To reverse a linked list, iterate through the list and change each node's `next` pointer to point to the previous node. Use three pointers (`prev`, `curr`, `next`) to keep track of nodes and avoid losing references. The process continues until the end, and the new head is the last node.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        // Initialize previous as null and current as head
        ListNode prev = null;
        ListNode curr = head;
        
        // Traverse the list
        while (curr != null) {
            // Store next node
            ListNode nextNode = curr.next;
            // Reverse the link
            curr.next = prev;
            // Move prev and curr one step forward
            prev = curr;
            curr = nextNode;
        }
        
        // Return new head
        return prev;
    }
}
```

---

### 2. Merge Two Sorted Lists (LeetCode #21)

**Intuition Summary**: Merge two sorted linked lists by comparing nodes from both lists and building a new list by selecting the smaller value. Use a dummy node to simplify the merging process, and adjust pointers as nodes are added. Continue until one or both lists are exhausted.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create a dummy node to serve as the head of the merged list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Compare nodes from both lists
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Attach remaining nodes from list1 or list2
        current.next = list1 != null ? list1 : list2;
        
        // Return the merged list
        return dummy.next;
    }
}
```

---

### 3. Add Two Numbers (LeetCode #2)

**Intuition Summary**: Add two numbers represented as linked lists by iterating through both lists, summing digits, and handling carry-over. Create a new linked list for the result, updating the carry for each addition. Process remaining digits and any final carry after one list ends.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Initialize dummy node and current pointer
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        
        // Process both lists
        while (l1 != null || l2 != null || carry != 0) {
            // Get values, use 0 if node is null
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            
            // Calculate sum and new carry
            int total = x + y + carry;
            carry = total / 10;
            int digit = total % 10;
            
            // Create new node with digit
            current.next = new ListNode(digit);
            current = current.next;
            
            // Move to next nodes if available
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        
        // Return result list
        return dummy.next;
    }
}
```

---

### 4. Linked List Cycle (LeetCode #141)

**Intuition Summary**: Detect a cycle in a linked list using Floyd's Tortoise and Hare algorithm. Use two pointers moving at different speeds (slow by 1, fast by 2). If they meet, a cycle exists; if fast reaches the end, there’s no cycle.

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        // Handle empty list or single node
        if (head == null || head.next == null) {
            return false;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // Move pointers until they meet or fast reaches end
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        
        // No cycle found
        return false;
    }
}
```

---

### 5. Remove Nth Node From End (LeetCode #19)

**Intuition Summary**: Remove the Nth node from the end by using two pointers with a gap of N nodes. Move both pointers together until the second reaches the end; the first points to the node before the one to delete. Adjust pointers to skip the target node.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Create dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        
        // Move second pointer n steps ahead
        for (int i = 0; i <= n; i++) {
            second = second.next;
        }
        
        // Move both pointers until second reaches end
        while (second != null) {
            first = first.next;
            second = second.next;
        }
        
        // Remove nth node
        first.next = first.next.next;
        
        // Return head
        return dummy.next;
    }
}
```

---

### 6. Intersection of Two Linked Lists (LeetCode #160)

**Intuition Summary**: Find the intersection of two linked lists by aligning their lengths. Traverse both lists, switching to the other list when one ends. If the pointers meet, they’re at the intersection point (or null if no intersection), due to equalized path lengths.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // Handle empty lists
        if (headA == null || headB == null) {
            return null;
        }
        
        // Initialize pointers
        ListNode a = headA;
        ListNode b = headB;
        
        // Traverse lists, switching heads when end is reached
        while (a != b) {
            a = a != null ? a.next : headB;
            b = b != null ? b.next : headA;
        }
        
        // Return intersection point or null
        return a;
    }
}
```
