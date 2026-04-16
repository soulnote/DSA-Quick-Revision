
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

---

## 1. **Wildcard Matching**  
**Problem:** `*` matches any sequence, `?` matches one char. Check if string `s` matches pattern `p`.

```java
Boolean[][] memo;
public boolean isMatch(String s, String p) {
    memo = new Boolean[s.length() + 1][p.length() + 1];
    return dp(0, 0, s, p);
}
private boolean dp(int i, int j, String s, String p) {
    if (j == p.length()) return i == s.length();
    if (memo[i][j] != null) return memo[i][j];
    boolean match = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?');
    if (p.charAt(j) == '*')
        return memo[i][j] = (i < s.length() && dp(i + 1, j, s, p)) || dp(i, j + 1, s, p);
    else
        return memo[i][j] = match && dp(i + 1, j + 1, s, p);
}
```

 Do pointer `i` (string) aur `j` (pattern). Agar `*` hai toh ya toh `i++` (ek character match karo) ya `j++` (skip karo). Nahi toh normal char match karo.

---

## 2. **Regular Expression Matching**  
**Problem:** `'.'` → any char, `'*'` → zero or more of previous char.

```java
Boolean[][] memo;
public boolean isMatch(String s, String p) {
    memo = new Boolean[s.length() + 1][p.length() + 1];
    return dfs(0, 0, s, p);
}
private boolean dfs(int i, int j, String s, String p) {
    if (j == p.length()) return i == s.length();
    if (memo[i][j] != null) return memo[i][j];
    boolean match = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
    if (j + 1 < p.length() && p.charAt(j + 1) == '*')
        return memo[i][j] = (match && dfs(i + 1, j, s, p)) || dfs(i, j + 2, s, p);
    else
        return memo[i][j] = match && dfs(i + 1, j + 1, s, p);
}
```

 `*` ke liye do choice – uss char ko zero baar use karo (j+2) ya ek baar use karke aage badho (i+1, same j).

---

## 3. **Longest Increasing Subsequence (O(n²) memo)**  
**Problem:** Length of LIS.

```java
int[] memo;
public int lengthOfLIS(int[] nums) {
    memo = new int[nums.length];
    int ans = 0;
    for (int i = 0; i < nums.length; i++)
        ans = Math.max(ans, dfs(i, nums));
    return ans;
}
private int dfs(int i, int[] nums) {
    if (memo[i] != 0) return memo[i];
    int res = 1;
    for (int j = i + 1; j < nums.length; j++)
        if (nums[j] > nums[i])
            res = Math.max(res, 1 + dfs(j, nums));
    return memo[i] = res;
}
```

 Har index `i` se start karo aur aage ke saare `j > i` mein dekho agar `nums[j] > nums[i]` ho toh 1 + dfs(j) le lo.

---

## 4. **Edit Distance**  
**Problem:** Minimum operations (insert/delete/replace) to convert word1 → word2.

```java
Integer[][] memo;
public int minDistance(String w1, String w2) {
    memo = new Integer[w1.length() + 1][w2.length() + 1];
    return solve(0, 0, w1, w2);
}
private int solve(int i, int j, String w1, String w2) {
    if (i == w1.length()) return w2.length() - j;
    if (j == w2.length()) return w1.length() - i;
    if (memo[i][j] != null) return memo[i][j];
    if (w1.charAt(i) == w2.charAt(j))
        return memo[i][j] = solve(i + 1, j + 1, w1, w2);
    int insert = 1 + solve(i, j + 1, w1, w2);
    int delete = 1 + solve(i + 1, j, w1, w2);
    int replace = 1 + solve(i + 1, j + 1, w1, w2);
    return memo[i][j] = Math.min(insert, Math.min(delete, replace));
}
```

 Char match ho toh aage badho. Nahi toh teeno operations try karo – insert, delete, replace.

---

## 5. **Burst Balloons**  
**Problem:** Burst balloon i to get `nums[i-1] * nums[i] * nums[i+1]`. Max coins.

```java
int[][] memo;
public int maxCoins(int[] nums) {
    int[] arr = new int[nums.length + 2];
    arr[0] = arr[arr.length - 1] = 1;
    System.arraycopy(nums, 0, arr, 1, nums.length);
    memo = new int[arr.length][arr.length];
    return dfs(1, arr.length - 2, arr);
}
private int dfs(int l, int r, int[] arr) {
    if (l > r) return 0;
    if (memo[l][r] != 0) return memo[l][r];
    int max = 0;
    for (int i = l; i <= r; i++) {
        int coins = arr[l - 1] * arr[i] * arr[r + 1];
        coins += dfs(l, i - 1, arr) + dfs(i + 1, r, arr);
        max = Math.max(max, coins);
    }
    return memo[l][r] = max;
}
```

 Last balloon `i` ko phodne ki socho – usse pehle left aur right subarray phodo, phir current wala.

---

## 6. **Scramble String**  
**Problem:** Check if string s2 is scrambled version of s1.

```java
HashMap<String, Boolean> memo = new HashMap<>();
public boolean isScramble(String s1, String s2) {
    if (s1.equals(s2)) return true;
    String key = s1 + "#" + s2;
    if (memo.containsKey(key)) return memo.get(key);
    int n = s1.length();
    for (int i = 1; i < n; i++) {
        if (isScramble(s1.substring(0, i), s2.substring(0, i)) &&
            isScramble(s1.substring(i), s2.substring(i)))
            return memo.put(key, true);
        if (isScramble(s1.substring(0, i), s2.substring(n - i)) &&
            isScramble(s1.substring(i), s2.substring(0, n - i)))
            return memo.put(key, true);
    }
    memo.put(key, false);
    return false;
}
```

 String ko do parts mein baanto – ya toh same order swap karo ya opposite order (scramble).

---

## 7. **Distinct Subsequences**  
**Problem:** Count distinct subsequences of `s` that equal `t`.

```java
Integer[][] memo;
public int numDistinct(String s, String t) {
    memo = new Integer[s.length()][t.length()];
    return solve(0, 0, s, t);
}
private int solve(int i, int j, String s, String t) {
    if (j == t.length()) return 1;
    if (i == s.length()) return 0;
    if (memo[i][j] != null) return memo[i][j];
    if (s.charAt(i) == t.charAt(j))
        return memo[i][j] = solve(i + 1, j + 1, s, t) + solve(i + 1, j, s, t);
    else
        return memo[i][j] = solve(i + 1, j, s, t);
}
```

 Current char match ho toh do option – use karo (j+1) ya skip karo (j same). Nahi toh skip hi karo.

---

## 8. **Maximum Profit in Job Scheduling**  
**Problem:** Non-overlapping jobs with start, end, profit. Max profit.

```java
int[] memo;
public int jobScheduling(int[] start, int[] end, int[] profit) {
    int n = start.length;
    Job[] jobs = new Job[n];
    for (int i = 0; i < n; i++) jobs[i] = new Job(start[i], end[i], profit[i]);
    Arrays.sort(jobs, (a,b) -> a.start - b.start);
    memo = new int[n];
    return dfs(0, jobs);
}
private int dfs(int i, Job[] jobs) {
    if (i == jobs.length) return 0;
    if (memo[i] != 0) return memo[i];
    int next = findNext(i, jobs);
    int take = jobs[i].profit + dfs(next, jobs);
    int skip = dfs(i + 1, jobs);
    return memo[i] = Math.max(take, skip);
}
private int findNext(int i, Job[] jobs) {
    int lo = i + 1, hi = jobs.length - 1, ans = jobs.length;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (jobs[mid].start >= jobs[i].end) {
            ans = mid;
            hi = mid - 1;
        } else lo = mid + 1;
    }
    return ans;
}
```

 Current job lelo toh next non-overlapping job dhundho binary search se. Skip karke bhi dekho.

---

## 9. **Minimum Difficulty of a Job Schedule**  
**Problem:** Split array into d subarrays, difficulty = max in subarray, sum of difficulties min.

```java
int[][] memo;
public int minDifficulty(int[] job, int d) {
    if (job.length < d) return -1;
    memo = new int[job.length][d + 1];
    return dfs(0, d, job);
}
private int dfs(int i, int d, int[] job) {
    if (i == job.length && d == 0) return 0;
    if (i == job.length || d == 0) return Integer.MAX_VALUE / 2;
    if (memo[i][d] != 0) return memo[i][d];
    int max = 0, res = Integer.MAX_VALUE;
    for (int j = i; j < job.length; j++) {
        max = Math.max(max, job[j]);
        int next = dfs(j + 1, d - 1, job);
        if (next < Integer.MAX_VALUE / 2)
            res = Math.min(res, max + next);
    }
    return memo[i][d] = res;
}
```

 Current subarray `i..j` ka max lo aur baaki (j+1 se end) ko `d-1` groups mein baanto.

---

## 10. **Cherry Pickup**  
**Problem:** (0,0) to (n-1,n-1) and back, max cherries, but cell empty (0), cherry (1), thorn (-1).

```java
Integer[][][] memo;
public int cherryPickup(int[][] grid) {
    int n = grid.length;
    memo = new Integer[n][n][n];
    return Math.max(0, dfs(0, 0, 0, grid));
}
private int dfs(int r1, int c1, int r2, int[][] grid) {
    int c2 = r1 + c1 - r2;
    int n = grid.length;
    if (r1 == n || c1 == n || r2 == n || c2 == n || grid[r1][c1] == -1 || grid[r2][c2] == -1)
        return Integer.MIN_VALUE;
    if (r1 == n - 1 && c1 == n - 1) return grid[r1][c1];
    if (memo[r1][c1][r2] != null) return memo[r1][c1][r2];
    int cherries = grid[r1][c1];
    if (r1 != r2 || c1 != c2) cherries += grid[r2][c2];
    int max = Math.max(
        Math.max(dfs(r1 + 1, c1, r2 + 1, grid), dfs(r1 + 1, c1, r2, grid)),
        Math.max(dfs(r1, c1 + 1, r2 + 1, grid), dfs(r1, c1 + 1, r2, grid))
    );
    return memo[r1][c1][r2] = cherries + (max == Integer.MIN_VALUE ? 0 : max);
}
```

 Dono trips ek saath chalao – (r1, c1) aur (r2, c2) same time pe. r1+c1 = r2+c2.

---

## 11. **Minimum Insertion Steps to Make a String Palindrome**  
**Problem:** Min insertions to make palindrome.

```java
Integer[][] memo;
public int minInsertions(String s) {
    memo = new Integer[s.length()][s.length()];
    return dfs(0, s.length() - 1, s);
}
private int dfs(int i, int j, String s) {
    if (i >= j) return 0;
    if (memo[i][j] != null) return memo[i][j];
    if (s.charAt(i) == s.charAt(j))
        return memo[i][j] = dfs(i + 1, j - 1, s);
    else
        return memo[i][j] = 1 + Math.min(dfs(i + 1, j, s), dfs(i, j - 1, s));
}
```

 End match ho toh aage badho. Nahi toh left ya right side insert karo (1 step).

---

## 12. **Maximum Length of Repeated Subarray**  
**Problem:** Longest common subarray (contiguous).

```java
int[][] memo;
public int findLength(int[] A, int[] B) {
    memo = new int[A.length][B.length];
    for (int[] row : memo) Arrays.fill(row, -1);
    int ans = 0;
    for (int i = 0; i < A.length; i++)
        for (int j = 0; j < B.length; j++)
            ans = Math.max(ans, dfs(i, j, A, B));
    return ans;
}
private int dfs(int i, int j, int[] A, int[] B) {
    if (i == A.length || j == B.length) return 0;
    if (memo[i][j] != -1) return memo[i][j];
    if (A[i] == B[j])
        return memo[i][j] = 1 + dfs(i + 1, j + 1, A, B);
    return memo[i][j] = 0;
}
```

 Agar match ho toh 1 + (i+1,j+1). Nahi toh 0. Har (i,j) se maximum nikaalo.

---

## 13. **Best Time to Buy and Sell Stock IV**  
**Problem:** At most k transactions.

```java
Integer[][][] memo;
public int maxProfit(int k, int[] prices) {
    memo = new Integer[prices.length][k + 1][2];
    return dfs(0, k, 0, prices);
}
private int dfs(int i, int k, int holding, int[] p) {
    if (i == p.length || k == 0) return 0;
    if (memo[i][k][holding] != null) return memo[i][k][holding];
    int skip = dfs(i + 1, k, holding, p);
    if (holding == 1)
        return memo[i][k][holding] = Math.max(skip, p[i] + dfs(i + 1, k - 1, 0, p));
    else
        return memo[i][k][holding] = Math.max(skip, -p[i] + dfs(i + 1, k, 1, p));
}
```

 holding=1 hai toh bech sakte ho (profit +), nahi toh kharid sakte ho (-price).

---

## 14. **Knight Probability in Chessboard**  
**Problem:** Probability knight stays on board after k moves.

```java
double[][][] memo;
int[][] moves = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
public double knightProbability(int n, int k, int r, int c) {
    memo = new double[n][n][k + 1];
    return dfs(r, c, k, n);
}
private double dfs(int r, int c, int k, int n) {
    if (r < 0 || r >= n || c < 0 || c >= n) return 0;
    if (k == 0) return 1;
    if (memo[r][c][k] != 0) return memo[r][c][k];
    double sum = 0;
    for (int[] m : moves)
        sum += dfs(r + m[0], c + m[1], k - 1, n);
    return memo[r][c][k] = sum / 8.0;
}
```

 Har step pe 8 moves possible. Agar board ke bahar gaya toh 0. Agar k=0 aur andar hai toh 1.

---

## 15. **Super Egg Drop**  
**Problem:** Find minimum moves to know threshold floor with k eggs, n floors.

```java
Integer[][] memo;
public int superEggDrop(int k, int n) {
    memo = new Integer[k + 1][n + 1];
    return dfs(k, n);
}
private int dfs(int e, int f) {
    if (f == 0 || f == 1) return f;
    if (e == 1) return f;
    if (memo[e][f] != null) return memo[e][f];
    int lo = 1, hi = f, ans = f;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int breakCase = dfs(e - 1, mid - 1);
        int notBreak = dfs(e, f - mid);
        if (breakCase > notBreak) {
            hi = mid - 1;
            ans = Math.min(ans, 1 + breakCase);
        } else {
            lo = mid + 1;
            ans = Math.min(ans, 1 + notBreak);
        }
    }
    return memo[e][f] = ans;
}
```

 Binary search on floor. Egg break → e-1, mid-1. Not break → e, f-mid.

---

## 16. **Palindrome Partitioning II**  
**Problem:** Min cuts to make all substrings palindrome.

```java
Integer[] memo;
boolean[][] pal;
public int minCut(String s) {
    int n = s.length();
    pal = new boolean[n][n];
    for (int len = 1; len <= n; len++)
        for (int i = 0; i + len <= n; i++)
            pal[i][i + len - 1] = (s.charAt(i) == s.charAt(i + len - 1)) && (len <= 2 || pal[i + 1][i + len - 2]);
    memo = new Integer[n];
    return dfs(0, s) - 1;
}
private int dfs(int i, String s) {
    if (i == s.length()) return 0;
    if (memo[i] != null) return memo[i];
    int ans = Integer.MAX_VALUE;
    for (int j = i; j < s.length(); j++)
        if (pal[i][j])
            ans = Math.min(ans, 1 + dfs(j + 1, s));
    return memo[i] = ans;
}
```

 Pehle palindrome table banao. Then har palindrome substring pe cut karke aage dekho.

---

## 17. **Minimum Cost to Merge Stones**  
**Problem:** Merge k piles at a time, cost = sum, min total cost.

```java
Integer[][][] memo;
int[] prefix;
public int mergeStones(int[] stones, int k) {
    int n = stones.length;
    if ((n - 1) % (k - 1) != 0) return -1;
    prefix = new int[n + 1];
    for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + stones[i];
    memo = new Integer[n][n][k + 1];
    return dfs(0, n - 1, 1, k);
}
private int dfs(int l, int r, int piles, int k) {
    if (l == r) return piles == 1 ? 0 : Integer.MAX_VALUE / 2;
    if (memo[l][r][piles] != null) return memo[l][r][piles];
    if (piles == 1)
        return memo[l][r][piles] = dfs(l, r, k, k) + prefix[r + 1] - prefix[l];
    int res = Integer.MAX_VALUE / 2;
    for (int i = l; i < r; i += (k - 1))
        res = Math.min(res, dfs(l, i, 1, k) + dfs(i + 1, r, piles - 1, k));
    return memo[l][r][piles] = res;
}
```

 piles=1 means last merge – pehle k piles banao then merge. Warna split karo.

---

## 18. **Longest Palindromic Subsequence**  
**Problem:** Longest palindromic subsequence (not substring).

```java
Integer[][] memo;
public int longestPalindromeSubseq(String s) {
    memo = new Integer[s.length()][s.length()];
    return dfs(0, s.length() - 1, s);
}
private int dfs(int i, int j, String s) {
    if (i > j) return 0;
    if (i == j) return 1;
    if (memo[i][j] != null) return memo[i][j];
    if (s.charAt(i) == s.charAt(j))
        return memo[i][j] = 2 + dfs(i + 1, j - 1, s);
    else
        return memo[i][j] = Math.max(dfs(i + 1, j, s), dfs(i, j - 1, s));
}
```

 End match → 2 + middle. Nahi toh left ya right skip karo.

---

## 19. **Longest String Chain**  
**Problem:** Word chain where next word = prev + 1 char.

```java
Map<String, Integer> memo = new HashMap<>();
public int longestStrChain(String[] words) {
    Set<String> set = new HashSet<>(Arrays.asList(words));
    int ans = 0;
    for (String w : words)
        ans = Math.max(ans, dfs(w, set));
    return ans;
}
private int dfs(String word, Set<String> set) {
    if (memo.containsKey(word)) return memo.get(word);
    int max = 1;
    for (int i = 0; i < word.length(); i++) {
        String prev = word.substring(0, i) + word.substring(i + 1);
        if (set.contains(prev))
            max = Math.max(max, 1 + dfs(prev, set));
    }
    memo.put(word, max);
    return max;
}
```

 Har character remove karke dekho agar set mein ho toh 1 + uski chain.

---

## 20. **Count Different Palindromic Subsequences**  
**Problem:** Count distinct non-empty palindromic subsequences.

```java
Integer[][][] memo;
public int countPalindromicSubsequences(String s) {
    int n = s.length();
    memo = new Integer[n][n][4];
    return dfs(0, n - 1, s);
}
private int dfs(int i, int j, String s) {
    if (i > j) return 0;
    int res = 0;
    for (int c = 0; c < 4; c++) {
        char ch = (char)('a' + c);
        int left = s.indexOf(ch, i);
        int right = s.lastIndexOf(ch, j);
        if (left == -1 || left > j) continue;
        if (left == right) res++;
        else if (left < right) res += 2 + dfs(left + 1, right - 1, s);
    }
    return res % 1000000007;
}
```

 Har char 'a' to 'd' ke liye first aur last occurrence dhundho. Agar same → 1. Agar diff → 2 + middle.

---

## 📌 Pro Tips (Hinglish):
- **Memoization** = recursion + cache (`memo` array/map).
- Hard DP problems mein **state** define karo – mostly `(i, j)` ya `(i, k)`.
- Agar `memo` me value hai toh seedha return karo – **overlapping subproblems** solve hota hai.
- Base case likhna mandatory – recursion rokne ke liye.
- Kabhi `Integer.MAX_VALUE` use karo toh `+1` se overflow na ho – isliye `Integer.MAX_VALUE / 2` safe hai.
