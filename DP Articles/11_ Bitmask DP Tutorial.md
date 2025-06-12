# Bitmask DP Tutorial 

## Bitmask DP Kya Hai?
Bitmask DP ek dynamic programming technique hai jisme hum bit manipulation ka use karke subsets ya visited elements ke states track karte hain. Har bit (0 ya 1) ek element ke inclusion/exclusion ko represent karta hai, aur DP se hum overlapping subproblems solve karte hain.

**Examples:**
1. Traveling Salesman Problem (TSP)
2. Minimum Cost to Visit All Cities
3. Shortest Superstring
4. Count Ways to Assign Tasks
5. Boolean Parenthesization

**Common Theme:**
- Ek bitmask (integer) use hota hai jo current subset ya visited elements ko represent karta hai.
- States mein typically include: current position/index aur bitmask.
- Top-down memoization zyada common hai kyunki states recursive hote hain.

---

## Intuition Kaise Build Kare?
Bitmask DP problems ko samajhne ke liye ye socho:
- Tumhe ek set of elements hai (cities, strings, tasks), aur tumhe unke subsets ke combinations process karne hain.
- Bitmask se track karo kaunse elements already used/visited hain (1 = used, 0 = not used).
- Har step pe decide karo: kaunsa next element include karna hai, aur bitmask update karo.

For example:
- **TSP** mein tumhe saare cities visit karne ka minimum cost chahiye, aur bitmask track karta hai kaunse cities visited hain.
- **Boolean Parenthesization** mein bitmask se track karo kaunse sub-expressions evaluate hue.

**Key Intuition:**
- State define karo jo current position/index aur bitmask (subset) track kare.
- Transition formula socho: har unvisited element ko try karo, bitmask update karo.
- Base cases set karo jo complete subset ya invalid states handle kare.

---

## General Approach
Bitmask DP problems ko solve karne ke steps:
1. **State Define Karo:**
   - `dp[pos][mask]` kya represent karta hai? For example, TSP mein `dp[pos][mask]` = min cost to visit remaining cities starting from pos with visited cities in mask.
2. **Transition Formula Likho:**
   - Current state se next state kaise jaye? For example, har unvisited city i ke liye: `dp[pos][mask] = min(dp[next][mask | (1 << i)] + cost[pos][i])`.
3. **Base Cases Set Karo:**
   - Jab mask complete ho (all bits 1), check if valid result possible hai.
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive + Memoization (common for Bitmask DP)
   - Bottom-Up: Iterative (possible but rare)

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization)
- **Kab Use Karo?**
  - Bitmask DP mein zyadatar top-down use hota hai kyunki states (pos, mask) recursive nature ke hote hain.
  - Jab subproblems sparse hain ya saare states visit nahi karne padte.
- **Pros:**
  - Code intuitive hota hai, state transitions clear rahte hain.
  - Sirf needed subproblems solve hote hain.
- **Cons:**
  - Memoization array bada ho sakta hai (O(n * 2^n)).
  - Stack overflow ka risk bade inputs pe.

### Bottom-Up (Tabulation)
- **Kab Use Karo?**
  - Jab states predictable hon aur iteration se solve ho sake, lekin Bitmask DP mein ye rare hai.
  - Jab memory optimization chahiye.
- **Pros:**
  - No recursion, no stack overflow.
  - Can be slightly faster for small state spaces.
- **Cons:**
  - Code complex ho jata hai, especially bitmask transitions ke liye.
  - Unnecessary states bhi process ho sakte hain.

**Recommendation for Bitmask DP:**
- **Top-Down** almost always better hai kyunki Bitmask DP ka recursive nature memoization ke sath fit hota hai. Bottom-Up possible hai lekin impractical zyadatar cases mein.

---

## Problem 1: Traveling Salesman Problem (TSP)
### Problem Statement:
Ek graph diya hai jisme n cities hain aur unke bich distances. Minimum cost find karo jo saare cities ko exactly ek baar visit kare aur starting city pe wapas aaye.

**Example:**
```
Input: graph = [[0,10,15,20],[10,0,35,25],[15,35,0,30],[20,25,30,0]]
Output: 80
Explanation: Path 0->1->3->2->0 gives cost 10+25+30+15 = 80.
```

### Intuition:
- Har city ko visit karo, bitmask se track karo kaunse cities visited hain.
- State: `dp[pos][mask]` = min cost to visit remaining cities starting from pos with mask.
- Transition: For each unvisited city i, `dp[pos][mask] = min(dp[i][mask | (1 << i)] + graph[pos][i])`.
- Base cases: When all cities visited, return cost to starting city.

### Tree Diagram:
```
pos=0, mask=0001 (city 0 visited)
 /        \
i=1       i=2
mask=0011 mask=0101
cost+=10  cost+=15
```

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    private int n;
    
    public int shortestPathLength(int[][] graph) {
        n = graph.length;
        memo = new Integer[n][1 << n];
        // Start from city 0, mask with only city 0 visited
        return tspHelper(graph, 0, 1);
    }
    
    private int tspHelper(int[][] graph, int pos, int mask) {
        // Base case: all cities visited, return cost to city 0
        if (mask == (1 << n) - 1) {
            return graph[pos][0];
        }
        // Check memoized state
        if (memo[pos][mask] != null) return memo[pos][mask];
        
        int minCost = Integer.MAX_VALUE;
        // Try visiting each unvisited city
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0 && graph[pos][i] != 0) { // Unvisited and reachable
                int newMask = mask | (1 << i); // Mark city i as visited
                int cost = graph[pos][i] + tspHelper(graph, i, newMask);
                minCost = Math.min(minCost, cost);
            }
        }
        
        return memo[pos][mask] = minCost;
    }
}
```

---

## Problem 2: Minimum Cost to Visit All Cities
### Problem Statement:
Ek graph diya hai jisme n cities hain, aur directed edges with costs. Minimum cost find karo to visit all cities at least once, starting from any city.

**Example:**
```
Input: n = 3, edges = [[0,1,2],[1,2,3],[0,2,4]]
Output: 5
Explanation: Path 0->1->2 costs 2+3 = 5.
```

### Intuition:
- Similar to TSP, lekin cycle banane ki zarurat nahi, just saare cities visit karo.
- State: `dp[pos][mask]` = min cost to visit remaining cities starting from pos.
- Transition: Same as TSP, but no return to start.
- Base cases: When mask complete, return 0.

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    private int n;
    
    public int minCost(int n, int[][] edges) {
        this.n = n;
        // Build adjacency list
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }
        
        memo = new Integer[n][1 << n];
        int minCost = Integer.MAX_VALUE;
        // Try starting from each city
        for (int i = 0; i < n; i++) {
            minCost = Math.min(minCost, minCostHelper(graph, i, 1 << i));
        }
        return minCost;
    }
    
    private int minCostHelper(List<int[]>[] graph, int pos, int mask) {
        // Base case: all cities visited
        if (mask == (1 << n) - 1) return 0;
        // Check memoized state
        if (memo[pos][mask] != null) return memo[pos][mask];
        
        int minCost = Integer.MAX_VALUE;
        // Try visiting each unvisited city
        for (int[] edge : graph[pos]) {
            int next = edge[0], cost = edge[1];
            if ((mask & (1 << next)) == 0) { // Unvisited
                int newMask = mask | (1 << next);
                int subCost = minCostHelper(graph, next, newMask);
                if (subCost != Integer.MAX_VALUE) {
                    minCost = Math.min(minCost, cost + subCost);
                }
            }
        }
        
        return memo[pos][mask] = minCost;
    }
}
```

---

## Problem 3: Shortest Superstring
### Problem Statement:
Ek array of strings `words` diya hai. Shortest string find karo jo saare words ko as substrings contain kare.

**Example:**
```
Input: words = ["alex","loves","leetcode"]
Output: 14
Explanation: "alexlovesleetcode" is shortest superstring.
```

### Intuition:
- Har word ko ek node mano, aur overlap ke basis pe edges banaye.
- State: `dp[mask][last]` = min length superstring for mask with last word.
- Transition: Try adding each unvisited word, compute overlap.
- Base cases: Single word ka length.

### Top-Down Code:
```java
public class Solution {
    private Integer[][] memo;
    private int n;
    
    public int shortestSuperstring(String[] words) {
        n = words.length;
        memo = new Integer[1 << n][n];
        // Compute overlaps
        int[][] overlap = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    overlap[i][j] = computeOverlap(words[i], words[j]);
                }
            }
        }
        
        int minLen = Integer.MAX_VALUE;
        // Try each word as last
        for (int i = 0; i < n; i++) {
            minLen = Math.min(minLen, superstringHelper(words, overlap, (1 << n) - 1, i));
        }
        return minLen;
    }
    
    private int superstringHelper(String[] words, int[][] overlap, int mask, int last) {
        // Base case: no words left
        if (mask == 0) return words[last].length();
        // Check memoized state
        if (memo[mask][last] != null) return memo[mask][last];
        
        int minLen = Integer.MAX_VALUE;
        // Try each unvisited word
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                int newMask = mask ^ (1 << i); // Remove i from mask
                int subLen = superstringHelper(words, overlap, newMask, i);
                if (subLen != Integer.MAX_VALUE) {
                    // Add word length minus overlap
                    minLen = Math.min(minLen, subLen + words[i].length() - overlap[i][last]);
                }
            }
        }
        
        return memo[mask][last] = minLen;
    }
    
    // Compute overlap between two strings
    private int computeOverlap(String a, String b) {
        for (int i = Math.min(a.length(), b.length()); i > 0; i--) {
            if (a.substring(a.length() - i).equals(b.substring(0, i))) {
                return i;
            }
        }
        return 0;
    }
}
```

---

## Problem 4: Count Ways to Assign Tasks
### Problem Statement:
N tasks aur N people hain, aur ek cost matrix diya hai. Kitne ways mein tasks assign kar sakte hain?

**Example:**
```
Input: cost = [[1,2,3],[4,5,6],[7,8,9]]
Output: 6
Explanation: 6 permutations of task assignments.
```

### Intuition:
- Har task ko ek person ko assign karo, bitmask se track karo assigned people.
- State: `dp[mask]` = ways to assign tasks to people in mask.
- Transition: For each unvisited person i, `dp[mask] += dp[mask | (1 << i)]`.
- Base cases: When mask complete, return 1.

### Top-Down Code:
```java
public class Solution {
    private Long[] memo;
    private int n;
    
    public long countWays(int[][] cost) {
        n = cost.length;
        memo = new Long[1 << n];
        return countWaysHelper(cost, 0);
    }
    
    private long countWaysHelper(int[][] cost, int mask) {
        // Base case: all tasks assigned
        if (mask == (1 << n) - 1) return 1;
        // Check memoized state
        if (memo[mask] != null) return memo[mask];
        
        long ways = 0;
        int task = Integer.bitCount(mask); // Current task index
        // Try assigning task to each unassigned person
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) { // Person i unassigned
                int newMask = mask | (1 << i);
                ways += countWaysHelper(cost, newMask);
            }
        }
        
        return memo[mask] = ways;
    }
}
```

---

## Problem 5: Boolean Parenthesization
### Problem Statement:
Ek string di hai jisme operands (T, F) aur operators (&, |, ^) hain. Kitne ways mein expression true ban sakta hai?

**Example:**
```
Input: expression = "T|T&F"
Output: 2
Explanation: (T | (T & F)) = T, and (T | T) & F = T
```

### Intuition:
- Expression ko split karke evaluate karo, bitmask se track karo kaunse sub-expressions used hain.
- State: `dp[i][j][isTrue]` = ways to make expression[i..j] true/false.
- Transition: For each operator k, combine left and right results.
- Base cases: Single char evaluation.

### Top-Down Code:
```java
public class Solution {
    private Long[][][] memo;
    
    public int countWays(String expression) {
        int n = expression.length();
        memo = new Long[n][n][2];
        return (int) countWaysHelper(expression, 0, n-1, 1);
    }
    
    private long countWaysHelper(String exp, int i, int j, int isTrue) {
        // Base case: single character
        if (i == j) {
            if (isTrue == 1) return exp.charAt(i) == 'T' ? 1 : 0;
            else return exp.charAt(i) == 'F' ? 1 : 0;
        }
        // Base case: invalid range
        if (i > j) return 0;
        // Check memoized state
        if (memo[i][j][isTrue] != null) return memo[i][j][isTrue];
        
        long ways = 0;
        // Try each operator as split point
        for (int k = i + 1; k <= j - 1; k += 2) {
            char op = exp.charAt(k);
            // Compute left and right sub-expression results
            long leftTrue = countWaysHelper(exp, i, k-1, 1);
            long leftFalse = countWaysHelper(exp, i, k-1, 0);
            long rightTrue = countWaysHelper(exp, k+1, j, 1);
            long rightFalse = countWaysHelper(exp, k+1, j, 0);
            
            // Combine based on operator
            if (op == '&') {
                if (isTrue == 1) ways += leftTrue * rightTrue;
                else ways += leftTrue * rightFalse + leftFalse * rightTrue + leftFalse * rightFalse;
            } else if (op == '|') {
                if (isTrue == 1) ways += leftTrue * rightTrue + leftTrue * rightFalse + leftFalse * rightTrue;
                else ways += leftFalse * rightFalse;
            } else if (op == '^') {
                if (isTrue == 1) ways += leftTrue * rightFalse + leftFalse * rightTrue;
                else ways += leftTrue * rightTrue + leftFalse * rightFalse;
            }
        }
        
        return memo[i][j][isTrue] = ways % 1000000007;
    }
}
```

---

## Conclusion
Bitmask DP pattern bohot powerful hai jab subsets ya visited elements ke states track karne hon. Har problem ka core idea alag hai, lekin approach same:
- State define karo (pos, mask), transition clear karo, base cases set karo.
- Top-Down (memoization) zyada intuitive aur common hai Bitmask DP ke liye.
