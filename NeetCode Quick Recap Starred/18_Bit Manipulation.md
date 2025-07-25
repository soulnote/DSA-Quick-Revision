
## 136\. Single Number

**Single Number** problem mein aapko ek array mein unique element find karna hota hai jahan baaki sab do baar aate hain.

-----

### Description/Overview

Aapko ek non-empty integer array `nums` diya gaya hai. Har element do baar aata hai, sirf ek ko chhodkar. Aapko woh single unique element find karna hai. Solution linear time complexity mein hona chahiye aur minimum extra space use karna chahiye.

For example:

  * `nums = [2,2,1]` Output: `1`
  * `nums = [4,1,2,1,2]` Output: `4`

### Approach (How to do it)

Is problem ke liye sabse efficient tareeka **XOR bitwise operation** use karna hai.
**XOR ki khaasiyat:**

  * `A XOR 0 = A`
  * `A XOR A = 0`
  * `XOR commutative aur associative hota hai`

Agar aap saare elements ka XOR karoge, toh jo numbers do baar aayenge, woh ek doosre ko cancel out kar denge (`A XOR A = 0`). Aur jo single number hai, woh `0 XOR SingleNumber = SingleNumber` ki wajah se bach jayega.

### Solution (The Way to Solve)

Hum **XOR bitwise operator** ka use karte hain. XOR ki property hai ki ek number ko do baar ussi se XOR karne par 0 milta hai (`A ^ A = 0`), aur kisi number ko 0 se XOR karne par wahi number milta hai (`A ^ 0 = A`). Is logic ka use karke, jab hum array ke sabhi elements ka XOR lete hain, toh jo numbers do baar aate hain, woh ek doosre ko cancel kar dete hain (unka XOR 0 ho jata hai). Aur jo akela number hota hai, woh `0` ke saath XOR hone par wahi akela number return kar deta hai. Hum ek `result` variable ko `0` se initialize karte hain aur phir array ke har element ka `XOR` `result` ke saath lete jaate hain. End mein `result` hi hamara single unique number hoga.

### Code

```java
class Solution {
    public int singleNumber(int[] nums) {
        // Ek variable ko initialize karo single number store karne ke liye.
        // XOR ka identity element 0 hota hai (any_number ^ 0 = any_number).
        int singleNumber = 0;

        // Array ke har number par iterate karo.
        for (int num : nums) {
            // Current number ko singleNumber variable ke saath XOR karo.
            // XOR ki properties:
            // 1. A ^ A = 0 (Kisi number ko khud se XOR karne par 0 milta hai)
            // 2. A ^ 0 = A (Kisi number ko 0 se XOR karne par wahi number milta hai)
            // In properties ki wajah se, jo numbers do baar aate hain woh
            // ek doosre ko cancel kar denge (0 ban jayenge), aur sirf unique number bachega.
            singleNumber ^= num;
        }

        // singleNumber mein bacha hua value hi unique element hai.
        return singleNumber;
    }
}
```

-----

## 191\. Number of 1 Bits

**Number of 1 Bits** problem mein aapko ek unsigned integer mein kitne '1' bits hain woh count karna hota hai.

-----

### Description/Overview

Aapko ek unsigned integer `n` diya gaya hai. Aapko uske binary representation mein '1' (set bits) ki sankhya count karke return karni hai. Java mein `int` signed hota hai, lekin hum is problem ko unsigned interpretation mein solve karte hain.

For example:

  * `n = 00000000000000000000000000001011` (binary of 11) Output: `3`
  * `n = 11111111111111111111111111111101` (binary of -3) Output: `31`

### Approach (How to do it)

Is problem ko solve karne ke kai tarike hain. Sabse efficient mein se ek hai **Brian Kernighan's Algorithm**.

**Brian Kernighan's Algorithm:**
Har iteration mein, yeh algorithm number ke least significant set bit ko remove karta hai (`n = n & (n - 1)`). Jab tak `n` 0 nahi ho jata, tab tak loop chalta hai aur `count` badhta jaata hai.

**Example of `n & (n - 1)`:**

  * Agar `n = 12` (binary `1100`)
      * `n - 1 = 11` (binary `1011`)
      * `n & (n - 1) = 1100 & 1011 = 1000` (Rightmost '1' hata diya gaya)
  * Agar `n = 8` (binary `1000`)
      * `n - 1 = 7` (binary `0111`)
      * `n & (n - 1) = 1000 & 0111 = 0000` (Last '1' hata diya gaya)

### Solution (The Way to Solve)

Hum **Brian Kernighan's algorithm** ka use karte hain jo '1' bits ko count karne ka ek bahut efficient tarika hai. Ek `count` variable ko `0` se initialize karte hain. Ek `while` loop chalate hain jab tak `n` `0` nahi ho jaata. Har iteration mein, `n = n & (n - 1)` operation perform karte hain. Yeh operation `n` ke least significant (sabse daayein wala) set bit (`1`) ko unset (0) kar deta hai. Jab bhi ek `1` bit unset hota hai, `count` ko increment karte hain. Jab `n` `0` ho jaata hai, iska matlab saare `1` bits count ho gaye hain, aur `count` return karte hain. Yeh method `n` mein jitne `1` bits hain, utni hi baar loop chalata hai.

### Code

```java
public class Solution {
    // n ko unsigned value ki tarah treat karna hai
    public int hammingWeight(int n) {
        int count = 0;

        // Brian Kernighan's Algorithm
        // Yeh algorithm baar-baar least significant set bit ko 0 karta hai
        // aur count badhata hai. Yeh theek utni hi baar chalta hai jitne set bits hote hain.
        while (n != 0) {
            // n & (n - 1) operation least significant bit ko unset kar deta hai.
            // Example: n = 12 (binary 1100)
            // n - 1 = 11 (binary 1011)
            // n & (n - 1) = 1100 & 1011 = 1000 (Sabse right wala '1' remove ho gaya)
            // Agar n = 8 (binary 1000)
            // n - 1 = 7 (binary 0111)
            // n & (n - 1) = 1000 & 0111 = 0000 (Aakhri '1' remove ho gaya)
            n = n & (n - 1);
            count++;
        }

        return count;
    }
}
```

-----

## 338\. Counting Bits

**Counting Bits** problem mein aapko 0 se `n` tak har number mein kitne '1' bits hain woh calculate karke ek array mein store karna hota hai.

-----

### Description/Overview

Aapko ek non-negative integer `n` diya gaya hai. Aapko ek array `ans` return karna hai jahan `ans[i]` mein `i` (for `0 <= i <= n`) ki binary representation mein number of '1's honge.

For example:

  * `n = 2` Output: `[0,1,1]`
  * `n = 5` Output: `[0,1,1,2,1,2]`

### Approach (How to do it)

Is problem ko **dynamic programming (DP)** ya bit manipulation patterns ka use karke $O(N)$ time mein solve kar sakte hain.

**Key Idea (Dynamic Programming):**
Bits ke pattern ko observe karo:

  * `dp[0] = 0`
  * `dp[1] = dp[0] + 1 = 1`
  * `dp[2] = dp[0] + 1 = 1` (2 is 10, 0 is 0, plus a leading 1)
  * `dp[3] = dp[1] + 1 = 2` (3 is 11, 1 is 1, plus a leading 1)

Ek pattern nikalta hai: `dp[i] = dp[i / 2] + (i % 2)`.
`i / 2` effectively `i` ko ek bit se right shift karta hai. `i % 2` uska last bit deta hai.
Example:
`dp[5]`: `5` is `101`. `5 / 2 = 2` (`10`). `5 % 2 = 1`.
`dp[5] = dp[2] + 1`. `dp[2]` 1 hai (`10`), plus last bit 1, so `dp[5]` 2 hai. Yeh kaam karta hai\!

### Solution (The Way to Solve)

Is problem ko solve karne ke liye, hum **dynamic programming** ka use karte hain jo $O(N)$ time complexity mein solve karta hai. Ek `dp` array banate hain jahan `dp[i]` `i` mein '1' bits ki sankhya ko store karega.
Base case `dp[0] = 0` hai.
Phir, `i` ko 1 se `n` tak iterate karte hain. `dp[i]` ko calculate karne ke liye `dp[i / 2]` (jo `i` ko ek bit se right shift karne ke baad '1' bits ki sankhya hai) aur `i % 2` (jo `i` ka last bit hai) ka sum lete hain.
For example:
`i = 5` (binary `101`)
`i / 2 = 2` (binary `10`)
`i % 2 = 1`
Toh, `dp[5] = dp[2] + 1`. Kyunki `dp[2]` 1 hai, `dp[5]` 2 hoga.
End mein `dp` array ko return karte hain.

### Code

```java
class Solution {
    public int[] countBits(int n) {
        // Ek DP array banao jo 0 se n tak har number ke liye 1s ki sankhya store karega.
        int[] dp = new int[n + 1];

        // Base case: 0 mein 1s ki sankhya 0 hai.
        dp[0] = 0;

        // 1 se n tak iterate karo.
        for (int i = 1; i <= n; i++) {
            // 'i' mein 1s ki sankhya 'i / 2' se derive ki ja sakti hai.
            // Jab hum 'i' ko ek bit se right shift karte hain (yani, i / 2), toh
            // bache hue part mein 1s ki sankhya dp[i / 2] hoti hai.
            // Phir hum 1 add karte hain agar original number 'i' odd tha (iska matlab uska last bit 1 tha).
            // Yeh dp[i] = dp[i >> 1] + (i & 1); ke barabar hai.
            dp[i] = dp[i / 2] + (i % 2);
        }

        return dp;
    }
}
```

-----

## 190\. Reverse Bits

**Reverse Bits** problem mein aapko ek 32-bit unsigned integer ke bits ko reverse karna hota hai.

-----

### Description/Overview

Aapko ek 32-bit unsigned integer `n` diya gaya hai. Aapko uske bits ko reverse karna hai. Example ke liye, agar binary representation `000...0001011` hai, toh reverse karne ke baad `1101000...000` hona chahiye.

For example:

  * `n = 00000010100101000001111010011100` (binary input)
      * Output: `00111001011110000010100101000000` (reversed binary)

### Approach (How to do it)

Bits ko reverse karne ke liye, hum input integer `n` ko left se right iterate karte hain (ya right se left), aur har bit ko result mein sahi position par place karte hain.

**Algorithm:**

1.  `result = 0` se initialize karo.
2.  32 baar loop karo (ek 32-bit integer ke har bit ke liye):
      * **Result ko Left Shift karo:** `result = result << 1`. (Result ko ek position left shift karo naye bit ke liye space banane ke liye).
      * **`n` ka Last Bit Check karo:** `lastBit = n & 1`. (n ka least significant bit extract karo).
      * **Result ka Last Bit Set karo:** `result = result | lastBit`. (Extracted bit ko result ke least significant position par set karo).
      * **`n` ko Right Shift karo:** `n = n >> 1`. (n ko ek position right shift karo agle bit ko process karne ke liye).
3.  `result` return karo.

### Solution (The Way to Solve)

Hum ek 32-bit integer ke bits ko ulta karne ke liye ek loop ka use karte hain jo 32 baar chalta hai (kyunki ek integer mein 32 bits hote hain).
Ek `reversedNum` naam ka variable `0` se initialize karte hain, jo hamara final ulta kiya hua number hoga.
Har iteration mein:

1.  `reversedNum` ko **left shift** karte hain ek position se (`reversedNum << 1`). Yeh naye bit ke liye space banata hai.
2.  Original number `n` ka **last bit** (`n & 1`) nikalte hain.
3.  Is nikale gaye bit ko `reversedNum` mein **add** karte hain (`reversedNum = reversedNum | (n & 1)`). Yeh nikale gaye bit ko `reversedNum` ke sabse right position par rakhta hai.
4.  `n` ko **right shift** karte hain ek position se (`n >>= 1`) taaki next iteration ke liye next bit ready ho sake.
    Loop ke end mein, `reversedNum` mein `n` ke bits ka ulta order hoga, jise hum return karte hain.

### Code

```java
public class Solution {
    // n ko unsigned value ki tarah treat karna hai
    public int reverseBits(int n) {
        int reversedNum = 0; // Reversed bits store karne ke liye variable initialize karo

        // Ek 32-bit integer mein 32 bits hote hain, toh hum 32 baar iterate karte hain.
        for (int i = 0; i < 32; i++) {
            // Step 1: `reversedNum` ko 1 se left shift karo. Yeh next bit ke liye space banata hai.
            // (e.g., 000 -> 0000)
            reversedNum <<= 1;

            // Step 2: Original number 'n' se last bit (least significant bit) extract karo.
            // Agar 'n' ka last bit 1 hai, (n & 1) 1 hoga; otherwise, 0 hoga.
            int lastBit = n & 1;

            // Step 3: `reversedNum` ke last bit ko extracted `lastBit` par set karo.
            // Bitwise OR ka use karte hain bit set karne ke liye.
            reversedNum |= lastBit;

            // Step 4: Original number 'n' ko 1 se right shift karo. Yeh next bit ko
            // least significant position par move karta hai agle iteration ke liye.
            n >>= 1;
        }

        return reversedNum; // Reversed bits ke saath integer return karo
    }
}
```

-----

## 268\. Missing Number

**Missing Number** problem mein aapko 0 se `n` tak numbers ke array mein se missing number find karna hota hai.

-----

### Description/Overview

Aapko ek array `nums` diya gaya hai jismein `n` distinct numbers hain jo range `[0, n]` mein hain. Aapko range mein se woh unique number return karna hai jo array mein missing hai.

For example:

  * `nums = [3,0,1]` Output: `2`
  * `nums = [9,6,4,2,3,5,7,0,1]` Output: `8`

### Approach (How to do it)

Is problem ko solve karne ke do main efficient tarike hain:

1.  **Summation (Gauss's Formula):**

      * 0 se `n` tak ke numbers ka expected sum calculate karo: `n * (n + 1) / 2`.
      * `nums` array ke numbers ka actual sum calculate karo.
      * Missing number `expectedSum - actualSum` hoga.
      * Pros: Simple, $O(N)$ time, $O(1)$ space.
      * Cons: Agar `n` bahut bada ho toh Integer overflow ho sakta hai.

2.  **XOR Operation:**

      * `XOR` property: `A XOR A = 0` and `A XOR 0 = A`.
      * 0 se `n` tak ke sabhi numbers ka XOR karo.
      * `nums` array ke sabhi numbers ka XOR karo.
      * In dono results ka final XOR karne par missing number mil jayega.
      * Pros: Bitwise operation efficient hai, potential overflow ko summation se better handle karta hai. $O(N)$ time, $O(1)$ space.

Hum yahan **XOR approach** use karenge kyunki yeh more robust hai.

### Solution (The Way to Solve)

Is problem ko solve karne ke liye, hum **XOR bitwise operator** ka use karte hain. XOR ki properties hain ki `A ^ A = 0` (ek hi number ko do baar XOR karne par 0 milta hai) aur `A ^ 0 = A` (kisi number ko 0 ke saath XOR karne par wahi number milta hai).
Hum `n` (array ki length) se `missing` variable ko initialize karte hain. Yeh isliye hai kyunki range `[0, n]` hai, matlab `n` bhi ek possible missing number ho sakta hai.
Phir, hum 0 se `n-1` tak ke indices ke saath-saath `nums` array ke elements ko ek hi loop mein XOR karte hain.
Loop mein `i` (`0` se `n-1` tak) ke liye:

1.  `missing` ko `i` ke saath XOR karte hain (yeh un expected numbers ke liye hai jo `0` se `n-1` tak hone chahiye).
2.  `missing` ko `nums[i]` ke saath XOR karte hain (yeh array mein present actual numbers ke liye hai).
    Is process ke end mein, `missing` variable mein woh unique number hoga jo range `[0, n]` mein hona chahiye tha lekin `nums` array mein nahi hai. Yeh method $O(N)$ time complexity aur $O(1)$ extra space use karta hai.

### Code

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;

        // XOR approach (more robust)

        // missing ko 'n' se initialize karo. Yeh us case ko cover karta hai jahan 'n' hi missing number hai.
        // Aur, yeh XOR chain set karta hai.
        int missing = n;

        // 0 se n-1 tak ke sabhi numbers (indices) aur nums mein sabhi numbers ko XOR karo.
        // Jo numbers do baar aate hain (ek baar index ke roop mein, ek baar nums mein number ke roop mein)
        // woh ek doosre ko cancel kar denge (x ^ x = 0).
        // Bacha hua value hi missing number hoga.
        for (int i = 0; i < n; i++) {
            missing ^= i;       // Expected number (index) ke saath XOR karo
            missing ^= nums[i]; // Array se actual number ke saath XOR karo
        }

        return missing; // 'missing' mein bacha hua value hi missing number hai
    }
}
```

-----

## 371\. Sum of Two Integers

**Sum of Two Integers** problem mein aapko do integers ka sum calculate karna hota hai without using `+` or `-` operators.

-----

### Description/Overview

Aapko do integers `a` aur `b` diye gaye hain. Aapko unka sum return karna hai, lekin `+` aur `-` operators ka use kiye bina.

For example:

  * `a = 1, b = 2` Output: `3`
  * `a = 2, b = 3` Output: `5`

### Approach (How to do it)

Is problem ko solve karne ke liye **bitwise operators** ka use karte hain. Sum ko carry aur sum of bits ke terms mein socha ja sakta hai, jaise computer hardware level par addition hota hai.

**Key Bitwise Operations:**

  * **XOR (`^`)**: Yeh bits ka sum calculate karta hai bina carry ko consider kiye. `0^0=0`, `1^1=0`, `0^1=1`, `1^0=1`. Yeh carry ke bina addition jaisa hai.
  * **AND (`&`)**: Yeh identify karta hai kahan carry generate hoga. `1&1=1` (carry), otherwise `0`.
  * **Left Shift (`<< 1`)**: Carry ko next bit position par shift karne ke liye.

### Solution (The Way to Solve)

Hum do integers `a` aur `b` ka sum calculate karne ke liye bitwise operators (`^` aur `&`) ka use karte hain, bina `+` ya `-` ka use kiye.
Ek `while` loop chalate hain jab tak `b` `0` nahi ho jata (yani, jab tak koi carry nahi bacha hai).
Har iteration mein:

1.  **Sum calculate karo (bina carry ke):** `sumWithoutCarry = a ^ b`. Yeh un bits ko add karta hai jahan dono mein se sirf ek bit 1 hai, jaise `0+1=1` ya `1+0=1`. Yeh `1+1=0` deta hai, carry ko ignore karta hai.
2.  **Carry calculate karo:** `carry = (a & b) << 1`. `a & b` उन bits ko 1 dega jahan `a` aur `b` dono mein 1 hai (yani, jahan carry generate hoga). Phir, isko left shift karte hain 1 se (`<< 1`) kyunki carry agli bit position par jaata hai.
3.  **`a` aur `b` ko update karo:** `a` ko `sumWithoutCarry` ke barabar set karte hain, aur `b` ko `carry` ke barabar set karte hain. Agli iteration mein, `carry` ko `sumWithoutCarry` mein add kiya jayega.
    Jab `b` `0` ho jaata hai, toh iska matlab hai ki sabhi carries ko add kiya ja chuka hai, aur `a` mein final sum hoga, jise hum return karte hain.

### Code

```java
class Solution {
    public int getSum(int a, int b) {
        // Tab tak iterate karo jab tak koi carry nahi bachta (b 0 ho jaye)
        while (b != 0) {
            // Carry ko consider kiye bina sum calculate karo.
            // Yeh bits ko XOR karke achieve kiya jata hai (0+0=0, 0+1=1, 1+0=1, 1+1=0).
            int sumWithoutCarry = a ^ b;

            // Carry calculate karo.
            // Yeh bits ko AND karke (1&1=1, otherwise 0) aur phir
            // result ko 1 se left-shift karke achieve kiya jata hai. Yeh carry ko next significant position par move karta hai.
            int carry = (a & b) << 1;

            // 'a' ko sum without carry ke barabar update karo next iteration ke liye.
            a = sumWithoutCarry;

            // 'b' ko calculated carry ke barabar update karo. Yeh carry
            // next iteration mein add kiya jayega.
            b = carry;
        }

        // Jab b 0 ho jata hai, iska matlab saare carries process aur add ho chuke hain.
        // 'a' mein ab final sum hai.
        return a;
    }
}
```

-----

## 7\. Reverse Integer

**Reverse Integer** problem mein aapko ek signed 32-bit integer ko reverse karna hota hai.

-----

### Description/Overview

Aapko ek signed 32-bit integer `x` diya gaya hai. Aapko `x` ke digits ko reverse karna hai.
Agar reversing `x` ke karan value signed 32-bit integer range `[-2^31, 2^31 - 1]` se bahar ho jati hai, toh `0` return karo.
Assume karo ki environment 64-bit integers ko store nahi kar sakta (like `long` type).

For example:

  * `x = 123` Output: `321`
  * `x = -123` Output: `  -321 `
  * `x = 2147483647` (Integer.MAX\_VALUE) Output: `0` (Overflow)

### Approach (How to do it)

Number ko reverse karne ke liye, hum modulus operator (`%`) se last digit extract karte hain aur division operator (`/`) se number ko short karte hain. **Overflow check har step par zaroori hai.**

**Algorithm:**

1.  `reversed = 0` se initialize karo.
2.  `while (x != 0)` loop chalao:
      * **Last Digit Extract karo:** `digit = x % 10`.
      * **Overflow Check (Bahut Zaroori\!):**
          * Agar `reversed > Integer.MAX_VALUE / 10` OR (`reversed == Integer.MAX_VALUE / 10` AND `digit > 7`): `0` return karo.
            (Kyunki `Integer.MAX_VALUE` 2147483647 hai, jo 7 par end hota hai).
          * Agar `reversed < Integer.MIN_VALUE / 10` OR (`reversed == Integer.MIN_VALUE / 10` AND `digit < -8`): `0` return karo.
            (Kyunki `Integer.MIN_VALUE` -2147483648 hai, jo 8 par end hota hai).
      * **Digit Add karo:** `reversed = reversed * 10 + digit`.
      * **`x` se Last Digit Remove karo:** `x /= 10`.
3.  `reversed` return karo.

### Solution (The Way to Solve)

Hum ek integer `x` ko reverse karne ke liye ek loop ka use karte hain.
Ek `reversed` naam ka variable `0` se initialize karte hain, jo hamara final reversed integer hoga.
Ek `while` loop chalate hain jab tak `x` `0` nahi ho jata.
Har iteration mein:

1.  **Last digit extract karo:** `digit = x % 10` ka use karke `x` ka last digit nikalte hain.
2.  **Overflow check karo:** Yeh sabse important step hai. `reversed` ko `10` se multiply karne se pehle, hum check karte hain ki kya yeh `Integer.MAX_VALUE` ya `Integer.MIN_VALUE` range se bahar chala jayega.
      * Agar `reversed` pehle se hi `Integer.MAX_VALUE / 10` se zyada hai, ya `Integer.MAX_VALUE / 10` ke barabar hai aur `digit` 7 se zyada hai, toh overflow hoga. Is case mein 0 return karte hain.
      * Isi tarah, `Integer.MIN_VALUE` ke liye bhi check karte hain (-2147483648). Agar `reversed` pehle se hi `Integer.MIN_VALUE / 10` se kam hai, ya `Integer.MIN_VALUE / 10` ke barabar hai aur `digit` -8 se kam hai, toh underflow hoga. Is case mein bhi 0 return karte hain.
3.  **Digit add karo:** `reversed = reversed * 10 + digit` ka use karke nikale gaye digit ko `reversed` mein add karte hain.
4.  **`x` se last digit remove karo:** `x = x / 10` ka use karke `x` ka last digit remove karte hain taaki next iteration ke liye preparation ho sake.
    Loop ke end mein, `reversed` mein reversed integer hoga, jise hum return karte hain.

### Code

```java
class Solution {
    public int reverse(int x) {
        int reversed = 0; // Reversed integer store karne ke liye variable initialize karo

        // Jab tak original number 0 nahi ho jata tab tak loop karo
        while (x != 0) {
            // x ka last digit extract karo
            int digit = x % 10;

            // Overflow check karo 'reversed' ko 10 se multiply karne se PEHLE.
            // Yeh 32-bit integer range ko handle karne ka crucial part hai.

            // Positive overflow check
            if (reversed > Integer.MAX_VALUE / 10 || (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0; // Overflow
            }
            // Negative overflow (underflow) check
            if (reversed < Integer.MIN_VALUE / 10 || (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0; // Underflow
            }
            // Note: Integer.MAX_VALUE hai 2147483647, toh uska last digit 7 hai.
            // Integer.MIN_VALUE hai -2147483648, toh uska last digit 8 hai.
            // Toh MAX_VALUE/10 ke liye, agar digit > 7 hai, toh overflow hota hai.
            // MIN_VALUE/10 ke liye, agar digit < -8 hai, toh underflow hota hai.

            // Extracted digit ko 'reversed' number mein add karo
            reversed = reversed * 10 + digit;

            // x se last digit remove karo
            x /= 10;
        }

        return reversed; // Reversed integer return karo
    }
}
```
