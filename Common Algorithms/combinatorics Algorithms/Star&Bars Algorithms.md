**Stars and Bars** ek combinatorics technique hai jo use hoti hai **identical items** ko **distinct groups** me baantne ke problems solve karne ke liye.
Iska use mostly **non-negative integer solutions** dhoondhne ke liye hota hai kisi equation jaise:

> **x₁ + x₂ + x₃ + ... + xₖ = n**, jaha sabhi xᵢ ≥ 0

---

### 🔶 Intuition :

Agar tumhare paas `n` identical stars (items) hain aur tumhe unhe `k` boxes (groups) me baantna hai, toh tum **(k - 1)** bars (dividers) use karoge.
Total positions = `n` stars + `k - 1` bars = `n + k - 1`
Tumhe unme se `k - 1` bars choose karni hain (baaki jagah pe stars rahenge).

### 🔸 Formula:

> Number of solutions = **C(n + k - 1, k - 1)**
> Jaha:

* `n` = total stars (items)
* `k` = total boxes (groups)

---

### ✅ Example:

> Q: Find number of non-negative integer solutions of
> **x + y + z = 5**

Here, n = 5 (stars), k = 3 variables ⇒
Answer = C(5 + 3 - 1, 3 - 1) = C(7, 2) = **21**

---

### 🧑‍💻 Java Code Example:

```java
import java.util.*;

public class StarsAndBars {
    
    // Function to calculate nCr (combinations)
    public static long combination(int n, int r) {
        if (r > n - r)
            r = n - r; // Use symmetry C(n, r) = C(n, n-r)
        
        long result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
            result /= (i + 1);
        }
        return result;
    }

    // Stars and Bars Function
    public static long starsAndBars(int stars, int groups) {
        return combination(stars + groups - 1, groups - 1);
    }

    public static void main(String[] args) {
        int stars = 5;   // total items
        int groups = 3;  // number of variables/groups

        long ways = starsAndBars(stars, groups);
        System.out.println("Number of non-negative integer solutions: " + ways);
    }
}
```
Acha! Ab baat karte hain **Stars and Bars with Constraints** ki. Jab kisi equation ke variables ke **minimum value > 0 ya koi aur condition** hoti hai, toh basic formula thoda adjust hota hai.

---

### 🔶 Problem Form:

> Find the number of **positive** (x > 0) or **bounded** integer solutions of
> **x₁ + x₂ + ... + xₖ = n** where `xᵢ ≥ aᵢ` (or `xᵢ > 0`)

---

### ✅ Case 1: All variables **> 0** (Positive integers)

Agar question hai:

> Find number of **positive integer** solutions of
> **x + y + z = 5**

### 🔸 Trick:

Har variable ko 1-1 initially de do (taaki sab positive ho jaaye).
Toh total 3 variables hain ⇒ pehle se 1×3 = 3 consume ho gaye.
Now solve:

> x' + y' + z' = 5 - 3 = 2, where x', y', z' ≥ 0

Ye ab stars and bars ka **non-negative** wala problem hai.

### 🧮 Formula (for all xᵢ ≥ 1):

> Answer = C(n - 1, k - 1)

---

### ✅ Case 2: Variable has minimum value ≥ aᵢ

For equation:

> x + y + z = 20, where x ≥ 2, y ≥ 3, z ≥ 4

**Step 1:** Pehle sabse unki min value minus kar do:

Let

* x' = x - 2
* y' = y - 3
* z' = z - 4

So equation becomes:

> x' + y' + z' = 20 - (2 + 3 + 4) = 11, where x', y', z' ≥ 0

Ab ye simple stars and bars ho gaya:

> Answer = C(11 + 3 - 1, 3 - 1) = C(13, 2) = **78**

---

### 🧑‍💻 Java Code (Constraints Example):

```java
public class StarsAndBarsConstraints {

    public static long combination(int n, int r) {
        if (r > n - r) r = n - r;
        long result = 1;
        for (int i = 0; i < r; i++) {
            result *= (n - i);
            result /= (i + 1);
        }
        return result;
    }

    // Stars and Bars with Minimum Constraints
    public static long withMinConstraints(int totalSum, int[] mins) {
        int adjustedSum = totalSum;
        for (int min : mins) {
            adjustedSum -= min;
        }
        if (adjustedSum < 0) return 0;

        int groups = mins.length;
        return combination(adjustedSum + groups - 1, groups - 1);
    }

    public static void main(String[] args) {
        int totalSum = 20;
        int[] mins = {2, 3, 4}; // x ≥ 2, y ≥ 3, z ≥ 4

        long ways = withMinConstraints(totalSum, mins);
        System.out.println("Number of valid solutions: " + ways);
    }
}
```
