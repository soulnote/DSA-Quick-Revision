
# 💡 Backtracking 
```java
void backtrack(State, Path, Result) {
    // 1. Base Case: Goal reached? Add Path to Result
    if (goalReached) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    // 2. Choices explore karo
    for (Choice : availableChoices) {
        if (!isValid(Choice)) continue;  // Pruning (Faaltu branches kaat do)
        
        // 3. DO: Choice ko Path mein add karo
        path.add(Choice);
        markVisited(Choice);
        
        // 4. EXPLORE: Recursively aage badho
        backtrack(State + 1, Path, Result);
        
        // 5. UNDO: Backtrack karo (Wapas aao aur undo karo)
        path.remove(path.size() - 1);
        unmarkVisited(Choice);
    }
}
```

---

### 🎯 1. N-Queens (Classic Backtracking)
**Problem:** NxN board mein N queens aise rakho ki koi bhi ek dusre ko na kaate.
**Logic:** Row by row queens place karo. Har row mein sirf ek queen. Column aur Dono Diagonals check karo.

```java
class NQueens {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) Arrays.fill(row, '.');
        
        boolean[] colCheck = new boolean[n];
        boolean[] diag1 = new boolean[2 * n]; // r + c
        boolean[] diag2 = new boolean[2 * n]; // r - c + n
        
        backtrack(0, n, board, result, colCheck, diag1, diag2);
        return result;
    }
    
    private void backtrack(int row, int n, char[][] board, List<List<String>> result,
                          boolean[] col, boolean[] d1, boolean[] d2) {
        // Base Case: Saari queens place ho gayi
        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (char[] r : board) temp.add(new String(r));
            result.add(temp);
            return;
        }
        
        // Har column try karo current row mein
        for (int c = 0; c < n; c++) {
            // Pruning: Agar column ya diagonal occupied hai to skip
            if (col[c] || d1[row + c] || d2[row - c + n]) continue;
            
            // DO: Queen place karo
            board[row][c] = 'Q';
            col[c] = d1[row + c] = d2[row - c + n] = true;
            
            // EXPLORE: Next row pe jao
            backtrack(row + 1, n, board, result, col, d1, d2);
            
            // UNDO: Queen hatao (Backtrack)
            board[row][c] = '.';
            col[c] = d1[row + c] = d2[row - c + n] = false;
        }
    }
}
```

### 🎯 2. Sudoku Solver
**Problem:** 9x9 Sudoku board solve karo.
**Logic:** Empty cell dhundho. 1-9 tak numbers try karo. Agar valid hai to place karo aur aage badho. Agar aage solution nahi mila to UNDO karo.

```java
class SudokuSolver {
    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }
    
    private boolean backtrack(char[][] board, int row, int col) {
        // Base Case: Board poora bhar gaya
        if (row == 9) return true;
        
        // Agle cell ka calculation
        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;
        
        // Agar cell already filled hai to aage badho
        if (board[row][col] != '.') {
            return backtrack(board, nextRow, nextCol);
        }
        
        // 1-9 tak try karo
        for (char num = '1'; num <= '9'; num++) {
            if (isValid(board, row, col, num)) {
                // DO
                board[row][col] = num;
                
                // EXPLORE
                if (backtrack(board, nextRow, nextCol)) return true;
                
                // UNDO
                board[row][col] = '.';
            }
        }
        return false; // Koi number valid nahi hai
    }
    
    private boolean isValid(char[][] board, int r, int c, char num) {
        for (int i = 0; i < 9; i++) {
            // Row check, Column check, 3x3 Box check
            if (board[r][i] == num || board[i][c] == num || 
                board[3 * (r/3) + i/3][3 * (c/3) + i%3] == num) return false;
        }
        return true;
    }
}
```

### 🎯 3. Permutations (Array ki saari arrangements)
**Problem:** Distinct integers ka array diya hai, saare possible permutations return karo.
**Logic:** Visited array maintain karo taaki ek element dobara use na ho.

```java
class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // Base Case: Path complete ho gaya
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // Pruning: Already used elements skip karo
            if (used[i]) continue;
            
            // DO
            path.add(nums[i]);
            used[i] = true;
            
            // EXPLORE
            backtrack(nums, used, path, result);
            
            // UNDO
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
}
```

### 🎯 4. Permutations II (Duplicates ke saath)
**Problem:** Array mein duplicates ho sakte hain. Unique permutations chahiye.
**Logic:** Array sort karo. Agar current element pichle element ke barabar hai aur pichla element use nahi hua, to skip karo.

```java
class PermutationsUnique {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Important for duplicate handling
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // Duplicate handling: Agar same number pehle unused hai to ise skip karo
            if (used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) continue;
            
            path.add(nums[i]); used[i] = true;
            backtrack(nums, used, path, result);
            path.remove(path.size() - 1); used[i] = false;
        }
    }
}
```

### 🎯 5. Subsets / Power Set
**Problem:** Array ke saare possible subsets return karo.
**Logic:** Har element ke paas do choices: Ya to subset mein aaye, ya na aaye.

```java
class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        // Har state ek valid subset hai (Empty subset bhi)
        result.add(new ArrayList<>(path));
        
        for (int i = start; i < nums.length; i++) {
            // DO
            path.add(nums[i]);
            // EXPLORE: i+1 kyunki combinations chahiye, permutations nahi
            backtrack(nums, i + 1, path, result);
            // UNDO
            path.remove(path.size() - 1);
        }
    }
}
```

### 🎯 6. Subsets II (Duplicates ke saath)
**Problem:** Array mein duplicates hain. Unique subsets chahiye.
**Logic:** Sort karo. Agar current element pichle ke barabar hai to skip karo (sirf tab jab wo pehli baar aa raha ho uss level pe).

```java
class SubsetsWithDup {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path));
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates at same level
            if (i > start && nums[i] == nums[i-1]) continue;
            
            path.add(nums[i]);
            backtrack(nums, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### 🎯 7. Combination Sum
**Problem:** Array se elements unlimited baar le sakte ho. Sum target ke barabar hona chahiye.
**Logic:** Subsets jaisa hi hai, bas yahan `i+1` nahi, `i` pass karte hain taaki dobara use kar sakein.

```java
class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] arr, int target, int start, List<Integer> path, List<List<Integer>> result) {
        // Base Cases
        if (target < 0) return; // Pruning: Target negative ho gaya
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            path.add(arr[i]);
            // Yahan i pass kiya, i+1 nahi (Unlimited use allowed)
            backtrack(arr, target - arr[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### 🎯 8. Combination Sum II (Each element only once, Duplicates allowed)
**Problem:** Array sorted hai ya nahi. Har element ek hi baar use karna hai, aur unique combinations chahiye.

```java
class CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // Duplicate handling ke liye sort
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] arr, int target, int start, List<Integer> path, List<List<Integer>> result) {
        if (target < 0) return;
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            // Duplicate skip karo same level pe
            if (i > start && arr[i] == arr[i-1]) continue;
            
            path.add(arr[i]);
            // Yahan i+1 pass kiya (Each element once)
            backtrack(arr, target - arr[i], i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### 🎯 9. Palindrome Partitioning
**Problem:** String ko aise substrings mein todo ki saari substrings palindrome ho.
**Logic:** Har index se shuru karo. Agar `s[start...i]` palindrome hai to ise path mein add karo aur aage `i+1` se recursion karo.

```java
class PalindromePartition {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> path, List<List<String>> result) {
        // Base Case: String khatam ho gayi
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < s.length(); i++) {
            String sub = s.substring(start, i + 1);
            // Pruning: Sirf tab aage badho jab substring palindrome ho
            if (isPalindrome(sub)) {
                path.add(sub);
                backtrack(s, i + 1, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
        return true;
    }
}
```

### 🎯 10. Generate Parentheses
**Problem:** `n` pairs ke saare valid parentheses combinations generate karo.
**Logic:** Do counters rakho: `open` aur `close`. Jab tak `open < n`, `'('` add karo. Jab `close < open`, `')'` add karo.

```java
class GenerateParenthesis {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(n, 0, 0, new StringBuilder(), result);
        return result;
    }
    
    private void backtrack(int n, int open, int close, StringBuilder sb, List<String> result) {
        // Base Case: Valid string complete
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }
        
        // Choice 1: Add '(' agar limit se kam hai
        if (open < n) {
            sb.append('(');
            backtrack(n, open + 1, close, sb, result);
            sb.deleteCharAt(sb.length() - 1); // UNDO
        }
        
        // Choice 2: Add ')' sirf tab jab close < open
        if (close < open) {
            sb.append(')');
            backtrack(n, open, close + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1); // UNDO
        }
    }
}
```

### 🎯 11. Word Search (Grid pe DFS Backtracking)
**Problem:** Grid mein word exist karta hai kya? (Adjacent cells se)
**Logic:** Har cell se DFS shuru karo. Visited mark karne ke liye cell ko temporarily `#` ya kuch aur bana do.

```java
class WordSearch {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (backtrack(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }
    
    private boolean backtrack(char[][] board, String word, int idx, int r, int c) {
        // Base Case: Poora word match ho gaya
        if (idx == word.length()) return true;
        // Boundary aur character match check
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length || 
            board[r][c] != word.charAt(idx)) return false;
        
        // DO: Mark visited
        char temp = board[r][c];
        board[r][c] = '#'; 
        
        // EXPLORE: 4 Directions
        boolean found = backtrack(board, word, idx + 1, r + 1, c) ||
                        backtrack(board, word, idx + 1, r - 1, c) ||
                        backtrack(board, word, idx + 1, r, c + 1) ||
                        backtrack(board, word, idx + 1, r, c - 1);
        
        // UNDO: Unmark visited
        board[r][c] = temp;
        return found;
    }
}
```

### 🎯 12. Rat in a Maze
**Problem:** Grid mein source (0,0) se destination (N-1, N-1) tak jaane ke saare paths. `1` open path hai, `0` blocked hai.

```java
class RatMaze {
    public static ArrayList<String> findPath(int[][] m, int n) {
        ArrayList<String> result = new ArrayList<>();
        boolean[][] vis = new boolean[n][n];
        if (m[0][0] == 1) backtrack(m, 0, 0, n, "", vis, result);
        return result;
    }
    
    private static void backtrack(int[][] m, int r, int c, int n, String path, 
                                  boolean[][] vis, ArrayList<String> res) {
        // Base Case: Destination reached
        if (r == n - 1 && c == n - 1) {
            res.add(path);
            return;
        }
        
        // Directions: Down, Left, Right, Up (Lexicographical order)
        int[] dr = {1, 0, 0, -1};
        int[] dc = {0, -1, 1, 0};
        char[] dir = {'D', 'L', 'R', 'U'};
        
        vis[r][c] = true;
        
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            if (nr >= 0 && nc >= 0 && nr < n && nc < n && m[nr][nc] == 1 && !vis[nr][nc]) {
                backtrack(m, nr, nc, n, path + dir[i], vis, res);
            }
        }
        
        vis[r][c] = false; // UNDO
    }
}
```

### 🎯 13. Letter Combinations of Phone Number
**Problem:** Mobile keypad ke digits diye hain. Saare possible letter combinations return karo.
**Logic:** Har digit ke corresponding letters ka mapping rakho. Phir simple DFS.

```java
class LetterCombinations {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(digits, 0, new StringBuilder(), map, result);
        return result;
    }
    
    private void backtrack(String digits, int idx, StringBuilder sb, String[] map, List<String> res) {
        if (idx == digits.length()) {
            res.add(sb.toString());
            return;
        }
        
        String letters = map[digits.charAt(idx) - '0'];
        for (char c : letters.toCharArray()) {
            sb.append(c);
            backtrack(digits, idx + 1, sb, map, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```

---

### 📋 Backtracking Cheat Sheet (Kab Kaise Sochna Hai)

| Problem Type | Template Trick | Key Point |
| :--- | :--- | :--- |
| **Permutations** | `boolean[] used` | Order matters. Visited tracking. |
| **Combinations / Subsets** | `start` index | Order doesn't matter. Combinations ke liye start index badhao. |
| **Unlimited Use** | `i` pass karo | Combination Sum jaisa. |
| **Duplicate Handling** | Sort + `if(i>start && arr[i]==arr[i-1])` | Same level pe duplicate skip karo. |
| **Grid Problems** | 4 Directions + Mark/Unmark | `board[r][c] = '#'` jaisa kuch. |
| **String Partitioning** | Loop `start` to `end` | Palindrome Partition, Word Break. |

### 🔥 Advanced Backtracking Patterns (Short Mention)

| Pattern | Problem | Unique Trick |
| :--- | :--- | :--- |
| **Hamiltonian Path** | Knight's Tour | Saare cells exactly ek baar visit karo. |
| **M-Coloring** | Graph Coloring | Adjacent nodes same color nahi hone chahiye. |
| **Remove Invalid Parentheses** | Minimum removals | BFS + Backtracking combo. |
Here are the **20 most asked backtracking problems** in coding interviews, with Java solutions and Hinglish explanations.

---

## 🎯 What is Backtracking?

  Backtracking ka matlab hai - saare possibilities try karo, agar koi option kaam nahi karta toh piche hatkar (backtrack) next option try karo. Yeh recursion + pruning ka combination hai.

**Common pattern:**
```java
void backtrack(solution, choices) {
    if (goal reached) {
        add solution;
        return;
    }
    for (each choice) {
        make choice;
        backtrack();
        undo choice;  // Backtrack step
    }
}
```

---

## 📋 20 Most Asked Backtracking Problems

---

### 1. Subsets (All subsets of array)
**Problem:** `[1,2,3]` → `[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]`

**Approach:** Har element ko ya toh lo ya nahi lo.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++) {
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);  // Backtrack
    }
}
```

>   Har index pe do option hai - element lo ya mat lo. Start se loop chalao, element add karo, recursion call karo, phir remove karo (backtrack).

**Time:** O(2ⁿ * n)

---

### 2. Subsets II (With duplicates)
**Problem:** `[1,2,2]` → Unique subsets only.

**Approach:** Sort karo aur duplicate skip karo.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i-1]) continue;  // Skip duplicate
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

>   Pehle sort karo. Agar current element previous ke equal hai aur i > start hai toh skip karo (duplicate subset nahi chahiye).

---

### 3. Permutations
**Problem:** `[1,2,3]` → All possible orders.

**Approach:** Har baar unused elements mein se ek choose karo.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        used[i] = false;
        temp.remove(temp.size() - 1);
    }
}
```

>   Used array se track karo kaunsa element already le liya. Jab temp ka size equal ho jaye nums ke size ke, toh answer mil gaya.

**Time:** O(n! * n)

---

### 4. Permutations II (With duplicates)
**Problem:** `[1,1,2]` → Unique permutations.

**Approach:** Sort karo, aur same level pe duplicate skip karo.

```java
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;  // Skip duplicate
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        used[i] = false;
        temp.remove(temp.size() - 1);
    }
}
```

>   Sort karo. Jab same value ho aur previous wala used nahi hai, toh skip karo. Isse same level ke duplicates nahi aayenge.

---

### 5. Combination Sum
**Problem:** `[2,3,6,7]`, target=7 → `[[2,2,3],[7]]` (Unlimited use allowed)

**Approach:** Same element ko baar baar use kar sakte ho.

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] candidates, int remain, int start) {
    if (remain < 0) return;
    if (remain == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    for (int i = start; i < candidates.length; i++) {
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remain - candidates[i], i);  // i not i+1 (can reuse)
        temp.remove(temp.size() - 1);
    }
}
```

>   Remain target - current sum track karo. Agar remain 0 ho gaya, answer mil gaya. Start se loop chalao, next call mein i hi bhejo (reuse allowed).

---

### 6. Combination Sum II
**Problem:** `[10,1,2,7,6,1,5]`, target=8 → Unique combinations, each element once.

**Approach:** Sort karo, duplicate skip karo, next call mein i+1.

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int[] candidates, int remain, int start) {
    if (remain < 0) return;
    if (remain == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    for (int i = start; i < candidates.length; i++) {
        if (i > start && candidates[i] == candidates[i-1]) continue;
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remain - candidates[i], i + 1);  // i+1 (no reuse)
        temp.remove(temp.size() - 1);
    }
}
```

>   Sort karo. Duplicate skip karo. Next call mein i+1 bhejo kyunki ek element ek baar hi use ho sakta hai.

---

### 7. Palindrome Partitioning
**Problem:** `"aab"` → `[["a","a","b"],["aa","b"]]`

**Approach:** Har partition point check karo ki palindrome hai ya nahi.

```java
public List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<List<String>> result, List<String> temp, String s, int start) {
    if (start == s.length()) {
        result.add(new ArrayList<>(temp));
        return;
    }
    for (int end = start + 1; end <= s.length(); end++) {
        String substr = s.substring(start, end);
        if (isPalindrome(substr)) {
            temp.add(substr);
            backtrack(result, temp, s, end);
            temp.remove(temp.size() - 1);
        }
    }
}

private boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
    return true;
}
```

>   Start se lekar end tak substring check karo. Agar palindrome hai toh add karo aur end se aage backtrack karo.

---

### 8. N-Queens
**Problem:** N×N board pe N queens rakho taaki koi do ek dusre ko attack na karein.

**Approach:** Har row mein ek queen rakho, column aur diagonals check karo.

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (int i = 0; i < n; i++) Arrays.fill(board[i], '.');
    backtrack(result, board, 0, n);
    return result;
}

private void backtrack(List<List<String>> result, char[][] board, int row, int n) {
    if (row == n) {
        result.add(construct(board));
        return;
    }
    for (int col = 0; col < n; col++) {
        if (isSafe(board, row, col, n)) {
            board[row][col] = 'Q';
            backtrack(result, board, row + 1, n);
            board[row][col] = '.';
        }
    }
}

private boolean isSafe(char[][] board, int row, int col, int n) {
    // Check column
    for (int i = 0; i < row; i++) if (board[i][col] == 'Q') return false;
    // Check diagonal (top-left)
    for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--) if (board[i][j] == 'Q') return false;
    // Check diagonal (top-right)
    for (int i = row-1, j = col+1; i >= 0 && j < n; i--, j++) if (board[i][j] == 'Q') return false;
    return true;
}

private List<String> construct(char[][] board) {
    List<String> res = new ArrayList<>();
    for (char[] row : board) res.add(new String(row));
    return res;
}
```

>   Har row mein ek queen rakho. Column check karo, do diagonals check karo. Safe hai toh place karo aur next row pe jao.

---

### 9. Sudoku Solver
**Problem:** Empty cells ('.') ko fill karo.

**Approach:** Har empty cell pe 1-9 try karo, valid hai toh aage badho.

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
                        board[i][j] = '.';
                    }
                }
                return false;
            }
        }
    }
    return true;
}

private boolean isValid(char[][] board, int row, int col, char c) {
    for (int i = 0; i < 9; i++) {
        if (board[row][i] == c) return false;  // Row check
        if (board[i][col] == c) return false;  // Column check
        int boxRow = 3 * (row / 3) + i / 3;
        int boxCol = 3 * (col / 3) + i % 3;
        if (board[boxRow][boxCol] == c) return false;  // 3x3 box check
    }
    return true;
}
```

>   Pehla empty cell dhundho. 1 se 9 tak try karo. Valid hai toh place karo aur recursion call karo. Agar solve ho gaya toh true return, nahi toh backtrack.

---

### 10. Letter Combinations of a Phone Number
**Problem:** `"23"` → `["ad","ae","af","bd","be","bf","cd","ce","cf"]`

**Approach:** Har digit ke corresponding letters try karo.

```java
public List<String> letterCombinations(String digits) {
    List<String> result = new ArrayList<>();
    if (digits == null || digits.length() == 0) return result;
    String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    backtrack(result, new StringBuilder(), digits, 0, mapping);
    return result;
}

private void backtrack(List<String> result, StringBuilder sb, String digits, int index, String[] mapping) {
    if (index == digits.length()) {
        result.add(sb.toString());
        return;
    }
    String letters = mapping[digits.charAt(index) - '0'];
    for (char c : letters.toCharArray()) {
        sb.append(c);
        backtrack(result, sb, digits, index + 1, mapping);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

>   Har digit ke corresponding letters lo. Ek letter choose karo, append karo, next digit pe jao. End mein answer add karo.

---

### 11. Generate Parentheses
**Problem:** n=3 → `["((()))","(()())","(())()","()(())","()()()"]`

**Approach:** Open aur close count track karo. Open < n tabhi open daalo, close < open tabhi close daalo.

```java
public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), 0, 0, n);
    return result;
}

private void backtrack(List<String> result, StringBuilder sb, int open, int close, int n) {
    if (sb.length() == 2 * n) {
        result.add(sb.toString());
        return;
    }
    if (open < n) {
        sb.append('(');
        backtrack(result, sb, open + 1, close, n);
        sb.deleteCharAt(sb.length() - 1);
    }
    if (close < open) {
        sb.append(')');
        backtrack(result, sb, open, close + 1, n);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

>   Open count n se kam hai tabhi '(' daalo. Close count open se kam hai tabhi ')' daalo. Jab length 2n ho jaye, answer mil gaya.

---

### 12. Word Search
**Problem:** 2D grid mein given word exist karta hai ya nahi? (Adjacent cells - up/down/left/right)

**Approach:** Har cell se start karo, DFS karo, visited mark karo.

```java
public boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (backtrack(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

private boolean backtrack(char[][] board, String word, int i, int j, int index) {
    if (index == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
    if (board[i][j] != word.charAt(index)) return false;
    
    char temp = board[i][j];
    board[i][j] = '#';  // Mark visited
    
    boolean found = backtrack(board, word, i+1, j, index+1) ||
                    backtrack(board, word, i-1, j, index+1) ||
                    backtrack(board, word, i, j+1, index+1) ||
                    backtrack(board, word, i, j-1, index+1);
    
    board[i][j] = temp;  // Unmark (backtrack)
    return found;
}
```

>   Har cell se word ka first character match karo. Match ho gaya toh visited mark karo (temporarily change karo) aur 4 directions mein search karo. End tak pahunch gaye toh true.

---

### 13. Restore IP Addresses
**Problem:** `"25525511135"` → `["255.255.11.135","255.255.111.35"]`

**Approach:** 3 dots place karo, har part 0-255 ke beech mein hona chahiye.

```java
public List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<String> result, List<String> parts, String s, int start) {
    if (parts.size() == 4) {
        if (start == s.length()) {
            result.add(String.join(".", parts));
        }
        return;
    }
    
    for (int len = 1; len <= 3; len++) {
        if (start + len > s.length()) break;
        String part = s.substring(start, start + len);
        if ((part.length() > 1 && part.charAt(0) == '0') || Integer.parseInt(part) > 255) continue;
        parts.add(part);
        backtrack(result, parts, s, start + len);
        parts.remove(parts.size() - 1);
    }
}
```

>   4 parts banaane hain. Har part length 1-3 ki ho sakti hai. Leading zero allowed nahi hai (except "0" itself). Range 0-255 hona chahiye.

---

### 14. Combination Sum III
**Problem:** K numbers ka sum = n, numbers 1-9, each used once.

**Approach:** 1 se 9 tak loop, combination sum jaisa.

```java
public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), k, n, 1);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int k, int remain, int start) {
    if (temp.size() == k && remain == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    if (temp.size() > k || remain < 0) return;
    
    for (int i = start; i <= 9; i++) {
        temp.add(i);
        backtrack(result, temp, k, remain - i, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

>   Exactly k numbers chahiye. 1-9 loop. Remain target se subtract karte jao. Size k aur remain 0 ho gaya toh answer.

---

### 15. Factor Combinations
**Problem:** `12` → `[[2,6],[2,2,3],[3,4]]`

**Approach:** Factors dhundho, aur recursively break karo.

```java
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), n, 2);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, int n, int start) {
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
    
    // Add the remaining factor
    if (n >= start) {
        temp.add(n);
        backtrack(result, temp, 1, n);
        temp.remove(temp.size() - 1);
    }
}
```

>   i se sqrt(n) tak loop karo. Agar n divisible hai i se, toh i ko add karo aur n/i ke liye recursion karo. End mein khud n ko add karo.

---

### 16. Beautiful Arrangement
**Problem:** 1..n ke permutations jahan `i` divisible hai `perm[i]` se ya `perm[i]` divisible hai `i` se.

**Approach:** Har position pe unused numbers try karo jo condition satisfy karein.

```java
int count = 0;

public int countArrangement(int n) {
    boolean[] used = new boolean[n + 1];
    backtrack(n, used, 1);
    return count;
}

private void backtrack(int n, boolean[] used, int pos) {
    if (pos > n) {
        count++;
        return;
    }
    
    for (int num = 1; num <= n; num++) {
        if (!used[num] && (num % pos == 0 || pos % num == 0)) {
            used[num] = true;
            backtrack(n, used, pos + 1);
            used[num] = false;
        }
    }
}
```

>   Position pos pe 1..n mein se unused number try karo. Condition check karo (num % pos == 0 ya pos % num == 0). End tak pahunch gaye toh count++.

---

### 17. Gray Code
**Problem:** n=2 → `[0,1,3,2]` (Adjacent numbers differ by 1 bit)

**Approach:** Backtracking se build karo, ya direct formula.

```java
public List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    result.add(0);
    backtrack(result, n, new HashSet<>(), 0);
    return result;
}

private boolean backtrack(List<Integer> result, int n, Set<Integer> seen, int prev) {
    if (result.size() == (1 << n)) return true;
    
    for (int i = 0; i < n; i++) {
        int next = prev ^ (1 << i);  // Flip i-th bit
        if (!seen.contains(next)) {
            seen.add(next);
            result.add(next);
            if (backtrack(result, n, seen, next)) return true;
            result.remove(result.size() - 1);
            seen.remove(next);
        }
    }
    return false;
}
```

>   Har step mein ek bit flip karo. Jo number already use nahi hai woh add karo. End mein saare numbers (2ⁿ) aa gaye toh return true.

---

### 18. Partition to K Equal Sum Subsets
**Problem:** Array ko k subsets mein divide karo jinka sum equal ho.

**Approach:** Target sum = total/k. Har subset ko fill karo.

```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % k != 0) return false;
    
    int target = sum / k;
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    return backtrack(nums, used, k, 0, target, nums.length - 1);
}

private boolean backtrack(int[] nums, boolean[] used, int k, int currSum, int target, int start) {
    if (k == 0) return true;
    if (currSum == target) {
        return backtrack(nums, used, k - 1, 0, target, nums.length - 1);
    }
    
    for (int i = start; i >= 0; i--) {  // Start from end (optimization)
        if (!used[i] && currSum + nums[i] <= target) {
            used[i] = true;
            if (backtrack(nums, used, k, currSum + nums[i], target, i - 1)) return true;
            used[i] = false;
            // Optimization: If currSum is 0, this means first element of subset failed
            if (currSum == 0) return false;
            // Skip duplicates
            while (i > 0 && nums[i] == nums[i-1]) i--;
        }
    }
    return false;
}
```

>   Target sum = total/k. Ek subset fill karo. Jab currSum target ke equal ho jaye, next subset ke liye recursion call karo. End mein k=0 ho gaya toh true.

---

### 19. Split a String Into Fibonacci Sequence
**Problem:** `"123456579"` → `[123,456,579]`

**Approach:** First two numbers choose karo, then check fibonacci property.

```java
public List<Integer> splitIntoFibonacci(String num) {
    List<Integer> result = new ArrayList<>();
    backtrack(result, num, 0);
    return result;
}

private boolean backtrack(List<Integer> result, String num, int start) {
    if (start == num.length() && result.size() >= 3) return true;
    
    for (int i = start; i < num.length(); i++) {
        // Leading zero not allowed
        if (num.charAt(start) == '0' && i > start) break;
        
        long curr = Long.parseLong(num.substring(start, i + 1));
        if (curr > Integer.MAX_VALUE) break;
        
        int size = result.size();
        if (size >= 2) {
            long sum = (long) result.get(size - 1) + result.get(size - 2);
            if (curr > sum) break;
            if (curr < sum) continue;
        }
        
        result.add((int) curr);
        if (backtrack(result, num, i + 1)) return true;
        result.remove(result.size() - 1);
    }
    return false;
}
```

>   First number choose karo, second choose karo, then sum check karo. Agar curr sum ke equal hai toh add karo. Leading zero allowed nahi hai.

---

### 20. Matchsticks to Square
**Problem:** Matchsticks se square bana sakte ho ya nahi?

**Approach:** 4 sides, har side ka sum = total/4.

```java
public boolean makesquare(int[] matchsticks) {
    int sum = 0;
    for (int stick : matchsticks) sum += stick;
    if (sum % 4 != 0) return false;
    
    Arrays.sort(matchsticks);
    int[] sides = new int[4];
    return backtrack(matchsticks, sides, matchsticks.length - 1, sum / 4);
}

private boolean backtrack(int[] sticks, int[] sides, int index, int target) {
    if (index < 0) {
        return sides[0] == target && sides[1] == target && sides[2] == target;
    }
    
    for (int i = 0; i < 4; i++) {
        if (sides[i] + sticks[index] > target) continue;
        // Optimization: Agar same value pehle try ho chuka hai toh skip
        if (i > 0 && sides[i] == sides[i - 1]) continue;
        
        sides[i] += sticks[index];
        if (backtrack(sticks, sides, index - 1, target)) return true;
        sides[i] -= sticks[index];
    }
    return false;
}
```

>   Total sum/4 = side length. 4 sides hai. Har matchstick ko kisi side mein daalo. Agar side target se zyada ho jaye toh skip. End mein saari sides target ke equal honi chahiye.

---

## 📊 Complete Summary Table

| # | Problem | Key Pattern | Time Complexity |
|---|---------|-------------|-----------------|
| 1 | Subsets | Include/Exclude | O(2ⁿ) |
| 2 | Subsets II | Sort + Duplicate skip | O(2ⁿ) |
| 3 | Permutations | Used array | O(n!) |
| 4 | Permutations II | Sort + Skip duplicates | O(n!) |
| 5 | Combination Sum | Reuse allowed | O(2ᵗ) |
| 6 | Combination Sum II | No reuse + Skip duplicates | O(2ⁿ) |
| 7 | Palindrome Partitioning | Check palindrome | O(n·2ⁿ) |
| 8 | N-Queens | Row by row + Validate | O(n!) |
| 9 | Sudoku Solver | Try 1-9 + Validate | O(9ᵐ) |
| 10 | Letter Combinations | Mapping + Recursion | O(4ⁿ·n) |
| 11 | Generate Parentheses | Open/Close count | O(4ⁿ/√n) |
| 12 | Word Search | DFS + Visited | O(m·n·4ˡ) |
| 13 | Restore IP | 3 dots placement | O(3⁴) |
| 14 | Combination Sum III | Exactly k numbers | O(C(9,k)) |
| 15 | Factor Combinations | Factor + Recursion | O(√n · log n) |
| 16 | Beautiful Arrangement | Condition check | O(k!) |
| 17 | Gray Code | Bit flip | O(2ⁿ) |
| 18 | Partition K Subsets | Target sum | O(k·2ⁿ) |
| 19 | Fibonacci Split | First two numbers | O(n²) |
| 20 | Matchsticks Square | 4 sides | O(4ⁿ) |

---

## 🎯 Backtracking Interview Tips

1. **Pattern Recognition:**
   - Subsets/Combinations → Start index + loop
   - Permutations → Used array
   - Path finding (Grid) → DFS + Visited marking
   - Partition problems → Check condition + Recursion

2. **Optimization Techniques:**
   - **Sorting:** Duplicates handle karne ke liye
   - **Pruning:** Jaldi false return karo
   - **Memoization:** Overlapping subproblems ke liye
   - **Start from end:** Bade numbers pehle try karo (matchsticks wale problems mein)

3. **Template yaad rakho:**
```java
void backtrack(parameters) {
    if (goal reached) {
        add answer;
        return;
    }
    for (choices) {
        if (valid) {
            make choice;
            backtrack(updated params);
            undo choice;  // IMPORTANT!
        }
    }
}
```

4. **Common Mistakes:**
   - Backtrack step bhoolna (remove karna)
   - Base case galat set karna
   - Duplicates handle na karna
   - Visited mark/Unmark bhoolna (Word Search wale problems mein)

5. **When to use Backtracking:**
   - Saare possibilities explore karne hain
   - "All possible combinations/permutations"
   - "Find if exists" (kuch condition ke saath)
   - Constraint satisfaction problems (N-Queens, Sudoku)
