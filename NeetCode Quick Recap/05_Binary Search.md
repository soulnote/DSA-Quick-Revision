
# Problem: Koko Eating Bananas

Koko loves eating bananas. Given `n` piles of bananas, where `piles[i]` represents the number of bananas in the ith pile, and `h` hours before the guards return, Koko wants to finish eating all bananas within `h` hours.

* Koko chooses an eating speed `k` (bananas per hour).
* Each hour, Koko eats `k` bananas from a single pile. If the pile has fewer than `k` bananas, she eats all of them and does not continue eating in that hour.
* Koko wants to eat as slowly as possible but still finish all bananas before the guards come back.

**Return the minimum integer `k` such that Koko can finish all bananas within `h` hours.**

---

### Example:

**Input:**
`piles = [3, 6, 7, 11], h = 8`

**Output:**
`4`

---

### Approach: Binary Search on Eating Speed

1. **Range of possible speeds:**
   Minimum speed is `1` (eat at least one banana per hour),
   Maximum speed is the size of the largest pile (eat entire largest pile in one hour).

2. **Binary Search:**
   Check the mid speed `midSpeed` between low and high.

3. **Calculate total hours to eat all piles at speed `midSpeed`:**
   For each pile, hours needed = ceil(pile / midSpeed)
   Sum all hours.

4. **Adjust binary search range:**

   * If total hours ≤ `h`, try to find a smaller speed (lower bound)
   * Else, increase speed (upper bound)

---

### Java Code with Comments:

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // Step 1: Find the maximum bananas in any pile (upper bound for speed)
        int maxPile = 0;
        for (int bananas : piles) {
            maxPile = Math.max(maxPile, bananas);
        }

        // Step 2: Binary search between 1 and maxPile for minimum valid speed
        int left = 1;
        int right = maxPile;
        int result = maxPile;  // Initialize result with max possible speed

        while (left <= right) {
            int midSpeed = left + (right - left) / 2;

            // Step 3: Calculate total hours needed at current midSpeed
            long totalHours = 0;
            for (int bananas : piles) {
                // Use ceiling division to calculate hours for each pile
                totalHours += (bananas + midSpeed - 1) / midSpeed;
            }

            // Step 4: Check if Koko can finish within h hours
            if (totalHours <= h) {
                // midSpeed is valid, try to find smaller speed
                result = midSpeed;
                right = midSpeed - 1;
            } else {
                // midSpeed too slow, increase speed
                left = midSpeed + 1;
            }
        }

        return result;
    }
}
```

---

---

# Problem: Find Minimum in Rotated Sorted Array

You are given a sorted array `nums` of unique elements that has been rotated between 1 and n times.
Your task is to find the minimum element in this rotated array in **O(log n)** time.

---

### Example:

**Input:**
`nums = [3,4,5,1,2]`

**Output:**
`1`

**Explanation:**
The original array `[1,2,3,4,5]` was rotated 3 times to become `[3,4,5,1,2]`. The minimum element is `1`.

---

### Approach: Modified Binary Search

* The rotated array is partly sorted.
* Compare the middle element with the left element to determine which half is sorted.
* If left half is sorted, the minimum could be the leftmost element.
* If the middle element is less than the left element, the rotation point is in the left half.
* Narrow down the search space based on these observations until you find the minimum.

---

### Java Code with Comments:

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        // Initialize minValue to a large number
        int minValue = Integer.MAX_VALUE;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Case 1: Left half is sorted
            if (nums[mid] >= nums[left]) {
                // The smallest value in this sorted part could be nums[left]
                minValue = Math.min(minValue, nums[left]);
                // Move search to the right half
                left = mid + 1;
            } else {
                // Case 2: Rotation point in left half, mid is smaller than left
                minValue = Math.min(minValue, nums[mid]);
                // Move search to the left half
                right = mid - 1;
            }
        }

        return minValue;
    }
}
```
---

# Problem: Search in Rotated Sorted Array

Given a rotated sorted array `nums` (with distinct integers) and a `target` value,
return the index of `target` if it exists in the array; otherwise, return `-1`.

Your solution must run in **O(log n)** time.

---

### Example:

**Input:**
`nums = [4,5,6,7,0,1,2]`, `target = 0`

**Output:**
`4`

**Explanation:**
`0` is found at index 4 in the rotated array.

---

### Approach: Modified Binary Search

* The array is sorted but rotated at some pivot.
* At each step, identify which half (left or right) is sorted.
* Check if `target` lies within the sorted half:

  * If yes, discard the other half.
  * If no, search the other half.
* Continue until you find the target or the search space is empty.

---

### Java Code with Comments:

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if mid is the target
            if (nums[mid] == target) return mid;

            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                // Check if target lies in the left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // Search left half
                } else {
                    left = mid + 1;  // Search right half
                }
            } 
            // Otherwise, right half is sorted
            else {
                // Check if target lies in the right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;  // Search right half
                } else {
                    right = mid - 1; // Search left half
                }
            }
        }

        // Target not found
        return -1;
    }
}
```

---

# Problem: Time Based Key-Value Store

Design a data structure to store multiple values for the same key with different timestamps,
and retrieve the value corresponding to the largest timestamp less than or equal to a given timestamp.

---

### Example:

**Input:**

```
["TimeMap", "set", "get", "get", "set", "get", "get"]
[[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
```

**Output:**

```
[null, null, "bar", "bar", null, "bar2", "bar2"]
```

---

### Approach:

* Store values for each key as a list of `(value, timestamp)` pairs.
* Use **binary search** on the list to find the value with the greatest timestamp ≤ the requested timestamp.
* This ensures efficient retrieval in O(log n) time.

---

### Java Code with Comments:

```java
import java.util.*;

class TimeMap {

    // Inner class to store value and its timestamp
    class Entry {
        String value;
        int timestamp;

        Entry(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    // Map from key to list of timestamped values
    private Map<String, List<Entry>> map;

    // Initialize the data structure
    public TimeMap() {
        map = new HashMap<>();
    }

    // Store the value with the given key and timestamp
    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new Entry(value, timestamp));
    }

    // Retrieve the value with the largest timestamp <= given timestamp
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }

        List<Entry> entries = map.get(key);

        int left = 0, right = entries.size() - 1;
        String result = "";

        // Binary search for closest timestamp <= given timestamp
        while (left <= right) {
            int mid = left + (right - left) / 2;
            Entry current = entries.get(mid);

            if (current.timestamp == timestamp) {
                return current.value;  // Exact match found
            } else if (current.timestamp < timestamp) {
                result = current.value;  // Potential result, look right for closer match
                left = mid + 1;
            } else {
                right = mid - 1;  // Search left half
            }
        }

        return result;  // Return closest value or empty if none found
    }
}
```

---
Absolutely! Here's a detailed explanation along with the code, comments, and step-by-step logic for the **Median of Two Sorted Arrays** problem.

---

# Problem: Median of Two Sorted Arrays

You are given two sorted arrays `nums1` and `nums2` of sizes `m` and `n`.
Find the median of the combined sorted array in **O(log(m+n))** time complexity.

---

### Example:

```
Input: nums1 = [1, 3], nums2 = [2]
Output: 2.0

Explanation: The merged sorted array is [1, 2, 3].  
The median is the middle element 2.
```

---

# Intuition:

If we merge the two arrays and sort them, the median is straightforward:

* If total length is odd, median is the middle element.
* If even, median is average of two middle elements.

But merging takes **O(m+n)** time, which is not efficient.

---

# Optimal Approach: Binary Search on Smaller Array

We can solve it in O(log(min(m, n))) by applying binary search on the smaller array.

---

# How?

Imagine splitting the two arrays into two halves each:

* Left halves combined: all elements that would be to the left of the median.
* Right halves combined: all elements that would be to the right of the median.

We want to find a partition in `nums1` and `nums2` such that:

* Number of elements on left side = number of elements on right side (or left side has 1 extra if total length is odd)
* Every element on left side ≤ every element on right side.

This ensures the median is between max of left sides and min of right sides.

---

# Detailed Steps:

1. Ensure `nums1` is the smaller array, so binary search is done on it for efficiency.

2. Use binary search on `nums1` with indices `low=0` and `high=length(nums1)`.
   Pick `mid1` as partition point in `nums1`.
   Calculate `mid2` as partition point in `nums2` so that left + right halves split the combined array correctly.

3. Define:

   * `l1 = nums1[mid1 - 1]` if `mid1 > 0`, else `-∞`
   * `r1 = nums1[mid1]` if `mid1 < length(nums1)`, else `+∞`
   * `l2 = nums2[mid2 - 1]` if `mid2 > 0`, else `-∞`
   * `r2 = nums2[mid2]` if `mid2 < length(nums2)`, else `+∞`

4. Check conditions:

   * If `l1 <= r2` and `l2 <= r1`, we have the correct partition.

     * If total length is odd, median = `max(l1, l2)`
     * Else, median = `(max(l1, l2) + min(r1, r2)) / 2`

   * Else if `l1 > r2`, move `high = mid1 - 1` (move left in `nums1`)

   * Else move `low = mid1 + 1` (move right in `nums1`)

Repeat until the correct partition is found.

---

# Code with detailed comments:

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        // Always binary search on the smaller array
        if (n1 > n2) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int totalLength = n1 + n2;
        int half = (totalLength + 1) / 2;  // Half length for left side

        int low = 0, high = n1;

        while (low <= high) {
            int mid1 = low + (high - low) / 2;  // Partition for nums1
            int mid2 = half - mid1;              // Partition for nums2

            // Elements just left of partition or -∞ if none
            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1];

            // Elements just right of partition or +∞ if none
            int r1 = (mid1 == n1) ? Integer.MAX_VALUE : nums1[mid1];
            int r2 = (mid2 == n2) ? Integer.MAX_VALUE : nums2[mid2];

            // Check if correct partition found
            if (l1 <= r2 && l2 <= r1) {
                // If total length is odd, median is max of left elements
                if (totalLength % 2 == 1) {
                    return Math.max(l1, l2);
                } else {
                    // Else median is average of max left and min right
                    return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
                }
            } else if (l1 > r2) {
                // l1 is too big, move left in nums1
                high = mid1 - 1;
            } else {
                // l2 is too big, move right in nums1
                low = mid1 + 1;
            }
        }

        // If input arrays are invalid or no median found
        return 0.0;
    }
}
```

---

# Why this works?

* We find a partition where elements on the left side of combined arrays are smaller than or equal to elements on the right side.
* Because arrays are sorted, binary search quickly narrows down correct partition.
* The use of `Integer.MIN_VALUE` and `Integer.MAX_VALUE` handles edge cases where partition falls at array boundaries.

---

### Summary:

* Time Complexity: O(log(min(m, n)))
* Space Complexity: O(1)
* Uses binary search partitioning to find median efficiently without full merge.

