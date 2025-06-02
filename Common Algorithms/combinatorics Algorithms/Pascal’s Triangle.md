# 🔺 Pascal’s Triangle

### 📘 Kya hai Pascal’s Triangle?

Pascal's Triangle ek triangular array hota hai jisme **binomial coefficients (nCr)** ko directly generate kiya ja sakta hai, bina factorials ke.

---

### 📐 Structure:

```
Row 0:          1
Row 1:        1   1
Row 2:      1   2   1
Row 3:    1   3   3   1
Row 4:  1   4   6   4   1
          ...
```

* Har element:

  ```
  pascal[n][r] = pascal[n-1][r-1] + pascal[n-1][r]
  ```
* Edge elements (`r = 0` or `r = n`) hamesha 1 hote hain.

---

### 📌 Why use Pascal's Triangle?

* Efficiently calculate `nCr` without factorials.
* Useful when:

  * Multiple queries of `nCr` are needed.
  * Values of `n` are relatively small (e.g. `n ≤ 1000`).

---

### ✅ Example Problem:

**"Print nCr for multiple values using Pascal’s Triangle up to n = 1000"**

---

### ✅ Java Code Using Pascal's Triangle

```java
public class PascalTriangle {
    static final int N = 1001;
    static final int MOD = 1000000007;
    static long[][] pascal = new long[N][N];

    // Build Pascal’s Triangle up to N
    static void buildPascal() {
        for (int i = 0; i < N; i++) {
            pascal[i][0] = 1;  // nC0 = 1
            for (int j = 1; j <= i; j++) {
                pascal[i][j] = (pascal[i - 1][j - 1] + pascal[i - 1][j]) % MOD;
            }
        }
    }

    // Get nCr using Pascal’s Triangle
    static long getNCR(int n, int r) {
        if (r < 0 || r > n) return 0;
        return pascal[n][r];
    }

    public static void main(String[] args) {
        buildPascal();

        int n = 5, r = 2;
        System.out.println("nCr of " + n + " and " + r + " is: " + getNCR(n, r));  // Output: 10

        n = 10; r = 3;
        System.out.println("nCr of " + n + " and " + r + " is: " + getNCR(n, r));  // Output: 120
    }
}
```

---

### 🧠 Use-cases:

* Competitive programming (small `n`, many queries)
* Dynamic programming transitions (like in subset or partition problems)
* When modulus involved (compute once, reuse many times)

