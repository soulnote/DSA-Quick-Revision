# NeetCode 150 

---

## 🌐 Arrays & Hashing (9 Problems)

1. **Contains Duplicate** – Check for duplicates using a set.
2. **Valid Anagram** – Use a hash map or sorting to compare letter frequencies.
3. **Two Sum** – Use a hash map to store complements.
4. **Group Anagrams** – Hash sorted words into lists.
5. **Top K Frequent Elements** – Use a frequency map and heap.
6. **Product of Array Except Self** – Use prefix and suffix arrays.
7. **Valid Sudoku** – Check rows, columns, and boxes using sets.
8. **Encode and Decode Strings** – Use delimiters and lengths to encode.
9. **Longest Consecutive Sequence** – Use a set for constant-time lookup.

---

## 🛍 Two Pointers (5 Problems)

1. **Valid Palindrome** – Compare characters from both ends.
2. **Two Sum II** – Use two pointers since array is sorted.
3. **3Sum** – Sort the array and fix one number, two-pointer for others.
4. **Container With Most Water** – Use two pointers to maximize area.
5. **Trapping Rain Water** – Use two-pointer approach to track max heights.

---

## 🌧 Sliding Window (6 Problems)

1. **Best Time to Buy and Sell Stock** – Track min price and max profit.
2. **Longest Substring Without Repeating Characters** – Use a set to track characters.
3. **Longest Repeating Character Replacement** – Use a map to track counts.
4. **Permutation in String** – Use sliding window and character counts.
5. **Minimum Window Substring** – Use two maps and two pointers.
6. **Sliding Window Maximum** – Use a deque to maintain window max.

---

## 🧶 Stack (7 Problems)

1. **Valid Parentheses** – Use stack to track open brackets.
2. **Min Stack** – Track current min at each stack level.
3. **Evaluate Reverse Polish Notation** – Use a stack for evaluation.
4. **Generate Parentheses** – Use backtracking with open/close counters.
5. **Daily Temperatures** – Use stack to track indices.
6. **Car Fleet** – Sort cars and use stack to simulate fleets.
7. **Largest Rectangle in Histogram** – Use monotonic stack.

---

## 🔍 Binary Search (7 Problems)

1. **Binary Search** – Standard binary search.
2. **Search a 2D Matrix** – Treat 2D matrix as a 1D array.
3. **Koko Eating Bananas** – Use binary search on answer space.
4. **Search in Rotated Sorted Array** – Use binary search with rotation check.
5. **Find Minimum in Rotated Sorted Array** – Modified binary search.
6. **Time Based Key-Value Store** – Use binary search for timestamps.
7. **Median of Two Sorted Arrays** – Use binary search on partitions.

---

## 📝 Linked List (11 Problems)

1. **Reverse Linked List** – Reverse using prev, curr pointers.
2. **Merge Two Sorted Lists** – Use dummy node and pointers.
3. **Reorder List** – Split, reverse second half, merge.
4. **Remove Nth Node From End of List** – Use two-pointer gap.
5. **Copy List with Random Pointer** – Use hashmap or interweaving method.
6. **Add Two Numbers** – Add values and manage carry.
7. **Linked List Cycle** – Use fast and slow pointers.
8. **Find the Duplicate Number** – Use cycle detection (Floyd’s algorithm).
9. **LRU Cache** – Use doubly linked list + hashmap.
10. **Merge K Sorted Lists** – Use a heap to merge nodes.
11. **Reverse Nodes in k-Group** – Reverse every k nodes recursively or iteratively.

---

## 🌳 Trees (15 Problems)

1. **Invert Binary Tree** – Recursively swap left and right children.
2. **Maximum Depth of Binary Tree** – DFS to find max depth.
3. **Diameter of Binary Tree** – DFS to track depth and max diameter.
4. **Balanced Binary Tree** – DFS to check heights of subtrees.
5. **Same Tree** – Recursively compare values and children.
6. **Subtree of Another Tree** – Compare subtree roots recursively.
7. **Lowest Common Ancestor of BST** – Use BST properties.
8. **Binary Tree Level Order Traversal** – Use BFS.
9. **Binary Tree Right Side View** – Track rightmost nodes per level.
10. **Count Good Nodes in Binary Tree** – Track max seen during DFS.
11. **Construct Binary Tree from Preorder and Inorder Traversal** – Use indexes and recursion.
12. **Validate Binary Search Tree** – Use bounds during DFS.
13. **Kth Smallest Element in BST** – Inorder traversal.
14. **Binary Tree Maximum Path Sum** – Use DFS with backtracking.
15. **Serialize and Deserialize Binary Tree** – Use preorder and null markers.

---

## ⚡ Heap / Priority Queue (7 Problems)

1. **Merge K Sorted Lists** – Use min heap.
2. **Top K Frequent Elements** – Use heap based on frequency.
3. **Kth Largest Element in an Array** – Use min heap of size k.
4. **Task Scheduler** – Use heap to manage cooldowns.
5. **Design Twitter** – Use heap to track tweet times.
6. **Find Median from Data Stream** – Use two heaps.
7. **Last Stone Weight** – Use max heap to simulate collisions.

---

## 🔙 Backtracking (9 Problems)

1. **Subsets** – Generate combinations recursively.
2. **Combination Sum** – Explore candidates recursively.
3. **Permutations** – Swap elements to generate permutations.
4. **N-Queens** – Place queens row by row using constraints.
5. **Word Search** – DFS with visited tracking.
6. **Palindrome Partitioning** – Use backtracking and isPalindrome check.
7. **Letter Combinations of a Phone Number** – Map digits to letters and recurse.
8. **Combination Sum II** – Track used elements to avoid duplicates.
9. **Restore IP Addresses** – Backtrack valid sections.

---

## 🌐 Tries (3 Problems)

1. **Implement Trie** – Use nested dictionaries or nodes.
2. **Add and Search Word** – Support wildcards with DFS.
3. **Word Search II** – Trie + backtracking for prefix match.

---

## 🌉 Graphs (13 Problems)

1. **Number of Islands** – DFS or BFS to count components.
2. **Clone Graph** – DFS/BFS with hashmap for visited.
3. **Max Area of Island** – DFS to explore island area.
4. **Pacific Atlantic Water Flow** – Reverse DFS from ocean borders.
5. **Surrounded Regions** – Mark escape regions, flip the rest.
6. **Rotting Oranges** – BFS with queue of rotten oranges.
7. **Walls and Gates** – BFS from all gates.
8. **Course Schedule** – Topological sort with cycle detection.
9. **Course Schedule II** – Topological sort to return order.
10. **Redundant Connection** – Union Find to detect cycles.
11. **Number of Connected Components in an Undirected Graph** – DFS or Union Find.
12. **Graph Valid Tree** – Check acyclic and connected.
13. **Word Ladder** – BFS from start word.

---

## 🧭 Advanced Graphs (6 Problems)

1. **Network Delay Time** – Use Dijkstra’s algorithm for shortest delay.
2. **Swim in Rising Water** – Use Dijkstra’s or Binary Search + BFS.
3. **Alien Dictionary** – Topological sort using graph of characters.
4. **Cheapest Flights Within K Stops** – Modified Dijkstra or Bellman-Ford.
5. **Reachable Nodes In Subdivided Graph** – Use heap and graph traversal.
6. **Path With Maximum Probability** – Use max-heap and Dijkstra-style traversal.

---

## 🧮 1D Dynamic Programming (12 Problems)

1. **Climbing Stairs** – Use DP to count ways using previous two states.
2. **Min Cost Climbing Stairs** – DP using min of previous two costs.
3. **House Robber** – Use DP to avoid robbing adjacent houses.
4. **House Robber II** – Circle variation; rob excluding first or last.
5. **Longest Palindromic Substring** – Expand around centers or use DP.
6. **Palindromic Substrings** – Count with expand around center or DP.
7. **Decode Ways** – DP using one or two digit lookups.
8. **Coin Change** – Bottom-up DP for fewest coins.
9. **Maximum Product Subarray** – Track max/min to handle negatives.
10. **Word Break** – DP with word dictionary and prefix check.
11. **Longest Increasing Subsequence** – Use DP or binary search.
12. **Partition Equal Subset Sum** – Subset sum DP.

---
Here's the continuation after the **1D Dynamic Programming** section:

---

## 🧮 2D Dynamic Programming (11 Problems)

1. **Unique Paths** – Use a DP table to track the number of ways.
2. **Unique Paths II** – Handle obstacles in the DP table.
3. **Edit Distance** – Use DP to track minimum operations.
4. **Longest Common Subsequence** – Use DP to track subsequences.
5. **Interleaving String** – Use DP to check if a string is interleaved.
6. **Word Break II** – Backtrack with DP to find all valid segments.
7. **Partition Equal Subset Sum** – Subset sum DP for partitioning.
8. **Maximum Length of Repeated Subarray** – DP for longest common subarray.
9. **Dungeon Game** – DP with reverse traversal to manage health.
10. **Candy** – Greedily distribute candies with DP to maintain fairness.
11. **Palindrome Partitioning II** – Use DP to minimize cuts.

---

## 💡 Greedy (8 Problems)

1. **Jump Game** – Greedily check if you can reach the end.
2. **Jump Game II** – Use greedy approach to minimize the number of jumps.
3. **Gas Station** – Use a greedy algorithm to solve circular problems.
4. **Candy** – Distribute candies using greedy approach to ensure fairness.
5. **Assign Cookies** – Use greedy approach to maximize happiness.
6. **Interval List Intersections** – Use greedy to check overlaps.
7. **Maximum Subarray** – Use greedy to track maximum sum.
8. **Best Time to Buy and Sell Stock II** – Use greedy to make the best trades.

---

## ⏳ Intervals (6 Problems)

1. **Meeting Rooms** – Use a greedy approach to check if meetings can fit.
2. **Meeting Rooms II** – Use a priority queue to track overlapping meetings.
3. **Non-overlapping Intervals** – Greedy approach to minimize overlaps.
4. **Insert Interval** – Merge intervals while inserting a new one.
5. **Interval Scheduling Maximization** – Use greedy approach to select the most intervals.
6. **Merge Intervals** – Merge overlapping intervals.

---

## 📏 Math & Geometry (8 Problems)

1. **Reverse Integer** – Handle overflow while reversing integer.
2. **Roman to Integer** – Convert Roman numeral to integer using mapping.
3. **Integer to Roman** – Convert integer to Roman numeral using pattern.
4. **Sqrt(x)** – Use binary search or Newton’s method for square root.
5. **Add Binary** – Add two binary strings.
6. **Pow(x, n)** – Use exponentiation by squaring.
7. **Max Points on a Line** – Use hashing to track collinearity.
8. **Count Primes** – Use sieve algorithm to count primes.

---

## 🔢 Bit Manipulation (7 Problems)

1. **Single Number** – XOR to find the unique element.
2. **Number of 1 Bits** – Use bitwise operations to count bits.
3. **Power of Two** – Check if a number is a power of two.
4. **Reverse Bits** – Reverse the bits of an integer.
5. **Counting Bits** – Use dynamic programming to count bits for all numbers.
6. **Sum of Two Integers** – Use bitwise operations for sum.
7. **Bitwise AND of Numbers Range** – Use properties of bitwise AND over a range.

---


