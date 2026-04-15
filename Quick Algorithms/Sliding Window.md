
# 💡 Sliding Window 
**Logic:** Do pointers `left` aur `right` lo. `right` window expand karega, `left` window shrink karega. Beech mein condition maintain karni hai.

```
Template:
while (right < n) {
    // 1. Current element ko window mein add karo
    // 2. Jab tak condition violate ho, left ko aage badhao (shrink)
    // 3. Answer update karo
    // 4. Right ko aage badhao
}
```

---

## 📘 FIXED SIZE WINDOW (Window Size Fixed Hai)

### 🎯 1. Maximum Sum Subarray of Size K
**Problem:** Array mein size `k` ki har subarray ka sum nikalo aur maximum return karo.
**Logic:** Pehle `k` elements ka sum lo. Phir slide karte waqt pichla element minus, naya element plus.

```java
public int maxSumSubarray(int[] arr, int k) {
    int n = arr.length;
    if (n < k) return -1;
    
    // Pehli window ka sum
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i];
    
    int maxSum = windowSum;
    
    // Slide karo
    for (int i = k; i < n; i++) {
        windowSum = windowSum - arr[i - k] + arr[i];
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

### 🎯 2. First Negative in Every Window of Size K
**Problem:** Har size `k` ki window ka pehla negative number batao.
**Logic:** Queue use karo negative numbers ke indices store karne ke liye.

```java
public int[] firstNegative(int[] arr, int k) {
    int n = arr.length;
    int[] result = new int[n - k + 1];
    Queue<Integer> q = new LinkedList<>(); // Negative numbers ke indices
    
    int right = 0, left = 0;
    int idx = 0;
    
    while (right < n) {
        // Negative number mila to queue mein daalo
        if (arr[right] < 0) q.add(right);
        
        // Window size k tak pahunch gaye
        if (right - left + 1 == k) {
            // Queue khali nahi hai to front pe answer hai
            if (!q.isEmpty()) result[idx] = arr[q.peek()];
            else result[idx] = 0;
            idx++;
            
            // Left pointer badhane se pehle check karo
            if (!q.isEmpty() && q.peek() == left) q.poll();
            left++;
        }
        right++;
    }
    return result;
}
```

### 🎯 3. Count Occurrences of Anagram
**Problem:** String `s` mein string `p` ke saare anagrams ka count batao.
**Logic:** Character frequency map maintain karo. Window size `p.length()` fix rakho.

```java
public int countAnagrams(String s, String p) {
    int k = p.length();
    if (k > s.length()) return 0;
    
    int[] pFreq = new int[26];
    int[] sFreq = new int[26];
    
    // Pattern ki frequency
    for (char c : p.toCharArray()) pFreq[c - 'a']++;
    
    int count = 0;
    int left = 0, right = 0;
    
    while (right < s.length()) {
        // Current char add karo
        sFreq[s.charAt(right) - 'a']++;
        
        // Window size k ho gayi
        if (right - left + 1 == k) {
            if (Arrays.equals(pFreq, sFreq)) count++;
            
            // Left remove karo
            sFreq[s.charAt(left) - 'a']--;
            left++;
        }
        right++;
    }
    return count;
}
```

### 🎯 4. Maximum of All Subarrays of Size K
**Problem:** Har size `k` ki window ka maximum element return karo.
**Logic:** **Deque** use karo. Decreasing order mein elements rakho (front pe hamesha max hoga).

```java
public int[] maxSlidingWindow(int[] arr, int k) {
    int n = arr.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> dq = new LinkedList<>(); // Indices store karega
    
    int idx = 0;
    for (int right = 0; right < n; right++) {
        // Deque se chhote elements hatao (back se)
        while (!dq.isEmpty() && arr[dq.peekLast()] <= arr[right]) {
            dq.pollLast();
        }
        dq.addLast(right);
        
        // Window ke bahar wale elements hatao (front se)
        if (dq.peekFirst() <= right - k) {
            dq.pollFirst();
        }
        
        // Window complete hone pe front is the answer
        if (right >= k - 1) {
            result[idx++] = arr[dq.peekFirst()];
        }
    }
    return result;
}
```

---

## 📗 VARIABLE SIZE WINDOW (Window Size Variable Hai)

### 🎯 5. Longest Substring Without Repeating Characters
**Problem:** Sabse lambi substring jisme saare characters unique ho.
**Logic:** HashMap ya Set use karo. Jab duplicate mile, left se delete karte jao.

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int left = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        // Duplicate hai to left se delete karo
        while (set.contains(c)) {
            set.remove(s.charAt(left));
            left++;
        }
        
        set.add(c);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 🎯 6. Longest Substring with At Most K Distinct Characters
**Problem:** Substring mein maximum `k` unique characters allowed hain. Sabse lambi length batao.
**Logic:** HashMap maintain karo frequency ke saath. Jab map size `> k`, left shrink karo.

```java
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        map.put(c, map.getOrDefault(c, 0) + 1);
        
        // Condition violate: Distinct characters > k
        while (map.size() > k) {
            char leftChar = s.charAt(left);
            map.put(leftChar, map.get(leftChar) - 1);
            if (map.get(leftChar) == 0) map.remove(leftChar);
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 🎯 7. Minimum Window Substring
**Problem:** String `s` mein aisi minimum length ki substring dhundho jisme `t` ke saare characters maujood ho.
**Logic:** Do maps rakho. Jab saare required characters mil jayein, left shrink karte raho.

```java
public String minWindow(String s, String t) {
    if (s.length() < t.length()) return "";
    
    Map<Character, Integer> tMap = new HashMap<>();
    for (char c : t.toCharArray()) tMap.put(c, tMap.getOrDefault(c, 0) + 1);
    
    Map<Character, Integer> sMap = new HashMap<>();
    int left = 0, minLen = Integer.MAX_VALUE, minStart = 0;
    int required = tMap.size(), formed = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        sMap.put(c, sMap.getOrDefault(c, 0) + 1);
        
        // Agar current character ka count tMap ke barabar ho gaya
        if (tMap.containsKey(c) && sMap.get(c).intValue() == tMap.get(c).intValue()) {
            formed++;
        }
        
        // Jab saare required characters mil jayein, window shrink karo
        while (left <= right && formed == required) {
            // Update minimum length
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                minStart = left;
            }
            
            // Left character hatao
            char leftChar = s.charAt(left);
            sMap.put(leftChar, sMap.get(leftChar) - 1);
            if (tMap.containsKey(leftChar) && sMap.get(leftChar) < tMap.get(leftChar)) {
                formed--;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}
```

### 🎯 8. Longest Repeating Character Replacement
**Problem:** String mein maximum `k` characters replace kar sakte ho. Sabse lambi substring batao jisme saare characters same ho.
**Logic:** Window mein maximum frequency wale character ko chhod kar baaki sab replace honge. `(windowLength - maxFreq) <= k` hona chahiye.

```java
public int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0, maxFreq = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        freq[c - 'A']++;
        maxFreq = Math.max(maxFreq, freq[c - 'A']);
        
        // Replacements needed = window length - maxFreq
        while ((right - left + 1) - maxFreq > k) {
            freq[s.charAt(left) - 'A']--;
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 🎯 9. Fruit Into Baskets
**Problem:** Do baskets hain. Har basket mein ek type ka fruit. Maximum kitne fruits collect kar sakte ho? (At most 2 distinct numbers).
**Logic:** Bilkul **Longest Substring with At Most K Distinct Characters** jaisa hai, yahan `k = 2`.

```java
public int totalFruit(int[] fruits) {
    Map<Integer, Integer> map = new HashMap<>();
    int left = 0, maxFruits = 0;
    
    for (int right = 0; right < fruits.length; right++) {
        map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
        
        while (map.size() > 2) {  // 2 types allowed hain (2 baskets)
            map.put(fruits[left], map.get(fruits[left]) - 1);
            if (map.get(fruits[left]) == 0) map.remove(fruits[left]);
            left++;
        }
        
        maxFruits = Math.max(maxFruits, right - left + 1);
    }
    return maxFruits;
}
```

### 🎯 10. Subarray Sum Equals K (Prefix Sum + HashMap)
**Problem:** Array mein kitne subarrays ka sum `k` ke barabar hai.
**Logic:** Ye sliding window nahi hai (negative numbers ki wajah se), lekin pattern important hai. Prefix Sum + HashMap use karo.

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // Empty subarray ka sum 0
    
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        if (map.containsKey(sum - k)) {
            count += map.get(sum - k);
        }
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
}
```

### 🎯 11. Subarray Product Less Than K
**Problem:** Kitne subarrays ka product `k` se kam hai.
**Logic:** Sliding Window. Jab product `>= k` ho jaye, left se divide karte jao.

```java
public int numSubarrayProductLessThanK(int[] nums, int k) {
    if (k <= 1) return 0;
    
    int product = 1, left = 0, count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        product *= nums[right];
        
        while (product >= k) {
            product /= nums[left];
            left++;
        }
        
        // Right se khatam hone wale saare subarrays valid hain
        count += right - left + 1;
    }
    return count;
}
```

### 🎯 12. Maximum Consecutive Ones III
**Problem:** Array mein maximum `k` zeros ko ones mein flip kar sakte ho. Consecutive ones ki maximum length batao.
**Logic:** Window mein zeros ka count `<= k` rakho.

```java
public int longestOnes(int[] nums, int k) {
    int left = 0, zeroCount = 0, maxLen = 0;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) zeroCount++;
        
        while (zeroCount > k) {
            if (nums[left] == 0) zeroCount--;
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

---

## 📋 Sliding Window Cheat Sheet

| Problem Type | Condition | Trick |
| :--- | :--- | :--- |
| **Fixed Size Window** | Window size `== k` | `if(right - left + 1 == k)` |
| **At Most K Distinct** | `map.size() > k` | Shrink till valid |
| **Exactly K Distinct** | `map.size() > k` | `(AtMostK - AtMostK-1)` |
| **Minimum Window** | `formed == required` | Shrink to minimize |
| **Max Replacement** | `(len - maxFreq) > k` | Track maxFreq in window |
| **Negative Numbers** | Sum condition | Use **Prefix Sum + HashMap** |

---

## 🔥 Advanced Sliding Window Variations

### 🎯 13. Binary Subarrays With Sum (Exactly K)
**Problem:** Array mein exactly `k` ones wale subarrays ka count.
**Logic:** `AtMost(k) - AtMost(k-1)` trick.

```java
public int numSubarraysWithSum(int[] nums, int goal) {
    return atMostK(nums, goal) - atMostK(nums, goal - 1);
}

private int atMostK(int[] nums, int k) {
    if (k < 0) return 0;
    int left = 0, sum = 0, count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum > k) {
            sum -= nums[left];
            left++;
        }
        count += right - left + 1;
    }
    return count;
}
```

### 🎯 14. Count Number of Nice Subarrays (Exactly K Odds)
**Problem:** Array mein exactly `k` odd numbers wale subarrays ka count.
**Logic:** Wahi `AtMost(k) - AtMost(k-1)` trick.

```java
public int numberOfSubarrays(int[] nums, int k) {
    return atMostKOdds(nums, k) - atMostKOdds(nums, k - 1);
}

private int atMostKOdds(int[] nums, int k) {
    if (k < 0) return 0;
    int left = 0, oddCount = 0, count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] % 2 == 1) oddCount++;
        
        while (oddCount > k) {
            if (nums[left] % 2 == 1) oddCount--;
            left++;
        }
        count += right - left + 1;
    }
    return count;
}
```

### 🎯 15. Subarrays with K Different Integers
**Problem:** Array mein exactly `k` distinct integers wale subarrays ka count.
**Logic:** Wahi `AtMostKDistinct(k) - AtMostKDistinct(k-1)` trick.

```java
public int subarraysWithKDistinct(int[] nums, int k) {
    return atMostKDistinct(nums, k) - atMostKDistinct(nums, k - 1);
}

private int atMostKDistinct(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    int left = 0, count = 0;
    
    for (int right = 0; right < nums.length; right++) {
        map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
        
        while (map.size() > k) {
            map.put(nums[left], map.get(nums[left]) - 1);
            if (map.get(nums[left]) == 0) map.remove(nums[left]);
            left++;
        }
        count += right - left + 1;
    }
    return count;
}
```

---

## 📊 Summary Table (Kab Kya Lagana Hai)

| Condition | Approach |
| :--- | :--- |
| **Fixed Window Size** | Simple loop, subtract old, add new |
| **Max Length with Condition** | Expand right, shrink left till valid |
| **Min Length with Condition** | Expand right, when valid, shrink left to find min |
| **Count of Subarrays** | Usually `AtMost(K) - AtMost(K-1)` trick |
| **Negative Numbers in Array** | Prefix Sum + HashMap (Sliding Window kaam nahi karega) |
| **Max/Min in Window** | **Deque** (Monotonic Queue) |
