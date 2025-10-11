
# **KMP Algorithm Tutorial (Knuth-Morris-Pratt)**

## 1️⃣ **Problem KMP solves**

Suppose we have:

* A **text** `T` of length `n`
* A **pattern** `P` of length `m`

We want to **find all occurrences of pattern `P` in text `T` efficiently**.

**Naive approach:**

* For each position in `T`, check if `P` matches.
* Worst-case: O(n × m) → slow for large strings.

**KMP solution:**

* Time complexity → **O(n + m)**
* Idea → **don’t go back in the text when a mismatch happens**
* Use **information from the pattern itself** to skip unnecessary comparisons.

---

## 2️⃣ **Key Idea**

Suppose we are comparing pattern `P` with text `T`:

```
Text:    a b a b a b c a b a b a c
Pattern: a b a b a c
```

* While matching, if we find a mismatch at some position, we **don’t start matching pattern from scratch**.
* Instead, we use **previous matches in the pattern** to know **where to resume in the pattern**.

This is done using the **LPS array (Longest Prefix Suffix)**.

---

## 3️⃣ **LPS Array (Longest Prefix Suffix)**

**Definition:**

* `lps[i]` = length of the **longest proper prefix of P[0..i] which is also a suffix**

**Proper prefix:** All characters except the whole string.

**Example:**

Pattern: `"ABABCAB"`

| i | P[0..i] | lps[i] |
| - | ------- | ------ |
| 0 | A       | 0      |
| 1 | AB      | 0      |
| 2 | ABA     | 1      |
| 3 | ABAB    | 2      |
| 4 | ABABC   | 0      |
| 5 | ABABCA  | 1      |
| 6 | ABABCAB | 2      |

**Intuition:**

* If mismatch happens at position `i`, instead of going to `0`, we can resume from `lps[i-1]` in the pattern.
* This avoids redundant comparisons.

---

## 4️⃣ **Step 1: Build the LPS Array**

**Algorithm:**

```java
int[] computeLPS(String pattern) {
    int m = pattern.length();
    int[] lps = new int[m];
    int len = 0; // length of previous longest prefix suffix
    lps[0] = 0; // first character always 0

    int i = 1;
    while (i < m) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len - 1]; // fallback
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps;
}
```

**Step by step explanation:**

1. Start from `i=1` (0th always 0)
2. Compare `pattern[i]` with `pattern[len]`
3. If match → increment `len` and set `lps[i] = len`
4. If mismatch → fallback to previous longest prefix: `len = lps[len-1]`
5. If `len == 0` → set `lps[i] = 0`

✅ Complexity: O(m)

---

## 5️⃣ **Step 2: KMP Search**

Once LPS is ready, search in text:

```java
void KMPSearch(String text, String pattern) {
    int n = text.length();
    int m = pattern.length();
    int[] lps = computeLPS(pattern);

    int i = 0; // index for text
    int j = 0; // index for pattern

    while (i < n) {
        if (pattern.charAt(j) == text.charAt(i)) {
            i++;
            j++;
        }

        if (j == m) { // found a match
            System.out.println("Pattern found at index " + (i - j));
            j = lps[j - 1]; // continue searching
        } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
            if (j != 0) {
                j = lps[j - 1]; // fallback in pattern
            } else {
                i++;
            }
        }
    }
}
```

**Intuition:**

* `i` never decreases → we never go back in text
* `j` uses **lps** to skip unnecessary comparisons
* Efficient O(n + m) time

---

## 6️⃣ **Visualization**

```
Text:    A B A B A B C A B A B A C
Pattern: A B A B A C

Step 1: Match first 5 chars (ABABA) → mismatch at 6th char
Step 2: Use LPS: lps[5-1]=2 → resume pattern from index 2
Step 3: Compare text[5] with pattern[2] → continue
```

✅ You can see that we **skip rechecking first 2 characters** in pattern

---

## 7️⃣ **Time Complexity**

* LPS computation → O(m)
* Pattern search → O(n)
* Total → **O(n + m)**

Space: O(m) → LPS array

---

## 8️⃣ **Key Takeaways**

1. **LPS array** is the core of KMP → tells how many characters we can skip
2. Never go back in text → efficient
3. Use **fallback in pattern** using `lps[j-1]` when mismatch
4. Ideal for **pattern matching problems**, multiple occurrences, string search, etc.

---

### ✅ **Example Run**

```
Text:    A B A B A B C
Pattern: A B A B C

Compute LPS: [0,0,1,2,0]
Search:
i=0 j=0 → match
i=1 j=1 → match
i=2 j=2 → match
i=3 j=3 → match
i=4 j=4 → mismatch
Fallback j=lps[3]=2
Continue search
```

---

# **KMP Visualization with LPS**

**Example:**

```
Text:    A B A B A B C
Pattern: A B A B C
```

**Step 1: Compute LPS for Pattern**

Pattern: `A B A B C`

| Index | Char | LPS |
| ----- | ---- | --- |
| 0     | A    | 0   |
| 1     | B    | 0   |
| 2     | A    | 1   |
| 3     | B    | 2   |
| 4     | C    | 0   |

✅ LPS tells: “If mismatch happens at this index, resume comparison from LPS[index-1] in pattern.”

---

## **Step 2: Search in Text**

We align pattern with text and move along:

**Text:**  A B A B A B C
**Pattern:** A B A B C

```
i = text index, j = pattern index
```

---

### **Iteration 1:** Compare first 5 chars

```
Text:    A B A B A B C
Index:   0 1 2 3 4 5 6
Pattern: A B A B C
Index:   0 1 2 3 4
```

* i=0,j=0 → match (A==A)
* i=1,j=1 → match (B==B)
* i=2,j=2 → match (A==A)
* i=3,j=3 → match (B==B)
* i=4,j=4 → mismatch (A!=C)

---

### **Step 3: Use LPS to Skip**

* LPS[j-1] = LPS[3] = 2
* Instead of restarting pattern from index 0, **resume from pattern index 2**

```
Pattern index j = 2
Compare text[4] (A) with pattern[2] (A) → match
```

✅ We skipped comparing pattern[0] and pattern[1] again!

---

### **Step 4: Continue Matching**

```
Text:    A B A B A B C
Index:   0 1 2 3 4 5 6
Pattern:       A B C
Index:         2 3 4
```

* i=4,j=2 → match (A==A)
* i=5,j=3 → match (B==B)
* i=6,j=4 → match (C==C)

🎉 Pattern found at **text index = i-j = 6-4 = 2**

---

### **Visual Summary**

```
Step 1: Initial Alignment
Text:    A B A B A B C
Pattern: A B A B C

Mismatch at pattern[4], text[4]
Use LPS[3]=2 → resume pattern from index 2

Step 2: Skipped comparisons
Text:        A B A B C
Pattern:     A B C
```

✅ Key Point: **We never move back in the text**, we just adjust pattern using LPS → O(n+m) time.

---

### **Step 5: Takeaways from Visualization**

1. LPS tells **how many chars we can safely skip in pattern**.
2. Text index **never decreases** → efficiency.
3. Pattern index may move back using **LPS** but we skip redundant comparisons.
4. Works perfectly for **overlapping patterns**, like `"ABABAB"` etc.

---
