# ⭐ Combination Sum
Given an array of **distinct** integers `candidates` and a target integer `target`, find all unique combinations of candidates where the chosen numbers sum to `target`.

* You can use the same number multiple times.
* Combinations are unique based on frequency of numbers.
* Return all valid combinations in any order.

---

### 📌 Example

#### Input:

```
candidates = [2,3,6,7], target = 7
```

#### Output:

```
[[2,2,3],[7]]
```

**Explanation:**

* `2 + 2 + 3 = 7`
* `7 = 7`
* These are the only valid unique combinations.

---

### ✅ Java Code with Comments

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        solve(0, candidates, target, ans, new ArrayList<>());
        return ans;
    }

    // Recursive backtracking method
    private void solve(int idx, int[] nums, int target, List<List<Integer>> ans, List<Integer> list) {
        // If target < 0 or index out of bounds, stop exploring
        if (idx >= nums.length || target < 0) return;

        // If exact target is reached, add the current combination to answer
        if (target == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }

        // Include current number and recurse with reduced target (can reuse same index)
        list.add(nums[idx]);
        solve(idx, nums, target - nums[idx], ans, list);

        // Backtrack by removing last added number
        list.remove(list.size() - 1);

        // Exclude current number and move to next index
        solve(idx + 1, nums, target, ans, list);
    }
}
```

This solution uses **backtracking** to explore all possible combinations with pruning when target becomes negative or indices are exhausted.

---
# ⭐ Combination Sum II

Given a collection of candidate numbers (`candidates`) and a target number (`target`), find all **unique combinations** where the candidate numbers sum to `target`.

* Each number in `candidates` **may only be used once** in each combination.
* The solution set **must not contain duplicate combinations**.

---

### 📌 Example

#### Input:

```
candidates = [10,1,2,7,6,1,5], target = 8
```

#### Output:

```
[
  [1,1,6],
  [1,2,5],
  [1,7],
  [2,6]
]
```

### ✅ Java Code with Comments

```java
import java.util.*;

public class Solution {
    private List<List<Integer>> res;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        res = new ArrayList<>();
        Arrays.sort(candidates);  // Sort to handle duplicates easily
        dfs(candidates, target, 0, new ArrayList<>(), 0);
        return res;
    }

    // Backtracking method
    private void dfs(int[] candidates, int target, int i, List<Integer> cur, int total) {
        // If exact target reached, add current combination
        if (total == target) {
            res.add(new ArrayList<>(cur));
            return;
        }

        // If total exceeds target or index goes beyond array, stop exploration
        if (total > target || i == candidates.length) {
            return;
        }

        // Include candidates[i] and recurse
        cur.add(candidates[i]);
        dfs(candidates, target, i + 1, cur, total + candidates[i]);
        cur.remove(cur.size() - 1);  // Backtrack

        // Skip duplicates for the next iteration
        while (i + 1 < candidates.length && candidates[i] == candidates[i + 1]) {
            i++;
        }

        // Exclude candidates[i] and recurse
        dfs(candidates, target, i + 1, cur, total);
    }
}
```

**Explanation:**

* The array is sorted to group duplicates together.
* After including an element, recursion moves forward.
* After backtracking, duplicate elements are skipped to avoid repeated combinations.
* Each element can be used only once per combination (`i + 1` in recursion).

---
# ⭐ Subsets II


Given an integer array `nums` that **may contain duplicates**, return **all possible subsets** (the power set).

* The solution set **must not contain duplicate subsets**.
* Return the solution in **any order**.


### 📌 Example

#### Input:

```
nums = [1, 2, 2]
```

#### Output:

```
[[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
```
--

### ✅ Java Code with Comments

```java
import java.util.*;

public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        HashSet<List<Integer>> ans = new HashSet<>(); // To avoid duplicate subsets
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);  // Sort to handle duplicates easily
        solve(0, nums, list, ans);
        return new ArrayList<>(ans);  // Convert set back to list
    }

    // Backtracking method to generate subsets
    public void solve(int idx, int[] nums, List<Integer> list, HashSet<List<Integer>> ans) {
        if (idx == nums.length) {
            if (!ans.contains(list)) {
                ans.add(new ArrayList<>(list));  // Add a copy of current subset
            }
            return;
        }

        // Include nums[idx]
        list.add(nums[idx]);
        solve(idx + 1, nums, list, ans);

        // Exclude nums[idx]
        list.remove(list.size() - 1);
        solve(idx + 1, nums, list, ans);
    }
}
```

**Explanation:**

* Use backtracking to explore both including and excluding each element.
* Sort the array to group duplicates, helping in skipping duplicates.
* Use a `HashSet` to store subsets and avoid duplicates.
* Return all unique subsets after processing.

---
# ⭐ Palindrome Partitioning


Given a string `s`, partition `s` such that **every substring** of the partition is a **palindrome**. Return **all possible palindrome partitioning** of `s`.

### 📌 Example

#### Input:

```
s = "aab"
```

#### Output:

```
[["a", "a", "b"], ["aa", "b"]]
```

---

### ✅ Java Code with Comments

```java
import java.util.*;

public class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> list = new ArrayList<>();
        solve(0, s, list, ans);
        return ans;
    }

    // Backtracking helper method to generate palindrome partitions
    public void solve(int idx, String s, List<String> list, List<List<String>> ans) {
        if (idx == s.length()) {
            ans.add(new ArrayList<>(list));  // Add current partition to result
            return;
        }

        for (int i = idx; i < s.length(); i++) {
            String sub = s.substring(idx, i + 1);

            // Check if the current substring is a palindrome
            if (isPalindrome(sub)) {
                list.add(sub);              // Choose current palindrome substring
                solve(i + 1, s, list, ans); // Explore further partitions
                list.remove(list.size() - 1); // Backtrack
            }
        }
    }

    // Utility method to check if a string is palindrome
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left <= right) {
            if (s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }
}
```

**Explanation:**

* Use backtracking to explore all partitions.
* For each starting index, try all possible substrings.
* Check if a substring is palindrome; if yes, include it and recurse.
* Backtrack after exploring each choice.
* Collect all partitions where all substrings are palindromes.

---
# ⭐ N-Queens


The n-queens puzzle is the problem of placing **n** queens on an **n x n** chessboard such that no two queens attack each other.

Given an integer `n`, return **all distinct solutions** to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where `'Q'` and `'.'` both indicate a queen and an empty space, respectively.

### 📌 Example

#### Input:

```
n = 4
```

#### Output:

```
[
  [".Q..","...Q","Q...","..Q."],
  ["..Q.","Q...","...Q",".Q.."]
]
```

**Explanation:**
There exist two distinct solutions to the 4-queens puzzle as shown above.


### ✅ Java Code with Comments

```java
import java.util.*;

public class Solution {
    public List<List<String>> solveNQueens(int n) {
        // Initialize the chessboard with '.'
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        List<List<String>> ans = new ArrayList<>();
        placeQueens(board, 0, ans);
        return ans;
    }

    // Recursive function to place queens row by row
    private void placeQueens(char[][] board, int row, List<List<String>> ans) {
        if (row == board.length) {
            // All queens are placed, add the board configuration to results
            ans.add(constructBoard(board));
            return;
        }

        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';           // Place queen
                placeQueens(board, row + 1, ans); // Recur for next row
                board[row][col] = '.';           // Backtrack
            }
        }
    }

    // Construct the board configuration into a list of strings
    private List<String> constructBoard(char[][] board) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            res.add(new String(board[i]));
        }
        return res;
    }

    // Check if placing a queen at (row, col) is safe
    private boolean isSafe(char[][] board, int row, int col) {
        // Check vertical upwards
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true; // Safe to place queen
    }
}
```


**Explanation:**

* Use backtracking to place queens row by row.
* For each row, try all columns and check if it is safe to place a queen.
* If safe, place the queen and recurse for the next row.
* If all queens are placed (row == n), add the configuration to result.
* Backtrack when needed to explore other possibilities.

---
