# Binary Search

## **Binary Search Basics - Theory**

Binary search ek **efficient search algorithm** hai jo **sorted array** mein kaam karta hai. Yeh **divide and conquer** technique use karta hai.

### **Kaise kaam karta hai?**

Jaise dictionary mein word dhundhna - tum beech mein kholte ho, agar word chota hai to left side mein dekhte ho, bada hai to right side mein.

**Steps:**
1. Array ke **middle element** ko find karo
2. Agar target == middle → mil gaya
3. Agar target < middle → left half mein search karo
4. Agar target > middle → right half mein search karo
5. Repeat until element mil jaye ya range khatam ho jaye

### **Important Conditions:**
- Array **sorted** hona chahiye (ascending/descending)
- **Time Complexity:** O(log n) - har step mein search space half ho jati hai
- **Space Complexity:** O(1) iterative, O(log n) recursive

### **Why O(log n)?**
- n = 16 → max comparisons = 4 (2⁴ = 16)
- n = 1,000,000 → max comparisons = 20 (2²⁰ ≈ 1 million)

---

## **Basic Binary Search Implementation**

```java
class BinarySearchBasic {
    
    // Method 1: Iterative (Most common, recommended)
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;  // Avoid overflow
            
            if(nums[mid] == target) {
                return mid;  // Element found
            }
            else if(nums[mid] < target) {
                left = mid + 1;  // Search in right half
            }
            else {
                right = mid - 1;  // Search in left half
            }
        }
        
        return -1;  // Element not found
    }
    
    // Method 2: Recursive
    public int binarySearchRecursive(int[] nums, int target, int left, int right) {
        if(left > right) return -1;
        
        int mid = left + (right - left) / 2;
        
        if(nums[mid] == target) return mid;
        else if(nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        }
        else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
    
    // Method 3: Descending order array mein search
    public int binarySearchDescending(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) return mid;
            else if(nums[mid] > target) {  // Note: opposite condition
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
```

---

## **1. First and Last Position of Element** ⭐⭐⭐ (Most Asked)

### Theory
**Problem:** Sorted array mein target element ki first aur last occurrence find karo.

**Algorithm:**
1. **Left boundary (first occurrence)** dhundhne ke liye:
   - Jab nums[mid] == target, tab bhi left side search karo (right = mid - 1)
   - Answer store karte jao

2. **Right boundary (last occurrence)** dhundhne ke liye:
   - Jab nums[mid] == target, tab bhi right side search karo (left = mid + 1)
   - Answer store karte jao

### Java Code
```java
class FirstLastPosition {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        
        return result;
    }
    
    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int first = -1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) {
                first = mid;
                right = mid - 1;  // Left side mein aur search karo
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return first;
    }
    
    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int last = -1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) {
                last = mid;
                left = mid + 1;  // Right side mein aur search karo
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return last;
    }
    
    // Alternative: Using binary search with flags
    public int binarySearch(int[] nums, int target, boolean findFirst) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) {
                result = mid;
                if(findFirst) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return result;
    }
    
    // Time: O(log n), Space: O(1)
}
```

---

## **2. Search Insert Position** ⭐⭐

### Theory
**Problem:** Sorted array mein target ka insertion position find karo. Agar target exist karta hai to uska index return karo, nahi to wahan index return karo jahan insert hona chahiye.

**Algorithm:**
- Normal binary search
- Agar target milta hai to index return karo
- Nahi milta to `left` pointer hi insertion position hai

### Java Code
```java
class SearchInsert {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) {
                return mid;
            }
            else if(nums[mid] < target) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        
        return left;  // Insertion position
    }
    
    // Time: O(log n), Space: O(1)
}
```

---

## **3. Sqrt(x) - Find Square Root** ⭐⭐

### Theory
**Problem:** Integer ka square root find karo (floor value).

**Algorithm:**
1. Search space: 1 se x tak
2. Mid ka square calculate karo
3. Agar mid*mid == x → mid return karo
4. Agar mid*mid < x → left = mid + 1, ans = mid store karo
5. Agar mid*mid > x → right = mid - 1

### Java Code
```java
class Sqrt {
    // Method 1: Binary Search
    public int mySqrt(int x) {
        if(x < 2) return x;
        
        int left = 1;
        int right = x / 2;  // Square root can't be more than x/2 for x>4
        int ans = 0;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            // Use long to avoid overflow
            long square = (long)mid * mid;
            
            if(square == x) {
                return mid;
            }
            else if(square < x) {
                ans = mid;
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return ans;
    }
    
    // Method 2: Newton's Method (More efficient)
    public int mySqrtNewton(int x) {
        if(x < 2) return x;
        
        long guess = x;
        while(guess * guess > x) {
            guess = (guess + x / guess) / 2;
        }
        return (int)guess;
    }
    
    // Time: O(log n), Space: O(1)
}
```

---

## **4. Find Peak Element** ⭐⭐

### Theory
**Problem:** Peak element dhundho (element jo adjacent se bada ho). Array partially sorted hai (first increasing, then decreasing).

**Algorithm (Modified Binary Search):**
1. Mid element check karo
2. Agar nums[mid] < nums[mid+1] → peak right side hai (left = mid + 1)
3. Agar nums[mid] > nums[mid+1] → peak left side hai (right = mid)
4. Multiple peaks ho sakte hain, koi bhi return karo

### Java Code
```java
class FindPeak {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] < nums[mid + 1]) {
                left = mid + 1;  // Peak right side
            } else {
                right = mid;      // Peak left side (including mid)
            }
        }
        
        return left;  // left == right == peak index
    }
    
    // For array with multiple peaks, returns any peak
    // Time: O(log n), Space: O(1)
}
```

---

## **5. Search in Rotated Sorted Array** ⭐⭐⭐

### Theory
**Problem:** Rotated sorted array mein target search karo (e.g., [4,5,6,7,0,1,2]).

**Algorithm:**
1. Mid find karo
2. Check karo ki left half sorted hai ya right half
3. Decide karo ki target konse half mein ho sakta hai

**Key Insight:** Rotated array mein at least one half hamesha sorted hota hai

### Java Code
```java
class SearchRotated {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) return mid;
            
            // Left half sorted hai
            if(nums[left] <= nums[mid]) {
                // Target left half mein hai
                if(nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half sorted hai
            else {
                // Target right half mein hai
                if(nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    // Search in rotated sorted array WITH duplicates
    public boolean searchWithDuplicates(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] == target) return true;
            
            // Handle duplicates: skip them
            if(nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
            }
            else if(nums[left] <= nums[mid]) {
                if(nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            else {
                if(nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }
    
    // Find minimum in rotated sorted array
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
    
    // Time: O(log n), Space: O(1)
}
```

---

## **6. Find Minimum in Rotated Sorted Array** ⭐⭐

### Theory
**Problem:** Rotated sorted array mein minimum element find karo.

**Algorithm:**
1. Compare mid with right element
2. Agar nums[mid] > nums[right] → minimum right side hai (left = mid + 1)
3. Agar nums[mid] < nums[right] → minimum left side hai (right = mid)
4. Agar nums[mid] == nums[right] (duplicates case) → right-- (skip duplicate)

### Java Code
```java
class FindMinimum {
    // Without duplicates
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
    
    // With duplicates
    public int findMinWithDuplicates(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(nums[mid] > nums[right]) {
                left = mid + 1;
            }
            else if(nums[mid] < nums[right]) {
                right = mid;
            }
            else {  // nums[mid] == nums[right]
                right--;
            }
        }
        return nums[left];
    }
    
    // Time: O(log n), worst O(n) for duplicates, Space: O(1)
}
```

---

## **7. Find K Closest Elements** ⭐⭐

### Theory
**Problem:** Sorted array mein k closest elements find karo to target.

**Algorithm (Binary Search + Two Pointers):**
1. Binary search se target ke closest element ka position find karo
2. Two pointers (left, right) use karke window expand karo
3. Agar left valid hai aur closer hai to left--, nahi to right++

### Java Code
```java
class KClosest {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Find insertion point of x
        int left = 0;
        int right = arr.length - k;
        
        // Binary search for the left bound
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare distances
            if(x - arr[mid] > arr[mid + k] - x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        // Collect k elements starting from left
        List<Integer> result = new ArrayList<>();
        for(int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }
    
    // Alternative: Two pointers approach
    public List<Integer> findClosestElementsTwoPointers(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - 1;
        
        while(right - left >= k) {
            if(Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                left++;
            } else {
                right--;
            }
        }
        
        List<Integer> result = new ArrayList<>();
        for(int i = left; i <= right; i++) {
            result.add(arr[i]);
        }
        return result;
    }
    
    // Time: O(log(n-k) + k), Space: O(1)
}
```

---

## **8. Capacity to Ship Packages** ⭐⭐⭐ (Hard Pattern)

### Theory
**Problem:** Minimum capacity (weight per day) find karo taki saare packages D days mein ship ho jayein.

**Algorithm (Binary Search on Answer):**
1. Search space: max(weights) se sum(weights) tak
2. Mid capacity ke liye check karo kitne days lagenge
3. Agar days <= D to capacity kam karo (right = mid - 1)
4. Agar days > D to capacity badhao (left = mid + 1)

**Pattern:** Ye "Minimum X such that condition Y is satisfied" type ka problem hai

### Java Code
```java
class ShipPackages {
    public int shipWithinDays(int[] weights, int days) {
        int left = 0;  // max weight (minimum possible capacity)
        int right = 0; // sum of weights (maximum possible capacity)
        
        for(int w : weights) {
            left = Math.max(left, w);
            right += w;
        }
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(canShip(weights, days, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canShip(int[] weights, int days, int capacity) {
        int currentDays = 1;
        int currentWeight = 0;
        
        for(int weight : weights) {
            if(currentWeight + weight > capacity) {
                currentDays++;
                currentWeight = 0;
            }
            currentWeight += weight;
        }
        
        return currentDays <= days;
    }
    
    // Time: O(n log(sum - max)), Space: O(1)
}
```

---

## **9. Koko Eating Bananas** ⭐⭐

### Theory
**Problem:** Koko ko saare bananas k hours mein khane hain. Minimum eating speed (bananas/hour) find karo.

**Algorithm (Binary Search on Answer):**
1. Search space: 1 se max(piles) tak
2. Mid speed ke liye calculate karo total hours lagenge
3. Agar hours <= k to speed kam karo
4. Agar hours > k to speed badhao

### Java Code
```java
class KokoBananas {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;
        
        for(int pile : piles) {
            right = Math.max(right, pile);
        }
        
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            if(canEat(piles, h, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canEat(int[] piles, int h, int speed) {
        int hours = 0;
        for(int pile : piles) {
            hours += (pile + speed - 1) / speed;  // Ceil division
        }
        return hours <= h;
    }
    
    // Time: O(n log max), Space: O(1)
}
```

---

## **10. Median of Two Sorted Arrays** ⭐⭐⭐ (Hard)

### Theory
**Problem:** Do sorted arrays ka median find karo in O(log(min(m,n))) time.

**Algorithm:**
1. Chhoti array pe binary search karo
2. Partition points find karo
3. Check karo ki left side ke saare elements right side se chote hain

### Java Code
```java
class MedianOfTwoArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is smaller
        if(nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        int half = (total + 1) / 2;
        
        int left = 0;
        int right = m;
        
        while(left <= right) {
            int partition1 = left + (right - left) / 2;
            int partition2 = half - partition1;
            
            int left1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int right1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            int left2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int right2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            if(left1 <= right2 && left2 <= right1) {
                // Found correct partition
                if(total % 2 == 0) {
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    return Math.max(left1, left2);
                }
            }
            else if(left1 > right2) {
                right = partition1 - 1;
            }
            else {
                left = partition1 + 1;
            }
        }
        
        return -1;
    }
    
    // Time: O(log(min(m,n))), Space: O(1)
}
```

---

## **11. Time Based Key-Value Store** ⭐⭐⭐

### Theory
**Problem:** Key ke saath timestamps store karo, aur given timestamp se pehle ka value return karo.

**Algorithm:**
1. HashMap mein key -> List of (timestamp, value) pairs store karo
2. Get operation mein binary search use karo

### Java Code
```java
class TimeMap {
    private Map<String, List<Pair>> map;
    
    class Pair {
        int timestamp;
        String value;
        
        Pair(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        if(!map.containsKey(key)) return "";
        
        List<Pair> list = map.get(key);
        int left = 0;
        int right = list.size() - 1;
        String result = "";
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            
            if(list.get(mid).timestamp <= timestamp) {
                result = list.get(mid).value;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Time: O(log n) for get, O(1) for set
}
```

---

## **12. Binary Search on Infinite Array** ⭐⭐

### Theory
**Problem:** Infinite sorted array mein target find karo (size pata nahi).

**Algorithm:**
1. Pehle bounds find karo (exponential search)
2. Phir normal binary search

### Java Code
```java
class InfiniteArraySearch {
    // Assuming we have a method getElement(index) that returns value or null
    public int searchInfiniteArray(int target) {
        // Find bounds
        int left = 0;
        int right = 1;
        
        // Exponentially increase right until we find bound
        while(getElement(right) < target) {
            left = right;
            right = right * 2;
        }
        
        // Normal binary search
        while(left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = getElement(mid);
            
            if(midVal == target) return mid;
            else if(midVal < target) left = mid + 1;
            else right = mid - 1;
        }
        
        return -1;
    }
    
    // Dummy method - in real scenario, this would fetch from data source
    private int getElement(int index) {
        // Implementation depends on data source
        return 0;
    }
    
    // Time: O(log position), Space: O(1)
}
```

---

## **Binary Search Patterns Cheat Sheet**

### **Pattern 1: Basic Search**
```java
while(left <= right) {
    int mid = left + (right - left) / 2;
    if(nums[mid] == target) return mid;
    else if(nums[mid] < target) left = mid + 1;
    else right = mid - 1;
}
```

### **Pattern 2: Find First/Last**
```java
// First occurrence
while(left <= right) {
    int mid = left + (right - left) / 2;
    if(nums[mid] >= target) {
        result = mid;
        right = mid - 1;
    } else {
        left = mid + 1;
    }
}
```

### **Pattern 3: Search in Rotated Array**
```java
while(left <= right) {
    int mid = left + (right - left) / 2;
    if(nums[mid] == target) return mid;
    
    if(nums[left] <= nums[mid]) {  // Left sorted
        if(nums[left] <= target && target < nums[mid]) right = mid - 1;
        else left = mid + 1;
    } else {  // Right sorted
        if(nums[mid] < target && target <= nums[right]) left = mid + 1;
        else right = mid - 1;
    }
}
```

### **Pattern 4: Binary Search on Answer**
```java
while(left < right) {
    int mid = left + (right - left) / 2;
    if(condition(mid)) {
        right = mid;  // or left = mid + 1 depending on problem
    } else {
        left = mid + 1;  // or right = mid - 1
    }
}
```

---

## **Common Variations Table**

| Problem Type | Condition | Left Update | Right Update |
|--------------|-----------|-------------|--------------|
| Find target | nums[mid] == target | left = mid + 1 | right = mid - 1 |
| First occurrence | nums[mid] >= target | left = mid + 1 | right = mid - 1 |
| Last occurrence | nums[mid] <= target | left = mid + 1 | right = mid - 1 |
| Insert position | nums[mid] < target | left = mid + 1 | right = mid - 1 |
| Find min rotated | nums[mid] > nums[right] | left = mid + 1 | right = mid |
| Find peak | nums[mid] < nums[mid+1] | left = mid + 1 | right = mid |

---

## **Time Complexity Analysis**

### **How to calculate O(log n)?**

**Binary Search recurrence:** T(n) = T(n/2) + O(1)

**Using Master Theorem:** T(n) = aT(n/b) + f(n)
- a = 1 (ek recursive call)
- b = 2 (half size)
- f(n) = O(1)

**Case 2:** f(n) = O(n^log_b(a)) = O(n^0) = O(1)
Therefore T(n) = O(log n)

### **Space Complexity:**
- **Iterative:** O(1) - sirf variables use hote hain
- **Recursive:** O(log n) - recursion stack

---

## **Interview Tips (Hinglish)**

### **1. Mid Calculation - Overflow Se Bacho**
```java
// Wrong (overflow possible for large arrays)
int mid = (left + right) / 2;

// Correct
int mid = left + (right - left) / 2;

// Alternative (unsigned shift)
int mid = (left + right) >>> 1;
```

### **2. Loop Condition - Jab Tak left <= right**
- `while(left <= right)` - jab search space empty ho jaye
- `while(left < right)` - jab last element bacha ho (peak element, min in rotated)

### **3. When to use which?**

| Condition | Use |
|-----------|-----|
| Target exists, need index | `left <= right` |
| Need first/last occurrence | `left <= right` with result variable |
| Find minimum/maximum | `left < right` |
| Binary search on answer | `left < right` |

### **4. Common Mistakes:**
- `mid = (left + right) / 2` → overflow ho sakta hai
- Loop condition galat lagana → infinite loop
- Boundary update galat karna (`mid` vs `mid+1` vs `mid-1`)
- Sorted array nahi hai to binary search nahi kar sakte

### **5. Edge Cases Hamesha Test Karo:**
- Empty array
- Single element
- Target first element
- Target last element
- Target not present
- Duplicates
- Negative numbers

---

## **Pro Tips for Interviews**

1. **Pehle brute force batao** - "Mai O(n) kar sakta hun, lekin O(log n) mein karna hai"

2. **Draw karo** - Binary search ko explain karte time array draw karo

3. **Invariant maintain karo** - Hamesha socho ki left aur right kya represent kar rahe hain

4. **Dry run karo** - Chhoti example pe manually run karo

5. **Edge cases pehle discuss karo** - Empty array, duplicates, target out of bounds
