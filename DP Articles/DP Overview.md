
# Linear Dynamic Programming Tutorial in Java

## Introduction
Dynamic Programming (DP) is a problem-solving technique used to solve problems by breaking them into smaller subproblems, solving each subproblem once, and storing their solutions to avoid redundant computations. **Linear DP** is a subset of DP where the state transitions follow a linear pattern, typically solved using a 1D array or a sequence of states that progress in a straightforward manner.

In this tutorial, we’ll:
- Explain the key concepts of Linear DP.
- Provide a step-by-step approach to solve Linear DP problems.
- Discuss common Linear DP patterns.
- Solve example problems with Java code and detailed explanations.
- Provide practice problems to test your understanding.

By the end, you’ll have a solid foundation to tackle Linear DP problems confidently.

---

## What is Linear Dynamic Programming?
Linear DP problems involve states that can be represented as a sequence, and the solution to each state depends on a fixed number of previous states. The term "linear" refers to the linear arrangement of states, often indexed by a single variable (e.g., `i` in a loop). These problems are typically solved using:
- A **1D DP array** where `dp[i]` represents the solution for state `i`.
- **Bottom-up** (iterative) or **top-down** (recursive with memoization) approaches.

### Key Characteristics of Linear DP
- **State**: A variable that describes the subproblem (e.g., `dp[i]` for the solution up to index `i`).
- **Transition**: A rule to compute the current state based on previous states (e.g., `dp[i] = dp[i-1] + something`).
- **Base Case**: The starting point or initial conditions (e.g., `dp[0] = 0`).
- **Optimal Substructure**: The solution to the problem can be built from solutions to smaller subproblems.
- **Overlapping Subproblems**: The same subproblems are solved multiple times, making DP efficient by storing results.

---

## Steps to Solve Linear DP Problems
1. **Identify the State**: Determine what `dp[i]` represents (e.g., maximum sum, minimum cost, or number of ways up to index `i`).
2. **Define the State Transition**: Write a formula or rule to compute `dp[i]` based on previous states (e.g., `dp[i] = min(dp[i-1], dp[i-2]) + cost[i]`).
3. **Set Base Cases**: Initialize the starting states (e.g., `dp[0] = 0`, `dp[1] = cost[1]`).
4. **Compute the Solution**:
   - **Bottom-Up**: Use loops to fill the DP array iteratively.
   - **Top-Down**: Use recursion with memoization to avoid recomputing states.
5. **Return the Final Answer**: The answer is usually stored in `dp[n]` or derived from the DP array.

---

## Common Linear DP Patterns
Here are some common Linear DP problem types you’ll encounter:
1. **Fibonacci-Like Problems**: Each state depends on a fixed number of previous states (e.g., `dp[i] = dp[i-1] + dp[i-2]`).
2. **Path Counting**: Count the number of ways to reach a point (e.g., climbing stairs).
3. **Optimization Problems**: Find the minimum or maximum value (e.g., minimum cost to reach the end).
4. **Subset or Selection Problems**: Decide whether to include or exclude elements (e.g., maximum sum subarray).

---

## Example Problems with Java Code

Let’s dive into three classic Linear DP problems to solidify your understanding. Each problem includes a clear explanation, Java code, and step-by-step analysis.

### Example 1: Fibonacci Number
**Problem**: Compute the nth Fibonacci number, where each number is the sum of the two preceding ones (F(0) = 0, F(1) = 1).

**Solution Approach**:
- **State**: `dp[i]` represents the ith Fibonacci number.
- **Transition**: `dp[i] = dp[i-1] + dp[i-2]`.
- **Base Cases**: `dp[0] = 0`, `dp[1] = 1`.
- **Answer**: `dp[n]`.

**Java Code (Bottom-Up)**:
```java
public class Fibonacci {
    public static long fibonacci(int n) {
        if (n <= 1) return n;
        
        // Initialize DP array
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        
        // Fill DP array
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Fibonacci(" + n + ") = " + fibonacci(n)); // Output: 55
    }
}
```

**Explanation**:
- We use a `long[]` array to store Fibonacci numbers to handle larger values.
- Base cases are set as `dp[0] = 0` and `dp[1] = 1`.
- For each `i` from 2 to `n`, we compute `dp[i]` as the sum of the two previous states.
- Time Complexity: O(n), Space Complexity: O(n).

**Space Optimization**:
Since we only need the last two states, we can reduce space to O(1):
```java
public static long fibonacciOptimized(int n) {
    if (n <= 1) return n;
    
    long prev = 0, curr = 1;
    for (int i = 2; i <= n; i++) {
        long next = prev + curr;
        prev = curr;
        curr = next;
    }
    
    return curr;
}
```

---

### Example 2: Climbing Stairs
**Problem**: You are climbing a staircase with `n` steps. At each step, you can climb 1 or 2 steps. Find the number of distinct ways to reach the top.

**Solution Approach**:
- **State**: `dp[i]` represents the number of ways to reach step `i`.
- **Transition**: `dp[i] = dp[i-1] + dp[i-2]` (you can reach step `i` from step `i-1` or `i-2`).
- **Base Cases**: `dp[0] = 1` (one way to stay at step 0), `dp[1] = 1` (one way to reach step 1).
- **Answer**: `dp[n]`.

**Java Code (Bottom-Up)**:
```java
public class ClimbingStairs {
    public static int climbStairs(int n) {
        if (n <= 1) return 1;
        
        // Initialize DP array
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        
        // Fill DP array
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Ways to climb " + n + " stairs: " + climbStairs(n)); // Output: 8
    }
}
```

**Explanation**:
- This is similar to the Fibonacci problem but with a different interpretation.
- `dp[i]` is the sum of ways to reach the previous two steps.
- We initialize `dp[0] = 1` and `dp[1] = 1` as base cases.
- Time Complexity: O(n), Space Complexity: O(n).

**Top-Down (Memoization)**:
```java
public class ClimbingStairsMemo {
    public static int climbStairs(int n) {
        int[] memo = new int[n + 1];
        return climbStairsHelper(n, memo);
    }
    
    private static int climbStairsHelper(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] != 0) return memo[n];
        
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Ways to climb " + n + " stairs: " + climbStairs(n)); // Output: 8
    }
}
```

**Explanation**:
- We use a `memo` array to store computed results.
- The recursive function checks if the result for `n` is already computed; if not, it computes it using the transition formula.
- Time Complexity: O(n), Space Complexity: O(n).

---

### Example 3: House Robber
**Problem**: You are a thief planning to rob houses along a street. Each house has a certain amount of money (`nums[i]`). You cannot rob adjacent houses. Find the maximum amount you can rob.

**Solution Approach**:
- **State**: `dp[i]` represents the maximum amount that can be robbed from houses up to index `i`.
- **Transition**: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` (either skip house `i` or rob house `i` and skip `i-1`).
- **Base Cases**: `dp[0] = nums[0]`, `dp[1] = max(nums[0], nums[1])`.
- **Answer**: `dp[n-1]`.

**Java Code (Bottom-Up)**:
```java
public class HouseRobber {
    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        // Initialize DP array
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        // Fill DP array
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        
        return dp[nums.length - 1];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 9, 3, 1};
        System.out.println("Maximum amount to rob: " + rob(nums)); // Output: 12 (2 + 9 + 1)
    }
}
```

**Explanation**:
- `dp[i]` stores the maximum loot up to house `i`.
- For each house, we choose between:
  - Not robbing house `i` (take `dp[i-1]`).
  - Robbing house `i` and skipping `i-1` (take `dp[i-2] + nums[i]`).
- Time Complexity: O(n), Space Complexity: O(n).

**Space Optimization**:
Since we only need the last two states:
```java
public static int robOptimized(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    
    int prev2 = nums[0];
    int prev1 = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        int curr = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

---

## Tips for Solving Linear DP Problems
1. **Start with a Small Example**: Test the problem with small inputs to understand the pattern.
2. **Draw the State Transitions**: Visualize how states depend on each other.
3. **Choose Bottom-Up or Top-Down**:
   - Bottom-Up is often more space-efficient and avoids recursion stack issues.
   - Top-Down is intuitive for complex problems but requires memoization.
4. **Optimize Space**: If only a few previous states are needed, use variables instead of an array.
5. **Debugging**: Print the DP array for small cases to verify your transitions.

---

## Practice Problems
To build confidence, try solving these Linear DP problems on platforms like LeetCode:
1. **Min Cost Climbing Stairs** (LeetCode #746): Similar to Climbing Stairs but with costs.
2. **Decode Ways** (LeetCode #91): Count ways to decode a string.
3. **Maximum Subarray** (LeetCode #53): Find the subarray with the maximum sum.
4. **Unique Paths** (LeetCode #62): Count paths in a grid (can be solved as Linear DP with a 1D array).
5. **Jump Game** (LeetCode #55): Determine if you can reach the last index.

---

To make the memoization approach for Linear Dynamic Programming (DP) clearer, I’ll enhance the previous tutorial by adding **dry run diagrams** and **recursion tree diagrams** for the example problems (Climbing Stairs and House Robber). These diagrams will visually illustrate how memoization works, showing the recursive calls, memoized states, and how redundant computations are avoided. I’ll update the relevant sections of the tutorial to include these diagrams, keeping the focus on clarity and ease of understanding for beginners. The diagrams will be described in text (as ASCII art or detailed explanations) since I can’t render images directly, and they’ll be embedded within the artifact.

Below is the updated tutorial, with dry run and recursion tree diagrams added to the Climbing Stairs and House Robber examples. I’ll also ensure the artifact follows the required format and retains the same `artifact_id` since this is an update to the previous memoization tutorial.



# Linear Dynamic Programming with Memoization Tutorial in Java

## Introduction
This tutorial builds on the memoization approach for Linear Dynamic Programming (DP) by adding **dry run diagrams** and **recursion tree diagrams** to make the concepts visually clear. Memoization is a top-down DP technique that uses recursion and stores subproblem results to avoid redundant computations. Linear DP involves sequential states, typically solved with a 1D array. This tutorial focuses on:
- How to apply memoization to Linear DP problems.
- Visualizing the process with dry run and recursion tree diagrams.
- When memoization might not be ideal.
- Example problems with Java code and detailed diagrams.

By the end, you’ll understand how memoization works visually and be able to solve Linear DP problems confidently.

---

## What is Memoization in Linear DP?
Memoization involves:
- Solving subproblems recursively.
- Storing results in a 1D array (or hash map) for Linear DP states.
- Reusing stored results to skip redundant calculations.

For Linear DP, states are indexed by a single variable (e.g., `i`), and `dp[i]` depends on previous states (e.g., `dp[i-1]`, `dp[i-2]`). Diagrams will show how recursive calls build the solution and how memoization prunes redundant paths.

---

## Steps to Use Memoization in Linear DP
1. **Define the State**: Determine what `dp[i]` represents.
2. **Write the Recursive Function**: Compute `dp[i]` based on previous states.
3. **Add Memoization**: Store results in an array and check for computed states.
4. **Set Base Cases**: Initialize smallest subproblems.
5. **Call the Function**: Start from the desired state (e.g., `n`).

---

## Example Problems with Java Code and Diagrams

We’ll revisit two problems (Climbing Stairs and House Robber) and add diagrams to illustrate the memoization process. Each example includes:
- Java code for memoization.
- A **recursion tree diagram** showing recursive calls and memoized states.
- A **dry run diagram** showing how the memo array is filled.

### Example 1: Climbing Stairs (Memoization)
**Problem**: Find the number of distinct ways to climb `n` steps, taking 1 or 2 steps at a time.

**Solution Approach**:
- **State**: `dp[i]` is the number of ways to reach step `i`.
- **Recursive Relation**: `dp[i] = dp[i-1] + dp[i-2]`.
- **Base Cases**: `dp[0] = 1`, `dp[1] = 1`.
- **Memoization**: Use a 1D array `memo`.

**Java Code**:
```java
public class ClimbingStairsMemo {
    public static int climbStairs(int n) {
        int[] memo = new int[n + 1]; // Initialize memo array
        return climbStairsHelper(n, memo);
    }
    
    private static int climbStairsHelper(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] != 0) return memo[n];
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println("Ways to climb " + n + " stairs: " + climbStairs(n)); // Output: 5
    }
}
```

**Recursion Tree Diagram** (for `n = 4`):
The recursion tree shows how `climbStairsHelper(4)` breaks into subproblems. Memoized states are marked with an asterisk (*) and reused.

```
                climbStairsHelper(4)
                   /            \
                  /              \
         helper(3)                helper(2)
         /       \                /       \
    helper(2)  helper(1)=1*    helper(1)=1* helper(0)=1*
    /       \
helper(1)=1* helper(0)=1*
```

**Explanation**:
- `helper(4)` calls `helper(3)` and `helper(2)`.
- `helper(3)` calls `helper(2)` and `helper(1)`. `helper(1)` returns 1 (base case) and is memoized (`memo[1] = 1`).
- `helper(2)` calls `helper(1)` and `helper(0)`. Both are base cases (`memo[1] = 1`, `memo[0] = 1`).
- When `helper(3)` needs `helper(2)`, it computes `memo[2] = memo[1] + memo[0] = 1 + 1 = 2`.
- When `helper(4)` needs `helper(2)`, it uses `memo[2] = 2` (marked with *), avoiding recomputation.
- Finally, `memo[4] = memo[3] + memo[2] = 3 + 2 = 5`.

**Dry Run Diagram** (for `n = 4`):
The dry run shows how the `memo` array is filled as recursive calls return.

```
Step | Call           | Memo Array [0,1,2,3,4] | Result
-----|----------------|------------------------|--------
1    | helper(4)      | [0,0,0,0,0]            | Needs helper(3), helper(2)
2    | helper(3)      | [0,0,0,0,0]            | Needs helper(2), helper(1)
3    | helper(2)      | [0,0,0,0,0]            | Needs helper(1), helper(0)
4    | helper(1)      | [0,1,0,0,0]            | Returns 1, memo[1] = 1
5    | helper(0)      | [1,1,0,0,0]            | Returns 1, memo[0] = 1
6    | helper(2)      | [1,1,2,0,0]            | memo[2] = memo[1] + memo[0] = 2
7    | helper(1)      | [1,1,2,0,0]            | Returns memo[1] = 1
8    | helper(3)      | [1,1,2,3,0]            | memo[3] = memo[2] + memo[1] = 3
9    | helper(2)      | [1,1,2,3,0]            | Returns memo[2] = 2
10   | helper(4)      | [1,1,2,3,5]            | memo[4] = memo[3] + memo[2] = 5
```

**Explanation**:
- The `memo` array starts as `[0,0,0,0,0]`.
- Base cases fill `memo[0] = 1`, `memo[1] = 1`.
- `memo[2]` is computed as 2, `memo[3]` as 3, and `memo[4]` as 5.
- Reused states (e.g., `memo[1]`, `memo[2]`) avoid redundant calls.

**Time Complexity**: O(n) (each state computed once).
**Space Complexity**: O(n) (memo array) + O(n) (recursion stack).

---

### Example 2: House Robber (Memoization)
**Problem**: Find the maximum amount you can rob from houses (`nums`) without robbing adjacent houses.

**Solution Approach**:
- **State**: `dp[i]` is the maximum amount robbed up to house `i`.
- **Recursive Relation**: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`.
- **Base Cases**: `dp[0] = nums[0]`, `dp[1] = max(nums[0], nums[1])`.
- **Memoization**: Use a 1D array `memo`.

**Java Code**:
```java
public class HouseRobberMemo {
    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] memo = new int[nums.length];
        for (int i = 0; i < nums.length; i++) memo[i] = -1;
        return robHelper(nums, nums.length - 1, memo);
    }
    
    private static int robHelper(int[] nums, int i, int[] memo) {
        if (i == 0) return nums[0];
        if (i == 1) return Math.max(nums[0], nums[1]);
        if (memo[i] != -1) return memo[i];
        memo[i] = Math.max(robHelper(nums, i - 1, memo), robHelper(nums, i - 2, memo) + nums[i]);
        return memo[i];
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 9, 3};
        System.out.println("Maximum amount to rob: " + rob(nums)); // Output: 12
    }
}
```

**Recursion Tree Diagram** (for `nums = [2, 7, 9, 3]`, `n = 3`):
The tree shows recursive calls for `robHelper(3)`. Memoized states are marked with *.

```
                robHelper(3)
                   /            \
                  /              \
         helper(2)            helper(1)=7*
         /       \                
    helper(1)=7* helper(0)=2*
```

**Explanation**:
- `helper(3)` calls `helper(2)` (skip house 3) and `helper(1) + nums[3]` (rob house 3).
- `helper(2)` calls `helper(1)` and `helper(0) + nums[2]`.
- `helper(0)` returns 2 (`memo[0] = 2`).
- `helper(1)` returns `max(nums[0], nums[1]) = max(2, 7) = 7` (`memo[1] = 7`).
- `helper(2)` computes `max(helper(1), helper(0) + nums[2]) = max(7, 2 + 9) = 11` (`memo[2] = 11`).
- `helper(3)` uses `memo[1] = 7` and computes `max(helper(2), helper(1) + nums[3]) = max(11, 7 + 3) = 11` (`memo[3] = 11`).

**Dry Run Diagram** (for `nums = [2, 7, 9, 3]`):
The dry run shows the `memo` array updates.

```
Step | Call           | Memo Array [0,1,2,3] | Result
-----|----------------|----------------------|--------
1    | helper(3)      | [-1,-1,-1,-1]        | Needs helper(2), helper(1)
2    | helper(2)      | [-1,-1,-1,-1]        | Needs helper(1), helper(0)
3    | helper(1)      | [-1,7,-1,-1]         | Returns 7, memo[1] = 7
4    | helper(0)      | [2,7,-1,-1]          | Returns 2, memo[0] = 2
5    | helper(2)      | [2,7,11,-1]          | memo[2] = max(memo[1], memo[0] + nums[2]) = 11
6    | helper(1)      | [2,7,11,-1]          | Returns memo[1] = 7
7    | helper(3)      | [2,7,11,11]          | memo[3] = max(memo[2], memo[1] + nums[3]) = 11
```

**Explanation**:
- `memo` starts as `[-1,-1,-1,-1]`.
- Base cases fill `memo[0] = 2`, `memo[1] = 7`.
- `memo[2] = 11`, `memo[3] = 11` are computed using the max formula.
- `memo[1]` is reused, avoiding recomputation.

**Time Complexity**: O(n).
**Space Complexity**: O(n) + recursion stack.

---

## When to Use Memoization
- **Recursive Structure**: Problems with clear recursive relations.
- **Sparse States**: When not all states are needed.
- **Prototyping**: Easier to implement initially.

## When Memoization Might Not Work
1. **Deep Recursion**: StackOverflowError for large `n` (e.g., `n > 10^5`).
   - **Solution**: Use bottom-up DP.
2. **Memory Overhead**: Extra space for recursion stack.
   - **Solution**: Optimize with bottom-up or variables.
3. **Complex Transitions**: Hard to manage in recursion.
   - **Solution**: Simplify or use bottom-up.
4. **Initialization Bugs**: Sentinel values may conflict.
   - **Solution**: Use hash map or bottom-up.
5. **Performance**: Recursive calls are slower than loops.
   - **Solution**: Use bottom-up for speed.

---

## Tips for Memoization
- Use arrays for Linear DP states.
- Handle edge cases (e.g., `n = 0`).
- Initialize `memo` with sentinels (e.g., `-1`).
- Test with small inputs to verify recursion.
- Check stack limits for large `n`.

---
---

## Conclusion
Memoization is a powerful tool for Linear DP, made clearer with recursion trees and dry run diagrams. These visuals show how recursive calls build the solution and how memoization eliminates redundancy. Practice the examples, draw diagrams for new problems, and consider switching to bottom-up DP for large inputs or performance needs.

