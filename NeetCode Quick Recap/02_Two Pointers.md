

# Problem: Trapping Rain Water

You are given **n** non-negative integers representing an elevation map where the width of each bar is 1. Your task is to compute how much water can be trapped after raining.

---

### Example:

**Input:**
`height = [0,1,0,2,1,0,1,3,2,1,2,1]`

**Output:**
`6`

**Explanation:**
The elevation map represented by the array shows bars of different heights. Water gets trapped between these bars. In this case, 6 units of water are trapped after raining.

---

### Solution Code with Comments:

```java
class Solution {
    public int trap(int[] height) {
        int totalWater = 0;              // Total units of trapped water
        int n = height.length;           // Length of the elevation array
        
        // Arrays to store the maximum height to the left and right of each position
        int[] left = new int[n];
        int[] right = new int[n];
        
        int leftMax = 0;                 // Track max height from the left side
        int rightMax = 0;                // Track max height from the right side
        
        // Calculate max height to the left of each bar
        for (int i = 0; i < n; i++) {
            left[i] = Math.max(leftMax, height[i]);
            leftMax = left[i];
        }
        
        // Calculate max height to the right of each bar
        for (int i = n - 1; i >= 0; i--) {
            right[i] = Math.max(rightMax, height[i]);
            rightMax = right[i];
        }
        
        // Calculate the trapped water at each position
        for (int i = 0; i < n; i++) {
            // Water trapped on top of current bar is the minimum of max heights on both sides minus the bar's height
            int water = Math.min(left[i], right[i]) - height[i];
            totalWater += water;
        }
        
        return totalWater;  // Return the total trapped water
    }
}
```

### Explanation:

* For each position in the array, the water trapped depends on the highest bars on the left and right side.
* We first precompute `left[i]` as the highest bar to the left of `i` (including `i`).
* Similarly, we compute `right[i]` as the highest bar to the right of `i` (including `i`).
* The water trapped on each bar is the minimum of `left[i]` and `right[i]` minus the height of the current bar.
* Summing all these values gives the total amount of trapped water.

---


## Problem: Trapping Rain Water (Two-Pointer Optimized Approach)


```java
class Solution {
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;  // Two pointers
        int leftMax = 0, rightMax = 0;            // Max heights from both ends
        int totalWater = 0;                       // Total trapped water

        while (left < right) {
            if (height[left] < height[right]) {
                // Water trapped depends on leftMax
                if (height[left] >= leftMax) {
                    leftMax = height[left];       // Update leftMax
                } else {
                    totalWater += leftMax - height[left];  // Water trapped at current left
                }
                left++;  // Move left pointer inward
            } else {
                // Water trapped depends on rightMax
                if (height[right] >= rightMax) {
                    rightMax = height[right];     // Update rightMax
                } else {
                    totalWater += rightMax - height[right]; // Water trapped at current right
                }
                right--;  // Move right pointer inward
            }
        }

        return totalWater;  // Return total water trapped
    }
}
```

### Explanation:

* This approach uses **O(1)** extra space (no arrays).
* We initialize two pointers: one at the beginning (`left`) and one at the end (`right`).
* Track the **maximum height** seen so far from both ends: `leftMax` and `rightMax`.
* At each step, move the pointer with the smaller height inward.

  * If `height[left] < height[right]`, we check `leftMax`.
  * If `height[right] <= height[left]`, we check `rightMax`.
* The idea is: **the water level is determined by the shorter boundary**, so we move from the side with the shorter height.
* This runs in **O(n)** time and uses **O(1)** space.

---

# Problem: 3Sum

Given an integer array `nums`, return **all the triplets** `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

**Note:** The solution set must not contain duplicate triplets.

---

### Example:

**Input:**
`nums = [-1, 0, 1, 2, -1, -4]`

**Output:**
`[[-1, -1, 2], [-1, 0, 1]]`

**Explanation:**
The triplets that sum to zero are:

* `(-1) + 0 + 1 = 0`
* `(-1) + (-1) + 2 = 0`
  Duplicates are avoided, and the order of output does not matter.

---

### Solution Code with Comments:

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);  // Sort the array to use two-pointer technique and handle duplicates
        List<List<Integer>> list = new ArrayList<>();
        
        // Iterate through the array, fixing the first element of the triplet
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate elements to avoid duplicate triplets
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int j = i + 1;              // Second pointer
            int k = nums.length - 1;    // Third pointer
            
            // Use two-pointer approach to find the other two numbers
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                
                if (sum == 0) {
                    // Found a triplet that sums to 0
                    list.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                    
                    // Skip duplicates for second element
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    // Skip duplicates for third element
                    while (j < k && nums[k] == nums[k + 1]) k--;
                } 
                else if (sum > 0) {
                    // Sum is too big, move the right pointer left
                    k--;
                } else {
                    // Sum is too small, move the left pointer right
                    j++;
                }
            }
        }
        
        return list;  // Return the list of triplets
    }
}
```

---

### Explanation:

* The array is first **sorted** to enable the two-pointer technique and to easily skip duplicates.
* We fix one element (`nums[i]`) and use two pointers (`j`, `k`) to find pairs such that the three numbers sum to zero.
* To avoid duplicates:

  * Skip the same element for `i` if it's the same as the previous one.
  * After finding a valid triplet, skip over duplicate values for both `j` and `k`.
* This solution has a time complexity of **O(n²)** and space complexity of **O(1)** (excluding output list).

---
