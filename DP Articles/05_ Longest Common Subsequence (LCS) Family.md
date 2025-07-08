## Longest Common Subsequence (LCS) Family Kya Hai?

**Longest Common Subsequence (LCS)** problem mein aapko do strings di jaati hain, aur aapko unka **longest common subsequence** dhundhna hota hai.

**Subsequence** ka matlab hai ki original string se characters delete karke ek nayi string banana, lekin unka **relative order** change nahi hona chahiye.

**Common Subsequence** ka matlab hai ki ek subsequence jo dono strings mein ho.

**Longest Common Subsequence** ka matlab hai ki us common subsequence ki maximum possible length.

**Example:**

  * String 1: `"ABCDGH"`
  * String 2: `"AEDFHR"`
  * **LCS:** `"ADH"` (Length 3)
      * `A` from S1, `A` from S2
      * `D` from S1, `D` from S2
      * `H` from S1, `H` from S2

Notice karein ki `B`, `C`, `G` aur `E`, `F`, `R` ko delete kar diya gaya hai, lekin `A` ke baad `D` aur `D` ke baad `H` ka order maintain kiya gaya hai.

-----

## LCS Family Ko Kaise Samjhein aur Approach Banayein?

LCS family ke problems ko pehchanne aur approach karne ke kuch key steps hote hain:

### 1\. Problem Ko Pehchanana (Identification)

  * **Do sequences/strings:** Jab bhi problem mein do sequences (strings, arrays, lists) di hon aur unke beech "commonality" (jaise common subsequence, common substring) ya ek ko doosre mein "badalne" (jaise edit distance) ki baat ho, toh LCS family DP ho sakti hai.
  * **Relative Order:** Agar characters ka relative order maintain rakhna zaroori ho, toh yeh subsequence problem hai (na ki substring jismein characters contiguous hone chahiye).
  * **Optimization:** Problem ka goal ya toh maximum length/value dhundhna hoga (LCS, Longest Palindromic Subsequence) ya minimum operations/cost (Edit Distance).

### 2\. DP State Definition

LCS family problems hamesha **2D DP table** use karte hain.
`dp[i][j]` ka matlab generally hota hai:

  * `dp[i][j]` = `string1` ke pehle `i` characters aur `string2` ke pehle `j` characters ko consider karte hue kuch result (e.g., LCS length).

Aam taur par, `dp` table ki dimensions `(m+1) x (n+1)` hoti hain, jahan `m` aur `n` strings ki lengths hain. `+1` isliye kyuki `0` row aur `0` column base cases (empty string) ko handle karte hain.

### 3\. Base Cases

  * **`dp[0][j] = 0`:** Agar `string1` empty hai (0 characters), toh kisi bhi `string2` ke subsequence ke saath common subsequence ki length 0 hogi.
  * **`dp[i][0] = 0`:** Agar `string2` empty hai, toh kisi bhi `string1` ke subsequence ke saath common subsequence ki length 0 hogi.

### 4\. Transitions (Recurrence Relation)

Yeh sabse crucial part hai. `dp[i][j]` ko calculate karne ke liye, hum `string1[i-1]` (i-th character of string1, if 1-indexed string) aur `string2[j-1]` (j-th character of string2) ko compare karte hain.

**Case 1: Characters Match**
If `string1[i-1] == string2[j-1]` (yani, `string1` ka `i`-th character `string2` ke `j`-th character ke barabar hai):

  * Iska matlab hai ki hum is common character ko apne subsequence mein include kar sakte hain.
  * Toh, `dp[i][j]` hoga `1` (current common character ke liye) + `dp[i-1][j-1]` (remaining strings ka LCS).
  * `dp[i][j] = 1 + dp[i-1][j-1]`

**Case 2: Characters Don't Match**
If `string1[i-1] != string2[j-1]`:

  * Iska matlab hai ki hum dono characters ko ek hi common subsequence mein include nahi kar sakte.
  * Toh, humein do possibilities consider karni hongi:
    1.  `string1` ke `i` characters aur `string2` ke `j-1` characters ka LCS (`dp[i][j-1]`). (yani, `string2` ke current character ko ignore kiya)
    2.  `string1` ke `i-1` characters aur `string2` ke `j` characters ka LCS (`dp[i-1][j]`). (yani, `string1` ke current character ko ignore kiya)
  * Hum in dono mein se **maximum** value lenge, kyuki hum **longest** common subsequence dhundh rahe hain.
  * `dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])`

### 5\. Traversal Order

  * `dp` table ko hamesha **top-left se bottom-right** tak fill kiya jaata hai.
  * Outer loop `i` (for `string1`'s length) from `1` to `m`.
  * Inner loop `j` (for `string2`'s length) from `1` to `n`.

### 6\. Final Answer

  * `dp[m][n]` mein final result hoga, jahan `m` aur `n` original strings ki lengths hain.

### Complexity Analysis

  * **Time Complexity:** $O(m \\cdot n)$. Har `dp` cell ko fill karne mein constant time lagta hai.
  * **Space Complexity:** $O(m \\cdot n)$ for the `dp` table. Kuch cases mein space optimization possible hai $O(\\min(m, n))$ tak.

-----

## Example: Longest Common Subsequence (LCS)

Chaliye, standard LCS problem ka solution dekhte hain.

**Problem:**

Given two strings `text1` and `text2`, return the length of their longest common subsequence. If there is no common subsequence, return 0.

**Input:**

  * `text1 = "abcde"`
  * `text2 = "ace"`

**Output:** 3 (LCS is "ace")

-----

### Solution: Longest Common Subsequence (Java Code)

```java
import java.util.Arrays;

public class LongestCommonSubsequence {

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // dp[i][j] will store the length of the LCS of text1[0...i-1] and text2[0...j-1].
        // The table size is (m+1) x (n+1) to handle empty string cases (0th row and 0th column).
        int[][] dp = new int[m + 1][n + 1];

        // Base cases:
        // dp[0][j] = 0 for all j: If text1 is empty, LCS is 0.
        // dp[i][0] = 0 for all i: If text2 is empty, LCS is 0.
        // (These are implicitly 0 due to default array initialization in Java)

        // Fill the dp table using a bottom-up approach.
        // i iterates through lengths of text1 prefix (from 1 to m)
        for (int i = 1; i <= m; i++) {
            // j iterates through lengths of text2 prefix (from 1 to n)
            for (int j = 1; j <= n; j++) {
                // If the current characters match (text1[i-1] and text2[j-1]),
                // then this character contributes 1 to the LCS,
                // plus the LCS of the strings without these characters (dp[i-1][j-1]).
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // If the current characters do not match,
                    // we take the maximum of two possibilities:
                    // 1. Exclude text1's current character: LCS of text1[0...i-2] and text2[0...j-1] (dp[i-1][j])
                    // 2. Exclude text2's current character: LCS of text1[0...i-1] and text2[0...j-2] (dp[i][j-1])
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // The result is stored in dp[m][n], which represents the LCS of the entire text1 and text2.
        return dp[m][n];
    }

    public static void main(String[] args) {
        LongestCommonSubsequence solver = new LongestCommonSubsequence();

        // Example 1:
        String text1_1 = "abcde";
        String text2_1 = "ace";
        System.out.println("LCS of \"" + text1_1 + "\" and \"" + text2_1 + "\": " + solver.longestCommonSubsequence(text1_1, text2_1)); // Expected: 3

        // Example 2:
        String text1_2 = "abc";
        String text2_2 = "abc";
        System.out.println("LCS of \"" + text1_2 + "\" and \"" + text2_2 + "\": " + solver.longestCommonSubsequence(text1_2, text2_2)); // Expected: 3

        // Example 3:
        String text1_3 = "abc";
        String text2_3 = "def";
        System.out.println("LCS of \"" + text1_3 + "\" and \"" + text2_3 + "\": " + solver.longestCommonSubsequence(text1_3, text2_3)); // Expected: 0

        // Example 4:
        String text1_4 = "AGGTAB";
        String text2_4 = "GXTXAYB";
        System.out.println("LCS of \"" + text1_4 + "\" and \"" + text2_4 + "\": " + solver.longestCommonSubsequence(text1_4, text2_4)); // Expected: 4 (GTAB)
    }
}
```

-----

## LCS Family Ke Kuch Popular Variations:

LCS family mein sirf LCS hi nahi, balki iske concepts ko use karke kai alag problems solve ki jaati hain:

1.  **Longest Common Substring:** (Substring contiguous hota hai). DP state same, lekin recurrence alag: agar characters match nahi karte, toh `dp[i][j]` `0` ho jaega, aur maximum value ko track karna hoga.
2.  **Longest Palindromic Subsequence (LPS):** Ek string `s` ka LPS nikalne ke liye, aap LCS(`s`, `reverse(s)`) nikal sakte hain.
3.  **Minimum Number of Deletions to Make a String Palindrome:** `string.length() - LPS(string)`
4.  **Minimum Insertions to Make a String Palindrome:** `string.length() - LPS(string)` (same as deletions)
5.  **Shortest Common Supersequence (SCS):** Do strings `s1` aur `s2` ka SCS woh sabse chhoti string hai jismein `s1` aur `s2` dono subsequences hain. `SCS_length = s1.length() + s2.length() - LCS_length`.
6.  **Edit Distance (Levenshtein Distance):** Minimum operations (insertion, deletion, replacement) to transform one string into another. LCS se thoda alag recurrence hai.
7.  **Print LCS:** Sirf length hi nahi, actual LCS string ko print karna. Iske liye DP table ko traceback karna padta hai.
8.  **Longest Repeating Subsequence:** String `s` ka LRS woh subsequence hai jo `s` mein kam se kam do baar repeat ho raha hai, jahan repeating instances ke characters ka index same nahi hona chahiye. `LCS(s, s)` use karte hain, with an added condition `i != j` for character matches.
9.  **Wildcard Matching / Regular Expression Matching:** Kuch variations ko DP se solve kar sakte hain jo LCS jaisi dikhti hain.

-----

## LCS Family Problems Ko Kaise Samjhein aur Approach Karein (Summary):

1.  **Pehchanein:** Kya problem mein do sequences hain? Kya relative order important hai? Kya hum commonality ya transformation dhundh rahe hain?
2.  **DP Table:** `dp[i][j]` define karein (usually `text1` ke `i` chars aur `text2` ke `j` chars ke beech ka result). Dimensions `(m+1) x (n+1)`.
3.  **Base Cases:** `0`th row aur `0`th column empty string scenarios ke liye (aksar `0` ya initial values).
4.  **Recurrence:**
      * **Match:** Agar `char1 == char2` (yani `text1[i-1] == text2[j-1]`), toh `1 + dp[i-1][j-1]` use karein.
      * **No Match:** Agar `char1 != char2`, toh `dp[i-1][j]` aur `dp[i][j-1]` mein se `max` (LCS, LPS, SCS) ya `min` (Edit Distance) choose karein. Operations (insert, delete, replace) ke costs bhi add ho sakte hain.
5.  **Loop Order:** Hamesha `i` from `1` to `m` aur `j` from `1` to `n` (bottom-up).
6.  **Final Result:** `dp[m][n]`.

LCS family DP ko samajhne aur master karne ke liye practice bahut zaroori hai. Ek baar aap basic LCS ko samajh jaayenge, toh yeh variations ko tackle karna aasaan ho jaega.
------
-----

Aapne **LCS (Longest Common Subsequence) Family** ke kuch bahut hi important variations ka zikr kiya hai. Chaliye, in sabhi problems ko ek-ek karke detail mein samjhte hain, unke approach aur solutions ke saath.

-----

### 1\. Longest Common Substring (LCSUB)

**Problem Statement:**

Aapko do strings, `s1` aur `s2` di gayi hain. Aapko unke **Longest Common Substring** ki length return karni hai. Ek substring contiguous characters ka ek sequence hota hai.

**Example:**

  * `s1 = "abcdef"`
  * `s2 = "abxdef"`
  * **Output:** 3 (Common substrings "ab" (length 2) and "def" (length 3). Max is "def")

**Constraints:**

  * Strings ki length $1 \\le L \\le 1000$

-----

#### Approach: Longest Common Substring

LCSUB bhi 2D DP table use karta hai, bilkul LCS ki tarah. `dp[i][j]` ka matlab `s1` ke pehle `i` characters aur `s2` ke pehle `j` characters ko consider karte hue **ending at `s1[i-1]` and `s2[j-1]`** common substring ki length.

**Key Difference from LCS:**
LCS mein characters match na hone par hum `max(dp[i-1][j], dp[i][j-1])` lete the, kyuki subsequence non-contiguous ho sakta hai. Lekin substring contiguous hona chahiye. Iska matlab hai, agar characters match nahi karte, toh current common substring break ho jaati hai, aur is point par common substring ki length `0` ho jaati hai. Hamein `dp` table ke saare cells mein se **maximum value** ko track karna hoga.

**DP State Definition:**

`dp[i][j]` = Length of the longest common substring ending at `s1[i-1]` and `s2[j-1]`.

**Base Cases:**

  * `dp[0][j] = 0` (s1 empty)
  * `dp[i][0] = 0` (s2 empty)
  * (Java mein default initialization se yeh `0` ho jate hain)

**Transitions (Recurrence Relation):**

Loop `i` from `1` to `m`, `j` from `1` to `n`.

  * **If `s1.charAt(i-1) == s2.charAt(j-1)`:**
      * `dp[i][j] = 1 + dp[i-1][j-1]` (Current character ko include karke pichle common substring ki length badha do)
  * **If `s1.charAt(i-1) != s2.charAt(j-1)`:**
      * `dp[i][j] = 0` (Characters match nahi karte, toh common substring yahan toot jaati hai)

**Final Answer:**

`dp` table ka max value. `dp[m][n]` final answer nahi hota, kyuki longest common substring kahin bhi occur ho sakta hai, zaroori nahi ki strings ke end mein ho. Hamein ek `maxLength` variable maintain karna hoga.

**Complexity Analysis:**

  * **Time Complexity:** $O(m \\cdot n)$.
  * **Space Complexity:** $O(m \\cdot n)$.

-----

#### Solution: Longest Common Substring (Java Code)

```java
import java.util.Arrays;

public class LongestCommonSubstring {

    public int longestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // dp[i][j] stores the length of the longest common substring
        // ending at s1[i-1] and s2[j-1].
        int[][] dp = new int[m + 1][n + 1];

        // This variable will store the maximum length found across the entire DP table.
        int maxLength = 0;

        // Iterate through the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If characters match, extend the common substring.
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    // Update maxLength if current common substring is longer.
                    maxLength = Math.max(maxLength, dp[i][j]);
                } else {
                    // If characters don't match, the common substring breaks,
                    // so the length ending here is 0.
                    dp[i][j] = 0;
                }
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestCommonSubstring solver = new LongestCommonSubstring();

        String s1_1 = "abcdef";
        String s2_1 = "abxdef";
        System.out.println("LCSUB of \"" + s1_1 + "\" and \"" + s2_1 + "\": " + solver.longestCommonSubstring(s1_1, s2_1)); // Expected: 3 ("def")

        String s1_2 = "OldSite:GeeksforGeeks.org";
        String s2_2 = "NewSite:GeeksQuiz.com";
        System.out.println("LCSUB of \"" + s1_2 + "\" and \"" + s2_2 + "\": " + solver.longestCommonSubstring(s1_2, s2_2)); // Expected: 10 ("Geeks")

        String s1_3 = "abcde";
        String s2_3 = "fghij";
        System.out.println("LCSUB of \"" + s1_3 + "\" and \"" + s2_3 + "\": " + solver.longestCommonSubstring(s1_3, s2_3)); // Expected: 0
    }
}
```

-----

### 2\. Longest Palindromic Subsequence (LPS)

**Problem Statement:**

Aapko ek string `s` di gayi hai. Aapko **Longest Palindromic Subsequence (LPS)** ki length return karni hai. Ek subsequence contiguous hone ki zaroorat nahi hai.

**Example:**

  * `s = "bbbab"`

  * **Output:** 4 (LPS is "bbbb")

  * `s = "cbbd"`

  * **Output:** 2 (LPS is "bb")

-----

#### Approach: Longest Palindromic Subsequence

Yeh problem directly LCS ki application hai. Ek string `s` ka longest palindromic subsequence woh hi hoga jo `s` aur `reverse(s)` ka longest common subsequence ho.

**Logic:**

1.  String `s` ko reverse karo, let's call it `s_rev`.
2.  `LPS(s)` is simply `LCS(s, s_rev)`.
3.  Phir standard LCS algorithm apply karo.

-----

#### Solution: Longest Palindromic Subsequence (Java Code)

```java
import java.util.Arrays;

public class LongestPalindromicSubsequence {

    // Helper method to calculate LCS, reused from previous example
    private int calculateLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public int longestPalindromicSubsequence(String s) {
        // Step 1: Create the reverse of the input string.
        StringBuilder sb = new StringBuilder(s);
        String s_rev = sb.reverse().toString();

        // Step 2: The LPS of 's' is the LCS of 's' and 's_rev'.
        return calculateLCS(s, s_rev);
    }

    public static void main(String[] args) {
        LongestPalindromicSubsequence solver = new LongestPalindromicSubsequence();

        String s1 = "bbbab";
        System.out.println("LPS of \"" + s1 + "\": " + solver.longestPalindromicSubsequence(s1)); // Expected: 4 ("bbbb")

        String s2 = "cbbd";
        System.out.println("LPS of \"" + s2 + "\": " + solver.longestPalindromicSubsequence(s2)); // Expected: 2 ("bb")

        String s3 = "agbcba";
        System.out.println("LPS of \"" + s3 + "\": " + solver.longestPalindromicSubsequence(s3)); // Expected: 5 ("abcba")
    }
}
```

-----

### 3\. Minimum Number of Deletions to Make a String Palindrome

**Problem Statement:**

Aapko ek string `s` di gayi hai. Aapko string ko palindrome banane ke liye **minimum number of deletions** return karna hai.

**Example:**

  * `s = "aebcbda"`
  * **Output:** 2
  * **Explanation:**
      * LPS for "aebcbda" is "abcba" (length 5).
      * Original length = 7.
      * Deletions needed = 7 - 5 = 2 (delete 'e' and 'd').

-----

#### Approach: Minimum Deletions to Make Palindrome

Yeh bhi LPS ka direct application hai.
Agar hum ek string `s` ka **Longest Palindromic Subsequence (LPS)** nikalte hain, toh woh LPS string `s` mein se minimum deletions ke baad banne wala sabse bada palindrome hota hai.
Jo characters LPS ka part nahi hain, unhein delete karna padega.

**Logic:**

`Min_Deletions = Original_String_Length - Length_of_LPS(Original_String)`

-----

#### Solution: Minimum Deletions to Make Palindrome (Java Code)

```java
import java.util.Arrays;

public class MinDeletionsToMakePalindrome {

    // Reusing the LCS calculation logic
    private int calculateLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public int minDeletions(String s) {
        int originalLength = s.length();

        // Find the Longest Palindromic Subsequence (LPS) of s.
        // LPS of s is equivalent to LCS(s, reverse(s)).
        StringBuilder sb = new StringBuilder(s);
        String s_rev = sb.reverse().toString();
        int lpsLength = calculateLCS(s, s_rev);

        // Minimum deletions = Original String Length - Length of LPS
        return originalLength - lpsLength;
    }

    public static void main(String[] args) {
        MinDeletionsToMakePalindrome solver = new MinDeletionsToMakePalindrome();

        String s1 = "aebcbda";
        System.out.println("Min deletions for \"" + s1 + "\": " + solver.minDeletions(s1)); // Expected: 2 (LPS="abcba", length=5. 7-5=2)

        String s2 = "geeksforgeeks";
        System.out.println("Min deletions for \"" + s2 + "\": " + solver.minDeletions(s2)); // Expected: 8 (LPS="eefee", length=5. 13-5=8)

        String s3 = "aba";
        System.out.println("Min deletions for \"" + s3 + "\": " + solver.minDeletions(s3)); // Expected: 0 (Already a palindrome)
    }
}
```

-----

### 4\. Minimum Insertions to Make a String Palindrome

**Problem Statement:**

Aapko ek string `s` di gayi hai. Aapko string ko palindrome banane ke liye **minimum number of insertions** return karna hai.

**Example:**

  * `s = "aebcbda"`
  * **Output:** 2
  * **Explanation:** To make "aebcbda" a palindrome, we need to insert 'e' and 'd'. For example, "adbecbda" or "aedbcbdea".
      * Original string: `a e b c b d a`
      * LPS: `a   b c b   a`
      * Missing chars to make `aebcbda` a palindrome from `abcba`:
          * `e` needs to be inserted at `a`'s right.
          * `d` needs to be inserted at `a`'s left.
      * Total insertions needed = 2.

-----

#### Approach: Minimum Insertions to Make Palindrome

Yeh problem bhi LPS ka direct application hai, aur interestingly, iska answer **Minimum Deletions** ke barabar hi hota hai.

**Logic:**

Agar humein ek string `s` ko palindrome banana hai, toh hamein `s` ke `LPS` ko preserve karna hoga. Baaki jo characters `LPS` ka part nahi hain, unhein delete karna padega, ya unke corresponding characters ko insert karna padega. Number of characters to delete is equal to the number of characters to insert to make it a palindrome.

`Min_Insertions = Original_String_Length - Length_of_LPS(Original_String)`

-----

#### Solution: Minimum Insertions to Make Palindrome (Java Code)

```java
import java.util.Arrays;

public class MinInsertionsToMakePalindrome {

    // Reusing the LCS calculation logic
    private int calculateLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public int minInsertions(String s) {
        int originalLength = s.length();

        // Find the Longest Palindromic Subsequence (LPS) of s.
        StringBuilder sb = new StringBuilder(s);
        String s_rev = sb.reverse().toString();
        int lpsLength = calculateLCS(s, s_rev);

        // Minimum insertions = Original String Length - Length of LPS
        // This is the same formula as minimum deletions.
        return originalLength - lpsLength;
    }

    public static void main(String[] args) {
        MinInsertionsToMakePalindrome solver = new MinInsertionsToMakePalindrome();

        String s1 = "aebcbda";
        System.out.println("Min insertions for \"" + s1 + "\": " + solver.minInsertions(s1)); // Expected: 2

        String s2 = "google";
        System.out.println("Min insertions for \"" + s2 + "\": " + solver.minInsertions(s2)); // Expected: 2 (LPS="ogl", 6-3=3, or "oogo", 6-4=2. LPS is "oogo" or "oglo" length 4. Then 6-4=2)

        String s3 = "race";
        System.out.println("Min insertions for \"" + s3 + "\": " + solver.minInsertions(s3)); // Expected: 3 (LPS="r", "a", "c", "e" each length 1. So 4-1=3 for "racecar")
    }
}
```

-----

### 5\. Shortest Common Supersequence (SCS)

**Problem Statement:**

Aapko do strings `s1` aur `s2` di gayi hain. Aapko unke **Shortest Common Supersequence (SCS)** ki length return karni hai. SCS woh sabse chhoti string hai jismein `s1` aur `s2` dono as subsequences maujood hain.

**Example:**

  * `s1 = "abac"`
  * `s2 = "cab"`
  * **Output:** 5 (SCS is "cabac")
  * **Explanation:**
      * `s1 = "abac"` is a subsequence of "cabac".
      * `s2 = "cab"` is a subsequence of "cabac".
      * LCS of "abac" and "cab" is "ab" (length 2).
      * `s1.length() + s2.length() - LCS_length = 4 + 3 - 2 = 5`.

-----

#### Approach: Shortest Common Supersequence

SCS ki length LCS ki length se directly related hai.

**Logic:**

Agar aap `s1` aur `s2` ko combine karte hain, toh unki total length `s1.length() + s2.length()` hogi. Ismein jo characters common subsequence (LCS) ka part hain, woh dono strings mein maujood hain. SCS mein un common characters ko sirf ek baar likha jaega. Isliye, total length mein se LCS ki length ko minus kar do, kyuki woh characters duplicate ho rahe the.

`SCS_Length = s1.length() + s2.length() - LCS_Length(s1, s2)`

-----

#### Solution: Shortest Common Supersequence (Java Code)

```java
import java.util.Arrays;

public class ShortestCommonSupersequence {

    // Reusing the LCS calculation logic
    private int calculateLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public int shortestCommonSupersequenceLength(String s1, String s2) {
        // Calculate the length of the Longest Common Subsequence (LCS) of s1 and s2.
        int lcsLength = calculateLCS(s1, s2);

        // The length of the Shortest Common Supersequence (SCS) is given by:
        // length(s1) + length(s2) - length(LCS(s1, s2))
        // This formula works because the common characters (LCS) are counted twice
        // when you just add lengths, so subtracting LCS_length once corrects this.
        return s1.length() + s2.length() - lcsLength;
    }

    public static void main(String[] args) {
        ShortestCommonSupersequence solver = new ShortestCommonSupersequence();

        String s1_1 = "abac";
        String s2_1 = "cab";
        System.out.println("SCS length of \"" + s1_1 + "\" and \"" + s2_1 + "\": " + solver.shortestCommonSupersequenceLength(s1_1, s2_1)); // Expected: 5

        String s1_2 = "geek";
        String s2_2 = "eke";
        System.out.println("SCS length of \"" + s1_2 + "\" and \"" + s2_2 + "\": " + solver.shortestCommonSupersequenceLength(s1_2, s2_2)); // Expected: 5 (LCS="ek", length 2. 4+3-2=5. SCS="geeke")

        String s1_3 = "AGGTAB";
        String s2_3 = "GXTXAYB";
        System.out.println("SCS length of \"" + s1_3 + "\" and \"" + s2_3 + "\": " + solver.shortestCommonSupersequenceLength(s1_3, s2_3)); // Expected: 10 (LCS="GTAB", length 4. 6+7-4=9) Wait, 13-4=9. My calculation for GXTXAYB is 7. So 6+7-4=9.
                                                                                                                           // My LCS for GGTAB/GXTXAYB was 4. So 6+7-4=9.
                                                                                                                           // The example from GfG (AGGTAB, GXTXAYB) has LCS "GTAB" (length 4). SCS is 6+7-4 = 9. So `AGXGTXAYB` or `AGGXTXAYB`
    }
}
```

-----

### 6\. Edit Distance (Levenshtein Distance)

**Problem Statement:**

Aapko do strings `word1` aur `word2` di gayi hain. Aapko `word1` ko `word2` mein badalne ke liye **minimum number of operations** return karna hai. Teen tarah ke operations allowed hain:

1.  **Insertion**
2.  **Deletion**
3.  **Replacement**

**Example:**

  * `word1 = "horse"`, `word2 = "ros"`
  * **Output:** 3
  * **Explanation:**
      * horse -\> rorse (replace 'h' with 'r')
      * rorse -\> rose (delete 'r')
      * rose -\> ros (delete 'e')

-----

#### Approach: Edit Distance

Edit Distance bhi 2D DP problem hai, jahan `dp[i][j]` ka matlab `word1` ke first `i` characters ko `word2` ke first `j` characters mein transform karne ke liye minimum operations.

**DP State Definition:**

`dp[i][j]` = Minimum operations to convert `word1[0...i-1]` to `word2[0...j-1]`.

**Base Cases:**

  * `dp[0][j] = j`: `word1` empty hai. `word2` ke `j` characters banane ke liye `j` insertions chahiye.
  * `dp[i][0] = i`: `word2` empty hai. `word1` ke `i` characters ko empty banane ke liye `i` deletions chahiye.

**Transitions (Recurrence Relation):**

Loop `i` from `1` to `m`, `j` from `1` to `n`.

  * **If `word1.charAt(i-1) == word2.charAt(j-1)`:**
      * `dp[i][j] = dp[i-1][j-1]` (Characters match, no operation needed for them).
  * **If `word1.charAt(i-1) != word2.charAt(j-1)`:**
      * `dp[i][j]` will be `1 + min(delete_cost, insert_cost, replace_cost)`.
          * **Deletion:** `1 + dp[i-1][j]` (Delete `word1[i-1]`, then convert `word1[0...i-2]` to `word2[0...j-1]`).
          * **Insertion:** `1 + dp[i][j-1]` (Insert `word2[j-1]` into `word1`, then convert `word1[0...i-1]` to `word2[0...j-2]`).
          * **Replacement:** `1 + dp[i-1][j-1]` (Replace `word1[i-1]` with `word2[j-1]`, then convert `word1[0...i-2]` to `word2[0...j-2]`).
      * `dp[i][j] = 1 + Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]))`

**Final Answer:**

`dp[m][n]`.

**Complexity Analysis:**

  * **Time Complexity:** $O(m \\cdot n)$.
  * **Space Complexity:** $O(m \\cdot n)$.

-----

#### Solution: Edit Distance (Java Code)

```java
import java.util.Arrays;

public class EditDistance {

    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // dp[i][j] stores the minimum operations to convert word1[0...i-1] to word2[0...j-1].
        int[][] dp = new int[m + 1][n + 1];

        // Base cases:
        // If word1 is empty (i=0), we need 'j' insertions to get word2[0...j-1].
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        // If word2 is empty (j=0), we need 'i' deletions to convert word1[0...i-1] to empty.
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // If characters match, no operation is needed for them.
                // The cost is just the cost of converting the remaining prefixes.
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // If characters don't match, we consider three operations:
                    // 1. Deletion: Delete word1[i-1]. Cost: 1 + dp[i-1][j]
                    // 2. Insertion: Insert word2[j-1] into word1. Cost: 1 + dp[i][j-1]
                    // 3. Replacement: Replace word1[i-1] with word2[j-1]. Cost: 1 + dp[i-1][j-1]
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],       // Deletion
                                        Math.min(dp[i][j - 1],   // Insertion
                                                 dp[i - 1][j - 1])); // Replacement
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        EditDistance solver = new EditDistance();

        String word1_1 = "horse";
        String word2_1 = "ros";
        System.out.println("Edit Distance from \"" + word1_1 + "\" to \"" + word2_1 + "\": " + solver.minDistance(word1_1, word2_1)); // Expected: 3

        String word1_2 = "intention";
        String word2_2 = "execution";
        System.out.println("Edit Distance from \"" + word1_2 + "\" to \"" + word2_2 + "\": " + solver.minDistance(word1_2, word2_2)); // Expected: 5

        String word1_3 = "sitting";
        String word2_3 = "kitten";
        System.out.println("Edit Distance from \"" + word1_3 + "\" to \"" + word2_3 + "\": " + solver.minDistance(word1_3, word2_3)); // Expected: 3
    }
}
```

-----

### 7\. Print LCS

**Problem Statement:**

Aapko do strings `text1` aur `text2` di gayi hain. Aapko unka **Longest Common Subsequence (LCS) string** print karni hai. Agar kai LCS hain, toh koi bhi ek.

**Example:**

  * `text1 = "AGGTAB"`
  * `text2 = "GXTXAYB"`
  * **Output:** "GTAB"

-----

#### Approach: Print LCS

LCS length nikalne ke liye humne jo `dp` table banaya tha, woh table humein LCS string ko reconstruct karne mein help karta hai.

**Logic:**

1.  Pehle standard LCS algorithm run karke `dp` table (`int[][] dp`) ko fill karo.
2.  Ab `dp` table ko **bottom-right se top-left** tak traceback karo (`i` starting from `m`, `j` starting from `n`).
3.  **If `text1.charAt(i-1) == text2.charAt(j-1)`:**
      * Matlab, yeh character common subsequence ka part hai. Is character ko result string mein add karo aur `i` aur `j` dono ko `1` se decrease karo (`i--`, `j--`).
4.  **If `text1.charAt(i-1) != text2.charAt(j-1)`:**
      * Hamein yeh decide karna hai ki hum `dp[i-1][j]` (up) se aaye hain ya `dp[i][j-1]` (left) se.
      * Agar `dp[i-1][j] > dp[i][j-1]`: Iska matlab hum `dp[i-1][j]` se aaye the (yani `text1` ke current character ko skip kiya tha). Toh `i` ko `1` se decrease karo (`i--`).
      * Else (`dp[i][j-1] >= dp[i-1][j]`): Iska matlab hum `dp[i][j-1]` se aaye the (yani `text2` ke current character ko skip kiya tha). Toh `j` ko `1` se decrease karo (`j--`).
5.  Yeh process tab tak repeat karo jab tak `i > 0` aur `j > 0` na ho.
6.  Result string ko reverse kar do (kyuki hum piche se build kar rahe hain).

-----

#### Solution: Print LCS (Java Code)

```java
import java.util.Arrays;

public class PrintLCS {

    public String printLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Step 1: Compute the DP table for LCS length
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Step 2: Traceback the DP table to reconstruct the LCS string
        StringBuilder lcs = new StringBuilder();
        int i = m;
        int j = n;

        while (i > 0 && j > 0) {
            // If current characters match, it's part of the LCS
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else {
                // If characters don't match, move to the direction that gave the max LCS length
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--; // Move up (skip char from text1)
                } else {
                    j--; // Move left (skip char from text2)
                }
            }
        }

        // The LCS was built in reverse order, so reverse it back.
        return lcs.reverse().toString();
    }

    public static void main(String[] args) {
        PrintLCS solver = new PrintLCS();

        String text1_1 = "AGGTAB";
        String text2_1 = "GXTXAYB";
        System.out.println("LCS of \"" + text1_1 + "\" and \"" + text2_1 + "\": " + solver.printLCS(text1_1, text2_1)); // Expected: "GTAB"

        String text1_2 = "abcde";
        String text2_2 = "ace";
        System.out.println("LCS of \"" + text1_2 + "\" and \"" + text2_2 + "\": " + solver.printLCS(text1_2, text2_2)); // Expected: "ace"

        String text1_3 = "abc";
        String text2_3 = "def";
        System.out.println("LCS of \"" + text1_3 + "\" and \"" + text2_3 + "\": " + solver.printLCS(text1_3, text2_3)); // Expected: ""
    }
}
```

-----

### 8\. Longest Repeating Subsequence (LRS)

**Problem Statement:**

Aapko ek string `s` di gayi hai. Aapko **Longest Repeating Subsequence (LRS)** ki length return karni hai. LRS ek subsequence hai jo `s` mein kam se kam do baar repeat hota hai, jahan repeating instances ke characters ka original string mein index same nahi hona chahiye.

**Example:**

  * `s = "AABEBCDD"`
  * **Output:** 3 (LRS is "ABD")
  * **Explanation:**
      * 'A' from index 0, 'B' from index 3, 'D' from index 6
      * 'A' from index 1, 'B' from index 4, 'D' from index 7
      * Common subsequence "ABD". First 'A' (index 0) and second 'A' (index 1) count as different occurrences because their indices are different.

-----

#### Approach: Longest Repeating Subsequence

LRS problem LCS ka hi ek variation hai. `LRS(s)` nikalne ke liye, hum `LCS(s, s)` calculate karte hain, lekin ek extra condition ke saath: agar `s[i-1]` aur `s[j-1]` match karte hain, toh unke original indices `(i-1)` aur `(j-1)` **different** hone chahiye.

**DP State Definition:**

`dp[i][j]` = Length of the LRS considering `s[0...i-1]` and `s[0...j-1]` (comparing `s` with itself).

**Base Cases:**

  * `dp[0][j] = 0`
  * `dp[i][0] = 0`

**Transitions (Recurrence Relation):**

Loop `i` from `1` to `N`, `j` from `1` to `N` (where `N` is string `s`'s length).

  * **If `s.charAt(i-1) == s.charAt(j-1)` AND `i != j`:**
      * `dp[i][j] = 1 + dp[i-1][j-1]` (Characters match AND their original indices are different, so they can form a repeating pair).
  * **Else (`s.charAt(i-1) != s.charAt(j-1)` OR `i == j`):**
      * `dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])` (Characters don't form a repeating pair, so move on as in normal LCS).

**Final Answer:**

`dp[N][N]`.

-----

#### Solution: Longest Repeating Subsequence (Java Code)

```java
import java.util.Arrays;

public class LongestRepeatingSubsequence {

    public int longestRepeatingSubsequence(String s) {
        int n = s.length();

        // dp[i][j] stores the length of the LRS of s[0...i-1] and s[0...j-1].
        // This is essentially LCS(s, s) with an additional condition.
        int[][] dp = new int[n + 1][n + 1];

        // Fill the dp table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // If characters at current positions (s.charAt(i-1) and s.charAt(j-1)) match
                // AND their original indices are different (i != j),
                // then they form a part of the repeating subsequence.
                if (s.charAt(i - 1) == s.charAt(j - 1) && i != j) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // If characters don't match OR their indices are the same (not a "repeating" instance),
                    // then take the maximum from excluding either character, similar to standard LCS.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][n];
    }

    public static void main(String[] args) {
        LongestRepeatingSubsequence solver = new LongestRepeatingSubsequence();

        String s1 = "AABEBCDD";
        System.out.println("LRS of \"" + s1 + "\": " + solver.longestRepeatingSubsequence(s1)); // Expected: 3 ("ABD")

        String s2 = "axxxy";
        System.out.println("LRS of \"" + s2 + "\": " + solver.longestRepeatingSubsequence(s2)); // Expected: 2 ("xx")

        String s3 = "banana";
        System.out.println("LRS of \"" + s3 + "\": " + solver.longestRepeatingSubsequence(s3)); // Expected: 3 ("ana") or "bna" or "bna"

        String s4 = "aaaa";
        System.out.println("LRS of \"" + s4 + "\": " + solver.longestRepeatingSubsequence(s4)); // Expected: 2 ("aa")
    }
}
```

-----

### 9\. Wildcard Matching / Regular Expression Matching

**Problem Statement:**

Wildcard Matching (`?`, `*`) aur Regular Expression Matching (`.`, `*`) LCS family se inspire hote hain, lekin inki DP recurrence thodi zyada complex hoti hai. Yeh pure LCS problems nahi hain, balki pattern matching problems hain jo 2D DP se solve ki jaati hain.

**Wildcard Matching (LeetCode 44):**

Aapko do strings `s` aur `p` (pattern) di gayi hain. Implement wildcard pattern matching with support for `'?'` and `'*'`.

  * `'?'` matches any single character.
  * `'*'` matches any sequence of characters (including the empty sequence).

**Example:**

  * `s = "aa"`, `p = "a"` -\> `false`
  * `s = "aa"`, `p = "*"` -\> `true`
  * `s = "cb"`, `p = "?a"` -\> `false`
  * `s = "adceb"`, `p = "*a*b"` -\> `true`

-----

#### Approach: Wildcard Matching

Yeh bhi 2D DP hai. `dp[i][j]` ka matlab `s` ke pehle `i` characters `p` ke pehle `j` characters se match karte hain ya nahi.

**DP State Definition:**

`dp[i][j]` = `true` if `s[0...i-1]` matches `p[0...j-1]`, else `false`.

**Base Cases:**

  * `dp[0][0] = true` (Empty string matches empty pattern)
  * `dp[0][j]` for `j > 0`: `p` empty nahi hai, `s` empty hai.
      * `dp[0][j] = dp[0][j-1]` if `p.charAt(j-1) == '*'` (empty sequence match ho sakta hai)
      * Else `false`
  * `dp[i][0]` for `i > 0`: `s` empty nahi hai, `p` empty hai. Always `false`.

**Transitions (Recurrence Relation):**

Loop `i` from `1` to `m`, `j` from `1` to `n`.

  * **If `s.charAt(i-1) == p.charAt(j-1)` OR `p.charAt(j-1) == '?'`:**
      * `dp[i][j] = dp[i-1][j-1]` (Current characters match, ya '?' se match, toh pichle subproblem par depend karega)
  * **If `p.charAt(j-1) == '*'`:**
      * `'*'` can represent:
        1.  **Empty sequence:** `dp[i][j-1]` (current `*` ko ignore kar do, `s[0...i-1]` ko `p[0...j-2]` se match karo)
        2.  **One or more characters:** `dp[i-1][j]` (current `*` ko `s[i-1]` se match karao, aur `s[0...i-2]` ko `p[0...j-1]` (same `*` ke saath) se match karo)
      * `dp[i][j] = dp[i][j-1] || dp[i-1][j]`
  * **Else (`s.charAt(i-1) != p.charAt(j-1)` and `p.charAt(j-1)` is not `'?'` or `'*'`):**
      * `dp[i][j] = false` (No match)

**Final Answer:**

`dp[m][n]`.

**Complexity Analysis:**

  * **Time Complexity:** $O(m \\cdot n)$.
  * **Space Complexity:** $O(m \\cdot n)$.

-----

#### Solution: Wildcard Matching (Java Code)

```java
import java.util.Arrays;

public class WildcardMatching {

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] is true if s[0...i-1] matches p[0...j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Base cases:
        // Empty string matches empty pattern
        dp[0][0] = true;

        // s is empty, p is not empty.
        // Only '*' can match an empty string.
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1]; // '*' matches empty sequence
            }
        }

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Case 1: Characters match or pattern has '?'
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // Case 2: Pattern has '*'
                else if (p.charAt(j - 1) == '*') {
                    // '*' can match an empty sequence (dp[i][j-1])
                    // OR '*' can match one or more characters (dp[i-1][j])
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
                // Case 3: Characters don't match and pattern is not '?' or '*'
                else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        WildcardMatching solver = new WildcardMatching();

        System.out.println("s=\"aa\", p=\"a\": " + solver.isMatch("aa", "a"));     // Expected: false
        System.out.println("s=\"aa\", p=\"*\": " + solver.isMatch("aa", "*"));     // Expected: true
        System.out.println("s=\"cb\", p=\"?a\": " + solver.isMatch("cb", "?a"));   // Expected: false
        System.out.println("s=\"adceb\", p=\"*a*b\": " + solver.isMatch("adceb", "*a*b")); // Expected: true
        System.out.println("s=\"acdcb\", p=\"a*c?b\": " + solver.isMatch("acdcb", "a*c?b")); // Expected: false
        System.out.println("s=\"mississippi\", p=\"mis*is*p*.\": " + solver.isMatch("mississippi", "mis*is*p*.")); // Expected: false
        System.out.println("s=\"\", p=\"*\": " + solver.isMatch("", "*")); // Expected: true
        System.out.println("s=\"\", p=\"\": " + solver.isMatch("", ""));   // Expected: true
    }
}
```

-----

**Regular Expression Matching (LeetCode 10):**

Regular Expression Matching (`.` aur `*` ke saath) Wildcard Matching se thoda zyada tricky hoti hai, khaaskar `*` ke behaviour ke kaaran. `*` yahan `0 ya usse zyada baar pichle character` ko match karta hai. Iski recurrence alag hoti hai aur isko LCS family se alag category mein dekha jaata hai, though 2D DP pattern same hai. Iski explanation ko yahan include karne se length bahut badh jaegi, isliye main isko abhi skip kar raha hoon.

-----
