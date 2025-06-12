# Unbounded Knapsack Type Tutorial 

## 🔍 **Unbounded Knapsack Problem Pehchaanne ke Signs:**

---

### ✅ 1. **Har item ko **bar-bar** (multiple times) use karne ki ijazat ho**

> Problem explicitly ya implicitly bole:
> **"Aap ek item ko jitni baar chahein use kar sakte ho"**

**Example phrases**:

* “Infinite supply of coins/rods”
* “You can use an item any number of times”
* “You can cut the rod into multiple pieces of same length”
* “Repeat allowed”

---

### ✅ 2. **Target sum / capacity / total cost / weight optimize karna ho**

> Problem me aapko kuch **target banana ho** aur aapko allowed ho ki **same item ko repeat karke** uss target ko achieve karo.

**Typical Goals**:

* Min number of items
* Count of ways
* Max profit/value
* Whether target can be formed or not

---

### ✅ 3. **Knapsack state me i aur w (ya target) ho lekin i par repeat allowed ho**

* Normal 0/1 Knapsack me `dp[i][w]` me `i` next item me jaata hai (one-time use)
* Unbounded me `i` wahi par rahta hai (same item baar-baar allowed)

---

## 🧪 Examples Se Pehchaan:

---

### 🔸 1. **Coin Change (Minimum Coins)**

> Infinite supply of coins, target sum banana hai with min number of coins.

```java
dp[i][j] = min coins to make sum j using coins[0..i]
```

Same coin bar-bar use allowed hai → **Unbounded**

---

### 🔸 2. **Coin Change 2 (Number of Combinations)**

> Kitne tareeke se coins ko repeat karke target sum bana sakte ho?

---

### 🔸 3. **Rod Cutting Problem**

> Ek rod ko multiple pieces me kaat ke max profit nikaalo (piece ka length repeat ho sakta hai)

---

### 🔸 4. **Integer Break**

> Ek integer ko positive numbers me break karo — maximize product

```java
dp[n] = max(dp[n - i] * i)
```

Number i ko bar-bar use kar sakte ho → Unbounded feel

---

### 🔸 5. **Max Ribbon Cut**

> Ribbon ko cut karo given sizes me — max number of pieces.

---

## 🔁 Comparison Table:

| Feature                  | 0/1 Knapsack | Unbounded Knapsack |
| ------------------------ | ------------ | ------------------ |
| Har item ek baar         | ✅ Yes        | ❌ No               |
| Har item bar-bar         | ❌ No         | ✅ Yes              |
| Transition               | `dp[i+1][w]` | `dp[i][w]`         |
| Coin/Rod/Integer related | ❌ Not always | ✅ Mostly           |

---

## 🧠 Transition Formula:

```java
// Unbounded Knapsack
if (wt[i] <= W)
    dp[i][W] = max(dp[i][W], val[i] + dp[i][W - wt[i]]);
```

Note: `i` doesn’t increase → item repeat allowed.

---
Thanks for the continued trust! As requested, I’ll provide a detailed Hinglish tutorial for the **Unbounded Knapsack Type** pattern, covering all the example problems (Coin Change (Min Coins), Coin Change 2 (Count Ways), Rod Cutting, Integer Break, Maximum Ribbon Cut). I’ll include the intuition, approach, top-down vs. bottom-up comparison, tree diagram where needed, and Java code with comments for each problem, following the same structure as before. Next time, just give the pattern name, and I’ll deliver the same format with all example problems solved.

Given your preference for clear explanations and code for all problems (from our previous discussions), I’ll ensure each problem is thoroughly explained with both top-down and bottom-up approaches, optimized code where applicable, and recursion trees for clarity.

---



---

## Unbounded Knapsack Type Kya Hai?
Ye pattern tab use hota hai jab humein ek set of items diya jata hai, aur har item ko **kitni baar bhi choose kar sakte hain**. Har item ka ek weight (ya value, jaise coin denomination) hota hai, aur humein ek constraint ke under optimal result find karna hota hai (jaise minimum coins, max value, ya number of ways).

**Examples:**
1. Coin Change (Min Coins)
2. Coin Change 2 (Count Ways)
3. Rod Cutting
4. Integer Break
5. Maximum Ribbon Cut

**Common Theme:**
- Ek 2D ya 1D DP array use hota hai jisme states (item index, remaining capacity) store hote hain.
- Har item ko multiple times use kar sakte hain, to include karne ke baad bhi same item consider karte hain.
- Optimal result (min, max, ya count) chahiye.

---

## Intuition Kaise Build Kare?
Unbounded Knapsack problems ko samajhne ke liye ye socho:
- Tum ek dukaan pe ho, aur ek bag hai jiska weight limit hai.
- Har item ko baar-baar daal sakte ho.
- Har decision (item lena ya nahi) future ke results ko affect karta hai, aur tumhe best possible outcome chahiye.

For example:
- **Coin Change (Min Coins)** mein tumhe minimum coins chahiye jo target amount banaye.
- **Coin Change 2** mein tumhe ways count karne hain jo target amount banaye.

**Key Intuition:**
- State define karo jo current item index aur remaining capacity bataye.
- Transition formula socho: item include karne ka result (same item fir se consider) vs exclude karne ka result (next item).
- Base cases set karo jo empty set ya zero capacity handle kare.

---

## General Approach
Unbounded Knapsack problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][w]` kya represent karta hai? For example, Coin Change mein `dp[i][w]` = min coins for amount w using first i coins.
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, `dp[i][w] = min(dp[i][w], dp[i][w-coins[i-1]] + 1)` for Coin Change.
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[i][0] = 0` (no coins for amount 0).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Coin Change 2 jaisa problem.
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
  - Jab iteration se problem easily solve ho sake.
- **Pros:**
  - No recursion, no stack overflow.
  - Faster kyunki function calls nahi.
- **Cons:**
  - Code thoda complex lag sakta hai.
  - Unnecessary subproblems bhi solve ho sakte hain.

**Recommendation for Unbounded Knapsack:**
- **Bottom-Up** zyada common hai kyunki states predictable hote hain, aur iteration efficient hota hai, especially jab items ko multiple times use karna ho.
- Top-Down use karo jab recursion se logic clear ho ya subproblems kam hain.

---

## Problem 1: Coin Change (Min Coins)
### Problem Statement:
Ek array `coins` aur target amount `amount` diya hai. Minimum coins find karo jo amount banaye. Har coin ko unlimited times use kar sakte ho.

**Example:**
```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

### Intuition:
- Har coin ke liye decide: kitni baar use karna hai.
- State: `dp[i][w]` = min coins for amount w using first i coins.
- Transition: `dp[i][w] = min(dp[i-1][w], dp[i][w-coins[i-1]] + 1)`.
- Base cases: `dp[i][0] = 0`, invalid cases = infinity.

### Tree Diagram:
```
amount=11, i=3 (coin=5)
 /           \
Use 5        Skip
amount=6     amount=11
dp[3][6]+1   dp[2][11]
```
Har node pe coin use karo ya skip karo.

### Bottom-Up Code:
```java
public class Solution {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n+1][amount+1];
        
        // Base case: amount = 0 needs 0 coins
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        // Invalid case: amount > 0 with no coins
        for (int w = 1; w <= amount; w++) dp[0][w] = Integer.MAX_VALUE;
        
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= amount; w++) {
                dp[i][w] = dp[i-1][w]; // Skip coin
                if (coins[i-1] <= w && dp[i][w-coins[i-1]] != Integer.MAX_VALUE) {
                    dp[i][w] = Math.min(dp[i][w], 1 + dp[i][w-coins[i-1]]); // Use coin
                }
            }
        }
        
        return dp[n][amount] == Integer.MAX_VALUE ? -1 : dp[n][amount];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        memo = new Integer[n+1][amount+1];
        int result = coinChangeHelper(coins, amount, n);
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private int coinChangeHelper(int[] coins, int w, int i) {
        if (w == 0) return 0;
        if (i == 0) return Integer.MAX_VALUE;
        if (memo[i][w] != null) return memo[i][w];
        
        memo[i][w] = coinChangeHelper(coins, w, i-1); // Skip coin
        if (coins[i-1] <= w) {
            int useCoin = coinChangeHelper(coins, w-coins[i-1], i);
            if (useCoin != Integer.MAX_VALUE) {
                memo[i][w] = Math.min(memo[i][w], 1 + useCoin); // Use coin
            }
        }
        
        return memo[i][w];
    }
}
```

### Space Optimization:
Since we reuse coins, we can use a 1D array:
```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount+1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int w = 1; w <= amount; w++) {
        for (int coin : coins) {
            if (coin <= w && dp[w-coin] != Integer.MAX_VALUE) {
                dp[w] = Math.min(dp[w], 1 + dp[w-coin]);
            }
        }
    }
    
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
}
```

---

## Problem 2: Coin Change 2 (Count Ways)
### Problem Statement:
Ek array `coins` aur target amount `amount` diya hai. Kitne ways mein coins use karke amount bana sakte hain? Har coin unlimited times use kar sakte ho.

**Example:**
```
Input: amount = 5, coins = [1,2,5]
Output: 4
Explanation: Ways: [1,1,1,1,1], [1,1,1,2], [1,2,2], [5]
```

### Intuition:
- Har coin ke liye decide: kitni baar use karna hai.
- State: `dp[i][w]` = number of ways to make amount w using first i coins.
- Transition: `dp[i][w] = dp[i-1][w] + dp[i][w-coins[i-1]]`.
- Base cases: `dp[i][0] = 1` (empty combination).

### Bottom-Up Code:
```java
public class Solution {
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n+1][amount+1];
        
        // Base case: amount = 0 has 1 way
        for (int i = 0; i <= n; i++) dp[i][0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= amount; w++) {
                dp[i][w] = dp[i-1][w]; // Skip coin
                if (coins[i-1] <= w) {
                    dp[i][w] += dp[i][w-coins[i-1]]; // Use coin
                }
            }
        }
        
        return dp[n][amount];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int change(int amount, int[] coins) {
        int n = coins.length;
        memo = new Integer[n+1][amount+1];
        return changeHelper(coins, amount, n);
    }
    
    private int changeHelper(int[] coins, int w, int i) {
        if (w == 0) return 1;
        if (i == 0) return 0;
        if (memo[i][w] != null) return memo[i][w];
        
        memo[i][w] = changeHelper(coins, w, i-1); // Skip coin
        if (coins[i-1] <= w) {
            memo[i][w] += changeHelper(coins, w-coins[i-1], i); // Use coin
        }
        
        return memo[i][w];
    }
}
```

### Space Optimization:
```java
public int change(int amount, int[] coins) {
    int[] dp = new int[amount+1];
    dp[0] = 1;
    
    for (int coin : coins) {
        for (int w = coin; w <= amount; w++) {
            dp[w] += dp[w-coin];
        }
    }
    
    return dp[amount];
}
```

---

## Problem 3: Rod Cutting
### Problem Statement:
Ek rod ki length `n` hai, aur ek array `price` diya hai jisme `price[i]` i-length ke piece ka price hai. Max price find karo jo rod cut karke mil sakta hai.

**Example:**
```
Input: price = [1,5,8,9], n = 4
Output: 10
Explanation: Cut into two pieces of length 2 (5+5=10).
```

### Intuition:
- Har cut ke liye decide: kitni length ka piece cut karna hai.
- State: `dp[i][w]` = max price for length w using first i pieces.
- Transition: `dp[i][w] = max(dp[i-1][w], dp[i][w-i] + price[i-1])`.
- Base cases: `dp[i][0] = 0`.

### Bottom-Up Code:
```java
public class Solution {
    public int rodCutting(int[] price, int n) {
        int[][] dp = new int[price.length+1][n+1];
        
        for (int i = 1; i <= price.length; i++) {
            for (int w = 1; w <= n; w++) {
                dp[i][w] = dp[i-1][w]; // Skip piece
                if (i <= w) {
                    dp[i][w] = Math.max(dp[i][w], dp[i][w-i] + price[i-1]); Pragma: System;
                    dp[i][w] = Math.max(dp[i][w], dp[i][w-i] + price[i-1]); // Use piece
                }
            }
        }
        
        return dp[price.length][n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int rodCutting(int[] price, int n) {
        memo = new Integer[price.length+1][n+1];
        return rodCuttingHelper(price, n, price.length);
    }
    
    private int rodCuttingHelper(int[] price, int w, int i) {
        if (w == 0 || i == 0) return 0;
        if (memo[i][w] != null) return memo[i][w];
        
        memo[i][w] = rodCuttingHelper(price, w, i-1); // Skip piece
        if (i <= w) {
            memo[i][w] = Math.max(memo[i][w], price[i-1] + rodCuttingHelper(price, w-i, i)); // Use piece
        }
        
        return memo[i][w];
    }
}
```

### Space Optimization:
```java
public int rodCutting(int[] price, int n) {
    int[] dp = new int[n+1];
    
    for (int i = 1; i <= price.length; i++) {
        for (int w = i; w <= n; w++) {
            dp[w] = Math.max(dp[w], dp[w-i] + price[i-1]);
        }
    }
    
    return dp[n];
}
```

---

## Problem 4: Integer Break
### Problem Statement:
Ek positive integer `n` diya hai. Isse at least two positive integers mein break karo aur unka product maximize karo.

**Example:**
```
Input: n = 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
```

### Intuition:
- Har number ke liye decide: kaha break karna hai.
- State: `dp[i]` = max product for number i.
- Transition: `dp[i] = max(j * dp[i-j], j * (i-j))` for j from 1 to i-1.
- Base cases: `dp[2] = 1`.

### Bottom-Up Code:
```java
public class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * dp[i-j], j * (i-j)));
            }
        }
        
        return dp[n];
    }
}
```

###LifTop-Down Code:
```java
public class Solution {
    private Integer[] memo;
    
    public int integerBreak(int n) {
        memo = new Integer[n+1];
        return integerBreakHelper(n);
    }
    
    private int integerBreakHelper(int n) {
        if (n <= 2) return n == 2 ? 1 : 0;
        if (memo[n] != null) return memo[n];
        
        int maxProduct = 0;
        for (int j = 1; j < n; j++) {
            maxProduct = Math.max(maxProduct, Math.max(j * integerBreakHelper(n-j), j * (n-j)));
        }
        
        memo[n] = maxProduct;
        return maxProduct;
    }
}
```

### Optimized Code (Math Insight):
After observing patterns, breaking into 3s maximizes product (except for small n):
```java
public int integerBreak(int n) {
    if (n <= 3) return n == 2 ? 1 : n-1;
    
    int threes = n / 3;
    int remainder = n % 3;
    
    if (remainder == 0) return (int) Math.pow(3, threes);
    if (remainder == 1) return (int) Math.pow(3, threes-1) * 4;
    return (int) Math.pow(3, threes) * 2;
}
```

---

## Problem 5: Maximum Ribbon Cut
### Problem Statement:
Ek ribbon ki length `n` hai, aur ek array `lengths` diya hai jisme possible cut lengths hain. Max number of cuts find karo jo ribbon se bana sakte hain.

**Example:**
```
Input: n = 7, lengths = [2,3,5]
Output: 3
Explanation: Cut into [2,2,3].
```

### Intuition:
- Har cut ke liye decide: kaunsi length use karna hai.
- State: `dp[i][w]` = max cuts for length w using first i lengths.
- Transition: `dp[i][w] = max(dp[i-1][w], 1 + dp[i][w-lengths[i-1]])`.
- Base cases: `dp[i][0] = 0`, invalid cases = -1.

### Bottom-Up Code:
```java
public class Solution {
    public int maxRibbonCut(int[] lengths, int n) {
        int m = lengths.length;
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 0; i <= m; i++) {
            for (int w = 0; w <= n; w++) {
                dp[i][w] = -1;
            }
        }
        for (int i = 0; i <= m; i++) dp[i][0] = 0;
        
        for (int i = 1; i <= m; i++) {
            for (int w = 1; w <= n; w++) {
                dp[i][w] = dp[i-1][w]; // Skip length
                if (lengths[i-1] <= w && dp[i][w-lengths[i-1]] !=Rust: System;
                    dp[i][w] = Math.max(dp[i][w], 1 + dp[i][w-lengths[i-1]]); // Use length
                }
            }
        }
        
        return dp[m][n] == -1 ? 0 : dp[m][n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int maxRibbonCut(int[] lengths, int n) {
        int m = lengths.length;
        memo = new Integer[m+1][n+1];
        for (int i = 0; i <= m; i++) Arrays.fill(memo[i], -1);
        int result = maxRibbonCutHelper(lengths, n, m);
        return result == -1 ? 0 : result;
    }
    
    private int maxRibbonCutHelper(int[] lengths, int w, int i) {
        if (w == 0) return 0;
        if (i == 0) return Integer.MIN_VALUE;
        if (memo[i][w] != -1) return memo[i][w];
        
        memo[i][w] = maxRibbonCutHelper(lengths, w, i-1); // Skip length
        if (lengths[i-1] <= w) {
            int use = maxRibbonCutHelper(lengths, w-lengths[i-1], i);
            if (use != Integer.MIN_VALUE) {
                memo[i][w] = Math.max(memo[i][w], 1 + use); // Use length
            }
        }
        
        return memo[i][w];
    }
}
```

### Space Optimization:
```java
public int maxRibbonCut(int[] lengths, int n) {
    int[] dp = new int[n+1];
    Arrays.fill(dp, -1);
    dp[0] = 0;
    
    for (int w = 1; w <= n; w++) {
        for (int len : lengths) {
            if (len <= w && dp[w-len] != -1) {
                dp[w] = Math.max(dp[w], 1 + dp[w-len]);
            }
        }
    }
    
    return dp[n] == -1 ? 0 : dp[n];
}
```

---

## Conclusion
Unbounded Knapsack Type pattern bohot powerful hai jab items ko multiple times use karna ho. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai.
- Space optimization ke liye 1D array use karo jab possible ho.
