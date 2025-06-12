
# DP on Strings Tutorial 

## DP on Strings Kya Hai?
Ye pattern tab use hota hai jab humein do ya ek string(s) pe operations perform karni ho, jaise characters insert, delete, replace, ya pattern matching. Aksar ye problems dynamic programming se solve hote hain kyunki states (string indices) overlap karte hain.

**Examples:**
1. Edit Distance
2. Wildcard Matching
3. Regular Expression Matching
4. Minimum ASCII Delete Sum
5. Scramble String

**Common Theme:**
- Ek 2D DP array use hota hai jisme states (i, j) do strings ke current positions ko track karte hain.
- Har state mein decide karo: characters match karte hain ya operation (insert/delete/replace) chahiye.
- Optimal result (minimum cost, boolean match, ya valid scramble) chahiye.

---

## Intuition Kaise Build Kare?
DP on Strings problems ko samajhne ke liye ye socho:
- Tum do strings ko compare kar rahe ho, ek character at a time.
- Har position pe decide karo: characters match karte hain to aage badho, warna operation perform karo (jaise delete ya replace).
- Tumhe minimum operations, matching result, ya valid transformation chahiye.

For example:
- **Edit Distance** mein minimum operations chahiye jo ek string ko doosri mein convert kare.
- **Wildcard Matching** mein check karo ki string ek pattern ke sath match karti hai ya nahi.

**Key Intuition:**
- State define karo jo current indices (i, j) tak ka result bataye.
- Transition formula socho: match hone pe ek action, mismatch pe operations try karo.
- Base cases set karo jo empty strings ya patterns handle kare.

---

## General Approach
DP on Strings problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][j]` kya represent karta hai? For example, Edit Distance mein `dp[i][j]` = min operations for s1[0..i-1] to s2[0..j-1].
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, Edit Distance mein:
     - If match: `dp[i][j] = dp[i-1][j-1]`
     - Else: `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[0][j] = j` (insert all).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Scramble String ya Regular Expression Matching.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare subproblems solve karne pade, jaise Edit Distance.
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for DP on Strings:**
- **Bottom-Up** zyada common hai kyunki states predictable hote hain (i, j ke combinations), aur iteration efficient hota hai.
- Top-Down use karo jab recursion se logic clear ho (jaise Scramble String) ya complex pattern matching ho.

---

## Problem 1: Edit Distance
### Problem Statement:
Do strings `word1` aur `word2` di hain. Minimum operations (insert, delete, replace) find karo jo word1 ko word2 mein convert kare.

**Example:**
```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: horse -> rorse (replace 'h' with 'r') -> rose (remove 'r') -> ros (remove 'e')
```

### Intuition:
- Har character ke liye check: match hai ya operation chahiye.
- State: `dp[i][j]` = min operations for word1[0..i-1] to word2[0..j-1].
- Transition:
  - If match: `dp[i][j] = dp[i-1][j-1]`
  - Else: `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`
- Base cases: `dp[0][j] = j`, `dp[i][0] = i`.

### Tree Diagram:
```
word1="ho", word2="ro", i=2, j=2 (o vs o)
 /            \
Match         Mismatch
dp[1][1]      min(replace, delete, insert)
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        memo = new Integer[m+1][n+1];
        return minDistanceHelper(word1, word2, m, n);
    }
    
    private int minDistanceHelper(String word1, String word2, int i, int j) {
        // Base case: if word1 is empty, insert all chars of word2
        if (i == 0) return j;
        // Base case: if word2 is empty, delete all chars of word1
        if (j == 0) return i;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        // If characters match, no operation needed
        if (word1.charAt(i-1) == word2.charAt(j-1)) {
            memo[i][j] = minDistanceHelper(word1, word2, i-1, j-1);
        } else {
            // Try replace, delete, insert and take minimum
            memo[i][j] = 1 + Math.min(minDistanceHelper(word1, word2, i-1, j-1), // replace
                            Math.min(minDistanceHelper(word1, word2, i-1, j), // delete
                                     minDistanceHelper(word1, word2, i, j-1))); // insert
        }
        
        return memo[i][j];
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m+1][n+1];
        
        // Base cases: empty string conversions
        for (int i = 0; i <= m; i++) dp[i][0] = i; // Delete all chars
        for (int j = 0; j <= n; j++) dp[0][j] = j; // Insert all chars
        
        // Fill dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If chars match, no operation needed
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // Take minimum of replace, delete, insert
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], // replace
                                    Math.min(dp[i-1][j], // delete
                                             dp[i][j-1])); // insert
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### Space Optimization:
```java
public class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] dp = new int[n+1];
        
        // Base case: empty word1
        for (int j = 0; j <= n; j++) dp[j] = j;
        
        // Iterate with single row
        for (int i = 1; i <= m; i++) {
            int[] curr = new int[n+1];
            curr[0] = i; // Base case: empty word2
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    curr[j] = dp[j-1]; // Match
                } else {
                    curr[j] = 1 + Math.min(dp[j-1], // replace
                                          Math.min(dp[j], curr[j-1])); // delete, insert
                }
            }
            dp = curr;
        }
        
        return dp[n];
    }
}
```

---

## Problem 2: Wildcard Matching
### Problem Statement:
Ek string `s` aur ek pattern `p` diya hai, jisme p mein '*' (zero or more chars) aur '*' (single char) hain. Check karo if s matches p.

**Example:**
```
Input: s = "aa", p = "a*"
Output: true
Explanation: '*' matches "aa".
```

### Intuition:
- Har pattern char ke liye check: match, single char, ya '*' ke multiple expansions.
- State: `dp[i][j]` = whether s[0..i-1] matches p[0..j-1].
- Transition:
  - If match or '?': `dp[i][j] = dp[i-1][j-1]`
  - If '*': `dp[i][j] = dp[i][j-1] || dp[i-1][j]` (use '*' or more chars)
- Base cases: `dp[0][0] = true`, `dp[0][j] = true` if p[j-1] = '*'.

### Top-Down Code:
```java
public class Solution {
    private Boolean[][] memo;
    
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        memo = new Boolean[m+1][n+1];
        return isMatchHelper(s, p, m, n);
    }
    
    private boolean isMatchHelper(String s, String p, int i, int j) {
        // Base case: both strings exhausted
        if (i == 0 && j == 0) return true;
        // Base case: pattern exhausted, string not
        if (j == 0) return false;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        // If pattern is '*', try matching zero or more chars
        if (p.charAt(j-1) == '*') {
            memo[i][j] = isMatchHelper(s, p, i, j-1) || // Use '*' for zero chars
                         (i > 0 && isMatchHelper(s, p, i-1, j)); // Use '*' for one more char
        } else {
            // If chars match or '?', check previous states
            if (i > 0 && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?')) {
                memo[i][j] = isMatchHelper(s, p, i-1, j-1);
            } else {
                memo[i][j] = false;
            }
        }
        
        return memo[i][j];
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        
        // Base case: empty string and pattern match
        dp[0][0] = true;
        // Handle patterns starting with '*'
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j-1) == '*') dp[0][j] = dp[0][j-1];
        }
        
        // Fill dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j-1) == '*') {
                    // '*' can match zero or more chars
                    dp[i][j] = dp[i][j-1] || dp[i-1][j];
                } else if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?') {
                    // Match or '?' allows previous state
                    dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        
        return dp[m][n];
    }
}
```

---

## Problem 3: Regular Expression Matching
### Problem Statement:
Ek string `s` aur ek pattern `p` diya hai, jisme '.' (any char) aur '*' (zero or more of prev char) hain. Check karo if s matches p.

**Example:**
```
Input: s = "aa", p = "a*"
Output: true
Explanation: "a*" matches "aa".
```

### Intuition:
- Similar to Wildcard Matching, lekin '*' ka matlab alag hai (zero or more of previous char).
- State: `dp[i][j]` = whether s[0..i-1] matches p[0..j-1].
- Transition:
  - If match or '.': `dp[i][j] = dp[i-1][j-1]`
  - If '*': `dp[i][j] = dp[i][j-2] || (match && dp[i-1][j])`
- Base cases: `dp[0][0] = true`, `dp[0][j] = true` if p[j-1] = '*' and dp[0][j-2].

### Top-Down Code:
```java
public class Solution {
    private Boolean[][] memo;
    
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        memo = new Boolean[m+1][n+1];
        return isMatchHelper(s, p, m, n);
    }
    
    private boolean isMatchHelper(String s, String p, int i, int j) {
        // Base case: both exhausted
        if (i == 0 && j == 0) return true;
        // Base case: pattern exhausted
        if (j == 0) return false;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        // Handle '*' case
        if (j > 1 && p.charAt(j-1) == '*') {
            // Zero occurrences or match previous char
            memo[i][j] = isMatchHelper(s, p, i, j-2) ||
                         (i > 0 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') &&
                          isMatchHelper(s, p, i-1, j));
        } else {
            // Match single char or '.'
            if (i > 0 && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.')) {
                memo[i][j] = isMatchHelper(s, p, i-1, j-1);
            } else {
                memo[i][j] = false;
            }
        }
        
        return memo[i][j];
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        
        // Base case: empty string and pattern
        dp[0][0] = true;
        // Handle patterns with '*'
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j-1) == '*') dp[0][j] = dp[0][j-2];
        }
        
        // Fill dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j-1) == '*') {
                    // Zero occurrences or match previous char
                    dp[i][j] = dp[i][j-2] ||
                               (j > 1 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') &&
                                dp[i-1][j]);
                } else if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.') {
                    // Match single char or '.'
                    dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        
        return dp[m][n];
    }
}
```

---

## Problem 4: Minimum ASCII Delete Sum
### Problem Statement:
Do strings `s1` aur `s2` di hain. Minimum ASCII value sum find karo jo delete karke dono strings ko same banaye.

**Example:**
```
Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Delete 's' (115) and 't' (116) to get "ea", sum = 231.
```

### Intuition:
- Similar to Edit Distance, lekin cost ASCII values ka sum hai.
- State: `dp[i][j]` = min ASCII delete sum for s1[0..i-1] to s2[0..j-1].
- Transition:
  - If match: `dp[i][j] = dp[i-1][j-1]`
  - Else: `dp[i][j] = min(dp[i-1][j] + s1[i-1], dp[i][j-1] + s2[j-1])`
- Base cases: `dp[0][j] = sum(s2[0..j-1])`, `dp[i][0] = sum(s1[0..i-1])`.

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        memo = new Integer[m+1][n+1];
        return deleteSumHelper(s1, s2, m, n);
    }
    
    private int deleteSumHelper(String s1, String s2, int i, int j) {
        // Base case: delete all remaining chars of s2
        if (i == 0 && j > 0) {
            int sum = 0;
            for (int k = 0; k < j; k++) sum += s2.charAt(k);
            return sum;
        }
        // Base case: delete all remaining chars of s1
        if (j == 0 && i > 0) {
            int sum = 0;
            for (int k = 0; k < i; k++) sum += s1.charAt(k);
            return sum;
        }
        // Base case: both empty
        if (i == 0 && j == 0) return 0;
        // Check memoized state
        if (memo[i][j] != null) return memo[i][j];
        
        // If chars match, no deletion needed
        if (s1.charAt(i-1) == s2.charAt(j-1)) {
            memo[i][j] = deleteSumHelper(s1, s2, i-1, j-1);
        } else {
            // Try deleting from s1 or s2
            memo[i][j] = Math.min(deleteSumHelper(s1, s2, i-1, j) + s1.charAt(i-1),
                                  deleteSumHelper(s1, s2, i, j-1) + s2.charAt(j-1));
        }
        
        return memo[i][j];
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        // Base cases: delete all chars from s1 or s2
        for (int i = 1; i <= m; i++) dp[i][0] = dp[i-1][0] + s1.charAt(i-1);
        for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j-1] + s2.charAt(j-1);
        
        // Fill dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If chars match, no deletion
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // Minimum of deleting from s1 or s2
                    dp[i][j] = Math.min(dp[i-1][j] + s1.charAt(i-1),
                                        dp[i][j-1] + s2.charAt(j-1));
                }
            }
        }
        
        return dp[m][n];
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
DP on Strings pattern bohot versatile hai aur string manipulation/matching problems ke liye perfect. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai for complex problems like Scramble String.

---
