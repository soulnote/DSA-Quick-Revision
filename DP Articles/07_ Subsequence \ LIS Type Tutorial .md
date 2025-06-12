# Subsequence / LIS Type Tutorial 

**Subsequence / LIS (Longest Increasing Subsequence) type** problems ko pehchaanne ke liye kuch strong indicators hote hain. Ye problems mostly **non-contiguous elements** ke selection par based hoti hain **(order maintained but skipping allowed)**. Yahaan main detailed markers de raha hoon jo help karenge:

---

## 🔍 **Kaise Pehchaane ki Problem Subsequence / LIS Type hai?**

### ✅ **Key Clues (Checklist)**

| Marker                                      | Description                                                                                           |
| ------------------------------------------- | ----------------------------------------------------------------------------------------------------- |
| 📌 **"Subsequence" word**                   | Problem explicitly bolta hai: *"find length of longest subsequence..."*                               |
| ⏫ **"Increasing / Decreasing" requirement** | Subsequence ko increasing ya decreasing hone ka constraint diya ho                                    |
| 📈 **"Longest / Maximum" Length**           | Usually poocha jata hai: *longest increasing subsequence*, *maximum sum increasing subsequence*, etc. |
| 🧩 **Non-contiguous elements allowed**      | Tum kisi bhi index ke baad wale elements ko choose kar sakte ho, but **order maintain** hona chahiye  |
| 🔢 **1D array diya hota hai**               | Mostly problems ek integer array par based hoti hain                                                  |
| ♻️ **Choices for each element**             | Har element ke liye decide karna hota hai: "include ya skip?"                                         |
| 🔙 **Previous element ke upar decision**    | DP state mein often previous element ya index ka reference hota hai                                   |

---

## 🎯 **Common Variants of LIS-Type Problems**

| Problem                                          | Type                    |
| ------------------------------------------------ | ----------------------- |
| Longest Increasing Subsequence (LIS)             | Classic                 |
| Longest Decreasing Subsequence                   | Variation               |
| Maximum Sum Increasing Subsequence               | DP + LIS                |
| Number of LIS                                    | DP + Counting           |
| Longest Bitonic Subsequence                      | LIS + LDS               |
| Russian Doll Envelopes                           | 2D LIS                  |
| Building Bridges                                 | LIS after sorting       |
| Minimum Number of Deletions to make Array Sorted | Array size - LIS length |

---

## 🧠 **Intuition Behind LIS-Type Problems**

* Tum **sequence mein aage badh rahe ho**, aur har step pe soch rahe ho:

  * *kya main is element ko include karu subsequence mein?*
  * *kya main skip karu aur better element ka wait karu?*

* **DP state** generally hoti hai:

  * `dp[i] = length of LIS ending at index i`
  * Ya phir recursive form: `dp(i, prevIndex)`

---

## 🔄 **Visual Flow for Recognition**

```plaintext
Array diya hai?
      ↓
Kya question subsequence ki baat karta hai?
      ↓
Kya order maintain karna hai?
      ↓
Kya "increasing", "maximum", "longest" jaise words hain?
      ↓
→ Yes → Likely LIS-Type Problem
```

---

## 📌 Example:

**Q:** Given an array, find the length of the longest increasing subsequence.
➡️ Ye clearly LIS-Type hai.

**Q:** You are given envelopes with width and height. Find max number of envelopes you can nest.
➡️ Russian Doll — sort + LIS on height → LIS-Type

---

Hello dosto! Aaj hum **Subsequence / LIS Type** pattern ke baare mein detail mein baat karenge. Ye pattern tab use hota hai jab humein sequences (arrays ya lists) mein elements ko compare karna ho, aur order preserve karte hue subsequences find karni ho. Is tutorial mein hum saare example problems (Longest Increasing Subsequence, Number of LIS, Maximum Sum Increasing Subsequence, Longest Bitonic Subsequence, Russian Doll Envelopes) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with comments. Chalo shuru karte hain!

---

## Subsequence / LIS Type Kya Hai?
Ye pattern tab use hota hai jab humein ek sequence ke elements ke bich subsequences find karni ho, jisme order maintain rahe (elements ko same relative order mein lena hai, lekin consecutive hona zaroori nahi). Aksar ye problems increasing ya decreasing order ke subsequences ya unke variations pe based hote hain.

**Examples:**
1. Longest Increasing Subsequence (LIS)
2. Number of LIS
3. Maximum Sum Increasing Subsequence
4. Longest Bitonic Subsequence
5. Russian Doll Envelopes

**Common Theme:**
- Ek 1D ya 2D DP array use hota hai jisme states (index ya constraints) store hote hain.
- Har element ke liye decide karo: subsequence mein include karo ya nahi, based on order constraints.
- Optimal result (length, count, sum, ya complex conditions) chahiye.

---

## Intuition Kaise Build Kare?
Subsequence / LIS problems ko samajhne ke liye ye socho:
- Tum ek array ke elements ko dekhte ho, aur ek subsequence banani hai jo specific conditions (jaise increasing order) follow kare.
- Har element ke liye decide karo: isse subsequence mein add karo ya skip karo.
- Tumhe maximum length, sum, ya count chahiye.

For example:
- **Longest Increasing Subsequence** mein tumhe ek subsequence chahiye jisme har next element bada ho.
- **Russian Doll Envelopes** mein 2D constraints (width aur height) ke sath LIS solve karna hai.

**Key Intuition:**
- State define karo jo current index tak ka optimal subsequence bataye.
- Transition formula socho: current element ko include karne ka result vs skip karne ka result.
- Base cases set karo jo empty subsequence ya starting points handle kare.

---

## General Approach
Subsequence / LIS problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i]` ya `dp[i][j]` kya represent karta hai? For example, LIS mein `dp[i]` = length of LIS ending at index i.
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, LIS mein `dp[i] = max(dp[j] + 1)` for all j < i where nums[j] < nums[i].
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[i] = 1` (single element subsequence).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Number of LIS ya Russian Doll Envelopes.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Stack overflow ka risk bade inputs pe.
  - Function call overhead se thoda slow.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab saare subproblems solve karne pade.
  - Jab iteration se problem easily solve ho sake, jaise LIS ka standard solution.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for Subsequence / LIS:**
- **Bottom-Up** zyada common hai kyunki states predictable hote hain, aur iteration efficient hota hai, especially LIS ke liye.
- Top-Down use karo jab recursion se logic clear ho (jaise Number of LIS) ya complex constraints hain.

---

## Problem 1: Longest Increasing Subsequence (LIS)
### Problem Statement:
Ek array `nums` diya hai. Isme longest increasing subsequence (LIS) ki length find karo, jisme har next element strictly bada ho.

**Example:**
```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: LIS is [2,3,7,18] of length 4.
```

### Intuition:
- Har element ke liye check: isse subsequence mein add kar sakte hain ya nahi (agar pichla element chhota ho).
- State: `dp[i]` = length of LIS ending at index i.
- Transition: `dp[i] = max(dp[j] + 1)` for all j < i where nums[j] < nums[i].
- Base cases: `dp[i] = 1` (single element).

### Tree Diagram:
```
nums=[2,5,3], i=2 (nums[2]=3)
 /        \
Include   Skip
j=0 (2<3) j=1 (5<3)
dp[0]+1   N/A
```

### Bottom-Up Code:
```java
public class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        memo = new Integer[n][n+1];
        return lisHelper(nums, n-1, n);
    }
    
    private int lisHelper(int[] nums, int i, int prev) {
        if (i < 0) return 0;
        if (memo[i][prev] != null) return memo[i][prev];
        
        int skip = lisHelper(nums, i-1, prev);
        int take = 0;
        if (prev == nums.length || nums[i] < nums[prev]) {
            take = 1 + lisHelper(nums, i-1, i);
        }
        
        return memo[i][prev] = Math.max(take, skip);
    }
}
```

### Optimized Code (O(n log n)):
Using binary search to maintain an active list:
```java
public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int len = 0;
    
    for (int num : nums) {
        int left = 0, right = len;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (tails[mid] < num) left = mid + 1;
            else right = mid;
        }
        tails[left] = num;
        if (left == len) len++;
    }
    
    return len;
}
```

---

## Problem 2: Number of LIS
### Problem Statement:
Ek array `nums` diya hai. Kitne longest increasing subsequences hain?

**Example:**
```
Input: nums = [1,3,5,4,7]
Output: 2
Explanation: Two LIS: [1,3,5,7] and [1,3,4,7].
```

### Intuition:
- LIS ki length ke sath count bhi track karo.
- State: `dp[i]` = length of LIS ending at i, `count[i]` = number of such LIS.
- Transition: If `dp[j] + 1 == dp[i]`, add `count[j]` to `count[i]`.
- Base cases: `dp[i] = 1`, `count[i] = 1`.

### Bottom-Up Code:
```java
public class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        int maxLen = 1, result = 0;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] += count[j];
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        for (int i = 0; i < n; i++) {
            if (dp[i] == maxLen) result += count[i];
        }
        
        return result;
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[][] memoLen, memoCount;
    private int n, maxLen;
    
    public int findNumberOfLIS(int[] nums) {
        n = nums.length;
        memoLen = new int[n][n+1];
        memoCount = new int[n][n+1];
        for (int[] row : memoLen) Arrays.fill(row, -1);
        for (int[] row : memoCount) Arrays.fill(row, -1);
        maxLen = 0;
        lisHelper(nums, n-1, n);
        
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (lisLength(nums, i, n) == maxLen) {
                result += lisCount(nums, i, n);
            }
        }
        return result;
    }
    
    private int lisLength(int[] nums, int i, int prev) {
        if (i < 0) return 0;
        if (memoLen[i][prev] != -1) return memoLen[i][prev];
        
        int skip = lisLength(nums, i-1, prev);
        int take = 0;
        if (prev == n || nums[i] < nums[prev]) {
            take = 1 + lisLength(nums, i-1, i);
        }
        
        maxLen = Math.max(maxLen, Math.max(take, skip));
        return memoLen[i][prev] = Math.max(take, skip);
    }
    
    private int lisCount(int[] nums, int i, int prev) {
        if (i < 0) return 0;
        if (memoCount[i][prev] != -1) return memoCount[i][prev];
        
        if (lisLength(nums, i, prev) != maxLen) return 0;
        
        int skip = lisCount(nums, i-1, prev);
        int take = 0;
        if (prev == n || nums[i] < nums[prev]) {
            if (lisLength(nums, i-1, i) + 1 == maxLen) {
                take = lisCount(nums, i-1, i);
            }
        }
        
        return memoCount[i][prev] = take + skip;
    }
}
```

---

## Problem 3: Maximum Sum Increasing Subsequence
### Problem Statement:
Ek array `nums` diya hai. Increasing subsequence ka maximum sum find karo.

**Example:**
```
Input: nums = [1,101,2,3,100,4,5]
Output: 106
Explanation: Subsequence [1,2,3,100] sums to 106.
```

### Intuition:
- Har element ke liye check: increasing subsequence mein add karo ya nahi.
- State: `dp[i]` = max sum of increasing subsequence ending at i.
- Transition: `dp[i] = max(dp[j] + nums[i])` for j < i where nums[j] < nums[i].
- Base cases: `dp[i] = nums[i]`.

### Bottom-Up Code:
```java
public class Solution {
    public int maxSumIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) dp[i] = nums[i];
        int maxSum = nums[0];
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + nums[i]);
                }
            }
            maxSum = Math.max(maxSum, dp[i]);
        }
        
        return maxSum;
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int maxSumIS(int[] nums) {
        int n = nums.length;
        memo = new Integer[n][n+1];
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            maxSum = Math.max(maxSum, maxSumHelper(nums, i, n));
        }
        return maxSum;
    }
    
    private int maxSumHelper(int[] nums, int i, int prev) {
        if (i < 0) return 0;
        if (memo[i][prev] != null) return memo[i][prev];
        
        int skip = maxSumHelper(nums, i-1, prev);
        int take = 0;
        if (prev == nums.length || nums[i] < nums[prev]) {
            take = nums[i] + maxSumHelper(nums, i-1, i);
        }
        
        return memo[i][prev] = Math.max(take, skip);
    }
}
```

---

## Problem 4: Longest Bitonic Subsequence
### Problem Statement:
Ek array `nums` diya hai. Longest bitonic subsequence ki length find karo (pehle increasing, phir decreasing).

**Example:**
```
Input: nums = [1,11,2,10,4,5,2,1]
Output: 6
Explanation: [1,11,10,5,2,1] is a bitonic subsequence of length 6.
```

### Intuition:
- Bitonic subsequence = increasing subsequence + decreasing subsequence.
- State: `dp_inc[i]` = LIS ending at i, `dp_dec[i]` = LDS starting at i.
- Transition: Compute LIS and LDS separately, then combine.
- Base cases: `dp_inc[i] = 1`, `dp_dec[i] = 1`.

### Bottom-Up Code:
```java
public class Solution {
    public int longestBitonicSequence(int[] nums) {
        int n = nums.length;
        int[] dp_inc = new int[n];
        int[] dp_dec = new int[n];
        Arrays.fill(dp_inc, 1);
        Arrays.fill(dp_dec, 1);
        
        // Compute LIS
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp_inc[i] = Math.max(dp_inc[i], dp_inc[j] + 1);
                }
            }
        }
        
        // Compute LDS
        for (int i = n-2; i >= 0; i--) {
            for (int j = n-1; j > i; j--) {
                if (nums[j] < nums[i]) {
                    dp_dec[i] = Math.max(dp_dec[i], dp_dec[j] + 1);
                }
            }
        }
        
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, dp_inc[i] + dp_dec[i] - 1);
        }
        
        return maxLen;
    }
}
```

### Top-Down Code:
Complex for bitonic due to two-phase computation; bottom-up is preferred.

---

## Problem 5: Russian Doll Envelopes
### Problem Statement:
Ek 2D array `envelopes` diya hai jisme `[width, height]` pairs hain. Max number of envelopes find karo jo nested ho sakte hain (strictly increasing width aur height).

**Example:**
```
Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
Output: 3
Explanation: Envelopes: [2,3] -> [5,4] -> [6,7]
```

### Intuition:
- Sort envelopes by width (if equal, decreasing height).
- Apply LIS on height to find max nesting.
- State: Same as LIS, but on sorted height array.
- Transition: Same as LIS.

### Bottom-Up Code:
```java
public class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int n = envelopes.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
}
```

### Optimized Code (O(n log n)):
```java
public class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] tails = new int[envelopes.length];
        int len = 0;
        
        for (int[] env : envelopes) {
            int left = 0, right = len;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < env[1]) left = mid + 1;
                else right = mid;
            }
            tails[left] = env[1];
            if (left == len) len++;
        }
        
        return len;
    }
}
```

---

## Conclusion
Subsequence / LIS Type pattern bohot powerful hai jab order-preserved subsequences find karni ho. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai.
- Space optimization ya binary search use karo jab possible ho (jaise O(n log n) LIS).
