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

## 🎯 Binary Search Core Concept

  Binary search ka magic hai - har step mein search space ko **half** kar dena. Chahe array ho ya answer space, agar **monotonic property** hai (true/false pattern), toh binary search lag sakta hai.

```java
// Standard template
int left = 0, right = n-1;
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (condition(mid)) {
        return mid;  // or right = mid - 1
    } else if (condition2) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
```

---

## 📋 20 Hard Binary Search Problems

---

### 1. Search in Rotated Sorted Array
**Problem:** `[4,5,6,7,0,1,2]`, target=0 → Find index. Rotated sorted array mein search.

**Approach:** Pehle identify karo ki mid kis half mein hai (left sorted ya right sorted), then decide kaha search karna hai.

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

>   Array do parts mein bata hai - ek increasing part, fir rotation. Mid ko dekho, pata karo left side sorted hai ya right side. Target agar sorted part mein hai toh waha search karo, warna dusre part mein.

**Time:** O(log n)

---

### 2. Find Minimum in Rotated Sorted Array
**Problem:** `[4,5,6,7,0,1,2]` → return 0 (minimum element)

**Approach:** Binary search. Agar mid > right, toh minimum right side mein hai. Warna left side mein.

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

>   Compare nums[mid] with nums[right]. Agar mid > right, matlab rotation point right side mein hai, isliye left = mid+1. Warna right = mid. End mein left aur right same point pe milenge jo minimum hai.

**Time:** O(log n)

---

### 3. Find Peak Element
**Problem:** Array mein koi bhi peak element (nums[i] > nums[i-1] and nums[i] > nums[i+1]) return karo.

**Approach:** Binary search. Agar mid < mid+1, toh peak right side mein hai, warna left side mein.

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

>   Binary search se peak dhundhna. Agar mid apne se next element se chota hai, matlab increasing slope hai, toh peak aage hai. Warna decreasing slope hai, toh peak piche hai.

**Time:** O(log n)

---

### 4. Search in Rotated Sorted Array II (With Duplicates)
**Problem:** Rotated array with duplicates. Target find karo.

**Approach:** Previous approach + duplicate skip logic. Jab nums[left] == nums[mid] == nums[right] ho, toh left++ aur right-- karo.

```java
public boolean search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return true;
        
        // Duplicates handle karo
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

>   Duplicates hone se problem ye hai ki hum pata nahi laga sakte ki kaunsa half sorted hai. Jab left, mid, right teeno equal ho, toh left++ aur right-- karke duplicates skip karo.

**Time:** O(log n) average, O(n) worst case (when many duplicates)

---

### 5. Median of Two Sorted Arrays
**Problem:** Do sorted arrays ka median find karo. O(log(min(m,n))) time mein.

**Approach:** Chhoti array pe binary search. Partition point dhundho jaha left partition ka max <= right partition ka min.

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return findMedianSortedArrays(nums2, nums1);
    }
    
    int m = nums1.length, n = nums2.length;
    int left = 0, right = m;
    int totalLeft = (m + n + 1) / 2;
    
    while (left <= right) {
        int i = left + (right - left) / 2;  // nums1 se kitne elements left partition mein
        int j = totalLeft - i;              // nums2 se kitne elements left partition mein
        
        int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];
        
        if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
            // Correct partition mil gaya
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

>   Chhoti array pe binary search karo. Partition point i find karo jisse left side ke saare elements <= right side ke saare elements. Total left side elements = (m+n+1)/2 hona chahiye.

**Time:** O(log(min(m,n)))

---

### 6. Kth Smallest Element in a Sorted Matrix
**Problem:** Row-wise and column-wise sorted matrix mein kth smallest element.

**Approach:** Binary search on answer (value space). Count kitne elements <= mid hain.

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
    
    while (row >= 0 && col < n) {
        if (matrix[row][col] <= target) {
            count += row + 1;
            col++;
        } else {
            row--;
        }
    }
    return count;
}
```

>   Answer space pe binary search (min value se max value tak). Mid ke liye count karo kitne elements <= mid hain. Agar count < k, toh answer bada hai, warna chota. Count efficiently karne ke liye matrix properties use karo.

**Time:** O(n log(range))

---

### 7. Find K Closest Elements
**Problem:** Sorted array se k closest elements to x find karo.

**Approach:** Binary search se insertion point find karo, then two pointers se expand karo.

```java
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int left = 0, right = arr.length - k;
    
    // Binary search to find the starting point
    while (left < right) {
        int mid = left + (right - left) / 2;
        
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

>   Starting point find karo binary search se. Compare karo: arr[mid] aur arr[mid+k] mein se kaun x ke close hai. Agar arr[mid] ka distance zyada hai, toh left = mid+1, warna right = mid.

**Time:** O(log(n-k) + k)

---

### 8. Capacity to Ship Packages Within D Days
**Problem:** Packages weights array, D days mein ship karna hai. Minimum capacity kya honi chahiye?

**Approach:** Binary search on answer (capacity). Minimum capacity = max weight, maximum capacity = sum of weights.

```java
public int shipWithinDays(int[] weights, int days) {
    int left = 0, right = 0;
    for (int w : weights) {
        left = Math.max(left, w);
        right += w;
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
    int currentLoad = 0;
    int daysNeeded = 1;
    
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

>   Capacity pe binary search. Check karo ki given capacity se D days mein ship ho sakta hai ya nahi. Agar ho sakta hai, toh capacity kam karo, warna badhao.

**Time:** O(n log(sum - max))

---

### 9. Koko Eating Bananas
**Problem:** Bananas piles, H hours mein khaani hain. Minimum eating speed k?

**Approach:** Binary search on answer (speed). Speed 1 se max pile tak.

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

>   Speed pe binary search. Har pile ke liye hours = ceil(pile/speed) calculate karo. Total hours <= h hona chahiye.

**Time:** O(n log(max))

---

### 10. Split Array Largest Sum
**Problem:** Array ko m parts mein split karna hai. Minimize the largest sum of any part.

**Approach:** Binary search on answer (largest sum). Lower bound = max element, upper bound = sum of all.

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

>   Maximum sum pe binary search. Check karo ki maxSum se m parts mein split ho sakta hai ya nahi. Agar ho sakta hai, toh maxSum kam karo, warna badhao.

**Time:** O(n log(sum - max))

---

### 11. Find the Duplicate Number
**Problem:** [1..n] numbers, ek duplicate hai. Find it without modifying array, O(1) space.

**Approach:** Binary search on value space (1 to n). Count numbers <= mid.

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

>   Value space (1 to n) pe binary search. Count karo kitne numbers <= mid hain. Agar count > mid, matlab duplicate left side mein hai, warna right side mein.

**Time:** O(n log n)

---

### 12. Find Minimum in Rotated Sorted Array II (With Duplicates)
**Problem:** Rotated sorted array with duplicates mein minimum find karo.

**Approach:** Duplicates skip karte hue binary search.

```java
public int findMin(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[right]) {
            left = mid + 1;
        } else if (nums[mid] < nums[right]) {
            right = mid;
        } else {
            right--;  // Duplicates skip karo
        }
    }
    return nums[left];
}
```

>   Jab nums[mid] == nums[right] ho, toh right-- karke duplicate skip karo. Baaki logic same as without duplicates.

**Time:** O(log n) average, O(n) worst case

---

### 13. Time Based Key-Value Store
**Problem:** set(key, value, timestamp), get(key, timestamp) → value at that timestamp or previous.

**Approach:** HashMap of key -> List of pairs (timestamp, value). Get mein binary search.

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

>   Har key ke liye timestamps sorted order mein store karo. Get mein binary search se <= timestamp wala latest value dhundho.

**Time:** Set: O(1), Get: O(log n)

---

### 14. Search in a Sorted Array of Unknown Size
**Problem:** Array size pata nahi hai. get(index) method hai. Target find karo.

**Approach:** Pehle boundaries find karo (exponential search), phir binary search.

```java
public int search(ArrayReader reader, int target) {
    int left = 0, right = 1;
    
    // Find boundaries
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

>   Pehle exponential growth se right boundary find karo (2, 4, 8, ...). Phir normal binary search.

**Time:** O(log position)

---

### 15. Maximum Number of Removable Characters
**Problem:** String s, string p, removable array. Remove characters from s in given order. Maximum removals possible such that p still subsequence of s.

**Approach:** Binary search on k (number of removals). Check function mein p subsequence hai ya nahi.

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

>   Removals count pe binary search. Check karo ki k removals ke baad bhi p subsequence hai ya nahi.

**Time:** O((n+m) log k)

---

### 16. Minimum Time to Complete Trips
**Problem:** buses ka time array hai. Total trips banana hai. Minimum time kya hoga?

**Approach:** Binary search on answer (time). Lower bound = 1, upper bound = min(time) * totalTrips.

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

>   Time pe binary search. Given time mein totalTrips complete ho sakti hai ya nahi check karo. GivenTime / busTime = us bus ki trips.

**Time:** O(n log(max))

---

### 17. Minimum Number of Days to Make m Bouquets
**Problem:** Bloom day array. m bouquets banana hai, har bouquet mein k adjacent flowers chahiye. Minimum days?

**Approach:** Binary search on answer (days). Check function mein kitne bouquets ban sakte hain.

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

>   Days pe binary search. Given days mein kitne bouquets ban sakte hain check karo. Adjacent flowers count karo.

**Time:** O(n log(max))

---

### 18. Find the Smallest Divisor Given a Threshold
**Problem:** Array nums, threshold. Divisor d dhundho jisse sum of ceil(nums[i]/d) <= threshold.

**Approach:** Binary search on divisor. Lower bound = 1, upper bound = max(nums).

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

>   Divisor pe binary search. Sum calculate karo ceil division se. Agar sum <= threshold, toh divisor chota kar sakte ho.

**Time:** O(n log(max))

---

### 19. Find Right Interval
**Problem:** Intervals array [[1,2],[2,3],[3,4]]. Har interval ke liye right interval find karo (start >= current end).

**Approach:** Starts sort karo, binary search for each interval's end.

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

>   Start values ko sort karo with original indices. Har interval ke end ke liye binary search karo pehla start >= end dhundhne ke liye.

**Time:** O(n log n)

---

### 20. Maximum Profit in Job Scheduling
**Problem:** startTime[], endTime[], profit[]. Non-overlapping jobs se max profit.

**Approach:** Sort by end time. DP + binary search. For each job, find last non-conflicting job.

```java
public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length;
    Job[] jobs = new Job[n];
    
    for (int i = 0; i < n; i++) {
        jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
    }
    Arrays.sort(jobs, (a, b) -> a.end - b.end);
    
    int[] dp = new int[n];  // dp[i] = max profit up to job i
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

>   Jobs ko end time se sort karo. DP[i] = max profit tak i jobs consider karke. Current job include karna hai toh binary search se last non-conflicting job dhundho.

**Time:** O(n log n)

---

## 🎯 Hard Binary Search Interview Tips

1. **Identify Binary Search on Answer:**
   - Jab "minimum maximum" ya "maximum minimum" ho
   - Jab "capacity", "speed", "days", "time" pe BS karna ho
   - Jab monotonic property ho (true/false pattern)

2. **Template Yaad Rakho:**
```java
// For finding minimum valid value
while (left < right) {
    int mid = left + (right - left) / 2;
    if (check(mid)) {
        right = mid;
    } else {
        left = mid + 1;
    }
}

// For finding maximum valid value
while (left < right) {
    int mid = left + (right - left + 1) / 2;
    if (check(mid)) {
        left = mid;
    } else {
        right = mid - 1;
    }
}
```

3. **Rotated Array Patterns:**
   - Compare with left or right based on problem
   - Duplicates hone par extra handling
   - Identify sorted half always

4. **Binary Search on Answer Checklist:**
   - What is my search space? (min to max)
   - Is monotonic condition satisfied? (false..false, true..true)
   - Can I check a mid value efficiently?

5. **Common Mistakes:**
   - Integer overflow: Use `mid = left + (right-left)/2`
   - Off-by-one errors: Carefully choose left <= right vs left < right
   - Wrong boundary initialization
   - Forgetting to handle duplicates
