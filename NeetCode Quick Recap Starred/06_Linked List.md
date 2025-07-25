# **Copy List with Random Pointer**

Aapko ek linked list di gayi hai jahan har node mein ek additional **random** pointer hai jo list ke kisi bhi node ya `null` ko point kar sakta hai. Aapko list ki ek **deep copy** banani hai. Copied list mein saare naye nodes honge jinki values original jaisi hongi aur `next` aur `random` pointers ka structure bhi original jaisa hoga.

-----

## Example

Input:
`head = [[7,null],[13,0],[11,4],[10,2],[1,0]]`

Output:
A deep copy with the same structure:
`[[7,null],[13,0],[11,4],[10,2],[1,0]]`

-----

### Intuition (Samajh):

Normal linked list copy karna easy hai, bas `next` pointers ko follow karte jao aur naye nodes banate jao. Lekin yahan **random** pointers bhi hain, jo list mein kisi bhi node ko point kar sakte hain. Isko handle karna tricky hai kyunki jab hum kisi node ka copy banate hain, toh ho sakta hai uske random pointer se point kiya gaya node abhi tak copy na hua ho.

Is problem ko constant extra space (excluding recursion stack for hashmap based solution) mein solve karne ka ek smart tareeka hai original list ke structure ko modify karna temporarily, phir `random` pointers assign karna, aur finally original list ko restore karna.

**Key Idea:**
Hum har original node `N` ke theek baad uski copy `N'` ko insert kar denge. Isse list `N1 -> N1' -> N2 -> N2' -> ...` aisi ho jayegi.
Ab `N1'.random` ko assign karna bahut easy hai: agar `N1.random` kisi `Nk` ko point kar raha hai, toh `N1'.random` us `Nk` ke copy `Nk'` ko point karega. `Nk'` toh `Nk.next` hi hai. Toh `N1'.random = N1.random.next`.

-----

### Approach (Tareeka):

Yeh solution teen main steps mein kaam karta hai:

1.  **Copy Nodes aur Interleave Karna:**

      * Original linked list par iterate karo.
      * Har `original_node` ke liye, uski ek nayi `copy_node` banao.
      * `copy_node` ko `original_node` ke theek baad insert kar do. `original_node.next` ko `copy_node` bana do, aur `copy_node.next` ko `original_node` ka original `next` pointer bana do.
      * Is step ke baad, linked list aisi dikhegi: `Original1 -> Copy1 -> Original2 -> Copy2 -> Original3 -> Copy3 -> ...`

2.  **Copied Nodes ke Random Pointers Assign Karna:**

      * Ab phir se interleaved list par iterate karo, lekin ab sirf original nodes par move karo (`iter = iter.next.next`).
      * Har `original_node` ke liye, uski `copy_node` hai `original_node.next`.
      * Agar `original_node.random` null nahi hai:
          * `original_node.next.random` (yani `copy_node.random`) ko assign karo `original_node.random.next` se. `original_node.random.next` isliye kyunki `original_node.random` (jo kisi `X` ko point karta hai) uska copy (`X'`) theek `X.next` par hi hai.

3.  **Copied List ko Original List se Separate Karna aur Original List ko Restore Karna:**

      * Ek `pseudoHead` (dummy node) banao copied list ke head ko track karne ke liye.
      * Phir se interleaved list par iterate karo.
      * Har `original_node` par:
          * `copy_node = original_node.next`
          * `original_node.next` ko uske original next node (`copy_node.next`) par point kar do. Isse original list restore ho jayegi.
          * `copy_node.next` ko abhi tak ki copied list ke last node se connect karo.
          * `copyIter` ko `copy_node` par move karo.
      * `pseudoHead.next` return kar do, jo deep copied list ka actual head hoga.

-----

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
        Node iter = head, next; // iter: original list par iterate karega, next: temp storage for next node

        // Step 1: Har original node ki copy banao aur use original node ke theek baad insert karo.
        // Isse list aisi ho jayegi: Original1 -> Copy1 -> Original2 -> Copy2 -> ...
        while (iter != null) {
            next = iter.next; // Original next node ko store karo
            Node copy = new Node(iter.val); // Current original node ki copy banao
            iter.next = copy;              // Original node ko uski copy se link karo
            copy.next = next;              // Copy ko original ke next node se link karo
            iter = next;                   // Next original node par move karo
        }

        // Step 2: Copied nodes ke random pointers ko assign karo.
        // Hum abhi bhi interleaved list par hain.
        // copy_node ka random pointer original_node.random ke copy_node ko point karega.
        // Original_node.random ka copy_node hai original_node.random.next
        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                // iter.next (current original node ki copy) ka random pointer
                // iter.random.next (original.random wale node ki copy) ko point karega.
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next; // Next original node par move karo (skip the copy node)
        }

        // Step 3: Copied list ko original list se alag karo aur original list ko restore karo.
        iter = head; // Original list ke head par wapis aao
        Node pseudoHead = new Node(0); // Copied list ke liye ek dummy head node banao
        Node copyIter = pseudoHead;    // Copied list ko build karne ke liye pointer

        while (iter != null) {
            next = iter.next.next; // Next original node ko store karo

            // Current original node se uski copy node extract karo
            Node copy = iter.next;
            copyIter.next = copy; // Copied list mein copy node ko add karo
            copyIter = copy;      // Copied list pointer ko aage badhao

            // Original list ke next pointer ko restore karo
            iter.next = next;

            iter = next; // Original list pointer ko aage badhao
        }

        return pseudoHead.next; // Deep copied list ka actual head return karo
    }
}
```

-----

# **Find the Duplicate Number**

Aapko ek integers ka array `nums` diya gaya hai jismein `n + 1` integers hain aur har integer `[1, n]` range mein hai (inclusive). `nums` mein sirf ek hi repeated number hai. Aapko yeh repeated number return karna hai.

Aapko problem ko array ko **modify kiye bina** aur **sirf constant extra space** ka use karke solve karna hai.

-----

## Example

Input:
`nums = [1,3,4,2,2]`

Output:
`2`

-----

## Intuition (Samajh):

Yeh problem ek bahut hi famous algorithm, **Floyd's Tortoise and Hare (Cycle Detection)**, ka use karke solve ki ja sakti hai. Yeh algorithm linked lists mein cycles dhundhne ke liye use hota hai.

Hum is array ko ek linked list ki tarah imagine kar sakte hain:

  * Array ka **index** ek node ko represent karta hai.
  * `nums[index]` value ko us node ke **next pointer** ki tarah treat kar sakte hain, jo `nums[index]` value wale index par point karta hai.

For example, `nums = [1,3,4,2,2]`:

  * `0 -> nums[0] = 1`
  * `1 -> nums[1] = 3`
  * `2 -> nums[2] = 4`
  * `3 -> nums[3] = 2`
  * `4 -> nums[4] = 2`

Agar `nums[i]` values `1` se `n` tak hain, aur `n+1` elements hain, toh Pigeonhole Principle ke according ek duplicate hoga. Yeh duplicate hi cycle create karega.

Path looks like: `0 -> 1 -> 3 -> 2 -> 4 -> 2`
Yahan `2` duplicate hai, aur `2 -> 4 -> 2` ek cycle hai jiska entry point `2` hai.

Algorithm ke steps:

1.  **Cycle Detection (Intersection Point):** Slow aur Fast pointers use karo. Slow pointer 1 step aage badhega, Fast pointer 2 steps aage badhega. Agar cycle hai, toh woh milenge.
2.  **Cycle Entry Point (Duplicate Number):** Jab slow aur fast mil jaayen, fast ko wapis start par (index 0) le aao. Ab slow aur fast dono 1-1 step aage badhenge. Jahan woh milenge, wahi cycle ka entry point hoga, aur wahi duplicate number bhi hoga.

-----

## Code with Comments

```java
class Solution {
    public int findDuplicate(int[] nums) {
        // Edge case: Agar array khaali hai, toh koi duplicate nahi ho sakta.
        if (nums.length == 0)
            return 0; // Ya koi appropriate error value

        // Step 1: Slow aur Fast pointers ko initialize karo.
        // Dono nums[0] se shuru hote hain (yani index 0 se next value par).
        int slow = nums[0]; // Slow pointer ek step move karega
        int fast = nums[0]; // Fast pointer do steps move karega

        // Cycle Detection Phase:
        // Slow 1 step moves: slow = nums[slow]
        // Fast 2 steps moves: fast = nums[nums[fast]]
        // Jab tak slow aur fast milte nahi, tab tak loop chalao.
        do {
            slow = nums[slow];        // Slow pointer ko ek step aage badhao
            fast = nums[nums[fast]];  // Fast pointer ko do steps aage badhao
        } while (slow != fast); // Jab yeh dono milte hain, toh cycle hai.

        // Step 2: Cycle Entry Point (Duplicate Number) dhundhne ki phase.
        // Fast pointer ko wapis shuruat (index 0 se value) par le aao.
        fast = nums[0];
        // Ab slow aur fast dono ek-ek step move karenge.
        // Jahan woh milenge, wahi cycle ka entry point hoga, jo duplicate number hai.
        while (slow != fast) {
            slow = nums[slow]; // Slow pointer ek step aage
            fast = nums[fast]; // Fast pointer ek step aage
        }

        // Jab slow aur fast mil jaate hain, toh slow (ya fast) jis value par hai,
        // wahi duplicate number hai.
        return slow;
    }
}
```

-----

# **LRU Cache**

Ek data structure design karna hai jo Least Recently Used (LRU) cache ki constraints ko follow kare.

`LRUCache` class implement karo:

  * `LRUCache(int capacity)`: Cache ko positive size `capacity` ke saath initialize karta hai.
  * `int get(int key)`: Key ki value return karta hai agar key cache mein मौजूद hai, otherwise `-1` return karta hai.
  * `void put(int key, int value)`: Agar key cache mein मौजूद hai, toh value ko update karta hai. Otherwise, key-value pair ko add karta hai. Agar cache capacity se zyada ho jaata hai, toh least recently used (LRU) key ko hata deta hai.

`get` aur `put` dono operations **O(1)** average time complexity mein chalne chahiye.

-----

## Example

Input:
`["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]`
`[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]`

Output:
`[null, null, null, 1, null, -1, null, -1, 3, 4]`

-----

## Intuition (Samajh):

LRU cache ka concept yeh hai ki jo item sabse kam use hua hai (least recently used), use cache se remove kar do jab cache full ho jaye aur naya item add karna ho.
`O(1)` time complexity ka matlab hai ki humein `get` aur `put` operations bahut tez karne hain.

Iske liye do data structures ka combination use karte hain:

1.  **HashMap (`HashMap<Integer, Node> map`)**:

      * Keys ko unke corresponding nodes (value aur pointers ke saath) se map karta hai.
      * Yeh `O(1)` average time mein key-value pair ko `get` karne aur `update` karne mein help karta hai.

2.  **Doubly Linked List (`Node head, tail`)**:

      * Nodes ko unke usage order ke hisab se arrange karta hai.
      * **Most Recently Used (MRU)** nodes list ke `head` ke paas honge.
      * **Least Recently Used (LRU)** nodes list ke `tail` ke paas honge.
      * Doubly linked list allows `O(1)` time removal and insertion of nodes.

**Operations ka Logic:**

  * **`get(key)`**:

      * Agar `key` `map` mein nahi hai, toh `-1` return karo.
      * Agar `key` hai, toh us corresponding `node` ko `map` se retrieve karo.
      * `node` ko uski current position se `remove` karo.
      * `node` ko `head` par `insert` karo (kyunki abhi-abhi use hua hai, toh yeh most recently used ban gaya).
      * `node.value` return karo.

  * **`put(key, value)`**:

      * **Agar `key` `map` mein already hai:**
          * Us `node` ko retrieve karo.
          * `node.value` ko update karo.
          * `node` ko uski current position se `remove` karo.
          * `node` ko `head` par `insert` karo (most recently used ban gaya).
      * **Agar `key` `map` mein nahi hai (naya key-value pair):**
          * Check karo ki `map.size()` `capacity` ke barabar hai ya nahi.
          * **Agar `map.size() == capacity` (cache full hai):**
              * `tail.prev` (dummy tail ke pehle wala node, jo LRU hai) ko `map` se remove karo (uske `key` ka use karke).
              * `tail.prev` ko doubly linked list se `remove` karo.
          * Naya `Node` banao `key` aur `value` ke saath.
          * Naye `node` ko `map` mein add karo.
          * Naye `node` ko `head` par `insert` karo.

**Doubly Linked List ke Helper Functions:**

  * `remove(Node node)`: Ek given node ko linked list se remove karta hai (uske `prev` aur `next` pointers ko adjust karke).
  * `insertToHead(Node node)`: Ek given node ko dummy `head` ke theek baad insert karta hai.

-----

## Code with Comments

```java
import java.util.HashMap;

class LRUCache {

    // Doubly Linked List Node class
    class Node {
        int key;   // Key ko bhi store karna zaroori hai map se remove karte waqt
        int value;
        Node prev; // Pichla node
        Node next; // Agla node

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> map; // Key se Node ko access karne ke liye HashMap
    private int capacity;               // Cache ki maximum capacity
    private Node head, tail;            // Dummy head aur tail nodes doubly linked list ke liye.
                                        // Yeh operations ko simplify karte hain edge cases handle karne mein.

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0); // Dummy head node
        tail = new Node(0, 0); // Dummy tail node
        head.next = tail;      // Head ka next tail hoga initially
        tail.prev = head;      // Tail ka prev head hoga initially
    }

    public int get(int key) {
        // Agar key map mein nahi hai, toh cache miss hua.
        if (!map.containsKey(key)) {
            return -1; // -1 return karo
        }
        
        // Key mila! Ab is node ko most recently used banana hai.
        Node node = map.get(key);
        remove(node);          // Node ko uski current position se remove karo
        insertToHead(node);    // Node ko head par insert karo (MRU banane ke liye)
        return node.value;     // Value return karo
    }

    public void put(int key, int value) {
        // Agar key already cache mein hai
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; // Value update karo
            remove(node);       // Node ko uski current position se remove karo
            insertToHead(node); // Node ko head par re-insert karo (MRU banane ke liye)
        } else {
            // Key naya hai
            // Cache full hai toh LRU item ko remove karna hoga
            if (map.size() == capacity) {
                // Least recently used node tail.prev par hoga.
                // Map se LRU key ko remove karo
                map.remove(tail.prev.key);
                // Linked list se LRU node ko remove karo
                remove(tail.prev);
            }
            // Naya node banao
            Node newNode = new Node(key, value);
            // Naye node ko map mein add karo
            map.put(key, newNode);
            // Naye node ko head par insert karo (MRU banane ke liye)
            insertToHead(newNode);
        }
    }

    // Helper function: Kisi node ko doubly linked list se remove karna
    private void remove(Node node) {
        // Node ke pichle node ka next, node ke agle node ko point karega
        node.prev.next = node.next;
        // Node ke agle node ka prev, node ke pichle node ko point karega
        node.next.prev = node.prev;
    }

    // Helper function: Kisi node ko head ke theek baad insert karna
    private void insertToHead(Node node) {
        // Naye node ka next, head ke original next node ko point karega
        node.next = head.next;
        // Naye node ka prev, head ko point karega
        node.prev = head;
        // Head ke original next node ka prev, naye node ko point karega
        head.next.prev = node;
        // Head ka next, naye node ko point karega
        head.next = node;
    }
}
```

-----

# **Merge k Sorted Lists**

Aapko `k` linked lists ka ek array diya gaya hai, har ek ascending order mein sorted hai.

Saari linked lists ko ek single sorted linked list mein merge karo aur use return karo.

-----

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

-----

## Intuition (Samajh):

Agar do sorted linked lists ko merge karna ho, toh hum do pointers use karte hain aur chote element ko aage badhate hain. Lekin yahan `k` lists hain. Agar hum sabko ek-ek karke merge karein (jaise `L1` aur `L2` ko merge kiya, phir result ko `L3` se merge kiya, etc.), toh time complexity bahut zyada ho jayegi.

Is problem ko efficiently solve karne ke liye, hum ek **Min-Heap (Priority Queue)** ka use kar sakte hain.

**Key Idea:**

  * Min-heap hamesha sabse chota element top par rakhega.
  * Hum shuruat mein har non-empty list ke `head` node ko min-heap mein daal denge.
  * Phir, baar-baar min-heap se sabse chota node nikalenge (yani `poll()` karenge).
  * Is node ko apni merged list mein add kar denge.
  * Agar is node ka `next` node exists karta hai, toh us `next` node ko min-heap mein daal denge.
  * Yeh process tab tak chalegi jab tak min-heap khaali na ho jaye.

Is approach se, hum hamesha available nodes mein se sabse chota element select karte hain, jisse final list sorted rehti hai.

-----

## Code with Comments

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

import java.util.PriorityQueue; // PriorityQueue use karne ke liye

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // Edge case: Agar lists array null hai ya khaali hai, toh kuch merge nahi kar sakte.
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Merged list ke liye ek dummy head node banate hain.
        // Yeh final result ke head ko return karne mein help karega.
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy; // prev pointer jo merged list ko build karega

        // Step 1: Min-heap (PriorityQueue) banao.
        // Ismein ListNode objects store honge, aur woh unki 'val' ke according sort honge (smallest first).
        PriorityQueue<ListNode> minPQ = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Step 2: Har list ke head node ko (agar woh null nahi hai) min-heap mein add karo.
        for (ListNode node : lists) {
            if (node != null) {
                minPQ.offer(node); // Heap mein add karo
            }
        }

        // Step 3: Jab tak min-heap khaali nahi ho jata, tab tak nodes ko process karo.
        while (!minPQ.isEmpty()) {
            // Heap se sabse chota node nikalo (poll method top element ko remove karke return karta hai)
            ListNode curr = minPQ.poll();
            
            // Nikle hue node ko merged list mein add karo
            prev.next = curr;
            prev = prev.next; // prev pointer ko aage badhao

            // Agar current node ka next node exist karta hai, toh use bhi heap mein add karo.
            // Kyunki woh agla candidate ho sakta hai sabse chote element ke liye.
            if (curr.next != null) {
                minPQ.offer(curr.next);
            }
        }

        // Dummy head ke next se start hone wali merged list return karo.
        return dummy.next;
    }
}
```

-----

# **Reverse Nodes in k-Group**

Aapko ek linked list ka head diya gaya hai. List ke nodes ko `k` ke groups mein reverse karo, aur modified list return karo.

  * Sirf nodes ko hi change kiya ja sakta hai, node values ko nahi.
  * Agar nodes ki sankhya `k` ka multiple nahi hai, toh end mein bache hue nodes ko as-is rehne do.

-----

## Example

**Input:**
`head = [1,2,3,4,5], k = 2`
**Output:**
`[2,1,4,3,5]`

-----

## Intuition (Samajh):

Is problem mein hamein linked list ko `k`-sized groups mein reverse karna hai. Jo groups `k` se chhote hain, unhein reverse nahi karna.

Iske liye hum iterative approach use karenge:

1.  **List Length:** Sabse pehle poori linked list ki length count kar lo. Yeh batayega ki kitne complete `k`-groups hain aur kitne nodes end mein bach rahe hain jo reverse nahi honge.
2.  **Dummy Head:** Ek `dummyHead` node banao. Yeh modified list ke head ko handle karne mein help karega, khaas kar tab jab pehla group reverse ho.
3.  **Group Reversal Loop:**
      * Jab tak `remaining_length >= k` hai, tab tak loop chalao.
      * Har iteration mein, ek `k`-sized group ko reverse karna hai.
      * Reversal ke liye, hum teen pointers (like in standard linked list reversal) use karenge: `pre`, `cur`, `nex`.
      * `pre`: Previous node of the group (jo reversed group ko previous part se connect karega).
      * `cur`: Current node of the group.
      * `nex`: Next node of the group.
      * Group reverse hone ke baad, `pre` ko update karke reversed group ke end tak le aao (jo ki original group ka first node tha).
      * `remaining_length` ko `k` se kam kar do.

-----

## Code with Comments

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    
    // Helper function: Linked list ki total length calculate karta hai
    static int lengthOfLinkedList(ListNode head) {
        int length = 0;
        ListNode temp = head; // Head ko modify na karne ke liye temp pointer
        while(temp != null) {
            length++;
            temp = temp.next;
        }
        return length;
    }
    
    public ListNode reverseKGroup(ListNode head, int k) {
        // Base cases: Agar list khaali hai ya single node hai, toh koi reversal nahi.
        if(head == null || head.next == null || k == 1) return head;

        int length = lengthOfLinkedList(head); // Poori list ki length dhundo
        
        // Dummy head node banaya jisse new head ko handle karna easy ho jaye.
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        
        ListNode pre = dummyHead; // pre: reversed group ka pichla node (initially dummy)
        ListNode cur;             // cur: current group ka pehla node (reverse hone wala)
        ListNode nex;             // nex: current group ka agla node (reverse hone wala)

        // Jab tak bache hue nodes ki sankhya 'k' se zyada ya barabar hai, tab tak groups ko reverse karo.
        while(length >= k) {
            cur = pre.next; // Current group ka pehla node
            nex = cur.next; // Current group ka doosra node

            // Current 'k' group ke nodes ko reverse karo.
            // Yeh loop k-1 times chalega (kyunki pehla swap 1st aur 2nd node ke beech hota hai)
            for(int i = 1; i < k; i++) {
                // Connection cut karo: cur ka next, nex ke next ko point karega
                cur.next = nex.next;
                // nex ko pre.next ke baad insert karo (reversed part ki shuruat mein)
                nex.next = pre.next;
                // pre ka next ab nex ko point karega (nayi shuruat)
                pre.next = nex;
                // nex ko aage badhao (ab nex woh node hai jo original list mein cur ke baad tha)
                nex = cur.next;
            }
            // Ek group reverse ho gaya.
            // pre ko ab current reversed group ke end par le aao (jo ki original group ka pehla node tha).
            pre = cur;
            // Total bachi hui length ko 'k' se kam karo.
            length -= k;
        }
        // Dummy head ke next se start hone wali modified list return karo.
        return dummyHead.next;
    }
}
```
