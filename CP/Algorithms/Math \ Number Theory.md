
# Math / Number Theory

Number Theory maths ka wo branch hai jo **integers** (purnank) aur **unke properties** se deal karta hai. Coding interviews mein ye concepts kaafi helpful hote hain.

---

## 📚 Core Concepts (Basic Se Advanced Tak)

### 1. **Modulo Arithmetic (%)** - Sabse Important
```
Jab hum do numbers divide karte hain, remainder bachta hai - wohi modulo hai.

Example: 17 ÷ 5 = 3 remainder 2, isliye 17 % 5 = 2

Properties:
- (a + b) % m = (a%m + b%m) % m
- (a - b) % m = (a%m - b%m + m) % m
- (a × b) % m = (a%m × b%m) % m
```

**Real Life Example:** Clock mein agar 10 baje hai aur 5 ghante baad = (10+5)%12 = 3 baje

---

### 2. **GCD (Greatest Common Divisor) - Mahattam Samapvartak**
Do numbers ka **sabse bada common divisor**.

```
Example: 12 aur 18 ke divisors
12: 1,2,3,4,6,12
18: 1,2,3,6,9,18
Common: 1,2,3,6
Sabse bada = 6, isliye GCD(12,18) = 6
```

**Euclidean Algorithm (Super Fast):**
```
GCD(a,b) = GCD(b, a % b)
Jab tak b = 0 nahi hota, tab tak karo.

Example: GCD(48,18)
48 % 18 = 12 → GCD(18,12)
18 % 12 = 6  → GCD(12,6)
12 % 6 = 0   → GCD(6,0) = 6 ✅
```

---

### 3. **LCM (Least Common Multiple) - Laghutam Samapvartak**
Do numbers ka **sabse chhota common multiple**.

```
Formula: LCM(a,b) = (a × b) / GCD(a,b)

Example: LCM(12,18) = (12 × 18) / 6 = 216 / 6 = 36
```

---

### 4. **Prime Numbers (Abhajya Sankhya)**
Wo numbers jo sirf 1 se aur khud se divisible hote hain.

```
Prime: 2,3,5,7,11,13,17,19,23...
Not Prime: 4,6,8,9,10,12,14,15...

Special: 1 prime nahi hai, 2 sabse chhota prime hai
```

**Prime Check Karne Ka Efficient Tarika:**
```
Agar number n hai, toh sirf √n tak check karna hai.
Kyunki agar n = a × b, toh a ya b mein se ek √n se chhota hoga.
```

---

### 5. **Sieve of Eratosthenes (Prime Numbers Generate Karna)**
1 se N tak ke saare prime numbers efficiently find karne ka tarika.

```
Kaam Kaise Karta Hai:
1. 2 se N tak saare numbers "prime" mark karo
2. 2 se start karo, 2 ke saare multiples "not prime" mark karo
3. Next unmarked number 3 hai, uske multiples mark karo
4. Aise karte jao √N tak
```

**Example: N=30**
```
Start: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20...
2 ke multiples: 4,6,8,10,12,14,16,18,20 → remove
3 ke multiples: 6,9,12,15,18 → remove
5 ke multiples: 10,15,20 → remove
Bache: 2,3,5,7,11,13,17,19,23,29 → Ye prime numbers hain
```

---

### 6. **Prime Factorization (Abhajya Gunankhand)**
Kisi number ko prime numbers ke product mein todna.

```
Example: 84 = 2 × 2 × 3 × 7 = 2² × 3¹ × 7¹

Tarika: Number ko chhote chhote primes se divide karte jao.
84 ÷ 2 = 42
42 ÷ 2 = 21
21 ÷ 3 = 7
7 ÷ 7 = 1
```

---

### 7. **Exponentiation (Ghat) - Power Calculate Karna**
```
2³ = 2 × 2 × 2 = 8
3⁴ = 3 × 3 × 3 × 3 = 81
```

**Fast Exponentiation (Binary Exponentiation) - O(log n) mein:**
```
3⁵ calculate karna hai:
5 = binary 101 = 4 + 1
3⁵ = 3⁴ × 3¹

Aise O(log n) steps mein kaam ho jata hai.
```

---

### 8. **Modular Inverse**
Jab hum modulo arithmetic mein division karna chahte hain.

```
Definition: a × x ≡ 1 (mod m) → x is modular inverse of a modulo m

Example: modulo 7 mein 3 ka inverse?
3 × 5 = 15 ≡ 1 (mod 7) ✅, isliye inverse = 5

Only possible when GCD(a,m) = 1
```

---

### 9. **Trailing Zeros in Factorial (n! ke end mein kitne zeros)**
```
n! = n × (n-1) × (n-2) × ... × 1

Zero kaise aata hai? 2 × 5 = 10 se!
2 ki power 5 se zyada hoti hai, isliye sirf 5 count karo.

Formula: Trailing Zeros = n/5 + n/25 + n/125 + ...
```

**Example: 10! mein kitne zeros?**
```
10/5 = 2
10/25 = 0
Total = 2 zeros ✅ (10! = 3628800)
```

---

### 10. **Digits Based Properties**

**Sum of Digits:**
```
1234 ka sum = 1+2+3+4 = 10
```

**Palindrome Number:** Jo number aage aur piche same padhe
```
121, 1331, 12321 → Palindromes
123, 456 → Not palindromes
```

**Armstrong Number:** Digit ke cubes ka sum = number itself
```
153 = 1³ + 5³ + 3³ = 1 + 125 + 27 = 153 ✅
```

**Happy Number:** Digits ke squares ka sum repeat karo, eventually 1 pe pahunche toh happy
```
19 → 1²+9²=82 → 8²+2²=68 → 6²+8²=100 → 1²+0²+0²=1 ✅
```

---

## 📋 Top 10 Most Asked Number Theory Problems

---

### 1. **Count Primes (LeetCode 204) - Sieve of Eratosthenes**
**Problem:** n se chhote prime numbers count karo.

**Algorithm:** Sieve of Eratosthenes

```java
public int countPrimes(int n) {
    if (n <= 2) return 0;
    
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    for (int i = 2; i * i < n; i++) {
        if (isPrime[i]) {
            // i ke saare multiples ko false mark karo
            for (int j = i * i; j < n; j += i) {
                isPrime[j] = false;
            }
        }
    }
    
    int count = 0;
    for (int i = 2; i < n; i++) {
        if (isPrime[i]) count++;
    }
    return count;
}
```

> Sieve of Eratosthenes use karo. 2 se √n tak har prime ke multiples ko false mark karte jao. End mein jo true bache wahi prime numbers hain.

**Time:** O(n log log n)

---

### 2. **Happy Number (LeetCode 202)**
**Problem:** Number happy hai ya nahi? (Digits ke squares ka sum repeat karo, 1 pe pahunche toh happy)

**Algorithm:** Cycle detection (HashSet ya Floyd's cycle)

```java
public boolean isHappy(int n) {
    Set<Integer> seen = new HashSet<>();
    
    while (n != 1 && !seen.contains(n)) {
        seen.add(n);
        n = getNext(n);
    }
    return n == 1;
}

private int getNext(int n) {
    int sum = 0;
    while (n > 0) {
        int digit = n % 10;
        sum += digit * digit;
        n /= 10;
    }
    return sum;
}
```

> Digits ke squares ka sum nikaalte jao. Agar number repeat ho gaya (cycle), toh happy nahi hai. Agar 1 pe pahunch gaye, toh happy hai.

**Time:** O(log n)

---

### 3. **Power of Two / Three / Four**
**Problem:** Number power of two hai ya nahi? (2^x form mein hai?)

**Algorithm:** Bit manipulation ya log

```java
// Power of Two
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}

// Power of Three
public boolean isPowerOfThree(int n) {
    // 3^19 = 1162261467 is max power of 3 in int range
    return n > 0 && 1162261467 % n == 0;
}

// Power of Four
public boolean isPowerOfFour(int n) {
    return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
}
```

> Power of two ka property: (n & (n-1)) == 0. Power of three: 3^19 maximum hai, isliye 3^19 % n == 0 check karo. Power of four: power of two bhi hona chahiye aur 1 bit odd position pe honi chahiye.

**Time:** O(1)

---

### 4. **Ugly Number (LeetCode 263, 264)**
**Problem:** Ugly numbers wo hain jinke prime factors sirf 2, 3, 5 hain.

**Algorithm:** Divide by 2,3,5 repeatedly

```java
// Check if ugly number (263)
public boolean isUgly(int n) {
    if (n <= 0) return false;
    
    int[] primes = {2, 3, 5};
    for (int prime : primes) {
        while (n % prime == 0) {
            n /= prime;
        }
    }
    return n == 1;
}

// Find nth ugly number (264) - Using DP
public int nthUglyNumber(int n) {
    int[] ugly = new int[n];
    ugly[0] = 1;
    
    int p2 = 0, p3 = 0, p5 = 0;
    
    for (int i = 1; i < n; i++) {
        int next2 = ugly[p2] * 2;
        int next3 = ugly[p3] * 3;
        int next5 = ugly[p5] * 5;
        
        ugly[i] = Math.min(next2, Math.min(next3, next5));
        
        if (ugly[i] == next2) p2++;
        if (ugly[i] == next3) p3++;
        if (ugly[i] == next5) p5++;
    }
    return ugly[n-1];
}
```

> Ugly number check: number ko repeatedly 2,3,5 se divide karo. Agar end mein 1 bache toh ugly hai. Nth ugly number: DP se generate karo - har baar 2,3,5 se multiply karke min lo.

**Time:** O(n) for nth ugly

---

### 5. **GCD of Array / Find GCD**
**Problem:** Array ka GCD find karo.

**Algorithm:** Euclidean algorithm repeatedly

```java
public int findGCD(int[] nums) {
    int result = nums[0];
    for (int i = 1; i < nums.length; i++) {
        result = gcd(result, nums[i]);
    }
    return result;
}

private int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

> Pehle do numbers ka GCD nikaalo, fir us GCD ka next number ke saath GCD nikaalo. Aise karte jao. GCD property: GCD(a,b,c) = GCD(GCD(a,b), c)

**Time:** O(n log max)

---

### 6. **Trailing Zeros in Factorial (LeetCode 172)**
**Problem:** n! ke end mein kitne zeros hain?

**Algorithm:** Count multiples of 5, 25, 125...

```java
public int trailingZeroes(int n) {
    int count = 0;
    while (n > 0) {
        n /= 5;
        count += n;
    }
    return count;
}
```

> Zero 2×5 se aata hai. 2 ki power 5 se zyada hoti hai, isliye sirf 5 count karo. n/5 + n/25 + n/125 + ... karte jao jab tak n > 0.

**Time:** O(log n)

---

### 7. **Roman to Integer / Integer to Roman (LeetCode 13, 12)**
**Problem:** Roman numeral ko integer mein convert karo aur vice versa.

**Algorithm:** Greedy approach

```java
// Roman to Integer
public int romanToInt(String s) {
    Map<Character, Integer> map = Map.of(
        'I', 1, 'V', 5, 'X', 10, 'L', 50,
        'C', 100, 'D', 500, 'M', 1000
    );
    
    int result = 0;
    for (int i = 0; i < s.length(); i++) {
        int curr = map.get(s.charAt(i));
        if (i < s.length() - 1 && curr < map.get(s.charAt(i + 1))) {
            result -= curr;
        } else {
            result += curr;
        }
    }
    return result;
}

// Integer to Roman
public String intToRoman(int num) {
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < values.length && num > 0; i++) {
        while (num >= values[i]) {
            num -= values[i];
            sb.append(symbols[i]);
        }
    }
    return sb.toString();
}
```

> Roman to integer: agar chhota character bade se pehle hai (IV, IX), toh subtract karo, warna add karo. Integer to Roman: sabse badi value se shuru karo, greedy approach se symbol add karte jao.

**Time:** O(n)

---

### 8. **Excel Sheet Column Title / Number (LeetCode 168, 171)**
**Problem:** Column number se title (A, B, ..., Z, AA, AB...) aur title se number.

**Algorithm:** Base-26 conversion

```java
// Column Number to Title (1 -> A, 26 -> Z, 27 -> AA)
public String convertToTitle(int columnNumber) {
    StringBuilder sb = new StringBuilder();
    
    while (columnNumber > 0) {
        columnNumber--;
        sb.append((char)('A' + columnNumber % 26));
        columnNumber /= 26;
    }
    return sb.reverse().toString();
}

// Column Title to Number (A -> 1, Z -> 26, AA -> 27)
public int titleToNumber(String columnTitle) {
    int result = 0;
    for (char c : columnTitle.toCharArray()) {
        result = result * 26 + (c - 'A' + 1);
    }
    return result;
}
```

> 26 base ka number system hai, lekin A=1, Z=26. Title se number: har character ko 26 se multiply karte jao. Number se title: 0-based karo, phir 26 se divide.

**Time:** O(n)

---

### 9. **Reverse Integer (LeetCode 7)**
**Problem:** Integer reverse karo. Agar overflow ho jaye toh 0 return karo.

**Algorithm:** Modulo and division with overflow check

```java
public int reverse(int x) {
    int reversed = 0;
    
    while (x != 0) {
        int digit = x % 10;
        x /= 10;
        
        // Check overflow
        if (reversed > Integer.MAX_VALUE / 10 || 
            (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
            return 0;
        }
        if (reversed < Integer.MIN_VALUE / 10 || 
            (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
            return 0;
        }
        
        reversed = reversed * 10 + digit;
    }
    return reversed;
}
```

> Har digit nikaalne ke liye x%10 aur x/10 use karo. Overflow check important hai - reversed*10 karne se pehle check karo ki Integer.MAX_VALUE/10 se zyada toh nahi.

**Time:** O(log n)

---

### 10. **Sqrt(x) (LeetCode 69)**
**Problem:** Square root of x (integer) find karo. Decimal part truncate karna hai.

**Algorithm:** Binary search

```java
public int mySqrt(int x) {
    if (x < 2) return x;
    
    int left = 1, right = x / 2;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        long square = (long) mid * mid;
        
        if (square == x) {
            return mid;
        } else if (square < x) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return right;
}
```

> Binary search use karo. mid*mid compare karo x se. Integer overflow se bachne ke liye long use karo. End mein right return karo (woh floor value hai).

**Time:** O(log n)

---

## 📊 Quick Reference Table

| Concept | Formula / Method | Time Complexity |
|---------|-----------------|-----------------|
| GCD | Euclidean Algorithm | O(log min(a,b)) |
| LCM | (a×b)/GCD(a,b) | O(log min(a,b)) |
| Prime Check | Check till √n | O(√n) |
| Sieve of Eratosthenes | Mark multiples | O(n log log n) |
| Fast Power | Binary exponentiation | O(log n) |
| Trailing Zeros | n/5 + n/25 + ... | O(log n) |
| Modulo Inverse | Fermat's / Extended Euclid | O(log m) |

---

## 🎯 Important Tips for Interviews

### 1. **Overflow Se Bachna:**
```java
// Galat - overflow ho sakta hai
int result = a * b;

// Sahi - pehle long mein check karo
if (a > Integer.MAX_VALUE / b) {
    // Overflow
}
```

### 2. **Modulo Operations Ka Dhyaan Rakho:**
```java
// Negative numbers ke liye positive modulo lana
int mod = (a % m + m) % m;
```

### 3. **Prime Numbers Ke Kuch Facts:**
- 2 hi even prime hai
- 3 se lekar √n tak sirf odd numbers check karo
- Sieve of Eratosthenes sabse fast hai 1 se N tak primes ke liye

### 4. **GCD ke Properties:**
- GCD(a,b) × LCM(a,b) = a × b
- GCD(a,b) = GCD(a-b, b)
- GCD(a,b) = GCD(a%b, b)

### 5. **Pattern Recognition:**
- **"Divisible by"** → Modulo operator use karo
- **"Prime numbers"** → Sieve of Eratosthenes
- **"GCD/LCM"** → Euclidean algorithm
- **"Power of 2"** → Bit manipulation
- **"Large exponent"** → Binary exponentiation
