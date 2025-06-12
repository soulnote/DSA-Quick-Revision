

# Digit DP Tutorial

Hello dosto! Aaj hum **Digit DP** pattern ke baare mein detail mein baat karenge. Ye pattern tab use hota hai jab humein numbers ko digit-by-digit process karna ho, aur koi specific property (jaise digit sum, XOR, ya increasing order) ke basis pe count karna ho. Is tutorial mein hum saare example problems (Count of numbers with given sum, Count of numbers with at most K digits having a digit sum = X, Count of numbers ≤ N with digit XOR = X, Numbers with at most 3 non-zero digits, Count numbers with digits in increasing order) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with detailed comments at important points. Chalo shuru karte hain!

---

## Digit DP Kya Hai?
Digit DP ek dynamic programming technique hai jisme hum numbers ko digit-by-digit process karte hain, aur har digit ke liye states maintain karte hain jo koi property track karti hain (jaise sum, XOR, ya non-zero digits). Ye pattern aksar counting problems mein use hota hai, jaha humein ek range ya constraint ke under numbers count karne hote hain.

**Examples:**
1. Count of numbers with given sum
2. Count of numbers with at most K digits having a digit sum = X
3. Count of numbers ≤ N with digit XOR = X
4. Numbers with at most 3 non-zero digits
5. Count numbers with digits in increasing order

**Common Theme:**
- Ek recursive function use hota hai jo digit-by-digit number banata hai.
- States mein typically include: current position (pos), tight flag (number chhota ya equal hai), aur property-specific state (jaise sum, XOR).
- Top-down (memoization) zyada common hai kyunki states complex hote hain.

---

## Intuition Kaise Build Kare?
Digit DP problems ko samajhne ke liye ye socho:
- Tum ek number bana rahe ho, ek digit at a time, starting from leftmost digit.
- Har digit ke liye decide karo: kaunsi digit (0-9) daal sakte ho, based on constraints (jaise tight flag ya sum).
- Tumhe count karna hai kitne numbers valid conditions satisfy karte hain.

For example:
- **Count of numbers with given sum** mein har digit ke sum ko track karo aur check karo ki total sum target ke barabar hai.
- **Numbers with at most 3 non-zero digits** mein non-zero digits ka count rakho aur ensure karo ki ye 3 se zyada na ho.

**Key Intuition:**
- State define karo jo current digit position, tight condition, aur property (sum, XOR, etc.) track kare.
- Transition formula socho: har possible digit (0-9) try karo, aur next state update karo.
- Base cases set karo jo number complete hone ya invalid state ko handle kare.

---

## General Approach
Digit DP problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[pos][tight][property]` kya represent karta hai? For example, `dp[pos][tight][sum]` = count of valid numbers starting from pos with given sum.
2. **Transition Formula Likho:**
   - Current state se next state kaise jaye? For example, har digit d (0-9) ke liye: if tight, d ≤ max_digit, else d ≤ 9.
3. **Base Cases Set Karo:**
   - Jab pos == digits.length, check if property satisfied (jaise sum == target).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization (common for Digit DP)
   - Bottom-Up: Iterative (rare, due to complex states)

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Digit DP mein zyadatar top-down use hota hai kyunki states complex hote hain (pos, tight, sum, etc.).
  - Recursive structure natural lagta hai digit-by-digit processing ke liye.
- **Pros:**
  - Code intuitive hota hai, digit selection ka logic clear rahta hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe (rare in Digit DP).
  - Memoization array bada ho sakta hai.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab states predictable aur manageable hon, lekin Digit DP mein ye rare hai.
  - Iteration se complex state transitions handle karna mushkil hota hai.
- **Pros:**
  - No recursion, no stack overflow.
  - Memory optimized ho sakta hai in some cases.
- **Cons:**
  - Code complex ho jata hai, especially tight flag aur property tracking ke liye.
  - Unnecessary states bhi process ho sakte hain.

**Recommendation for Digit DP:**
- **Top-Down** almost always better hai kyunki Digit DP ka recursive nature memoization ke sath fit hota hai. Bottom-Up possible hai lekin impractical zyadatar cases mein.

---

## Problem 1: Count of Numbers with Given Sum
### Problem Statement:
Ek range [1, N] di hai. Kitne numbers ka digit sum S ke barabar hai?

**Example:**
```
Input: N = "20", S = 2
Output: 2
Explanation: Numbers 2 and 11 have digit sum = 2.
```

### Intuition:
- Har digit position pe 0-9 try karo, aur sum track karo.
- State: `dp[pos][tight][sum]` = count of numbers from pos with given sum.
- Transition: For each digit d, update sum += d, aur tight flag check karo.
- Base cases: If pos == digits.length, check if sum == S.

### Tree Diagram:
```
pos=0, tight=1, sum=0 (N=20)
 / | \
d=0 d=1 d=2
sum=0 sum=1 sum=2
tight=0 tight=0 tight=1
```

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public long countNumbersWithSum(String N, int S) {
        int n = N.length();
        memo = new Long[n][2][S+1];
        // Convert N to char array for digit access
        char[] digits = N.toCharArray();
        return countHelper(digits, 0, 1, 0, S);
    }
    
    private long countHelper(char[] digits, int pos, int tight, int sum, int S) {
        // Base case: reached end of number
        if (pos == digits.length) {
            return sum == S ? 1 : 0;
        }
        // Check memoized state
        if (memo[pos][tight][sum] != null) return memo[pos][tight][sum];
        
        long count = 0;
        // Max digit allowed based on tight flag
        int maxDigit = tight == 1 ? digits[pos] - '0' : 9;
        // Try all possible digits
        for (int d = 0; d <= maxDigit && sum + d <= S; d++) {
            // Update tight flag: if tight and d == maxDigit, next digit still constrained
            int newTight = tight == 1 && d == maxDigit ? 1 : 0;
            count += countHelper(digits, pos + 1, newTight, sum + d, S);
        }
        
        return memo[pos][tight][sum] = count;
    }
}
```

---

## Problem 2: Count of Numbers with At Most K Digits Having Digit Sum = X
### Problem Statement:
Kitne numbers hain jo at most K digits ke hain aur unka digit sum X ke barabar hai?

**Example:**
```
Input: K = 2, X = 3
Output: 3
Explanation: Numbers: 03, 12, 21
```

### Intuition:
- Similar to previous problem, lekin N ke jagah K digits tak consider karo.
- State: `dp[pos][tight][sum]` with leading zeros handling.
- Transition: Same as above, but allow leading zeros explicitly.
- Base cases: Same as above.

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public long countNumbersWithSum(int K, int X) {
        memo = new Long[K][2][X+1];
        // Assume max number is 10^K - 1
        char[] digits = new char[K];
        Arrays.fill(digits, '9');
        return countHelper(digits, 0, 1, 0, X);
    }
    
    private long countHelper(char[] digits, int pos, int tight, int sum, int X) {
        // Base case: reached end of number
        if (pos == digits.length) {
            return sum == X ? 1 : 0;
        }
        // Check memoized state
        if (memo[pos][tight][sum] != null) return memo[pos][tight][sum];
        
        long count = 0;
        // Max digit allowed
        int maxDigit = tight == 1 ? digits[pos] - '0' : 9;
        // Try all possible digits
        for (int d = 0; d <= maxDigit && sum + d <= X; d++) {
            // Update tight flag
            int newTight = tight == 1 && d == maxDigit ? 1 : 0;
            count += countHelper(digits, pos + 1, newTight, sum + d, X);
        }
        
        return memo[pos][tight][sum] = count;
    }
}
```

---

## Problem 3: Count of Numbers ≤ N with Digit XOR = X
### Problem Statement:
Ek number N diya hai. Kitne numbers ≤ N hain jinke digits ka XOR X ke barabar hai?

**Example:**
```
Input: N = "20", X = 2
Output: 3
Explanation: Numbers 2, 11, 20 have digit XOR = 2.
```

### Intuition:
- Har digit ka XOR track karo instead of sum.
- State: `dp[pos][tight][xor]` = count of numbers with given XOR.
- Transition: For each digit d, update xor ^= d.
- Base cases: If pos == digits.length, check if xor == X.

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public long countNumbersWithXOR(String N, int X) {
        int n = N.length();
        memo = new Long[n][2][1024]; // XOR can be large, adjust as needed
        char[] digits = N.toCharArray();
        return countHelper(digits, 0, 1, 0, X);
    }
    
    private long countHelper(char[] digits, int pos, int tight, int xor, int X) {
        // Base case: reached end of number
        if (pos == digits.length) {
            return xor == X ? 1 : 0;
        }
        // Check memoized state
        if (memo[pos][tight][xor] != null) return memo[pos][tight][xor];
        
        long count = 0;
        // Max digit allowed
        int maxDigit = tight == 1 ? digits[pos] - '0' : 9;
        // Try all possible digits
        for (int d = 0; d <= maxDigit; d++) {
            // Update tight flag
            int newTight = tight == 1 && d == maxDigit ? 1 : 0;
            // Update XOR
            count += countHelper(digits, pos + 1, newTight, xor ^ d, X);
        }
        
        return memo[pos][tight][xor] = count;
    }
}
```

---

## Problem 4: Numbers with At Most 3 Non-Zero Digits
### Problem Statement:
Ek range [1, N] di hai. Kitne numbers hain jinke at most 3 non-zero digits hain?

**Example:**
```
Input: N = "20"
Output: 12
Explanation: Numbers like 1, 2, 10, 11, 12, 20, etc., with ≤ 3 non-zero digits.
```

### Intuition:
- Non-zero digits ka count track karo.
- State: `dp[pos][tight][nonZero]` = count of numbers with ≤ 3 non-zero digits.
- Transition: For d = 0, nonZero same; for d > 0, nonZero += 1 if ≤ 3.
- Base cases: If pos == digits.length, return 1 if nonZero ≤ 3.

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public long countNumbersWithNonZero(String N) {
        int n = N.length();
        memo = new Long[n][2][4]; // At most 3 non-zero digits
        char[] digits = N.toCharArray();
        return countHelper(digits, 0, 1, 0);
    }
    
    private long countHelper(char[] digits, int pos, int tight, int nonZero) {
        // Base case: reached end of number
        if (pos == digits.length) {
            return nonZero <= 3 ? 1 : 0;
        }
        // Check memoized state
        if (memo[pos][tight][nonZero] != null) return memo[pos][tight][nonZero];
        
        long count = 0;
        // Max digit allowed
        int maxDigit = tight == 1 ? digits[pos] - '0' : 9;
        // Try all possible digits
        for (int d = 0; d <= maxDigit; d++) {
            // Update tight flag
            int newTight = tight == 1 && d == maxDigit ? 1 : 0;
            // Update non-zero count
            int newNonZero = nonZero + (d > 0 ? 1 : 0);
            if (newNonZero <= 3) {
                count += countHelper(digits, pos + 1, newTight, newNonZero);
            }
        }
        
        return memo[pos][tight][nonZero] = count;
    }
}
```

---

## Problem 5: Count Numbers with Digits in Increasing Order
### Problem Statement:
Kitne numbers hain jo at most N hain aur unke digits strictly increasing order mein hain?

**Example:**
```
Input: N = "20"
Output: 8
Explanation: Numbers: 1, 2, 3, 4, 5, 6, 7, 12
```

### Intuition:
- Har digit strictly bada hona chahiye pichle digit se.
- State: `dp[pos][tight][prev]` = count of numbers with increasing digits.
- Transition: For each digit d > prev, try adding it.
- Base cases: If pos == digits.length, return 1.

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public long countIncreasingDigits(String N) {
        int n = N.length();
        memo = new Long[n][2][10];
        char[] digits = N.toCharArray();
        return countHelper(digits, 0, 1, 0);
    }
    
    private long countHelper(char[] digits, int pos, int tight, int prev) {
        // Base case: reached end of number
        if (pos == digits.length) {
            return 1;
        }
        // Check memoized state
        if (memo[pos][tight][prev] != null) return memo[pos][tight][prev];
        
        long count = 0;
        // Max digit allowed
        int maxDigit = tight == 1 ? digits[pos] - '0' : 9;
        // Try digits greater than prev
        for (int d = prev + 1; d <= maxDigit; d++) {
            // Update tight flag
            int newTight = tight == 1 && d == maxDigit ? 1 : 0;
            count += countHelper(digits, pos + 1, newTight, d);
        }
        
        return memo[pos][tight][prev] = count;
    }
}
```

---

## Conclusion
Digit DP pattern bohot useful hai jab numbers ko digit-by-digit process karna ho aur specific properties count karni ho. Har problem ka core idea alag hai, lekin approach same:
- State define karo (pos, tight, property), transition clear karo, base cases set karo.
- Top-Down (memoization) zyada intuitive aur common hai Digit DP ke liye.


---
**Digit DP** problems ek *special type of dynamic programming* hain jo **digits of numbers (usually integers)** par kaam karti hain. Inhe pehchanna tricky ho sakta hai, lekin kuch **strong indicators** aur **patterns** hote hain jinke basis par tum turant samajh sakte ho ki problem Digit DP par based hai.

---

## 🔍 **Kaise Pehchaane ki Problem Digit DP Type Hai?**

### ✅ **Strong Clues (Recognition Markers)**

| Marker                                        | Description                                                                                                                          |
| --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| 🔢 **Number Range (like 1 to N)**             | Question bolta hai: *“How many numbers from 1 to N satisfy some property?”*                                                          |
| 🧮 **Digit-wise Conditions**                  | Property specific hoti hai digits pe: *no adjacent same digits*, *sum of digits even*, *contains digit 3*, *no digit repeated*, etc. |
| 🎯 **Count of valid numbers chahiye**         | Mostly *count* poocha jata hai, not the actual numbers                                                                               |
| 🚫 **Constraints very large (like N ≤ 10¹⁸)** | Itne large inputs mein brute force possible nahi hota; isliye digit-by-digit recursion                                               |
| 🧬 **Recursive + Positional Thinking needed** | Tumhe har digit ke position pe decision lena padta hai                                                                               |
| 🔗 **Tight bound logic**                      | Question mein “<= N” jaisa constraint diya hota hai — jiska matlab hota hai tumhe tight bound maintain karna padega                  |

---

## 🧠 **Digit DP Intuition**

Tum number ko **digit-by-digit traverse** karte ho (left to right), aur har position pe **decision lete ho**:

* Kya current digit ko 0 se 9 ke beech freely choose kar sakte ho?
* Kya abhi tak ban raha number upper bound `N` ke equal hai ya chhota ho gaya?
* Kya tum kisi condition ko tod rahe ho (like repeated digits)?

Digit DP ka core hota hai:

```
dp(pos, tight, something_else)
```

* `pos`: current digit position
* `tight`: kya abhi tak number N ke barabar jaa raha hai?
* `something_else`: jaise sum, prev digit, mask for digits used, etc.

---

## 📘 **Typical Questions jisme Digit DP lagega**

| Problem                                                       | Why Digit DP?           |
| ------------------------------------------------------------- | ----------------------- |
| Count numbers between L and R having digit sum divisible by K | digit sum + range       |
| Count numbers ≤ N with no repeated digits                     | digit uniqueness        |
| Count numbers ≤ N with exactly K digits being 7               | digit constraint        |
| Count numbers without consecutive same digits                 | digit pattern condition |
| Count numbers with alternating even/odd digits                | property on digit       |
| Total numbers ≤ N where each digit is even                    | per digit restriction   |

---

## 🔄 **Flow to Recognize Digit DP**

```plaintext
Kya number ka range diya hai? (like 1 to N)
      ↓
Kya condition digits pe hai? (digit sum, repeated digits, etc.)
      ↓
Kya output count chahiye?
      ↓
Kya constraints large hain? (N ≤ 10^18)
      ↓
→ Yes → Likely a Digit DP Problem
```

---

## 🔧 Typical DP State Format

```java
dp(pos, tight, mask/prevDigit/sum/leadingZero)
```

* `tight` -> 1 if prefix equals N till now, else 0
* `mask` -> bitmask if digit repetition is involved
* `sum` -> if sum of digits is being calculated
* `leadingZero` -> to avoid counting leading zeroes

---
