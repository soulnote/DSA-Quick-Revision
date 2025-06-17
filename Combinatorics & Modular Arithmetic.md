# Combinatorics and Modular Arithmetic Tutorial for Coding Problems

This tutorial covers the essentials of **combinatorics** and **modular arithmetic**, two fundamental topics in competitive programming. It includes basics, key algorithms, and Java code examples with detailed explanations to help you solve coding problems efficiently.

---

## 1. Basics of Combinatorics

Combinatorics is the mathematics of counting and arranging objects. In coding, it’s often used to compute permutations, combinations, and other counting-related quantities.

### Key Concepts

- **Permutations**: The number of ways to arrange \( r \) items from a set of \( n \) distinct items.  
  Formula:  
  \[
  P(n, r) = \frac{n!}{(n - r)!}
  \]
  - **Example**: Arranging 2 letters from \{A, B, C\}: \( P(3, 2) = 6 \) (AB, BA, AC, CA, BC, CB).

- **Combinations**: The number of ways to choose \( r \) items from \( n \) without regard to order.  
  Formula:  
  \[
  C(n, r) = \binom{n}{r} = \frac{n!}{r! \cdot (n - r)!}
  \]
  - **Example**: Choosing 2 letters from \{A, B, C\}: \( C(3, 2) = 3 \) (AB, AC, BC).

- **Factorials**: \( n! = n \cdot (n-1) \cdot \ldots \cdot 1 \). Factorials grow rapidly, so we use modular arithmetic to manage large values in coding.

---

## 2. Basics of Modular Arithmetic

Modular arithmetic deals with remainders and is essential for handling large numbers in programming, often with a modulus like \( 10^9 + 7 \).

### Key Concepts

- **Modulo Operation**: \( a \mod m \) is the remainder of \( a \) divided by \( m \).  
  - **Example**: \( 7 \mod 3 = 1 \) (since \( 7 = 2 \cdot 3 + 1 \)).

- **Properties**:  
  - Addition: \( (a + b) \mod m = ((a \mod m) + (b \mod m)) \mod m \)  
  - Multiplication: \( (a \cdot b) \mod m = ((a \mod m) \cdot (b \mod m)) \mod m \)  
  - Exponentiation: \( (a^b) \mod m \) (computed efficiently with fast exponentiation).

- **Modular Inverse**: For a number \( b \) and modulus \( m \), the inverse \( x \) satisfies \( b \cdot x \equiv 1 \mod m \). It exists if \( \gcd(b, m) = 1 \).  
  - For a prime \( p \), use Fermat’s Little Theorem: \( x = b^{p-2} \mod p \).

---

## 3. Algorithms

In coding problems, we often need to compute \( C(n, r) \mod p \) efficiently. Here are two approaches:

### Algorithm 1: Dynamic Programming (Pascal’s Triangle)

- **Idea**: Use a DP table where \( dp[i][j] = C(i, j) \mod p \), based on:  
  \[
  dp[i][j] = (dp[i-1][j-1] + dp[i-1][j]) \mod p
  \]
  Base cases: \( dp[i][0] = 1 \), \( dp[i][i] = 1 \).  
- **Time Complexity**: \( O(n \cdot r) \).

### Algorithm 2: Factorials and Modular Inverses

- **Idea**: Precompute factorials (\( \text{fact}[i] \)) and their inverses (\( \text{invfact}[i] \)), then:  
  \[
  C(n, r) = \text{fact}[n] \cdot \text{invfact}[r] \cdot \text{invfact}[n - r] \mod p
  \]
- **Time Complexity**: \( O(n) \) preprocessing, \( O(1) \) per query.

### Fast Exponentiation

- **Idea**: Compute \( a^b \mod p \) in \( O(\log b) \) using binary exponentiation.

---

## 4. Java Code Examples

Here are implementations of the algorithms with detailed comments.

### 4.1 Fast Exponentiation

```java
public class ModularArithmetic {
    public static long powMod(long base, long exponent, long mod) {
        long result = 1;
        base = base % mod;
        while (exponent > 0) {
            if (exponent % 2 == 1) {  // If exponent is odd
                result = (result * base) % mod;
            }
            base = (base * base) % mod;  // Square the base
            exponent /= 2;  // Divide exponent by 2
        }
        return result;
    }

    public static void main(String[] args) {
        long base = 2, exponent = 10, mod = 1000000007;
        System.out.println(powMod(base, exponent, mod));  // Output: 1024
    }
}
```

### 4.2 Combinations with Dynamic Programming

```java
public class CombinatoricsDP {
    public static long binomialDP(int n, int k, long p) {
        long[][] dp = new long[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;  // C(i, 0) = 1
            if (i <= k) dp[i][i] = 1;  // C(i, i) = 1
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % p;
            }
        }
        return dp[n][k];
    }

    public static void main(String[] args) {
        int n = 5, k = 2;
        long p = 7;
        System.out.println(binomialDP(n, k, p));  // Output: 3 (10 % 7)
    }
}
```

### 4.3 Combinations with Factorials and Inverses

```java
public class CombinatoricsFactorial {
    public static void precompute(int n, long p, long[] fact, long[] invfact) {
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % p;
        }
        invfact[n] = powMod(fact[n], p - 2, p);  // Fermat's Little Theorem
        for (int i = n - 1; i >= 0; i--) {
            invfact[i] = (invfact[i + 1] * (i + 1)) % p;
        }
    }

    public static long binomial(int n, int k, long p, long[] fact, long[] invfact) {
        if (k > n) return 0;
        return (fact[n] * ((invfact[k] * invfact[n - k]) % p)) % p;
    }

    public static long powMod(long base, long exponent, long mod) {
        long result = 1;
        base = base % mod;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent /= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 5, k = 2;
        long p = 7;
        long[] fact = new long[n + 1];
        long[] invfact = new long[n + 1];
        precompute(n, p, fact, invfact);
        System.out.println(binomial(n, k, p, fact, invfact));  // Output: 3
    }
}
```

---

## 5. Tips for Coding Problems

- **Avoid Overflow**: Apply \( \mod p \) after each operation.  
- **Precompute**: Use factorials and inverses for multiple queries.  
- **Edge Cases**: Handle \( k > n \) or \( k = 0 \).  
- **Efficiency**: Use fast exponentiation for inverses and powers.  
- **Debugging**: Test with small inputs (e.g., \( C(5, 2) \mod 7 = 3 \)).

---

## 6. Practice Problems

1. Compute \( C(1000, 500) \mod 10^9 + 7 \).  
2. Find \( C(5, 3) \mod 7 \).  
3. Calculate the number of ways to arrange 5 items with restrictions, modulo 11.  
4. Compute grid paths from (0,0) to (4,4), modulo \( 10^9 + 7 \).

---

