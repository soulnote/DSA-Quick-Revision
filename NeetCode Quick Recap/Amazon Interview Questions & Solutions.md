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
# Graph
---

### 1. Course Schedule (LeetCode #207)

**Intuition Summary**: Detect if a directed graph (representing course prerequisites) has a cycle using topological sort via DFS. A cycle means it's impossible to finish all courses. Track visited nodes and recursion stack to detect cycles.

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        // Track visited nodes and recursion stack
        boolean[] visited = new boolean[numCourses];
        boolean[] recStack = new boolean[numCourses];
        
        // Run DFS for each unvisited node
        for (int i = 0; i < numCourses; i++) {
            if (!visited[i] && hasCycle(i, graph, visited, recStack)) {
                return false;
            }
        }
        
        // No cycle found, can finish all courses
        return true;
    }
    
    // DFS to detect cycle
    private boolean hasCycle(int node, List<List<Integer>> graph, boolean[] visited, boolean[] recStack) {
        // Mark node as visited and add to recursion stack
        visited[node] = true;
        recStack[node] = true;
        
        // Explore neighbors
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (hasCycle(neighbor, graph, visited, recStack)) {
                    return true;
                }
            } else if (recStack[neighbor]) {
                // Cycle detected
                return true;
            }
        }
        
        // Remove node from recursion stack
        recStack[node] = false;
        return false;
    }
}
```

---

### 2. Number of Islands (LeetCode #200)

**Intuition Summary**: Count islands (connected '1's) in a grid using DFS. For each unvisited '1', perform DFS to mark all connected '1's as visited, incrementing the island count. Repeat until all cells are processed.

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;
        
        // Iterate through each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    // Found an island, use DFS to mark all connected land
                    dfs(grid, i, j);
                    islands++;
                }
            }
        }
        
        return islands;
    }
    
    // DFS to mark connected land as visited
    private void dfs(char[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Check boundaries and if cell is land
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
            return;
        }
        
        // Mark as visited by changing to '0'
        grid[i][j] = '0';
        
        // Explore all four directions
        dfs(grid, i - 1, j); // up
        dfs(grid, i + 1, j); // down
        dfs(grid, i, j - 1); // left
        dfs(grid, i, j + 1); // right
    }
}
```

---

### 3. Graph Valid Tree (LeetCode #261)

**Intuition Summary**: A valid tree has no cycles and exactly n-1 edges for n nodes. Use DFS to check for cycles and ensure all nodes are connected. Track visited nodes and parent to avoid false cycle detection.

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // A tree must have n-1 edges
        if (edges.length != n - 1) return false;
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // Track visited nodes
        boolean[] visited = new boolean[n];
        
        // Run DFS from node 0
        if (hasCycle(0, -1, graph, visited)) {
            return false;
        }
        
        // Check if all nodes are visited (connected)
        for (boolean v : visited) {
            if (!v) return false;
        }
        
        return true;
    }
    
    // DFS to detect cycle
    private boolean hasCycle(int node, int parent, List<List<Integer>> graph, boolean[] visited) {
        // Mark node as visited
        visited[node] = true;
        
        // Explore neighbors
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                if (visited[neighbor] || hasCycle(neighbor, node, graph, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
```

---

### 4. Clone Graph (LeetCode #133)

**Intuition Summary**: Clone a graph by creating a copy of each node and its neighbors. Use DFS and a hash map to track cloned nodes, avoiding duplicate cloning. Recursively clone neighbors and update their connections.

```java
/**
 * Definition for a Node.
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 *     public Node(int _val) {
 *         val = _val;
 *         neighbors = new ArrayList<>();
 *     }
 * }
 */
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;
        
        // Map to store original node to its clone
        Map<Node, Node> cloned = new HashMap<>();
        
        // Start DFS cloning
        return dfsClone(node, cloned);
    }
    
    // DFS to clone node and its neighbors
    private Node dfsClone(Node node, Map<Node, Node> cloned) {
        // If already cloned, return the clone
        if (cloned.containsKey(node)) {
            return cloned.get(node);
        }
        
        // Create clone of current node
        Node clone = new Node(node.val);
        cloned.put(node, clone);
        
        // Clone all neighbors
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfsClone(neighbor, cloned));
        }
        
        return clone;
    }
}
```

---

### 5. Find All Recipes (LeetCode #2115)

**Intuition Summary**: Model recipes and ingredients as a directed graph, where edges go from ingredients to recipes. Use topological sort (DFS) to find recipes that can be made, checking if all ingredients are available or producible.

```java
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // Build graph: ingredient -> recipe
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        
        // Initialize graph and in-degree
        for (int i = 0; i < recipes.length; i++) {
            for (String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipes[i]);
                inDegree.put(recipes[i], inDegree.getOrDefault(recipes[i], 0) + 1);
            }
            inDegree.putIfAbsent(recipes[i], 0);
        }
        
        // Initialize queue with supplies
        Queue<String> queue = new LinkedList<>(Arrays.asList(supplies));
        List<String> result = new ArrayList<>();
        
        // Process using topological sort
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            
            // If current is a recipe, add to result
            if (inDegree.containsKey(curr)) {
                result.add(curr);
            }
            
            // Update neighbors (recipes that use curr)
            for (String recipe : graph.getOrDefault(curr, new ArrayList<>())) {
                inDegree.put(recipe, inDegree.get(recipe) - 1);
                if (inDegree.get(recipe) == 0) {
                    queue.offer(recipe);
                }
            }
        }
        
        return result;
    }
}
```

---

### 6. Currency Converter (Related: LeetCode #743 - Network Delay Time)

**Intuition Summary**: Treat currencies as nodes and conversion rates as weighted edges in a directed graph. Use Dijkstra's algorithm to find the shortest path (maximum conversion rate) between two currencies, handling cycles and unreachable pairs.

```java
class Solution {
    public double convertCurrency(String start, String end, Map<String, Map<String, Double>> graph) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) return -1.0;
        
        // Priority queue for Dijkstra's algorithm (min heap for negative log weights)
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
        Map<String, Double> dist = new HashMap<>();
        
        // Initialize distances
        dist.put(start, 1.0);
        pq.offer(new double[]{0, 1.0, start.hashCode()}); // {node, dist}
        
        while (!pq.isEmpty()) {
            double[] curr = pq.poll();
            String currNode = String.valueOf((int)curr[2]);
            double currDist = curr[1];
            
            // Skip if better path found
            if (currDist > dist.getOrDefault(currNode, Double.MAX_VALUE)) continue;
            
            // Check neighbors
            for (Map.Entry<String, Double> neighbor : graph.get(currNode).entrySet()) {
                String nextNode = neighbor.getKey();
                double weight = neighbor.getValue();
                double newDist = currDist * weight;
                
                // Update distance if better
                if (newDist > dist.getOrDefault(nextNode, 0.0)) {
                    dist.put(nextNode, newDist);
                    pq.offer(new double[]{nextNode.hashCode(), newDist, nextNode.hashCode()});
                }
            }
        }
        
        // Return result or -1 if unreachable
        return dist.getOrDefault(end, -1.0);
    }
}
```
# Stack & Queues

---

### 1. Min Stack (LeetCode #155)

**Intuition Summary**: Design a stack that supports push, pop, top, and getMin in O(1) time. Use two stacks: one for values and one to track the minimum at each push. When pushing, compare with the current minimum to update the min stack.

```java
class MinStack {
    private Stack<Integer> stack; // Stack for storing values
    private Stack<Integer> minStack; // Stack for tracking minimums
    
    public MinStack() {
        // Initialize both stacks
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        // Push value to main stack
        stack.push(val);
        // Update minStack: push val if it's less than or equal to current min
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    public void pop() {
        // Pop from main stack
        int val = stack.pop();
        // If popped value is current min, pop from minStack
        if (val == minStack.peek()) {
            minStack.pop();
        }
    }
    
    public int top() {
        // Return top value of main stack
        return stack.peek();
    }
    
    public int getMin() {
        // Return current minimum from minStack
        return minStack.peek();
    }
}
```

---

### 2. Valid Parentheses (LeetCode #20)

**Intuition Summary**: Check if a string of parentheses is valid by using a stack to track opening brackets. For each closing bracket, pop and check if it matches the last opening bracket. The stack should be empty at the end if valid.

```java
class Solution {
    public boolean isValid(String s) {
        // Initialize stack for opening brackets
        Stack<Character> stack = new Stack<>();
        
        // Iterate through each character
        for (char c : s.toCharArray()) {
            // Push opening brackets to stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // For closing brackets, check if stack is empty or mismatch
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }
        
        // Stack should be empty for valid string
        return stack.isEmpty();
    }
}
```

---

### 3. Implement Queue using Stacks (LeetCode #232)

**Intuition Summary**: Implement a queue using two stacks. Use one stack for enqueue (push) and another for dequeue (pop). When dequeuing, transfer elements from the enqueue stack to the dequeue stack to reverse order, ensuring FIFO behavior.

```java
class MyQueue {
    private Stack<Integer> inStack; // Stack for enqueue
    private Stack<Integer> outStack; // Stack for dequeue
    
    public MyQueue() {
        // Initialize both stacks
        inStack = new Stack<>();
        outStack = new Stack<>();
    }
    
    public void push(int x) {
        // Push to inStack for enqueue
        inStack.push(x);
    }
    
    public int pop() {
        // Transfer to outStack if empty
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        // Pop from outStack for dequeue
        return outStack.pop();
    }
    
    public int peek() {
        // Transfer to outStack if empty
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        // Peek from outStack
        return outStack.peek();
    }
    
    public boolean empty() {
        // Queue is empty if both stacks are empty
        return inStack.isEmpty() && outStack.isEmpty();
    }
}
```

---

### 4. Next Greater Element I (LeetCode #496)

**Intuition Summary**: Find the next greater element for each number in nums1 from nums2 using a stack. Iterate through nums2, popping elements from the stack when a larger number is found, mapping the popped element to the larger number. Use a map to store results.

```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map to store next greater element for each number
        Map<Integer, Integer> nextGreater = new HashMap<>();
        // Stack to track elements waiting for their next greater
        Stack<Integer> stack = new Stack<>();
        
        // Process nums2
        for (int num : nums2) {
            // Pop elements smaller than current num and map to current num
            while (!stack.isEmpty() && stack.peek() < num) {
                nextGreater.put(stack.pop(), num);
            }
            // Push current num to stack
            stack.push(num);
        }
        
        // Remaining elements in stack have no next greater
        while (!stack.isEmpty()) {
            nextGreater.put(stack.pop(), -1);
        }
        
        // Build result for nums1
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater.get(nums1[i]);
        }
        
        return result;
    }
}
```

---

### 5. Daily Temperatures (LeetCode #739)

**Intuition Summary**: Find the number of days until a warmer day for each day using a stack. Iterate through temperatures, popping days from the stack when a warmer day is found, calculating the difference in days. The stack stores indices of days with increasing temperatures.

```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        // Stack to store indices of days with increasing temperatures
        Stack<Integer> stack = new Stack<>();
        
        // Iterate through temperatures
        for (int i = 0; i < n; i++) {
            // Pop days with lower temp and calculate days until warmer
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevDay = stack.pop();
                result[prevDay] = i - prevDay;
            }
            // Push current day index
            stack.push(i);
        }
        
        // Remaining days in stack have no warmer day (default 0)
        return result;
    }
}
```
# HashMap 

---

### 1. Two Sum (LeetCode #1, Hash Map Approach)

**Intuition Summary**: Find two numbers in an array that sum to a target using a hash map. Iterate through the array, storing each number and its index. For each number, check if the complement (target - number) exists in the map. If found, return their indices.

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Map to store number and its index
        Map<Integer, Integer> map = new HashMap<>();
        
        // Iterate through array
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // Check if complement exists in map
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            }
            // Add current number and index to map
            map.put(nums[i], i);
        }
        
        // No solution found (problem guarantees a solution)
        return new int[] {};
    }
}
```

---

### 2. Subarray Sum Equals K (LeetCode #560)

**Intuition Summary**: Find the number of subarrays with sum equal to k using a hash map to store cumulative sums. For each index, compute the cumulative sum and check if (sum - k) exists in the map to count valid subarrays. Update the map with the current sum.

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        // Map to store cumulative sum and its frequency
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // Initialize with 0 sum for single subarray case
        
        int sum = 0;
        int count = 0;
        
        // Iterate through array
        for (int num : nums) {
            // Update cumulative sum
            sum += num;
            // Add count of subarrays with sum (sum - k)
            count += map.getOrDefault(sum - k, 0);
            // Update map with current sum
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        
        return count;
    }
}
```

---

### 3. Top K Frequent Elements (LeetCode #347)

**Intuition Summary**: Find the k most frequent elements using a hash map and a min-heap. First, count frequencies with a map. Then, use a min-heap of size k to keep the k highest frequencies, updating it as needed. Extract elements from the heap for the result.

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Map to store number and its frequency
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Min-heap to store k elements based on frequency
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> freqMap.get(a) - freqMap.get(b)
        );
        
        // Process each number
        for (int num : freqMap.keySet()) {
            minHeap.offer(num);
            // Keep heap size at k
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        // Build result array
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }
        
        return result;
    }
}
```

---

### 4. Longest Consecutive Sequence (LeetCode #128)

**Intuition Summary**: Find the longest consecutive sequence using a hash set for O(1) lookups. For each number, check if it’s the start of a sequence (no number-1 in set). If so, count consecutive numbers forward. Track the longest sequence found.

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        // Set to store all numbers
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        
        int longest = 0;
        
        // Check each potential sequence start
        for (int num : set) {
            // Only process if num is start of sequence
            if (!set.contains(num - 1)) {
                int current = num;
                int streak = 1;
                
                // Count consecutive numbers
                while (set.contains(current + 1)) {
                    current++;
                    streak++;
                }
                
                // Update longest streak
                longest = Math.max(longest, streak);
            }
        }
        
        return longest;
    }
}
```

---

### 5. Map-Based Problem (Related: LeetCode #451 - Sort Characters By Frequency)

**Intuition Summary**: Sort characters in a string by their frequency using a hash map and a max-heap. Count frequencies with a map, then use a max-heap to sort characters by frequency. Build the result string by appending characters based on their frequency.

```java
class Solution {
    public String frequencySort(String s) {
        // Map to store character and its frequency
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        
        // Max-heap to sort by frequency
        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
            (a, b) -> freqMap.get(b) - freqMap.get(a)
        );
        maxHeap.addAll(freqMap.keySet());
        
        // Build result string
        StringBuilder result = new StringBuilder();
        while (!maxHeap.isEmpty()) {
            char c = maxHeap.poll();
            int freq = freqMap.get(c);
            for (int i = 0; i < freq; i++) {
                result.append(c);
            }
        }
        
        return result.toString();
    }        
}    
```

# Sorting and searching

---

### 1. Merge Intervals (LeetCode #56)

**Intuition Summary**: Sort intervals by start time to process them in order. Iterate through sorted intervals, merging overlapping ones by updating the end time if the current interval starts before the previous end. Add non-overlapping intervals to the result.

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // List to store merged intervals
        List<int[]> result = new ArrayList<>();
        
        // Process each interval
        for (int[] interval : intervals) {
            // If result is empty or no overlap, add interval
            if (result.isEmpty() || result.get(result.size() - 1)[1] < interval[0]) {
                result.add(interval);
            } else {
                // Merge by updating end time
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], interval[1]);
            }
        }
        
        // Convert list to array
        return result.toArray(new int[result.size()][]);
    }
}
```

---

### 2. Search in Rotated Sorted Array (LeetCode #33)

**Intuition Summary**: Use binary search on a rotated sorted array by determining which half is sorted. Compare the middle element with the target and endpoints to decide whether to search the sorted or unsorted half, adjusting pointers accordingly.

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // Binary search
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                // Check if target is in left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                // Check if target is in right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        // Target not found
        return -1;
    }
}
```

---

### 3. Merge Sort Implementation

**Intuition Summary**: Merge sort divides the array into two halves recursively until single elements remain, then merges sorted halves by comparing and combining elements in sorted order. It ensures stable sorting with O(n log n) time complexity.

```java
class MergeSort {
    public void mergeSort(int[] arr) {
        // Base case: no need to sort if array is empty or has one element
        if (arr == null || arr.length <= 1) {
            return;
        }
        // Create temporary array for merging
        int[] temp = new int[arr.length];
        // Start merge sort
        mergeSort(arr, 0, arr.length - 1, temp);
    }
    
    // Recursive function to divide array
    private void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            // Sort left half
            mergeSort(arr, left, mid, temp);
            // Sort right half
            mergeSort(arr, mid + 1, right, temp);
            // Merge sorted halves
            merge(arr, left, mid, right, temp);
        }
    }
    
    // Merge two sorted subarrays
    private void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // Copy elements to temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }
        
        int i = left;      // Index for left subarray
        int j = mid + 1;   // Index for right subarray
        int k = left;      // Index for merged array
        
        // Merge elements in sorted order
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        
        // Copy remaining elements from left subarray
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
    }
}
```

---

### 4. Quick Sort Implementation

**Intuition Summary**: Quick sort selects a pivot (e.g., last element), partitions the array around it by placing smaller elements before and larger after, then recursively sorts the subarrays. It achieves O(n log n) average time complexity but O(n²) in worst cases.

```java
class QuickSort {
    public void quickSort(int[] arr) {
        // Start quick sort
        quickSort(arr, 0, arr.length - 1);
    }
    
    // Recursive function to sort array
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition array and get pivot index
            int pi = partition(arr, low, high);
            // Sort left part
            quickSort(arr, low, pi - 1);
            // Sort right part
            quickSort(arr, pi + 1, high);
        }
    }
    
    // Partition array using last element as pivot
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Choose last element as pivot
        int i = low - 1;      // Index of smaller element
        
        // Iterate through subarray
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                // Swap elements
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Place pivot in correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1; // Return pivot index
    }
}
```
