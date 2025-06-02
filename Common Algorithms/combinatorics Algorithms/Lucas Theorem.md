# 📘 **Lucas Theorem – Concept **

Lucas Theorem un cases ke liye useful hai **jab `n` aur `r` bahut bade ho jaate hain (like `10^18`)**, aur aapko `nCr % p` nikalna hota hai **jab `p` ek prime hota hai**.

### 🔁 Formula:

Agar `n` aur `r` bade hain aur `p` ek prime hai:

```
nCr(n, r) % p = nCr(n % p, r % p) * nCr(n / p, r / p) % p
```

Isko hum **recursive** tarike se solve karte hain, jab tak `n` aur `r` chhote ban jaayein (less than `p`), jahan pe normal `nCr` formula laga sakte hain.

---

## 📌 Why use Lucas Theorem?

Normal method se factorial banane ke liye `O(n)` time lagta hai. Agar `n` = 10^18 ho, to yeh practically impossible hai.

Lucas theorem ke through hum **base `p` me break karke chhoti chhoti values par `nCr` nikalte hain**.

---

## ✅ Example Leetcode-style Problem:

### ❓**Problem**:

You are given `n`, `r`, and `p` where `n`, `r` ≤ 10^18 and `p` is a small prime (≤ 10^5).
Return **nCr % p**.

> Constraints:
>
> * `1 ≤ p ≤ 100000` (prime)
> * `0 ≤ r ≤ n ≤ 10^18`

This is typically found on advanced platforms or coding rounds, but here’s a simulation of such a question.

---

## ✅ Java Code Using Lucas Theorem:

```java
import java.util.*;

public class LucasTheoremNCR {
    static int p;  // Modulo prime
    static long[] fact;

    // Modular exponentiation
    static long power(long a, long b) {
        long res = 1;
        a = a % p;
        while (b > 0) {
            if ((b & 1) == 1)
                res = (res * a) % p;
            a = (a * a) % p;
            b >>= 1;
        }
        return res;
    }

    // Modular inverse using Fermat's Little Theorem
    static long modInverse(long a) {
        return power(a, p - 2);
    }

    // Precompute factorial mod p
    static void computeFactorials() {
        fact = new long[p];
        fact[0] = 1;
        for (int i = 1; i < p; i++) {
            fact[i] = (fact[i - 1] * i) % p;
        }
    }

    // Compute nCr % p where n, r < p
    static long nCrModP(long n, long r) {
        if (r > n) return 0;
        long numerator = fact[(int) n];
        long denominator = (fact[(int) r] * fact[(int) (n - r)]) % p;
        return (numerator * modInverse(denominator)) % p;
    }

    // Lucas Theorem recursive function
    static long lucas(long n, long r) {
        if (r == 0) return 1;
        long ni = n % p;
        long ri = r % p;
        return (lucas(n / p, r / p) * nCrModP(ni, ri)) % p;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        long n = sc.nextLong();
        System.out.print("Enter r: ");
        long r = sc.nextLong();
        System.out.print("Enter prime p: ");
        p = sc.nextInt();

        computeFactorials(); // Factorials modulo p
        long result = lucas(n, r); // Lucas Theorem recursion
        System.out.println("nCr % p = " + result);
    }
}
```

---

## ✅ Example Input/Output:

```
Input:
n = 1000000000000
r = 1000
p = 10007

Output:
nCr % p = 4832
```

---

## 🔍 Summary:

* Lucas Theorem helps compute `nCr % p` when `n` is huge.
* Factorials are computed modulo `p` only up to size `p`, so memory efficient.
* Recursive breakdown converts huge values into manageable subproblems.

