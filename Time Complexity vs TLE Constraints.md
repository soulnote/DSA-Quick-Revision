
## ✅ **Time Complexity vs TLE Constraints**

### ⏱ General Rule of Thumb

| Time Limit  | Input Size (N)   | Acceptable Time Complexity              |
| ----------- | ---------------- | --------------------------------------- |
| 1 second    | \~10⁸ operations | O(N), O(N log N), or O(N√N) for N ≈ 10⁵ |
| 1 second    | N ≤ 10⁵          | O(N log N) or better                    |
| 1 second    | N ≤ 10⁴          | O(N²) or better                         |
| 1 second    | N ≤ 10³          | O(N³) or better                         |
| 1 second    | N ≤ 100          | O(2^N) or O(N!) may be accepted         |
| 2-5 seconds | Larger N         | Slightly slower algorithms acceptable   |

> **CPU can do \~10⁸ operations per second** in CP platforms like LeetCode, Codeforces, AtCoder, etc.

---

## 📊 **Detailed Table of Time Complexities and Input Constraints**

| Time Complexity | Max Input Size `n` to Avoid TLE (\~1s) |
| --------------- | -------------------------------------- |
| O(1)            | Any size                               |
| O(log N)        | Up to 10¹⁸                             |
| O(N)            | Up to 10⁸                              |
| O(N log N)      | Up to 10⁶                              |
| O(N√N)          | Up to 10⁵                              |
| O(N²)           | Up to 10⁴                              |
| O(N³)           | Up to 500–1,000                        |
| O(2^N)          | Up to 20–25                            |
| O(N!)           | Up to 10–12                            |

---

Here’s the complete table with **actual operation count values** added for each time complexity and input size:

---

### ✅ Time Complexity vs Input Size vs Approximate Operations (for \~1 second)

| Time Complexity | Max Input Size `n` to Avoid TLE (\~1s) | Approximate Operations       |
| --------------- | -------------------------------------- | ---------------------------- |
| **O(1)**        | Any size                               | 1                            |
| **O(log N)**    | Up to 10¹⁸                             | \~60                         |
| **O(N)**        | Up to 10⁸                              | 100,000,000                  |
| **O(N log N)**  | Up to 10⁶                              | 10⁶ × log₂10⁶ ≈ 20×10⁶       |
| **O(N√N)**      | Up to 10⁵                              | 10⁵ × √10⁵ ≈ 3.1×10⁷         |
| **O(N²)**       | Up to 10⁴                              | 10⁴ × 10⁴ = 10⁸              |
| **O(N³)**       | Up to 500–1000                         | 1000³ = 10⁹                  |
| **O(2^N)**      | Up to 20–25                            | 2²⁰ ≈ 10⁶, 2²⁵ ≈ 3×10⁷       |
| **O(N!)**       | Up to 10–12                            | 10! = 3.6×10⁶, 12! ≈ 4.8×10⁸ |

---

### 📌 Note:

* Most **CP platforms allow up to \~10⁸ operations/second**.
