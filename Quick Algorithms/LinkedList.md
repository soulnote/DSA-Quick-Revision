# Linked List 
## 📚 Theory + Java Code for All Important Linked List Questions

---

## **Linked List Basics - Theory ()**

Linked list ek **linear data structure** hai jisme nodes ek sequence mein connected hote hain. Har node do cheezein store karta hai:
1. **Data** - actual value
2. **Next** - next node ka reference

**Array vs Linked List:**

| Feature | Array | Linked List |
|---------|-------|-------------|
| Memory | Contiguous | Non-contiguous |
| Access | O(1) random | O(n) sequential |
| Insert/Delete | O(n) | O(1) (if position known) |
| Size | Fixed | Dynamic |

**Types of Linked Lists:**
1. **Singly Linked List** - sirf next pointer
2. **Doubly Linked List** - next + prev pointer
3. **Circular Linked List** - last node first node ko point kare

---

## **Basic Implementation**

```java
// Node Class
class ListNode {
    int val;
    ListNode next;
    
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
    
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

// Basic Linked List Operations
class LinkedList {
    ListNode head;
    
    // Insert at beginning
    public void insertAtBeginning(int val) {
        ListNode newNode = new ListNode(val);
        newNode.next = head;
        head = newNode;
    }
    
    // Insert at end
    public void insertAtEnd(int val) {
        ListNode newNode = new ListNode(val);
        if(head == null) {
            head = newNode;
            return;
        }
        
        ListNode current = head;
        while(current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    
    // Delete by value
    public void delete(int val) {
        if(head == null) return;
        
        if(head.val == val) {
            head = head.next;
            return;
        }
        
        ListNode current = head;
        while(current.next != null && current.next.val != val) {
            current = current.next;
        }
        
        if(current.next != null) {
            current.next = current.next.next;
        }
    }
    
    // Search
    public boolean search(int val) {
        ListNode current = head;
        while(current != null) {
            if(current.val == val) return true;
            current = current.next;
        }
        return false;
    }
    
    // Print list
    public void printList() {
        ListNode current = head;
        while(current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    // Get length
    public int length() {
        int count = 0;
        ListNode current = head;
        while(current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}
```

---

## **1. Reverse a Linked List** ⭐⭐⭐ (Most Asked)

### Theory
**Problem:** Linked list ko reverse karo (Iterative aur Recursive dono tarike se).

**Algorithm (Iterative):**
1. Three pointers: prev = null, current = head, next = null
2. current.next ko prev point karao
3. Prev aur current ko aage badhao

**Algorithm (Recursive):**
1. Base case: head ya head.next null hai to return head
2. Recursively rest list reverse karo
3. head.next.next = head karo
4. head.next = null karo

### Java Code
```java
class ReverseLinkedList {
    // Method 1: Iterative (Most common)
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while(current != null) {
            ListNode next = current.next;  // Store next
            current.next = prev;           // Reverse link
            prev = current;                // Move prev forward
            current = next;                // Move current forward
        }
        return prev;  // New head
    }
    
    // Method 2: Recursive
    public ListNode reverseListRecursive(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
    
    // Time: O(n), Space: O(1) iterative, O(n) recursive
}
```

---

## **2. Detect Cycle (Floyd's Cycle Detection)** ⭐⭐⭐

### Theory
**Problem:** Linked list mein cycle hai ya nahi detect karo.

**Algorithm (Floyd's Tortoise and Hare):**
1. Slow pointer (tortoise) - 1 step move kare
2. Fast pointer (hare) - 2 steps move kare
3. Agar cycle hai to fast aur slow kabhi na kabhi milenge
4. Agar fast null tak pahunch gaya to cycle nahi hai

**Cycle ka starting point find karna:**
1. Pehle detect karo ki cycle hai
2. Phir slow ko head pe rakh do
3. Dono ko 1-1 step move karo, jahan mile wahi cycle ka start hai

### Java Code
```java
class DetectCycle {
    // Detect if cycle exists
    public boolean hasCycle(ListNode head) {
        if(head == null) return false;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if(slow == fast) return true;
        }
        return false;
    }
    
    // Find cycle start node
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Detect cycle
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if(slow == fast) {
                // Find cycle start
                slow = head;
                while(slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }
    
    // Find cycle length
    public int cycleLength(ListNode head) {
        if(!hasCycle(head)) return 0;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Find meeting point
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) break;
        }
        
        // Count length
        int length = 0;
        ListNode current = slow;
        do {
            current = current.next;
            length++;
        } while(current != slow);
        
        return length;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **3. Find Middle of Linked List** ⭐⭐

### Theory
**Problem:** Linked list ka middle node find karo.

**Algorithm (Tortoise and Hare):**
1. Slow pointer 1 step, fast pointer 2 steps move kare
2. Jab fast null ho ya fast.next null ho, slow middle pe hoga

**Odd length:** Middle exactly middle node  
**Even length:** First middle node (or second, depends on requirement)

### Java Code
```java
class MiddleOfList {
    // Method 1: Two pointers (Fast & Slow)
    public ListNode findMiddle(ListNode head) {
        if(head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // Method 2: First middle in even length
    public ListNode findFirstMiddle(ListNode head) {
        if(head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while(fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **4. Remove Nth Node from End** ⭐⭐

### Theory
**Problem:** End se Nth node remove karo.

**Algorithm (One pass):**
1. Dummy node banao (edge cases handle karne ke liye)
2. Fast pointer ko n+1 steps aage badhao
3. Dono pointers ko move karo jab tak fast null na ho jaye
4. Slow pointer ke next ko skip karo

### Java Code
```java
class RemoveNthFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // Move fast n+1 steps ahead
        for(int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // Move both until fast reaches end
        while(fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Remove nth node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    // Alternative: Find length first
    public ListNode removeNthFromEndLength(ListNode head, int n) {
        int length = 0;
        ListNode current = head;
        while(current != null) {
            length++;
            current = current.next;
        }
        
        int target = length - n;
        if(target == 0) return head.next;
        
        current = head;
        for(int i = 0; i < target - 1; i++) {
            current = current.next;
        }
        current.next = current.next.next;
        
        return head;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **5. Merge Two Sorted Lists** ⭐⭐

### Theory
**Problem:** Do sorted linked lists ko merge karke ek sorted list banao.

**Algorithm:**
1. Dummy node banao (result list ka head)
2. Dono lists compare karo, chota node result mein add karo
3. Ek list khatam ho jaye to dusri list ke bache nodes attach karo

### Java Code
```java
class MergeTwoLists {
    // Method 1: Iterative
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while(list1 != null && list2 != null) {
            if(list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // Attach remaining nodes
        if(list1 != null) current.next = list1;
        if(list2 != null) current.next = list2;
        
        return dummy.next;
    }
    
    // Method 2: Recursive
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        if(list1 == null) return list2;
        if(list2 == null) return list1;
        
        if(list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }
    
    // Time: O(m + n), Space: O(1) iterative, O(m+n) recursive
}
```

---

## **6. Palindrome Linked List** ⭐⭐⭐

### Theory
**Problem:** Check karo ki linked list palindrome hai ya nahi.

**Algorithm:**
1. Middle of list find karo
2. Second half reverse karo
3. First half aur reversed second half compare karo
4. (Optional) List ko wapas original restore karo

### Java Code
```java
class PalindromeList {
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;
        
        // Step 1: Find middle
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Step 2: Reverse second half
        ListNode secondHalf = reverse(slow);
        ListNode firstHalf = head;
        
        // Step 3: Compare
        while(secondHalf != null) {
            if(firstHalf.val != secondHalf.val) return false;
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        
        return true;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **7. Intersection of Two Linked Lists** ⭐⭐

### Theory
**Problem:** Do linked lists ka intersection point find karo (Y-shaped).

**Algorithm:**
1. Dono lists ki length calculate karo
2. Longer list ko difference se aage badhao
3. Dono pointers ko move karo, jahan mile wahi intersection hai

**Alternative (Elegant):**
1. Pointer A list1 se start kare, pointer B list2 se
2. Jab A null ho to list2 pe shift karo, B null ho to list1 pe
3. Dono same node pe milenge (intersection ya null)

### Java Code
```java
class IntersectionOfLists {
    // Method 1: Length difference
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = getLength(headA);
        int lenB = getLength(headB);
        
        // Move longer list ahead
        while(lenA > lenB) {
            headA = headA.next;
            lenA--;
        }
        while(lenB > lenA) {
            headB = headB.next;
            lenB--;
        }
        
        // Find intersection
        while(headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        
        return headA;
    }
    
    // Method 2: Two pointers (More elegant)
    public ListNode getIntersectionNodeElegant(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;
        
        ListNode a = headA;
        ListNode b = headB;
        
        while(a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }
        
        return a;
    }
    
    private int getLength(ListNode head) {
        int length = 0;
        while(head != null) {
            length++;
            head = head.next;
        }
        return length;
    }
    
    // Time: O(m + n), Space: O(1)
}
```

---

## **8. Linked List Cycle II (Start of Cycle)** ⭐⭐

### Theory
**Problem:** Cycle ka starting point find karo.

**Algorithm:**
1. Floyd's cycle detection use karo
2. Jab slow aur fast mile, slow ko head pe rakh do
3. Dono ko 1 step move karo, jahan mile wahi cycle start hai

**Math Proof:**
- Distance from head to cycle start = x
- Distance from cycle start to meeting point = y
- Cycle length = c
- Fast ne slow se 2x distance travel kiya
- Equation solve karo to milega x = remaining distance

### Java Code
```java
class CycleStart {
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Detect cycle
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if(slow == fast) {
                // Find cycle start
                slow = head;
                while(slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        
        return null;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **9. Remove Duplicates from Sorted List** ⭐

### Theory
**Problem:** Sorted linked list se duplicates remove karo.

**Algorithm:**
1. Current pointer head se start karo
2. Jab current aur current.next same ho to skip karo
3. Nahi to aage badho

### Java Code
```java
class RemoveDuplicates {
    // Remove duplicates (keep one)
    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        
        while(current != null && current.next != null) {
            if(current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return head;
    }
    
    // Remove all duplicates (no duplicates left)
    public ListNode deleteAllDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode current = head;
        
        while(current != null) {
            // Skip duplicates
            while(current.next != null && current.val == current.next.val) {
                current = current.next;
            }
            
            if(prev.next == current) {
                prev = prev.next;
            } else {
                prev.next = current.next;
            }
            current = current.next;
        }
        
        return dummy.next;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **10. Add Two Numbers** ⭐⭐

### Theory
**Problem:** Do linked lists ko add karo (digits reverse order mein hain).

**Example:** 
```
List1: 2 -> 4 -> 3 (represents 342)
List2: 5 -> 6 -> 4 (represents 465)
Result: 7 -> 0 -> 8 (represents 807)
```

**Algorithm:**
1. Carry variable maintain karo
2. Dono lists ka sum + carry calculate karo
3. Naya node banao sum % 10 se
4. Carry = sum / 10

### Java Code
```java
class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;
        
        while(l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            
            if(l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    // Time: O(max(m, n)), Space: O(max(m, n))
}
```

---

## **11. Rotate List** ⭐⭐

### Theory
**Problem:** Linked list ko right side se k positions rotate karo.

**Algorithm:**
1. Length calculate karo
2. k = k % length (actual rotations)
3. Last node ko head se connect karo (circular banake)
4. Naya head dhundho (length - k steps)
5. Naya head se pehle node ka next null karo

### Java Code
```java
class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k == 0) return head;
        
        // Find length and last node
        int length = 1;
        ListNode last = head;
        while(last.next != null) {
            length++;
            last = last.next;
        }
        
        // Actual rotations needed
        k = k % length;
        if(k == 0) return head;
        
        // Make it circular
        last.next = head;
        
        // Find new head (length - k steps from head)
        int stepsToNewHead = length - k;
        ListNode newTail = head;
        for(int i = 1; i < stepsToNewHead; i++) {
            newTail = newTail.next;
        }
        
        ListNode newHead = newTail.next;
        newTail.next = null;
        
        return newHead;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **12. Reverse Nodes in k-Group** ⭐⭐⭐ (Hard)

### Theory
**Problem:** Har k group ke nodes ko reverse karo.

**Algorithm:**
1. Count k nodes group mein hain ya nahi
2. Agar k nodes hain to unhe reverse karo
3. Recursively remaining list process karo
4. Groups ko connect karo

### Java Code
```java
class ReverseKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k == 1) return head;
        
        // Check if k nodes exist
        ListNode current = head;
        int count = 0;
        while(current != null && count < k) {
            current = current.next;
            count++;
        }
        
        if(count == k) {
            // Reverse k nodes
            ListNode prev = null;
            ListNode curr = head;
            ListNode next = null;
            count = 0;
            
            while(curr != null && count < k) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
                count++;
            }
            
            // Recursively reverse remaining
            head.next = reverseKGroup(curr, k);
            return prev;
        }
        
        return head;
    }
    
    // Iterative approach (more complex but O(1) space)
    public ListNode reverseKGroupIterative(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;
        
        while(true) {
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if(kthNode == null) break;
            
            ListNode nextGroupStart = kthNode.next;
            ListNode prev = nextGroupStart;
            ListNode curr = prevGroupEnd.next;
            
            // Reverse k nodes
            while(curr != nextGroupStart) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            ListNode temp = prevGroupEnd.next;
            prevGroupEnd.next = kthNode;
            prevGroupEnd = temp;
        }
        
        return dummy.next;
    }
    
    private ListNode getKthNode(ListNode start, int k) {
        ListNode current = start;
        for(int i = 0; i < k; i++) {
            if(current == null) return null;
            current = current.next;
        }
        return current;
    }
    
    // Time: O(n), Space: O(n/k) recursion stack
}
```

---

## **13. Copy List with Random Pointer** ⭐⭐⭐

### Theory
**Problem:** Deep copy karo list ka jisme har node ke paas random pointer bhi hai.

**Algorithm (3-pass):**
1. Har node ke baad copy node insert karo
2. Random pointers set karo
3. Original aur copy list separate karo

### Java Code
```java
class Node {
    int val;
    Node next;
    Node random;
    
    Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

class CopyRandomList {
    public Node copyRandomList(Node head) {
        if(head == null) return null;
        
        // Pass 1: Create copy nodes and insert after original
        Node current = head;
        while(current != null) {
            Node copy = new Node(current.val);
            copy.next = current.next;
            current.next = copy;
            current = copy.next;
        }
        
        // Pass 2: Set random pointers
        current = head;
        while(current != null) {
            if(current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }
        
        // Pass 3: Separate lists
        Node dummy = new Node(0);
        Node copyCurrent = dummy;
        current = head;
        
        while(current != null) {
            copyCurrent.next = current.next;
            copyCurrent = copyCurrent.next;
            current.next = copyCurrent.next;
            current = current.next;
        }
        
        return dummy.next;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **14. Sort List (Merge Sort on Linked List)** ⭐⭐⭐

### Theory
**Problem:** Linked list ko sort karo in O(n log n) time.

**Algorithm (Merge Sort):**
1. Middle find karo (slow-fast pointer)
2. List ko do halves mein divide karo
3. Recursively dono halves sort karo
4. Sorted halves ko merge karo

### Java Code
```java
class SortList {
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;
        
        // Find middle
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while(fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split list
        prev.next = null;
        
        // Sort both halves
        ListNode left = sortList(head);
        ListNode right = sortList(slow);
        
        // Merge sorted halves
        return merge(left, right);
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        if(l1 != null) current.next = l1;
        if(l2 != null) current.next = l2;
        
        return dummy.next;
    }
    
    // Time: O(n log n), Space: O(log n) recursion stack
}
```

---

## **15. Odd Even Linked List** ⭐⭐

### Theory
**Problem:** Odd index nodes ko pehle, even index nodes ko baad mein arrange karo.

**Algorithm:**
1. Odd pointer (head) aur even pointer (head.next)
2. EvenHead store karo
3. Odd.next = odd.next.next, even.next = even.next.next
4. End mein odd.next = evenHead

### Java Code
```java
class OddEvenList {
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null) return head;
        
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        
        while(even != null && even.next != null) {
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        
        odd.next = evenHead;
        return head;
    }
    
    // Time: O(n), Space: O(1)
}
```

---


## **Complexity Analysis Cheat Sheet**

### **Time Complexity Patterns:**

| Pattern | Time | Example |
|---------|------|---------|
| Single pass traversal | O(n) | Search, length, print |
| Two pointers (same speed) | O(n) | Remove duplicates |
| Two pointers (different speed) | O(n) | Middle, cycle detection |
| Nested loops (same list) | O(n²) | Brute force palindrome |
| Divide and conquer | O(n log n) | Merge sort |
| Multiple passes | O(n) | Copy with random pointer |

### **Space Complexity Patterns:**

| Pattern | Space | Example |
|---------|-------|---------|
| In-place (no extra memory) | O(1) | Reverse, cycle detection |
| Recursion | O(n) | Reverse recursive, sort list |
| Hash map | O(n) | Copy list (alternative) |
| New list creation | O(n) | Add two numbers |

---

## **Interview Tips**

1. **Dummy node use karo** - Head change karne wali problems mein dummy node bohot helpful hai
   ```java
   ListNode dummy = new ListNode(0);
   dummy.next = head;
   ```

2. **Null pointer exception se bacho** - Hamesha check karo `current != null` before `current.next`

3. **Two pointers technique master karo** - 80% problems is se solve ho jati hain
   - Fast-Slow (cycle, middle)
   - Prev-Current (reverse)
   - Left-Right (palindrome)

4. **Edge cases hamesha test karo:**
   - Empty list (head == null)
   - Single node
   - Two nodes
   - k > length (rotate list)
   - n == length (remove nth)

5. **Draw karo diagram** - Linked list problems mein diagram banake solve karo, bahut help milti hai

6. **Recursion vs Iterative:**
   - Recursion elegant hota hai lekin O(n) space leta hai
   - Iterative thoda complex but O(1) space mein hota hai

7. **Common pitfalls:**
   - Update pointers sahi order mein karo
   - Temporary variable store karna mat bhoolna
   - Cycle detect karte time fast.next null check karo

---


## 🎯 Linked List Core Concepts

**Linked List = Nodes connected by pointers/references**
- **Singly Linked List:** Next pointer only
- **Doubly Linked List:** Next and prev pointers
- **Circular Linked List:** Last node points to head

**Common Techniques:**
- **Two Pointers (Slow/Fast):** Cycle detection, middle element
- **Dummy Node:** Edge cases handle karne ke liye (empty list, head change)
- **Reversal:** In-place reversal using prev/curr/next pointers
- **Recursion:** Reverse printing, merge lists

**Node structure:**
```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
```

---

## 📋 20 Hard Linked List Problems

---

### 1. Reverse Linked List
**Problem:** Singly linked list reverse karo.

**Approach:** Iterative - prev, curr, next pointers. Recursive bhi ho sakta hai.

```java
// Iterative (Easy but fundamental)
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    
    while (curr != null) {
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}

// Recursive
public ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseListRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}
```

>  Iterative mein prev null se start karo. Har step mein curr ka next prev kar do, phir prev aur curr aage badhao. Recursive mein pehle last node tak jao, phir wapas aate hue links reverse karo.

**Time:** O(n), **Space:** O(1) iterative, O(n) recursive

---

### 2. Detect Cycle (Floyd's Cycle Detection)
**Problem:** Linked list mein cycle hai ya nahi?

**Approach:** Slow (1 step) and fast (2 steps) pointers. Agar cycle hai toh fast kabhi slow ko catch karega.

```java
public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

>  Slow ek step, fast do step chalega. Cycle hai toh fast slow ko kabhi na kabhi pakad lega. Race track mein fast runner slow runner ko lap kar deta hai, waise hi.

**Time:** O(n), **Space:** O(1)

---

### 3. Find Cycle Start Node
**Problem:** Cycle ka starting node dhundho.

**Approach:** Pehle slow-fast se cycle detect karo. Phir slow ko head pe le jao, dono ek step se chaloge - jaha milenge wahi cycle start.

```java
public ListNode detectCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    
    // Find meeting point
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) break;
    }
    
    // No cycle
    if (fast == null || fast.next == null) return null;
    
    // Find cycle start
    slow = head;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
}
```

>  Meeting point milne ke baad, slow ko head pe le jao. Phir dono ek step se chalo. Meeting point hi cycle ka start hoga. Math proof hai - distance head to start = distance meeting to start.

**Time:** O(n), **Space:** O(1)

---

### 4. Find Middle of Linked List
**Problem:** Middle node return karo. Even length mein second middle.

**Approach:** Slow-fast pointers. Slow ek step, fast do step. Fast end pe pahunchega tab slow middle pe hoga.

```java
public ListNode middleNode(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

>  Fast jab end pe pahunch jayega, tab slow middle pe hoga. Even length mein fast null pe rukta hai, odd length mein fast.next null pe.

**Time:** O(n), **Space:** O(1)

---

### 5. Remove Nth Node from End
**Problem:** Last se nth node remove karo.

**Approach:** Dummy node + two pointers. First pointer ko n steps aage badhao, phir dono ko tab tak chalayo jab tak first null na ho jaye.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode first = dummy;
    ListNode second = dummy;
    
    // Move first ahead by n+1 steps
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove nth node
    second.next = second.next.next;
    return dummy.next;
}
```

>  Dummy node use karo taaki head remove karne mein problem na ho. First pointer ko n+1 steps aage karo, phir dono chalte raho. Jab first end pe pahunche, second pointer nth node ke just pehle hoga.

**Time:** O(n), **Space:** O(1)

---

### 6. Merge Two Sorted Lists
**Problem:** Do sorted linked lists ko merge karo.

**Approach:** Dummy node + compare nodes, chhota wala attach karo.

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    
    // Attach remaining nodes
    if (l1 != null) curr.next = l1;
    if (l2 != null) curr.next = l2;
    
    return dummy.next;
}
```

>  Do lists mein se chhota value wala node uthao, result list mein attach karo. Ek list khatam ho jaye toh remaining list directly attach kar do.

**Time:** O(m + n), **Space:** O(1)

---

### 7. Merge K Sorted Lists
**Problem:** K sorted lists merge karo.

**Approach 1:** Min-heap (priority queue). **Approach 2:** Divide and conquer (merge pairs recursively).

```java
// Min-Heap approach
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    
    PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
    
    for (ListNode node : lists) {
        if (node != null) pq.offer(node);
    }
    
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    while (!pq.isEmpty()) {
        ListNode smallest = pq.poll();
        curr.next = smallest;
        curr = curr.next;
        
        if (smallest.next != null) {
            pq.offer(smallest.next);
        }
    }
    return dummy.next;
}

// Divide and Conquer approach (Better for large K)
public ListNode mergeKListsDC(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    return mergeHelper(lists, 0, lists.length - 1);
}

private ListNode mergeHelper(ListNode[] lists, int left, int right) {
    if (left == right) return lists[left];
    int mid = left + (right - left) / 2;
    ListNode l1 = mergeHelper(lists, left, mid);
    ListNode l2 = mergeHelper(lists, mid + 1, right);
    return mergeTwoLists(l1, l2);
}
```

>  Min-heap mein saare lists ke first nodes daalo. Heap se smallest node uthao, result mein attach karo, uski list ka next node heap mein daalo. DC approach mein lists ko pairs mein merge karte jao.

**Time:** O(N log K) where N = total nodes, **Space:** O(K) for heap

---

### 8. Reverse Nodes in k-Group
**Problem:** List ko k size ke groups mein reverse karo. Last group chhota ho toh waise hi rahega.

**Approach:** Recursion + reverse function. Pehle k nodes reverse karo, baaki list recursively handle karo.

```java
public ListNode reverseKGroup(ListNode head, int k) {
    if (head == null) return null;
    
    // Check if we have k nodes
    ListNode curr = head;
    int count = 0;
    while (curr != null && count < k) {
        curr = curr.next;
        count++;
    }
    
    if (count < k) return head;  // Less than k nodes, return as is
    
    // Reverse first k nodes
    ListNode prev = null;
    ListNode next = null;
    curr = head;
    for (int i = 0; i < k; i++) {
        next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    
    // head is now the last node of reversed group
    // Recursively reverse remaining list
    head.next = reverseKGroup(curr, k);
    
    return prev;
}
```

>  Pehle check karo ki k nodes hain ya nahi. Agar hain, toh pehle k nodes reverse karo. Phir head.next ko recursion result se connect karo. Return reversed group ka new head.

**Time:** O(n), **Space:** O(n/k) recursion stack

---

### 9. Palindrome Linked List
**Problem:** Linked list palindrome hai ya nahi?

**Approach:** Middle find karo, second half reverse karo, phir compare karo.

```java
public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;
    
    // Find middle
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Reverse second half
    ListNode secondHalf = reverseList(slow);
    ListNode firstHalf = head;
    
    // Compare
    while (secondHalf != null) {
        if (firstHalf.val != secondHalf.val) return false;
        firstHalf = firstHalf.next;
        secondHalf = secondHalf.next;
    }
    return true;
}
```

>  Slow-fast se middle dhundho. Second half reverse karo. Phir first half aur reversed second half compare karo. Sab match kare toh palindrome.

**Time:** O(n), **Space:** O(1)

---

### 10. Add Two Numbers
**Problem:** Do linked lists (digits reversed order) ko add karo. 2→4→3 is 342.

**Approach:** Carry maintain karte hue add karo.

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    int carry = 0;
    
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        
        carry = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
    }
    return dummy.next;
}
```

>  Do lists mein se digit + carry add karo. New digit = sum % 10, new carry = sum / 10. Jab tak dono lists aur carry bache tab tak karo.

**Time:** O(max(m,n)), **Space:** O(max(m,n))

---

### 11. Copy List with Random Pointer
**Problem:** Har node mein random pointer hai (kisi bhi node ko point kar sakta hai). Deep copy banao.

**Approach:** Three passes: 1) Insert copy nodes after each original, 2) Set random pointers, 3) Separate lists.

```java
public Node copyRandomList(Node head) {
    if (head == null) return null;
    
    // Pass 1: Insert copy nodes
    Node curr = head;
    while (curr != null) {
        Node copy = new Node(curr.val);
        copy.next = curr.next;
        curr.next = copy;
        curr = copy.next;
    }
    
    // Pass 2: Set random pointers
    curr = head;
    while (curr != null) {
        if (curr.random != null) {
            curr.next.random = curr.random.next;
        }
        curr = curr.next.next;
    }
    
    // Pass 3: Separate lists
    Node dummy = new Node(0);
    Node copyCurr = dummy;
    curr = head;
    
    while (curr != null) {
        copyCurr.next = curr.next;
        copyCurr = copyCurr.next;
        curr.next = copyCurr.next;
        curr = curr.next;
    }
    return dummy.next;
}
```

>  O(1) space wala approach - har original node ke baad copy node insert karo. Phir random pointers set karo (copy.random = original.random.next). Finally lists separate karo.

**Time:** O(n), **Space:** O(1)

---

### 12. Intersection of Two Linked Lists
**Problem:** Do lists ka intersection node dhundho (Y shape).

**Approach:** Length difference nikaalo, longer list ko aage badhao, phir compare karo.

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    
    ListNode a = headA;
    ListNode b = headB;
    
    // Jab tak dono same nahi ho jate
    while (a != b) {
        a = (a == null) ? headB : a.next;
        b = (b == null) ? headA : b.next;
    }
    return a;
}
```

>  Elegant approach - dono pointers chalte raho. Agar koi null pe pahunche toh dusri list ke head pe le jao. Ye ensure karta hai ki dono pointers intersection tak same distance travel karein.

**Time:** O(m+n), **Space:** O(1)

---

### 13. Swap Nodes in Pairs
**Problem:** Adjacent nodes swap karo. 1→2→3→4 → 2→1→4→3

**Approach:** Dummy node + recursion or iterative.

```java
public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    
    while (prev.next != null && prev.next.next != null) {
        ListNode first = prev.next;
        ListNode second = prev.next.next;
        
        // Swap
        first.next = second.next;
        second.next = first;
        prev.next = second;
        
        // Move prev
        prev = first;
    }
    return dummy.next;
}
```

>  Dummy node lo. Prev ke aage do nodes hain toh unko swap karo. First node second node ko point karega, second node first ko point karega, prev second ko point karega. Phir prev = first (swapped pair ke baad).

**Time:** O(n), **Space:** O(1)

---

### 14. Reorder List
**Problem:** L0 → L1 → L2 → L3 → L4 → L0 → L4 → L1 → L3 → L2

**Approach:** Middle find karo, second half reverse karo, phir merge karo.

```java
public void reorderList(ListNode head) {
    if (head == null || head.next == null) return;
    
    // Find middle
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Reverse second half
    ListNode second = reverseList(slow.next);
    slow.next = null;
    
    // Merge first and second halves
    ListNode first = head;
    while (second != null) {
        ListNode temp1 = first.next;
        ListNode temp2 = second.next;
        
        first.next = second;
        second.next = temp1;
        
        first = temp1;
        second = temp2;
    }
}
```

>  Middle dhundho, second half reverse karo. Phir first half aur reversed second half ko alternate mein merge karo.

**Time:** O(n), **Space:** O(1)

---

### 15. Rotate List
**Problem:** List ko right se k steps rotate karo.

**Approach:** List ko circular banao, phir n-k steps aage badhakar break karo.

```java
public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null || k == 0) return head;
    
    // Find length and make list circular
    ListNode curr = head;
    int length = 1;
    while (curr.next != null) {
        curr = curr.next;
        length++;
    }
    curr.next = head;  // Make circular
    
    // Find new head
    k = k % length;
    int stepsToNewHead = length - k;
    ListNode newTail = head;
    for (int i = 1; i < stepsToNewHead; i++) {
        newTail = newTail.next;
    }
    
    ListNode newHead = newTail.next;
    newTail.next = null;  // Break circle
    
    return newHead;
}
```

>  Pehle length nikaalo aur list circular banao. K ko length se modulo karo. New head = length-k steps aage. Waha pe break karo.

**Time:** O(n), **Space:** O(1)

---

### 16. Odd Even Linked List
**Problem:** Odd indices pehle, even indices baad mein. 1→2→3→4→5 → 1→3→5→2→4

**Approach:** Two pointers - odd head aur even head maintain karo.

```java
public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null) return head;
    
    ListNode odd = head;
    ListNode even = head.next;
    ListNode evenHead = even;
    
    while (even != null && even.next != null) {
        odd.next = even.next;
        odd = odd.next;
        even.next = odd.next;
        even = even.next;
    }
    odd.next = evenHead;
    return head;
}
```

>  Odd pointer 1st node pe, even pointer 2nd node pe. Odd ko even ke next se connect karo, odd aage badhao. Even ko odd ke next se connect karo, even aage badhao. End mein odd.next = evenHead.

**Time:** O(n), **Space:** O(1)

---

### 17. Remove Duplicates from Sorted List II
**Problem:** Duplicates wale nodes completely remove karo (ek bhi copy na rakho).

**Approach:** Dummy node + prev pointer. Jab duplicate mile, tab tak skip karo.

```java
public ListNode deleteDuplicates(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    ListNode curr = head;
    
    while (curr != null) {
        // Check if current is start of duplicates
        boolean hasDuplicate = false;
        while (curr.next != null && curr.val == curr.next.val) {
            curr = curr.next;
            hasDuplicate = true;
        }
        
        if (hasDuplicate) {
            prev.next = curr.next;  // Skip all duplicates
        } else {
            prev = prev.next;  // Move prev
        }
        curr = curr.next;
    }
    return dummy.next;
}
```

>  Dummy node use karo. Jab duplicate chain mile, tab tak curr aage badhao. Phir prev.next = curr.next se saare duplicates skip karo. Warna prev aage badhao.

**Time:** O(n), **Space:** O(1)

---

### 18. Partition List
**Problem:** List ko partition karo jaha nodes with value < x, phir nodes >= x.

**Approach:** Two dummy lists - smaller and greater, phir join karo.

```java
public ListNode partition(ListNode head, int x) {
    ListNode smallerHead = new ListNode(0);
    ListNode greaterHead = new ListNode(0);
    ListNode smaller = smallerHead;
    ListNode greater = greaterHead;
    
    while (head != null) {
        if (head.val < x) {
            smaller.next = head;
            smaller = smaller.next;
        } else {
            greater.next = head;
            greater = greater.next;
        }
        head = head.next;
    }
    
    greater.next = null;  // Important to avoid cycle
    smaller.next = greaterHead.next;
    
    return smallerHead.next;
}
```

>  Do dummy lists banao - ek chhote nodes ke liye, ek bade nodes ke liye. Traverse karte hue nodes ko unme daalo. End mein chhoti list ke end mein badi list join karo.

**Time:** O(n), **Space:** O(1)

---

### 19. Flatten a Multilevel Doubly Linked List
**Problem:** Doubly linked list jisme har node ka child pointer bhi hai. Flatten karo.

**Approach:** Recursion se child list ko flatten karo aur insert karo.

```java
public Node flatten(Node head) {
    if (head == null) return null;
    
    Node curr = head;
    
    while (curr != null) {
        if (curr.child != null) {
            Node next = curr.next;
            Node childTail = flatten(curr.child);
            
            curr.next = curr.child;
            curr.child.prev = curr;
            
            if (next != null) {
                childTail.next = next;
                next.prev = childTail;
            }
            
            curr.child = null;
            curr = childTail;
        }
        curr = curr.next;
    }
    return head;
}
```

>  Har node ka child agar hai toh recursively flatten karo. Child list ko current ke baad insert karo. Child list ka tail find karo aur original next se connect karo.

**Time:** O(n), **Space:** O(depth)

---

### 20. LRU Cache
**Problem:** Least Recently Used cache implement karo. Get aur put O(1) mein.

**Approach:** HashMap + Doubly Linked List. Recently used nodes head ke paas, least used tail ke paas.

```java
class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }
    
    private Map<Integer, Node> map;
    private Node head, tail;
    private int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        removeNode(node);
        addToFront(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            removeNode(node);
            addToFront(node);
        } else {
            if (map.size() == capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToFront(newNode);
        }
    }
    
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

>  HashMap se O(1) lookup, Doubly Linked List se O(1) removal/addition. Recently used node ko head ke paas rakho, least recently used tail ke paas. Get/put pe node ko head pe move karo.

**Time:** O(1), **Space:** O(capacity)


---

## 🎯 Hard Linked List Interview Tips

1. **Dummy Node is your best friend:**
   - Jab head change ho sakta hai (removal, insertion, partitioning)
   - Edge cases handle karne mein help karta hai
   - `ListNode dummy = new ListNode(0); dummy.next = head;`

2. **Two Pointers (Slow/Fast) pattern:**
   - Middle find karna
   - Cycle detection
   - Nth node from end

3. **Reversal pattern:**
```java
ListNode prev = null;
ListNode curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
```

4. **Common Mistakes:**
   - Null pointer exception - hamesha `node.next` access karne se pehle `node != null` check karo
   - Cycle bhoolna (especially when reversing or merging)
   - Recursion mein base case bhoolna

5. **When to use recursion vs iteration:**
   - Recursion: Reverse printing, reverse list (elegant), flatten (depth-based)
   - Iteration: Most operations (better space complexity)

6. **Visualization technique:**
   - Paper pe draw karo pointers ke saath
   - Har step ke baad kya change hoga socho
   - Small example leke test karo

