# Type of DP patterns

## 🧠 1. **1D DP – Linear State**

Used when you have to find optimal values for a single index moving forward or backward.

* **Examples**:

  1. Fibonacci Numbers
  2. Climbing Stairs
  3. Minimum Cost Climbing Stairs
  4. House Robber
  5. Decode Ways

---

## 🪜 2. **2D DP – Grid Based**

Used for problems involving a grid or 2D matrix (usually directional movement allowed).

* **Examples**:

  1. Unique Paths
  2. Minimum Path Sum
  3. Longest Path in Matrix
  4. Cherry Pickup I/II
  5. Dungeon Game

---

## 🧳 3. **0/1 Knapsack Type**

Choose or not choose items with capacity constraints.

* **Examples**:

  1. 0/1 Knapsack
  2. Subset Sum
  3. Partition Equal Subset Sum
  4. Target Sum
  5. Count of Subsets with Given Sum

---

## 💰 4. **Unbounded Knapsack Type**

You can take an item **multiple** times (coins, rods, etc.)

* **Examples**:

  1. Coin Change (Min Coins)
  2. Coin Change 2 (Count Ways)
  3. Rod Cutting
  4. Integer Break
  5. Maximum Ribbon Cut

---

## 💞 5. **Longest Common Subsequence (LCS) Family**

Compares two sequences (strings or arrays), usually with `i, j` states.

* **Examples**:

  1. LCS
  2. Longest Common Substring
  3. Edit Distance
  4. Shortest Common Supersequence
  5. Sequence Pattern Matching (is A subsequence of B?)

---

## 🔀 6. **Palindrome Based DP**

Work with substrings checking for palindromic structure.

* **Examples**:

  1. Longest Palindromic Subsequence
  2. Longest Palindromic Substring
  3. Minimum Insertions to Make Palindrome
  4. Palindrome Partitioning
  5. Count Palindromic Subsequences

---

## 📈 7. **Subsequence / LIS Type**

Used when comparing elements in sequences with order preservation.

* **Examples**:

  1. Longest Increasing Subsequence (LIS)
  2. Number of LIS
  3. Maximum Sum Increasing Subsequence
  4. Longest Bitonic Subsequence
  5. Russian Doll Envelopes

---

## 🔄 8. **Digit DP**

Works on digit-by-digit logic, especially for counting numbers with properties.

* **Examples**:

  1. Count of numbers with given sum
  2. Count of numbers with at most K digits having a digit sum = X
  3. Count of numbers ≤ N with digit XOR = X
  4. Numbers with at most 3 non-zero digits
  5. Count numbers with digits in increasing order

---

## 🧵 9. **DP on Strings**

Used where operations like insert/delete/replace needed on strings.

* **Examples**:

  1. Edit Distance
  2. Wildcard Matching
  3. Regular Expression Matching
  4. Minimum ASCII Delete Sum
  5. Scramble String

---

## 🌳 10. **DP on Trees**

Use DFS + DP when nodes depend on subtrees.

* **Examples**:

  1. Diameter of Binary Tree
  2. Maximum Path Sum
  3. House Robber III (Tree version)
  4. Count of BSTs with N nodes
  5. Binary Tree Cameras

---

## 🧮 11. **Bitmask DP**

Used when you have to track subset states or visited elements (especially in TSP, permutations).

* **Examples**:

  1. Traveling Salesman Problem (TSP)
  2. Minimum Cost to Visit All Cities
  3. Shortest Superstring
  4. Count ways to assign tasks
  5. Boolean Parenthesization

---

## 🧬 12. **MCM (Matrix Chain Multiplication) Type**

Partitioning problems where you try all breaks between two indices.

* **Examples**:

  1. Matrix Chain Multiplication
  2. Palindrome Partitioning II
  3. Boolean Parenthesization
  4. Burst Balloons
  5. Scramble String

---

## 🪢 13. **DP on Intervals**

Where state depends on an interval `[i...j]`.

* **Examples**:

  1. Burst Balloons
  2. Optimal BST
  3. Minimum Score Triangulation
  4. Removing Boxes
  5. Stone Game variants

---
