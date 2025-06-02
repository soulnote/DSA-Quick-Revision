## 🧮 **Catalan Numbers**

### 📘 Kya hai Catalan Numbers?

Catalan Numbers ek special sequence hai jo **combinatorics** me kaafi use hoti hai. Ye un problems me aati hai jahan **structure formation** ho raha ho – jaise:

---

### 📌 Catalan Number Problems:

* Valid parentheses combinations
* Number of Binary Search Trees (BSTs)
* Matrix chain multiplication (triangulations)
* Ways to divide polygon into triangles
* Dyck words, mountain ranges, etc.

---

### 📐 Formula (Recursive & Closed-form):

#### 1. **Recursive Formula**:

```
C₀ = 1
Cₙ = C₀*Cₙ₋₁ + C₁*Cₙ₋₂ + ... + Cₙ₋₁*C₀   (for n ≥ 1)
```

#### 2. **Closed-Form using Binomial Coefficient**:

```
Catalan(n) = C(2n, n) / (n + 1)
           = (2n)! / (n! * (n+1)!)
```

#### 3. **Modulo Version (with Fermat)**:

When modulus `p` is prime:

```
Catalan(n) = C(2n, n) * modInverse(n+1) % p
```

---

## ✅ Java Code: Catalan Numbers using DP and Binomial Coefficient

### 🔹 Method 1: DP Based (for small `n`)

```java
public class CatalanDP {
    static int MOD = 1000000007;

    static long[] catalanDP(int n) {
        long[] cat = new long[n + 1];
        cat[0] = 1;

        for (int i = 1; i <= n; i++) {
            cat[i] = 0;
            for (int j = 0; j < i; j++) {
                cat[i] = (cat[i] + cat[j] * cat[i - j - 1]) % MOD;
            }
        }
        return cat;
    }

    public static void main(String[] args) {
        int n = 10;
        long[] cat = catalanDP(n);
        for (int i = 0; i <= n; i++) {
            System.out.println("Catalan[" + i + "] = " + cat[i]);
        }
    }
}
```

---

### 🔹 Method 2: Binomial Coefficient with Fermat's Theorem

```java
public class CatalanBinomial {
    static int MOD = 1000000007;
    static long[] fact;

    static long power(long a, long b) {
        long res = 1;
        a %= MOD;
        while (b > 0) {
            if ((b & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    static long modInverse(long a) {
        return power(a, MOD - 2); // Fermat's Little Theorem
    }

    static void computeFactorials(int n) {
        fact = new long[2 * n + 2];
        fact[0] = 1;
        for (int i = 1; i <= 2 * n + 1; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
    }

    static long catalan(int n) {
        computeFactorials(n);
        long numerator = fact[2 * n];
        long denominator = (fact[n] * fact[n + 1]) % MOD;
        return (numerator * modInverse(denominator)) % MOD;
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println("Catalan[" + n + "] = " + catalan(n)); // Output: 42
    }
}
```

---

## ✅ Real Examples:

| n | Catalan(n) | Meaning                                      |
| - | ---------- | -------------------------------------------- |
| 0 | 1          | Empty structure                              |
| 1 | 1          | ()                                           |
| 2 | 2          | (()), ()()                                   |
| 3 | 5          | ((())), (()()), (())(), ()(()), ()()()       |
| 4 | 14         | BSTs, triangulations, valid parentheses etc. |

---

## 📝 Problem Statement: **Number of Valid Parentheses Combinations**

**Given an integer `n`, return the number of valid parentheses strings consisting of `n` pairs of parentheses.**

### Example:

* Input: `n = 3`
* Output: `5`

**Explanation:**
The 5 valid parentheses strings are:

```
((())), (()()), (())(), ()(()), ()()()
```

---

## 💡 Why Catalan Numbers?

The number of valid parentheses strings with `n` pairs is the `n`th **Catalan number**.

---

## ✅ Java Code Using Catalan Number (Binomial Formula + Fermat's Little Theorem)

```java
public class ValidParenthesesCount {

    static int MOD = 1000000007;
    static long[] fact;

    // Fast modular exponentiation
    static long power(long a, long b) {
        long res = 1;
        a %= MOD;
        while (b > 0) {
            if ((b & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    // Modular inverse using Fermat's Little Theorem
    static long modInverse(long a) {
        return power(a, MOD - 2);
    }

    // Precompute factorials up to 2n
    static void computeFactorials(int n) {
        fact = new long[2 * n + 1];
        fact[0] = 1;
        for (int i = 1; i <= 2 * n; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
    }

    // Calculate nth Catalan number using binomial formula
    static long catalan(int n) {
        computeFactorials(n);
        long numerator = fact[2 * n];
        long denominator = (fact[n] * fact[n + 1]) % MOD;
        return (numerator * modInverse(denominator)) % MOD;
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println("Number of valid parentheses with " + n + " pairs = " + catalan(n));
    }
}
```

---

## Output:

```
Number of valid parentheses with 3 pairs = 5
```

