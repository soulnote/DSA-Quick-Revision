
# 🚀 Range Updates with Difference Array and Prefix Sum

Handling **multiple range update queries** efficiently is a common challenge in competitive programming and system design. A powerful and elegant technique to tackle this is the **Difference Array** combined with **Prefix Sum**.

This article will teach you:

* The problem this method solves
* How the technique works (with intuition)
* Java code with full comments
* Why we do `+1` and `-1`

---

## 🎯 Problem

You're given:

* A number `N` representing points from `1` to `N`
* `Q` ranges, each of the form `[L, R]`

**Goal:** For each point from `1` to `N`, count how many ranges contain that point.

---

### ❌ Naive Approach: O(N × Q)

For each range `[L, R]`, loop through all points from `L` to `R` and increment a counter.

This works for small inputs but is too slow when `N` or `Q` is large (up to `10^7`).

---

## ✅ Efficient Approach: Difference Array + Prefix Sum

Instead of updating every value in `[L, R]`, we can **mark only the boundaries** of the update using a **difference array**, and then apply a **prefix sum** to compute final values.

---

## 🧠 Intuition: Why +1 and -1?

Let’s say we want to increment all values from index `L` to `R` by `+1`.

Instead of doing:

```java
for (int i = L; i <= R; i++) arr[i] += 1;
```

We do:

```java
diff[L] += 1;     // Start adding from L
diff[R + 1] -= 1; // Stop adding after R
```

Then, we apply a prefix sum to the `diff` array. This makes sure:

* Every index from `L` to `R` gets +1
* Every index after `R` stops getting affected

It’s like saying:

> “Start the +1 effect at index `L`, and cancel it right after `R`.”

---

## 🧪 Example

Given `N = 5`, and the ranges:

* \[1, 3]
* \[4, 5]
* \[3, 4]
* \[1, 5]

Let’s compute how many ranges each point is covered by.

---

## 💻 Full Java Code with Comments

```java
public class RangeFrequencyCounter {
    public static void main(String[] args) {
        // Number of elements
        int N = 5;

        // Array of ranges [L, R]
        int[][] ranges = {
            {1, 3},
            {4, 5},
            {3, 4},
            {1, 5}
        };

        // Step 1: Create difference array of size N + 2
        // Why N+2? Because we might need to access index R+1
        int[] diff = new int[N + 2];

        // Step 2: Apply +1 at L and -1 at R+1 for each range
        for (int[] range : ranges) {
            int L = range[0];
            int R = range[1];

            // Start the increment at index L
            diff[L] += 1;

            // Stop the increment after index R
            diff[R + 1] -= 1;
        }

        // Step 3: Build the final result using prefix sum
        int[] result = new int[N + 1]; // We will use indices 1 to N

        for (int i = 1; i <= N; i++) {
            // Add previous value to get current count
            result[i] = result[i - 1] + diff[i];
        }

        // Step 4: Print result
        System.out.println("Points covered by ranges:");
        for (int i = 1; i <= N; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
```

---

## ✅ Output

```
Points covered by ranges:
2 2 3 2 2
```

Meaning:

* Point 1 is in 2 ranges
* Point 2 is in 2 ranges
* Point 3 is in 3 ranges
* Point 4 is in 2 ranges
* Point 5 is in 2 ranges

---

## 🧠 Key Takeaways

* Use **difference arrays** when you need to **perform multiple range updates efficiently**.
* Combine with **prefix sums** to get the final values.
* Reduces time complexity from **O(Q × N)** to **O(Q + N)**.
* The trick lies in marking **+1 at L** and **-1 at R + 1** to simulate a range increment in constant time.

---
