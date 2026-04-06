
## 📊 **The Universal Recursion Template** (Use for EVERY problem)

```java
public ReturnType solve(Problem p) {
    // STEP 1: Base Case - Smallest possible input
    if (isBaseCase(p)) {
        return directAnswer(p);
    }
    
    // STEP 2: Make problem smaller (1 step only)
    Problem smallerProblem = makeSmaller(p);
    
    // STEP 3: Trust recursion gives answer for smaller problem
    ReturnType smallerAnswer = solve(smallerProblem);
    
    // STEP 4: Combine (current step work + smaller answer)
    ReturnType currentAnswer = combine(p, smallerAnswer);
    
    return currentAnswer;
}
```

---

## 🎯 **The 3-Question Framework** (Ask these BEFORE coding)

| Question | What to look for |
|----------|------------------|
| **1. Base case kya hai?** | Smallest input where answer is obvious (n=0, n=1, empty array) |
| **2. Ek step mein kya kaam hai?** | What ONE calculation/decision at current level? |
| **3. Chhota problem kaise bana?** | n-1? n/2? left subtree? right subtree? |

---

## 📈 **Tree Diagram Method** (Visualize recursion)

For any problem, draw this tree:

```
                    f(5)  ← Level 0
                   /    \
                f(4)    f(3)  ← Level 1
               /   \   /   \
            f(3) f(2) f(2) f(1) ← Level 2
            ... (until base case)
```

**How to draw:**
1. Write function call at root
2. Branch for each recursive call
3. Stop at base case (leaves)
4. Propagate answers upward

---

## 🔥 **Type 1: Return Value Problems** (Calculate & Return)

### **Example 1: Factorial**
```java
// Question: n! = n * (n-1) * (n-2) * ... * 1

public int factorial(int n) {
    // 1. Base case: smallest factorial
    if (n == 0 || n == 1) {
        return 1;  // 0! = 1, 1! = 1
    }
    
    // 2. Make smaller: n-1
    // 3. Trust recursion gives (n-1)!
    // 4. Combine: n * (n-1)!
    return n * factorial(n - 1);
}

// Tree Diagram:
// factorial(3)
//    ↓
// 3 * factorial(2)
//        ↓
//    2 * factorial(1)
//            ↓
//            1
// Backtrack: 2*1=2, 3*2=6
```

### **Example 2: Fibonacci**
```java
// Question: fib(n) = fib(n-1) + fib(n-2), fib(0)=0, fib(1)=1

public int fib(int n) {
    // Base cases
    if (n == 0) return 0;
    if (n == 1) return 1;
    
    // Two smaller problems: n-1 and n-2
    // Trust both give correct answers
    // Combine by addition
    return fib(n - 1) + fib(n - 2);
}

// Tree Diagram:
//                    fib(4)
//                   /      \
//              fib(3)      fib(2)
//             /    \       /    \
//         fib(2) fib(1) fib(1) fib(0)
//         /   \    1      1      0
//     fib(1) fib(0)
//        1      0
// Result: fib(2)=1, fib(3)=2, fib(4)=3
```

### **Example 3: Array Sum**
```java
// Question: Sum all elements of array

public int sum(int[] arr, int index) {
    // Base case: reached end of array
    if (index == arr.length) {
        return 0;
    }
    
    // Smaller: index+1 (move forward)
    // Combine: current + sum of rest
    return arr[index] + sum(arr, index + 1);
}
```

---

## 🎨 **Type 2: Build Answer Problems** (Subsets, Permutations)

### **Template for Building Answers**
```java
public void solve(Problem p, List<Type> current, List<List<Type>> result) {
    // Base case: valid solution found
    if (isValidSolution(current)) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Try all choices at current step
    for (Choice choice : getChoices(p)) {
        // Make choice
        current.add(choice);
        
        // Recurse with updated state
        solve(updatedProblem, current, result);
        
        // UNDO choice (Backtracking)
        current.remove(current.size() - 1);
    }
}
```

### **Example 1: Generate All Subsets**
```java
// Question: Given [1,2,3], find all subsets

public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, int start, 
                       List<Integer> current, 
                       List<List<Integer>> result) {
    // Base: Add current subset to result
    result.add(new ArrayList<>(current));
    
    // Try including each remaining element
    for (int i = start; i < nums.length; i++) {
        // Choose
        current.add(nums[i]);
        
        // Recurse (can't reuse same index)
        backtrack(nums, i + 1, current, result);
        
        // Undo (Backtrack)
        current.remove(current.size() - 1);
    }
}

// Tree Diagram:
//                            []
//            /        |        \
//          [1]       [2]       [3]
//         /   \       |
//      [1,2] [1,3]  [2,3]
//       |
//    [1,2,3]
```

### **Example 2: Generate All Permutations**
```java
// Question: Given [1,2,3], find all permutations

public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrack(nums, used, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, boolean[] used, 
                       List<Integer> current, 
                       List<List<Integer>> result) {
    // Base case: permutation of full length
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Try each unused element at current position
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            // Choose
            used[i] = true;
            current.add(nums[i]);
            
            // Recurse
            backtrack(nums, used, current, result);
            
            // Undo
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }
}

// Tree Diagram:
//                            []
//            /               |               \
//          [1]              [2]              [3]
//       /     \           /     \           /     \
//    [1,2]   [1,3]    [2,1]   [2,3]     [3,1]   [3,2]
//      |       |         |       |         |       |
//   [1,2,3] [1,3,2]  [2,1,3] [2,3,1]  [3,1,2] [3,2,1]
```

### **Example 3: Combination Sum**
```java
// Question: Find combinations that sum to target (can reuse numbers)

public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(candidates, target, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] candidates, int remaining, 
                       int start, List<Integer> current,
                       List<List<Integer>> result) {
    // Base case 1: Found valid combination
    if (remaining == 0) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Base case 2: Exceeded target
    if (remaining < 0) {
        return;
    }
    
    // Try each candidate
    for (int i = start; i < candidates.length; i++) {
        // Choose
        current.add(candidates[i]);
        
        // Recurse (can reuse same i)
        backtrack(candidates, remaining - candidates[i], 
                 i, current, result);
        
        // Undo
        current.remove(current.size() - 1);
    }
}
```

---

## 🌲 **Type 3: Tree/Graph Traversal**

### **Template for Tree Traversal**
```java
public void traverse(TreeNode root) {
    // Base case: null node
    if (root == null) {
        return;
    }
    
    // Process current node (Pre-order)
    process(root);
    
    // Recursively traverse children
    traverse(root.left);
    traverse(root.right);
    
    // Process after children (Post-order)
    // process(root);
}
```

### **Example: Binary Tree Path Sum**
```java
// Question: Find if root-to-leaf path sum equals target

public boolean hasPathSum(TreeNode root, int targetSum) {
    // Base case: empty tree
    if (root == null) {
        return false;
    }
    
    // Base case: leaf node
    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }
    
    // Recurse on left and right with reduced target
    return hasPathSum(root.left, targetSum - root.val) ||
           hasPathSum(root.right, targetSum - root.val);
}
```

---

## 🚀 **Memoization (DP with Recursion)** - Top-Down Approach

### **Template**
```java
// Step 1: Create memo array/map
int[] memo;

public int solve(Problem p) {
    // Step 2: Initialize memo with -1
    memo = new int[n + 1];
    Arrays.fill(memo, -1);
    
    return dp(p);
}

private int dp(Problem p) {
    // Step 3: Base case
    if (isBaseCase(p)) {
        return baseValue;
    }
    
    // Step 4: Check memo
    if (memo[index] != -1) {
        return memo[index];
    }
    
    // Step 5: Calculate and store
    int result = calculate(p);
    memo[index] = result;
    
    return result;
}
```

### **Example: Fibonacci with Memoization**
```java
public int fib(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return fibMemo(n, memo);
}

private int fibMemo(int n, int[] memo) {
    // Base cases
    if (n == 0) return 0;
    if (n == 1) return 1;
    
    // Check memo
    if (memo[n] != -1) {
        return memo[n];
    }
    
    // Calculate and store
    memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    return memo[n];
}

// Tree becomes linear instead of exponential!
// Without memo: O(2^n)
// With memo: O(n)
```

### **Example: Climbing Stairs**
```java
// Question: Climb n stairs, can take 1 or 2 steps at a time
// Find number of distinct ways

public int climbStairs(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return climb(n, memo);
}

private int climb(int n, int[] memo) {
    if (n < 0) return 0;
    if (n == 0) return 1;
    
    if (memo[n] != -1) return memo[n];
    
    memo[n] = climb(n - 1, memo) + climb(n - 2, memo);
    return memo[n];
}
```

---

## 📝 **Complete Problem-Solving Checklist**

### **Phase 1: Understand (2 minutes)**
- [ ] What is the input?
- [ ] What is the output?
- [ ] Can I draw a small example?
- [ ] What are the constraints?

### **Phase 2: Identify Type (1 minute)**
```
□ Type 1 (Return value) → Calculate and return something
□ Type 2 (Build answer) → Generate combinations/permutations
□ Type 3 (Traversal) → Tree/Graph navigation
```

### **Phase 3: Apply 3 Questions (3 minutes)**
```
1. Base case? → ________________________________
2. One step work? → ____________________________
3. Smaller problem? → __________________________
```

### **Phase 4: Draw Tree (3 minutes)**
```
Draw recursion tree for n=3 or n=4
Identify overlapping subproblems for memoization
```

### **Phase 5: Code (5 minutes)**
```
Start with recursion
Add memoization if overlapping subproblems
```

### **Phase 6: Dry Run (3 minutes)**
```
Trace with smallest input (n=0, n=1)
Trace with n=2
Trace with n=3
Verify against example
```

---

## 🎯 **Quick Reference: When to Use What**

| Problem Type | Pattern | Memoization? |
|--------------|---------|--------------|
| Factorial | `n * f(n-1)` | No (no overlap) |
| Fibonacci | `f(n-1) + f(n-2)` | YES |
| Subsets | Include/exclude each element | No (build all) |
| Permutations | Try each unused element | No (build all) |
| Combination Sum | Try each with backtrack | No (build all) |
| Tree Traversal | Visit left + right | No (unique paths) |
| DP Problems | Minimax, count ways | YES |

---

## 💡 **Mental Model to Overcome Anxiety**

```
╔══════════════════════════════════════════════════════════╗
║  "Recursion se darna nahi, trust karna hai"              ║
║                                                          ║
║  ❌ WRONG: Trying to trace entire recursion in mind      ║
║  ✅ RIGHT: "Mujhe bas EK level sochna hai. Baki         ║
║              recursion khud handle karega"               ║
║                                                          ║
║  Think like this:                                        ║
║  "Agar main maan loon ki solve(n-1) sahi answer         ║
║   dega, toh solve(n) ke liye mujhe bas current step     ║
║   karna hai."                                            ║
╚══════════════════════════════════════════════════════════╝
```

---

## 🏋️ **Practice Problems (In Order of Difficulty)**

### Easy (Start here)
1. Factorial
2. Fibonacci
3. Power of a number (x^n)
4. Sum of digits
5. Palindrome check

### Medium (Build confidence)
1. Generate all subsets
2. Generate all permutations
3. Combination Sum
4. Letter Combinations of Phone Number
5. Generate Parentheses

### Hard (Master level)
1. N-Queens
2. Sudoku Solver
3. Word Search
4. Word Break (with memo)
5. House Robber (DP with memo)

---

## 📌 **Cheat Sheet for Interviews**

```java
// SUBSETS
void subsets(int[] nums, int start, List<Integer> curr, List<List<Integer>> result) {
    result.add(new ArrayList<>(curr));
    for (int i = start; i < nums.length; i++) {
        curr.add(nums[i]);
        subsets(nums, i + 1, curr, result);
        curr.remove(curr.size() - 1);
    }
}

// PERMUTATIONS
void permute(int[] nums, boolean[] used, List<Integer> curr, List<List<Integer>> result) {
    if (curr.size() == nums.length) {
        result.add(new ArrayList<>(curr));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            used[i] = true;
            curr.add(nums[i]);
            permute(nums, used, curr, result);
            used[i] = false;
            curr.remove(curr.size() - 1);
        }
    }
}

// COMBINATION SUM (can reuse)
void combinationSum(int[] candidates, int target, int start, List<Integer> curr, List<List<Integer>> result) {
    if (target == 0) {
        result.add(new ArrayList<>(curr));
        return;
    }
    if (target < 0) return;
    for (int i = start; i < candidates.length; i++) {
        curr.add(candidates[i]);
        combinationSum(candidates, target - candidates[i], i, curr, result);
        curr.remove(curr.size() - 1);
    }
}

// DP WITH MEMO
int dp(int n, int[] memo) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    memo[n] = dp(n-1, memo) + dp(n-2, memo);
    return memo[n];
}
```

---

## 🎓 **Final Advice**

1. **Start with pen and paper** - Draw the tree for n=3 before coding
2. **Trust the recursion** - Don't trace beyond 2 levels deep
3. **Always identify base case first** - That's your stopping condition
4. **Backtracking = Make choice → Recurse → Undo choice**
5. **Memoization = Cache results of overlapping subproblems**

**Remember:** Every recursion expert was once confused. The only difference is they practiced with small examples and built trust in the process. You've got this! 💪
