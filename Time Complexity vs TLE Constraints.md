#  **Time Complexity vs TLE (Time Limit Exceeded) Constraints**

Competitive Programming mein, aapke code ki **Time Complexity** (samay ki jatilta) bahut important hoti hai. Agar aapka solution given Time Limit ke andar nahi chala, toh aapko **TLE** mil jayega, yaani **Time Limit Exceeded**. Isliye, yeh samajhna bahut zaroori hai ki kis input size ke liye kaun si time complexity acceptable hai.

### ⏱ **General Rule of Thumb (Mota-Mota Hisaab)**

Yeh ek general guide hai ki 1 second ke time limit mein aap kitne operations expect kar sakte hain:

| Time Limit | Input Size (N) | Acceptable Time Complexity |
| :--------- | :------------- | :------------------------- |
| 1 second | \~10⁸ operations | O(N), O(N log N), or O(N√N) for N ≈ 10⁵ |
| 1 second | N ≤ 10⁵ | O(N log N) or better |
| 1 second | N ≤ 10⁴ | O(N²) or better |
| 1 second | N ≤ 10³ | O(N³) or better |
| 1 second | N ≤ 100 | O(2^N) or O(N!) may be accepted |
| 2-5 seconds | Larger N | Slightly slower algorithms acceptable |

> **CPU can do \~10⁸ operations per second** competitive programming platforms jaise LeetCode, Codeforces, AtCoder, etc. par. Yaani, ek second mein aapka program lagbhag 10 crore operations perform kar sakta hai.

---

##  **Detailed Table of Time Complexities aur Input Constraints**

Is table mein aap har time complexity ke liye maximum input size `n` dekh sakte hain jise lagbhag 1 second ke andar process kiya ja sakta hai:

| Time Complexity | Max Input Size `n` to Avoid TLE (\~1s) |
| :-------------- | :------------------------------------- |
| O(1) | Any size (kitna bhi bada ho) |
| O(log N) | Up to 10¹⁸ (bohot bada number) |
| O(N) | Up to 10⁸ |
| O(N log N) | Up to 10⁶ |
| O(N√N) | Up to 10⁵ |
| O(N²) | Up to 10⁴ |
| O(N³) | Up to 500–1,000 |
| O(2^N) | Up to 20–25 |
| O(N!) | Up to 10–12 |

---

###  **Time Complexity vs Input Size vs Approximate Operations (for \~1 second)**

Is table mein har time complexity aur input size ke saath approximate operations ki sankhya di gayi hai, jo aapko 1 second ke time limit mein milni chahiye:

| Time Complexity | Max Input Size `n` to Avoid TLE (\~1s) | Approximate Operations |
| :-------------- | :------------------------------------- | :--------------------- |
| **O(1)** | Any size | 1 |
| **O(log N)** | Up to 10¹⁸ | \~60 |
| **O(N)** | Up to 10⁸ | 100,000,000 |
| **O(N log N)** | Up to 10⁶ | 10⁶ × log₂10⁶ ≈ 20×10⁶ |
| **O(N√N)** | Up to 10⁵ | 10⁵ × √10⁵ ≈ 3.1×10⁷ |
| **O(N²)** | Up to 10⁴ | 10⁴ × 10⁴ = 10⁸ |
| **O(N³)** | Up to 500–1000 | 1000³ = 10⁹ |
| **O(2^N)** | Up to 20–25 | 2²⁰ ≈ 10⁶, 2²⁵ ≈ 3×10⁷ |
| **O(N!)** | Up to 10–12 | 10! = 3.6×10⁶, 12! ≈ 4.8×10⁸ |

---

###  **Important Notes (Kuch Khaas Baatein):**

1.  **Constants Matter (Constants bhi important hain):** `O(N)` ka matlab yeh nahi ki har `N` operations ke liye exactly `N` operations honge. `5N` ya `10N` bhi `O(N)` hi hota hai. Real-world mein, ye constants aapke code ke performance ko affect kar sakte hain. Jaise, ek `O(N)` solution jismein bohot saare complex operations hain, woh `O(N log N)` solution se bhi slow ho sakta hai jismein kam complex operations hain.

2.  **Language and Compiler (Bhasha aur Compiler):** Java aur Python jaise languages C++ se thodi slower ho sakti hain. C++ generally 10⁸ operations per second achieve kar leta hai, jabki Python mein ye number 10⁷ ya usse bhi kam ho sakta hai. Isliye, agar aap Python use kar rahe hain, toh aapko thoda aur conservative rehna padega time complexity ke maamle mein.

3.  **Input/Output Operations (Input/Output Operations):** `Scanner/System.out.println` (Java) ya `input()/print()` (Python) jaise standard I/O operations bhi time consume karte hain. Large inputs ke liye, faster I/O methods (jaise Java mein `BufferedReader/PrintWriter`) use karna advisable hota hai.

4.  **Test Cases (Test Cases):** Competitive programming problems mein hidden test cases hote hain. Aapka solution sabse worst-case test case ke liye bhi time limit ke andar hona chahiye.

5.  **Memory Limit (Memory Limit):** Time complexity ke saath-saath, **Space Complexity** (jagah ki jatilta) bhi important hai. Agar aapka program bahut zyada memory use karta hai, toh aapko **MLE** (Memory Limit Exceeded) mil sakta hai.

6.  **"Slightly slower algorithms acceptable" (Thode Dheeme Algorithms bhi chal sakte hain):** Jab time limit 2-5 seconds hota hai, toh aap N³ ke liye N ≈ 1500 tak, ya N² ke liye N ≈ 2-3 \* 10⁴ tak consider kar sakte hain, depending on the constant factor.

7.  **Logarithmic Base (Logarithmic Base):** `log N` ka base typically 2 होता है (`log₂N`), khaaskar binary search ya divide and conquer algorithms mein.

Inn guidelines ko dhyan mein rakh kar, aap competitive programming mein Time Limit Exceeded se bach sakte hain aur apne solutions ko efficiently design kar sakte hain. Best of luck!
