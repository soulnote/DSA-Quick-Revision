# Subsequence / LIS Type Questions Kya Hote Hain?

**Subsequence** ka matlab hai ki original sequence (string, array) se kuch elements ko delete karke ek nayi sequence banana, lekin unka **relative order** maintain rehna chahiye. Contiguous hone ki zaroorat nahi hoti.

**LIS (Longest Increasing Subsequence)** ek classic subsequence problem hai jahan aapko ek array (ya list) of numbers di jati hai, aur aapko uski **longest possible subsequence** dhundhni hoti hai jismein elements strictly increasing order mein hon.

**Example of LIS:**

  * Array: `[10, 22, 9, 33, 21, 50, 41, 60]`
  * **LIS:** `[10, 22, 33, 41, 60]` (Length 5)
      * Ya `[10, 22, 33, 50, 60]` (Length 5)

LIS type questions mein sirf increasing hi nahi, decreasing, bitonic, ya specific conditions wale subsequences bhi ho sakte hain.

-----

### Subsequence / LIS Type Questions Ko Kaise Pehchanein?

Kuch key indicators hain jo batate hain ki problem LIS type ho sakti hai:

1.  **Single Sequence/Array:** Problem mein generally ek hi array ya string di jaati hai (LCS ki tarah do nahi).
2.  **Order Preservation:** Aapko elements ko select karna hai, lekin unka original relative order maintain rakhna hai. Example: `[3,1,4]` mein `[3,4]` ek subsequence hai, lekin `[4,3]` nahi.
3.  **Optimization (Max/Min):** Goal usually "longest" (maximum length), "largest sum", "minimum deletions/insertions" hota hai.
4.  **Property Check:** Subsequence mein elements ki koi khaas property honi chahiye (e.g., increasing, decreasing, divisible by, alternating, etc.).
5.  **"Choose or Not Choose" Decision:** Har element ke liye, aapke paas do choices hoti hain: "is element ko subsequence mein include karein ya na karein". Agar include karte hain, toh kuch conditions satisfy honi chahiye (jaise previous element se bada hona).

-----

### Subsequence / LIS Type Questions Ko Approach Kaise Karein? Kya Pattern Hona Chahiye?

LIS problems ko solve karne ke liye do common approaches hain:

#### Approach 1: Dynamic Programming (Bottom-Up, $O(N^2)$ Time, $O(N)$ Space)

Yeh sabse common aur fundamental approach hai.

**1. DP State Definition:**

  * `dp[i]` = Length (ya sum, ya count, depending on problem) of the **longest/best subsequence ending at index `i`**.
  * Aap array `arr` ko iterate karte hain, aur har `arr[i]` ke liye, aap `dp[i]` ko calculate karte hain.

**2. Base Case:**

  * Har element khud mein ek subsequence of length 1 hai. So, initially `dp[i] = 1` for all `i`. (Agar sum hai toh `dp[i] = arr[i]`)

**3. Transitions (Recurrence Relation):**

`dp[i]` ko calculate karne ke liye, aap `arr[i]` se pehle ke saare elements `arr[j]` (jahan `j < i`) ko dekhte hain.

  * Iterate `i` from `0` to `N-1` (ya `1` to `N` agar 1-indexed)
  * Initialize `dp[i] = 1` (minimum, single element subsequence)
  * Iterate `j` from `0` to `i-1`:
      * **Condition Check:** Agar `arr[i]` ko `arr[j]` ke baad include kiya ja sakta hai (e.g., `arr[i] > arr[j]` for LIS, `arr[i] % arr[j] == 0` for Longest Divisible Subsequence, etc.).
      * **Update `dp[i]`:** `dp[i] = Math.max(dp[i], 1 + dp[j])` (Iska matlab, `arr[i]` ko `arr[j]` par end hone wale subsequence mein add kar sakte hain, jo us subsequence ki length ko `1` se badha dega).

**4. Final Answer:**

  * Saare `dp` values mein se **maximum value** hi final answer hogi. Kyuki LIS zaroori nahi ki array ke last element par end ho.

**Visualization (Example: LIS for `[10, 22, 9, 33, 21, 50]`):**

| Index (i) | `arr[i]` | Possible `arr[j]` (`j < i`) | Condition `arr[i] > arr[j]` | `1 + dp[j]` | `dp[i]` (Current Max) |
| :-------- | :------- | :-------------------------- | :-------------------------- | :---------- | :-------------------- |
| 0         | 10       | -                           | -                           | -           | 1                     |
| 1         | 22       | 10 (dp[0]=1)                | 22 \> 10 (True)              | 1+1=2       | 2                     |
| 2         | 9        | 10 (dp[0]=1)                | 9 \> 10 (False)              | -           | 1                     |
|           |          | 22 (dp[1]=2)                | 9 \> 22 (False)              | -           |                       |
| 3         | 33       | 10 (dp[0]=1)                | 33 \> 10 (True)              | 1+1=2       | 2                     |
|           |          | 22 (dp[1]=2)                | 33 \> 22 (True)              | 1+2=3       | 3                     |
|           |          | 9 (dp[2]=1)                 | 33 \> 9 (True)               | 1+1=2       |                       |
| 4         | 21       | 10 (dp[0]=1)                | 21 \> 10 (True)              | 1+1=2       | 2                     |
|           |          | 22 (dp[1]=2)                | 21 \> 22 (False)             | -           |                       |
|           |          | 9 (dp[2]=1)                 | 21 \> 9 (True)               | 1+1=2       |                       |
|           |          | 33 (dp[3]=3)                | 21 \> 33 (False)             | -           |                       |
| 5         | 50       | 10 (dp[0]=1)                | 50 \> 10 (True)              | 1+1=2       | 2                     |
|           |          | 22 (dp[1]=2)                | 50 \> 22 (True)              | 1+2=3       | 3                     |
|           |          | 9 (dp[2]=1)                 | 50 \> 9 (True)               | 1+1=2       |                       |
|           |          | 33 (dp[3]=3)                | 50 \> 33 (True)              | 1+3=4       | 4                     |
|           |          | 21 (dp[4]=2)                | 50 \> 21 (True)              | 1+2=3       |                       |

Final `dp` array: `[1, 2, 1, 3, 2, 4]`
Max value = 4.

**Complexity Analysis:**

  * **Time Complexity:** $O(N^2)$ due to nested loops.
  * **Space Complexity:** $O(N)$ for the `dp` array.

#### Approach 2: Dynamic Programming with Binary Search (Optimized for LIS, $O(N \\log N)$ Time, $O(N)$ Space)

Yeh LIS aur uske variations ko solve karne ka sabse efficient तरीका है. Yeh approach specifically LIS length nikalne ke liye hai, actual subsequence ko print karne ke liye nahi.

**Key Idea:**

Hum ek `tails` (ya `activeLists`) array maintain karte hain. `tails[k]` smallest ending element hai saare increasing subsequences ka जिनकी length `k+1` hai. `tails` array hamesha sorted rehta hai.

**Logic:**

1.  Initialize `tails` array (ya `ArrayList`) empty.
2.  Iterate `num` through the input `array`:
      * Find the **smallest element in `tails` that is `greater than or equal to` `num`** using binary search (`lower_bound` in C++, `Collections.binarySearch` in Java with custom logic, or manual binary search).
      * **If such an element is found:** Replace it with `num`. (Iska matlab, humne ek existing subsequence ko `num` se extend kiya, lekin ab woh `num` par end ho raha hai, jo is subsequence ko future mein extend karne ke liye better candidate banata hai kyuki `num` chhota hai).
      * **If no such element is found:** `num` `tails` ke saare elements se bada hai. Iska matlab `num` ek new longest increasing subsequence start kar sakta hai. `num` ko `tails` ke end mein add kar do. (Iska matlab, humne LIS ki length ko `1` se badha diya).

**3. Final Answer:**

  * `tails` array ki final size hi LIS ki length hogi.

**Visualization (Example: LIS for `[10, 22, 9, 33, 21, 50]`):**

| `num` | `tails` (before) | Binary Search (idx for `num`) | Action                 | `tails` (after)  | Current LIS Length (`tails.size()`) |
| :---- | :--------------- | :---------------------------- | :--------------------- | :--------------- | :-------------------------------- |
| 10    | `[]`             | (Not found, -1)               | Add 10                 | `[10]`           | 1                                 |
| 22    | `[10]`           | (Not found, -2)               | Add 22                 | `[10, 22]`       | 2                                 |
| 9     | `[10, 22]`       | 0 (for 10)                    | Replace 10 with 9      | `[9, 22]`        | 2                                 |
| 33    | `[9, 22]`        | (Not found, -3)               | Add 33                 | `[9, 22, 33]`    | 3                                 |
| 21    | `[9, 22, 33]`    | 1 (for 22)                    | Replace 22 with 21     | `[9, 21, 33]`    | 3                                 |
| 50    | `[9, 21, 33]`    | (Not found, -4)               | Add 50                 | `[9, 21, 33, 50]`| 4                                 |

Final `tails` array: `[9, 21, 33, 50]`. Length = 4.
**Note:** `tails` array khud LIS nahi hai, yeh sirf LIS ki length track karta hai.

**Complexity Analysis:**

  * **Time Complexity:** $O(N \\log N)$ (N iterations, each involving a `log N` binary search).
  * **Space Complexity:** $O(N)$ for the `tails` array.

-----

### Key Pattern and Generalization for LIS Type Questions:

1.  **Ek array/sequence par operation:** Generally, `dp[i]` ka matlab `array[i]` par end hone wale subsequence ka best result hota hai.
2.  **Inner loop for previous elements:** `for (int j = 0; j < i; j++)` is a common pattern to check all previous possible elements that could extend the subsequence.
3.  **Condition based on problem:** `if (arr[i] > arr[j])` for LIS, `if (arr[i] < arr[j])` for LDS, `if (arr[i] % arr[j] == 0)` for Divisible Subsequence, etc.
4.  **`1 + dp[j]` update:** Har valid `j` ke liye, `dp[i]` ko `1 + dp[j]` se update karna (`max` ya `min` ke saath).
5.  **Final result is global max/min:** DP table ke saare elements mein se max/min lena.

-----

### Example: Longest Increasing Subsequence (LIS)

**Problem:**

Aapko ek integer array `nums` diya gaya hai, return the length of the longest strictly increasing subsequence.

**Input:**

  * `nums = [10,9,2,5,3,7,101,18]`

**Output:** 4
**Explanation:** The longest increasing subsequence is `[2,3,7,101]` (or `[2,5,7,101]`, `[2,5,7,18]` etc), hence length is 4.

-----

#### Solution: LIS ($O(N^2)$ DP Approach)

```java
import java.util.Arrays;

public class LongestIncreasingSubsequenceN2 {

    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        // dp[i] stores the length of the longest increasing subsequence ending at index i.
        int[] dp = new int[n];

        // Initialize each element's LIS length to 1 (the element itself forms a subsequence of length 1).
        Arrays.fill(dp, 1);

        int maxLength = 1; // Stores the overall maximum LIS length found so far.

        // Iterate through the array starting from the second element.
        // For each element nums[i], consider all elements before it (nums[j] where j < i).
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If nums[i] is greater than nums[j], it means nums[i] can extend the LIS ending at nums[j].
                // We update dp[i] to be the maximum of its current value and (1 + dp[j]).
                // 1 represents including nums[i], and dp[j] is the LIS length ending at nums[j].
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            // Update the overall maximum LIS length.
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequenceN2 solver = new LongestIncreasingSubsequenceN2();

        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("LIS length for " + Arrays.toString(nums1) + ": " + solver.lengthOfLIS(nums1)); // Expected: 4

        int[] nums2 = {0, 1, 0, 3, 2, 3};
        System.out.println("LIS length for " + Arrays.toString(nums2) + ": " + solver.lengthOfLIS(nums2)); // Expected: 4

        int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
        System.out.println("LIS length for " + Arrays.toString(nums3) + ": " + solver.lengthOfLIS(nums3)); // Expected: 1

        int[] nums4 = {1, 3, 6, 7, 9, 4, 10, 5, 6};
        System.out.println("LIS length for " + Arrays.toString(nums4) + ": " + solver.lengthOfLIS(nums4)); // Expected: 6 (1,3,6,7,9,10 or 1,3,4,5,6 etc)
    }
}
```

-----

#### Solution: LIS ($O(N \\log N)$ DP with Binary Search Approach)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LongestIncreasingSubsequenceNLogN {

    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 'tails' list will store the smallest end element of an increasing subsequence
        // of a specific length. tails[i] stores the smallest end element of an IS of length (i+1).
        // It maintains increasing order.
        List<Integer> tails = new ArrayList<>();

        for (int num : nums) {
            // Find the index 'i' in 'tails' where 'num' can replace the current element
            // to maintain the increasing order and potentially form a new or shorter
            // ending for an existing subsequence.
            // Collections.binarySearch returns:
            //   - index if element found
            //   - (-(insertion point) - 1) if element not found.
            //     The insertion point is where the element would be inserted to maintain order.
            int i = Collections.binarySearch(tails, num);

            // If 'num' is found, it means an identical value already exists or can replace itself,
            // we don't need to do anything as it doesn't extend the length or improve a shorter IS.
            // However, the problem states "strictly increasing". If we want to strictly enforce it,
            // we should find the 'first element strictly greater than num'.
            // In Collections.binarySearch, if 'num' is present, 'i' will be its index.
            // If we want 'num' to be strictly greater, we'd look for index of first element >= num and
            // if tails[i] == num, we advance i.
            // For LIS strictly increasing: We need to find the first element in tails that is >= num.
            // If such element exists, replace it. Otherwise, add num to the end.
            // The binarySearch result 'i' is exactly what we need for this.
            // If num is found, i is its index.
            // If num is not found, -(insertion_point) - 1. The insertion_point is the index where num would be inserted.
            // So, for num, we want to find the first element in tails >= num.
            // This is effectively `lower_bound` in C++ STL.
            // If `i < 0`, `i` is `-(insertion point) - 1`.
            // So, `insertion_point = -i - 1`.
            int insertionPoint = (i < 0) ? (-i - 1) : i;

            if (insertionPoint == tails.size()) {
                // If 'num' is greater than all elements in 'tails',
                // it extends the longest increasing subsequence found so far.
                tails.add(num);
            } else {
                // Otherwise, 'num' can replace the element at 'insertionPoint'.
                // This implies 'num' can form an increasing subsequence of the same length
                // but ending with a smaller value, which is better for future extensions.
                tails.set(insertionPoint, num);
            }
        }

        // The size of the 'tails' list is the length of the longest increasing subsequence.
        return tails.size();
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequenceNLogN solver = new LongestIncreasingSubsequenceNLogN();

        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("LIS length for " + Arrays.toString(nums1) + ": " + solver.lengthOfLIS(nums1)); // Expected: 4

        int[] nums2 = {0, 1, 0, 3, 2, 3};
        System.out.println("LIS length for " + Arrays.toString(nums2) + ": " + solver.lengthOfLIS(nums2)); // Expected: 4

        int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
        System.out.println("LIS length for " + Arrays.toString(nums3) + ": " + solver.lengthOfLIS(nums3)); // Expected: 1 (Strictly increasing)

        int[] nums4 = {1, 3, 6, 7, 9, 4, 10, 5, 6};
        System.out.println("LIS length for " + Arrays.toString(nums4) + ": " + solver.lengthOfLIS(nums4)); // Expected: 6
    }
}
```

-----

### Other LIS Type Problems (Variations):

1.  **Longest Decreasing Subsequence (LDS):** LIS jaisa hi hai, bas condition `if (nums[i] < nums[j])` ho jaegi. `N log N` mein bhi `tails` array reversed order mein ya binary search `upper_bound` use karke kar sakte hain.
2.  **Longest Bitonic Subsequence (LBS):** LBS ek subsequence hai jo pehle increasing hoti hai aur phir decreasing. Iske liye do DP arrays lagte hain: `LIS_from_left[i]` aur `LDS_from_right[i]`. `LBS = max(LIS_from_left[i] + LDS_from_right[i] - 1)`.
3.  **Maximum Sum Increasing Subsequence (MSIS):** LIS length ki jagah sum dhundhna. `dp[i]` = Maximum sum increasing subsequence ending at `i`. Update: `dp[i] = Math.max(dp[i], arr[i] + dp[j])` (where `dp[i]` initially `arr[i]`).
4.  **Number of Longest Increasing Subsequences:** LIS length ke saath, unki count bhi track karni padti hai. Har `dp[i]` ke saath `count[i]` bhi store karte hain.
5.  **Longest Divisible Subsequence:** Condition `if (arr[i] % arr[j] == 0)`.
6.  **Box Stacking Problem:** Boxes ko stack karna hai (heights, widths, depths). Ek box doosre ke upar tabhi aa sakta hai jab woh usse chhota ho. Boxes ko rotate bhi kar sakte hain. Saari possible rotations ko generate karke, unhe sort karke, LIS jaisa apply karte hain.
7.  **Russian Doll Envelopes (LeetCode 354):** Similar to Box Stacking. Envelopes ko nesting karna hai (width, height). Iske liye pehle width ke basis par sort karte hain, aur agar widths same hain toh height ke basis par decreasing order mein, phir heights par LIS lagate hain.

-----

**Summary Pattern:**

  * **Pehchan:** Single sequence, relative order, max/min optimization, property based subsequence.
  * **DP State:** `dp[i]` = best result ending at `array[i]`.
  * **Base Case:** `dp[i]` initialized to single element result.
  * **Transition ($N^2$):** Nested loop, inner loop `j < i`, check condition, `dp[i] = combine(dp[i], new_val_from_dp[j])`.
  * **Transition ($N \\log N$):** `tails` array, binary search, replace or append.
  * **Final Answer:** Max/Min value from `dp` array (for $N^2$), or size of `tails` array (for $N \\log N$).

-----

### 1\. Longest Decreasing Subsequence (LDS)

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko uski **longest strictly decreasing subsequence** ki length return karni hai.

**Example:**

  * `nums = [10, 22, 9, 33, 21, 50, 41, 60]`
  * **Output:** 3 (LDS can be `[22, 21, 9]` or `[50, 41]` etc. Max is 3)

-----

#### Approach: Longest Decreasing Subsequence

LDS, LIS ka mirror image hai. Isko solve karne ke do main tarike hain:

1.  **$O(N^2)$ DP Approach:** Bilkul LIS jaisa hi, bas condition `nums[i] < nums[j]` ho jaegi.

      * **DP State:** `dp[i]` = Length of the longest decreasing subsequence ending at index `i`.
      * **Base Case:** `dp[i] = 1` for all `i`.
      * **Transition:** For each `i` from `1` to `N-1`, iterate `j` from `0` to `i-1`.
          * `if (nums[i] < nums[j])`: `dp[i] = Math.max(dp[i], 1 + dp[j])`
      * **Result:** `max(dp)` array.

2.  **$O(N \\log N)$ DP with Binary Search Approach:** Ismein `tails` array ka use hota hai, lekin `LIS` se ulta.

      * **Logic:** `tails[k]` smallest ending element of all decreasing subsequences of length `k+1`. Isko maintain karne ke liye, jab aap `num` ko process karte hain:
          * Agar `num` current `tails` array ke **saare elements se bada** hai (yaani, `num` kisi decreasing subsequence ko extend nahi kar sakta agar `tails` mein already sorted decreasing values hain), toh `num` ko `tails` mein **pahle element** se replace karo jo `num` se chhota hai, taki hum ek lambi decreasing sequence bana sake.
          * **Better Alternative:** Problem ko **Longest Increasing Subsequence of the negated array** mein convert kar do, ya **Longest Increasing Subsequence of the array in reverse order**.
          * **Most Common for LDS (N log N):** Find the **first element in `tails` that is `greater than` `num`**.
              * If found, replace it with `num`.
              * If not found (meaning `num` is smaller than or equal to all elements in `tails`), append `num` to `tails`. This means `tails` will store elements in **decreasing order**.
              * Binary search for `upper_bound` (smallest element strictly greater than `num`). If found, replace. If not found, add to end.
      * **Simpler Alternative for N log N:** Input array `nums` ko reverse kar do (`reverse_nums`). Ab `reverse_nums` par **LIS** (Longest Increasing Subsequence) apply karo. Iska result hi original array ka LDS hoga. (Example: `[3,2,1]` ka LDS `[3,2,1]` hai. `[1,2,3]` ka LIS `[1,2,3]` hai.)

-----

#### Solution: Longest Decreasing Subsequence (Java Code - using $O(N^2)$ DP)

```java
import java.util.Arrays;

public class LongestDecreasingSubsequence {

    public int lengthOfLDS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element itself is a decreasing subsequence of length 1.

        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If nums[i] is strictly less than nums[j],
                // then nums[i] can extend the LDS ending at nums[j].
                if (nums[i] < nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestDecreasingSubsequence solver = new LongestDecreasingSubsequence();

        int[] nums1 = {10, 22, 9, 33, 21, 50, 41, 60};
        System.out.println("LDS length for " + Arrays.toString(nums1) + ": " + solver.lengthOfLDS(nums1)); // Expected: 3 (e.g., [22, 21, 9])

        int[] nums2 = {3, 2, 1};
        System.out.println("LDS length for " + Arrays.toString(nums2) + ": " + solver.lengthOfLDS(nums2)); // Expected: 3

        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("LDS length for " + Arrays.toString(nums3) + ": " + solver.lengthOfLDS(nums3)); // Expected: 1
    }
}
```

-----

### 2\. Longest Bitonic Subsequence (LBS)

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko uski **longest bitonic subsequence** ki length return karni hai. Ek bitonic subsequence pehle strictly increasing hoti hai aur phir strictly decreasing hoti hai. Ek strictly increasing ya strictly decreasing subsequence bhi bitonic ho sakti hai.

**Example:**

  * `nums = [1, 11, 2, 10, 4, 5, 2, 1]`
  * **Output:** 6 (LBS can be `[1, 2, 10, 4, 2, 1]` or `[1, 2, 4, 5, 2, 1]`)

-----

#### Approach: Longest Bitonic Subsequence

LBS ko solve karne ke liye, hum LIS aur LDS ke concepts ko combine karte hain.
Har element `nums[i]` ko **peak** maan kar dekhte hain. Agar `nums[i]` peak hai, toh LBS us `nums[i]` tak left se increasing subsequence aur right se decreasing subsequence ka sum hoga.

**Logic:**

1.  **Calculate LIS ending at each index:**

      * Create `lis[n]` array. `lis[i]` = length of LIS ending at `nums[i]`.
      * Calculate this using the standard $O(N^2)$ LIS DP approach (from left to right).

2.  **Calculate LDS starting at each index (or LIS on reversed array):**

      * Create `lds[n]` array. `lds[i]` = length of LDS starting at `nums[i]`.
      * Iske liye, array ko **right to left** iterate karo, aur standard LIS logic apply karo (ya `nums` array ko reverse karke LIS apply kar do).
          * For `i` from `N-1` down to `0`:
          * For `j` from `N-1` down to `i+1`:
              * `if (nums[i] > nums[j])`: `lds[i] = Math.max(lds[i], 1 + lds[j])`

3.  **Combine LIS and LDS:**

      * Iterate `i` from `0` to `N-1`.
      * Har `i` par, `lis[i]` aur `lds[i]` ko add karo.
      * **Important:** `nums[i]` ko humne `lis[i]` aur `lds[i]` dono mein count kiya hai. So, `nums[i]` ko ek baar subtract karna padega.
      * `LBS_at_i = lis[i] + lds[i] - 1`
      * Maximum `LBS_at_i` across all `i` will be the final answer.

**Constraints:** `N` up to $10^5$, toh $N^2$ solution kaam nahi karega. Iske liye $N \\log N$ LIS/LDS ka use karna padega. Lekin basic approach $N^2$ hai.

-----

#### Solution: Longest Bitonic Subsequence (Java Code - $O(N^2)$ DP)

```java
import java.util.Arrays;

public class LongestBitonicSubsequence {

    public int longestBitonicSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) { // A single element is also bitonic (either increasing or decreasing)
            return 1;
        }

        int n = nums.length;
        int[] lis = new int[n]; // LIS ending at index i
        int[] lds = new int[n]; // LDS starting at index i

        // Step 1: Calculate LIS for each element (from left to right)
        Arrays.fill(lis, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    lis[i] = Math.max(lis[i], 1 + lis[j]);
                }
            }
        }

        // Step 2: Calculate LDS for each element (from right to left)
        Arrays.fill(lds, 1);
        for (int i = n - 2; i >= 0; i--) { // Start from second last element
            for (int j = n - 1; j > i; j--) { // Compare with elements to its right
                if (nums[i] > nums[j]) {
                    lds[i] = Math.max(lds[i], 1 + lds[j]);
                }
            }
        }

        // Step 3: Find the maximum LBS by combining LIS and LDS
        int maxLBS = 0;
        for (int i = 0; i < n; i++) {
            // A single element is counted in both LIS and LDS at index i.
            // So, subtract 1 to avoid double counting the peak element.
            // A bitonic sequence must have at least one increasing and one decreasing part.
            // If lis[i] is 1, it means it's purely decreasing from that point.
            // If lds[i] is 1, it means it's purely increasing to that point.
            // We need a sequence with at least 2 distinct elements to form a 'peak'.
            // If lis[i] or lds[i] is 1, it might be a purely increasing or purely decreasing sequence.
            // The problem statement usually implies at least 3 elements or at least 1 peak.
            // If the question says "strictly increasing THEN strictly decreasing"
            // it means it must have both parts (length > 1 for increasing and > 1 for decreasing).
            // But if it says "can be purely increasing or purely decreasing", then min length can be 1.
            // Given example has elements >= 1.
            maxLBS = Math.max(maxLBS, lis[i] + lds[i] - 1);
        }

        return maxLBS;
    }

    public static void main(String[] args) {
        LongestBitonicSubsequence solver = new LongestBitonicSubsequence();

        int[] nums1 = {1, 11, 2, 10, 4, 5, 2, 1};
        System.out.println("LBS length for " + Arrays.toString(nums1) + ": " + solver.longestBitonicSubsequence(nums1)); // Expected: 6 ([1, 2, 10, 4, 2, 1] or [1, 2, 4, 5, 2, 1])

        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("LBS length for " + Arrays.toString(nums2) + ": " + solver.longestBitonicSubsequence(nums2)); // Expected: 5 (purely increasing)

        int[] nums3 = {5, 4, 3, 2, 1};
        System.out.println("LBS length for " + Arrays.toString(nums3) + ": " + solver.longestBitonicSubsequence(nums3)); // Expected: 5 (purely decreasing)

        int[] nums4 = {1, 2, 1};
        System.out.println("LBS length for " + Arrays.toString(nums4) + ": " + solver.longestBitonicSubsequence(nums4)); // Expected: 3
    }
}
```

-----

### 3\. Maximum Sum Increasing Subsequence (MSIS)

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko uski **maximum sum increasing subsequence** ka sum return karna hai.

**Example:**

  * `nums = [1, 101, 2, 3, 100, 4, 5]`
  * **Output:** 106
  * **Explanation:** MSIS is `[1, 2, 3, 100]` with sum 106. (`[1, 2, 3, 4, 5]` sum 15, `[1, 101]` sum 102)

-----

#### Approach: Maximum Sum Increasing Subsequence

MSIS bilkul LIS length nikalne jaisa hi hai, bas `dp[i]` ab length ki jagah sum store karega.

**DP State Definition:**

  * `dp[i]` = Maximum sum of an increasing subsequence ending at `nums[i]`.

**Base Case:**

  * `dp[i] = nums[i]` for all `i`. (Har element khud mein ek increasing subsequence hai jiska sum wahi element hai).

**Transitions (Recurrence Relation):**

Loop `i` from `0` to `N-1`.
Initialize `dp[i] = nums[i]`.
Loop `j` from `0` to `i-1`:

  * `if (nums[i] > nums[j])`:
      * `dp[i] = Math.max(dp[i], nums[i] + dp[j])` (Iska matlab, `nums[i]` ko `nums[j]` par end hone wale subsequence mein add karke sum increase kar sakte hain).

**Final Answer:**

`dp` array mein se **maximum value**.

-----

#### Solution: Maximum Sum Increasing Subsequence (Java Code)

```java
import java.util.Arrays;

public class MaximumSumIncreasingSubsequence {

    public int maxSumIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        // dp[i] stores the maximum sum of an increasing subsequence ending at index i.
        int[] dp = new int[n];

        // Initialize dp[i] with nums[i] because each element itself is an increasing subsequence
        // with a sum equal to its value.
        for (int i = 0; i < n; i++) {
            dp[i] = nums[i];
        }

        int maxSum = nums[0]; // Stores the overall maximum sum found so far.

        // Iterate through the array starting from the second element.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If nums[i] is greater than nums[j], it means nums[i] can extend the
                // increasing subsequence ending at nums[j].
                // We update dp[i] with the maximum of its current value and (nums[i] + dp[j]).
                // nums[i] is added because it's the current element being included.
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], nums[i] + dp[j]);
                }
            }
            // Update the overall maximum sum.
            maxSum = Math.max(maxSum, dp[i]);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        MaximumSumIncreasingSubsequence solver = new MaximumSumIncreasingSubsequence();

        int[] nums1 = {1, 101, 2, 3, 100, 4, 5};
        System.out.println("MSIS for " + Arrays.toString(nums1) + ": " + solver.maxSumIS(nums1)); // Expected: 106 ([1, 2, 3, 100])

        int[] nums2 = {3, 4, 5, 10};
        System.out.println("MSIS for " + Arrays.toString(nums2) + ": " + solver.maxSumIS(nums2)); // Expected: 22 (3+4+5+10)

        int[] nums3 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("MSIS for " + Arrays.toString(nums3) + ": " + solver.maxSumIS(nums3)); // Expected: 121 (2+3+7+101)
    }
}
```

-----

### 4\. Number of Longest Increasing Subsequences

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko **number of longest increasing subsequences** return karna hai.

**Example:**

  * `nums = [1, 3, 5, 4, 7]`
  * **Output:** 2
  * **Explanation:**
      * LIS length = 4.
      * LIS are: `[1, 3, 4, 7]` and `[1, 3, 5, 7]`.

-----

#### Approach: Number of Longest Increasing Subsequences

Is problem mein, humein sirf LIS ki length nahi, balki un LIS ki count bhi track karni padti hai. Iske liye, hum do DP arrays maintain karte hain.

**DP State Definitions:**

1.  `dp_len[i]` = Length of the longest increasing subsequence ending at `nums[i]`. (Same as standard LIS $O(N^2)$ DP).
2.  `dp_count[i]` = Number of longest increasing subsequences ending at `nums[i]`.

**Base Case:**

  * `dp_len[i] = 1` for all `i`.
  * `dp_count[i] = 1` for all `i`. (Each element itself forms one LIS of length 1).

**Transitions (Recurrence Relation):**

Loop `i` from `0` to `N-1`.
Initialize `dp_len[i] = 1`, `dp_count[i] = 1`.
Loop `j` from `0` to `i-1`:

  * `if (nums[i] > nums[j])`:
      * **Case 1: Found a longer LIS:** `if (dp_len[j] + 1 > dp_len[i])`
          * `dp_len[i] = dp_len[j] + 1`
          * `dp_count[i] = dp_count[j]` (New LIS found, so reset count to previous LIS count)
      * **Case 2: Found another LIS of the same length:** `else if (dp_len[j] + 1 == dp_len[i])`
          * `dp_count[i] += dp_count[j]` (Add counts from previous LIS of same length)

**Final Answer:**

1.  Pehle, calculate `overall_max_len`, which is the maximum value in `dp_len` array.
2.  Phir, `dp_count` array ko iterate karo. Jo bhi `dp_count[i]` ki value `overall_max_len` ke barabar hai, un sabhi `dp_count[i]` ko sum kar do. That sum is the final answer.

-----

#### Solution: Number of Longest Increasing Subsequences (Java Code)

```java
import java.util.Arrays;

public class NumberOfLIS {

    public int findNumberOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] dp_len = new int[n];   // dp_len[i] = length of LIS ending at nums[i]
        int[] dp_count = new int[n]; // dp_count[i] = number of LIS ending at nums[i]

        // Initialize: each element is an LIS of length 1, and there's 1 such LIS.
        Arrays.fill(dp_len, 1);
        Arrays.fill(dp_count, 1);

        int overall_max_len = 0; // Will store the maximum LIS length found so far
        if (n > 0) { // If nums is not empty, min LIS length is 1
            overall_max_len = 1;
        }
        
        // Fill DP tables
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    // Case 1: Found a longer LIS ending at nums[i]
                    if (dp_len[j] + 1 > dp_len[i]) {
                        dp_len[i] = dp_len[j] + 1;
                        dp_count[i] = dp_count[j]; // Reset count as we found a new longer path
                    }
                    // Case 2: Found another LIS of the same length ending at nums[i]
                    else if (dp_len[j] + 1 == dp_len[i]) {
                        dp_count[i] += dp_count[j]; // Add counts from the equally long path
                    }
                }
            }
            // Update the overall maximum length
            overall_max_len = Math.max(overall_max_len, dp_len[i]);
        }

        // Calculate the total number of LIS
        int total_lis_count = 0;
        for (int i = 0; i < n; i++) {
            if (dp_len[i] == overall_max_len) {
                total_lis_count += dp_count[i];
            }
        }

        return total_lis_count;
    }

    public static void main(String[] args) {
        NumberOfLIS solver = new NumberOfLIS();

        int[] nums1 = {1, 3, 5, 4, 7};
        System.out.println("Number of LIS for " + Arrays.toString(nums1) + ": " + solver.findNumberOfLIS(nums1)); // Expected: 2

        int[] nums2 = {2, 2, 2, 2, 2};
        System.out.println("Number of LIS for " + Arrays.toString(nums2) + ": " + solver.findNumberOfLIS(nums2)); // Expected: 5 (Each single element is an LIS of length 1)

        int[] nums3 = {1, 2, 4, 3, 5, 4, 7, 2};
        System.out.println("Number of LIS for " + Arrays.toString(nums3) + ": " + solver.findNumberOfLIS(nums3)); // Expected: 3 ([1,2,3,4,7], [1,2,4,4,7] no, [1,2,4,5,7])
                                                                                                                   // LIS length is 5.
                                                                                                                   // [1,2,3,4,7]
                                                                                                                   // [1,2,4,5,7]
                                                                                                                   // Count is 2. (Oops, for this specific input, the output is 2 based on LeetCode)
                                                                                                                   // LIS: [1,2,3,4,7] and [1,2,4,5,7]
    }
}
```

-----

### 5\. Longest Divisible Subsequence

**Problem Statement:**

Aapko ek integer array `nums` diya gaya hai. Aapko uski **longest subsequence** ki length return karni hai jismein har adjacent pair `(nums[i], nums[j])` (jahan `i < j`) ke liye `nums[j] % nums[i] == 0` ho, yaani `nums[j]` `nums[i]` se divisible ho.

**Example:**

  * `nums = [1, 2, 3, 4, 6, 9, 12]`
  * **Output:** 4 (LDS can be `[1, 2, 4, 12]` or `[1, 3, 6, 12]`)

-----

#### Approach: Longest Divisible Subsequence

Yeh LIS jaisa hi hai, bas increasing condition ki jagah divisible condition use hoti hai.
**Important:** Solutions ko simplify karne ke liye, **input array ko pehle sort karna behtar hota hai**. Agar array sorted nahi hai, toh `nums[j] % nums[i] == 0` aur `nums[i] % nums[j] == 0` dono cases dekhne padenge, jo complexity badha dega. Sorted array mein, `nums[j] >= nums[i]` hoga, toh sirf `nums[j] % nums[i] == 0` check karna kaafi hai.

**DP State Definition:**

  * `dp[i]` = Length of the longest divisible subsequence ending at `nums[i]`.

**Base Case:**

  * `dp[i] = 1` for all `i`.

**Steps:**

1.  **Sort the input array `nums`** in ascending order. This simplifies the divisibility check (we only need to check `nums[i] % nums[j] == 0` for `j < i`).
2.  Initialize `dp[i] = 1` for all `i`.
3.  Loop `i` from `0` to `N-1`.
4.  Loop `j` from `0` to `i-1`:
      * **Condition Check:** `if (nums[i] % nums[j] == 0)`:
          * `dp[i] = Math.max(dp[i], 1 + dp[j])`

**Final Answer:**

`max(dp)` array.

-----

#### Solution: Longest Divisible Subsequence (Java Code)

```java
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; // For actual list, but problem asks for length.

public class LongestDivisibleSubsequence {

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return new ArrayList<>();
        }

        // Step 1: Sort the array. This is crucial.
        // After sorting, if nums[i] % nums[j] == 0 for j < i, it simplifies the logic.
        Arrays.sort(nums);

        // dp[i] stores the length of the largest divisible subset ending with nums[i].
        int[] dp = new int[n];
        // parent[i] stores the index of the previous element in the largest divisible subset ending with nums[i].
        // This is used to reconstruct the subset.
        int[] parent = new int[n];

        Arrays.fill(dp, 1); // Each element itself forms a divisible subset of length 1.
        
        // Track the index of the last element of the overall largest subset.
        int maxLen = 1;
        int maxIdx = 0;

        // Step 2: Fill DP tables (similar to LIS)
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Initialize parent to self (no previous element yet)
            for (int j = 0; j < i; j++) {
                // If nums[i] is divisible by nums[j]
                if (nums[i] % nums[j] == 0) {
                    // If taking nums[j] results in a longer subset
                    if (1 + dp[j] > dp[i]) {
                        dp[i] = 1 + dp[j];
                        parent[i] = j; // Set j as the parent of i
                    }
                }
            }
            // Update maxLen and maxIdx if a longer subset is found
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIdx = i;
            }
        }

        // Step 3: Reconstruct the longest divisible subset using the parent array
        List<Integer> result = new ArrayList<>();
        int curr = maxIdx;
        while (parent[curr] != curr) { // While not reached the start of the subset
            result.add(0, nums[curr]); // Add to the beginning to maintain order
            curr = parent[curr];
        }
        result.add(0, nums[curr]); // Add the first element

        // Problem asks for length, but solution gives list for completeness
        // If only length is needed, return maxLen
        // return maxLen;
        return result;
    }

    public static void main(String[] args) {
        LongestDivisibleSubsequence solver = new LongestDivisibleSubsequence();

        int[] nums1 = {1, 2, 3, 4, 6, 9, 12};
        System.out.println("LDS for " + Arrays.toString(nums1) + ": " + solver.largestDivisibleSubset(nums1)); // Expected: [1, 2, 4, 12] or [1, 3, 6, 12] (length 4)

        int[] nums2 = {3, 4, 16, 8}; // Sorted: [3, 4, 8, 16]
        System.out.println("LDS for " + Arrays.toString(nums2) + ": " + solver.largestDivisibleSubset(nums2)); // Expected: [4, 8, 16] (length 3)

        int[] nums3 = {1};
        System.out.println("LDS for " + Arrays.toString(nums3) + ": " + solver.largestDivisibleSubset(nums3)); // Expected: [1] (length 1)
    }
}
```

-----

### 6\. Box Stacking Problem

**Problem Statement:**

Aapko `n` boxes diye gaye hain. Har box ki teen dimensions (height `h`, width `w`, depth `d`) hain. Aapko boxes ko stack karna hai, jismein har box apne neeche wale box se strictly smaller dimensions (width, depth) mein hona chahiye. Aap boxes ko rotate kar sakte hain (yani `(h, w, d)` ko `(w, h, d)`, `(d, h, w)` etc. mein badal sakte hain). Goal hai **maximum possible total height** of the stack.

**Example:**

  * **Input:** `boxes = {{1,2,3}, {4,5,6}, {3,4,5}}`
  * **Output:** (Depends on the exact numbers, but the idea is to maximize height)

-----

#### Approach: Box Stacking Problem

Yeh problem LIS ka ek classic variation hai, jahan items ko define karna aur sort karna sabse challenging part hai.

**Logic:**

1.  **Generate all Rotations:** Har box ke liye 3 possible orientations generate karo:

      * `(h, w, d)`
      * `(w, h, d)` (if `w != h`)
      * `(d, h, w)` (if `d != h` and `d != w`)
      * **Convention:** To simplify comparison, har orientation mein `width` aur `depth` ko sort kar lo taki `width <= depth` rahe. Example: If dimensions are `(A, B, C)`, we'll get `(A, min(B,C), max(B,C))`, `(B, min(A,C), max(A,C))`, `(C, min(A,B), max(A,B))`. This ensures consistent comparison for width and depth.

2.  **Sort the Generated Boxes:** Saari generated box orientations ko **decreasing order of base area** (width \* depth) ya **decreasing order of width** (and if width same, then decreasing order of depth) mein sort karo. This is critical.

      * Reason: Jab hum LIS jaisa DP apply karenge, hum chahenge ki `nums[i]` ke aage `nums[j]` ka comparison correct ho. Agar base area bada hai, toh woh stack mein neeche aega.
      * **Sorting Criteria:** Sort by `width * depth` in **descending** order. If areas are equal, sort by `width` in **descending** order. This handles cases where a smaller box has a larger area (unlikely for strict stacking, but good for robustness). Or simplest, sort by **width in descending order**, then by **depth in descending order**. This ensures that for a current box `i`, any `j < i` has a width `>=` current box `i`'s width.
      * A more robust way for sorting is to sort by `width` descending, and if widths are equal, then by `depth` descending. This might not be intuitive but is used in Russian Doll Envelopes and similar problems.
      * **Common Method:** Sort all possible box orientations by `width` in descending order, then by `depth` in descending order.

3.  **Apply LIS-like DP:**

      * Define `dp[i]` = Maximum height of a stack whose top box is `box_orientations[i]`.
      * **Base Case:** `dp[i] = box_orientations[i].height` for all `i`.
      * **Transitions:** Loop `i` from `0` to `N_rotations-1`.
          * Loop `j` from `0` to `i-1`.
          * **Condition:** If `box_orientations[i].width < box_orientations[j].width` AND `box_orientations[i].depth < box_orientations[j].depth` (i.e., `box_orientations[i]` can be placed on top of `box_orientations[j]`).
              * `dp[i] = Math.max(dp[i], box_orientations[i].height + dp[j])`

4.  **Final Answer:** `max(dp)` array.

-----

#### Solution: Box Stacking Problem (Java Code - Conceptual, detailed implementation would be lengthy)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

// Helper class to represent a box's orientation
class Box {
    int h, w, d; // height, width, depth

    public Box(int h, int w, int d) {
        this.h = h;
        this.w = w;
        this.d = d;
    }

    // For sorting, typically sort by base area descending, then width descending
    // Or in Russian Doll style: sort by width descending, then depth descending
    // Here, we sort by base area (w*d) descending to prioritize larger bases at the bottom
    // If base areas are equal, sort by width descending (or any consistent tie-breaker)
    // The key is that a box below must have larger w and d.
    // So if we iterate through sorted boxes, we can always place box[i] on box[j] if box[i].w < box[j].w && box[i].d < box[j].d
    // For sorting, we need a comparator. Let's make width as primary sort key for simplicity (descending), then depth (descending)
    // This allows LIS on height to find optimal stack.
    // The "standard" sort for this problem is by base area in DESCENDING order (w*d).
    // If base areas are equal, sort by width in DESCENDING order.
    // This ensures that when we iterate, a box comes before another if it can be placed below it.
    // Let's implement that common sorting for Box Stacking:
    // Sort by base area (w*d) in descending order. If areas are equal, sort by width in descending order.
}

public class BoxStacking {

    public int maxHeight(int[][] boxes) {
        // Step 1: Generate all 3 rotations for each box and ensure w <= d for consistency.
        // Also, store them as custom Box objects.
        List<Box> allRotations = new ArrayList<>();
        for (int[] box : boxes) {
            // Original: (h, w, d)
            // Ensure w <= d for consistent comparison later
            allRotations.add(new Box(box[0], Math.min(box[1], box[2]), Math.max(box[1], box[2])));
            
            // Rotation 1: (w, h, d)
            allRotations.add(new Box(box[1], Math.min(box[0], box[2]), Math.max(box[0], box[2])));
            
            // Rotation 2: (d, h, w)
            allRotations.add(new Box(box[2], Math.min(box[0], box[1]), Math.max(box[0], box[1])));
        }

        // Step 2: Sort all rotations.
        // Sort in decreasing order of base area (w*d). If areas are equal, sort by w in descending order.
        Collections.sort(allRotations, (b1, b2) -> {
            int area1 = b1.w * b1.d;
            int area2 = b2.w * b2.d;
            if (area1 != area2) {
                return Integer.compare(area2, area1); // Descending area
            }
            return Integer.compare(b2.w, b1.w); // Descending width (tie-breaker)
        });

        int n = allRotations.size();
        // dp[i] stores the maximum height of a stack ending with allRotations.get(i) as the top box.
        int[] dp = new int[n];

        int overallMaxHeight = 0;

        // Step 3: Apply LIS-like DP
        for (int i = 0; i < n; i++) {
            Box currentBox = allRotations.get(i);
            dp[i] = currentBox.h; // A single box forms a stack of its own height

            for (int j = 0; j < i; j++) {
                Box prevBox = allRotations.get(j);

                // Condition for stacking: currentBox can be placed on top of prevBox
                // if currentBox's width and depth are strictly smaller than prevBox's.
                // Because we sorted by area, prevBox will always have area >= currentBox's area.
                // This specific check ensures the actual fit for stacking.
                if (currentBox.w < prevBox.w && currentBox.d < prevBox.d) {
                    dp[i] = Math.max(dp[i], currentBox.h + dp[j]);
                }
            }
            overallMaxHeight = Math.max(overallMaxHeight, dp[i]);
        }

        return overallMaxHeight;
    }

    public static void main(String[] args) {
        BoxStacking solver = new BoxStacking();

        // Example: {height, width, depth}
        int[][] boxes1 = {{4, 6, 7}, {1, 2, 3}, {4, 5, 6}, {10, 12, 32}};
        // Expected output would require manual calculation after rotations and sorting.
        // This problem is a bit complex for a quick manual trace.
        System.out.println("Max height for boxes " + Arrays.deepToString(boxes1) + ": " + solver.maxHeight(boxes1)); // A complex example

        int[][] boxes2 = {{1, 1, 1}, {2, 2, 2}};
        System.out.println("Max height for boxes " + Arrays.deepToString(boxes2) + ": " + solver.maxHeight(boxes2)); // Expected: 3 (1+2)
    }
}
```

-----

### 7\. Russian Doll Envelopes (LeetCode 354)

**Problem Statement:**

Aapko `n` envelopes diye gaye hain, jahan `envelopes[i] = [width_i, height_i]` hai. Ek envelope `(w, h)` doosre envelope `(W, H)` ke andar tabhi aa sakta hai jab `w < W` aur `h < H`. Aap kitne envelopes ko ek doosre ke andar stack kar sakte hain, iski **maximum count** return karni hai.

**Example:**

  * `envelopes = [[5,4],[6,4],[6,7],[2,3]]`
  * **Output:** 3
  * **Explanation:** The maximum number of envelopes you can Russian doll is 3: `[2,3] => [5,4] => [6,7]`. (Order is important here based on size)

-----

#### Approach: Russian Doll Envelopes

यह Box Stacking का एक विशेष मामला है और इसे $O(N \\log N)$ में LIS के साथ हल किया जा सकता है.

**Logic:**

1.  **Sort the Envelopes:**

      * Sort the `envelopes` array:
          * First, sort by `width` in **ascending** order.
          * If `widths` are equal, sort by `height` in **descending** order.
      * **Why this specific sort?**
          * Ascending by width: Ensures that when we iterate, we are considering envelopes with progressively larger widths.
          * Descending by height for equal widths: This is crucial\! If we have `[3,4]` and `[3,5]`, if we allow both to be part of the LIS by height, we might incorrectly say `[3,4] => [3,5]` is a valid sequence. But an envelope cannot contain another of the *same* width. By sorting equal widths in descending order of height, we ensure that for envelopes with the same width, only one (the largest height) will be considered to extend an LIS, and it won't allow other same-width envelopes to be placed *inside* it. This effectively makes the width comparison `w < W` work correctly with `height`'s LIS.

2.  **Apply LIS on Heights:**

      * Sort karne ke baad, `envelopes` array ki `heights` ko extract karo.
      * Is `heights` array par standard **Longest Increasing Subsequence (LIS)** algorithm (preferably the $O(N \\log N)$ version using binary search) apply karo.
      * LIS ki length hi final answer hogi.

-----

#### Solution: Russian Doll Envelopes (Java Code)

```java
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RussianDollEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        // Step 1: Sort the envelopes.
        // Sort by width in ascending order.
        // If widths are equal, sort by height in descending order.
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] != b[0]) { // Compare widths
                return Integer.compare(a[0], b[0]); // Ascending width
            }
            return Integer.compare(b[1], a[1]); // Descending height if widths are equal
        });

        // Step 2: Extract heights and find LIS on heights.
        // This is where the standard N log N LIS algorithm comes in.
        // 'tails' will store the smallest ending height of an increasing subsequence
        // of a specific length.
        List<Integer> tails = new ArrayList<>();

        for (int[] envelope : envelopes) {
            int currentHeight = envelope[1]; // We are only interested in heights now

            // Use binary search (Collections.binarySearch) to find the insertion point.
            // Collections.binarySearch returns:
            //   - index if element found
            //   - (-(insertion point) - 1) if element not found.
            // The insertion point is the index where the element would be inserted
            // to maintain the sorted order. This is effectively 'lower_bound'.
            int i = Collections.binarySearch(tails, currentHeight);

            // If i < 0, element was not found. insertionPoint is where it should be.
            // insertionPoint = -i - 1
            int insertionPoint = (i < 0) ? (-i - 1) : i;

            if (insertionPoint == tails.size()) {
                // If currentHeight is greater than all elements in 'tails',
                // it extends the longest increasing subsequence found so far.
                tails.add(currentHeight);
            } else {
                // Otherwise, 'currentHeight' can replace the element at 'insertionPoint'.
                // This means 'currentHeight' can form an increasing subsequence of the same length
                // but ending with a smaller value, which is better for future extensions.
                tails.set(insertionPoint, currentHeight);
            }
        }

        // The size of the 'tails' list is the length of the longest increasing subsequence.
        return tails.size();
    }

    public static void main(String[] args) {
        RussianDollEnvelopes solver = new RussianDollEnvelopes();

        int[][] envelopes1 = {{5,4},{6,4},{6,7},{2,3}};
        System.out.println("Max envelopes for " + Arrays.deepToString(envelopes1) + ": " + solver.maxEnvelopes(envelopes1)); // Expected: 3

        int[][] envelopes2 = {{1,1},{1,1},{1,1}};
        System.out.println("Max envelopes for " + Arrays.deepToString(envelopes2) + ": " + solver.maxEnvelopes(envelopes2)); // Expected: 1 (cannot nest same size)

        int[][] envelopes3 = {{4,5},{6,7},{2,3},{1,1}};
        System.out.println("Max envelopes for " + Arrays.deepToString(envelopes3) + ": " + solver.maxEnvelopes(envelopes3)); // Expected: 4 (1,1 -> 2,3 -> 4,5 -> 6,7)
    }
}
```

-----
