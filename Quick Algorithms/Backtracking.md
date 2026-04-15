
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
