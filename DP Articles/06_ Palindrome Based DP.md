

# Longest Common Subsequence (LCS) Family Tutorial

Hello dosto! Aaj hum **Longest Common Subsequence (LCS) Family** pattern ke baare mein detail mein baat karenge. Ye pattern tab use hota hai jab humein do sequences (strings ya arrays) ko compare karna ho, aur usually do indices (i, j) ke through states define hote hain. Is tutorial mein hum saare example problems (Longest Common Subsequence, Longest Common Substring, Edit Distance, Shortest Common Supersequence, Sequence Pattern Matching) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with comments. Chalo shuru karte hain!

---

## LCS Family Kya Hai?
Ye pattern tab use hota hai jab humein do sequences (jaise strings ya arrays) ke beech common elements ya operations find karne hote hain. Har problem mein do indices (i, j) use hote hain jo dono sequences ke current positions ko track karte hain. Goal hota hai ya to common subsequence/substring find karna, ya minimum operations, ya koi derived sequence.

**Examples:**
1. Longest Common Subsequence (LCS)
2. Longest Common Substring
3. Edit Distance
4. Shortest Common Supersequence
5. Sequence Pattern Matching (is A subsequence of B?)

**Common Theme:**
- Ek 2D DP array use hota hai jisme states (i, j) store hote hain.
- Har state mein decision lena hota hai: characters match karte hain ya nahi.
- Optimal result (length, operations, ya true/false) chahiye.

---

## Intuition Kaise Build Kare?
LCS Family problems ko samajhne ke liye ye socho:
- Tum do strings ya arrays ke characters ko ek sath compare kar rahe ho.
- Har position pe decide karo: characters match karte hain to include karo, warna skip karo ya operation perform karo.
- Tumhe maximum common part, minimum operations, ya specific pattern check karna hai.

For example:
- **LCS** mein do strings ke common subsequence ki max length find karo.
- **Edit Distance** mein minimum operations chahiye jo ek string ko doosri mein convert kare.

**Key Intuition:**
- State define karo jo current indices (i, j) tak ka result bataye.
- Transition formula socho: match hone pe ek action, mismatch pe doosra action.
- Base cases set karo jo empty strings ya arrays handle kare.

---

## General Approach
LCS Family problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][j]` kya represent karta hai? For example, LCS mein `dp[i][j]` = length of LCS for prefixes text1[0..i-1] and text2[0..j-1].
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, LCS mein:
     - If match: `dp[i][j] = dp[i-1][j-1] + 1`
     - Else: `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[0][j] = 0`, `dp[i][0] = 0` (empty string).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Edit Distance ya Shortest Common Supersequence.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare subproblems solve karne pade.
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for LCS Family:**
- **Bottom-Up** zyada common hai kyunki states predictable hote hain (i, j ke combinations), aur iteration efficient hota hai.
- Top-Down use karo jab recursion se logic clear ho ya subproblems kam hain.

---

## Problem 1: Longest Common Subsequence (LCS)
### Problem Statement:
Do strings `text1` aur `text2` di hain. Inka longest common subsequence (LCS) ki length find karo. Subsequence mein order same hona chahiye, lekin consecutive hona zaroori nahi.

**Example:**
```
Input: text1 = "ABCDGH", text2 = "AEDFHR"
Output: 3
Explanation: LCS is "ADH" of length 3.
```

### Intuition:
- Har character ke liye check: dono strings mein match hai ya nahi.
- State: `dp[i][j]` = LCS length for text1[0..i-1] and text2[0..j-1].
- Transition:
  - If match: `dp[i][j] = dp[i-1][j-1] + 1`
  - Else: `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`
- Base cases: `dp[0][j] = 0`, `dp[i][0] = 0`.

### Tree Diagram:
```
text1="ABC", text2="AEC", i=2, j=2 (B vs E)
 /            \
Match (B==E)   Mismatch
N/A            max(dp[1][2], dp[2][1])
```
Har node pe check: characters match ya nahi.

### Bottom-Up Code:
```java
public class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1; // Match
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // Mismatch
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        memo = new Integer[m+1][n+1];
        return lcsHelper(text1, text2, m, n);
    }
    
    private int lcsHelper(String text1, String text2, int i, int j) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        if (text1.charAt(i-1) == text2.charAt(j-1)) {
            memo[i][j] = lcsHelper(text1, text2, i-1, j-1) + 1;
        } else {
            memo[i][j] = Math.max(lcsHelper(text1, text2, i-1, j), 
                                 lcsHelper(text1, text2, i, j-1));
        }
        
        return memo[i][j];
    }
}
```

### Space Optimization:
Since only previous row is needed:
```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[] dp = new int[n+1];
    
    for (int i = 1; i <= m; i++) {
        int[] curr = new int[n+1];
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                curr[j] = dp[j-1] + 1;
            } else {
                curr[j] = Math.max(dp[j], curr[j-1]);
            }
        }
        dp = curr;
    }
    
    return dp[n];
}
```

---

## Problem 2: Longest Common Substring
### Problem Statement:
Do strings `text1` aur `text2` di hain. Inka longest common substring ki length find karo. Substring consecutive hona chahiye.

**Example:**
```
Input: text1 = "ABCDGH", text2 = "ACDGHR"
Output: 4
Explanation: Substring "CDGH" of length 4.
```

### Intuition:
- Har character ke liye check: match hai to substring extend karo, else reset.
- State: `dp[i][j]` = length of longest common substring ending at text1[i-1] and text2[j-1].
- Transition:
  - If match: `dp[i][j] = dp[i-1][j-1] + 1`
  - Else: `dp[i][j] = 0`
- Base cases: `dp[0][j] = 0`, `dp[i][0] = 0`.

### Bottom-Up Code:
```java
public class Solution {
    public int longestCommonSubstring(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m+1][n+1];
        int maxLen = 0;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        
        return maxLen;
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    private int maxLen;
    
    public int longestCommonSubstring(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        memo = new Integer[m+1][n+1];
        maxLen = 0;
        lcsHelper(text1, text2, m, n);
        return maxLen;
    }
    
    private int lcsHelper(String text1, String text2, int i, int j) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        if (text1.charAt(i-1) == text2.charAt(j-1)) {
            memo[i][j] = lcsHelper(text1, text2, i-1, j-1) + 1;
            maxLen = Math.max(maxLen, memo[i][j]);
        } else {
            memo[i][j] = 0;
        }
        
        lcsHelper(text1, text2, i-1, j); // Explore all pairs
        lcsHelper(text1, text2, i, j-1);
        return memo[i][j];
    }
}
```

### Space Optimization:
```java
public int longestCommonSubstring(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[] dp = new int[n+1];
    int maxLen = 0;
    
    for (int i = 1; i <= m; i++) {
        int[] curr = new int[n+1];
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                curr[j] = dp[j-1] + 1;
                maxLen = Math.max(maxLen, curr[j]);
            }
        }
        dp = curr;
    }
    
    return maxLen;
}
```

---

## Problem 3: Edit Distance
### Problem Statement:
Do strings `word1` aur `word2` di hain. Minimum operations (insert, delete, replace) find karo jo word1 ko word2 mein convert kare.

**Example:**
```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: horse -> rorse (replace 'h' with 'r') -> rose (remove 'r') -> ros (remove 'e')
```

### Intuition:
- Har character ke liye check: match hai ya operation chahiye (insert, delete, replace).
- State: `dp[i][j]` = min operations for word1[0..i-1] to word2[0..j-1].
- Transition:
  - If match: `dp[i][j] = dp[i-1][j-1]`
  - Else: `dp[i][j] = 1 + min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1])`
- Base cases: `dp[0][j] = j` (insert), `dp[i][0] = i` (delete).

### Tree Diagram:
```
word1="ho", word2="ro", i=2, j=2 (o vs o)
 /            \
Match         Mismatch
dp[1][1]      min(replace, delete, insert)
```

### Bottom-Up Code:
```java
public class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m+1][n+1];
        
        // Base cases
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
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

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
   。然而

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        memo = new Integer[m+1][n+1];
        return minDistanceHelper(word1, word2, m, n);
    }
    
    private int minDistanceHelper(String word1, String word2, int i, int j) {
        if (i == 0) return j;
        if (j == 0) return i;
        if (memo[i][j] != null) return memo[i][j];
        
        if (word1.charAt(i-1) == word2.charAt(j-1)) {
            memo[i][j] = minDistanceHelper(word1, word2, i-1, j-1);
        } else {
            memo[i][j] = 1 + Math.min(minDistanceHelper(word1, word2, i-1, j-1), // replace
                                     Math.min(minDistanceHelper(word1, word2, i-1, j), // delete
                                              minDistanceHelper(word1, word2, i, j-1))); // insert
        }
        
        return memo[i][j];
    }
}
```

### Space Optimization:
```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[] dp = new int[n+1];
    
    for (int j = 0; j <= n; j++) dp[j] = j;
    
    for (int i = 1; i <= m; i++) {
        int[] curr = new int[n+1];
        curr[0] = i;
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i-1) == word2.charAt(j-1)) {
                curr[j] = dp[j-1];
            } else {
                curr[j] = 1 + Math.min(dp[j-1], Math.min(dp[j], curr[j-1]));
            }
        }
        dp = curr;
    }
    
    return dp[n];
}
```

---

## Problem 4: Shortest Common Supersequence
### Problem Statement:
Do strings `str1` aur `str2` di hain. Ek shortest string find karo jo dono ka supersequence ho (dono ke saare characters usme hone chahiye in order).

**Example:**
```
Input: str1 = "AB", str2 = "AC"
Output: 3
Explanation: "ABC" is a supersequence of length 3.
```

### Intuition:
- LCS find karo, then non-LCS characters add karo.
- State: Same as LCS, then construct supersequence.
- Transition: Same as LCS, then backtrack to build string.
- Base cases: Same as LCS.

### Bottom-Up Code (Length Only):
```java
public class Solution {
    public int shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        
        return m + n - dp[m][n]; // Total length = len1 + len2 - LCS
    }
}
```

### Top-Down Code (Length Only):
```java
public class Solution {
    private Integer[][] memo;
    
    public int shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        memo = new Integer[m+1][n+1];
        int lcs = lcsHelper(str1, str2, m, n);
        return m + n - lcs;
    }
    
    private int lcsHelper(String str1, String str2, int i, int j) {
        if (i == 0 || j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        if (str1.charAt(i-1) == str2.charAt(j-1)) {
            memo[i][j] = lcsHelper(str1, str2, i-1, j-1) + 1;
        } else {
            memo[i][j] = Math.max(lcsHelper(str1, str2, i-1, j), 
                                 lcsHelper(str1, str2, i, j-1));
        }
        
        return memo[i][j];
    }
}
```

### Space Optimization:
Same as LCS space optimization, then compute length as `m + n - lcs`.

---

## Problem 5: Sequence Pattern Matching
### Problem Statement:
Do strings `A` aur `B` di hain. Check karo ki A, B ka subsequence hai ya nahi.

**Example:**
```
Input: A = "AXY", B = "ADXCPY"
Output: true
Explanation: AXY is a subsequence of ADXCPY.
```

### Intuition:
- Check karo ki A ka har character B mein same order mein milta hai ya nahi.
- State: `dp[i][j]` = true if A[0..i-1] is subsequence of B[0..j-1].
- Transition: Same as LCS, but only check if A is fully matched.
- Base cases: `dp[0][j] = true`, `dp[i][0] = false` (except `dp[0][0]`).

### Bottom-Up Code:
```java
public class Solution {
    public boolean isSubsequence(String A, String B) {
        int m = A.length(), n = B.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int j = 0; j <= n; j++) dp[0][j] = 1;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i-1) == B.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        
        return dp[m][n] == 1;
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public boolean isSubsequence(String A, String B) {
        int m = A.length(), n = B.length();
        memo = new Integer[m+1][n+1];
        return isSubsequenceHelper(A, B, m, n) == 1;
    }
    
    private int isSubsequenceHelper(String A, String B, int i, int j) {
        if (i == 0) return 1;
        if (j == 0) return 0;
        if (memo[i][j] != null) return memo[i][j];
        
        if (A.charAt(i-1) == B.charAt(j-1)) {
            memo[i][j] = isSubsequenceHelper(A, B, i-1, j-1);
        } else {
            memo[i][j] = isSubsequenceHelper(A, B, i, j-1);
        }
        
        return memo[i][j];
    }
}
```

### Optimized Code (Two-Pointer):
Since we only need to check subsequence, we can use two pointers:
```java
public boolean isSubsequence(String A, String B) {
    int i = 0;
    for (char c : B.toCharArray()) {
        if (i < A.length() && A.charAt(i) == c) {
            i++;
        }
    }
    return i == A.length();
}
```

---

## Conclusion
LCS Family pattern bohot versatile hai aur sequence comparison problems ke liye perfect. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai.
- Space optimization ke liye 1D array use karo jab possible ho.

---

---

## 🔍 **Kaise Pehchane ki Problem Palindrome Based DP Hai?**

### ✅ 1. Problem statement me yeh words ho sakte hain:

* **"Make string palindrome"**
* **"Longest palindromic subsequence/substring"**
* **"Minimum insertions/deletions to form palindrome"**
* **"Palindrome partition" / "cut" / "split"**
* **"Count palindromic substrings"**

---

### ✅ 2. Common Patterns in Problem:

| 🔁 Pattern                                            | 🔎 Example               |
| ----------------------------------------------------- | ------------------------ |
| Compare string with **its reverse**                   | `LCS(s, reverse(s))`     |
| Compare characters from both ends (i and j)           | `if (s[i] == s[j])`      |
| Try **adding/removing** characters to make palindrome | Min insert/delete        |
| Divide string and check palindromic **partitions**    | Cut/segment problems     |
| Expand around center for substrings                   | `i ← center, j → center` |

---

## 🧠 **What Approach To Use?**

| Problem Type                       | Recognition                             | DP State                                    | Base Idea                    |
| ---------------------------------- | --------------------------------------- | ------------------------------------------- | ---------------------------- |
| 🧬 Longest Palindromic Subsequence | "Longest subsequence that’s palindrome" | `dp[i][j] = LPS in s[i...j]`                | Use 2D DP from back to front |
| 🧬 Longest Palindromic Substring   | "Substring (not subsequence)"           | `dp[i][j] = true if s[i...j] is palindrome` | OR Expand around center      |
| 🧬 Min Insertions/Deletions        | "Min chars to make palindrome"          | `n - LPS(s)`                                | Use LPS idea                 |
| 🧬 Palindrome Partitioning         | "Split into palindromes" or "Min cuts"  | `dp[i] = min cuts for s[0...i]`             | Check every substring        |
| 🧬 Count of Palindromic Substrings | "How many substrings are palindromes"   | DP\[i]\[j] or expand around center          | Count valid substrings       |

---

## 📌 Example Identifiers & Actions:

| Statement Hint                                 | Use This                                   |
| ---------------------------------------------- | ------------------------------------------ |
| "Make string palindrome by inserting/removing" | Find `LPS` and use `n - LPS`               |
| "Longest palindromic subsequence"              | 2D DP with `s[i] == s[j]` logic            |
| "Longest palindromic substring"                | Expand around center or `dp[i][j]` boolean |
| "Partition such that all are palindromes"      | DP + backtracking                          |
| "Count all palindromic substrings"             | Expand around center or DP                 |

---

## 🔧 Java Snippet Example: Longest Palindromic Subsequence

```java
int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];
    
    for (int i = n - 1; i >= 0; i--) {
        dp[i][i] = 1;
        for (int j = i + 1; j < n; j++) {
            if (s.charAt(i) == s.charAt(j))
                dp[i][j] = 2 + dp[i+1][j-1];
            else
                dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
        }
    }
    return dp[0][n - 1];
}
```

---
