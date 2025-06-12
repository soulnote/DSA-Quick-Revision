# Matrix Chain Multiplication (MCM) Type Tutorial 

Hello dosto! Aaj hum **Matrix Chain Multiplication (MCM) Type** pattern ke baare mein detail mein baat karenge. Ye pattern tab use hota hai jab humein ek sequence (jaise matrices, strings, ya expressions) ko do parts mein split karke optimal cost ya result find karna ho, aur har possible split try karni ho. Is tutorial mein hum saare example problems (Matrix Chain Multiplication, Palindrome Partitioning II, Boolean Parenthesization, Burst Balloons, Scramble String) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with detailed comments at important points. Chalo shuru karte hain!

---

## MCM Type Kya Hai?
MCM Type pattern partitioning problems ke liye hai jisme hum do indices (i, j) ke bich har possible break try karte hain, aur left aur right parts ke results combine karke optimal solution find karte hain. Ye problems aksar recursive hote hain aur overlapping subproblems ke wajah se DP se solve hote hain.

**Examples:**
1. Matrix Chain Multiplication
2. Palindrome Partitioning II
3. Boolean Parenthesization
4. Burst Balloons
5. Scramble String

**Common Theme:**
- Ek 2D DP array use hota hai jisme states (i, j) subsequence [i..j] ka optimal result store karte hain.
- Har state mein har possible split point (k) try karo aur min/max result chuno.
- Base cases single element ya empty sequence ko handle karte hain.

---

## Intuition Kaise Build Kare?
MCM Type problems ko samajhne ke liye ye socho:
- Tumhe ek sequence hai (matrices, string, balloons), aur tumhe isse optimal tareeke se do parts mein split karna hai.
- Har split point k ke liye, left part (i..k) aur right part (k+1..j) ke results compute karo, aur unhe combine karo.
- Tumhe minimum cost, maximum score, ya number of ways chahiye.

For example:
- **Matrix Chain Multiplication** mein minimum multiplication cost chahiye matrices ke chain ko multiply karne ka.
- **Burst Balloons** mein maximum coins chahiye balloons ko optimal order mein burst karke.

**Key Intuition:**
- State define karo jo subsequence [i..j] ka optimal result bataye.
- Transition formula socho: har split point k ke liye left aur right results combine karo.
- Base cases set karo jo single element ya empty sequence handle kare.

---

## General Approach
MCM Type problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][j]` kya represent karta hai? For example, MCM mein `dp[i][j]` = minimum cost to multiply matrices from i to j.
2. **Transition Formula Likho:**
   - Current state ka answer har possible split k se kaise banega? For example, MCM mein `dp[i][j] = min(dp[i][k] + dp[k+1][j] + cost(i,k,j))`.
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[i][i] = 0` (single matrix).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Boolean Parenthesization ya Scramble String.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare subproblems solve karne pade, jaise MCM ya Palindrome Partitioning.
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for MCM Type:**
- **Top-Down** zyada common hai kyunki MCM problems recursive nature ke hote hain, aur memoization se overlapping subproblems handle ho jate hain.
- Bottom-Up use karo jab problem ka structure predictable ho aur iteration efficient lage.

---

## Problem 1: Matrix Chain Multiplication
### Problem Statement:
Ek array `dims` diya hai jisme dimensions of matrices hain (ith matrix ka size dims[i-1] x dims[i]). Minimum multiplications find karo jo saari matrices multiply karne ke liye chahiye.

**Example:**
```
Input: dims = [10, 20, 30, 40, 30]
Output: 30000
Explanation: Optimal grouping: (A1(A2A3))A4 = 10*20*30 + 10*30*40 + 10*40*30 = 30000
```

### Intuition:
- Har matrix chain ko split karke multiply karo aur minimum cost chuno.
- State: `dp[i][j]` = min cost to multiply matrices from i to j.
- Transition: `dp[i][j] = min(dp[i][k] + dp[k+1][j] + dims[i-1]*dims[k]*dims[j])` for k from i to j-1.
- Base cases: `dp[i][i] = 0`.

### Tree Diagram:
```
i=1, j=3 (matrices A1 to A3)
 /        |        \
k=1       k=2       k=3
(A1)(A2A3) (A1A2)(A3)
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int matrixChainMultiplication(int[] dims) {
        int n = dims.length - 1; // Number of matrices
        memo = new Integer[n+1][n+1];
        return mcmHelper(dims, 1, n);
    }
    
    private int mcmHelper(int[] dims, int i, int j) {
        // Base case: single matrix, no multiplication needed
        if (i == j) return 0;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        int minCost = Integer.MAX_VALUE;
        // Try all possible splits at k
        for (int k = i; k < j; k++) {
            int cost = mcmHelper(dims, i, k) + // Cost of left part
                       mcmHelper(dims, k+1, j) + // Cost of right part
                       dims[i-1] * dims[k] * dims[j]; // Cost of multiplying results
            minCost = Math.min(minCost, cost);
        }
        
        return memo[i][j] = minCost;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int matrixChainMultiplication(int[] dims) {
        int n = dims.length - 1; // Number of matrices
        int[][] dp = new int[n+1][n+1];
        
        // Initialize dp array for single matrices
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 0; // No cost for single matrix
        }
        
        // l is the chain length
        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;
                // Try all splits k within [i, j)
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + // Cost of left part
                               dp[k+1][j] + // Cost of right part
                               dims[i-1] * dims[k] * dims[j]; // Multiplication cost
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[1][n];
    }
}
```

---

## Problem 2: Palindrome Partitioning II
### Problem Statement:
Ek string `s` di hai. Minimum cuts find karo jo string ko palindromic substrings mein partition kare.

**Example:**
```
Input: s = "aab"
Output: 1
Explanation: Partition as ["aa", "b"] with 1 cut.
```

### Intuition:
- Har substring ke liye check: palindrome hai ya nahi, aur minimum cuts chuno.
- State: `dp[i][j]` = min cuts for s[i..j], or simpler `dp[i]` = min cuts for s[0..i-1].
- Transition: `dp[i] = min(dp[j] + 1)` if s[j..i-1] is palindrome.
- Base cases: `dp[0] = -1`.

### Top-Down Code:
```java
public class Solution {
    private Integer[] memo;
    private boolean[][] isPalindrome;
    
    public int minCut(String s) {
        int n = s.length();
        memo = new Integer[n+1];
        isPalindrome = new boolean[n][n];
        
        // Precompute palindrome table
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true; // Single char is palindrome
        }
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i <= n - l; i++) {
                int j = i + l - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    isPalindrome[i][j] = (l == 2) ? true : isPalindrome[i+1][j-1];
                }
            }
        }
        
        return minCutHelper(s, n);
    }
    
    private int minCutHelper(String s, int i) {
        // Base case: empty string needs no cuts
        if (i == 0) return -1;
        // Check memoized state
        if (memo[i] != null) return memo[i];
        
        int minCuts = Integer.MAX_VALUE;
        // Try all splits
        for (int j = 0; j < i; j++) {
            if (isPalindrome[j][i-1]) {
                minCuts = Math.min(minCuts, minCutHelper(s, j) + 1);
            }
        }
        
        return memo[i] = minCuts;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int minCut(String s) {
        int n = s.length();
        int[] dp = new int[n+1];
        boolean[][] isPalindrome = new boolean[n][n];
        
        // Precompute palindrome table
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true; // Single char is palindrome
        }
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i <= n - l; i++) {
                int j = i + l - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    isPalindrome[i][j] = (l == 2) ? true : isPalindrome[i+1][j-1];
                }
            }
        }
        
        // Fill dp array
        dp[0] = -1; // Empty string
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            // Try all splits
            for (int j = 0; j < i; j++) {
                if (isPalindrome[j][i-1]) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n];
    }
}
```

---

## Problem 3: Boolean Parenthesization
### Problem Statement:
Ek string `expression` di hai jisme operands (T, F) aur operators (&, |, ^) hain. Kitne ways mein expression true ban sakta hai?

**Example:**
```
Input: expression = "T|T&F"
Output: 2
Explanation: (T | (T & F)) = T, and (T | T) & F = T
```

### Intuition:
- Expression ko split karke evaluate karo, considering each operator as a split point.
- State: `dp[i][j][isTrue]` = ways to make expression[i..j] true (or false).
- Transition: For each operator k, combine left and right results based on operator.
- Base cases: `dp[i][i][T] = 1` if expression[i] = T, else 0.

### Tree Diagram:
```
i=0, j=3 (T|T&F)
 /        \
k=1       k=3
(T)|(T&F) (T|T)&(F)
```

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public int countWays(String expression) {
        int n = expression.length();
        memo = new Long[n][n][2];
        return (int) countWaysHelper(expression, 0, n-1, 1);
    }
    
    private long countWaysHelper(String exp, int i, int j, int isTrue) {
        // Base case: single character
        if (i == j) {
            if (isTrue == 1) return exp.charAt(i) == 'T' ? 1 : 0;
            else return exp.charAt(i) == 'F' ? 1 : 0;
        }
        // Base case: invalid range
        if (i > j) return 0;
        // Check memoized state
        if (memo[i][j][isTrue] != null) return memo[i][j][isTrue];
        
        long ways = 0;
        // Try each operator as split point
        for (int k = i + 1; k <= j - 1; k += 2) {
            char op = exp.charAt(k);
            // Compute left and right sub-expression results
            long leftTrue = countWaysHelper(exp, i, k-1, 1);
            long leftFalse = countWaysHelper(exp, i, k-1, 0);
            long rightTrue = countWaysHelper(exp, k+1, j, 1);
            long rightFalse = countWaysHelper(exp, k+1, j, 0);
            
            // Combine based on operator
            if (op == '&') {
                if (isTrue == 1) ways += leftTrue * rightTrue;
                else ways += leftTrue * rightFalse + leftFalse * rightTrue + leftFalse * rightFalse;
            } else if (op == '|') {
                if (isTrue == 1) ways += leftTrue * rightTrue + leftTrue * rightFalse + leftFalse * rightTrue;
                else ways += leftFalse * rightFalse;
            } else if (op == '^') {
                if (isTrue == 1) ways += leftTrue * rightFalse + leftFalse * rightTrue;
                else ways += leftTrue * rightTrue + leftFalse * rightFalse;
            }
        }
        
        return memo[i][j][isTrue] = ways % 1000000007;
    }
}
```

### Bottom-Up Code:
Complex for this problem; top-down is preferred due to recursive nature and conditional logic.

---

## Problem 4: Burst Balloons
### Problem Statement:
Ek array `nums` diya hai jisme balloon values hain. Har balloon burst karne pe coins milte hain = nums[i-1] * nums[i] * nums[i+1]. Max coins find karo.

**Example:**
```
Input: nums = [3,1,5,8]
Output: 167
Explanation: Burst order: [3,1,5,8] -> [3,5,8] -> [3,8] -> [8] -> [] gives 3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 167
```

### Intuition:
- Har balloon ko last mein burst karne ka socho, kyunki coins depend karte hain adjacent balloons pe.
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
        newNums[0] = 1;
        newNums[n+1] = 1;
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
        newNums[0] = 1;
        newNums[n+1] = 1;
        for (int i = 0; i < n; i++) newNums[i+1] = nums[i];
        
        int[][] dp = new int[n+2][n+2];
        
        // l is the window length
        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                // Try each balloon as last to burst
                for (int k = i; k <= j; k++) {
                    int coins = newNums[i-1] * newNums[k] * newNums[j+1] + // Burst k
                                dp[i][k-1] + // Left part
                                dp[k+1][j]; // Right part
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[1][n];
    }
}
```

---

## Problem 5: Scramble String
### Problem Statement:
Do strings `s1` aur `s2` di hain. Check karo ki s1 ko binary tree ke form mein rearrange karke s2 banaya ja sakta hai ya nahi.

**Example:**
```
Input: s1 = "great", s2 = "rgeat"
Output: true
Explanation: Rearrange "great" as "rgeat" via tree splits.
```

### Intuition:
- Strings ko split karke check karo ki subtrees scramble hain.
- State: `dp[i][j][len]` = whether s1[i..i+len-1] and s2[j..j+len-1] are scrambled.
- Transition: For each split k, check if (left1, left2) and (right1, right2) scramble, or (left1, right2) and (right1, left2).
- Base cases: Single char comparison.

### Tree Diagram:
```
i=0, j=0, len=5 (great, rgeat)
 /        \
k=2       k=3
(gr)(eat) (gre)(at)
```

### Top-Down Code:
```java
public class Solution {
    private Boolean[][][][] memo;
    
    public boolean isScramble(String s1, String s2) {
        int n = s1.length();
        memo = new Boolean[n][n][n+1][2];
        return isScrambleHelper(s1, s2, 0, 0, n, false);
    }
    
    private boolean isScrambleHelper(String s1, String s2, int i, int j, int len, boolean swapped) {
        // Base case: single character
        if (len == 1) {
            return s1.charAt(i) == s2.charAt(j);
        }
        // Check memoized state
        if (memo[i][j][len][swapped ? 1 : 0] != null) {
            return memo[i][j][len][swapped ? 1 : 0];
        }
        
        // Check if strings are equal (optimization)
        String sub1 = s1.substring(i, i+len);
        String sub2 = s2.substring(j, j+len);
        if (sub1.equals(sub2) && !swapped) {
            memo[i][j][len][0] = true;
            return true;
        }
        
        // Check character frequency (pruning)
        if (!haveSameChars(s1, s2, i, j, len)) {
            memo[i][j][len][swapped ? 1 : 0] = false;
            return false;
        }
        
        // Try all possible splits
        for (int k = 1; k < len; k++) {
            // Non-swapped case
            if (isScrambleHelper(s1, s2, i, j, k, false) &&
                isScrambleHelper(s1, s2, i+k, j+k, len-k, false)) {
                memo[i][j][len][swapped ? 1 : 0] = true;
                return true;
            }
            // Swapped case
            if (isScrambleHelper(s1, s2, i, j+len-k, k, true) &&
                isScrambleHelper(s1, s2, i+k, j, len-k, true)) {
                memo[i][j][len][swapped ? 1 : 0] = true;
                return true;
            }
        }
        
        memo[i][j][len][swapped ? 1 : 0] = false;
        return false;
    }
    
    // Helper to check if substrings have same characters
    private boolean haveSameChars(String s1, String s2, int i, int j, int len) {
        int[] count = new int[26];
        for (int k = 0; k < len; k++) {
            count[s1.charAt(i+k) - 'a']++;
            count[s2.charAt(j+k) - 'a']--;
        }
        for (int c : count) if (c != 0) return false;
        return true;
    }
}
```

### Bottom-Up Code:
Complex for Scramble String due to recursive splits; top-down is preferred.

---

## Conclusion
MCM Type pattern bohot powerful hai jab sequence ko optimal tareeke se split aur process karna ho. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Top-Down zyada intuitive hai, lekin Bottom-Up effic
