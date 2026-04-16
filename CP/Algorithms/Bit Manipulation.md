# Bit Manipulation 

## Theory - Kya hai Bit Manipulation?

**Simple Words:** Computer mein sab kuch **0 aur 1 (bits)** mein store hota hai. Bit manipulation ka matlab hai in **bits ke saath direct operation** karna. Jaise light switch ON/OFF karna - 1 = ON, 0 = OFF.

### Real Life Example:

Maan lo tumhare paas 8 light bulbs hain (8 bits):
```
Bulbs:   [OFF] [OFF] [OFF] [OFF] [OFF] [OFF] [OFF] [OFF]
Binary:   0     0     0     0     0     0     0     0

Agar 5th bulb ON karna hai:
Binary:   0     0     0     0     1     0     0     0
Position: 7     6     5     4     3     2     1     0  (right se counting)
```

Bitwise operations se tum **ek saath multiple bulbs** ON/OFF kar sakte ho, bahut fast!

---

## Core Concepts - Basic Operators

### 1. AND (&) - Dono 1 toh 1

| Operation | Result |
|-----------|--------|
| 0 & 0 = 0 | Dono OFF → OFF |
| 0 & 1 = 0 | Ek OFF → OFF |
| 1 & 0 = 0 | Ek OFF → OFF |
| 1 & 1 = 1 | Dono ON → ON |

**Example:**
```
  1010  (10 decimal)
& 1100  (12 decimal)
= 1000  (8 decimal)
```

**Real Life:** Jaise do switches series mein lage hain - dono ON tabhi light jalegi.

### 2. OR (|) - Koi ek 1 toh 1

| Operation | Result |
|-----------|--------|
| 0 \| 0 = 0 | Dono OFF → OFF |
| 0 \| 1 = 1 | Ek ON → ON |
| 1 \| 0 = 1 | Ek ON → ON |
| 1 \| 1 = 1 | Dono ON → ON |

**Example:**
```
  1010  (10)
| 1100  (12)
= 1110  (14)
```

**Real Life:** Jaise do switches parallel mein lage hain - koi bhi ON karo, light jalegi.

### 3. XOR (^) - Different ho toh 1

| Operation | Result |
|-----------|--------|
| 0 ^ 0 = 0 | Same → OFF |
| 0 ^ 1 = 1 | Different → ON |
| 1 ^ 0 = 1 | Different → ON |
| 1 ^ 1 = 0 | Same → OFF |

**Example:**
```
  1010  (10)
^ 1100  (12)
= 0110  (6)
```

**Magic Property:** 
- `a ^ a = 0` (number apne saath XOR = 0)
- `a ^ 0 = a` (number 0 se XOR = number itself)
- `a ^ b ^ a = b` (order doesn't matter)

### 4. NOT (~) - Ulta kar do

| Operation | Result |
|-----------|--------|
| ~0 = 1 | 0 ko 1 bana do |
| ~1 = 0 | 1 ko 0 bana do |

**Example:**
```
~1010 = ...11110101 (infinite 1s ke baad)
Java mein ~10 = -11 (2's complement)
```

### 5. Left Shift (<<) - Left shift karo, 0 bharo

```
5 << 1 = 10    (101 << 1 = 1010)
5 << 2 = 20    (101 << 2 = 10100)

Formula: a << b = a × 2^b
```

**Real Life:** Jaise number mein right side 0 add kar dena.

### 6. Right Shift (>>) - Right shift karo, sign bit bharo

```
5 >> 1 = 2     (101 >> 1 = 10)
5 >> 2 = 1     (101 >> 2 = 1)

Formula: a >> b = a / 2^b (floor)
```

### 7. Unsigned Right Shift (>>>) - Right shift karo, 0 bharo

```
-5 >>> 1 = big positive number (sign bit ignore, 0 bharo)
```

---

## Important Bit Tricks (Yaad Rakhne Wale)

### Trick 1: Check if number is power of two
```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}

// Example: 8 (1000) & 7 (0111) = 0 ✅
// Example: 6 (0110) & 5 (0101) = 4 (0100) != 0 ❌
```

### Trick 2: Check if number is odd or even
```java
// Fastest way - No modulo operator
boolean isOdd = (n & 1) == 1;   // Last bit 1 hai toh odd
boolean isEven = (n & 1) == 0;  // Last bit 0 hai toh even
```

### Trick 3: Turn off rightmost 1 bit
```java
int turnOffRightmostOne(int n) {
    return n & (n - 1);
}
// Example: 12 (1100) & 11 (1011) = 8 (1000)
```

### Trick 4: Isolate rightmost 1 bit
```java
int isolateRightmostOne(int n) {
    return n & (-n);
}
// Example: 12 (1100) & -12 (0100) = 4 (0100)
```

### Trick 5: Count set bits (1s) in a number
```java
// Brian Kernighan's Algorithm
int countSetBits(int n) {
    int count = 0;
    while (n > 0) {
        n &= (n - 1);  // Turn off rightmost 1
        count++;
    }
    return count;
}
// Example: 13 (1101) → 3 set bits
```

### Trick 6: XOR - Find the only number appearing odd times
```java
int findOddOccurrence(int[] arr) {
    int result = 0;
    for (int num : arr) {
        result ^= num;
    }
    return result;
}
// a^a=0, a^0=a → jo bacha woh odd wala
```

### Trick 7: Swap two numbers without temp variable
```java
int a = 5, b = 10;
a = a ^ b;  // a = 5^10
b = a ^ b;  // b = (5^10)^10 = 5
a = a ^ b;  // a = (5^10)^5 = 10
```

### Trick 8: Toggle (flip) the ith bit
```java
int toggleIthBit(int n, int i) {
    return n ^ (1 << i);
}
// Example: 5 (0101), toggle bit 1 → 0101 ^ 0010 = 0111 (7)
```

### Trick 9: Set the ith bit to 1
```java
int setIthBit(int n, int i) {
    return n | (1 << i);
}
```

### Trick 10: Clear (set to 0) the ith bit
```java
int clearIthBit(int n, int i) {
    return n & ~(1 << i);
}
```

### Trick 11: Check if ith bit is set (1)
```java
boolean isIthBitSet(int n, int i) {
    return (n & (1 << i)) != 0;
}
```

### Trick 12: Multiply or divide by 2
```java
int multiplyBy2 = n << 1;    // n * 2
int divideBy2 = n >> 1;      // n / 2
int multiplyBy4 = n << 2;    // n * 4
```

### Trick 13: Get absolute value without Math.abs()
```java
int abs(int n) {
    int mask = n >> 31;  // if n negative: all 1s, if positive: all 0s
    return (n + mask) ^ mask;
}
```

### Trick 14: Find the only missing number from 0 to n
```java
int missingNumber(int[] nums) {
    int xor = 0;
    for (int i = 0; i <= nums.length; i++) xor ^= i;
    for (int num : nums) xor ^= num;
    return xor;
}
```

### Trick 15: Find the two numbers appearing odd times
```java
int[] findTwoOdd(int[] arr) {
    int xor = 0;
    for (int num : arr) xor ^= num;
    
    // Isolate rightmost set bit
    int rightmost = xor & -xor;
    
    int num1 = 0, num2 = 0;
    for (int num : arr) {
        if ((num & rightmost) == 0) {
            num1 ^= num;
        } else {
            num2 ^= num;
        }
    }
    return new int[]{num1, num2};
}
```

---

## Bit Masking - Ek Important Concept

Bit masking ka matlab hai **bits ko flags ki tarah use karna**.

### Real Life Example:

Maanlo tumhare paas 4 features hain:
```
Feature A: bit 0 (1 << 0 = 1)
Feature B: bit 1 (1 << 1 = 2)
Feature C: bit 2 (1 << 2 = 4)
Feature D: bit 3 (1 << 3 = 8)
```

Ab tum settings store kar sakte ho ek integer mein:
```
Settings = 0

// Enable Feature A and C
settings |= (1 << 0) | (1 << 2);  // settings = 1 + 4 = 5

// Check if Feature C is enabled
boolean hasC = (settings & (1 << 2)) != 0;  // true

// Disable Feature A
settings &= ~(1 << 0);  // settings = 5 - 1 = 4

// Toggle Feature B
settings ^= (1 << 1);   // 4 ^ 2 = 6
```

### Code Template:

```java
// Bitmask for DP - When n <= 20, use bitmask for subsets
int n = 10;
int totalStates = 1 << n;  // 1024 states

for (int mask = 0; mask < totalStates; mask++) {
    // mask represents a subset
    // bit j = 1 means element j is included
    
    for (int j = 0; j < n; j++) {
        if ((mask & (1 << j)) != 0) {
            // element j is in the subset
        }
    }
}
```

---

## Common Bit Manipulation Problems

### Problem 1: Single Number (LeetCode 136)
**Problem:** Array mein har number 2 baar aata hai, ek number 1 baar. Wo find karo.

```java
public int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;
    }
    return result;
}
```
XOR ka magic - a^a=0, a^0=a. Saare numbers XOR karte jao, duplicate ones cancel ho jayenge, jo bachega woh answer hai.

### Problem 2: Number of 1 Bits (LeetCode 191)
**Problem:** Number ke binary representation mein kitne 1s hain?

```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1);  // Remove rightmost 1
        count++;
    }
    return count;
}
```
n & (n-1) rightmost 1 bit ko 0 kar deta hai. Jab tak number 0 nahi ho jata, count karte jao.

### Problem 3: Reverse Bits (LeetCode 190)
**Problem:** 32-bit number ke bits reverse karo.

```java
public int reverseBits(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
        result <<= 1;           // Make space for new bit
        result |= (n & 1);      // Add last bit of n
        n >>= 1;                // Remove last bit of n
    }
    return result;
}
```
n ki last bit nikaalo, result mein add karte jao, result ko left shift karte jao. 32 baar karo.

### Problem 4: Power of Two (LeetCode 231)
**Problem:** Number power of two hai ya nahi?

```java
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

### Problem 5: Missing Number (LeetCode 268)
**Problem:** [0,n] mein ek number missing hai. Find karo.

```java
public int missingNumber(int[] nums) {
    int xor = nums.length;
    for (int i = 0; i < nums.length; i++) {
        xor ^= i ^ nums[i];
    }
    return xor;
}
```

### Problem 6: Find the Difference (LeetCode 389)
**Problem:** String s mein ek character extra add kar diya hai t mein. Wo character find karo.

```java
public char findTheDifference(String s, String t) {
    char result = 0;
    for (char c : s.toCharArray()) result ^= c;
    for (char c : t.toCharArray()) result ^= c;
    return result;
}
```

### Problem 7: Sum of Two Integers (LeetCode 371)
**Problem:** Bina + operator ke do numbers ka sum karo.

```java
public int getSum(int a, int b) {
    while (b != 0) {
        int carry = (a & b) << 1;  // Carry
        a = a ^ b;                  // Sum without carry
        b = carry;                  // Carry ko next iteration mein le jao
    }
    return a;
}
```
XOR sum deta hai (bina carry), AND carry deta hai. Jab tak carry 0 nahi ho jata, karte jao.

### Problem 8: Subsets (LeetCode 78)
**Problem:** Array ke saare subsets generate karo using bitmask.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    int totalSubsets = 1 << n;  // 2^n
    
    for (int mask = 0; mask < totalSubsets; mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                subset.add(nums[i]);
            }
        }
        result.add(subset);
    }
    return result;
}
```

### Problem 9: Counting Bits (LeetCode 338)
**Problem:** 0 se n tak har number mein kitne 1s hain?

```java
public int[] countBits(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        dp[i] = dp[i >> 1] + (i & 1);
    }
    return dp;
}
```
i >> 1 = i/2 (right shift), i & 1 = last bit. i ke bits = (i/2 ke bits) + (last bit).

### Problem 10: Maximum Product of Word Lengths (LeetCode 318)
**Problem:** Do words jinka koi common letter nahi hai. Unke length ka maximum product.

```java
public int maxProduct(String[] words) {
    int n = words.length;
    int[] masks = new int[n];
    
    for (int i = 0; i < n; i++) {
        int mask = 0;
        for (char c : words[i].toCharArray()) {
            mask |= (1 << (c - 'a'));
        }
        masks[i] = mask;
    }
    
    int maxProduct = 0;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if ((masks[i] & masks[j]) == 0) {
                maxProduct = Math.max(maxProduct, words[i].length() * words[j].length());
            }
        }
    }
    return maxProduct;
}
```

---

## Quick Reference Table

| Operation | Formula | Use Case |
|-----------|---------|----------|
| Check odd/even | n & 1 | Fastest parity check |
| Multiply by 2 | n << 1 | Power of 2 multiply |
| Divide by 2 | n >> 1 | Power of 2 division |
| Clear lowest set bit | n & (n-1) | Count set bits |
| Isolate lowest set bit | n & -n | Extract rightmost 1 |
| Toggle bit i | n ^ (1 << i) | Flip specific bit |
| Set bit i | n \| (1 << i) | Turn on bit |
| Clear bit i | n & ~(1 << i) | Turn off bit |
| Check bit i | (n & (1 << i)) != 0 | Test bit |
| Power of 2 check | n > 0 && (n & (n-1)) == 0 | Fast check |
| XOR swap | a ^= b; b ^= a; a ^= b | Swap without temp |
| Get absolute | (n + (n>>31)) ^ (n>>31) | No Math.abs() |

---

## Important Points Yaad Rakho

1. **Bitwise operations are extremely fast** - ek CPU cycle mein complete ho jati hain
2. **Use bitmask when n <= 20** - 2^n states manageable hai
3. **XOR is magic** - Use for finding unique elements, swapping, parity
4. **AND with (n-1) clears lowest set bit** - Very useful for counting bits
5. **Left shift = multiply by 2** - Use for power of 2 operations
6. **Right shift = divide by 2** - Use for fast division (integer)

## Kab Use Karein?

✅ Check if number is power of 2
✅ Count set bits (1s) in binary representation
✅ Find number appearing odd times (XOR)
✅ Generate all subsets (bitmask)
✅ Toggle, set, clear specific bits
✅ Optimize performance (faster than arithmetic)
✅ State tracking in DP (when n <= 20)
