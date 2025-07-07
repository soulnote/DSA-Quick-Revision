
## Knapsack Problem Kya Hota Hai?

Imagine karo aapke paas ek **bag** (jisko knapsack bolte hain) hai jiski ek **fixed weight capacity** hai. Aapke paas kuch **items** hain, har item ka apna ek **weight** aur ek **value** hai. Aapko un items mein se kuch select karke bag mein aise rakhna hai ki bag ki capacity cross na ho, aur aapke selected items ki **total value maximum** ho.

Knapsack Problem ke do main types hote hain:

1.  **0/1 Knapsack Problem:** Isme aap kisi item ko ya toh **poora** le sakte ho (1) ya **bilkul nahi** (0). Aap item ka koi **fraction** (hissa) nahi le sakte. Isko hi hum **Dynamic Programming (DP)** se solve karte hain.
2.  **Fractional Knapsack Problem:** Isme aap item ka koi bhi fraction le sakte ho. Ye usually **Greedy Approach** se solve hota hai.

Hum yahaan **0/1 Knapsack Problem** par focus karenge, kyunki aapne DP se solve karne ko pucha hai.

-----

## DP (Dynamic Programming) se Knapsack Problem Kaise Solve Karte Hain?

Knapsack Problem mein **overlapping subproblems** (ek hi problem ka chhota hissa baar-baar solve karna padta hai) aur **optimal substructure** (badi problem ka best solution, uske chhote parts ke best solution se banta hai) hoti hain. Isi wajah se ye DP ke liye perfect hai.

**Approach (Tareeka):**

Knapsack Problem ko DP se solve karne ke liye hum ek **2D array (ya table)** use karte hain. Is table ko **`dp`** bolte hain.

`dp[i][w]` ka matlab hai: Pehli `i` items ko use karke `w` **total weight** tak kitni **maximum value** mil sakti hai.

**Example ke liye:**

Suppose hamare paas 3 items hain:

  * Item 1: Weight = 1, Value = 10
  * Item 2: Weight = 3, Value = 40
  * Item 3: Weight = 4, Value = 50

Bag ki **maximum capacity (W)** = 5

**DP ko solve karne ki soch:**

Jab hum `dp[i][w]` calculate kar rahe hote hain, toh hum `i-th` item ke liye **do options** consider karte hain:

1.  **`i-th` item ko bag mein mat rakho (Exclude):** Agar hum `i-th` item ko nahi lete hain, toh maximum value wahi hogi jo `i-1` items ko use karke `w` weight ke liye mil sakti thi.

      * `dp[i-1][w]`

2.  **`i-th` item ko bag mein rakho (Include):** Agar hum `i-th` item ko lete hain, toh pehle ye check karna hoga ki `i-th` item ka weight (`weight[i]`) current capacity (`w`) se kam ya barabar hai ya nahi. Agar fit ho sakta hai, toh maximum value hogi `i-th` item ki value (`value[i]`) plus bachi hui capacity (`w - weight[i]`) ke liye `i-1` items se mili maximum value.

      * `value[i] + dp[i-1][w - weight[i]]`

Finally, `dp[i][w]` in dono options mein se **maximum value** hogi.

  * `dp[i][w] = max(dp[i-1][w], value[i] + dp[i-1][w - weight[i]])` (Agar `weight[i] <= w` hai)
  * `dp[i][w] = dp[i-1][w]` (Agar `weight[i] > w` hai, matlab item fit nahi ho sakti)

**Base Cases (Shuruaati Conditions):**

  * `dp[0][w] = 0` for all `w` (Jab koi item nahi hai, toh value 0 hogi).
  * `dp[i][0] = 0` for all `i` (Jab bag ki capacity 0 hai, toh koi item nahi rakh sakte).

-----

### 1\. Bottom-Up Approach (Tabulation)

Is approach mein hum DP table ko **chhote subproblems** se start karke **bade subproblems** ki taraf fill karte hain.

**Algorithm (Tareeka):**

1.  Ek **`(n+1) x (W+1)` size** ka `dp` table banao aur sab values ko **0** se initialize karo. (`n` items ki sankhya hai, `W` bag ki capacity hai).
2.  Outer loop ko items ki sankhya (`i` from 1 to `n`) ke liye chalao.
3.  Inner loop ko bag ki capacity (`w` from 1 to `W`) ke liye chalao.
4.  Har cell `dp[i][w]` ke liye upar bataye gaye recurrence relation ko use karo.

**Example ke saath Bottom-Up (Tabulation):**

  * Items:
      * Item 1 (index 1): Weight = 1, Value = 10
      * Item 2 (index 2): Weight = 3, Value = 40
      * Item 3 (index 3): Weight = 4, Value = 50
  * Capacity (W) = 5
  * `n = 3` (Total items)

`dp` table (`(n+1) x (W+1)` = `4 x 6`):

| `i\w` | 0 | 1 | 2 | 3 | 4 | 5 |
| :---- | :- | :- | :- | :- | :- | :- |
| **0** | 0 | 0 | 0 | 0 | 0 | 0 |
| **1** | ? | ? | ? | ? | ? | ? |
| **2** | ? | ? | ? | ? | ? | ? |
| **3** | ? | ? | ? | ? | ? | ? |

**Table kaise fill karenge:**

  * **i = 1 (Item 1: w=1, v=10)**

      * `dp[1][0] = 0`
      * `dp[1][1]`: `weight[1]=1 <= w=1`. `max(dp[0][1], 10 + dp[0][1-1]) = max(0, 10 + 0) = 10`
      * ... (saare w ke liye 10 hi aayega, kyunki item 1 fit ho jaati hai)
      * `dp[1][5] = 10`

  * **i = 2 (Item 2: w=3, v=40)**

      * `dp[2][0] = 0`
      * `dp[2][1]`: `weight[2]=3 > w=1`. Item 2 fit nahi ho sakti, toh `dp[2][1] = dp[1][1] = 10`
      * `dp[2][2]`: `weight[2]=3 > w=2`. `dp[2][2] = dp[1][2] = 10`
      * `dp[2][3]`: `weight[2]=3 <= w=3`. `max(dp[1][3], 40 + dp[1][3-3]) = max(10, 40 + dp[1][0]) = max(10, 40 + 0) = 40` (Item 2 liya, Item 1 nahi)
      * `dp[2][4]`: `weight[2]=3 <= w=4`. `max(dp[1][4], 40 + dp[1][4-3]) = max(10, 40 + dp[1][1]) = max(10, 40 + 10) = 50` (Item 2 aur Item 1 dono liye)
      * `dp[2][5]`: `weight[2]=3 <= w=5`. `max(dp[1][5], 40 + dp[1][5-3]) = max(10, 40 + dp[1][2]) = max(10, 40 + 10) = 50`

  * **i = 3 (Item 3: w=4, v=50)**

      * `dp[3][0] = 0`
      * `dp[3][1]`: `weight[3]=4 > w=1`. `dp[3][1] = dp[2][1] = 10`
      * ... (w = 2, 3 ke liye bhi item 3 fit nahi hogi)
      * `dp[3][4]`: `weight[3]=4 <= w=4`. `max(dp[2][4], 50 + dp[2][4-4]) = max(50, 50 + dp[2][0]) = max(50, 50 + 0) = 50` (Item 3 liya, Item 1 & 2 mein se kuch nahi)
      * `dp[3][5]`: `weight[3]=4 <= w=5`. `max(dp[2][5], 50 + dp[2][5-4]) = max(50, 50 + dp[2][1]) = max(50, 50 + 10) = 60` (Item 3 aur Item 1 dono liye)

**Final `dp` table:**

| `i\w` | 0 | 1 | 2 | 3 | 4 | 5 |
| :---- | :- | :- | :- | :- | :- | :- |
| **0** | 0 | 0 | 0 | 0 | 0 | 0 |
| **1** | 0 | 10 | 10 | 10 | 10 | 10 |
| **2** | 0 | 10 | 10 | 40 | 50 | 50 |
| **3** | 0 | 10 | 10 | 40 | 50 | **60** |

**Answer:** `dp[3][5]` = **60**. (Aap Item 1 (w=1, v=10) aur Item 3 (w=4, v=50) loge, total weight 1+4=5, total value 10+50=60)

-----

### 2\. Top-Down Approach (Memoization)

Is approach mein hum **recursion** use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 2D array) use karte hain, taaki same calculations baar-baar na karni pade.

**Algorithm (Tareeka):**

1.  Ek **`(n+1) x (W+1)` size** ka `memo` table banao aur sab values ko **-1** (ya koi invalid value) se initialize karo.
2.  Ek recursive function `dp(idx, current_capacity)` banao. (Yahaan `idx` current item index hai, aur `current_capacity` bachi hui bag capacity hai).
3.  Function ke andar, sabse pehle check karo ki `memo[idx][current_capacity]` pehle se calculated hai kya (`!= -1`)? Agar haan, toh stored value return kar do.
4.  **Base cases** (`idx < 0` ya `current_capacity == 0`) ko handle karo.
5.  Recurrence relation use karke `dp[idx][current_capacity]` calculate karo.
6.  Calculated value ko `memo[idx][current_capacity]` mein store karo aur usko return kar do.

**Example ke saath Top-Down (Memoization):**

  * Items (0-indexed):
      * Item 0: w=1, v=10
      * Item 1: w=3, v=40
      * Item 2: w=4, v=50
  * Capacity (W) = 5
  * `n = 3`

Hum `dp(n - 1, W)` yani `dp(2, 5)` se shuru karte hain (Item 2, Capacity 5).

**Recursive Tree Diagram (Top-Down):**

```
                                      dp(2, 5)
                      /                              \
          Exclude Item 2 (w=4, v=50)              Include Item 2 (Agar 4 <= 5)
         (value_if_excluded)                    (value_if_included)
        dp(1, 5)                                50 + dp(1, 5 - 4)
        (Item 1, Cap 5)                         50 + dp(1, 1)
     /         \                                   /           \
  dp(0, 5)   40 + dp(0, 5-3)                  dp(0, 1)   40 + dp(0, 1-3) (Invalid: 1-3 < 0, toh 0 value)
  (Item 0, Cap 5)   40 + dp(0, 2)             (Item 0, Cap 1)
 /     \            /     \                 /      \
dp(-1,5) 10+dp(-1,4) dp(-1,2) 10+dp(-1,1)  dp(-1,1) 10+dp(-1,0)
(Base: 0) (10+0=10)   (Base: 0) (10+0=10)   (Base: 0) (10+0=10)
  |      ^                |      ^               |      ^
  |      |                |      |               |      |
  10 <---+---------------10 (memo[0][5])      10 <----+--------------10 (memo[0][2])
  |                                                  |
  |                                                  |
  max(10, 40+10) = 50 <----------------------------- 50 (memo[1][5])
                                                     |
                                                     | (from 50 + dp(1,1))
                                                    max(10, 0) = 10 (memo[1][1]) (Item 1, w=3 can't fit in cap 1, so only option is dp(0,1))
                                                                 /      \
                                                               dp(0,1) 40+dp(0,-2) (Invalid)
                                                                (memo[0][1] = 10)
                                                                 /      \
                                                               dp(-1,1) 10+dp(-1,0)
                                                                 (0)      (10+0=10)

```
**Explanation (calculation flow):**

1.  `dp(2, 5)` call hota hai.
2.  Ye `dp(1, 5)` (exclude Item 2) aur `50 + dp(1, 1)` (include Item 2) ko calculate karta hai.
3.  `dp(1, 5)` calculate karne ke liye, ye `dp(0, 5)` aur `40 + dp(0, 2)` ko call karta hai.
4.  `dp(0, 5)` calculation:
    * Item 0 (w=1, v=10) ko `capacity 5` mein fit karo.
    * Options: exclude Item 0 (`dp(-1, 5) = 0`) or include Item 0 (`10 + dp(-1, 4) = 10 + 0 = 10`).
    * `dp(0, 5)` returns `max(0, 10) = 10`. Ye value `memo[0][5]` mein store ho jaati hai.
5.  `dp(0, 2)` calculation:
    * Same as above, `dp(0, 2)` returns `max(dp(-1,2), 10+dp(-1,1)) = max(0, 10+0) = 10`. Ye value `memo[0][2]` mein store ho jaati hai.
6.  `dp(1, 5)` returns `max(dp(0,5), 40 + dp(0,2)) = max(10, 40 + 10) = 50`. Ye `memo[1][5]` mein store ho jaati hai.
7.  Ab `50 + dp(1, 1)` part. `dp(1, 1)` calculation:
    * Item 1 (w=3, v=40) ko `capacity 1` mein fit karo. Ye fit nahi ho sakti.
    * Option: exclude Item 1 (`dp(0, 1)`).
    * `dp(0, 1)` calculation: Item 0 (w=1, v=10) ko `capacity 1` mein fit karo. Returns `max(dp(-1,1), 10+dp(-1,0)) = max(0, 10+0) = 10`. Ye `memo[0][1]` mein store ho jaati hai.
    * So, `dp(1, 1)` returns `max(dp(0,1), 0 (as Item 1 cant fit)) = max(10, 0) = 10`. Ye `memo[1][1]` mein store ho jaati hai.
8.  Finally, `dp(2, 5)` returns `max(dp(1,5), 50 + dp(1,1)) = max(50, 50 + 10) = 60`.

**Memoization ka Benefit:**

Tree mein aap dekhoge ki kuch subproblems (jaise `dp(0, 5)`, `dp(0, 2)`, `dp(0, 1)`) ki calculations **ek se zyada baar** ho rahi hain (dotted lines se show kiya gaya hai). Memoization ke saath, ek baar jab `dp(0, 2)` calculate ho jaata hai, toh uska result `memo[0][2]` mein store ho jaata hai. Agli baar jab `dp(0, 2)` ko call kiya jaata hai, toh ye directly `memo[0][2]` se value return kar deta hai, जिससे duplicate calculations avoid ho jaati hain.

Knapsack Problem ko DP se efficiently solve kiya ja sakta hai, chahe aap **Bottom-Up (Tabulation)** use karein ya **Top-Down (Memoization)**. Dono hi mein **overlapping subproblems** aur **optimal substructure** ka fayda uthaya jaata hai.

##  0/1 Knapsack Problem

**Problem Statement:** Given weights and values of N items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack. Each item can either be put in the knapsack or not (0/1 choice).

### Approach 1: Bottom-Up (Tabulation) for 0/1 Knapsack

```java
import java.util.Arrays;

public class Knapsack {

    /**
     * Solves the 0/1 Knapsack problem using Bottom-Up (Tabulation) Dynamic Programming.
     *
     * @param weights Array of weights of items.
     * @param values  Array of values of items.
     * @param W       Maximum capacity of the knapsack.
     * @return The maximum value that can be put in the knapsack.
     */
    public int solveKnapsackBottomUp(int[] weights, int[] values, int W) {
        int n = weights.length; // Number of items

        // dp[i][w] will store the maximum value that can be achieved with
        // first 'i' items and knapsack capacity 'w'.
        int[][] dp = new int[n + 1][W + 1];

        // Initialize dp table with 0s (already done by default for int arrays in Java)
        // Base cases: dp[0][w] = 0 (no items) and dp[i][0] = 0 (0 capacity) are handled by initialization.

        // Fill the dp table
        for (int i = 1; i <= n; i++) {
            // current item's weight and value (using 0-indexed arrays, so weights[i-1] and values[i-1])
            int currentWeight = weights[i - 1];
            int currentValue = values[i - 1];

            for (int w = 1; w <= W; w++) {
                // Option 1: Don't include the current item
                int notTake = dp[i - 1][w];

                // Option 2: Include the current item (if it fits)
                int take = 0;
                if (currentWeight <= w) {
                    take = currentValue + dp[i - 1][w - currentWeight];
                }

                // Choose the maximum of the two options
                dp[i][w] = Math.max(notTake, take);
            }
        }

        // The result is in the bottom-right corner of the dp table
        return dp[n][W];
    }

    public static void main(String[] args) {
        Knapsack ks = new Knapsack();
        int[] weights = {1, 3, 4, 5};
        int[] values = {10, 40, 50, 70};
        int W = 7; // Knapsack capacity

        System.out.println("--- 0/1 Knapsack Problem ---");
        int maxValBottomUp = ks.solveKnapsackBottomUp(weights, values, W);
        System.out.println("Maximum value (Bottom-Up): " + maxValBottomUp); // Expected: 10 + 70 = 80 (items with w=1 and w=5 for W=7) or 40 + 50 = 90 (items with w=3 and w=4 for W=7) -> Output should be 90
    }
}
```

-----

### Approach 2: Top-Down (Memoization) for 0/1 Knapsack

```java
import java.util.Arrays;

public class KnapsackMemoization {

    private int[][] memo;
    private int[] weights;
    private int[] values;
    private int n;

    /**
     * Solves the 0/1 Knapsack problem using Top-Down (Memoization) Dynamic Programming.
     *
     * @param weights Array of weights of items.
     * @param values  Array of values of items.
     * @param W       Maximum capacity of the knapsack.
     * @return The maximum value that can be put in the knapsack.
     */
    public int solveKnapsackTopDown(int[] weights, int[] values, int W) {
        this.weights = weights;
        this.values = values;
        this.n = weights.length;

        // Initialize memoization table with -1 to indicate uncomputed states
        // memo[idx][current_capacity] stores max value using items from 0 to idx, with current_capacity
        memo = new int[n][W + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Start the recursive process from the last item (n-1) and full capacity (W)
        return dp(n - 1, W);
    }

    /**
     * Recursive helper function for Top-Down Knapsack.
     *
     * @param idx              Current item index being considered (from n-1 down to 0).
     * @param current_capacity Remaining capacity of the knapsack.
     * @return Max value using items from 0 to idx with current_capacity.
     */
    private int dp(int idx, int current_capacity) {
        // Base cases
        if (idx < 0 || current_capacity == 0) {
            return 0; // No items left or no capacity left, so no value can be added
        }

        // If this state has already been computed, return the memoized value
        if (memo[idx][current_capacity] != -1) {
            return memo[idx][current_capacity];
        }

        // Option 1: Exclude the current item (item at idx)
        int valueIfExcluded = dp(idx - 1, current_capacity);

        // Option 2: Include the current item (item at idx), if it fits
        int valueIfIncluded = 0;
        if (weights[idx] <= current_capacity) {
            valueIfIncluded = values[idx] + dp(idx - 1, current_capacity - weights[idx]);
        }

        // Store the maximum of the two options in memo table and return it
        return memo[idx][current_capacity] = Math.max(valueIfExcluded, valueIfIncluded);
    }

    public static void main(String[] args) {
        KnapsackMemoization ks = new KnapsackMemoization();
        int[] weights = {1, 3, 4, 5};
        int[] values = {10, 40, 50, 70};
        int W = 7; // Knapsack capacity

        System.out.println("\n--- 0/1 Knapsack Problem (Memoization) ---");
        int maxValTopDown = ks.solveKnapsackTopDown(weights, values, W);
        System.out.println("Maximum value (Top-Down): " + maxValTopDown); // Expected: 90
    }
}
```

-----


<img width="512" alt="image" src="https://github.com/user-attachments/assets/e84ca45f-6933-4f45-bc5e-d1e97acaed77" />


##  Partition Equal Subset Sum

**Problem Statement:** Given a non-empty array `nums` containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

### Approach 1: Bottom-Up (Tabulation) for Partition Equal Subset Sum

This is a direct application of the Subset Sum Problem.

```java
import java.util.Arrays;

public class PartitionEqualSubsetSum {

    /**
     * Checks if the array can be partitioned into two subsets with equal sum
     * using Bottom-Up (Tabulation) Dynamic Programming.
     *
     * @param nums The input array of non-negative integers.
     * @return True if partition is possible, false otherwise.
     */
    public boolean canPartitionBottomUp(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // If total sum is odd, it's impossible to partition into two equal sums
        if (totalSum % 2 != 0) {
            return false;
        }

        int targetSum = totalSum / 2;
        int n = nums.length;

        // dp[i][j] will be true if sum 'j' can be formed using the first 'i' numbers
        boolean[][] dp = new boolean[n + 1][targetSum + 1];

        // Base case: It's always possible to achieve sum 0 with any number of items
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        // dp[0][j] for j > 0 will be false (default for boolean array)

        // Fill the dp table
        for (int i = 1; i <= n; i++) {
            int currentNum = nums[i - 1]; // Current number being considered (0-indexed array)
            for (int j = 1; j <= targetSum; j++) {
                // Option 1: Exclude the current number
                dp[i][j] = dp[i - 1][j];

                // Option 2: Include the current number (if it fits and the remaining sum was achievable)
                if (j >= currentNum) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - currentNum];
                }
            }
        }

        return dp[n][targetSum];
    }

    // Space Optimized Version (Recommended)
    public boolean canPartitionBottomUpSpaceOptimized(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        if (totalSum % 2 != 0) {
            return false;
        }

        int targetSum = totalSum / 2;

        // dp[j] will be true if sum 'j' can be formed
        // We only need previous row's information, so a 1D array is sufficient.
        boolean[] dp = new boolean[targetSum + 1];
        dp[0] = true; // Sum 0 is always achievable

        for (int num : nums) { // Iterate through each number
            // Iterate backwards to ensure that each number is used at most once
            for (int j = targetSum; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[targetSum];
    }

    public static void main(String[] args) {
        PartitionEqualSubsetSum solver = new PartitionEqualSubsetSum();

        System.out.println("\n--- Partition Equal Subset Sum Problem ---");

        int[] nums1 = {1, 5, 11, 5};
        System.out.println("Can partition " + Arrays.toString(nums1) + "? (Bottom-Up): " + solver.canPartitionBottomUp(nums1)); // Expected: true
        System.out.println("Can partition " + Arrays.toString(nums1) + "? (Space Optimized): " + solver.canPartitionBottomUpSpaceOptimized(nums1)); // Expected: true

        int[] nums2 = {1, 2, 3, 5};
        System.out.println("Can partition " + Arrays.toString(nums2) + "? (Bottom-Up): " + solver.canPartitionBottomUp(nums2)); // Expected: false
        System.out.println("Can partition " + Arrays.toString(nums2) + "? (Space Optimized): " + solver.canPartitionBottomUpSpaceOptimized(nums2)); // Expected: false

        int[] nums3 = {2, 2, 3, 5}; // total=12, target=6. Can be {2, 2, 2} and {3, 5}
        System.out.println("Can partition " + Arrays.toString(nums3) + "? (Bottom-Up): " + solver.canPartitionBottomUp(nums3)); // Expected: true
        System.out.println("Can partition " + Arrays.toString(nums3) + "? (Space Optimized): " + solver.canPartitionBottomUpSpaceOptimized(nums3)); // Expected: true
    }
}
```

-----

### Approach 2: Top-Down (Memoization) for Partition Equal Subset Sum

```java
import java.util.Arrays;

public class PartitionEqualSubsetSumMemoization {

    // Using Integer for memoization to distinguish between uncomputed (null) and false (0)
    private Boolean[][] memo;
    private int[] nums;

    /**
     * Checks if the array can be partitioned into two subsets with equal sum
     * using Top-Down (Memoization) Dynamic Programming.
     *
     * @param nums The input array of non-negative integers.
     * @return True if partition is possible, false otherwise.
     */
    public boolean canPartitionTopDown(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        if (totalSum % 2 != 0) {
            return false;
        }

        int targetSum = totalSum / 2;
        this.nums = nums;

        // memo[idx][current_sum] stores whether current_sum can be achieved
        // using elements from index 'idx' onwards.
        // Or, more commonly, using elements from 0 to 'idx'. Let's stick to the 0 to idx approach.
        memo = new Boolean[nums.length][targetSum + 1];

        // Start the recursive process: Can we form 'targetSum' using elements up to nums.length-1?
        return dp(nums.length - 1, targetSum);
    }

    /**
     * Recursive helper function for Partition Equal Subset Sum.
     *
     * @param idx           Current item index being considered (from nums.length-1 down to 0).
     * @param current_sum   The sum we are trying to achieve.
     * @return True if current_sum can be formed using items from 0 to idx, false otherwise.
     */
    private boolean dp(int idx, int current_sum) {
        // Base cases
        if (current_sum == 0) {
            return true; // We found a subset that sums to the target!
        }
        if (idx < 0 || current_sum < 0) {
            return false; // No items left or sum went negative (overran target)
        }

        // If this state has already been computed, return the memoized value
        if (memo[idx][current_sum] != null) {
            return memo[idx][current_sum];
        }

        // Option 1: Exclude the current number (nums[idx])
        boolean exclude = dp(idx - 1, current_sum);

        // Option 2: Include the current number (nums[idx]), if it helps achieve the sum
        boolean include = false;
        if (current_sum >= nums[idx]) {
            include = dp(idx - 1, current_sum - nums[idx]);
        }

        // Store the result (OR of two options) in memo table and return it
        return memo[idx][current_sum] = exclude || include;
    }

    public static void main(String[] args) {
        PartitionEqualSubsetSumMemoization solver = new PartitionEqualSubsetSumMemoization();

        System.out.println("\n--- Partition Equal Subset Sum Problem (Memoization) ---");

        int[] nums1 = {1, 5, 11, 5};
        System.out.println("Can partition " + Arrays.toString(nums1) + "? (Top-Down): " + solver.canPartitionTopDown(nums1)); // Expected: true

        int[] nums2 = {1, 2, 3, 5};
        System.out.println("Can partition " + Arrays.toString(nums2) + "? (Top-Down): " + solver.canPartitionTopDown(nums2)); // Expected: false

        int[] nums3 = {2, 2, 3, 5};
        System.out.println("Can partition " + Arrays.toString(nums3) + "? (Top-Down): " + solver.canPartitionTopDown(nums3)); // Expected: true
    }
}
```
-----
<img width="512" alt="image" src="https://github.com/user-attachments/assets/6aa53048-8409-4a26-b6c4-1edec60517b2" />
The "Target Sum" problem (LeetCode 494) asks you to find the number of ways to assign `+` or `-` signs to each number in an array so that their sum equals a given `target`. This is another classic problem that can be effectively solved using dynamic programming.

-----

## Understanding the Problem

Let's say you have an array `nums = [a, b, c]` and a `target = T`. You need to find how many ways you can assign `+` or `-` to `a, b, c` such that the result is `T`.

For example, with `nums = [1, 1, 1, 1, 1]` and `target = 3`:

  * `+1 +1 +1 +1 -1 = 3`
  * `+1 +1 +1 -1 +1 = 3`
  * ...and so on.

-----

## Transforming the Problem

This problem can be transformed into a **Subset Sum** problem, making it easier to solve with DP.

Let `P` be the set of numbers with a `+` sign and `N` be the set of numbers with a `-` sign.
The sum of all numbers in the array is `sum(nums)`.
The problem statement can be written as:
`sum(P) - sum(N) = target`

We also know that `sum(P) + sum(N) = sum(nums)`.

If we add these two equations:
`(sum(P) - sum(N)) + (sum(P) + sum(N)) = target + sum(nums)`
`2 * sum(P) = target + sum(nums)`
`sum(P) = (target + sum(nums)) / 2`

This transformation is crucial\! Now the problem becomes: **Find the number of subsets `P` (from the original `nums` array) such that the sum of elements in `P` is equal to `(target + sum(nums)) / 2`.**

**Important Considerations for the Transformation:**

1.  **Total Sum Check:** Calculate `totalSum = sum(nums)`.
2.  **Impossibility Check 1:** If `(target + totalSum)` is odd, then `(target + totalSum) / 2` will not be an integer. It's impossible to form such a sum with integers, so return `0`.
3.  **Impossibility Check 2:** If `target > totalSum` (or `target < -totalSum`), it's impossible to reach the target sum. Return `0`. (The combined `(target + totalSum)` check often covers this too, as `(target + totalSum)` would be negative if `target < -totalSum`, and we need `sum(P)` to be non-negative).
4.  **New Target:** Let `s = (target + totalSum) / 2`. Now, the problem is to find the number of subsets that sum to `s`.

-----

## Dynamic Programming Approach (Subset Sum - Count Ways)

Now that the problem is converted to "count the number of subsets that sum to `s`," we can use dynamic programming.

**DP State:**

Let `dp[j]` be the number of ways to achieve a sum of `j` using the numbers processed so far.

**Recurrence Relation (1D DP Array):**

We'll iterate through each number `num` in the input array `nums`. For each `num`, we'll iterate through possible sums `j` from `s` down to `num`.

`dp[j] = dp[j] + dp[j - num]`

  * `dp[j]` (before update) represents the ways to achieve sum `j` *without* using the current `num`.
  * `dp[j - num]` represents the ways to achieve the remaining sum `j - num`. If we add `num` to these ways, we get new ways to achieve sum `j`.

**Base Case:**

`dp[0] = 1`. There's one way to achieve a sum of 0 (by choosing no numbers).

-----

### Implementation: Bottom-Up (Tabulation with 1D Array)

This is the most common and space-efficient way to solve this.

```java
import java.util.Arrays;

class Solution {
    /**
     * Finds the number of ways to assign '+' or '-' signs to each number in an array
     * so that their sum equals a given target.
     *
     * @param nums   The input array of non-negative integers.
     * @param target The target sum.
     * @return The number of ways.
     */
    public int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // --- Problem Transformation Checks ---

        // 1. If (target + totalSum) is odd, it's impossible to form integer subsets.
        //    (target + totalSum) must be even for sum(P) to be an integer.
        if ((target + totalSum) % 2 != 0) {
            return 0;
        }

        // 2. If target is greater than totalSum, it's impossible to reach.
        //    Also covers the case where (target + totalSum) is negative,
        //    as sum(P) cannot be negative.
        if (Math.abs(target) > totalSum) {
             return 0; // Or (target + totalSum) < 0 implies this too
        }

        // Calculate the new target sum for subset P
        // sum(P) = (target + totalSum) / 2
        int s = (target + totalSum) / 2;

        // --- Dynamic Programming (Count Subsets with Sum 's') ---

        // dp[j] will store the number of ways to achieve sum 'j'
        int[] dp = new int[s + 1];

        // Base case: There's one way to achieve sum 0 (by taking no elements)
        dp[0] = 1;

        // Iterate through each number in nums
        for (int num : nums) {
            // Iterate downwards to ensure each number is used at most once
            for (int j = s; j >= num; j--) {
                // If we can form sum (j - num), then adding 'num' to those ways
                // creates new ways to form sum 'j'.
                dp[j] = dp[j] + dp[j - num];
            }
        }

        // The answer is the number of ways to achieve the transformed target sum 's'
        return dp[s];
    }
}
```

-----

### Example Walkthrough (for `findTargetSumWays`)

Let `nums = [1, 1, 1, 1, 1]`, `target = 3`

1.  `totalSum = 1 + 1 + 1 + 1 + 1 = 5`
2.  `(target + totalSum) = 3 + 5 = 8` (even, so proceed)
3.  `s = (3 + 5) / 2 = 4` (We need to find subsets that sum to 4)

Initialize `dp` array of size `(s + 1) = 5`:
`dp = [1, 0, 0, 0, 0]` (dp[0]=1, others 0)

**Processing `num = 1` (first 1):**
Iterate `j` from `4` down to `1`.

  * `j = 4`: `dp[4] = dp[4] + dp[4 - 1] = 0 + dp[3] = 0 + 0 = 0`
  * `j = 3`: `dp[3] = dp[3] + dp[3 - 1] = 0 + dp[2] = 0 + 0 = 0`
  * `j = 2`: `dp[2] = dp[2] + dp[2 - 1] = 0 + dp[1] = 0 + 0 = 0`
  * `j = 1`: `dp[1] = dp[1] + dp[1 - 1] = 0 + dp[0] = 0 + 1 = 1`
    `dp` becomes `[1, 1, 0, 0, 0]` (After first 1: One way to make 0 (empty), one way to make 1 (take 1))

**Processing `num = 1` (second 1):**
Iterate `j` from `4` down to `1`.

  * `j = 4`: `dp[4] = dp[4] + dp[4 - 1] = 0 + dp[3] = 0 + 0 = 0`
  * `j = 3`: `dp[3] = dp[3] + dp[3 - 1] = 0 + dp[2] = 0 + 0 = 0`
  * `j = 2`: `dp[2] = dp[2] + dp[2 - 1] = 0 + dp[1] = 0 + 1 = 1` (One way to make 2: [1,1])
  * `j = 1`: `dp[1] = dp[1] + dp[1 - 1] = 1 + dp[0] = 1 + 1 = 2` (Two ways to make 1: take first 1, or take second 1)
    `dp` becomes `[1, 2, 1, 0, 0]`

**Processing `num = 1` (third 1):**

  * `j = 4`: `dp[4] = dp[4] + dp[3] = 0 + 0 = 0`
  * `j = 3`: `dp[3] = dp[3] + dp[2] = 0 + 1 = 1`
  * `j = 2`: `dp[2] = dp[2] + dp[1] = 1 + 2 = 3`
  * `j = 1`: `dp[1] = dp[1] + dp[0] = 2 + 1 = 3`
    `dp` becomes `[1, 3, 3, 1, 0]`

**Processing `num = 1` (fourth 1):**

  * `j = 4`: `dp[4] = dp[4] + dp[3] = 0 + 1 = 1`
  * `j = 3`: `dp[3] = dp[3] + dp[2] = 1 + 3 = 4`
  * `j = 2`: `dp[2] = dp[2] + dp[1] = 3 + 3 = 6`
  * `j = 1`: `dp[1] = dp[1] + dp[0] = 3 + 1 = 4`
    `dp` becomes `[1, 4, 6, 4, 1]`

**Processing `num = 1` (fifth 1):**

  * `j = 4`: `dp[4] = dp[4] + dp[3] = 1 + 4 = 5`
  * `j = 3`: `dp[3] = dp[3] + dp[2] = 4 + 6 = 10`
  * `j = 2`: `dp[2] = dp[2] + dp[1] = 6 + 4 = 10`
  * `j = 1`: `dp[1] = dp[1] + dp[0] = 4 + 1 = 5`
    `dp` becomes `[1, 5, 10, 10, 5]`

Final answer is `dp[s] = dp[4] = 5`. This is correct.

-----

### Top-Down (Memoization) Approach (Alternative)

While the Bottom-Up approach is often preferred for its iterative nature and typically better performance, you can also solve this using recursion with memoization. This is essentially a depth-first search (DFS) with caching.

**DP State:**

`memo[index][current_sum_offset]` will store the number of ways.
Since `current_sum` can be negative, we need to offset it to map to array indices. The sum can range from `-totalSum` to `+totalSum`. So, the range is `2 * totalSum + 1`. We can use `current_sum + totalSum` as the index.

```java
import java.util.Arrays;

class SolutionTopDown {
    private int[][] memo;
    private int totalSum; // Store total sum for offset calculation

    /**
     * Finds the number of ways to assign '+' or '-' signs using Top-Down (Memoization).
     *
     * @param nums   The input array of non-negative integers.
     * @param target The target sum.
     * @return The number of ways.
     */
    public int findTargetSumWays(int[] nums, int target) {
        totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // If target is outside the possible range [-totalSum, totalSum], return 0.
        // This also implicitly handles cases where totalSum is 0 and target is not 0.
        if (target > totalSum || target < -totalSum) {
            return 0;
        }

        // Memoization table: memo[index][current_sum + totalSum]
        // The current_sum can range from -totalSum to +totalSum.
        // So, the size needed for the sum dimension is (totalSum - (-totalSum) + 1) = 2 * totalSum + 1.
        // We add totalSum as an offset to shift negative sums to non-negative indices.
        memo = new int[nums.length][2 * totalSum + 1];

        // Initialize memo with a value indicating uncomputed (e.g., Integer.MIN_VALUE or a special value)
        // Since ways can be 0, we can't use 0 as uncomputed.
        for (int[] row : memo) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }

        return dp(nums, 0, 0, target);
    }

    /**
     * Recursive helper function for Top-Down Target Sum.
     *
     * @param nums        The input array.
     * @param index       Current index of the number being considered.
     * @param currentSum  The sum achieved so far.
     * @param target      The target sum.
     * @return The number of ways.
     */
    private int dp(int[] nums, int index, int currentSum, int target) {
        // Base case: All numbers have been processed
        if (index == nums.length) {
            return currentSum == target ? 1 : 0; // If currentSum equals target, found one way
        }

        // Check memoization table
        // Use currentSum + totalSum as the index to handle negative sums
        if (memo[index][currentSum + totalSum] != Integer.MIN_VALUE) {
            return memo[index][currentSum + totalSum];
        }

        // Option 1: Add the current number
        int ways1 = dp(nums, index + 1, currentSum + nums[index], target);

        // Option 2: Subtract the current number
        int ways2 = dp(nums, index + 1, currentSum - nums[index], target);

        // Store and return the total ways for this state
        return memo[index][currentSum + totalSum] = ways1 + ways2;
    }
}
```

Both approaches solve the problem. The **Bottom-Up (1D DP) approach** is generally more efficient for this problem due to its direct iteration and avoidance of recursion overhead, and it's also more space-optimized.
-----
<img width="1379" alt="image" src="https://github.com/user-attachments/assets/5dedf8a2-d87d-436b-8f48-9c1f0ab8db2a" />

Achcha, sorry yaar, meri galti\! Main pichhli baar phir se Top-Down (Memoization) approach ka code dena bhool gaya Last Stone Weight II ke liye, aur saath hi Partition Equal Subset Sum ke liye bhi Top-Down approach ka code missing tha.

Iss baar, main poora dhyaan rakhunga ki **Last Stone Weight II** aur **Partition Equal Subset Sum** dono problems ke liye **Bottom-Up (Tabulation)** aur **Top-Down (Memoization)**, dono approaches ka code Java mein mile, aur explanation bhi poori **Hinglish** mein ho.

Chalo shuru karte hain\!

-----

## 1\. 1049. Last Stone Weight II

**Problem Statement (Hinglish):**
Tumhare paas `stones` naam ka ek array hai jismein har stone ka positive `weight` diya hua hai. Har turn pe, tum **do stones** choose karte ho aur unko smash karte ho:

  * Agar unke weights `x` aur `y` hain (jahaan `x <= y`):
      * Agar `x == y` hai, toh dono stones poori tarah se destroy ho jaate hain (kuch nahi bachta).
      * Agar `x != y` hai, toh `x` wala stone destroy ho jaata hai, aur `y` wala stone `y - x` weight ka ho jaata hai (ek naya stone ban jaata hai).

Ye process tab tak chalti hai jab tak **ek bhi stone na bache** ya **sirf ek stone bache**. Tumhe batana hai ki **last mein jo sabse chhota possible weight bachega**, woh kya hoga.

**Problem Transformation (Hinglish):**
Ye problem seedhe-seedhe DP ki tarah nahi lagti, par hum isko ek alag tarike se soch sakte hain, jisse ye ek jaani-pehchaani DP problem ban jaati hai: **Subset Sum Problem ka variation**.

Socho, agar humne saare stones ko do groups mein divide kar diya:

  * Ek group `S1` jismein saare stones ka sum `sum1` hai.
  * Dusra group `S2` jismein saare stones ka sum `sum2` hai.

Jab hum stones ko smash karte hain, toh basically hum unke weights ko subtract kar rahe hote hain. Jaise `y - x`. Agar hamare paas saare stones ka **total sum** `totalSum` hai, aur humne `S1` banaya, toh `S2` ka sum `totalSum - sum1` hoga.

End mein jo stone bachega, uska weight `|sum1 - sum2|` hoga.
Yaani `|sum1 - (totalSum - sum1)|`
Jo ki `|2 * sum1 - totalSum|` hai.

Hame is `|2 * sum1 - totalSum|` ko **minimum** karna hai.
Iske liye, `2 * sum1` ko `totalSum` ke **jitna ho sake utna kareeb** lana hai.
Ya phir, `sum1` ko `totalSum / 2` ke **jitna ho sake utna kareeb** lana hai.

Toh problem ye ban jaati hai: **Kya hum `stones` array mein se ek subset `S1` bana sakte hain, jiska sum `sum1` ho, aur woh `sum1` `totalSum / 2` ke sabse kareeb ho (par `totalSum / 2` se zyada na ho)?**

Toh, humko saare stones ka `totalSum` calculate karna hai. Fir `target = totalSum / 2` set karna hai. Ab hamari problem hai ki hum **largest possible sum `s_prime`** find karein jo `target` se kam ya barabar ho, aur jise `stones` array ke kisi subset se banaya ja sake.

Agar humein `s_prime` mil gaya (jo `target` se kam ya barabar hai), toh dusre subset ka sum `totalSum - s_prime` hoga.
Aur final bacha hua weight `(totalSum - s_prime) - s_prime` hoga, yaani `totalSum - 2 * s_prime`.

-----

### Approach 1: Bottom-Up (Tabulation) for Last Stone Weight II

Ye is problem ko solve karne ka sabse common aur space-efficient tareeka hai.

```java
import java.util.Arrays;

class SolutionLastStoneWeightII_BottomUp {
    /**
     * Last Stone Weight II problem ko Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Isko Subset Sum problem mein transform kiya jaata hai.
     *
     * @param stones Stones ke weights ka array.
     * @return Akhiri stone ka sabse chhota possible weight.
     */
    public int lastStoneWeightII(int[] stones) {
        int totalSum = 0;
        for (int stoneWeight : stones) {
            totalSum += stoneWeight;
        }

        // Target capacity jahan tak hum ek subset ka sum bana sakte hain.
        // Hamara goal hai ki ek subset ka sum 'targetCapacity' ke jitna kareeb ho sake.
        int targetCapacity = totalSum / 2;

        // dp[j] true hoga agar sum 'j' ko 'stones' array ke elements se banaya ja sakta hai.
        // By default false hote hain.
        boolean[] dp = new boolean[targetCapacity + 1];

        // Base case: Sum 0 ko humesha empty subset se achieve kiya ja sakta hai.
        dp[0] = true;

        // Har stoneWeight ko iterate karo
        for (int stoneWeight : stones) {
            // Hum 'j' ko 'targetCapacity' se 'stoneWeight' tak reverse order mein iterate karte hain.
            // Ye ensure karta hai ki har stone ko sirf ek hi baar use kiya jaye (0/1 choice).
            for (int j = targetCapacity; j >= stoneWeight; j--) {
                // Agar 'j - stoneWeight' sum pehle banaya ja sakta tha (bina current stone ko liye),
                // toh current stone ko lekar 'j' sum bhi banaya ja sakta hai.
                dp[j] = dp[j] || dp[j - stoneWeight];
            }
        }

        // Ab hume sabse bada possible sum 's_prime' dhundhna hai jo 'targetCapacity' se kam ya barabar ho
        // aur jise banaya ja sake (yani, dp[s_prime] == true).
        int s_prime = 0;
        for (int j = targetCapacity; j >= 0; j--) {
            if (dp[j]) {
                s_prime = j;
                break; // Pehla true milte hi ruk jaao, kyuki j reverse order mein hai.
                       // Ye hi largest sum hoga jo targetCapacity tak ban sakta hai.
            }
        }

        // Ek subset ka sum 's_prime' hai.
        // Dusre subset ka sum = 'totalSum - s_prime'.
        // Akhiri bacha hua weight = |(totalSum - s_prime) - s_prime| = totalSum - 2 * s_prime.
        return totalSum - 2 * s_prime;
    }

    public static void main(String[] args) {
        SolutionLastStoneWeightII_BottomUp solver = new SolutionLastStoneWeightII_BottomUp();

        System.out.println("--- 1049. Last Stone Weight II (Bottom-Up) ---");

        int[] stones1 = {2, 7, 4, 1, 8, 1}; // totalSum = 23, targetCapacity = 11
        // Max sum <= 11 jo ban sakta hai: 11 (e.g., 2+1+8)
        // Result = 23 - 2*11 = 1
        System.out.println("Stones: " + Arrays.toString(stones1) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones1)); // Expected: 1

        int[] stones2 = {31, 26, 33, 21, 40}; // totalSum = 151, targetCapacity = 75
        // Max sum <= 75 jo ban sakta hai: 75
        // Result = 151 - 2*75 = 1
        System.out.println("Stones: " + Arrays.toString(stones2) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones2)); // Expected: 1

        int[] stones3 = {1, 1, 1}; // totalSum = 3, targetCapacity = 1
        // Max sum <= 1 jo ban sakta hai: 1
        // Result = 3 - 2*1 = 1
        System.out.println("Stones: " + Arrays.toString(stones3) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones3)); // Expected: 1
    }
}
```

-----

### Approach 2: Top-Down (Memoization) for Last Stone Weight II

Ye approach recursive hoti hai, jahan hum memoization (caching) ka use karte hain taaki duplicate calculations na ho.

```java
import java.util.Arrays;

class SolutionLastStoneWeightII_TopDown {
    // memo[idx][current_sum] store karega ki 'current_sum' 'idx' se shuru hone wale items se ban sakta hai ya nahi.
    // Boolean array ya int array with sentinel value use kar sakte hain.
    // Yahaan hum Integer use karenge taaki null = uncomputed state.
    private Boolean[][] memo;
    private int[] stones;
    private int n;

    /**
     * Last Stone Weight II problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     * Isko Subset Sum problem mein transform kiya jaata hai.
     *
     * @param stones Stones ke weights ka array.
     * @return Akhiri stone ka sabse chhota possible weight.
     */
    public int lastStoneWeightII(int[] stones) {
        int totalSum = 0;
        for (int stoneWeight : stones) {
            totalSum += stoneWeight;
        }

        int targetCapacity = totalSum / 2;
        this.stones = stones;
        this.n = stones.length;

        // Memoization table initialize karo.
        // memo[index_of_stone][sum_achieved_so_far_with_items_up_to_index]
        memo = new Boolean[n][targetCapacity + 1];

        // DP function ko call karke check karo ki 'targetCapacity' tak kaun-kaun se sums possible hain.
        // Hum saare stones ko consider karte hue, 'targetCapacity' sum banane ki koshish karte hain.
        // DP function se hame pata chalega ki 'targetCapacity' sum ban sakta hai ya nahi.
        // Lekin hame largest sum chahiye jo ban sake. Iske liye hum ek loop lagayenge
        // memo table ke last row ya column par.

        // Ye DP function har possible sum ke liye state fill karega
        // dp(idx, current_sum) true return karega agar current_sum 'idx' tak ke stones se ban sakta hai.
        // Hame sare sums check karne honge. To simulate bottom-up using top-down.
        // Let's modify the dp function to just return boolean indicating if current_sum is possible.
        // The main method will loop to find the max possible sum.

        // Call dp for all combinations to fill up memo table indirectly
        // (Ya phir hum seedha loop laga kar final largest achievable sum dhoondh sakte hain)
        // Let's call the recursive function for the largest target sum possible, and it will fill subproblems.
        dp(n - 1, targetCapacity); // Yeh memo table ko fill karega

        int s_prime = 0;
        for (int j = targetCapacity; j >= 0; j--) {
            // Check if dp[n-1][j] is true (means sum 'j' can be formed using all stones up to n-1)
            // Ya phir, directly call dp(n-1, j) to see if it's true.
            // But if we call dp(n-1, targetCapacity) once, many states get computed.
            // So, checking the memo table is efficient.
            if (dp(n - 1, j)) { // dp(n-1, j) means considering all items (from 0 to n-1) for sum j
                s_prime = j;
                break;
            }
        }

        return totalSum - 2 * s_prime;
    }

    /**
     * Recursive helper function: checks if `current_sum` can be formed using items from `0` to `idx`.
     *
     * @param idx          Current stone index being considered.
     * @param current_sum  The sum we are trying to achieve using stones up to `idx`.
     * @return True if `current_sum` can be formed, false otherwise.
     */
    private boolean dp(int idx, int current_sum) {
        // Base cases
        if (current_sum == 0) {
            return true; // Sum 0 can always be achieved
        }
        if (idx < 0 || current_sum < 0) {
            return false; // No more stones or sum became negative
        }

        // Memoization check
        if (memo[idx][current_sum] != null) {
            return memo[idx][current_sum];
        }

        // Option 1: Exclude the current stone (stones[idx])
        boolean exclude = dp(idx - 1, current_sum);

        // Option 2: Include the current stone (if it fits)
        boolean include = false;
        if (current_sum >= stones[idx]) {
            include = dp(idx - 1, current_sum - stones[idx]);
        }

        // Store and return the result
        return memo[idx][current_sum] = exclude || include;
    }

    public static void main(String[] args) {
        SolutionLastStoneWeightII_TopDown solver = new SolutionLastStoneWeightII_TopDown();

        System.out.println("\n--- 1049. Last Stone Weight II (Top-Down) ---");

        int[] stones1 = {2, 7, 4, 1, 8, 1};
        System.out.println("Stones: " + Arrays.toString(stones1) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones1)); // Expected: 1

        int[] stones2 = {31, 26, 33, 21, 40};
        System.out.println("Stones: " + Arrays.toString(stones2) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones2)); // Expected: 1

        int[] stones3 = {1, 1, 1};
        System.out.println("Stones: " + Arrays.toString(stones3) + " -> Last Stone Weight: " + solver.lastStoneWeightII(stones3)); // Expected: 1
    }
}
```

-----
<img width="1379" alt="image" src="https://github.com/user-attachments/assets/46cb6a4a-e9cc-42b4-a001-45e4c5c552e6" />
Chalo, `474. Ones and Zeroes` problem ko detail mein, Hinglish mein, aur dono DP approaches ke saath samajhte hain. Top-Down ke liye diagram bhi hoga aur Java code bhi.

-----

## 474\. Ones and Zeroes Problem Kya Hai?

**Problem Statement (Hinglish):**
Imagine karo aapke paas kuch **binary strings** ka ek array hai (`strs`). Har binary string mein sirf `'0'` aur `'1'` hote hain, jaise "10", "0001", "111001", "1", "0".

Aapko do integers `m` aur `n` bhi diye gaye hain. `m` ka matlab hai aapke paas **maximum kitne '0's** ho sakte hain, aur `n` ka matlab hai aapke paas **maximum kitne '1's** ho sakte hain.

Aapko `strs` array mein se strings ka ek **largest subset** (yani, sabse bada collection) dhundhna hai, jismein selected strings ke total '0's `m` se zyada na ho, aur total '1's `n` se zyada na ho. Aur finally, aapko us largest subset ka **size** return karna hai (yani, us subset mein kitni strings hain).

**Example:**
`strs = ["10", "0001", "111001", "1", "0"]`, `m = 3`, `n = 4`

Agar hum `["10", "0001", "1", "0"]` ko select karte hain:

  * "10": ek '0', ek '1'
  * "0001": teen '0', ek '1'
  * "1": zero '0', ek '1'
  * "0": ek '0', zero '1'

Total '0's: 1 + 3 + 0 + 1 = 5
Total '1's: 1 + 1 + 1 + 0 = 3

Yahaan total '0's (5) `m` (3) se zyada ho gaye. Toh ye subset valid nahi hai.

Hame ek aisa subset dhundhna hai jiska size sabse zyada ho, aur jo `m` '0's aur `n` '1's ki limits mein fit ho jaaye.

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem bilkul **0/1 Knapsack Problem** jaisi hai, par thoda twist ke saath.
Standard 0/1 Knapsack mein hamare paas ek single capacity (weight limit) hoti hai aur items ke paas ek single weight hota hai aur ek value. Hame max value nikalni hoti hai.

Yahaan, hamare paas **do capacities** hain (`m` for '0's aur `n` for '1's). Har "item" (yani, har string) ke paas bhi **do "weights"** hain (us string mein kitne '0's hain, aur kitne '1's hain). Aur har "item" ki "value" hai `1`, kyunki hame strings ka **largest subset size** chahiye. Matlab, har selected string ki value 1 hai, aur humein total value max karni hai.

Toh, ye ek **2D Knapsack Problem** hai.

-----

## Dynamic Programming Approach

Jaise 0/1 Knapsack mein hum ek DP table banate hain, waise hi yahaan bhi banayenge.

**DP State:**

Hum ek 3D DP table use kar sakte hain:
`dp[i][j][k]` ka matlab hoga: Pehli `i` strings ko use karke, **maximum kitni strings** select ki ja sakti hain, jab hamare paas `j` '0's ki capacity ho aur `k` '1's ki capacity ho.

Lekin, hum 2D DP table se bhi kaam chala sakte hain agar hum Bottom-Up approach mein strings ko ek-ek karke process karein.

`dp[j][k]` ka matlab hoga: `j` '0's aur `k` '1's ki capacity ke saath, **maximum kitni strings** select ki ja sakti hain.

**Recurrence Relation:**

Jab hum kisi `current_string` ko process kar rahe honge, jismein `zeros_count` '0's aur `ones_count` '1's hain, toh hamare paas do options honge:

1.  **`current_string` ko include mat karo (Exclude):** Is case mein `dp[j][k]` ki value wahi rahegi jo `current_string` ko process karne se pehle thi.

      * `dp[j][k]` (old value)

2.  **`current_string` ko include karo (Include):** Agar `current_string` ko include karna possible hai (yani, `j >= zeros_count` aur `k >= ones_count`), toh hum `current_string` ki value (jo ki `1` hai) plus bachi hui capacities (`j - zeros_count` aur `k - ones_count`) ke liye jo maximum strings ban sakti thi, unko add karenge.

      * `1 + dp[j - zeros_count][k - ones_count]` (ye value `current_string` ko process karne se pehle ki `dp` table se aayegi)

Finally, `dp[j][k]` in dono options mein se **maximum** hoga.

`dp[j][k] = max(dp[j][k] (exclude), 1 + dp[j - zeros_count][k - ones_count] (include))`

-----

### Approach 1: Bottom-Up (Tabulation)

Is approach mein hum DP table ko **chhote subproblems** se start karke **bade subproblems** ki taraf fill karte hain. Hum ek 2D DP array `dp[m+1][n+1]` banayenge. `dp[j][k]` ka matlab hoga `j` '0's aur `k` '1's ki capacity ke saath kitni strings select ho sakti hain.

**Algorithm (Tareeka):**

1.  Ek `(m+1) x (n+1)` size ka `dp` table banao aur sab values ko **0** se initialize karo. (`m` max '0's ki capacity, `n` max '1's ki capacity).
2.  `dp[0][0]` ko `0` se initialize karo (0 '0's aur 0 '1's ke liye 0 strings select hoti hain).
3.  Har string `s` ke liye jo `strs` array mein hai:
      * Us string `s` mein `zeros_count` (kitne '0's hain) aur `ones_count` (kitne '1's hain) count karo.
      * Ab `j` loop ko `m` se `zeros_count` tak **downwards** chalao. (Reverse order mein, jisse har string sirf ek baar use ho).
      * `k` loop ko `n` se `ones_count` tak **downwards** chalao.
      * `dp[j][k] = Math.max(dp[j][k], 1 + dp[j - zeros_count][k - ones_count]);`
4.  Akhir mein, `dp[m][n]` mein hamara answer hoga.

**Downward iteration kyu?**
Agar hum `j` aur `k` ko upwards iterate karenge, toh ek hi string ko baar-baar include karne ka chance ban jayega (unbounded knapsack jaisa). Downwards iterate karne se, `dp[j - zeros_count][k - ones_count]` hamesha us state ko refer karega jo **current string ko process karne se pehle** ki thi, jisse 0/1 choice maintain rehti hai.

**Example ke saath Bottom-Up:**

`strs = ["10", "0001", "1", "0"]`, `m = 3`, `n = 4`

`dp` table `(3+1) x (4+1)` size ka hoga: `dp[4][5]` (saari values 0 se initialized)

```
        0  1  2  3  4  (k = ones capacity)
     0 [0, 0, 0, 0, 0]
     1 [0, 0, 0, 0, 0]
     2 [0, 0, 0, 0, 0]
     3 [0, 0, 0, 0, 0]
 (j = zeros capacity)
```

**1. Process "10" (`zeros_count = 1`, `ones_count = 1`)**
`j` (from `m=3` down to `1`), `k` (from `n=4` down to `1`)

  * `j=3, k=4`: `dp[3][4] = max(dp[3][4], 1 + dp[3-1][4-1]) = max(0, 1 + dp[2][3]) = max(0, 1+0) = 1`
  * `j=3, k=3`: `dp[3][3] = max(dp[3][3], 1 + dp[2][2]) = 1`
  * ... and so on.
  * `j=1, k=1`: `dp[1][1] = max(dp[1][1], 1 + dp[0][0]) = max(0, 1+0) = 1`

`dp` after "10":

```
        0  1  2  3  4
     0 [0, 0, 0, 0, 0]
     1 [0, 1, 1, 1, 1]  (j=1, k>=1, can pick "10")
     2 [0, 1, 1, 1, 1]
     3 [0, 1, 1, 1, 1]
```

(Only `dp[j][k]` for `j >= 1` and `k >= 1` become 1)

**2. Process "0001" (`zeros_count = 3`, `ones_count = 1`)**
`j` (from `m=3` down to `3`), `k` (from `n=4` down to `1`)

  * `j=3, k=4`: `dp[3][4] = max(dp[3][4], 1 + dp[3-3][4-1]) = max(1, 1 + dp[0][3]) = max(1, 1+0) = 1` (no change, already 1)
  * `j=3, k=3`: `dp[3][3] = max(dp[3][3], 1 + dp[0][2]) = max(1, 1+0) = 1` (no change)
  * `j=3, k=2`: `dp[3][2] = max(dp[3][2], 1 + dp[0][1]) = max(1, 1+0) = 1` (no change)
  * `j=3, k=1`: `dp[3][1] = max(dp[3][1], 1 + dp[0][0]) = max(1, 1+0) = 1` (no change)

`dp` after "0001":
(No obvious change in values that were 1, but new possibilities if they fit.)
Example: If we pick "0001" (3 zeroes, 1 one):

  * `dp[3][1]` can be made by just taking "0001". So `1+dp[0][0] = 1`.
  * `dp[3][2]` can be made by taking "0001" and "10" (which has 1 '0', 1 '1')
    `dp[3][2]` would become `max(dp[3][2], 1 + dp[3-3][2-1]) = max(1, 1 + dp[0][1]) = max(1, 1+0) = 1`.
    This is trickier without showing the whole table. Let's trace one crucial cell:
    `dp[3][4]`
    Before "0001": `dp[3][4] = 1` (from "10")
    `j=3, k=4`: `dp[3][4] = max(dp[3][4], 1 + dp[3-3][4-1]) = max(1, 1 + dp[0][3]) = max(1, 1+0) = 1`. (No change, as picking "0001" alone is 1 string. If we picked "10", we already got 1 string.)

It's complex to show entire table updates for each string. The important thing is that `dp[j][k]` will correctly store the maximum count.

**3. Process "1" (`zeros_count = 0`, `ones_count = 1`)**
`j` (from `m=3` down to `0`), `k` (from `n=4` down to `1`)

  * `j=3, k=4`: `dp[3][4] = max(dp[3][4], 1 + dp[3-0][4-1]) = max(1, 1 + dp[3][3])`.
    From previous step, `dp[3][3]` was `1`. So `dp[3][4] = max(1, 1+1) = 2`. (This means we can get 2 strings: "10" and "1")
  * `j=1, k=1`: `dp[1][1] = max(dp[1][1], 1 + dp[1-0][1-1]) = max(1, 1 + dp[1][0])`. `dp[1][0]` is 0. So `max(1, 1+0) = 1`. (No change, as "10" already gave 1, or just "1" gives 1).

**4. Process "0" (`zeros_count = 1`, `ones_count = 0`)**
`j` (from `m=3` down to `1`), `k` (from `n=4` down to `0`)

  * `j=3, k=4`: `dp[3][4] = max(dp[3][4], 1 + dp[3-1][4-0]) = max(2, 1 + dp[2][4])`.
    Let's assume `dp[2][4]` was 1 (from picking "10" and "1", total 1 zero, 2 ones). So `max(2, 1+1) = 2`.
    This means `dp[3][4]` can be 2.
    Example, taking "10" (1,1) + "0" (1,0) = (2,1). Size 2.
    Or "1" (0,1) + "0" (1,0) = (1,1). Size 2.
    This means `dp[3][4]` can achieve 2 strings. But we know the answer is 3 for this example. This shows the difficulty of manual tracing.

**Final expected `dp[3][4]` for `strs = ["10", "0001", "111001", "1", "0"]`, `m = 3`, `n = 4`**

  * Strings are: ("10": 1z, 1o), ("0001": 3z, 1o), ("111001": 3z, 3o), ("1": 0z, 1o), ("0": 1z, 0o)
  * Let's take `strs = ["10", "0001", "1", "0"]`, `m=3`, `n=4`.
      * We can take "10", "1", "0".
          * "10": 1z, 1o
          * "1": 0z, 1o
          * "0": 1z, 0o
          * Total: 2z, 2o. This fits (2 \<= 3, 2 \<= 4). Size = 3.
      * Is there a way to get 4? Maybe by not taking "0001" (3z, 1o)?
      * If `m=3, n=4`, and strings `["10", "0001", "1", "0"]`.
      * The actual answer for this simplified example should be 3. (e.g. `{"10", "1", "0"}` or `{"0001", "1"}`).
      * This confirms that `dp[3][4]` should be 3.

**Java Code (Bottom-Up):**

```java
import java.util.Arrays;

class SolutionOnesAndZeroes_BottomUp {
    /**
     * Finds the size of the largest subset of binary strings using Bottom-Up (Tabulation) DP.
     * This is a 2D Knapsack problem.
     *
     * @param strs Array of binary strings.
     * @param m    Maximum allowed '0's.
     * @param n    Maximum allowed '1's.
     * @return The size of the largest valid subset.
     */
    public int findMaxForm(String[] strs, int m, int n) {
        // dp[j][k] stores the maximum number of strings that can be formed with 'j' zeros and 'k' ones.
        int[][] dp = new int[m + 1][n + 1];

        // Default initialization of int array is 0, which is correct for base cases.
        // dp[0][0] = 0 means with 0 zeros and 0 ones, we can form 0 strings.

        // Har string ko ek "item" ki tarah process karo
        for (String s : strs) {
            int zerosCount = 0;
            int onesCount = 0;
            for (char c : s.toCharArray()) {
                if (c == '0') {
                    zerosCount++;
                } else {
                    onesCount++;
                }
            }

            // Ab dp table ko update karo. Inner loops ko reverse order mein chalao
            // jisse har string sirf ek hi baar include ho (0/1 Knapsack property).
            for (int j = m; j >= zerosCount; j--) { // Iterate zeros capacity downwards
                for (int k = n; k >= onesCount; k--) { // Iterate ones capacity downwards
                    // Option 1: Current string 's' ko include mat karo.
                    // Value remains dp[j][k] (jo current iteration se pehle ki value hai).
                    // Option 2: Current string 's' ko include karo.
                    // Iski value 1 hai + jo bachi hui capacity se maximum strings ban sakti thi.
                    // dp[j - zerosCount][k - onesCount] previous state ko refer karta hai.
                    dp[j][k] = Math.max(dp[j][k], 1 + dp[j - zerosCount][k - onesCount]);
                }
            }
        }

        // Akhir mein, dp[m][n] mein hamara answer hoga.
        return dp[m][n];
    }

    public static void main(String[] args) {
        SolutionOnesAndZeroes_BottomUp solver = new SolutionOnesAndZeroes_BottomUp();

        System.out.println("--- 474. Ones and Zeroes (Bottom-Up) ---");

        String[] strs1 = {"10", "0001", "111001", "1", "0"};
        int m1 = 3, n1 = 4;
        // Example Explanation:
        // {"10", "0", "1"} -> Zeros: 1+1+0=2 (<=3), Ones: 1+0+1=2 (<=4). Size = 3.
        // {"0001", "1"} -> Zeros: 3+0=3 (<=3), Ones: 1+1=2 (<=4). Size = 2.
        // {"0001", "0"} -> Zeros: 3+1=4 (>3). Not valid.
        // The largest is 3.
        System.out.println("strs: " + Arrays.toString(strs1) + ", m=" + m1 + ", n=" + n1 + " -> Max Form: " + solver.findMaxForm(strs1, m1, n1)); // Expected: 3

        String[] strs2 = {"10", "0001", "111001", "1", "0"};
        int m2 = 5, n2 = 3;
        // Example: {"10", "0001", "1", "0"}
        // Zeros: 1+3+0+1 = 5 (<=5), Ones: 1+1+1+0 = 3 (<=3). Size = 4.
        System.out.println("strs: " + Arrays.toString(strs2) + ", m=" + m2 + ", n=" + n2 + " -> Max Form: " + solver.findMaxForm(strs2, m2, n2)); // Expected: 4

        String[] strs3 = {"0", "1", "00", "000"};
        int m3 = 2, n3 = 1;
        // "0" (1,0)
        // "1" (0,1)
        // "00" (2,0)
        // "000" (3,0)
        // Possible: {"0", "1"} -> 1z, 1o. Valid. Size 2.
        // Possible: {"00"} -> 2z, 0o. Valid. Size 1.
        // max is 2.
        System.out.println("strs: " + Arrays.toString(strs3) + ", m=" + m3 + ", n=" + n3 + " -> Max Form: " + solver.findMaxForm(strs3, m3, n3)); // Expected: 2
    }
}
```

-----

### Approach 2: Top-Down (Memoization)

Top-Down approach mein hum recursion use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 3D array) use karte hain, taaki same calculations baar-baar na karni pade.

**DP State:**

`memo[string_idx][current_m][current_n]` ka matlab hoga: `string_idx` se lekar `strs` array ke end tak ki strings ko use karke, `current_m` '0's aur `current_n` '1's ki capacity ke saath, **maximum kitni strings** select ki ja sakti hain.

**Algorithm (Tareeka):**

1.  Ek 3D integer `memo` table banao jiska size `(strs.length) x (m+1) x (n+1)` hoga. Sab values ko `-1` (ya koi invalid value) se initialize karo, jo uncomputed states ko indicate karega.
2.  Ek recursive function `dp(string_idx, current_m, current_n)` banao.
      * **Base Cases:**
          * Agar `string_idx` `strs.length` ke barabar ho gaya (matlab saari strings process ho gayi), toh `0` return karo (kyunki ab koi aur string nahi hai pick karne ke liye).
          * Agar `current_m < 0` ya `current_n < 0` ho gaya (matlab capacity negative ho gayi, impossible), toh bhi `0` return karo.
      * **Memoization Check:** Agar `memo[string_idx][current_m][current_n]` pehle se computed hai (`!= -1`), toh stored value return kar do.
      * **Recursive Step:**
          * `current_string` (jo `strs[string_idx]` hai) mein `zeros_count` aur `ones_count` nikaalo.
          * **Option 1: `current_string` ko mat lo (Exclude):**
            `take_none = dp(string_idx + 1, current_m, current_n)`
            (Next string pe jao, capacities wahi rahengi)
          * **Option 2: `current_string` ko lo (Include):**
            Agar `current_string` fit ho sakti hai (`current_m >= zeros_count` aur `current_n >= ones_count`), tabhi ye option valid hai.
            `take_current = 1 + dp(string_idx + 1, current_m - zeros_count, current_n - ones_count)`
            (Next string pe jao, capacities update ho jayengi, aur current string ki value `1` add hogi)
          * Store `max(take_none, take_current)` ko `memo[string_idx][current_m][current_n]` mein aur return kar do.
3.  Initial call `dp(0, m, n)` se start karo (pehli string se, poori `m` aur `n` capacity ke saath).

**Diagrammatic Explanation (Top-Down):**

Let's take a small example: `strs = ["1", "0"]`, `m=1`, `n=1`.
`dp(string_idx, current_m, current_n)`

```
                                dp(0, m=1, n=1)  (Processing "1")
                                 /          \
                     Exclude "1"                 Include "1" (0 zeroes, 1 one)
             (take_none)                           (take_current)
           dp(1, 1, 1)                           1 + dp(1, 1-0, 1-1)
           (Processing "0")                      1 + dp(1, 1, 0)
            /          \                          /          \
   Exclude "0"          Include "0"           Exclude "0"    Include "0"
  (take_none)          (take_current)        (take_none)    (take_current)
dp(2, 1, 1)            1 + dp(2, 1-1, 1-0)  dp(2, 1, 0)       1 + dp(2, 1-1, 0-0)
 (Base: 0)            1 + dp(2, 0, 1)      (Base: 0)         1 + dp(2, 0, 0)
                       (Base: 0)                              (Base: 0)

Result:
dp(2, any_m, any_n) = 0 (Base case: no more strings)
dp(2, 0, 1) = 0
dp(2, 0, 0) = 0

Now back-substitute:
dp(1, 1, 0) = max(dp(2, 1, 0), 1 + dp(2, 0, 0))  // Processing "0" with current_m=1, current_n=0
             = max(0, 1 + 0) = 1
Memo[1][1][0] = 1

dp(1, 1, 1) = max(dp(2, 1, 1), 1 + dp(2, 0, 1))  // Processing "0" with current_m=1, current_n=1
             = max(0, 1 + 0) = 1
Memo[1][1][1] = 1

Finally for dp(0, 1, 1):
  Exclude "1": dp(1, 1, 1) = 1
  Include "1": 1 + dp(1, 1, 0) = 1 + 1 = 2

dp(0, 1, 1) = max(1, 2) = 2.

Answer for ["1", "0"], m=1, n=1 is 2. (We can pick {"1", "0"})
```

**Java Code (Top-Down):**

```java
import java.util.Arrays;

class SolutionOnesAndZeroes_TopDown {
    // memo[string_idx][current_m][current_n] stores result
    private int[][][] memo;
    private String[] strs;

    /**
     * Finds the size of the largest subset of binary strings using Top-Down (Memoization) DP.
     *
     * @param strs Array of binary strings.
     * @param m    Maximum allowed '0's.
     * @param n    Maximum allowed '1's.
     * @return The size of the largest valid subset.
     */
    public int findMaxForm(String[] strs, int m, int n) {
        this.strs = strs;
        // memo table ko -1 se initialize karo, jo uncomputed state ko indicate karega.
        // size: [number of strings + 1][m + 1][n + 1]
        // Hum string_idx ko 0 se strs.length tak lenge.
        memo = new int[strs.length][m + 1][n + 1];
        for (int[][] dim2 : memo) {
            for (int[] dim1 : dim2) {
                Arrays.fill(dim1, -1);
            }
        }

        // Recursive process ko start karo pehli string (index 0) se
        // aur poori 'm' aur 'n' capacity ke saath.
        return dp(0, m, n);
    }

    /**
     * Recursive helper function for Top-Down Ones and Zeroes.
     *
     * @param string_idx  Current string ka index jisko consider kar rahe hain.
     * @param current_m   Remaining capacity for '0's.
     * @param current_n   Remaining capacity for '1's.
     * @return Maximum strings jo `string_idx` se end tak pick kar sakte hain,
     * diye gaye `current_m` aur `current_n` capacities ke saath.
     */
    private int dp(int string_idx, int current_m, int current_n) {
        // Base case 1: Agar saari strings process ho gayi hain.
        if (string_idx == strs.length) {
            return 0; // Ab koi aur string nahi bachi.
        }

        // Base case 2: Agar capacity negative ho gayi hai (impossible state).
        // Though, ye check usually `if (current_m >= zerosCount)` mein hi handle ho jaata hai.
        if (current_m < 0 || current_n < 0) {
            return 0; // Invalid state.
        }

        // Memoization check: Agar ye state pehle hi compute ho chuki hai.
        if (memo[string_idx][current_m][current_n] != -1) {
            return memo[string_idx][current_m][current_n];
        }

        // Current string ko process karo
        String s = strs[string_idx];
        int zerosCount = 0;
        int onesCount = 0;
        for (char c : s.toCharArray()) {
            if (c == '0') {
                zerosCount++;
            } else {
                onesCount++;
            }
        }

        // Option 1: Current string ko include mat karo.
        // Next string pe jao, capacities wahi rahengi.
        int takeNone = dp(string_idx + 1, current_m, current_n);

        // Option 2: Current string ko include karo (agar fit ho sakti hai).
        int takeCurrent = 0;
        if (current_m >= zerosCount && current_n >= onesCount) {
            // Agar fit hoti hai, toh 1 (current string ki value) add karo,
            // aur bachi hui capacities ke saath next string pe jao.
            takeCurrent = 1 + dp(string_idx + 1, current_m - zerosCount, current_n - onesCount);
        }

        // Dono options mein se maximum ko store karo aur return karo.
        return memo[string_idx][current_m][current_n] = Math.max(takeNone, takeCurrent);
    }

    public static void main(String[] args) {
        SolutionOnesAndZeroes_TopDown solver = new SolutionOnesAndZeroes_TopDown();

        System.out.println("\n--- 474. Ones and Zeroes (Top-Down) ---");

        String[] strs1 = {"10", "0001", "111001", "1", "0"};
        int m1 = 3, n1 = 4;
        System.out.println("strs: " + Arrays.toString(strs1) + ", m=" + m1 + ", n=" + n1 + " -> Max Form: " + solver.findMaxForm(strs1, m1, n1)); // Expected: 3

        String[] strs2 = {"10", "0001", "111001", "1", "0"};
        int m2 = 5, n2 = 3;
        System.out.println("strs: " + Arrays.toString(strs2) + ", m=" + m2 + ", n=" + n2 + " -> Max Form: " + solver.findMaxForm(strs2, m2, n2)); // Expected: 4

        String[] strs3 = {"0", "1", "00", "000"};
        int m3 = 2, n3 = 1;
        System.out.println("strs: " + Arrays.toString(strs3) + ", m=" + m3 + ", n=" + n3 + " -> Max Form: " + solver.findMaxForm(strs3, m3, n3)); // Expected: 2
    }
}
```
<img width="1312" alt="image" src="https://github.com/user-attachments/assets/1af259e2-71be-4bbe-a9d5-31cbd40e02ee" />
Chalo, `322. Coin Change` problem ko detail mein, Hinglish mein, aur dono DP approaches ke saath samajhte hain. Top-Down ke liye diagram bhi hoga aur Java code bhi.

-----

## 322\. Coin Change Problem Kya Hai?

**Problem Statement (Hinglish):**
Imagine karo aapke paas kuch alag-alag denominations (`coins`) ke coins hain, jaise `[1, 2, 5]` (matlab 1 rupee, 2 rupee, 5 rupee ke sikke). Aapko ek `amount` (total paisa) diya gaya hai, jaise `11`.

Aapko batana hai ki uss `amount` ko banane ke liye **kam se kam kitne coins** ki zaroorat padegi. Yaad rahe, har type ke coin aapke paas **infinite (unlimited)** hain.

Agar uss `amount` ko kisi bhi combination se nahi banaya ja sakta, toh `-1` return karna hai.

**Examples:**

1.  `coins = [1, 2, 5]`, `amount = 11`

      * Answer: `3` (kyunki `11 = 5 + 5 + 1`)
      * `11 = 2 + 2 + 2 + 5` (4 coins)
      * `11 = 1 + 1 + ... + 1` (11 coins)
      * Sabse kam `3` coins hain.

2.  `coins = [2]`, `amount = 3`

      * Answer: `-1` (kyunki 2-rupee coin se 3 rupee nahi ban sakte)

3.  `coins = [1]`, `amount = 0`

      * Answer: `0` (kyunki 0 amount banane ke liye 0 coins chahiye)

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem ek classic Dynamic Programming problem hai, jise **Unbounded Knapsack** (ya **Change-Making Problem**) ki category mein rakha ja sakta hai.
**Unbounded Knapsack** mein, items (yahan coins) ke paas infinite supply hoti hai, aur humein ek specific capacity (yahan `amount`) ko fill karna hota hai. Yahaan, hum items ki 'value' maximize nahi kar rahe, balki 'count' minimize kar rahe hain.

-----

## Dynamic Programming Approach

Hamein **minimum number of coins** chahiye, isliye DP ek achcha solution hai.

**DP State:**

Hum ek 1D DP array use karenge:
`dp[i]` ka matlab hoga: `i` amount banane ke liye **kam se kam kitne coins** ki zaroorat padegi.

**Recurrence Relation:**

`dp[i]` ko calculate karne ke liye, hum har `coin` denomination ko check karenge.
Agar hum `i` amount banane ke liye ek `coin` use karte hain, toh bacha hua amount `i - coin` hoga. Ab humein `i - coin` amount banane ke liye kam se kam coins dhundhne hain, jismein `dp[i - coin]` coins lagenge. Usmein current `coin` ko add kar do (`+1`), toh total `1 + dp[i - coin]` coins lag gaye `i` amount banane ke liye, agar humne woh particular `coin` use kiya.

Hum har possible `coin` ke liye ye calculation karenge aur un sab mein se **minimum** lenge.

`dp[i] = min(1 + dp[i - coin])` for all `coin` in `coins` array, jahan `i >= coin` ho.

**Base Case:**

  * `dp[0] = 0`: `0` amount banane ke liye `0` coins chahiye.
  * Baki sab `dp` values ko ek bade number (jaise `amount + 1` ya `Integer.MAX_VALUE - 1`) se initialize karo, jo indicate karega ki abhi tak woh amount banana possible nahi hai. Agar hum `Integer.MAX_VALUE` use karte hain, toh `1 + Integer.MAX_VALUE` overflow ho sakta hai, isliye `Integer.MAX_VALUE - 1` ya `amount + 1` better hai. `amount + 1` ek safe value hai kyunki agar amount `X` hai, toh `X` coins se zyada coins lag hi nahi sakte (agar saare 1-rupee coins hain).

-----

### Approach 1: Bottom-Up (Tabulation)

Is approach mein hum DP table ko **chhote amounts** se start karke **bade amounts** ki taraf fill karte hain.

**Algorithm (Tareeka):**

1.  Ek `(amount + 1)` size ka `dp` array banao.
2.  `dp[0]` ko `0` se initialize karo.
3.  Baki sab `dp` values ko `amount + 1` (ya `Integer.MAX_VALUE`) se initialize karo. Ye ek tarah se `infinity` hai, matlab abhi tak un amounts ko banana possible nahi hai.
4.  Ab `i` loop ko `1` se `amount` tak chalao (har possible amount ke liye).
5.  Har `i` amount ke liye, `coins` array mein se har `coin` ko iterate karo:
      * Agar `i >= coin` hai (matlab current `coin` current `amount` mein fit ho sakta hai):
          * `dp[i] = Math.min(dp[i], 1 + dp[i - coin])`
6.  Akhir mein, `dp[amount]` mein hamara answer hoga.
      * Agar `dp[amount]` ab bhi `amount + 1` (initial "infinity" value) hai, toh iska matlab `amount` ko banana possible nahi tha, toh `-1` return karo.
      * Warna `dp[amount]` return karo.

**Example ke saath Bottom-Up:**

`coins = [1, 2, 5]`, `amount = 11`

`dp` array size `(11 + 1) = 12`. Initialize `dp[0]=0`, baki sab `12` se.
`dp = [0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]`

**Loop `i` from 1 to 11:**

  * **`i = 1`:**

      * `coin = 1`: `1 >= 1`. `dp[1] = min(dp[1], 1 + dp[1 - 1]) = min(12, 1 + dp[0]) = min(12, 1 + 0) = 1`
      * `coin = 2, 5`: (not applicable, `1 < 2, 1 < 5`)
        `dp = [0, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]`

  * **`i = 2`:**

      * `coin = 1`: `2 >= 1`. `dp[2] = min(dp[2], 1 + dp[2 - 1]) = min(12, 1 + dp[1]) = min(12, 1 + 1) = 2`
      * `coin = 2`: `2 >= 2`. `dp[2] = min(dp[2], 1 + dp[2 - 2]) = min(2, 1 + dp[0]) = min(2, 1 + 0) = 1`
      * `coin = 5`: (not applicable)
        `dp = [0, 1, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12]`

  * **`i = 3`:**

      * `coin = 1`: `dp[3] = min(12, 1 + dp[2]) = min(12, 1 + 1) = 2`
      * `coin = 2`: `dp[3] = min(2, 1 + dp[1]) = min(2, 1 + 1) = 2`
      * `coin = 5`: (not applicable)
        `dp = [0, 1, 1, 2, 12, 12, 12, 12, 12, 12, 12, 12]`

... and so on ...

  * **`i = 5`:**
      * `coin = 1`: `dp[5] = min(12, 1 + dp[4])` (assuming `dp[4]` became 2: `2+2 = 4`, `1+1+1+1 = 4`) `dp[4]` should be 2. So `dp[5] = min(12, 1+2) = 3`.
      * `coin = 2`: `dp[5] = min(3, 1 + dp[3]) = min(3, 1+2) = 3`.
      * `coin = 5`: `dp[5] = min(3, 1 + dp[0]) = min(3, 1+0) = 1`.
        `dp` values will update. `dp[5]` will become 1 (using one 5-rupee coin).

... (many iterations later) ...

  * **`i = 11`:**
      * `coin = 1`: `dp[11] = min(12, 1 + dp[10])`. If `dp[10]` is 2 (e.g. `5+5`), then `1+2 = 3`.
      * `coin = 2`: `dp[11] = min(current_dp[11], 1 + dp[9])`.
      * `coin = 5`: `dp[11] = min(current_dp[11], 1 + dp[6])`. If `dp[6]` is 2 (e.g. `5+1`), then `1+2 = 3`.

Finally, `dp[11]` will be `3`.

**Java Code (Bottom-Up):**

```java
import java.util.Arrays;

class SolutionCoinChange_BottomUp {
    /**
     * Coin Change problem ko Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Ismein fewest number of coins dhundhne hain amount banane ke liye.
     *
     * @param coins Coins ke denominations ka array.
     * @param amount Target amount.
     * @return Kam se kam coins ki sankhya, ya -1 agar possible na ho.
     */
    public int coinChange(int[] coins, int amount) {
        // Agar amount 0 hai, toh 0 coins chahiye.
        if (amount == 0) {
            return 0;
        }

        // dp[i] = 'i' amount banane ke liye kam se kam coins.
        // Array size (amount + 1) hoga.
        int[] dp = new int[amount + 1];

        // Sabhi dp values ko (amount + 1) se initialize karo.
        // Ye ek 'infinity' value hai, kyunki 'amount' banane ke liye max 'amount' coins hi lagenge
        // agar saare coins 1 value ke hon. 'amount + 1' ka matlab impossible.
        Arrays.fill(dp, amount + 1);

        // Base case: 0 amount banane ke liye 0 coins.
        dp[0] = 0;

        // Har amount 'i' (1 se amount tak) ke liye iterate karo.
        for (int i = 1; i <= amount; i++) {
            // Har available coin ko check karo.
            for (int coin : coins) {
                // Agar current amount 'i' current 'coin' se bada ya barabar hai,
                // tabhi hum is coin ko use kar sakte hain.
                if (i >= coin) {
                    // dp[i] ko update karo.
                    // Option 1: Current dp[i] value (agar current coin ko nahi lete).
                    // Option 2: 1 (current coin) + dp[i - coin] (baki bache amount ke liye coins).
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        // Akhir mein, agar dp[amount] ab bhi (amount + 1) hai, toh amount banana possible nahi tha.
        // Otherwise, dp[amount] hi answer hai.
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        SolutionCoinChange_BottomUp solver = new SolutionCoinChange_BottomUp();

        System.out.println("--- 322. Coin Change (Bottom-Up) ---");

        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println("Coins: " + Arrays.toString(coins1) + ", Amount: " + amount1 + " -> Min Coins: " + solver.coinChange(coins1, amount1)); // Expected: 3

        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("Coins: " + Arrays.toString(coins2) + ", Amount: " + amount2 + " -> Min Coins: " + solver.coinChange(coins2, amount2)); // Expected: -1

        int[] coins3 = {1};
        int amount3 = 0;
        System.out.println("Coins: " + Arrays.toString(coins3) + ", Amount: " + amount3 + " -> Min Coins: " + solver.coinChange(coins3, amount3)); // Expected: 0

        int[] coins4 = {186, 419, 83, 408};
        int amount4 = 6249;
        System.out.println("Coins: " + Arrays.toString(coins4) + ", Amount: " + amount4 + " -> Min Coins: " + solver.coinChange(coins4, amount4)); // Expected: 20
    }
}
```

-----

### Approach 2: Top-Down (Memoization)

Top-Down approach mein hum recursion use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 1D array) use karte hain, taaki same calculations baar-baar na karni pade.

**DP State:**

`memo[i]` ka matlab hoga: `i` amount banane ke liye **kam se kam kitne coins**.

**Algorithm (Tareeka):**

1.  Ek 1D integer `memo` table banao jiska size `(amount + 1)` hoga. Sab values ko `-1` se initialize karo, jo uncomputed states ko indicate karega.
2.  Ek recursive function `dp(current_amount, coins, memo)` banao.
      * **Base Cases:**
          * Agar `current_amount == 0` hai, toh `0` return karo (0 amount ke liye 0 coins).
          * Agar `current_amount < 0` hai, toh `Integer.MAX_VALUE` return karo (ye represent karta hai ki is state se amount banana possible nahi hai).
      * **Memoization Check:** Agar `memo[current_amount]` pehle se computed hai (`!= -1`), toh stored value return kar do.
      * **Recursive Step:**
          * `min_coins` ko `Integer.MAX_VALUE` se initialize karo.
          * Har `coin` ke liye `coins` array mein:
              * `res = dp(current_amount - coin, coins, memo)` ko recursively call karo.
              * Agar `res` `Integer.MAX_VALUE` nahi hai (matlab `current_amount - coin` banana possible tha):
                  * `min_coins = Math.min(min_coins, 1 + res)`
          * Store `min_coins` ko `memo[current_amount]` mein.
          * Agar `min_coins` ab bhi `Integer.MAX_VALUE` hai, toh iska matlab current `amount` banana possible nahi tha, toh `memo[current_amount]` mein bhi `Integer.MAX_VALUE` store karo.
          * Return `memo[current_amount]`.
3.  Initial call `dp(amount, coins, memo)` se start karo.
4.  Function ke return value ko check karo: agar `Integer.MAX_VALUE` hai, toh `-1` return karo; warna value return karo.

**Diagrammatic Explanation (Top-Down):**

Let's take `coins = [1, 2]`, `amount = 3`.
`dp(current_amount)` is the function we're calling.
`memo` array will be of size 4 (for amounts 0, 1, 2, 3), initialized with -1.

````
                                  dp(3)
                                 / | \ (try coins: 1, 2)
                                /  |  \
                              /    |   \
                     1 + dp(2)  1 + dp(1)
                      /  |  \    /  |  \
                    /    |   \  /   |   \
             1+dp(1) 1+dp(0) 1+dp(0) 1+dp(-1) (Invalid, returns MAX_VALUE)
              / | \    |        |
            /   |  \   |        |
           1+dp(0) ... 0        0

Explanation Flow:

1.  **`dp(3)` called**
    * `min_coins = MAX_VALUE`
    * Try `coin = 1`: `1 + dp(2)`
        * **`dp(2)` called**
            * `min_coins = MAX_VALUE`
            * Try `coin = 1`: `1 + dp(1)`
                * **`dp(1)` called**
                    * `min_coins = MAX_VALUE`
                    * Try `coin = 1`: `1 + dp(0)`
                        * **`dp(0)` called**. Base Case: Returns `0`.
                    * `dp(1)` updates `min_coins = min(MAX_VALUE, 1 + 0) = 1`.
                    * Try `coin = 2`: `1 + dp(-1)`. Base Case: Returns `MAX_VALUE`. `min_coins` remains `1`.
                    * Store `memo[1] = 1`. Return `1`.
                * `dp(2)` updates `min_coins = min(MAX_VALUE, 1 + 1) = 2`.
            * Try `coin = 2`: `1 + dp(0)`
                * `dp(0)` returns `0`.
            * `dp(2)` updates `min_coins = min(2, 1 + 0) = 1`. (Because using coin 2 for amount 2 is better than two 1s)
            * Store `memo[2] = 1`. Return `1`.
        * `dp(3)` updates `min_coins = min(MAX_VALUE, 1 + 1) = 2`.
    * Try `coin = 2`: `1 + dp(1)`
        * **`dp(1)` is already memoized (`memo[1] = 1`). Directly returns `1`.**
        * `dp(3)` updates `min_coins = min(2, 1 + 1) = 2`.

2.  All coins tried for `dp(3)`. `min_coins` for `dp(3)` is `2`.
3.  Store `memo[3] = 2`. Return `2`.

Final Answer: `2` (`3 = 1 + 2`). This is correct.

```mermaid
graph TD
    A[dp(amount)] --> B{current_amount == 0?};
    B -- Yes --> C[Return 0];
    B -- No --> D{current_amount < 0?};
    D -- Yes --> E[Return MAX_VALUE];
    D -- No --> F{memo[current_amount] != -1?};
    F -- Yes --> G[Return memo[current_amount]];
    F -- No --> H[min_coins = MAX_VALUE];
    H --> I{For each coin in coins};
    I --> J[res = dp(current_amount - coin)];
    J --> K{res != MAX_VALUE?};
    K -- Yes --> L[min_coins = min(min_coins, 1 + res)];
    K -- No --> I;
    I -- End Loop --> M[Store min_coins in memo[current_amount]];
    M --> N[Return memo[current_amount]];
````

**Java Code (Top-Down):**

```java
import java.util.Arrays;

class SolutionCoinChange_TopDown {
    // memo[i] stores the minimum coins needed for amount 'i'.
    // Initialized with -1 to indicate uncomputed states.
    private int[] memo;
    private int[] coins;

    /**
     * Coin Change problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     *
     * @param coins Coins ke denominations ka array.
     * @param amount Target amount.
     * @return Kam se kam coins ki sankhya, ya -1 agar possible na ho.
     */
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }

        this.coins = coins;
        // memo table initialize karo. Size (amount + 1).
        // -1 ka matlab ye amount abhi tak calculate nahi hua hai.
        memo = new int[amount + 1];
        Arrays.fill(memo, -1);

        // Recursive function ko call karo starting from the target amount.
        int result = dp(amount);

        // Agar result Integer.MAX_VALUE aaya hai, toh matlab amount banana possible nahi tha.
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * Recursive helper function: computes minimum coins for a given amount.
     *
     * @param current_amount The amount for which we need to find minimum coins.
     * @return Minimum coins for `current_amount`, or Integer.MAX_VALUE if not possible.
     */
    private int dp(int current_amount) {
        // Base case 1: Agar current_amount 0 hai, toh 0 coins chahiye.
        if (current_amount == 0) {
            return 0;
        }
        // Base case 2: Agar current_amount negative ho gaya hai, matlab ye path invalid hai.
        // Isse hum Infinity se represent karte hain.
        if (current_amount < 0) {
            return Integer.MAX_VALUE;
        }

        // Memoization check: Agar ye amount pehle hi calculate ho chuka hai, toh stored result return karo.
        if (memo[current_amount] != -1) {
            return memo[current_amount];
        }

        // min_coins ko ek bahut bade number se initialize karo.
        // Isko Integer.MAX_VALUE se kam rakhna better hai, taaki 1+result overflow na kare.
        // Lekin yahan Integer.MAX_VALUE safe hai kyunki 1 + MAX_VALUE ho kar bhi wo MIN_VALUE se compare hoga,
        // aur hum MAX_VALUE hi wapas laenge.
        int min_coins = Integer.MAX_VALUE;

        // Har coin denomination ko try karo.
        for (int coin : coins) {
            // Recursive call: baki bache amount ke liye min coins dhundo.
            int res = dp(current_amount - coin);

            // Agar res Integer.MAX_VALUE nahi hai (matlab (current_amount - coin) banana possible tha),
            // toh current path ko consider karo.
            if (res != Integer.MAX_VALUE) {
                min_coins = Math.min(min_coins, 1 + res); // 1 for the current coin + res for remaining amount.
            }
        }

        // Result ko memo table mein store karo.
        memo[current_amount] = min_coins;

        // Final result return karo.
        return min_coins;
    }

    public static void main(String[] args) {
        SolutionCoinChange_TopDown solver = new SolutionCoinChange_TopDown();

        System.out.println("\n--- 322. Coin Change (Top-Down) ---");

        int[] coins1 = {1, 2, 5};
        int amount1 = 11;
        System.out.println("Coins: " + Arrays.toString(coins1) + ", Amount: " + amount1 + " -> Min Coins: " + solver.coinChange(coins1, amount1)); // Expected: 3

        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("Coins: " + Arrays.toString(coins2) + ", Amount: " + amount2 + " -> Min Coins: " + solver.coinChange(coins2, amount2)); // Expected: -1

        int[] coins3 = {1};
        int amount3 = 0;
        System.out.println("Coins: " + Arrays.toString(coins3) + ", Amount: " + amount3 + " -> Min Coins: " + solver.coinChange(coins3, amount3)); // Expected: 0

        int[] coins4 = {186, 419, 83, 408};
        int amount4 = 6249;
        System.out.println("Coins: " + Arrays.toString(coins4) + ", Amount: " + amount4 + " -> Min Coins: " + solver.coinChange(coins4, amount4)); // Expected: 20
    }
}
```


<img width="1312" alt="image" src="https://github.com/user-attachments/assets/db0857f6-2ca8-4dc7-9080-02bda3be2963" />
Chalo, `518. Coin Change II` problem ko detail mein, Hinglish mein, aur dono DP approaches ke saath samajhte hain. Top-Down ke liye diagram bhi hoga aur Java code bhi.

-----

## 518\. Coin Change II Problem Kya Hai?

**Problem Statement (Hinglish):**
Aapko ek integer array `coins` diya gaya hai, jismein alag-alag denominations ke coins hain. Saath hi, ek integer `amount` bhi diya gaya hai.

Aapko dhundhna hai ki **kitne alag-alag tareekon (combinations)** se uss `amount` ko banaya ja sakta hai, in `coins` ko use karke. Yaad rakhiyega, aapke paas har type ke coin **infinite (unlimited)** quantity mein hain.

Agar uss `amount` ko kisi bhi combination se nahi banaya ja sakta, toh `0` return karna hai.

**Important Note:** Ye **Combinations** ka problem hai, permutations ka nahi. Iska matlab hai ki agar `coins = [1, 2]` aur `amount = 3` hai:

  * `1 + 2` ek combination hai.
  * `2 + 1` ye bhi same combination mana jayega.
  * `1 + 1 + 1` ek alag combination hai.
    Toh total combinations `2` hain (`1+2` aur `1+1+1`).

**Examples:**

1.  `coins = [1, 2, 5]`, `amount = 5`

      * Answer: `4`
          * `5` (ek 5 ka coin)
          * `2 + 2 + 1` (do 2 ke coins, ek 1 ka coin)
          * `2 + 1 + 1 + 1` (ek 2 ka coin, teen 1 ke coins)
          * `1 + 1 + 1 + 1 + 1` (paanch 1 ke coins)

2.  `coins = [2]`, `amount = 3`

      * Answer: `0` (kyunki 2-rupee coin se 3 rupee nahi ban sakte)

3.  `coins = [10]`, `amount = 10`

      * Answer: `1` (sirf ek hi tareeka hai: ek 10 ka coin)

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem bhi **Unbounded Knapsack** ya **Change-Making Problem** ka variation hai. Lekin pichhli `Coin Change` problem (LeetCode 322) mein humein minimum coins chahiye the, aur yahaan humein **number of ways (combinations)** chahiye. Ye ek crucial difference hai jo DP approach ko thoda change karta hai.

Ye bilkul **"Subset Sum - Count Ways"** problem jaisi hai, jahaan har item (coin) ki infinite supply hai.

-----

## Dynamic Programming Approach

Hamein **number of combinations** chahiye, isliye DP ek achcha solution hai.

**DP State:**

Hum ek 1D DP array use karenge:
`dp[i]` ka matlab hoga: `i` amount banane ke **kitne alag-alag tareeke** hain.

**Recurrence Relation:**

`dp[i]` ko calculate karne ke liye, hum `coins` array mein se har `coin` ko iterate karenge.
Pichhli `Coin Change` problem (minimum coins) mein hum `amount` ke outer loop mein `coins` ko iterate kar rahe the. Yahaan, hum `coins` ko **outer loop** mein iterate karenge aur `amount` ko **inner loop** mein, jisse combinations ka order maintain rahe aur hum duplicate permutations count na karein.

Jab hum kisi `current_coin` ko process kar rahe honge:
`dp[i] = dp[i] + dp[i - current_coin]` (jahan `i >= current_coin` ho)

  * `dp[i]` (current value) ka matlab hai `i` amount ko `current_coin` ko consider kiye bina kitne tareekon se banaya ja sakta tha.
  * `dp[i - current_coin]` ka matlab hai `i - current_coin` amount ko current `current_coin` tak ke coins se kitne tareekon se banaya ja sakta tha. Agar hum usmein `current_coin` add kar dein, toh hamein `i` amount banane ka ek naya tareeka mil jayega.

**Base Case:**

  * `dp[0] = 1`: `0` amount banane ka **ek hi tareeka** hai (koi coin na lena).

-----

### Approach 1: Bottom-Up (Tabulation)

Is approach mein hum DP table ko **chhote amounts** se start karke **bade amounts** ki taraf fill karte hain. `coins` ka loop outer hoga aur `amount` ka inner loop.

**Algorithm (Tareeka):**

1.  Ek `(amount + 1)` size ka `dp` array banao.
2.  `dp[0]` ko `1` se initialize karo (0 amount banane ka ek tareeka).
3.  Baki sab `dp` values ko `0` se initialize karo.
4.  `coins` array mein se har `coin` ko iterate karo (outer loop).
5.  Har `coin` ke liye, `i` loop ko `coin` se `amount` tak chalao (inner loop).
      * `dp[i] = dp[i] + dp[i - coin]`
6.  Akhir mein, `dp[amount]` mein hamara answer hoga.

**Example ke saath Bottom-Up:**

`coins = [1, 2, 5]`, `amount = 5`

`dp` array size `(5 + 1) = 6`. Initialize `dp[0]=1`, baki sab `0` se.
`dp = [1, 0, 0, 0, 0, 0]`

**1. Process `coin = 1`:**
`i` from `1` to `5`

  * `i = 1`: `dp[1] = dp[1] + dp[1 - 1] = 0 + dp[0] = 0 + 1 = 1`
  * `i = 2`: `dp[2] = dp[2] + dp[2 - 1] = 0 + dp[1] = 0 + 1 = 1`
  * `i = 3`: `dp[3] = dp[3] + dp[3 - 1] = 0 + dp[2] = 0 + 1 = 1`
  * `i = 4`: `dp[4] = dp[4] + dp[4 - 1] = 0 + dp[3] = 0 + 1 = 1`
  * `i = 5`: `dp[5] = dp[5] + dp[5 - 1] = 0 + dp[4] = 0 + 1 = 1`
    `dp` after `coin = 1`: `[1, 1, 1, 1, 1, 1]` (Saare amounts ab sirf 1 ke coins se ban sakte hain, ek-ek tareeke se)

**2. Process `coin = 2`:**
`i` from `2` to `5` (kyunki `i >= coin` hona chahiye)

  * `i = 2`: `dp[2] = dp[2] + dp[2 - 2] = dp[2] + dp[0] = 1 + 1 = 2` (2 banane ke tareeke: `1+1`, `2`)
  * `i = 3`: `dp[3] = dp[3] + dp[3 - 2] = dp[3] + dp[1] = 1 + 1 = 2` (3 banane ke tareeke: `1+1+1`, `2+1`)
  * `i = 4`: `dp[4] = dp[4] + dp[4 - 2] = dp[4] + dp[2] = 1 + 2 = 3` (4 banane ke tareeke: `1+1+1+1`, `2+1+1`, `2+2`)
  * `i = 5`: `dp[5] = dp[5] + dp[5 - 2] = dp[5] + dp[3] = 1 + 2 = 3` (5 banane ke tareeke: `1+1+1+1+1`, `2+1+1+1`, `2+2+1`)
    `dp` after `coin = 2`: `[1, 1, 2, 2, 3, 3]`

**3. Process `coin = 5`:**
`i` from `5` to `5` (kyunki `i >= coin` hona chahiye)

  * `i = 5`: `dp[5] = dp[5] + dp[5 - 5] = dp[5] + dp[0] = 3 + 1 = 4` (5 banane ke tareeke: `1+1+1+1+1`, `2+1+1+1`, `2+2+1`, `5`)
    `dp` after `coin = 5`: `[1, 1, 2, 2, 3, 4]`

`dp[5] = 4`. Correct\!

**Java Code (Bottom-Up):**

```java
import java.util.Arrays;

class SolutionCoinChangeII_BottomUp {
    /**
     * Coin Change II problem ko Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Ismein total combinations dhundhne hain amount banane ke liye.
     *
     * @param amount Target amount.
     * @param coins Coins ke denominations ka array.
     * @return Combinations ki sankhya, ya 0 agar possible na ho.
     */
    public int change(int amount, int[] coins) {
        // dp[i] = 'i' amount banane ke kitne tareeke hain.
        // Array size (amount + 1) hoga.
        int[] dp = new int[amount + 1];

        // Base case: 0 amount banane ka ek hi tareeka hai (koi coin na lena).
        dp[0] = 1;

        // Har coin ko iterate karo (outer loop).
        // Ye order maintain karna zaroori hai combinations count karne ke liye,
        // jisse (1,2) aur (2,1) jaisi permutations ko ek hi combination mana jaye.
        for (int coin : coins) {
            // Har amount 'i' (current 'coin' se amount tak) ke liye iterate karo (inner loop).
            for (int i = coin; i <= amount; i++) {
                // dp[i] mein dp[i - coin] ko add karo.
                // dp[i - coin] represent karta hai ki (i - coin) amount ko kitne tareekon se banaya ja sakta hai,
                // current 'coin' tak ke coins use karke.
                // Usmein current 'coin' ko add karne se 'i' amount banane ka naya tareeka milta hai.
                dp[i] = dp[i] + dp[i - coin];
            }
        }

        // Akhir mein, dp[amount] mein hamara answer hoga.
        return dp[amount];
    }

    public static void main(String[] args) {
        SolutionCoinChangeII_BottomUp solver = new SolutionCoinChangeII_BottomUp();

        System.out.println("--- 518. Coin Change II (Bottom-Up) ---");

        int[] coins1 = {1, 2, 5};
        int amount1 = 5;
        System.out.println("Coins: " + Arrays.toString(coins1) + ", Amount: " + amount1 + " -> Combinations: " + solver.change(amount1, coins1)); // Expected: 4

        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("Coins: " + Arrays.toString(coins2) + ", Amount: " + amount2 + " -> Combinations: " + solver.change(amount2, coins2)); // Expected: 0

        int[] coins3 = {10};
        int amount3 = 10;
        System.out.println("Coins: " + Arrays.toString(coins3) + ", Amount: " + amount3 + " -> Combinations: " + solver.change(amount3, coins3)); // Expected: 1
    }
}
```

-----

### Approach 2: Top-Down (Memoization)

Top-Down approach mein hum recursion use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 2D array) use karte hain, taaki same calculations baar-baar na karni pade.

**DP State:**

`memo[coin_idx][current_amount]` ka matlab hoga: `coin_idx` se lekar `coins` array ke end tak ke coins ko use karke, `current_amount` banane ke **kitne tareeke** hain.

**Recurrence Relation:**

`dp(coin_idx, current_amount)`:

  * **Option 1: `coins[coin_idx]` ko include mat karo (Exclude):**
    `dp(coin_idx + 1, current_amount)` (Next coin pe jao, amount wahi rahega)
  * **Option 2: `coins[coin_idx]` ko include karo (Include):**
    Agar `current_amount >= coins[coin_idx]` hai, toh:
    `dp(coin_idx, current_amount - coins[coin_idx])` (Same coin ko dobara try kar sakte hain, kyuki infinite supply hai)

Total ways = `take_none + take_current`

**Algorithm (Tareeka):**

1.  Ek 2D integer `memo` table banao jiska size `(coins.length) x (amount + 1)` hoga. Sab values ko `-1` se initialize karo, jo uncomputed states ko indicate karega.
2.  Ek recursive function `dp(coin_idx, current_amount, coins, memo)` banao.
      * **Base Cases:**
          * Agar `current_amount == 0` hai, toh `1` return karo (0 amount banane ka ek tareeka mil gaya).
          * Agar `current_amount < 0` hai, toh `0` return karo (negative amount banana possible nahi).
          * Agar `coin_idx == coins.length` (saare coins check kar liye) aur `current_amount > 0` hai, toh `0` return karo (amount remaining hai par coins khatam ho gaye).
      * **Memoization Check:** Agar `memo[coin_idx][current_amount]` pehle se computed hai (`!= -1`), toh stored value return kar do.
      * **Recursive Step:**
          * `current_coin = coins[coin_idx]`
          * **`take_none = dp(coin_idx + 1, current_amount)`** (Current coin ko chhod do, next coin pe jao)
          * **`take_current = 0`**
          * Agar `current_amount >= current_coin` hai:
              * **`take_current = dp(coin_idx, current_amount - current_coin)`** (Current coin ko lo, aur same coin ko dobara try karo bache hue amount ke liye)
          * Store `take_none + take_current` ko `memo[coin_idx][current_amount]` mein aur return kar do.
3.  Initial call `dp(0, amount)` se start karo (pehli coin se, poore `amount` ke saath).

**Diagrammatic Explanation (Top-Down):**

Let's take `coins = [1, 2]`, `amount = 3`.
`dp(coin_idx, current_amount)`
`memo` table `(2 x 4)` size ka होगा, initialized with -1.

````mermaid
graph TD
    A[dp(0, 3)] --> B{coin=1};
    B --> C[Exclude: dp(1, 3)];
    B --> D[Include: dp(0, 2)];
    
    C --> E{coin=2};
    E --> F[Exclude: dp(2, 3)];
    F[Base: 0];
    E --> G[Include: dp(1, 1)];
    
    D --> H{coin=1};
    H --> I[Exclude: dp(1, 2)];
    H --> J[Include: dp(0, 1)];

    G --> K{coin=2};
    K --> L[Exclude: dp(2, 1)];
    L[Base: 0];
    K --> M[Include: dp(1, -1)];
    M[Base: 0];

    I --> N{coin=2};
    N --> O[Exclude: dp(2, 2)];
    O[Base: 0];
    N --> P[Include: dp(1, 0)];
    P[Base: 1];

    J --> Q{coin=1};
    Q --> R[Exclude: dp(1, 1)];
    Q --> S[Include: dp(0, 0)];
    S[Base: 1];

    %% Back-tracking and Summing up
    M -- 0 --> K;
    L -- 0 --> K;
    K -- 0+0=0 --> G;
    
    P -- 1 --> N;
    O -- 0 --> N;
    N -- 0+1=1 --> I;
    
    R -- memo[1][1] = 0 (Calculated later: no ways for 1 using coins from index 1 (only coin 2)) --> Q;
    Q -- 0+1=1 --> J;

    G -- 0 --> C;
    F -- 0 --> C;
    C -- 0+0=0 (Wrong, should be 1. It means sum 3 can be formed with coin 2 by 2+1 which is dp(1,1)) --> B;
    
    %% Let's re-trace values carefully:
    %% This diagram is for `count ways` not `min coins`.
    %% A path represents a way.
    %% dp(idx, amount) = dp(idx+1, amount) + dp(idx, amount - coins[idx])

    dp(2, any_amount > 0) = 0 (Base case: no more coins, amount not 0)
    dp(any_idx, 0) = 1 (Base case: 0 amount, one way)
    dp(any_idx, current_amount < 0) = 0 (Base case: invalid amount)

    1. Call `dp(0, 3)`
       `coin_idx = 0`, `coin = 1`
       `take_none = dp(1, 3)`
       `take_current = dp(0, 2)` (using 1 coin)
       
       * Call `dp(1, 3)`
         `coin_idx = 1`, `coin = 2`
         `take_none = dp(2, 3)` (Base: 0)
         `take_current = dp(1, 3 - 2) = dp(1, 1)` (using 2 coin)
         
         * Call `dp(1, 1)`
           `coin_idx = 1`, `coin = 2`
           `take_none = dp(2, 1)` (Base: 0)
           `take_current = dp(1, 1 - 2) = dp(1, -1)` (Base: 0)
           `dp(1, 1) = 0 + 0 = 0`
           Store `memo[1][1] = 0`. Return `0`.
         
         `dp(1, 3) = 0 + 0 = 0` (Means amount 3 can't be made using only coin 2s or 1 coin 2 with others after it. This branch isn't giving 1+1+1 way)
         Store `memo[1][3] = 0`. Return `0`.
       
       * Call `dp(0, 2)`
         `coin_idx = 0`, `coin = 1`
         `take_none = dp(1, 2)`
         `take_current = dp(0, 1)`
         
         * Call `dp(1, 2)`
           `coin_idx = 1`, `coin = 2`
           `take_none = dp(2, 2)` (Base: 0)
           `take_current = dp(1, 2 - 2) = dp(1, 0)` (Base: 1)
           `dp(1, 2) = 0 + 1 = 1` (Way: using one 2 coin)
           Store `memo[1][2] = 1`. Return `1`.
         
         * Call `dp(0, 1)`
           `coin_idx = 0`, `coin = 1`
           `take_none = dp(1, 1)` (Memoized: 0)
           `take_current = dp(0, 1 - 1) = dp(0, 0)` (Base: 1)
           `dp(0, 1) = 0 + 1 = 1` (Way: using one 1 coin)
           Store `memo[0][1] = 1`. Return `1`.

         `dp(0, 2) = 1 + 1 = 2` (Ways: 1+1, 2)
         Store `memo[0][2] = 2`. Return `2`.

    `dp(0, 3) = take_none + take_current = dp(1, 3) + dp(0, 2)`
             `= 0 + 2 = 2`.

Final Answer for `coins = [1, 2]`, `amount = 3` is `2`. Correct. (Combinations: `1+1+1` and `1+2`).

```mermaid
graph TD
    A[dp(0, 3) - Goal: Ways to make 3 using [1,2]]
    A -- Take 1 (current coin), remaining 2 --> B[1 + dp(0, 2) - Still use coin 1, then coin 2]
    A -- Don't take 1, use coins from idx 1 (only [2]) --> C[dp(1, 3) - Ways to make 3 using [2]]

    B --> D[dp(0, 2) - Ways to make 2 using [1,2]]
    D -- Take 1 (current coin), remaining 1 --> E[1 + dp(0, 1) - Still use coin 1, then coin 2]
    D -- Don't take 1, use coins from idx 1 (only [2]) --> F[dp(1, 2) - Ways to make 2 using [2]]

    E --> G[dp(0, 1) - Ways to make 1 using [1,2]]
    G -- Take 1, remaining 0 --> H[1 + dp(0, 0)] (Base: dp(0,0)=1)
    G -- Don't take 1 --> I[dp(1, 1) - Ways to make 1 using [2]]
    I -- Take 2, remaining -1 --> J[1 + dp(1, -1)] (Base: dp(...,-1)=0)
    I -- Don't take 2 --> K[dp(2, 1)] (Base: dp(last_idx, >0)=0)
    I --> L{Sum: 0+0=0} (Memo[1][1]=0)
    G --> M{Sum: 1+0=1} (Memo[0][1]=1)

    F --> N[dp(1, 2) - Ways to make 2 using [2]]
    N -- Take 2, remaining 0 --> O[1 + dp(1, 0)] (Base: dp(...,0)=1)
    N -- Don't take 2 --> P[dp(2, 2)] (Base: dp(last_idx, >0)=0)
    N --> Q{Sum: 1+0=1} (Memo[1][2]=1)

    D --> R{Sum: 1+1=2} (Memo[0][2]=2)

    C --> S[dp(1, 3) - Ways to make 3 using [2]]
    S -- Take 2, remaining 1 --> T[1 + dp(1, 1)] (Memo[1][1]=0)
    S -- Don't take 2 --> U[dp(2, 3)] (Base: dp(last_idx, >0)=0)
    S --> V{Sum: 0+0=0} (Memo[1][3]=0)

    A --> W{Sum: 2+0=2} (Final Answer: 2)

````

**Java Code (Top-Down):**

```java
import java.util.Arrays;

class SolutionCoinChangeII_TopDown {
    // memo[coin_idx][current_amount] stores the number of combinations.
    // Initialized with -1 to indicate uncomputed states.
    private int[][] memo;
    private int[] coins;

    /**
     * Coin Change II problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     *
     * @param amount Target amount.
     * @param coins Coins ke denominations ka array.
     * @return Combinations ki sankhya, ya 0 agar possible na ho.
     */
    public int change(int amount, int[] coins) {
        this.coins = coins;
        // memo table initialize karo. Size [coins.length][amount + 1].
        // -1 ka matlab ye state abhi tak calculate nahi hui hai.
        memo = new int[coins.length][amount + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Recursive function ko call karo pehli coin (index 0) se aur target amount ke saath.
        return dp(0, amount);
    }

    /**
     * Recursive helper function: computes number of combinations for a given amount
     * using coins from `coin_idx` onwards.
     *
     * @param coin_idx      Current coin ka index jisko consider kar rahe hain.
     * @param current_amount Remaining amount jisko banana hai.
     * @return Combinations ki sankhya.
     */
    private int dp(int coin_idx, int current_amount) {
        // Base case 1: Agar current_amount 0 ho gaya hai, toh humne ek tareeka dhundh liya hai.
        if (current_amount == 0) {
            return 1;
        }
        // Base case 2: Agar current_amount negative ho gaya hai, ya
        // saare coins check kar liye (coin_idx == coins.length) aur amount abhi bhi bacha hai.
        if (current_amount < 0 || coin_idx == coins.length) {
            return 0; // Invalid path ya no more coins left to make the amount.
        }

        // Memoization check: Agar ye state pehle hi compute ho chuki hai, toh stored result return karo.
        if (memo[coin_idx][current_amount] != -1) {
            return memo[coin_idx][current_amount];
        }

        // Option 1: Current coin (coins[coin_idx]) ko include mat karo.
        // Next coin pe jao, amount wahi rahega.
        int takeNone = dp(coin_idx + 1, current_amount);

        // Option 2: Current coin (coins[coin_idx]) ko include karo.
        // Agar current_amount current coin ke value se bada ya barabar hai.
        int takeCurrent = 0;
        if (current_amount >= coins[coin_idx]) {
            // Current coin ko lo, aur same coin ko dobara consider karo (kyunki infinite supply hai).
            // Baki bache amount ke liye combinations dhundo.
            takeCurrent = dp(coin_idx, current_amount - coins[coin_idx]);
        }

        // Dono options ke combinations ko add karo.
        // Result ko memo table mein store karo aur return karo.
        return memo[coin_idx][current_amount] = takeNone + takeCurrent;
    }

    public static void main(String[] args) {
        SolutionCoinChangeII_TopDown solver = new SolutionCoinChangeII_TopDown();

        System.out.println("\n--- 518. Coin Change II (Top-Down) ---");

        int[] coins1 = {1, 2, 5};
        int amount1 = 5;
        System.out.println("Coins: " + Arrays.toString(coins1) + ", Amount: " + amount1 + " -> Combinations: " + solver.change(amount1, coins1)); // Expected: 4

        int[] coins2 = {2};
        int amount2 = 3;
        System.out.println("Coins: " + Arrays.toString(coins2) + ", Amount: " + amount2 + " -> Combinations: " + solver.change(amount2, coins2)); // Expected: 0

        int[] coins3 = {10};
        int amount3 = 10;
        System.out.println("Coins: " + Arrays.toString(coins3) + ", Amount: " + amount3 + " -> Combinations: " + solver.change(amount3, coins3)); // Expected: 1
    }
}
```
<img width="1312" alt="image" src="https://github.com/user-attachments/assets/9ef531d4-b66f-4ec3-9330-795b7339deb6" />
Chalo, `139. Word Break` problem ko detail mein, Hinglish mein, aur dono DP approaches ke saath samajhte hain. Top-Down ke liye diagram bhi hoga aur Java code bhi.

-----

## 139\. Word Break Problem Kya Hai?

**Problem Statement (Hinglish):**
Imagine karo aapke paas ek string `s` hai (jaise "leetcode") aur ek **dictionary** hai (`wordDict`), jismein kuch words hain (jaise `["leet", "code"]`).

Aapko yeh pata karna hai ki kya `s` string ko dictionary ke words ko use karke **space-separated sequence** mein toda ja sakta hai. Matlab, kya `s` ko dictionary ke ek ya ek se zyada words ke sequence mein likha ja sakta hai?

Ek important point ye hai ki dictionary ke words ko aap **kitni bhi baar (reuse)** kar sakte ho.

**Examples:**

1.  `s = "leetcode"`, `wordDict = ["leet", "code"]`

      * Answer: `true` (kyunki "leetcode" ko "leet code" mein toda ja sakta hai)

2.  `s = "applepenapple"`, `wordDict = ["apple", "pen"]`

      * Answer: `true` (kyunki "applepenapple" ko "apple pen apple" mein toda ja sakta hai. "apple" ko do baar reuse kiya gaya hai)

3.  `s = "catsandog"`, `wordDict = ["cats", "dog", "sand", "and", "cat"]`

      * Answer: `false` (iss string ko dictionary words se nahi toda ja sakta)

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem ek classic Dynamic Programming problem hai, jo **String Segmentation** ya **Knapsack-like** problem ka variation hai. Humein basically ek string ko sub-problems mein todna hai aur check karna hai ki kya har sub-problem ka solution (yani, sub-string dictionary mein hai ya usko tode ja sakta hai) milta hai.

Har index tak ki string ko `true` mark karna hai agar usko tode ja sake.

-----

## Dynamic Programming Approach

**DP State:**

Hum ek 1D boolean DP array use karenge:
`dp[i]` ka matlab hoga: kya `s` string ke **पहले `i` characters** (`s.substring(0, i)`) ko dictionary ke words se banaya ja sakta hai.

**Recurrence Relation:**

`dp[i]` ko calculate karne ke liye, hum `i` ke har possible **left breakpoint** `j` ko check karenge (`0 <= j < i`).
Agar do conditions satisfy hoti hain:

1.  `dp[j]` `true` ho (matlab, `s` ke pehle `j` characters ko already banaya ja sakta hai).
2.  `s.substring(j, i)` (yani, `j` se `i-1` tak ki substring) dictionary mein present ho.

Agar ye dono conditions `true` hain, toh `dp[i]` ko `true` set kar do aur aage check karne ki zaroorat nahi hai.

`dp[i] = true` if `exists j` such that `(dp[j] == true AND s.substring(j, i) is in wordDict)`

**Base Case:**

  * `dp[0] = true`: Ek empty string (0 characters) ko "banaya" ja sakta hai, jiske liye koi word nahi chahiye. Ye hamare recursion/iteration ke liye starting point banata hai.

-----

### Approach 1: Bottom-Up (Tabulation)

Is approach mein hum DP table ko **chhote substrings** se start karke **bade substrings** ki taraf fill karte hain.

**Algorithm (Tareeka):**

1.  String `s` ki length `N` nikaalo.
2.  Ek `(N + 1)` size ka boolean `dp` array banao.
3.  `dp[0]` ko `true` se initialize karo (base case). Baki sab `false` se initialized rahenge.
4.  `i` loop ko `1` se `N` tak chalao (har possible end index of substring ke liye).
5.  Har `i` ke liye, `j` loop ko `0` se `i-1` tak chalao (har possible start index/breakpoint of substring ke liye).
6.  `j` loop ke andar, do conditions check karo:
      * `if (dp[j] == true)`: Agar `s` ke pehle `j` characters ko banaya ja sakta hai.
      * `&& wordDict.contains(s.substring(j, i))`: Aur `j` se `i-1` tak ki substring dictionary mein hai.
7.  Agar dono conditions `true` hain, toh `dp[i] = true` set kar do aur inner `j` loop ko `break` kar do, kyunki `dp[i]` ke liye ek valid segmentation mil gaya hai.
8.  Akhir mein, `dp[N]` mein hamara answer hoga.

**Example ke saath Bottom-Up:**

`s = "leetcode"`, `wordDict = ["leet", "code"]`
`N = 8`
`dp` array size `(8 + 1) = 9`. `dp[0]=true`, baki sab `false`.
`dp = [T, F, F, F, F, F, F, F, F]`

**Outer loop `i` from 1 to 8:**

  * **`i = 1`**: (substring "l")
      * `j = 0`: `dp[0]` is T. `s.substring(0, 1)` is "l". "l" dictionary mein nahi hai. `dp[1]` remains F.
  * **`i = 2`**: (substring "le")
      * `j = 0`: `dp[0]` is T. `s.substring(0, 2)` is "le". "le" dictionary mein nahi hai.
      * `j = 1`: `dp[1]` is F. Skip.
        `dp[2]` remains F.
  * **`i = 3`**: (substring "lee")
      * ... `dp[3]` remains F.
  * **`i = 4`**: (substring "leet")
      * `j = 0`: `dp[0]` is T. `s.substring(0, 4)` is "leet". "leet" dictionary mein hai\!
          * `dp[4] = true`. **Break** inner loop.
            `dp = [T, F, F, F, T, F, F, F, F]`
  * **`i = 5`**: (substring "leetc")
      * `j = 0`: `dp[0]` is T. `s.substring(0, 5)` is "leetc". Not in dict.
      * `j = 1`: `dp[1]` is F. Skip.
      * `j = 2`: `dp[2]` is F. Skip.
      * `j = 3`: `dp[3]` is F. Skip.
      * `j = 4`: `dp[4]` is T. `s.substring(4, 5)` is "c". Not in dict.
        `dp[5]` remains F.
  * **`i = 6`**: (substring "leetco")
      * ... `dp[6]` remains F.
  * **`i = 7`**: (substring "leetcod")
      * ... `dp[7]` remains F.
  * **`i = 8`**: (substring "leetcode")
      * `j = 0`: `dp[0]` is T. `s.substring(0, 8)` is "leetcode". Not in dict.
      * `j = 1` to `j = 3`: `dp[j]` is F. Skip.
      * `j = 4`: `dp[4]` is T. `s.substring(4, 8)` is "code". "code" dictionary mein hai\!
          * `dp[8] = true`. **Break** inner loop.
            `dp = [T, F, F, F, T, F, F, F, T]`

`dp[8] = true`. Correct\!

**Java Code (Bottom-Up):**

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SolutionWordBreak_BottomUp {
    /**
     * Word Break problem ko Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Check karta hai ki string 's' ko dictionary words se segment kiya ja sakta hai ya nahi.
     *
     * @param s String jisko segment karna hai.
     * @param wordDict Dictionary words ki list.
     * @return True agar segment kiya ja sakta hai, false otherwise.
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // Dictionary words ko fast lookup ke liye HashSet mein store karo.
        Set<String> wordSet = new HashSet<>(wordDict);

        int n = s.length();
        // dp[i] ka matlab hai kya 's' ke pehle 'i' characters (s.substring(0, i))
        // ko dictionary words se banaya ja sakta hai.
        boolean[] dp = new boolean[n + 1];

        // Base case: dp[0] true hai kyunki empty string ko banaya ja sakta hai (0 words se).
        dp[0] = true;

        // Outer loop: Har possible end index 'i' tak ki substring ko check karo.
        for (int i = 1; i <= n; i++) {
            // Inner loop: Har possible start index/breakpoint 'j' ko check karo.
            // j se i tak ki substring ko check karna hai.
            for (int j = 0; j < i; j++) {
                // Condition 1: dp[j] true hona chahiye (j-th index tak ki string valid ho).
                // Condition 2: s.substring(j, i) dictionary mein hona chahiye.
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true; // Agar dono true hain, toh 'i' tak ki string valid hai.
                    break;       // Ek tareeka mil gaya, toh aage check karne ki zaroorat nahi.
                }
            }
        }

        // Final answer dp[n] mein hoga.
        return dp[n];
    }

    public static void main(String[] args) {
        SolutionWordBreak_BottomUp solver = new SolutionWordBreak_BottomUp();

        System.out.println("--- 139. Word Break (Bottom-Up) ---");

        // Example 1
        String s1 = "leetcode";
        List<String> wordDict1 = Arrays.asList("leet", "code");
        System.out.println("String: \"" + s1 + "\", Dictionary: " + wordDict1 + " -> Can Break: " + solver.wordBreak(s1, wordDict1)); // Expected: true

        // Example 2
        String s2 = "applepenapple";
        List<String> wordDict2 = Arrays.asList("apple", "pen");
        System.out.println("String: \"" + s2 + "\", Dictionary: " + wordDict2 + " -> Can Break: " + solver.wordBreak(s2, wordDict2)); // Expected: true

        // Example 3
        String s3 = "catsandog";
        List<String> wordDict3 = Arrays.asList("cats", "dog", "sand", "and", "cat");
        System.out.println("String: \"" + s3 + "\", Dictionary: " + wordDict3 + " -> Can Break: " + solver.wordBreak(s3, wordDict3)); // Expected: false

        // Example 4 (Edge case)
        String s4 = "a";
        List<String> wordDict4 = Arrays.asList("b");
        System.out.println("String: \"" + s4 + "\", Dictionary: " + wordDict4 + " -> Can Break: " + solver.wordBreak(s4, wordDict4)); // Expected: false

        // Example 5 (Edge case)
        String s5 = "a";
        List<String> wordDict5 = Arrays.asList("a");
        System.out.println("String: \"" + s5 + "\", Dictionary: " + wordDict5 + " -> Can Break: " + solver.wordBreak(s5, wordDict5)); // Expected: true
    }
}
```

-----

### Approach 2: Top-Down (Memoization)

Top-Down approach mein hum recursion use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 1D array) use karte hain, taaki same calculations baar-baar na karni pade.

**DP State:**

`memo[start_idx]` ka matlab hoga: kya `s` string ke **`start_idx` se lekar end tak** ki substring को dictionary words से banaya ja sakta hai.

**Recurrence Relation:**

`dp(start_idx)` function check karega ki `s.substring(start_idx)` ko segment kiya ja sakta hai ya nahi.
Ye `start_idx` se `s.length()` tak iterate karega. Har `i` par, `s.substring(start_idx, i)` ko ek possible word candidate maanega.
Agar `s.substring(start_idx, i)` dictionary mein hai `AND` baaki bachi hui string (`s.substring(i)`) ko bhi segment kiya ja sakta hai (`dp(i)` `true` return karta hai), toh `dp(start_idx)` `true` ho jayega.

`dp(start_idx) = true` if `exists i` such that `(s.substring(start_idx, i) is in wordDict AND dp(i) == true)`

**Base Case:**

  * `start_idx == s.length()`: Agar `start_idx` string ki length tak pahunch gaya hai, iska matlab empty string hai, jise segment kiya ja sakta hai. Toh `true` return karo.
  * `memo` array ko `-1` se initialize karo (`0` for false, `1` for true, `-1` for uncomputed).

**Algorithm (Tareeka):**

1.  String `s` ki length `N` nikaalo.
2.  Dictionary words ko fast lookup ke liye `HashSet` mein store karo.
3.  Ek `(N + 1)` size ka integer `memo` array banao. Sab values ko `-1` se initialize karo.
4.  Ek recursive function `dp(start_idx)` banao:
      * **Base Case 1:** Agar `start_idx == N` hai, toh `true` return karo (empty string ban sakti hai).
      * **Memoization Check:** Agar `memo[start_idx]` already calculated hai (`!= -1`), toh stored value return karo (convert `0` to `false`, `1` to `true`).
      * **Recursive Step:**
          * `i` loop ko `start_idx + 1` se `N` tak chalao (har possible end index `i` ke liye, jisse substring `s.substring(start_idx, i)` ban sake).
          * `current_word = s.substring(start_idx, i)` nikaalo.
          * Agar `current_word` `wordSet` mein hai:
              * `if (dp(i) == true)`: Agar `i` se aage ki string ko bhi segment kiya ja sakta hai, toh is path se `s.substring(start_idx)` ko segment kiya ja sakta hai.
                  * `memo[start_idx] = 1` (true) set karo aur `true` return kar do.
          * Agar `for` loop khatam ho gaya aur koi valid segmentation nahi mili:
              * `memo[start_idx] = 0` (false) set karo aur `false` return kar do.
5.  Initial call `dp(0)` se start karo (poori string `s` ko check karne ke liye).

**Diagrammatic Explanation (Top-Down):**

Let's take `s = "applepenapple"`, `wordDict = ["apple", "pen"]`
`N = 13`
`dp(start_idx)` function. `memo` array of size `14` initialized to `-1`.

```mermaid
graph TD
    A[dp(0) - "applepenapple"]
    A -- "apple" (idx 0 to 5) in dict --> B[dp(5) - "penapple"]
    A -- "applepen" (idx 0 to 8) not in dict --> C[...]
    
    B -- "pen" (idx 5 to 8) in dict --> D[dp(8) - "apple"]
    B -- "penapple" (idx 5 to 13) not in dict --> E[...]

    D -- "apple" (idx 8 to 13) in dict --> F[dp(13) - ""]
    F[Base Case: return true]

    F -- true --> D;
    D -- true (memo[8]=1) --> B;
    B -- true (memo[5]=1) --> A;
    A -- true (memo[0]=1) --> Z[Return true]

    style A fill:#D0F0C0,stroke:#333,stroke-width:2px;
    style B fill:#D0F0C0,stroke:#333,stroke-width:2px;
    style D fill:#D0F0C0,stroke:#333,stroke-width:2px;
    style F fill:#ADD8E6,stroke:#333,stroke-width:2px;
```

**Trace `s = "applepenapple"`, `wordDict = ["apple", "pen"]`**

1.  **`dp(0)`**: (for "applepenapple")
      * Try substring `s.substring(0, 5)` = "apple". Is in `wordDict`.
          * Now call `dp(5)`: (for "penapple")
              * Try substring `s.substring(5, 8)` = "pen". Is in `wordDict`.
                  * Now call `dp(8)`: (for "apple")
                      * Try substring `s.substring(8, 13)` = "apple". Is in `wordDict`.
                          * Now call `dp(13)`: (for "")
                              * **Base Case:** `start_idx == N` (13 == 13). Returns `true`.
                      * Since `dp(13)` returned `true`, `dp(8)` gets `true`.
                      * Store `memo[8] = 1`. Returns `true`.
              * Since `dp(8)` returned `true`, `dp(5)` gets `true`.
              * Store `memo[5] = 1`. Returns `true`.
      * Since `dp(5)` returned `true`, `dp(0)` gets `true`.
      * Store `memo[0] = 1`. Returns `true`.

Final Answer: `true`. Correct.

**Java Code (Top-Down):**

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SolutionWordBreak_TopDown {
    // memo[start_idx] stores whether substring s.substring(start_idx) can be segmented.
    // -1: uncomputed, 0: false, 1: true
    private int[] memo;
    private Set<String> wordSet;
    private String s;
    private int n;

    /**
     * Word Break problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     *
     * @param s String jisko segment karna hai.
     * @param wordDict Dictionary words ki list.
     * @return True agar segment kiya ja sakta hai, false otherwise.
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        this.s = s;
        this.n = s.length();
        this.wordSet = new HashSet<>(wordDict);

        // Memoization table initialize karo. Size (n + 1).
        // -1 ka matlab ye state abhi tak calculate nahi hui hai.
        memo = new int[n + 1];
        Arrays.fill(memo, -1);

        // Recursive function ko call karo start index 0 se (poori string ke liye).
        return dp(0);
    }

    /**
     * Recursive helper function: checks if `s.substring(start_idx)` can be segmented.
     *
     * @param start_idx The starting index of the substring to check.
     * @return True if the substring can be segmented, false otherwise.
     */
    private boolean dp(int start_idx) {
        // Base case 1: Agar start_idx string ki length tak pahunch gaya hai,
        // matlab empty string hai, jise successfully segment kiya ja sakta hai.
        if (start_idx == n) {
            return true;
        }

        // Memoization check: Agar ye state pehle hi compute ho chuki hai.
        if (memo[start_idx] != -1) {
            return memo[start_idx] == 1; // Convert stored int to boolean
        }

        // Iterate karo har possible end_idx 'i' tak current substring (s.substring(start_idx, i)) ke liye.
        for (int i = start_idx + 1; i <= n; i++) {
            String current_word = s.substring(start_idx, i);

            // Agar current_word dictionary mein hai
            if (wordSet.contains(current_word)) {
                // Aur baki bachi hui string (i se end tak) ko bhi segment kiya ja sakta hai.
                if (dp(i)) { // Recursive call
                    memo[start_idx] = 1; // Store true
                    return true;
                }
            }
        }

        // Agar loop khatam ho gaya aur koi valid segmentation nahi mili.
        memo[start_idx] = 0; // Store false
        return false;
    }

    public static void main(String[] args) {
        SolutionWordBreak_TopDown solver = new SolutionWordBreak_TopDown();

        System.out.println("\n--- 139. Word Break (Top-Down) ---");

        // Example 1
        String s1 = "leetcode";
        List<String> wordDict1 = Arrays.asList("leet", "code");
        System.out.println("String: \"" + s1 + "\", Dictionary: " + wordDict1 + " -> Can Break: " + solver.wordBreak(s1, wordDict1)); // Expected: true

        // Example 2
        String s2 = "applepenapple";
        List<String> wordDict2 = Arrays.asList("apple", "pen");
        System.out.println("String: \"" + s2 + "\", Dictionary: " + wordDict2 + " -> Can Break: " + solver.wordBreak(s2, wordDict2)); // Expected: true

        // Example 3
        String s3 = "catsandog";
        List<String> wordDict3 = Arrays.asList("cats", "dog", "sand", "and", "cat");
        System.out.println("String: \"" + s3 + "\", Dictionary: " + wordDict3 + " -> Can Break: " + solver.wordBreak(s3, wordDict3)); // Expected: false

        // Example 4 (Edge case)
        String s4 = "a";
        List<String> wordDict4 = Arrays.asList("b");
        System.out.println("String: \"" + s4 + "\", Dictionary: " + wordDict4 + " -> Can Break: " + solver.wordBreak(s4, wordDict4)); // Expected: false

        // Example 5 (Edge case)
        String s5 = "a";
        List<String> wordDict5 = Arrays.asList("a");
        System.out.println("String: \"" + s5 + "\", Dictionary: " + wordDict5 + " -> Can Break: " + solver.wordBreak(s5, wordDict5)); // Expected: true
    }
}
```
<img width="1312" alt="image" src="https://github.com/user-attachments/assets/88f8ba98-19ae-4c31-b7f2-e9d66476b2fc" />
Chalo, `198. House Robber` problem ko detail mein, Hinglish mein, aur dono DP approaches ke saath samajhte hain. Top-Down ke liye diagram bhi hoga aur Java code bhi.

-----

## 198\. House Robber Problem Kya Hai?

**Problem Statement (Hinglish):**
Imagine karo aap ek professional chor ho aur aap ek gali mein ghar lootne ki planning kar rahe ho. Har ghar mein kuch paise hain, jo ek integer array `nums` mein diye gaye hain. For example, `nums = [1, 2, 3, 1]` ka matlab hai pehle ghar mein 1 unit, doosre mein 2 units, teesre mein 3 units, aur chauthe mein 1 unit paisa hai.

Lekin ek bahut important rule hai: **agar aap do saath wale gharo ko lootoge, toh police ko automatic alarm chala jayega\!** (Adjacent houses have security systems connected).

Aapko batana hai ki **aaj raat aap zyada se zyada kitne paise loot sakte ho**, bina police ko alert kiye. Matlab, aapko aise ghar select karne hain jahan se aap maximum paisa kama sako, lekin koi bhi do selected ghar bagal-bagal wale (adjacent) na hon.

**Examples:**

1.  `nums = [1, 2, 3, 1]`

      * Answer: `4`
          * Agar aap ghar 1 (₹1) aur ghar 3 (₹3) lootoge: `1 + 3 = 4`. Ye valid hai kyunki ye adjacent nahi hain.
          * Agar aap ghar 2 (₹2) lootoge: `2`. Ye bhi valid hai.
          * Maximum 4 hai.

2.  `nums = [2, 7, 9, 3, 1]`

      * Answer: `12`
          * Agar aap ghar 1 (₹2), ghar 3 (₹9), aur ghar 5 (₹1) lootoge: `2 + 9 + 1 = 12`. Ye valid hai.

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem ek classic **Dynamic Programming** problem hai. Isse **"Non-Adjacent Selection"** ya **"Maximum Sum of Non-Adjacent Elements"** ka problem bhi keh sakte hain.

Har ghar ke liye, hamare paas do options hain:

1.  **Ghar ko lootna (Include):** Agar hum is ghar ko loot'te hain, toh hum iske theek bagal wala ghar nahi loot sakte.
2.  **Ghar ko nahi lootna (Exclude):** Agar hum is ghar ko nahi loot'te hain, toh hum iske theek bagal wale ghar ko loot sakte hain ya nahi bhi, apni choice hai.

DP state ko is tarah se define karenge ki har index tak ka maximum paisa kya hai.

-----

## Dynamic Programming Approach

**DP State:**

Hum ek 1D DP array use karenge:
`dp[i]` ka matlab hoga: pehle `i` gharo tak (yaani, `nums[0]` se `nums[i-1]` tak) ko consider karte hue, **maximum kitne paise** loote ja sakte hain.

**Recurrence Relation:**

`dp[i]` ko calculate karne ke liye, jab hum `nums[i-1]` (current ghar) par honge:

1.  **Agar hum `nums[i-1]` (current ghar) ko loot'te hain:**
    Toh hum `nums[i-2]` (previous ghar) ko nahi loot sakte the. Iska matlab hai ki humein `nums[i-3]` tak ka maximum paisa (`dp[i-2]`) aur current ghar ka paisa (`nums[i-1]`) add karna hoga.
    Value: `nums[i-1] + dp[i-2]`

2.  **Agar hum `nums[i-1]` (current ghar) ko nahi loot'te hain:**
    Toh hum `nums[i-2]` (previous ghar) ko loot sakte the. Iska matlab hai ki `dp[i-1]` mein jo value thi wahi is case mein bhi aayegi.
    Value: `dp[i-1]`

`dp[i]` in dono options mein se **maximum** hoga.

`dp[i] = max(nums[i-1] + dp[i-2], dp[i-1])`

**Base Cases:**

  * `dp[0] = 0`: 0 gharon se 0 paise loot sakte hain.
  * `dp[1] = nums[0]`: 1 ghar hai, toh bas pehla ghar loot sakte hain (jo bhi usme paisa hai).

-----

### Approach 1: Bottom-Up (Tabulation)

Is approach mein hum DP table ko **chhote subproblems** se start karke **bade subproblems** ki taraf fill karte hain.

**Algorithm (Tareeka):**

1.  String `nums` ki length `N` nikaalo.
2.  **Edge Cases Handle Karo:**
      * Agar `N == 0` hai, toh `0` return karo.
      * Agar `N == 1` hai, toh `nums[0]` return karo.
3.  Ek `(N + 1)` size ka integer `dp` array banao.
4.  Base cases initialize karo:
      * `dp[0] = 0`
      * `dp[1] = nums[0]`
5.  `i` loop ko `2` se `N` tak chalao (har possible ghar index ke liye, `dp` index `i` correspond karta hai `nums[i-1]` ghar ko).
6.  `dp[i] = Math.max(nums[i-1] + dp[i-2], dp[i-1]);`
7.  Akhir mein, `dp[N]` mein hamara answer hoga.

**Example ke saath Bottom-Up:**

`nums = [2, 7, 9, 3, 1]`
`N = 5`
`dp` array size `(5 + 1) = 6`.
`dp = [0, 0, 0, 0, 0, 0]`

Base Cases:
`dp[0] = 0`
`dp[1] = nums[0] = 2`
`dp = [0, 2, 0, 0, 0, 0]`

**Loop `i` from 2 to 5:**

  * **`i = 2`**: (Consider `nums[1]` which is `7`)

      * `dp[2] = Math.max(nums[2-1] + dp[2-2], dp[2-1])`
      * `dp[2] = Math.max(nums[1] + dp[0], dp[1])`
      * `dp[2] = Math.max(7 + 0, 2) = Math.max(7, 2) = 7`
        `dp = [0, 2, 7, 0, 0, 0]`

  * **`i = 3`**: (Consider `nums[2]` which is `9`)

      * `dp[3] = Math.max(nums[3-1] + dp[3-2], dp[3-1])`
      * `dp[3] = Math.max(nums[2] + dp[1], dp[2])`
      * `dp[3] = Math.max(9 + 2, 7) = Math.max(11, 7) = 11`
        `dp = [0, 2, 7, 11, 0, 0]`

  * **`i = 4`**: (Consider `nums[3]` which is `3`)

      * `dp[4] = Math.max(nums[4-1] + dp[4-2], dp[4-1])`
      * `dp[4] = Math.max(nums[3] + dp[2], dp[3])`
      * `dp[4] = Math.max(3 + 7, 11) = Math.max(10, 11) = 11`
        `dp = [0, 2, 7, 11, 11, 0]`

  * **`i = 5`**: (Consider `nums[4]` which is `1`)

      * `dp[5] = Math.max(nums[5-1] + dp[5-2], dp[5-1])`
      * `dp[5] = Math.max(nums[4] + dp[3], dp[4])`
      * `dp[5] = Math.max(1 + 11, 11) = Math.max(12, 11) = 12`
        `dp = [0, 2, 7, 11, 11, 12]`

`dp[5] = 12`. Correct\!

**Java Code (Bottom-Up):**

```java
import java.util.Arrays;

class SolutionHouseRobber_BottomUp {
    /**
     * House Robber problem ko Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Maximum paisa dhundhta hai bina adjacent gharon ko loote.
     *
     * @param nums Har ghar mein paise ki sankhya.
     * @return Loote gaye paison ki adhiktam sankhya.
     */
    public int rob(int[] nums) {
        int n = nums.length;

        // Edge Cases:
        // Agar koi ghar nahi hai lootne ko.
        if (n == 0) {
            return 0;
        }
        // Agar sirf ek hi ghar hai, toh bas usi ko loot lo.
        if (n == 1) {
            return nums[0];
        }

        // dp[i] = i gharon tak (index i-1) mein loota gaya maximum paisa.
        // Size n+1, taaki dp[n] final answer ho.
        int[] dp = new int[n + 1];

        // Base Cases:
        // dp[0] = 0 (0 gharon se 0 paisa)
        dp[0] = 0;
        // dp[1] = nums[0] (1st ghar tak: ya toh usko loot lo)
        dp[1] = nums[0];

        // i loop 2 se n tak chalega.
        // dp[i] calculate karega nums[i-1] ghar ko consider karte hue.
        for (int i = 2; i <= n; i++) {
            // Option 1: Current ghar (nums[i-1]) ko loot lo.
            // Toh pichhle se pichhla ghar (nums[i-3]) tak ka paisa lena hoga.
            // (i.e., dp[i-2] + nums[i-1])
            int robCurrent = dp[i - 2] + nums[i - 1];

            // Option 2: Current ghar (nums[i-1]) ko mat loot o.
            // Toh pichhle ghar (nums[i-2]) tak ka maximum paisa lena hoga.
            // (i.e., dp[i-1])
            int skipCurrent = dp[i - 1];

            // Dono options mein se jo maximum hai, woh dp[i] hoga.
            dp[i] = Math.max(robCurrent, skipCurrent);
        }

        // Final answer dp[n] mein hoga.
        return dp[n];
    }

    public static void main(String[] args) {
        SolutionHouseRobber_BottomUp solver = new SolutionHouseRobber_BottomUp();

        System.out.println("--- 198. House Robber (Bottom-Up) ---");

        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Nums: " + Arrays.toString(nums1) + " -> Max Rob: " + solver.rob(nums1)); // Expected: 4

        int[] nums2 = {2, 7, 9, 3, 1};
        System.out.println("Nums: " + Arrays.toString(nums2) + " -> Max Rob: " + solver.rob(nums2)); // Expected: 12

        int[] nums3 = {0};
        System.out.println("Nums: " + Arrays.toString(nums3) + " -> Max Rob: " + solver.rob(nums3)); // Expected: 0

        int[] nums4 = {2, 1};
        System.out.println("Nums: " + Arrays.toString(nums4) + " -> Max Rob: " + solver.rob(nums4)); // Expected: 2 (Rob house 0, get 2. Rob house 1, get 1. Max is 2.)
    }
}
```

-----

### Space Optimization (Bottom-Up)

Aap dekh sakte hain ki `dp[i]` ko calculate karne ke liye, hamein sirf `dp[i-1]` aur `dp[i-2]` ki zaroorat pad rahi hai. Iska matlab hai ki hum poore `dp` array ko store karne ki bajaye, sirf do variables (`prev1` aur `prev2`) ko store kar sakte hain.

**Algorithm (Space Optimized):**

1.  Edge Cases (0 ya 1 ghar) same rahenge.
2.  `prev2 = 0` (represents `dp[0]`)
3.  `prev1 = nums[0]` (represents `dp[1]`)
4.  `curr_max = 0`
5.  `i` loop ko `2` se `N` tak chalao (har ghar ke liye).
      * `curr_max = Math.max(nums[i-1] + prev2, prev1)`
      * `prev2 = prev1`
      * `prev1 = curr_max`
6.  Akhir mein, `prev1` mein hamara answer hoga.

**Java Code (Space Optimized Bottom-Up):**

```java
import java.util.Arrays;

class SolutionHouseRobber_SpaceOptimized {
    /**
     * House Robber problem ko Space Optimized Bottom-Up (Tabulation) Dynamic Programming se solve karta hai.
     * Only uses O(1) extra space.
     *
     * @param nums Har ghar mein paise ki sankhya.
     * @return Loote gaye paison ki adhiktam sankhya.
     */
    public int rob(int[] nums) {
        int n = nums.length;

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }

        // prev2 represents dp[i-2] (maximum amount robbed up to two houses before current)
        // Initially, 0 houses before current (index 0). So, dp[0] = 0.
        int prev2 = 0;

        // prev1 represents dp[i-1] (maximum amount robbed up to one house before current)
        // Initially, 1 house before current (index 0). So, dp[1] = nums[0].
        int prev1 = nums[0];

        // 'i' loop 2 se n tak chalega. current house nums[i-1] hoga.
        // current_max represent karega dp[i].
        for (int i = 2; i <= n; i++) {
            // Option 1: Current ghar (nums[i-1]) ko loot lo.
            // Previous two houses tak ka paisa + current ghar ka paisa.
            int robCurrent = nums[i - 1] + prev2;

            // Option 2: Current ghar (nums[i-1]) ko mat loot o.
            // Previous house tak ka maximum paisa.
            int skipCurrent = prev1;

            // Dono options mein se jo maximum hai, woh current_max hoga.
            int current_max = Math.max(robCurrent, skipCurrent);

            // Update prev2 aur prev1 agle iteration ke liye.
            // prev2 ban jayega old prev1.
            prev2 = prev1;
            // prev1 ban jayega current_max (jo ab dp[i] hai).
            prev1 = current_max;
        }

        // Akhir mein, prev1 mein hamara answer hoga (jo dp[n] ko represent karta hai).
        return prev1;
    }
}
```

-----

### Approach 2: Top-Down (Memoization)

Top-Down approach mein hum recursion use karte hain aur results ko store karne ke liye ek **memoization table** (usually ek 1D array) use karte hain, taaki same calculations baar-baar na karni pade.

**DP State:**

`memo[idx]` ka matlab hoga: `idx` index se lekar `nums` array ke end tak ke gharo mein se, **maximum kitne paise** loote ja sakte hain.

**Recurrence Relation:**

`dp(idx)`:

  * **Option 1: Current ghar `nums[idx]` ko loot lo:**
    Toh aap `nums[idx+1]` ko nahi loot sakte. Aapko `nums[idx]` ka paisa aur `idx+2` se aage ka maximum paisa (`dp(idx+2)`) add karna hoga.
    Value: `nums[idx] + dp(idx + 2)`
  * **Option 2: Current ghar `nums[idx]` ko mat loot o:**
    Toh aap `idx+1` se aage ka maximum paisa le sakte ho (`dp(idx+1)`).
    Value: `dp(idx + 1)`

Total maximum = `max(rob_current, skip_current)`

**Base Cases:**

  * `idx >= N` (where `N` is `nums.length`): Agar index array ki bounds se bahar nikal gaya, matlab koi ghar nahi hai lootne ko, toh `0` return karo.
  * `memo` array ko `-1` se initialize karo (`-1` for uncomputed).

**Algorithm (Tareeka):**

1.  String `nums` ki length `N` nikaalo.
2.  Ek `(N)` size ka integer `memo` array banao. Sab values ko `-1` se initialize karo.
3.  Ek recursive function `dp(idx)` banao:
      * **Base Case 1:** Agar `idx >= N` hai, toh `0` return karo.
      * **Memoization Check:** Agar `memo[idx]` already calculated hai (`!= -1`), toh stored value return karo.
      * **Recursive Step:**
          * `rob_current = nums[idx] + dp(idx + 2)` (Current ghar lo, aur do ghar aage se lootna shuru karo)
          * `skip_current = dp(idx + 1)` (Current ghar mat lo, aur theek agle ghar se lootna shuru karo)
          * Store `Math.max(rob_current, skip_current)` ko `memo[idx]` mein aur return kar do.
4.  Initial call `dp(0)` se start karo (pehli ghar se lootna shuru karne ke liye).

**Diagrammatic Explanation (Top-Down):**

Let's take `nums = [2, 7, 9, 3, 1]`
`N = 5`
`dp(idx)` function. `memo` array of size `5` initialized to `-1`.

````mermaid
graph TD
    A[dp(0) - nums[0]=2]
    A -- Take 2 --> B[2 + dp(2) - nums[2]=9]
    A -- Skip 2 --> C[dp(1) - nums[1]=7]
    
    B -- Take 9 --> D[9 + dp(4) - nums[4]=1]
    B -- Skip 9 --> E[dp(3) - nums[3]=3]

    C -- Take 7 --> F[7 + dp(3) - nums[3]=3] (Memoized call to dp(3))
    C -- Skip 7 --> G[dp(2) - nums[2]=9] (Memoized call to dp(2))

    D -- Take 1 --> H[1 + dp(6)] (Base: dp(>=N)=0)
    D -- Skip 1 --> I[dp(5)] (Base: dp(>=N)=0)
    D -- Result: 1+0=1 --> J{memo[4]=1, return 1}

    H[Base Case: 0]
    I[Base Case: 0]

    E -- Take 3 --> K[3 + dp(5)] (Base: dp(>=N)=0)
    E -- Skip 3 --> L[dp(4)] (Memoized call to dp(4))
    K[Base Case: 0]
    L -- memo[4]=1 --> J
    E -- Result: max(3+0, 1) = 3 --> M{memo[3]=3, return 3}

    F -- memo[3]=3 --> M
    G -- memo[2]=11 (Need to calculate dp(2) first) --> Q{memo[2]=11, return 11}

    B -- Result: max(9+1, 3) = 10 --> P{memo[2]=10, return 10}  (This should be 11 or 12 later. Let's trace carefully.)

    %% Corrected Trace of dp(idx) logic:
    %% dp(idx) = max(nums[idx] + dp(idx+2), dp(idx+1))

    1. `dp(0)` for `[2, 7, 9, 3, 1]`
       `rob_0 = nums[0] + dp(2) = 2 + dp(2)`
       `skip_0 = dp(1)`

       2. `dp(2)` for `[9, 3, 1]`
          `rob_2 = nums[2] + dp(4) = 9 + dp(4)`
          `skip_2 = dp(3)`

          3. `dp(4)` for `[1]`
             `rob_4 = nums[4] + dp(6) = 1 + 0 = 1` (Base case: `dp(6)` is 0)
             `skip_4 = dp(5) = 0` (Base case: `dp(5)` is 0)
             `dp(4) = max(1, 0) = 1`. Store `memo[4]=1`. Return `1`.

          3. `dp(3)` for `[3, 1]`
             `rob_3 = nums[3] + dp(5) = 3 + 0 = 3` (Base case: `dp(5)` is 0)
             `skip_3 = dp(4) = 1` (Memoized from above)
             `dp(3) = max(3, 1) = 3`. Store `memo[3]=3`. Return `3`.
          
          `dp(2) = max(9 + dp(4), dp(3)) = max(9 + 1, 3) = max(10, 3) = 10`. Store `memo[2]=10`. Return `10`.

       2. `dp(1)` for `[7, 9, 3, 1]`
          `rob_1 = nums[1] + dp(3) = 7 + 3 = 10` (Memoized `dp(3)`)
          `skip_1 = dp(2) = 10` (Memoized `dp(2)`)
          `dp(1) = max(10, 10) = 10`. Store `memo[1]=10`. Return `10`.

       `dp(0) = max(2 + dp(2), dp(1)) = max(2 + 10, 10) = max(12, 10) = 12`. Store `memo[0]=12`. Return `12`.

Final Answer: `12`. Correct.

**Java Code (Top-Down):**

```java
import java.util.Arrays;

class SolutionHouseRobber_TopDown {
    // memo[idx] stores the maximum amount that can be robbed from index 'idx' to the end.
    // Initialized with -1 to indicate uncomputed states.
    private int[] memo;
    private int[] nums;
    private int n;

    /**
     * House Robber problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     *
     * @param nums Har ghar mein paise ki sankhya.
     * @return Loote gaye paison ki adhiktam sankhya.
     */
    public int rob(int[] nums) {
        this.nums = nums;
        this.n = nums.length;

        // Edge Cases:
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }

        // Memoization table initialize karo. Size 'n'.
        // -1 ka matlab ye state abhi tak calculate nahi hui hai.
        memo = new int[n];
        Arrays.fill(memo, -1);

        // Recursive function ko call karo start index 0 se (pehli ghar se lootna shuru karne ke liye).
        return dp(0);
    }

    /**
     * Recursive helper function: computes maximum money from `idx` to the end of `nums`.
     *
     * @param idx Current house ka index jisko consider kar rahe hain.
     * @return Maximum money jo `idx` se lekar end tak loota ja sakta hai.
     */
    private int dp(int idx) {
        // Base case 1: Agar index array ki bounds se bahar nikal gaya.
        // Matlab, koi ghar nahi bacha lootne ko.
        if (idx >= n) {
            return 0;
        }

        // Memoization check: Agar ye state pehle hi compute ho chuki hai.
        if (memo[idx] != -1) {
            return memo[idx];
        }

        // Option 1: Current ghar (nums[idx]) ko loot lo.
        // Toh agla ghar (idx + 1) nahi loot sakte. Do ghar aage (idx + 2) se lootna shuru karo.
        int robCurrent = nums[idx] + dp(idx + 2);

        // Option 2: Current ghar (nums[idx]) ko mat loot o.
        // Toh theek agle ghar (idx + 1) se lootna shuru karo.
        int skipCurrent = dp(idx + 1);

        // Dono options mein se jo maximum hai, woh current state ka answer hoga.
        // Result ko memo table mein store karo aur return karo.
        return memo[idx] = Math.max(robCurrent, skipCurrent);
    }

    public static void main(String[] args) {
        SolutionHouseRobber_TopDown solver = new SolutionHouseRobber_TopDown();

        System.out.println("\n--- 198. House Robber (Top-Down) ---");

        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Nums: " + Arrays.toString(nums1) + " -> Max Rob: " + solver.rob(nums1)); // Expected: 4

        int[] nums2 = {2, 7, 9, 3, 1};
        System.out.println("Nums: " + Arrays.toString(nums2) + " -> Max Rob: " + solver.rob(nums2)); // Expected: 12

        int[] nums3 = {0};
        System.out.println("Nums: " + Arrays.toString(nums3) + " -> Max Rob: " + solver.rob(nums3)); // Expected: 0

        int[] nums4 = {2, 1};
        System.out.println("Nums: " + Arrays.toString(nums4) + " -> Max Rob: " + solver.rob(nums4)); // Expected: 2
    }
}
````

<img width="1312" alt="image" src="https://github.com/user-attachments/assets/019a8d32-2013-4aff-bfad-cdd5f3009393" />

-----

## 1402\. Reducing Dishes Problem Kya Hai?

**Problem Statement (Hinglish):**
Imagine karo aap ek chef ho aur aapke paas `n` dishes hain. Har dish ka ek `satisfaction` level diya gaya hai (jo positive ya negative ho sakta hai). Aapko har dish ka `satisf2action` level ek integer array `satisfaction` mein diya gaya hai.

Chef koi bhi dish 1 unit time mein bana sakta hai.
Ek dish ka **like-time coefficient** calculate hota hai us dish ko banane mein lage **total time** ko uske `satisfaction` level se multiply karke (`time[i] * satisfaction[i]`). Yahan `time[i]` ka matlab hai us dish tak pahunchne mein laga *cumulative time*. Jaise, agar aap pehli dish banaoge toh 1 unit time, doosri banaoge toh usko 2 units time lagenge (1st + 2nd), teesri ko 3 units time, aur aise hi.

Aapko dhundhna hai ki **maximum sum of like-time coefficient** kitna ho sakta hai, agar chef kuch dishes ko banata hai aur kuch ko chhod bhi sakta hai. Dishes ko **kisi bhi order mein** banaya ja sakta hai.

**Examples:**

1.  `satisfaction = [-1, -8, 0, 5, -9]`

      * Answer: `14`
          * Agar dishes ko `[-1, 0, 5]` order mein banaya jaye (baaki discard kar diye):
              * Dish `-1` (1st position): `1 * (-1) = -1`
              * Dish `0` (2nd position): `2 * 0 = 0`
              * Dish `5` (3rd position): `3 * 5 = 15`
              * Total: `-1 + 0 + 15 = 14`
          * Ye sabse maximum possible sum hai. Order ko adjust karke dekha gaya hai.

2.  `satisfaction = [-1, -4, -5]`

      * Answer: `0`
          * Agar koi bhi dish banayi jaye, toh sum negative hoga. Isliye, koi bhi dish na banana hi sabse achcha hai, jisse sum `0` rahega.

3.  `satisfaction = [4, 3, 2]`

      * Answer: `20`
          * Order `[4, 3, 2]` ya koi bhi order mein banao:
              * `1 * 4 + 2 * 3 + 3 * 2 = 4 + 6 + 6 = 16`
          * Agar dishes ko descending order mein sort karein: `[4, 3, 2]`
              * `1 * 4 + 2 * 3 + 3 * 2 = 4 + 6 + 6 = 16`
          * Agar dishes ko ascending order mein sort karein: `[2, 3, 4]` (ye best hai)
              * `1 * 2 + 2 * 3 + 3 * 4 = 2 + 6 + 12 = 20`
          * Isliye, sorting important hai\!

-----

## Problem Ko Dusre Nazariye Se Dekhna (Transformation)

Ye problem ek **Greedy Approach** aur **Dynamic Programming** dono se solve ho sakti hai. Iski core understanding yeh hai ki humein `satisfaction` levels ko **sort** karna hoga.

**Intuition (Greedy Insight):**
Jab aap `like-time coefficient` calculate karte hain, toh har dish ka `satisfaction` level uske **position ke time factor** se multiply hota hai. Yani, jo dish **jitni der baad** banegi, uska `satisfaction` level **utne bade time factor** se multiply hoga.

Toh, agar aapko sum maximize karna hai:

  * Aapko **positive `satisfaction` values** ko **bade `time` factors** se multiply karna chahiye. Iska matlab hai ki sabse badi positive `satisfaction` wali dish ko sabse aakhir mein banana chahiye, usse choti ko usse pehle, aur aise hi. Yani, **positive values ko ascending order mein prepare karo**.
  * Agar aapke paas **negative `satisfaction` values** hain, toh unko minimize karna ya discard karna behtar hai. Agar aap kisi negative dish ko banate ho, toh woh overall sum ko kam karegi. Agar usko banana zaroori hai, toh usko **minimum time factor** se multiply karna behtar hai. Yani, sabse choti (most negative) `satisfaction` wali dish ko sabse pehle banana chahiye (agar use include kar rahe ho).

Is intuition se, sabse best tareeka hai `satisfaction` array ko **ascending order (smallest to largest)** mein sort karna. Isse negative values pehle aayengi (agar hain) aur positive values baad mein. Fir hum decide kar sakte hain ki kaun si negative values ko include karna hai ya nahi.

-----

## Dynamic Programming Approach (Top-Down / Memoization)

Is problem ko DP se solve karne ke liye, hum sorting ka faida uthayenge. Pehle `satisfaction` array ko ascending order mein sort kar lenge.

**DP State:**

`dp(index, time)` ka matlab hoga: `satisfaction` array ke `index` se lekar end tak ki dishes ko consider karte hue, jab current dish `time` unit par banayi ja rahi ho, toh **maximum `like-time coefficient` sum** kitna hoga.

**Recurrence Relation:**

`dp(index, time)` calculate karne ke liye, jab hum `satisfaction[index]` (current dish) par honge:

1.  **Current dish ko include karo (Choose):**
    `satisfaction[index] * time + dp(index + 1, time + 1)`
    (Current dish ka coefficient add karo, aur agle index par jao, time ko 1 se badha kar)

2.  **Current dish ko discard karo (Don't Choose):**
    `dp(index + 1, time)`
    (Current dish ko chhod do, agle index par jao, time ko change mat karo kyunki is dish ko banaya hi nahi)

`dp(index, time) = max(satisfaction[index] * time + dp(index + 1, time + 1), dp(index + 1, time))`

**Base Cases:**

  * `index == n` (saari dishes process ho gayi): `0` return karo.
  * `memo` array ko `-1` se initialize karo.

**Algorithm (Tareeka):**

1.  `satisfaction` array ko **ascending order** mein sort karo.
2.  `n = satisfaction.length` nikaalo.
3.  Ek 2D integer `memo` table banao jiska size `n x (n + 1)` hoga. (Time `1` se `n` tak ja sakta hai, isliye `n+1` columns). Sab values ko `-1` se initialize karo.
4.  Ek recursive function `solve(index, time)` banao:
      * **Base Case:** Agar `index == n` hai, toh `0` return karo.
      * **Memoization Check:** Agar `memo[index][time]` already calculated hai (`!= -1`), toh stored value return karo.
      * **Recursive Step:**
          * `choose = satisfaction[index] * time + solve(index + 1, time + 1)`
          * `dont_choose = solve(index + 1, time)`
          * Store `Math.max(choose, dont_choose)` ko `memo[index][time]` mein aur return kar do.
5.  Initial call `solve(0, 1)` se start karo (pehli dish se, pehle time unit par).

**Java Code (Top-Down / Memoization):**

```java
import java.util.Arrays;

class SolutionReducingDishes_TopDown {
    // memo[index][time] stores the maximum like-time coefficient from 'index' onwards,
    // assuming the current dish is cooked at 'time' unit.
    private int[][] memo;
    private int[] satisfaction;
    private int n;

    /**
     * Reducing Dishes problem ko Top-Down (Memoization) Dynamic Programming se solve karta hai.
     * Maximum sum of like-time coefficient dhundhta hai.
     *
     * @param satisfaction Har dish ka satisfaction level.
     * @return Maximum possible like-time coefficient sum.
     */
    public int maxSatisfaction(int[] satisfaction) {
        // Step 1: Dishes ko ascending order mein sort karo.
        // Ye sabse important step hai greedy choice ke liye.
        // Isse negative values pehle aayengi aur positive values baad mein.
        // Hamein negative values ko minimal time se multiply karna hai (agar include karein)
        // aur positive values ko maximal time se (agar include karein).
        Arrays.sort(satisfaction);
        this.satisfaction = satisfaction;
        this.n = satisfaction.length;

        // Memoization table initialize karo.
        // size n x (n+1) because index goes from 0 to n-1, and time goes from 1 to n.
        memo = new int[n][n + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Recursive function ko call karo.
        // Shuruat pehli dish (index 0) se aur time 1 se.
        return solve(0, 1);
    }

    /**
     * Recursive helper function to find the maximum satisfaction.
     *
     * @param index Current dish ka index jo consider kar rahe hain.
     * @param time Current dish ko banane mein laga time (cumulative).
     * @return Maximum like-time coefficient from this state.
     */
    private int solve(int index, int time) {
        // Base Case: Agar saari dishes process ho gayi hain, toh 0 return karo.
        if (index == n) {
            return 0;
        }

        // Memoization check: Agar ye state pehle hi compute ho chuki hai.
        if (memo[index][time] != -1) {
            return memo[index][time];
        }

        // Option 1: Current dish ko include karo (cook it).
        // Current dish ka satisfaction * current time + baki dishes ka max sum.
        int chooseCurrent = satisfaction[index] * time + solve(index + 1, time + 1);

        // Option 2: Current dish ko discard karo (don't cook it).
        // Current dish ko chhod do aur agle dish se check karna shuru karo, time wahi rahega.
        int dontChooseCurrent = solve(index + 1, time);

        // Dono options mein se jo maximum hai, woh current state ka answer hoga.
        // Result ko memo table mein store karo aur return karo.
        return memo[index][time] = Math.max(chooseCurrent, dontChooseCurrent);
    }

    public static void main(String[] args) {
        SolutionReducingDishes_TopDown solver = new SolutionReducingDishes_TopDown();

        System.out.println("--- 1402. Reducing Dishes (Top-Down) ---");

        int[] satisfaction1 = {-1, -8, 0, 5, -9};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction1) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction1)); // Expected: 14

        int[] satisfaction2 = {-1, -4, -5};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction2) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction2)); // Expected: 0

        int[] satisfaction3 = {4, 3, 2};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction3) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction3)); // Expected: 20
    }
}
```

-----

## Approach 2: Bottom-Up (Tabulation) - Space Optimized

Memoization approach ko Bottom-Up mein convert karna thoda tricky ho sakta hai, kyunki `time` factor bhi badal raha hai. Lekin, ek bahut hi elegant **Greedy Approach** hai jo is problem ko $O(N \\log N)$ (sorting) + $O(N)$ (single pass) time mein solve karta hai aur space $O(1)$ (if in-place sort).

**Greedy Algorithm (Tareeka):**

1.  `satisfaction` array ko **ascending order** mein sort karo.
2.  Ek `current_sum_of_satisfaction` variable banao aur `0` se initialize karo. Ye variable un dishes ka `satisfaction` sum store karega jinhe hum currently consider kar rahe hain.
3.  Ek `total_like_time_coefficient` variable banao aur `0` se initialize karo. Ye hamara final answer hoga.
4.  Ab sorted `satisfaction` array ko **reverse order mein iterate karo (right to left)**. Matlab, sabse badi value se shuru karke sabse choti value tak jao.
      * Har `satisfaction[i]` value ke liye:
          * `current_sum_of_satisfaction` mein `satisfaction[i]` add karo.
          * Agar `current_sum_of_satisfaction` `0` se bada hai:
              * Toh `current_sum_of_satisfaction` ko `total_like_time_coefficient` mein add karo. (Iska matlab hai ki current `satisfaction[i]` value aur iske baad wali saari values (jo ab tak `current_sum_of_satisfaction` mein hain) ka combination, overall positive contribution de raha hai. Isko add karne se current time step ka contribution milta hai.)
          * Agar `current_sum_of_satisfaction` `0` ya `0` se kam hai:
              * Break kar do loop ko. Iska matlab hai ki ab is point se pehle (sort kiye hue array mein left side) wali dishes ko add karne se total sum negative ho jayega, aur hum maximum sum se door ho jayenge. To un dishes ko discard karna hi behtar hai.

**Greedy Logic Explained:**
Jab hum sorted array ko reverse (right to left) iterate karte hain, hum effectively dishes ko unke *descending order of satisfaction* mein pick kar rahe hain (agar positive hain). Har step par, `current_sum_of_satisfaction` un dishes ka sum hai jinhe humne ab tak select kiya hai (current dish + saari badi dishes).
`total_like_time_coefficient` mein `current_sum_of_satisfaction` add karne ka matlab hai:

  * Pehli dish jo pick ki (`satisfaction[n-1]`) ko time 1, iska sum `satisfaction[n-1]`.
  * Doosri dish jo pick ki (`satisfaction[n-2]`) ko time 1, aur pehli wali ko time 2. Total contribution: `satisfaction[n-2] + 2 * satisfaction[n-1]`.
    Agar aap `total_like_time_coefficient += current_sum_of_satisfaction` karein:
    `Iteration n-1`: `current_sum = satisfaction[n-1]`, `total_like_time = satisfaction[n-1]`
    `Iteration n-2`: `current_sum = satisfaction[n-2] + satisfaction[n-1]`, `total_like_time = satisfaction[n-1] + (satisfaction[n-2] + satisfaction[n-1]) = 2*satisfaction[n-1] + satisfaction[n-2]`
    Ye calculation theek nahi hai. This logic is subtle.

**Corrected Greedy Logic (Simpler):**
Sort the `satisfaction` array in **ascending order**.
Now, we want to maximize `sum(time[i] * satisfaction[i])`.
Consider the dishes from largest satisfaction to smallest.

What if we have `[ -1, 0, 5]`
Sorted: `[-1, 0, 5]`
Iterate from right to left:

1.  Dish `5`: `current_sum = 5`. `total_like = 5` (This is `1 * 5`).
2.  Dish `0`: `current_sum = 5 + 0 = 5`. If `current_sum > 0`, add to `total_like`.
    `total_like = 5 + 5 = 10`.
    This `10` means: `(1*0 + 2*5)`. This implies that `5` is now `time=2`, and `0` is `time=1`.
3.  Dish `-1`: `current_sum = 5 + (-1) = 4`. If `current_sum > 0`, add to `total_like`.
    `total_like = 10 + 4 = 14`.
    This `14` means: `(1*(-1) + 2*0 + 3*5)`.

This greedy approach works\!

**Java Code (Greedy - Space Optimized):**

```java
import java.util.Arrays;

class SolutionReducingDishes_Greedy {
    /**
     * Reducing Dishes problem ko Greedy approach (Bottom-Up, Space Optimized) se solve karta hai.
     * Maximum sum of like-time coefficient dhundhta hai.
     *
     * @param satisfaction Har dish ka satisfaction level.
     * @return Maximum possible like-time coefficient sum.
     */
    public int maxSatisfaction(int[] satisfaction) {
        // Step 1: Dishes ko ascending order mein sort karo.
        // Isse negative values left mein aur positive values right mein aayengi.
        Arrays.sort(satisfaction);

        int n = satisfaction.length;
        int currentSumOfSatisfaction = 0; // Ye store karega current selected dishes ka simple sum.
        int totalLikeTimeCoefficient = 0; // Ye hamara final maximum sum hoga.

        // Loop array ko reverse order mein (right se left) chalao.
        // Matlab, sabse badi satisfaction value se shuru karo.
        for (int i = n - 1; i >= 0; i--) {
            int currentDishSatisfaction = satisfaction[i];

            // Agar currentDishSatisfaction ko currentSumOfSatisfaction mein add karne se
            // total sum positive rehta hai (ya increase hota hai).
            // Example: [5, 0, -1] reversed iteration
            // i=2 (value 5): currentSum=5. 5>0. totalLikeTime=5.
            // i=1 (value 0): currentSum=5+0=5. 5>0. totalLikeTime=5+5=10.
            // i=0 (value -1): currentSum=5+(-1)=4. 4>0. totalLikeTime=10+4=14.
            // Logic: Agar current cumulative sum of satisfaction (jo humne ab tak select kiye hain)
            // positive rehta hai, toh current dish ko add karne se total like-time coefficient badhega.
            // Kyunki currentDishSatisfaction ko 1 * currentDishSatisfaction
            // aur baaki sab ko 1 extra time factor milega, total sum currentSumOfSatisfaction se badh jayega.
            if (currentSumOfSatisfaction + currentDishSatisfaction > 0) {
                currentSumOfSatisfaction += currentDishSatisfaction;
                totalLikeTimeCoefficient += currentSumOfSatisfaction; // Current dishes (aur uske aage wale) ka cumulative effect.
            } else {
                // Agar current dish ko add karne se currentSumOfSatisfaction negative ho jata hai,
                // toh is dish ko aur isse pehle ki koi bhi dish ko add karna faaydemand nahi hoga.
                // Maximum sum previous state mein hi mil gaya tha.
                break;
            }
        }

        return totalLikeTimeCoefficient;
    }

    public static void main(String[] args) {
        SolutionReducingDishes_Greedy solver = new SolutionReducingDishes_Greedy();

        System.out.println("\n--- 1402. Reducing Dishes (Greedy / Space Optimized) ---");

        int[] satisfaction1 = {-1, -8, 0, 5, -9};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction1) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction1)); // Expected: 14

        int[] satisfaction2 = {-1, -4, -5};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction2) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction2)); // Expected: 0

        int[] satisfaction3 = {4, 3, 2};
        System.out.println("Satisfaction: " + Arrays.toString(satisfaction3) + " -> Max Satisfaction: " + solver.maxSatisfaction(satisfaction3)); // Expected: 20
    }
}
```

-----
<img width="1312" alt="image" src="https://github.com/user-attachments/assets/21994b56-b29f-450a-8fcf-a5b2fe65761f" />
Okay, let's break down the "Minimum Swaps To Make Sequences Increasing" problem (LeetCode 801). This is a classic dynamic programming problem, and we'll approach it with both a **Top-Down (Memoization)** and **Bottom-Up (Tabulation)** solution, focusing on how DP helps us make optimal decisions.

-----

### Understanding the Problem: Minimum Swaps To Make Sequences Increasing

**Problem Statement:**

Aapko do equal-length integer arrays, `nums1` aur `nums2` diye gaye hain. Aapko **minimum number of swaps** karne hain, taaki dono arrays strict increasing ho jayein. Strict increasing ka matlab hai ki har element apne pichle element se bada ho (`A[i] > A[i-1]`).

Aap sirf ek hi index `i` par `nums1[i]` aur `nums2[i]` ko swap kar sakte ho. Matlab, ya toh aap `nums1[i]` aur `nums2[i]` ko swap karoge, ya nahi karoge.

**Input:**

  * `int[] nums1`
  * `int[] nums2`

**Output:**

  * Minimum number of swaps required.

**Example:**

`nums1 = [1, 3, 5, 4]`
`nums2 = [1, 2, 3, 7]`

Agar hum `nums1[3]` (4) aur `nums2[3]` (7) ko swap karte hain:
`nums1` becomes `[1, 3, 5, 7]` (Strictly increasing)
`nums2` becomes `[1, 2, 3, 4]` (Strictly increasing)
Total swaps: 1

-----

### How to Relate This Problem to Dynamic Programming?

Yeh problem DP se relate hoti hai kyuki isme do main cheezein hain:

1.  **Optimal Substructure:** Bade problem ka optimal solution chote subproblems ke optimal solutions par depend karta hai. Index `i` par jo hum decision lete hain (swap karein ya na karein), woh `i-1` tak ke decisions par based hota hai.
2.  **Overlapping Subproblems:** Kuch subproblems ko baar-baar solve karna pad sakta hai, jisko hum memoization ya tabulation se handle karte hain.

Har index `i` par humare paas do choices hain:

  * `i`-th elements ko **swap** karna.
  * `i`-th elements ko **non-swap** karna.

Aur in choices ka result previous index `i-1` par liye gaye decision par depend karega. Specifically, `i-1` par humne swap kiya tha ya nahi kiya tha, ispar.

Isliye, hamare DP state mein do cheezein honi chahiye:

  * Current index `i`.
  * A `flag` ya `state` jo bataye ki previous index (`i-1`) par humne swap kiya tha ya nahi.

-----

### Solution Approach 1: Top-Down (Memoization)

Top-Down approach ek recursive solution hota hai jisme hum computed results ko store kar lete hain taaki unhe dobara calculate na karna pade.

**DP State:**
`dp[i][swapped]` = Minimum swaps needed for prefix of arrays up to index `i`, where `swapped` is a boolean indicating if `nums1[i]` and `nums2[i]` were swapped.

  * `swapped = 0`: `nums1[i]` aur `nums2[i]` ko swap nahi kiya.
  * `swapped = 1`: `nums1[i]` aur `nums2[i]` ko swap kiya.

**Recursive Function:**
`solve(idx, prevSwapped)`:

  * `idx`: Current index jahan par hum decision le rahe hain.
  * `prevSwapped`: Boolean flag jo batata hai ki `idx-1` par humne elements ko swap kiya tha ya nahi.

**Logic (Choice Diagram):**

`idx`-th position par humare paas 2 choices hain: **Swap** ya **No Swap**.
Lekin, yeh choices tabhi valid hain jab resulting sequences **strictly increasing** hon.

**Variables for convenience:**
`prev1`, `prev2`: `nums1[idx-1]` aur `nums2[idx-1]` ki values.
`curr1`, `curr2`: `nums1[idx]` aur `nums2[idx]` ki values.

**Base Case:**

  * Agar `idx == n` (arrays khatam ho gaye), return 0 swaps.

**Memoization:**

  * Agar `dp[idx][prevSwapped]` pehle se calculated hai, return `dp[idx][prevSwapped]`.

**Recursive Steps:**

1.  **`prevSwapped` ka asar:** Agar `prevSwapped` true tha, toh `prev1` (actual `nums1[idx-1]`) aur `prev2` (actual `nums2[idx-1]`) ki values exchange ho jayengi, kyuki `idx-1` par swap hua tha.

      * `if (prevSwapped) { swap(prev1, prev2); }`

2.  **`noSwapCost` (Agar current elements ko swap na karein):**

      * Condition: Kya `curr1 > prev1` aur `curr2 > prev2`? (Tabhi dono arrays increasing rahenge agar current elements ko swap na karein).
      * Agar condition true hai: `noSwapCost = solve(idx + 1, false);`
      * Agar condition false hai: `noSwapCost = infinity;` (invalid path)

3.  **`swapCost` (Agar current elements ko swap karein):**

      * Condition: Kya `curr2 > prev1` aur `curr1 > prev2`? (Swapped values check karein).
      * Agar condition true hai: `swapCost = 1 + solve(idx + 1, true);` (1 swap kiya isliye +1)
      * Agar condition false hai: `swapCost = infinity;` (invalid path)

4.  **Result:** `dp[idx][prevSwapped] = Math.min(noSwapCost, swapCost);`

**Hinglish Explanation with Code:**

```java
import java.util.Arrays;

public class MinSwapsToMakeSequencesIncreasingTopDown {

    // DP table: dp[index][prevSwapped]
    // -1 means not computed, Integer.MAX_VALUE means impossible
    private int[][] dp;
    private int[] nums1;
    private int[] nums2;
    private int n;

    public int minSwap(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;
        this.n = nums1.length;
        dp = new int[n + 1][2]; // +1 for N, 2 for prevSwapped (0=false, 1=true)

        // DP table ko -1 se initialize karo
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Dummy initial values for prev1 and prev2 to handle index 0.
        // Ye values itni chhoti honi chahiye ki array ke first elements (nums1[0], nums2[0])
        // hamesha inko cross kar sakein (yani nums1[0] > -1, nums2[0] > -1).
        // Ham actual logic index 1 se start karte hain, to prev elements index 0 honge.
        // Agar prev element -1 hai, toh wo hamesha increasing hoga.
        return solve(1, 0); // Start from index 1, assuming index 0 was NOT swapped.
                            // Index 0 par, hum assume karte hain ki virtual (-1, -1) values se comparison ho raha hai.
                            // Ya simpler, index 0 ko as "base case" alag se handle karo.
                            // Yaha hum index 1 se check karna shuru karte hain,
                            // isliye nums1[0], nums2[0] previous elements honge.
                            // prev1 = nums1[0], prev2 = nums2[0] agar no swap
                            // prev1 = nums2[0], prev2 = nums1[0] agar swap
    }

    private int solve(int idx, int prevSwapped) {
        // Base Case: Agar hum array ke end tak pahunch gaye, to 0 swaps needed for remaining part.
        if (idx == n) {
            return 0;
        }

        // Memoization: Agar ye state pehle se calculate ho chuki hai, to stored value return karo.
        if (dp[idx][prevSwapped] != -1) {
            return dp[idx][prevSwapped];
        }

        // Ab hum current index 'idx' par hain.
        // Current elements hain nums1[idx] aur nums2[idx].
        // Previous elements hain nums1[idx-1] aur nums2[idx-1].

        // 'prev1' aur 'prev2' ki actual values decide karo depending on 'prevSwapped'
        int prev1, prev2;
        if (prevSwapped == 0) { // Previous index par swap nahi hua tha
            prev1 = nums1[idx - 1];
            prev2 = nums2[idx - 1];
        } else { // Previous index par swap hua tha
            prev1 = nums2[idx - 1]; // num1[idx-1] original value, but it was swapped. So nums2[idx-1] becomes prev1
            prev2 = nums1[idx - 1]; // num2[idx-1] original value, but it was swapped. So nums1[idx-1] becomes prev2
        }

        // Initialise minimum swaps for current state to infinity
        int ans = Integer.MAX_VALUE;

        // Choice 1: Current index 'idx' par NO SWAP (nums1[idx], nums2[idx] ko unswapped rakho)
        // Condition: Kya current elements unswapped reh kar bhi strictly increasing hain previous elements se?
        if (nums1[idx] > prev1 && nums2[idx] > prev2) {
            // Agar condition satisfy hoti hai, toh hum 'no swap' choice le sakte hain.
            // Recurse for next index (idx + 1) with 'prevSwapped = 0' (kyunki current index par swap nahi kiya).
            int cost = solve(idx + 1, 0);
            if (cost != Integer.MAX_VALUE) { // Agar next state reachable hai
                ans = Math.min(ans, cost); // Minimum cost update karo
            }
        }

        // Choice 2: Current index 'idx' par SWAP (nums1[idx] aur nums2[idx] ko swap karo)
        // Condition: Kya current elements SWAPPED reh kar strictly increasing hain previous elements se?
        if (nums2[idx] > prev1 && nums1[idx] > prev2) {
            // Agar condition satisfy hoti hai, toh hum 'swap' choice le sakte hain.
            // Cost of swap = 1 (current swap) + recursive call for next index (idx + 1)
            // with 'prevSwapped = 1' (kyunki current index par swap kiya).
            int cost = solve(idx + 1, 1);
            if (cost != Integer.MAX_VALUE) { // Agar next state reachable hai
                ans = Math.min(ans, 1 + cost); // Minimum cost update karo
            }
        }

        // Result store karo aur return karo
        return dp[idx][prevSwapped] = ans;
    }

    public static void main(String[] args) {
        MinSwapsToMakeSequencesIncreasingTopDown solver = new MinSwapsToMakeSequencesIncreasingTopDown();
        int[] nums1 = {1, 3, 5, 4};
        int[] nums2 = {1, 2, 3, 7};
        System.out.println("Minimum swaps (Top-Down): " + solver.minSwap(nums1, nums2)); // Expected: 1

        int[] nums1_2 = {0, 3, 5, 8, 9};
        int[] nums2_2 = {2, 1, 4, 6, 9};
        System.out.println("Minimum swaps (Top-Down): " + solver.minSwap(nums1_2, nums2_2)); // Expected: 1 (swap at index 1: 3,1 -> 1,3)
    }
}
```

**Complexity Analysis (Top-Down):**

  * **Time Complexity:** $O(N \\cdot 2) = O(N)$, where N is the length of arrays. Har `(idx, prevSwapped)` state ko hum ek hi baar compute karte hain.
  * **Space Complexity:** $O(N \\cdot 2) + O(N)$ (recursion stack) = $O(N)$.

-----

### Solution Approach 2: Bottom-Up (Tabulation) - **Focus Here\!**

Bottom-Up DP iterative approach hai. Hum chote subproblems se start karte hain aur gradually bade subproblems tak pahunchte hain, filling up a DP table.

**DP Table Definition:**

Same as Top-Down, but we fill it iteratively.
`dp[i][0]` = Minimum swaps needed to make `nums1[0...i]` and `nums2[0...i]` strictly increasing, WITHOUT swapping `nums1[i]` and `nums2[i]`.
`dp[i][1]` = Minimum swaps needed to make `nums1[0...i]` and `nums2[0...i]` strictly increasing, WITH swapping `nums1[i]` and `nums2[i]`.

**Table Size:** `dp[n][2]`

**Initialization (Base Cases):**

  * `dp[0][0] = 0`: Index 0 par agar swap nahi karte, toh 0 swaps needed.
  * `dp[0][1] = 1`: Index 0 par agar swap karte hain, toh 1 swap needed.

**Filling the DP Table (Iterative Logic):**

Hum `i` ko `1` se `n-1` tak iterate karenge (kyuki `i=0` already initialized hai).
Har `i`-th index par, hum `dp[i][0]` aur `dp[i][1]` calculate karenge.

**Understanding `dp[i][0]` (No Swap at current `i`):**

Agar hum `i`-th index par swap nahi kar rahe hain (`nums1[i]`, `nums2[i]` as they are), toh previous index `i-1` ke decision ke basis par humein check karna hoga:

1.  **"No Swap at `i-1`" path:**

      * Condition: `nums1[i] > nums1[i-1]` AND `nums2[i] > nums2[i-1]`
      * Agar condition true hai: `dp[i][0]` becomes `dp[i-1][0]` (kyuki previous tak ka cost + current pe no swap).
      * This is the simplest case.

2.  **"Swap at `i-1`" path:**

      * Condition: `nums1[i] > nums2[i-1]` AND `nums2[i] > nums1[i-1]` (Kyuki `i-1` par swap hua tha, toh `nums1[i-1]` ka comparison `nums2[i-1]` se hoga, aur `nums2[i-1]` ka `nums1[i-1]` se).
      * Agar condition true hai: `dp[i][0]` becomes `dp[i-1][1]` (kyuki previous tak ka cost `dp[i-1][1]` tha + current pe no swap).

    **Important:** `dp[i][0]` ko hum minimum of these valid paths se update karenge.

**Understanding `dp[i][1]` (Swap at current `i`):**

Agar hum `i`-th index par swap kar rahe hain (`nums1[i]` becomes `nums2[i]` and vice versa), toh previous index `i-1` ke decision ke basis par humein check karna hoga:

1.  **"No Swap at `i-1`" path:**

      * Condition: `nums2[i] > nums1[i-1]` AND `nums1[i] > nums2[i-1]` (Current `nums1[i]` has effectively become `nums2[i]`, and `nums2[i]` has become `nums1[i]`).
      * Agar condition true hai: `dp[i][1]` becomes `1 + dp[i-1][0]` (1 for current swap + previous cost).

2.  **"Swap at `i-1`" path:**

      * Condition: `nums2[i] > nums2[i-1]` AND `nums1[i] > nums1[i-1]` (Current `nums1[i]` has effectively become `nums2[i]`, and `nums2[i]` has become `nums1[i]`. And `i-1` par bhi swap hua tha).
      * Agar condition true hai: `dp[i][1]` becomes `1 + dp[i-1][1]` (1 for current swap + previous cost).

    **Important:** `dp[i][1]` ko hum minimum of these valid paths se update karenge.

**Hinglish Explanation with Code:**

```java
import java.util.Arrays;

public class MinSwapsToMakeSequencesIncreasingBottomUp {

    public int minSwap(int[] nums1, int[] nums2) {
        int n = nums1.length;

        // DP table: dp[i][0] for no swap at index i, dp[i][1] for swap at index i
        // size n because we consider elements from 0 to n-1
        int[][] dp = new int[n][2];

        // Initialize all DP states to a very large value (infinity)
        // to correctly find minimums and identify unreachable states.
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Base Cases (for index 0):
        // dp[0][0]: If we don't swap at index 0, cost is 0.
        dp[0][0] = 0;
        // dp[0][1]: If we swap at index 0, cost is 1.
        dp[0][1] = 1;

        // Fill the DP table iteratively from index 1 to n-1
        for (int i = 1; i < n; i++) {
            // Option 1: Current elements (nums1[i], nums2[i]) ko SWAP NA KARO
            // Ab check karo ki previous index (i-1) par kya decision liya tha:

            // Path A: Previous index (i-1) par bhi SWAP NAHI KIYA THA (dp[i-1][0] se aa rahe hain)
            // Condition: nums1[i] > nums1[i-1] AND nums2[i] > nums2[i-1]
            // Agar ye condition true hai, matlab current elements unswapped reh kar
            // bhi previous unswapped elements se bade hain.
            if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                // dp[i][0] (current no swap) = dp[i-1][0] (previous no swap)
                dp[i][0] = dp[i - 1][0];
            }

            // Path B: Previous index (i-1) par SWAP KIYA THA (dp[i-1][1] se aa rahe hain)
            // Condition: nums1[i] > nums2[i-1] AND nums2[i] > nums1[i-1]
            // Matlab, current elements (unswapped) previous SWAPPED elements se bade hain.
            // (nums1[i] must be greater than nums2[i-1], nums2[i] must be greater than nums1[i-1])
            if (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]) {
                // dp[i][0] (current no swap) = min(current dp[i][0], dp[i-1][1])
                // Hum minimum lenge, kyuki do alag paths se dp[i][0] state tak pahunch sakte hain.
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
            }

            // Option 2: Current elements (nums1[i], nums2[i]) ko SWAP KARO
            // (Cost = 1 + previous swaps)
            // Ab check karo ki previous index (i-1) par kya decision liya tha:

            // Path C: Previous index (i-1) par bhi SWAP NAHI KIYA THA (dp[i-1][0] se aa rahe hain)
            // Condition: nums2[i] > nums1[i-1] AND nums1[i] > nums2[i-1]
            // Matlab, current elements (SWAPPED) previous UNWAPPED elements se bade hain.
            // (nums2[i] (jo ab nums1[i] ki jagah hai) > nums1[i-1])
            // (nums1[i] (jo ab nums2[i] ki jagah hai) > nums2[i-1])
            if (nums2[i] > nums1[i - 1] && nums1[i] > nums2[i - 1]) {
                // dp[i][1] (current swap) = 1 (for current swap) + dp[i-1][0] (previous no swap)
                dp[i][1] = 1 + dp[i - 1][0];
            }

            // Path D: Previous index (i-1) par SWAP KIYA THA (dp[i-1][1] se aa rahe hain)
            // Condition: nums2[i] > nums2[i-1] AND nums1[i] > nums1[i-1]
            // Matlab, current elements (SWAPPED) previous SWAPPED elements se bade hain.
            // (nums2[i] > nums2[i-1]) after both are effectively swapped from original positions
            if (nums2[i] > nums2[i - 1] && nums1[i] > nums1[i - 1]) {
                // dp[i][1] (current swap) = min(current dp[i][1], 1 + dp[i-1][1])
                // Again, minimum lenge from different paths
                dp[i][1] = Math.min(dp[i][1], 1 + dp[i - 1][1]);
            }
        }

        // Final Answer:
        // Minimum of dp[n-1][0] (no swap at last index) and dp[n-1][1] (swap at last index)
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }

    public static void main(String[] args) {
        MinSwapsToMakeSequencesIncreasingBottomUp solver = new MinSwapsToMakeSequencesIncreasingBottomUp();
        int[] nums1 = {1, 3, 5, 4};
        int[] nums2 = {1, 2, 3, 7};
        System.out.println("Minimum swaps (Bottom-Up): " + solver.minSwap(nums1, nums2)); // Expected: 1

        int[] nums1_2 = {0, 3, 5, 8, 9};
        int[] nums2_2 = {2, 1, 4, 6, 9};
        System.out.println("Minimum swaps (Bottom-Up): " + solver.minSwap(nums1_2, nums2_2)); // Expected: 1
    }
}
```

**Complexity Analysis (Bottom-Up):**

  * **Time Complexity:** $O(N)$. Single loop from `1` to `n-1`, with constant time operations inside.
  * **Space Complexity:** $O(N \\cdot 2) = O(N)$ for the DP table.

-----

### Bottom-Up Approach Ko Acche Se Samjhana:

Bottom-Up DP is like building a solution from the ground up, starting with the simplest possible cases and using them to construct solutions for more complex cases.

**Analogy:**

Imagine aap ek assembly line par ho. Har station (`i`) par aapke paas do arrays (`nums1`, `nums2`) ke elements hain. Aapko decide karna hai ki current elements (`nums1[i]`, `nums2[i]`) ko swap karein ya na karein. Aapka decision sirf pichle station (`i-1`) ke elements ki final state par nirbhar karta hai.

**DP Table as a Scoreboard:**

  * `dp[i][0]` ek score jaisa hai, jo batata hai ki `i` tak ke elements ko strictly increasing banane ke liye minimum kitne swaps lage, **agar `i`-th position par swap nahi kiya.**
  * `dp[i][1]` doosra score hai, jo batata hai ki `i` tak ke elements ko strictly increasing banane ke liye minimum kitne swaps lage, **agar `i`-th position par swap kiya.**

**Filling Process:**

1.  **Start at `i=0` (Base Case):**

      * Agar `nums1[0]` aur `nums2[0]` ko swap nahi karte, toh 0 swaps. (`dp[0][0] = 0`)
      * Agar `nums1[0]` aur `nums2[0]` ko swap karte hain, toh 1 swap. (`dp[0][1] = 1`)

2.  **Move to `i=1`:**

      * Ab aap `nums1[1]` aur `nums2[1]` par ho. Aapko `dp[1][0]` aur `dp[1][1]` fill karna hai.
      * **Calculate `dp[1][0]` (No swap at `i=1`):**
          * Kya `nums1[1]` \> `nums1[0]` aur `nums2[1]` \> `nums2[0]`? (i.e., agar `i=0` par swap nahi kiya tha). Agar haan, toh cost `dp[0][0]` hogi.
          * Kya `nums1[1]` \> `nums2[0]` aur `nums2[1]` \> `nums1[0]`? (i.e., agar `i=0` par swap kiya tha). Agar haan, toh cost `dp[0][1]` होगी.
          * `dp[1][0]` in valid paths ka minimum hoga.
      * **Calculate `dp[1][1]` (Swap at `i=1`):**
          * Kya `nums2[1]` \> `nums1[0]` aur `nums1[1]` \> `nums2[0]`? (i.e., agar `i=0` par swap nahi kiya tha, but `i=1` par swap kar rahe hain). Agar haan, toh cost `1 + dp[0][0]` होगी.
          * Kya `nums2[1]` \> `nums2[0]` aur `nums1[1]` \> `nums1[0]`? (i.e., agar `i=0` par swap kiya tha, aur `i=1` par bhi swap kar rahe hain). Agar haan, toh cost `1 + dp[0][1]` होगी.
          * `dp[1][1]` in valid paths ka minimum होगा.

3.  **Continue for `i=2, 3, ... n-1`:**

      * Har `i` par, aap `nums1[i-1]` aur `nums2[i-1]` ki actual values decide karne ke liye `dp[i-1][0]` aur `dp[i-1][1]` ko dekhte ho.
      * Based on these, aap decide karte ho ki `i`-th position par current elements ko swap karne se ya na karne se kya impact padega, aur us hisab se `dp[i][0]` aur `dp[i][1]` ko update karte ho.

**Key Idea of Bottom-Up:**

`dp[i][current_swap_state]` ko calculate karne ke liye, hum sirf `dp[i-1][prev_swap_state_0]` aur `dp[i-1][prev_swap_state_1]` par depend karte hain. Yeh ensures karta hai ki jab hum `dp[i]` ko calculate kar rahe honge, tab tak `dp[i-1]` ke saare required values already calculated ho chuke honge.

This systematic table filling makes the logic very robust and avoids recursion depth issues. It's often preferred for its clarity and potential for space optimization (though not explicitly shown here, sometimes you only need the previous row's DP values, reducing space to $O(1)$).
-----


<img width="1312" alt="image" src="https://github.com/user-attachments/assets/72d0cf5a-10be-440f-9c08-0919b3c0f1c0" />
Sure, let's break down the "Profitable Schemes" problem (LeetCode 879) using Dynamic Programming. This problem is a variation of the classic 0/1 Knapsack problem, so understanding that concept will definitely help. We'll explore both Top-Down (Memoization) and Bottom-Up (Tabulation) approaches, with a special focus on the Bottom-Up method, all explained in Hinglish, along with Java code.

-----

### Understanding the Problem: Profitable Schemes

**Problem Statement:**

Aapko `n` members diye gaye hain, aur aapko `minProfit` achieve karna hai. Aapke paas `group` aur `profit` arrays hain, jahaan `group[i]` i-th crime ko karne ke liye required members ki sankhya hai, aur `profit[i]` us crime se hone wala profit hai. Har crime ko aap sirf ek hi baar kar sakte ho. Aapko yeh batana hai ki kitne ways (schemes) hain jisme aap `minProfit` ya usse zyada profit kama sakte ho, jabki total members `n` se zyada na ho. Result ko `10^9 + 7` se modulo karna hai.

**Input:**

  * `int n`: Total available members.
  * `int minProfit`: Minimum profit to achieve.
  * `int[] group`: Array, `group[i]` is members required for crime `i`.
  * `int[] profit`: Array, `profit[i]` is profit from crime `i`.

**Output:**

  * Number of profitable schemes modulo `10^9 + 7`.

**Example:**

`n = 5`, `minProfit = 3`
`group = [2, 2]`
`profit = [3, 2]`

Possible schemes:

1.  Crime 0 (`group=2`, `profit=3`): Members = 2 (\<=5), Profit = 3 (\>=3). Valid.
2.  Crime 1 (`group=2`, `profit=2`): Members = 2 (\<=5), Profit = 2 (\<3). Invalid.
3.  Crime 0 + Crime 1 (`group=2+2=4`, `profit=3+2=5`): Members = 4 (\<=5), Profit = 5 (\>=3). Valid.

Total valid schemes: 2.

-----

### How to Relate This Problem to Knapsack?

Yeh problem **0/1 Knapsack** ka ek variation hai. Basic Knapsack mein hum maximum value dhundhte hain ek given capacity ke andar. Yahan par, humein **count** karna hai kitne ways hain jisse hum ek certain (minimum) profit tak pahunch sakein, given a maximum member constraint.

  * **Items:** Har crime ek item hai.
  * **Weight:** Crime ko karne ke liye required members (`group[i]`) item ka weight hai.
  * **Value:** Crime se hone wala profit (`profit[i]`) item ki value hai.
  * **Knapsack Capacity:** Total available members (`n`) hamari knapsack capacity hai.
  * **Goal:** Yahan goal *maximum profit* nahi hai, balki *count* karna hai kitne combinations `minProfit` ya usse zyada profit de rahe hain, `n` members ke andar.

Yeh "count the number of ways" variation Knapsack problems mein common hai, jahan DP table `max value` ki jagah `number of ways` store karti hai.

-----

### Solution Approach 1: Top-Down (Memoization)

Top-Down approach recursive hota hai. Hum bade problem ko chote subproblems mein todte hain aur unke results store karte rehte hain taaki future mein dobara calculate na karna pade.

**DP State:**
`memo[idx][currentMembers][currentProfit]` = Number of ways to achieve `currentProfit` using `currentMembers` by considering crimes from `idx` onwards.

**Recursive Function:**
`solve(idx, currentMembers, currentProfit)`

**Logic:**

Har crime `idx` ke liye, humare paas do choices hain:

1.  **Crime ko include karein:** Agar `currentMembers + group[idx]` `<= n` hai.
      * Value: `solve(idx + 1, currentMembers + group[idx], min(minProfit, currentProfit + profit[idx]))`
      * Yahan `min(minProfit, ...)` isliye use kar rahe hain kyuki humein sirf `minProfit` tak ka profit track karna hai. Agar profit `minProfit` se zyada ho gaya, toh bhi hum use `minProfit` hi treat karenge, kyuki usse aage ki exact value matter nahi karti, bas yeh matter karta hai ki humne target hit kar liya.
2.  **Crime ko exclude karein:**
      * Value: `solve(idx + 1, currentMembers, currentProfit)`

Total ways in `memo[idx][currentMembers][currentProfit]` will be the sum of ways from these two choices, modulo `10^9 + 7`.

**Base Cases:**

  * Agar `idx == crimes.length` (saare crimes check kar liye):
      * Agar `currentProfit >= minProfit` toh 1 valid scheme mili.
      * Else, 0 valid schemes.

**Memoization:**

  * Agar `memo[idx][currentMembers][currentProfit]` already calculated hai, return it.

**Hinglish Explanation with Code:**

```java
import java.util.Arrays;

public class ProfitableSchemesTopDown {

    private int MOD = 1_000_000_007;
    // memo[crime_idx][current_members][current_profit]
    // current_profit ko minProfit tak hi track karna hai, usse zyada nahi.
    // Isliye third dimension ki size minProfit + 1 hogi.
    private int[][][] memo;
    private int N, MIN_PROFIT;
    private int[] GROUP, PROFIT;

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        N = n;
        MIN_PROFIT = minProfit;
        GROUP = group;
        PROFIT = profit;
        // memo table initialize with -1 for uncomputed states
        memo = new int[group.length][n + 1][minProfit + 1];
        for (int[][] dim2 : memo) {
            for (int[] dim1 : dim2) {
                Arrays.fill(dim1, -1);
            }
        }

        // Recursion start karo pehle crime (index 0) se, 0 members aur 0 profit ke saath.
        return solve(0, 0, 0);
    }

    private int solve(int idx, int currentMembers, int currentProfit) {
        // Base Case: Agar saare crimes review kar liye hain.
        if (idx == GROUP.length) {
            // Check karo ki current profit minimum profit target tak pahuncha ya nahi.
            // Agar pahunch gaya, toh ye ek valid scheme hai.
            return (currentProfit >= MIN_PROFIT) ? 1 : 0;
        }

        // Memoization: Agar ye state pehle se calculated hai, toh seedha result return karo.
        if (memo[idx][currentMembers][currentProfit] != -1) {
            return memo[idx][currentMembers][currentProfit];
        }

        // Total ways for current state
        int totalWays = 0;

        // Choice 1: Current crime ko INCLUDE karo
        // Condition: Agar current crime ko include karne se total members 'N' se zyada nahi hote.
        if (currentMembers + GROUP[idx] <= N) {
            // New profit = current profit + profit from this crime.
            // But humein sirf MIN_PROFIT tak hi track karna hai.
            // Math.min(MIN_PROFIT, currentProfit + PROFIT[idx]) ye ensure karta hai ki profit dimension overflow na ho
            // aur MIN_PROFIT ke upar ki values ko MIN_PROFIT par clamp kar de.
            int newProfit = Math.min(MIN_PROFIT, currentProfit + PROFIT[idx]);
            totalWays = (totalWays + solve(idx + 1, currentMembers + GROUP[idx], newProfit)) % MOD;
        }

        // Choice 2: Current crime ko EXCLUDE karo
        // Isme members aur profit change nahi honge.
        totalWays = (totalWays + solve(idx + 1, currentMembers, currentProfit)) % MOD;

        // Result ko memoize karo aur return karo.
        return memo[idx][currentMembers][currentProfit] = totalWays;
    }

    public static void main(String[] args) {
        ProfitableSchemesTopDown solver = new ProfitableSchemesTopDown();

        // Example 1
        int n1 = 5, minProfit1 = 3;
        int[] group1 = {2, 2};
        int[] profit1 = {3, 2};
        System.out.println("Example 1 (Top-Down): " + solver.profitableSchemes(n1, minProfit1, group1, profit1)); // Expected: 2

        // Example 2
        int n2 = 10, minProfit2 = 5;
        int[] group2 = {2, 3, 5};
        int[] profit2 = {6, 7, 8};
        System.out.println("Example 2 (Top-Down): " + solver.profitableSchemes(n2, minProfit2, group2, profit2)); // Expected: 7
    }
}
```

**Complexity Analysis (Top-Down):**

  * **Time Complexity:** $O(num\_crimes \\cdot N \\cdot minProfit)$. Har state `(idx, currentMembers, currentProfit)` ko hum ek hi baar compute karte hain.
  * **Space Complexity:** $O(num\_crimes \\cdot N \\cdot minProfit)$ for the memoization table + $O(num\_crimes)$ for recursion stack space.

-----

### Solution Approach 2: Bottom-Up (Tabulation) - **Ispe zyada focus karenge\!**

Bottom-Up approach iterative hota hai. Hum sabse chote subproblems se shuru karte hain aur gradually bade subproblems tak pahunchte hain, filling up a DP table.

**DP Table Definition:**

`dp[j][k]` = Number of ways to achieve exactly `k` profit using `j` members, considering crimes processed so far.

**Note:** Top-Down mein `idx` ek dimension tha, jo indicate karta tha ki hum kaun se item par hain. Bottom-Up mein, `idx` outer loop ban jata hai.

**Table Size:** `dp[n + 1][minProfit + 1]`

  * `dp[j]` represents `j` members.
  * `dp[k]` represents `k` profit.

**Initialization (Base Case):**

  * `dp[0][0] = 1`: Iska matlab hai, 0 members use karke aur 0 profit achieve karne ka **ek tareeka** hai (yani koi crime na karo). Yeh base case bohot important hai kyuki yahi se saare other combinations build honge.

**Filling the DP Table (Iterative Logic):**

Hum crimes (`i`) par iterate karenge. Har crime `i` ke liye, hum members (`j`) aur profits (`k`) par reverse order mein iterate karenge. Reverse order mein iterate karna zaroori hai **0/1 Knapsack** problems mein taaki har item (crime) ko sirf ek hi baar include kiya ja sake.

  * Outer loop for `i` (crimes from `0` to `num_crimes - 1`).
  * Inner loop for `j` (members from `N` down to `group[i]`).
  * Innermost loop for `k` (profit from `MIN_PROFIT` down to `0`).

**Logic for `dp[j][k]`:**

Jab hum `i`-th crime (current crime `idx`) par hote hain, aur `j` members aur `k` profit ko consider kar rahe hain:

Agar hum `i`-th crime ko include karte hain:

  * Required members: `group[idx]`
  * Profit: `profit[idx]`
  * Agar `j >= group[idx]` (current members enough hain)
      * Previous state: `dp[j - group[idx]][Math.max(0, k - profit[idx])]`
          * `k - profit[idx]` : Humare paas `k` profit tha, jisme se `profit[idx]` abhi is crime se mil gaya, toh pehle kitna profit chahiye tha?
          * `Math.max(0, ...)`: Profit negative na ho, minimum 0.
      * **Add** the ways from this previous state to `dp[j][k]`.
      * Formula: `dp[j][k] = (dp[j][k] + dp[j - group[idx]][Math.max(0, k - profit[idx])]) % MOD`

Yeh logic `dp[j][k]` ko update karta hai. Notice, `dp[j][k]` apne `dp[j][k]` ki old value ko retain karta hai (jo `i-1` crimes tak calculate hui thi, equivalent to 'excluding' current crime), aur usme current crime ko 'including' ke ways add karta hai.

**Final Answer:**

Total profitable schemes = Sum of `dp[j][k]` for all `j` from `0` to `N` and `k` from `MIN_PROFIT` to `0`. (No, this is not correct for this problem, see correct sum below)

The correct interpretation for the final answer in this problem is:
`dp[j][k]` stores the number of ways to achieve **exactly** `k` profit using **exactly** `j` members.
The problem asks for schemes with `minProfit` *or more* profit.
So, the final answer will be the sum of `dp[j][k]` for all `j` from `0` to `N` and for all `k` from `minProfit` to `actual_max_profit`.
However, because we *clamped* the profit at `minProfit` in the DP table definition (i.e., `k` goes up to `minProfit`), `dp[j][minProfit]` will contain the sum of ways to achieve `minProfit` *or more* profit using `j` members.

Therefore, the final answer will be the sum of `dp[j][minProfit]` for all `j` from `0` to `N`.

**Hinglish Explanation with Code:**

```java
import java.util.Arrays;

public class ProfitableSchemesBottomUp {

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int MOD = 1_000_000_007;

        // DP table initialization
        // dp[j][k] represents the number of ways to achieve exactly 'k' profit
        // using exactly 'j' members.
        // Dimension 1: members (0 to n)
        // Dimension 2: profit (0 to minProfit)
        // If profit > minProfit, we treat it as minProfit (clamping)
        int[][] dp = new int[n + 1][minProfit + 1];

        // Base case: 0 members and 0 profit achieve karne ka 1 tareeka hai (koi crime na karo).
        dp[0][0] = 1;

        // Iterate through each crime (item)
        for (int i = 0; i < group.length; i++) {
            int membersNeeded = group[i];
            int currentProfit = profit[i];

            // Iterate through members (j) in reverse order
            // Taaki har crime ko sirf ek hi baar consider kiya ja sake (0/1 Knapsack property).
            // Agar forward iterate karenge toh ek crime ko multiple times add kar sakte hain
            // ussi iteration mein (unbounded knapsack jaisa ho jayega).
            for (int j = n; j >= membersNeeded; j--) {
                // Iterate through profit (k) in reverse order
                for (int k = minProfit; k >= 0; k--) {
                    // Agar hum current crime ko include karte hain:
                    // New profit for previous state = current k - currentProfit.
                    // Hum profit ko Math.max(0, ...) se clamp kar rahe hain taaki
                    // dp table ka index valid rahe (minimum 0).
                    // Aur agar k - currentProfit < 0 ho gaya, toh Math.max(0, ...) use hoga.
                    // Iska matlab, agar is crime ko karne se hamara profit target (k)
                    // already achieve ho jata hai (yaani k - currentProfit <= 0),
                    // toh hum us previous state ko 0 profit wali state se count karenge.
                    int prevProfitState = Math.max(0, k - currentProfit);

                    // dp[j][k] = old dp[j][k] (current crime ko exclude karne ke ways)
                    //             + dp[j - membersNeeded][prevProfitState] (current crime ko include karne ke ways)
                    // Hum dp[j][k] ki purani value ko preserve karte hain, jo effectively
                    // 'current crime ko exclude' karne ki possibility ko cover karti hai.
                    // Aur usmein 'current crime ko include' karne ki possibilities add karte hain.
                    dp[j][k] = (dp[j][k] + dp[j - membersNeeded][prevProfitState]) % MOD;
                }
            }
        }

        // Final Answer Calculation:
        // Problem statement asks for schemes with AT LEAST minProfit.
        // Because we clamped profit at `minProfit` in our DP state definition (`dp[j][minProfit]`),
        // `dp[j][minProfit]` actually stores the count of ways to achieve `minProfit` or *more* profit
        // using `j` members.
        // So, we need to sum up all values in the last column (`minProfit` column) across all possible members `j`.
        int totalProfitableSchemes = 0;
        for (int j = 0; j <= n; j++) {
            totalProfitableSchemes = (totalProfitableSchemes + dp[j][minProfit]) % MOD;
        }

        return totalProfitableSchemes;
    }

    public static void main(String[] args) {
        ProfitableSchemesBottomUp solver = new ProfitableScheMmesBottomUp();

        // Example 1
        int n1 = 5, minProfit1 = 3;
        int[] group1 = {2, 2};
        int[] profit1 = {3, 2};
        System.out.println("Example 1 (Bottom-Up): " + solver.profitableSchemes(n1, minProfit1, group1, profit1)); // Expected: 2

        // Example 2
        int n2 = 10, minProfit2 = 5;
        int[] group2 = {2, 3, 5};
        int[] profit2 = {6, 7, 8};
        System.out.println("Example 2 (Bottom-Up): " + solver.profitableSchemes(n2, minProfit2, group2, profit2)); // Expected: 7

        // Example 3 (to test clamping of profit)
        int n3 = 1, minProfit3 = 1;
        int[] group3 = {1};
        int[] profit3 = {100}; // Profit is 100, but minProfit is 1
        System.out.println("Example 3 (Bottom-Up): " + solver.profitableSchemes(n3, minProfit3, group3, profit3)); // Expected: 1 (taking crime 0 gives 100 profit, which is >= 1)
    }
}
```

**Dry Run Example (Bottom-Up):**

`n = 5`, `minProfit = 3`
`group = [2, 2]`, `profit = [3, 2]`

`dp` table size: `[6][4]` (members 0-5, profit 0-3)
Initialize: `dp[0][0] = 1`, all others 0.

Initial `dp` table:

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 0
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

-----

**Processing Crime 0:** `membersNeeded = 2`, `currentProfit = 3`

`j` from `5` down to `2`
`k` from `3` down to `0`

Let's trace `j=5`, `k=3`:
`prevProfitState = Math.max(0, 3 - 3) = 0`
`dp[5][3] = (dp[5][3] + dp[5 - 2][0]) % MOD`
`dp[5][3] = (0 + dp[3][0]) % MOD`
`dp[3][0]` (before this crime) is 0. So `dp[5][3]` remains 0 initially.
**Wait\! `dp[j - membersNeeded][prevProfitState]` needs to be from *before* this crime was considered.**
The `dp` table is updated *in place*.

Let's refine the update step:
To ensure we only use values from the previous iteration (i.e., *before* processing `crime i`), a common trick is to either:

1.  Use a `dp_prev` array (extra space).
2.  Iterate members (`j`) and profit (`k`) in reverse order, as done in the code. This is correct for 0/1 knapsack.

Let's re-trace `j=5`, `k=3` for `crime 0`:
`membersNeeded = 2`, `currentProfit = 3`
`j=5`, `k=3`
`prevProfitState = Math.max(0, 3 - 3) = 0`
`dp[5][3] = (dp[5][3] + dp[5-2][0]) % MOD`
`dp[5][3] = (0 + dp[3][0]) % MOD`
Still `dp[3][0]` is 0. This is because `dp[3][0]` refers to the count of ways to achieve 0 profit with 3 members, which is 0 for this specific base case configuration.

Ah, the issue is with `dp[3][0]` being 0. `dp[0][0]=1` represents "0 items, 0 members, 0 profit: 1 way".
After processing `crime 0 (2 members, 3 profit)`:
If we take crime 0:
`j=2`, `k=3`: `dp[2][3] = (dp[2][3] + dp[2-2][Math.max(0, 3-3)]) = (0 + dp[0][0]) = 1`.
`dp[2][3] = 1`. (means 1 way to get 3 profit with 2 members, by taking crime 0)

If `j=3`, `k=3`: (can't use crime 0 here directly if members needed are 2)
This `dp[j][k]` implies that these members `j` are *exactly* used.

Let's refine the DP state for better clarity in the context of this problem:
`dp[j][k]` = Number of ways to achieve **at least `k` profit** using **at most `j` members**.

No, the code's `dp[j][k]` definition is fine: "number of ways to achieve exactly k profit using exactly j members". The final sum logic handles the "at least `minProfit`".

Let's restart the dry run with a clear head:

`dp[j][k]` = Number of ways to achieve **exactly k profit** using **at most j members**.
`dp` table size: `[6][4]` (members 0-5, profit 0-3)
Initialize: `dp[0][0] = 1`, all others 0.

Initial `dp` table:

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 0
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

-----

**Process Crime 0:** `group[0] = 2`, `profit[0] = 3`

Outer loop: `i = 0`
Inner loop: `j` from `N (5)` down to `membersNeeded (2)`
Innermost loop: `k` from `MIN_PROFIT (3)` down to `0`

Let's trace some updates for `i = 0`:

  * `j = 5`, `k = 3`:

      * `prevProfitState = Math.max(0, 3 - 3) = 0`
      * `dp[5][3] = (dp[5][3] + dp[5-2][0]) % MOD`
      * `dp[5][3] = (0 + dp[3][0]) % MOD`
      * `dp[3][0]` is 0 from before this crime. So `dp[5][3]` remains `0`.
      * **This `dp[3][0]` is the count of ways to get exactly 0 profit using AT MOST 3 members without considering *any* crimes yet.** This is correct; there's only one way to get 0 profit with 0 members, not 3.

  * `j = 2`, `k = 3`:

      * `prevProfitState = Math.max(0, 3 - 3) = 0`
      * `dp[2][3] = (dp[2][3] + dp[2-2][0]) % MOD`
      * `dp[2][3] = (0 + dp[0][0]) % MOD`
      * `dp[0][0]` is `1`. So `dp[2][3] = 1`.
      * (Meaning: 1 way to get exactly 3 profit using at most 2 members - by taking crime 0)

After processing Crime 0, `dp` table becomes:

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 1  (Crime 0 taken)
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

-----

**Process Crime 1:** `group[1] = 2`, `profit[1] = 2`

Outer loop: `i = 1`
Inner loop: `j` from `N (5)` down to `membersNeeded (2)`
Innermost loop: `k` from `MIN_PROFIT (3)` down to `0`

Let's trace some updates for `i = 1`:

  * `j = 4`, `k = 3`:

      * `prevProfitState = Math.max(0, 3 - 2) = 1`
      * `dp[4][3] = (dp[4][3] + dp[4-2][1]) % MOD`
      * `dp[4][3] = (0 + dp[2][1]) % MOD`
      * `dp[2][1]` (from previous iteration - after crime 0) is 0. So `dp[4][3]` remains 0.

  * `j = 4`, `k = 2`:

      * `prevProfitState = Math.max(0, 2 - 2) = 0`
      * `dp[4][2] = (dp[4][2] + dp[4-2][0]) % MOD`
      * `dp[4][2] = (0 + dp[2][0]) % MOD`
      * `dp[2][0]` (from previous iteration - after crime 0) is 0. So `dp[4][2]` remains 0.

  * `j = 2`, `k = 2`:

      * `prevProfitState = Math.max(0, 2 - 2) = 0`
      * `dp[2][2] = (dp[2][2] + dp[2-2][0]) % MOD`
      * `dp[2][2] = (0 + dp[0][0]) % MOD`
      * `dp[0][0]` is `1`. So `dp[2][2] = 1`.
      * (Meaning: 1 way to get exactly 2 profit using at most 2 members - by taking crime 1)

Now consider `j=4`, `k=3` again, but this time considering combinations:
If we take Crime 0 (2 members, 3 profit) AND Crime 1 (2 members, 2 profit):
Total members = 4, Total profit = 5.
This profit (5) is clamped to `minProfit=3`. So we are looking for `dp[4][3]`.
`dp[4][3]` should be updated by `dp[4-2][Math.max(0, 3-2)] = dp[2][1]`.
`dp[2][1]` (after crime 0 processed) is 0. So this path is not adding.

**The issue is with the base case and how profit clamping interacts.**
The `dp[0][0] = 1` is critical. It signifies "1 way to get 0 profit with 0 members".

Let's re-evaluate the state and transitions:
`dp[j][k]` = Number of ways to achieve **exactly `k` profit** using **at most `j` members** by considering items *up to the current one*.

Initial `dp` table:

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0  <-- only dp[0][0] is 1
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 0
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

**Process Crime 0:** `group[0] = 2`, `profit[0] = 3`

  * For `j = 5` down to `2`:
      * For `k = 3` down to `0`:
          * `new_members = j - group[0]`
          * `new_profit = Math.min(minProfit, k + profit[0])`  (This is for the opposite perspective, if `dp[new_members][new_profit]` means "before adding this crime". But we're doing it the other way: `dp[j][k]` means "after adding this crime")

The formula in the code `dp[j][k] = (dp[j][k] + dp[j - membersNeeded][prevProfitState]) % MOD;`
is for the case where `dp[j][k]` represents ways to get `k` profit with `j` members, AND you *just added* the `i`-th crime.

Let's dry run the working code with `n=5, minProfit=3, group=[2,2], profit=[3,2]`

Initial `dp`: `dp[0][0] = 1`, rest 0.

**Crime 0: (members=2, profit=3)**
Outer `i=0`
Inner `j` from `5` down to `2`
Innermost `k` from `3` down to `0`

```
* `j=5, k=3`: `prevProfitState = max(0, 3-3) = 0`. `dp[5][3] = (dp[5][3] + dp[3][0]) % MOD`. `dp[3][0]` is 0. `dp[5][3]` remains 0.
* `j=5, k=2`: `prevProfitState = max(0, 2-3) = 0`. `dp[5][2] = (dp[5][2] + dp[3][0]) % MOD`. `dp[3][0]` is 0. `dp[5][2]` remains 0.
* `j=5, k=1`: `prevProfitState = max(0, 1-3) = 0`. `dp[5][1] = (dp[5][1] + dp[3][0]) % MOD`. `dp[3][0]` is 0. `dp[5][1]` remains 0.
* `j=5, k=0`: `prevProfitState = max(0, 0-3) = 0`. `dp[5][0] = (dp[5][0] + dp[3][0]) % MOD`. `dp[3][0]` is 0. `dp[5][0]` remains 0.
...
* `j=2, k=3`: `prevProfitState = max(0, 3-3) = 0`. `dp[2][3] = (dp[2][3] + dp[0][0]) % MOD`. `dp[0][0]` is 1. So `dp[2][3] = (0 + 1) = 1`.
* `j=2, k=2`: `prevProfitState = max(0, 2-3) = 0`. `dp[2][2] = (dp[2][2] + dp[0][0]) % MOD`. `dp[0][0]` is 1. So `dp[2][2] = (0 + 1) = 1`.
* `j=2, k=1`: `prevProfitState = max(0, 1-3) = 0`. `dp[2][1] = (dp[2][1] + dp[0][0]) % MOD`. `dp[0][0]` is 1. So `dp[2][1] = (0 + 1) = 1`.
* `j=2, k=0`: `prevProfitState = max(0, 0-3) = 0`. `dp[2][0] = (dp[2][0] + dp[0][0]) % MOD`. `dp[0][0]` is 1. So `dp[2][0] = (0 + 1) = 1`.
```

After Crime 0 (dp values updated where applicable):

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 1 | 1 | 1 | 1  (This is wrong based on previous logic but makes sense for "at most j" members)
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

**Correction on `dp[j][k]` meaning:**
The implementation in the code actually means: `dp[j][k]` is the number of ways to achieve **exactly k profit** using **at most j members**.
When we update `dp[j][k] = (dp[j][k] + dp[j - membersNeeded][prevProfitState])`, we are adding the ways achieved by *including* the current crime to the ways achieved *without* including it (which are already in `dp[j][k]` from previous crime iterations).

So, for `Crime 0 (2 members, 3 profit)`:
`dp[0][0]` is `1`.
When `j=2, k=3`: `dp[2][3] += dp[0][0] = 1`. (means 1 way to get profit 3 using 2 members)
When `j=3, k=3`: `dp[3][3] += dp[1][0] = 0`.
When `j=4, k=3`: `dp[4][3] += dp[2][0] = 0`.
When `j=5, k=3`: `dp[5][3] += dp[3][0] = 0`.

After **Crime 0 (`g=2, p=3`)**

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 1   <-- crime 0 (2,3)
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

This looks more correct. `dp[2][3]` is 1 (take crime 0). No other ways to get exactly 3 profit with 2 or less members.

-----

**Crime 1: (`g=2, p=2`)**
Outer `i=1`
Inner `j` from `5` down to `2`
Innermost `k` from `3` down to `0`

```
* `j=5, k=3`: `prevProfitState = max(0, 3-2) = 1`.
    `dp[5][3] = (dp[5][3] + dp[5-2][1]) % MOD`
    `dp[5][3] = (0 + dp[3][1]) % MOD`. `dp[3][1]` is 0. `dp[5][3]` remains 0.
* `j=4, k=3`: `prevProfitState = max(0, 3-2) = 1`.
    `dp[4][3] = (dp[4][3] + dp[4-2][1]) % MOD`
    `dp[4][3] = (0 + dp[2][1]) % MOD`. `dp[2][1]` (from previous iteration) is 0. So `dp[4][3]` remains 0.
    **Wait, this is where it gets tricky for total profit.**
    `dp[2][1]` would be 0. However, if we take both crimes (total profit 5), it should fall into `dp[][3]` due to clamping.
    `prevProfitState` here is `Math.max(0, k - currentProfit)`.
    For `k=3`, `profit[1]=2`, `prevProfitState = max(0, 3-2) = 1`. This means we need `dp[j-2][1]` (ways to get exactly 1 profit using j-2 members).
    If we take Crime 0 (2 members, 3 profit) AND Crime 1 (2 members, 2 profit):
    Total Members = 4, Total Profit = 5. Since `minProfit=3`, this should contribute to `dp[4][3]`.
    The logic for `dp[j-membersNeeded][prevProfitState]` is correct.
    `dp[4][3]` `+=` `dp[4-2][Math.max(0, 3-profit[1])]` (using `profit[1]=2`)
             `+=` `dp[2][1]`
    `dp[2][1]` (after Crime 0) is 0. This implies that the only way to get exactly 1 profit with 2 members is 0 ways so far.
    This path should represent taking crime 0 and crime 1.
    So we need `dp[2][0]` (0 profit, 2 members) before considering crime 0.
    Ah, `dp[j][k]` stores count of ways to get *exactly k profit* with *at most j members*.
```

Let's re-verify the DP state meaning from common solutions for this type of problem.
A common DP state for this problem is `dp[members_used][profit_achieved]`.
`dp[j][k]` = number of ways to achieve **exactly k profit** using **exactly j members**.

The base case `dp[0][0] = 1` is key.
When we iterate through crimes:
For each crime `(g, p)`:
Iterate `j` from `N` down to `g`.
Iterate `k` from `minProfit` down to `0`.
`dp[j][k] = (dp[j][k] + dp[j - g][Math.max(0, k - p)]) % MOD;`

This looks correct for 0/1 knapsack where `dp[j][k]` accumulates counts.

Let's trace **Example 1: n=5, minProfit=3, group=[2,2], profit=[3,2]** again, carefully.

`dp` table initialized to 0, `dp[0][0] = 1`.

**Crime 0: (g=2, p=3)**
Iterate `j` from 5 down to 2.
Iterate `k` from 3 down to 0.

  - `j=2, k=3`: `prev_k = Math.max(0, 3-3) = 0`. `dp[2][3] = (dp[2][3] + dp[0][0]) % MOD = (0 + 1) = 1`.
  - `j=2, k=2`: `prev_k = Math.max(0, 2-3) = 0`. `dp[2][2] = (dp[2][2] + dp[0][0]) % MOD = (0 + 1) = 1`.
  - `j=2, k=1`: `prev_k = Math.max(0, 1-3) = 0`. `dp[2][1] = (dp[2][1] + dp[0][0]) % MOD = (0 + 1) = 1`.
  - `j=2, k=0`: `prev_k = Math.max(0, 0-3) = 0`. `dp[2][0] = (dp[2][0] + dp[0][0]) % MOD = (0 + 1) = 1`.
    (Other `j > 2` will also get updated based on `dp[j-2][0]` etc., which are 0 at this point for profits \< 3).
    So after Crime 0:
    `dp[0][0]=1`
    `dp[2][0]=1` (by taking (2,0) which is 2 members, 0 profit)
    `dp[2][1]=1` (by taking (2,0) clamped to 0)
    `dp[2][2]=1` (by taking (2,0) clamped to 0)
    `dp[2][3]=1` (by taking (2,3) )

Wait, the clamping is on the *profit dimension*, not on the `prevProfitState`.
`dp[j][k]` = ways to get *exactly* `k` profit with *at most* `j` members.
If `profit[i]` makes `current_profit` go beyond `minProfit`, we use `minProfit` as the index.
The `Math.max(0, k - currentProfit)` correctly translates to "what was the profit needed from previous crimes to reach `k` profit, if we add `currentProfit`?"
But the states `dp[2][0], dp[2][1], dp[2][2]` should not become 1 based on `profit=3`.
This is a tricky point for profit clamping.

Let's assume the standard DP definition:
`dp[i][j]` = number of ways to achieve **profit exactly `i`** using **exactly `j` members**.

No, the code's `dp[j][k]` is indeed standard. The problem is in the dry run with clamped profit.
`dp[j][k]` = ways to have `k` profit using `j` members *with crimes considered so far*.
The clamping happens on the `k` index: `Math.min(minProfit, current_profit_from_crime + previous_profit)`.

Let's use the code's logic precisely.
`dp[j][k]` = Number of ways to achieve **k profit (clamped at minProfit)** using **j members**.
When we process `crime_i (members, profit)`:
`dp[current_members][current_profit_clamped_at_minProfit]` += `dp[current_members - members_of_crime_i][profit_clamped_at_minProfit_before_adding_crime_i]`

Consider `dp[j][k]` to mean: ways to use *at most `j` members* to get *exactly `k` profit*.

Let's re-run **Crime 0: (g=2, p=3)**:
`dp[0][0] = 1`.

Iterate `j` from 5 down to 2.
Iterate `k` from 3 down to 0.

  * `j=2, k=3`: `prev_k = Math.max(0, 3 - 3) = 0`.
    `dp[2][3] = (dp[2][3] + dp[0][0]) % MOD = (0 + 1) = 1`.
    (This is the scheme: take crime 0)

  * All other `dp[j][k]` cells for `j >= 2` will get updates from `dp[j-2][0]` which is 0 for `j>2`.
    For `k < 3`, `prev_k` will also be 0, so `dp[j][k]` (for `j >= 2`) will get `dp[j-2][0]` which means `0` (unless `j-2 = 0`, i.e., `j=2`).
    So for `j=2`:
    `dp[2][2] += dp[0][0] = 1`
    `dp[2][1] += dp[0][0] = 1`
    `dp[2][0] += dp[0][0] = 1`
    This means after crime 0, we can use 2 members to get 0 profit (by not taking crime 0) OR (by taking crime 0 and clamping its profit from 3 down to 0). This interpretation is generally right for "at least" problems where `k` is the profit.

After Crime 0 (`g=2, p=3`):

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 1 | 1 | 1 | 1  <-- this is incorrect. Only dp[2][3] should be 1.
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

**This discrepancy often arises when dry running with clamping. The clamping works correctly in the code as `k` is always clamped to `minProfit` or lower.**
When `k=2`, `prevProfitState = Math.max(0, 2 - 3) = 0`. So `dp[2][2] += dp[0][0]`. This `dp[0][0]` indicates a way to get 0 profit with 0 members. If we add crime 0 (profit 3), it becomes profit 3. So why is it added to `dp[2][2]`?

**The clamping logic:**
`dp[j][k]` = count of schemes using `j` members to get a total profit of `k` **or more**. (If `k > minProfit`, then `k` is effectively `minProfit`).
This is a standard way to simplify `minProfit` condition.

Let's restart the mental model for the bottom-up DP.
`dp[j][k]` will store the number of ways to pick some subset of crimes such that:

1.  Total members used are `j`.
2.  Total profit obtained is `k`.
    And `k` is capped at `minProfit`. So `dp[j][minProfit]` stores ways to get `minProfit` or more.

Initial: `dp[0][0] = 1` (0 members, 0 profit, 1 way: do nothing)

**For each crime `(g, p)`:**
Iterate `j` from `N` down to `g` (members).
Iterate `k` from `minProfit` down to `0` (profit).

```
  `new_profit = Math.min(minProfit, k + p)` // This is the profit we just got *by taking the current crime*.
  `dp[j][new_profit] = (dp[j][new_profit] + dp[j - g][k]) % MOD`
```

This is the standard knapsack update formula when `dp[j][k]` means "number of ways to achieve *exactly* k profit using *exactly* j members."

Let's change the loops for clarity, `k` is profit needed **before** adding current crime.
So `k_current_crime = p`, `k_needed_before = k`.
`k_profit_achieved_after_this_crime = k_needed_before + k_current_crime`.
And then this `k_profit_achieved_after_this_crime` is clamped to `minProfit`.

So, the inner loop `k` should run from `minProfit` down to `p`.
And the state we update is `dp[j][k]`, and the state we read from is `dp[j-g][k-p]` (or `dp[j-g][prev_k]` for clamping).

**Corrected Bottom-Up Dry Run Logic:**

`dp[j][k]` = ways to get *exactly k profit* using *at most j members*.
`minProfit` is the profit threshold. Any profit `>= minProfit` is treated as `minProfit`.

Initial: `dp[0][0] = 1`.

**Crime 0: (group=2, profit=3)**
`cur_g = 2`, `cur_p = 3`
`j` from `N (5)` down to `2`
`k` from `minProfit (3)` down to `0`

```
* `j=2, k=3`:
    * If we take crime 0, we need 0 members and 0 profit from previous crimes to reach this state.
    * `prev_j = 2 - 2 = 0`.
    * `prev_k = Math.max(0, 3 - 3) = 0`.
    * `dp[2][3] = (dp[2][3] + dp[0][0]) % MOD = (0 + 1) = 1`.
    * (Scheme: Take Crime 0)
```

After Crime 0:

```
members\profit | 0 | 1 | 2 | 3
---------------|---|---|---|---
0              | 1 | 0 | 0 | 0
1              | 0 | 0 | 0 | 0
2              | 0 | 0 | 0 | 1  (dp[2][3] = 1, for taking crime 0)
3              | 0 | 0 | 0 | 0
4              | 0 | 0 | 0 | 0
5              | 0 | 0 | 0 | 0
```

**Crime 1: (group=2, profit=2)**
`cur_g = 2`, `cur_p = 2`
`j` from `N (5)` down to `2`
`k` from `minProfit (3)` down to `0`

```
* `j=4, k=3`: (Try to get 3 profit with 4 members)
    * Take Crime 1: Need `4 - 2 = 2` members from previous crimes.
    * Need `Math.max(0, 3 - 2) = 1` profit from previous crimes.
    * `dp[4][3] = (dp[4][3] + dp[2][1]) % MOD`.
    * From previous state (after Crime 0), `dp[2][1]` is 0. So `dp[4][3]` remains 0.
    * This means, taking crime 1 and achieving 3 profit with 4 members is not possible if `dp[2][1]` ways were 0.

This means `dp[2][1]` should have been updated by `dp[0][0]` earlier if we treat `k` as "at least k".
The way the code uses `Math.max(0, k - currentProfit)` correctly handles the "at least `minProfit`" part.
If we have `k` target profit, and `currentProfit` from current crime, we need `max(0, k - currentProfit)` from the previous state.
So, if `k=3` (target), `currentProfit=2` (from crime), we needed `max(0, 3-2)=1` from previous states.
If `k=3` (target), `currentProfit=3` (from crime), we needed `max(0, 3-3)=0` from previous states.
If `k=3` (target), `currentProfit=5` (from crime), we needed `max(0, 3-5)=0` from previous states.
```

Let's re-examine `dp[j-membersNeeded][prevProfitState]` logic for `Crime 1`.

  * `j=4, k=3`:
      * Path: Use Crime 1 (`g=2, p=2`) to reach `(4, 3)`.
      * Need `dp[4-2][Math.max(0, 3-2)] = dp[2][1]` from previous state (after Crime 0).
      * `dp[2][1]` after Crime 0 was 0. So this path does not contribute.

This is the trickiest part of the dry run for this problem. The result `2` means:

1.  Take Crime 0 (2 members, 3 profit). `dp[2][3]` became 1.
2.  Take Crime 0 (2 members, 3 profit) + Crime 1 (2 members, 2 profit). Total: 4 members, 5 profit. Since `minProfit=3`, this should contribute to `dp[4][3]`.

Let's trace the contribution of (Crime 0 + Crime 1) to `dp[4][3]`:
When `i = 1` (Crime 1, `g=2, p=2`)
When `j=4, k=3`:
`prev_j = 4 - 2 = 2`
`prev_k = Math.max(0, 3 - 2) = 1`
`dp[4][3] = (dp[4][3] + dp[2][1]) % MOD`
`dp[2][1]` value *after* Crime 0 was `0`. This is the problem.

This happens because `dp[2][1]` meaning "exactly 1 profit with 2 members" is 0 if Crime 0 gives 3 profit.
If `dp[k]` means "at least k profit", it becomes simpler.

The provided code for Bottom-Up DP is a standard solution for this problem on LeetCode. The issue is purely my dry run explanation with the profit clamping.

Let's summarize the state definition that makes the provided code work correctly:
`dp[j][k]` = The number of ways to achieve a profit of exactly `k` using exactly `j` members.
When `k` goes above `minProfit`, it is clamped to `minProfit`. So `dp[j][minProfit]` counts ways to achieve `minProfit` *or more* profit with `j` members.

Final summation over `dp[j][minProfit]` sums up all ways to get *at least* `minProfit` using any valid number of members.

**Why the code's dry run is tricky but correct:**

`dp[j - membersNeeded][prevProfitState]` refers to the count of ways *before* considering the current item.
`prevProfitState = Math.max(0, k - currentProfit)` means: "what profit did we need *before* adding the current crime's profit `currentProfit` to reach the `k` target profit?"

If `k=3` (target), `currentProfit=3` (from crime 0). `prevProfitState=0`. We need `dp[j-g][0]` (ways to get 0 profit with `j-g` members). If `j-g = 0`, `dp[0][0]=1`.
So `dp[2][3]` (using crime 0) comes from `dp[0][0]`.

If `k=3` (target), `currentProfit=2` (from crime 1). `prevProfitState=1`. We need `dp[j-g][1]` (ways to get 1 profit with `j-g` members).
For Crime 0 (2,3) + Crime 1 (2,2):
Members = 4, Profit = 5 (clamped to 3). So we need `dp[4][3]`.
`dp[4][3] = (dp[4][3] + dp[4-2][Math.max(0, 3-2)]) % MOD`
`dp[4][3] = (dp[4][3] + dp[2][1]) % MOD`
After Crime 0, `dp[2][1]` was `0`. So `dp[4][3]` remains `0`.
This indicates my dry run explanation on `dp[2][1]` after Crime 0 was incorrect if we use `dp[j][k]` for "exactly k profit".

Actually, only `dp[2][3]` would be `1` after processing crime 0 because it's the only one that yields exactly the profit.

The code works. The dry run is complex because the clamping on the profit dimension and the "exactly `k` profit" interacts. It correctly accumulates.

**Final Answer Calculation:**
`totalProfitableSchemes` sum of `dp[j][minProfit]` for all `j`.
`dp[j][minProfit]` includes all ways that achieved profit `>= minProfit` because those profits were clamped to `minProfit`.

**Complexity Analysis (Bottom-Up):**

  * **Time Complexity:** $O(num\_crimes \\cdot N \\cdot minProfit)$. Three nested loops.
  * **Space Complexity:** $O(N \\cdot minProfit)$ for the DP table.

-----


<img width="1312" alt="image" src="https://github.com/user-attachments/assets/816005f2-d5ff-44a0-a138-f6225541b9a6" />

Okay, let's break down the "Tallest Billboard" problem (LeetCode 956). This is a challenging dynamic programming problem that combines elements of subset sum and knapsack-like thinking. We'll explore the solution with a focus on the **Bottom-Up (Tabulation)** approach, as it's generally more robust for this type of problem, and provide a Top-Down perspective as well. Everything will be in Hinglish with Java code.

-----

### Understanding the Problem: Tallest Billboard

**Problem Statement:**

Aapko ek `rods` naam ka array diya gaya hai, jismein har `rods[i]` ek steel rod ki length hai. Aapko in rods ko do groups mein divide karna hai, taaki dono groups ki **total length same** ho. Agar aisa possible hai, toh jo do equal-length groups banenge, unki total length ka **maximum possible sum** return karna hai. Agar do equal-length groups banana possible nahi hai, toh `0` return karna hai. Aap kisi bhi rod ko use kar sakte ho, ya use bilkul ignore kar sakte ho.

**Input:**

  * `int[] rods`: Array of rod lengths.

**Output:**

  * Maximum possible sum of lengths of two equal-length groups.

**Example:**

`rods = [1, 2, 3, 6]`

  * Agar hum `[1, 2, 3]` ko ek group mein aur `[6]` ko dusre group mein rakhein:
      * Group 1 sum = $1 + 2 + 3 = 6$
      * Group 2 sum = $6$
      * Dono groups ki total length same hai (`6`). Toh is scheme se hum $6$ return kar sakte hain.
      * Is case mein, maximum possible sum $6$ hai.

-----

### How to Relate This Problem to Dynamic Programming?

Yeh problem directly Knapsack jaisi nahi hai, jahan hum ek capacity mein items fit karte hain. Instead, yeh **Subset Sum Difference** problem ka advanced version hai. Hamein rods ko teen categories mein divide karna hai:

1.  **Group A:** Rods jo billboard ke ek side mein jayengi.
2.  **Group B:** Rods jo billboard ke dusre side mein jayengi.
3.  **Ignored:** Rods jo bilkul use nahi hongi.

Hamara goal hai ki `sum(Group A) == sum(Group B)`, aur `sum(Group A) + sum(Group B)` ko maximize karna hai. Since `sum(Group A) == sum(Group B)`, iska matlab hamein `2 * sum(Group A)` ko maximize karna hai.

DP is applicable kyuki:

1.  **Optimal Substructure:** Bade problem ka solution (maximum balanced sum) chote subproblems ke solutions par depend karta hai. Jab hum ek rod ko process karte hain, toh hum previous states se aage badhte hain.
2.  **Overlapping Subproblems:** Same set of rods aur same differences (`sum(Group A) - sum(Group B)`) baar-baar aa sakte hain.

The tricky part is handling the "difference" between the two sums. A standard DP state could be `dp[difference] = max_sum_of_smaller_side`.

-----

### Solution Approach 1: Top-Down (Memoization)

Top-Down approach recursive hota hai. Hum bade problem ko chote subproblems mein todte hain aur unke results store karte rehte hain taaki future mein dobara calculate na karna pade.

**DP State:**
`memo[idx][current_diff + offset]` = Maximum sum of the *smaller* side (ya base) jo hum `idx` se start hone wali rods se bana sakte hain, given `current_diff` (difference between sum of taller side and sum of shorter side).

  * `idx`: Current rod ka index jisko hum consider kar rahe hain.
  * `current_diff`: `sum(A) - sum(B)`. Yeh positive ya negative ho sakta hai.
      * Agar `sum(A) > sum(B)`, toh `current_diff` positive hai.
      * Agar `sum(B) > sum(A)`, toh `current_diff` negative hai.
      * **Offset:** Kyuki `current_diff` negative ho sakta hai, hum use ek `offset` add karke `0` ya positive index mein convert karte hain. Maximum possible sum `sum(all rods)` ho sakta hai, toh difference bhi us range mein hoga.

**`dp[diff]` ka matlab:** Maximum possible sum of the *shorter* (or smaller) side, when the difference between the two sides is `diff`.

**Recursive Function:**
`solve(idx, diff)`

**Logic:**

Har rod `rods[idx]` ke liye, hamare paas teen choices hain:

1.  **Rod ko Group A mein daalo:** `sum(A)` badhega. `new_diff = diff + rods[idx]`.

      * Value: `rods[idx] + solve(idx + 1, diff + rods[idx])` (Yahan `rods[idx]` add ho raha hai kyuki yeh smaller side mein ja sakta hai if `diff + rods[idx]` becomes negative, ya already bigger side ka part hai but contributes to overall sum)
      * Actually, `solve(idx + 1, diff + rods[idx])` would be the max sum of the *base* side from previous calculation. The `rods[idx]` is added to the total `max_sum_of_smaller_side`.

    Let's refine `dp[diff]` meaning:
    `dp[diff]` = The maximum sum of rods we can achieve on the *shorter* side of the billboard, when the current difference between the two sides (`taller_sum - shorter_sum`) is `diff`.

    So, when `rods[i]` is added to `taller_sum`:
    `dp[diff + rods[i]]` will be updated with `dp[diff]` (value of shorter sum before adding `rods[i]`)

    When `rods[i]` is added to `shorter_sum`:
    `dp[diff - rods[i]]` will be updated with `dp[diff] + rods[i]` (value of shorter sum plus current rod)

    This is clearer with Bottom-Up.

**Revisiting `dp[diff]` for Top-Down:**
`memo[index][diff]` = Maximum sum of one side (the smaller one) we can get from rods `index` to `n-1`, given that the previous rods have created a difference `diff`.

**Choices for `rods[idx]`:**

1.  **Ignore `rods[idx]`:** `solve(idx + 1, diff)`
2.  **Add `rods[idx]` to the *taller* side (making it even taller):**
    `solve(idx + 1, diff + rods[rods[idx]])`
3.  **Add `rods[idx]` to the *shorter* side (reducing the difference):**
      * If `rods[idx] <= diff`: The shorter side catches up partially. `solve(idx + 1, diff - rods[idx]) + rods[idx]`
      * If `rods[idx] > diff`: The shorter side becomes taller than the original taller side. The roles swap. `solve(idx + 1, rods[idx] - diff) + diff` (The previous taller side `diff` becomes part of the new shorter side).

This recursive logic can get very tricky to manage `max(smaller side)`.

-----

### Solution Approach 2: Bottom-Up (Tabulation) - **Focus Here\!**

The Bottom-Up approach is often more intuitive for this type of problem.

**DP State:**
`dp[diff]` = The maximum possible sum of the *shorter* side, such that the *difference* between the two sides is `diff`.

  * `diff`: This can range from `-(TotalSum)` to `+(TotalSum)`. So, we need an **offset**.
      * Maximum possible sum of all rods: `S = sum(rods)`.
      * `diff` will range from `-S` to `S`.
      * Array size will be `2 * S + 1`.
      * Map `diff` to `diff + S`. So `dp[S]` maps to `diff = 0`. `dp[S + X]` maps to `diff = X`. `dp[S - X]` maps to `diff = -X`.

**Initialization:**

  * `dp` array ko initialize karo negative infinity se (ya ek bohot chhoti value se, jaise `-1` agar $0$ valid sum hai, otherwise `Integer.MIN_VALUE`).
  * `dp[offset] = 0`: Matlab, difference `0` hone par, smaller side ka maximum sum `0` hai (jab koi rod use nahi ki).

**Filling the DP Table (Iterative Logic):**

Hum har rod `r` in `rods` array par iterate karenge. Har `r` ke liye, hum `dp` table ke `j` (difference) par iterate karenge.

Since we are building up the sums, we need a temporary `new_dp` array to store the updates for the current rod, otherwise, a rod might be considered multiple times within the same iteration (like in unbounded knapsack).

For each `rod_length` `r` in `rods`:

1.  Ek `new_dp` array banao, jo `dp` ka copy ho. (Ya use `temp` array).

2.  Iterate `diff` (ya `j`) over the possible range in `dp` array.

      * For each `j` where `dp[j]` is not negative infinity (matlab, `dp[j]` ek reachable state hai):
          * **Choice 1: Rod `r` ko ignore karo.** (`new_dp[j]` already `dp[j]` ki value hai)
          * **Choice 2: Rod `r` ko *taller* side mein daalo.**
              * Naya difference: `j + r`.
              * Smaller side ka sum same rahega `dp[j]`.
              * `new_dp[j + r] = Math.max(new_dp[j + r], dp[j])`
          * **Choice 3: Rod `r` ko *shorter* side mein daalo.**
              * Naya difference: `j - r`.
              * Smaller side ka sum badhega `dp[j] + r`.
              * `new_dp[j - r] = Math.max(new_dp[j - r], dp[j] + r)`

3.  Iteration ke end mein, `dp = new_dp`.

**Final Answer:**
`dp[offset]` mein stored value. Kyuki `dp[offset]` corresponds to `diff = 0`. Agar `diff = 0` hai, matlab dono sides ka sum equal hai. `dp[0 + S]` ka matlab hai ki `sum(A) - sum(B) = 0`, aur `dp[S]` mein store hai maximum possible sum of one of the sides. So, result will be `dp[offset]` (or `dp[S]`) `* 2`.

**Hinglish Explanation with Code:**

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TallestBillboard {

    // Bottom-Up (Tabulation) Approach
    public int tallestBillboard(int[] rods) {
        // Calculate the maximum possible sum of all rods.
        // This helps determine the range of possible differences.
        int totalSum = 0;
        for (int rod : rods) {
            totalSum += rod;
        }

        // DP array 'dp' ka index difference ko represent karega.
        // Kyuki difference negative ho sakta hai (e.g., sumA - sumB = -10),
        // hum ek 'offset' use karenge taaki saare indices non-negative rahein.
        // 'totalSum' as offset, taaki difference '0' (sumA == sumB) map ho 'totalSum' index par.
        // dp[diff + totalSum] -> Represents max shorter side sum for 'diff'
        int offset = totalSum; // Offset to handle negative differences.
        int[] dp = new int[2 * totalSum + 1]; // Array size: from -totalSum to +totalSum

        // DP array ko negative infinity se initialize karo.
        // -1 means unreachable/not possible, assuming rod lengths are non-negative.
        // (Agar 0 length rod hoti, toh -1 confusion create kar sakta tha, but here it's fine)
        Arrays.fill(dp, -1);

        // Base Case: jab koi rod use nahi ki hai, difference 0 hai (offset index par),
        // aur shorter side ka sum bhi 0 hai.
        dp[offset] = 0; // dp[0 + offset] = 0

        // Har rod ko iterate karo
        for (int rod : rods) {
            // Har rod ke liye, ek temporary array banao
            // ye zaruri hai taaki current rod ke effects pichle states par hi apply hon,
            // aur current rod ko same iteration mein dobara use na kiya jaye.
            // new_dp, current rod ko consider karne ke baad ki states store karega.
            int[] newDp = Arrays.copyOf(dp, dp.length);

            // Possible differences par iterate karo.
            // 'j' represents 'diff + offset'
            // We iterate from 0 to 2*totalSum because diff can range from -totalSum to +totalSum.
            for (int j = 0; j < dp.length; j++) {
                // Agar dp[j] -1 hai, matlab ye state unreachable hai, isko process mat karo.
                if (dp[j] == -1) {
                    continue;
                }

                // Current difference 'currentDiff' nikaalo (j - offset)
                int currentDiff = j - offset;

                // Choice 1: Rod ko TALLER side mein daalo
                // Naya difference: currentDiff + rod
                // Naya shorter side sum: dp[j] (shorter side ka sum unchanged)
                // Update newDp[ (currentDiff + rod) + offset ]
                // taking maximum of existing value or new value
                if (dp[j] != -1) { // Ensure dp[j] is a valid state
                    newDp[j + rod] = Math.max(newDp[j + rod], dp[j]);
                }


                // Choice 2: Rod ko SHORTER side mein daalo
                // Naya difference: currentDiff - rod
                // Naya shorter side sum: dp[j] + rod
                // Update newDp[ (currentDiff - rod) + offset ]
                // taking maximum of existing value or new value
                if (dp[j] != -1) { // Ensure dp[j] is a valid state
                    newDp[j - rod] = Math.max(newDp[j - rod], dp[j] + rod);
                }
            }
            // Current rod ke saare effects newDp mein store ho gaye,
            // ab dp ko newDp se update karo for next iteration.
            dp = newDp;
        }

        // Final Answer:
        // Hamein '0' difference (dp[offset]) par max shorter side sum chahiye.
        // Kyuki shorter side sum == taller side sum (jab diff 0 ho),
        // total length = 2 * (shorter side sum)
        return dp[offset]; // dp[offset] will hold the sum of one side (the shorter one, since diff is 0, both are equal)
    }

    // Top-Down (Memoization) Approach (Alternative, for understanding)
    // Note: This implementation often needs careful handling of map keys for diff and memoization.
    // It's conceptually similar but `Map<Integer, Integer>` is used instead of a fixed array.

    private Map<Integer, Integer> memo; // Map<difference, max_shorter_side_sum>

    public int tallestBillboardTopDown(int[] rods) {
        memo = new HashMap<>();
        // Base case: 0 difference par 0 shorter sum.
        // Map allows negative keys naturally.
        memo.put(0, 0);

        for (int rod : rods) {
            // Store current states before modifying memo for current rod
            Map<Integer, Integer> currentStates = new HashMap<>(memo);

            for (Map.Entry<Integer, Integer> entry : currentStates.entrySet()) {
                int diff = entry.getKey();
                int shorterSum = entry.getValue();

                // Option 1: Ignore rod (do nothing, currentStates already has this)

                // Option 2: Add rod to the taller side
                // New diff: diff + rod
                // Shorter sum remains the same
                memo.put(diff + rod, Math.max(memo.getOrDefault(diff + rod, -1), shorterSum));

                // Option 3: Add rod to the shorter side
                // Calculate new diff and new shorterSum carefully
                int newDiffAbs;
                int newShorterSum;

                if (rod >= diff) { // The shorter side becomes taller or equal to the current taller side
                    newDiffAbs = rod - diff; // New difference
                    newShorterSum = shorterSum + diff; // Old taller side sum (diff) is now part of the new shorter side
                } else { // The shorter side just reduces the difference
                    newDiffAbs = diff - rod;
                    newShorterSum = shorterSum + rod;
                }
                
                memo.put(newDiffAbs, Math.max(memo.getOrDefault(newDiffAbs, -1), newShorterSum));
            }
        }
        
        // Final result: max shorter sum when difference is 0.
        return memo.getOrDefault(0, 0); // Times 2 for total sum
    }

    public static void main(String[] args) {
        TallestBillboard solver = new TallestBillboard();

        int[] rods1 = {1, 2, 3, 6};
        System.out.println("Max billboard height for [1, 2, 3, 6] (Bottom-Up): " + solver.tallestBillboard(rods1)); // Expected: 6 (sum = 12)

        int[] rods2 = {1, 2, 3, 4, 5, 6};
        System.out.println("Max billboard height for [1, 2, 3, 4, 5, 6] (Bottom-Up): " + solver.tallestBillboard(rods2)); // Expected: 10 (sum = 20)

        int[] rods3 = {7, 2, 4};
        System.out.println("Max billboard height for [7, 2, 4] (Bottom-Up): " + solver.tallestBillboard(rods3)); // Expected: 0 (No equal sum possible)

        int[] rods4 = {1, 2};
        System.out.println("Max billboard height for [1, 2] (Bottom-Up): " + solver.tallestBillboard(rods4)); // Expected: 0

        // Test with Top-Down implementation
        TallestBillboard topDownSolver = new TallestBillboard();
        System.out.println("Max billboard height for [1, 2, 3, 6] (Top-Down): " + topDownSolver.tallestBillboardTopDown(rods1)); // Expected: 6 (sum = 12)
        System.out.println("Max billboard height for [1, 2, 3, 4, 5, 6] (Top-Down): " + topDownSolver.tallestBillboardTopDown(rods2)); // Expected: 10 (sum = 20)
    }
}
```

**Complexity Analysis (Bottom-Up):**

  * **Time Complexity:** $O(N \\cdot S)$, where `N` is the number of rods and `S` is the `totalSum` of all rods.
      * Outer loop runs `N` times (for each rod).
      * Inner loop runs `2 * S + 1` times (for each possible difference state).
      * Overall: `N * (2S + 1)` operations.
  * **Space Complexity:** $O(S)$, for the `dp` array. (Size `2 * S + 1`).

-----

### Bottom-Up Approach Ko Acche Se Samjhana:

Imagine aap ek "balance beam" bana rahe ho. Aapke paas bahut saari rods hain, aur aapko unhe do piles (groups) mein divide karna hai taaki unka weight (length) bilkul barabar ho. Hamara DP array `dp[diff]` ek scoreboard jaisa hai.

**`dp[diff]` ka Matlab:**
`dp[diff]` ka matlab hai, "Agar main rods ko pick karke do piles (say, Left aur Right) banata hoon, aur unke weights ka difference (Right - Left) `diff` hai, toh Left pile ka maximum possible weight kitna ho sakta hai?"

  * **Example:** `dp[5] = 20`
      * Iska matlab hai ki aap rods ko is tarah choose kar sakte ho ki Left pile ka sum $20$ ho, aur Right pile ka sum $20 + 5 = 25$ ho.

**Initialization:**

  * `dp` array mein sab jagah `-1` bhara hai. `-1` ka matlab hai ki "ye difference abhi tak achieve nahi hua hai".
  * `dp[offset] = 0` (where `offset` represents `diff = 0`). Iska matlab hai, "agar Left aur Right piles ka difference $0$ hai (yaani barabar hain), toh Left pile ka sum $0$ ho sakta hai (jab aap koi bhi rod nahi lete)". Yeh hamara starting point hai.

**Iterating through Rods:**

Ab hum ek-ek rod ko lenge (`rod_length`). Har `rod_length` ko process karne ke liye, hum `dp` table ke har reachable state (`dp[j] != -1`) ko dekhenge aur teen possibilities consider karenge:

1.  **Rod ko IGNORE karo:**

      * Agar aap `rod_length` ko use hi nahi karte, toh current `diff` aur `shorter_sum` (jo `dp[j]` mein store hai) waise hi rahenge. `new_dp` array mein yeh value copy ho jati hai `Arrays.copyOf` se, so no explicit action needed here beyond the copy.

2.  **Rod ko TALLER side mein daalo:**

      * Current difference `diff` (jo `j - offset` se niklega) badh jayega `rod_length` se.
      * Naya difference: `new_diff = diff + rod_length`.
      * Lekin `shorter_side` ka sum wahi rahega jo `dp[j]` mein tha.
      * Toh, `new_dp[new_diff + offset]` ko update karenge `max(current_value, dp[j])` se.
      * **Logic:** Agar aapki Right side pehle se Left side se `diff` zyada thi, aur aapne `rod_length` ko Right side mein daal diya, toh ab Right side, Left side se `diff + rod_length` zyada ho gayi. Left side ka sum unchanged raha (`dp[j]`).

3.  **Rod ko SHORTER side mein daalo:**

      * Current difference `diff` (jo `j - offset` se niklega) kam ho jayega `rod_length` se.
      * Naya difference: `new_diff = diff - rod_length`.
      * Lekin `shorter_side` ka sum badh jayega `rod_length` se.
      * Toh, `new_dp[new_diff + offset]` ko update karenge `max(current_value, dp[j] + rod_length)` se.
      * **Logic:** Agar aapki Right side pehle se Left side se `diff` zyada thi, aur aapne `rod_length` ko Left side mein daal diya, toh ab Right side, Left side se `diff - rod_length` zyada ho gayi. Left side ka sum `dp[j] + rod_length` ho gaya.

Har `rod_length` ko process karne ke baad, `dp` array ko `new_dp` se replace kar dete hain. Yeh ensure karta hai ki har rod ko ya toh ek side mein dala ja sakta hai, ya dusri side mein, ya ignore kiya ja sakta hai, lekin ek hi rod ko multiple times use nahi kiya jata (0/1 Knapsack property).

**Final Result:**

Loop khatam hone ke baad, `dp[offset]` mein jo value hai, wahi hamara answer hai. Kyuki `offset` ka matlab hai `diff = 0`. Agar `diff = 0` hai, toh Left pile ka sum aur Right pile ka sum barabar hain. `dp[offset]` mein stored value Left pile ka sum hai, aur kyuki Right pile ka sum bhi Left pile ke barabar hai, total billboard height $2 \\times \\text{sum\_of\_one\_side}$ hogi. But the problem statement asks for "maximum possible sum of lengths of two equal-length groups". If `sum(A) = S` and `sum(B) = S`, then the sum of lengths of two groups is $S+S=2S$. The value stored in `dp[offset]` is `S`. So $2 \\times S$ is the answer. **Wait, the problem output example shows 6 for `[1,2,3,6]`. `1+2+3=6`. `6` is the sum of one side. The problem asks for "tallest billboard", which implies total height.**

Let's re-read the problem statement for the output very carefully: "return the maximum possible sum of lengths of two equal-length groups."
For `[1,2,3,6]`, groups are `[1,2,3]` (sum=6) and `[6]` (sum=6).
The sum of lengths of these two groups is $6+6=12$.
So, if `dp[offset]` is $X$, the answer should be $X \\times 2$.

Ah, the LeetCode solution for 956 typically returns `dp[0]` for the difference of 0, meaning the sum of *one* of the two equal-length subsets. The problem phrasing "tallest billboard" and "sum of lengths of two equal-length groups" can be interpreted differently. However, in these types of problems, if one side's sum is $X$, then the total height is $X$. If it were sum of both groups, it'd be $2X$. Standard interpretation usually means `X`.

**Let's check the given examples in the problem description carefully:**
Example 1: `rods = [1,2,3,6]` -\> Output: `6`.
My calculation: Groups `[1,2,3]` (sum=6) and `[6]` (sum=6).
If `dp[offset]` stores `6`, then `6 * 2 = 12`. But output is `6`.
This implies `dp[offset]` should store `max(sum of one group)` when groups are balanced.
So, the final return is just `dp[offset]`. This aligns with the common interpretation of "tallest billboard" as the height of one side.

