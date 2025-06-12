# DP on Intervals Tutorial

Hello dosto! Aaj hum **DP on Intervals** pattern ke baare mein detail mein baat karenge. Ye pattern tab use hota hai jab humein ek sequence ke interval [i..j] pe states define karni ho aur optimal result find karna ho, jaise minimum cost, maximum score, ya game strategies. Is tutorial mein hum saare example problems (Burst Balloons, Optimal BST, Minimum Score Triangulation, Removing Boxes, Stone Game variants) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with detailed comments at important points. Chalo shuru karte hain!

---

## DP on Intervals Kya Hai?
DP on Intervals ek dynamic programming technique hai jisme states ek sequence ke interval [i..j] pe define hote hain. Har interval ke liye hum optimal result compute karte hain, aksar by splitting the interval into smaller sub-intervals or processing elements within it.

**Examples:**
1. Burst Balloons
2. Optimal BST
3. Minimum Score Triangulation
4. Removing Boxes
5. Stone Game variants

**Common Theme:**
- Ek 2D DP array use hota hai jisme `dp[i][j]` interval [i..j] ka optimal result store karta hai.
- Transitions mein interval ko split karke left aur right parts ke results combine kiye jate hain.
- Base cases small intervals (length 1 or 0) ko handle karte hain.

---

## Intuition Kaise Build Kare?
DP on Intervals problems ko samajhne ke liye ye socho:
- Tumhe ek sequence ka ek interval [i..j] hai, aur tumhe iska optimal result chahiye (jaise max coins, min cost).
- Interval ko split karke smaller intervals ke results compute karo, ya interval ke elements ko process karo.
- Har split ya decision ke liye optimal choice chuno.

For example:
- **Burst Balloons** mein tumhe interval ke balloons burst karne ka max coins chahiye, considering adjacent balloons.
- **Stone Game** mein players intervals se stones pick karte hain, aur max score chahiye.

**Key Intuition:**
- State define karo jo interval [i..j] ka result bataye.
- Transition formula socho: interval ko kaise split ya process karoge?
- Base cases set karo jo empty ya single-element intervals handle kare.

---

## General Approach
DP on Intervals problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][j]` kya represent karta hai? For example, Burst Balloons mein `dp[i][j]` = max coins for bursting balloons in [i..j].
2. **Transition Formula Likho:**
   - Current state ka answer kaise banega? For example, try all split points k or process interval boundaries.
3. **Base Cases Set Karo:**
   - Empty interval ya single element ke liye kya return hoga? For example, `dp[i][i] = 0`.
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Removing Boxes ya Optimal BST.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare subproblems solve karne pade, jaise Burst Balloons ya Minimum Score Triangulation.
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for DP on Intervals:**
- **Bottom-Up** zyada common hai kyunki intervals ke states predictable hote hain (i, j ke combinations), aur iteration efficient hota hai.
- Top-Down use karo jab recursion se logic clear ho ya complex state dependencies hon.

---

## Problem 1: Burst Balloons
### Problem Statement:
Ek array `nums` diya hai jisme balloon values hain. Har balloon burst karne pe coins milte hain = nums[i-1] * nums[i] * nums[i+1]. Max coins find karo.

**Example:**
```
Input: nums = [3,1,5,8]
Output: 167
Explanation: Burst order: [3,1,5,8] -> [3,5,8] -> [3,8] -> [8] -> [] gives 3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 167
```

### Intuition:
- Har balloon ko last mein burst karne ka socho, kyunki coins adjacent balloons pe depend karte hain.
- State: `dp[i][j]` = max coins for bursting balloons in range [i..j].
- Transition: `dp[i][j] = max(nums[i-1]*nums[k]*nums[j+1] + dp[i][k-1] + dp[k+1][j])` for k from i to j.
- Base cases: `dp[i][i] = nums[i-1]*nums[i]*nums[i+1]`.

### Tree Diagram:
```
i=0, j=3 (3,1,5,8)
 /        |        \
k=0       k=1       k=2
3 last    1 last    5 last
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // Add 1s at boundaries
        int[] newNums = new int[n+2];
        newNums[0] = newNums[n+1] = 1;
        for (int i = 0; i < n; i++) newNums[i+1] = nums[i];
        
        memo = new Integer[n+2][n+2];
        return maxCoinsHelper(newNums, 1, n);
    }
    
    private int maxCoinsHelper(int[] nums, int i, int j) {
        // Base case: no balloons
        if (i > j) return 0;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        int maxCoins = 0;
        // Try each balloon as last to burst
        for (int k = i; k <= j; k++) {
            int coins = nums[i-1] * nums[k] * nums[j+1] + // Coins for bursting k
                        maxCoinsHelper(nums, i, k-1) + // Left part
                        maxCoinsHelper(nums, k+1, j); // Right part
            maxCoins = Math.max(maxCoins, coins);
        }
        
        return memo[i][j] = maxCoins;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // Add 1s at boundaries
        int[] newNums = new int[n+2];
        newNums[0] = newNums[n+1] = 1;
        for (int i = 0; i < n; i++) newNums[i+1] = nums[i];
        
        int[][] dp = new int[n+2][n+2];
        
        // Process intervals by length
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                // Try each balloon as last to burst
                for (int k = i; k <= j; k++) {
                    int coins = newNums[i-1] * newNums[k] * newNums[j+1] + // Burst k
                                dp[i][k-1] + dp[k+1][j]; // Left and right parts
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[1][n];
    }
}
```

---

## Problem 2: Optimal BST
### Problem Statement:
Keys aur unki frequencies di hain. Minimum expected search cost wala Binary Search Tree banaye.

**Example:**
```
Input: keys = [10, 12], freq = [34, 50]
Output: 118
Explanation: Optimal BST has cost = 34*1 + 50*2 = 118
```

### Intuition:
- Har key ko root banao aur left/right subtrees ke costs compute karo.
- State: `dp[i][j]` = min cost for BST with keys[i..j].
- Transition: `dp[i][j] = min(dp[i][k-1] + dp[k+1][j] + sum(freq[i..j]))` for k from i to j.
- Base cases: `dp[i][i] = freq[i]`, `dp[i][i-1] = 0`.

### Tree Diagram:
```
i=0, j=1 (keys: 10,12)
 /        \
k=0       k=1
10 root   12 root
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    private int[] prefixSum;
    
    public int optimalBST(int[] keys, int[] freq) {
        int n = keys.length;
        memo = new Integer[n][n];
        // Compute prefix sum for frequency range
        prefixSum = new int[n+1];
        for (int i = 0; i < n; i++) {
            prefixSum[i+1] = prefixSum[i] + freq[i];
        }
        
        return optimalBSTHelper(keys, freq, 0, n-1);
    }
    
    private int optimalBSTHelper(int[] keys, int[] freq, int i, int j) {
        // Base case: empty interval
        if (i > j) return 0;
        // Base case: single key
        if (i == j) return freq[i];
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        int minCost = Integer.MAX_VALUE;
        // Try each key as root
        for (int k = i; k <= j; k++) {
            int cost = optimalBSTHelper(keys, freq, i, k-1) + // Left subtree
                       optimalBSTHelper(keys, freq, k+1, j) + // Right subtree
                       (prefixSum[j+1] - prefixSum[i]); // Frequency sum for depth
            minCost = Math.min(minCost, cost);
        }
        
        return memo[i][j] = minCost;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int optimalBST(int[] keys, int[] freq) {
        int n = keys.length;
        int[][] dp = new int[n+1][n+1];
        // Prefix sum for frequency range
        int[] prefixSum = new int[n+1];
        for (int i = 0; i < n; i++) {
            prefixSum[i+1] = prefixSum[i] + freq[i];
        }
        
        // Process intervals by length
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // Try each key as root
                for (int k = i; k <= j; k++) {
                    int left = (k > i) ? dp[i][k-1] : 0;
                    int right = (k < j) ? dp[k+1][j] : 0;
                    int cost = left + right + (prefixSum[j+1] - prefixSum[i]);
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[0][n-1];
    }
}
```

---

## Problem 3: Minimum Score Triangulation
### Problem Statement:
Ek convex polygon ke vertices ke values `values` mein diye hain. Minimum score find karo triangulation ke liye, jaha score har triangle ke vertices ka product hai.

**Example:**
```
Input: values = [1,2,3]
Output: 6
Explanation: Triangle (1,2,3) has score 1*2*3 = 6
```

### Intuition:
- Har triangle banane ke liye interval [i..j] mein ek vertex k chuno aur remaining polygon ke liye recurse karo.
- State: `dp[i][j]` = min score for triangulating polygon from i to j.
- Transition: `dp[i][j] = min(dp[i][k] + dp[k][j] + values[i]*values[k]*values[j])` for k from i+1 to j-1.
- Base cases: `dp[i][i+1] = 0` (no triangle possible).

### Tree Diagram:
```
i=0, j=3 (vertices: 1,2,3,4)
 /        \
k=1       k=2
(0,1,3)   (0,2,3)
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        memo = new Integer[n][n];
        return minScoreHelper(values, 0, n-1);
    }
    
    private int minScoreHelper(int[] values, int i, int j) {
        // Base case: less than 3 vertices can't form triangle
        if (j - i < 2) return 0;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        int minScore = Integer.MAX_VALUE;
        // Try each vertex k to form triangle
        for (int k = i+1; k < j; k++) {
            int score = minScoreHelper(values, i, k) + // Left part
                        minScoreHelper(values, k, j) + // Right part
                        values[i] * values[k] * values[j]; // Triangle score
            minScore = Math.min(minScore, score);
        }
        
        return memo[i][j] = minScore;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        
        // Process intervals by length
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // Try each vertex k to form triangle
                for (int k = i+1; k < j; k++) {
                    int score = dp[i][k] + dp[k][j] + // Left and right parts
                                values[i] * values[k] * values[j]; // Triangle score
                    dp[i][j] = Math.min(dp[i][j], score);
                }
            }
        }
        
        return dp[0][n-1];
    }
}
```

---

## Problem 4: Removing Boxes
### Problem Statement:
Ek array `boxes` diya hai jisme colored boxes hain. Points milte hain = (number of consecutive same-colored boxes)^2. Max points find karo by removing boxes.

**Example:**
```
Input: boxes = [1,3,2,2,2,3,4,3,1]
Output: 23
Explanation: Remove optimally to get score = 23
```

### Intuition:
- Consecutive same-colored boxes ko ek group mein remove karo for max points.
- State: `dp[i][j][k]` = max points for interval [i..j] with k extra boxes of color boxes[j] attached.
- Transition: Try splitting at same-colored boxes or removing groups.
- Base cases: `dp[i][i][k] = (k+1)^2`.

### Top-Down Code:
```java
public class Solution {
    private Integer[][][] memo;
    
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        memo = new Integer[n][n][n];
        return removeBoxesHelper(boxes, 0, n-1, 0);
    }
    
    private int removeBoxesHelper(int[] boxes, int i, int j, int k) {
        // Base case: invalid interval
        if (i > j) return 0;
        // Check memoized state
        if (memo[i][j][k] != null) return memo[i][j][k];
        
        // Extend consecutive boxes of same color
        while (i < j && boxes[i] == boxes[i+1]) {
            i++;
            k++;
        }
        
        // Remove current group
        int maxPoints = (k+1) * (k+1) + removeBoxesHelper(boxes, i+1, j, 0);
        
        // Try splitting at same-colored boxes
        for (int m = i+1; m <= j; m++) {
            if (boxes[i] == boxes[m]) {
                int points = removeBoxesHelper(boxes, i+1, m-1, 0) +
                             removeBoxesHelper(boxes, m, j, k+1);
                maxPoints = Math.max(maxPoints, points);
            }
        }
        
        return memo[i][j][k] = maxPoints;
    }
}
```

### Bottom-Up Code:
Complex due to the extra state k; top-down is preferred for clarity.

---

## Problem 5: Stone Game
### Problem Statement:
Do players ek array `piles` se stones pick karte hain, alternating turns mein, from either end. Max stones difference find karo for first player (Stone Game I).

**Example:**
```
Input: piles = [5,3,4,5]
Output: 10
Explanation: First player can get all stones (5+5=10)
```

### Intuition:
- Har turn mein player interval ke ends se pick karta hai.
- State: `dp[i][j]` = max difference first player can achieve for piles[i..j].
- Transition: `dp[i][j] = max(piles[i] - dp[i+1][j], piles[j] - dp[i][j-1])`.
- Base cases: `dp[i][i] = piles[i]`.

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        memo = new Integer[n][n];
        int diff = stoneGameHelper(piles, 0, n-1);
        return diff > 0; // First player wins if positive difference
    }
    
    private int stoneGameHelper(int[] piles, int i, int j) {
        // Base case: single pile
        if (i == j) return piles[i];
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        // First player's turn: maximize difference
        int takeLeft = piles[i] - stoneGameHelper(piles, i+1, j);
        int takeRight = piles[j] - stoneGameHelper(piles, i, j-1);
        
        return memo[i][j] = Math.max(takeLeft, takeRight);
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        
        // Base case: single pile
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        
        // Process intervals by length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // Maximize difference: take left or right
                int takeLeft = piles[i] - dp[i+1][j];
                int takeRight = piles[j] - dp[i][j-1];
                dp[i][j] = Math.max(takeLeft, takeRight);
            }
        }
        
        return dp[0][n-1] > 0;
    }
}
```

---

## Conclusion
DP on Intervals pattern bohot versatile hai aur interval-based optimization ya game problems ke liye perfect. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai for complex problems like Removing Boxes.
