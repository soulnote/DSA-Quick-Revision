# Linked List 
## 📚 Theory + Java Code for All Important Linked List Questions

---

## **Linked List Basics - Theory (Hinglish)**

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

## **Quick Revision Table**

| Problem | Time | Space | Key Technique |
|---------|------|-------|---------------|
| Reverse List | O(n) | O(1) | Three pointers |
| Detect Cycle | O(n) | O(1) | Floyd's cycle |
| Middle Element | O(n) | O(1) | Slow-fast pointer |
| Remove Nth from End | O(n) | O(1) | Two pointers |
| Merge Two Lists | O(m+n) | O(1) | Two pointers |
| Palindrome | O(n) | O(1) | Reverse second half |
| Intersection | O(m+n) | O(1) | Length difference |
| Add Two Numbers | O(max(m,n)) | O(max(m,n)) | Carry method |
| Rotate List | O(n) | O(1) | Make circular |
| Reverse k-Group | O(n) | O(n/k) | Recursion |
| Copy Random List | O(n) | O(1) | Interweaving |
| Sort List | O(n log n) | O(log n) | Merge sort |

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

## **Interview Tips (Hinglish)**

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
