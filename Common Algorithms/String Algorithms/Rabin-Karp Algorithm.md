
# **Rabin-Karp Algorithm Tutorial**

## 1️⃣ **Problem it Solves**

We want to **find all occurrences of a pattern `P` in a text `T`** efficiently.

**Naive approach:**

* Compare pattern at every position → O(n × m)
* Slow for large strings

**Rabin-Karp Idea:**

* Use **hashing** to compare substrings instead of comparing character by character
* Hash of substring → **integer representing substring**
* Compare hashes first → if hashes match, then compare characters to avoid collisions

✅ This avoids unnecessary comparisons → faster in practice

---

## 2️⃣ **Intuition**

* Represent string as a **number in some base** `b` (like base 256 for ASCII)
* Hash function:

```
Hash(S) = S[0]*b^(m-1) + S[1]*b^(m-2) + ... + S[m-1]*b^0  (mod prime)
```

* Example:

```
S = "ABC", b = 256, prime = 101
Hash("ABC") = (65*256^2 + 66*256 + 67) % 101
```

* Now, instead of comparing substrings directly, compare **hashes**

---

## 3️⃣ **Rolling Hash (Key Part)**

* After computing hash of substring starting at 0 → hash of next substring can be **computed in O(1)**

```
Hash(T[i+1..i+m]) = (b*(Hash(T[i..i+m-1]) - T[i]*b^(m-1)) + T[i+m]) % prime
```

* We **subtract first char**, multiply by base, add new char
* Avoid recomputing entire substring hash → O(1) per substring

---

## 4️⃣ **Algorithm Steps**

1. Compute **hash of pattern** `hashP`
2. Compute **hash of first substring** in text `hashT`
3. Slide window over text (length m):

   * If `hashT == hashP` → possible match → compare characters to confirm
   * Else → compute next hash using **rolling hash** formula

---

## 5️⃣ **Java Implementation**

```java
public void rabinKarp(String text, String pattern) {
    int n = text.length();
    int m = pattern.length();
    int prime = 101; // modulo prime
    int base = 256;

    long hashP = 0, hashT = 0, h = 1;

    // The value of base^(m-1)
    for (int i = 0; i < m - 1; i++) h = (h * base) % prime;

    // Initial hash for pattern and first window
    for (int i = 0; i < m; i++) {
        hashP = (base * hashP + pattern.charAt(i)) % prime;
        hashT = (base * hashT + text.charAt(i)) % prime;
    }

    // Slide the pattern over text
    for (int i = 0; i <= n - m; i++) {
        // Check hash match
        if (hashP == hashT) {
            boolean match = true;
            for (int j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) System.out.println("Pattern found at index " + i);
        }

        // Compute hash of next window
        if (i < n - m) {
            hashT = (base * (hashT - text.charAt(i) * h) + text.charAt(i + m)) % prime;
            if (hashT < 0) hashT += prime; // ensure positive
        }
    }
}
```

---

## 6️⃣ **Example Run**

```
Text:    A B C D A B C
Pattern: A B C
```

* hashP = hash("ABC")
* hashT = hash("ABC") → match → compare → found at index 0
* slide window → hashT = hash("BCD") → mismatch → continue
* slide → hashT = hash("CDA") → mismatch
* slide → hashT = hash("DAB") → mismatch
* slide → hashT = hash("ABC") → match → compare → found at index 4

---

## 7️⃣ **Time Complexity**

| Case       | Complexity               |
| ---------- | ------------------------ |
| Average    | O(n + m)                 |
| Worst-case | O(n×m) (hash collisions) |

* Space: O(1) extra

✅ Using a good prime and base → collisions rare → almost O(n + m)

---

## 8️⃣ **Key Points**

1. **Hash first, compare later** → reduces comparisons
2. **Rolling hash** → compute new substring hash in O(1)
3. Works well for **pattern search**, plagiarism detection, multiple pattern search (with extensions)

---

# **Rabin-Karp Visualization (Rolling Hash)**

**Example:**

```
Text:    A B C D A B C
Pattern: A B C
Length of pattern m = 3
Base = 256, Prime = 101
```

---

## **Step 1: Compute Hash of Pattern**

```
Pattern: A B C
Char ASCII: 65 66 67
Hash(P) = (65*256^2 + 66*256 + 67) % 101 = 10  (example simplified)
```

---

## **Step 2: Compute Hash of First Window in Text**

```
Text window: A B C
ASCII: 65 66 67
Hash(T[0..2]) = same as pattern hash = 10
✅ Match! Compare chars → match found at index 0
```

---

## **Step 3: Slide Window Using Rolling Hash**

**Formula:**

```
Hash(next window) = (base*(hashT - oldChar*base^(m-1)) + newChar) % prime
```

---

### **Window 2:** B C D

```
Old hash: 10
Remove oldChar 'A' (65)
Add newChar 'D' (68)
Hash(T[1..3]) = (256*(10 - 65*256^2) + 68) % 101 = 57
❌ Hash mismatch → skip
```

---

### **Window 3:** C D A

```
Old hash: 57
Remove oldChar 'B' (66)
Add newChar 'A' (65)
Hash(T[2..4]) = ... = 33
❌ Hash mismatch → skip
```

---

### **Window 4:** D A B

```
Old hash: 33
Remove oldChar 'C' (67)
Add newChar 'B' (66)
Hash(T[3..5]) = ... = 91
❌ Hash mismatch → skip
```

---

### **Window 5:** A B C

```
Old hash: 91
Remove oldChar 'D' (68)
Add newChar 'C' (67)
Hash(T[4..6]) = 10
✅ Hash match → compare chars → match found at index 4
```

---

## **Step 4: Visualization Table**

| Window | Text Chars | HashT | Match? |
| ------ | ---------- | ----- | ------ |
| 0      | A B C      | 10    | ✅ 0    |
| 1      | B C D      | 57    | ❌      |
| 2      | C D A      | 33    | ❌      |
| 3      | D A B      | 91    | ❌      |
| 4      | A B C      | 10    | ✅ 4    |

---

## **Step 5: Key Takeaways from Visualization**

1. **Hash first, compare later** → avoids unnecessary char comparisons
2. **Rolling hash** → compute next hash in O(1)
3. Found pattern at **indices 0 and 4**

---

💡 **Extra Tip:**

* Always use a **large prime** and a **base >= alphabet size**
* Can use **double hashing** to avoid collisions

---
