
# 0/1 Knapsack Type Tutorial 
Kisi problem ka **Knapsack Type DP** par based hona tab samajh aata hai jab problem ka core idea ho:

> **“Items ko choose karna ya na karna in such a way that some total (value, weight, sum, etc.) optimize ho.”**

---

## 🔍 **Kaise pehchane ki problem Knapsack type hai?**

Yeh 5 common signs hain:

---

### ✅ 1. **"Include ya exclude" ka decision ho**

Agar har item par aapko decide karna ho:
👉 **"isko lein ya na lein?"**
To yeh Knapsack jaisa hota hai.

**Examples**:

* 0/1 Knapsack: weight ≤ W, maximize value.
* Subset Sum: kisi subset ka sum = target ho sakta hai?

---

### ✅ 2. **Capacity constraint ho**

Jaise:

* Total weight W se zyada nahi hona chahiye
* Total coins ka sum target hona chahiye
* Maximum number of operations allowed

**Examples**:

* Partition Equal Subset Sum
* Coin Change
* Target Sum

---

### ✅ 3. **Item repeat ho sakta hai ya nahi — ispe logic change ho**

Knapsack me 2 main types hote hain:

| Type               | Repeat Allowed? | DP State                         |
| ------------------ | --------------- | -------------------------------- |
| 0/1 Knapsack       | ❌ No            | `dp[i][w]` — i-th item, weight w |
| Unbounded Knapsack | ✅ Yes           | `dp[w]` or `dp[i][w]`            |

---

### ✅ 4. **Items ka weight/value diya ho + goal optimize karna ho**

Agar aapko **value maximize**, **cost minimize**, **ways count karna**, etc. diya ho — aur items ke sath unka weight/cost/value diya ho.

---

### ✅ 5. **Target-based Problems jisme subset banana ho**

Jaise:

* "Kya aise subset hai jinka sum X ho?"
* "Kitne tarike hain sum X banane ke?"

---

## 🧪 Examples se aur clear karte hain:

---

### 🔸 1. **0/1 Knapsack**

Each item once — maximize value with weight ≤ W.

```java
dp[i][w] = max value using first i items with weight limit w
```

---

### 🔸 2. **Coin Change (Unbounded Knapsack)**

Infinite coins — Min number of coins for target sum.

```java
dp[i][j] = min coins to make sum j using first i coins
```

---

### 🔸 3. **Subset Sum**

Kya possible hai sum X banana?

```java
dp[i][sum] = true/false
```

---

### 🔸 4. **Target Sum**

* and - signs laga ke given target banana.

Hidden knapsack (subset sum) problem hai.

---

### 🔸 5. **Rod Cutting**

Cut rod in parts to maximize profit → Unbounded Knapsack

---

## 📌 Summary Table:

| Feature                       | Knapsack Type      |
| ----------------------------- | ------------------ |
| Choose item or not            | 0/1 Knapsack       |
| Choose item multiple times    | Unbounded Knapsack |
| Subset sum = target           | Subset Sum DP      |
| Coin/rod-based combinations   | Unbounded Knapsack |
| Weight/value/cost optimize ho | Knapsack based     |

---
## 🎯 Knapsack Visual Roadmap
---
```
                 ┌──────────────┐
                 │ Knapsack DP  │
                 └────┬─────────┘
                      │
    ┌─────────────────┼────────────────┐
    ▼                                     ▼
"Each item once"                    "Each item unlimited times"
(0/1 Knapsack)                     (Unbounded Knapsack)
    ▼                                     ▼
  dp[i][w]                          dp[i][w] or dp[w]
  Include or Exclude item          Try item again & again

Examples:                          Examples:
✔ 0/1 Knapsack                     ✔ Coin Change (Min/Count)
✔ Subset Sum                       ✔ Rod Cutting
✔ Partition Equal Subset          ✔ Integer Break
✔ Target Sum                       ✔ Max Ribbon Cut
✔ Count Subsets With Sum
```
---

## 0/1 Knapsack Type Kya Hai?
Ye pattern tab use hota hai jab humein ek set of items diya jata hai, aur har item ko ya to **lena hai ya nahi lena hai** (no partial selection). Har item ka ek weight aur value (ya koi property) hota hai, aur humein ek constraint (jaise knapsack capacity ya target sum) ke under optimal result find karna hota hai.

**Examples:**
1. 0/1 Knapsack
2. Subset Sum
3. Partition Equal Subset Sum
4. Target Sum
5. Count of Subsets with Given Sum

**Common Theme:**
- Ek 2D DP array use hota hai jisme states (item index, remaining capacity) store hote hain.
- Har item ke liye do choices: include karo ya exclude karo.
- Optimal result (max value, true/false, count of ways) chahiye.

---

## Intuition Kaise Build Kare?
0/1 Knapsack problems ko samajhne ke liye ye socho:
- Tum ek dukaan pe ho, aur ek bag hai jiska weight limit hai.
- Har item ke liye decide karo: bag mein daalo ya nahi.
- Har decision future ke results ko affect karta hai, aur tumhe best possible outcome chahiye.

For example:
- **0/1 Knapsack** mein har item ka weight aur value hai, aur tumhe max value chahiye within capacity.
- **Subset Sum** mein check karo ki koi subset ka sum target ke barabar ho sakta hai ya nahi.

**Key Intuition:**
- State define karo jo current item index aur remaining capacity bataye.
- Transition formula socho: item include karne ka result vs exclude karne ka result.
- Base cases set karo jo empty set ya zero capacity handle kare.

---

## General Approach
0/1 Knapsack problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i][w]` kya represent karta hai? For example, 0/1 Knapsack mein `dp[i][w]` = max value using first i items with weight capacity w.
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, `dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt[i-1]] + val[i-1])`.
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, `dp[0][w] = 0` (no items), `dp[i][0] = 0` (no capacity).
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + 2D DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage, jaise Subset Sum jaisa problem.
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

**Recommendation for 0/1 Knapsack:**
- **Bottom-Up** zyada common hai kyunki states predictable hote hain, aur iteration efficient hota hai.
- Top-Down use karo jab recursion se logic clear ho ya subproblems kam hain.

---

## Problem 1: 0/1 Knapsack
### Problem Statement:
N items hain, har item ka weight `wt[i]` aur value `val[i]` hai. Ek knapsack hai jiska capacity `W` hai. Max value find karo jo knapsack mein daal sakte ho, bina capacity exceed kiye.

**Example:**
```
Input: wt = [1,3,4], val = [15,20,30], W = 4
Output: 35
Explanation: Take items with weight 1 and 4 (values 15+30=35).
```

### Intuition:
- Har item ke liye decide: include karo ya exclude.
- State: `dp[i][w]` = max value using first i items with capacity w.
- Transition: `dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt[i-1]] + val[i-1])`.
- Base cases: `dp[0][w] = 0`, `dp[i][0] = 0`.

### Tree Diagram:
```
i=2, w=4 (wt[1]=3, val[1]=20)
 /           \
Include      Exclude
w=4-3=1       w=4
dp[1][1]+20   dp[1][4]
```
Har node pe do choices: include ya exclude item.

### Bottom-Up Code:
```java
public class Solution {
    public int knapsack(int[] wt, int[] val, int W) {
        int n = wt.length;
        int[][] dp = new int[n+1][W+1];
        
        // Fill DP array
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (wt[i-1] <= w) {
                    dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w-wt[i-1]] + val[i-1]);
                } else {
                    dp[i][w] = dp[i-1][w];
                }
            }
        }
        
        return dp[n][W];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[][] memo;
    
    public int knapsack(int[] wt, int[] val, int W) {
        int n = wt.length;
        memo = new int[n+1][W+1];
        for (int[] row : memo) Arrays.fill(row, -1);
        return knapsackHelper(wt, val, W, n);
    }
    
    private int knapsackHelper(int[] wt, int[] val, int w, int i) {
        if (i == 0 || w == 0) return 0;
        if (memo[i][w] != -1) return memo[i][w];
        
        if (wt[i-1] <= w) {
            memo[i][w] = Math.max(
                knapsackHelper(wt, val, w, i-1),
                knapsackHelper(wt, val, w-wt[i-1], i-1) + val[i-1]
            );
        } else {
            memo[i][w] = knapsackHelper(wt, val, w, i-1);
        }
        
        return memo[i][w];
    }
}
```

### Space Optimization:
Since only previous row is needed:
```java
public int knapsack(int[] wt, int[] val, int W) {
    int n = wt.length;
    int[] dp = new int[W+1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = W; w >= wt[i-1]; w--) {
            dp[w] = Math.max(dp[w], dp[w-wt[i-1]] + val[i-1]);
        }
    }
    
    return dp[W];
}
```

---

## Problem 2: Subset Sum
### Problem Statement:
Ek array `nums` aur target sum `sum` diya hai. Check karo ki koi subset ka sum target ke barabar ho sakta hai ya nahi.

**Example:**
```
Input: nums = [3,34,4,12,5,2], sum = 9
Output: true
Explanation: Subset [4,5] sums to 9.
```

### Intuition:
- Har element ke liye decide: include karo ya nahi.
- State: `dp[i][s]` = true if sum s possible using first i elements.
- Transition: `dp[i][s] = dp[i-1][s] || dp[i-1][s-nums[i-1]]`.
- Base cases: `dp[i][0] = true`, `dp[0][s] = false` (except `dp[0][0]`).

### Bottom-Up Code:
```java
public class Solution {
    public boolean subsetSum(int[] nums, int sum) {
        int n = nums.length;
        boolean[][] dp = new boolean[n+1][sum+1];
        
        // Base case: sum = 0 is always possible
        for (int i = 0; i <= n; i++) dp[i][0] = true;
        
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= sum; s++) {
                if (nums[i-1] <= s) {
                    dp[i][s] = dp[i-1][s] || dp[i-1][s-nums[i-1]];
                } else {
                    dp[i][s] = dp[i-1][s];
                }
            }
        }
        
        return dp[n][sum];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Boolean[][] memo;
    
    public boolean subsetSum(int[] nums, int sum) {
        int n = nums.length;
        memo = new Boolean[n+1][sum+1];
        return subsetSumHelper(nums, sum, n);
    }
    
    private boolean subsetSumHelper(int[] nums, int s, int i) {
        if (s == 0) return true;
        if (i == 0) return false;
        if (memo[i][s] != null) return memo[i][s];
        
        if (nums[i-1] <= s) {
            memo[i][s] = subsetSumHelper(nums, s, i-1) || 
                         subsetSumHelper(nums, s-nums[i-1], i-1);
        } else {
            memo[i][s] = subsetSumHelper(nums, s, i-1);
        }
        
        return memo[i][s];
    }
}
```

### Space Optimization:
```java
public boolean subsetSum(int[] nums, int sum) {
    int n = nums.length;
    boolean[] dp = new boolean[sum+1];
    dp[0] = true;
    
    for (int i = 1; i <= n; i++) {
        for (int s = sum; s >= nums[i-1]; s--) {
            dp[s] = dp[s] || dp[s-nums[i-1]];
        }
    }
    
    return dp[sum];
}
```

---

## Problem 3: Partition Equal Subset Sum
### Problem Statement:
Ek array `nums` diya hai. Check karo ki array ko do subsets mein partition kar sakte hain jinka sum equal ho.

**Example:**
```
Input: nums = [1,5,11,5]
Output: true
Explanation: Subsets: [1,5,5] and [11], both sum to 11.
```

### Intuition:
- Array ka total sum even hona chahiye, aur ek subset ka sum total/2 hona chahiye.
- Ye Subset Sum problem ban jata hai with target = total_sum/2.
- State: Same as Subset Sum.

### Bottom-Up Code:
```java
public class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        
        int target = sum / 2;
        int n = nums.length;
        boolean[][] dp = new boolean[n+1][target+1];
        
        for (int i = 0; i <= n; i++) dp[i][0] = true;
        
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= target; s++) {
                if (nums[i-1] <= s) {
                    dp[i][s] = dp[i-1][s] || dp[i-1][s-nums[i-1]];
                } else {
                    dp[i][s] = dp[i-1][s];
                }
            }
        }
        
        return dp[n][target];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Boolean[][] memo;
    
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        
        int target = sum / 2;
        int n = nums.length;
        memo = new Boolean[n+1][target+1];
        return canPartitionHelper(nums, target, n);
    }
    
    private boolean canPartitionHelper(int[] nums, int s, int i) {
        if (s == 0) return true;
        if (i == 0) return false;
        if (memo[i][s] != null) return memo[i][s];
        
        if (nums[i-1] <= s) {
            memo[i][s] = canPartitionHelper(nums, s, i-1) || 
                         canPartitionHelper(nums, s-nums[i-1], i-1);
        } else {
            memo[i][s] = canPartitionHelper(nums, s, i-1);
        }
        
        return memo[i][s];
    }
}
```

### Space Optimization:
```java
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % 2 != 0) return false;
    
    int target = sum / 2;
    boolean[] dp = new boolean[target+1];
    dp[0] = true;
    
    for (int num : nums) {
        for (int s = target; s >= num; s--) {
            dp[s] = dp[s] || dp[s-num];
        }
    }
    
    return dp[target];
}
```

---

## Problem 4: Target Sum
### Problem Statement:
Ek array `nums` aur target `S` diya hai. Har element ko + ya - sign dekar target sum S banane ke kitne ways hain?

**Example:**
```
Input: nums = [1,1,1,1,1], S = 3
Output: 5
Explanation: Ways: [-1,-1,1,1,1], [-1,1,-1,1,1], etc.
```

### Intuition:
- Har element ke liye do choices: + ya -.
- Ye 0/1 Knapsack ban jata hai: sum of positive numbers (P) - sum of negative numbers (N) = S.
- Total sum = P + N, so P - N = S => P = (S + total_sum)/2.
- Find number of subsets with sum = (S + total_sum)/2.

### Bottom-Up Code:
```java
public class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (S > sum || S < -sum || (S + sum) % 2 != 0) return 0;
        
        int target = (S + sum) / 2;
        int n = nums.length;
        int[][] dp = new int[n+1][target+1];
        
        dp[0][0] = 1; // Empty subset sums to 0
        
        for (int i = 1; i <= n; i++) {
            for (int s = 0; s <= target; s++) {
                dp[i][s] = dp[i-1][s];
                if (nums[i-1] <= s) {
                    dp[i][s] += dp[i-1][s-nums[i-1]];
                }
            }
        }
        
        return dp[n][target];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (S > sum || S < -sum || (S + sum) % 2 != 0) return 0;
        
        int target = (S + sum) / 2;
        int n = nums.length;
        memo = new Integer[n+1][target+1];
        return findTargetSumWaysHelper(nums, target, n);
    }
    
    private int findTargetSumWaysHelper(int[] nums, int s, int i) {
        if (i == 0 && s == 0) return 1;
        if (i == 0) return 0;
        if (memo[i][s] != null) return memo[i][s];
        
        memo[i][s] = findTargetSumWaysHelper(nums, s, i-1);
        if (nums[i-1] <= s) {
            memo[i][s] += findTargetSumWaysHelper(nums, s-nums[i-1], i-1);
        }
        
        return memo[i][s];
    }
}
```

### Space Optimization:
```java
public int findTargetSumWays(int[] nums, int S) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (S > sum || S < -sum || (S + sum) % 2 != 0) return 0;
    
    int target = (S + sum) / 2;
    int[] dp = new int[target+1];
    dp[0] = 1;
    
    for (int num : nums) {
        for (int s = target; s >= num; s--) {
            dp[s] += dp[s-num];
        }
    }
    
    return dp[target];
}
```

---

## Problem 5: Count of Subsets with Given Sum
### Problem Statement:
Ek array `nums` aur target sum `sum` diya hai. Kitne subsets ka sum target ke barabar hai?

**Example:**
```
Input: nums = [1,2,3,3], sum = 6
Output: 3
Explanation: Subsets: [1,2,3], [1,2,3], [3,3]
```

### Intuition:
- Har element ke liye decide: include karo ya nahi.
- State: `dp[i][s]` = number of subsets using first i elements summing to s.
- Transition: `dp[i][s] = dp[i-1][s] + dp[i-1][s-nums[i-1]]`.
- Base cases: `dp[0][0] = 1`, `dp[0][s] = 0`.

### Bottom-Up Code:
```java
public class Solution {
    public int countSubsets(int[] nums, int sum) {
        int n = nums.length;
        int[][] dp = new int[n+1][sum+1];
        
        dp[0][0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int s = 0; s <= sum; s++) {
                dp[i][s] = dp[i-1][s];
                if (nums[i-1] <= s) {
                    dp[i][s] += dp[i-1][s-nums[i-1]];
                }
            }
        }
        
        return dp[n][sum];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    
    public int countSubsets(int[] nums, int sum) {
        int n = nums.length;
        memo = new Integer[n+1][sum+1];
        return countSubsetsHelper(nums, sum, n);
    }
    
    private int countSubsetsHelper(int[] nums, int s, int i) {
        if (s == 0 && i == 0) return 1;
        if (i == 0) return 0;
        if (memo[i][s] != null) return memo[i][s];
        
        memo[i][s] = countSubsetsHelper(nums, s, i-1);
        if (nums[i-1] <= s) {
            memo[i][s] += countSubsetsHelper(nums, s-nums[i-1], i-1);
        }
        
        return memo[i][s];
    }
}
```

### Space Optimization:
```java
public int countSubsets(int[] nums, int sum) {
    int n = nums.length;
    int[] dp = new int[sum+1];
    dp[0] = 1;
    
    for (int num : nums) {
        for (int s = sum; s >= num; s--) {
            dp[s] += dp[s-num];
        }
    }
    
    return dp[sum];
}
```

---

## Conclusion
0/1 Knapsack Type pattern bohot versatile hai aur choice-based problems ke liye perfect. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai.
- Space optimization ke liye 1D array use karo jab possible ho.
