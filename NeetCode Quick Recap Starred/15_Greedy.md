
## 53\. Maximum Subarray

**Maximum Subarray** ek classic Dynamic Programming problem hai jismein humein ek array mein maximum sum wala contiguous subarray find karna hota hai.

-----

### Description/Overview

Aapko ek integer array `nums` diya gaya hai. Aapko return karna hai us **contiguous subarray** ka sum jiska sum maximum ho. Ek subarray mein atleast ek number hona chahiye.

For example:

  * `nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]`
      * Output: 6
      * Explanation: `[4, -1, 2, 1]` has the largest sum = 6.
  * `nums = [1]`
      * Output: 1
  * `nums = [5, 4, -1, 7, 8]`
      * Output: 23

### Approach (How to do it)

This problem can be solved efficiently using **Kadane's Algorithm**, which is a dynamic programming approach.

1.  **Define `current_max` and `global_max`:**

      * `current_max`: Yeh track karta hai ki current position tak ka maximum subarray sum kya hai (jo current element par end ho raha hai).
      * `global_max`: Yeh track karta hai ki poore array mein ab tak ka overall maximum subarray sum kya hai.

2.  **Initialization:**

      * Initialize `current_max = nums[0]`.
      * Initialize `global_max = nums[0]`.

3.  **Iterate:**

      * Loop through the array `nums` starting from the second element (`i = 1` to `n-1`).
      * For each element `nums[i]`:
          * `current_max = Math.max(nums[i], current_max + nums[i])`
              * Explanation: Hum decide karte hain ki current element `nums[i]` ko ek naye subarray ki shuruat banayein ya usko previous `current_max` subarray mein extend karein. Agar `nums[i]` akela bada hai, toh naya subarray shuru karte hain. Agar `current_max + nums[i]` bada hai, toh extend karte hain.
          * `global_max = Math.max(global_max, current_max)`
              * Explanation: Har step par `global_max` ko update karte hain agar `current_max` usse bada ho jaye.

4.  **Final Result:** `global_max`.

### Solution (The Way to Solve)

Hum Kadane's Algorithm ka use karte hain. Do variables maintain karte hain: `currentMax` (current element tak ka max sum) aur `globalMax` (overall max sum). `currentMax` ko `nums[0]` aur `globalMax` ko bhi `nums[0]` se initialize karte hain. Fir, array ke har element par iterate karte hain. Har element `num` ke liye, `currentMax` ko `Math.max(num, currentMax + num)` se update karte hain. Iska matlab hai ki hum ya toh naya subarray `num` se shuru karte hain, ya `num` ko previous `currentMax` wale subarray mein add karte hain. Fir, `globalMax` ko `Math.max(globalMax, currentMax)` se update karte hain. Finally, `globalMax` return karte hain.

### Code

```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            // According to problem constraints, nums will always have at least one element.
            // But good practice to handle.
            return 0;
        }

        // Initialize current_max to the first element. This will store the maximum sum
        // of a subarray ending at the current position.
        int currentMax = nums[0];
        // Initialize global_max to the first element. This will store the overall
        // maximum sum found so far.
        int globalMax = nums[0];

        // Iterate through the array starting from the second element
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            // For the current number, we have two choices:
            // 1. Start a new subarray with `num` (if `num` itself is greater than `currentMax + num`)
            // 2. Extend the existing subarray by adding `num` to `currentMax`
            currentMax = Math.max(num, currentMax + num);
            
            // Update the global maximum sum if the current maximum sum is greater
            globalMax = Math.max(globalMax, currentMax);
        }

        // The global maximum sum is the result
        return globalMax;
    }
}
```

-----

## 55\. Jump Game

**Jump Game** ek Dynamic Programming problem hai (jise Greedy se bhi solve kar sakte hain) jismein humein check karna hota hai ki kya hum array ke last index tak pahunch sakte hain.

-----

### Description/Overview

Aapko ek integer array `nums` diya gaya hai. Aap initially first index par ho (`nums[0]`). Har element `nums[i]` represent karta hai ki aap `i` se maximum kitne steps aage jump kar sakte ho. Aapko return karna hai `true` agar aap last index tak pahunch sakte ho, `false` otherwise.

For example:

  * `nums = [2, 3, 1, 1, 4]`
      * Output: `true` (Jump 1 step from index 0 to 1, then 3 steps to last index)
  * `nums = [3, 2, 1, 0, 4]`
      * Output: `false` (Aap index 3 par pahunch jaoge, but wahan se 0 steps aage jump kar sakte ho, so last index tak nahi pahunch paoge).

### Approach (How to do it)

This problem can be solved using Dynamic Programming (Top-Down or Bottom-Up) or a more efficient **Greedy** approach. The Greedy approach is generally preferred for its simplicity and better time complexity.

**Greedy Approach:**
The idea is to keep track of the `maxReach` (farthest index reachable so far).

1.  **Initialization:**

      * `maxReach = 0` (Initially, we are at index 0, so max reachable is 0).
      * `n = nums.length`.

2.  **Iterate:**

      * Loop `i` from `0` to `n-1`.
      * **Check Reachability:** If `i > maxReach`, it means the current index `i` cannot be reached, so we cannot reach the end. Return `false`.
      * **Update `maxReach`:** `maxReach = Math.max(maxReach, i + nums[i])`. (From current index `i`, we can jump `nums[i]` steps forward. So, `i + nums[i]` is a new potential maximum reach).
      * **Check Goal:** If `maxReach >= n - 1`, it means we can reach or go beyond the last index. Return `true`.

3.  **Final Result:** If the loop finishes without returning `true` or `false` in between, it implies we couldn't reach the end. Return `false`. (This usually happens if `n` is small and the loop ends, but `maxReach` wasn't `n-1`.) Actually, the loop will correctly return `true` once `maxReach >= n-1` is met.

### Solution (The Way to Solve)

Hum greedy approach ka use karte hain. Ek variable `maxReach` maintain karte hain jo track karta hai ki hum kitni door tak pahunch sakte hain. `maxReach` ko initially 0 set karte hain. Array ke har index `i` par iterate karte hain. Agar `i` `maxReach` se bada ho jata hai, toh matlab hum is index tak pahunch hi nahi sakte, toh `false` return karte hain. Warna, `maxReach` ko `Math.max(maxReach, i + nums[i])` se update karte hain. Agar `maxReach` `nums.length - 1` ke barabar ya usse bada ho jata hai, toh matlab hum last index tak pahunch sakte hain, toh `true` return karte hain. Agar loop poora ho jata hai aur humne `true` return nahi kiya, toh matlab hum last index tak nahi pahunch paye, toh `false` return karte hain.

### Code

```java
class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return true; // Already at or beyond the last index
        }

        // maxReach tracks the farthest index we can reach so far.
        int maxReach = 0;

        // Iterate through the array. 'i' represents the current position.
        // We only need to iterate as long as 'i' is within our 'maxReach'.
        // If 'i' exceeds 'maxReach', it means we cannot reach this position,
        // and thus cannot reach the end.
        for (int i = 0; i < n; i++) {
            // If the current position 'i' is beyond what we can reach,
            // it means we are stuck and cannot proceed to the end.
            if (i > maxReach) {
                return false;
            }

            // Update the maximum reach: either the current maxReach,
            // or the new reach from the current position (i + nums[i]).
            maxReach = Math.max(maxReach, i + nums[i]);

            // If we can reach or go beyond the last index (n-1),
            // we have found a path.
            if (maxReach >= n - 1) {
                return true;
            }
        }

        // If the loop completes, it means we have iterated through all reachable positions
        // and still couldn't reach or surpass the last index.
        return false;
    }
}
```

-----

## 45\. Jump Game II

**Jump Game II** ek Dynamic Programming problem hai (jise Greedy BFS se bhi solve kar sakte hain) jismein humein array ke last index tak pahunchne ke liye minimum jumps find karne hote hain.

-----

### Description/Overview

Aapko ek integer array `nums` diya gaya hai. Aap initially first index par ho (`nums[0]`). Har element `nums[i]` represent karta hai ki aap `i` se maximum kitne steps aage jump kar sakte ho. Aapko return karna hai ki last index tak pahunchne ke liye **minimum number of jumps** kitne lagenge. It is guaranteed that you can reach the last index.

For example:

  * `nums = [2, 3, 1, 1, 4]`
      * Output: 2
      * Explanation:
          * From index 0, jump 1 step to index 1 (`nums[0] = 2`).
          * From index 1, jump 3 steps to index 4 (last index) (`nums[1] = 3`).
          * Total jumps = 2.
  * `nums = [2, 3, 0, 1, 4]`
      * Output: 2
      * Explanation:
          * From index 0, jump 1 step to index 1 (`nums[0]=2`).
          * From index 1, jump 3 steps to index 4 (last index) (`nums[1]=3`).
          * Total jumps = 2. (Notice you could also jump from index 0 to 2, but then from 2 to 3, then 3 to 4, which is 3 jumps. We need minimum.)

### Approach (How to do it)

This problem is a classic example where a greedy approach (often thought of as a Breadth-First Search-like traversal) works best for finding the minimum number of jumps.

**Greedy (BFS-like) Approach:**
The idea is to extend our `reach` as far as possible with each jump.

1.  **Initialization:**

      * `n = nums.length`.
      * If `n <= 1`, return `0` (already at the end).
      * `jumps = 0` (Number of jumps taken).
      * `currentEnd = 0` (The farthest index reachable with the current number of jumps).
      * `farthestReach = 0` (The farthest index that can be reached from any position within the current jump's range).

2.  **Iterate:**

      * Loop `i` from `0` to `n-2` (We don't need to check the last element, as we are trying to reach it).
      * **Update `farthestReach`:** `farthestReach = Math.max(farthestReach, i + nums[i])`. This finds the maximum reach possible from any position we can currently access.
      * **Check if a jump is needed:** If `i == currentEnd`:
          * It means we have exhausted all possibilities from the current jump, and we need to take another jump.
          * Increment `jumps`.
          * Update `currentEnd = farthestReach`. (The new `currentEnd` is the maximum reach we just calculated).
          * **Early Exit:** If `currentEnd >= n - 1`, it means after this jump, we can reach or go beyond the last index. Return `jumps`.

3.  **Final Result:** The loop will eventually find the minimum jumps and return. If for some reason the loop completes (which it shouldn't as problem guarantees reachability), `jumps` would contain the answer.

### Solution (The Way to Solve)

Hum ek greedy (BFS-like) approach use karte hain. Variables `jumps`, `currentEnd`, aur `farthestReach` maintain karte hain. `jumps` ko 0 se initialize karte hain. `currentEnd` (current jump se kitni door tak pahunch sakte hain) aur `farthestReach` (next jump se kitni door tak pahunch sakte hain) ko bhi 0 se initialize karte hain.
Array ke har element `i` par iterate karte hain (last element tak nahi). Har step par `farthestReach` ko `Math.max(farthestReach, i + nums[i])` se update karte hain. Jab `i` `currentEnd` tak pahunchta hai, toh iska matlab hai ki humne previous jump se jitni door tak ja sakte the, utni door tak cover kar liya hai, aur ab humein next jump leni padegi. Is point par `jumps` ko increment karte hain aur `currentEnd` ko naye `farthestReach` par update karte hain. Agar `currentEnd` last index (`n-1`) tak pahunch jata hai, toh `jumps` return kar dete hain.

### Code

```java
class Solution {
    public int jump(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0; // Already at the end or no jumps needed
        }

        int jumps = 0;       // Count of jumps taken
        int currentEnd = 0;  // The maximum index reachable with the current number of jumps
        int farthestReach = 0; // The maximum index reachable from any position within the current jump's range

        // Iterate through the array. We go up to n-2 because if we are at n-1, we are already at the end.
        for (int i = 0; i < n - 1; i++) {
            // Update farthestReach: From current position 'i', we can jump nums[i] steps.
            // So, i + nums[i] is a potential new farthest reachable index.
            farthestReach = Math.max(farthestReach, i + nums[i]);

            // If we have reached the end of the current jump's coverage (currentEnd),
            // it means we must take another jump.
            if (i == currentEnd) {
                jumps++; // Increment the jump count
                currentEnd = farthestReach; // Update the new 'currentEnd' to the farthest we can now reach

                // If the new 'currentEnd' reaches or surpasses the last index, we are done.
                if (currentEnd >= n - 1) {
                    break; // Exit early as we found the minimum jumps
                }
            }
        }

        return jumps;
    }
}
```

-----

## 134\. Gas Station

**Gas Station** ek Greedy Algorithm problem hai jismein humein ek circular route par travel karne ke liye starting gas station find karna hota hai.

-----

### Description/Overview

Aapke paas `n` gas stations hain jo ek circular path par hain. Aapko do integer arrays `gas` aur `cost` diye gaye hain.

  * `gas[i]` hai `i-th` station par available gas ki amount.
  * `cost[i]` hai `i-th` station se `(i+1)-th` station tak jaane ka cost.
    Aap car mein empty tank ke saath start karte ho. Agar aap poora circular path travel kar sakte ho, toh starting gas station ka index return karo. Agar aisa koi station nahi hai, toh `-1` return karo. Agar solution exist karta hai, toh woh unique hoga.

For example:

  * `gas = [1, 2, 3, 4, 5], cost = [3, 4, 5, 1, 2]`
      * Output: 3
      * Explanation:
          * Start at station 3 (`gas[3]=4`, `cost[3]=1`). Tank = 0.
          * Station 3: fill 4. Tank = 4. Go to station 4. Cost = 1. Tank = 3.
          * Station 4: fill 5. Tank = 8. Go to station 0. Cost = 2. Tank = 6.
          * Station 0: fill 1. Tank = 7. Go to station 1. Cost = 3. Tank = 4.
          * Station 1: fill 2. Tank = 6. Go to station 2. Cost = 4. Tank = 2.
          * Station 2: fill 3. Tank = 5. Go to station 3. Cost = 5. Tank = 0.
          * Saare stations travel kar liye. Index 3 is the answer.
  * `gas = [2, 3, 4], cost = [3, 4, 3]`
      * Output: -1
      * Explanation: Total gas = 9, Total cost = 10. Gas is less than cost.

### Approach (How to do it)

This problem can be solved with a greedy approach.

**Key Observations:**

1.  **Total Gas vs. Total Cost:** If the `sum(gas)` is less than `sum(cost)`, it's impossible to complete the circuit. Return `-1`. This is a necessary condition.
2.  **Finding the Starting Point:** If `sum(gas) >= sum(cost)`, a solution must exist and be unique. How to find it?
      * We iterate through the stations, keeping track of the `current_tank` balance.
      * If at any point `current_tank` becomes negative, it means we cannot reach the current station from our *current starting point*. So, we discard the current starting point and try the *next station* as a potential starting point.
      * When we reset the `current_tank` to 0, we also update the `start_station` to the current `i + 1`.

**Algorithm:**

1.  **Calculate `total_gas` and `total_cost`:**

      * Sum up all elements in `gas` and `cost` arrays.
      * If `total_gas < total_cost`, return `-1`.

2.  **Iterate to find `start_station`:**

      * Initialize `current_tank = 0`.
      * Initialize `start_station = 0`.
      * Loop `i` from `0` to `n-1`:
          * `current_tank += gas[i] - cost[i]`.
          * If `current_tank < 0`:
              * This `start_station` (from which we began this `current_tank` calculation) is not valid.
              * Reset `current_tank = 0`.
              * Set `start_station = i + 1`. (Try the next station as the new potential starting point).

3.  **Final Result:** `start_station`. (Because if a solution exists, this logic will correctly identify it).

### Solution (The Way to Solve)

Hum do main checks karte hain. Pehla, agar total available gas total required cost se kam hai, toh `  -1 ` return karte hain, kyunki circular path complete karna impossible hai.
Agar total gas total cost se zyada ya barabar hai, toh humein pata hai ki ek unique solution exist karta hai. Is solution ko find karne ke liye, hum ek `currentTank` aur `startStation` variable maintain karte hain. `currentTank` mein current station tak ka net gas balance store karte hain. `startStation` mein potential starting station ka index hota hai.
Array ke har station `i` par iterate karte hain. `currentTank` mein `gas[i] - cost[i]` add karte hain. Agar `currentTank` negative ho jata hai, toh iska matlab hai ki current `startStation` se hum current `i` tak nahi pahunch paye. Toh, `currentTank` ko 0 reset karte hain aur `startStation` ko `i + 1` set karte hain.
Loop complete hone par `startStation` mein sahi answer store hoga.

### Code

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int totalGas = 0;   // Sum of all gas available
        int totalCost = 0;  // Sum of all cost required

        // Calculate total gas and total cost to quickly check feasibility
        for (int i = 0; i < n; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }

        // If total gas is less than total cost, it's impossible to complete the circuit.
        // Return -1.
        if (totalGas < totalCost) {
            return -1;
        }

        // If a solution exists, it is guaranteed to be unique.
        // We can find it by tracking the current tank level and resetting the start point
        // whenever the tank goes negative.
        int currentTank = 0;    // Tracks the current gas in the tank
        int startStation = 0;   // Stores the potential starting station index

        for (int i = 0; i < n; i++) {
            // Add the gas gained at current station and subtract the cost to reach the next station
            currentTank += gas[i] - cost[i];

            // If the current tank becomes negative, it means we cannot reach the current station 'i'
            // from our current 'startStation'. So, this 'startStation' is not valid.
            // We reset the tank and try the next station (i+1) as the potential starting point.
            if (currentTank < 0) {
                currentTank = 0;         // Reset tank
                startStation = i + 1;    // Try the next station as the starting point
            }
        }

        // By the problem statement, if totalGas >= totalCost, a unique solution exists.
        // The 'startStation' found by this greedy approach will be the correct one.
        return startStation;
    }
}
```

-----

## 846\. Hand of Straights

**Hand of Straights** ek Array/Greedy problem hai (jahan Map ka use bhi hota hai) jismein humein check karna hota hai ki kya cards ko groups of consecutive cards mein divide kiya ja sakta hai.

-----

### Description/Overview

Aapko ek integer array `hand` diya gaya hai, jismein cards ke numbers hain, aur ek integer `groupSize` diya gaya hai. Aapko return karna hai `true` agar aap `hand` ke saare cards ko groups mein rearrange kar sakte ho, jahan har group mein theek `groupSize` cards honge, aur woh cards **consecutive** honge (yani numbers `x, x+1, x+2, ... x + groupSize - 1`).

For example:

  * `hand = [1, 2, 3, 6, 2, 3, 4, 7, 8], groupSize = 3`
      * Output: `true`
      * Explanation: `[1, 2, 3]`, `[2, 3, 4]`, `[6, 7, 8]`
  * `hand = [1, 2, 3, 4, 5], groupSize = 4`
      * Output: `false` (Cannot form `4` consecutive cards)

### Approach (How to do it)

This problem can be solved using a greedy approach with the help of a frequency map (or `TreeMap` for sorted keys).

1.  **Preliminary Check:**

      * If `hand.length % groupSize != 0`, then it's impossible to form groups of `groupSize`. Return `false`.

2.  **Frequency Map:**

      * Use a `TreeMap<Integer, Integer>` (or sort the array and use a regular `HashMap`) to store the frequency of each card number. `TreeMap` is better here because it keeps keys sorted, which is useful for finding consecutive numbers.

3.  **Greedy Strategy:**

      * Iterate while the `TreeMap` is not empty:
          * Get the smallest card number `startCard` from the `TreeMap` (this will be the first card of a new group).
          * For each card from `startCard` to `startCard + groupSize - 1`:
              * Check if `currentCard` exists in the `TreeMap` with count `> 0`. If not, it means we cannot form a consecutive group, so return `false`.
              * Decrement the count of `currentCard` in the `TreeMap`.
              * If the count becomes 0, remove `currentCard` from the `TreeMap`.

4.  **Final Result:** If the loop completes without returning `false`, it means all cards were successfully grouped. Return `true`.

### Solution (The Way to Solve)

Pehle, check karte hain ki `hand` ki length `groupSize` se divisible hai ya nahi. Agar nahi, toh `false` return karte hain.
Fir, cards ki frequency store karne ke liye `TreeMap<Integer, Integer>` (ya sorted array use karke loop) ka use karte hain. `TreeMap` automatic sorting provide karta hai.
Jab tak `TreeMap` empty nahi hota, loop karte hain:

1.  `TreeMap` se sabse chhota card `startCard` nikalte hain. Yeh naye group ka pehla card hoga.
2.  `startCard` se shuru karke `groupSize` cards (yani `startCard, startCard+1, ..., startCard+groupSize-1`) ko check karte hain.
3.  Har `currentCard` ke liye, check karte hain ki `TreeMap` mein uski frequency `> 0` hai ya nahi. Agar nahi, toh `false` return karte hain.
4.  `currentCard` ki frequency ko decrement karte hain. Agar frequency 0 ho jati hai, toh `currentCard` ko `TreeMap` se remove kar dete hain.
    Agar loop successfully complete ho jata hai, toh `true` return karte hain.

### Code

```java
import java.util.TreeMap;
import java.util.Map;
import java.util.Arrays; // For sorting if using HashMap instead of TreeMap

class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;

        // If the total number of cards is not divisible by groupSize,
        // it's impossible to form groups of that size.
        if (n % groupSize != 0) {
            return false;
        }

        // Use a TreeMap to store card frequencies. TreeMap automatically
        // keeps keys (card numbers) sorted, which is crucial for finding
        // consecutive cards greedily.
        Map<Integer, Integer> cardCounts = new TreeMap<>();
        for (int card : hand) {
            cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
        }

        // While there are still cards left in the hand
        while (!cardCounts.isEmpty()) {
            // Get the smallest card available. This will be the starting card
            // for our new consecutive group.
            int startCard = cardCounts.keySet().iterator().next();

            // Try to form a group of 'groupSize' consecutive cards starting from 'startCard'
            for (int i = 0; i < groupSize; i++) {
                int currentCard = startCard + i;

                // If the currentCard is not available (count is 0 or not in map),
                // then we cannot form a consecutive group.
                if (!cardCounts.containsKey(currentCard) || cardCounts.get(currentCard) == 0) {
                    return false;
                }

                // Decrement the count of the currentCard
                cardCounts.put(currentCard, cardCounts.get(currentCard) - 1);

                // If the count becomes 0, remove the card from the map
                if (cardCounts.get(currentCard) == 0) {
                    cardCounts.remove(currentCard);
                }
            }
        }

        // If all cards were successfully grouped, return true
        return true;
    }
}
```

-----

## 1899\. Merge Triplets to Form Target Triplet

**Merge Triplets to Form Target Triplet** ek Greedy/Array problem hai jismein humein check karna hota hai ki kya diye gaye triplets ko merge karke ek target triplet bana sakte hain.

-----

### Description/Overview

Aapko ek 2D integer array `triplets` diya gaya hai, jahan `triplets[i] = [a_i, b_i, c_i]` hai. Aapko ek integer array `target = [x, y, z]` diya gaya hai.
Aap ek operation perform kar sakte ho: do triplets `triplet_1 = [a_1, b_1, c_1]` aur `triplet_2 = [a_2, b_2, c_2]` ko merge karke ek naya triplet `[max(a_1, a_2), max(b_1, b_2), max(c_1, c_2)]` bana sakte ho. Aap kitni bhi baar operations perform kar sakte ho.
Aapko return karna hai `true` agar aap diye gaye triplets ko merge karke `target` triplet bana sakte ho, `false` otherwise.

For example:

  * `triplets = [[2, 5, 3], [1, 8, 4], [1, 7, 5]], target = [2, 7, 5]`
      * Output: `true`
      * Explanation: Merge `[2, 5, 3]` and `[1, 7, 5]` -\> `[max(2,1), max(5,7), max(3,5)] = [2, 7, 5]`. Achieved target.
  * `triplets = [[3, 4, 5], [1, 2, 3]], target = [3, 2, 5]`
      * Output: `false` (Target 'y' component 2 hai, but koi bhi triplet ka 'b' component 2 nahi hai jo 'a' aur 'c' ko target tak na exceed kare.)
  * `triplets = [[2, 5, 3], [2, 3, 4], [1, 2, 5], [5, 2, 3]], target = [5, 5, 5]`
      * Output: `true`

### Approach (How to do it)

This problem has a clever greedy approach.

**Key Observation:**
When merging two triplets `[a1, b1, c1]` and `[a2, b2, c2]` to get `[max(a1, a2), max(b1, b2), max(c1, c2)]`, the components *only increase or stay the same*. They never decrease.

This means:

1.  If any component of a `triplet[i]` is **greater than** its corresponding component in `target`, then that `triplet[i]` cannot be used, because merging it would make the final `target` component exceed the required value.
2.  We only care about triplets where **all three components** are less than or equal to their corresponding `target` components.
3.  From these "valid" triplets, we want to see if we can "collect" `target[0]`, `target[1]`, and `target[2]`.

**Algorithm:**

1.  Initialize a boolean array `found = [false, false, false]` to track if we have found a `triplet` that contributes to matching `target[0]`, `target[1]`, and `target[2]` respectively.
2.  Iterate through each `triplet = [a, b, c]` in `triplets`:
      * **Check Validity:** If `a > target[0]` or `b > target[1]` or `c > target[2]`, then skip this triplet. It's an invalid candidate.
      * **Check for Contribution:** If the triplet is valid:
          * If `a == target[0]`, set `found[0] = true`.
          * If `b == target[1]`, set `found[1] = true`.
          * If `c == target[2]`, set `found[2] = true`.
3.  **Final Result:** Return `found[0] && found[1] && found[2]`. This means we have found at least one valid triplet that could provide `target[0]`, at least one valid triplet that could provide `target[1]`, and at least one valid triplet that could provide `target[2]`. Since merging takes `max`, if we find such triplets, we can eventually merge them to form the `target`.

### Solution (The Way to Solve)

Is problem ko solve karne ke liye greedy approach use karte hain. Key idea ye hai ki merge karne par triplet ke components ya toh badhte hain ya same rehte hain, kam nahi hote.
Toh, hum sirf un triplets par focus karte hain jinke saare components `target` ke corresponding components se less than or equal to hon. Agar koi triplet ka koi bhi component `target` ke corresponding component se bada hai, toh us triplet ko merge karne se `target` exceed ho jayega, isliye usko ignore karte hain.
Ek boolean array `found = [false, false, false]` banate hain. Har triplet `[a, b, c]` par iterate karte hain. Agar `a <= target[0]`, `b <= target[1]`, aur `c <= target[2]` hai, toh yeh valid candidate hai. Agar `a == target[0]` hai, toh `found[0]` ko `true` set karte hain. Similar for `b` and `c`.
Loop ke end mein, agar `found[0] && found[1] && found[2]` (sab true) hai, toh `true` return karte hain. Warna, `false`.

### Code

```java
class Solution {
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        // Boolean flags to track if we have found a triplet that can contribute
        // to achieving target[0], target[1], and target[2] respectively.
        boolean foundX = false;
        boolean foundY = false;
        boolean foundZ = false;

        // Iterate through each triplet in the given array
        for (int[] triplet : triplets) {
            int a = triplet[0];
            int b = triplet[1];
            int c = triplet[2];

            // Crucial condition: A triplet is only useful if ALL its components
            // are less than or equal to the corresponding target components.
            // If any component in the current triplet is greater than the target's,
            // then merging this triplet will make the target component exceed.
            if (a <= target[0] && b <= target[1] && c <= target[2]) {
                // If this triplet's 'a' component matches target[0], mark foundX as true.
                if (a == target[0]) {
                    foundX = true;
                }
                // If this triplet's 'b' component matches target[1], mark foundY as true.
                if (b == target[1]) {
                    foundY = true;
                }
                // If this triplet's 'c' component matches target[2], mark foundZ as true.
                if (c == target[2]) {
                    foundZ = true;
                }
            }
        }

        // We can achieve the target triplet if and only if we have found
        // at least one valid triplet that provided the target[0] value,
        // at least one valid triplet that provided the target[1] value,
        // and at least one valid triplet that provided the target[2] value.
        // Since merging takes Math.max, if these values are available from valid triplets,
        // they can eventually be combined to form the target.
        return foundX && foundY && foundZ;
    }
}
```

-----

## 763\. Partition Labels

**Partition Labels** ek String/Greedy problem hai jismein humein string ko partitions mein divide karna hota hai, jismein har letter ek hi partition mein appear kare.

-----

### Description/Overview

Aapko ek string `s` diya gaya hai jismein small English letters hain. Aapko `s` ko jitne ho sake utne parts mein partition karna hai, taaki har letter zyada se zyada ek part mein appear kare. Phir, un parts ki lengths ki list return karni hai.

For example:

  * `s = "ababcbacadefegdehijhklij"`
      * Output: `[9, 7, 8]`
      * Explanation:
          * The first partition is "ababcbaca". Its length is 9. In this partition, letters 'a', 'b', 'c' appear.
          * The next partition is "defegde". Its length is 7. Letters 'd', 'e', 'f', 'g' appear.
          * The last partition is "hijhklij". Its length is 8. Letters 'h', 'i', 'j', 'k', 'l' appear.
          * Every letter appears in at most one part. And this is the maximum number of parts we can get.
  * `s = "eccbbbbdec"`
      * Output: `[10]`

### Approach (How to do it)

This problem can be solved using a greedy approach.

**Key Idea:**
To make the maximum number of partitions, each partition should be as small as possible. This means if a character `c` appears in a partition, all occurrences of `c` *must* be within that same partition.

**Algorithm:**

1.  **Find Last Occurrences:**

      * Create an array `lastOccurrence[26]` (for 'a' through 'z') to store the last index of each character in `s`.
      * Iterate through `s` once to populate `lastOccurrence`. `lastOccurrence[c - 'a'] = i`.

2.  **Greedy Partitioning:**

      * Initialize `partitions = new ArrayList<Integer>()`.

      * Initialize `start = 0` (start of the current partition).

      * Initialize `end = 0` (end of the current partition). This `end` will be the furthest right boundary that any character *currently within our partition* forces us to extend to.

      * Loop `i` from `0` to `s.length() - 1`:

          * Get the current character `char_i = s.charAt(i)`.
          * Update `end = Math.max(end, lastOccurrence[char_i - 'a'])`. (The current partition must extend at least to the last occurrence of `char_i`).
          * If `i == end`:
              * It means we have reached the furthest point required by any character in the current partition. This is where we can make a cut.
              * Add `(end - start + 1)` to `partitions`.
              * Update `start = i + 1` (start of the next partition).

3.  **Final Result:** `partitions`.

### Solution (The Way to Solve)

Hum greedy approach use karte hain. Pehle, ek array `lastOccurrence` banate hain jo har character ka `s` mein last occurrence index store karega.
Fir, `partitions` list, `start` (current partition ka start index), aur `end` (current partition ki max reach) ko initialize karte hain.
String `s` ke har character `i` par iterate karte hain. `s.charAt(i)` ka last occurrence `lastOccurrence[s.charAt(i) - 'a']` se fetch karte hain aur `end` ko `Math.max(end, lastOccurrence[s.charAt(i) - 'a'])` se update karte hain.
Agar current index `i` `end` ke barabar ho jata hai, toh iska matlab hai ki is point tak current partition ke saare characters ke last occurrences cover ho gaye hain. Yahan hum ek partition bana sakte hain. `partitions` list mein `(end - start + 1)` add karte hain, aur `start` ko `i + 1` set karte hain agle partition ke liye.
Final result `partitions` list hogi.

### Code

```java
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; // Can use HashMap instead of array for last occurrence

class Solution {
    public List<Integer> partitionLabels(String s) {
        // Step 1: Store the last occurrence index of each character
        // Using an array of size 26 for 'a' through 'z'
        int[] lastOccurrence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }

        List<Integer> partitions = new ArrayList<>();
        int startOfPartition = 0; // Start index of the current partition
        int endOfPartition = 0;   // Farthest reach (last index) required for the current partition

        // Step 2: Iterate through the string to find partition boundaries
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            
            // Update the 'endOfPartition' to the maximum of its current value
            // and the last occurrence of the 'currentChar'.
            // This ensures that all occurrences of characters seen so far are included.
            endOfPartition = Math.max(endOfPartition, lastOccurrence[currentChar - 'a']);

            // If the current index 'i' is equal to 'endOfPartition',
            // it means all characters seen from 'startOfPartition' up to 'i'
            // have had their last occurrences within or at 'i'.
            // So, this is a valid point to cut a partition.
            if (i == endOfPartition) {
                // Calculate the length of the current partition
                partitions.add(endOfPartition - startOfPartition + 1);
                // Set the start of the next partition to the next index
                startOfPartition = i + 1;
            }
        }

        return partitions;
    }
}
```

-----

## 678\. Valid Parenthesis String

**Valid Parenthesis String** ek String/Dynamic Programming problem hai (jise Greedy se bhi solve kar sakte hain) jismein humein check karna hota hai ki kya ek string valid parenthesis string hai `*` wildcard ke saath.

-----

### Description/Overview

Aapko ek string `s` diya gaya hai jismein teen tarah ke characters hain: `'(', ')', aur '*'`. Aapko return karna hai `true` agar string `s` valid hai, `false` otherwise.
Validity rules:

1.  Har left parenthesis `'('` ka corresponding right parenthesis `')'` hona chahiye.
2.  Har right parenthesis `')'` ka corresponding left parenthesis `'('` hona chahiye.
3.  Left parenthesis `'('` ko uske corresponding right parenthesis `')'` se pehle aana chahiye.
4.  `'*'` ko ek single right parenthesis `')'`, ek single left parenthesis `'('`, ya ek empty string `""` ke roop mein treat kiya ja sakta hai.

For example:

  * `s = "()"`
      * Output: `true`
  * `s = "(*)"`
      * Output: `true` (`*` can be treated as `(` or `)`)
  * `s = "(*))"`
      * Output: `true` (`*` as `(` makes `(())`)
  * `s = "((*)`
      * Output: `true` (`*` as `)`)
  * `s = "(())((()()()())()()(())(*()(())))%(`
      * Output: `false` (Unbalanced `(` at the end)

### Approach (How to do it)

This problem can be solved using Dynamic Programming, but a more efficient and intuitive solution involves a **Greedy** approach using two variables to track possible balance ranges.

**Greedy Approach:**
We can keep track of the possible range of open parentheses counts.

  * `low`: The minimum possible number of open parentheses that are currently unmatched.
  * `high`: The maximum possible number of open parentheses that are currently unmatched.

<!-- end list -->

1.  **Initialization:**

      * `low = 0`
      * `high = 0`

2.  **Iterate:**

      * Loop through each character `c` in the string `s`:
          * If `c == '('`:

              * `low++` (Minimum open count increases)
              * `high++` (Maximum open count increases)

          * If `c == ')':`

              * `low = Math.max(0, low - 1)` (Minimum open count decreases, but cannot go below 0)
              * `high--` (Maximum open count decreases)

          * If `c == '*':`

              * `low = Math.max(0, low - 1)` (If `*` is treated as `)`, it closes an open parenthesis, min open count decreases). Cannot go below 0.
              * `high++` (If `*` is treated as `(`, it adds an open parenthesis, max open count increases).

          * **Early Invalidity Check:** After processing each character:

              * If `high < 0`, it means even with all `*` treated as `(`, we have too many closing parentheses. This string is invalid. Return `false`.

3.  **Final Check:**

      * After iterating through the entire string:
      * If `low == 0`, it means that it's possible to balance all parentheses (minimum unmatched open parentheses is 0). Return `true`.
      * If `low > 0`, it means there are always some unmatched open parentheses, regardless of how `*` are interpreted. Return `false`.

### Solution (The Way to Solve)

Hum greedy approach use karte hain jismein do variables `low` aur `high` maintain karte hain. `low` track karta hai minimum possible open parenthesis count ko jo unmatched hain, aur `high` track karta hai maximum possible open parenthesis count ko jo unmatched hain.
Dono ko 0 se initialize karte hain.
String `s` ke har character `c` par iterate karte hain:

  * Agar `c` `(` hai, toh `low` aur `high` dono ko increment karte hain.
  * Agar `c` `)` hai, toh `low` ko `Math.max(0, low - 1)` se aur `high` ko `high - 1` se update karte hain.
  * Agar `c` `*` hai, toh `low` ko `Math.max(0, low - 1)` se (kyunki `*` `)` ban sakta hai) aur `high` ko `high + 1` se (kyunki `*` `(` ban sakta hai) update karte hain.
    Har character ke baad, agar `high` negative ho jata hai, toh string invalid hai, `false` return karte hain.
    Loop ke end mein, agar `low` 0 hai, toh `true` return karte hain (matlab minimum unmatched open parenthesis 0 hai, so balanced possible hai). Warna, `false` return karte hain.

### Code

```java
class Solution {
    public boolean checkValidString(String s) {
        // low: minimum number of open parentheses that are currently unmatched.
        // This occurs when we treat '*' as ')' to minimize open parentheses, or as '' if it would make low negative.
        int low = 0;

        // high: maximum number of open parentheses that are currently unmatched.
        // This occurs when we treat '*' as '(' to maximize open parentheses.
        int high = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                low++;
                high++;
            } else if (c == ')') {
                // If ')' is encountered, it tries to close an open parenthesis.
                // Minimum: If low > 0, we can close one. Else, it means no open '(' available, keep 0.
                low = Math.max(0, low - 1);
                // Maximum: Always decrement high, as ')' consumes an open parenthesis.
                high--;
            } else { // c == '*'
                // If '*' is treated as ')', it helps reduce the minimum open count.
                // We take max(0, low - 1) to ensure low doesn't go negative.
                low = Math.max(0, low - 1);
                // If '*' is treated as '(', it increases the maximum open count.
                high++;
            }

            // If at any point high becomes negative, it means we have more ')' than '(' (even
            // if all '*' are treated as '('), which makes the string invalid.
            if (high < 0) {
                return false;
            }
        }

        // After iterating through the entire string, if low is 0, it means
        // it's possible to balance all parentheses (minimum unmatched open parentheses is 0).
        // If low > 0, it means there are always some unmatched open parentheses,
        // regardless of how '*' are interpreted, so it's invalid.
        return low == 0;
    }
}
```
