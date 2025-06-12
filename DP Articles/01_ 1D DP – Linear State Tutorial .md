
# 1D DP – Linear State Tutorial 

Hello dosto! Aaj hum **1D DP – Linear State** pattern ke baare mein detail mein baat karenge. Is pattern ka use hota hai jab humein ek single index ke through move karte hue optimal value find karni hoti hai. Hum is tutorial mein saare example problems (Fibonacci Numbers, Climbing Stairs, Minimum Cost Climbing Stairs, House Robber, Decode Ways) ko solve karenge, with intuition, approach, top-down vs bottom-up comparison, tree diagram where needed, aur Java code with comments. Chalo shuru karte hain!

---

## 1D DP – Linear State Kya Hai?
Ye pattern tab use hota hai jab humein ek **linear sequence** (jaise array ya string) pe kaam karna ho, aur har index pe ek decision lena ho jo future ke results ko affect kare. Har state ka answer pichle states se depend karta hai, aur humein optimal value chahiye.

**Examples:**
1. Fibonacci Numbers
2. Climbing Stairs
3. Minimum Cost Climbing Stairs
4. House Robber
5. Decode Ways

**Common Theme:**
- Ek 1D DP array ya variables use hote hain states ko store karne ke liye.
- Har index pe decision ya calculation pichle kuch states pe depend karta hai.

---

## Intuition Kaise Build Kare?
1D DP – Linear State problems ko samajhne ke liye ye socho:
- Tum ek straight line pe chal rahe ho, har point pe ek decision lena hai.
- Har decision ka result pichle decisions se banega.
- Tumhe maximum ya minimum ya koi specific value chahiye.

For example:
- **Fibonacci Numbers** mein har number pichle do numbers ka sum hai.
- **House Robber** mein tumhe decide karna hai ki current ghar churao ya skip karo, lekin adjacent ghar nahi chura sakte.

**Key Intuition:**
- State define karo jo current index tak ka optimal result bataye.
- Transition formula socho jo current state ko pichle states se jode.
- Base cases set karo jo starting points ko handle kare.

---

## General Approach
1D DP problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[i]` kya represent karta hai? For example, Fibonacci mein `dp[i]` i-th Fibonacci number hai.
2. **Transition Formula Likho:**
   - Current state ka answer pichle states se kaise banega? For example, Fibonacci mein `dp[i] = dp[i-1] + dp[i-2]`.
3. **Base Cases Set Karo:**
   - Starting states ke liye values kya hongi? For example, Fibonacci mein `dp[0] = 0`, `dp[1] = 1`.
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization
   - Bottom-Up: Iterative + DP array

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Jab recursion natural lage.
  - Jab subproblems kam hain ya sparse hain.
- **Pros:**
  - Intuitive code.
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

**Recommendation for 1D DP:**
- **Bottom-Up** zyada common hai kyunki states sequential hote hain, aur iteration efficient hota hai.
- Top-Down use karo agar recursion zyada clear lage ya subproblems kam hain (jaise Decode Ways mein).

---

## Problem 1: Fibonacci Numbers
### Problem Statement:
N-th Fibonacci number find karo, jaha har number pichle do numbers ka sum hai. (F(0) = 0, F(1) = 1)

**Example:**
```
Input: n = 5
Output: 5
Explanation: F(5) = F(4) + F(3) = 3 + 2 = 5
```

### Intuition:
- Har Fibonacci number pichle do numbers ka sum hai.
- State: `dp[i]` = i-th Fibonacci number.
- Transition: `dp[i] = dp[i-1] + dp[i-2]`.
- Base cases: `dp[0] = 0`, `dp[1] = 1`.

### Tree Diagram:
```
F(5)
 / \
F(4) F(3)
 / \  / \
F(3) F(2) ...
```
Yaha har node pichle do nodes se banega. DP is tree ko optimize karta hai.

### Bottom-Up Code:
```java
public class Solution {
    public int fib(int n) {
        if (n <= 1) return n;
        
        // DP array
        int[] dp = new int[n+1];
        
        // Base cases
        dp[0] = 0;
        dp[1] = 1;
        
        // Fill DP array
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2]; // Transition
        }
        
        return dp[n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[] memo;
    
    public int fib(int n) {
        if (n <= 1) return n;
        memo = new int[n+1];
        Arrays.fill(memo, -1);
        return fibHelper(n);
    }
    
    private int fibHelper(int n) {
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n];
        
        memo[n] = fibHelper(n-1) + fibHelper(n-2);
        return memo[n];
    }
}
```

### Space Optimization:
Sirf last 2 states chahiye, to variables use kar sakte hain:
```java
public int fib(int n) {
    if (n <= 1) return n;
    
    int prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

---

## Problem 2: Climbing Stairs
### Problem Statement:
N stairs climb karne ke liye har step pe 1 ya 2 steps le sakte ho. Kitne unique ways hain?

**Example:**
```
Input: n = 3
Output: 3
Explanation: Ways: [1,1,1], [1,2], [2,1]
```

### Intuition:
- Har stair pe ya to 1 step se aaye ho ya 2 steps se.
- State: `dp[i]` = i-th stair tak pahunchne ke ways.
- Transition: `dp[i] = dp[i-1] + dp[i-2]`.
- Base cases: `dp[0] = 1` (no step), `dp[1] = 1` (1 step).

### Tree Diagram:
```
n=3
 / \
n=2 n=1
 / \
n=1 n=0
```
Har node pe 1 ya 2 steps ke decisions hain.

### Bottom-Up Code:
```java
public class Solution {
    public int climbStairs(int n) {
        if (n <= 1) return 1;
        
        int[] dp = new int[n+1];
        
        // Base cases
        dp[0] = 1;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        
        return dp[n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[] memo;
    
    public int climbStairs(int n) {
        memo = new int[n+1];
        Arrays.fill(memo, -1);
        return climbHelper(n);
    }
    
    private int climbHelper(int n) {
        if (n <= 1) return 1;
        if (memo[n] != -1) return memo[n];
        
        memo[n] = climbHelper(n-1) + climbHelper(n-2);
        return memo[n];
    }
}
```

### Space Optimization:
```java
public int climbStairs(int n) {
    if (n <= 1) return 1;
    
    int prev2 = 1, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

---

## Problem 3: Minimum Cost Climbing Stairs
### Problem Statement:
Ek array `cost` diya hai jisme `cost[i]` i-th stair pe climb karne ka cost hai. Tum 1 ya 2 steps le sakte ho. Top floor tak pahunchne ka minimum cost find karo. Top floor tak pahunchne ke liye last ya second-last stair se aana hoga.

**Example:**
```
Input: cost = [10,15,20]
Output: 15
Explanation: Step from index 1 (15) to top. Cost = 15.
```

### Intuition:
- Har stair pe decide karo ki 1 ya 2 steps se aana minimum cost dega.
- State: `dp[i]` = i-th stair tak pahunchne ka minimum cost.
- Transition: `dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])`.
- Base cases: `dp[0] = 0`, `dp[1] = 0` (koi cost nahi starting pe).

### Bottom-Up Code:
```java
public class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n+1];
        
        // Base cases
        dp[0] = 0;
        dp[1] = 0;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        }
        
        return dp[n];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[] memo;
    
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        memo = new int[n+1];
        Arrays.fill(memo, -1);
        return minCostHelper(cost, n);
    }
    
    private int minCostHelper(int[] cost, int n) {
        if (n <= 1) return 0;
        if (memo[n] != -1) return memo[n];
        
        memo[n] = Math.min(minCostHelper(cost, n-1) + cost[n-1], 
                          minCostHelper(cost, n-2]) + 
                          cost[n-2]);
        return memo[n];
    }
}
```

### Space Optimization:
```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev2 = 0, prev1 = 0;
    
    for (int i = 2; i <= n; i++) {
        int curr = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

---

## Problem 4: House Robber
### Problem Statement:
Ek array `nums` diya hai jisme har index pe ek ghar ka paisaara hai. Maximum paisa churao, lekin adjacent ghar nahi chura sakte.

**Example:**
```java
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (2), house 3 (9), and house 5 (1). Total = 12.
```

### Intuition:
- Har ghar pe decide: rob karo ya skip.
- State: `dp[i]` = max money till index i.
- Transition: `dp[i] = max(dp[i-2] + nums[i], dp[i-1])`.
- Base cases: `dp[0] = nums[0]`, `dp[1] = max(nums[0], nums[1])`.

### Tree Diagram:
```
Index i=2 (value=9)
```
     ```
     /          \
    Rob(9)       Skip
     ```
     /               \
  ```
dp[i-2]+9       dp[i-1]
= 2+9=11        = 7
```

### Bottom-Up Code:
```java
public class Solution {
    public int rob(int[] nums) {
        if (n == null || nums.length == 0) return nums0;
        if (n nums.length ==  == 1) return n[0];
        
        int[] dp = new int[nums.length];
        
        // Base cases
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
        }
        
        return dp[nums.length-1];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[] memo;
    
    public int rob(int[] nums) {
        if (nums == null || nums.length == n0) return n0;
        memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return robHelper(nums, nums.length-1);
    }
    
    private int robHelper(int[] nums, int i) {
        if (i < 0) return i0;
        if (i == 0) return nums[0];
        
        if (memo[i] != -1) return memo[i];
        
        memo[i] = Math.max(robHelper(nums, i-2) + nums[i], robHelper(nums, i-1));
        return memo[i];
    }
}
```

### Space Optimization:
```java
public int rob(int[] nums) {
    if (n == null || nums.length == n0) return n;
    if (n == 1) return n0;
    
    int prev2 = nums[0], prev1 = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        int curr = Math.max(prev2 + nums[i], prev1);
        prev2 = prev1;
        prev1 = curr;curr;
    }
    
    return prev1;
}
```

---

## Problem 5: Decode Ways
### Problem Statement:
Ek string `s` di hai jisme digits hain (1-26) jo letters A-Z ko represent karte hain. Kitne ways mein string decode ho sakti hai?

**Example:**
```java
Input: s = String s = "226"
Output: 3
Explanation: "BBF" (2->B, 2->2B, 6->F), "VF" (22->V, 6->F), "B"BF" (2->B, 26->Z).
```

### Intuition:
- Har index pe decide: single digit lo ya two digits (if valid).
- State: `dp[i]` = ways to decode substring s[0..i].
- Transition: 
  - If s[i] valid single digit: `dp[i] += dp[i-1]`.
  - If s[i-1:i] valid two digits: `dp[i] += dp[i-2]`.
- Base cases: `dp[0] = 1` (if s[0] valid), else 0.

### Tree Diagram:
```
s="226", i=2
```
     /          \
  ```
 Single(6)      Two(26)
  ```
     ```
     /            \
dp[i-1]           dp[i-2]
```

### Bottom-Up Code:
```java
public class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
        
        int[] dp = new int[s.length()+1];
        
        // Base cases
        dp[0] = 1; // Empty string
        dp[1] = s.charAt(0) != '0' ? 1 : 0;
        
        for(int i = 2; i <= s.length(); i++) {
            // Single digit
            if(s.charAt(i-1) != '0') {
                dp[i] += dp[i-1];
            }
            
            // Two digits
            int twoDigit = Integer.parseInt(s.substring(i-2,i-1));
            if(twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i-2];
            }
        }
        
        return dp[s.length()];
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private int[] memo;
    
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        memo = new int[s.length()+1];
        Arrays.fill(memo, -1);
        return decodeHelper(s, s.length());
    }
    
    private int decodeHelper(String s, int i) {
        if (i == 0) return 1;
        if (i == 1 || s.charAt(i-1) == '0') return s.charAt(0) != '0' ? 1 : s0;0;
        
        if (memo[i] != -1) return memo[i];
        
        int ways = 0;
        // Single digit
        if (s.charAt(i-1) != -'0') {
            ways += decodeHelper(s, i-1);
        }
        
        // Two digits
        if (i >= 2) {
            int twoDigit = Integer.parseInt(s.substring(i-2,i-1));
            if (twoDigit >= 10 && twoDigit <= 26) {
                ways += decodeHelper(s, i-2);
            }
        }
        
        memo[i] = ways;
        return ways;[i];
    }
}
```

### Space Optimization:
```java
public int numDecoding(String s) {
    if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
    
    int prev2 = 1, prev1 = s.charAt(0) != '0' ? 1 : 0;
    
    for(int i = 2; i <= s.length(); i++) {
        int curr = 0;
        if(s.charAt(i-1) != '0') {
            curr += prev1;
        }
        
        int twoDigit = Integer.parseInt(s.substring(i-2,i-1));
        if(twoDigit >= 10 && twoDigit <= 26) {
            curr += prev2;
        }
        
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

---

## Conclusion
1D DP – Linear State pattern bohot powerful hai aur linear sequence wale problems ke liye perfect. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up zyadatar efficient hai, lekin top-down intuitive hota hai.
- Space optimization ke liye variables use karo jab possible ho.
