# **Polynomial Hash / Rolling Hash**

## 1️⃣ **Problem it Solves**

* Compare **substrings efficiently**
* Detect **pattern occurrences, duplicates, palindromes**
* Useful in **Rabin-Karp algorithm**

Naive substring comparison → O(n×m)
Polynomial hash → O(1) per substring (after preprocessing)

---

## 2️⃣ **Intuition**

Treat a string as a **polynomial** in some **base `B`**, modulo a **prime `P`** to avoid overflow.

```
String S = s0 s1 s2 ... sm-1
Hash(S) = s0*B^(m-1) + s1*B^(m-2) + ... + sm-1*B^0 (mod P)
```

* `s0, s1, ...` → numeric value of characters
* `B` → base (usually ≥ alphabet size, e.g., 31 or 256)
* `P` → large prime

---

### **Example**

```
S = "abc"
ASCII: a=1, b=2, c=3 (or 97, 98, 99)
B = 31, P = 101
Hash("abc") = (1*31^2 + 2*31 + 3) % 101
```

✅ Unique numeric representation of string

---

## 3️⃣ **Rolling Hash**

Instead of recomputing hash of every substring, use **previous hash to compute next**:

If substring S[i..i+m-1] has hash `hash_old`, then

```
hash_new = (B*(hash_old - S[i]*B^(m-1)) + S[i+m]) % P
```

* Remove first char contribution
* Multiply by base B
* Add new char
* Mod P to avoid overflow

---

## 4️⃣ **Prefix Hash Optimization**

Sometimes we want **hash of any substring in O(1)**.

1. Precompute **prefix hashes**:

```
prefix[i] = hash(S[0..i]) = (prefix[i-1]*B + S[i]) % P
```

2. Hash of substring S[l..r]:

```
Hash(S[l..r]) = (prefix[r] - prefix[l-1]*B^(r-l+1)) % P
```

* Precompute `B^i % P` → fast power
* Now **any substring hash = O(1)**

---

## 5️⃣ **Java Example (Single Rolling Hash)**

```java
class RollingHash {
    long[] prefix;
    long[] power;
    int base = 31;
    int mod = 1000000007;
    
    public RollingHash(String s) {
        int n = s.length();
        prefix = new long[n];
        power = new long[n];
        
        power[0] = 1;
        prefix[0] = s.charAt(0) - 'a' + 1;
        
        for (int i = 1; i < n; i++) {
            power[i] = (power[i - 1] * base) % mod;
            prefix[i] = (prefix[i - 1] * base + (s.charAt(i) - 'a' + 1)) % mod;
        }
    }
    
    public long getHash(int l, int r) {
        long result = prefix[r];
        if (l > 0) {
            result = (result - prefix[l - 1] * power[r - l + 1]) % mod;
        }
        if (result < 0) result += mod;
        return result;
    }
}
```

✅ After preprocessing, any substring hash = **O(1)**

---

## 6️⃣ **Example**

```
S = "abcd"
Substring "bc" → l=1, r=2
Hash("bc") = getHash(1,2)
```

* Compute once → compare with other substring hashes
* Fast duplicate check, pattern search

---

## 7️⃣ **Double Hashing (Optional but Safer)**

* Single mod → collision possible
* Use **two different primes**:

```
Hash1(S) = mod1, Hash2(S) = mod2
Compare (Hash1, Hash2) → very low collision probability
```

---

## 8️⃣ **Applications**

1. **Rabin-Karp Algorithm** → pattern search
2. **Substring duplicates** → detect repeated substrings
3. **Palindrome detection** → hash of substring = hash of reverse
4. **String comparison in O(1)** after preprocessing
5. **Rolling hash + binary search** → find **longest duplicate substring**

---

## ✅ Key Takeaways

* Represent string as **polynomial in base B**
* **Modulo a large prime** to prevent overflow
* **Rolling hash** allows fast substring comparison
* Precompute prefix hash → substring hash in O(1)
* Double hashing → safe from collisions

------

# **Polynomial Hash / Rolling Hash Visualization**

**Example String:**

```
S = "abcd"
Base B = 31
Prime P = 101
Char mapping: a=1, b=2, c=3, d=4
```

---

## **Step 1: Compute Polynomial Hash of Entire String**

Formula:

```
Hash(S) = S[0]*B^(n-1) + S[1]*B^(n-2) + ... + S[n-1]*B^0 (mod P)
```

```
S = a b c d
Hash("abcd") = 1*31^3 + 2*31^2 + 3*31^1 + 4*31^0
```

```
= 1*29791 + 2*961 + 3*31 + 4
= 29791 + 1922 + 93 + 4
= 31810
Hash mod 101 = 31810 % 101 = 20
```

✅ Hash of "abcd" = 20

---

## **Step 2: Rolling Hash for Next Substring**

We want **substring "bcd"**, which starts at index 1

**Old substring:** "abc" → hash_old = 1*31^2 + 2*31 + 3 = 1026

**Remove first char contribution (a=1)**:

```
hash_new = (hash_old - S[i]*B^(m-1))*B + newChar
hash_new = (1026 - 1*31^2)*31 + 4
hash_new = (1026 - 961)*31 + 4
hash_new = 65*31 + 4
hash_new = 2019
hash_new % 101 = 2019 % 101 = 20
```

✅ Hash of "bcd" = 20 → fast computation without recomputing entire substring

---

## **Step 3: Prefix Hash Array (O(1) substring hash)**

```
prefix[0] = S[0] = 1
prefix[1] = prefix[0]*B + S[1] = 1*31 + 2 = 33
prefix[2] = prefix[1]*B + S[2] = 33*31 + 3 = 1026
prefix[3] = prefix[2]*B + S[3] = 1026*31 + 4 = 31810
```

**Substring hash from index 1 to 3 ("bcd")**:

```
Hash(1,3) = prefix[3] - prefix[0]*B^(3) = 31810 - 1*31^3 = 31810 - 29791 = 2019
2019 % 101 = 20 ✅
```

---

## **Step 4: Visualization Table**

| Substring | Prefix Hash | Remove first char? | Rolling Hash | Mod 101 |
| --------- | ----------- | ------------------ | ------------ | ------- |
| abc       | 1026        | -                  | -            | 16      |
| bcd       | 31810       | remove 'a'         | 2019         | 20      |

* Rolling hash **avoids full recomputation**
* Fast comparison of substrings → O(1) per substring

---

## **Step 5: Key Takeaways**

1. Treat string as **polynomial in base B**
2. Use **mod prime** to avoid overflow
3. **Rolling hash:** slide window → O(1) hash update
4. **Prefix hash array:** hash of any substring in O(1)
5. Can use **double hashing** to avoid collisions

---

💡 **Extra Tip:**

* Base B = 31 or 256
* Prime P = 10^9+7 or 10^9+9 for large strings

---

