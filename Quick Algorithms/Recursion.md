# Recursion 
## **Recursion Basics**

Recursion ek **function ka woh tareeka hai jo khud ko call kare**. Jaise Russian dolls - ek ke andar ek.

### **Real-life Example:**
Russian Dolls (Matryoshka) - sabse badi doll kholo, andar ek chhoti doll, phir uske andar aur chhoti, jab tak sabse chhoti na mil jaye.

### **Recursion ke 3 Must-Have Components:**

| Component | Explanation | Example |
|-----------|-------------|---------|
| **Base Case** | Rukne ki condition | `if(n == 0) return 1` |
| **Recursive Call** | Khud ko call karna | `return n * factorial(n-1)` |
| **Progress** | Base case ki taraf badhna | `n-1` (ghat raha hai) |

### **Recursion vs Iteration**

| Aspect | Recursion | Iteration |
|--------|-----------|-----------|
| Code Length | Chhota, elegant | Thoda lamba |
| Readability | Natural (mathematical) | Thoda complex |
| Memory | Stack use (zyada) | Less memory |
| Performance | Slow (function call overhead) | Fast |
| Infinite Loop Risk | Stack overflow | Loop forever |

---

## **Recursion Kaise Kaam Karta Hai? (Call Stack)**

```java
factorial(5)
│
├─> 5 * factorial(4)
│   │
│   ├─> 4 * factorial(3)
│   │   │
│   │   ├─> 3 * factorial(2)
│   │   │   │
│   │   │   ├─> 2 * factorial(1)
│   │   │   │   │
│   │   │   │   └─> 1 * factorial(0) → base case returns 1
│   │   │   │
│   │   │   └─> returns 2 * 1 = 2
│   │   │
│   │   └─> returns 3 * 2 = 6
│   │
│   └─> returns 4 * 6 = 24
│
└─> returns 5 * 24 = 120
```

**Stack Visualization:**
```
Step 1: factorial(5) called
Step 2: factorial(4) pushed
Step 3: factorial(3) pushed
Step 4: factorial(2) pushed
Step 5: factorial(1) pushed
Step 6: factorial(0) pushed → base case reached
Step 7: Values start returning (unwinding)
```

---

## **Basic Recursion Examples**

### **1. Factorial** ⭐

```java
class Factorial {
    // Basic recursive factorial
    public int factorial(int n) {
        // Base case
        if(n == 0 || n == 1) return 1;
        
        // Recursive case
        return n * factorial(n - 1);
    }
    
    // Tail recursion (optimized by compiler)
    public int factorialTail(int n, int accumulator) {
        if(n == 0 || n == 1) return accumulator;
        return factorialTail(n - 1, n * accumulator);
    }
    
    // Time: O(n), Space: O(n) for stack
}
```

### **2. Fibonacci** ⭐

```java
class Fibonacci {
    // Basic recursion (inefficient - O(2^n))
    public int fib(int n) {
        if(n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }
    
    // With memoization (DP) - O(n)
    private Map<Integer, Integer> memo = new HashMap<>();
    
    public int fibMemo(int n) {
        if(n <= 1) return n;
        
        if(memo.containsKey(n)) return memo.get(n);
        
        int result = fibMemo(n - 1) + fibMemo(n - 2);
        memo.put(n, result);
        return result;
    }
    
    // Time: O(2^n) worst, O(n) with memoization
    // Space: O(n) stack
}
```

### **3. Power (Exponentiation)** ⭐

```java
class Power {
    // Simple recursion
    public double power(double x, int n) {
        if(n == 0) return 1;
        if(n < 0) return 1 / power(x, -n);
        return x * power(x, n - 1);
    }
    
    // Optimized: O(log n)
    public double powerOptimized(double x, int n) {
        if(n == 0) return 1;
        if(n < 0) {
            x = 1 / x;
            n = -n;
        }
        
        double half = powerOptimized(x, n / 2);
        if(n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
    
    // Time: O(n) simple, O(log n) optimized
}
```

---

## **1. Generate All Subsets (Power Set)** ⭐⭐

### Theory
**Problem:** Array ke saare subsets generate karo.

**Algorithm:**
- Har element ke liye do choice: include karo ya nahi
- Recursively dono possibilities explore karo

### Java Code
```java
class Subsets {
    // Method 1: Backtracking
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> current, 
                          List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        
        for(int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);  // Backtrack
        }
    }
    
    // Method 2: Include/Exclude pattern
    public List<List<Integer>> subsetsIncludeExclude(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        generate(0, nums, new ArrayList<>(), result);
        return result;
    }
    
    private void generate(int index, int[] nums, List<Integer> current, 
                         List<List<Integer>> result) {
        if(index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Exclude current element
        generate(index + 1, nums, current, result);
        
        // Include current element
        current.add(nums[index]);
        generate(index + 1, nums, current, result);
        current.remove(current.size() - 1);
    }
    
    // Time: O(2^n), Space: O(n) for recursion stack
}
```

---

## **2. Generate All Permutations** ⭐⭐⭐

### Theory
**Problem:** Array ke saare permutations generate karo.

**Algorithm (Backtracking):**
- Har position ke liye remaining elements mein se choose karo
- Swap karke explore karo, phir backtrack karo

### Java Code
```java
class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        if(start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for(int num : nums) permutation.add(num);
            result.add(permutation);
            return;
        }
        
        for(int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            backtrack(nums, start + 1, result);
            swap(nums, start, i);  // Backtrack
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // For string permutations
    public List<String> permuteString(String s) {
        List<String> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        backtrackString(chars, 0, result);
        return result;
    }
    
    private void backtrackString(char[] chars, int start, List<String> result) {
        if(start == chars.length) {
            result.add(new String(chars));
            return;
        }
        
        for(int i = start; i < chars.length; i++) {
            swapChar(chars, start, i);
            backtrackString(chars, start + 1, result);
            swapChar(chars, start, i);
        }
    }
    
    private void swapChar(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
    
    // Time: O(n * n!), Space: O(n) recursion stack
}
```

---

## **3. Combination Sum** ⭐⭐⭐

### Theory
**Problem:** Array se elements choose karo jinka sum target ke equal ho (unlimited times use kar sakte ho).

**Algorithm:**
- Har element ko multiple times include kar sakte ho
- Backtracking with remaining sum

### Java Code
```java
class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);  // Optional: for pruning
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int remaining, int start,
                          List<Integer> current, List<List<Integer>> result) {
        if(remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for(int i = start; i < candidates.length; i++) {
            if(candidates[i] > remaining) break;  // Pruning
            
            current.add(candidates[i]);
            backtrack(candidates, remaining - candidates[i], i, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // Combination Sum II (each element used once)
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack2(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack2(int[] candidates, int remaining, int start,
                           List<Integer> current, List<List<Integer>> result) {
        if(remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for(int i = start; i < candidates.length; i++) {
            if(candidates[i] > remaining) break;
            
            // Skip duplicates
            if(i > start && candidates[i] == candidates[i - 1]) continue;
            
            current.add(candidates[i]);
            backtrack2(candidates, remaining - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // Time: O(2^n), Space: O(n) recursion stack
}
```

---

## **4. Generate Parentheses** ⭐⭐⭐

### Theory
**Problem:** n pairs of parentheses ke saare valid combinations generate karo.

**Rules:**
- Opening bracket count < n tabhi add kar sakte ho
- Closing bracket count < opening count tabhi add kar sakte ho

### Java Code
```java
class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, StringBuilder current,
                          int open, int close, int n) {
        if(current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }
        
        if(open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);
        }
        
        if(close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);
        }
    }
    
    // Time: O(4^n / sqrt(n)), Space: O(n)
}
```

---

## **5. Word Search** ⭐⭐⭐

### Theory
**Problem:** Board mein adjacent cells se word form kar sakte ho ya nahi.

**Algorithm (Backtracking):**
- Har cell se DFS start karo
- Current character match karo
- Visited mark karo
- 4 directions mein explore karo

### Java Code
```java
class WordSearch {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(dfs(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int i, int j, 
                        int index, boolean[][] visited) {
        if(index == word.length()) return true;
        
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length ||
           visited[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }
        
        visited[i][j] = true;
        
        // Explore all 4 directions
        boolean found = dfs(board, word, i + 1, j, index + 1, visited) ||
                        dfs(board, word, i - 1, j, index + 1, visited) ||
                        dfs(board, word, i, j + 1, index + 1, visited) ||
                        dfs(board, word, i, j - 1, index + 1, visited);
        
        visited[i][j] = false;  // Backtrack
        
        return found;
    }
    
    // Time: O(m * n * 4^L), Space: O(L) recursion stack
}
```

---

## **6. N-Queens Problem** ⭐⭐⭐

### Theory
**Problem:** N×N chessboard pe N queens rakho taaki koi do queens ek dusre ko attack na kar sakein.

**Rules:**
- Same row mein nahi ho sakti
- Same column mein nahi ho sakti
- Same diagonal mein nahi ho sakti

### Java Code
```java
class NQueens {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        for(int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrack(board, 0, result);
        return result;
    }
    
    private void backtrack(char[][] board, int row, List<List<String>> result) {
        if(row == board.length) {
            result.add(construct(board));
            return;
        }
        
        for(int col = 0; col < board.length; col++) {
            if(isValid(board, row, col)) {
                board[row][col] = 'Q';
                backtrack(board, row + 1, result);
                board[row][col] = '.';
            }
        }
    }
    
    private boolean isValid(char[][] board, int row, int col) {
        // Check column
        for(int i = 0; i < row; i++) {
            if(board[i][col] == 'Q') return false;
        }
        
        // Check diagonal (top-left)
        for(int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if(board[i][j] == 'Q') return false;
        }
        
        // Check diagonal (top-right)
        for(int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if(board[i][j] == 'Q') return false;
        }
        
        return true;
    }
    
    private List<String> construct(char[][] board) {
        List<String> result = new ArrayList<>();
        for(char[] row : board) {
            result.add(new String(row));
        }
        return result;
    }
    
    // Count solutions (without storing)
    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];  // row - col + n - 1
        boolean[] diag2 = new boolean[2 * n - 1];  // row + col
        
        return backtrackCount(0, n, cols, diag1, diag2);
    }
    
    private int backtrackCount(int row, int n, boolean[] cols, 
                               boolean[] diag1, boolean[] diag2) {
        if(row == n) return 1;
        
        int count = 0;
        for(int col = 0; col < n; col++) {
            int d1 = row - col + n - 1;
            int d2 = row + col;
            
            if(!cols[col] && !diag1[d1] && !diag2[d2]) {
                cols[col] = diag1[d1] = diag2[d2] = true;
                count += backtrackCount(row + 1, n, cols, diag1, diag2);
                cols[col] = diag1[d1] = diag2[d2] = false;
            }
        }
        return count;
    }
    
    // Time: O(n!), Space: O(n²) for board
}
```

---

## **7. Sudoku Solver** ⭐⭐⭐

### Theory
**Problem:** Sudoku puzzle solve karo (empty cells '.' se represent hain).

**Algorithm (Backtracking):**
- Empty cell dhundho
- 1 se 9 tak numbers try karo
- Valid check karo (row, column, 3×3 box)
- Recursively solve karo

### Java Code
```java
class SudokuSolver {
    public void solveSudoku(char[][] board) {
        solve(board);
    }
    
    private boolean solve(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == '.') {
                    for(char c = '1'; c <= '9'; c++) {
                        if(isValid(board, i, j, c)) {
                            board[i][j] = c;
                            
                            if(solve(board)) {
                                return true;
                            } else {
                                board[i][j] = '.';  // Backtrack
                            }
                        }
                    }
                    return false;  // No valid number found
                }
            }
        }
        return true;  // Board complete
    }
    
    private boolean isValid(char[][] board, int row, int col, char c) {
        // Check row
        for(int i = 0; i < 9; i++) {
            if(board[row][i] == c) return false;
        }
        
        // Check column
        for(int i = 0; i < 9; i++) {
            if(board[i][col] == c) return false;
        }
        
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for(int i = boxRow; i < boxRow + 3; i++) {
            for(int j = boxCol; j < boxCol + 3; j++) {
                if(board[i][j] == c) return false;
            }
        }
        
        return true;
    }
    
    // Optimized with precomputation
    public void solveSudokuOptimized(char[][] board) {
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] boxes = new boolean[9][10];
        
        // Initialize constraints
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[(i/3)*3 + j/3][num] = true;
                }
            }
        }
        
        solveOptimized(board, 0, 0, rows, cols, boxes);
    }
    
    private boolean solveOptimized(char[][] board, int row, int col,
                                   boolean[][] rows, boolean[][] cols,
                                   boolean[][] boxes) {
        if(row == 9) return true;
        if(col == 9) return solveOptimized(board, row + 1, 0, rows, cols, boxes);
        if(board[row][col] != '.') {
            return solveOptimized(board, row, col + 1, rows, cols, boxes);
        }
        
        int boxIndex = (row/3)*3 + col/3;
        
        for(int num = 1; num <= 9; num++) {
            if(!rows[row][num] && !cols[col][num] && !boxes[boxIndex][num]) {
                board[row][col] = (char)(num + '0');
                rows[row][num] = cols[col][num] = boxes[boxIndex][num] = true;
                
                if(solveOptimized(board, row, col + 1, rows, cols, boxes)) {
                    return true;
                }
                
                board[row][col] = '.';
                rows[row][num] = cols[col][num] = boxes[boxIndex][num] = false;
            }
        }
        return false;
    }
    
    // Time: O(9^(n²)), but heuristics make it faster in practice
}
```

---

## **8. Letter Combinations of a Phone Number** ⭐⭐

### Theory
**Problem:** Phone keypad ke digits se possible letter combinations generate karo.

### Java Code
```java
class PhoneNumberCombinations {
    private final String[] mapping = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits == null || digits.length() == 0) return result;
        
        backtrack(digits, 0, new StringBuilder(), result);
        return result;
    }
    
    private void backtrack(String digits, int index, StringBuilder current,
                          List<String> result) {
        if(index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        int digit = digits.charAt(index) - '0';
        String letters = mapping[digit];
        
        for(char c : letters.toCharArray()) {
            current.append(c);
            backtrack(digits, index + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
    
    // Time: O(4^n * n), Space: O(n)
}
```

---

## **9. Palindrome Partitioning** ⭐⭐

### Theory
**Problem:** String ko saare possible palindrome substrings mein divide karo.

### Java Code
```java
class PalindromePartitioning {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> current,
                          List<List<String>> result) {
        if(start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for(int end = start; end < s.length(); end++) {
            if(isPalindrome(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backtrack(s, end + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while(left < right) {
            if(s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    
    // With memoization for palindrome check
    public List<List<String>> partitionMemo(String s) {
        List<List<String>> result = new ArrayList<>();
        Boolean[][] memo = new Boolean[s.length()][s.length()];
        backtrackMemo(s, 0, new ArrayList<>(), result, memo);
        return result;
    }
    
    private void backtrackMemo(String s, int start, List<String> current,
                              List<List<String>> result, Boolean[][] memo) {
        if(start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for(int end = start; end < s.length(); end++) {
            if(isPalindromeMemo(s, start, end, memo)) {
                current.add(s.substring(start, end + 1));
                backtrackMemo(s, end + 1, current, result, memo);
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isPalindromeMemo(String s, int left, int right, Boolean[][] memo) {
        if(memo[left][right] != null) return memo[left][right];
        
        int l = left, r = right;
        while(l < r) {
            if(s.charAt(l) != s.charAt(r)) {
                memo[left][right] = false;
                return false;
            }
            l++;
            r--;
        }
        memo[left][right] = true;
        return true;
    }
    
    // Time: O(n * 2^n), Space: O(n²)
}
```

---

## **10. Restore IP Addresses** ⭐⭐

### Theory
**Problem:** String se valid IP addresses generate karo.

**Rules:**
- 4 parts, each part 0-255
- No leading zeros (except "0" itself)

### Java Code
```java
class RestoreIPAddresses {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if(s.length() < 4 || s.length() > 12) return result;
        
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> parts,
                          List<String> result) {
        if(parts.size() == 4) {
            if(start == s.length()) {
                result.add(String.join(".", parts));
            }
            return;
        }
        
        for(int len = 1; len <= 3; len++) {
            if(start + len > s.length()) break;
            
            String part = s.substring(start, start + len);
            
            // Check validity
            if(isValid(part)) {
                parts.add(part);
                backtrack(s, start + len, parts, result);
                parts.remove(parts.size() - 1);
            }
        }
    }
    
    private boolean isValid(String part) {
        if(part.length() > 3) return false;
        if(part.startsWith("0") && part.length() > 1) return false;
        
        int num = Integer.parseInt(part);
        return num >= 0 && num <= 255;
    }
    
    // Time: O(3^4) = O(81) practically constant, Space: O(n)
}
```

---

## **11. Subsets II (With Duplicates)** ⭐⭐

### Theory
**Problem:** Array ke saare unique subsets generate karo (duplicates allowed).

### Java Code
```java
class SubsetsWithDuplicates {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // Important for duplicate handling
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> current,
                          List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        
        for(int i = start; i < nums.length; i++) {
            // Skip duplicates
            if(i > start && nums[i] == nums[i - 1]) continue;
            
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // Time: O(2^n), Space: O(n)
}
```

---

## **12. Permutations II (With Duplicates)** ⭐⭐

### Theory
**Problem:** Array ke saare unique permutations generate karo (duplicates allowed).

### Java Code
```java
class PermutationsWithDuplicates {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> current,
                          List<List<Integer>> result) {
        if(current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(used[i]) continue;
            
            // Skip duplicates
            if(i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            
            used[i] = true;
            current.add(nums[i]);
            backtrack(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    // Time: O(n * n!), Space: O(n)
}
```

---

## **Recursion Patterns Summary**

### **Pattern 1: Include/Exclude (Subsets)**
```java
void recurse(int index) {
    if(index == n) { add result; return; }
    
    // Exclude
    recurse(index + 1);
    
    // Include
    add element;
    recurse(index + 1);
    remove element;
}
```

### **Pattern 2: Permutations (Swap)**
```java
void recurse(int start) {
    if(start == n) { add result; return; }
    
    for(int i = start; i < n; i++) {
        swap(start, i);
        recurse(start + 1);
        swap(start, i);
    }
}
```

### **Pattern 3: Combinations (Choose)**
```java
void recurse(int start) {
    if(current.size() == k) { add result; return; }
    
    for(int i = start; i < n; i++) {
        add nums[i];
        recurse(i + 1);
        remove last;
    }
}
```

### **Pattern 4: Backtracking with Constraints**
```java
void recurse(int index) {
    if(condition met) { add result; return; }
    if(invalid) return;
    
    for(each choice) {
        if(valid choice) {
            make choice;
            recurse(index + 1);
            undo choice;
        }
    }
}
```

---

## **Time Complexity Analysis**

| Pattern | Time Complexity | Space Complexity |
|---------|----------------|------------------|
| Factorial | O(n) | O(n) |
| Fibonacci | O(2^n) | O(n) |
| Subsets | O(2^n) | O(n) |
| Permutations | O(n * n!) | O(n) |
| Combinations | O(C(n,k) * k) | O(k) |
| N-Queens | O(n!) | O(n²) |
| Sudoku | O(9^(n²)) | O(n²) |
| Word Search | O(m * n * 4^L) | O(L) |

---

## **Interview Tips ()**

### **1. Recursion Identify Kaise Karein?**

**Clues:**
- Problem ko chhote sub-problems mein break kar sakte ho
- Tree/Graph traversal
- Generate all possibilities
- Backtracking problems
- Divide and conquer

### **2. Recursion Likhte Time:**

```java
public ReturnType solve(Parameters) {
    // 1. Base case (Rukne ki condition)
    if(base condition) return base value;
    
    // 2. Recursive case (Khud ko call)
    ReturnType subResult = solve(smaller problem);
    
    // 3. Combine results
    return combine(current, subResult);
}
```

### **3. Common Mistakes:**

| Mistake | Solution |
|---------|----------|
| No base case | Hamesha base case define karo |
| Infinite recursion | Ensure progress towards base |
| Stack overflow | Consider iterative alternative |
| Forgetting to backtrack | Undo changes after recursion |
| Not handling duplicates | Sort and skip |

### **4. Optimization Techniques:**

1. **Memoization (Top-down DP):** Store results of subproblems
2. **Pruning:** Cut branches that can't lead to solution
3. **Tail recursion:** Compiler optimize kar sakta hai
4. **Iterative conversion:** For large input sizes

---

## **Pro Tips for Interviews**

1. **Pehle recursion tree draw karo** - Visualization se clarity aati hai

2. **Base case pehle likho** - Recursion ka sabse important part

3. **Small input se test karo** - n=0, n=1, n=2 se check karo

4. **Time complexity batao** - Recurrence relation derive karo

5. **Memoization suggest karo** - Agar overlapping subproblems hain

# Most Asked 20 Hard Recursion Problems in Interview

### 1. N-Queens
**Problem:** Place N queens on N×N chessboard so no two attack each other.

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (int i = 0; i < n; i++) {
        Arrays.fill(board[i], '.');
    }
    solve(result, board, 0, n);
    return result;
}

private void solve(List<List<String>> result, char[][] board, int row, int n) {
    if (row == n) {
        result.add(construct(board));
        return;
    }
    
    for (int col = 0; col < n; col++) {
        if (isSafe(board, row, col, n)) {
            board[row][col] = 'Q';
            solve(result, board, row + 1, n);
            board[row][col] = '.'; // backtrack
        }
    }
}

private boolean isSafe(char[][] board, int row, int col, int n) {
    // Check column
    for (int i = 0; i < row; i++) {
        if (board[i][col] == 'Q') return false;
    }
    // Check diagonal (top-left)
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
        if (board[i][j] == 'Q') return false;
    }
    // Check diagonal (top-right)
    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
        if (board[i][j] == 'Q') return false;
    }
    return true;
}
```

 Har row mein ek queen rakhni hai. Har column try karo, agar safe hai toh queen rakh do, next row pe jao. Agar koi solution nahi milta toh backtrack karo (queen hata do).

---

### 2. Sudoku Solver
**Problem:** Fill empty cells ('.') with digits 1-9 following Sudoku rules.

```java
public void solveSudoku(char[][] board) {
    solve(board);
}

private boolean solve(char[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == '.') {
                for (char c = '1'; c <= '9'; c++) {
                    if (isValid(board, i, j, c)) {
                        board[i][j] = c;
                        if (solve(board)) return true;
                        board[i][j] = '.'; // backtrack
                    }
                }
                return false; // No valid number found
            }
        }
    }
    return true; // Board is full
}

private boolean isValid(char[][] board, int row, int col, char c) {
    for (int i = 0; i < 9; i++) {
        if (board[row][i] == c) return false; // Check row
        if (board[i][col] == c) return false; // Check column
        int boxRow = 3 * (row / 3) + i / 3;
        int boxCol = 3 * (col / 3) + i % 3;
        if (board[boxRow][boxCol] == c) return false; // Check 3x3 box
    }
    return true;
}
```

 Pehla empty cell dhundho, usme 1 se 9 tak numbers try karo. Agar number valid hai toh daal do aur agle empty cell ke liye recursion call karo. Agar baad mein koi solution nahi milta toh backtrack karke next number try karo.

---

### 3. Generate Parentheses
**Problem:** Generate all combinations of n well-formed parentheses.

```java
public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, "", 0, 0, n);
    return result;
}

private void backtrack(List<String> result, String curr, int open, int close, int n) {
    if (curr.length() == 2 * n) {
        result.add(curr);
        return;
    }
    
    if (open < n) {
        backtrack(result, curr + "(", open + 1, close, n);
    }
    if (close < open) {
        backtrack(result, curr + ")", open, close + 1, n);
    }
}
```

 Open brackets ka count < n hai toh '(' add karo. Close brackets ka count < open count hai toh ')' add karo. Jab length 2n ho jaye toh result mein add karo.

---

### 4. Combination Sum
**Problem:** Find all unique combinations where numbers sum to target (can reuse same number).

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, 
                       int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remaining - candidates[i], i); // Not i+1 (reuse allowed)
        temp.remove(temp.size() - 1);
    }
}
```

 Remaining target se current number subtract karte jao. Agar 0 ho jaye toh solution mil gaya. Agar negative ho jaye toh return. `i` pass karo because same element dobara use kar sakte ho.

---

### 5. Combination Sum II
**Problem:** Find combinations where sum = target, each number used once, no duplicates.

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        if (i > start && candidates[i] == candidates[i - 1]) continue; // Skip duplicates
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remaining - candidates[i], i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Pehle sort karo. Agar same element consecutive hai toh skip karo (duplicate combination se bachne ke liye). `i+1` pass karo because same element dobaara use nahi kar sakte.

---

### 6. Permutations
**Problem:** Generate all permutations of distinct integers.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        temp.remove(temp.size() - 1);
        used[i] = false;
    }
}
```

 `used` array maintain karo ki kaun se elements already use ho chuke hain. Har unused element ko try karo, use mark karo, recursion karo, phir unmark karo (backtrack).

---

### 7. Permutations II (With Duplicates)
**Problem:** Generate all unique permutations with duplicate numbers.

```java
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        temp.remove(temp.size() - 1);
        used[i] = false;
    }
}
```

 Sort karo. Agar current element previous ke equal hai aur previous used nahi hai toh skip karo (duplicate permutations avoid karne ke liye).

---

### 8. Subsets
**Problem:** Generate all possible subsets (power set).

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    
    for (int i = start; i < nums.length; i++) {
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Har recursion call mein current subset result mein add karo. Fir remaining elements mein se ek element choose karo, add karo, recursion karo, phir remove karo.

---

### 9. Subsets II (With Duplicates)
**Problem:** Generate unique subsets with duplicate numbers.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Sort karo. Consecutive duplicates ko skip karo same level pe.

---

### 10. Palindrome Partitioning
**Problem:** Partition string into palindromic substrings.

```java
public List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<List<String>> result, List<String> temp,
                       String s, int start) {
    if (start == s.length()) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int end = start + 1; end <= s.length(); end++) {
        String substring = s.substring(start, end);
        if (isPalindrome(substring)) {
            temp.add(substring);
            backtrack(result, temp, s, end);
            temp.remove(temp.size() - 1);
        }
    }
}

private boolean isPalindrome(String s) {
    int i = 0, j = s.length() - 1;
    while (i < j) {
        if (s.charAt(i++) != s.charAt(j--)) return false;
    }
    return true;
}
```

 String ko different lengths mein cut karo. Agar cut part palindrome hai toh temp mein add karo, baaki string ke liye recursion call karo.

---

### 11. Word Search
**Problem:** Check if word exists in 2D grid (adjacent cells).

```java
public boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

private boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (index == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
    if (board[i][j] != word.charAt(index)) return false;
    
    char temp = board[i][j];
    board[i][j] = '#'; // Mark as visited
    
    boolean found = dfs(board, word, i + 1, j, index + 1) ||
                    dfs(board, word, i - 1, j, index + 1) ||
                    dfs(board, word, i, j + 1, index + 1) ||
                    dfs(board, word, i, j - 1, index + 1);
    
    board[i][j] = temp; // Backtrack
    return found;
}
```

 Har cell se start karo. 4 directions mein DFS karo. Current cell ko mark karo visited (temporarily) taki dobaara na use ho. Agar word complete ho jaye toh true return karo.

---

### 12. Restore IP Addresses
**Problem:** Generate all valid IP addresses from string.

```java
public List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<String> result, List<String> temp, 
                       String s, int start) {
    if (temp.size() == 4) {
        if (start == s.length()) {
            result.add(String.join(".", temp));
        }
        return;
    }
    
    for (int i = 1; i <= 3 && start + i <= s.length(); i++) {
        String segment = s.substring(start, start + i);
        if (isValid(segment)) {
            temp.add(segment);
            backtrack(result, temp, s, start + i);
            temp.remove(temp.size() - 1);
        }
    }
}

private boolean isValid(String segment) {
    if (segment.length() > 1 && segment.charAt(0) == '0') return false;
    int num = Integer.parseInt(segment);
    return num >= 0 && num <= 255;
}
```

 String ko 4 parts mein baantna hai. Har part length 1-3 ki ho sakti hai aur value 0-255 ke beech mein honi chahiye. Leading zero allowed nahi hai (except "0" itself).

---

### 13. Letter Combinations of a Phone Number
**Problem:** Return all letter combinations for phone digits.

```java
private static final String[] MAPPING = {
    "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
};

public List<String> letterCombinations(String digits) {
    if (digits.isEmpty()) return new ArrayList<>();
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), digits, 0);
    return result;
}

private void backtrack(List<String> result, StringBuilder sb, 
                       String digits, int index) {
    if (index == digits.length()) {
        result.add(sb.toString());
        return;
    }
    
    String letters = MAPPING[digits.charAt(index) - '0'];
    for (char c : letters.toCharArray()) {
        sb.append(c);
        backtrack(result, sb, digits, index + 1);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

 Har digit ke corresponding letters pe loop karo. Ek letter choose karo, aage badho, backtrack karo.

---

### 14. Factor Combinations
**Problem:** Find all combinations of factors of n (excluding 1 and n).

```java
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), n, 2);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int n, int start) {
    if (n == 1) {
        if (temp.size() > 1) {
            result.add(new ArrayList<>(temp));
        }
        return;
    }
    
    for (int i = start; i <= Math.sqrt(n); i++) {
        if (n % i == 0) {
            temp.add(i);
            backtrack(result, temp, n / i, i);
            temp.remove(temp.size() - 1);
        }
    }
    
    // Add the last factor
    if (n >= start) {
        temp.add(n);
        backtrack(result, temp, 1, start);
        temp.remove(temp.size() - 1);
    }
}
```

 `n` ke factors dhundho (i se sqrt(n) tak). Factor milne pe temp mein add karo, `n/i` ke liye recursion karo. End mein last factor add karo.

---

### 15. Gray Code
**Problem:** Generate n-bit Gray code sequence.

```java
public List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    result.add(0);
    backtrack(result, n, 1);
    return result;
}

private void backtrack(List<Integer> result, int n, int size) {
    if (size == 1 << n) return;
    
    int prev = result.get(size - 1);
    for (int i = 0; i < n; i++) {
        int next = prev ^ (1 << i);
        if (!result.contains(next)) {
            result.add(next);
            backtrack(result, n, size + 1);
            return; // Found valid next number
        }
    }
}
```

 Last number se ek bit flip karke next number generate karo. Agar woh number already sequence mein nahi hai toh use add karo aur recursion continue karo.

---

### 16. Remove Invalid Parentheses
**Problem:** Remove minimum parentheses to make string valid.

```java
public List<String> removeInvalidParentheses(String s) {
    List<String> result = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();
    
    queue.offer(s);
    visited.add(s);
    boolean found = false;
    
    while (!queue.isEmpty()) {
        String curr = queue.poll();
        
        if (isValid(curr)) {
            result.add(curr);
            found = true;
        }
        
        if (found) continue;
        
        for (int i = 0; i < curr.length(); i++) {
            if (curr.charAt(i) != '(' && curr.charAt(i) != ')') continue;
            String next = curr.substring(0, i) + curr.substring(i + 1);
            if (!visited.contains(next)) {
                visited.add(next);
                queue.offer(next);
            }
        }
    }
    return result;
}

private boolean isValid(String s) {
    int count = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') count++;
        else if (c == ')') {
            count--;
            if (count < 0) return false;
        }
    }
    return count == 0;
}
```

 BFS approach - har level pe ek character remove karo. Pehle level jahan valid string milegi wahi minimum removals honge. Duplicate strings avoid karne ke liye visited set use karo.

---

### 17. Word Break II
**Problem:** Add spaces to break string into dictionary words.

```java
public List<String> wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet<>(wordDict);
    Map<String, List<String>> memo = new HashMap<>();
    return dfs(s, dict, memo);
}

private List<String> dfs(String s, Set<String> dict, Map<String, List<String>> memo) {
    if (memo.containsKey(s)) return memo.get(s);
    
    List<String> result = new ArrayList<>();
    if (dict.contains(s)) result.add(s);
    
    for (int i = 1; i < s.length(); i++) {
        String prefix = s.substring(0, i);
        if (dict.contains(prefix)) {
            List<String> suffixes = dfs(s.substring(i), dict, memo);
            for (String suffix : suffixes) {
                result.add(prefix + " " + suffix);
            }
        }
    }
    
    memo.put(s, result);
    return result;
}
```

 String ko prefix aur suffix mein baanto. Agar prefix dictionary mein hai toh suffix ke liye recursion call karo. Memoization use karo taki same substring baar baar na compute ho.

---

### 18. Expression Add Operators
**Problem:** Add operators (+, -, *) to make expression equal target.

```java
public List<String> addOperators(String num, int target) {
    List<String> result = new ArrayList<>();
    if (num == null || num.length() == 0) return result;
    backtrack(result, new StringBuilder(), num, target, 0, 0, 0);
    return result;
}

private void backtrack(List<String> result, StringBuilder expr, String num,
                       int target, int index, long eval, long prev) {
    if (index == num.length()) {
        if (eval == target) result.add(expr.toString());
        return;
    }
    
    for (int i = index; i < num.length(); i++) {
        if (i != index && num.charAt(index) == '0') break; // No leading zero
        
        long curr = Long.parseLong(num.substring(index, i + 1));
        int len = expr.length();
        
        if (index == 0) {
            expr.append(curr);
            backtrack(result, expr, num, target, i + 1, curr, curr);
            expr.setLength(len);
        } else {
            // Try '+'
            expr.append("+").append(curr);
            backtrack(result, expr, num, target, i + 1, eval + curr, curr);
            expr.setLength(len);
            
            // Try '-'
            expr.append("-").append(curr);
            backtrack(result, expr, num, target, i + 1, eval - curr, -curr);
            expr.setLength(len);
            
            // Try '*'
            expr.append("*").append(curr);
            backtrack(result, expr, num, target, i + 1, 
                     eval - prev + prev * curr, prev * curr);
            expr.setLength(len);
        }
    }
}
```

 Multiplication ke liye special handling chahiye because operator precedence. `prev` variable store karo last term ko. Multiplication ke time pe `eval - prev + prev * curr` karo.

---

### 19. Partition to K Equal Sum Subsets
**Problem:** Partition array into k subsets with equal sum.

```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) return false;
    int target = sum / k;
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    return backtrack(nums, used, k, 0, target, 0, nums.length - 1);
}

private boolean backtrack(int[] nums, boolean[] used, int k, int currSum,
                          int target, int start, int index) {
    if (k == 1) return true;
    if (currSum == target) {
        return backtrack(nums, used, k - 1, 0, target, 0, nums.length - 1);
    }
    
    for (int i = start; i >= 0; i--) {
        if (!used[i] && currSum + nums[i] <= target) {
            used[i] = true;
            if (backtrack(nums, used, k, currSum + nums[i], target, i - 1, index)) {
                return true;
            }
            used[i] = false;
        }
    }
    return false;
}
```

 Pehle total sum calculate karo, target = sum/k. Ek subset mein target sum achieve karne ke baad next subset ke liye recursion call karo. Backtracking se saare subsets fill karo.

---

### 20. Beautiful Arrangement
**Problem:** Count permutations where for each i, either i % nums[i] == 0 or nums[i] % i == 0.

```java
public int countArrangement(int n) {
    boolean[] used = new boolean[n + 1];
    return backtrack(used, 1, n);
}

private int backtrack(boolean[] used, int pos, int n) {
    if (pos > n) return 1;
    
    int count = 0;
    for (int i = 1; i <= n; i++) {
        if (!used[i] && (i % pos == 0 || pos % i == 0)) {
            used[i] = true;
            count += backtrack(used, pos + 1, n);
            used[i] = false;
        }
    }
    return count;
}
```

 Position `pos` pe, unused numbers mein se woh number select karo jo condition satisfy kare (`i % pos == 0` ya `pos % i == 0`). Phir next position pe jao.

---

## 📌 Key Recursion/Backtracking Patterns ()

| Pattern | Key Insight | Example Problems |
|---------|-------------|------------------|
| **Choose/Not Choose** | Har element ko include/exclude karo | Subsets, Combination Sum |
| **Permutation** | `used` array, har unused element try karo | Permutations, N-Queens |
| **Decision Tree** | Har decision pe branch karo | Generate Parentheses, Letter Combinations |
| **Grid DFS** | 4 directions, mark visited | Word Search, Word Search II |
| **Partitioning** | String/Array ko different cuts pe break karo | Palindrome Partitioning, Restore IP |
| **Pruning** | Early termination for invalid branches | Sudoku Solver, N-Queens |

## ⚡ Quick Tips

1. **Base Case** - Recursion rokne ki condition (usually end of array/string)
2. **Backtrack** - State restore karo after recursion call (remove last element, unmark visited)
3. **Pruning** - Invalid paths ko early terminate karo (performance boost)
4. **Memoization** - Overlapping subproblems ke liye cache use karo
5. **Sorting** - Duplicates handle karne ke liye sort karo aur consecutive duplicates skip karo
