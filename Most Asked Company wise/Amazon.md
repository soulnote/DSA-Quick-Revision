## 🔢 Arrays

-----

### ⭐ Two Sum (Easy - but important for understanding hash maps)

**Problem:** Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`. Assume each input would have exactly one solution, and you may not use the same element twice.

**Intuition (Samajh):**
Har number ke liye, humein check karna hai ki `target - current_number` array mein exist karta hai ya nahi. Agar array sorted hota toh Two Pointers chal jaata, par unsorted array mein `O(N^2)` brute force hoga. `O(N)` mein karne ke liye, jab hum array ko traverse karte hain, toh humein jaldi se pata chalna chahiye ki complement (remaining part) exist karta hai ya nahi. Iske liye **Hash Map** best hai.

**Approach (Tareeka): Hash Map**

1.  **Initialize:** `Map<Integer, Integer> numMap = new HashMap<>();` (key: number, value: its index).
2.  **Iterate:** `nums` array par iterate karo `i = 0` se `N-1` tak.
3.  **Calculate Complement:** `complement = target - nums[i];`
4.  **Check in Map:** `if (numMap.containsKey(complement))`:
      * Agar `complement` map mein mil gaya, toh `return new int[]{numMap.get(complement), i};` (uske index aur current index).
5.  **Add to Map:** `numMap.put(nums[i], i);` (Agar complement nahi mila, toh current number ko map mein daal do aage ke checks ke liye).
6.  **Return:** (Problem ke constraint ke hisab se solution always mil jaega, varna empty array ya exception).

-----

### ⭐ Trapping Rain Water (Hard - very common, tests understanding of two pointers/stack)

**Problem:** Given `n` non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

**Intuition (Samajh):**
Kisi bhi bar `i` par kitna paani trap hoga, woh depend karta hai uske left side ki maximum height aur right side ki maximum height par. Trap hone wala paani `min(max_left_height, max_right_height) - current_height` hota hai. Negative hua toh paani trap nahi hoga.

**Approach 1 (Tareeka): Two Pointers (O(N) time, O(1) space - Optimized)**

1.  **Initialize:** `left = 0`, `right = N-1`. `left_max = 0`, `right_max = 0`. `total_water = 0`.
2.  **Iterate:** `while (left < right)`:
3.  **Move Pointer:** `if (height[left] < height[right])`:
      * **Left Side:** Agar left bar chhota hai, toh left side se calculation karo. `left_max = Math.max(left_max, height[left]);`
      * `total_water += left_max - height[left];`
      * `left++;`
4.  **Else (Right Side):**
      * `right_max = Math.max(right_max, height[right]);`
      * `total_water += right_max - height[right];`
      * `right--;`
5.  **Return:** `total_water`.

**Approach 2 (Tareeka): Precompute Left/Right Max Arrays (O(N) time, O(N) space)**

1.  `left_max[i]`: Maximum height to the left of `i`, including `height[i]`.
2.  `right_max[i]`: Maximum height to the right of `i`, including `height[i]`.
3.  **Fill `left_max`:** Iterate from left to right.
4.  **Fill `right_max`:** Iterate from right to left.
5.  **Calculate Water:** Iterate from `i = 0` to `N-1`.
      * `water_at_i = min(left_max[i], right_max[i]) - height[i];`
      * `total_water += water_at_i;`

-----

### ⭐ Number of Islands / Biggest Island (Medium - Graph traversal, DFS/BFS)

**Problem:** Same as discussed previously. `Number of Islands` counts connected land components. `Biggest Island` finds the area (number of cells) of the largest connected land component.

**Solution:**
Refer to the `Number of Islands` solution provided earlier. For **Biggest Island**:

1.  Same DFS/BFS logic.
2.  `dfs(grid, r, c)` function mein, `count` ki jagah `current_island_size` track karo.
3.  Har DFS call ke baad `max_island_size = Math.max(max_island_size, current_island_size);` update karo.
4.  Return `max_island_size`.

-----

### ⭐ Merge Intervals (Medium - Sorting, greedy approach)

**Problem:** Given an array of `intervals` where `intervals[i] = [starti, endi]`, merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

**Intuition (Samajh):**
Agar intervals sorted nahi hain, toh overlap check karna mushkil hai. Pehle intervals ko unke `start` time ke hisab se sort karo. Uske baad, greedy approach se merge kar sakte hain.

**Approach (Tareeka): Sort + Greedy**

1.  **Sort:** `intervals` array ko `start` time ke basis par sort karo. `Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));`
2.  **Initialize:** `List<int[]> mergedIntervals = new ArrayList<>();`
      * Add the first interval to `mergedIntervals`.
3.  **Iterate:** `intervals` ke baki elements par iterate karo (second element se start).
      * `currentInterval = intervals[i];`
      * `lastMergedInterval = mergedIntervals.get(mergedIntervals.size() - 1);`
      * **Check Overlap:** `if (currentInterval[0] <= lastMergedInterval[1])`: (Overlap ho raha hai)
          * `lastMergedInterval[1] = Math.max(lastMergedInterval[1], currentInterval[1]);` (End time update karo)
      * **No Overlap:** `else`: (New interval, add as is)
          * `mergedIntervals.add(currentInterval);`
4.  **Return:** `mergedIntervals` ko `int[][]` mein convert karke.

-----

### ⭐ Kth Largest Element in an Array (Medium - Quickselect/Heap)

**Problem:** Given an integer array `nums` and an integer `k`, return the `k`th largest element in the array. Note that it is the `k`th largest element in the sorted order, not the `k`th distinct element.

**Intuition (Samajh):**
Sorted array mein `N-K` index par jo element hoga, woh `K`th largest hoga. Poore array ko sort karna `O(N log N)` hai. Hum `O(N)` average time complexity mein kar sakte hain.

**Approach 1 (Tareeka): Min-Heap (Priority Queue)**

1.  **Initialize:** `PriorityQueue<Integer> minHeap = new PriorityQueue<>();`
2.  **Iterate:** `nums` array ke har element `num` par iterate karo.
      * `minHeap.add(num);`
      * `if (minHeap.size() > k)`:
          * `minHeap.poll();` (Heap size `k` se zyada hote hi smallest element remove kar do)
3.  **Return:** `minHeap.peek();` (Heap ka top `k`th largest hoga).
    **Time Complexity:** `O(N log K)`.

**Approach 2 (Tareeka): Quickselect (Partitioning algorithm, like QuickSort)**

1.  Yeh QuickSort ke partition step ka use karta hai. Goal hai pivot element ko uski sahi sorted position par rakhna. Agar pivot ka index `N-k` hai (0-indexed), toh wohi `k`th largest hai. Agar pivot ka index `N-k` se chhota hai, toh right side mein search karo. Agar bada hai, toh left side mein search karo.
2.  Average `O(N)` time complexity, worst case `O(N^2)`.

-----

### ⭐ Maximum Subarray (Medium - Kadane's Algorithm)

**Problem:** Given an integer array `nums`, find the subarray with the largest sum, and return its sum.

**Intuition (Samajh):**
"Subarray" matlab continuous. "Largest sum". Brute force `O(N^3)` ya `O(N^2)` hoga. `O(N)` mein solve karne ke liye **Kadane's Algorithm** use hota hai. Idea yeh hai ki agar `current_sum` negative ho jata hai, toh usko discard kar do aur naye subarray se start karo. Kyunki negative sum kisi bhi future positive sum ko kam hi karega.

**Approach (Tareeka): Kadane's Algorithm**

1.  **Initialize:** `max_so_far = Integer.MIN_VALUE;` `current_max = 0;`
2.  **Iterate:** `nums` array ke har element `num` par iterate karo.
      * `current_max += num;`
      * `max_so_far = Math.max(max_so_far, current_max);`
      * `if (current_max < 0)`:
          * `current_max = 0;` (Agar current sum negative ho gaya, toh naye subarray se start karo)
3.  **Return:** `max_so_far`.
      * **Edge Case:** Agar saare numbers negative hon, toh Kadane's mein `current_max` kabhi `max_so_far` ko update nahi karega properly agar `current_max` ko `0` se reset kiya.
      * **Correct Kadane's (Handles all negatives):**
          * `max_so_far = nums[0];`
          * `current_max = nums[0];`
          * `for i = 1 to N-1:`
              * `current_max = Math.max(nums[i], current_max + nums[i]);`
              * `max_so_far = Math.max(max_so_far, current_max);`

-----

### ⭐ Subarray Sum Equals K (Medium - Hash Map)

**Problem:** Given an array of integers `nums` and an integer `k`, return the total number of continuous subarrays whose sum equals to `k`.

**Intuition (Samajh):**
Brute force `O(N^2)` mein saare subarrays ka sum check karna hoga. `O(N)` mein karne ke liye **Prefix Sum + Hash Map** ka concept use hota hai.
`sum[i..j] = prefix_sum[j] - prefix_sum[i-1]`.
Hum `prefix_sum[j] - prefix_sum[i-1] == k` dhundh rahe hain.
Iska matlab `prefix_sum[j] - k == prefix_sum[i-1]`.
Toh, current `prefix_sum` calculate karte jao, aur check karte jao ki `current_prefix_sum - k` map mein exist karta hai ya nahi.

**Approach (Tareeka): Prefix Sum + Hash Map**

1.  **Initialize:**
      * `Map<Integer, Integer> prefixSumCount = new HashMap<>();` (key: prefix sum, value: count of times this prefix sum occurred).
      * `prefixSumCount.put(0, 1);` (Important: Empty prefix sum 0 hota hai aur ek baar occur ho gaya).
      * `current_sum = 0;`
      * `count = 0;`
2.  **Iterate:** `nums` array ke har element `num` par iterate karo.
      * `current_sum += num;`
      * `if (prefixSumCount.containsKey(current_sum - k))`:
          * `count += prefixSumCount.get(current_sum - k);` (Agar `current_sum - k` mil gaya, toh uski frequency add karo `count` mein).
      * `prefixSumCount.put(current_sum, prefixSumCount.getOrDefault(current_sum, 0) + 1);` (Current prefix sum ko map mein add/update karo).
3.  **Return:** `count`.

-----

### ⭐ Longest Substring Without Repeating Characters (Medium - Sliding Window, Hash Set)

**Problem:** Same as previously discussed.

**Solution:** Refer to the solution in the previous response.

-----

### ⭐ Rotate Image (Medium - In-place array manipulation)

**Problem:** You are given an `n x n` 2D `matrix` representing an image, rotate the image by 90 degrees (clockwise). You have to rotate the image **in-place**, meaning you have to modify the input 2D matrix directly.

**Intuition (Samajh):**
In-place rotation tricky hai. 90 degree clockwise rotation ke liye do steps mein kar sakte hain:

1.  **Transpose the matrix:** `matrix[i][j]` ko `matrix[j][i]` bana do.
2.  **Reverse each row:** Har row ko reverse kar do.

**Approach (Tareeka): Transpose + Reverse Rows**

1.  **Transpose:**
      * `for i = 0 to n-1`:
          * `for j = i+1 to n-1`: (Only upper triangle traverse karo, diagonal ke elements ko swap nahi karna, aur `(j,i)` already swapped ho chuka hoga `(i,j)` se)
              * `swap(matrix[i][j], matrix[j][i]);`
2.  **Reverse Each Row:**
      * `for i = 0 to n-1`:
          * `left = 0`, `right = n-1`;
          * `while (left < right)`:
              * `swap(matrix[i][left], matrix[i][right]);`
              * `left++; right--;`

-----

### ⭐ Best Time to Buy and Sell Stock (I and II) (Easy/Medium - DP or greedy)

**Problem I (Easy):** You are given an array `prices` where `prices[i]` is the price of a given stock on the `ith` day. You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock. Return the maximum profit.

**Intuition (Samajh):**
Humein `sell_price - buy_price` maximize karna hai, jahan `buy_day < sell_day`. Har din, humein minimum buy price chahiye jo ab tak mila hai.

**Approach (Tareeka): Single Pass**

1.  **Initialize:** `min_price = Integer.MAX_VALUE;` `max_profit = 0;`
2.  **Iterate:** `prices` array ke har `price` par iterate karo.
      * `if (price < min_price)`:
          * `min_price = price;` (Naya minimum buy price mila)
      * `else if (price - min_price > max_profit)`:
          * `max_profit = price - min_price;` (Agar current price par bechne se zyada profit ho raha hai)
3.  **Return:** `max_profit`.

-----

**Problem II (Medium):** You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day. On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day. Find the maximum profit.

**Intuition (Samajh):**
Yahan multiple transactions allowed hain. Har baar jab price badhta hai (`price[i] > price[i-1]`), toh hum us "upward trend" ko capture kar sakte hain. Yani, `buy low, sell high` ko repeatedly apply karo.

**Approach (Tareeka): Greedy**

1.  **Initialize:** `max_profit = 0;`
2.  **Iterate:** `prices` array par `i = 1` se `N-1` tak iterate karo.
      * `if (prices[i] > prices[i-1])`:
          * `max_profit += (prices[i] - prices[i-1]);` (Agar price badha hai, toh previous day buy karke aaj bech do)
3.  **Return:** `max_profit`.

-----

### ⭐ Find First and Last Position of Element in Sorted Array (Medium - Binary Search)

**Problem:** Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value. If `target` is not found, return `[-1, -1]`.

**Intuition (Samajh):**
Sorted array mein element search karna hai, toh **Binary Search** use hoga. Yahan first aur last occurrence dhundhni hai, toh standard binary search ko thoda modify karna padega.

**Approach (Tareeka): Two Modified Binary Searches**

1.  **`findFirstOccurrence(nums, target)`:**
      * `low = 0, high = N-1, first_pos = -1;`
      * `while (low <= high)`:
          * `mid = low + (high - low) / 2;`
          * `if (nums[mid] == target)`:
              * `first_pos = mid;` (Candidate for first position)
              * `high = mid - 1;` (Left side mein aur dhundo)
          * `else if (nums[mid] < target)`: `low = mid + 1;`
          * `else`: `high = mid - 1;`
      * `return first_pos;`
2.  **`findLastOccurrence(nums, target)`:**
      * `low = 0, high = N-1, last_pos = -1;`
      * `while (low <= high)`:
          * `mid = low + (high - low) / 2;`
          * `if (nums[mid] == target)`:
              * `last_pos = mid;` (Candidate for last position)
              * `low = mid + 1;` (Right side mein aur dhundo)
          * `else if (nums[mid] < target)`: `low = mid + 1;`
          * `else`: `high = mid - 1;`
      * `return last_pos;`
3.  **Main Function:**
      * `first = findFirstOccurrence(nums, target);`
      * `last = findLastOccurrence(nums, target);`
      * `return new int[]{first, last};`

-----

## 🔗 Linked Lists

-----

### ⭐ Reverse a Linked List (Easy - Fundamental)

**Problem:** Same as `Fix a Broken Linked List Reversal` previously discussed. This is a fundamental operation.

**Solution:** Refer to the `Fix a Broken Linked List Reversal` solution in the previous response.

-----

### ⭐ Merge K Sorted Lists (Hard - Priority Queue/Divide and Conquer)

**Problem:** You are given an array of `k` linked-lists `lists`, each list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.

**Intuition (Samajh):**
Multiple sorted lists ko merge karna hai. Merge two sorted lists easy hai. `K` lists ke liye:

  * **Brute Force:** Saare nodes ko ek array mein daalo, sort karo, phir new list banao. `O(N log N)`.
  * **Iterative Merging:** Pehli do ko merge karo, phir result ko third se, and so on. `O(K * N)`.
  * **Priority Queue (Min-Heap):** `K` lists ke `K` heads mein se sabse chhota element nikalte jao. `O(N log K)`.
  * **Divide and Conquer:** Lists ko do parts mein divide karo, recursively merge karo, phir do merged lists ko merge karo. `O(N log K)`.

**Approach 1 (Tareeka): Priority Queue (Min-Heap)**

1.  **Initialize:** `PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));`
2.  **Add Heads:** Har non-empty list ke head node ko `minHeap` mein add karo.
3.  **Build Result List:**
      * `dummy = new ListNode(0);`
      * `tail = dummy;`
      * `while (!minHeap.isEmpty())`:
          * `smallestNode = minHeap.poll();`
          * `tail.next = smallestNode;`
          * `tail = tail.next;`
          * `if (smallestNode.next != null)`: `minHeap.add(smallestNode.next);` (Agar smallest node ka next hai, toh use heap mein add karo).
4.  **Return:** `dummy.next;`

**Approach 2 (Tareeka): Divide and Conquer**

1.  `mergeKLists(lists)`:
      * `if (lists == null || lists.length == 0) return null;`
      * `return mergeSort(lists, 0, lists.length - 1);`
2.  `mergeSort(lists, start, end)`:
      * `if (start == end) return lists[start];`
      * `mid = start + (end - start) / 2;`
      * `l1 = mergeSort(lists, start, mid);`
      * `l2 = mergeSort(lists, mid + 1, end);`
      * `return mergeTwoLists(l1, l2);` (Standard merge two sorted lists function)

-----

### ⭐ Copy List with Random Pointer (Medium - Hash Map)

**Problem:** A linked list is given such that each node contains an additional random pointer, which could point to any node in the list, or to `null`. Construct a deep copy of the list.

**Intuition (Samajh):**
Deep copy mein saare nodes naye hone chahiye, aur unke `next` aur `random` pointers bhi naye nodes ko point karne chahiye, original nodes ko nahi. Sirf next pointers ko copy karna easy hai. `random` pointer problem create karta hai kyunki woh future nodes ko point kar sakta hai jo abhi bane hi nahi. **Hash Map** se `old_node -> new_node` mapping store kar sakte hain.

**Approach (Tareeka): Hash Map**

1.  **Initialize:** `Map<Node, Node> oldToNewMap = new HashMap<>();`
2.  **First Pass (Create Nodes):**
      * Iterate through the original list.
      * Har `oldNode` ke liye, ek `newNode = new Node(oldNode.val);` banao.
      * `oldToNewMap.put(oldNode, newNode);`
3.  **Second Pass (Assign Pointers):**
      * Iterate through the original list again.
      * `newNode = oldToNewMap.get(oldNode);`
      * `newNode.next = oldToNewMap.get(oldNode.next);` (Agar `oldNode.next` null hai, toh `get(null)` null hi return karega).
      * `newNode.random = oldToNewMap.get(oldNode.random);`
4.  **Return:** `oldToNewMap.get(original_head);`

**Alternative (Without HashMap - O(1) space):**

1.  Original nodes ke beech mein unki copies insert karo: `A -> A' -> B -> B' -> C -> C'`.
2.  Original random pointers ko use karke copied nodes ke random pointers ko set karo: `A'.random = A.random.next`.
3.  List ko original aur copied parts mein split karo.

-----

### ⭐ Add Two Numbers (Medium - Basic linked list manipulation)

**Problem:** You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

**Intuition (Samajh):**
Linked lists numbers ko reverse order mein store kar rahe hain (e.g., `2 -> 4 -> 3` represents 342). Yeh addition ko easy banata hai, bilkul jaise hum school mein numbers ko right-to-left add karte the. Carry ka dhyan rakhna hai.

**Approach (Tareeka): Simulate Addition**

1.  **Initialize:** `dummyHead = new ListNode(0);` `current = dummyHead;` `carry = 0;`
2.  **Iterate:** `while (l1 != null || l2 != null || carry != 0)`:
      * `val1 = (l1 != null) ? l1.val : 0;`
      * `val2 = (l2 != null) ? l2.val : 0;`
      * `sum = val1 + val2 + carry;`
      * `carry = sum / 10;`
      * `digit = sum % 10;`
      * `current.next = new ListNode(digit);`
      * `current = current.next;`
      * `if (l1 != null) l1 = l1.next;`
      * `if (l2 != null) l2 = l2.next;`
3.  **Return:** `dummyHead.next;`

-----

### ⭐ Flattening a Linked List (Medium/Hard - often seen in India interviews, involves merging sorted lists)

**Problem:** You are given a linked list where each node has two pointers: `next` and `bottom`. The `next` pointer points to the next node in the main list, and the `bottom` pointer points to a sub-linked list that is also sorted. All individual linked lists (main and sub-lists) are sorted. Flatten the list into a single sorted linked list.

**Intuition (Samajh):**
Yeh `Merge K Sorted Lists` ka variation hai, jahan lists tree-like structure mein hain (`next` aur `bottom`). `next` pointer horizontal lists ko join karta hai, `bottom` vertical lists ko join karta hai. Humein saari lists ko ek single sorted list mein merge karna hai. Recursive approach `merge two sorted lists` ko use kar sakta hai.

**Approach (Tareeka): Recursive Merging**

1.  **Base Cases:**
      * `if (head == null || head.next == null) return head;` (Empty list ya single main list node)
2.  **Recursive Step:**
      * `mergedList = flatten(head.next);` (Pehle right side ke saare lists ko flatten karo aur merge karo).
      * `head.next = null;` (Important: `head` ko `mergedList` se merge karne se pehle uska `next` pointer null karo).
      * `result = mergeTwoSortedLists(head, mergedList);` (Current `head` ki `bottom` list aur `mergedList` ko merge karo).
3.  **`mergeTwoSortedLists(l1, l2)` function:** (Standard function for merging two sorted linked lists).
      * Return a new sorted list formed by merging `l1` and `l2`. Remember `next` points to the primary direction, `bottom` for sub-lists. For flattening, think of all `bottom` pointers as `next` pointers during merge. The `mergeTwoSortedLists` function will take two sorted lists (where elements are connected by `bottom` pointers) and merge them into one sorted list (connected by `bottom` pointers).
      * The main `flatten` function essentially takes the current `head`'s `bottom` list and merges it with the flattened version of `head.next`.

<!-- end list -->

```java
// Node definition (example, usually provided)
class Node {
    int data;
    Node next;
    Node bottom;

    Node(int d) {
        data = d;
        next = null;
        bottom = null;
    }
}

class Solution {
    Node flatten(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Recursively flatten the rest of the main list
        Node mergedList = flatten(head.next);

        // Now, merge the current head's bottom list with the mergedList
        // The current head itself becomes the head of its bottom list for this merge
        return mergeTwoLists(head, mergedList);
    }

    // Merges two sorted linked lists (connected by bottom pointers)
    Node mergeTwoLists(Node l1, Node l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        Node result;
        if (l1.data < l2.data) {
            result = l1;
            result.bottom = mergeTwoLists(l1.bottom, l2);
        } else {
            result = l2;
            result.bottom = mergeTwoLists(l1, l2.bottom);
        }
        // Ensure the 'next' pointer of the merged nodes are null or handled appropriately
        // In this flattening problem, only 'bottom' matters for the final flattened list
        result.next = null; // Important: Disconnect the original next links as we are building a vertical list
        return result;
    }
}
```

-----

### ⭐ Palindrome Linked List (Easy)

**Problem:** Given the `head` of a singly linked list, return `true` if it is a palindrome or `false` otherwise.

**Intuition (Samajh):**
Palindrome ka matlab forward aur backward same ho. Linked List mein backward traverse karna mushkil hai.

  * **Brute Force:** List ko array mein copy karo, phir array ko check karo palindrome hai ya nahi. `O(N)` time, `O(N)` space.
  * **Optimized:** List ko do parts mein split karo, second half ko reverse karo, phir compare karo. `O(N)` time, `O(1)` space.

**Approach (Tareeka): Find Middle, Reverse Second Half, Compare**

1.  **Find Middle:** Use two pointers, `slow` and `fast`. When `fast` reaches end, `slow` will be at middle.
      * `slow = head`, `fast = head`;
      * `while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }`
2.  **Reverse Second Half:** `secondHalf = reverseList(slow);` (This `reverseList` is the standard one).
3.  **Compare:**
      * `firstHalf = head;`
      * `while (secondHalf != null)`:
          * `if (firstHalf.val != secondHalf.val) return false;`
          * `firstHalf = firstHalf.next;`
          * `secondHalf = secondHalf.next;`
4.  **Restore (Optional):** Agar original list ko modify nahi karna hai, toh second half ko dobara reverse karke join kar sakte ho.
5.  **Return:** `true`.

-----

## 🌲 Trees

-----

### ⭐ Validate Binary Search Tree (Medium - DFS/BFS, range check)

**Problem:** Same as previously discussed.

**Solution:** Refer to the `Validate Binary Search Tree` solution in the previous response.

-----

### ⭐ Right View of a Binary Tree (Easy/Medium - BFS/DFS with level tracking)

**Problem:** Given the `root` of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

**Intuition (Samajh):**
Har level par, jo sabse rightmost node hoga, wohi dikhega. `BFS (Level Order Traversal)` ya `DFS (Right-first traversal with level tracking)` use kar sakte hain.

**Approach 1 (Tareeka): BFS (Level Order Traversal)**

1.  **Initialize:** `Queue<TreeNode> q = new LinkedList<>();` `List<Integer> result = new ArrayList<>();`
2.  **Add Root:** `if (root == null) return result;` `q.add(root);`
3.  **BFS Loop:** `while (!q.isEmpty())`:
      * `level_size = q.size();`
      * `for i = 0 to level_size - 1`:
          * `node = q.poll();`
          * `if (i == level_size - 1)`: `result.add(node.val);` (Agar yeh level ka last node hai, toh rightmost hai)
          * `if (node.left != null) q.add(node.left);`
          * `if (node.right != null) q.add(node.right);`
4.  **Return:** `result`.

**Approach 2 (Tareeka): DFS (Right-First Preorder Traversal)**

1.  **Initialize:** `List<Integer> result = new ArrayList<>();`
2.  **Helper Function:** `dfs(node, level, result)`:
      * `if (node == null) return;`
      * `if (level == result.size())`: `result.add(node.val);` (Agar yeh level pehli baar encounter hua hai, toh yeh node us level ka pehla rightmost node hoga)
      * `dfs(node.right, level + 1, result);` (Pehle right explore karo)
      * `dfs(node.left, level + 1, result);`
3.  **Initial Call:** `dfs(root, 0, result);`
4.  **Return:** `result`.

-----

### ⭐ Lowest Common Ancestor of a Binary Tree (Medium - DFS)

**Problem:** Given a binary tree, find the lowest common ancestor (LCA) of two given nodes, `p` and `q`. The LCA is defined as the lowest node in the tree that has both `p` and `q` as descendants (where we allow a node to be a descendant of itself).

**Intuition (Samajh):**
LCA dhundhne ke liye, hum recursion (DFS) use kar sakte hain. Jab bhi hum ek node par hote hain, hum check karte hain ki `p` aur `q` uske left subtree mein hain ya right subtree mein, ya woh khud `p` ya `q` hai.

**Approach (Tareeka): Recursive DFS**

1.  **`lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)` function:**
      * **Base Cases:**
          * `if (root == null || root == p || root == q) return root;` (Agar root null hai, ya root khud `p` ya `q` hai, toh root hi answer hai).
      * **Recursive Calls:**
          * `left_lca = lowestCommonAncestor(root.left, p, q);`
          * `right_lca = lowestCommonAncestor(root.right, p, q);`
      * **Combine Results:**
          * `if (left_lca != null && right_lca != null) return root;` (Agar `p` ek subtree mein hai aur `q` doosre subtree mein, toh `root` hi LCA hai).
          * `if (left_lca != null) return left_lca;` (Agar `p` aur `q` dono left subtree mein hain, ya `left_lca` khud `p` ya `q` hai).
          * `else return right_lca;` (Agar `p` aur `q` dono right subtree mein hain, ya `right_lca` khud `p` ya `q` hai).

-----

### ⭐ Construct Binary Tree from Preorder and Inorder Traversal (Medium)

**Problem:** Given two integer arrays `preorder` and `inorder` where `preorder` is the preorder traversal of a binary tree and `inorder` is the inorder traversal of the same tree, construct and return the binary tree.

**Intuition (Samajh):**

  * **Preorder traversal:** `[Root, Left Subtree, Right Subtree]`
  * **Inorder traversal:** `[Left Subtree, Root, Right Subtree]`
    Preorder ka pehla element always `Root` hota hai. Inorder traversal mein root ke left side wale saare elements `Left Subtree` ke hote hain, aur right side wale `Right Subtree` ke. Iss property ko recursively use kar sakte hain.

**Approach (Tareeka): Recursive**

1.  **Helper Function:** `buildTree(preorder, preStart, preEnd, inorder, inStart, inEnd)`
2.  **Base Cases:**
      * `if (preStart > preEnd || inStart > inEnd) return null;`
3.  **Find Root:**
      * `root_val = preorder[preStart];`
      * `root_node = new TreeNode(root_val);`
4.  **Find Root in Inorder:**
      * `root_in_inorder_index = -1;`
      * `for i = inStart to inEnd`:
          * `if (inorder[i] == root_val) { root_in_inorder_index = i; break; }`
5.  **Calculate Subtree Sizes:**
      * `left_subtree_size = root_in_inorder_index - inStart;`
6.  **Recursive Calls:**
      * `root_node.left = buildTree(preorder, preStart + 1, preStart + left_subtree_size, inorder, inStart, root_in_inorder_index - 1);`
      * `root_node.right = buildTree(preorder, preStart + left_subtree_size + 1, preEnd, inorder, root_in_inorder_index + 1, inEnd);`
7.  **Return:** `root_node;`

**Optimization:** Inorder array mein root ka index dhundhne ke liye `HashMap<Integer, Integer> valueToIndexMap` banao taaki `O(1)` mein index mil jaye.

-----

### ⭐ All Nodes Distance K in Binary Tree (Medium - BFS/DFS)

**Problem:** Given the `root` of a binary tree, the value of a target node `target`, and an integer `k`, return an array of the values of all nodes that have a distance `k` from the target node.

**Intuition (Samajh):**
Normal BFS/DFS from `target` node sirf "downwards" search karega. Humein "upwards" bhi search karna hai (target ke ancestors ki taraf). Iske liye tree ko ek "graph" mein convert karna padega jahan har node ka parent bhi accessible ho.

**Approach (Tareeka): Build Adjacency List + BFS**

1.  **Build Parent Pointers (or Adjacency List):**
      * DFS use karke, har node ke liye uska `parent` store karo (`Map<TreeNode, TreeNode> parentMap`). Ya phir ek full adjacency list bana lo jahan `node -> [child1, child2, parent]` ho.
2.  **BFS from Target Node:**
      * `Queue<TreeNode> q = new LinkedList<>();`
      * `Set<TreeNode> visited = new HashSet<>();`
      * `q.add(target);`
      * `visited.add(target);`
      * `distance = 0;`
3.  **BFS Loop:** `while (!q.isEmpty() && distance <= k)`:
      * `level_size = q.size();`
      * `for i = 0 to level_size - 1`:
          * `curr = q.poll();`
          * `if (distance == k) result.add(curr.val);`
          * **Explore Neighbors (children and parent):**
              * `if (curr.left != null && !visited.contains(curr.left)) { q.add(curr.left); visited.add(curr.left); }`
              * `if (curr.right != null && !visited.contains(curr.right)) { q.add(curr.right); visited.add(curr.right); }`
              * `parent = parentMap.get(curr);`
              * `if (parent != null && !visited.contains(parent)) { q.add(parent); visited.add(parent); }`
      * `distance++;`
4.  **Return:** `result`.

-----

### ⭐ Kth Smallest Element in a BST (Medium - Inorder traversal)

**Problem:** Same as previously discussed.

**Solution:** Refer to the `Kth Smallest Element in a BST` solution in the previous response.

-----

### ⭐ Subtree of Another Tree (Easy/Medium)

**Problem:** Given the roots of two binary trees, `root` and `subRoot`, return `true` if `subRoot` is a subtree of `root` of `false` otherwise. A subtree of a binary tree `tree` is `tree` itself or any tree that could be obtained by cutting down a `node` of `tree` and all of its descendants. The subtree should have the same structure and node values as `subRoot`.

**Intuition (Samajh):**
Humein `root` tree mein har possible node par check karna hai ki kya us node se start hone wala subtree `subRoot` ke exactly identical hai.
Yeh do functions involve karega:

1.  `isSameTree(p, q)`: Check if two trees are identical.
2.  `isSubtree(root, subRoot)`: Iterate through `root` and call `isSameTree`.

**Approach (Tareeka): Recursive DFS**

1.  **`isSubtree(root, subRoot)` Function:**

      * `if (root == null) return false;` (Agar root null hai, toh koi subtree nahi mil sakta)
      * `if (isSameTree(root, subRoot)) return true;` (Agar current root hi `subRoot` ke identical hai)
      * `return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);` (Left aur right subtrees mein recursively check karo)

2.  **`isSameTree(p, q)` Function:** (Standard "Same Tree" problem)

      * `if (p == null && q == null) return true;`
      * `if (p == null || q == null || p.val != q.val) return false;`
      * `return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);`

-----

## 🌳 Graphs

-----

### ⭐ Number of Islands (Medium - BFS/DFS)

**Problem:** Same as previously discussed.

**Solution:** Refer to the `Number of Islands` solution in the previous response.

-----

### ⭐ Course Schedule / Course Schedule II (Medium - Topological Sort)

**Problem: Course Schedule**
There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` means you must take course `bi` first if you want to take course `ai`. Return `true` if you can finish all courses, or `false` otherwise.

**Problem: Course Schedule II**
Same as above, but return the order of courses you should take to finish all courses. If it's impossible, return an empty array.

**Intuition (Samajh):**
Prerequisites ka matlab hai ki courses ke beech dependencies hain. Ye graph banata hai jahan courses nodes hain aur prerequisites directed edges hain (`bi -> ai`). "Can finish all courses" ka matlab hai ki graph mein koi **cycle** nahi honi chahiye. "Order of courses" ka matlab hai **Topological Sort**.

**Approach (Tareeka): Topological Sort (BFS - Kahn's Algorithm)**

1.  **Build Graph & Calculate In-degrees:**
      * `adj = new ArrayList[numCourses];` (Adjacency list)
      * `inDegree = new int[numCourses];` (Number of prerequisites for each course)
      * `for each [ai, bi] in prerequisites`:
          * `adj[bi].add(ai);` (Edge from `bi` to `ai`)
          * `inDegree[ai]++;`
2.  **Initialize Queue:**
      * `Queue<Integer> q = new LinkedList<>();`
      * `for i = 0 to numCourses - 1`:
          * `if (inDegree[i] == 0) q.add(i);` (Jin courses ki koi prerequisite nahi, unhein queue mein add karo)
3.  **BFS Loop:**
      * `List<Integer> result = new ArrayList<>();` (For Course Schedule II)
      * `count = 0;` (Number of courses processed)
      * `while (!q.isEmpty())`:
          * `curr = q.poll();`
          * `result.add(curr);` (Add to topological order)
          * `count++;`
          * `for neighbor in adj[curr]`:
              * `inDegree[neighbor]--;`
              * `if (inDegree[neighbor] == 0) q.add(neighbor);` (Agar neighbor ki saari prerequisites complete ho gayi, toh use queue mein add karo)
4.  **Check for Cycle / Return Result:**
      * **Course Schedule:** `return count == numCourses;` (Agar saare courses process ho gaye, toh cycle nahi thi).
      * **Course Schedule II:** `if (count == numCourses) return result.stream().mapToInt(i -> i).toArray();`
          * `else return new int[]{};` (Cycle hai, impossible).

**Alternative (DFS):** Detect cycles using DFS by tracking `visited` states (unvisited, visiting, visited).

-----

### ⭐ Word Ladder / Word Ladder II (Hard - BFS, graph building)

**Problem: Word Ladder**
Given two words, `beginWord` and `endWord`, and a dictionary `wordList`, return the length of the shortest transformation sequence from `beginWord` to `endWord`, such that:

  * Only one letter can be changed at a time.
  * Each transformed word must exist in `wordList`.
    Return 0 if no such sequence exists.

**Problem: Word Ladder II**
Return *all* such shortest transformation sequences.

**Intuition (Samajh):**
"Shortest transformation sequence" implies **BFS**. Words nodes hain, aur agar do words ke beech ek letter ka difference hai, toh unke beech edge hai.
`Word Ladder II` requires all shortest paths, which means BFS ke dauran path reconstruct karna padega ya parent pointers track karne padenge.

**Approach (Tareeka): BFS (for Word Ladder)**

1.  **Initialize:** `Set<String> wordSet = new HashSet<>(wordList);` `if (!wordSet.contains(endWord)) return 0;`
      * `Queue<String> q = new LinkedList<>();`
      * `q.add(beginWord);`
      * `visited = new HashSet<>();` `visited.add(beginWord);`
      * `level = 1;`
2.  **BFS Loop:** `while (!q.isEmpty())`:
      * `level_size = q.size();`
      * `for i = 0 to level_size - 1`:
          * `currWord = q.poll();`
          * `if (currWord.equals(endWord)) return level;`
          * **Generate Neighbors:**
              * `char[] charArr = currWord.toCharArray();`
              * `for j = 0 to charArr.length - 1`:
                  * `originalChar = charArr[j];`
                  * `for char c = 'a' to 'z'`:
                      * `if (c == originalChar) continue;`
                      * `charArr[j] = c;`
                      * `newWord = new String(charArr);`
                      * `if (wordSet.contains(newWord) && !visited.contains(newWord))`:
                          * `visited.add(newWord);`
                          * `q.add(newWord);`
                  * `charArr[j] = originalChar;` (Backtrack char change)
      * `level++;`
3.  **Return:** `0;`

**For Word Ladder II (Harder):**
BFS ke dauran, `Map<String, List<String>> adjMap` banao (word -\> list of words that can reach it in previous level). BFS khatam hone par, endWord se `adjMap` ko use karke saare paths DFS/backtracking se reconstruct karo. Careful with visited sets, for level-by-level BFS, you typically need to manage visited words carefully per level or per step.

-----

### ⭐ Rotting Oranges (Medium - BFS)

**Problem:** You are given an `m x n` grid where each cell can have one of three values: `0` (empty), `1` (fresh orange), `2` (rotten orange). Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten. Return the minimum number of minutes that must elapse until no fresh oranges are present. If this is impossible, return -1.

**Intuition (Samajh):**
"Minimum minutes" aur "spread" ka concept **BFS** mein aata hai. Yeh Multi-Source BFS problem hai kyunki multiple rotten oranges se infection simultaneously spread hoga.

**Approach (Tareeka): Multi-Source BFS**

1.  **Initialize:**
      * `Queue<int[]> q = new LinkedList<>();` (`[row, col]` of rotten oranges)
      * `fresh_oranges_count = 0;`
      * `minutes = 0;`
      * Scan grid: saare rotten oranges ko queue mein add karo, fresh oranges count karo.
2.  **Edge Case:** `if (fresh_oranges_count == 0) return 0;`
3.  **BFS Loop:** `while (!q.isEmpty())`:
      * `level_size = q.size();`
      * `for i = 0 to level_size - 1`:
          * `rotten_orange_pos = q.poll();`
          * Explore 4 neighbors (`dr`, `dc` arrays for directions):
              * `new_r = rotten_orange_pos[0] + dr;`
              * `new_c = rotten_orange_pos[1] + dc;`
              * **Valid & Fresh:** `if (new_r, new_c` valid, in bounds, and `grid[new_r][new_c] == 1` (fresh orange)):
                  * `grid[new_r][new_c] = 2;` (Mark as rotten)
                  * `fresh_oranges_count--;`
                  * `q.add(new int[]{new_r, new_c});`
      * `if (!q.isEmpty()) minutes++;` (Only increment minutes if there were oranges to rot in this minute)
4.  **Final Check:** `if (fresh_oranges_count == 0) return minutes;` `else return -1;` (Agar saare fresh oranges rot nahi hue).

-----

### ⭐ Making a Large Island (Medium - DFS/Union-Find)

**Problem:** You are given an `n x n` binary matrix `grid`. You are allowed to change **at most one** `0` to a `1`. Return the size of the largest island in the grid after applying this operation. If no `0` can be changed, return the size of the largest island present initially.

**Intuition (Samajh):**
Normal "Number of Islands" ki tarah, pehle har island ka size dhundo. Phir, har `0` par jao, us `0` ko `1` banao, aur check karo ki uske neighbors (jo ab `1` hain) kis-kis island se belong karte hain. Un islands ke sizes ko add karo aur `0` ko `1` banane ka 1 bhi add karo. Maximum size track karo.
**Union-Find** bhi use kar sakte hain connected components ko manage karne ke liye, ya DFS multiple times.

**Approach (Tareeka): DFS for Initial Islands + Iterating Zeros**

1.  **Phase 1: Identify and Size Islands:**

      * `island_id = 2;` (Use IDs starting from 2 to distinguish from original 0s and 1s).
      * `Map<Integer, Integer> island_size = new HashMap<>();` (ID -\> Size)
      * `for r, c in grid`:
          * `if (grid[r][c] == 1)`:
              * `current_size = dfs(grid, r, c, island_id);` (DFS to traverse island, mark cells with `island_id`, return size)
              * `island_size.put(island_id, current_size);`
              * `island_id++;`
      * `max_overall_size = (island_size.isEmpty()) ? 0 : Collections.max(island_size.values());` (Initial largest island)

2.  **`dfs(grid, r, c, id)` function:**

      * Base cases for bounds, water, or already visited.
      * Mark `grid[r][c] = id;`
      * `size = 1;`
      * Recursively call for 4 neighbors, add their sizes.
      * `return size;`

3.  **Phase 2: Iterate through Zeros and Connect Islands:**

      * `for r, c in grid`:
          * `if (grid[r][c] == 0)`:
              * `current_connected_size = 1;` (The `0` itself contributes 1)
              * `Set<Integer> connected_islands = new HashSet<>();` (To avoid double-counting if a `0` touches same island multiple times)
              * `for each neighbor (nr, nc) of (r, c)`:
                  * `if (nr, nc` is valid and `grid[nr][nc] > 1)`: (Neighbor is part of an island with ID \> 1)
                      * `island_id_of_neighbor = grid[nr][nc];`
                      * `if (!connected_islands.contains(island_id_of_neighbor))`:
                          * `current_connected_size += island_size.get(island_id_of_neighbor);`
                          * `connected_islands.add(island_id_of_neighbor);`
              * `max_overall_size = Math.max(max_overall_size, current_connected_size);`

4.  **Return:** `max_overall_size`.

      * **Edge Case:** Agar poori grid `0`s ki hai, aur sirf ek `0` ko `1` bana sakte hain, toh max size 1 hoga. If poori grid `1`s ki hai, toh change karne ki zaroorat nahi, `N*N` hi answer hai.

-----

## 🧮 Dynamic Programming (DP)

-----

### ⭐ Coin Change (Medium - classic DP)

**Problem:** You are given an integer array `coins` representing coins of different denominations and an integer `amount` representing a total amount of money. Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

**Intuition (Samajh):**
"Fewest number" suggests optimization, and since we are building up to an amount from smaller amounts, **Dynamic Programming** is a good fit.
`dp[i]` represents the minimum coins needed to make amount `i`.

**Approach (Tareeka): DP Array**

1.  **`dp` Array:** `int[] dp = new int[amount + 1];`
2.  **Initialization:**
      * `Arrays.fill(dp, amount + 1);` (Infinity ki jagah `amount + 1` use karte hain kyunki maximum coins amount se zyada nahi ho sakte for valid solutions).
      * `dp[0] = 0;` (0 amount ke liye 0 coins).
3.  **Fill `dp` Table:**
      * `for a = 1 to amount`:
          * `for coin in coins`:
              * `if (a - coin >= 0)`: (Agar current coin se subtract karke valid previous amount ban raha hai)
                  * `dp[a] = Math.min(dp[a], 1 + dp[a - coin]);` (Current amount ke liye min coins update karo)
4.  **Result:** `return (dp[amount] > amount) ? -1 : dp[amount];` (Agar `dp[amount]` abhi bhi `amount + 1` hai, matlab impossible).

-----

### ⭐ Task Scheduler (Medium - often seen in Online Assessments)

**Problem:** You are given an array of characters `tasks` representing the tasks a CPU needs to do. It contains capital letters A to Z where each letter represents a different task. Tasks can be done in any order. Each task takes one unit of time. For each unit of time, the CPU can either complete one task or be idle. However, there is a non-negative integer `n` that represents the cooldown period between two same tasks (the same letter between two tasks must have at least `n` units of time apart). Return the minimum number of units of time the CPU will take to finish all the given tasks.

**Intuition (Samajh):**
Goal: Minimum time. Cool-down period hai `n`. Most frequent tasks ko pehle place karna chahiye taaki cool-down period mein slots fill ho sakein doosre tasks se. Agar cooldown period mein koi doosra task available nahi hai, toh CPU idle rahega.

**Approach (Tareeka): Greedy (Frequency Based)**

1.  **Count Frequencies:** `int[] freq = new int[26];` Tasks ki frequencies count karo.
2.  **Find Max Frequency:** `maxFreq = 0;` `maxFreqCount = 0;` (Kitne tasks ki frequency `maxFreq` hai)
      * `for f in freq`:
          * `if (f > maxFreq) { maxFreq = f; maxFreqCount = 1; }`
          * `else if (f == maxFreq) { maxFreqCount++; }`
3.  **Calculate Minimum Time:**
      * `partLength = maxFreq - 1;` (Agar max freq task ko `k` baar karna hai, toh `k-1` parts banenge blocks ke)
      * `emptySlots = partLength * n;` (Har part mein `n` empty slots hote hain)
      * `availableTasks = total_tasks - (maxFreq * maxFreqCount);` (Max freq wale tasks ko chhodkar baaki tasks)
      * `time = partLength * (n + 1) + maxFreqCount;` (Base time: `(maxFreq-1)` blocks of `n+1` (maxFreqTask + n idle/other tasks) + last block with `maxFreqCount` tasks).
      * `time = Math.max(time, tasks.length);` (Minimum time ya toh calculated idle slots ke sath hoga, ya total tasks jitna, whichever is greater).

**Example:** `tasks = ["A","A","A","B","B"]`, `n = 2`

  * Freq: A=3, B=2
  * `maxFreq = 3`, `maxFreqCount = 1` (Only 'A' has max freq)
  * `partLength = 3 - 1 = 2`
  * `time = (maxFreq - 1) * (n + 1) + maxFreqCount`
      * `time = 2 * (2 + 1) + 1 = 2 * 3 + 1 = 7`
  * Sequence: `A _ _ A _ _ A` (Min time is 7)
  * Compare with `tasks.length = 5`. `max(7, 5) = 7`.

-----

### ⭐ Minimum Path Sum in a Grid (Medium)

**Problem:** Given an `m x n` grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path. You can only move either down or right at any point in time.

**Intuition (Samajh):**
"Minimum path sum" aur grid traversal, only down/right movement. This is a classic **Dynamic Programming** problem.
`dp[i][j]` will store the minimum path sum to reach `(i, j)`.

**Approach (Tareeka): 2D DP**

1.  **`dp` Array:** `int[][] dp = new int[m][n];`
2.  **Initialization:**
      * `dp[0][0] = grid[0][0];`
      * **First Row:** `for j = 1 to n-1`: `dp[0][j] = dp[0][j-1] + grid[0][j];` (Only move right)
      * **First Column:** `for i = 1 to m-1`: `dp[i][0] = dp[i-1][0] + grid[i][0];` (Only move down)
3.  **Fill `dp` Table:**
      * `for i = 1 to m-1`:
          * `for j = 1 to n-1`:
              * `dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);` (Current cell ka value + minimum of coming from top or left)
4.  **Result:** `return dp[m-1][n-1];`

-----

## 🏗️ Stacks & Queues

-----

### ⭐ LRU Cache (Medium - Design problem, often combines HashMap and Doubly Linked List)

**Problem:** Design a data structure that follows the constraints of a Least Recently Used (LRU) cache. Implement the `LRUCache` class:

  * `LRUCache(int capacity)`: Initializes the LRU cache with given `capacity`.
  * `int get(int key)`: Get the value of the key if the key exists in the cache. Otherwise, return -1.
  * `void put(int key, int value)`: Update the value of the key if it exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.

**Intuition (Samajh):**

  * `get` and `put` should be `O(1)`. This implies a **Hash Map** (`key -> value`).
  * "Least Recently Used" (LRU) policy ka matlab hai ki humein items ka access order maintain karna hai. Jab capacity exceed hoti hai, toh sabse kam use hue item ko remove karna hai. Iske liye **Doubly Linked List** (DLL) best hai, jahan recently used items head ke paas honge aur least recently used items tail ke paas.

**Approach (Tareeka): HashMap + Doubly Linked List**

1.  **Node Class:** `class Node { int key; int value; Node prev; Node next; }`

2.  **`LRUCache` Class Members:**

      * `Map<Integer, Node> cache;` (`key -> Node` in DLL)
      * `Node head;` (Dummy head of DLL, points to most recently used)
      * `Node tail;` (Dummy tail of DLL, points to least recently used)
      * `int capacity;`

3.  **Helper Functions for DLL:**

      * `addNode(Node node)`: Add a node right after `head`.
      * `removeNode(Node node)`: Remove a node from DLL.
      * `moveToHead(Node node)`: Move an existing node to head (remove from current position, add after head).
      * `popTail()`: Remove the node just before `tail` (LRU node).

4.  **`LRUCache(int capacity)` Constructor:**

      * Initialize `cache`, `head`, `tail`, `capacity`. Connect `head` and `tail`.

5.  **`get(int key)`:**

      * `if (cache.containsKey(key))`:
          * `node = cache.get(key);`
          * `moveToHead(node);` (Most recently used ho gaya)
          * `return node.value;`
      * `else return -1;`

6.  **`put(int key, int value)`:**

      * `if (cache.containsKey(key))`:
          * `node = cache.get(key);`
          * `node.value = value;`
          * `moveToHead(node);`
      * `else`:
          * `newNode = new Node(key, value);`
          * `cache.put(key, newNode);`
          * `addNode(newNode);`
          * `if (cache.size() > capacity)`:
              * `tailNode = popTail();`
              * `cache.remove(tailNode.key);`

-----

### ⭐ LFU Cache (Hard - More complex cache design)

**Problem:** Design a data structure that follows the constraints of a Least Frequently Used (LFU) cache.

  * `LFUCache(int capacity)`: Initializes.
  * `int get(int key)`: Returns value. If exists, increments its usage frequency.
  * `void put(int key, int value)`: Updates/Inserts. If number of keys exceeds capacity, evicts the LFU key. If there's a tie, evict the LRU among them.

**Intuition (Samajh):**
LFU LRU se zyada complex hai kyunki ismein do criteria hain: frequency (primary) aur then recency (secondary for tie-breaking).
Humein `key -> value` (HashMap), `key -> frequency` (HashMap), aur frequencies ko track karna hai. Har frequency ke liye us frequency par items ki doubly linked list chahiye (LRU tie-breaking ke liye).

**Approach (Tareeka): Two HashMaps + Doubly Linked Lists of Frequencies**

1.  **Data Structures:**

      * `Map<Integer, Node> cache;` (`key -> actual Node` storing key, value, freq)
      * `Map<Integer, DoublyLinkedList> freqListMap;` (`frequency -> DoublyLinkedList` containing nodes with that frequency)
      * `minFreq;` (Current minimum frequency in the cache)
      * `capacity;`
      * `size;` (Current number of items in cache)

2.  **`Node` Class:** `class Node { int key, value, freq; Node prev, next; }`

3.  **`DoublyLinkedList` Class:** A simple DLL that supports `addNode(node)`, `removeNode(node)`, `getHead()`, `getTail()`, `isEmpty()`. This DLL will store `Node` objects.

4.  **Helper `updateNode(Node node)`:**

      * This is the core logic. When a node is accessed (`get`) or updated (`put`):
      * 1.  Remove node from its `freqListMap.get(node.freq)` DLL.
      * 2.  If that DLL becomes empty and `node.freq` was `minFreq`, increment `minFreq`.
      * 3.  Increment `node.freq`.
      * 4.  Add `node` to `freqListMap.get(node.freq)` DLL. Create new DLL if not exists.

5.  **`LFUCache(int capacity)` Constructor:** Initialize all members.

6.  **`get(int key)`:**

      * `if (!cache.containsKey(key)) return -1;`
      * `node = cache.get(key);`
      * `updateNode(node);`
      * `return node.value;`

7.  **`put(int key, int value)`:**

      * `if (capacity == 0) return;`
      * `if (cache.containsKey(key))`:
          * `node = cache.get(key);`
          * `node.value = value;`
          * `updateNode(node);`
      * `else`:
          * `if (size == capacity)`:
              * `lfuList = freqListMap.get(minFreq);`
              * `nodeToRemove = lfuList.getTail();` (LRU for LFU freq)
              * `lfuList.removeNode(nodeToRemove);`
              * `cache.remove(nodeToRemove.key);`
              * `size--;`
          * `newNode = new Node(key, value);`
          * `newNode.freq = 1;`
          * `cache.put(key, newNode);`
          * `minFreq = 1;` (New node is always freq 1, so minFreq reset)
          * `add new Node to freqListMap.get(1)` (Create if not exists).
          * `size++;`

-----

### ⭐ Valid Parentheses (Easy - Stack)

**Problem:** Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.
An input string is valid if:

1.  Open brackets must be closed by the same type of brackets.
2.  Open brackets must be closed in the correct order.
3.  Every close bracket has a corresponding open bracket of the same type.

**Intuition (Samajh):**
"Last in, first out" property of matching parentheses suggests **Stack**. Jab open bracket mile, stack mein push karo. Jab close bracket mile, stack ke top se pop karo aur check karo ki matching pair hai ya nahi.

**Approach (Tareeka): Stack**

1.  **Initialize:** `Stack<Character> stack = new Stack<>();`
2.  **Iterate String:** `s` ke har `char c` par iterate karo.
3.  **Process Characters:**
      * `if (c == '(' || c == '{' || c == '[')`: `stack.push(c);` (Open brackets ko push karo)
      * `else if (c == ')' || c == '}' || c == ']')`: (Close bracket mila)
          * `if (stack.isEmpty()) return false;` (Stack empty hai matlab koi matching open bracket nahi)
          * `top = stack.pop();`
          * `if (!isMatchingPair(top, c)) return false;` (Matching nahi hai)
4.  **Final Check:** `return stack.isEmpty();` (Loop khatam hone ke baad stack empty hona chahiye, varna unmatched open brackets hain).

**`isMatchingPair(char open, char close)` Helper:**

  * `return (open == '(' && close == ')') || (open == '{' && close == '}') || (open == '[' && close == ']');`

-----

### ⭐ Implement Queue using Stacks (Easy - Design)

**Problem:** Implement a first in, first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

**Intuition (Samajh):**
Stack LIFO hai, Queue FIFO hai. Do stacks se FIFO behavior achieve karna hai.

  * Ek stack `input` ke liye.
  * Ek stack `output` ke liye.
    Jab `pop` ya `peek` karna ho, agar `output` stack empty hai, toh saare elements `input` stack se `output` stack mein transfer karo. Isse elements reverse ho jaenge aur `output` stack ka top element FIFO order mein hoga.

**Approach (Tareeka): Two Stacks**

1.  **Members:** `Stack<Integer> input = new Stack<>();` `Stack<Integer> output = new Stack<>();`
2.  **`push(int x)`:**
      * `input.push(x);` (Directly input stack mein daalo)
3.  **`pop()`:**
      * `peek();` (Pehle sure karo ki `output` stack mein elements hain)
      * `return output.pop();`
4.  **`peek()`:**
      * `if (output.isEmpty())`:
          * `while (!input.isEmpty()) { output.push(input.pop()); }` (Transfer all from input to output)
      * `return output.peek();`
5.  **`empty()`:**
      * `return input.isEmpty() && output.isEmpty();`

-----

## ⛰️ Heaps/Priority Queues

-----

### ⭐ K Closest Points to Origin (Medium - Min-heap)

**Problem:** Given an array of `points` on a 2D plane where `points[i] = [xi, yi]`, return the `k` closest points to the origin `(0, 0)`. The distance between two points `(x1, y1)` and `(x2, y2)` is Euclidean distance, `sqrt((x2 - x1)^2 + (y2 - y1)^2)`. You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).

**Intuition (Samajh):**
"K closest/largest/smallest" type ke problems ke liye **Heaps (Priority Queues)** best hain. Distance calculation mein `sqrt` ki zaroorat nahi hai, sirf `x^2 + y^2` compare kar sakte hain kyunki `sqrt` monotonically increasing function hai.

**Approach (Tareeka): Max-Heap**

1.  **Initialize:** `PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1]));`
      * Max-heap kyun? Kyunki hum `k` sabse chhote elements rakhna chahte hain. Jab koi naya element aata hai, agar woh heap ke top (largest distance) se chhota hai, toh largest distance wale ko remove karke naya element add kar do. Heap size `k` maintain hogi.
2.  **Iterate Points:** Har `point` `[x, y]` par iterate karo.
      * `maxHeap.add(point);`
      * `if (maxHeap.size() > k)`:
          * `maxHeap.poll();` (Remove the point with largest distance if heap size exceeds `k`)
3.  **Result:** `int[][] result = new int[k][2];`
      * `for i = 0 to k-1`: `result[i] = maxHeap.poll();`
4.  **Return:** `result`.

-----

### ⭐ Median of a Number Stream (Medium - Two heaps)

**Problem:** The median is the middle value in an ordered integer list. If the size of the list is even, there is no single middle value, and the median is typically the average of the two middle values.
Design a data structure that supports the following two operations:

  * `void addNum(int num)`: Adds an integer number from the data stream to the data structure.
  * `double findMedian()`: Returns the median of all elements so far.

**Intuition (Samajh):**
Humein stream ke numbers ka median dynamically calculate karna hai. Agar hum numbers ko do parts mein split kar dein: ek part jo median se chhote hain, aur ek part jo median se bade hain.

  * `maxHeap (left_half)`: Stores the smaller half of the numbers, with the largest element at top.
  * `minHeap (right_half)`: Stores the larger half of the numbers, with the smallest element at top.
    Condition: `maxHeap.size()` should be `minHeap.size()` or `minHeap.size() + 1`.

**Approach (Tareeka): Two Heaps**

1.  **Members:**
      * `PriorityQueue<Integer> maxHeap;` (`smaller half`, stores numbers \<= median)
      * `PriorityQueue<Integer> minHeap;` (`larger half`, stores numbers \>= median)
2.  **`MedianFinder()` Constructor:** Initialize heaps.
3.  **`addNum(int num)`:**
      * `maxHeap.add(num);` (Pehle maxHeap mein add karo)
      * `minHeap.add(maxHeap.poll());` (Phir maxHeap ke top (largest small) ko minHeap mein move karo)
      * **Balance Heaps:**
          * `if (maxHeap.size() < minHeap.size())`: `maxHeap.add(minHeap.poll());` (Agar maxHeap chhota ho gaya, toh minHeap se ek element maxHeap mein move karo)
4.  **`findMedian()`:**
      * `if (maxHeap.size() == minHeap.size())`: `return (double)(maxHeap.peek() + minHeap.peek()) / 2.0;`
      * `else`: `return (double)maxHeap.peek();` (MaxHeap mein ek extra element hoga agar odd count hai)

-----

## 📜 Strings

-----

### ⭐ Longest Palindromic Substring (Medium - DP or two pointers)

**Problem:** Given a string `s`, return the longest palindromic substring in `s`.

**Intuition (Samajh):**
"Longest substring" -\> DP ya Sliding Window. "Palindromic" -\> symmetry.

  * Brute force: Har substring generate karo, palindrome check karo. `O(N^3)`.
  * DP: `dp[i][j]` true agar `s[i..j]` palindrome hai. `O(N^2)`.
  * Expand Around Center: Har character (ya har do characters ke beech) ko center maan kar expand karo. `O(N^2)`.

**Approach 1 (Tareeka): Expand Around Center**

1.  **Initialize:** `start = 0`, `max_length = 0`.

2.  **Iterate:** `s` ke har index `i` par iterate karo.

      * `len1 = expandAroundCenter(s, i, i);` (Odd length palindromes, e.g., "aba")
      * `len2 = expandAroundCenter(s, i, i + 1);` (Even length palindromes, e.g., "abba")
      * `current_max_len = Math.max(len1, len2);`
      * `if (current_max_len > max_length)`:
          * `max_length = current_max_len;`
          * `start = i - (current_max_len - 1) / 2;` (Calculate new start index)

3.  **Return:** `s.substring(start, start + max_length);`

4.  **`expandAroundCenter(s, left, right)` function:**

      * `while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right))`:
          * `left--;`
          * `right++;`
      * `return right - left - 1;` (Current palindrome ki length)

**Approach 2 (Tareeka): DP**

1.  **`dp` Array:** `boolean[][] dp = new boolean[N][N];` `dp[i][j]` is true if `s.substring(i, j+1)` is palindrome.
2.  **Base Cases:**
      * Single characters: `dp[i][i] = true;`
      * Two characters: `dp[i][i+1] = (s.charAt(i) == s.charAt(i+1));`
3.  **Fill `dp` Table (Increasing Length):**
      * `for len = 3 to N`:
          * `for i = 0 to N - len`:
              * `j = i + len - 1;`
              * `if (s.charAt(i) == s.charAt(j) && dp[i+1][j-1])`:
                  * `dp[i][j] = true;`
4.  Track `max_len` and `start` while filling `dp`.

-----

### ⭐ Group Anagrams (Medium - Hash Map)

**Problem:** Given an array of strings `strs`, group the anagrams together. You can return the answer in any order. An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Intuition (Samajh):**
Anagrams ka matlab hai ki same characters, same count, bas order alag. Agar hum har word ko "canonical form" mein convert kar sakein (jo saare anagrams ke liye same ho), toh unhein group karna easy hoga Hash Map se. Canonical form typically sorted string hoti hai.

**Approach (Tareeka): Hash Map + Sorted String Key**

1.  **Initialize:** `Map<String, List<String>> anagramGroups = new HashMap<>();`
2.  **Iterate:** `strs` array ke har `str` par iterate karo.
3.  **Create Key:**
      * `char[] charArr = str.toCharArray();`
      * `Arrays.sort(charArr);`
      * `sortedStr = new String(charArr);` (Yeh key banegi map ke liye)
4.  **Group:**
      * `if (!anagramGroups.containsKey(sortedStr))`: `anagramGroups.put(sortedStr, new ArrayList<>());`
      * `anagramGroups.get(sortedStr).add(str);`
5.  **Return:** `new ArrayList<>(anagramGroups.values());`

-----

### ⭐ Most Common Word (Easy/Medium - String parsing, hash map)

**Problem:** Given a paragraph and a list of `banned` words, return the most frequent word that is not in the list of `banned` words. It is guaranteed that there is at least one word that is not banned, and that the answer is unique. Words are case-insensitive. Punctuation is ignored.

**Intuition (Samajh):**
String parsing (lower-casing, removing punctuation) + frequency counting (Hash Map) + filtering (banned list check).

**Approach (Tareeka): String Processing + HashMap**

1.  **Prepare Banned Set:** `Set<String> bannedSet = new HashSet<>(Arrays.asList(banned));` (Faster lookup).
2.  **Process Paragraph:**
      * Convert `paragraph` to lowercase.
      * Replace all non-alphabetic characters with spaces. (Regex can do this: `paragraph.replaceAll("[^a-z]", " ")`)
      * Split the paragraph into words by spaces. `String[] words = paragraph.split(" ");`
3.  **Count Frequencies:**
      * `Map<String, Integer> wordCount = new HashMap<>();`
      * `for word in words`:
          * `if (word.isEmpty()) continue;`
          * `if (!bannedSet.contains(word))`:
              * `wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);`
4.  **Find Most Frequent:**
      * `maxCount = 0;` `resultWord = "";`
      * `for entry in wordCount.entrySet()`:
          * `if (entry.getValue() > maxCount)`:
              * `maxCount = entry.getValue();`
              * `resultWord = entry.getKey();`
5.  **Return:** `resultWord`.

-----

### ⭐ Longest Common Prefix (Easy)

**Problem:** Write a function to find the longest common prefix string amongst an array of strings. If there is no common prefix, return an empty string "".

**Intuition (Samajh):**
Humein sabse lambi string dhundhni hai jo saari input strings ke shuruat mein common ho.

**Approach (Tareeka): Iterative Comparison**

1.  **Base Cases:**
      * `if (strs == null || strs.length == 0) return "";`
      * `if (strs.length == 1) return strs[0];`
2.  **Initialize:** `prefix = strs[0];` (Pehle string ko initial prefix maan lo)
3.  **Iterate Strings:** `for i = 1 to strs.length - 1`:
      * `current_str = strs[i];`
      * `while (current_str.indexOf(prefix) != 0)`: (Agar `prefix` `current_str` ka starting prefix nahi hai)
          * `prefix = prefix.substring(0, prefix.length() - 1);` (Prefix ko chhota karo)
          * `if (prefix.isEmpty()) return "";` (Agar prefix empty ho gaya, matlab koi common prefix nahi)
4.  **Return:** `prefix;`

-----

### ⭐ Reverse Words in a String (Medium)

**Problem:** Given an input string `s`, reverse the order of the words. A word is defined as a sequence of non-space characters. The words in `s` will be separated by at least one space. Return a string of the words in reverse order concatenated by a single space.

**Intuition (Samajh):**
Words ko pehle extract karo, phir unhein reverse order mein join karo. Multiple spaces ko single space mein convert karna hai, aur leading/trailing spaces ko remove karna hai.

**Approach (Tareeka): Split + Reverse + Join**

1.  **Trim & Split:**
      * `s = s.trim();` (Leading/trailing spaces remove karo)
      * `String[] words = s.split("\\s+");` (Multiple spaces `\\s+` se split karo)
2.  **Reverse and Join:**
      * `StringBuilder reversed_s = new StringBuilder();`
      * `for i = words.length - 1 down to 0`:
          * `reversed_s.append(words[i]);`
          * `if (i > 0) reversed_s.append(" ");`
3.  **Return:** `reversed_s.toString();`

-----

## 💡 Other noteworthy problems/types:

-----

### ⭐ Design an Array Statistics Tracker (Hard)

**Problem:** Design a data structure `ArrayStatisticsTracker` that supports:

  * `add(int num)`: Adds a number to the collection.
  * `getMean()`: Returns the mean of all numbers added so far.
  * `getMedian()`: Returns the median of all numbers added so far.
  * `getMode()`: Returns the mode (most frequent number). If multiple modes, any one is fine.

**Intuition (Samajh):**

  * **Mean:** `sum / count`. Easy to track `sum` and `count`.
  * **Median:** `Median of a Number Stream` problem (Two Heaps).
  * **Mode:** `Frequency Map` (`HashMap<Integer, Integer>`). Max frequency track karo.

**Approach (Tareeka): Combine existing solutions**

1.  **Members:**

      * `long sum;` `int count;` (For mean)
      * `PriorityQueue<Integer> maxHeap;` `PriorityQueue<Integer> minHeap;` (For median)
      * `Map<Integer, Integer> freqMap;` (For mode)
      * `int currentMode;` `int maxFreq;` (To efficiently track mode)

2.  **`add(int num)`:**

      * **Mean:** `sum += num; count++;`
      * **Median:** Use `addNum` logic from "Median of a Number Stream".
      * **Mode:**
          * `freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);`
          * `if (freqMap.get(num) > maxFreq)`:
              * `maxFreq = freqMap.get(num);`
              * `currentMode = num;`

3.  **`getMean()`:** `return (double)sum / count;`

4.  **`getMedian()`:** Use `findMedian` logic from "Median of a Number Stream".

5.  **`getMode()`:** `return currentMode;`

-----

### ⭐ Insert Delete GetRandom O(1) (Medium - Design, Array + Hash Map)

**Problem:** Implement the `RandomizedSet` class:

  * `RandomizedSet()`: Initializes the `RandomizedSet` object.
  * `bool insert(int val)`: Inserts `val` into the set. Returns `true` if `val` was not present, `false` otherwise.
  * `bool remove(int val)`: Removes `val` from the set. Returns `true` if `val` was present, `false` otherwise.
  * `int getRandom()`: Returns a random element from the current set of elements (all elements must have equal probability).

**Intuition (Samajh):**

  * `insert`, `delete`, `getRandom` all `O(1)` average time.
  * `getRandom` `O(1)`: Array (or `ArrayList`) se `random.nextInt(size)` se element `O(1)` mein mil jata hai.
  * `insert`, `delete` `O(1)`: `HashSet` mein `O(1)` average time hota hai, par `HashSet` se `getRandom` `O(N)` hai (iterate karna padega).
  * **Combination:** `ArrayList` + `HashMap`.
      * `ArrayList<Integer> list;` (Elements store karega, `getRandom` ke liye)
      * `Map<Integer, Integer> valToIndexMap;` (`value -> its index in the list`, `insert/delete` ke liye)

**Approach (Tareeka): ArrayList + HashMap**

1.  **Members:**
      * `List<Integer> list;`
      * `Map<Integer, Integer> valToIndexMap;`
      * `Random rand;`
2.  **`RandomizedSet()` Constructor:** Initialize members.
3.  **`insert(int val)`:**
      * `if (valToIndexMap.containsKey(val)) return false;`
      * `list.add(val);`
      * `valToIndexMap.put(val, list.size() - 1);`
      * `return true;`
4.  **`remove(int val)`:**
      * `if (!valToIndexMap.containsKey(val)) return false;`
      * `idxToRemove = valToIndexMap.get(val);`
      * `lastElement = list.get(list.size() - 1);`
      * **Crucial Step (O(1) delete):**
          * If `idxToRemove` last element nahi hai, toh last element ko `idxToRemove` par move karo.
          * `list.set(idxToRemove, lastElement);`
          * `valToIndexMap.put(lastElement, idxToRemove);` (Update map for moved element)
      * `list.remove(list.size() - 1);` (Last element remove karo)
      * `valToIndexMap.remove(val);`
      * `return true;`
5.  **`getRandom()`:**
      * `return list.get(rand.nextInt(list.size()));`

-----

### ⭐ Meeting Rooms II (Medium - Sorting, Priority Queue/Greedy)

**Problem:** Given an array of meeting time intervals `intervals` where `intervals[i] = [start, end]`, return the minimum number of conference rooms required.

**Intuition (Samajh):**
Humein rooms allocate karne hain. Jab koi meeting start hoti hai, toh room chahiye. Jab meeting end hoti hai, toh room khali ho jata hai. Minimum rooms chahiye, toh greedy approach use karo.
**Sort by start times.** Phir ek `min-heap` use karo jo current rooms ke `end` times ko track kare.

**Approach (Tareeka): Sort + Min-Heap**

1.  **Sort:** `intervals` ko `start` time ke basis par sort karo.
2.  **Initialize:** `PriorityQueue<Integer> endTimes = new PriorityQueue<>();` (Min-heap stores end times of meetings currently in rooms).
3.  **Iterate Intervals:** Har sorted `interval` `[start, end]` par iterate karo.
      * `if (!endTimes.isEmpty() && start >= endTimes.peek())`:
          * Agar current meeting `start` time pehle ki kisi meeting ke `end` time se bada ya barabar hai, toh woh room reuse ho sakta hai.
          * `endTimes.poll();` (Us room ko khali karo)
      * `endTimes.add(end);` (Current meeting ke `end` time ko add karo)
4.  **Return:** `endTimes.size();` (Heap size indicates rooms needed).

-----

### ⭐ Next Greater Element (Variations, typically Medium)

**Problem (Typical Variation: Next Greater Element I):** You are given two arrays `nums1` and `nums2` where `nums1` is a subset of `nums2`. Find the next greater number for each element of `nums1` in `nums2`. The next greater number of a number `x` in `nums2` is the first greater number to its right in `nums2`. If it does not exist, output -1 for this number.

**Intuition (Samajh):**
"Next greater element to the right" suggests using a **Monotonic Stack**. Stack mein elements decreasing order mein honge. Jab koi naya element aata hai, toh woh stack ke top par chhote elements ka next greater element ban jata hai.

**Approach (Tareeka): Monotonic Stack + HashMap**

1.  **Initialize:**
      * `Map<Integer, Integer> nextGreaterMap = new HashMap<>();` (`number -> its next greater element`)
      * `Stack<Integer> stack = new Stack<>();` (Monotonic decreasing stack)
2.  **Process `nums2`:** `nums2` array ke har element `num` par iterate karo.
      * `while (!stack.isEmpty() && num > stack.peek())`:
          * `nextGreaterMap.put(stack.pop(), num);` (Stack ka top `num` se chhota hai, toh `num` uska next greater element hai)
      * `stack.push(num);` (Current `num` ko stack mein push karo)
3.  **Process `nums1`:**
      * `int[] result = new int[nums1.length];`
      * `for i = 0 to nums1.length - 1`:
          * `result[i] = nextGreaterMap.getOrDefault(nums1[i], -1);` (Map se value retrieve karo, agar nahi mila toh -1)
4.  **Return:** `result`.

-----

### ⭐ Find median from Data Stream (Medium)

**Problem:** Same as `Median of a Number Stream`, previously discussed.

**Solution:** Refer to the `Median of a Number Stream` solution.
