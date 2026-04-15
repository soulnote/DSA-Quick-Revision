# Bit Manipulation 

## **Bit Manipulation Basics - Theory (Hinglish)**

Bit manipulation ka matlab hai **binary digits (0/1)** ke saath direct operation karna. Computers binary mein kaam karte hain, isliye bit operations **bahut fast** hoti hain.

### **Why Bit Manipulation?**
- **Speed:** Arithmetic operations se 10-100x faster
- **Memory:** Flags store karne mein efficient
- **Interview favorite:** Elegant solutions milte hain

---

## **Fundamental Bit Operations**

### **1. Bitwise Operators**

| Operator | Name | Example | Result |
|----------|------|---------|--------|
| `&` | AND | 5 & 3 = 1 | 101 & 011 = 001 |
| `\|` | OR | 5 \| 3 = 7 | 101 \| 011 = 111 |
| `^` | XOR | 5 ^ 3 = 6 | 101 ^ 011 = 110 |
| `~` | NOT | ~5 = -6 | ~00000101 = 11111010 |
| `<<` | Left Shift | 5 << 1 = 10 | 101 → 1010 |
| `>>` | Right Shift | 5 >> 1 = 2 | 101 → 10 |
| `>>>` | Unsigned Right Shift | -5 >>> 1 = 2147483645 | |

### **2. Truth Table**

| A | B | A&B | A\|B | A^B | ~A |
|---|---|-----|------|-----|----|
| 0 | 0 | 0 | 0 | 0 | 1 |
| 0 | 1 | 0 | 1 | 1 | 1 |
| 1 | 0 | 0 | 1 | 1 | 0 |
| 1 | 1 | 1 | 1 | 0 | 0 |

### **3. Important Properties**

```java
// XOR Properties (Most Important!)
a ^ a = 0
a ^ 0 = a
a ^ b ^ a = b  (Cancellation property)
a ^ b = b ^ a  (Commutative)
(a ^ b) ^ c = a ^ (b ^ c)  (Associative)

// AND Properties
a & 0 = 0
a & a = a
a & ~a = 0

// OR Properties
a | 0 = a
a | a = a
a | ~a = -1 (all bits 1)
```

---

## **Basic Bit Manipulation Techniques**

```java
class BitBasics {
    
    // 1. Check if number is even or odd
    public boolean isEven(int n) {
        return (n & 1) == 0;  // Last bit 0 = even
    }
    
    // 2. Get ith bit (0-indexed from right)
    public int getIthBit(int n, int i) {
        return (n >> i) & 1;
    }
    
    // 3. Set ith bit to 1
    public int setIthBit(int n, int i) {
        return n | (1 << i);
    }
    
    // 4. Clear ith bit (set to 0)
    public int clearIthBit(int n, int i) {
        return n & ~(1 << i);
    }
    
    // 5. Toggle ith bit (0→1, 1→0)
    public int toggleIthBit(int n, int i) {
        return n ^ (1 << i);
    }
    
    // 6. Update ith bit to given value (0 or 1)
    public int updateIthBit(int n, int i, int bitValue) {
        // First clear the bit, then OR with new value
        return (n & ~(1 << i)) | (bitValue << i);
    }
    
    // 7. Clear last set bit (rightmost 1)
    public int clearLastSetBit(int n) {
        return n & (n - 1);
    }
    
    // 8. Get lowest set bit
    public int getLowestSetBit(int n) {
        return n & -n;
    }
    
    // 9. Count set bits (Brian Kernighan's algorithm)
    public int countSetBits(int n) {
        int count = 0;
        while(n != 0) {
            n &= (n - 1);  // Clear lowest set bit
            count++;
        }
        return count;
    }
    
    // 10. Check if number is power of 2
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    // 11. Multiply by 2
    public int multiplyBy2(int n) {
        return n << 1;
    }
    
    // 12. Divide by 2
    public int divideBy2(int n) {
        return n >> 1;
    }
    
    // 13. Check if two numbers have opposite signs
    public boolean oppositeSigns(int x, int y) {
        return (x ^ y) < 0;
    }
    
    // 14. Swap two numbers without temp
    public void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a=" + a + ", b=" + b);
    }
}
```

---

## **1. Single Number (Find the element that appears once)** ⭐⭐⭐

### Theory
**Problem:** Array mein har element 2 baar aata hai, sirf ek element 1 baar aata hai. Use find karo.

**Algorithm (Using XOR):**
- XOR all numbers together
- Duplicates cancel out (a ^ a = 0)
- Result = single number

**Example:** [4,1,2,1,2] → 4^1^2^1^2 = 4

### Java Code
```java
class SingleNumber {
    // Every element appears twice except one
    public int singleNumber(int[] nums) {
        int result = 0;
        for(int num : nums) {
            result ^= num;
        }
        return result;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **2. Single Number II (Appears thrice except one)** ⭐⭐

### Theory
**Problem:** Har element 3 baar aata hai, ek element 1 baar aata hai.

**Algorithm (Bit counting):**
1. Har bit position ke liye count karo kitni baar 1 hai
2. Agar count % 3 == 1, to result ki woh bit set karo

### Java Code
```java
class SingleNumberII {
    public int singleNumber(int[] nums) {
        int result = 0;
        
        for(int i = 0; i < 32; i++) {
            int sum = 0;
            for(int num : nums) {
                if(((num >> i) & 1) == 1) {
                    sum++;
                }
            }
            if(sum % 3 == 1) {
                result |= (1 << i);
            }
        }
        return result;
    }
    
    // Optimized: Using two variables (ones, twos)
    public int singleNumberOptimized(int[] nums) {
        int ones = 0, twos = 0;
        
        for(int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }
        
        return ones;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **3. Number of 1 Bits (Hamming Weight)** ⭐⭐

### Theory
**Problem:** Integer ke binary representation mein kitne 1s hain.

### Java Code
```java
class HammingWeight {
    // Method 1: Brian Kernighan's Algorithm
    public int hammingWeight(int n) {
        int count = 0;
        while(n != 0) {
            n &= (n - 1);  // Clear lowest set bit
            count++;
        }
        return count;
    }
    
    // Method 2: Bit by bit
    public int hammingWeightBitByBit(int n) {
        int count = 0;
        for(int i = 0; i < 32; i++) {
            if(((n >> i) & 1) == 1) count++;
        }
        return count;
    }
    
    // Method 3: Using Integer built-in
    public int hammingWeightBuiltIn(int n) {
        return Integer.bitCount(n);
    }
    
    // Time: O(number of set bits), Space: O(1)
}
```

---

## **4. Counting Bits (DP with Bit Manipulation)** ⭐⭐

### Theory
**Problem:** 0 se n tak har number ke liye 1s ki count return karo.

**Pattern:** 
- `count[i] = count[i >> 1] + (i & 1)`
- i >> 1 = i/2 (right shift)
- i & 1 = last bit

### Java Code
```java
class CountingBits {
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        
        for(int i = 1; i <= n; i++) {
            result[i] = result[i >> 1] + (i & 1);
            // OR: result[i] = result[i / 2] + (i % 2);
        }
        
        return result;
    }
    
    // Alternative: result[i] = result[i & (i-1)] + 1
    public int[] countBitsAlternative(int n) {
        int[] result = new int[n + 1];
        
        for(int i = 1; i <= n; i++) {
            result[i] = result[i & (i - 1)] + 1;
        }
        
        return result;
    }
    
    // Time: O(n), Space: O(n)
}
```

---

## **5. Reverse Bits** ⭐⭐

### Theory
**Problem:** 32-bit integer ke bits reverse karo.

### Java Code
```java
class ReverseBits {
    public int reverseBits(int n) {
        int result = 0;
        
        for(int i = 0; i < 32; i++) {
            // Shift result left to make space
            result <<= 1;
            
            // Add last bit of n to result
            result |= (n & 1);
            
            // Shift n right to get next bit
            n >>= 1;
        }
        
        return result;
    }
    
    // Optimized using byte reverse
    public int reverseBitsOptimized(int n) {
        n = ((n & 0xFFFF0000) >>> 16) | ((n & 0x0000FFFF) << 16);
        n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
        n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
        n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **6. Missing Number** ⭐⭐

### Theory
**Problem:** [0,n] range mein se ek number missing hai, use find karo.

**Algorithm (XOR):**
- XOR all numbers from 0 to n
- XOR all array elements
- Result = missing number (cancellation property)

### Java Code
```java
class MissingNumber {
    // Method 1: XOR
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int xor = 0;
        
        // XOR with indices and values
        for(int i = 0; i < n; i++) {
            xor ^= i ^ nums[i];
        }
        
        // XOR with n
        xor ^= n;
        
        return xor;
    }
    
    // Method 2: Sum formula
    public int missingNumberSum(int[] nums) {
        int n = nums.length;
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for(int num : nums) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **7. Find the Difference** ⭐

### Theory
**Problem:** String s mein ek character add karke string t banayi gayi. Added character find karo.

**Algorithm (XOR):**
- XOR all characters of s and t
- Duplicates cancel out, result = added character

### Java Code
```java
class FindDifference {
    public char findTheDifference(String s, String t) {
        char result = 0;
        
        // XOR all characters of s
        for(char c : s.toCharArray()) {
            result ^= c;
        }
        
        // XOR all characters of t
        for(char c : t.toCharArray()) {
            result ^= c;
        }
        
        return result;
    }
    
    // Time: O(n), Space: O(1)
}
```

---

## **8. Sum of Two Integers** ⭐⭐

### Theory
**Problem:** Bina + aur - operator ke sum karo.

**Algorithm:**
- `a ^ b` gives sum without carry
- `(a & b) << 1` gives carry
- Repeat until carry becomes 0

### Java Code
```java
class SumOfTwoIntegers {
    public int getSum(int a, int b) {
        while(b != 0) {
            int carry = (a & b) << 1;  // Calculate carry
            a = a ^ b;                  // Sum without carry
            b = carry;                  // Carry for next iteration
        }
        return a;
    }
    
    // Recursive version
    public int getSumRecursive(int a, int b) {
        if(b == 0) return a;
        return getSumRecursive(a ^ b, (a & b) << 1);
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **9. Power of Two** ⭐

### Theory
**Problem:** Check karo ki number 2 ki power hai ya nahi.

**Properties:**
- Power of 2 has exactly one set bit
- n & (n-1) clears the lowest set bit
- If result is 0, then only one bit was set

### Java Code
```java
class PowerOfTwo {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    // Alternative: Integer.bitCount
    public boolean isPowerOfTwoBitCount(int n) {
        return n > 0 && Integer.bitCount(n) == 1;
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **10. Power of Four** ⭐

### Theory
**Problem:** Check karo ki number 4 ki power hai ya nahi.

**Properties:**
- Power of 4 is also power of 2
- Power of 4 has set bit at even positions (0,2,4...)
- Mask: 0x55555555 (bits at odd positions are 0)

### Java Code
```java
class PowerOfFour {
    public boolean isPowerOfFour(int n) {
        return n > 0 && 
               (n & (n - 1)) == 0 &&  // Power of 2
               (n & 0x55555555) != 0; // Set bit at even position
    }
    
    // Alternative: Using modulo
    public boolean isPowerOfFourMod(int n) {
        return n > 0 && 
               (n & (n - 1)) == 0 &&  // Power of 2
               n % 3 == 1;             // 4^n % 3 = 1
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **11. Subsets (Power Set)** ⭐⭐

### Theory
**Problem:** Array ke saare subsets generate karo.

**Bitmask Technique:**
- Har subset ko binary number se represent karo
- Agar ith bit set hai to ith element include karo
- 0 se 2^n - 1 tak loop chalao

### Java Code
```java
class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        
        // Total subsets = 2^n
        for(int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            
            for(int i = 0; i < n; i++) {
                if((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            result.add(subset);
        }
        
        return result;
    }
    
    // Time: O(n * 2^n), Space: O(2^n)
}
```

---

## **12. Bitwise AND of Numbers Range** ⭐⭐⭐

### Theory
**Problem:** [left, right] range ke saare numbers ka bitwise AND find karo.

**Pattern:**
- AND of range = common prefix of left and right in binary
- Example: [5,7] = 101 & 110 & 111 = 100 = 4

### Java Code
```java
class BitwiseAndRange {
    public int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        
        // Find common prefix
        while(left < right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        
        return left << shift;
    }
    
    // Alternative: Clear lowest set bit of right
    public int rangeBitwiseAndAlternative(int left, int right) {
        while(left < right) {
            right &= (right - 1);  // Clear lowest set bit
        }
        return right;
    }
    
    // Time: O(log n), Space: O(1)
}
```

---

## **13. Find All Duplicates in Array** ⭐⭐

### Theory
**Problem:** Array mein duplicates find karo (each appears twice or once).

**Algorithm (Using sign as marker):**
- Har number ko index treat karo
- Number ko negative mark karo agar pehle dekha hai

### Java Code
```java
class FindDuplicates {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();
        
        for(int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            
            if(nums[index] < 0) {
                result.add(index + 1);  // Already seen, it's duplicate
            } else {
                nums[index] = -nums[index];  // Mark as seen
            }
        }
        
        return result;
    }
    
    // Time: O(n), Space: O(1) excluding output
}
```

---

## **14. Divide Two Integers** ⭐⭐⭐

### Theory
**Problem:** Bina *, /, % operator ke division karo.

**Algorithm (Bit manipulation):**
- Divisor ko repeatedly double karo
- Subtract from dividend
- Build quotient bit by bit

### Java Code
```java
class DivideIntegers {
    public int divide(int dividend, int divisor) {
        // Handle overflow
        if(dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        // Handle signs
        boolean negative = (dividend < 0) ^ (divisor < 0);
        
        // Convert to positive (using long to avoid overflow)
        long a = Math.abs((long)dividend);
        long b = Math.abs((long)divisor);
        
        long result = 0;
        
        while(a >= b) {
            long temp = b;
            long multiple = 1;
            
            // Double the divisor
            while(a >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }
            
            a -= temp;
            result += multiple;
        }
        
        return negative ? (int)-result : (int)result;
    }
    
    // Time: O(log² n), Space: O(1)
}
```

---

## **15. Maximum Product of Word Lengths** ⭐⭐

### Theory
**Problem:** Do words ka maximum product of lengths jinka koi common letter nahi hai.

**Bitmask Technique:**
- Har word ke liye bitmask banao (a=bit0, b=bit1, ...)
- Agar (mask1 & mask2) == 0 to common letters nahi hain

### Java Code
```java
class MaxProductWordLengths {
    public int maxProduct(String[] words) {
        int n = words.length;
        int[] masks = new int[n];
        
        // Create bitmask for each word
        for(int i = 0; i < n; i++) {
            int mask = 0;
            for(char c : words[i].toCharArray()) {
                mask |= 1 << (c - 'a');
            }
            masks[i] = mask;
        }
        
        // Find max product
        int max = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if((masks[i] & masks[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }
        
        return max;
    }
    
    // Time: O(n² * L), Space: O(n)
}
```

---

## **16. Gray Code** ⭐⭐

### Theory
**Problem:** Gray code sequence generate karo (adjacent numbers differ by only 1 bit).

**Formula:** G(i) = i ^ (i >> 1)

### Java Code
```java
class GrayCode {
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        
        for(int i = 0; i < (1 << n); i++) {
            result.add(i ^ (i >> 1));
        }
        
        return result;
    }
    
    // Alternative: Backtracking
    public List<Integer> grayCodeBacktracking(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        
        for(int i = 0; i < n; i++) {
            int size = result.size();
            for(int j = size - 1; j >= 0; j--) {
                result.add(result.get(j) | (1 << i));
            }
        }
        
        return result;
    }
    
    // Time: O(2^n), Space: O(2^n)
}
```

---

## **17. Repeated DNA Sequences** ⭐⭐

### Theory
**Problem:** 10-character long substrings jo multiple baar aate hain.

**Bitmask Technique:**
- Har character (A,C,G,T) ko 2 bits mein encode karo
- Rolling hash use karo

### Java Code
```java
class RepeatedDNASequences {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> result = new ArrayList<>();
        if(s.length() < 10) return result;
        
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0);
        map.put('C', 1);
        map.put('G', 2);
        map.put('T', 3);
        
        Set<Integer> seen = new HashSet<>();
        Set<Integer> added = new HashSet<>();
        
        int mask = 0;
        for(int i = 0; i < 10; i++) {
            mask = (mask << 2) | map.get(s.charAt(i));
        }
        seen.add(mask);
        
        for(int i = 10; i < s.length(); i++) {
            // Remove first 2 bits, add new 2 bits
            mask = ((mask & 0x3FFFF) << 2) | map.get(s.charAt(i));
            
            if(seen.contains(mask) && !added.contains(mask)) {
                result.add(s.substring(i - 9, i + 1));
                added.add(mask);
            } else {
                seen.add(mask);
            }
        }
        
        return result;
    }
    
    // Time: O(n), Space: O(n)
}
```

---

## **18. Total Hamming Distance** ⭐⭐

### Theory
**Problem:** Array mein saare pairs ka Hamming distance sum karo.

**Bit-by-bit approach:**
- Har bit position ke liye count karo kitni numbers mein 1 hai
- Pair = count1 * count0

### Java Code
```java
class TotalHammingDistance {
    public int totalHammingDistance(int[] nums) {
        int total = 0;
        
        for(int i = 0; i < 32; i++) {
            int count1 = 0;
            for(int num : nums) {
                if(((num >> i) & 1) == 1) {
                    count1++;
                }
            }
            int count0 = nums.length - count1;
            total += count1 * count0;
        }
        
        return total;
    }
    
    // Time: O(32 * n) = O(n), Space: O(1)
}
```

---

## **19. Find the XOR of Numbers from L to R** ⭐⭐

### Theory
**Problem:** Range [L,R] ke saare numbers ka XOR find karo.

**Formula:** XOR from 1 to n = 
- n if n%4 == 0
- 1 if n%4 == 1
- n+1 if n%4 == 2
- 0 if n%4 == 3

### Java Code
```java
class XorRange {
    public int xorFrom1ToN(int n) {
        if(n % 4 == 0) return n;
        if(n % 4 == 1) return 1;
        if(n % 4 == 2) return n + 1;
        return 0;  // n % 4 == 3
    }
    
    public int xorRange(int left, int right) {
        return xorFrom1ToN(right) ^ xorFrom1ToN(left - 1);
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **20. Minimum Flips to Make a OR b Equal to c** ⭐⭐

### Theory
**Problem:** Minimum bit flips chahiye taki (a OR b) == c ho jaye.

**Algorithm:**
- Har bit position check karo
- Agar c ki bit 0 hai to a aur b dono ki bit 0 honi chahiye
- Agar c ki bit 1 hai to a ya b ki bit 1 honi chahiye

### Java Code
```java
class MinFlips {
    public int minFlips(int a, int b, int c) {
        int flips = 0;
        
        for(int i = 0; i < 32; i++) {
            int aBit = (a >> i) & 1;
            int bBit = (b >> i) & 1;
            int cBit = (c >> i) & 1;
            
            if(cBit == 0) {
                // Both should be 0
                flips += aBit + bBit;
            } else {
                // At least one should be 1
                if(aBit == 0 && bBit == 0) {
                    flips++;
                }
            }
        }
        
        return flips;
    }
    
    // Time: O(1), Space: O(1)
}
```

---

## **Quick Reference Table**

| Operation | Bitwise | Example |
|-----------|---------|---------|
| Check odd/even | `n & 1` | 5&1=1 (odd) |
| Multiply by 2 | `n << 1` | 5<<1=10 |
| Divide by 2 | `n >> 1` | 5>>1=2 |
| Clear lowest set bit | `n & (n-1)` | 6&5=4 |
| Get lowest set bit | `n & -n` | 6&-6=2 |
| Toggle bit | `n ^ (1<<i)` | - |
| Check power of 2 | `n & (n-1) == 0` | - |
| Swap without temp | `a^=b; b^=a; a^=b` | - |
| Absolute value | `(n ^ (n>>31)) - (n>>31)` | - |

---

## **Common Patterns & Tricks**

### **Pattern 1: XOR Cancellation**
```java
// Find unique element where others appear twice
int result = 0;
for(int num : nums) result ^= num;
```

### **Pattern 2: Bit Counting**
```java
// Count set bits
int count = 0;
while(n > 0) {
    n &= (n - 1);
    count++;
}
```

### **Pattern 3: Subset Generation**
```java
// Generate all subsets
for(int mask = 0; mask < (1 << n); mask++) {
    for(int i = 0; i < n; i++) {
        if((mask & (1 << i)) != 0) {
            // include nums[i]
        }
    }
}
```

### **Pattern 4: State Encoding**
```java
// 10-bit mask for lowercase letters
int mask = 0;
for(char c : word.toCharArray()) {
    mask |= 1 << (c - 'a');
}
```

---

## **Time Complexity Analysis**

| Operation | Time | Space |
|-----------|------|-------|
| Basic bit ops (AND, OR, XOR) | O(1) | O(1) |
| Shift operations | O(1) | O(1) |
| Count set bits (Brian Kernighan) | O(k) where k=set bits | O(1) |
| Reverse bits | O(1) (32 iterations) | O(1) |
| Subset generation | O(n * 2^n) | O(2^n) |
| Bit counting in array | O(32 * n) = O(n) | O(1) |

---

## **Interview Tips (Hinglish)**

### **1. Yaad Rakhne Wali Properties**
```
XOR: a^a=0, a^0=a, a^b^a=b
AND: a&0=0, a&a=a
OR:  a|0=a, a|a=a
```

### **2. Common Bit Hacks**
```java
// Multiply by 2^k
n << k

// Divide by 2^k
n >> k

// Check if power of 2
(n & (n-1)) == 0

// Get lowest set bit
n & -n

// Clear lowest set bit
n & (n-1)

// Set bit at position i
n | (1 << i)

// Clear bit at position i
n & ~(1 << i)

// Toggle bit at position i
n ^ (1 << i)
```

### **3. Edge Cases Hamesha Check Karo**
- Negative numbers (2's complement representation)
- Integer overflow
- n = 0
- n = Integer.MIN_VALUE (-2147483648)

### **4. Common Mistakes**
- `1 << i` for i=31 gives negative number
- Right shift on negative numbers (>> vs >>>)
- XOR for negative numbers works fine, but be careful

### **5. When to Use Bit Manipulation?**
- Duplicate detection (XOR)
- Subset generation (bitmask)
- State representation (flags)
- Optimization problems
- Multiple of 2 operations

---

## **Pro Tips for Interviews**

1. **Pehle brute force batao** - "Mai O(n²) kar sakta hun, lekin XOR se O(n) mein ho jayega"

2. **Properties explain karo** - "XOR property use karenge ki a^a=0"

3. **Bit manipulation se code chhota aur elegant hota hai** - Interviewer impress hoga

4. **Negative numbers ka dhyan rakho** - Java mein negative numbers ke right shift ka behavior different hai

5. **Overflow se bacho** - Use `long` for intermediate calculations
