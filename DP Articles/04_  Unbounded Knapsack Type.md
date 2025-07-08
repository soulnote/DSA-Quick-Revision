
# Unbounded Knapsack Kya Hota Hai?

**Knapsack Problem** ek classic optimization problem hai jismein aapko ek knapsack (thela) aur kuch items diye hote hain. Har item ka apna **weight** aur **value** hota hai. Knapsack ki ek **maximum capacity** hoti hai. Aapka goal hota hai knapsack mein aise items bharna jisse **total value maximum** ho, lekin **total weight knapsack ki capacity se zyada na ho**.

Knapsack problem ke do main variations hain:

1.  **0/1 Knapsack:** Har item ko aap ya toh pura utha sakte hain (1) ya bilkul nahi (0). Kisi bhi item ko ek se zyada baar nahi utha sakte.
2.  **Unbounded Knapsack (ya Repetition Knapsack):** Har item ko aap **kitni bhi baar** utha sakte hain, jab tak ki knapsack ki capacity allow kare. Yahi hamara current topic hai.

**"Unbounded"** ka matlab hai ki items ki quantity par koi limit nahi hai (har item infinite supply mein available hai).

**Real-world analogies:**

  * Aapke paas kuch paise hain, aur aap alag-alag daamon aur nutrition values ke khaane khareed sakte hain. Aap zyada se zyada nutrition chahte hain.
  * Ek factory mein machine parts banane hain. Har part ko banane mein time aur material lagta hai. Aap total production time/material limit mein zyada se zyada "value" (profit) generate karna chahte hain.

-----

### Unbounded Knapsack Ko Kaise Approach Karte Hain?

Unbounded Knapsack bhi Dynamic Programming ka ek classic example hai.

**Key Idea:**

0/1 Knapsack mein, jab hum `dp[i][w]` calculate karte hain (item `i` tak `w` weight ke saath max value), toh `dp[i][w]` ya toh `dp[i-1][w]` (item `i` ko include nahi kiya) hota hai ya `dp[i-1][w - weight[i]] + value[i]` (item `i` ko include kiya). Notice karein ki `dp[i-1]` use hota hai, matlab ek item ko ek hi baar use kar sakte hain.

Unbounded Knapsack mein, agar hum item `i` ko include karte hain, toh hum fir se item `i` ko include karne par विचार kar sakte hain, kyuki yeh "unbounded" hai.
Iska matlab hai: `dp[w]` = maximum value for a knapsack of capacity `w`.

**DP State Definition:**

`dp[w]` = Maximum value jo hum `w` weight ki knapsack capacity ke saath achieve kar sakte hain.

**Base Case:**

`dp[0] = 0` (0 capacity ke knapsack mein 0 value aaegi).

**Transitions (Filling the `dp` table):**

Hum `dp` array ko `0` se `Capacity` tak fill karenge.

Loop `w` from `1` to `Capacity`:
Loop `i` from `0` to `num_items - 1`:
\* If `weight[i] <= w`: (Agar current item ko knapsack mein daal sakte hain)
\* `dp[w] = Math.max(dp[w], dp[w - weight[i]] + value[i])`
\* Explanation:
\* `dp[w]` (already computed value for capacity `w`)
\* `dp[w - weight[i]] + value[i]` (Agar hum current item `i` ko daalte hain, toh remaining capacity `w - weight[i]` ke liye max value `dp[w - weight[i]]` hogi, aur usmein current item `i` ki `value[i]` add ho jaegi).
\* Yahan key point `dp[w - weight[i]]` hai na ki `dp[w - weight[i]]` for previous item. Iska matlab hai ki item `i` ko pick karne ke baad, bachi hui capacity `w - weight[i]` mein hum **dobara item `i` ko pick karne ka option** rakhte hain. Isliye `dp[w - weight[i]]` current iteration ke `dp` array se hi aata hai.

**Final Answer:**

`dp[Capacity]` return karo.

**Complexity Analysis:**

  * **Time Complexity:** $O(\\text{Capacity} \\cdot \\text{num\_items})$. Har capacity `w` ke liye, hum saare items ko iterate karte hain.
  * **Space Complexity:** $O(\\text{Capacity})$. Sirf ek 1D `dp` array use hota hai.

-----

### Unbounded Knapsack Ka Ek Example

**Problem:** Aapke paas teen tarah ke coins hain, aur aapko ek target amount banana hai. Har coin ko aap kitni bhi baar use kar sakte hain. Coins ki value aur unka "weight" (yahan weight coin ki value hi hai) diya gaya hai. Aapko **maximum possible value** (yahan coin value hi hai) achieve karni hai jo `target_amount` ke barabar ya usse kam ho.
(Actual problem thoda different hota hai, jahan specific target amount banana hota hai, jiske liye minimum coins ya number of ways nikalte hain. Lekin Unbounded Knapsack ke concept ko samajhne ke liye, hum coin values ko item values aur coin values ko hi weights manenge, aur target amount ko capacity).

Let's modify it to be a more direct Knapsack problem example:

**Example Problem:**
Aapke paas kuch items hain, har item ka `value` aur `weight` diya gaya hai. Aapke paas ek knapsack hai jiski `capacity` N hai. Aap har item ko kitni bhi baar utha sakte hain. Aapko **maximum total value** nikalni hai jo knapsack mein aa sake.

**Input:**

  * `weights = [1, 3, 4]` (Item ke weights)
  * `values = [10, 40, 50]` (Item ki corresponding values)
  * `capacity = 6` (Knapsack ki max capacity)

**Output:** Maximum total value.

**Expected Output Calculation:**

Capacity 6 ke liye:

  * Item 1 (Weight 1, Value 10): 6 baar uthao -\> Total Weight 6, Total Value 60.
  * Item 2 (Weight 3, Value 40): 2 baar uthao -\> Total Weight 6, Total Value 80.
  * Item 3 (Weight 4, Value 50): 1 baar uthao -\> Total Weight 4, Total Value 50.
  * Combination: Item 2 (Weight 3, Value 40) + Item 1 (Weight 1, Value 10) + Item 1 (Weight 1, Value 10) + Item 1 (Weight 1, Value 10) -\> Total Weight 6, Total Value 40+10+10+10 = 70. (Incorrect, max is 80 here)
  * Combination: Item 2 (Weight 3, Value 40) + Item 2 (Weight 3, Value 40) -\> Total Weight 6, Total Value 80.

Maximum Value should be 80.

-----

### Java Code (Bottom-Up / Tabulation)

```java
import java.util.Arrays;

public class UnboundedKnapsack {

    /**
     * Calculates the maximum value that can be obtained in an unbounded knapsack.
     *
     * @param weights An array of weights of the items.
     * @param values  An array of values of the items.
     * @param capacity The maximum capacity of the knapsack.
     * @return The maximum total value that can be obtained.
     */
    public int solveUnboundedKnapsack(int[] weights, int[] values, int capacity) {
        int numItems = weights.length;

        // dp[w] will store the maximum value for a knapsack of capacity 'w'.
        // We need dp array of size (capacity + 1) because indices go from 0 to capacity.
        int[] dp = new int[capacity + 1];

        // Base case: dp[0] = 0 (0 capacity knapsack has 0 value).
        // Arrays are initialized to 0 by default in Java, so explicit setting is not strictly needed.
        // Arrays.fill(dp, 0); // This line is optional as ints are 0 by default

        // Iterate through each possible capacity 'w' from 1 to 'capacity'.
        // For each 'w', we try to fill it using any of the available items.
        for (int w = 1; w <= capacity; w++) {
            // For the current capacity 'w', try to include each item.
            for (int i = 0; i < numItems; i++) {
                // If the current item's weight is less than or equal to the current capacity 'w'.
                if (weights[i] <= w) {
                    // We have two choices:
                    // 1. Don't include the current item 'i': In this case, the value is dp[w] (from previous calculations for 'w').
                    // 2. Include the current item 'i':
                    //    The remaining capacity would be (w - weights[i]).
                    //    The maximum value for this remaining capacity is dp[w - weights[i]].
                    //    Since this is unbounded knapsack, we can potentially pick item 'i' again
                    //    from the remaining capacity. So, we use dp[w - weights[i]] (which might already
                    //    contain the value for 'i' if it was picked before for a smaller capacity).
                    //    Add the current item's value: values[i].
                    // We take the maximum of these two choices.
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }

        // The final answer is the maximum value for the given 'capacity'.
        return dp[capacity];
    }

    public static void main(String[] args) {
        UnboundedKnapsack solver = new UnboundedKnapsack();

        // Example 1:
        int[] weights1 = {1, 3, 4};
        int[] values1 = {10, 40, 50};
        int capacity1 = 6;
        // Expected: 80 (Pick item with weight 3, value 40 twice)
        System.out.println("Max value for capacity " + capacity1 + ": " + solver.solveUnboundedKnapsack(weights1, values1, capacity1));

        // Example 2:
        int[] weights2 = {2, 3, 1}; // Note: item with weight 1 is last
        int[] values2 = {10, 15, 6};
        int capacity2 = 7;
        // Calculation:
        // dp[0] = 0
        // dp[1]: Try item (1,6). dp[1] = max(0, dp[0]+6) = 6
        // dp[2]: Try item (1,6). dp[2] = max(0, dp[1]+6) = 12. Also (2,10). dp[2]=max(12, dp[0]+10)=12.
        // dp[3]: Try (1,6). dp[3]=max(0, dp[2]+6)=18. Also (2,10). dp[3]=max(18, dp[1]+10)=max(18,6+10=16)=18. Also (3,15). dp[3]=max(18, dp[0]+15)=18.
        // dp[4]: Try (1,6). dp[4]=max(0, dp[3]+6)=24. Try (2,10). dp[4]=max(24, dp[2]+10)=max(24,12+10=22)=24. Try (3,15). dp[4]=max(24, dp[1]+15)=max(24,6+15=21)=24.
        // dp[5]: Try (1,6). dp[5]=max(0, dp[4]+6)=30. Try (2,10). dp[5]=max(30, dp[3]+10)=max(30,18+10=28)=30. Try (3,15). dp[5]=max(30, dp[2]+15)=max(30,12+15=27)=30.
        // dp[6]: Try (1,6). dp[6]=max(0, dp[5]+6)=36. Try (2,10). dp[6]=max(36, dp[4]+10)=max(36,24+10=34)=36. Try (3,15). dp[6]=max(36, dp[3]+15)=max(36,18+15=33)=36.
        //       Also: 2*weight=3 (15). (3,15)*2= (6,30). Max value 30.
        //       dp[6] = max(dp[6], dp[6-3]+15) = max(36, dp[3]+15) = max(36, 18+15=33) = 36.
        // dp[7]: Try (1,6). dp[7]=max(0, dp[6]+6)=42. Try (2,10). dp[7]=max(42, dp[5]+10)=max(42,30+10=40)=42. Try (3,15). dp[7]=max(42, dp[4]+15)=max(42,24+15=39)=42.
        // Max value will be 42. (7 times item with weight 1, value 6)
        System.out.println("Max value for capacity " + capacity2 + ": " + solver.solveUnboundedKnapsack(weights2, values2, capacity2)); // Expected: 42

        // Example 3: No items fit
        int[] weights3 = {10};
        int[] values3 = {100};
        int capacity3 = 5;
        System.out.println("Max value for capacity " + capacity3 + ": " + solver.solveUnboundedKnapsack(weights3, values3, capacity3)); // Expected: 0

        // Example 4: Single item, multiple picks
        int[] weights4 = {5};
        int[] values4 = {100};
        int capacity4 = 17;
        // Expected: 300 (3 times item with weight 5, value 100)
        System.out.println("Max value for capacity " + capacity4 + ": " + solver.solveUnboundedKnapsack(weights4, values4, capacity4));
    }
}
```

-----

### Top-Down (Memoization) Approach

Unbounded Knapsack ko Top-Down (Memoization) se bhi solve kiya ja sakta hai.

**DP State:**
`memo[w]` = Maximum value for a knapsack of capacity `w`.

**Recursive Function:**
`solve(w, weights, values, memo)`

**Logic:**

  * **Base Case:** If `w == 0`, return `0`.
  * **Memoization Check:** If `memo[w]` already computed (`!= -1`), return it.
  * **Recursive Step:**
      * Initialize `max_val = 0`.
      * Loop `i` from `0` to `num_items - 1`:
          * If `weights[i] <= w`:
              * `current_val = solve(w - weights[i], weights, values, memo) + values[i]`
              * `max_val = Math.max(max_val, current_val)`
      * Store `max_val` in `memo[w]` and return.

**Initial Call:** `solve(capacity, weights, values, memo)`

```java
import java.util.Arrays;

public class UnboundedKnapsackTopDown {

    private int[] memo;
    private int[] weights_global;
    private int[] values_global;

    /**
     * Calculates the maximum value that can be obtained in an unbounded knapsack using memoization.
     *
     * @param weights An array of weights of the items.
     * @param values  An array of values of the items.
     * @param capacity The maximum capacity of the knapsack.
     * @return The maximum total value that can be obtained.
     */
    public int solveUnboundedKnapsack(int[] weights, int[] values, int capacity) {
        this.weights_global = weights;
        this.values_global = values;
        memo = new int[capacity + 1];
        // Initialize memo array with -1 to indicate uncomputed states.
        Arrays.fill(memo, -1);

        return solve(capacity);
    }

    private int solve(int currentCapacity) {
        // Base Case: If capacity is 0, no more items can be added, so value is 0.
        if (currentCapacity == 0) {
            return 0;
        }

        // Memoization Check: If the result for this capacity is already computed.
        if (memo[currentCapacity] != -1) {
            return memo[currentCapacity];
        }

        int max_val = 0; // Initialize max_val for currentCapacity

        // Try to include each item
        for (int i = 0; i < weights_global.length; i++) {
            // If the current item can fit in the remaining capacity
            if (weights_global[i] <= currentCapacity) {
                // Recursively call for the remaining capacity after adding the current item.
                // Add the current item's value to the result of the subproblem.
                int value_if_taken = solve(currentCapacity - weights_global[i]) + values_global[i];
                max_val = Math.max(max_val, value_if_taken);
            }
        }

        // Store the computed result in the memoization table.
        return memo[currentCapacity] = max_val;
    }

    public static void main(String[] args) {
        UnboundedKnapsackTopDown solver = new UnboundedKnapsackTopDown();

        int[] weights1 = {1, 3, 4};
        int[] values1 = {10, 40, 50};
        int capacity1 = 6;
        System.out.println("Max value for capacity " + capacity1 + " (Top-Down): " + solver.solveUnboundedKnapsack(weights1, values1, capacity1)); // Expected: 80

        int[] weights2 = {2, 3, 1};
        int[] values2 = {10, 15, 6};
        int capacity2 = 7;
        System.out.println("Max value for capacity " + capacity2 + " (Top-Down): " + solver.solveUnboundedKnapsack(weights2, values2, capacity2)); // Expected: 42

        int[] weights3 = {10};
        int[] values3 = {100};
        int capacity3 = 5;
        System.out.println("Max value for capacity " + capacity3 + " (Top-Down): " + solver.solveUnboundedKnapsack(weights3, values3, capacity3)); // Expected: 0

        int[] weights4 = {5};
        int[] values4 = {100};
        int capacity4 = 17;
        System.out.println("Max value for capacity " + capacity4 + " (Top-Down): " + solver.solveUnboundedKnapsack(weights4, values4, capacity4)); // Expected: 300
    }
}
```

-----

### Unbounded Knapsack aur 0/1 Knapsack Mein Mukhya Antar

| Feature             | 0/1 Knapsack                                 | Unbounded Knapsack                             |
| :------------------ | :------------------------------------------- | :--------------------------------------------- |
| **Item Use** | Har item ko ek ya zero baar use kar sakte hain. | Har item ko kitni bhi baar use kar sakte hain. |
| **DP Recurrence** | `dp[w] = max(dp[w], dp[w - weight[i]] + value[i])` (For each item, `w` loop goes *down* from `Capacity` to `weight[i]`, to use *previous* `dp[w - weight[i]]` state) | `dp[w] = max(dp[w], dp[w - weight[i]] + value[i])` (For each item, `w` loop goes *up* from `1` to `Capacity`, to use *current* `dp[w - weight[i]]` state) |
| **Tabulation Loop** | `for item ... for weight (Capacity down to weight[i]) ...` | `for weight ... for item ...` (or vice-versa, as long as `dp[w - weight[i]]` is computed for current item) |
| **Space** | $O(\\text{Capacity})$ (optimized)             | $O(\\text{Capacity})$                           |
| **Conceptual** | Once an item is chosen, it's "gone".        | An item, once chosen, is still "available" for the remaining capacity. |

**Important Loop Order Difference (Tabulation):**

  * **0/1 Knapsack:**

    ```java
    for (int i = 0; i < numItems; i++) { // Iterate through items
        for (int w = capacity; w >= weights[i]; w--) { // Iterate capacity downwards
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    ```

    Yahan `dp[w - weights[i]]` pichhle item ke liye ya current item ke liye, lekin current item ke *previous* weight calculation se aata hai, jo ensure karta hai ki ek item ek hi baar pick ho. `dp[w - weights[i]]` will refer to the `dp` state *before* considering the current item `i` for that smaller capacity.

  * **Unbounded Knapsack:**

    ```java
    for (int w = 1; w <= capacity; w++) { // Iterate through capacities
        for (int i = 0; i < numItems; i++) { // Iterate through items
            if (weights[i] <= w) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
    }
    ```

    Yahan `dp[w - weights[i]]` current item ke liye ya pichhle item ke liye, lekin current item ke *current* weight calculation se aata hai, jo allow karta hai ki ek item kitni bhi baar pick ho. `dp[w - weights[i]]` might have already been updated by considering the current item `i` for a smaller capacity, effectively allowing multiple picks.

----
The current date is **July 8, 2025**.

Bahut accha sawal\! Unbounded Knapsack ek bahut versatile DP pattern hai aur kai alag-alag roop mein interview mein pucha jaata hai. Chaliye, isse related 3 top interview questions, unke approach, aur solutions ke saath dekhte hain.

-----

### 1\. Coin Change II (LeetCode 518) - Number of Combinations

**Problem Statement:**

Aapko `coins` naam ka ek integer array diya gaya hai, jo alag-alag tarah ke coins ko darshata hai, aur ek integer `amount` diya gaya hai, jo total amount ko darshata hai.

Aapko return karna hai ki kitne tareekon se aap `amount` bana sakte hain using these coins. Assume karein ki har coin type ki supply **unlimited** hai (yani, aap har coin ko kitni bhi baar use kar sakte hain). Order important nahi hai (yani, `[1,2]` aur `[2,1]` ek hi combination mana jaega).

**Example:**

  * `amount = 5`, `coins = [1, 2, 5]`
  * **Output:** 4
  * **Explanation:**
      * `5 = 5`
      * `5 = 2 + 2 + 1`
      * `5 = 2 + 1 + 1 + 1`
      * `5 = 1 + 1 + 1 + 1 + 1`

**Constraints:**

  * `1 <= coins.length <= 12`
  * `1 <= coins[i] <= 5000`
  * All the values of `coins` are unique.
  * `0 <= amount <= 5000`
  * Result fits in a 32-bit integer.

-----

#### Approach: Coin Change II (Unbounded Knapsack - Number of Ways)

Yeh ek classic Unbounded Knapsack problem hai jahan hum "number of ways" calculate kar rahe hain. `amount` hamari "capacity" hai aur `coins` hamare "items" hain jinke `weight` aur `value` dono coin ki value hi hai.

**Key Idea:**

Order important nahi hai, isliye hum combinations count karte samay yeh ensure karenge ki hum har coin ko sirf ek baar "process" karein for all possible sub-amounts, aur bade coin ko process karne ke baad usse chhote coin ke saath double counting na ho. Iske liye ek specific loop order use hota hai.

**DP State Definition:**

`dp[i]` = Number of ways to make amount `i`.

**Base Case:**

`dp[0] = 1`. (Amount 0 banane ka ek tareeka hai: koi coin na lena).

**Transitions (Filling the `dp` table):**

Hum `dp` array ko `0` se `amount` tak fill karenge.

**Crucial Loop Order:**
To avoid counting permutations instead of combinations (e.g., `[1,2]` and `[2,1]` are same), we must iterate through the `coins` first, and then through `amounts` for each coin. This ensures that for a particular `coin`, we only consider adding it to combinations already formed by *smaller or equal* coins, preventing re-ordering.

```java
// Iterate through each coin
for (int coin : coins) {
    // For each coin, iterate through amounts from the coin's value up to the target amount.
    // This way, we allow current coin to be used multiple times if capacity allows.
    for (int j = coin; j <= amount; j++) {
        // dp[j] ko update karo.
        // Yeh current amount 'j' ko banane ke tareeke hain.
        // Ismein hum 'coin' ko add kar sakte hain, agar humne pehle 'j - coin' amount banaya ho.
        // dp[j - coin] tells us how many ways we could make the remaining amount.
        // Adding current 'coin' to each of those ways forms a new way to make 'j'.
        dp[j] += dp[j - coin];
    }
}
```

**Why this loop order for combinations?**
Agar `dp[j]` ke calculation mein `j - coin` ka `dp` value `current coin` ke `for loop` ke bahar se aata, toh `[1,2]` aur `[2,1]` alag counts hote. Jab `coin = 1` ke liye loop chalta hai, toh `dp[2]` update hota hai `dp[1]` se. Jab `coin = 2` ke liye loop chalta hai, toh `dp[2]` `dp[0]` se update hota hai. Har coin ke liye inner loop current coin ko *multiple times* use karne ki permission deta hai (kyunki `dp[j - coin]` already current coin ko consider kar chuka ho sakta hai), aur outer loop ensure karta hai ki bade coin ko add karne se chhote coin ke order ki double counting na ho.

**Final Answer:**

`dp[amount]` return karo.

-----

#### Solution: Coin Change II (Java Code)

```java
import java.util.Arrays;

public class CoinChangeII {

    public int change(int amount, int[] coins) {
        // dp[i] will store the number of ways to make amount 'i'.
        int[] dp = new int[amount + 1];

        // Base case: There is one way to make amount 0 (by taking no coins).
        dp[0] = 1;

        // Iterate through each coin.
        // This outer loop ensures that combinations like (1,2) and (2,1) are counted as one.
        // By processing coins one by one, and only using previous amounts formed by *this* coin or smaller coins,
        // we guarantee unique combinations.
        for (int coin : coins) {
            // For each coin, iterate through possible amounts from the coin's value up to the target amount.
            // If the amount 'j' is less than 'coin', we cannot use this 'coin' for 'j'.
            for (int j = coin; j <= amount; j++) {
                // To form amount 'j', we can either:
                // 1. Not use the current 'coin': In this case, dp[j] remains as it was from previous coins.
                // 2. Use the current 'coin': This means we have 'j - coin' amount remaining to make,
                //    and we can use the current 'coin' again (unbounded nature) or previous coins.
                //    The number of ways to do this is dp[j - coin].
                // We add these ways to the current dp[j].
                dp[j] += dp[j - coin];
            }
        }

        // The result is the number of ways to make the target 'amount'.
        return dp[amount];
    }

    public static void main(String[] args) {
        CoinChangeII solver = new CoinChangeII();

        // Example 1:
        int amount1 = 5;
        int[] coins1 = {1, 2, 5};
        System.out.println("Amount: " + amount1 + ", Coins: " + Arrays.toString(coins1) + " -> Ways: " + solver.change(amount1, coins1)); // Expected: 4

        // Example 2:
        int amount2 = 3;
        int[] coins2 = {2};
        System.out.println("Amount: " + amount2 + ", Coins: " + Arrays.toString(coins2) + " -> Ways: " + solver.change(amount2, coins2)); // Expected: 0

        // Example 3:
        int amount3 = 10;
        int[] coins3 = {10};
        System.out.println("Amount: " + amount3 + ", Coins: " + Arrays.toString(coins3) + " -> Ways: " + solver.change(amount3, coins3)); // Expected: 1
    }
}
```

-----

### 2\. Coin Change (LeetCode 322) - Minimum Number of Coins

**Problem Statement:**

Aapko `coins` naam ka ek integer array diya gaya hai, jo alag-alag tarah ke coins ko darshata hai, aur ek integer `amount` diya gaya hai, jo total amount ko darshata hai.

Aapko return karna hai **minimum number of coins** jinhe use karke aap total `amount` bana sakte hain. Agar woh amount un coins se nahi banaya ja sakta, toh `-1` return karein. Assume karein ki har coin type ki supply **unlimited** hai.

**Example:**

  * `amount = 11`, `coins = [1, 2, 5]`
  * **Output:** 3
  * **Explanation:** `11 = 5 + 5 + 1` (3 coins)

**Constraints:**

  * `1 <= coins.length <= 12`
  * `1 <= coins[i] <= 2^31 - 1`
  * `0 <= amount <= 10^4`

-----

#### Approach: Coin Change (Unbounded Knapsack - Minimum Value)

Yeh bhi Unbounded Knapsack ka hi ek variation hai, jahan hum "minimum count" dhundh rahe hain.

**Key Idea:**

Har amount `j` ke liye, hum saare available coins ko try karenge, aur dekhenge ki kis coin ko use karne se minimum coins mein amount `j` ban sakta hai.

**DP State Definition:**

`dp[i]` = Minimum number of coins required to make amount `i`.

**Base Case:**

`dp[0] = 0`. (Amount 0 banane ke liye 0 coins chahiye).

**Initialization:**

`dp` array ko `amount + 1` size ka banayenge. `dp[0]` ko `0` set karenge. Baaki saare elements ko `Integer.MAX_VALUE` se initialize karenge (ya `amount + 1`, kyuki `amount` se zyada coins ki zaroorat nahi padegi agar solution exist karta hai). Yeh `Integer.MAX_VALUE` batata hai ki abhi tak woh amount `unreachable` hai.

**Transitions (Filling the `dp` table):**

Hum `dp` array ko `1` se `amount` tak fill karenge.

```java
// Iterate through each possible amount from 1 to target amount.
for (int j = 1; j <= amount; j++) {
    // For each amount 'j', iterate through all available coins.
    for (int coin : coins) {
        // If the current coin can be used to form amount 'j' (i.e., j >= coin)
        // AND if the subproblem (j - coin) was reachable (dp[j - coin] is not Integer.MAX_VALUE)
        if (coin <= j && dp[j - coin] != Integer.MAX_VALUE) {
            // Update dp[j] with the minimum of its current value and
            // (1 + dp[j - coin]), where 1 is for the current 'coin' and
            // dp[j - coin] is the minimum coins needed for the remaining amount.
            dp[j] = Math.min(dp[j], 1 + dp[j - coin]);
        }
    }
}
```

**Loop Order:**
`Coin Change II` mein combinations ke liye `coin` ka loop outer tha, taaki order ki double counting na ho. `Coin Change (Minimum Coins)` mein, order se fark nahi padta, humein bas minimum count chahiye. Isliye, `amount` ka loop outer rakhna generally better hota hai, ya inner bhi ho sakta hai. Dono cases mein correct answer aayega, kyuki `dp[j - coin]` already correct minimum value rakhega (ya to kisi pichhle coin se, ya current coin se jo use ho chuka hai for a smaller `j - coin`).

**Final Answer:**

`dp[amount]` return karo. Agar `dp[amount]` abhi bhi `Integer.MAX_VALUE` hai, toh `-1` return karein (matlab amount banaya nahi ja sakta).

-----

#### Solution: Coin Change (Java Code)

```java
import java.util.Arrays;

public class CoinChange {

    public int coinChange(int[] coins, int amount) {
        // dp[i] will store the minimum number of coins to make amount 'i'.
        // Initialize with amount + 1 (a value larger than any possible valid count)
        // to represent infinity/unreachable amounts.
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Using amount + 1 as "infinity"

        // Base case: 0 coins needed to make amount 0.
        dp[0] = 0;

        // Iterate through each amount from 1 to the target amount.
        for (int i = 1; i <= amount; i++) {
            // For each amount 'i', try to use every coin type.
            for (int coin : coins) {
                // If the current coin can be used (i.e., 'i' is greater than or equal to 'coin')
                // AND if the subproblem (i - coin) was reachable (not "infinity").
                if (coin <= i && dp[i - coin] != amount + 1) {
                    // Update dp[i] with the minimum of its current value
                    // and (1 + dp[i - coin]).
                    // 1 represents using the current 'coin', and dp[i - coin] is the min coins
                    // for the remaining amount.
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        // If dp[amount] is still "infinity", it means the amount cannot be made.
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        CoinChange solver = new CoinChange();

        // Example 1:
        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println("Amount: " + amount1 + ", Coins: " + Arrays.toString(coins1) + " -> Min Coins: " + solver.coinChange(coins1, amount1)); // Expected: 3

        // Example 2:
        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("Amount: " + amount2 + ", Coins: " + Arrays.toString(coins2) + " -> Min Coins: " + solver.coinChange(coins2, amount2)); // Expected: -1

        // Example 3:
        int[] coins3 = {1};
        int amount3 = 0;
        System.out.println("Amount: " + amount3 + ", Coins: " + Arrays.toString(coins3) + " -> Min Coins: " + solver.coinChange(coins3, amount3)); // Expected: 0

        // Example 4: Large amount, small coins
        int[] coins4 = {1, 5, 10, 25};
        int amount4 = 100;
        System.out.println("Amount: " + amount4 + ", Coins: " + Arrays.toString(coins4) + " -> Min Coins: " + solver.coinChange(coins4, amount4)); // Expected: 4 (25*4)
    }
}
```

-----

### 3\. Rod Cutting Problem (GeeksforGeeks, similar to Unbounded Knapsack)

**Problem Statement:**

Aapko `length` n ki ek rod di gayi hai. Is rod ko cut karke alag-alag tukdon mein becha ja sakta hai. Har tukde ki `length i` ke liye ek `price[i]` diya gaya hai. Aapko rod ko is tarah se cut karna hai ki **maximum profit** ho.
Aap ek hi length ke tukde ko kitni bhi baar use kar sakte hain (kyunki aap rod ko alag-alag tarah se kaat sakte hain, aur har cut ek independent decision hai).

**Example:**

  * `N = 8` (Original rod length)
  * `price = [1, 5, 8, 9, 10, 17, 17, 20]` (Price of pieces of length 1, 2, ..., 8 respectively)
      * `price[0]` is for length 1, `price[1]` for length 2, etc. (0-indexed array, so `price[i]` corresponds to `length i+1`). Let's adjust for 1-indexed lengths in problem, so `price[length-1]` is for length `length`.

Let's assume `price` array maps directly: `price[i]` is the price for a piece of length `i`.

  * `lengths = [1, 2, 3, 4, 5, 6, 7, 8]`
  * `prices = [1, 5, 8, 9, 10, 17, 17, 20]` (price for length 1, 2, ..., 8)
  * `N = 8`

**Output:** Maximum profit.

**Expected Output Calculation:**

  * For `N=8`:
      * Cut into two pieces of length 4: `price[4-1] + price[4-1] = 9 + 9 = 18`
      * Cut into one piece of length 2 and one of length 6: `price[2-1] + price[6-1] = 5 + 17 = 22`
      * Cut into one piece of length 1 and one of length 7: `price[1-1] + price[7-1] = 1 + 17 = 18`
      * Cut into eight pieces of length 1: `8 * price[1-1] = 8 * 1 = 8`
      * Optimal: Cut into two pieces of length 2 and one of length 4: `price[2-1] + price[2-1] + price[4-1] = 5 + 5 + 9 = 19`
      * Optimal: One piece of length 2 and one piece of length 6. `5 + 17 = 22`.

Wait, the example from GeeksForGeeks for N=8 has output 22.
Path: Two pieces of length 2 (5+5) and one piece of length 4 (9) -\> 19. No, this is incorrect.
The prices were `[1, 5, 8, 9, 10, 17, 17, 20]` for length 1 to 8.
For `N=8`:
Two pieces of length 4. Value = `prices[3] + prices[3] = 9 + 9 = 18`.
One piece of length 2 and one of length 6. Value = `prices[1] + prices[5] = 5 + 17 = 22`.
This is the maximum.

**Constraints:**

  * `1 <= N <= 1000`
  * `1 <= price[i] <= 1000` (for 0-indexed price array, `i` from `0` to `N-1`)

-----

#### Approach: Rod Cutting Problem (Unbounded Knapsack)

Yeh problem bilkul Unbounded Knapsack jaisa hai.

  * **Knapsack Capacity:** Original rod ki length `N`.
  * **Items:** Har possible `length` ka tukda.
  * **Item Weight:** Tukde ki `length`.
  * **Item Value:** Us `length` ke tukde ka `price`.

Chunki aap rod ko kitni bhi baar kaat sakte hain, aur har cut se independent piece milta hai, isliye har `length` ke tukde ko "kitni bhi baar" use kiya ja sakta hai. Hence, Unbounded Knapsack.

**DP State Definition:**

`dp[i]` = Maximum profit jo `i` length ki rod ko cut karke banaya ja sakta hai.

**Base Case:**

`dp[0] = 0`. (0 length ki rod se 0 profit).

**Initialization:**

`dp` array ko `N + 1` size ka banayenge. `dp[0]` ko `0` set karenge. Baaki sab `0`.

**Transitions (Filling the `dp` table):**

Hum `dp` array ko `1` se `N` tak fill karenge.

```java
// Iterate through each possible rod length 'i' (which is our capacity).
for (int i = 1; i <= N; i++) {
    // For each rod length 'i', consider all possible first cuts (j from 1 to i).
    // A cut of length 'j' (with price[j-1]) leaves 'i - j' length remaining.
    for (int j = 1; j <= i; j++) { // j is the length of the current piece
        // price array is 0-indexed, so price for length 'j' is price[j-1].
        // dp[i] ko update karo.
        // current dp[i] (already computed max profit for length 'i')
        // vs (price of piece of length 'j' + max profit for remaining length 'i - j')
        dp[i] = Math.max(dp[i], price[j - 1] + dp[i - j]);
    }
}
```

**Final Answer:**

`dp[N]` return karo.

-----

#### Solution: Rod Cutting Problem (Java Code)

```java
import java.util.Arrays;

public class RodCutting {

    /**
     * Calculates the maximum profit obtainable by cutting a rod of length N.
     *
     * @param price An array where price[i] is the price of a piece of length (i+1).
     * So, price[0] is for length 1, price[1] for length 2, etc.
     * @param N     The total length of the rod.
     * @return The maximum profit.
     */
    public int cutRod(int price[], int N) {
        // dp[i] will store the maximum profit for a rod of length 'i'.
        int[] dp = new int[N + 1];

        // Base case: A rod of length 0 yields 0 profit.
        dp[0] = 0;

        // Iterate through each possible rod length from 1 to N (our "capacity").
        for (int i = 1; i <= N; i++) {
            // For each rod length 'i', consider all possible first cuts.
            // A cut can be of length 'j', where 'j' ranges from 1 to 'i'.
            for (int j = 1; j <= i; j++) {
                // The price for a piece of length 'j' is price[j-1] (due to 0-indexing).
                // The remaining length after taking a piece of length 'j' is (i - j).
                // The maximum profit for the remaining length (i - j) is dp[i - j].
                // We want to maximize the profit, so we take the maximum of:
                // 1. The current best profit for length 'i' (dp[i]).
                // 2. (price of current cut 'j') + (max profit from remaining length 'i - j').
                dp[i] = Math.max(dp[i], price[j - 1] + dp[i - j]);
            }
        }

        // The result is the maximum profit for the original rod length 'N'.
        return dp[N];
    }

    public static void main(String[] args) {
        RodCutting solver = new RodCutting();

        // Example 1:
        // prices: 1, 5, 8, 9, 10, 17, 17, 20 (for lengths 1 to 8)
        int[] prices1 = {1, 5, 8, 9, 10, 17, 17, 20};
        int N1 = 8;
        System.out.println("Max profit for rod length " + N1 + ": " + solver.cutRod(prices1, N1)); // Expected: 22

        // Example 2:
        int[] prices2 = {2, 5, 7, 8}; // for lengths 1, 2, 3, 4
        int N2 = 5; // Rod of length 5
        // Expected: 12 (cut into 2 pieces of length 2 and 1 piece of length 1, or 1 piece of length 3 and 1 piece of length 2 etc.)
        // Path: (Length 2, price 5) + (Length 3, price 7) = 5 + 7 = 12
        // Path: (Length 1, price 2) + (Length 4, price 8) = 2 + 8 = 10
        // Path: (Length 1, price 2) * 5 = 10
        // Path: (Length 2, price 5) + (Length 2, price 5) + (Length 1, price 2) = 5+5+2 = 12
        System.out.println("Max profit for rod length " + N2 + ": " + solver.cutRod(prices2, N2)); // Expected: 12

        // Example 3:
        int[] prices3 = {1}; // for length 1
        int N3 = 1;
        System.out.println("Max profit for rod length " + N3 + ": " + solver.cutRod(prices3, N3)); // Expected: 1
    }
}
```

-----
