
## 62\. Unique Paths

**Unique Paths** ek classic Dynamic Programming problem hai jismein humein ek grid mein top-left se bottom-right tak pahunchne ke unique ways count karne hote hain.

-----

### Description/Overview

Imagine karo ek `m x n` grid hai. Ek robot top-left corner (`grid[0][0]`) par khada hai. Robot sirf do moves kar sakta hai: **right** ya **down**. Robot ka goal hai bottom-right corner (`grid[m-1][n-1]`) tak pahunchna. Aapko find karna hai ki total kitne **unique paths** hain jinse robot destination tak pahunch sakta hai.

For example:

  * `m = 3, n = 7` (3 rows, 7 columns)
      * Output: 28
  * `m = 3, n = 2`
      * Paths: (Right, Down, Down), (Down, Right, Down), (Down, Down, Right)
      * Output: 3

### Approach (How to do it)

This problem has optimal substructure and overlapping subproblems, making it suitable for Dynamic Programming.

1.  **Define `dp` table:**

      * `int[][] dp = new int[m][n]`
      * `dp[i][j]` represents the number of unique paths to reach cell `(i, j)`.

2.  **Base Cases:**

      * `dp[0][0] = 1` (Starting cell tak pahunchne ka 1 way hai, khud wahi se).
      * First row ke saare cells tak pahunchne ka 1 way hai (sirf right move karke): `dp[0][j] = 1` for all `j`.
      * First column ke saare cells tak pahunchne ka 1 way hai (sirf down move karke): `dp[i][0] = 1` for all `i`.

3.  **Recursive Relation:**

      * Kisi bhi cell `(i, j)` tak pahunchne ke liye, robot ya toh `(i-1, j)` (upar se down) se aaya hoga ya `(i, j-1)` (left se right) se aaya hoga.
      * So, `dp[i][j] = dp[i-1][j] + dp[i][j-1]` for `i > 0` and `j > 0`.

4.  **Final Result:** `dp[m-1][n-1]`.

**Space Optimization (Optional):**
Agar aapko `m` rows aur `n` columns diye gaye hain, toh aap `O(min(m, n))` space use kar sakte hain, ya sirf `O(n)` space use kar sakte hain, kyunki current row ke values calculate karne ke liye sirf previous row ke values chahiye hote hain.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai cell `(i, j)` tak pahunchne ke unique paths ki count. First row aur first column ke saare cells ko 1 se initialize karte hain, kyunki un tak pahunchne ka ek hi tarika hai (ya toh sirf right ya sirf down). Baki cells ke liye, `dp[i][j]` ko `dp[i-1][j] + dp[i][j-1]` ke sum se fill karte hain. Matlab, `(i, j)` tak pahunchne ke tareeqe woh hain jo `(i-1, j)` (upar wale cell) tak pahunchne ke tareeqe plus `(i, j-1)` (left wale cell) tak pahunchne ke tareeqe. Final answer `dp[m-1][n-1]` hoga.

### Code

```java
class Solution {
    public int uniquePaths(int m, int n) {
        // dp[i][j] represents the number of unique paths to reach cell (i, j)
        int[][] dp = new int[m][n];

        // Base cases:
        // Fill the first row: Only 1 way to reach any cell in the first row (by moving right)
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // Fill the first column: Only 1 way to reach any cell in the first column (by moving down)
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        // Fill the rest of the DP table
        // For any cell (i, j), robot can come from (i-1, j) or (i, j-1)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        // The result is the number of ways to reach the bottom-right corner (m-1, n-1)
        return dp[m - 1][n - 1];
    }
}

/*
// Space Optimized Solution (O(N) space, where N is smaller of m, n)
class Solution {
    public int uniquePaths(int m, int n) {
        // Ensure n is the smaller dimension for space optimization
        if (m < n) {
            int temp = m;
            m = n;
            n = temp;
        }

        // dp array represents a single row/column, size 'n' (number of columns)
        // dp[j] will store the number of ways to reach cell (current_row, j)
        int[] dp = new int[n];

        // Initialize the first row (or first column effectively)
        // All cells in the first row have 1 way to reach them
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
        }

        // Iterate from the second row (i = 1) up to m-1
        for (int i = 1; i < m; i++) {
            // For each cell in the current row, update its value
            // dp[j] for current_cell (i, j) = dp[j] (from cell (i-1, j)) + dp[j-1] (from cell (i, j-1))
            for (int j = 1; j < n; j++) { // j starts from 1 because dp[0] (first column) is always 1
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        // The result is the last element of the dp array (bottom-right corner)
        return dp[n - 1];
    }
}
*/
```

-----

## 1143\. Longest Common Subsequence

**Longest Common Subsequence (LCS)** ek Dynamic Programming problem hai jismein humein do strings ka longest common subsequence ki length find karni hoti hai.

-----

### Description/Overview

Aapko do strings `text1` aur `text2` diye gaye hain. Aapko return karna hai unke **longest common subsequence** ki length.
A subsequence is derived from a string by deleting some or no characters without changing the order of the remaining characters.
A common subsequence do strings mein present subsequence hoti hai.

For example:

  * `text1 = "abcde", text2 = "ace"`
      * LCS: `"ace"`
      * Output: 3
  * `text1 = "abc", text2 = "abc"`
      * LCS: `"abc"`
      * Output: 3
  * `text1 = "abc", text2 = "def"`
      * LCS: `""` (empty string)
      * Output: 0

### Approach (How to do it)

This is a very famous and fundamental DP problem.

1.  **Define `dp` table:**

      * `int[][] dp = new int[n1 + 1][n2 + 1]` where `n1 = text1.length()`, `n2 = text2.length()`.
      * `dp[i][j]` represents the length of the longest common subsequence of `text1[0...i-1]` and `text2[0...j-1]`.
      * We use `n1 + 1` and `n2 + 1` size to handle empty string cases (index 0).

2.  **Base Cases:**

      * First row and first column of `dp` table will be 0.
          * `dp[i][0] = 0` for all `i`.
          * `dp[0][j] = 0` for all `j`.
          * (Already initialized to 0 by default in Java for `int` array).

3.  **Recursive Relation:** Iterate `i` from `1` to `n1`, `j` from `1` to `n2`.

      * `char1 = text1.charAt(i-1)`
      * `char2 = text2.charAt(j-1)`
      * **Case 1: Characters match:** If `char1 == char2`
          * `dp[i][j] = 1 + dp[i-1][j-1]`
          * Explanation: Agar current characters match karte hain, toh woh common subsequence ka part hain. Toh LCS ki length 1 se badh jayegi, plus jo LCS `text1[0...i-2]` aur `text2[0...j-2]` mein tha.
      * **Case 2: Characters don't match:** If `char1 != char2`
          * `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`
          * Explanation: Agar characters match nahi karte, toh hum ya toh `text1` se `char1` ko ignore karke `text1[0...i-2]` aur `text2[0...j-1]` ka LCS lenge (`dp[i-1][j]`), ya `text2` se `char2` ko ignore karke `text1[0...i-1]` aur `text2[0...j-2]` ka LCS lenge (`dp[i][j-1]`). Jo maximum hoga woh lenge.

4.  **Final Result:** `dp[n1][n2]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai `text1` ke pehle `i` characters aur `text2` ke pehle `j` characters ka LCS length. `dp` table ke first row aur column ko 0 se initialize karte hain (empty string ke common subsequence ki length 0 hoti hai). Fir, nested loops chala kar `dp` table fill karte hain. Agar `text1.charAt(i-1)` aur `text2.charAt(j-1)` match karte hain, toh `dp[i][j]` ko `1 + dp[i-1][j-1]` set karte hain. Agar match nahi karte, toh `dp[i][j]` ko `max(dp[i-1][j], dp[i][j-1])` set karte hain. Final answer `dp[text1.length()][text2.length()]` hoga.

### Code

```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int n1 = text1.length();
        int n2 = text2.length();

        // dp[i][j] will store the length of the LCS of text1[0...i-1] and text2[0...j-1]
        // Size (n1+1) x (n2+1) to handle empty string prefixes (index 0)
        int[][] dp = new int[n1 + 1][n2 + 1];

        // Base cases: First row and first column are already 0 by default,
        // which correctly represents LCS with an empty string.

        // Fill the DP table
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // If characters match (current characters from text1 and text2)
                // Note: i-1 and j-1 are used to access actual characters in strings
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1]; // Add 1 to LCS of previous prefixes
                } else {
                    // If characters don't match, take the maximum of:
                    // 1. LCS without the current char from text1 (dp[i-1][j])
                    // 2. LCS without the current char from text2 (dp[i][j-1])
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // The final result is the LCS of the entire text1 and text2 strings
        return dp[n1][n2];
    }
}
```

-----

## 309\. Best Time to Buy and Sell Stock With Cooldown

**Best Time to Buy and Sell Stock With Cooldown** ek Dynamic Programming problem hai jismein hum stock prices ke saath maximum profit find karte hain, with a cooldown period.

-----

### Description/Overview

Aapko ek array `prices` diya gaya hai, jahan `prices[i]` hai `i-th` day par stock ka price. Aap maximum profit achieve karna chahte ho. Aap ek transaction (buy and sell) complete karne ke baad **ek din ka cooldown** lena hoga. Matlab, agar aap day `i` par sell karte ho, toh aap day `i+1` par buy nahi kar sakte. Aap ek baar mein sirf ek stock hold kar sakte ho.

For example:

  * `prices = [1, 2, 3, 0, 2]`
      * Output: 3
      * Transactions: `buy on day 0, sell on day 1 (profit 2-1=1)`
      * `buy on day 3 (price 0), sell on day 4 (price 2) (profit 2-0=2)`
      * Total profit = 1 + 2 = 3. Notice day 2 (price 3) is cooldown.

### Approach (How to do it)

This problem requires a slightly more complex DP state because of the cooldown. We need to know not just the max profit, but also the "state" we are in (holding a stock, not holding a stock, or on cooldown).

Let's define three states for each day `i`:

  * `dp[i][0]`: Maximum profit if we **do nothing** (no stock held, not on cooldown from a sell today, i.e., at end of day `i` we are "free" and can buy tomorrow).
  * `dp[i][1]`: Maximum profit if we **hold a stock** at the end of day `i`.
  * `dp[i][2]`: Maximum profit if we **are on cooldown** at the end of day `i` (meaning we sold today).

However, a simpler and more common approach defines states based on actions for *maximum profit*:

1.  **`buy[i]`**: Maximum profit ending on day `i` if you **buy** (or hold) a stock on day `i`.
2.  **`sell[i]`**: Maximum profit ending on day `i` if you **sell** (or are on cooldown) a stock on day `i`.

Let `n = prices.length`.

**Detailed DP states:**

  * `buy[i]`: Max profit ending on day `i` having just *bought* a stock, or *holding* a stock that was bought before.

      * To be in `buy[i]` state, you either:
          * Were already holding a stock on day `i-1` and did nothing today (`buy[i-1]`).
          * Bought a stock today (`prices[i]`). If you bought today, you must have been "free" (not holding, not on cooldown) on day `i-1` *after* a cooldown. So, `sell[i-2]` minus `prices[i]`.
      * `buy[i] = Math.max(buy[i-1], sell[i-2] - prices[i])`
      * Initialize `buy[0] = -prices[0]`. `buy[1] = Math.max(-prices[0], -prices[1])`

  * `sell[i]`: Max profit ending on day `i` having just *sold* a stock on day `i`, or are now on cooldown.

      * To be in `sell[i]` state, you either:
          * Were already in a sell/cooldown state on day `i-1` and did nothing today (`sell[i-1]`).
          * Sold a stock today (`prices[i]`). If you sold today, you must have been holding a stock on day `i-1`. So, `buy[i-1] + prices[i]`.
      * `sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i])`
      * Initialize `sell[0] = 0`. `sell[1] = Math.max(sell[0], buy[0] + prices[1])`

**Important Considerations:**

  * `sell[i-2]` for `buy[i]` means that to buy on day `i`, you must have sold on day `i-2` or earlier (cooldown on `i-1`).
  * Base cases for `buy` and `sell` arrays will need careful initialization, especially for `sell[i-2]` when `i` is 0 or 1.
      * `buy[0] = -prices[0]`
      * `sell[0] = 0` (no profit on day 0 if no stock held)
      * `buy[1] = Math.max(-prices[0], -prices[1])` (buy day 0, or buy day 1)
      * `sell[1] = Math.max(sell[0], buy[0] + prices[1])` (do nothing or sell on day 1 if bought on day 0)

A simpler approach with `buy`, `sell`, and `cooldown` variables (optimized space):

  * `buy`: Max profit if you end the day with a stock in hand.
  * `sell`: Max profit if you end the day with no stock in hand, AND you did NOT sell today (so no cooldown).
  * `cooldown`: Max profit if you end the day on cooldown (meaning you sold today).

Iterate `i` from `0` to `n-1`:

  * `prevBuy = buy`

  * `prevSell = sell`

  * `prevCooldown = cooldown`

  * `buy = Math.max(prevBuy, prevSell - prices[i])` (Either hold the stock, or buy it today (must have been in 'sell' state yesterday, as 'cooldown' means you cannot buy))

  * `sell = prevBuy + prices[i]` (You must have bought it yesterday to sell it today)

  * `cooldown = Math.max(prevSell, prevCooldown)` (You are either free from cooldown today and just continue with `prevSell` (free state) or you are still in cooldown state `prevCooldown`)

This can be simplified:
Let `hold[i]` be max profit if you **hold** a stock on day `i`.
Let `notHold[i]` be max profit if you **don't hold** a stock on day `i`.

`hold[i]`
\= `max( hold[i-1], notHold[i-1] - prices[i] )` (if we don't consider cooldown yet for `notHold`)

This needs a specific state for "just sold". Let's use `s0, s1, s2` from solutions.
`s0[i]`: max profit at day `i` *ending with no stock and not in cooldown*. This means you were either in `s0[i-1]` (rest) or `s2[i-1]` (just finished cooldown).
`s1[i]`: max profit at day `i` *ending with stock*. This means you were either in `s1[i-1]` (held) or `s0[i-1]` (bought today).
`s2[i]`: max profit at day `i` *ending with cooldown*. This means you sold stock today. So you must have been in `s1[i-1]` and sold `prices[i]`.

  * `s0[i] = Math.max(s0[i-1], s2[i-1])` (Either idle or just finished cooldown)
  * `s1[i] = Math.max(s1[i-1], s0[i-1] - prices[i])` (Either hold or buy today)
  * `s2[i] = s1[i-1] + prices[i]` (Must have sold today from holding state)

Base cases:

  * `s0[0] = 0` (no stock, no profit)
  * `s1[0] = -prices[0]` (bought stock, negative profit)
  * `s2[0] = Integer.MIN_VALUE` (cannot sell on day 0)

Final Answer: `Math.max(s0[n-1], s2[n-1])`. (Can't end in `s1` as we want max profit, which means selling.)

### Solution (The Way to Solve)

Yeh problem standard Buy/Sell Stock problems se thoda different hai cooldown ki wajah se. Hum teen states define karte hain har day `i` ke liye:

1.  `s0[i]`: Maximum profit jab day `i` par koi stock hold nahi kar rahe aur cooldown par bhi nahi hain (ya toh hum hamesha free the ya previous day cooldown khatam ho gaya).
2.  `s1[i]`: Maximum profit jab day `i` par stock hold kar rahe hain.
3.  `s2[i]`: Maximum profit jab day `i` par stock sell kiya hai aur ab cooldown par hain.

Transitions:

  * `s0[i] = Math.max(s0[i-1], s2[i-1])` : Aaj free hain agar kal bhi free the (`s0[i-1]`) ya kal cooldown khatam ho gaya (`s2[i-1]`).
  * `s1[i] = Math.max(s1[i-1], s0[i-1] - prices[i])` : Aaj stock hold kar rahe hain agar kal bhi hold kar rahe the (`s1[i-1]`) ya kal free the aur aaj buy kar liya (`s0[i-1] - prices[i]`).
  * `s2[i] = s1[i-1] + prices[i]` : Aaj sell kiya hai (cooldown mein) toh kal stock hold kar rahe the (`s1[i-1]`) aur aaj `prices[i]` par sell kiya.

Base cases `s0[0]=0`, `s1[0]=-prices[0]`, `s2[0]=Integer.MIN_VALUE` set karte hain. Finally, `max(s0[n-1], s2[n-1])` return karte hain. Space optimization ke liye, hum sirf `prevS0`, `prevS1`, `prevS2` variables use kar sakte hain.

### Code

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0; // Not enough days for profit or cooldown
        }

        // DP states:
        // s0[i]: max profit ending on day i, with no stock in hand and NOT on cooldown.
        //         (i.e., you were either rest or finished cooldown yesterday)
        // s1[i]: max profit ending on day i, with a stock in hand.
        //         (i.e., you either held the stock or bought it today)
        // s2[i]: max profit ending on day i, with no stock in hand, AND ON cooldown.
        //         (i.e., you must have sold the stock today)

        // Initialize for day 0
        int s0_prev = 0;             // Max profit if idle/rest on day -1 (base for day 0)
        int s1_prev = -prices[0];    // Max profit if you buy on day 0
        int s2_prev = Integer.MIN_VALUE; // Cannot sell on day 0 and be in cooldown

        // For loop from day 1 to n-1
        for (int i = 1; i < n; i++) {
            int current_s0 = Math.max(s0_prev, s2_prev); // Today is s0: came from s0 (rest) or s2 (cooldown over)
            int current_s1 = Math.max(s1_prev, s0_prev - prices[i]); // Today is s1: came from s1 (held) or s0 (bought today)
            int current_s2 = s1_prev + prices[i]; // Today is s2: must have sold from s1 yesterday

            s0_prev = current_s0;
            s1_prev = current_s1;
            s2_prev = current_s2;
        }

        // The maximum profit will be either from ending in s0 state (free)
        // or ending in s2 state (just sold and on cooldown).
        // s1 state (holding stock) cannot be the final max profit as we want to sell.
        return Math.max(s0_prev, s2_prev);
    }
}
```

-----

## 518\. Coin Change II

**Coin Change II** ek Dynamic Programming problem hai jismein humein given coins se ek amount banane ke unique combinations (ways) find karne hote hain.

-----

### Description/Overview

Aapko ek integer array `coins` diya gaya hai jo different denominations ke coins ko represent karta hai, aur ek integer `amount` diya gaya hai. Aapko return karna hai ki kitne **unique combinations** se `amount` ko un coins se banaya ja sakta hai. Agar `amount` ko un coins se banaya nahi ja sakta, toh `0` return karo.
Har type ke coin ko **kitni bhi baar** use kar sakte ho.
Order of coins does not matter (e.g., 1+2 is same as 2+1).

For example:

  * `amount = 5, coins = [1, 2, 5]`
      * Output: 4
      * Combinations:
          * 5 = 5
          * 5 = 2 + 2 + 1
          * 5 = 2 + 1 + 1 + 1
          * 5 = 1 + 1 + 1 + 1 + 1
  * `amount = 3, coins = [2]`
      * Output: 0
  * `amount = 10, coins = [10]`
      * Output: 1

### Approach (How to do it)

This is a classic DP problem, a variation of the unbounded knapsack problem or "number of ways to make change". The key is to avoid duplicate combinations by processing coins one by one.

1.  **Define `dp` array:**

      * `int[] dp = new int[amount + 1]`
      * `dp[j]` represents the number of ways to make sum `j`.

2.  **Base Case:**

      * `dp[0] = 1` (There's 1 way to make amount 0: by using no coins). This is crucial for correct counting.

3.  **Iterate and Update:**

      * Loop `coin` through each coin in `coins` array (Outer loop, for each coin type):
          * Loop `j` from `coin` to `amount` (Inner loop, for each possible sum):
              * `dp[j] = dp[j] + dp[j - coin]`
              * Explanation:
                  * `dp[j]` (current value) is the number of ways to make sum `j` *without* using the current `coin`.
                  * `dp[j - coin]` is the number of ways to make the remaining amount `j - coin`. Agar hum remaining amount `j - coin` bana sakte hain, toh usmein current `coin` add karke `j` amount ban jayega.
                  * By adding `dp[j - coin]` to `dp[j]`, hum current `coin` ko include karne ke ways add kar rahe hain.
              * The order of loops (`coins` outer, `amount` inner) ensures that we count combinations, not permutations. If the loops were swapped, `1+2` and `2+1` would be counted as separate.

4.  **Final Result:** `dp[amount]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek `dp` array banate hain jahan `dp[j]` ka matlab hai amount `j` banane ke unique combinations ki count. `dp[0]` ko 1 set karte hain (0 amount ko 1 tareeqe se bana sakte hain: koi coin use na karke). Outer loop `coins` array ke har coin par iterate karta hai. Inner loop `j` ko `coin` se `amount` tak iterate karta hai. `dp[j]` ko `dp[j] + dp[j - coin]` se update karte hain. Yeh current coin ko include karne ke ways ko add karta hai (amount `j - coin` tak ke ways mein current `coin` ko add karke). Outer loop coin-wise iteration ensures that combinations are counted uniquely (e.g., [1,2] is same as [2,1]). Final answer `dp[amount]` hoga.

### Code

```java
class Solution {
    public int change(int amount, int[] coins) {
        // dp[j] will store the number of ways to make sum 'j'
        int[] dp = new int[amount + 1];

        // Base case: There is 1 way to make amount 0 (by using no coins)
        dp[0] = 1;

        // Iterate through each coin denomination
        // This outer loop ensures that combinations are counted, not permutations.
        // For example, using coin 1 then coin 2 is treated the same as coin 2 then coin 1.
        for (int coin : coins) {
            // For each amount 'j' from 'coin' up to 'amount'
            for (int j = coin; j <= amount; j++) {
                // dp[j] = (ways to make 'j' without using current 'coin')
                //         + (ways to make 'j' by including current 'coin')
                // The ways to make 'j' by including current 'coin' are equal to
                // the ways to make the remaining amount (j - coin).
                dp[j] += dp[j - coin];
            }
        }

        // The final result is the number of ways to make the target 'amount'
        return dp[amount];
    }
}
```

-----

## 494\. Target Sum

**Target Sum** ek Dynamic Programming problem hai jismein humein ek array ke elements ko plus/minus sign dekar target sum banane ke ways count karne hote hain.

-----

### Description/Overview

Aapko ek integer array `nums` diya gaya hai aur ek integer `target` diya gaya hai. Aapko `nums` array ke har element ke aage ya toh `+` sign ya `-` sign lagana hai aur phir un sab elements ka sum karna hai. Aapko find karna hai ki kitne **expressions** bana sakte hain jinse final sum `target` ke barabar ho.

For example:

  * `nums = [1, 1, 1, 1, 1], target = 3`
      * Output: 5
      * Expressions:
          * \-1 + 1 + 1 + 1 + 1 = 3
          * \+1 - 1 + 1 + 1 + 1 = 3
          * \+1 + 1 - 1 + 1 + 1 = 3
          * \+1 + 1 + 1 - 1 + 1 = 3
          * \+1 + 1 + 1 + 1 - 1 = 3
  * `nums = [1], target = 1`
      * Output: 1 (only +1)

### Approach (How to do it)

This problem can be solved using DFS (Backtracking) or Dynamic Programming. The DP approach usually involves transforming the problem into a variation of the Subset Sum problem.

**Transformation to Subset Sum Problem:**

Let `P` be the sum of numbers with `+` signs and `N` be the sum of numbers with `-` signs.
We want to find the number of ways such that `P - N = target`.

Also, we know that `P + N = total_sum` of all elements in `nums`.

Adding these two equations:
`(P - N) + (P + N) = target + total_sum`
`2P = target + total_sum`
`P = (target + total_sum) / 2`

So, the problem boils down to: "Find the number of subsets in `nums` whose sum is `(target + total_sum) / 2`."

**Constraints Check:**

  * If `(target + total_sum)` is odd, then `P` cannot be an integer. So, return `0`.
  * If `target + total_sum` is negative, return `0`. (Since `nums` contain non-negative numbers, `P` will be non-negative. If `target + total_sum` is negative, `P` would be negative which is impossible).
  * Also, `target` cannot be greater than `total_sum` (unless some `nums` elements are 0).
  * The `target` sum can be up to `1000`, `nums` length up to `20`. `total_sum` can be `20 * 1000 = 20000`. So `P` can be up to `10000`.

**DP approach for "Number of Subsets with a Given Sum":**

1.  **Calculate `totalSum`** of `nums`.

2.  **Check feasibility:**

      * If `(totalSum + target) % 2 != 0` or `totalSum < Math.abs(target)`, return `0`. (The second condition `totalSum < Math.abs(target)` handles cases where target is too big or negative, making P negative or sum smaller than abs(target)).

3.  **Calculate `s = (totalSum + target) / 2`** (This is our new target sum for the subset).

4.  **Define `dp` array:**

      * `int[] dp = new int[s + 1]`
      * `dp[j]` represents the number of ways to form sum `j` using elements from `nums`.

5.  **Base Case:**

      * `dp[0] = 1` (There's 1 way to make sum 0: by choosing an empty subset).

6.  **Iterate and Update:**

      * Loop `num` through each element in `nums`: (Outer loop, for each item)
          * Loop `j` from `s` down to `num`: (Inner loop, for each possible sum)
              * `dp[j] += dp[j - num]`
              * Explanation: For sum `j`, we can either:
                  * Not include `num` (ways are `dp[j]` from previous iteration of outer loop).
                  * Include `num` (ways are `dp[j - num]` from previous iteration of outer loop).
              * Add these ways. Again, iterating `j` backwards prevents using the same `num` multiple times within the calculation for a single `dp[j]`.

7.  **Final Result:** `dp[s]`.

### Solution (The Way to Solve)

Hum is problem ko "Subset Sum" problem mein transform karte hain. Agar `P` positive numbers ka sum hai aur `N` negative numbers ka sum hai, toh humein `P - N = target` chahiye. Choonki `P + N = total_sum` bhi hai, in dono equations ko solve karne par `2P = target + total_sum` ya `P = (target + total_sum) / 2` milta hai.
Sabse pehle, `nums` ka `totalSum` calculate karte hain. Agar `(totalSum + target)` odd hai ya `target` `totalSum` se bada hai (negative target ke liye `Math.abs(target)` check karte hain), toh `0` return karte hain. Warna, naya `target` `s = (totalSum + target) / 2` set karte hain.
Ab, hum ek `dp` array banate hain jahan `dp[j]` ka matlab hai sum `j` banane ke kitne tareeqe hain. `dp[0]` ko 1 set karte hain. `nums` ke har `num` ke liye, `j` ko `s` se `num` tak reverse order mein iterate karte hain, aur `dp[j]` mein `dp[j - num]` add karte hain. Final answer `dp[s]` hoga.

### Code

```java
import java.util.Arrays;

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Check for edge cases based on the transformation P - N = target and P + N = totalSum
        // This implies 2P = totalSum + target
        // 1. (totalSum + target) must be even, otherwise, P cannot be an integer.
        // 2. totalSum must be >= abs(target). If target is greater than totalSum,
        //    it's impossible to reach. Also, if target is very negative (e.g., -5, totalSum=1),
        //    then totalSum < abs(target) will catch it.
        //    (totalSum + target) cannot be negative either, as P cannot be negative.
        if (totalSum < Math.abs(target) || (totalSum + target) % 2 != 0) {
            return 0;
        }

        // New target sum for the subset (sum of positive numbers P)
        int subSetSum = (totalSum + target) / 2;

        // dp[j] will store the number of ways to form sum 'j' using a subset of nums
        int[] dp = new int[subSetSum + 1];

        // Base case: There is 1 way to make sum 0 (by choosing an empty subset)
        dp[0] = 1;

        // Iterate through each number in nums
        for (int num : nums) {
            // Iterate from subSetSum down to num
            // We iterate backwards to ensure each number is used at most once for a particular sum 'j'
            for (int j = subSetSum; j >= num; j--) {
                // If we can make sum (j - num), then by adding 'num' to it, we can also make sum 'j'.
                // So, add the number of ways to make (j - num) to the ways to make 'j'.
                dp[j] += dp[j - num];
            }
        }

        // The final result is the number of ways to form the 'subSetSum'
        return dp[subSetSum];
    }
}
```

-----

## 97\. Interleaving String

**Interleaving String** ek Dynamic Programming problem hai jismein humein check karna hota hai ki kya ek string do dusri strings ka valid interleaving hai.

-----

### Description/Overview

Aapko teen strings `s1`, `s2`, aur `s3` diye gaye hain. Aapko return karna hai `true` agar `s3` `s1` aur `s2` ka ek **interleaving** hai, `false` otherwise.
An interleaving of two strings `s1` and `s2` means `s3` contains all the characters of `s1` and all the characters of `s2`, and the relative order of characters in both `s1` and `s2` is maintained.

For example:

  * `s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"`
      * Output: `true` (Example: `a` (from s1), `a` (from s1), `d` (from s2), `b` (from s2), `b` (from s2), `c` (from s1), `b` (from s2), `c` (from s1), `a` (from s2), `c` (from s1))
  * `s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"`
      * Output: `false` (s3 has 'b' after 'aab' but 'c' should be next from s1, or 'a' from s2. The relative order is broken.)

### Approach (How to do it)

This is a classic 2D DP problem.

1.  **Preliminary Check:**

      * If `s3.length() != s1.length() + s2.length()`, then `s3` can never be an interleaving. Return `false`.

2.  **Define `dp` table:**

      * `boolean[][] dp = new boolean[n1 + 1][n2 + 1]` where `n1 = s1.length()`, `n2 = s2.length()`.
      * `dp[i][j]` will be `true` if `s3[0...i+j-1]` (prefix of `s3`) is an interleaving of `s1[0...i-1]` (prefix of `s1`) and `s2[0...j-1]` (prefix of `s2`).

3.  **Base Cases:**

      * `dp[0][0] = true` (Empty strings `s1` and `s2` can interleave to form an empty `s3` prefix).
      * **First row (`i=0`):** `dp[0][j]` means `s3[0...j-1]` is interleaving of empty `s1` and `s2[0...j-1]`. This is true if `s2[0...j-1]` matches `s3[0...j-1]`.
          * For `j` from `1` to `n2`: `dp[0][j] = dp[0][j-1] && (s2.charAt(j-1) == s3.charAt(j-1))`
      * **First column (`j=0`):** `dp[i][0]` means `s3[0...i-1]` is interleaving of `s1[0...i-1]` and empty `s2`. This is true if `s1[0...i-1]` matches `s3[0...i-1]`.
          * For `i` from `1` to `n1`: `dp[i][0] = dp[i-1][0] && (s1.charAt(i-1) == s3.charAt(i-1))`

4.  **Recursive Relation:** Iterate `i` from `1` to `n1`, `j` from `1` to `n2`.

      * `k = i + j - 1` (Current character index in `s3`).
      * `dp[i][j]` can be true if:
          * **Option 1: `s3[k]` comes from `s1`:**
              * `s1.charAt(i-1) == s3.charAt(k)` AND `dp[i-1][j]` is `true` (meaning `s3[0...k-1]` was an interleaving of `s1[0...i-2]` and `s2[0...j-1]`).
          * **Option 2: `s3[k]` comes from `s2`:**
              * `s2.charAt(j-1) == s3.charAt(k)` AND `dp[i][j-1]` is `true` (meaning `s3[0...k-1]` was an interleaving of `s1[0...i-1]` and `s2[0...j-2]`).
      * So, `dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(k)) || (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(k))`

5.  **Final Result:** `dp[n1][n2]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Sabse pehle, `s3` ki length check karte hain; agar `s1` aur `s2` ki combined length ke barabar nahi hai, toh `false` return karte hain. Ek boolean `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai ki `s3` ka `(i+j)` length ka prefix `s1` ke `i` length ke prefix aur `s2` ke `j` length ke prefix ka interleaving hai ya nahi. `dp[0][0]` ko true set karte hain. First row aur column ko base cases ke roop mein fill karte hain. Fir, nested loops chala kar `dp` table fill karte hain. `dp[i][j]` true hoga agar `s3` ka current character `s1` ke current character se match karta hai aur `dp[i-1][j]` true hai, OR `s3` ka current character `s2` ke current character se match karta hai aur `dp[i][j-1]` true hai. Final answer `dp[s1.length()][s2.length()]` hoga.

### Code

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n1 = s1.length();
        int n2 = s2.length();
        int n3 = s3.length();

        // If lengths don't match, s3 cannot be an interleaving
        if (n3 != n1 + n2) {
            return false;
        }

        // dp[i][j] will be true if s3[0...i+j-1] is an interleaving of s1[0...i-1] and s2[0...j-1]
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];

        // Base case: Empty s1 and s2 can interleave to form empty s3
        dp[0][0] = true;

        // Fill first column (s1's characters only)
        for (int i = 1; i <= n1; i++) {
            // dp[i][0] is true if s1[0...i-1] matches s3[0...i-1]
            dp[i][0] = dp[i - 1][0] && (s1.charAt(i - 1) == s3.charAt(i - 1));
        }

        // Fill first row (s2's characters only)
        for (int j = 1; j <= n2; j++) {
            // dp[0][j] is true if s2[0...j-1] matches s3[0...j-1]
            dp[0][j] = dp[0][j - 1] && (s2.charAt(j - 1) == s3.charAt(j - 1));
        }

        // Fill the rest of the DP table
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // k is the current index in s3
                int k = i + j - 1;

                // Option 1: s3[k] comes from s1.
                // This is possible if dp[i-1][j] was true (s3 up to k-1 was interleave of s1[0...i-2] & s2[0...j-1])
                // AND s1's current char matches s3's current char.
                boolean fromS1 = dp[i - 1][j] && (s1.charAt(i - 1) == s3.charAt(k));

                // Option 2: s3[k] comes from s2.
                // This is possible if dp[i][j-1] was true (s3 up to k-1 was interleave of s1[0...i-1] & s2[0...j-2])
                // AND s2's current char matches s3's current char.
                boolean fromS2 = dp[i][j - 1] && (s2.charAt(j - 1) == s3.charAt(k));

                // dp[i][j] is true if either option is true
                dp[i][j] = fromS1 || fromS2;
            }
        }

        // The final result is whether the entire s3 is an interleaving of entire s1 and s2
        return dp[n1][n2];
    }
}
```

-----

## 329\. Longest Increasing Path in a Matrix

**Longest Increasing Path in a Matrix** ek challenging Dynamic Programming problem hai jismein humein ek matrix mein longest increasing path ki length find karni hoti hai.

-----

### Description/Overview

Aapko ek `m x n` integers ka matrix diya gaya hai. Aapko return karna hai matrix mein **longest increasing path** ki length.
Aap har cell se upar, neeche, left, ya right move kar sakte ho (diagonal moves allowed nahi hain).
Ek increasing path banate waqt, aap ek cell se sirf us adjacent cell par move kar sakte ho jiski value current cell se **badi** ho.

For example:

  * `matrix = [[9,9,4],[6,6,8],[2,1,1]]`
      * Output: 4 (e.g., `[1, 2, 6, 9]`)
  * `matrix = [[3,4,5],[3,2,6],[2,2,1]]`
      * Output: 4 (e.g., `[3, 4, 5, 6]`)

### Approach (How to do it)

This problem is best solved using Dynamic Programming with Memoization (Top-Down DP) combined with Depth First Search (DFS). A simple DFS without memoization would lead to TLE (Time Limit Exceeded) due to redundant calculations.

1.  **Define `memo` table:**

      * `int[][] memo = new int[m][n]`
      * `memo[i][j]` stores the length of the longest increasing path starting from cell `(i, j)`.
      * Initialize `memo` table with 0s (or -1s) to indicate uncomputed states.

2.  **Overall Logic:**

      * Initialize `maxPath = 0`.
      * Loop `i` from `0` to `m-1`, `j` from `0` to `n-1`:
          * Call `dfs(matrix, i, j, memo, -1)` (or `Integer.MIN_VALUE` for `prevVal`).
          * Update `maxPath = Math.max(maxPath, memo[i][j])`.

3.  **`dfs(matrix, r, c, memo, prevVal)` Helper Function (with Memoization):**

      * **Base Cases / Constraints:**
          * If `r` or `c` are out of bounds.
          * If `matrix[r][c] <= prevVal` (current value is not greater than previous value, so cannot form an increasing path).
          * In these cases, return `0`.
      * **Memoization Check:**
          * If `memo[r][c]` is not `0` (or `> 0` if initialized with -1), it means this path has already been computed. Return `memo[r][c]`.
      * **Recursive Step:**
          * `currentVal = matrix[r][c]`.
          * Initialize `currentMaxLen = 1` (at least the current cell itself counts for length 1).
          * Explore 4 directions (up, down, left, right):
              * `dr = {0, 0, 1, -1}`, `dc = {1, -1, 0, 0}`
              * For each direction `k`:
                  * `nr = r + dr[k]`, `nc = c + dc[k]`
                  * `lenFromNeighbor = dfs(matrix, nr, nc, memo, currentVal)`
                  * `currentMaxLen = Math.max(currentMaxLen, 1 + lenFromNeighbor)` (1 for current cell + length from neighbor)
      * **Store and Return:**
          * `memo[r][c] = currentMaxLen`.
          * Return `currentMaxLen`.

**Important detail for `prevVal`:** The `prevVal` parameter is crucial to ensure strict increasing path. When calling `dfs` recursively, `currentVal` (i.e., `matrix[r][c]`) should be passed as `prevVal` for the next call. The initial call needs a very small `prevVal` so that `matrix[0][0]` is always greater.

### Solution (The Way to Solve)

Hum Dynamic Programming with Memoization (Top-Down DP) ka use karte hain. Ek `memo` 2D array banate hain jahan `memo[i][j]` cell `(i, j)` se start hone wale longest increasing path ki length store karta hai. `memo` array ko 0s se initialize karte hain. Har cell `(i, j)` ke liye, ek DFS function `dfs(matrix, r, c, memo, prevVal)` call karte hain.
`dfs` function mein:

1.  Boundary checks aur `matrix[r][c] <= prevVal` condition check karte hain (increasing path ke liye).
2.  Agar `memo[r][c]` pehle se compute ho chuka hai, toh sidha `memo[r][c]` return karte hain (memoization).
3.  Warna, `currentMaxLen` ko 1 (current cell ke liye) se initialize karte hain.
4.  Charon adjacent directions mein recursively `dfs` call karte hain, `matrix[r][c]` ko `prevVal` ke roop mein pass karte hue. Har recursive call se return hone wale length mein 1 add karke `currentMaxLen` ko update karte hain.
5.  `currentMaxLen` ko `memo[r][c]` mein store karte hain aur return karte hain.
    Main function mein, saare cells se `dfs` call karte hain aur overall `maxPath` ko track karte hain.

### Code

```java
class Solution {
    private int[][] memo; // Memoization table to store computed LIP lengths
    private int[][] matrix;
    private int rows, cols;
    private int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // Right, Left, Down, Up

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.memo = new int[rows][cols]; // Initialized to 0 by default

        int maxPath = 0;

        // Iterate through each cell and start a DFS from it
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Call DFS for each cell.
                // Pass -1 as prevVal to ensure the first cell always satisfies the increasing condition.
                maxPath = Math.max(maxPath, dfs(r, c));
            }
        }

        return maxPath;
    }

    // DFS function with memoization
    private int dfs(int r, int c) {
        // If the result for this cell is already computed, return it (memoization)
        if (memo[r][c] != 0) {
            return memo[r][c];
        }

        int currentMaxLen = 1; // At least the current cell itself forms a path of length 1

        // Explore all 4 adjacent directions
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            // Check boundary conditions and increasing path condition
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && matrix[nr][nc] > matrix[r][c]) {
                // Recursively call DFS for the next cell
                currentMaxLen = Math.max(currentMaxLen, 1 + dfs(nr, nc));
            }
        }

        // Store the computed result in the memo table before returning
        memo[r][c] = currentMaxLen;
        return currentMaxLen;
    }
}
```

-----

## 115\. Distinct Subsequences

**Distinct Subsequences** ek Dynamic Programming problem hai jismein humein count karna hota hai ki string `s` mein string `t` kitni baar distinct subsequence ke roop mein appear hoti hai.

-----

### Description/Overview

Aapko do strings `s` aur `t` diye gaye hain. Aapko return karna hai ki `s` mein `t` kitni baar as a **distinct subsequence** appear hoti hai.
A subsequence is formed by deleting zero or more characters from the original string without changing the relative order of the remaining characters.

For example:

  * `s = "rabbbit", t = "rabbit"`
      * Output: 3
      * Explanation: `s` ke teen tarike se "rabbit" bana sakte hain:
          * `rabbbit` (delete 3rd 'b')
          * `rabbbit` (delete 4th 'b')
          * `rabbbit` (delete 5th 'b')
  * `s = "babgbag", t = "bag"`
      * Output: 5

### Approach (How to do it)

This is a classic 2D DP problem.

1.  **Define `dp` table:**

      * `int[][] dp = new int[n1 + 1][n2 + 1]` where `n1 = s.length()`, `n2 = t.length()`.
      * `dp[i][j]` represents the number of distinct subsequences of `s[0...i-1]` that are equal to `t[0...j-1]`.

2.  **Base Cases:**

      * `dp[0][0] = 1`: Empty string `t` ko empty string `s` se banane ka 1 way hai. (Always 1 way to form an empty string from any string, by deleting all characters).
      * `dp[i][0] = 1` for all `i` from `0` to `n1`: Empty string `t` ko `s[0...i-1]` se banane ka 1 way hai (by deleting all characters from `s`).
      * `dp[0][j] = 0` for all `j` from `1` to `n2`: Non-empty string `t` ko empty string `s` se banane ka 0 way hai.

3.  **Recursive Relation:** Iterate `i` from `1` to `n1`, `j` from `1` to `n2`.

      * `charS = s.charAt(i-1)`
      * `charT = t.charAt(j-1)`
      * **Case 1: Characters match (`charS == charT`)**
          * `dp[i][j] = dp[i-1][j-1] + dp[i-1][j]`
          * Explanation: Agar `s[i-1]` aur `t[j-1]` match karte hain, toh do options hain:
            1.  **Match `s[i-1]` with `t[j-1]`:** Is case mein, remaining subsequences count `dp[i-1][j-1]` (matching `s[0...i-2]` with `t[0...j-2]`).
            2.  **Don't match `s[i-1]` with `t[j-1]` (ignore `s[i-1]`):** Is case mein, remaining subsequences count `dp[i-1][j]` (matching `s[0...i-2]` with `t[0...j-1]`).
            <!-- end list -->
              * Total ways will be sum of these two options.
      * **Case 2: Characters don't match (`charS != charT`)**
          * `dp[i][j] = dp[i-1][j]`
          * Explanation: Agar `s[i-1]` aur `t[j-1]` match nahi karte, toh `s[i-1]` ko `t[j-1]` ke roop mein use nahi kar sakte. Toh hum `s[i-1]` ko skip karte hain aur count `dp[i-1][j]` (matching `s[0...i-2]` with `t[0...j-1]`) ke barabar hoga.

4.  **Final Result:** `dp[n1][n2]`.

**Space Optimization (Optional):**
You can optimize space to `O(n2)` because `dp[i]` only depends on `dp[i-1]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai `s` ke pehle `i` characters mein se `t` ke pehle `j` characters ko distinct subsequence ke roop mein banane ke tareeqe. `dp[0][0]` ko 1 set karte hain (empty strings). `dp` table ke first column ko 1 se fill karte hain (empty `t` ko kisi bhi `s` se banane ka 1 way). `dp` table ke first row ko (excluding `dp[0][0]`) 0 se fill karte hain (non-empty `t` ko empty `s` se banane ka 0 way).
Nested loops chala kar `dp` table fill karte hain. Agar `s.charAt(i-1)` aur `t.charAt(j-1)` match karte hain, toh `dp[i][j]` ko `dp[i-1][j-1] + dp[i-1][j]` set karte hain. Agar match nahi karte, toh `dp[i][j]` ko `dp[i-1][j]` set karte hain (current `s` char ko skip karke). Final answer `dp[s.length()][t.length()]` hoga.

### Code

```java
class Solution {
    public int numDistinct(String s, String t) {
        int n1 = s.length();
        int n2 = t.length();

        // dp[i][j] will store the number of distinct subsequences of s[0...i-1]
        // that are equal to t[0...j-1]
        // Use long for dp values to prevent overflow, as numbers can be large
        long[][] dp = new long[n1 + 1][n2 + 1];

        // Base case 1: dp[i][0] = 1 for all i (empty string t can be formed in 1 way from any s)
        for (int i = 0; i <= n1; i++) {
            dp[i][0] = 1;
        }

        // Base case 2: dp[0][j] = 0 for all j > 0 (non-empty t cannot be formed from empty s)
        // This is default in Java for long arrays.

        // Fill the DP table
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // If the current characters match (s[i-1] == t[j-1])
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // Option 1: Match s[i-1] with t[j-1]. Then we need to find subsequences of s[0...i-2] for t[0...j-2].
                    // This count is dp[i-1][j-1].
                    // Option 2: Don't match s[i-1] with t[j-1] (treat s[i-1] as one of the deleted characters).
                    // Then we need to find subsequences of s[0...i-2] for t[0...j-1].
                    // This count is dp[i-1][j].
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // If the current characters don't match (s[i-1] != t[j-1])
                    // We must skip s[i-1]. So, the count is the same as finding subsequences
                    // of s[0...i-2] for t[0...j-1].
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // The result is the number of distinct subsequences of full 's' for full 't'
        return (int) dp[n1][n2];
    }
}
```

-----

## 72\. Edit Distance (commonly listed as \#72)

**Edit Distance** ek Dynamic Programming problem hai jismein humein do words ko ek jaisa banane ke liye minimum operations find karne hote hain.

-----

### Description/Overview

Aapko do strings `word1` aur `word2` diye gaye hain. Aapko find karna hai ki `word1` ko `word2` mein convert karne ke liye kitne **minimum operations** chahiye. Allowed operations teen hain:

1.  **Insert** a character.
2.  **Delete** a character.
3.  **Replace** a character.

For example:

  * `word1 = "horse", word2 = "ros"`
      * Output: 3
      * `horse` -\> `rorse` (replace 'h' with 'r')
      * `rorse` -\> `rose` (delete 'r')
      * `rose` -\> `ros` (delete 'e')
  * `word1 = "intention", word2 = "execution"`
      * Output: 5

### Approach (How to do it)

This is a classic 2D DP problem, also known as Levenshtein distance.

1.  **Define `dp` table:**

      * `int[][] dp = new int[n1 + 1][n2 + 1]` where `n1 = word1.length()`, `n2 = word2.length()`.
      * `dp[i][j]` represents the minimum operations needed to convert `word1[0...i-1]` to `word2[0...j-1]`.
      * We use `n1 + 1` and `n2 + 1` size to handle empty string cases (index 0).

2.  **Base Cases (First row and column initialization):**

      * `dp[0][0] = 0`: Empty `word1` to empty `word2` requires 0 operations.
      * `dp[i][0] = i`: To convert `word1[0...i-1]` to an empty string (`word2` prefix), you need to delete `i` characters from `word1`.
          * For `i` from `1` to `n1`: `dp[i][0] = i`.
      * `dp[0][j] = j`: To convert an empty string (`word1` prefix) to `word2[0...j-1]`, you need to insert `j` characters.
          * For `j` from `1` to `n2`: `dp[0][j] = j`.

3.  **Recursive Relation:** Iterate `i` from `1` to `n1`, `j` from `1` to `n2`.

      * `char1 = word1.charAt(i-1)`
      * `char2 = word2.charAt(j-1)`
      * **Case 1: Characters match (`char1 == char2`)**
          * `dp[i][j] = dp[i-1][j-1]`
          * Explanation: Agar current characters match karte hain, toh unhein modify karne ki zaroorat nahi. So, `dp[i][j]` previous (smaller) substrings `word1[0...i-2]` to `word2[0...j-2]` ke operations ke barabar hoga.
      * **Case 2: Characters don't match (`char1 != char2`)**
          * `dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])`
          * Explanation: Agar characters match nahi karte, toh humare paas teen operations ka choice hai, har choice mein 1 operation add hoga:
            1.  **Delete `char1` from `word1`:** `1 + dp[i-1][j]` (Convert `word1[0...i-2]` to `word2[0...j-1]`).
            2.  **Insert `char2` into `word1`:** `1 + dp[i][j-1]` (Effectively, `word1[0...i-1]` ko `word2[0...j-2]` tak convert kar liya, aur ab `word2[j-1]` ko insert karna hai).
            3.  **Replace `char1` with `char2`:** `1 + dp[i-1][j-1]` (Convert `word1[0...i-2]` to `word2[0...j-2]`, then replace `char1` with `char2`).
            <!-- end list -->
              * Minimum of these three options.

4.  **Final Result:** `dp[n1][n2]`.

**Space Optimization (Optional):**
You can optimize space to `O(min(n1, n2))` or even `O(n2)` because `dp[i]` only depends on `dp[i-1]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai `word1` ke pehle `i` characters ko `word2` ke pehle `j` characters mein convert karne ke minimum operations. `dp` table ke first row aur column ko initialize karte hain: `dp[i][0] = i` (deletion operations) aur `dp[0][j] = j` (insertion operations).
Nested loops chala kar `dp` table fill karte hain. Agar `word1.charAt(i-1)` aur `word2.charAt(j-1)` match karte hain, toh `dp[i][j]` ko `dp[i-1][j-1]` set karte hain. Agar match nahi karte, toh `dp[i][j]` ko `1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])` set karte hain (delete, insert, replace operations mein se minimum + 1). Final answer `dp[word1.length()][word2.length()]` hoga.

### Code

```java
class Solution {
    public int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();

        // dp[i][j] will store the minimum operations needed to convert word1[0...i-1] to word2[0...j-1]
        int[][] dp = new int[n1 + 1][n2 + 1];

        // Base cases:
        // Converting empty word1 to word2[0...j-1] requires j insertions
        for (int j = 0; j <= n2; j++) {
            dp[0][j] = j;
        }

        // Converting word1[0...i-1] to empty word2 requires i deletions
        for (int i = 0; i <= n1; i++) {
            dp[i][0] = i;
        }

        // Fill the DP table
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // If current characters match
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation needed for this character
                } else {
                    // If characters don't match, consider 3 options:
                    // 1. Delete char from word1: dp[i-1][j] + 1
                    // 2. Insert char into word1 (to match word2's char): dp[i][j-1] + 1
                    // 3. Replace char in word1 with word2's char: dp[i-1][j-1] + 1
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],       // Deletion
                                           Math.min(dp[i][j - 1],   // Insertion
                                                    dp[i - 1][j - 1])); // Replacement
                }
            }
        }

        // The final result is the minimum operations to convert full word1 to full word2
        return dp[n1][n2];
    }
}
```

-----

## 312\. Burst Balloons

**Burst Balloons** ek challenging Dynamic Programming problem hai jismein humein balloons ko burst karke maximum coins collect karne hote hain.

-----

### Description/Overview

Aapko `n` balloons diye gaye hain, jinko `nums` array mein represent kiya gaya hai. Har balloon par ek number likha hai jo uski value hai. Jab aap ek balloon `i` ko burst karte ho, toh aapko `nums[left] * nums[i] * nums[right]` coins milte hain. Yahan `left` aur `right` hain adjacent balloons `i` ke left aur right mein burst hone ke baad.
Jab ek balloon burst ho jata hai, toh uske left aur right ke balloons adjacent ban jate hain.
Aapka goal hai saare balloons burst karne ke baad **maximum coins** collect karna.

**Constraints:**

  * You can imagine `nums[-1] = nums[n] = 1` (fictitious balloons with value 1 at boundaries) to simplify edge cases. These boundary balloons are not burst.

For example:

  * `nums = [3, 1, 5, 8]`
      * Output: 167
      * Steps:
          * `[3, 1, 5, 8]` -\> burst 1: `3 * 1 * 5 = 15` coins. Array becomes `[3, 5, 8]`
          * `[3, 5, 8]` -\> burst 5: `3 * 5 * 8 = 120` coins. Array becomes `[3, 8]`
          * `[3, 8]` -\> burst 3: `1 * 3 * 8 = 24` coins. Array becomes `[8]`
          * `[8]` -\> burst 8: `1 * 8 * 1 = 8` coins. Array becomes `[]`
          * Total = `15 + 120 + 24 + 8 = 167`

### Approach (How to do it)

This problem is counter-intuitive. Instead of thinking about which balloon to burst *first*, think about which balloon to burst **last**.
Why last? Because when a balloon is burst last, its left and right neighbors are fixed (they must be the two balloons that were originally its neighbors, or the boundary 1s, after everything else in between has been burst). If we burst a balloon in the middle, its neighbors change, making the subproblem definition hard.

1.  **Preprocessing `nums` array:**

      * Create a new array `A` of size `n + 2`.
      * `A[0] = 1`, `A[n+1] = 1`.
      * Copy `nums` elements into `A[1...n]`.
      * So, `A` will be `[1, nums[0], ..., nums[n-1], 1]`.

2.  **Define `dp` table:**

      * `int[][] dp = new int[n + 2][n + 2]`
      * `dp[i][j]` represents the maximum coins you can get by bursting all balloons **strictly between index `i` and index `j`** (excluding `A[i]` and `A[j]`).

3.  **Initialization:**

      * `dp` table will be initialized to 0s. This is correct because if `i >= j-1`, there are no balloons strictly between `i` and `j` to burst, so 0 coins.

4.  **Iterate (Gap Strategy):**

      * Loop `len` from `1` to `n` (length of the subarray being considered).
      * Loop `i` from `1` to `n - len + 1` (starting index).
          * `j = i + len - 1` (ending index).
          * Now, we are considering the segment `A[i...j]`. We want to burst all balloons `A[i...j]` (if they were internal to some range).
          * We are thinking about bursting balloons **between `A[i-1]` and `A[j+1]`**.
          * For `dp[i][j]`, we are actually trying to compute max coins for bursting `A[i]...A[j]`.
          * Let `k` be the *last* balloon to burst in the range `A[i...j]`.
          * `k` will range from `i` to `j`.
          * `dp[i][j] = max(dp[i][j], dp[i][k-1] + dp[k+1][j] + (A[i-1] * A[k] * A[j+1]))`
              * `dp[i][k-1]`: Max coins from bursting balloons `A[i...k-1]` (left part).
              * `dp[k+1][j]`: Max coins from bursting balloons `A[k+1...j]` (right part).
              * `A[i-1] * A[k] * A[j+1]`: Coins from bursting `A[k]` (which is burst last in this segment `A[i...j]`). When `A[k]` is burst last, its immediate neighbors are `A[i-1]` and `A[j+1]`.

5.  **Final Result:** `dp[1][n]`. (Max coins for bursting all original balloons, which are between `A[0]` and `A[n+1]`).

### Solution (The Way to Solve)

Yeh problem standard DP problems se alag hai kyunki burst karne se neighbors change ho jate hain. Isko solve karne ka key idea hai ki hum yeh sochein ki kaun sa balloon **last** mein burst hoga ek particular range mein.
Pehle, `nums` array ko expand karte hain `[1, nums[0], ..., nums[n-1], 1]` mein, jahan 1s boundary balloons hain.
Fir, ek `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai maximum coins jo `A[i]` aur `A[j]` ke **between** ke saare balloons burst karke mil sakte hain (matlab `A[i+1]` se `A[j-1]` tak).
Hum `len` (length of the interval between `i` and `j`, excluding `i` and `j`) ko `1` se `n` tak iterate karte hain. `i` aur `j` ko set karne ke baad, `k` ko us balloon ke roop mein assume karte hain jo `A[i]` aur `A[j]` ke beech mein **last** mein burst hoga. `k` `i+1` se `j-1` tak jayega. Formula `dp[i][j] = max(dp[i][j], dp[i][k] + dp[k][j] + (A[i] * A[k] * A[j]))` use karte hain. (Note: Yahan `i`, `j`, `k` ko 0-indexed `A` ke saath adjust kiya gaya hai solution mein)

Adjusted `dp[i][j]` means max coins from bursting `nums[i...j]`.
The `dp[i][j]` should represent the maximum coins from bursting balloons `nums[i]` to `nums[j]`.
When we say `dp[i][j]`, `i` is the virtual left border, `j` is the virtual right border.
We are choosing `k` to be the *last* balloon to burst between `i` and `j`.
`dp[i][j] = max(dp[i][j], dp[i][k] + dp[k][j] + (A[i] * A[k] * A[j]))`
Here, `A[i]` and `A[j]` are the *fixed* neighbors of `A[k]` when `A[k]` is burst last.
The ranges are `[i, k]` and `[k, j]` where `k` is the last one.

Correct iteration structure based on last-burst thinking for `dp[i][j]` = max coins from bursting balloons in `A[i...j]` (inclusive of `A[i]`, `A[j]`, not exclusive like many solutions' `dp[i][j]` means `(i,j)` range):

Let `dp[i][j]` be the maximum coins from bursting balloons `A[i]` to `A[j]`.
Base case: `dp[i][i]` is not well-defined here because of `A[left] * A[i] * A[right]`.
The common solution uses `dp[i][j]` as "max coins from bursting `nums` in range `(i, j)` (exclusive of i, j)".
So `dp[i][j]` is for the sub-problem `nums[i+1...j-1]`.

The recursive relation `dp[left][right] = max(dp[left][right], dp[left][k] + dp[k][right] + A[left] * A[k] * A[right])` where `k` is the last balloon to burst in the range `(left, right)`.

The loops run on `len` (length of interval `j-i` for `dp[i][j]`).
`len` goes from 2 to `n+1`.
`i` from 0 to `n+2 - len`.
`j = i + len`.
`k` from `i+1` to `j-1`.

This means `dp[i][j]` is the maximum coins from bursting balloons `A[i+1], ..., A[j-1]`.
`k` is the *last* balloon burst *among* `A[i+1], ..., A[j-1]`.
When `A[k]` is burst last, its immediate neighbors are `A[i]` (left boundary) and `A[j]` (right boundary).

### Code

```java
import java.util.Arrays;

class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;

        // Create a new array A with padded 1s at both ends
        // A[0] = 1, A[n+1] = 1, and nums elements are A[1] to A[n]
        int[] A = new int[n + 2];
        A[0] = 1;
        A[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            A[i + 1] = nums[i];
        }

        // dp[i][j] will store the maximum coins obtained by bursting all balloons
        // STRICTLY BETWEEN index i and index j (i.e., A[i+1] to A[j-1])
        // in the modified array A.
        int[][] dp = new int[n + 2][n + 2];

        // Iterate based on the length of the subarray (gap)
        // 'len' is the number of balloons strictly between i and j.
        // It ranges from 0 to n. If len is 0, i and j are adjacent.
        // The effective range A[i...j] has (j-i-1) balloons.
        // We start with len = 1, meaning we are considering segments like (i, i+2) which has 1 balloon.
        for (int len = 1; len <= n; len++) { // 'len' represents the actual number of balloons in the segment being burst
            // 'i' is the left boundary (exclusive) of the subarray
            for (int i = 0; i <= n - len; i++) {
                int j = i + len + 1; // 'j' is the right boundary (exclusive) of the subarray
                                     // This means we are considering balloons from A[i+1] to A[j-1]
                                     // So original nums[i] to nums[j-2]

                // 'k' represents the index of the last balloon to be burst within the range (i, j)
                // A[k] will be the last balloon burst in the segment from A[i+1] to A[j-1]
                for (int k = i + 1; k < j; k++) {
                    // When A[k] is burst last, its immediate neighbors are A[i] and A[j]
                    // (because all balloons between A[i] and A[k], and between A[k] and A[j] are already burst)
                    int coins = A[i] * A[k] * A[j];
                    // Add coins from bursting the left part (between i and k) and the right part (between k and j)
                    coins += dp[i][k] + dp[k][j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }

        // The result is dp[0][n+1], which represents bursting all original balloons
        // (A[1] to A[n]), using A[0] and A[n+1] as boundaries.
        return dp[0][n + 1];
    }
}
```

-----

## 10\. Regular Expression Matching

**Regular Expression Matching** ek challenging Dynamic Programming problem hai jismein humein string aur regular expression pattern ko match karna hota hai.

-----

### Description/Overview

Aapko ek input string `s` aur ek pattern `p` diya gaya hai. Aapko implement karna hai regular expression matching jismein do special characters hain:

  * `.` matches any single character.
  * `*` matches zero or more of the preceding element.

Matching poore input string ko cover karna chahiye, partially nahi.

For example:

  * `s = "aa", p = "a"`
      * Output: `false` (p doesn't match the whole s)
  * `s = "aa", p = "a*"`
      * Output: `true` ('a\*' can match zero or more 'a's, here it matches two 'a's)
  * `s = "ab", p = ".*"`
      * Output: `true` ('.\*' matches zero or more (0 or more times) of any character '.')
  * `s = "aab", p = "c*a*b"`
      * Output: `true` ('c\*' matches zero 'c's, 'a\*' matches two 'a's, 'b' matches 'b')
  * `s = "mississippi", p = "mis*is*p*."`
      * Output: `false`

### Approach (How to do it)

This is a classic 2D Dynamic Programming problem.

1.  **Define `dp` table:**

      * `boolean[][] dp = new boolean[n_s + 1][n_p + 1]` where `n_s = s.length()`, `n_p = p.length()`.
      * `dp[i][j]` will be `true` if `s[0...i-1]` (prefix of `s`) matches `p[0...j-1]` (prefix of `p`).

2.  **Base Cases:**

      * `dp[0][0] = true`: Empty string `s` matches empty pattern `p`.
      * `dp[i][0] = false` for `i > 0`: Non-empty string `s` cannot match empty pattern `p`.
      * `dp[0][j]`: Empty string `s` can match patterns like `a*`, `c*a*`, etc.
          * For `j` from `1` to `n_p`:
              * If `p.charAt(j-1) == '*'`, then `dp[0][j] = dp[0][j-2]`. (Meaning `*` matches zero of preceding char, effectively removing `p[j-2]` and `p[j-1]` from consideration).
              * Otherwise (`*` nahi hai), `dp[0][j] = false`.

3.  **Recursive Relation:** Iterate `i` from `1` to `n_s`, `j` from `1` to `n_p`.

      * `char_s = s.charAt(i-1)`

      * `char_p = p.charAt(j-1)`

      * **Case 1: `char_p` is `.` or `char_p == char_s`**

          * `dp[i][j] = dp[i-1][j-1]`
          * Explanation: If current characters match or `p` has `.`, then match depends on if previous prefixes matched.

      * **Case 2: `char_p` is `*`**

          * `preceding_char_p = p.charAt(j-2)` (Character before `*`)

          * `dp[i][j]` has two possibilities:

            1.  **`*` matches zero of `preceding_char_p`:**
                  * `dp[i][j] = dp[i][j-2]`
                  * Explanation: Treat `p[j-2]` and `p[j-1]` (`*`) as empty string.
            2.  **`*` matches one or more of `preceding_char_p`:**
                  * This is possible ONLY IF `preceding_char_p` matches `char_s` (or `preceding_char_p` is `.`):
                      * If `preceding_char_p == char_s` or `preceding_char_p == '.'`:
                          * `dp[i][j] = dp[i][j] || dp[i-1][j]`
                          * Explanation: `dp[i-1][j]` means `s[0...i-2]` already matched `p[0...j-1]`. Now `s[i-1]` also matches `preceding_char_p` (which is `char_s`), so `*` effectively matched one more instance.

          * So, `dp[i][j] = dp[i][j-2]` (for zero occurrences)

              * If `(preceding_char_p == char_s || preceding_char_p == '.')` then `dp[i][j] = dp[i][j] || dp[i-1][j]` (for one or more occurrences).

4.  **Final Result:** `dp[n_s][n_p]`.

### Solution (The Way to Solve)

Hum bottom-up Dynamic Programming approach use karte hain. Ek boolean `dp` 2D array banate hain jahan `dp[i][j]` ka matlab hai `s` ka `i` length ka prefix `p` ke `j` length ke prefix se match karta hai ya nahi. `dp[0][0]` ko true set karte hain. First row `dp[0][j]` ko `*` rules ke according fill karte hain (e.g., `a*`, `.*`). First column `dp[i][0]` ko false set karte hain (non-empty `s` empty `p` se match nahi kar sakta).
Nested loops chala kar `dp` table fill karte hain.

  * Agar `p.charAt(j-1)` (current pattern character) `.` hai ya `s.charAt(i-1)` (current string character) se match karta hai, toh `dp[i][j]` ko `dp[i-1][j-1]` set karte hain.
  * Agar `p.charAt(j-1)` `*` hai, toh do options consider karte hain:
    1.  `*` zero occurrences match karta hai: `dp[i][j] = dp[i][j-2]` (pichle character aur `*` ko skip kar do).
    2.  `*` one or more occurrences match karta hai: Agar `p.charAt(j-2)` (star se pehle ka char) `s.charAt(i-1)` se match karta hai (ya `.` hai), toh `dp[i][j]` ko `dp[i][j] || dp[i-1][j]` set karte hain.
        Final answer `dp[s.length()][p.length()]` hoga.

### Code

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int n_s = s.length();
        int n_p = p.length();

        // dp[i][j] will be true if s[0...i-1] matches p[0...j-1]
        boolean[][] dp = new boolean[n_s + 1][n_p + 1];

        // Base case 1: Empty s and empty p match
        dp[0][0] = true;

        // Base case 2: Empty s can match patterns like "a*", ".*", "a*b*" etc.
        // For p[0...j-1]
        for (int j = 1; j <= n_p; j++) {
            // If current pattern char is '*', it can match zero of the preceding char.
            // So, dp[0][j] depends on dp[0][j-2]
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
            // Other cases (single char or .) won't match empty string 's', so they remain false (default)
        }

        // Fill the DP table for non-empty s and p
        for (int i = 1; i <= n_s; i++) {
            for (int j = 1; j <= n_p; j++) {
                char charS = s.charAt(i - 1);
                char charP = p.charAt(j - 1);

                // Case 1: Current pattern character is '.' or matches the current string character
                if (charP == '.' || charP == charS) {
                    dp[i][j] = dp[i - 1][j - 1]; // Match depends on previous prefixes
                }
                // Case 2: Current pattern character is '*'
                else if (charP == '*') {
                    char precedingCharP = p.charAt(j - 2); // The character before '*'

                    // Option A: '*' matches zero occurrences of the preceding character
                    dp[i][j] = dp[i][j - 2];

                    // Option B: '*' matches one or more occurrences of the preceding character
                    // This is only possible if the preceding character matches the current string character (charS)
                    if (precedingCharP == '.' || precedingCharP == charS) {
                        // If it matches, then dp[i][j] can also be true if:
                        // dp[i-1][j] was true (meaning s[0...i-2] matched p[0...j-1], and s[i-1] is another match for *)
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
                // Case 3: Current pattern character is a regular letter but doesn't match string char
                // (and not '.' or '*')
                // In this case, dp[i][j] remains false (default initialization)
            }
        }

        // The final result is whether the entire string 's' matches the entire pattern 'p'
        return dp[n_s][n_p];
    }
}
```
