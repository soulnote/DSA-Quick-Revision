## 🎯 Binary Search Core Concepts 

**Binary Search = Har step mein search space ko half karna**

### Templates Yaad Rakho:

**Template 1 (Standard - find exact value):**
```java
int left = 0, right = n - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;
    else if (nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}
return -1;
```

**Template 2 (Find first true in monotonic condition):**
```java
int left = 0, right = n;
while (left < right) {
    int mid = left + (right - left) / 2;
    if (condition(mid)) right = mid;
    else left = mid + 1;
}
return left;
```

**Template 3 (Find last true):**
```java
int left = 0, right = n - 1;
while (left < right) {
    int mid = left + (right - left + 1) / 2;
    if (condition(mid)) left = mid;
    else right = mid - 1;
}
return left;
```

---

## 📋 20 Hard Binary Search Problems

---

### 1. Search in Rotated Sorted Array (LeetCode 33)
**Problem:** Rotated sorted array mein target find karo. Example: [4,5,6,7,0,1,2], target=0

**Algorithm:** Standard Binary Search with rotated detection

```java
public int search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        
        // Left half sorted hai
        if (nums[left] <= nums[mid]) {
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } 
        // Right half sorted hai
        else {
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    return -1;
}
```

>  Pehle check karo ki mid kis half mein hai. Agar left half sorted hai aur target usmein hai toh left half mein search karo, warna right half mein. Agar right half sorted hai toh opposite.

**Time:** O(log n)

---

### 2. Find Minimum in Rotated Sorted Array (LeetCode 153)
**Problem:** Rotated sorted array mein minimum element find karo. Example: [4,5,6,7,0,1,2] → 0

**Algorithm:** Binary Search comparing mid with right

```java
public int findMin(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[right]) {
            left = mid + 1;  // Minimum right side mein hai
        } else {
            right = mid;     // Minimum left side mein hai (including mid)
        }
    }
    return nums[left];
}
```

>  nums[mid] aur nums[right] compare karo. Agar mid > right, matlab rotation point right side mein hai. Warna left side mein (mid bhi candidate hai).

**Time:** O(log n)

---

### 3. Search in Rotated Sorted Array II (With Duplicates - LeetCode 81)
**Problem:** Rotated sorted array WITH duplicates mein target search karo.

**Algorithm:** Binary Search with duplicate skip logic

```java
public boolean search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return true;
        
        // Duplicates handle - can't determine which half is sorted
        if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
            left++;
            right--;
        }
        else if (nums[left] <= nums[mid]) {  // Left half sorted
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {  // Right half sorted
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    return false;
}
```

>  Duplicates hone se problem ye hai ki hum pata nahi laga sakte ki kaunsa half sorted hai. Jab left, mid, right teeno equal ho, left++ aur right-- karke duplicates skip karo.

**Time:** O(log n) average, O(n) worst case

---

### 4. Median of Two Sorted Arrays (LeetCode 4) - HARDEST
**Problem:** Do sorted arrays ka median find karo. O(log(min(m,n))) time mein.

**Algorithm:** Binary search on smaller array for partition point

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // Ensure nums1 is the smaller array
    if (nums1.length > nums2.length) {
        return findMedianSortedArrays(nums2, nums1);
    }
    
    int m = nums1.length, n = nums2.length;
    int totalLeft = (m + n + 1) / 2;
    int left = 0, right = m;
    
    while (left <= right) {
        int i = left + (right - left) / 2;  // nums1 se left partition mein kitne
        int j = totalLeft - i;              // nums2 se left partition mein kitne
        
        int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];
        
        if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
            // Correct partition found
            if ((m + n) % 2 == 0) {
                return (Math.max(nums1LeftMax, nums2LeftMax) + 
                        Math.min(nums1RightMin, nums2RightMin)) / 2.0;
            } else {
                return Math.max(nums1LeftMax, nums2LeftMax);
            }
        } else if (nums1LeftMax > nums2RightMin) {
            right = i - 1;  // nums1 se kam elements lene hain
        } else {
            left = i + 1;   // nums1 se zyada elements lene hain
        }
    }
    return 0.0;
}
```

>  Chhoti array pe binary search karo. Partition point i dhundho jisse left side ke saare elements <= right side ke saare elements. Total left side elements = (m+n+1)/2 hona chahiye.

**Time:** O(log(min(m,n)))

---

### 5. Kth Smallest Element in a Sorted Matrix (LeetCode 378)
**Problem:** Row-wise and column-wise sorted matrix mein kth smallest element.

**Algorithm:** Binary search on value space (min to max)

```java
public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    int left = matrix[0][0], right = matrix[n-1][n-1];
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        int count = countLessOrEqual(matrix, mid);
        
        if (count < k) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    return left;
}

private int countLessOrEqual(int[][] matrix, int target) {
    int count = 0;
    int n = matrix.length;
    int row = n - 1, col = 0;
    
    // Start from bottom-left corner
    while (row >= 0 && col < n) {
        if (matrix[row][col] <= target) {
            count += row + 1;  // All elements above this column are also <= target
            col++;
        } else {
            row--;
        }
    }
    return count;
}
```

>  Answer space (min value se max value) pe binary search. Mid ke liye count karo kitne elements <= mid hain. Agar count < k, toh answer bada hai, warna chota.

**Time:** O(n log(range))

---

### 6. Find Peak Element (LeetCode 162)
**Problem:** Array mein koi bhi peak element dhundho (nums[i] > neighbors).

**Algorithm:** Binary search - agar mid < mid+1, peak right side hai

```java
public int findPeakElement(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] < nums[mid + 1]) {
            left = mid + 1;  // Peak right side mein hai
        } else {
            right = mid;     // Peak left side mein hai (including mid)
        }
    }
    return left;
}
```

>  Binary search se peak dhundhna. Agar mid apne se next element se chota hai, matlab increasing slope hai, toh peak aage hai. Warna decreasing slope hai, toh peak piche hai.

**Time:** O(log n)

---

### 7. Find First and Last Position of Element in Sorted Array (LeetCode 34)
**Problem:** Sorted array mein target ka first aur last position dhundho.

**Algorithm:** Two binary searches - one for left, one for right

```java
public int[] searchRange(int[] nums, int target) {
    int[] result = {-1, -1};
    if (nums.length == 0) return result;
    
    // Find first position
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    
    if (nums[left] != target) return result;
    result[0] = left;
    
    // Find last position
    right = nums.length - 1;
    while (left < right) {
        int mid = left + (right - left + 1) / 2;
        if (nums[mid] > target) {
            right = mid - 1;
        } else {
            left = mid;
        }
    }
    result[1] = left;
    
    return result;
}
```

>  Pehla position find karne ke liye left boundary binary search, last position ke liye right boundary binary search. Left boundary mein mid ko left side prefer karte hain, right boundary mein right side.

**Time:** O(log n)

---

### 8. Find K Closest Elements (LeetCode 658)
**Problem:** Sorted array se k closest elements to x find karo.

**Algorithm:** Binary search to find starting point

```java
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int left = 0, right = arr.length - k;
    
    // Binary search to find the starting point
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        // Compare distance of mid and mid+k
        if (x - arr[mid] > arr[mid + k] - x) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = left; i < left + k; i++) {
        result.add(arr[i]);
    }
    return result;
}
```

>  Starting point binary search se dhundho. Compare karo: arr[mid] aur arr[mid+k] mein se kaun x ke close hai. arr[mid] ka distance zyada hai toh left = mid+1, warna right = mid.

**Time:** O(log(n-k) + k)

---

### 9. Capacity to Ship Packages Within D Days (LeetCode 1011)
**Problem:** Packages weights array, D days mein ship karna hai. Minimum capacity?

**Algorithm:** Binary search on answer (capacity)

```java
public int shipWithinDays(int[] weights, int days) {
    int left = 0, right = 0;
    for (int w : weights) {
        left = Math.max(left, w);  // Minimum capacity = max weight
        right += w;                 // Maximum capacity = sum of all
    }
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canShip(weights, days, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canShip(int[] weights, int days, int capacity) {
    int daysNeeded = 1;
    int currentLoad = 0;
    
    for (int w : weights) {
        if (currentLoad + w > capacity) {
            daysNeeded++;
            currentLoad = w;
        } else {
            currentLoad += w;
        }
    }
    return daysNeeded <= days;
}
```

>  Capacity pe binary search. Minimum capacity = max weight, maximum = sum. Check karo ki given capacity se D days mein ship ho sakta hai ya nahi. Agar ho sakta hai, capacity kam karo, warna badhao.

**Time:** O(n log(sum - max))

---

### 10. Koko Eating Bananas (LeetCode 875)
**Problem:** Banana piles, H hours mein khaani hain. Minimum eating speed?

**Algorithm:** Binary search on speed

```java
public int minEatingSpeed(int[] piles, int h) {
    int left = 1, right = 0;
    for (int p : piles) right = Math.max(right, p);
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canEat(piles, h, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canEat(int[] piles, int h, int speed) {
    int hours = 0;
    for (int p : piles) {
        hours += (p + speed - 1) / speed;  // ceil division
        if (hours > h) return false;
    }
    return hours <= h;
}
```

>  Speed pe binary search. Speed 1 se max pile tak. Har pile ke liye hours = ceil(pile/speed). Total hours <= h hona chahiye.

**Time:** O(n log(max))

---

### 11. Split Array Largest Sum (LeetCode 410)
**Problem:** Array ko m parts mein split karna hai. Minimize the largest sum of any part.

**Algorithm:** Binary search on answer (largest sum)

```java
public int splitArray(int[] nums, int m) {
    int left = 0, right = 0;
    for (int num : nums) {
        left = Math.max(left, num);
        right += num;
    }
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canSplit(nums, m, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canSplit(int[] nums, int m, int maxSum) {
    int parts = 1;
    int currentSum = 0;
    
    for (int num : nums) {
        if (currentSum + num > maxSum) {
            parts++;
            currentSum = num;
            if (parts > m) return false;
        } else {
            currentSum += num;
        }
    }
    return true;
}
```

>  Maximum sum pe binary search. Lower bound = max element, upper bound = sum. Check karo ki maxSum se m parts mein split ho sakta hai ya nahi.

**Time:** O(n log(sum))

---

### 12. Find the Duplicate Number (LeetCode 287)
**Problem:** [1..n] numbers, ek duplicate hai. Find it without modifying array, O(1) space.

**Algorithm:** Binary search on value space (1 to n) - Floyd's cycle detection bhi use kar sakte

```java
public int findDuplicate(int[] nums) {
    int left = 1, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        int count = 0;
        
        for (int num : nums) {
            if (num <= mid) count++;
        }
        
        if (count > mid) {
            right = mid;  // Duplicate left side mein hai
        } else {
            left = mid + 1;
        }
    }
    return left;
}
```

>  Value space (1 to n) pe binary search. Count karo kitne numbers <= mid hain. Agar count > mid, matlab duplicate left side mein hai, warna right side mein.

**Time:** O(n log n)

---

### 13. Search in a Sorted Array of Unknown Size (LeetCode 702)
**Problem:** Array size pata nahi hai. ArrayReader.get(index) method hai. Target find karo.

**Algorithm:** Exponential search + Binary search

```java
public int search(ArrayReader reader, int target) {
    // Exponential search to find boundaries
    int left = 0, right = 1;
    
    while (reader.get(right) < target) {
        left = right;
        right *= 2;
    }
    
    // Binary search
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int val = reader.get(mid);
        
        if (val == target) return mid;
        if (val < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1;
}
```

>  Pehle exponential growth se right boundary find karo (2, 4, 8, ...). Phir normal binary search.

**Time:** O(log position)

---

### 14. Time Based Key-Value Store (LeetCode 981)
**Problem:** set(key, value, timestamp), get(key, timestamp) → value at that timestamp or previous.

**Algorithm:** HashMap of key -> List of pairs + Binary search on timestamp

```java
class TimeMap {
    Map<String, List<Pair>> map;
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        List<Pair> list = map.get(key);
        if (list == null) return "";
        
        int left = 0, right = list.size() - 1;
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).timestamp <= timestamp) {
                result = list.get(mid).value;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
    
    class Pair {
        int timestamp;
        String value;
        Pair(int t, String v) { timestamp = t; value = v; }
    }
}
```

>  Har key ke liye timestamps sorted order mein store karo. Get mein binary search se <= timestamp wala latest value dhundho.

**Time:** Set: O(1), Get: O(log n)

---

### 15. Minimum Time to Complete Trips (LeetCode 2187)
**Problem:** Buses ka time array hai. Total trips banana hai. Minimum time kya hoga?

**Algorithm:** Binary search on answer (time)

```java
public long minimumTime(int[] time, int totalTrips) {
    long left = 1;
    long right = (long) Arrays.stream(time).min().getAsInt() * totalTrips;
    
    while (left < right) {
        long mid = left + (right - left) / 2;
        if (canComplete(time, totalTrips, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canComplete(int[] time, int totalTrips, long givenTime) {
    long trips = 0;
    for (int t : time) {
        trips += givenTime / t;
        if (trips >= totalTrips) return true;
    }
    return trips >= totalTrips;
}
```

>  Time pe binary search. Given time mein totalTrips complete ho sakti hai ya nahi check karo. GivenTime / busTime = us bus ki trips.

**Time:** O(n log(max))

---

### 16. Minimum Number of Days to Make m Bouquets (LeetCode 1482)
**Problem:** Bloom day array. m bouquets banana hai, har bouquet mein k adjacent flowers chahiye. Minimum days?

**Algorithm:** Binary search on answer (days)

```java
public int minDays(int[] bloomDay, int m, int k) {
    if (m * k > bloomDay.length) return -1;
    
    int left = 1, right = 0;
    for (int day : bloomDay) right = Math.max(right, day);
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canMake(bloomDay, m, k, mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private boolean canMake(int[] bloomDay, int m, int k, int days) {
    int bouquets = 0;
    int flowers = 0;
    
    for (int day : bloomDay) {
        if (day <= days) {
            flowers++;
            if (flowers == k) {
                bouquets++;
                flowers = 0;
            }
        } else {
            flowers = 0;
        }
    }
    return bouquets >= m;
}
```

>  Days pe binary search. Given days mein kitne bouquets ban sakte hain check karo. Adjacent flowers count karo.

**Time:** O(n log(max))

---

### 17. Find the Smallest Divisor Given a Threshold (LeetCode 1283)
**Problem:** Array nums, threshold. Divisor d dhundho jisse sum of ceil(nums[i]/d) <= threshold.

**Algorithm:** Binary search on divisor

```java
public int smallestDivisor(int[] nums, int threshold) {
    int left = 1, right = 0;
    for (int num : nums) right = Math.max(right, num);
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (getSum(nums, mid) <= threshold) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

private int getSum(int[] nums, int divisor) {
    int sum = 0;
    for (int num : nums) {
        sum += (num + divisor - 1) / divisor;  // ceil division
    }
    return sum;
}
```

>  Divisor pe binary search. Sum calculate karo ceil division se. Agar sum <= threshold, divisor chota kar sakte ho.

**Time:** O(n log(max))

---

### 18. Find Right Interval (LeetCode 436)
**Problem:** Intervals array [[1,2],[2,3],[3,4]]. Har interval ke liye right interval find karo (start >= current end).

**Algorithm:** Sort starts + Binary search

```java
public int[] findRightInterval(int[][] intervals) {
    int n = intervals.length;
    int[][] starts = new int[n][2];
    
    for (int i = 0; i < n; i++) {
        starts[i] = new int[]{intervals[i][0], i};
    }
    Arrays.sort(starts, (a, b) -> a[0] - b[0]);
    
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        int end = intervals[i][1];
        int left = 0, right = n - 1;
        int ans = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (starts[mid][0] >= end) {
                ans = starts[mid][1];
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        result[i] = ans;
    }
    return result;
}
```

>  Start values ko sort karo with original indices. Har interval ke end ke liye binary search karo pehla start >= end dhundhne ke liye.

**Time:** O(n log n)

---

### 19. Maximum Profit in Job Scheduling (LeetCode 1235)
**Problem:** startTime[], endTime[], profit[]. Non-overlapping jobs se max profit.

**Algorithm:** Sort by end time + DP + Binary search

```java
public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length;
    Job[] jobs = new Job[n];
    
    for (int i = 0; i < n; i++) {
        jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
    }
    Arrays.sort(jobs, (a, b) -> a.end - b.end);
    
    int[] dp = new int[n];
    dp[0] = jobs[0].profit;
    
    for (int i = 1; i < n; i++) {
        int includeProfit = jobs[i].profit;
        int lastIndex = findLastNonConflict(jobs, i);
        if (lastIndex != -1) includeProfit += dp[lastIndex];
        
        dp[i] = Math.max(includeProfit, dp[i - 1]);
    }
    return dp[n - 1];
}

private int findLastNonConflict(Job[] jobs, int index) {
    int left = 0, right = index - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (jobs[mid].end <= jobs[index].start) {
            result = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return result;
}

class Job {
    int start, end, profit;
    Job(int s, int e, int p) { start = s; end = e; profit = p; }
}
```

>  Jobs ko end time se sort karo. DP[i] = max profit tak i jobs consider karke. Current job include karna hai toh binary search se last non-conflicting job dhundho.

**Time:** O(n log n)

---

### 20. Maximum Number of Removable Characters (LeetCode 1898)
**Problem:** String s, string p, removable array. Remove characters from s in given order. Maximum removals such that p still subsequence of s.

**Algorithm:** Binary search on k (number of removals)

```java
public int maximumRemovals(String s, String p, int[] removable) {
    int left = 0, right = removable.length;
    
    while (left < right) {
        int mid = left + (right - left + 1) / 2;
        if (isSubsequence(s, p, removable, mid)) {
            left = mid;
        } else {
            right = mid - 1;
        }
    }
    return left;
}

private boolean isSubsequence(String s, String p, int[] removable, int k) {
    Set<Integer> removed = new HashSet<>();
    for (int i = 0; i < k; i++) {
        removed.add(removable[i]);
    }
    
    int j = 0;
    for (int i = 0; i < s.length() && j < p.length(); i++) {
        if (!removed.contains(i) && s.charAt(i) == p.charAt(j)) {
            j++;
        }
    }
    return j == p.length();
}
```

>  Removals count pe binary search. Check karo ki k removals ke baad bhi p subsequence hai ya nahi.

**Time:** O((n+m) log k)

---

## 📊 Complete Summary Table

| # | Problem | Key Pattern | Complexity |
|---|---------|-------------|------------|
| 1 | Search in Rotated Array | Identify sorted half | O(log n) |
| 2 | Find Min in Rotated | Compare mid with right | O(log n) |
| 3 | Rotated Array with Duplicates | Skip duplicates | O(log n) avg |
| 4 | Median of Two Arrays | Partition on smaller array | O(log(min)) |
| 5 | Kth Smallest in Matrix | BS on value + count | O(n log range) |
| 6 | Find Peak Element | Compare mid with mid+1 | O(log n) |
| 7 | First and Last Position | Two binary searches | O(log n) |
| 8 | Find K Closest Elements | Find start point | O(log(n-k)+k) |
| 9 | Ship Within Days | BS on capacity | O(n log sum) |
| 10 | Koko Eating Bananas | BS on speed | O(n log max) |
| 11 | Split Array Largest Sum | BS on max sum | O(n log sum) |
| 12 | Find Duplicate Number | BS on value | O(n log n) |
| 13 | Unknown Size Array | Exponential + BS | O(log pos) |
| 14 | Time Based KV Store | BS on timestamp | O(log n) per get |
| 15 | Minimum Time for Trips | BS on time | O(n log max) |
| 16 | Make Bouquets | BS on days | O(n log max) |
| 17 | Smallest Divisor | BS on divisor | O(n log max) |
| 18 | Find Right Interval | Sort + BS | O(n log n) |
| 19 | Job Scheduling | Sort + BS + DP | O(n log n) |
| 20 | Maximum Removals | BS on k + subsequence | O((n+m) log k) |

---

## 🎯 Hard Binary Search Interview Tips

### 1. Identify Binary Search on Answer:
- **"Minimum maximum"** ya **"maximum minimum"** dikhe → BS on answer
- **"Capacity", "speed", "days", "time"** pe BS
- Monotonic property honi chahiye (false...false, true...true)

### 2. Templates Yaad Rakho:

**For finding minimum valid value (first true):**
```java
while (left < right) {
    int mid = left + (right - left) / 2;
    if (check(mid)) right = mid;
    else left = mid + 1;
}
```

**For finding maximum valid value (last true):**
```java
while (left < right) {
    int mid = left + (right - left + 1) / 2;
    if (check(mid)) left = mid;
    else right = mid - 1;
}
```

### 3. Rotated Array Patterns:
- Compare with **left** or **right** based on problem
- Duplicates hone par extra handling (skip)
- Identify **sorted half** always

### 4. Binary Search on Answer Checklist:
- **Search space kya hai?** (min to max)
- **Monotonic condition satisfied?** (check function monotonic honi chahiye)
- **Can I check a mid value efficiently?** (check function O(n) ya better)

### 5. Common Mistakes:
- **Integer overflow:** Use `mid = left + (right-left)/2`
- **Off-by-one errors:** Carefully choose `left < right` vs `left <= right`
- **Wrong boundary initialization**
- **Infinite loop:** Use correct mid calculation (for last true, use `+1`)

### 6. When to use which Binary Search:

| Scenario | Template |
|----------|----------|
| Exact value in sorted array | Standard (left <= right) |
| First element satisfying condition | First true |
| Last element satisfying condition | Last true |
| Rotated array | Special (compare with ends) |
| Unknown size | Exponential + BS |
