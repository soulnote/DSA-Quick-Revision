
# 2D DP – Grid Based Tutorial 

---

## 🔍 **Kaise pehchane ki problem 2D DP hai?**

### ✅ 1. **State Me 2 Index/Parameter Ho**

Agar DP state (ya recursion) me do variables/indices involved hain — jaise `dp[i][j]`, to high chance hai ki ye 2D DP hai.

**Example**:
`dp[i][j] = LCS of A[0...i] and B[0...j]`
\=> 2 strings ke indexes chal rahe hain → 2D DP

---

### ✅ 2. **Grid / Matrix Involved Ho**

Agar question me **2D grid** ya matrix diya ho aur aapko waha se kisi tarah ka path count, min path sum, max value path, etc. nikalna ho.

**Example Problems**:

* Unique Paths
* Minimum Path Sum
* Grid Traveller

---

### ✅ 3. **Two Strings / Arrays Ka Comparison Ho**

Agar aapko do strings ke beech comparison, alignment ya subsequence nikalna ho.

**Examples**:

* LCS (Longest Common Subsequence)
* Edit Distance
* Shortest Common Supersequence

---

### ✅ 4. **Substring ya Subarray Ranges Me Operations Ho**

Agar question me kisi array/string ka range `[i...j]` me kuch calculate karna ho repeatedly — like partition, cuts, palindromes.

**Example**:

* Palindrome Partitioning
* Matrix Chain Multiplication
* Boolean Parenthesization

---

## 🧠 **Ek Simple Trick:**

**Agar recursion ya memoization me 2 parameters lag rahe ho (mostly `i` and `j`) — samjho 2D DP hai.**

---

## 🧪 Example Se Samjho:

### 🔸 Problem: Longest Palindromic Subsequence

```java
int dp[i][j]; // i = start index, j = end index
```

* Agar `s.charAt(i) == s.charAt(j)`, to dono ko include karte hain
* Warna i+1...j aur i...j-1 par call karte hain
  \=> Yaha clearly 2 index hai → **2D DP**

---

### 🔸 Problem: Unique Paths in Grid

```java
int dp[i][j]; // i=row, j=col
```

* Har cell me aap upar se ya left se aa sakte ho.
  \=> Again 2D Grid based → **2D DP**

---

## 📌 Summary Table:

| Feature / Clue                | DP Type |
| ----------------------------- | ------- |
| Single index (i) or n         | 1D DP   |
| Grid / matrix given           | 2D DP   |
| Two variables changing (i, j) | 2D DP   |
| Substrings, subsequences      | 2D DP   |
| State depends on `dp[i][j]`   | 2D DP   |

---

## 2D DP – Grid Based Kya Hai?
Ye pattern tab use hota hai jab problem ek 2D grid ya matrix pe based ho, aur humein ek cell se doosre cell tak move karna ho, usually restricted directions mein (jaise right aur down, ya four directions). Har cell pe humein kuch calculate karna hota hai, jaise minimum cost, maximum score, ya number of ways.

**Examples:**
1. Unique Paths
2. Minimum Path Sum
3. Longest Path in Matrix
4. Cherry Pickup I/II
5. Dungeon Game

**Common Theme:**
- Ek 2D DP array use hota hai jisme har cell (i,j) ek state represent karti hai.
- Har state ka answer pichle states (adjacent cells) se depend karta hai.
- Movement directional hota hai, aur humein optimal result chahiye.

---

## Intuition Kaise Build Kare?
2D DP – Grid Based problems ko samajhne ke liye ye socho:
- Tum ek grid pe ho, aur ek starting point se destination tak jana hai.
- Har cell pe ek decision lena hai (jaise kaha move karna hai), aur ye decision pichle cells ke results pe depend karta hai.
- Tumhe maximum ya minimum value, ya number of ways chahiye.

For example:
- **Unique Paths** mein tumhe top-left se bottom-right tak jana hai, sirf right ya down move kar sakte ho, aur total ways count karne hain.
- **Dungeon Game** mein tumhe minimum health chahiye jo bottom-right tak survive karne ke liye zaruri hai.

**Key Intuition:**
- State define karo jo current cell (i,j) tak ka optimal result bataye.
- Transition formula socho jo current state ko adjacent cells se jode.
- Base cases set karo jo grid ke boundaries ya starting points ko handle kare.

---

## General Approach
2D DP problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][j]` kya represent karta hai? For example, Unique Paths mein `dp[i][j]` = (i,j) tak pahunchne ke ways.
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, Unique Paths mein `dp[i][j] = dp[i-1][j] + dp[i][j-1]`.
3. **Base Cases Set Karo:**
   - Grid ke edges ya starting points ke liye values kya hongi? For example, Unique Paths mein `dp[0][j] = 1`, `dp[i][0] = 1` (single path).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Cherry Pickup jaisa complex problem.
  - Jab subproblems sparse hain ya saare cells visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade grids pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare cells ke subproblems solve karne pade.
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for 2D DP:**
- **Bottom-Up** zyada common hai kyunki grid-based problems mein saare cells sequential order mein process ho sakte hain.
- Top-Down use karo jab problem complex ho (jaise Cherry Pickup II) ya recursion se logic clear ho.

---

## Problem 1: Unique Paths
### Problem Statement:
Ek m×n grid hai. Top-left (0,0) se bottom-right (m-1,n-1) tak jao, sirf right ya down move kar sakte ho. Kitne unique paths hain?

**Example:**
```
Input: m = 3, n = 2
Output: 3
Explanation: Paths: [right, right, down], [right, down, right], [down, right, right]
```

### Intuition:
- Har cell pe ya to upar se (i-1,j) ya left se (i,j-1) aaye ho.
- State: `dp[i][j]` = (i,j) tak pahunchne ke ways.
- Transition: `dp[i][j] = dp[i-1][j] + dp[i][j-1]`.
- Base cases: `dp[0][j] = 1`, `dp[i][0] = 1` (single path).

### Tree Diagram:
```
(2,1)
 /     \
(1,1) (2,0)
 /  \
(0,1) (1,0)
```
Har cell pe do choices: upar se ya left se.

### Bottom-Up Code:
```java
public class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        // Base cases: first row and column
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        
        // Fill DP array
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1]; // From above or left
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[][] memo;
    
    public int uniquePaths(int m, int n) {
        memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -1);
        return uniquePathsHelper(m-1, n-1);
    }
    
    private int uniquePathsHelper(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 || j == 0) return 1;
        if (memo[i][j] != -1) return memo[i][j];
        
        memo[i][j] = uniquePathsHelper(i-1, j) + uniquePathsHelper(i, j-1);
        return memo[i][j];
    }
}
```

### Space Optimization:
Since we only need the previous row, we can use a 1D array:
```java
public int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1); // First row
    
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[j] += dp[j-1]; // Current = above + left
        }
    }
    
    return dp[n-1];
}
```

---

## Problem 2: Minimum Path Sum
### Problem Statement:
Ek m×n grid hai jisme har cell pe positive number hai. Top-left se bottom-right tak minimum path sum find karo, sirf right ya down move kar sakte ho.

**Example:**
```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Path: 1->3->1->1->1 = 7
```

### Intuition:
- Har cell pe minimum sum wala path chuno.
- State: `dp[i][j]` = (i,j) tak minimum path sum.
- Transition: `dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])`.
- Base cases: First row and column fill karo grid values se.

### Bottom-Up Code:
```java
public class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        
        // Base case: top-left
        dp[0][0] = grid[0][0];
        
        // First row
        for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
        // First column
        for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
        
        // Fill DP array
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[][] memo;
    
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -1);
        return minPathSumHelper(grid, m-1, n-1);
    }
    
    private int minPathSumHelper(int[][] grid, int i, int j) {
        if (i < 0 || j < 0) return Integer.MAX_VALUE;
        if (i == 0 && j == 0) return grid[0][0];
        if (memo[i][j] != -1) return memo[i][j];
        
        memo[i][j] = grid[i][j] + Math.min(
            minPathSumHelper(grid, i-1, j),
            minPathSumHelper(grid, i, j-1)
        );
        return memo[i][j];
    }
}
```

### Space Optimization:
```java
public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[] dp = new int[n];
    
    dp[0] = grid[0][0];
    for (int j = 1; j < n; j++) dp[j] = dp[j-1] + grid[0][j];
    
    for (int i = 1; i < m; i++) {
        dp[0] = dp[0] + grid[i][0];
        for (int j = 1; j < n; j++) {
            dp[j] = grid[i][j] + Math.min(dp[j], dp[j-1]);
        }
    }
    
    return dp[n-1];
}
```

---

## Problem 3: Longest Path in Matrix
### Problem Statement:
Ek n×n matrix hai jisme har cell pe ek number hai. Ek path banaye jaha har next cell ka value bada ho current cell se, aur chaar directions mein move kar sakte ho (up, down, left, right). Longest path ki length find karo.

**Example:**
```
Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
Output: 4
Explanation: Path: 1->2->6->9
```

### Intuition:
- Har cell se chaar directions mein move karo, agar next cell ka value bada ho.
- State: `dp[i][j]` = (i,j) se longest path ki length.
- Transition: `dp[i][j] = 1 + max(dp[next_i][next_j])` for valid moves.
- Base case: Unvisited cells ka `dp[i][j] = -1`.

### Tree Diagram:
```
(1,1)=1
 / | \
U  D  L  R
```
Check all four directions if value increases.

### Top-Down Code (Memoization preferred due to sparse subproblems):
```java
public class Solution {
    private int[][] memo;
    private int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -1);
        
        int maxLen = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxLen = Math.max(maxLen, dfs(matrix, i, j, Integer.MIN_VALUE));
            }
        }
        return maxLen;
    }
    
    private int dfs(int[][] matrix, int i, int j, int prev) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || 
            matrix[i][j] <= prev) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        
        int maxLen = 1;
        for (int[] dir : directions) {
            int ni = i + dir[0], nj = j + dir[1];
            maxLen = Math.max(maxLen, 1 + dfs(matrix, ni, nj, matrix[i][j]));
        }
        
        memo[i][j] = maxLen;
        return maxLen;
    }
}
```

### Bottom-Up Code:
Bottom-Up is less common here due to dependency on DFS, but we can iterate with memoization.

---

## Problem 4: Cherry Pickup I
### Problem Statement:
Ek n×n grid hai jisme cherries hain (positive, negative, ya zero). Top-left se bottom-right tak jao (right/down) aur wapas top-left jao, max cherries collect karo. Ek cell ke cherries sirf ek baar collect kar sakte ho.

**Example:**
```
Input: grid = [[0,1,-1],[1,0,-1],[1,1,1]]
Output: 5
Explanation: Path: down->down->right->right->up->up->left->left
```

### Intuition:
- Do baar path banana hai, lekin same cells ke cherries share hote hain.
- State: `dp[i1][j1][i2][j2]` = max cherries for two paths at (i1,j1) and (i2,j2).
- Simplify to `dp[t][i1][i2]` where t = i1+j1 = i2+j2 (same time step).
- Transition: Check all combinations of moves (right/down for both paths).

### Top-Down Code:
```java
public class Solution {
    private int[][][] memo;
    private int n;
    
    public int cherryPickup(int[][] grid) {
        n = grid.length;
        memo = new int[n][n][n];
        for (int[][] layer : memo) for (int[] row : layer) Arrays.fill(row, -1);
        int result = cherryPickupHelper(grid, 0, 0, 0);
        return result < 0 ? 0 : result;
    }
    
    private int cherryPickupHelper(int[][] grid, int i1, int j1, int i2) {
        int j2 = i1 + j1 - i2; // t = i1+j1 = i2+j2
        if (i1 >= n || j1 >= n || i2 >= n || j2 >= n || 
            grid[i1][j1] == -1 || grid[i2][j2] == -1) return Integer.MIN_VALUE;
        if (i1 == n-1 && j1 == n-1) return grid[i1][j1];
        if (memo[i1][j1][i2] != -1) return memo[i1][j1][i2];
        
        int cherries = (i1 == i2 && j1 == j2) ? grid[i1][j1] : grid[i1][j1] + grid[i2][j2];
        int next = Integer.MIN_VALUE;
        
        next = Math.max(next, cherryPickupHelper(grid, i1+1, j1, i2+1)); // down, down
        next = Math.max(next, cherryPickupHelper(grid, i1+1, j1, i2));   // down, right
        next = Math.max(next, cherryPickupHelper(grid, i1, j1+1, i2+1)); // right, down
        next = Math.max(next, cherryPickupHelper(grid, i1, j1+1, i2));   // right, right
        
        if (next == Integer.MIN_VALUE) return memo[i1][j1][i2] = Integer.MIN_VALUE;
        return memo[i1][j1][i2] = cherries + next;
    }
}
```

### Bottom-Up Code:
Complex for Cherry Pickup I, so top-down is preferred.

---

## Problem 5: Cherry Pickup II
### Problem Statement:
Ek r×c grid hai. Do robots hain: ek (0,0) se aur ek (0,c-1) se start karta hai. Dono bottom row tak jate hain (down, down-left, down-right), max cherries collect karo.

**Example:**
```
Input: grid = [[3,1,1],[2,5,1],[1,5,5],[2,1,1]]
Output: 24
Explanation: Path 1: (0,0)->(1,1)->(2,2)->(3,1), Path 2: (0,2)->(1,1)->(2,2)->(3,1)
```

### Intuition:
- Do robots ek sath move karte hain, same row mein.
- State: `dp[r][c1][c2]` = max cherries when robot1 at (r,c1) and robot2 at (r,c2).
- Transition: Check all combinations of moves (3 directions per robot).

### Top-Down Code:
```java
public class Solution {
    private int[][][] memo;
    private int rows, cols;
    
    public int cherryPickup(int[][] grid) {
        rows = grid.length; cols = grid[0].length;
        memo = new int[rows][cols][cols];
        for (int[][] layer : memo) for (int[] row : layer) Arrays.fill(row, -1);
        return cherryPickupHelper(grid, 0, 0, cols-1);
    }
    
    private int cherryPickupHelper(int[][] grid, int r, int c1, int c2) {
        if (r >= rows || c1 < 0 || c1 >= cols || c2 < 0 || c2 >= cols) return Integer.MIN_VALUE;
        if (memo[r][c1][c2] != -1) return memo[r][c1][c2];
        if (r == rows-1) return c1 == c2 ? grid[r][c1] : grid[r][c1] + grid[r][c2];
        
        int cherries = c1 == c2 ? grid[r][c1] : grid[r][c1] + grid[r][c2];
        int next = Integer.MIN_VALUE;
        
        for (int dc1 = -1; dc1 <= 1; dc1++) {
            for (int dc2 = -1; dc2 <= 1; dc2++) {
                next = Math.max(next, cherryPickupHelper(grid, r+1, c1+dc1, c2+dc2));
            }
        }
        
        if (next == Integer.MIN_VALUE) return memo[r][c1][c2] = Integer.MIN_VALUE;
        return memo[r][c1][c2] = cherries + next;
    }
}
```

---

## Problem 6: Dungeon Game
### Problem Statement:
Ek m×n grid hai jisme har cell pe health points hain (positive ya negative). Top-left se bottom-right tak jao (right/down), minimum initial health find karo jo bottom-right tak survive karne ke liye chahiye.

**Example:**
```
Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
Output: 7
Explanation: Initial health >= 7 to survive with at least 1 health at end.
```

### Intuition:
- Har cell pe minimum health chahiye jo destination tak survive kare.
- State: `dp[i][j]` = minimum health needed at (i,j) to reach (m-1,n-1).
- Transition: `dp[i][j] = max(1, min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j])`.
- Base case: Last cell ke liye health calculate karo.

### Bottom-Up Code:
```java
public class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m+1][n+1];
        
        // Base cases
        dp[m][n-1] = 1; dp[m-1][n] = 1;
        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                int need = Math.min(
                    i+1 < m ? dp[i+1][j] : Integer.MAX_VALUE,
                    j+1 < n ? dp[i][j+1] : Integer.MAX_VALUE
                );
                dp[i][j] = Math.max(1, need - dungeon[i][j]);
            }
        }
        
        return dp[0][0];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[][] memo;
    private int m, n;
    
    public int calculateMinimumHP(int[][] dungeon) {
        m = dungeon.length; n = dungeon[0].length;
        memo = new int[m][n];
        for (int[] row : memo) Arrays.fill(row, -1);
        return calculateMinimumHPHelper(dungeon, 0, 0);
    }
    
    private int calculateMinimumHPHelper(int[][] dungeon, int i, int j) {
        if (i >= m || j >= n) return Integer.MAX_VALUE;
        if (i == m-1 && j == n-1) return Math.max(1, 1 - dungeon[i][j]);
        if (memo[i][j] != -1) return memo[i][j];
        
        int need = Math.min(
            calculateMinimumHPHelper(dungeon, i+1, j),
            calculateMinimumHPHelper(dungeon, i, j+1)
        );
        memo[i][j] = Math.max(1, need - dungeon[i][j]);
        return memo[i][j];
    }
}
```

---

## Conclusion
2D DP – Grid Based pattern grid problems ke liye perfect hai jaha directional movement hota hai. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down complex problems (jaise Cherry Pickup) mein intuitive hota hai.
- Space optimization ke liye 1D array ya variables use karo jab possible ho.
