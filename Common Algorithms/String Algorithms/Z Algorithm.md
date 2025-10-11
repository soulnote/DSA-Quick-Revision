
# **Z-Algorithm Tutorial**

## 1️⃣ **Problem it Solves**

* Given a **string `S`** (length n)
* We want to **find all occurrences of a pattern in a text** OR **compute repeated substrings efficiently**.

**Z-array** helps in **pattern matching in O(n)**.

---

## 2️⃣ **Z-Array Definition**

For string `S` of length `n`:

* `Z[i]` = **length of the longest substring starting at `i` which is also a prefix of `S`**

**Example:**

```
S = "aabxaabxcaabxaabx"
```

* Prefix: "aabx…"
* Z-array:

| i | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 |
| - | - | - | - | - | - | - | - | - | - | - | -- | -- | -- | -- | -- | -- |
| Z | 0 | 1 | 0 | 0 | 4 | 1 | 0 | 0 | 3 | 1 | 0  | 0  | 4  | 1  | 0  | 0  |

✅ Z[4]=4 → substring starting at index 4 → "aabx…" matches first 4 characters of prefix.

---

## 3️⃣ **How Z-Algorithm Works (Intuition)**

* Maintain a **window [L, R]** → rightmost segment that matches the prefix exactly.

* For each position `i`:

  1. **If i > R** → no current match with prefix:

     * Start matching manually
     * Update `[L, R]`

  2. **If i ≤ R** → i is inside current Z-box:

     * Let `k = i - L` (relative index in Z-box)
     * Two cases:

       * `Z[k] < R-i+1` → copy `Z[i] = Z[k]`
       * `Z[k] ≥ R-i+1` → try to extend the match beyond R manually

* **Main Idea:** Use **already computed Z-values** to skip redundant comparisons.

---

## 4️⃣ **Step-by-Step Example**

```
S = "aabxaabx"
```

1. Start Z[0] = 0 (by definition)
2. i=1 → match prefix starting from 1: "a" matches "a" → Z[1]=1
3. i=2 → no match with prefix → Z[2]=0
4. i=4 → substring "aabx…" matches prefix → Z[4]=4

* Window `[L,R]` moves forward as we find matches.
* Redundant comparisons avoided using `Z[k]`.

---

## 5️⃣ **Z-Algorithm Code (Java)**

```java
public int[] computeZ(String s) {
    int n = s.length();
    int[] Z = new int[n];
    Z[0] = 0; // by definition
    int L = 0, R = 0;

    for (int i = 1; i < n; i++) {
        if (i > R) {
            // i outside current Z-box
            L = R = i;
            while (R < n && s.charAt(R - L) == s.charAt(R)) R++;
            Z[i] = R - L;
            R--;
        } else {
            // i inside Z-box
            int k = i - L;
            if (Z[k] < R - i + 1) {
                Z[i] = Z[k];
            } else {
                L = i;
                while (R < n && s.charAt(R - L) == s.charAt(R)) R++;
                Z[i] = R - L;
                R--;
            }
        }
    }

    return Z;
}
```

---

## 6️⃣ **Pattern Matching using Z-Algorithm**

**Goal:** Find all occurrences of `pattern` in `text`.

1. Concatenate: `concat = pattern + "$" + text`
2. Compute Z-array of `concat`
3. Whenever `Z[i] == pattern.length()`, we found a match at `i - pattern.length() - 1` in text

```java
String concat = pattern + "$" + text;
int[] Z = computeZ(concat);
for (int i = 0; i < Z.length; i++) {
    if (Z[i] == pattern.length()) {
        System.out.println("Pattern found at index: " + (i - pattern.length() - 1));
    }
}
```

✅ Efficient and simple: O(n + m)

---

## 7️⃣ **Visualization**

```
Text:    a b a b a a b a b x
Pattern: a b a b x

Concat:  a b a b x $ a b a b a a b a b x
Index:   0 1 2 3 4 5 6 7 8 9 10 11 ...
Z[i]     0 0 2 0 0 ...
```

* Z[i] tells **how many characters from i match prefix**
* When Z[i] = pattern.length → match found

---

## 8️⃣ **Time Complexity**

* O(n) → linear
* Space: O(n) for Z-array
* Works **better than naive O(n×m)** for pattern search

---

## 9️⃣ **Key Takeaways**

1. Z[i] = longest prefix starting at i
2. Maintain Z-box [L, R] → avoid redundant checks
3. Efficient for **pattern search, substring counting, repeats**
4. Concatenate pattern + "$" + text → find matches using Z-array

---

