
### 💡 DP ka Golden Rule (Memoization Template)
1. **Recursive function** socho (Faith/Rukhsat).
2. **Base Case** handle karo.
3. **DP Array Check:** Agar `dp[state] != -1` to return karo (Yahi Memoization hai).
4. **Choices/Diagrams** banao.
5. Answer `dp` mein store karo aur return karo.

---

### 🎯 1. 0/1 Knapsack (Item ya to lo ya chhodo)
**Problem:** `N` items, weight `wt[]`, value `val[]`, capacity `W`. Max value batao.
**DP State:** `dp[n][W]` -> Pehle `n` items aur `W` capacity ke saath max value.

```java
class Knapsack {
    static int knapSack(int W, int wt[], int val[], int n) {
        int[][] dp = new int[n + 1][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return solve(W, wt, val, n, dp);
    }

    static int solve(int W, int wt[], int val[], int n, int[][] dp) {
        // Base Case: Agar items khatam ya capacity khatam
        if (n == 0 || W == 0) return 0;

        if (dp[n][W] != -1) return dp[n][W];

        // Choice Diagram
        if (wt[n - 1] <= W) {
            // Ya to item LO, ya MAT LO
            int leliya = val[n - 1] + solve(W - wt[n - 1], wt, val, n - 1, dp);
            int nahi_liya = solve(W, wt, val, n - 1, dp);
            return dp[n][W] = Math.max(leliya, nahi_liya);
        } else {
            // Weight zyada hai, nahi le sakte
            return dp[n][W] = solve(W, wt, val, n - 1, dp);
        }
    }
}
```

### 🎯 2. Unbounded Knapsack (Ek item kitni baar bhi le sakte ho)
**Problem:** Rod Cutting, Coin Change (Max ways).
**Difference:** 0/1 mein `n-1` karte the, yahan `n` hi rehne dete hain agar item liya to.

```java
// Rod Cutting Problem
static int rodCutting(int[] price, int n) {
    int[] length = new int[n];
    for (int i = 0; i < n; i++) length[i] = i + 1;
    int[][] dp = new int[n + 1][n + 1];
    for (int[] row : dp) Arrays.fill(row, -1);
    return solve(price, length, n, n, dp);
}

static int solve(int[] val, int[] wt, int W, int n, int[][] dp) {
    if (n == 0 || W == 0) return 0;
    if (dp[n][W] != -1) return dp[n][W];
    
    if (wt[n - 1] <= W) {
        // Yahan n-1 nahi, 'n' rahega kyunki dobara le sakte hain
        return dp[n][W] = Math.max(val[n - 1] + solve(val, wt, W - wt[n - 1], n, dp),
                                   solve(val, wt, W, n - 1, dp));
    } else {
        return dp[n][W] = solve(val, wt, W, n - 1, dp);
    }
}
```

### 🎯 3. Longest Common Subsequence (LCS)
**Problem:** Do strings di hain `s1`, `s2`. Sabse lamba common subsequence dhundho.
**DP State:** `dp[i][j]` -> `s1` ke pehle `i` chars aur `s2` ke pehle `j` chars ka LCS.

```java
static int lcs(String s1, String s2) {
    int n = s1.length(), m = s2.length();
    int[][] dp = new int[n + 1][m + 1];
    for (int[] row : dp) Arrays.fill(row, -1);
    return solve(s1, s2, n, m, dp);
}

static int solve(String s1, String s2, int i, int j, int[][] dp) {
    // Base: Koi bhi string khatam
    if (i == 0 || j == 0) return 0;
    if (dp[i][j] != -1) return dp[i][j];
    
    // Agar character match karta hai
    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
        return dp[i][j] = 1 + solve(s1, s2, i - 1, j - 1, dp);
    } else {
        // Match nahi karta to ek baar s1 ka char hatao, ek baar s2 ka
        return dp[i][j] = Math.max(solve(s1, s2, i - 1, j, dp),
                                   solve(s1, s2, i, j - 1, dp));
    }
}
```

### 🎯 4. Longest Palindromic Subsequence (LPS)
**Problem:** String mein sabse lamba palindrome subsequence.
**Logic:** LCS hi hai. Bas `s1 = s` aur `s2 = reverse(s)` kar do.

```java
static int lps(String s) {
    String rev = new StringBuilder(s).reverse().toString();
    // LCS wala function call kar do
    return lcs(s, rev);
}
```

### 🎯 5. Matrix Chain Multiplication (MCM)
**Problem:** Matrices ka order aise set karo ki multiplication cost minimum ho.
**DP State:** `dp[i][j]` -> Matrix `i` se Matrix `j` tak multiply karne ki min cost.
**Pattern:** `i` ko `k` tak, aur `k+1` ko `j` tak break karo.

```java
static int matrixChainOrder(int[] arr, int i, int j, int[][] dp) {
    // Base Case: Single matrix, multiplication cost 0
    if (i >= j) return 0;
    if (dp[i][j] != -1) return dp[i][j];
    
    int min = Integer.MAX_VALUE;
    // K will run from i to j-1
    for (int k = i; k < j; k++) {
        int cost = matrixChainOrder(arr, i, k, dp) +
                   matrixChainOrder(arr, k + 1, j, dp) +
                   arr[i - 1] * arr[k] * arr[j]; // Dimensions cost
        min = Math.min(min, cost);
    }
    return dp[i][j] = min;
}
```

### 🎯 6. Edit Distance
**Problem:** String `s1` ko `s2` banane mein kitne operations (Insert, Delete, Replace).
**Logic:** Agar char same hai to `i-1, j-1`. Agar alag hai to 1 + min(Insert, Delete, Replace).

```java
static int editDist(String s1, String s2, int i, int j, int[][] dp) {
    // Base: Agar pehli string khatam, to baaki bachi hui s2 ke chars INSERT karo
    if (i == 0) return j;
    // Base: Agar doosri khatam, to baaki s1 ke chars DELETE karo
    if (j == 0) return i;
    
    if (dp[i][j] != -1) return dp[i][j];
    
    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
        return dp[i][j] = editDist(s1, s2, i - 1, j - 1, dp);
    } else {
        int insert = editDist(s1, s2, i, j - 1, dp);
        int delete = editDist(s1, s2, i - 1, j, dp);
        int replace = editDist(s1, s2, i - 1, j - 1, dp);
        return dp[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
    }
}
```

### 🎯 7. Coin Change (Min Number of Coins)
**Problem:** `amount` banane ke liye minimum kitne coins chahiye.
**Logic:** Unbounded Knapsack jaisa hai. Choice: Coin lo ya na lo. Minimize karna hai.

```java
static int coinChange(int[] coins, int amount, int n, int[][] dp) {
    // Base: Amount 0 ho gaya, 0 coins lagenge
    if (amount == 0) return 0;
    // Base: Coins khatam ya amount negative
    if (n == 0 || amount < 0) return Integer.MAX_VALUE - 1; // -1 overflow na kare isliye
    
    if (dp[n][amount] != -1) return dp[n][amount];
    
    if (coins[n - 1] <= amount) {
        int lelo = 1 + coinChange(coins, amount - coins[n - 1], n, dp);
        int mat_lo = coinChange(coins, amount, n - 1, dp);
        return dp[n][amount] = Math.min(lelo, mat_lo);
    } else {
        return dp[n][amount] = coinChange(coins, amount, n - 1, dp);
    }
}
```

### 🎯 8. Subset Sum Problem
**Problem:** Array mein aisa subset hai jiska sum `sum` ke barabar ho?
**Logic:** Knapsack jaisa hi hai, bas yahan Return Type `boolean` hai.

```java
static boolean subsetSum(int[] arr, int sum, int n, Boolean[][] dp) {
    // Base Cases
    if (sum == 0) return true;
    if (n == 0) return false;
    
    if (dp[n][sum] != null) return dp[n][sum];
    
    if (arr[n - 1] <= sum) {
        // Ya to lo, ya mat lo
        return dp[n][sum] = subsetSum(arr, sum - arr[n - 1], n - 1, dp) ||
                            subsetSum(arr, sum, n - 1, dp);
    } else {
        return dp[n][sum] = subsetSum(arr, sum, n - 1, dp);
    }
}
```

### 🎯 9. DP on Grids (Minimum Path Sum)
**Problem:** Grid ke Top-Left se Bottom-Right tak jaane ka min cost path.
**Logic:** Tum sirf Right aur Down ja sakte ho.

```java
static int minPathSum(int[][] grid, int i, int j, int[][] dp) {
    int rows = grid.length, cols = grid[0].length;
    // Base: Destination pe pahunch gaye
    if (i == rows - 1 && j == cols - 1) return grid[i][j];
    // Base: Boundary ke bahar (Invalid)
    if (i >= rows || j >= cols) return Integer.MAX_VALUE;
    
    if (dp[i][j] != -1) return dp[i][j];
    
    int right = minPathSum(grid, i, j + 1, dp);
    int down = minPathSum(grid, i + 1, j, dp);
    
    return dp[i][j] = grid[i][j] + Math.min(right, down);
}
```

### 🎯 10. DP on Stocks (Best Time to Buy & Sell)
**Problem:** Ek din buy, future mein sell. Max Profit. (Only 1 transaction allowed).
**Logic:** Ye simple hai bina DP ke bhi ho jata hai, lekin DP State sochna seekho: `dp[i][buy]` (buy=1 matlab buy kar sakte ho, 0 matlab sell karna hai).

```java
static int maxProfit(int[] prices) {
    int n = prices.length;
    int[][] dp = new int[n][2];
    for (int[] row : dp) Arrays.fill(row, -1);
    return solve(prices, 0, 1, dp); // 1 means 'can buy'
}

static int solve(int[] prices, int day, int buy, int[][] dp) {
    if (day >= prices.length) return 0; // Market band
    if (dp[day][buy] != -1) return dp[day][buy];
    
    int profit = 0;
    if (buy == 1) {
        // Choice: Aaj Buy karo (kal Sell kar sakte) ya Skip karo
        int buy_karo = -prices[day] + solve(prices, day + 1, 0, dp);
        int skip_karo = solve(prices, day + 1, 1, dp);
        profit = Math.max(buy_karo, skip_karo);
    } else {
        // Choice: Aaj Sell karo (Profit book) ya Skip karo
        int sell_karo = prices[day] + solve(prices, day + 1, 1, dp);
        int skip_karo = solve(prices, day + 1, 0, dp);
        profit = Math.max(sell_karo, skip_karo);
    }
    return dp[day][buy] = profit;
}
```

### 🎯 11. Kadane's Algorithm DP Style (Maximum Subarray)
**Problem:** Contiguous subarray with max sum.
**DP State:** `dp[i]` -> `i` pe khatam hone wala max subarray sum.

```java
static int maxSubArray(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    dp[0] = nums[0];
    int max = dp[0];
    
    for (int i = 1; i < n; i++) {
        // Choice: Pichle subarray se judo ya nayi shuruat karo
        dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
        max = Math.max(max, dp[i]);
    }
    return max;
}
```

### 🎯 12. DP on Trees (House Robber III)
**Problem:** Binary Tree mein adjacent nodes ko rob nahi kar sakte.
**Logic:** Har node ke liye do options: Ya to current node rob karo (aur grandchildren pe jao), ya skip karo (children pe jao). Memoization ke liye `HashMap<TreeNode, Integer>` use karo.

```java
static int rob(TreeNode root) {
    HashMap<TreeNode, Integer> dp = new HashMap<>();
    return solve(root, dp);
}

static int solve(TreeNode root, HashMap<TreeNode, Integer> dp) {
    if (root == null) return 0;
    if (dp.containsKey(root)) return dp.get(root);
    
    // Option 1: Current Node Rob Karo
    int robCurr = root.val;
    if (root.left != null) robCurr += solve(root.left.left, dp) + solve(root.left.right, dp);
    if (root.right != null) robCurr += solve(root.right.left, dp) + solve(root.right.right, dp);
    
    // Option 2: Current Node Skip Karo
    int skipCurr = solve(root.left, dp) + solve(root.right, dp);
    
    int ans = Math.max(robCurr, skipCurr);
    dp.put(root, ans);
    return ans;
}
```

### 🧠 Advanced DP Patterns (Mention only, code length zyada ho jayega)

| Pattern | Logic | Memoization Trick |
| :--- | :--- | :--- |
| **Partition DP** | String/Array ko `k` parts mein todo | `dp[i][k]` (Index aur Count) |
| **Bitmask DP** | Nodes/Items visited ka track rakhna | `dp[mask][pos]` (Mask represent state) |
| **Digit DP** | 1 to N tak count karna kuch condition ke saath | `dp[pos][tight][sum]` |
| **DP on Intervals** | `dp[i][j]` = i se j tak ka answer | MCM jaisa hi hai |
