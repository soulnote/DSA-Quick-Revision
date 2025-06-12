**LCS (Longest Common Subsequence) Family** ek **popular group of 2D Dynamic Programming problems** hai jo mostly **strings ya sequences** ke beech comparison pe based hoti hain.

Yeh saare problems ka base concept hota hai:

> **"Do ya zyada sequences ke beech common patterns ya alignment dhundhna using DP."**

---

## 🌟 **Core Idea: Longest Common Subsequence (LCS)**

### 🔹 Problem Statement:

> Given two strings A and B, find the length of the longest subsequence common in both.

### 🧠 Intuition:

* Characters ko *skip* ya *match* karke subsequence banate hain.
* DP table `dp[i][j]` = LCS length for `A[0..i-1]` and `B[0..j-1]`.

### 💡 Transition:

```java
if (A.charAt(i-1) == B.charAt(j-1))
    dp[i][j] = 1 + dp[i-1][j-1];
else
    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
```

---

## 🧬 **LCS Family Problems** (with Problem Type)

| Problem Name                        | Type      | Description                                        |
| ----------------------------------- | --------- | -------------------------------------------------- |
| **LCS (Classic)**                   | Base      | Longest common subsequence                         |
| **Print LCS**                       | Extension | Recover actual subsequence                         |
| **Shortest Common Supersequence**   | Combo     | Smallest string which contains both as subsequence |
| **Minimum Insertions/Deletions**    | Edit      | To convert A → B                                   |
| **Longest Palindromic Subsequence** | Trick     | LCS of string and its reverse                      |
| **Longest Repeating Subsequence**   | Trick     | LCS of string with itself, but i ≠ j               |
| **Sequence Pattern Matching**       | Check     | Is A a subsequence of B?                           |
| **Edit Distance**                   | Extension | Min ops to convert A → B                           |
| **Wildcard Matching**               | Pattern   | DP based string matcher with `*`, `?`              |
| **Regex Matching**                  | Pattern   | Advanced form with `*`, `.`                        |

---

## 📌 Example Problems from Each Type:

### ✅ 1. **Print LCS**

Recover the actual LCS string from DP table (backtracking).

---

### ✅ 2. **Shortest Common Supersequence**

> `SCS(A, B) = A.length() + B.length() - LCS(A, B)`

---

### ✅ 3. **Min Insertions/Deletions to Convert A → B**

* Insertions = B.length() - LCS(A, B)
* Deletions = A.length() - LCS(A, B)

---

### ✅ 4. **Longest Palindromic Subsequence**

> LCS(A, reverse(A))
> Find longest subsequence which is a palindrome.

---

### ✅ 5. **Longest Repeating Subsequence**

> LCS(A, A) but i ≠ j
> Same char repeated at different places.

---

### ✅ 6. **Sequence Pattern Matching**

> Is A a subsequence of B?
> Check: `LCS(A, B) == A.length()`

---

## 🧠 Pattern Recognition Tips:

| Problem Asks For...  | Likely Type                   |
| -------------------- | ----------------------------- |
| Convert A to B       | Edit Distance / Insert/Delete |
| Longest same part    | LCS / LRS                     |
| Use string + reverse | Palindrome based              |
| Subsequence check    | Sequence Match                |
| Regex/wildcard match | Pattern Matching DP           |

---

## 💻 Java Template (LCS):

```java
int lcs(String a, String b) {
    int n = a.length(), m = b.length();
    int[][] dp = new int[n+1][m+1];
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (a.charAt(i-1) == b.charAt(j-1))
                dp[i][j] = 1 + dp[i-1][j-1];
            else
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
        }
    }
    return dp[n][m];
}
```

---
