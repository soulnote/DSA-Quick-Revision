## 🔢 Arrays & Sliding Window

---

### ⭐ Find the Largest Sum in a Fixed-Size Subarray (Medium-Hard)

**Problem:** Given an array of integers and a fixed size `k`, find the subarray (continuous part) of that size `k` which has the largest sum.

**Intuition (Samajh):**
Jab bhi "fixed size subarray" ya "continuous subarray" jaisi cheezein aati hain, toh **Sliding Window** technique bohot useful hoti hai. Humein har possible `k` size ke subarray ka sum calculate karna hai, aur usmein se maximum nikalna hai. Brute force mein har subarray ko nikal kar sum karenge, jo `O(N*K)` hoga. Sliding Window se hum `O(N)` mein kar sakte hain.

**Approach (Tareeka): Sliding Window**
1.  **Initialize:** `current_sum = 0`, `max_sum = Integer.MIN_VALUE`.
2.  **First Window:** Pehle `k` elements ka sum calculate karo aur use `current_sum` aur `max_sum` mein store karo.
3.  **Slide Window:** Array ke baaki elements ke through iterate karo (index `k` se `N-1` tak).
    * Har step par, window ko ek position aage slide karo.
    * `current_sum` mein se window ka **pehla element** (jo ab window se bahar ja raha hai) minus karo.
    * `current_sum` mein window ka **naya element** (jo ab window mein enter kar raha hai) plus karo.
    * `max_sum = Math.max(max_sum, current_sum);`
4.  **Return:** `max_sum`

**Example:** `arr = [1, 4, 2, 10, 2, 3, 1, 0, 20]`, `k = 3`
1.  `[1, 4, 2]` -> `sum = 7`, `max_sum = 7`
2.  Slide: Remove 1, Add 10. `[4, 2, 10]` -> `sum = 7 - 1 + 10 = 16`, `max_sum = 16`
3.  Slide: Remove 4, Add 2. `[2, 10, 2]` -> `sum = 16 - 4 + 2 = 14`, `max_sum = 16`
4.  ... continue ...
5.  `[1, 0, 20]` -> `sum = 4 + 19 = 21`, `max_sum = 21`

---

### ⭐ Sliding Window Maximum (Hard)

**Problem:** Given an array `nums` aur ek window size `k`, return the *sliding window maximum* for each window. Yani, jab window slide karegi, har baar us window ka maximum element kya hai, woh batao.

**Intuition (Samajh):**
Har window ke liye maximum nikalna hai. Simple approach `O(N*K)` hoga. Better `O(N)` solution ke liye humein kuch structure maintain karna hoga jo efficiently window ke max ko de sake. Yahan **Deque (Double-Ended Queue)** kaam aati hai. Deque mein hum window ke "potentially maximum" elements ko decreasing order mein store karenge.

**Approach (Tareeka): Deque (Double-Ended Queue)**
1.  **Initialize:** `Deque<Integer> deque = new LinkedList<>();` (stores indices), `List<Integer> result = new ArrayList<>();`
2.  **First Window (initial `k` elements):**
    * Har element `nums[i]` ke liye (jahan `i` `0` se `k-1` tak hai):
        * **Clean Deque:** Deque ke pichhe se un indices ko hatao jinpar present elements `nums[i]` se chhote hain. Kyunki `nums[i]` bada hai, pichhle chhote elements kabhi maximum nahi ban payenge.
        * **Add to Deque:** `i` ko deque ke pichhe add karo.
3.  **Slide Window (remaining elements):**
    * Index `i` `k` se `N-1` tak iterate karo.
    * **Add Max of Current Window:** `result.add(nums[deque.peekFirst()]);` (Deque ka front element current window ka maximum hoga).
    * **Remove Out-of-Window Elements:** Deque ke front se un indices ko hatao jo ab current window (`i-k`) se bahar ho gaye hain. `while (!deque.isEmpty() && deque.peekFirst() <= i - k)`
    * **Clean Deque (current element `nums[i]`):** Deque ke pichhe se un indices ko hatao jinpar elements `nums[i]` se chhote hain.
    * **Add to Deque:** `i` ko deque ke pichhe add karo.
4.  **Last Window:** Last window ka maximum add karo: `result.add(nums[deque.peekFirst()]);`
5.  **Return:** `result` as an `int[]` array.

**Key Idea of Deque:** Deque stores indices of elements in the current window in decreasing order of their values.
* **Front of Deque:** Always holds the index of the maximum element in the current window.
* **Back of Deque:** When a new element comes, it removes all smaller elements from the back because they can never be the maximum after the new larger element.

---

### ⭐ Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit (LeetCode hard, often solved with two deques for min/max)

**Problem:** Given an array of integers `nums` and an integer `limit`, return the size of the longest continuous subarray such that the absolute difference between any two elements in that subarray is less than or equal to `limit`.

**Intuition (Samajh):**
"Longest continuous subarray" indicates a **Sliding Window** problem. But yahan constraint "absolute difference between *any two pairs* <= `limit`" hai. Iska matlab hai ki subarray ke **maximum** aur **minimum** element ka difference `limit` se zyada nahi hona chahiye. `max_val - min_val <= limit`.

Har window ke liye maximum aur minimum ko efficiently nikalne ke liye do Deques (ek max ke liye, ek min ke liye) use karenge.

**Approach (Tareeka): Sliding Window with Two Deques**
1.  **Initialize:** `max_deque = new LinkedList<>();`, `min_deque = new LinkedList<>();` (both store indices). `left = 0`, `max_len = 0`.
2.  **Iterate with `right` pointer:** `right` pointer ko `0` se `N-1` tak move karo.
3.  **Maintain Deques:**
    * **`max_deque`:** Pichhe se un indices ko hatao jinpar elements `nums[right]` se chhote hain. Phir `right` ko `max_deque` ke pichhe add karo.
    * **`min_deque`:** Pichhe se un indices ko hatao jinpar elements `nums[right]` se bade hain. Phir `right` ko `min_deque` ke pichhe add karo.
4.  **Check Window Validity:** `while (nums[max_deque.peekFirst()] - nums[min_deque.peekFirst()] > limit)`:
    * Agar current window valid nahi hai (max - min > limit), toh `left` pointer ko aage badhao.
    * `left` ko increment karne se pehle, deque ke front se un indices ko remove karo jo `left` se chhote hain (window se bahar ho gaye hain). `if (max_deque.peekFirst() == left) max_deque.removeFirst();` `if (min_deque.peekFirst() == left) min_deque.removeFirst();`
    * `left++`;
5.  **Update Max Length:** `max_len = Math.max(max_len, right - left + 1);`
6.  **Return:** `max_len`

---

### ⭐ Find all anagrams in a string (Medium)

**Problem:** Given two strings, `s` and `p`, find all the start indices of `p`'s anagrams in `s`.

**Intuition (Samajh):**
"Anagram" aur "substring" implies **Sliding Window** combined with **frequency maps/arrays**. Anagram ka matlab hai ki characters aur unki count same honi chahiye, bas order alag ho sakta hai. Hum `p` ki character frequencies calculate karenge, aur phir `s` mein `p` ki length ki window ko slide karenge, har window ki frequencies ko `p` ki frequencies se compare karenge.

**Approach (Tareeka): Sliding Window with Frequency Arrays**
1.  **Pre-check:** Agar `s` ki length `p` se kam hai, toh koi anagram possible nahi, empty list return karo.
2.  **Frequency Maps:**
    * `p_freq = new int[26];` (for lowercase English letters)
    * `window_freq = new int[26];`
    * `p` ke saare characters ki frequencies `p_freq` mein store karo.
3.  **First Window (initial `p.length()` characters):**
    * `s` ke pehle `p.length()` characters ki frequencies `window_freq` mein store karo.
    * `if (Arrays.equals(p_freq, window_freq))` then `result.add(0)`.
4.  **Slide Window:** `left = 0`, `right = p.length()` se `s.length()-1` tak iterate karo.
    * `char_to_remove = s.charAt(left);`
    * `char_to_add = s.charAt(right);`
    * `window_freq[char_to_remove - 'a']--;`
    * `window_freq[char_to_add - 'a']++;`
    * `left++;`
    * `if (Arrays.equals(p_freq, window_freq))` then `result.add(left)`.
5.  **Return:** `result`

**Optimization:** `Arrays.equals` har baar `O(26)` leta hai. Isse bachne ke liye, ek `count` variable use kar sakte hain jo match hone wale characters ki count track kare. Jab `count == 26` ho, toh anagram mil gaya.

---

### ⭐ Given an array of integers and a number K, find the length of the longest subarray in which the absolute difference between any two pairs is less than or equal to K.

**Problem:** Same as "Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit". The wording is slightly different but the core problem is identical. "Absolute difference between any two pairs" means `max_val - min_val <= K`.

**Solution:** Refer to the solution for "Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit" above.

---

## 🔗 Linked Lists

---

### ⭐ Fix a Broken Linked List Reversal (Medium)

**Problem:** Linked List reversal code mein bugs hain. Unhein theek karo ya ek correct reversal function likho.

**Intuition (Samajh):**
Linked List reversal ek classic interview question hai. Ismein teen pointers use hote hain: `prev`, `curr`, aur `next_temp`. Har step par `curr` node ke `next` pointer ko `prev` ki taraf point karana hota hai.

**Approach (Tareeka): Iterative Reversal**
1.  **Pointers:** Initialize `prev = null`, `curr = head`.
2.  **Traversal:** `while (curr != null)` loop chalao.
3.  **Reverse Link:**
    * `next_temp = curr.next;` (current node ke next ko store kar lo, varna link toot jayega)
    * `curr.next = prev;` (current node ka next ab pichhle node ko point karega)
    * `prev = curr;` (prev ko current position par move karo)
    * `curr = next_temp;` (current ko next position par move karo)
4.  **Return:** Loop khatam hone par, `prev` naye head ko point karega. `return prev;`

**Common Bugs in broken code:**
* Incorrect handling of `curr.next` before changing it.
* Not updating `prev` and `curr` correctly.
* Not returning the correct new head.
* Edge cases like empty list or single node list.

---

## 🌳 Graphs

---

### ⭐ Find the shortest path with multiple constraints (Modified Dijkstra's algorithm)

**Problem:** Given a graph, find the shortest path between two nodes, but with additional constraints. For example, "shortest path that visits an intermediate node X", or "shortest path with a maximum allowed fuel/cost", or "shortest path that visits all nodes in a given set".

**Intuition (Samajh):**
Normal Dijkstra's algorithm shortest path by distance nikalta hai. Jab multiple constraints add hote hain, toh humein Dijkstra ke state definition ko modify karna padta hai. Dijkstra states typically are `(distance, node)`. Ab, `(distance, node, constraint1_value, constraint2_value, ...)` ho jayenge. Priority Queue `(PQ)` mein comparison bhi in new states ke hisab se hoga.

**Approach (Tareeka): Modified Dijkstra's**
1.  **State Definition:** Define the state for your Dijkstra's. Example: `(current_distance, current_node, current_fuel, num_visited_special_nodes)`.
2.  **`dist` Array/Map:** Ab `dist` array sirf `dist[node]` nahi hoga. Yeh `dist[node][constraint1_value][constraint2_value]` type ka hoga, ya `Map<State, Integer>` jaisa. Initialize sabko infinity se.
3.  **Priority Queue:** `PriorityQueue<State>` banegi. Comparison logic bhi modified state ke hisab se hoga (e.g., pehle distance, phir fuel, etc.).
4.  **Dijkstra's Logic:**
    * PQ mein start state add karo.
    * Jab tak PQ empty nahi hai:
        * Sabse chhota `(distance, node, ...)` state extract karo.
        * Agar yeh already processed (visited in a better way) hai, continue karo.
        * Neighbors ko explore karo: Har neighbor ke liye, naye state ki calculation karo (distance, constraint values update karke).
        * Agar naya path better hai, toh `dist` array update karo aur naye state ko PQ mein add karo.
5.  **Final Answer:** Target node par pahunchne ke baad, `dist` array mein se minimum value dhundo jo saari constraints ko satisfy karti ho.

**Example (Shortest path with maximum `K` fuel):**
State: `(fuel_spent, node, distance)` (Priority on fuel spent, then distance).
`dist[node][fuel_spent]` stores min distance.
When moving from `u` to `v` with edge weight `w`:
New fuel = `current_fuel + w`. If `new_fuel <= K` and `current_distance + w < dist[v][new_fuel]`, update and add to PQ.

---

### ⭐ Real-Time Traffic System (Modeling traffic as a graph with dynamic edge weights and finding the fastest route)

**Problem:** Design a system that finds the fastest route in a city where traffic conditions (edge weights) are constantly changing.

**Intuition (Samajh):**
City ko graph se model karenge: intersections as **nodes**, roads as **edges**. Traffic jam ke hisab se edge weights (travel time) change honge, so edge weights are **dynamic**. "Fastest route" implies shortest path problem (by time). "Real-time" ka matlab hai ki system ko updates receive karne chahiye aur queries ka jaldi jawab dena chahiye.

**Approach (Tareeka):**
1.  **Graph Representation:**
    * **Nodes:** Intersections (e.g., using IDs).
    * **Edges:** Roads between intersections. Store `(u, v, current_travel_time)`.
2.  **Dynamic Edge Weights:**
    * **Traffic Sensors/APIs:** Data sources jo har road segment par real-time speed/congestion provide karte hain.
    * **Weight Update Mechanism:** Jab traffic data receive ho, corresponding edge weights ko update karo. Iske liye `ConcurrentHashMap<Edge, Double>` ya database use kar sakte hain.
    * **Frequency:** Updates kitni baar honge (e.g., har 30 seconds, 1 minute)?
3.  **Finding Fastest Route (Shortest Path Algorithm):**
    * **Dijkstra's:** Best choice for single-source shortest path with non-negative edge weights.
    * **Optimization for Real-time:**
        * **Incremental Updates:** Instead of recalculating all paths, if a few edge weights change, can we update paths incrementally? (Complex, often not practical for full re-calc).
        * **Frequent Re-computation:** For critical queries, always run Dijkstra. For less critical, maybe use cached results and re-compute periodically.
        * **Pre-computation (if possible):** For common routes, pre-compute and store, then update if relevant edge weights change drastically.
        * **A* search:** If destination is known, A* can be faster than Dijkstra using heuristics.
4.  **System Architecture:**
    * **Data Ingestion Layer:** Traffic data receive karne ke liye (e.g., Kafka, message queues).
    * **Graph Service:** Graph data ko maintain karne aur update karne ke liye.
    * **Routing Service:** Shortest path queries ko handle karne ke liye.
    * **Caching Layer:** Frequent queries ke results cache karne ke liye.
    * **Load Balancing & Scalability:** Multiple instances of services to handle high load.

---

### ⭐ Bus Routes (Highly frequent)

**Problem:** Given a list of bus routes, where each `routes[i]` is a list of bus stops on that route. Also given `source` and `target` bus stops. Find the minimum number of buses you need to take to travel from `source` to `target`. If impossible, return -1.

**Intuition (Samajh):**
Yeh shortest path problem hai, lekin nodes kya hain? Stops ya routes? Agar stops nodes hain, toh ek stop se doosre stop tak jaane ke liye agar ek hi bus se ja rahe hain, toh cost 0, agar bus change ki, toh cost 1. Yeh thoda complex hai.

Easier approach: **Routes ko nodes mano**.
* **Nodes:** Har bus route ek node hai.
* **Edges:** Do routes ke beech edge tab hoga jab unmein koi **common stop** ho. Cost of edge is 1 (ek bus change).
* **Goal:** `source` stop se `target` stop tak pahunchne ke liye minimum routes (buses) lagengi.

**Approach (Tareeka): BFS on Routes**
1.  **Preprocessing (Build Graph):**
    * **`stopToRoutes` Map:** Ek `Map<Integer, List<Integer>> stopToRoutes` banao. Yeh map har stop ID ko un routes ki list se map karega jo us stop se guzarte hain.
    * **Graph of Routes:** `Map<Integer, List<Integer>> adj` jahan `adj[route_id]` mein woh saare routes honge jinke saath `route_id` common stops share karta hai. `adj[i]` ko populate karne ke liye, `routes[i]` ke har stop par jao, `stopToRoutes` se us stop se guzarte saare routes lao, aur unhein `adj[i]` mein add karo.
2.  **BFS:**
    * `Queue<Integer> q` (stores `route_id`).
    * `Set<Integer> visitedRoutes` (to avoid cycles and redundant work).
    * `int buses = 0`.
    * **Initial step:** Saare routes dhundo jo `source` stop se guzarte hain (`stopToRoutes.get(source)`). Un sabko `q` mein add karo aur `visitedRoutes` mein mark karo. `buses = 1`.
    * **If `source == target`:** `0` return karo (no buses needed).
    * **BFS Loop:**
        * Level by level traverse karo. Har level `buses` count increase karega.
        * Current route nikalo `r`. `routes[r]` ke saare stops par iterate karo.
        * Agar koi stop `target` ke barabar hai, `buses` return karo.
        * Agar nahi, toh `stopToRoutes` use karke un saare routes `next_r` ko dhundo jo is stop se guzarte hain.
        * Agar `next_r` `visitedRoutes` mein nahi hai, toh use `q` mein add karo aur `visitedRoutes` mark karo.
3.  **Return:** Loop khatam hone par bhi `target` nahi mila, toh `-1` return karo.

---

### ⭐ Number of Islands

**Problem:** Given a 2D grid of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.

**Intuition (Samajh):**
Yeh graph traversal problem hai, jahan grid cells nodes hain aur adjacent land cells ke beech edges hain. Har island ek connected component hai. Humein connected components ki count karni hai. BFS ya DFS, koi bhi use kar sakte hain.

**Approach (Tareeka): DFS**
1.  **Initialize:** `count = 0`.
2.  **Iterate Grid:** `grid` ke har cell `(r, c)` par iterate karo.
3.  **Find Land:** `if (grid[r][c] == '1')`: Agar current cell land hai:
    * `count++`; (Naya island mila)
    * **`dfs(grid, r, c)`:** DFS function call karo jo is island ke saare connected land cells ko visit karega aur unhein '0' (water) mein convert kar dega, taaki future iterations mein unhein dobara count na kiya ja sake.
4.  **`dfs(grid, r, c)` function:**
    * **Base Cases:**
        * `if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == '0') return;` (Boundary checks aur agar water hai ya already visited).
    * **Mark Visited:** `grid[r][c] = '0';` (Mark current land cell as visited by converting it to water).
    * **Explore Neighbors:** Recursively call `dfs` for all 4 adjacent cells: `(r+1, c)`, `(r-1, c)`, `(r, c+1)`, `(r, c-1)`.
5.  **Return:** `count`.

---

## 🌲 Trees

---

### ⭐ Validate Binary Search Tree (Medium, with follow-ups on iterative vs. recursive)

**Problem:** Given the root of a binary tree, determine if it is a valid Binary Search Tree (BST).
* Left subtree ke saare nodes ki value root se kam honi chahiye.
* Right subtree ke saare nodes ki value root se zyada honi chahiye.
* Left aur Right subtrees bhi valid BST hone chahiye.
* Koi duplicates nahi hone chahiye.

**Intuition (Samajh):**
BST validation ke liye sirf immediate children ko compare karna کافی nahi hai. Poore subtree ki range check karni padti hai. Yani, left subtree ke saare elements `root.val` se chhote hon, aur right subtree ke saare elements `root.val` se bade hon.

**Approach (Tareeka): Recursive with Min/Max Bounds**
1.  **Helper Function:** `isValidBST(TreeNode node, long minVal, long maxVal)`
2.  **Base Case:** `if (node == null) return true;` (Empty tree/subtree is a valid BST).
3.  **Current Node Check:** `if (node.val <= minVal || node.val >= maxVal) return false;` (Current node apni allowed range ke bahar hai).
4.  **Recursive Calls:**
    * Left subtree: `return isValidBST(node.left, minVal, node.val) &&`
    * Right subtree: `isValidBST(node.right, node.val, maxVal);`
    * Initial call: `isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);` (ya Integer.MIN/MAX_VALUE, depends on problem constraints on node.val).

**Follow-ups:**

* **Iterative Approach (In-order Traversal):**
    * A BST's in-order traversal (left -> root -> right) always produces elements in **sorted ascending order**.
    * Ek Stack use karke iterative in-order traversal karo.
    * Ek `long prev = Long.MIN_VALUE;` (ya Integer.MIN_VALUE) maintain karo.
    * Jab bhi koi node visit (process) ho (stack se pop ho), check karo `if (node.val <= prev) return false;`.
    * `prev = node.val;`
    * Agar traversal complete ho gaya bina `false` return kiye, toh `true`.

---

### ⭐ Kth Smallest Element in a BST

**Problem:** Given the root of a Binary Search Tree (BST) and an integer `k`, return the `k`th smallest value (1-indexed) of all the values in the tree.

**Intuition (Samajh):**
BST ki property ka use karo: **In-order traversal of a BST gives elements in sorted ascending order.** Toh, in-order traversal karte jao aur `k`th element milte hi return kar do.

**Approach (Tareeka): In-order Traversal (Recursive or Iterative)**

**Recursive:**
1.  **Helper Function:** `inorder(TreeNode node, List<Integer> list)` jo saare elements list mein add kare.
2.  **Main Function:** `inorder(root, list);`
3.  `return list.get(k-1);` (Agar `k` 1-indexed hai).
**Drawback:** Poori list banane ki zaroorat nahi.

**Optimized Recursive:**
1.  Global `count = 0`, `result = -1`.
2.  **Helper Function:** `inorder(TreeNode node, int k)`
    * `if (node == null) return;`
    * `inorder(node.left, k);`
    * `count++;`
    * `if (count == k) { result = node.val; return; }`
    * `inorder(node.right, k);`

**Iterative (Preferred):**
1.  `Stack<TreeNode> stack = new Stack<>();`
2.  `TreeNode curr = root;`
3.  `while (curr != null || !stack.isEmpty())`:
    * `while (curr != null)`: `curr` ko stack mein push karo aur left jao: `stack.push(curr); curr = curr.left;`
    * `curr = stack.pop();` (Sabse chhota element)
    * `k--;`
    * `if (k == 0) return curr.val;`
    * `curr = curr.right;` (Right subtree mein jao)
4.  (Should not reach here if `k` is valid)

---

### ⭐ Construct Quad Tree

**Problem:** Given a `n x n` grid of `0`s and `1`s, construct a Quad Tree for it. A Quad Tree is a tree where each node represents a square grid.
* If all values in the square are the same (all 0s or all 1s), the node is a leaf node, and its `isLeaf` property is true. Its `val` is the value (0 or 1).
* Otherwise, the node is not a leaf, its `isLeaf` is false, and it has four children representing four equal-sized sub-squares: `topLeft`, `topRight`, `bottomLeft`, `bottomRight`.

**Intuition (Samajh):**
Yeh problem **Divide and Conquer** principle par based hai. Grid ko repeatedly four sub-grids mein divide karte jao jab tak ek sub-grid mein saare values same na ho jayein. Har division ek naya non-leaf node banayega, aur har homogenous sub-grid ek leaf node banayega.

**Approach (Tareeka): Recursive Divide and Conquer**
1.  **`Node` Definition:** Problem statement mein `Node` class provide ki jaegi, jismein `val`, `isLeaf`, aur four children `topLeft`, `topRight`, `bottomLeft`, `bottomRight` honge.
2.  **`build(grid, row, col, size)` Function:** Yeh function `(row, col)` se start hone wale `size x size` ke square ke liye Quad Tree node banayega.
3.  **Base Cases:**
    * **Check Homogeneity:** Ek helper function `isSame(grid, row, col, size)` banao jo check kare ki kya `size x size` square ke saare values same hain.
    * `if (isSame(grid, row, col, size))`:
        * `return new Node(grid[row][col] == 1, true);` (Value 1 toh true, 0 toh false, `isLeaf` true).
4.  **Recursive Step (Divide):**
    * `Node node = new Node(false, false);` (Default values, will be overridden by children).
    * `half = size / 2;`
    * `node.topLeft = build(grid, row, col, half);`
    * `node.topRight = build(grid, row, col + half, half);`
    * `node.bottomLeft = build(grid, row + half, col, half);`
    * `node.bottomRight = build(grid, row + half, col + half, half);`
    * `return node;`

---

## 🏗️ Stacks & Queues

---

### ⭐ Maximum Frequency Stack (Hard)

**Problem:** Design a stack-like data structure that supports two operations:
* `push(val)`: Pushes an integer `val` onto the stack.
* `pop()`: Removes and returns the most frequent element. If there's a tie for most frequent, remove the element closest to the top of the stack.

**Intuition (Samajh):**
Normal stack operations `O(1)` hain. Yahan "most frequent" aur "closest to top" criteria hain, jo simple stack se nahi ho sakte. Humein frequency track karni hogi aur order bhi maintain karna hoga.
* **Frequency:** `Map<Integer, Integer> freqMap` (`value -> count`)
* **Order (for tie-breaking):** Stack-like behavior.

Agar hum multiple stacks use karein, har stack ek particular frequency ke elements ko hold kare?
* `Map<Integer, Stack<Integer>> freqStackMap` (`frequency -> stack of elements at that frequency`).
* `maxFreq` variable track karega current highest frequency.

**Approach (Tareeka): Map of Stacks**
1.  **Data Structures:**
    * `Map<Integer, Integer> freqMap;` (`value -> its current frequency`)
    * `Map<Integer, Stack<Integer>> freqStackMap;` (`frequency -> stack of elements with that frequency`)
    * `int maxFreq;` (Current maximum frequency found)

2.  **`push(int val)`:**
    * `freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);` (Update frequency of `val`).
    * `currentFreq = freqMap.get(val);`
    * **Update `maxFreq`:** `maxFreq = Math.max(maxFreq, currentFreq);`
    * **Add to `freqStackMap`:**
        * `if (!freqStackMap.containsKey(currentFreq)) { freqStackMap.put(currentFreq, new Stack<>()); }`
        * `freqStackMap.get(currentFreq).push(val);`

3.  **`pop()`:**
    * `Stack<Integer> topFreqStack = freqStackMap.get(maxFreq);` (Sabse zyada frequency wala stack).
    * `poppedVal = topFreqStack.pop();` (Is stack se top element nikalo, yehi most frequent aur closest to top hai).
    * **Update `freqMap`:** `freqMap.put(poppedVal, freqMap.get(poppedVal) - 1);`
    * **Update `maxFreq`:** `if (topFreqStack.isEmpty()) { maxFreq--; }` (Agar highest frequency wala stack empty ho gaya, toh `maxFreq` ko decrement karo).
    * `return poppedVal;`

---

### ⭐ Thread-Safe Circular Buffer (Hard, involving mutex locks and reducing lock contention)

**Problem:** Design a circular buffer (fixed-size queue) that can be accessed safely by multiple threads (producer/consumer model). Focus on thread safety and reducing lock contention.

**Intuition (Samajh):**
Circular buffer (ya Ring Buffer) ek queue hai jahan fixed capacity hoti hai aur elements wrap around hote hain. "Thread-safe" ka matlab hai ki multiple threads (e.g., producers add data, consumers remove data) bina data corruption ke access kar saken. "Reducing lock contention" ka matlab hai ki locks ko kam se kam time ke liye hold kiya jaye.

**Approach (Tareeka): Mutex Locks & Condition Variables**
1.  **Buffer Structure:**
    * `T[] buffer;` (Array to store elements)
    * `int capacity;`
    * `int head;` (Index for consumer)
    * `int tail;` (Index for producer)
    * `int count;` (Current number of elements in buffer)
2.  **Synchronization Primitives:**
    * `Lock lock = new ReentrantLock();` (Mutual exclusion for critical sections)
    * `Condition notFull = lock.newCondition();` (Producers wait here if buffer is full)
    * `Condition notEmpty = lock.newCondition();` (Consumers wait here if buffer is empty)
3.  **`enqueue(T data)` (Producer):**
    * `lock.lock();`
    * `try {`
        * `while (count == capacity) { notFull.await(); }` (If full, wait)
        * `buffer[tail] = data;`
        * `tail = (tail + 1) % capacity;`
        * `count++;`
        * `notEmpty.signalAll();` (Signal consumers that buffer is not empty)
    * `} finally { lock.unlock(); }`
4.  **`dequeue()` (Consumer):**
    * `lock.lock();`
    * `try {`
        * `while (count == 0) { notEmpty.await(); }` (If empty, wait)
        * `T data = buffer[head];`
        * `head = (head + 1) % capacity;`
        * `count--;`
        * `notFull.signalAll();` (Signal producers that buffer is not full)
        * `return data;`
    * `} finally { lock.unlock(); }`

**Reducing Lock Contention:**
* **Fine-grained locking (Advanced):** Instead of one lock for everything, use separate locks for head and tail (if possible, though tricky for circular buffer).
* **Wait-free algorithms (Complex):** Use atomic operations (e.g., `AtomicInteger` for `head`/`tail`) to avoid locks entirely in some cases, but circular buffer for general types is hard to make wait-free.
* **Batching:** Producers/consumers ek baar mein multiple items add/remove karein, jisse lock-acquire/release overhead kam ho.

---

### ⭐ Priority Queue questions (e.g., "Furthest Building You Can Reach" - LeetCode Medium/Hard)

**Problem: Furthest Building You Can Reach**
You are given an integer array `heights` representing the heights of buildings, some `bricks`, and some `ladders`. You can go from building `i` to building `i+1` if `heights[i+1] <= heights[i]` (no cost) or if `heights[i+1] > heights[i]` (climb cost).
For a climb `d = heights[i+1] - heights[i]`, you can either use `d` bricks or one ladder.
Return the furthest building index (0-indexed) you can reach.

**Intuition (Samajh):**
Goal: Furthest building. Jab bhi climb aata hai, humein decide karna hai bricks use karein ya ladder. Ladders valuable hain, kyunki woh kisi bhi height difference ko cover kar sakte hain. Bricks limited hain. Hum greedy approach use karenge.

Hum hamesha bricks use karenge jab tak possible ho. Agar bricks khatam ho jate hain, aur humein ek aur badi climb karni hai, tab hum apne past ke sabse chhoti brick-use wali climb ko find karenge, use ladder se replace karenge, aur woh chhoti climb ke bricks wapas le lenge.

**Approach (Tareeka): Greedy with Min-Heap (Priority Queue)**
1.  **Data Structure:** `PriorityQueue<Integer> pq = new PriorityQueue<>();` (Min-heap to store the height differences for which we used bricks).
2.  **Iterate Buildings:** `i` ko `0` se `heights.length - 2` tak iterate karo.
3.  **Calculate Difference:** `diff = heights[i+1] - heights[i];`
4.  **If Climb is Needed (`diff > 0`):**
    * **Use Bricks:** `bricks -= diff;`
    * **Add to PQ:** `pq.add(diff);` (Assuming we used bricks for this climb).
    * **Check Bricks:** `if (bricks < 0)`: Agar bricks negative ho gaye (khatam ho gaye):
        * **Check Ladders:** `if (ladders > 0)`: Agar ladders available hain:
            * `bricks += pq.poll();` (PQ se sabse chhoti brick-used climb ke bricks wapas lo).
            * `ladders--;` (Ek ladder use karo).
        * **Else (No Ladders):** `return i;` (Ab aur aage nahi ja sakte).
5.  **Return:** `heights.length - 1;` (Agar saare buildings cross ho gaye).

---

## 🧮 Dynamic Programming (DP)

---

### ⭐ A 2D DP question (could be hard level)

**Problem Example: Trapping Rain Water II (Hard)**
Given an `m x n` matrix of positive integers representing the height of each unit cell in a 2D elevation map, compute the volume of water it can trap after raining.

**Intuition (Samajh):**
Yeh problem "Trapping Rain Water" ka 2D version hai. Water kisi cell par tabhi trap hoga jab woh chaaron taraf se (ya boundaries se) higher cells se ghira ho. Water ka level minimum surrounding height tak hota hai. BFS/DFS ya priority queue ka use karke isko solve kar sakte hain. Isko min-heap based Dijkstra/Prims jaisa approach mana ja sakta hai.

**Approach (Tareeka): BFS with Min-Heap (Modified Dijkstra/Prims)**
1.  **Boundaries are water sources:** Pani boundary se leak ho sakta hai. So, initially saare boundary cells ko min-heap mein add karo, aur unhein visited mark karo.
2.  **Min-Heap:** `PriorityQueue<int[]> pq` (`[height, row, col]`). Height ke basis par sort.
3.  **`visited` array:** `boolean[][] visited`.
4.  **Algorithm:**
    * Saare boundary cells ko `pq` mein add karo aur `visited` mark karo.
    * `total_water = 0;`
    * `while (!pq.isEmpty())`:
        * `cell = pq.poll();` (Extract cell with minimum height).
        * `current_height = cell[0];`
        * `r = cell[1], c = cell[2];`
        * Neighbors explore karo (`nr, nc`):
            * `if` neighbor valid hai, `!visited[nr][nc]` hai:
                * `visited[nr][nc] = true;`
                * `if (grid[nr][nc] < current_height)`: Agar neighbor ki height current cell (jo min height wali boundary ban raha hai) se kam hai, toh paani trap hoga.
                    * `total_water += current_height - grid[nr][nc];`
                * `pq.add(new int[]{Math.max(current_height, grid[nr][nc]), nr, nc});` (Neighbor ko `pq` mein add karte waqt, uski effective boundary height `max(current_height, grid[nr][nc])` hogi).
5.  **Return:** `total_water`.

---

### ⭐ A DP question with a LeetCode Medium difficulty (and follow-ups)

**Problem Example: Longest Increasing Subsequence (LIS) (Medium)**
Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

**Intuition (Samajh):**
Subsequence ka matlab elements continuous hona zaroori nahi. "Longest" typically points to DP. Har element par, humein previous elements ko dekhna hoga jinse increasing subsequence ban sake.

**Approach (Tareeka): Dynamic Programming (O(N^2))**
1.  **`dp` Array:** `int[] dp = new int[N];` `dp[i]` represents the length of the longest increasing subsequence ending at index `i`.
2.  **Initialization:** Har element khud 1 length ka increasing subsequence hai, so `Arrays.fill(dp, 1);`
3.  **Fill `dp`:**
    * Outer loop `i` from `1` to `N-1`.
    * Inner loop `j` from `0` to `i-1`.
    * `if (nums[i] > nums[j])`: Agar `nums[i]` `nums[j]` se bada hai, toh `nums[i]` ko `nums[j]` par end hone wale subsequence mein add kar sakte hain.
        * `dp[i] = Math.max(dp[i], dp[j] + 1);`
4.  **Result:** `dp` array mein sabse badi value hi LIS ki length hogi. `return max_value_in_dp_array;`

**Follow-ups:**
* **O(N log N) solution:** Use Binary Search. Maintain an array `tails` where `tails[i]` is the smallest tail of all increasing subsequences of length `i+1`. When processing a new number, binary search for its position in `tails` to either extend an existing subsequence or start a new one.

---

### ⭐ Burst Balloons (a variation has been mentioned)

**Problem:** You are given `n` balloons, indexed from `0` to `n - 1`. Each balloon has a number printed on it, `nums[i]`. If you burst balloon `i`, you get `nums[left] * nums[i] * nums[right]` coins. `left` and `right` are adjacent balloons to `i`. After bursting `i`, `left` and `right` become adjacent. Find the maximum coins you can collect.

**Intuition (Samajh):**
Yeh Hard DP problem hai. Direct approach (aage se burst karna) mushkil hai kyunki neighbors change hote hain. **Reverse thinking** is the key: Instead of thinking which balloon to burst *next*, think which balloon to burst *last*.

Agar `i` last balloon burst hua, toh uske left aur right ke neighbors final configuration mein adjacent honge, aur `nums[left] * nums[i] * nums[right]` coins milenge.

**Approach (Tareeka): DP with Reverse Thinking (Interval DP)**
1.  **Add virtual balloons:** `nums` array ke shuru aur end mein 1 add karo (i.e., `[1, nums[0], ..., nums[n-1], 1]`). Ye virtual balloons burst nahi ho sakte aur unki value 1 hai.
    * New array `arr` of size `n+2`.
2.  **`dp[i][j]`:** `dp[i][j]` represents the maximum coins you can get by bursting all balloons **between index `i` and `j`** (exclusive of `i` and `j`), given that `arr[i]` and `arr[j]` are the last two balloons remaining in this subproblem.
3.  **State Transition:**
    * Outer loop: `length` of the interval `L` from `2` to `n+1`.
    * Inner loop: `i` from `0` to `n+1 - L`. `j = i + L`. (`i` and `j` are boundaries).
    * Innermost loop: `k` from `i+1` to `j-1` (`k` is the balloon burst last within `(i, j)` interval).
    * `dp[i][j] = Math.max(dp[i][j], arr[i] * arr[k] * arr[j] + dp[i][k] + dp[k][j]);`
        * `arr[i] * arr[k] * arr[j]` is the score for bursting `k` last.
        * `dp[i][k]` is max score for bursting `(i,k)`
        * `dp[k][j]` is max score for bursting `(k,j)`
4.  **Base Case:** `dp` values initialized to 0. `dp[i][i+1]` is 0 as there are no balloons between `i` and `i+1`.
5.  **Result:** `dp[0][n+1]`.

---

## 📜 Strings

---

### ⭐ Base 2 to Base 6 conversion

**Problem:** Convert a binary string (base 2) to its base 6 representation.

**Intuition (Samajh):**
Direct conversion from base 2 to base 6 is not straightforward. Easiest way is to convert base 2 to base 10 (decimal) first, and then convert that decimal number to base 6.

**Approach (Tareeka): Binary to Decimal to Base 6**
1.  **Binary to Decimal:**
    * `decimal_num = 0;`
    * Iterate the binary string from right to left (LSB to MSB).
    * For each digit `d` at position `p` (from right, starting at 0): `decimal_num += d * (2^p);`
    * Example: "110" (base 2) = `0 * 2^0 + 1 * 2^1 + 1 * 2^2 = 0 + 2 + 4 = 6` (base 10).
2.  **Decimal to Base 6:**
    * `base6_string = "";`
    * `if (decimal_num == 0) return "0";`
    * `while (decimal_num > 0)`:
        * `remainder = decimal_num % 6;`
        * `base6_string = remainder + base6_string;` (Prepend remainder)
        * `decimal_num /= 6;`
    * Example: 6 (base 10) to base 6
        * `6 % 6 = 0`, `base6_string = "0"`
        * `6 / 6 = 1`
        * `1 % 6 = 1`, `base6_string = "10"`
        * `1 / 6 = 0`
    * Result: "10" (base 6).

---

### ⭐ Given a string of 0s and 1s, find the maximum value of K such that K length substrings are flipped repeatedly to make all characters of the binary string equal to 0.

**Problem:** Ek binary string (0s aur 1s ki) di gayi hai. Humein maximum `K` dhundhna hai jisse `K` length ke substrings ko baar-baar flip karke poori string ko `0`s mein convert kiya ja sake.

**Intuition (Samajh):**
Yeh `K` flip operation `0`s ko `1`s aur `1`s ko `0`s banata hai. Agar saari string `0`s mein convert ho sakti hai, toh iska matlab hai ki har `1` ko eventually flip hona padega. Yeh `K` ki value par depend karega.

Yeh problem "K-th Lexicographical String of All Happy Strings of Length N" jaisi nahi hai, jahan permutations involved hain. Yahan `K` length flips aur final state `all 0s` hai.

**Constraint:** The key here is that **each '1' must be flipped an odd number of times**, and **each '0' an even number of times** (to remain 0 or become 0 from 1). A flip operation affects a contiguous block of `K` characters.

**Approach (Tareeka): Greedy with Difference Array / Simulation**
This is typically a harder problem, often involving concepts similar to "Binary K-Sum" or "Flip Binary String to Monotone Increasing".
1.  **Possible K values:** `K` can range from `1` to `N`.
2.  **Greedy Strategy:** Try each `K` from `N` down to `1`. For a given `K`, simulate the flips.
    * Start from the leftmost `1`. If `s[i] == '1'`, it must be the start of a flip operation. Flip `s[i...i+K-1]`.
    * **Crucial:** If `i+K > N` (flip goes out of bounds), then this `K` is not possible.
    * Keep track of flips using a `diff` array or by actually flipping a temporary copy of the string (less efficient).
    * After iterating, check if the entire string becomes `0`. If yes, this `K` is valid. Since we are checking `K` from `N` downwards, the first valid `K` found will be the maximum.
3.  **Efficient Simulation (using a `flipped` array or `diff` array):**
    * Create a boolean array `isFlipped[]` of size `N`. `isFlipped[i]` will be true if character at `i` needs to be flipped.
    * Also maintain a `flip_count` for current position.
    * For a given `K`:
        * `temp_flips = 0;` (Number of pending flips from `i-K` position)
        * `temp_arr = original_arr;` (Copy of the string or array of chars)
        * `for i = 0 to N-1`:
            * `current_char = temp_arr[i]`
            * If `i >= K` and `isFlipped[i-K]` is true (meaning a flip started `K` positions ago, and now its effect is ending), then `temp_flips--`.
            * `effective_char = current_char XOR (temp_flips % 2)`.
            * `if effective_char == '1'`:
                * `if i + K > N`: This `K` is invalid, break and try smaller `K`.
                * `temp_flips++;`
                * `isFlipped[i] = true;`
        * If loop finishes without invalid `K`, then `K` is valid. Return `K`.
4.  **Return:** The largest valid `K`, or `0` if no such `K` exists (e.g., if it's impossible).

---

### ⭐ Word Search

**Problem:** Given a 2D board of characters and a word, find if the word exists in the grid. The word can be constructed from letters of sequentially adjacent cells, where "adjacent" cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

**Intuition (Samajh):**
Yeh grid traversal aur string matching ka combination hai. Humare paas ek starting point nahi hai. Toh, har possible starting cell se try karna hoga. "Sequentially adjacent cells" aur "not used more than once" indicates **Backtracking (DFS)**.

**Approach (Tareeka): Backtracking (DFS)**
1.  **Iterate Board:** `board` ke har cell `(r, c)` par iterate karo.
2.  **Start DFS:** `if (board[r][c] == word.charAt(0))`: Agar current cell `word` ke pehle character se match karta hai, toh `dfs(board, word, 0, r, c)` function call karo. Agar `true` return hota hai, toh word mil gaya, return `true`.
3.  **`dfs(board, word, index, row, col)` function:**
    * **Base Case 1:** `if (index == word.length()) return true;` (Poora word successfully match ho gaya).
    * **Base Case 2:** `if (row < 0 || row >= rows || col < 0 || col >= cols || board[row][col] != word.charAt(index)) return false;` (Boundary checks ya character match nahi hua).
    * **Mark Visited & Recurse:**
        * `char originalChar = board[row][col];` (Original character store karo).
        * `board[row][col] = '#';` (Cell ko visited mark karo temporary, by changing its char).
        * `found = dfs(board, word, index + 1, row + 1, col) ||`
        * `dfs(board, word, index + 1, row - 1, col) ||`
        * `dfs(board, word, index + 1, row, col + 1) ||`
        * `dfs(board, word, index + 1, row, col - 1);` (4 directions explore karo).
    * **Backtrack:** `board[row][col] = originalChar;` (Cell ko unmark karo for other paths).
    * `return found;`
4.  **Return:** Loop khatam hone par bhi word nahi mila, toh `false` return karo.

---

### ⭐ Word Search II

**Problem:** Given a 2D board of characters and a list of words, find all words in the dictionary that can be found in the grid.

**Intuition (Samajh):**
"Word Search" jaisa hi hai, lekin ab multiple words dhundne hain. Agar hum har word ke liye alag se Word Search chalaenge, toh TLE (`Time Limit Exceeded`) ho sakta hai, khaaskar jab `wordList` badi ho.
Solution: **Trie (Prefix Tree) + Backtracking (DFS)**. Trie mein saare words daal do. Jab DFS board par traverse kare, toh Trie ko bhi traverse karo. Agar current prefix Trie mein valid hai, toh aage explore karo. Agar current path ek word banata hai (Trie node `isWord` flag `true`), toh word add karo result mein.

**Approach (Tareeka): Trie + Backtracking (DFS)**
1.  **Build Trie:** Saare `words` ko ek `Trie` mein insert karo. Har `TrieNode` mein ek `isWord` flag aur children honge. Optional: `TrieNode` mein `word` itself store kar sakte ho, ya `count` of words passing through it, to optimize `removeWord` if needed.
2.  **Initialize:** `List<String> result = new ArrayList<>();`
3.  **Iterate Board:** `board` ke har cell `(r, c)` par iterate karo.
4.  **Start DFS with Trie:**
    * `char ch = board[r][c];`
    * `if (trie.children[ch - 'a'] != null)`: Agar Trie mein `ch` se start hone wala koi path hai:
        * `dfs(board, r, c, trie.children[ch - 'a'], result);`
5.  **`dfs(board, row, col, trieNode, result)` function:**
    * **Base Cases:**
        * `if (row < 0 || row >= rows || col < 0 || col >= cols || board[row][col] == '#') return;` (Boundary checks ya already visited).
    * **Trie Check:**
        * `char ch = board[row][col];`
        * `if (trieNode.children[ch - 'a'] == null) return;` (Trie mein current path nahi hai).
        * `currNode = trieNode.children[ch - 'a'];`
    * **Found Word:** `if (currNode.word != null)`: (Agar yeh node ek word ka end hai)
        * `result.add(currNode.word);`
        * `currNode.word = null;` (Important: Word ko null kar do taaki dobara add na ho, ya `removeWord` function call karo)
    * **Mark Visited & Recurse:**
        * `board[row][col] = '#';`
        * Recursively call `dfs` for 4 adjacent cells with `currNode`.
    * **Backtrack:** `board[row][col] = originalChar;` (Cell ko unmark karo).

---

### ⭐ Longest Repeating Character Replacement

**Problem:** Given a string `s` that consists of only uppercase English letters. You can perform at most `k` operations on `s`. In one operation, you can choose any character of the string and change it to any other uppercase English character. Find the length of the longest substring containing the same letter you can get after performing at most `k` operations.

**Intuition (Samajh):**
"Longest substring" implies **Sliding Window**. Ismein humein ek window maintain karni hai jismein ek character dominant ho, aur baaki characters ko `k` operations se change kiya ja sake.

**Approach (Tareeka): Sliding Window with Frequency Map**
1.  **Initialize:** `freq = new int[26];` (Character frequencies for current window). `max_freq_in_window = 0;` (Maximum frequency of any character in the current window). `left = 0`, `max_len = 0`.
2.  **Slide Window:** `right` pointer ko `0` se `N-1` tak move karo.
3.  **Update Frequencies:**
    * `char_at_right = s.charAt(right);`
    * `freq[char_at_right - 'A']++;`
    * `max_freq_in_window = Math.max(max_freq_in_window, freq[char_at_right - 'A']);`
4.  **Check Window Validity:** `while ((right - left + 1) - max_freq_in_window > k)`:
    * Window size (`right - left + 1`) minus `max_freq_in_window` gives the number of characters that are *not* the dominant character in the window. If this count is greater than `k`, it means we need more than `k` operations, so the window is invalid.
    * `char_at_left = s.charAt(left);`
    * `freq[char_at_left - 'A']--;`
    * `left++;`
    * **(Important):** `max_freq_in_window` might need to be recomputed here, but for this specific problem, it's often not needed to decrement `max_freq_in_window` precisely during `left` pointer movement. The `max_freq_in_window` only needs to be updated when `right` pointer moves. The `(right - left + 1) - max_freq_in_window` condition still works because if the window size shrinks, `max_freq_in_window` will remain the same or smaller.
5.  **Update Max Length:** `max_len = Math.max(max_len, right - left + 1);`
6.  **Return:** `max_len`

---

### ⭐ Text Justification

**Problem:** Given an array of words and a `maxWidth`, format the text such that each line has exactly `maxWidth` characters and is fully (left and right) justified.
* Extra spaces are distributed as evenly as possible.
* If spaces cannot be distributed evenly, the extra spaces should be put on the left side.
* Last line is left-justified and no extra space between words.

**Intuition (Samajh):**
Yeh problem greedy approach aur careful space distribution par based hai. Humein words ko lines mein group karna hoga aur phir har line mein spaces ko justify karna hoga.

**Approach (Tareeka): Greedy with Space Distribution**
1.  **Initialize:** `List<String> result = new ArrayList<>();`
2.  **Line Grouping:**
    * `index = 0;`
    * `while (index < words.length)`:
        * `currentLineWords = new ArrayList<>();`
        * `currentLineLength = 0;`
        * `j = index;`
        * `while (j < words.length && (currentLineLength + words[j].length() + currentLineWords.size()) <= maxWidth)`:
            * `currentLineWords.add(words[j]);`
            * `currentLineLength += words[j].length();`
            * `j++;`
        * `index = j;` (Next line starts from `j`)
3.  **Justify Line:** `String justifiedLine = justify(currentLineWords, currentLineLength, maxWidth, (index == words.length));`
    * `result.add(justifiedLine);`

4.  **`justify` Helper Function:** `justify(words, totalWordsLength, maxWidth, isLastLine)`
    * `numWords = words.size();`
    * `numSpaces = maxWidth - totalWordsLength;`
    * **Case 1: Single Word or Last Line:**
        * `if (numWords == 1 || isLastLine)`:
            * Word ko left-justify karo, baaki spaces right mein.
            * `return String.join(" ", words) + " ".repeat(numSpaces - (numWords - 1));`
    * **Case 2: Multiple Words (not last line):**
        * `gaps = numWords - 1;`
        * `spacesPerGap = numSpaces / gaps;`
        * `extraSpaces = numSpaces % gaps;`
        * `StringBuilder sb = new StringBuilder();`
        * `for (int i = 0; i < numWords; i++)`:
            * `sb.append(words.get(i));`
            * `if (i < gaps)`:
                * `sb.append(" ".repeat(spacesPerGap));`
                * `if (extraSpaces > 0) { sb.append(" "); extraSpaces--; }` (Extra spaces left side se distribute honge)
        * `return sb.toString();`

---

### ⭐ Longest Substring Without Repeating Characters.

**Problem:** Given a string `s`, find the length of the longest substring without repeating characters.

**Intuition (Samajh):**
"Longest substring" aur "without repeating characters" clearly points to **Sliding Window** combined with a frequency map/set to track characters in the current window.

**Approach (Tareeka): Sliding Window with HashSet/HashMap**
1.  **Initialize:**
    * `Set<Character> charSet = new HashSet<>();` (Stores characters in the current window)
    * `left = 0`, `max_len = 0`.
2.  **Slide Window (`right` pointer):** `right` ko `0` se `N-1` tak move karo.
3.  **Check for Duplicates:**
    * `char_at_right = s.charAt(right);`
    * `while (charSet.contains(char_at_right))`: Agar `char_at_right` already set mein hai (duplicate):
        * `char_to_remove = s.charAt(left);`
        * `charSet.remove(char_to_remove);`
        * `left++;` (Window ko shrink karo left se)
    * `charSet.add(char_at_right);` (Duplicate removed, or no duplicate, add current char).
4.  **Update Max Length:** `max_len = Math.max(max_len, right - left + 1);`
5.  **Return:** `max_len`

---
