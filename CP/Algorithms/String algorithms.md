# String Algorithms 
##  Why String Algorithms Matter?

Real world applications:
- **Search engines** - Google mein type karte time suggestions
- **Plagiarism detection** - Similar text find karna
- **DNA sequencing** - Pattern matching in genes
- **Auto-correct** - Spell checking
- **Compression** - Zip files

---

##  Most Important String Algorithms

### 1. KMP Algorithm (Knuth-Morris-Pratt)

**Problem:** Find all occurrences of pattern in text efficiently.

**Naive approach:** O(n * m) time
**KMP:** O(n + m) time

**Concept:** Jab pattern mismatch hota hai, toh pattern ko utna shift karo jitna prefix aur suffix match ho raha hai.

```java
public List<Integer> KMP(String text, String pattern) {
    List<Integer> result = new ArrayList<>();
    if (pattern.isEmpty()) return result;
    
    // Step 1: Build LPS (Longest Prefix Suffix) array
    int[] lps = buildLPS(pattern);
    
    // Step 2: Search using LPS
    int i = 0; // index for text
    int j = 0; // index for pattern
    
    while (i < text.length()) {
        if (text.charAt(i) == pattern.charAt(j)) {
            i++;
            j++;
        }
        
        if (j == pattern.length()) {
            result.add(i - j); // Pattern found
            j = lps[j - 1];
        } 
        else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
            if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
    }
    return result;
}

private int[] buildLPS(String pattern) {
    int[] lps = new int[pattern.length()];
    int len = 0; // length of previous longest prefix suffix
    int i = 1;
    lps[0] = 0;
    
    while (i < pattern.length()) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps;
}
```

**Explanation:**

**LPS array kya hai?**
- Har index pe store karta hai ki kitna length ka prefix suffix match kar raha hai
- Example: pattern = "ABABAC"
  - Index 0 ('A'): lps[0] = 0
  - Index 1 ('B'): prefix "A", suffix "B" → match nahi → lps[1] = 0
  - Index 2 ('A'): prefix "AB", suffix "BA" → match nahi → lps[2] = 0
  - Index 3 ('B'): prefix "ABA", suffix "BAB" → match nahi → lps[3] = 0
  - Index 4 ('A'): prefix "ABAB", suffix "BABA" → match nahi → lps[4] = 0
  - Index 5 ('C'): prefix "ABABA", suffix "BABAC" → match nahi → lps[5] = 0

**Kaam kaise karta hai?**
Jab mismatch hota hai toh pattern ko wapas starting se nahi laate. LPS array batata hai ki kitna shift karna hai.

---

### 2. Rabin-Karp Algorithm

**Problem:** Find pattern in text using hashing.

**Concept:** Pattern ka hash calculate karo. Phir text ke har substring ka hash calculate karo. Agar hash match kare toh actual compare karo.

```java
public List<Integer> rabinKarp(String text, String pattern) {
    List<Integer> result = new ArrayList<>();
    int n = text.length();
    int m = pattern.length();
    
    if (m > n) return result;
    
    int prime = 101; // A prime number for hashing
    int d = 256;     // Number of characters
    
    // Calculate hash for pattern and first window of text
    int patternHash = 0;
    int textHash = 0;
    int h = 1;
    
    // Calculate h = d^(m-1) % prime
    for (int i = 0; i < m - 1; i++) {
        h = (h * d) % prime;
    }
    
    // Calculate initial hashes
    for (int i = 0; i < m; i++) {
        patternHash = (d * patternHash + pattern.charAt(i)) % prime;
        textHash = (d * textHash + text.charAt(i)) % prime;
    }
    
    // Slide the pattern over text
    for (int i = 0; i <= n - m; i++) {
        // If hash matches, verify characters
        if (patternHash == textHash) {
            boolean match = true;
            for (int j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) result.add(i);
        }
        
        // Calculate hash for next window
        if (i < n - m) {
            textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % prime;
            if (textHash < 0) textHash += prime;
        }
    }
    return result;
}
```

**Explanation:**

**Hash kya hai?**
String ko number mein convert karo. Example: "abc" = a*256^2 + b*256^1 + c*256^0

**Kaam kaise karta hai?**
1. Pattern ka hash banao
2. Text ke har window ka hash banao
3. Agar hash match kare toh actual compare karo
4. Next window ka hash previous hash se calculate karo (sliding window)

**Advantage:** Average case O(n+m)
**Disadvantage:** Hash collision possible hai

---

### 3. Z-Algorithm

**Problem:** Find all occurrences of pattern in text.

**Concept:** Z-array banate hain jo batata hai ki har index se kitna long substring pattern ke prefix se match karta hai.

```java
public List<Integer> ZAlgorithm(String text, String pattern) {
    String concat = pattern + "$" + text;
    int[] Z = calculateZ(concat);
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < Z.length; i++) {
        if (Z[i] == pattern.length()) {
            result.add(i - pattern.length() - 1);
        }
    }
    return result;
}

private int[] calculateZ(String str) {
    int n = str.length();
    int[] Z = new int[n];
    int l = 0, r = 0;
    
    for (int i = 1; i < n; i++) {
        if (i > r) {
            l = r = i;
            while (r < n && str.charAt(r - l) == str.charAt(r)) {
                r++;
            }
            Z[i] = r - l;
            r--;
        } else {
            int k = i - l;
            if (Z[k] < r - i + 1) {
                Z[i] = Z[k];
            } else {
                l = i;
                while (r < n && str.charAt(r - l) == str.charAt(r)) {
                    r++;
                }
                Z[i] = r - l;
                r--;
            }
        }
    }
    return Z;
}
```

**Explanation:**

**Z-array kya hai?**
Z[i] batata hai ki string ke index i se kitna long substring string ke prefix se match karta hai.

Example: "aabcaabxaaaz"
- Z[0] = 12 (poori string)
- Z[1] = 1 ("a" matches with prefix "a")
- Z[4] = 3 ("aab" matches with prefix "aab")

**Kaam kaise karta hai?**
Pattern + "$" + Text concatenate karo. Z-array calculate karo. Jahan Z[i] = pattern.length, wahan pattern mila.

---

### 4. Manacher's Algorithm

**Problem:** Find longest palindromic substring in O(n) time.

```java
public String longestPalindrome(String s) {
    if (s == null || s.length() < 2) return s;
    
    // Transform string to handle even length palindromes
    // Example: "abc" -> "^#a#b#c#$"
    String transformed = preprocess(s);
    int n = transformed.length();
    int[] P = new int[n]; // Palindrome radii
    int center = 0, right = 0;
    
    for (int i = 1; i < n - 1; i++) {
        int mirror = 2 * center - i; // mirror of i wrt center
        
        if (i < right) {
            P[i] = Math.min(right - i, P[mirror]);
        }
        
        // Expand around center i
        while (transformed.charAt(i + (1 + P[i])) == 
               transformed.charAt(i - (1 + P[i]))) {
            P[i]++;
        }
        
        // Update center and right boundary
        if (i + P[i] > right) {
            center = i;
            right = i + P[i];
        }
    }
    
    // Find maximum palindrome
    int maxLen = 0;
    int centerIndex = 0;
    for (int i = 1; i < n - 1; i++) {
        if (P[i] > maxLen) {
            maxLen = P[i];
            centerIndex = i;
        }
    }
    
    // Extract original substring
    int start = (centerIndex - maxLen) / 2;
    return s.substring(start, start + maxLen);
}

private String preprocess(String s) {
    StringBuilder sb = new StringBuilder();
    sb.append("^");
    for (char c : s.toCharArray()) {
        sb.append("#").append(c);
    }
    sb.append("#$");
    return sb.toString();
}
```

**Explanation:**

**Core idea:** Har character ko center maankar palindrome expand karo, lekin previously calculated information reuse karo.

**Key insights:**
1. String mein '#' insert karo taaki even length palindromes bhi handle ho
2. P[i] = index i par palindrome ka radius
3. Mirror property use karo - agar i, center ke right boundary ke andar hai, toh uska mirror already calculate hai

**Example:** "babad" → "^#b#a#b#a#d#$"
- Center 'a' pe palindrome "bab" hai radius 2

---

### 5. Suffix Array & LCP Array

**Problem:** Find longest repeated substring, lexicographically smallest rotation.

```java
public class SuffixArray {
    String s;
    Integer[] suffixArray;
    int[] rank;
    int[] lcp;
    
    public SuffixArray(String s) {
        this.s = s;
        buildSuffixArray();
        buildLCP();
    }
    
    private void buildSuffixArray() {
        int n = s.length();
        suffixArray = new Integer[n];
        for (int i = 0; i < n; i++) {
            suffixArray[i] = i;
        }
        
        // Sort suffixes using custom comparator
        Arrays.sort(suffixArray, (a, b) -> {
            return s.substring(a).compareTo(s.substring(b));
        });
    }
    
    private void buildLCP() {
        int n = s.length();
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }
        
        lcp = new int[n - 1];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }
            int j = suffixArray[rank[i] + 1];
            while (i + k < n && j + k < n && s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            }
            lcp[rank[i]] = k;
            if (k > 0) k--;
        }
    }
    
    public String longestRepeatedSubstring() {
        int maxLen = 0;
        int idx = -1;
        for (int i = 0; i < lcp.length; i++) {
            if (lcp[i] > maxLen) {
                maxLen = lcp[i];
                idx = suffixArray[i];
            }
        }
        return maxLen > 0 ? s.substring(idx, idx + maxLen) : "";
    }
}
```

**Explanation:**

**Suffix Array kya hai?**
String ke saare suffixes ka sorted order.

Example: "banana"
Suffixes:
0: banana
1: anana
2: nana
3: ana
4: na
5: a

Sorted suffixes:
5: a
3: ana
1: anana
0: banana
4: na
2: nana

Suffix array = [5, 3, 1, 0, 4, 2]

**LCP (Longest Common Prefix) array kya hai?**
Adjacent suffixes ke beech ka common prefix length.

LCP[0] = lcp("a", "ana") = 1 ("a")
LCP[1] = lcp("ana", "anana") = 3 ("ana")
LCP[2] = lcp("anana", "banana") = 0
LCP[3] = lcp("banana", "na") = 0
LCP[4] = lcp("na", "nana") = 2 ("na")

**Uses:**
- Longest repeated substring = max(LCP)
- Lexicographically smallest rotation
- Pattern searching

---

## 📋 Top 10 Most Asked String Algorithm Problems

---

### 1. Implement strStr() (Find pattern in text)

**Problem:** Return index of first occurrence of pattern in text.

```java
public int strStr(String haystack, String needle) {
    if (needle.isEmpty()) return 0;
    
    int n = haystack.length();
    int m = needle.length();
    
    // Use KMP for O(n+m) time
    int[] lps = buildLPS(needle);
    int i = 0, j = 0;
    
    while (i < n) {
        if (haystack.charAt(i) == needle.charAt(j)) {
            i++;
            j++;
        }
        if (j == m) {
            return i - j;
        } else if (i < n && haystack.charAt(i) != needle.charAt(j)) {
            if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
    }
    return -1;
}

private int[] buildLPS(String pattern) {
    int[] lps = new int[pattern.length()];
    int len = 0;
    int i = 1;
    
    while (i < pattern.length()) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps;
}
```

KMP algorithm use karo. LPS array build karo. Pattern match karte jao, mismatch pe LPS use karke shift karo.

---

### 2. Longest Palindromic Substring

**Problem:** Find longest palindrome in string.

```java
public String longestPalindrome(String s) {
    if (s == null || s.length() < 2) return s;
    
    int start = 0, maxLen = 1;
    
    for (int i = 0; i < s.length(); i++) {
        // Odd length palindrome
        int len1 = expandAroundCenter(s, i, i);
        // Even length palindrome
        int len2 = expandAroundCenter(s, i, i + 1);
        
        int len = Math.max(len1, len2);
        if (len > maxLen) {
            maxLen = len;
            start = i - (len - 1) / 2;
        }
    }
    return s.substring(start, start + maxLen);
}

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

Har character ko center maankar expand karo. Do cases - odd length (center ek character) aur even length (center do characters). O(n^2) time but simple.

---

### 3. Longest Common Prefix

**Problem:** Find longest common prefix among array of strings.

```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }
    }
    return prefix;
}
```

Pehle string ko prefix maano. Har next string se check karo. Agar prefix match nahi karta toh last character hatao. Tab tak karo jab tak match ho jaye.

---

### 4. Minimum Window Substring

**Problem:** Find smallest substring containing all characters of target.

```java
public String minWindow(String s, String t) {
    if (s.length() < t.length()) return "";
    
    int[] need = new int[128];
    int[] have = new int[128];
    
    for (char c : t.toCharArray()) {
        need[c]++;
    }
    
    int left = 0, right = 0;
    int minLen = Integer.MAX_VALUE;
    int minStart = 0;
    int count = 0;
    
    while (right < s.length()) {
        char c = s.charAt(right);
        have[c]++;
        
        if (need[c] > 0 && have[c] <= need[c]) {
            count++;
        }
        
        while (count == t.length()) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minStart = left;
            }
            
            char leftChar = s.charAt(left);
            have[leftChar]--;
            if (need[leftChar] > 0 && have[leftChar] < need[leftChar]) {
                count--;
            }
            left++;
        }
        right++;
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}
```

Sliding window technique. Right pointer badhao jab tak saare characters mil jaye. Phir left pointer badhao window chhoti karne ke liye. Count maintain karo kitne required characters mil gaye.

---

### 5. Group Anagrams

**Problem:** Group strings that are anagrams of each other.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    
    return new ArrayList<>(map.values());
}
```

Har string ko sort karo. Sorted string map mein key banegi. Jo strings same sorted string produce karegi woh anagrams hain.

---

### 6. Longest Substring Without Repeating Characters

**Problem:** Find longest substring with all unique characters.

```java
public int lengthOfLongestSubstring(String s) {
    int[] lastIndex = new int[128];
    Arrays.fill(lastIndex, -1);
    
    int maxLen = 0;
    int left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        if (lastIndex[c] >= left) {
            left = lastIndex[c] + 1;
        }
        
        lastIndex[c] = right;
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

Sliding window. Last index array maintain karo har character ka. Agar character window mein already hai toh left pointer ko move karo.

---

### 7. Palindromic Substrings

**Problem:** Count number of palindromic substrings.

```java
public int countSubstrings(String s) {
    int count = 0;
    
    for (int center = 0; center < s.length(); center++) {
        // Odd length
        count += expandCount(s, center, center);
        // Even length
        count += expandCount(s, center, center + 1);
    }
    return count;
}

private int expandCount(String s, int left, int right) {
    int count = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        count++;
        left--;
        right++;
    }
    return count;
}
```

Har center se expand karo. Har palindrome milega count++. O(n^2) time.

---

### 8. Decode String

**Problem:** Decode encoded string like "3[a]2[bc]" → "aaabcbc".

```java
public String decodeString(String s) {
    Stack<Integer> countStack = new Stack<>();
    Stack<StringBuilder> stringStack = new Stack<>();
    StringBuilder current = new StringBuilder();
    int k = 0;
    
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0');
        } else if (c == '[') {
            countStack.push(k);
            stringStack.push(current);
            current = new StringBuilder();
            k = 0;
        } else if (c == ']') {
            int repeat = countStack.pop();
            StringBuilder decoded = stringStack.pop();
            for (int i = 0; i < repeat; i++) {
                decoded.append(current);
            }
            current = decoded;
        } else {
            current.append(c);
        }
    }
    return current.toString();
}
```

Stack use karo. Number milne par count store karo. '[' milne par current string stack mein daalo. ']' milne par pop karo aur repeat karo.

---

### 9. Valid Palindrome II

**Problem:** Check if string can be palindrome by deleting at most one character.

```java
public boolean validPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return isPalindrome(s, left + 1, right) || 
                   isPalindrome(s, left, right - 1);
        }
        left++;
        right--;
    }
    return true;
}

private boolean isPalindrome(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}
```

Do pointer approach. Jab mismatch mile toh left delete karke check karo ya right delete karke check karo. Agar dono mein se koi true return kare toh valid palindrome.

---

### 10. Repeated Substring Pattern

**Problem:** Check if string can be formed by repeating a substring.

```java
public boolean repeatedSubstringPattern(String s) {
    int n = s.length();
    
    for (int len = 1; len <= n / 2; len++) {
        if (n % len == 0) {
            int repeats = n / len;
            StringBuilder sb = new StringBuilder();
            String sub = s.substring(0, len);
            for (int i = 0; i < repeats; i++) {
                sb.append(sub);
            }
            if (sb.toString().equals(s)) return true;
        }
    }
    return false;
}

// Optimized O(n) solution using KMP
public boolean repeatedSubstringPatternOptimized(String s) {
    String doubled = s + s;
    String sub = doubled.substring(1, doubled.length() - 1);
    return sub.contains(s);
}
```


- Approach 1: Har possible length check karo jo n ko divide kare
- Approach 2: s + s banake beech ka substring nikaalo. Agar s usme exist karta hai toh repeated pattern hai.

---

## 📊 Algorithm Comparison Table

| Algorithm | Time Complexity | Space Complexity | Best For |
|-----------|----------------|------------------|----------|
| KMP | O(n + m) | O(m) | Pattern matching |
| Rabin-Karp | O(n + m) avg | O(1) | Multiple pattern search |
| Z-Algorithm | O(n + m) | O(n + m) | Pattern + prefix matching |
| Manacher | O(n) | O(n) | Longest palindrome |
| Suffix Array | O(n log n) | O(n) | Advanced string problems |

---

## ⚡ Pro Tips

1. **KMP is most important** - Frequently asked in interviews
2. **Sliding window** - Substring problems ke liye best
3. **Two pointers** - Palindrome problems ke liye
4. **Stack** - Decoding, parenthesis problems
5. **HashMap** - Anagrams, frequency problems
6. **Trie** - Prefix search, auto-complete
7. **Rolling hash** - Rabin-Karp for pattern matching
