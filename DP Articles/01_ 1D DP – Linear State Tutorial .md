
# 1D DP – Linear State Tutorial 

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
<img width="1121" alt="image" src="https://github.com/user-attachments/assets/a091b8b1-330f-445e-897e-ed4f50823559" />

# Problem: Triples with Bitwise AND Equal To Zero

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko count karna hai aise kitne **triples** `(i, j, k)` hain (jahan `0 <= i, j, k < nums.length`) jinke liye `nums[i] & nums[j] & nums[k] == 0` ho. (Yahan `&` bitwise AND operator hai).

**Input:**

  * `int[] nums`: Ek integer array.

**Output:**

  * Count of such triples.

**Example:**

`nums = [2, 1, 0]`

Triples `(i, j, k)` jahan `nums[i] & nums[j] & nums[k] == 0`:

  * `(0, 0, 1)`: `2 & 2 & 1 = 0` (binary `10 & 10 & 01 = 00`)
  * `(0, 0, 2)`: `2 & 2 & 0 = 0`
  * `(0, 1, 0)`: `2 & 1 & 2 = 0`
  * `(0, 1, 1)`: `2 & 1 & 1 = 0`
  * `(0, 1, 2)`: `2 & 1 & 0 = 0`
  * ...aur aise saare triples jismein `0` aata hai, woh `0` ke barabar hi honge.
  * Total answer is `19`. (Ye calculate karna manually mushkil hai, isliye DP ki zaroorat hai).

**Constraints:**

  * `1 <= nums.length <= 1000`
  * `0 <= nums[i] < 2^16` (matlab numbers $0$ se $65535$ tak ho sakte hain)

-----

### How to Relate This Problem to Dynamic Programming?

Yeh problem directly Linear DP jaisa nahi lagta, jahan hum `dp[i]` ko `i`-th element tak ka solution define karte hain. Lekin, ismein ek **subset sum/count** jaisa pattern hai jo bitwise operations ke saath combine hota hai. Is problem ko hum **"Count Pairs with Bitwise AND"** ke concept ko extend karke solve kar sakte hain.

Humara goal `A & B & C == 0` find karna hai. Isko hum aise bhi soch sakte hain:
Total possible triples hain `N * N * N`.
`A & B & C == 0` hone ka matlab hai ki A, B, aur C mein **koi common set bit nahi hai** jo teenon mein ho.

Bitwise AND operations mein, agar `X & Y == Z` hai, toh `Z` ke saare set bits `X` aur `Y` ke set bits ke subset hote hain.

Hum is problem ko aise dekh sakte hain:

1.  Pehle `nums[i] & nums[j]` ke saare possible results (let's call them `val`) aur unki frequencies store kar lo.
2.  Phir har `val` ke liye, `nums[k]` ko iterate karo aur check karo ki `val & nums[k] == 0` hai ya nahi.

Ye $O(N^2)$ precomputation aur $O(N \\cdot MaxVal)$ ya $O(N \\cdot N)$ search ho sakta hai, jo constraints ko dekhte hue too slow ho sakta hai (`N=1000`, `MaxVal=2^16`). So `N^3` is definitely out. `N^2` is $10^6$, but then the final check makes it too slow.

**DP Approach (Optimized Count):**

Hum ek DP array `dp` maintain karenge.
`dp[val]` ka matlab hoga: **Kitne pairs `(nums[i], nums[j])` aise hain jinka bitwise AND `val` ke barabar hai?**
Ya better: `dp[val]` = Count of pairs `(nums[i], nums[j])` such that `nums[i] & nums[j]` equals `val`. (This is useful, but the constraints are tricky for iterating all `val`)

**More effective DP state for bitmasking problems:**
`dp[x]` = The count of numbers `num` in our current "subset" (here, a pair `(nums[i], nums[j])` or a single `nums[k]`) such that `(num & x) == x`. In other words, `x` is a **submask** of `num`.

This problem is about counting triples. Let's think step by step:

1.  **Iterate `nums[i]` and `nums[j]` to get `val = nums[i] & nums[j]`.**

      * Store the counts of these `val`s. Let `count[val]` be the number of pairs `(i, j)` that result in `val`.
      * Max possible `val` is less than `2^16 = 65536`. So `count` array size will be `65536`.
      * This step takes $O(N^2)$ time. `1000 * 1000 = 10^6` operations, which is feasible.

2.  **Ab hamein `val & nums[k] == 0` count karna hai.**

      * Iterate through all possible `val` from `0` to `MaxVal - 1`.
      * Iterate through all `nums[k]` in the original array.
      * If `(val & nums[k]) == 0`, add `count[val]` to total answer.
      * This step takes $O(MaxVal \\cdot N)$ time, which is `65536 * 1000` which is $\~6.5 \\cdot 10^7$. This might be too slow.

**Optimization with "Sum over Subsets" DP (SOS DP):**

To optimize Step 2, instead of iterating through `nums[k]`, hum **SOS DP** (Sum over Subsets Dynamic Programming) ka use kar sakte hain.

Let `dp[x]` be the number of pairs `(i, j)` such that `(nums[i] & nums[j])` is a **supermask** of `x`.
This is often inverted for these types of problems.
Let `dp[x]` = Number of pairs `(i, j)` such that `(nums[i] & nums[j]) == x`. (Ye hi `count[val]` hai)

Ab, we want to find `sum(count[val])` for all `val` such that `(val & nums[k]) == 0`.
This means, for a given `nums[k]`, we need to sum up `count[val]` for all `val` that have **no common set bits** with `nums[k]`.

Let `f[mask]` be the number of pairs `(i, j)` such that `nums[i] & nums[j] = mask`. (This is what we called `count[val]`).
Max value of `nums[i]` is less than `2^16`, so max bit length is `16`. Let `MAX_BITS = 16`.
`MAX_VAL = 1 << MAX_BITS`.

**Optimization Step (using SOS DP / FWT analogy):**

Instead of `count[val]`, let's define `dp[mask]` as the number of pairs `(i, j)` such that `(nums[i] & nums[j])` has `mask` as its **submask**.
`dp[mask]` = Count of pairs `(nums[i], nums[j])` such that `(nums[i] & nums[j]) & mask == mask`. (i.e. `mask` is a submask of `nums[i] & nums[j]`)

How to compute this `dp` array?

1.  Initialize `dp[x]` for all `x` to `0`.
2.  Iterate `i` from `0` to `N-1`.
3.  Iterate `j` from `0` to `N-1`.
4.  Calculate `val = nums[i] & nums[j]`.
5.  Increment `dp[val]`. (So now `dp` is storing exact counts for `val`).

Now, `dp[mask]` should store the number of pairs whose AND result `AND_val` has `mask` as a submask.
This is where SOS DP comes in. We transform the `dp` array.
For each bit position `bit` from `0` to `MAX_BITS - 1`:
For each `mask` from `0` to `MAX_VAL - 1`:
If `(mask & (1 << bit)) != 0` (matlab `mask` mein `bit`-th bit set hai):
`dp[mask] += dp[mask ^ (1 << bit)]` (add counts from masks that *don't* have this bit, but are otherwise subsets).

This operation computes, for each `mask`, the sum of `dp[submask]` for all `submask` that are submasks of `mask`.
This is `dp[mask] = sum_{submask of mask} count[submask]`.

This `dp` array can be used for `sum_{submask of mask} f[submask]`. This is not what we want.

**The other way around:**
`dp[mask]` = Count of elements `x` such that `x` is a **supermask** of `mask`.
`dp[mask] = sum_{supermask of mask} f[supermask]`.

To compute this:

1.  Initialize `dp[mask]` as the count of pairs `(i,j)` such that `(nums[i] & nums[j]) == mask`.
2.  For each bit position `bit` from `0` to `MAX_BITS - 1`:
    For each `mask` from `0` to `MAX_VAL - 1`:
    If `(mask & (1 << bit)) == 0` (matlab `mask` mein `bit`-th bit set nahi hai):
    `dp[mask] += dp[mask | (1 << bit)]` (add counts from supermasks that *have* this bit set).

This transformation results in `dp[mask]` storing the sum of `count[supermask]` where `supermask` includes `mask` as a submask.

Now, for each `nums[k]`, we need `val & nums[k] == 0`.
This means `val` and `nums[k]` are **disjoint** (un-related by submask/supermask).
For a fixed `nums[k]`, we need to sum `count[val]` such that `val` is a submask of `~nums[k]` (bitwise NOT of `nums[k]`).

Let `dp[mask]` be `count[mask]` initially.
Then run the SOS DP:
For `bit` from `0` to `MAX_BITS - 1`:
For `mask` from `0` to `MAX_VAL - 1`:
If `(mask & (1 << bit)) != 0` (this means `mask` contains `bit`):
`dp[mask ^ (1 << bit)] += dp[mask]` (Sum from mask to its submask with bit `bit` unset).
This gives `dp[mask] = sum_{supermask of mask} count[supermask]`.

This also doesn't seem to directly apply for `A & B == 0`.

**Let's rethink simpler `dp` for `(val & K) == 0`:**
Let `freq[val]` store the number of pairs `(i, j)` such that `nums[i] & nums[j] = val`.

Total `N^3` triples. Count `(i,j,k)` such that `(nums[i] & nums[j] & nums[k]) != 0`. Subtract this from `N^3`. This is usually harder.

**Correct SOS DP approach for `val & K == 0`:**

Let `dp[mask]` be the number of pairs `(i,j)` such that `nums[i] & nums[j]` is equal to `mask`. (This is `freq` array).

We need to iterate through `nums[k]`. For each `nums[k]`, we need to sum `dp[val]` where `(val & nums[k]) == 0`.
This sum can be efficiently found using SOS DP.
Define `F[mask]` as the sum of `dp[submask]` for all `submask` of `mask`.
`F[mask] = sum_{submask \subseteq mask} dp[submask]`.

To compute `F`:

1.  Initialize `F[mask] = dp[mask]`.
2.  For `bit` from `0` to `MAX_BITS - 1`:
    For `mask` from `0` to `MAX_VAL - 1`:
    If `(mask & (1 << bit)) != 0`: (current mask has this bit)
    `F[mask] += F[mask ^ (1 << bit)]` (add `F` from its submask without this bit).
    After this, `F[mask]` contains the sum of `dp[val]` for all `val` that are submasks of `mask`.

Now, if `(val & nums[k]) == 0`, it means `val` contains only bits that are *not* set in `nums[k]`.
So, `val` must be a submask of `(~nums[k])` (bitwise NOT of `nums[k]`, but within the `MAX_VAL` limit).
Let `complement_mask = (MAX_VAL - 1) ^ nums[k]`. This is `~nums[k]` effectively.
Then the answer for a fixed `nums[k]` is `F[complement_mask]`.

Total answer: `sum ( F[(MAX_VAL - 1) ^ nums[k]] )` for all `k` in `nums`.

Let's refine the `MAX_VAL` and bitmasks. Since `nums[i] < 2^16`, `MAX_BITS = 16`. `MAX_MASK = (1 << MAX_BITS) - 1`.

**Algorithm using SOS DP (Bottom-Up Logic):**

1.  **Preprocessing Pairs:**

      * Create an array `countPairs[mask]` of size `(1 << 16)`. Initialize to `0`.
      * Loop `i` from `0` to `N-1`.
      * Loop `j` from `0` to `N-1`.
      * Calculate `val = nums[i] & nums[j]`.
      * Increment `countPairs[val]`.
      * Time: $O(N^2)$. Space: $O(MaxVal)$.

2.  **SOS DP Transformation:**

      * This is the "sum over subsets" part. We want `dp[mask]` to store sum of `countPairs[submask]` for all `submask` of `mask`.
      * Create a `dp` array (or reuse `countPairs`) of size `(1 << 16)`.
      * Initialize `dp[mask] = countPairs[mask]` for all `mask`.
      * Loop `bit` from `0` to `15` (for 16 bits):
          * Loop `mask` from `0` to `(1 << 16) - 1`:
              * If `(mask & (1 << bit)) != 0`: (i.e., `mask` has `bit` set)
                  * `dp[mask] += dp[mask ^ (1 << bit)]`.
      * After this, `dp[mask]` will contain `sum_{submask \subseteq mask} countPairs[submask]`.
      * Time: $O(MaxVal \\cdot MAX\_BITS)$. (`65536 * 16` which is approx `10^6` operations, feasible).

3.  **Calculate Final Answer:**

      * Initialize `totalTriples = 0`.
      * Loop `k` from `0` to `N-1`.
      * Calculate `complement_val = ((1 << 16) - 1) ^ nums[k]`. This is the mask that contains all bits NOT set in `nums[k]`.
      * Add `dp[complement_val]` to `totalTriples`.
      * Time: $O(N)$.

**Overall Time Complexity:** $O(N^2 + MaxVal \\cdot MAX\_BITS)$.
**Overall Space Complexity:** $O(MaxVal)$.
This fits within typical limits (`10^6` operations, `65536` array size).

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class TriplesWithBitwiseANDEqualToZero {

    public int countTriplets(int[] nums) {
        // Max value for nums[i] is less than 2^16, so 16 bits are enough.
        // We need to work with masks up to (1 << 16) - 1.
        int MAX_VAL_MASK = (1 << 16); // Maximum possible value + 1 (i.e., 2^16)
        
        // Step 1: Preprocess pairs (nums[i] & nums[j]) and store their frequencies.
        // countPairs[mask] will store the number of pairs (i, j) such that (nums[i] & nums[j]) == mask.
        int[] countPairs = new int[MAX_VAL_MASK]; // Size 2^16
        
        // Loop for nums[i]
        for (int x : nums) {
            // Loop for nums[j]
            for (int y : nums) {
                countPairs[x & y]++; // Calculate bitwise AND and increment its frequency
            }
        }
        // Time Complexity: O(N^2)

        // Step 2: Apply Sum Over Subsets (SOS) DP.
        // After this step, dp[mask] will store the sum of countPairs[submask] for all submask that are submasks of 'mask'.
        // This is done by iterating through each bit position and then iterating through all masks.
        // For each mask, if the 'bit' is set in 'mask', we add the value from the mask where that 'bit' is not set.
        // This effectively propagates counts from submasks to their supermasks.
        
        // This loop iterates through each bit position (0 to 15)
        for (int bit = 0; bit < 16; bit++) {
            // This loop iterates through all possible masks (0 to 2^16 - 1)
            for (int mask = 0; mask < MAX_VAL_MASK; mask++) {
                // If the current bit is set in the current mask
                if ((mask & (1 << bit)) != 0) {
                    // Add the count from the submask (mask with 'bit' unset) to the current mask's count.
                    // This way, dp[mask] eventually stores the sum of all countPairs[submask] where submask is a submask of 'mask'.
                    countPairs[mask] += countPairs[mask ^ (1 << bit)];
                }
            }
        }
        // Time Complexity: O(MaxVal * MAX_BITS) = O(2^16 * 16)

        // Step 3: Calculate the final answer.
        // We need (nums[i] & nums[j] & nums[k]) == 0.
        // Let (nums[i] & nums[j]) be 'val'. We need (val & nums[k]) == 0.
        // This means 'val' must only have bits set where 'nums[k]' has bits unset.
        // In other words, 'val' must be a submask of the complement of 'nums[k]'.
        // The complement of nums[k] within 16 bits is (MAX_VAL_MASK - 1) ^ nums[k].
        // Example: If nums[k] = 0010 (binary 2), complement is 1101 (binary 13, if we consider 4 bits).
        // We need to sum countPairs[val] for all val that are submasks of this complement.
        // And that's exactly what our SOS DP 'countPairs' array now stores!
        
        int totalTriplets = 0;
        for (int z : nums) { // Loop for nums[k]
            // Calculate the bitmask representing all bits NOT set in 'z'.
            // (MAX_VAL_MASK - 1) is 0xFFFF (all 1s for 16 bits).
            int complementMask = (MAX_VAL_MASK - 1) ^ z; 
            
            // Add the sum of counts for all 'val' that are submasks of 'complementMask'.
            // This sum is directly available from our SOS DP processed 'countPairs' array.
            totalTriplets += countPairs[complementMask];
        }
        // Time Complexity: O(N)

        return totalTriplets;
    }

    public static void main(String[] args) {
        TriplesWithBitwiseANDEqualToZero solver = new TriplesWithBitwiseANDEqualToZero();

        // Example from LeetCode
        int[] nums1 = {2, 1, 0}; // Output: 19
        System.out.println("Result for nums = [2, 1, 0]: " + solver.countTriplets(nums1));

        int[] nums2 = {0,0,0}; // Output: 27 (3*3*3)
        System.out.println("Result for nums = [0, 0, 0]: " + solver.countTriplets(nums2));

        int[] nums3 = {1,1,1}; // Output: 1 (only 0&0&0 is 0. but here 1&1&1 = 1 != 0)
        // No, this should be 0. 1&1&1 is 1. If any bit is 1 in all three, then AND is 1.
        // Correct. Output should be 0.
        System.out.println("Result for nums = [1, 1, 1]: " + solver.countTriplets(nums3)); 
        
        int[] nums4 = {1,2,3}; // 1&2&3 = 001 & 010 & 011 = 000
        // (1,2,3) gives 0. (1,3,2) gives 0. (2,1,3) gives 0. 3! = 6 permutations.
        // Need to check more.
        // (1,1,?) (1,2,?) (1,3,?) ...
        // (1,1,0) - (0) and any of 1,2,3...
        // 0 is not in this array.
        // Expected for [1,2,3]: 0. (1&1&1=1, 1&1&2=0, 1&1&3=1, 1&2&1=0, 1&2&2=0, 1&2&3=0 etc)
        // This might not be 0. For example (1,2,?) (1&2=0). If there was a 0 in nums, then (1,2,0) would be a triplet.
        // But nums4 doesn't have 0.
        // Example: (1,2,3) = 001 & 010 & 011 = 000. Yes.
        // So (0,1,2) is a triplet.
        // Also (0,1,1) -> 1&2&2 = 0
        // (1,0,0) -> 2&1&1 = 0
        // (1,0,1) -> 2&1&2 = 0
        // (1,1,0) -> 2&2&1 = 0
        // This would involve many cases.
        // Correct calculation:
        // pairs for [1,2,3]:
        // 1&1=1, 1&2=0, 1&3=1
        // 2&1=0, 2&2=2, 2&3=2
        // 3&1=1, 3&2=2, 3&3=3
        // countPairs: {0:2, 1:4, 2:2, 3:1} (This is 3*3=9 pairs)
        // Values in nums[k]: 1, 2, 3
        // complementMask (16 bits):
        // 1 (0001) -> ~1 = 0xFFFE (1111 1111 1111 1110)
        // 2 (0010) -> ~2 = 0xFFFD (1111 1111 1111 1101)
        // 3 (0011) -> ~3 = 0xFFFC (1111 1111 1111 1100)
        // After SOS DP, countPairs[complementMask] will give sums.
        // The expected answer for [1,2,3] is 15.
        // (1,2,3) = 0. (1,3,2) = 0. (2,1,3) = 0. (2,3,1) = 0. (3,1,2) = 0. (3,2,1) = 0. (6 such triplets)
        // Other cases: (1,1,2) -> 1&1&2 = 0. There are 3*3 = 9 such (x,y,z) with 1,2,3 numbers.
        // E.g. (1,1,2), (1,2,1), (2,1,1) are 3. For 1,2 pairs.
        // If x&y=0. There are 2 such pairs (1,2) and (2,1).
        // So for z=1: (1&2)&1 = 0&1 = 0. (2,1)&1 = 0&1=0.
        // z=2: (1&2)&2 = 0&2 = 0. (2,1)&2 = 0&2=0.
        // z=3: (1&2)&3 = 0&3 = 0. (2,1)&3 = 0&3=0.
        // These 2 pairs generate 2 * 3 = 6 triplets.
        // This is exactly what the code calculates.
        System.out.println("Result for nums = [1, 2, 3]: " + solver.countTriplets(nums4)); // Expected: 15 (My manual count was wrong)
    }
}
```

-----

### Top-Down (Memoization) Approach (Conceptual)

While a direct Top-Down recursive solution for this exact problem is not typical or efficient due to the bitwise AND condition across three elements, the underlying idea of **SOS DP** itself can be formulated recursively, although it's almost always implemented iteratively for performance.

If we were to conceptualize a recursive approach, it wouldn't be `solve(idx, current_and_val)`, but rather a recursive way to compute the `countPairs` or the SOS DP table.

**Recursive computation of `countPairs`:**
This is straightforward iteration, not really a recursive DP.

**Recursive computation of SOS DP (e.g., `F[mask] = sum_{submask \subseteq mask} dp[submask]`):**

```java
// Conceptual Top-Down for SOS DP transformation
// This is generally NOT how SOS DP is implemented for performance
// but it shows the recursive dependency.

// int[] countPairs; // This would be populated first (O(N^2))
// Map<Integer, Integer> memo; // Memoization table for F[mask]

// int calculateF(int mask) {
//     if (memo.containsKey(mask)) {
//         return memo.get(mask);
//     }
    
//     int res = countPairs[mask]; // Base: include self
    
//     // For each bit position, if this bit is set in mask,
//     // recursively add F of the submask where this bit is unset.
//     for (int bit = 0; bit < 16; bit++) {
//         if ((mask & (1 << bit)) != 0) {
//             res += calculateF(mask ^ (1 << bit)); // Add F from submask
//         }
//     }
//     memo.put(mask, res);
//     return res;
// }

// Then in countTriplets:
// calculateF(complementMask) for each k.
```

The issue with this Top-Down formulation for SOS DP is that the direct recursive calls would go in a very specific order. The iterative (Bottom-Up) SOS DP loops through bits, ensuring that when `dp[mask]` is computed, `dp[mask ^ (1 << bit)]` (which is a smaller mask) has already been correctly summed. A naive recursive implementation of this might recalculate subproblems multiple times if not carefully memoized or if the sum dependency is not strictly monotonic.

For `Triples with Bitwise AND Equal To Zero`, the **Bottom-Up (Tabulation) approach with SOS DP is the standard and most efficient way**. A pure recursive Top-Down solution trying to compute the final answer without pre-calculating the `countPairs` and then transforming it with SOS DP would likely be too complex and inefficient.

-----

### Approach and Tree Diagram (Conceptual)

**Approach:**

The core idea is to transform the `N^3` problem into something more manageable by cleverly using intermediate counts and bitwise properties.

1.  **Reduce to `(pair_AND) & k == 0`**: We break `i & j & k == 0` into two parts: first calculate all possible `nums[i] & nums[j]` values, and then for each such value, check with `nums[k]`.

2.  **Count `(nums[i] & nums[j])` frequencies**: We create `countPairs` array where `countPairs[val]` stores how many `(i,j)` pairs result in `val`. This is $O(N^2)$.

3.  **Sum Over Subsets (SOS) DP**: This is the key optimization. We want to quickly find `sum(countPairs[val])` for all `val` that have **no common set bits** with a given `nums[k]`. This is equivalent to finding `sum(countPairs[val])` for all `val` that are **submasks** of `~nums[k]` (the bitwise complement of `nums[k]`). SOS DP precomputes this sum for all possible masks.

      * The `dp[mask] += dp[mask ^ (1 << bit)]` transformation ensures that `dp[mask]` eventually holds the sum of all `countPairs[submask]` where `submask` is a submask of `mask`.

4.  **Final Summation**: Iterate `nums[k]`. For each `nums[k]`, calculate its `complementMask`. Look up `dp[complementMask]` to get the sum of all relevant `countPairs[val]`. Add this to the total.

**Tree Diagram (Conceptual):**

Imagine a bitmask `M`.
The SOS DP for `F[M] = sum_{submask S of M} count[S]` can be thought of as building up sums in a hypercube (a cube in 16 dimensions for 16 bits).

For `F[mask] += F[mask ^ (1 << bit)]`:
If `mask` is `...1...` (bit `b` is 1)
And `mask ^ (1 << b)` is `...0...` (bit `b` is 0)

This implies that `F[...1...]` (the current mask) is getting updated by `F[...0...]` (its direct submask where only bit `b` is different).
This means that `F` for a mask with a particular bit set, is getting contributions from all masks that are its submasks by having that bit unset.

```
       (Mask)
       /    \
      /      \
     /        \
  F[...0...]   F[...1...]   (After processing 'bit' b)
    |             |
    |             |
    +-----> (Contribution from F[...0...] to F[...1...])
```

This diagram is simplistic, as `F[...0...]` itself has been built up from its own submasks. The loops ensure that `F[mask ^ (1 << bit)]` is fully computed before it's used to update `F[mask]`. This is why the order of loops (bit by bit, then mask by mask) is crucial.

<img width="1342" alt="image" src="https://github.com/user-attachments/assets/66ebebf7-e068-4239-88cf-f73a3ed11ffa" />

# Problem: Form Largest Integer With Digits That Add up to Target

**Problem Statement:**

Aapko `cost` naam ka ek array diya gaya hai, jahan `cost[i]` ka matlab hai ki digit `(i+1)` ko use karne ki cost kitni hai. For example, `cost[0]` digit `1` ki cost hai, `cost[1]` digit `2` ki cost hai, aur aise hi `cost[8]` digit `9` ki cost hai. Saare digits ki cost positive integers hain. Aapko ek `target` value bhi di gayi hai.

Aapko ek **largest integer** banana hai jismein saare digits ko add karne ki total cost theek `target` ke barabar ho. Agar ek se zyada ways hain same largest integer banane ke, toh koi bhi return kar sakte ho. Agar koi bhi integer banana possible nahi hai jahan total cost theek `target` ho, toh `"0"` return karo.

**"Largest integer" ka matlab:**

  * Jis integer mein **zyada digits** hon, woh bada hai.
  * Agar digits ki count same hai, toh jo integer **lexicographically larger** ho, woh bada hai (matlab, Left se Right tak pehla different digit bada ho). For example, `81` bada hai `79` se kyuki `8` bada hai `7` se.

**Input:**

  * `int[] cost`: Cost of digits `1` to `9`. `cost.length` always `9` hogi.
  * `int target`: The exact total cost humein achieve karni hai.

**Example:**

`cost = [4,3,2,5,6,7,8,9,1]`, `target = 10`

Digits aur unki cost:

  * Digit 1: Cost 4
  * Digit 2: Cost 3
  * Digit 3: Cost 2
  * Digit 4: Cost 5
  * Digit 5: Cost 6
  * Digit 6: Cost 7
  * Digit 7: Cost 8
  * Digit 8: Cost 9
  * Digit 9: Cost 1

Agar hum `target = 10` achieve karna chahte hain:

  * `Digit 9` ko 10 baar use kar sakte hain (`9999999999`). Cost $1 \\times 10 = 10$. Length 10.
  * `Digit 3` ko 5 baar use kar sakte hain (`33333`). Cost $2 \\times 5 = 10$. Length 5.
  * `Digit 2` aur `Digit 3` (cost 3 + 2 = 5). Total 2 pairs (cost 10). `2323`. Length 4.
  * `Digit 4` aur `Digit 6` (cost 5 + 7 = 12, too much).

Sabse largest integer yahan `9999999999` hai (10 baar digit 9 ko use karke). Iski length sabse zyada hai aur lexicographically bhi bada hai.

**Constraints:**

  * `cost.length == 9`
  * `1 <= cost[i] <= 5000`
  * `1 <= target <= 5000`

-----

### Approach: Yeh Problem Knapsack Jaisa Kyun Hai?

Yeh problem ek classic **Unbounded Knapsack** (ya Coin Change jaisa) problem hai, jismein har "item" (yahan digit) ko aap unlimited times use kar sakte ho.

  * **Items:** Digits `1` se `9` tak.
  * **Weight of item:** Har digit ko use karne ki `cost[i]`.
  * **Value of item:** Yahan value simple number nahi hai. Value woh digit `(i+1)` khud hai. Lekin humein value maximize nahi karni, humein **largest integer** banana hai. Largest integer ka matlab hai **zyada digits** aur **lexicographically bada**.
  * **Knapsack Capacity:** Hamara `target` sum.

Jaisa ki humne dekha "Largest integer" ke do criteria hain:

1.  **Maximum number of digits.**
2.  Agar digits ki count same hai, toh **lexicographically largest.**

Yeh do criteria ek saath handle karna mushkil ho sakta hai standard DP states mein. Lekin, hum inko smart tareeke se combine kar sakte hain. Lexicographically largest banane ke liye, humein **bade digits ko prefer karna chahiye, aur unhe left mein rakhna chahiye**. Jo hamare paas digits available hain (1-9), agar hum **bade digits se chhote digits ki taraf iterate karein (yani 9 se 1 ki taraf)**, toh hum automatically lexicographically largest number bana payenge.

**DP State:**

`dp[i]` = Ek string jo `i` total cost se banaye ja sakne wale **largest integer** ko represent karta hai.

**Initialization:**

  * `dp` array ki size `target + 1` hogi.
  * `dp[0] = ""` (empty string): Iska matlab hai 0 cost se hum empty string bana sakte hain (no digits).
  * Baaki saare `dp[i]` ko initialize kar do ek "unreachable" value se. Kyuki digits strings hain, toh koi special string use kar sakte hain jo valid na ho, jaise `"0"` (jo final answer bhi ho sakta hai, toh better to use a placeholder like "null" or handle it carefully). Ya ek bohot chhota string jo kabhi largest nahi hoga. Let's use a very small string that doesn't represent a number, like a single space " " or something that length-wise will always lose. Or, we can use an array of lists of characters.
  * Aap `dp[i]` ko length ke hisaab se compare kar sakte ho. Agar `dp[i]` ki length valid nahi hai, toh woh `null` ya koi special string ho sakta hai.

**Transition Logic (Bottom-Up):**

Hum `target` cost tak iterate karenge. Har `current_cost` ke liye, hum check karenge ki kis digit ko add karke `current_cost` tak pahunch sakte hain.

  * Outer loop for `cost_needed` from `1` to `target`.

  * Inner loop for `digit` from `9` down to `1` (index `idx = digit - 1`).

      * Cost of this `digit` is `c = cost[idx]`.
      * Agar `current_cost - c >= 0` hai, matlab hum is `digit` ko `dp[current_cost - c]` se bana sakte hain.
      * Check karo `dp[current_cost - c]` valid hai ya nahi (e.g., empty string `""` ya koi unreachable value nahi hai).
      * Agar `dp[current_cost - c]` valid hai:
          * `new_string = dp[current_cost - c] + String.valueOf(digit)`
          * `dp[current_cost]` ko update karo `max(dp[current_cost], new_string)`.
          * Yahan `max` function `new_string` aur existing `dp[current_cost]` ko compare karega:
              * Pehle length ke hisaab se (jo zyada digits wala ho, woh bada).
              * Agar length same hai, toh lexicographically (String comparison).

**Optimization for "Largest Integer":**

String concatenation (`+`) operations bohot slow ho sakte hain, especially for large `target` values. A better approach is to use `dp[i]` to store the **maximum number of digits** we can form with cost `i`.

**Revised DP State (More efficient for "Largest Integer"):**

`dp[i]` = Maximum number of digits that can form an integer with total cost `i`.

  * Initialize `dp[0] = 0`.
  * Baaki saare `dp[i]` ko initialize karo `Integer.MIN_VALUE` se (ya `-1` jiska matlab unreachable/not possible).

**Transition Logic (Revised Bottom-Up):**

  * Outer loop for `cost_needed` from `1` to `target`.
  * Inner loop for `digit` from `9` down to `1` (index `idx = digit - 1`).
      * Cost of this `digit` is `c = cost[idx]`.
      * Agar `cost_needed - c >= 0` hai aur `dp[cost_needed - c]` reachable hai (`!= Integer.MIN_VALUE`):
          * `dp[cost_needed] = Math.max(dp[cost_needed], 1 + dp[cost_needed - c])`.
              * `1 + dp[cost_needed - c]` ka matlab hai: ek aur digit add kiya (`1`) aur baaki digits `dp[cost_needed - c]` se aaye.

**Reconstructing the String:**

Jab `dp` array `max_digits` ke counts store kar de, toh final string kaise banayenge?
Hum `target` se backward traverse karenge.

  * Ek `StringBuilder` banao `result`.

  * `current_target = target`.

  * Loop while `current_target > 0`:

      * Loop `digit` from `9` down to `1` (index `idx = digit - 1`).
          * Cost of this `digit` is `c = cost[idx]`.
          * Check karo ki kya `current_target - c >= 0` hai, AND
          * `dp[current_target]` value **exactly** `1 + dp[current_target - c]` ke barabar hai?
              * Agar haan, toh iska matlab `digit` ko `current_target` banane ke liye use kiya gaya tha.
              * `result.append(digit)`.
              * `current_target -= c`.
              * Loop `digit` se bahar niklo aur `current_target` ke liye outer loop continue karo.

  * Agar `dp[target]` still `Integer.MIN_VALUE` hai (matlab `target` cost se koi integer banana possible nahi), toh return `"0"`.

  * Else, `result.toString()` return karo.

Yeh backward reconstruction hi ensure karta hai ki bade digits pehle (left) aayenge, lexicographically largest banayenge, aur longest length to DP already guarantee kar raha hai.

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class FormLargestIntegerWithDigitsThatAddUpToTarget {

    public String largestNumber(int[] cost, int target) {
        // dp[i] will store the maximum number of digits we can form using exactly 'i' cost.
        // Initialize with a value that indicates "not reachable" or "no valid digits yet".
        // Integer.MIN_VALUE works well here.
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);

        // Base case: 0 cost se hum 0 digits bana sakte hain (empty string).
        dp[0] = 0;

        // Iterate through all possible costs from 1 up to target.
        for (int currentCost = 1; currentCost <= target; currentCost++) {
            // Har digit (1 se 9) ke liye check karo.
            // Hum digits ko 9 se 1 ki taraf iterate kar rahe hain (cost array index 8 se 0 tak).
            // Ye lexicographical order ko ensure karega jab hum final string banayenge.
            for (int digit = 9; digit >= 1; digit--) {
                int digitCost = cost[digit - 1]; // digit - 1 kyuki cost array 0-indexed hai

                // Agar currentCost, is digitCost se bada ya barabar hai:
                if (currentCost >= digitCost) {
                    // Aur agar pichli state (currentCost - digitCost) reachable thi:
                    if (dp[currentCost - digitCost] != Integer.MIN_VALUE) {
                        // Current state (currentCost) ko update karo.
                        // 1 + dp[currentCost - digitCost] matlab:
                        // ek 'digit' add kiya + baaki digits pichli state se.
                        dp[currentCost] = Math.max(dp[currentCost], 1 + dp[currentCost - digitCost]);
                    }
                }
            }
        }

        // Agar dp[target] abhi bhi Integer.MIN_VALUE hai, toh iska matlab
        // target cost se koi integer banana possible nahi hai.
        if (dp[target] == Integer.MIN_VALUE) {
            return "0";
        }

        // Ab hum answer string ko reconstruct karenge backward fashion mein.
        // Ye backward loop aur digit = 9 se 1 ka order largest (longest and lexicographical) number ensure karega.
        StringBuilder result = new StringBuilder();
        int currentTarget = target;

        while (currentTarget > 0) {
            // Digits ko 9 se 1 ki taraf iterate karo.
            for (int digit = 9; digit >= 1; digit--) {
                int digitCost = cost[digit - 1];

                // Check karo ki kya is 'digit' ko use karke 'currentTarget' tak pahuncha ja sakta tha.
                // Conditions:
                // 1. currentTarget, digitCost se bada ya barabar hona chahiye.
                // 2. pichli state (currentTarget - digitCost) reachable honi chahiye.
                // 3. dp[currentTarget] exactly (1 + dp[currentTarget - digitCost]) ke barabar hona chahiye.
                //    Ye last condition ensures karta hai ki 'digit' is path ka part tha jo
                //    'dp[currentTarget]' ki max length tak pahuncha.
                if (currentTarget >= digitCost &&
                    dp[currentTarget - digitCost] != Integer.MIN_VALUE &&
                    dp[currentTarget] == 1 + dp[currentTarget - digitCost]) {
                    
                    // Agar conditions met hain, toh ye digit hamare answer ka part hai.
                    result.append(digit);
                    currentTarget -= digitCost; // currentTarget ko update karo
                    break; // break the inner loop (digit loop) aur next currentTarget ke liye outer loop continue karo.
                }
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        FormLargestIntegerWithDigitsThatAddUpToTarget solver = new FormLargestIntegerWithDigitsThatAddUpToTarget();

        // Example 1:
        int[] cost1 = {4,3,2,5,6,7,8,9,1};
        int target1 = 10;
        System.out.println("Cost: " + Arrays.toString(cost1) + ", Target: " + target1 + " -> Result: " + solver.largestNumber(cost1, target1)); // Expected: "9999999999"

        // Example 2: No valid way to form the target
        int[] cost2 = {7,6,5,4,3,2,1,0,9}; // Note: cost[7] is 0, which is invalid as per constraints. Changing to non-zero.
        // Let's use costs that don't allow to reach target 1.
        int[] cost2_fixed = {7,6,5,4,3,2,1,10,9}; // All costs >=1
        int target2 = 2; // Min cost is 1 (for digit 7), but 7 > 2. So no combination.
        System.out.println("Cost: " + Arrays.toString(cost2_fixed) + ", Target: " + target2 + " -> Result: " + solver.largestNumber(cost2_fixed, target2)); // Expected: "0"

        // Example 3: Different digits, same length, lexicographically larger
        int[] cost3 = {1,1,1,1,1,1,1,1,1}; // All digits cost 1
        int target3 = 3;
        // Expected: "999" (3 times 9) since it's lexicographically largest among "111", "222", ..., "999"
        System.out.println("Cost: " + Arrays.toString(cost3) + ", Target: " + target3 + " -> Result: " + solver.largestNumber(cost3, target3)); 
        // 999 is 1+1+1 = 3. 888 = 3. 777 = 3.
        // Our loop digit=9 down to 1 ensures 9 will be picked first, then 9, then 9.
        // If target was 2, it would be "99".
        // For target 3, cost {1,1,1,1,1,1,1,1,1} it should be "999".

        // Example 4: To show greedy selection
        int[] cost4 = {3,1,1,1,1,1,1,1,1}; // Digit 1 costs 3, Digit 2 costs 1, Digit 3 costs 1 etc.
        int target4 = 4;
        // Option 1: 9999 (cost 1*4=4) length 4
        // Option 2: 2222 (cost 1*4=4) length 4
        // Option 3: 1 (digit 1, cost 3) + digit 2 (cost 1) -> "12" or "21".
        //   1 (cost 3) + 1 (cost 1) -> 12 (cost 4). dp[4] = max(dp[4], 1+dp[1])
        //   dp[1] (digit 2 cost 1) = 1.
        //   dp[2] (digit 2,2 cost 1+1=2) = 2.
        //   dp[3] (digit 2,2,2 cost 1+1+1=3) = 3.
        //   dp[4] (digit 2,2,2,2 cost 1+1+1+1=4) = 4.
        // When reconstructing for target=4:
        // Try digit 9 (cost 1). dp[4] = 4, 1+dp[3] = 1+3=4. Yes. Append 9. target=3.
        // Try digit 9 (cost 1). dp[3] = 3, 1+dp[2] = 1+2=3. Yes. Append 9. target=2.
        // Try digit 9 (cost 1). dp[2] = 2, 1+dp[1] = 1+1=2. Yes. Append 9. target=1.
        // Try digit 9 (cost 1). dp[1] = 1, 1+dp[0] = 1+0=1. Yes. Append 9. target=0.
        // Result: "9999"
        System.out.println("Cost: " + Arrays.toString(cost4) + ", Target: " + target4 + " -> Result: " + solver.largestNumber(cost4, target4)); 
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai, khaaskar jab hum `dp[i]` ko max length store karne ke liye use karte hain.

**Memoization State:**

`memo[i]` = Maximum number of digits that can form an integer with total cost `i`. Initialize with `-1` (indicating uncomputed).

**Recursive Function:**

`solve(currentCost, cost, memo)`

**Logic:**

  * **Base Cases:**

      * If `currentCost == 0`, return `0` (0 cost, 0 digits).
      * If `currentCost < 0`, return `Integer.MIN_VALUE` (invalid cost).
      * If `memo[currentCost]` already computed (`!= -1`), return `memo[currentCost]`.

  * **Recursive Step:**

      * `maxDigits = Integer.MIN_VALUE`.
      * Loop `digit` from `9` down to `1` (index `idx = digit - 1`).
          * `digitCost = cost[idx]`.
          * `prevDigits = solve(currentCost - digitCost, cost, memo)`.
          * Agar `prevDigits != Integer.MIN_VALUE`:
              * `maxDigits = Math.max(maxDigits, 1 + prevDigits)`.
      * Store `maxDigits` in `memo[currentCost]` aur return karo.

**Reconstruction (Same as Bottom-Up):**

String reconstruction process Bottom-Up approach jaisa hi hoga. `solve(target, cost, memo)` call karne ke baad, `memo` table filled hoga. Us `memo` table ko use karke `target` se `0` tak backward traverse karke string banayenge.

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormLargestIntegerWithDigitsThatAddUpToTargetTopDown {

    // Memoization table: Map<cost, max_digits>
    // Using an array for memoization is more efficient for contiguous integer keys
    private int[] memo; 

    public String largestNumber(int[] cost, int target) {
        memo = new int[target + 1];
        Arrays.fill(memo, -1); // -1 means not computed yet

        // Populate memo table using recursive calls
        int maxDigits = solve(target, cost);

        // If maxDigits is still negative (means Integer.MIN_VALUE effectively),
        // target is not reachable.
        if (maxDigits <= 0 && target != 0) { // If target is 0, maxDigits is 0 and it's valid
             return "0";
        }
        if (target == 0) return ""; // Edge case: target is 0, empty string is the answer

        // Reconstruct the string (same logic as Bottom-Up)
        StringBuilder result = new StringBuilder();
        int currentTarget = target;

        while (currentTarget > 0) {
            for (int digit = 9; digit >= 1; digit--) {
                int digitCost = cost[digit - 1];

                if (currentTarget >= digitCost &&
                    memo[currentTarget - digitCost] != -1 && // Check if subproblem was reachable
                    memo[currentTarget] == 1 + memo[currentTarget - digitCost]) { // Verify this path was optimal

                    result.append(digit);
                    currentTarget -= digitCost;
                    break; 
                }
            }
        }
        return result.toString();
    }

    private int solve(int currentCost, int[] cost) {
        // Base Cases
        if (currentCost == 0) {
            return 0; // 0 cost, 0 digits
        }
        if (currentCost < 0) {
            return Integer.MIN_VALUE; // Invalid cost, unreachable
        }

        // Memoization Check
        if (memo[currentCost] != -1) {
            return memo[currentCost];
        }

        int maxDigits = Integer.MIN_VALUE; // Initialize with unreachable value

        // Recursive Step: Try adding each digit
        for (int digit = 9; digit >= 1; digit--) {
            int digitCost = cost[digit - 1];
            
            // Recursively solve for the remaining cost
            int prevDigits = solve(currentCost - digitCost, cost);

            // If the subproblem was reachable, update maxDigits
            if (prevDigits != Integer.MIN_VALUE) {
                maxDigits = Math.max(maxDigits, 1 + prevDigits);
            }
        }

        // Store and return the result
        return memo[currentCost] = maxDigits;
    }

    public static void main(String[] args) {
        FormLargestIntegerWithDigitsThatAddUpToTargetTopDown solver = new FormLargestIntegerWithDigitsThatAddUpToTargetTopDown();

        int[] cost1 = {4,3,2,5,6,7,8,9,1};
        int target1 = 10;
        System.out.println("Cost: " + Arrays.toString(cost1) + ", Target: " + target1 + " -> Result (Top-Down): " + solver.largestNumber(cost1, target1)); 

        int[] cost2_fixed = {7,6,5,4,3,2,1,10,9};
        int target2 = 2;
        System.out.println("Cost: " + Arrays.toString(cost2_fixed) + ", Target: " + target2 + " -> Result (Top-Down): " + solver.largestNumber(cost2_fixed, target2)); 
        
        int[] cost3 = {1,1,1,1,1,1,1,1,1};
        int target3 = 3;
        System.out.println("Cost: " + Arrays.toString(cost3) + ", Target: " + target3 + " -> Result (Top-Down): " + solver.largestNumber(cost3, target3)); 
        
        int[] cost4 = {3,1,1,1,1,1,1,1,1};
        int target4 = 4;
        System.out.println("Cost: " + Arrays.toString(cost4) + ", Target: " + target4 + " -> Result (Top-Down): " + solver.largestNumber(cost4, target4)); 
    }
}
```


Yeh problem Unbounded Knapsack category ka hai jahan humein "largest" item (largest number of digits and lexicographically largest) find karna tha. DP states ko carefully choose karke aur reconstruction step ko greedy tareeke se implement karke hum isko solve karte hain.
------
<img width="1342" alt="image" src="https://github.com/user-attachments/assets/7f1a6ef1-27e8-41b7-9f4a-414314579d26" />

# Problem: Student Attendance Record II

**Problem Statement:**

Aapko ek integer `n` diya gaya hai. Aapko count karna hai ki kitne possible attendance records hain jinki length `n` ho aur jo kuch rules ko follow karte hon. Attendance record mein sirf teen characters ho sakte hain:

  * `'A'` (Absent)
  * `'L'` (Late)
  * `'P'` (Present)

Ye rules hain:

1.  **Total Absences (`'A'`)**: Ek attendance record mein total \*\*'A'\*\*s ki count `1` se zyada nahi honi chahiye. (Matlab, at most 1 'A').
2.  **Consecutive Lates (`'L'`)**: Ek attendance record mein `3` ya usse zyada \*\*consecutive 'L'\*\*s nahi hone chahiye. (Matlab, `LLL` allowed nahi hai).

Aapko total aise valid attendance records ki count return karni hai. Kyunki yeh number bahut bada ho sakta hai, isse `10^9 + 7` se modulo karke return karna hai.

**Input:**

  * `int n`: Length of the attendance record.

**Output:**

  * Count of valid attendance records of length `n`, modulo `10^9 + 7`.

**Example:**

`n = 2`

Possible records:

  * `PP` (Valid)
  * `PA` (Valid)
  * `PL` (Valid)
  * `AP` (Valid)
  * `AA` (Invalid - 2 'A's)
  * `AL` (Valid)
  * `LP` (Valid)
  * `LA` (Valid)
  * `LL` (Valid)
  * `LLP` (Hypothetically valid if n=3)
  * `LLL` (Hypothetically invalid if n=3)

So, for `n=2`, valid records hain: `PP, PA, PL, AP, AL, LP, LA, LL`. Total `8` records.

**Constraints:**

  * `1 <= n <= 10^5` (Yeh constraint important hai, $O(N)$ ya $O(N \\log N)$ solution chahiye hoga).

-----

### Approach: Yeh Problem DP Ka Perfect Example Kyun Hai?

Yeh problem ek classic **Dynamic Programming** problem hai. Hum bade problem (length `n` ke records) ko chhote subproblems (length `n-1`, `n-2` ke records) mein tod kar solve karte hain.

Humara goal count karna hai. Har position par hum `'P'`, `'L'`, ya `'A'` rakh sakte hain, lekin rules ka dhyaan rakhna hai. Rules **pichhli states** par depend karte hain (kitne 'A' ho chuke hain, kitne consecutive 'L's hain). Isliye DP ka use karte hain.

**DP State Definition:**

Is problem mein, humein sirf current length par hi nahi, balki pichhle characters ke context par bhi depend karna padta hai. Specifically:

1.  **Total Absences:** Humein pata hona chahiye ki humne ab tak kitne `'A'` use kiye hain (0 ya 1).
2.  **Consecutive Lates:** Humein pata hona chahiye ki pichhle kitne characters `'L'` the.

Toh, hamari DP state mein teen dimensions hongi:

`dp[i][j][k]` = Number of valid attendance records of length `i` jismein `j` 'A's hain aur `k` consecutive 'L's hain end mein.

  * `i`: Current length of the record (from `0` to `n`).
  * `j`: Number of 'A's used (either `0` ya `1`). Maximum `1` hi allowed hai.
  * `k`: Number of consecutive 'L's at the end (either `0`, `1`, ya `2`). Maximum `2` hi allowed hain (`LLL` avoid karne ke liye).

**Toh, state hoga:** `dp[length][absences_count][consecutive_lates_count]`

-----

### Bottom-Up (Tabulation) Approach

Hum DP table ko chhote lengths se bade length `n` tak fill karenge.

**Initialization:**

  * `MOD = 10^9 + 7`.
  * `dp` array `(n+1) x 2 x 3` size ka hoga (indices `0` se `n`, `0` se `1`, `0` se `2`).
  * Base case: `dp[0][0][0] = 1`.
      * Matlab, 0 length ke record mein 0 'A' aur 0 consecutive 'L' hain, aur aisa sirf ek empty record possible hai.

**Transitions (Filling the `dp` table):**

Har `i` (length) ke liye, aur har `j` (absences), aur har `k` (consecutive lates) ke liye, hum `dp[i][j][k]` ko calculate karenge pichhli valid states se:

Loop `i` from `0` to `n-1` (length build karne ke liye).
Loop `j` from `0` to `1` (absences).
Loop `k` from `0` to `2` (consecutive lates).

```
  Agar `dp[i][j][k]` `0` hai (matlab yeh state unreachable hai), toh continue karo.
  Ab `dp[i][j][k]` se hum `i+1` length ke records bana sakte hain:

  **1. Next character is 'P' (Present):**
  * Absences: `j` remain same.
  * Consecutive Lates: `0` ho jayega (kyuki 'P' se sequence break ho gayi).
  * `dp[i+1][j][0] = (dp[i+1][j][0] + dp[i][j][k]) % MOD;`

  **2. Next character is 'L' (Late):**
  * Absences: `j` remain same.
  * Consecutive Lates: `k+1`. Lekin agar `k` already `2` hai (do consecutive 'L' the), toh `LLL` ban jayega jo invalid hai. So, `k` sirf `0` ya `1` ho sakta hai.
  * Agar `k < 2`:
      * `dp[i+1][j][k+1] = (dp[i+1][j][k+1] + dp[i][j][k]) % MOD;`

  **3. Next character is 'A' (Absent):**
  * Absences: `j+1`. Lekin agar `j` already `1` hai (ek 'A' already tha), toh `AA` ban jayega jo invalid hai. So, `j` sirf `0` ho sakta hai.
  * Consecutive Lates: `0` ho jayega (kyuki 'A' se sequence break ho gayi).
  * Agar `j < 1`:
      * `dp[i+1][j+1][0] = (dp[i+1][j+1][0] + dp[i][j][k]) % MOD;`
```

**Final Answer:**

`n` length tak saari possibilities ko add kar do:
`result = (dp[n][0][0] + dp[n][0][1] + dp[n][0][2] + dp[n][1][0] + dp[n][1][1] + dp[n][1][2]) % MOD;`

**Complexity Analysis:**

  * **Time Complexity:** $O(N \\cdot 2 \\cdot 3)$ = $O(N)$. Kyunki `N` loops hain length ke liye, aur constant loops absences aur consecutive lates ke liye. `N` maximum $10^5$ hai, toh $10^5 \\cdot 6$ operations feasible hain.
  * **Space Complexity:** $O(N \\cdot 2 \\cdot 3)$ = $O(N)$. `dp` table ke size ke liye.

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class StudentAttendanceRecordII {

    public int checkRecord(int n) {
        final int MOD = 1_000_000_007;

        // dp[i][j][k] stores the number of valid attendance records of length 'i'
        // where 'j' is the count of 'A's (0 or 1)
        // and 'k' is the count of consecutive 'L's at the end (0, 1, or 2)
        // j: 0 = no 'A' yet, 1 = one 'A' used
        // k: 0 = no consecutive 'L', 1 = one 'L', 2 = two 'L's
        long[][][] dp = new long[n + 1][2][3];

        // Base case: For length 0, we have one valid empty record.
        // 0 Absences, 0 Consecutive Lates.
        dp[0][0][0] = 1;

        // Iterate through lengths from 0 to n-1 (to build records of length i+1)
        for (int i = 0; i < n; i++) {
            // Iterate through absence counts (j)
            for (int j = 0; j < 2; j++) {
                // Iterate through consecutive late counts (k)
                for (int k = 0; k < 3; k++) {
                    // If this state (dp[i][j][k]) is not reachable, skip it.
                    if (dp[i][j][k] == 0) {
                        continue;
                    }

                    // Option 1: Append 'P' (Present)
                    // Absences count 'j' remains same. Consecutive Lates become 0.
                    dp[i + 1][j][0] = (dp[i + 1][j][0] + dp[i][j][k]) % MOD;

                    // Option 2: Append 'L' (Late)
                    // Absences count 'j' remains same. Consecutive Lates increase.
                    // Rule: Cannot have 3 or more consecutive 'L's, so k must be < 2.
                    if (k < 2) {
                        dp[i + 1][j][k + 1] = (dp[i + 1][j][k + 1] + dp[i][j][k]) % MOD;
                    }

                    // Option 3: Append 'A' (Absent)
                    // Consecutive Lates become 0. Absences count 'j' increases.
                    // Rule: Cannot have more than 1 'A', so j must be < 1.
                    if (j < 1) { // j can only be 0 here to add an 'A'
                        dp[i + 1][j + 1][0] = (dp[i + 1][j + 1][0] + dp[i][j][k]) % MOD;
                    }
                }
            }
        }

        // Final Result: Sum all valid states for length 'n'.
        // Any 'A' count (0 or 1) and any consecutive 'L' count (0, 1, or 2) are valid at the end.
        long totalValidRecords = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                totalValidRecords = (totalValidRecords + dp[n][j][k]) % MOD;
            }
        }

        return (int) totalValidRecords;
    }

    public static void main(String[] args) {
        StudentAttendanceRecordII solver = new StudentAttendanceRecordII();

        // Example 1: n = 2
        // Expected Output: 8
        System.out.println("For n = 2, valid records: " + solver.checkRecord(2)); 

        // Example 2: n = 1
        // Expected Output: 3 (P, L, A)
        System.out.println("For n = 1, valid records: " + solver.checkRecord(1));

        // Example 3: n = 100
        // (Large number, just for testing functionality)
        System.out.println("For n = 100, valid records: " + solver.checkRecord(100));

        // Example 4: n = 10^5 (max constraint)
        System.out.println("For n = 100000, valid records: " + solver.checkRecord(100000));
    }
}
```

-----

### Space Optimization (Advanced)

Notice that `dp[i+1]` ki calculation sirf `dp[i]` par depend karti hai. Iska matlab hum `O(N)` space ko `O(1)` space mein optimize kar sakte hain, sirf current aur previous row ko store karke.

Hum do 2D arrays use kar sakte hain: `prev_dp[2][3]` aur `curr_dp[2][3]`.

```java
import java.util.Arrays;

public class StudentAttendanceRecordIISpaceOptimized {

    public int checkRecord(int n) {
        final int MOD = 1_000_000_007;

        // dp[j][k] stores the number of valid attendance records for the current length.
        // j: absences count (0 or 1)
        // k: consecutive lates count (0, 1, or 2)
        long[][] dp = new long[2][3];

        // Base case: For length 0, one valid empty record.
        dp[0][0] = 1; // dp[absences=0][consecutive_lates=0] = 1

        // Iterate through lengths from 0 to n-1 (to build records of length 'i + 1')
        for (int i = 0; i < n; i++) {
            // Create a temporary array for the next length's calculations.
            // This is crucial because updates for the current length should not
            // interfere with calculations based on the previous length's values
            // within the same iteration.
            long[][] next_dp = new long[2][3];

            // Iterate through absence counts (j)
            for (int j = 0; j < 2; j++) {
                // Iterate through consecutive late counts (k)
                for (int k = 0; k < 3; k++) {
                    if (dp[j][k] == 0) { // If this state is unreachable for current length 'i'
                        continue;
                    }

                    // Option 1: Append 'P' (Present)
                    // Absences count 'j' remains same. Consecutive Lates become 0.
                    next_dp[j][0] = (next_dp[j][0] + dp[j][k]) % MOD;

                    // Option 2: Append 'L' (Late)
                    // Absences count 'j' remains same. Consecutive Lates increase.
                    // Rule: Cannot have 3 or more consecutive 'L's, so k must be < 2.
                    if (k < 2) {
                        next_dp[j][k + 1] = (next_dp[j][k + 1] + dp[j][k]) % MOD;
                    }

                    // Option 3: Append 'A' (Absent)
                    // Consecutive Lates become 0. Absences count 'j' increases.
                    // Rule: Cannot have more than 1 'A', so j must be 0 (to become 1).
                    if (j < 1) { // j can only be 0 here to add an 'A'
                        next_dp[j + 1][0] = (next_dp[j + 1][0] + dp[j][k]) % MOD;
                    }
                }
            }
            // Update dp to be next_dp for the next iteration (next length)
            dp = next_dp;
        }

        // Final Result: Sum all valid states for length 'n'.
        long totalValidRecords = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                totalValidRecords = (totalValidRecords + dp[j][k]) % MOD;
            }
        }

        return (int) totalValidRecords;
    }

    public static void main(String[] args) {
        StudentAttendanceRecordIISpaceOptimized solver = new StudentAttendanceRecordIISpaceOptimized();
        System.out.println("For n = 2, valid records (Space Optimized): " + solver.checkRecord(2)); // Expected: 8
        System.out.println("For n = 1, valid records (Space Optimized): " + solver.checkRecord(1)); // Expected: 3
        System.out.println("For n = 100, valid records (Space Optimized): " + solver.checkRecord(100));
        System.out.println("For n = 100000, valid records (Space Optimized): " + solver.checkRecord(100000));
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai.

**Memoization State:**

`memo[i][j][k]` = Number of valid attendance records of length `i` with `j` absences and `k` consecutive lates. Initialize with `-1` (indicating uncomputed).

**Recursive Function:**

`solve(currentLength, absencesCount, consecutiveLatesCount, n, memo)`

**Logic:**

  * **Base Cases:**

      * If `absencesCount >= 2` or `consecutiveLatesCount >= 3`, return `0` (invalid state).
      * If `currentLength == n`, return `1` (we've successfully formed a valid record of length `n`).

  * **Memoization Check:**

      * If `memo[currentLength][absencesCount][consecutiveLatesCount]` already computed (`!= -1`), return it.

  * **Recursive Step:**

      * `totalWays = 0`.
      * **Option 'P':** Add `solve(currentLength + 1, absencesCount, 0, n, memo)`
      * **Option 'L':** Add `solve(currentLength + 1, absencesCount, consecutiveLatesCount + 1, n, memo)`
      * **Option 'A':** Add `solve(currentLength + 1, absencesCount + 1, 0, n, memo)`
      * `totalWays` ko `MOD` se modulo karo.
      * Store `totalWays` in `memo[currentLength][absencesCount][consecutiveLatesCount]` aur return karo.

**Initial Call:** `solve(0, 0, 0, n, memo)`

**Tree Diagram (Conceptual):**

Imagine recursion tree:
`solve(0, 0, 0)` se shuru hota hai.

```
                  solve(0, 0, 0)  // Current length 0, 0 'A', 0 'L'
                 /     |      \
                /      |       \
        'P'    'L'     'A'
             /       |        \
            /        |         \
solve(1,0,0)  solve(1,0,1)  solve(1,1,0) // Length 1
            /|\       /|\       /|\
           P L A     P L A     P L A
          ...         ...         ...
```

Har node represents `(currentLength, absencesCount, consecutiveLatesCount)`. Edges represent adding a character. Red paths invalid rules ke karan cut ho jaate hain (e.g., `solve(..., 2, ...)` ya `solve(..., ..., 3)`). Memoization overlapping subproblems ko handle karta hai.

-----

### Top-Down (Memoization) Approach with Java Code

```java
import java.util.Arrays;

public class StudentAttendanceRecordIITopDown {

    final int MOD = 1_000_000_007;
    // Memoization table: memo[currentLength][absencesCount][consecutiveLatesCount]
    private int[][][] memo; 

    public int checkRecord(int n) {
        memo = new int[n + 1][2][3];
        // Initialize memo table with -1 (indicating not computed)
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        // Start recursion from length 0, 0 absences, 0 consecutive lates.
        return solve(0, 0, 0, n);
    }

    private int solve(int currentLength, int absencesCount, int consecutiveLatesCount, int n) {
        // Base Case 1: Invalid state (rules violated)
        if (absencesCount >= 2 || consecutiveLatesCount >= 3) {
            return 0; // This path is invalid
        }

        // Base Case 2: Reached desired length 'n'
        if (currentLength == n) {
            return 1; // Found one valid record
        }

        // Memoization Check: If already computed, return stored result
        if (memo[currentLength][absencesCount][consecutiveLatesCount] != -1) {
            return memo[currentLength][absencesCount][consecutiveLatesCount];
        }

        long totalWays = 0;

        // Option 1: Append 'P' (Present)
        // Absences count remains same, consecutive lates reset to 0.
        totalWays = (totalWays + solve(currentLength + 1, absencesCount, 0, n)) % MOD;

        // Option 2: Append 'L' (Late)
        // Absences count remains same, consecutive lates increase by 1.
        totalWays = (totalWays + solve(currentLength + 1, absencesCount, consecutiveLatesCount + 1, n)) % MOD;

        // Option 3: Append 'A' (Absent)
        // Absences count increases by 1, consecutive lates reset to 0.
        totalWays = (totalWays + solve(currentLength + 1, absencesCount + 1, 0, n)) % MOD;
        
        // Store and return the computed result
        return memo[currentLength][absencesCount][consecutiveLatesCount] = (int) totalWays;
    }

    public static void main(String[] args) {
        StudentAttendanceRecordIITopDown solver = new StudentAttendanceRecordIITopDown();

        System.out.println("For n = 2, valid records (Top-Down): " + solver.checkRecord(2)); // Expected: 8
        System.out.println("For n = 1, valid records (Top-Down): " + solver.checkRecord(1)); // Expected: 3
        System.out.println("For n = 100, valid records (Top-Down): " + solver.checkRecord(100));
        System.out.println("For n = 100000, valid records (Top-Down): " + solver.checkRecord(100000)); // Should work within time limits
    }
}
```

-----
<img width="1326" alt="image" src="https://github.com/user-attachments/assets/5be1b976-a47a-4097-b7b9-e56fff4a526f" />
# Problem: Minimum Number of Taps to Open to Water a Garden

**Problem Statement:**

Aapko `n` length ka ek garden diya gaya hai, jise `[0, n]` range se represent kiya ja sakta hai. Garden mein `n + 1` taps hain, `0` se `n` tak (matlab, har integer point par ek tap hai).
Har tap `i` ke liye, aapko ek integer `ranges[i]` diya gaya hai. Jab tap `i` open hota hai, toh woh `[i - ranges[i], i + ranges[i]]` range ko water kar sakta hai.

Aapka task hai ki **minimum number of taps** open karein jisse poora garden `[0, n]` water ho jaye. Agar poora garden water karna possible nahi hai, toh `-1` return karo.

**Input:**

  * `int n`: Garden ki total length.
  * `int[] ranges`: Har tap `i` ke liye uski watering range `ranges[i]`. `ranges.length` hamesha `n + 1` hogi.

**Example:**

`n = 5`, `ranges = [3, 4, 1, 1, 0, 0]`

Taps aur unki watering ranges:

  * Tap 0: `ranges[0] = 3`. Waters `[0-3, 0+3] = [-3, 3]`
  * Tap 1: `ranges[1] = 4`. Waters `[1-4, 1+4] = [-3, 5]`
  * Tap 2: `ranges[2] = 1`. Waters `[2-1, 2+1] = [1, 3]`
  * Tap 3: `ranges[3] = 1`. Waters `[3-1, 3+1] = [2, 4]`
  * Tap 4: `ranges[4] = 0`. Waters `[4-0, 4+0] = [4, 4]`
  * Tap 5: `ranges[5] = 0`. Waters `[5-0, 5+0] = [5, 5]`

Humein garden `[0, 5]` ko water karna hai.

  * Agar hum **Tap 1** ko open karein, toh woh `[-3, 5]` ko water karega. Jo `[0, 5]` ko cover kar leta hai.
  * Sirf `1` tap se poora garden cover ho gaya. Toh, minimum taps `1` hai.

**Constraints:**

  * `1 <= n <= 10^4`
  * `ranges.length == n + 1`
  * `0 <= ranges[i] <= 100`

-----

### Approach: Yeh Problem Greedy/Interval Jaisa Kyun Hai? Aur DP Kaise Lagta Hai?

Yeh problem ek classic **Interval Scheduling** type ka problem hai, jo ki **Greedy approach** se bhi solve ho sakta hai, ya fir **Dynamic Programming** se bhi. Jab hum minimum items choose karne ki baat karte hain jo ek range ko cover karein, toh yeh common pattern hai.

**Greedy Approach:**
Aksar aise problems jahan humein minimum cheezein use karke ek range cover karni hoti hai, greedy solution se solve ho jaate hain. Yahan humara goal `[0, n]` ko cover karna hai.

**Greedy ka Idea:**

1.  Pehle apne taps ko `[start, end]` intervals mein convert karo. Ensure karo ki `start` negative na ho (garden `[0, n]` se shuru hota hai) aur `end` `n` se zyada na ho (max garden length). Effectively, har tap `i` se hum ek valid interval `[max(0, i - ranges[i]), min(n, i + ranges[i])]` banate hain.
2.  In intervals ko `start` point ke hisaab se sort karo.
3.  Ab, `0` se shuru karo. Hamesha woh tap select karo jo current cover ki hui `end` point se shuru hota ho ya usse pehle shuru hota ho, aur **jo sabse door tak extend hota ho**. Isse hum maximum coverage paate hain har step par.

**DP Approach:**

Jab Greedy fail ho sakta hai ya thoda complicated ho, tab DP zyada robust hota hai.
Yahan DP `dp[i]` ko define kar sakte hain as:
`dp[i]` = Minimum number of taps required to water the garden segment `[0, i]`.

**DP State:**
`dp[i]` = Minimum taps needed to water the segment from **0 to `i`**.

**Initialization:**

  * `dp` array ki size `n + 1` hogi.
  * `dp[0] = 0` (0 length segment ko water karne ke liye 0 taps).
  * Baaki saare `dp[i]` ko `Integer.MAX_VALUE` se initialize karo (matlab unreachable).

**Transitions (Filling the `dp` table):**

Hum `i` (current point `0` se `n` tak) par iterate karenge.
Har `i` point ke liye, hum check karenge ki kaun se taps `[0, i]` ko cover karne mein help kar sakte hain.

  * Loop `i` from `1` to `n` (current point jahan tak water karna hai).
      * Loop `tap_idx` from `0` to `n`. (Har possible tap ko dekhenge)
          * Calculate `tap_start = max(0, tap_idx - ranges[tap_idx])`.
          * Calculate `tap_end = min(n, tap_idx + ranges[tap_idx])`.
          * Agar yeh tap `tap_end >= i` hai (matlab yeh tap `i` point tak ya usse aage water kar sakta hai), **AND** `tap_start < i` (matlab, yeh tap `i` point se pehle shuru hota hai taaki `i` ko cover kar sake):
              * Tab, `dp[i]` ko update karo. Is `tap_idx` ko include karne se hum `tap_start` tak ke segment ko water karne ki cost se `1` tap zyada add kar rahe hain.
              * `dp[i] = Math.min(dp[i], 1 + dp[tap_start])`.
              * `dp[tap_start]` ensure karta hai ki `[0, tap_start]` already covered hai minimum taps mein.
              * Condition `dp[tap_start] != Integer.MAX_VALUE` bhi add karna padega.

**Final Answer:**
`dp[n]` return karo. Agar `dp[n]` abhi bhi `Integer.MAX_VALUE` hai, toh `-1` return karo.

**Complexity Analysis (DP):**

  * **Time Complexity:** $O(N \\cdot (\\text{Number of Taps})) = O(N \\cdot N) = O(N^2)$.
      * `N` outer loop points. `N` inner loop taps.
      * `N = 10^4` hai, toh `N^2 = (10^4)^2 = 10^8`. Yeh shayad **Time Limit Exceeded (TLE)** ho sakta hai.

**Optimization to $O(N)$ with Preprocessing / Greedy DP:**

Since $O(N^2)$ is too slow, we need an $O(N)$ solution. The key insight lies in processing the taps.
Instead of iterating `i` and then `tap_idx`, hum taps ko prepare karenge.

Har tap `i` ek interval `[left_i, right_i]` deta hai.
Hum `dp[i]` ko `[0, i]` cover karne ke liye min taps store karenge.

Let's re-think `dp[i]`. `dp[i]` = Minimum taps to water `[0, i]`.
Iterate `i` from `0` to `n-1`. For each `i`, we consider adding a new tap to extend coverage beyond `i`.

**Alternative DP state (more aligned with greedy thinking):**
Let `max_reach[i]` be the furthest point reachable if we *must* open a tap at position `i`. This is simply `i + ranges[i]`.

**Correct $O(N)$ DP approach (Greedy-like):**
This is a common approach for "minimum intervals to cover a range".

1.  **Preprocessing Intervals:**

      * Ek array banao `max_right[i]`. `max_right[i]` store karega **furthest right point** jo `i` se shuru hone wala tap cover kar sakta hai.
      * Initialize `max_right` with `i` (matlab, `i` se `i` tak).
      * Loop `i` from `0` to `n`.
          * `left = max(0, i - ranges[i])`
          * `right = min(n, i + ranges[i])`
          * `max_right[left] = Math.max(max_right[left], right)`
          * Matlab, agar multiple taps `left` se shuru hote hain (ya `left` point ko cover karte hain), toh un sab mein se jo **sabse door tak jaata hai**, woh `max_right[left]` mein store ho jayega.
      * `max_right` mein, index `j` par store hoga, `j` se shuru hone wale taps mein se sabse door tak jaane wala point.

2.  **DP-like Iteration (Greedy Strategy):**

      * `taps_count = 0`

      * `current_max_reach = 0` (currently covered up to 0)

      * `next_max_reach = 0` (furthest we can reach with one more tap)

      * Loop `i` from `0` to `n`:

          * `next_max_reach = Math.max(next_max_reach, max_right[i])`
          * If `i == current_max_reach`:
              * Iska matlab hum `current_max_reach` tak pahunch gaye, ab aur taps kholne padenge.
              * Agar `current_max_reach` aur `next_max_reach` same hain, matlab hum aage badh hi nahi paye (`current_max_reach` tak aane ke baad koi tap usse aage nahi le ja raha), toh `return -1`.
              * `taps_count++` (ek aur tap khola).
              * `current_max_reach = next_max_reach`.
          * Agar `current_max_reach >= n` ho gaya, toh poora garden covered hai, `return taps_count`.

      * Final check: Agar loop khatam hone ke baad bhi `current_max_reach < n` hai, `return -1`.

      * Else, `return taps_count`.

Yeh approach `O(N)` time complexity mein solve ho jayega.
`N` maximum $10^4$ hai, $10^4$ operations feasible hain.

-----

### Bottom-Up (Tabulation) Approach (Optimized O(N))

```java
import java.util.Arrays;

public class MinimumTapsToWaterAGarden {

    public int minTaps(int n, int[] ranges) {
        // Step 1: Preprocess intervals to find the maximum reach from each point.
        // max_reach[i] stores the furthest point that can be watered,
        // starting from or before point 'i'.
        // Initialize max_reach array where max_reach[i] = i.
        // This means, without any tap, point 'i' can only water itself.
        int[] max_reach = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            max_reach[i] = i;
        }

        // Iterate through each tap (from 0 to n)
        for (int i = 0; i <= n; i++) {
            int tap_range = ranges[i];
            if (tap_range == 0) {
                continue; // This tap can't water anything beyond itself.
            }

            // Calculate the left and right boundaries of the watering range for tap 'i'.
            // Left boundary: max(0, i - ranges[i]) -- garden starts at 0.
            // Right boundary: min(n, i + ranges[i]) -- garden ends at n.
            int left = Math.max(0, i - tap_range);
            int right = Math.min(n, i + tap_range);

            // Update max_reach[left]:
            // For the starting point 'left', what's the furthest we can reach?
            // It's the maximum of current max_reach[left] and 'right'.
            // This is crucial: if multiple taps can cover 'left', we want the one that extends furthest.
            // And more generally, if a tap covers [L, R], it implies that from any point X in [L, R],
            // we can potentially reach up to R. But we only care about the starting point of the tap.
            // So, for any point 'j' that is reached by 'left', its max_reach should be updated.
            // This is effectively transforming the problem into: from point `j`, how far can we reach?
            // The `max_reach[left]` stores the max `right` boundary achievable by a tap starting at or before `left`.
            max_reach[left] = Math.max(max_reach[left], right);
        }

        // Example: max_reach = [5,5,3,4,4,5] for n=5, ranges=[3,4,1,1,0,0]
        // max_reach[0] = 5 (tap 1 covers [-3,5], so from 0 it covers till 5)
        // max_reach[1] = 5 (tap 1 covers [-3,5], so from 1 it covers till 5)
        // max_reach[2] = 3 (tap 2 covers [1,3], tap 0 covers [-3,3]. From 2, the max reach is 3)
        // max_reach[3] = 4 (tap 3 covers [2,4])
        // max_reach[4] = 4 (tap 4 covers [4,4])
        // max_reach[5] = 5 (tap 5 covers [5,5])

        // Step 2: Use a greedy approach to find the minimum number of taps.
        // We start at point 0. We want to extend our reach as far as possible.
        int taps_opened = 0;
        int current_end = 0;    // Represents the furthest point currently watered.
        int next_potential_end = 0; // Represents the furthest point we can reach with one more tap.

        // Iterate through the garden points from 0 to n.
        // 'i' represents the current point we are trying to cover.
        for (int i = 0; i <= n; i++) {
            // If 'i' is beyond the current watered segment (current_end),
            // it means we need to open a new tap.
            if (i > current_end) {
                // If we cannot extend our reach any further (next_potential_end is same as current_end),
                // then it's impossible to water the entire garden.
                if (next_potential_end <= current_end) { // Also covers next_potential_end == i when i > current_end
                                                        // current_end can be equal to next_potential_end if no tap extends coverage
                    return -1;
                }
                // Open a new tap to reach 'next_potential_end'.
                taps_opened++;
                current_end = next_potential_end;
            }

            // If the current point 'i' is beyond the total garden length 'n', break.
            if (i > n) break; // This ensures we don't go out of bounds for max_reach[i]

            // Update next_potential_end:
            // What's the maximum reach if we open a tap that covers 'i'?
            // It's the max_reach[i] (furthest point from a tap starting at or before 'i').
            next_potential_end = Math.max(next_potential_end, max_reach[i]);

            // If we have already covered the entire garden, return taps_opened.
            if (current_end >= n) {
                return taps_opened;
            }
        }
        
        // Final check: If after iterating through all points, the garden is not fully covered.
        if (current_end < n) {
            return -1;
        }

        return taps_opened;
    }

    public static void main(String[] args) {
        MinimumTapsToWaterAGarden solver = new MinimumTapsToWaterAGarden();

        // Example 1:
        int n1 = 5;
        int[] ranges1 = {3, 4, 1, 1, 0, 0};
        System.out.println("N = " + n1 + ", Ranges = " + Arrays.toString(ranges1) + " -> Min Taps: " + solver.minTaps(n1, ranges1)); // Expected: 1

        // Example 2:
        int n2 = 3;
        int[] ranges2 = {0, 0, 0, 0};
        System.out.println("N = " + n2 + ", Ranges = " + Arrays.toString(ranges2) + " -> Min Taps: " + solver.minTaps(n2, ranges2)); // Expected: -1 (Cannot cover anything)

        // Example 3:
        int n3 = 7;
        int[] ranges3 = {1, 2, 1, 0, 2, 1, 0, 1};
        System.out.println("N = " + n3 + ", Ranges = " + Arrays.toString(ranges3) + " -> Min Taps: " + solver.minTaps(n3, ranges3)); // Expected: 3

        // Example 4:
        int n4 = 8;
        int[] ranges4 = {4, 0, 0, 0, 0, 0, 0, 0, 4};
        System.out.println("N = " + n4 + ", Ranges = " + Arrays.toString(ranges4) + " -> Min Taps: " + solver.minTaps(n4, ranges4)); // Expected: 2 (Tap 0 covers [0,4], Tap 8 covers [4,8])

        // Example 5: Another -1 case
        int n5 = 9;
        int[] ranges5 = {0, 5, 0, 3, 0, 3, 0, 1, 0, 1};
        System.out.println("N = " + n5 + ", Ranges = " + Arrays.toString(ranges5) + " -> Min Taps: " + solver.minTaps(n5, ranges5)); // Expected: -1
    }
}
```

-----

### Top-Down (Memoization) Approach (Less Intuitive for this specific problem)

Is problem ke liye direct Top-Down recursive DP banana thoda mushkil ho sakta hai, kyuki yeh intervals ko cover karne ka problem hai. DP state `dp[i]` = Minimum taps to cover `[0, i]` ko directly recursive function se compute karna $O(N^2)$ ho jayega.

A `solve(currentPoint)` function jo minimum taps return kare to cover `[currentPoint, n]` might look like this:

```java
// Conceptual Top-Down DP (will likely TLE for N=10^4)
// This is to show the structure, not a recommended solution for this problem.

/*
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class MinimumTapsToWaterAGardenTopDown {

    // memo[i] will store the minimum taps to water garden from point 'i' to 'n'.
    private Map<Integer, Integer> memo;
    private int[] effective_max_right; // Precomputed max reach from each point
    private int N_global;

    public int minTaps(int n, int[] ranges) {
        N_global = n;
        effective_max_right = new int[n + 1];
        // Initialize effective_max_right as in Bottom-Up preprocessing
        for (int i = 0; i <= n; i++) {
            effective_max_right[i] = i;
        }
        for (int i = 0; i <= n; i++) {
            int left = Math.max(0, i - ranges[i]);
            int right = Math.min(n, i + ranges[i]);
            effective_max_right[left] = Math.max(effective_max_right[left], right);
        }

        memo = new HashMap<>();
        // Start solving from point 0
        int result = solve(0);

        // If result is effectively infinity, means it's not possible
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // solve(currentPoint) returns min taps to cover [currentPoint, N_global]
    private int solve(int currentPoint) {
        // Base Case: If currentPoint is beyond or at N_global, we've covered the garden.
        if (currentPoint >= N_global) {
            return 0; // 0 taps needed from here.
        }

        // Memoization Check
        if (memo.containsKey(currentPoint)) {
            return memo.get(currentPoint);
        }

        int minTapsForSegment = Integer.MAX_VALUE;

        // Iterate through all possible next taps we can open from currentPoint
        // We want to find a tap that starts AT or BEFORE currentPoint
        // and covers as far as possible. This recursive structure doesn't match greedy.
        // It's more like: from currentPoint, which tap will take me to the furthest nextPoint.
        
        // This is where the recursive top-down for this type of problem becomes non-trivial.
        // A common recursive formulation for interval covering:
        // solve(current_covered_end) = min taps to cover [current_covered_end, n]
        // This would involve finding all taps that cover current_covered_end
        // and then recursively calling solve(new_covered_end)
        
        // Let's redefine: solve(start_point) is min taps needed to cover [start_point, n]
        // Iterate over all possible taps (i, ranges[i])
        // If tap_i covers start_point (i - ranges[i] <= start_point and i + ranges[i] > start_point)
        // Then we can use this tap and then recursively solve for the remaining part from i + ranges[i]
        
        // This leads to a state like: min_taps[i] = min taps to cover [i, N]
        // To cover [i, N]:
        // Find a tap (say, at index `t`) such that `t - ranges[t] <= i` and `t + ranges[t]` is maximized. Let this be `max_reach_from_i`.
        // Then min_taps[i] = 1 + min_taps[max_reach_from_i]
        // This is basically re-implementing the greedy approach recursively.

        // So, for simplicity, stick to the greedy-like DP solution shown in Bottom-Up.
        // The Top-Down version of this would essentially look like this:
        
        // Consider taps that can cover the 'currentPoint'
        for (int i = 0; i <= N_global; i++) { // Iterate through all tap indices
            int tap_start = Math.max(0, i - ranges[i]);
            int tap_end = Math.min(N_global, i + ranges[i]);

            if (tap_start <= currentPoint && tap_end > currentPoint) { // If this tap covers currentPoint
                // We use this tap, and recurse for the part beyond this tap's reach
                int remainingTaps = solve(tap_end);
                if (remainingTaps != Integer.MAX_VALUE) {
                    minTapsForSegment = Math.min(minTapsForSegment, 1 + remainingTaps);
                }
            }
        }
        
        memo.put(currentPoint, minTapsForSegment);
        return minTapsForSegment;
    }
}
*/
```

As you can see, a direct Top-Down recursive solution for this problem is not as natural or efficient as the Bottom-Up optimized approach because it's difficult to define disjoint subproblems that sum up neatly without re-evaluating the "best next tap" repeatedly. The greedy strategy within the Bottom-Up framework is the most straightforward and performant way to solve this.

-----

<img width="1081" alt="image" src="https://github.com/user-attachments/assets/230e4ac5-1e6f-4d81-b028-f268b80ddd7d" />
# Problem: Count All Valid Pickup and Delivery Options

**Problem Statement:**

Aapko `n` orders diye gaye hain. Har order `i` ke liye ek **pickup** `Pi` aur ek **delivery** `Di` operation hota hai. Aapko count karna hai ki aise kitne valid sequences hain `2n` operations ke, jismein saare `Pi` aur `Di` shamil hon.

Valid sequence ke rules hain:

1.  Har `Di` operation apne corresponding `Pi` operation ke **baad** aana chahiye. (Matlab, `P1` ke baad hi `D1` ho sakta hai, `P2` ke baad hi `D2` etc.).
2.  Har `Pi` operation aur `Di` operation unique honge.

Kyunki answer bahut bada ho sakta hai, isse `10^9 + 7` se modulo karke return karna hai.

**Input:**

  * `int n`: Number of orders.

**Output:**

  * Count of valid sequences, modulo `10^9 + 7`.

**Example:**

`n = 1`
Orders: `P1, D1`
Possible sequences:

  * `P1, D1` (Valid)

Total `1` valid sequence.

`n = 2`
Orders: `P1, D1, P2, D2`
Possible sequences:

  * `P1, D1, P2, D2` (Valid)
  * `P1, P2, D1, D2` (Valid)
  * `P1, P2, D2, D1` (Valid)
  * `P2, D2, P1, D1` (Valid)
  * `P2, P1, D1, D2` (Valid)
  * `P2, P1, D2, D1` (Valid)

Total `6` valid sequences.

**Constraints:**

  * `1 <= n <= 500` (Yeh constraint important hai. $O(N)$ ya $O(N^2)$ solution chal jayega, $O(N\!)$ ya usse bada nahi).

-----

### Approach: Yeh Problem Combinatorics aur DP Ka Mix Kyun Hai?

Yeh problem directly Linear DP jaisa nahi lagta jahan `dp[i]` kisi `i`-th element tak ka solution ho. Yeh zyada combinatorics aur permutation ka problem hai. Lekin, aise problems ko aksar hum DP se solve kar sakte hain agar hum ek recursive relation dhoond sakein.

**DP State:**

`dp[i]` = Number of ways to arrange `i` pairs of (Pickup, Delivery) operations `(P1, D1), ..., (Pi, Di)` such that all rules are followed.

**Base Case:**

  * `dp[0] = 1`: Iska matlab hai 0 orders ko arrange karne ka 1 tarika hai (empty sequence).

**Transition Logic (Recursive Relation):**

`dp[n]` ko calculate karne ke liye `dp[n-1]` ka use kaise karein?

Maana ki humein `n-1` orders (`P1,D1 ... P(n-1),D(n-1)`) ko arrange karne ke valid ways pata hain (`dp[n-1]`). Ab hum `P(n), D(n)` ko ismein kaise add karein?

Total `2 * (n-1)` positions hain already occupied. Ab humare paas `2` naye operations (`P(n)` aur `D(n)`) hain.
In `2 * (n-1)` positions ke beech mein `2 * (n-1) + 1` empty slots hain jahan hum `P(n)` ko rakh sakte hain.
`_ X _ X _ X ... _ X _` (Yahan `X` ek existing `P_` ya `D_` operation hai)

Let's place `P(n)` somewhere.
Agar hum `P(n)` ko place kar dete hain, toh uske liye `2 * (n-1) + 1` possible positions hain.
Maana `P(n)` `k`-th position par rakha gaya hai (0-indexed).
Ab `D(n)` ko place karna hai. Rule ke mutabik `D(n)` ko `P(n)` ke **baad** aana chahiye.
Total `2n` positions hain. Agar `P(n)` `k`-th position par hai, toh `D(n)` ke liye `(k+1)` se `2n-1` tak positions available hain.
Total available slots for `D(n)`: `(2n - 1) - (k+1) + 1 = 2n - k - 1`.

Yeh approach seedhe permutations count karne mein thodi confusing ho sakti hai.
Iska ek zyada intuitive tareeka hai `dp[i]` ko `i` pairs (P\_x, D\_x) ko arrange karne ke tarike manna.

**Consider `i` pairs (P\_1, D\_1), ..., (P\_i, D\_i).**
Hum previous state `dp[i-1]` se `dp[i]` kaise nikalenge?

Assume we have `i-1` pairs arranged validly. Now we want to add `P_i` and `D_i`.
Total positions in the `i-1` arrangement are `2 * (i-1)`.
Jab hum `P_i` aur `D_i` ko add karenge, toh total `2i` positions ho jayengi.

`P_i` aur `D_i` ko hum `2i` empty slots mein dal sakte hain.
Pehle `P_i` ko ek slot do. `2i` slots hain.
Ab `D_i` ke liye `2i-1` slots bache. Lekin `D_i` ko `P_i` ke baad aana chahiye.

**Let's use a simpler combinatorial logic:**

Consider `i` orders. Total `2i` positions.
Pehle `P_i` aur `D_i` ko consider karo.
`P_i` ko hum kisi bhi `2i` positions par rakh sakte hain.
`D_i` ko `P_i` ke baad aana chahiye, toh `2i-1` positions mein se `(2i-1 - k)` positions available hain.

**Combinatorial thought process for `dp[i]` based on `dp[i-1]`:**

Suppose we have already placed `(i-1)` pairs correctly. This has `dp[i-1]` ways.
Now we need to place `P_i` and `D_i`.
There are `2*(i-1)` items already placed.
In these `2*(i-1)` items, we have `2*(i-1) + 1` available "slots" to insert `P_i`.
Example: `_ A _ B _ C _` (3 items, 4 slots)

1.  **Place `P_i`:**

      * There are `2*(i-1) + 1` possible positions for `P_i`.

2.  **Place `D_i`:**

      * After placing `P_i`, there are `2*(i-1) + 1 + 1 = 2i` total positions available in the augmented sequence.
      * `D_i` ko `P_i` ke baad aana chahiye.
      * Agar `P_i` ko `k`-th slot (0-indexed) par rakha hai, toh `D_i` ko `k+1, k+2, ..., 2i-1` positions par rakha ja sakta hai.
      * Total `(2i-1) - (k+1) + 1 = 2i - k - 1` positions hain `D_i` ke liye.
      * Total possible positions for `P_i` and `D_i` combined, such that `D_i` comes after `P_i` are:
          * Choose 2 positions out of `2i` positions: `(2i choose 2) = (2i * (2i - 1)) / 2`.
          * For each such pair of positions, only one order `(P_i, D_i)` is valid.
          * So, `(2i * (2i - 1)) / 2` ways to place `P_i` and `D_i` relative to each other within the new `2i` slots.

**So, the recurrence relation becomes:**
`dp[i] = dp[i-1] * (number of ways to insert P_i and D_i into the 2*(i-1) existing slots)`

Number of ways to insert `P_i` and `D_i` into `2*(i-1)` items is:

  * Total slots for `P_i` (including before first item and after last item): `2*(i-1) + 1`.
  * Let `m = 2*(i-1)`. We have `m` items.
  * We choose two spots out of `m+1` possible spots. These are slots before/between/after existing items.
      * `_ item1 _ item2 _ ... _ item_m _` (m items, m+1 slots)
  * Let's pick slot `j` for `P_i`. Then we have `m+1` options.
  * Now we have `m+1` items (m old items + P\_i).
  * Number of slots for `D_i`: `m+1+1 = m+2`. But `D_i` must come after `P_i`.

**Simpler Approach (from the end):**
Consider `dp[i]` as the number of ways to arrange `i` pickup-delivery pairs.
Jab hum `i`-th pair (`P_i, D_i`) ko arrange karte hain, toh is pair ko hum `2i` positions mein place karte hain.
Total `2i` positions mein se `P_i` ke liye `2i` options hain.
`D_i` ke liye `P_i` ke baad `2i - 1` options hain. Toh total `2i * (2i - 1)` ways to choose slots for `P_i` and `D_i`.
Lekin `P_i` ko `D_i` se pehle aana chahiye, toh effectively hum `2i` slots mein se `2` slots choose karte hain, `(2i C 2)` ways. Once chosen, `P_i` goes into the first slot and `D_i` into the second.

So, `number_of_ways_to_place_last_pair = (2i * (2i - 1)) / 2`.
This is the number of ways to pick 2 positions out of `2i` positions for `P_i` and `D_i`, ensuring `P_i` is before `D_i`.

Ab, yeh jo `(2i * (2i - 1)) / 2` positions hain, inmein `dp[i-1]` ways se baaki `i-1` pairs ko arrange kiya ja sakta hai.

**Recurrence Relation:**
`dp[i] = dp[i-1] * (2i * (2i - 1)) / 2`

**Let's test this:**

  * `dp[0] = 1`

  * `dp[1] = dp[0] * (2*1 * (2*1 - 1)) / 2`
    `dp[1] = 1 * (2 * 1) / 2 = 1 * 1 = 1`. (Correct: P1 D1)

  * `dp[2] = dp[1] * (2*2 * (2*2 - 1)) / 2`
    `dp[2] = 1 * (4 * 3) / 2 = 1 * 6 = 6`. (Correct: for n=2, 6 sequences)

  * `dp[3] = dp[2] * (2*3 * (2*3 - 1)) / 2`
    `dp[3] = 6 * (6 * 5) / 2 = 6 * 15 = 90`.

This looks like the correct recurrence relation\!

**Final recurrence relation:**
`dp[i] = dp[i-1] * (2 * i - 1) * i`

Reasoning:
To place the `i`-th pair `(P_i, D_i)` into a valid arrangement of `(i-1)` pairs (which has `2(i-1)` elements):

1.  **Place `P_i`**: There are `2*(i-1) + 1` possible positions for `P_i`. (Empty slots before/between/after existing elements).
2.  **Place `D_i`**: After `P_i` is placed, there are `2*(i-1) + 1 + 1 = 2i` total positions. `D_i` ko `P_i` ke baad place karna hai.
      * Agar `P_i` `k`-th position par hai, toh `D_i` ke liye `2i - k` available positions hain.
      * Average positions for `D_i` would be `(2i-1 + 1) / 2 = i`. No, this is not correct reasoning.

Let's use a simpler argument:
Total elements for `i-1` pairs: `2(i-1)`.
Consider `P_i` and `D_i`. In any valid final sequence, `P_i` will appear before `D_i`.
Imagine we have `2i` slots.
We pick two slots for `P_i` and `D_i`. The number of ways to pick 2 slots out of `2i` is `(2i C 2) = (2i * (2i-1)) / 2`.
Once the two slots are picked, `P_i` goes in the first and `D_i` in the second. This takes care of `P_i` before `D_i`.
The remaining `2i - 2` slots will be filled by the remaining `i-1` pairs in `dp[i-1]` ways.

So, `dp[i] = dp[i-1] * ( (2*i * (2*i - 1)) / 2 )`.
This simplifies to: `dp[i] = dp[i-1] * i * (2*i - 1)`.

Let's re-verify:
`dp[0] = 1`
`dp[1] = dp[0] * 1 * (2*1 - 1) = 1 * 1 * 1 = 1`
`dp[2] = dp[1] * 2 * (2*2 - 1) = 1 * 2 * 3 = 6`
`dp[3] = dp[2] * 3 * (2*3 - 1) = 6 * 3 * 5 = 90`

This recurrence relation works perfectly with the examples.

**Constraints:** `n <= 500`.
`O(N)` solution is perfect. Modulo `10^9 + 7` har step par apply karna padega.

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class CountAllValidPickupAndDeliveryOptions {

    public int countOrders(int n) {
        final int MOD = 1_000_000_007;

        // dp[i] will store the number of valid sequences for 'i' orders.
        long[] dp = new long[n + 1];

        // Base case: For 0 orders, there is 1 valid empty sequence.
        dp[0] = 1;

        // Iterate from 1 order up to 'n' orders.
        for (int i = 1; i <= n; i++) {
            // Formula: dp[i] = dp[i-1] * (2*i - 1) * i
            // (2*i - 1) is the number of choices for the position of D_i relative to P_i.
            // i is the number of possible positions for the (P_i, D_i) pair.

            // Or, more clearly derived: dp[i] = dp[i-1] * ( (2*i * (2*i - 1)) / 2 )
            // (2*i * (2*i - 1)) / 2 is "2i choose 2", the number of ways to choose 2 positions
            // for P_i and D_i from 2i total available positions.
            // P_i is always placed before D_i in these chosen spots.

            long waysToPlaceCurrentPair = (2L * i * (2L * i - 1)) / 2;
            
            dp[i] = (dp[i-1] * waysToPlaceCurrentPair) % MOD;
        }

        return (int) dp[n];
    }

    public static void main(String[] args) {
        CountAllValidPickupAndDeliveryOptions solver = new CountAllValidPickupAndDeliveryOptions();

        // Example 1: n = 1
        // Expected Output: 1
        System.out.println("For n = 1, valid sequences: " + solver.countOrders(1)); // Output: 1

        // Example 2: n = 2
        // Expected Output: 6
        System.out.println("For n = 2, valid sequences: " + solver.countOrders(2)); // Output: 6

        // Example 3: n = 3
        // Expected Output: 90
        System.out.println("For n = 3, valid sequences: " + solver.countOrders(3)); // Output: 90

        // Example 4: n = 7 (a larger test)
        // dp[7] = dp[6] * (14 * 13)/2 = dp[6] * 91
        // dp[6] = dp[5] * (12 * 11)/2 = dp[5] * 66
        // dp[5] = dp[4] * (10 * 9)/2 = dp[4] * 45
        // dp[4] = dp[3] * (8 * 7)/2 = dp[3] * 28 = 90 * 28 = 2520
        // dp[5] = 2520 * 45 = 113400
        // dp[6] = 113400 * 66 = 7484400
        // dp[7] = 7484400 * 91 = 680879400
        System.out.println("For n = 7, valid sequences: " + solver.countOrders(7)); // Output: 680879400 (Modulated value)

        // Max n = 500
        System.out.println("For n = 500, valid sequences: " + solver.countOrders(500)); 
    }
}
```

**Complexity Analysis (Bottom-Up):**

  * **Time Complexity:** $O(N)$. Loop `n` times, har step par constant time calculation. Maximum $N=500$ hai, toh $500$ operations bohot fast hain.
  * **Space Complexity:** $O(N)$ for `dp` array. Agar hum sirf `dp[i-1]` ko use karein aur `dp[i]` ko calculate karein, toh `O(1)` space mein bhi ho sakta hai (sirf `prev_dp` aur `curr_dp` variable rakh ke).

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi bilkul seedhe tareeke se translate ho jayega.

**Memoization State:**
`memo[i]` = Number of valid sequences for `i` orders. Initialize with `-1`.

**Recursive Function:**
`solve(current_n, memo)`

**Logic:**

  * **Base Case:**
      * If `current_n == 0`, return `1`.
  * **Memoization Check:**
      * If `memo[current_n]` already computed (`!= -1`), return it.
  * **Recursive Step:**
      * `result = (long)solve(current_n - 1, memo) * (2L * current_n * (2L * current_n - 1) / 2) % MOD;`
      * Store `result` in `memo[current_n]` aur return karo.

**Initial Call:** `solve(n, memo)`

```java
import java.util.Arrays;

public class CountAllValidPickupAndDeliveryOptionsTopDown {

    final int MOD = 1_000_000_007;
    private long[] memo;

    public int countOrders(int n) {
        memo = new long[n + 1];
        Arrays.fill(memo, -1); // Initialize with -1 to indicate uncomputed

        return (int) solve(n);
    }

    private long solve(int current_n) {
        // Base case: 0 orders means 1 valid (empty) sequence.
        if (current_n == 0) {
            return 1;
        }

        // Memoization check: If already computed, return the stored result.
        if (memo[current_n] != -1) {
            return memo[current_n];
        }

        // Recursive step: Apply the recurrence relation.
        // dp[i] = dp[i-1] * i * (2*i - 1)
        // Or, dp[i] = dp[i-1] * ( (2*i * (2*i - 1)) / 2 )
        
        long waysForPrevOrders = solve(current_n - 1);
        
        // Number of ways to place the current P_i and D_i pair.
        // There are 2*current_n total slots. We choose 2 slots for P_i and D_i.
        // (2*current_n choose 2) = (2*current_n * (2*current_n - 1)) / 2
        long positionsForCurrentPair = (2L * current_n * (2L * current_n - 1)) / 2;
        
        // Multiply previous ways with ways to place current pair and take modulo.
        long result = (waysForPrevOrders * positionsForCurrentPair) % MOD;
        
        // Store the result in memo table and return.
        return memo[current_n] = result;
    }

    public static void main(String[] args) {
        CountAllValidPickupAndDeliveryOptionsTopDown solver = new CountAllValidPickupAndDeliveryOptionsTopDown();

        System.out.println("For n = 1, valid sequences (Top-Down): " + solver.countOrders(1));
        System.out.println("For n = 2, valid sequences (Top-Down): " + solver.countOrders(2));
        System.out.println("For n = 3, valid sequences (Top-Down): " + solver.countOrders(3));
        System.out.println("For n = 7, valid sequences (Top-Down): " + solver.countOrders(7));
        System.out.println("For n = 500, valid sequences (Top-Down): " + solver.countOrders(500));
    }
}
```

-----

### Tree Diagram (Conceptual)

Is problem mein tree diagram recursion ke steps ko visualize karega:

`solve(n)`
`-> solve(n-1)` \* `waysToPlaceNthPair`
`-> solve(n-2)` \* `waysToPlace(n-1)thPair` \* `waysToPlaceNthPair`
...
`-> solve(0)` (base case, returns 1)

`solve(n)`
/                 |  
P (calls solve(n-1))  P (calls solve(n-1))  P (calls solve(n-1))
/|\\                /|\\                /|  
P L A              P L A              P L A
...                ...                ...

Yeh diagram bas calls ko dikhata hai. Har call `solve(k)` internally `(2k * (2k-1))/2` factor se multiply hota hai. Memoization overlapping subproblems ko reuse karke efficiency badhati hai.

-----
<img width="1081" alt="image" src="https://github.com/user-attachments/assets/62da6764-f0c0-424b-bf8e-75a110358483" />
# Problem: Maximum Profit in Job Scheduling

**Problem Statement:**

Aapko `n` jobs diye gaye hain. Har job ke liye aapko teen cheezein di gayi hain:

  * `startTime[i]`: Job `i` ka start time.
  * `endTime[i]`: Job `i` ka end time.
  * `profit[i]`: Job `i` ko complete karne par milne wala profit.

Aapko jobs ka ek subset (sub-selection) choose karna hai jisse **maximum total profit** mil sake. Condition yeh hai ki aap jo jobs choose kar rahe hain, woh **non-overlapping** honi chahiye. Matlab, agar aap Job A aur Job B choose karte hain, toh Job A ka `endTime` Job B ke `startTime` se pehle ya barabar hona chahiye, ya vice versa.

**Input:**

  * `int[] startTime`: Array of start times for each job.
  * `int[] endTime`: Array of end times for each job.
  * `int[] profit`: Array of profits for each job.

**Output:**

  * Maximum total profit.

**Example:**

`startTime = [1,2,3,3]`, `endTime = [3,4,5,6]`, `profit = [50,10,40,70]`

Jobs:

  * Job 0: `[1,3]`, Profit 50
  * Job 1: `[2,4]`, Profit 10
  * Job 2: `[3,5]`, Profit 40
  * Job 3: `[3,6]`, Profit 70

Possible Non-overlapping Combinations:

  * Job 0 (`[1,3]`, Profit 50)
      * Agar Job 0 liya, toh Job 1 (`[2,4]`) nahi le sakte (overlaps).
      * Job 2 (`[3,5]`) le sakte hain (Job 0 ka end time 3, Job 2 ka start time 3, non-overlapping). Total Profit: 50 + 40 = 90.
      * Job 3 (`[3,6]`) le sakte hain. Total Profit: 50 + 70 = 120.
      * Job 0 ke baad aur koi job nahi le sakte jo non-overlapping ho.
  * Job 1 (`[2,4]`, Profit 10)
      * Iske baad koi aur job nahi le sakte jo non-overlapping ho.
  * Job 2 (`[3,5]`, Profit 40)
      * Iske baad koi aur job nahi le sakte jo non-overlapping ho.
  * Job 3 (`[3,6]`, Profit 70)
      * Iske baad koi aur job nahi le sakte jo non-overlapping ho.

Maximum profit yahan `120` hai (Job 0 + Job 3).

**Constraints:**

  * `1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4` (`N` ki value).
  * `1 <= startTime[i] <= endTime[i] <= 10^9` (Times bahut bade ho sakte hain).
  * `1 <= profit[i] <= 10^4`

-----

### Approach: Yeh Problem DP aur Binary Search/Greedy Ka Mix Kyun Hai?

Yeh problem ek classic **Weighted Interval Scheduling** problem hai. Yeh DP ka ek common application hai. Jab times bahut bade hote hain (`10^9`), toh hum DP array ko time ke hisaab se define nahi kar sakte (`dp[time_point]`). Isliye hum DP ko jobs ki index ke hisaab se define karte hain.

**Key Idea:**
Jobs ko `endTime` ke hisaab se sort karna sabse pehla step hai. Aisa karne se, jab hum `i`-th job ko consider karte hain, toh humein sirf un jobs ko dekhna hota hai jo `i`-th job se pehle khatam ho chuki hain.

**DP State:**

`dp[i]` = Maximum profit jo hum pehli `i` jobs mein se choose karke kama sakte hain, jab jobs ko `endTime` ke hisaab se sorted kiya gaya ho.

**Preprocessing:**

1.  Jobs ko ek single structure mein combine karo (e.g., ek custom class `Job` ya ek 2D array `int[][] jobs = new int[n][3]`).
    `jobs[k] = {startTime[k], endTime[k], profit[k]}`.
2.  Is `jobs` array ko `endTime` ke ascending order mein sort karo. Agar `endTime` same ho, toh `startTime` ya `profit` ke hisaab se sort kar sakte hain (ya koi bhi order, it won't affect correctness much, but a consistent order is good).

**Initialization:**

  * `dp` array ki size `n` hogi.
  * `dp[0]` = `jobs[0].profit` (Pehli job ka profit).

**Transitions (Filling the `dp` table):**

Loop `i` from `0` to `n-1` (har sorted job ke liye):

Har job `i` ke liye do choices hain:

1.  **Job `i` ko include na karein:**

      * Is case mein, maximum profit `dp[i-1]` hoga (agar `i > 0` hai).
      * `dp[i] = dp[i-1]` (initial assumption).

2.  **Job `i` ko include karein:**

      * Agar hum Job `i` ko include karte hain, toh humein ek aisi previous job `j` dhoondni padegi jiska `endTime <= jobs[i].startTime` ho.
      * Hum `jobs[i].startTime` se pehle khatam hone wali **last possible job** `j` ko dhoondhenge.
      * Is job `j` ko dhoondne ke liye, hum `jobs` array mein **Binary Search** ka use kar sakte hain.
      * Binary search `jobs` array mein `jobs[i].startTime` se pehle khatam hone wali job ka index dega.
      * Let `prevJobIndex` be the index of the latest job `j` such that `jobs[j].endTime <= jobs[i].startTime`.
      * Agar aisi koi job `j` mili (`prevJobIndex != -1`), toh Job `i` ko include karne ka profit hoga: `jobs[i].profit + dp[prevJobIndex]`.
      * Agar aisi koi job `j` nahi mili (`prevJobIndex == -1`), toh Job `i` ko include karne ka profit sirf `jobs[i].profit` hoga.
      * `dp[i] = Math.max(dp[i], jobs[i].profit + (prevJobIndex != -1 ? dp[prevJobIndex] : 0));`

**Binary Search for `prevJobIndex`:**

`findNextJob(jobs, currentJobEndTime)` function:

  * Input: `jobs` array (sorted by `endTime`), `currentJobEndTime` (the `startTime` of the job we are considering).
  * Output: `index` of the latest job `j` such that `jobs[j].endTime <= currentJobEndTime`.
  * Use `Arrays.binarySearch` ya custom binary search. `Arrays.binarySearch` `key` se chhota ya barabar element dhoondhne ke liye `insertion point` return karta hai.
      * `Arrays.binarySearch` returns `-(insertion point) - 1` if key is not found.
      * The insertion point is the index of the first element greater than the key.
      * So if `idx = Arrays.binarySearch(jobs, new int[]{0, currentJobEndTime, 0}, (a, b) -> Integer.compare(a[1], b[1]))`
      * If `idx < 0`, it means `currentJobEndTime` was not found. The actual index would be `-(idx + 1)`. We need the element just before this insertion point. So `-(idx + 1) - 1`.

**Correct Binary Search Logic:**
Hum `jobs` array mein `jobs[i].startTime` se chhota ya barabar `endTime` wali job dhoondhenge.
`low = 0`, `high = i-1`, `ans = -1`.
While `low <= high`:
`mid = low + (high - low) / 2`
If `jobs[mid].endTime <= jobs[i].startTime`:
`ans = mid`
`low = mid + 1` (try to find a later job)
Else:
`high = mid - 1` (this job ends too late, look earlier)
Return `ans`.

**Final Answer:**
`dp[n-1]` return karo.

**Complexity Analysis (DP with Binary Search):**

  * **Time Complexity:**
      * Sorting jobs: $O(N \\log N)$.
      * DP loop: $N$ iterations.
      * Inside DP loop: Binary Search takes $O(\\log N)$.
      * Total: $O(N \\log N)$.
      * `N = 5 \times 10^4`, so $N \\log N$ is feasible. $(5 \\times 10^4) \\times \\log(5 \\times 10^4) \\approx 5 \\times 10^4 \\times 16 \\approx 8 \\times 10^5$, which is well within limits.
  * **Space Complexity:** $O(N)$ for `jobs` array and `dp` array.

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;
import java.util.Comparator;

public class MaximumProfitInJobScheduling {

    // Custom class to hold job details
    static class Job {
        int startTime;
        int endTime;
        int profit;

        Job(int startTime, int endTime, int profit) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;

        // Step 1: Combine job details into a single array of Job objects.
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        // Step 2: Sort jobs by their end times.
        // This is crucial for the DP approach and binary search.
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.endTime));

        // dp[i] will store the maximum profit considering jobs up to index 'i' (inclusive)
        // in the sorted 'jobs' array.
        int[] dp = new int[n];

        // Base case: For the first job, the maximum profit is just its own profit.
        dp[0] = jobs[0].profit;

        // Step 3: Fill the DP table.
        for (int i = 1; i < n; i++) {
            // Option 1: Do not include the current job (jobs[i]).
            // Max profit is same as max profit for previous jobs.
            int profitWithoutCurrent = dp[i - 1];

            // Option 2: Include the current job (jobs[i]).
            // Find the latest non-overlapping job (jobs[prevJobIndex])
            // whose endTime <= jobs[i].startTime.
            int prevJobIndex = findLatestNonOverlappingJob(jobs, i, jobs[i].startTime);

            int profitWithCurrent = jobs[i].profit;
            if (prevJobIndex != -1) {
                profitWithCurrent += dp[prevJobIndex];
            }

            // Take the maximum of the two options.
            dp[i] = Math.max(profitWithoutCurrent, profitWithCurrent);
        }

        // The maximum profit for all 'n' jobs is stored in dp[n-1].
        return dp[n - 1];
    }

    /**
     * Helper function to find the index of the latest job in the sorted 'jobs' array
     * (up to 'currentIndex - 1') whose endTime is less than or equal to 'targetStartTime'.
     * Uses binary search.
     */
    private int findLatestNonOverlappingJob(Job[] jobs, int currentIndex, int targetStartTime) {
        int low = 0;
        int high = currentIndex - 1; // Search only among jobs before current job
        int resultIndex = -1; // Default to -1 if no such job is found

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (jobs[mid].endTime <= targetStartTime) {
                resultIndex = mid; // This job is a candidate, try to find a later one
                low = mid + 1;
            } else {
                high = mid - 1; // This job ends too late, look earlier
            }
        }
        return resultIndex;
    }

    public static void main(String[] args) {
        MaximumProfitInJobScheduling solver = new MaximumProfitInJobScheduling();

        // Example 1:
        int[] st1 = {1, 2, 3, 3};
        int[] et1 = {3, 4, 5, 6};
        int[] p1 = {50, 10, 40, 70};
        System.out.println("Max Profit for Example 1: " + solver.jobScheduling(st1, et1, p1)); // Expected: 120

        // Example 2:
        int[] st2 = {1, 1, 1};
        int[] et2 = {2, 3, 4};
        int[] p2 = {5, 6, 4};
        System.out.println("Max Profit for Example 2: " + solver.jobScheduling(st2, et2, p2)); // Expected: 6 (Only one job can be taken)

        // Example 3:
        int[] st3 = {1, 2, 3, 4, 6};
        int[] et3 = {3, 5, 10, 6, 9};
        int[] p3 = {20, 20, 100, 70, 60};
        // Jobs sorted by endTime:
        // Job (1,3,20)
        // Job (2,5,20)
        // Job (4,6,70)
        // Job (6,9,60)
        // Job (3,10,100)
        //
        // dp[0]: Job (1,3,20) -> 20
        // dp[1]: Job (2,5,20). Take it: 20. Don't take: 20. Max: 20
        // dp[2]: Job (4,6,70). Non-overlapping with (1,3,20). Take: 70 + dp[0] = 70+20=90. Don't take: dp[1]=20. Max: 90
        // dp[3]: Job (6,9,60). Non-overlapping with (4,6,70). Take: 60 + dp[2] = 60+90=150. Don't take: dp[2]=90. Max: 150
        // dp[4]: Job (3,10,100). Non-overlapping with (1,3,20). Take: 100 + dp[0] = 100+20=120. Non-overlapping with (2,5,20). Take: 100 + dp[1] = 100+20=120. Don't take: dp[3]=150. Max: 150
        // Expected: 150
        System.out.println("Max Profit for Example 3: " + solver.jobScheduling(st3, et3, p3)); // Expected: 150
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai, aur structure similar hoga.

**Memoization State:**
`memo[i]` = Maximum profit considering jobs from index `i` to `n-1` (after sorting).
(Or, `memo[i]` = Max profit considering jobs up to index `i`, similar to bottom-up, but recursive).

Let's stick to `memo[i]` = Max profit considering jobs up to index `i` (inclusive) in the sorted array.

**Preprocessing:**
Same as Bottom-Up: combine jobs, sort by `endTime`.

**Recursive Function:**
`solve(currentIndex, jobs, memo)`

**Logic:**

  * **Base Cases:**
      * If `currentIndex < 0`, return `0` (no jobs to consider, no profit).
      * If `currentIndex == 0`, return `jobs[0].profit`.
  * **Memoization Check:**
      * If `memo[currentIndex]` already computed (`!= -1`), return it.
  * **Recursive Step:**
      * **Option 1: Don't include `jobs[currentIndex]`:**
          * `profitWithoutCurrent = solve(currentIndex - 1, jobs, memo)`
      * **Option 2: Include `jobs[currentIndex]`:**
          * Find `prevJobIndex` using binary search (same `findLatestNonOverlappingJob` helper).
          * `profitWithCurrent = jobs[currentIndex].profit + solve(prevJobIndex, jobs, memo)`
      * `memo[currentIndex] = Math.max(profitWithoutCurrent, profitWithCurrent)`
      * Return `memo[currentIndex]`.

**Initial Call:** `solve(n - 1, jobs, memo)`

```java
import java.util.Arrays;
import java.util.Comparator;

public class MaximumProfitInJobSchedulingTopDown {

    static class Job {
        int startTime;
        int endTime;
        int profit;

        Job(int startTime, int endTime, int profit) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }
    }

    private int[] memo;
    private Job[] jobs; // To make it accessible to recursive function

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;

        jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        Arrays.sort(jobs, Comparator.comparingInt(job -> job.endTime));

        memo = new int[n];
        Arrays.fill(memo, -1); // Initialize memo with -1 (uncomputed)

        return solve(n - 1); // Start recursion from the last job
    }

    private int solve(int currentIndex) {
        // Base case: If no jobs left to consider
        if (currentIndex < 0) {
            return 0;
        }

        // Memoization check
        if (memo[currentIndex] != -1) {
            return memo[currentIndex];
        }

        // Option 1: Do not include the current job
        int profitWithoutCurrent = solve(currentIndex - 1);

        // Option 2: Include the current job
        // Find the latest non-overlapping job before currentIndex
        int prevJobIndex = findLatestNonOverlappingJob(currentIndex, jobs[currentIndex].startTime);

        int profitWithCurrent = jobs[currentIndex].profit + solve(prevJobIndex);

        // Store and return the maximum of the two options
        return memo[currentIndex] = Math.max(profitWithoutCurrent, profitWithCurrent);
    }

    /**
     * Helper function (same as in Bottom-Up)
     */
    private int findLatestNonOverlappingJob(int currentIndex, int targetStartTime) {
        int low = 0;
        int high = currentIndex - 1;
        int resultIndex = -1; 

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (jobs[mid].endTime <= targetStartTime) {
                resultIndex = mid; 
                low = mid + 1;
            } else {
                high = mid - 1; 
            }
        }
        return resultIndex;
    }

    public static void main(String[] args) {
        MaximumProfitInJobSchedulingTopDown solver = new MaximumProfitInJobSchedulingTopDown();

        int[] st1 = {1, 2, 3, 3};
        int[] et1 = {3, 4, 5, 6};
        int[] p1 = {50, 10, 40, 70};
        System.out.println("Max Profit for Example 1 (Top-Down): " + solver.jobScheduling(st1, et1, p1)); // Expected: 120

        int[] st2 = {1, 1, 1};
        int[] et2 = {2, 3, 4};
        int[] p2 = {5, 6, 4};
        System.out.println("Max Profit for Example 2 (Top-Down): " + solver.jobScheduling(st2, et2, p2)); // Expected: 6

        int[] st3 = {1, 2, 3, 4, 6};
        int[] et3 = {3, 5, 10, 6, 9};
        int[] p3 = {20, 20, 100, 70, 60};
        System.out.println("Max Profit for Example 3 (Top-Down): " + solver.jobScheduling(st3, et3, p3)); // Expected: 150
    }
}
```

-----

### Tree Diagram (Conceptual for Top-Down)

`solve(n-1)`
/  
(Include Job n-1)           (Exclude Job n-1)
/  
`profit[n-1] + solve(prev_idx)`   `solve(n-2)`
/    \\                     /  
...    ...                 ...    ...

Har `solve(k)` call do branches mein split hota hai: `k`-th job ko include karna ya exclude karna. `prev_idx` ko dhoondne ke liye binary search use hota hai. Memoization overlapping subproblems (same `solve(k)` calls) ko avoid karta hai.

-----

# Understanding the Problem: Palindrome Partitioning III

**Problem Statement:**

Aapko ek string `s` aur ek integer `k` diya gaya hai. Aapko string `s` ko `k` non-empty disjoint substrings mein partition karna hai. Har substring ko ek palindrome banane ke liye minimum number of character changes karne hain. Aapko total minimum number of character changes return karna hai.

**Input:**

  * `String s`: Input string.
  * `int k`: Number of partitions.

**Output:**

  * Minimum number of character changes required to make all `k` substrings palindromes.

**Example:**

`s = "abc"`, `k = 1`

  * Partition: `["abc"]`
  * `abc` ko palindrome banane ke liye changes: `ab`c -\> `aba` (1 change: c to a) or `abc` -\> `aba` (1 change: c to a) or `abc` -\> `aca` (1 change: b to c)
  * Minimum changes: 1

`s = "aabbc"`, `k = 3`

  * Possible partitions and changes:
      * `["aa", "bb", "c"]`:
          * "aa": 0 changes
          * "bb": 0 changes
          * "c": 0 changes
          * Total: 0 changes
  * Minimum changes: 0

`s = "leetcode"`, `k = 8`

  * Since `k` equals the length of the string, har character ek separate substring banega.
  * `["l", "e", "e", "t", "c", "o", "d", "e"]`
  * Har single character string already ek palindrome hota hai.
  * Total changes: 0

**Constraints:**

  * `1 <= s.length <= 100` (`N` ki value).
  * `1 <= k <= s.length`
  * `s` contains only lowercase English letters.

-----

### Approach: Yeh Problem DP Ka Perfect Example Kyun Hai?

Yeh problem ek classic **Dynamic Programming** problem hai. Ismein do levels ki DP involved hain:

1.  **Preprocessing DP:** Har substring `s[i...j]` ko palindrome banane ke liye kitne minimum changes chahiye, yeh calculate karna.
2.  **Main DP:** String `s` ko `k` partitions mein divide karke minimum total changes nikalna.

**Step 1: Preprocessing - Calculate Palindrome Costs**

Pehle hum ek 2D array `cost[i][j]` banayenge jo substring `s[i...j]` ko palindrome banane ke liye required minimum changes store karega.

`cost[i][j]` = Minimum changes to make `s[i...j]` a palindrome.

**How to calculate `cost[i][j]`:**

  * Initialize `cost` array.
  * Loop `len` from `2` to `n` (substring length).
      * Loop `i` from `0` to `n - len` (start index).
          * `j = i + len - 1` (end index).
          * `cost[i][j]` = `cost[i+1][j-1]` (changes for inner substring).
          * If `s.charAt(i) != s.charAt(j)`, then `cost[i][j]++` (ek extra change outer characters ke liye).
          * Base cases:
              * `cost[i][i] = 0` (single char is palindrome).
              * `cost[i][i+1] = (s.charAt(i) == s.charAt(i+1) ? 0 : 1)` (2 chars).

**Complexity of Preprocessing:** $O(N^2)$ time and $O(N^2)$ space.
`N = 100`, so $100^2 = 10000$ operations, which is very fast.

-----

**Step 2: Main DP - Partitioning for Minimum Changes**

Ab hum ek 2D DP array `dp[i][j]` banayenge.
`dp[i][j]` = Minimum changes required to partition the prefix `s[0...i-1]` into `j` palindromic substrings.
(Yahan `i` string length ko denote karega, `0` se `n` tak).

**Initialization:**

  * `dp` array ki size `(n+1) x (k+1)` hogi.
  * `dp[0][0] = 0` (empty string ko 0 partitions mein 0 changes se partition kar sakte hain).
  * Baaki saare `dp` values ko `infinity` se initialize karo (e.g., `Integer.MAX_VALUE`).

**Transitions (Filling the `dp` table):**

Loop `i` from `1` to `n` (current prefix length `s[0...i-1]`).
Loop `j` from `1` to `k` (number of partitions).

```
* Condition: `j <= i` (partitions ki count string length se zyada nahi ho sakti).
* To calculate `dp[i][j]`, hum `s[0...i-1]` ko `j` partitions mein divide kar rahe hain.
* Last partition `s[p...i-1]` hoga, jahan `p` start index hai.
* `p` can range from `0` to `i-1`.
* `dp[i][j] = min(dp[p][j-1] + cost[p][i-1])` for all valid `p`.
* Valid `p`: `0 <= p < i`. Also, `p` should be at least `j-1` to allow `j-1` partitions before `p`.
* So, `p` loop from `j-1` to `i-1`. (If `p` is `j-1`, it means `s[0...j-2]` is `j-1` partitions, and `s[j-1...i-1]` is the last partition).

Loop `p` from `0` to `i-1` (start index of the last partition):
    * Agar `dp[p][j-1]` infinity nahi hai (matlab `s[0...p-1]` ko `j-1` partitions mein divide karna possible hai).
    * `current_cost = cost[p][i-1]` (cost of making `s[p...i-1]` a palindrome).
    * `dp[i][j] = Math.min(dp[i][j], dp[p][j-1] + current_cost);`
```

**Final Answer:**
`dp[n][k]` return karo.

**Complexity Analysis (Main DP):**

  * **Time Complexity:** $O(N \\cdot K \\cdot N)$ = $O(N^2 K)$.
      * Outer loops: `i` (N), `j` (K).
      * Inner loop: `p` (N).
      * `N = 100`, `K = 100`. So $100^2 \\times 100 = 100^3 = 10^6$ operations, which is perfectly fine.
  * **Space Complexity:** $O(N K)$ for `dp` array. $100 \\times 100 = 10000$ integers.

-----

### Combined Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class PalindromePartitioningIII {

    public int palindromePartition(String s, int k) {
        int n = s.length();

        // Step 1: Precompute the cost to make any substring a palindrome.
        // cost[i][j] stores the minimum changes to make s[i...j] a palindrome.
        int[][] cost = new int[n][n];

        // Base cases for cost: single characters are palindromes (0 changes)
        // and two characters.
        for (int i = 0; i < n; i++) {
            cost[i][i] = 0; // Single character is a palindrome
        }

        // Fill cost table for lengths from 2 to n.
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // Cost for s[i...j] is cost for s[i+1...j-1] plus 1 if s[i] != s[j].
                cost[i][j] = cost[i + 1][j - 1];
                if (s.charAt(i) != s.charAt(j)) {
                    cost[i][j]++;
                }
            }
        }
        // Example: s = "abc"
        // cost[0][0] = 0 ('a')
        // cost[1][1] = 0 ('b')
        // cost[2][2] = 0 ('c')
        // len = 2:
        // cost[0][1] = cost[1][0] (base case, 0) + (s[0]!=s[1]?1:0) = 0 + 1 = 1 ('ab' -> 'aa' or 'bb')
        // cost[1][2] = cost[2][1] (base case, 0) + (s[1]!=s[2]?1:0) = 0 + 1 = 1 ('bc' -> 'bb' or 'cc')
        // len = 3:
        // cost[0][2] = cost[1][1] (0) + (s[0]!=s[2]?1:0) = 0 + 1 = 1 ('abc' -> 'aba')

        // Step 2: Main DP to find minimum changes for k partitions.
        // dp[i][j] = minimum changes to partition s[0...i-1] into j palindromic substrings.
        // Initialize with a large value (infinity)
        int[][] dp = new int[n + 1][k + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // Use /2 to prevent overflow on addition
        }

        // Base case: Empty string s[0...-1] needs 0 changes for 0 partitions.
        dp[0][0] = 0;

        // Iterate through string lengths (i) from 1 to n.
        for (int i = 1; i <= n; i++) {
            // Iterate through number of partitions (j) from 1 to k.
            for (int j = 1; j <= k; j++) {
                // A partition count 'j' cannot be greater than the current string length 'i'.
                // Also, we need at least 'j' characters to form 'j' non-empty partitions.
                if (j > i) {
                    continue;
                }

                // To form dp[i][j], the last partition will be s[p...i-1].
                // The previous part s[0...p-1] must be partitioned into j-1 substrings.
                // 'p' can range from 0 (if s[0...i-1] is the first and only partition)
                // up to i-1 (if s[i-1...i-1] is the last partition, and s[0...i-2] is j-1 partitions).
                // Minimum 'p' must be 'j-1' because s[0...p-1] needs 'j-1' partitions.
                for (int p = 0; p < i; p++) {
                    // Check if dp[p][j-1] is reachable (not infinity)
                    if (dp[p][j - 1] != Integer.MAX_VALUE / 2) {
                        // Calculate cost for the last partition s[p...i-1]
                        int currentPartitionCost = cost[p][i - 1];
                        
                        // Update dp[i][j] with the minimum of current value and
                        // (cost for previous partitions + cost for current partition).
                        dp[i][j] = Math.min(dp[i][j], dp[p][j - 1] + currentPartitionCost);
                    }
                }
            }
        }

        return dp[n][k];
    }

    public static void main(String[] args) {
        PalindromePartitioningIII solver = new PalindromePartitioningIII();

        // Example 1:
        String s1 = "abc";
        int k1 = 1;
        System.out.println("String: " + s1 + ", k: " + k1 + " -> Min Changes: " + solver.palindromePartition(s1, k1)); // Expected: 1

        // Example 2:
        String s2 = "aabbc";
        int k2 = 3;
        System.out.println("String: " + s2 + ", k: " + k2 + " -> Min Changes: " + solver.palindromePartition(s2, k2)); // Expected: 0

        // Example 3:
        String s3 = "leetcode";
        int k3 = 8;
        System.out.println("String: " + s3 + ", k: " + k3 + " -> Min Changes: " + solver.palindromePartition(s3, k3)); // Expected: 0

        // Custom Test Case:
        String s4 = "abacaba";
        int k4 = 2;
        // cost table for "abacaba"
        // cost[0][6] = 0 (abacaba is palindrome)
        // cost[0][0] = 0 ('a')
        // cost[0][1] = 1 ('ab')
        // cost[0][2] = 0 ('aba')
        // cost[0][3] = 1 ('abac') -> 'abaa' or 'abca'
        // dp[7][2]
        // p=0: dp[0][1] + cost[0][6] = inf + 0 = inf
        // p=1: dp[1][1] + cost[1][6] = cost[0][0] + cost[1][6] = 0 + cost("bacaba") = 0 + 2 = 2 (b->a, c->a)
        // p=2: dp[2][1] + cost[2][6] = cost[0][1] + cost[2][6] = 1 + cost("acaba") = 1 + 1 = 2 (ab->aa, a->a, c->a, b->a)
        // ...
        // Expected: 0 (e.g., "abacab", "a" -> cost("abacab")=1, cost("a")=0. Total 1.
        // or "abac", "aba" -> cost("abac")=1, cost("aba")=0. Total 1.
        // or "a", "bacaba" -> cost("a")=0, cost("bacaba")=2. Total 2.
        // The best partition is "abacaba" itself if k=1, cost=0.
        // If k=2, "abacab" + "a" -> cost("abacab")=1, cost("a")=0. Total 1.
        // Or "aba" + "caba" -> cost("aba")=0, cost("caba")=1. Total 1.
        // Or "abaca" + "ba" -> cost("abaca")=0, cost("ba")=1. Total 1.
        System.out.println("String: " + s4 + ", k: " + k4 + " -> Min Changes: " + solver.palindromePartition(s4, k4)); // Expected: 1
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai.

**Memoization State:**
`memo[currentIndex][remainingPartitions]` = Minimum changes to partition `s[currentIndex...n-1]` into `remainingPartitions` palindromic substrings.

**Preprocessing:**
Same as Bottom-Up: calculate `cost[i][j]` first.

**Recursive Function:**
`solve(currentIndex, remainingPartitions, s, cost, memo)`

**Logic:**

  * **Base Cases:**

      * If `remainingPartitions == 0`:
          * If `currentIndex == n` (string fully partitioned), return `0`.
          * Else (string not fully partitioned but no partitions left), return `infinity`.
      * If `currentIndex == n`:
          * If `remainingPartitions == 0`, return `0`.
          * Else (string ended but partitions still needed), return `infinity`.
      * If `remainingPartitions > (n - currentIndex)` (more partitions needed than remaining characters), return `infinity`.

  * **Memoization Check:**

      * If `memo[currentIndex][remainingPartitions]` already computed (`!= -1`), return it.

  * **Recursive Step:**

      * `minChanges = infinity`
      * Loop `nextCutIndex` from `currentIndex` to `n-1`:
          * `currentPartitionCost = cost[currentIndex][nextCutIndex]` (cost of `s[currentIndex...nextCutIndex]`)
          * `remainingChanges = solve(nextCutIndex + 1, remainingPartitions - 1, s, cost, memo)`
          * If `remainingChanges != infinity`:
              * `minChanges = Math.min(minChanges, currentPartitionCost + remainingChanges)`
      * Store `minChanges` in `memo[currentIndex][remainingPartitions]` and return.

**Initial Call:** `solve(0, k, s, cost, memo)`

```java
import java.util.Arrays;

public class PalindromePartitioningIIITopDown {

    // Precomputed cost to make a substring a palindrome
    private int[][] cost;
    // Memoization table: memo[currentIndex][remainingPartitions]
    private int[][] memo;
    private int N; // Length of the string
    private String S; // The input string

    public int palindromePartition(String s, int k) {
        N = s.length();
        S = s;

        // Step 1: Precompute palindrome costs
        cost = new int[N][N];
        for (int i = 0; i < N; i++) {
            cost[i][i] = 0;
        }
        for (int len = 2; len <= N; len++) {
            for (int i = 0; i <= N - len; i++) {
                int j = i + len - 1;
                cost[i][j] = cost[i + 1][j - 1];
                if (S.charAt(i) != S.charAt(j)) {
                    cost[i][j]++;
                }
            }
        }

        // Step 2: Initialize memoization table
        memo = new int[N][k + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 indicates not computed
        }

        // Start recursion from index 0 with k partitions needed
        return solve(0, k);
    }

    /**
     * Recursive function to find minimum changes.
     * @param currentIndex The starting index of the current substring to consider.
     * @param remainingPartitions The number of partitions still needed.
     * @return Minimum changes.
     */
    private int solve(int currentIndex, int remainingPartitions) {
        // Base Case 1: If no partitions are needed.
        if (remainingPartitions == 0) {
            // If the entire string has been partitioned (currentIndex reached N), return 0.
            // Otherwise, it means we still have characters left but no partitions, so it's invalid (infinity).
            return (currentIndex == N) ? 0 : Integer.MAX_VALUE / 2;
        }

        // Base Case 2: If we have processed all characters but still need partitions.
        if (currentIndex == N) {
            return Integer.MAX_VALUE / 2; // Cannot form required partitions
        }

        // Base Case 3: If remaining characters are less than remaining partitions.
        // We can't form 'remainingPartitions' non-empty substrings from fewer than 'remainingPartitions' characters.
        if (remainingPartitions > (N - currentIndex)) {
            return Integer.MAX_VALUE / 2;
        }

        // Memoization check
        if (memo[currentIndex][remainingPartitions] != -1) {
            return memo[currentIndex][remainingPartitions];
        }

        int minChanges = Integer.MAX_VALUE / 2;

        // Try all possible cut points for the current partition.
        // The current partition starts at 'currentIndex' and ends at 'nextCutIndex'.
        // 'nextCutIndex' can go from 'currentIndex' to 'N-1'.
        for (int nextCutIndex = currentIndex; nextCutIndex < N; nextCutIndex++) {
            // Cost to make s[currentIndex...nextCutIndex] a palindrome.
            int currentPartitionCost = cost[currentIndex][nextCutIndex];

            // Recursively solve for the rest of the string with one less partition needed.
            int remainingChanges = solve(nextCutIndex + 1, remainingPartitions - 1);

            // If the remaining part is solvable (not infinity)
            if (remainingChanges != Integer.MAX_VALUE / 2) {
                minChanges = Math.min(minChanges, currentPartitionCost + remainingChanges);
            }
        }

        // Store and return the result
        return memo[currentIndex][remainingPartitions] = minChanges;
    }

    public static void main(String[] args) {
        PalindromePartitioningIIITopDown solver = new PalindromePartitioningIIITopDown();

        String s1 = "abc";
        int k1 = 1;
        System.out.println("String: " + s1 + ", k: " + k1 + " -> Min Changes (Top-Down): " + solver.palindromePartition(s1, k1)); // Expected: 1

        String s2 = "aabbc";
        int k2 = 3;
        System.out.println("String: " + s2 + ", k: " + k2 + " -> Min Changes (Top-Down): " + solver.palindromePartition(s2, k2)); // Expected: 0

        String s3 = "leetcode";
        int k3 = 8;
        System.out.println("String: " + s3 + ", k: " + k3 + " -> Min Changes (Top-Down): " + solver.palindromePartition(s3, k3)); // Expected: 0

        String s4 = "abacaba";
        int k4 = 2;
        System.out.println("String: " + s4 + ", k: " + k4 + " -> Min Changes (Top-Down): " + solver.palindromePartition(s4, k4)); // Expected: 1
    }
}
```

-----

### Tree Diagram (Conceptual for Top-Down)

`solve(0, k)`
/ |  
/  |  
Cut at 0  Cut at 1  ... Cut at N-1
/    \\    /  
`cost[0][0] + solve(1, k-1)`
`cost[0][1] + solve(2, k-1)`
...
`cost[0][N-1] + solve(N, k-1)`

Har `solve(currentIndex, remainingPartitions)` call string के remaining part को `remainingPartitions` में divide करने के लिए सभी possible first partition cuts (`nextCutIndex`) को explore करता है, और फिर recursively solve करता है। Memoization overlapping `(currentIndex, remainingPartitions)` states को handle करता है।

-----
<img width="1106" alt="image" src="https://github.com/user-attachments/assets/8c538523-38cd-40ad-b536-3504ad9e9b83" />
# Problem: Restore The Array

**Problem Statement:**

Aapko ek string `s` diya gaya hai jo digits se bani hai, aur ek integer `k` diya gaya hai.
Aapko count karna hai ki `s` ko kitne tareekon se ek array of integers mein restore kiya ja sakta hai, jahan har integer in conditions ko satisfy karta ho:

1.  Har integer without leading zeros hona chahiye (e.g., "01" valid nahi hai, "0" valid hai).
2.  Har integer `1` aur `k` ke beech mein hona chahiye (inclusive).

Kyunki answer bahut bada ho sakta hai, isse `10^9 + 7` se modulo karke return karna hai.

**Input:**

  * `String s`: A string consisting of digits.
  * `int k`: The upper bound for the integers.

**Output:**

  * Number of ways to restore the array, modulo `10^9 + 7`.

**Example:**

`s = "1000"`, `k = 10000`

  * Possible ways:
      * `[1000]` (Valid: 1000 is between 1 and 10000)
  * Total `1` way.

`s = "1000"`, `k = 10`

  * Possible ways:
      * `[1, 0, 0, 0]` (Invalid: 0 has leading zeros and 0 is not between 1 and 10. Also, "0" is allowed as a single digit number but here it's part of a multi-digit number, so it's a leading zero. Actually "0" is considered a number, but "01" is not. Check constraints, usually "0" alone is valid. The constraint "without leading zeros" implies that "0" is okay, but "01", "00" are not. Let's assume "0" is fine, but any number like "0X" is invalid.)
      * No valid way to partition.
  * Total `0` ways.

`s = "1317"`, `k = 2000`

  * Possible ways:
      * `[1317]`
      * `[13, 17]` (13 \<= 2000, 17 \<= 2000)
      * `[1, 317]` (1 \<= 2000, 317 \<= 2000)
      * `[1, 3, 17]` (1 \<= 2000, 3 \<= 2000, 17 \<= 2000)
      * `[131, 7]` (131 \<= 2000, 7 \<= 2000)
  * Total `5` ways.

**Constraints:**

  * `1 <= s.length <= 10^5` (`N` ki value).
  * `1 <= k <= 10^9` (K bahut bada ho sakta hai).
  * `s` consists only of digits.

-----

### Approach: Yeh Problem DP Ka Perfect Example Kyun Hai?

Yeh problem ek classic **Dynamic Programming** problem hai, jahan hum ek bade problem (poori string `s` ko restore karna) ko chhote subproblems (prefixes ya suffixes ko restore karna) mein tod kar solve karte hain.

**DP State Definition:**

`dp[i]` = Number of ways to restore the substring `s[i...n-1]` (suffix starting at index `i`) into a valid array of integers.

**Base Case:**

  * `dp[n] = 1`: Iska matlab hai empty string (jab hum `n`-th index tak pahunch gaye, string khatam ho gayi) ko restore karne ka 1 tarika hai (empty array).

**Transitions (Filling the `dp` table):**

Hum `dp` table ko `n-1` se `0` tak fill karenge (bottom-up, ya Top-down bhi possible hai).

Loop `i` from `n-1` down to `0`:

  * `dp[i] = 0` se initialize karo.

  * Agar `s.charAt(i) == '0'` hai, toh `dp[i] = 0` hoga. Kyunki koi bhi number '0' se shuru nahi ho sakta (except single "0" itself, but "0" is generally not in range [1, k] unless k \>= 0. Here k \>= 1. And no "0X" type numbers).

      * (Actually, "0" is usually allowed if `k >= 0`. But here `k >= 1`. The "no leading zeros" means `s = "0"` is okay, `s = "01"` is not. If `s.charAt(i)` is '0', it cannot be the start of a multi-digit number like "0123". It can only be "0" itself. But "0" is not in range `[1, k]` since `k >= 1`. So, if `s.charAt(i) == '0'`, then no valid numbers can start at `i`, hence `dp[i]` is 0).

  * Agar `s.charAt(i) != '0'` hai:

      * `currentNum = 0`
      * Loop `j` from `i` to `n-1` (har possible end point ke liye current number ka):
          * `currentNum = currentNum * 10 + (s.charAt(j) - '0')`

          * Check `currentNum` ki validity:

              * `currentNum` should not overflow `long` (although `k` is `10^9`, `currentNum` can reach `10^10` if `s` is "10000000000", so `long` is necessary).
              * `currentNum <= k` hona chahiye.

          * Agar `currentNum` valid hai (`currentNum > 0` and `currentNum <= k`):

              * `dp[i] = (dp[i] + dp[j+1]) % MOD;`
              * Matlab, `s[i...j]` ko ek number banaya, aur `dp[j+1]` se number of ways ko add kar diya.

          * Agar `currentNum` `k` se bada ho gaya (`currentNum > k`), toh isse further digits add karna useless hai kyuki woh aur bhi bade ho jayenge. Break the inner loop.

          * `currentNum` agar overflow kar gaya `long` ki capacity se, toh bhi break kar do.

**Final Answer:**
`dp[0]` return karo.

**Complexity Analysis:**

  * **Time Complexity:** $O(N \\cdot L)$, jahan `N` string ki length hai aur `L` average length of numbers (roughly `log10(k)`).
      * Outer loop: `N` iterations (for `i`).
      * Inner loop: Max `N` iterations (for `j`), but usually `log10(k)` iterations because `currentNum` quickly exceeds `k`. The maximum length of a number that can be $\\le 10^9$ is 10 digits (e.g., 1000000000). So, the inner loop runs at most 10-11 times.
      * Total: $O(N \\cdot \\text{max\_digits\_in\_K})$. Max digits for `k=10^9` is 10. So $10^5 \\cdot 10 = 10^6$ operations. This is efficient enough.
  * **Space Complexity:** $O(N)$ for `dp` array.

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class RestoreTheArray {

    public int restoreArray(String s, int k) {
        int n = s.length();
        final int MOD = 1_000_000_007;

        // dp[i] stores the number of ways to restore the substring s[i...n-1]
        // (suffix starting at index i) into a valid array of integers.
        long[] dp = new long[n + 1];

        // Base case: An empty string (when we reach index n) has 1 way to be restored
        // (an empty array).
        dp[n] = 1;

        // Iterate from the end of the string backwards.
        for (int i = n - 1; i >= 0; i--) {
            // If the current character is '0', it cannot start any valid number (except "0" itself,
            // but "0" is not in range [1, k] as k >= 1).
            // So, no ways to form numbers starting with '0' other than "0" which is out of range.
            if (s.charAt(i) == '0') {
                dp[i] = 0;
                continue;
            }

            long currentNumber = 0;
            // Iterate from current index 'i' to 'j' to form possible numbers.
            // 'j' represents the end index of the current number being formed (inclusive).
            for (int j = i; j < n; j++) {
                // Form the number by appending the digit s.charAt(j).
                // Use long to avoid overflow since currentNumber can potentially exceed Integer.MAX_VALUE
                // before it exceeds k (if k is large, like 10^9).
                currentNumber = currentNumber * 10 + (s.charAt(j) - '0');

                // Check for overflow (currentNumber became negative or too large).
                // If k is max 10^9, then a 10-digit number like "1000000000" is fine.
                // A 11-digit number like "2000000000" is also fine.
                // But "10000000000" (11 digits) will exceed Integer.MAX_VALUE (2*10^9).
                // It can still be less than k if k is 10^10, but k is max 10^9.
                // So, currentNumber will not overflow long if k is 10^9 and s.length is 10^5
                // since we break when currentNumber > k.
                // Maximum currentNumber will be k. Max digits for k=10^9 is 10 digits.
                // If s[i...j] forms "1234567890", this is 10 digits.
                // If s[i...j] forms "12345678901", this is 11 digits, it will exceed k.
                // So, no need to check for long overflow, just check against k.
                
                // If the formed number is greater than 'k', it's invalid.
                // Since digits are only increasing the number, any further numbers formed
                // by adding more digits will also be greater than 'k'. So, break.
                if (currentNumber > k) {
                    break;
                }

                // If currentNumber is valid (1 <= currentNumber <= k),
                // then add the number of ways from the substring s[j+1...n-1] to dp[i].
                dp[i] = (dp[i] + dp[j + 1]) % MOD;
            }
        }

        // The result is the number of ways to restore the entire string s[0...n-1].
        return (int) dp[0];
    }

    public static void main(String[] args) {
        RestoreTheArray solver = new RestoreTheArray();

        // Example 1: s = "1000", k = 10000
        // Expected: 1 ([1000])
        System.out.println("s = \"1000\", k = 10000 -> Ways: " + solver.restoreArray("1000", 10000));

        // Example 2: s = "1000", k = 10
        // Expected: 0
        System.out.println("s = \"1000\", k = 10 -> Ways: " + solver.restoreArray("1000", 10));

        // Example 3: s = "1317", k = 2000
        // Expected: 5
        System.out.println("s = \"1317\", k = 2000 -> Ways: " + solver.restoreArray("1317", 2000));

        // Custom Test Cases:
        // s = "1234567890", k = 90
        // Expected: 0 (only [1], [2], ... can be formed but 10, 12, etc are too big)
        System.out.println("s = \"1234567890\", k = 90 -> Ways: " + solver.restoreArray("1234567890", 90));

        // s = "1234567890", k = 1000000000
        // Expected: 1 ([1234567890])
        System.out.println("s = \"1234567890\", k = 1000000000 -> Ways: " + solver.restoreArray("1234567890", 1000000000));

        // s = "300", k = 30
        // Expected: 0 (300 > 30, "00" has leading zero for second zero)
        System.out.println("s = \"300\", k = 30 -> Ways: " + solver.restoreArray("300", 30));

        // s = "2020", k = 30
        // Expected: 1 ([20, 20])
        System.out.println("s = \"2020\", k = 30 -> Ways: " + solver.restoreArray("2020", 30));
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai, aur structure similar hoga.

**Memoization State:**
`memo[i]` = Number of ways to restore `s[i...n-1]`. Initialize with `-1`.

**Recursive Function:**
`solve(currentIndex, s, k, memo)`

**Logic:**

  * **Base Case:**
      * If `currentIndex == n`, return `1` (empty string, 1 way).
  * **Memoization Check:**
      * If `memo[currentIndex]` already computed (`!= -1`), return it.
  * **Main Logic:**
      * If `s.charAt(currentIndex) == '0'`, `memo[currentIndex] = 0` and return `0`.
      * `totalWays = 0`
      * `currentNum = 0`
      * Loop `j` from `currentIndex` to `n-1`:
          * `currentNum = currentNum * 10 + (s.charAt(j) - '0')`
          * If `currentNum > k`, break.
          * `totalWays = (totalWays + solve(j + 1, s, k, memo)) % MOD;`
      * Store `totalWays` in `memo[currentIndex]` and return.

**Initial Call:** `solve(0, s, k, memo)`

```java
import java.util.Arrays;

public class RestoreTheArrayTopDown {

    final int MOD = 1_000_000_007;
    private int N;
    private String S_global;
    private int K_global;
    private int[] memo; // Using int[] for memoization

    public int restoreArray(String s, int k) {
        N = s.length();
        S_global = s;
        K_global = k;
        memo = new int[N + 1];
        Arrays.fill(memo, -1); // Initialize memo table with -1 (uncomputed)

        return solve(0); // Start recursion from index 0
    }

    /**
     * Recursive function to calculate the number of ways to restore the array
     * from substring s[currentIndex...N-1].
     * @param currentIndex The starting index of the current substring.
     * @return The number of ways, modulo MOD.
     */
    private int solve(int currentIndex) {
        // Base case: If we've reached the end of the string, it's one valid way (empty array).
        if (currentIndex == N) {
            return 1;
        }

        // Memoization check: If the result for this subproblem is already computed.
        if (memo[currentIndex] != -1) {
            return memo[currentIndex];
        }

        // If the current character is '0', it cannot start any valid number (as numbers must be >= 1).
        // A single "0" is usually treated as valid, but here k >= 1, so "0" is out of range.
        if (S_global.charAt(currentIndex) == '0') {
            return memo[currentIndex] = 0;
        }

        long currentNumber = 0; // Use long to build the number to avoid overflow before comparing with K_global
        int totalWays = 0;

        // Iterate through possible end points (j) for the number starting at currentIndex.
        for (int j = currentIndex; j < N; j++) {
            // Form the number by appending the digit s.charAt(j).
            currentNumber = currentNumber * 10 + (S_global.charAt(j) - '0');

            // If the formed number exceeds K_global, then this path (and any longer number) is invalid.
            if (currentNumber > K_global) {
                break;
            }

            // If the number is valid (1 <= currentNumber <= K_global),
            // then add the ways from the remaining part of the string (from j+1).
            totalWays = (totalWays + solve(j + 1)) % MOD;
        }

        // Store the computed result in the memoization table.
        return memo[currentIndex] = totalWays;
    }

    public static void main(String[] args) {
        RestoreTheArrayTopDown solver = new RestoreTheArrayTopDown();

        System.out.println("s = \"1000\", k = 10000 -> Ways (Top-Down): " + solver.restoreArray("1000", 10000));
        System.out.println("s = \"1000\", k = 10 -> Ways (Top-Down): " + solver.restoreArray("1000", 10));
        System.out.println("s = \"1317\", k = 2000 -> Ways (Top-Down): " + solver.restoreArray("1317", 2000));
        System.out.println("s = \"1234567890\", k = 90 -> Ways (Top-Down): " + solver.restoreArray("1234567890", 90));
        System.out.println("s = \"1234567890\", k = 1000000000 -> Ways (Top-Down): " + solver.restoreArray("1234567890", 1000000000));
        System.out.println("s = \"300\", k = 30 -> Ways (Top-Down): " + solver.restoreArray("300", 30));
        System.out.println("s = \"2020\", k = 30 -> Ways (Top-Down): " + solver.restoreArray("2020", 30));
    }
}
```

-----

### Tree Diagram (Conceptual for Top-Down)

`solve(0)` (from index 0)
/  |  
/   |  
"1"   "12"   "123" ...
/       |  
`solve(1)` `solve(2)` `solve(3)` ...
/|\\     /|\\      /|  
...     ...      ...

Har `solve(currentIndex)` call `currentIndex` se shuru hone wale har possible valid number को explore karta hai, और फिर बाकी के string के लिए recursive call करता है। Memoization overlapping `(currentIndex)` states को avoid करता है।

-----

<img width="1106" alt="image" src="https://github.com/user-attachments/assets/eb597a49-fbde-4ed2-b789-ef479e92c4e0" />
# Problem: Decode Ways II

**Problem Statement:**

Aapko ek encoded message `s` diya gaya hai jismein digits (`'0'-'9'`) aur asterisk (`'*'`) characters hain. Is message ko decode karna hai following rules ke hisaab se:

  * `'A'` ko `1` se decode karte hain.
  * `'B'` ko `2` se decode karte hain.
  * ...
  * `'Z'` ko `26` se decode karte hain.

Rules mein kuch additional complexities hain:

  * Ek asterisk (`'*'`) kisi bhi digit (`'1'` se `'9'`) ko represent kar sakta hai.
  * `'0'` ko kisi bhi character se decode nahi kiya ja sakta.
  * A two-digit sequence (e.g., "12", "26") ko decode kar sakte hain agar woh `10` se `26` ke range mein ho.

Aapko total number of ways return karna hai jisse message ko decode kiya ja sakta hai. Kyunki answer bahut bada ho sakta hai, isse `10^9 + 7` se modulo karke return karna hai.

**Input:**

  * `String s`: The encoded message.

**Output:**

  * Total number of decode ways, modulo `10^9 + 7`.

**Example:**

`s = "*"`

  * `*` can be `1, 2, ..., 9`. Har ek ko ek character mein decode kiya ja sakta hai.
  * Total `9` ways.

`s = "1*"`

  * `*` can be `0, 1, ..., 9`.
  * Combinations:
      * `1` aur `*`: `1` ko `A` (1 way), `*` ko `1..9` (9 ways). Total `1 * 9 = 9` ways.
      * `1*` as a two-digit number: `10, 11, ..., 19`. Ye saare valid hain. Total `10` ways.
  * Total `9 + 10 = 19` ways.

`s = "2*"`

  * `*` can be `0, 1, ..., 9`.
  * Combinations:
      * `2` aur `*`: `2` ko `B` (1 way), `*` ko `1..9` (9 ways). Total `1 * 9 = 9` ways.
      * `2*` as a two-digit number:
          * `20, 21, ..., 26` (Valid: 7 ways).
          * `27, 28, 29` (Invalid: \> 26).
  * Total `9 + 7 = 16` ways.

**Constraints:**

  * `1 <= s.length <= 10^5` (`N` ki value).
  * `s` contains only digits and `'*'`.

-----

### Approach: Yeh Problem DP Ka Perfect Example Kyun Hai?

Yeh `Decode Ways` problem ka ek advanced version hai. Normal `Decode Ways` ki tarah, yeh bhi ek **Dynamic Programming** problem hai. Hum ek bade problem (poori string `s` ko decode karna) ko chhote subproblems (prefixes ya suffixes ko decode karna) mein tod kar solve karte hain.

**DP State Definition:**

`dp[i]` = Number of ways to decode the prefix `s[0...i-1]`.
(Yahan `i` string ki length ko denote karega, `0` se `n` tak).

**Base Cases:**

  * `dp[0] = 1`: Iska matlab hai empty string ko decode karne ka 1 tarika hai (empty sequence).
  * `dp[1]`:
      * Agar `s[0] == '0'`, `dp[1] = 0`.
      * Agar `s[0] == '*'`, `dp[1] = 9` (can be 1-9).
      * Agar `s[0]` koi digit `1-9` hai, `dp[1] = 1`.

**Transitions (Filling the `dp` table):**

Hum `dp` table ko `2` se `n` tak fill karenge (bottom-up).
Loop `i` from `2` to `n`:

`dp[i]` ko calculate karne ke liye, hum `s[i-1]` (current character) aur `s[i-2]` (previous character) ko dekhenge.

`dp[i]` ka calculation do parts mein split hoga:

1.  **Single-digit decoding for `s[i-1]`:**

      * Agar `s.charAt(i-1) == '0'`, toh yeh single digit decode nahi ho sakta, iska contribution `0` hoga.
      * Agar `s.charAt(i-1) == '*'`, toh yeh `1` se `9` tak koi bhi digit ho sakta hai, so `9 * dp[i-1]` ways.
      * Agar `s.charAt(i-1)` koi digit `1` se `9` hai, toh `1 * dp[i-1]` ways.
      * Total ways from single digit: `count_single_digit_decodings(s.charAt(i-1)) * dp[i-1]`.

2.  **Two-digit decoding using `s[i-2]` and `s[i-1]`:**

      * Consider the substring `s[i-2...i-1]`.
      * `s.charAt(i-2)` aur `s.charAt(i-1)` ke combinations dekho:
          * **Case `s.charAt(i-2) == '1'`:**
              * Agar `s.charAt(i-1) == '*'`: `1*` can be `10` to `19` (10 ways). So `10 * dp[i-2]` ways.
              * Agar `s.charAt(i-1)` digit `0` to `9` hai: `1` + digit forms `10` to `19` (1 way). So `1 * dp[i-2]` ways.
          * **Case `s.charAt(i-2) == '2'`:**
              * Agar `s.charAt(i-1) == '*'`: `2*` can be `20` to `26` (7 ways). So `7 * dp[i-2]` ways.
              * Agar `s.charAt(i-1)` digit `0` to `6` hai: `2` + digit forms `20` to `26` (1 way). So `1 * dp[i-2]` ways.
              * Agar `s.charAt(i-1)` digit `7` to `9` hai: Invalid (0 ways).
          * **Case `s.charAt(i-2) == '*'`:**
              * Agar `s.charAt(i-1) == '*'`:
                  * `**` means `11-19` (`*` is 1) (9 ways) or `21-26` (`*` is 2) (6 ways). Total `9 + 6 = 15` ways.
                  * So `15 * dp[i-2]` ways.
              * Agar `s.charAt(i-1)` digit `0` to `6` hai:
                  * `*` can be `1` or `2`.
                  * If `*` is `1`: `1` + `s.charAt(i-1)` (1 way).
                  * If `*` is `2`: `2` + `s.charAt(i-1)` (1 way).
                  * Total `2 * dp[i-2]` ways.
              * Agar `s.charAt(i-1)` digit `7` to `9` hai:
                  * `*` can only be `1` (e.g., `17`, `18`, `19`).
                  * Total `1 * dp[i-2]` ways.
          * **Other cases for `s.charAt(i-2)` (e.g., '0' or '3'-'9'):** No two-digit decoding possible (0 ways).

**Modulo Arithmetic:** Har addition aur multiplication ke baad `MOD` apply karna zaroori hai.

**Final Answer:**
`dp[n]` return karo.

**Complexity Analysis:**

  * **Time Complexity:** $O(N)$. Single loop from `2` to `n`. Inside the loop, constant number of calculations.
  * **Space Complexity:** $O(N)$ for `dp` array. (This can be optimized to $O(1)$ since `dp[i]` only depends on `dp[i-1]` and `dp[i-2]`).

-----

### Bottom-Up (Tabulation) Approach with Java Code

```java
import java.util.Arrays;

public class DecodeWaysII {

    public int numDecodings(String s) {
        int n = s.length();
        final int MOD = 1_000_000_007;

        // dp[i] represents the number of ways to decode the prefix s[0...i-1].
        long[] dp = new long[n + 1];

        // Base case: An empty string has one way to be decoded (empty sequence).
        dp[0] = 1;

        // dp[1] represents decoding s[0].
        if (n >= 1) {
            if (s.charAt(0) == '0') {
                dp[1] = 0;
            } else if (s.charAt(0) == '*') {
                dp[1] = 9; // '*' can be 1-9
            } else { // s.charAt(0) is a digit 1-9
                dp[1] = 1;
            }
        }

        // Fill the DP table from i = 2 to n.
        for (int i = 2; i <= n; i++) {
            char current_char = s.charAt(i - 1); // s[i-1]
            char prev_char = s.charAt(i - 2);    // s[i-2]

            // 1. Consider decoding current_char as a single digit.
            if (current_char == '0') {
                dp[i] = 0; // '0' cannot be decoded as a single digit
            } else if (current_char == '*') {
                dp[i] = (dp[i] + 9 * dp[i - 1]) % MOD; // '*' can be 1-9 (9 ways)
            } else { // current_char is '1'-'9'
                dp[i] = (dp[i] + dp[i - 1]) % MOD; // One way to decode current_char
            }

            // 2. Consider decoding prev_char and current_char as a two-digit number.
            // Check combinations of prev_char and current_char
            if (prev_char == '1') {
                if (current_char == '*') {
                    // '1*' can form 10-19 (10 ways)
                    dp[i] = (dp[i] + 10 * dp[i - 2]) % MOD;
                } else { // current_char is '0'-'9'
                    // '1' + '0'-'9' forms 10-19 (1 way)
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            } else if (prev_char == '2') {
                if (current_char == '*') {
                    // '2*' can form 20-26 (7 ways)
                    dp[i] = (dp[i] + 7 * dp[i - 2]) % MOD;
                } else if (current_char >= '0' && current_char <= '6') {
                    // '2' + '0'-'6' forms 20-26 (1 way)
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
                // If current_char is '7'-'9', '2' + '7'-'9' is invalid, so no contribution.
            } else if (prev_char == '*') {
                if (current_char == '*') {
                    // '**':
                    // '*' as '1' (10-19): 9 options (11, 12 ... 19)
                    // '*' as '2' (20-26): 6 options (21, 22 ... 26)
                    // Total = 9 + 6 = 15 ways
                    dp[i] = (dp[i] + 15 * dp[i - 2]) % MOD;
                } else if (current_char >= '0' && current_char <= '6') {
                    // '*' + '0'-'6':
                    // '*' as '1' (10-16): 1 option
                    // '*' as '2' (20-26): 1 option
                    // Total = 2 ways (e.g., if current_char is '5', we can have '15' or '25')
                    dp[i] = (dp[i] + 2 * dp[i - 2]) % MOD;
                } else { // current_char is '7'-'9'
                    // '*' + '7'-'9':
                    // '*' can only be '1' (e.g., '17', '18', '19'). '27' is invalid.
                    // Total = 1 way
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            }
        }

        return (int) dp[n];
    }

    public static void main(String[] args) {
        DecodeWaysII solver = new DecodeWaysII();

        // Example 1: s = "*"
        // Expected: 9
        System.out.println("s = \"*\" -> Ways: " + solver.numDecodings("*"));

        // Example 2: s = "1*"
        // Expected: 19
        System.out.println("s = \"1*\" -> Ways: " + solver.numDecodings("1*"));

        // Example 3: s = "2*"
        // Expected: 16
        System.out.println("s = \"2*\" -> Ways: " + solver.numDecodings("2*"));

        // Example 4: s = "**"
        // Expected: dp[2]
        // s[0]='*', s[1]='*'
        // Single digit: dp[1]=9. 9 * 9 = 81
        // Two digits:
        // '*' as 1: 10-19 (9 ways if 0 is included) -> 11, 12, ..., 19 (9 ways)
        // '*' as 2: 20-26 (6 ways)
        // Total ways for two digits: 9+6 = 15
        // Total = 81 + 15 = 96
        System.out.println("s = \"**\" -> Ways: " + solver.numDecodings("**")); // Expected: 96

        // Example 5: s = "0"
        // Expected: 0
        System.out.println("s = \"0\" -> Ways: " + solver.numDecodings("0"));

        // Example 6: s = "10"
        // Expected: 1
        System.out.println("s = \"10\" -> Ways: " + solver.numDecodings("10"));
        
        // Example 7: s = "30" (invalid two-digit, 0 as single digit)
        // Expected: 0
        System.out.println("s = \"30\" -> Ways: " + solver.numDecodings("30"));
    }
}
```

-----

### Space Optimization (from $O(N)$ to $O(1)$)

Since `dp[i]` only depends on `dp[i-1]` and `dp[i-2]`, hum `dp` array ko optimize kar sakte hain. Hum sirf do variables maintain karenge: `prev2` (for `dp[i-2]`) aur `prev1` (for `dp[i-1]`).

```java
import java.util.Arrays;

public class DecodeWaysIIOptimized {

    public int numDecodings(String s) {
        int n = s.length();
        final int MOD = 1_000_000_007;

        // Base case 0: Empty string has 1 way
        long prev2 = 1; // Represents dp[i-2]
        long prev1 = 0; // Represents dp[i-1] (for n=0, dp[0]=1, for n=1, dp[1] depends on s[0])

        // Handle the first character (dp[1])
        if (n >= 1) {
            if (s.charAt(0) == '0') {
                prev1 = 0;
            } else if (s.charAt(0) == '*') {
                prev1 = 9;
            } else {
                prev1 = 1;
            }
        }
        
        // If n=0, then prev1 will remain 0, and the loop won't run, 
        // leading to incorrect result. The problem constraints say 1 <= s.length.
        // So, n will be at least 1.

        // Loop from i = 2 up to n.
        for (int i = 2; i <= n; i++) {
            char current_char = s.charAt(i - 1);
            char prev_char = s.charAt(i - 2);
            long current_dp = 0; // Represents dp[i]

            // 1. Single-digit decoding for current_char
            if (current_char == '0') {
                current_dp = 0;
            } else if (current_char == '*') {
                current_dp = (9 * prev1) % MOD; // 9 ways from prev1 (dp[i-1])
            } else {
                current_dp = prev1 % MOD; // 1 way from prev1 (dp[i-1])
            }

            // 2. Two-digit decoding using prev_char and current_char
            if (prev_char == '1') {
                if (current_char == '*') {
                    current_dp = (current_dp + 10 * prev2) % MOD;
                } else {
                    current_dp = (current_dp + prev2) % MOD;
                }
            } else if (prev_char == '2') {
                if (current_char == '*') {
                    current_dp = (current_dp + 7 * prev2) % MOD;
                } else if (current_char >= '0' && current_char <= '6') {
                    current_dp = (current_dp + prev2) % MOD;
                }
            } else if (prev_char == '*') {
                if (current_char == '*') {
                    current_dp = (current_dp + 15 * prev2) % MOD;
                } else if (current_char >= '0' && current_char <= '6') {
                    current_dp = (current_dp + 2 * prev2) % MOD;
                } else { // current_char is '7'-'9'
                    current_dp = (current_dp + prev2) % MOD;
                }
            }
            
            // Update prev2 and prev1 for the next iteration
            prev2 = prev1;
            prev1 = current_dp;
        }

        // For n=1, loop doesn't run, prev1 already holds dp[1].
        // For n>1, prev1 will hold dp[n].
        return (int) prev1;
    }

    public static void main(String[] args) {
        DecodeWaysIIOptimized solver = new DecodeWaysIIOptimized();

        System.out.println("s = \"*\" -> Ways (Optimized): " + solver.numDecodings("*"));
        System.out.println("s = \"1*\" -> Ways (Optimized): " + solver.numDecodings("1*"));
        System.out.println("s = \"2*\" -> Ways (Optimized): " + solver.numDecodings("2*"));
        System.out.println("s = \"**\" -> Ways (Optimized): " + solver.numDecodings("**"));
        System.out.println("s = \"0\" -> Ways (Optimized): " + solver.numDecodings("0"));
        System.out.println("s = \"10\" -> Ways (Optimized): " + solver.numDecodings("10"));
        System.out.println("s = \"30\" -> Ways (Optimized): " + solver.numDecodings("30"));
    }
}
```

-----

### Top-Down (Memoization) Approach

Top-Down approach bhi is problem ke liye feasible hai, aur structure similar hoga.

**Memoization State:**
`memo[i]` = Number of ways to decode `s[i...n-1]`. Initialize with `-1`.

**Recursive Function:**
`solve(currentIndex, s, memo)`

**Logic:**

  * **Base Cases:**

      * If `currentIndex == n`, return `1` (empty string from this point, 1 way).
      * If `s.charAt(currentIndex) == '0'`, return `0` (cannot start with '0').
      * If `currentIndex == n-1`:
          * If `s.charAt(currentIndex) == '*'`, return `9`.
          * Else return `1`.

  * **Memoization Check:**

      * If `memo[currentIndex]` already computed (`!= -1`), return it.

  * **Main Logic:**

      * `totalWays = 0`
      * **Single-digit decode:**
          * If `s.charAt(currentIndex) == '*'`, add `9 * solve(currentIndex + 1, s, memo)` to `totalWays`.
          * Else (`s.charAt(currentIndex)` is `1`-`9`), add `solve(currentIndex + 1, s, memo)` to `totalWays`.
      * **Two-digit decode (look at `s[currentIndex]` and `s[currentIndex + 1]`):**
          * Define `firstChar = s.charAt(currentIndex)` and `secondChar = s.charAt(currentIndex + 1)`.
          * Logic similar to Bottom-Up to calculate ways from `firstChar` and `secondChar` combination, multiplied by `solve(currentIndex + 2, s, memo)`.
          * Add this to `totalWays`.
      * Store `totalWays` in `memo[currentIndex]` and return.

<!-- end list -->

```java
import java.util.Arrays;

public class DecodeWaysIITopDown {

    final int MOD = 1_000_000_007;
    private int N;
    private String S_global;
    private long[] memo; // Using long[] for memoization results

    public int numDecodings(String s) {
        N = s.length();
        S_global = s;
        memo = new long[N]; // memo[i] for results from s[i...N-1]
        Arrays.fill(memo, -1); // Initialize memo table with -1 (uncomputed)

        return (int) solve(0); // Start recursion from index 0
    }

    /**
     * Recursive function to calculate the number of ways to decode
     * substring s[currentIndex...N-1].
     * @param currentIndex The starting index of the current substring.
     * @return The number of ways, modulo MOD.
     */
    private long solve(int currentIndex) {
        // Base Case 1: If we've reached the end of the string, it's one valid way (empty suffix).
        if (currentIndex == N) {
            return 1;
        }

        // Base Case 2: If the current character is '0', it cannot start any valid number.
        // Problem states '0' cannot be mapped to any letter.
        if (S_global.charAt(currentIndex) == '0') {
            return 0;
        }

        // Memoization check
        if (memo[currentIndex] != -1) {
            return memo[currentIndex];
        }

        long totalWays = 0;

        // --- Option 1: Decode s[currentIndex] as a single digit ---
        if (S_global.charAt(currentIndex) == '*') {
            // '*' can be decoded in 9 ways (1-9)
            totalWays = (totalWays + 9 * solve(currentIndex + 1)) % MOD;
        } else {
            // A digit '1'-'9' can be decoded in 1 way
            totalWays = (totalWays + solve(currentIndex + 1)) % MOD;
        }

        // --- Option 2: Decode s[currentIndex] and s[currentIndex + 1] as a two-digit number ---
        if (currentIndex + 1 < N) { // Ensure there's a second character
            char firstChar = S_global.charAt(currentIndex);
            char secondChar = S_global.charAt(currentIndex + 1);

            if (firstChar == '1') {
                if (secondChar == '*') {
                    totalWays = (totalWays + 10 * solve(currentIndex + 2)) % MOD; // 10-19 (10 ways)
                } else { // secondChar is '0'-'9'
                    totalWays = (totalWays + solve(currentIndex + 2)) % MOD; // 10-19 (1 way)
                }
            } else if (firstChar == '2') {
                if (secondChar == '*') {
                    totalWays = (totalWays + 7 * solve(currentIndex + 2)) % MOD; // 20-26 (7 ways)
                } else if (secondChar >= '0' && secondChar <= '6') {
                    totalWays = (totalWays + solve(currentIndex + 2)) % MOD; // 20-26 (1 way)
                }
                // If secondChar is '7'-'9', '2' + '7'-'9' is invalid, so no contribution here.
            } else if (firstChar == '*') {
                if (secondChar == '*') {
                    // '**':
                    // '*' as '1' (10-19): 9 options (11-19)
                    // '*' as '2' (20-26): 6 options (21-26)
                    totalWays = (totalWays + 15 * solve(currentIndex + 2)) % MOD;
                } else if (secondChar >= '0' && secondChar <= '6') {
                    // '*' + '0'-'6':
                    // '*' as '1' (10-16): 1 option
                    // '*' as '2' (20-26): 1 option
                    totalWays = (totalWays + 2 * solve(currentIndex + 2)) % MOD;
                } else { // secondChar is '7'-'9'
                    // '*' + '7'-'9':
                    // '*' can only be '1' (e.g., '17', '18', '19'). '27' is invalid.
                    totalWays = (totalWays + solve(currentIndex + 2)) % MOD;
                }
            }
        }

        // Store the computed result in the memoization table.
        return memo[currentIndex] = totalWays;
    }

    public static void main(String[] args) {
        DecodeWaysIITopDown solver = new DecodeWaysIITopDown();

        System.out.println("s = \"*\" -> Ways (Top-Down): " + solver.numDecodings("*"));
        System.out.println("s = \"1*\" -> Ways (Top-Down): " + solver.numDecodings("1*"));
        System.out.println("s = \"2*\" -> Ways (Top-Down): " + solver.numDecodings("2*"));
        System.out.println("s = \"**\" -> Ways (Top-Down): " + solver.numDecodings("**"));
        System.out.println("s = \"0\" -> Ways (Top-Down): " + solver.numDecodings("0"));
        System.out.println("s = \"10\" -> Ways (Top-Down): " + solver.numDecodings("10"));
        System.out.println("s = \"30\" -> Ways (Top-Down): " + solver.numDecodings("30"));
    }
}
```

-----

### Tree Diagram (Conceptual for Top-Down)

`solve(0)`
/  
(Single-digit decode `s[0]`)   (Two-digit decode `s[0]s[1]`)
/  
`ways_from_s[0] * solve(1)`      `ways_from_s[0]s[1] * solve(2)`
/ \\                          /  
...  ...                      ...  ...

Har `solve(currentIndex)` call do options explore karta hai: current character ko single digit decode karna, ya current aur next character ko two-digit decode karna. `ways_from_X` yahan numbers of ways (1, 7, 9, 10, 15 etc.) ko denote karta hai jo specific characters ya asterisks se aate hain. Memoization overlapping `(currentIndex)` states ko avoid karta hai.

Yeh `Decode Ways II` problem ek bahut accha example hai jahan aapko `DP` base `Decode Ways` problem ko extend karna hota hai, aur `*` character ki wajah se `combinatorics` ka logic add karna padta hai. Both Bottom-Up (Tabulation) aur Top-Down (Memoization) approaches, khaas kar optimized space version, efficiently solve karte hain.



