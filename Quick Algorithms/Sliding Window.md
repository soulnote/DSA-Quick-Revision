
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


---

## 🎯 Sliding Window Core Concepts 

**Sliding Window = Expand right pointer, contract left pointer to maintain valid window**

**Types of Sliding Window:**
1. **Fixed Size Window:** Window size fix hai, bas right pointer badhao, left bhi badhao
2. **Variable Size Window:** Window valid condition pe expand, invalid pe contract
3. **At Most / At Least K:** Condition based window

**Common Patterns:**
- **Maximum sum subarray of size k** → Fixed window
- **Longest substring without repeating characters** → Variable window
- **Minimum window substring** → Two hashmaps + shrinking
- **Subarrays with k different integers** → At most k - at most (k-1)

---

## 📋 20 Hard Sliding Window Problems

---

### 1. Maximum Sum Subarray of Size K (Fixed Window)
**Problem:** Array mein k size ke subarray ka maximum sum.

**Approach:** Window maintain karo, sum calculate karo, slide karte raho.

```java
public int maxSumSubarray(int[] nums, int k) {
    int windowSum = 0;
    int maxSum = Integer.MIN_VALUE;
    
    for (int i = 0; i < nums.length; i++) {
        windowSum += nums[i];
        
        if (i >= k - 1) {  // Window size reached
            maxSum = Math.max(maxSum, windowSum);
            windowSum -= nums[i - k + 1];  // Remove leftmost
        }
    }
    return maxSum;
}
```

>  Window ka sum maintain karo. Jab window size k ho jaye, max update karo aur left element window se hata do. Right pointer badhate jao.

**Time:** O(n)

---

### 2. Longest Substring Without Repeating Characters
**Problem:** String mein longest substring jisme koi character repeat na ho.

**Approach:** HashMap se last index store karo. Agar duplicate mile toh left pointer ko last occurrence+1 pe le jao.

```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastIndex = new HashMap<>();
    int left = 0;
    int maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
            left = lastIndex.get(c) + 1;  // Move left to after duplicate
        }
        
        lastIndex.put(c, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

>  Right pointer badhao. Agar character already window mein hai, toh left pointer ko uske last index+1 pe le jao. Har step pe max length update karo.

**Time:** O(n)

---

### 3. Minimum Window Substring (Hard)
**Problem:** String s mein smallest substring jisme string t ke saare characters (count included) aayein.

**Approach:** Two hashmaps - required aur current. Expand right, jab valid ho toh shrink left.

```java
public String minWindow(String s, String t) {
    if (s.length() < t.length()) return "";
    
    Map<Character, Integer> need = new HashMap<>();
    Map<Character, Integer> have = new HashMap<>();
    
    for (char c : t.toCharArray()) {
        need.put(c, need.getOrDefault(c, 0) + 1);
    }
    
    int left = 0;
    int required = need.size();
    int formed = 0;
    int[] result = {-1, 0, 0};  // {length, left, right}
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        have.put(c, have.getOrDefault(c, 0) + 1);
        
        if (need.containsKey(c) && have.get(c).intValue() == need.get(c).intValue()) {
            formed++;
        }
        
        // Shrink window from left while valid
        while (left <= right && formed == required) {
            c = s.charAt(left);
            
            if (result[0] == -1 || right - left + 1 < result[0]) {
                result[0] = right - left + 1;
                result[1] = left;
                result[2] = right;
            }
            
            have.put(c, have.get(c) - 1);
            if (need.containsKey(c) && have.get(c) < need.get(c)) {
                formed--;
            }
            left++;
        }
    }
    return result[0] == -1 ? "" : s.substring(result[1], result[2] + 1);
}
```

>  Need map mein t ke characters count rakho. Right pointer badhao, have map update karo. Jab formed == required (saare characters mil gaye), tab left pointer shrink karo minimum window find karne ke liye.

**Time:** O(m + n)

---

### 4. Longest Repeating Character Replacement
**Problem:** At most k characters change kar sakte ho to make longest substring with same character.

**Approach:** Window mein most frequent character count track karo. Window size - maxCount > k hai toh shrink karo.

```java
public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int left = 0;
    int maxCount = 0;  // Max frequency in current window
    int maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        count[c - 'A']++;
        maxCount = Math.max(maxCount, count[c - 'A']);
        
        // If we need to replace more than k characters, shrink window
        if (right - left + 1 - maxCount > k) {
            count[s.charAt(left) - 'A']--;
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

>  Window mein max frequency wala character track karo. Window size - maxCount = kitne characters replace karne honge. Agar ye k se zyada ho, toh left pointer badhao.

**Time:** O(n)

---

### 5. Permutation in String
**Problem:** s2 mein s1 ka koi permutation exists karta hai as substring?

**Approach:** Fixed window of size s1.length(). Count match karo.

```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;
    
    int[] s1Count = new int[26];
    int[] s2Count = new int[26];
    
    for (char c : s1.toCharArray()) {
        s1Count[c - 'a']++;
    }
    
    int left = 0;
    for (int right = 0; right < s2.length(); right++) {
        s2Count[s2.charAt(right) - 'a']++;
        
        if (right >= s1.length() - 1) {
            if (matches(s1Count, s2Count)) return true;
            s2Count[s2.charAt(left) - 'a']--;
            left++;
        }
    }
    return false;
}

private boolean matches(int[] s1Count, int[] s2Count) {
    for (int i = 0; i < 26; i++) {
        if (s1Count[i] != s2Count[i]) return false;
    }
    return true;
}
```

>  Fixed window size s1.length(). Har window mein s2 ka count array check karo ki s1 ke count se match karta hai ya nahi. Window slide karte raho.

**Time:** O(26 * n) = O(n)

---

### 6. Subarrays with K Different Integers
**Problem:** Exactly k different integers wale subarrays count karo.

**Approach:** AtMost(k) - AtMost(k-1). Exactly k = at most k - at most (k-1).

```java
public int subarraysWithKDistinct(int[] nums, int k) {
    return atMostK(nums, k) - atMostK(nums, k - 1);
}

private int atMostK(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0;
    int result = 0;
    
    for (int right = 0; right < nums.length; right++) {
        count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);
        
        while (count.size() > k) {
            count.put(nums[left], count.get(nums[left]) - 1);
            if (count.get(nums[left]) == 0) count.remove(nums[left]);
            left++;
        }
        result += right - left + 1;  // All subarrays ending at right
    }
    return result;
}
```

>  Exactly k ka formula: atMost(k) - atMost(k-1). AtMost(k) wale function mein window expand karo, jab distinct count k se zyada ho toh shrink karo. Har step pe window size result mein add karo.

**Time:** O(n)

---

### 7. Max Consecutive Ones III
**Problem:** At most k zeros flip kar sakte ho. Maximum consecutive ones length?

**Approach:** Zero count track karo. Zero count > k toh shrink karo.

```java
public int longestOnes(int[] nums, int k) {
    int left = 0;
    int zeroCount = 0;
    int maxLen = 0;
    
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

>  Window mein zeros count karo. Agar zeroCount > k ho jaye, left pointer badhao jab tak zeroCount <= k na ho jaye.

**Time:** O(n)

---

### 8. Fruit Into Baskets
**Problem:** At most 2 types of fruits (like subarray with at most 2 distinct numbers).

**Approach:** AtMost(2) wala sliding window.

```java
public int totalFruit(int[] fruits) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0;
    int maxLen = 0;
    
    for (int right = 0; right < fruits.length; right++) {
        count.put(fruits[right], count.getOrDefault(fruits[right], 0) + 1);
        
        while (count.size() > 2) {
            count.put(fruits[left], count.get(fruits[left]) - 1);
            if (count.get(fruits[left]) == 0) count.remove(fruits[left]);
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

>  Exactly fruit basket wala problem - at most 2 distinct numbers. Window mein distinct count track karo, agar 2 se zyada ho jaye toh left se shrink karo.

**Time:** O(n)

---

### 9. Minimum Size Subarray Sum
**Problem:** Smallest subarray jiska sum >= target.

**Approach:** Window expand karo, sum maintain karo. Jab sum >= target, shrink karo aur answer update karo.

```java
public int minSubArrayLen(int target, int[] nums) {
    int left = 0;
    int sum = 0;
    int minLen = Integer.MAX_VALUE;
    
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        
        while (sum >= target) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left];
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

>  Right pointer badhao, sum increase karo. Jab sum >= target, tab left pointer badhake smallest window find karo.

**Time:** O(n)

---

### 10. Maximum Points You Can Obtain from Cards
**Problem:** Array se either start se ya end se k cards le sakte ho. Maximum sum?

**Approach:** Total sum - minimum subarray sum of length n-k.

```java
public int maxScore(int[] cardPoints, int k) {
    int n = cardPoints.length;
    int windowSize = n - k;
    int totalSum = 0;
    int windowSum = 0;
    
    for (int num : cardPoints) totalSum += num;
    
    // Find minimum sum of windowSize consecutive cards
    for (int i = 0; i < windowSize; i++) {
        windowSum += cardPoints[i];
    }
    
    int minWindowSum = windowSum;
    for (int i = windowSize; i < n; i++) {
        windowSum += cardPoints[i] - cardPoints[i - windowSize];
        minWindowSum = Math.min(minWindowSum, windowSum);
    }
    
    return totalSum - minWindowSum;
}
```

>  Total sum - minimum sum of n-k consecutive elements. Yeh approach hai - jo cards nahi le rahe unka sum minimum hona chahiye.

**Time:** O(n)

---

### 11. Longest Substring with At Most K Distinct Characters
**Problem:** At most k distinct characters wali longest substring.

**Approach:** AtMostK wala sliding window.

```java
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    if (k == 0) return 0;
    
    Map<Character, Integer> count = new HashMap<>();
    int left = 0;
    int maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        count.put(c, count.getOrDefault(c, 0) + 1);
        
        while (count.size() > k) {
            char leftChar = s.charAt(left);
            count.put(leftChar, count.get(leftChar) - 1);
            if (count.get(leftChar) == 0) count.remove(leftChar);
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

>  Window mein distinct characters count karo. Agar k se zyada ho jaye, left se shrink karo jab tak distinct count <= k na ho jaye.

**Time:** O(n)

---

### 12. Number of Substrings Containing All Three Characters
**Problem:** String mein substrings count karo jisme 'a', 'b', 'c' teeno aayein.

**Approach:** Sliding window with count array. Jab teeno mil jayein, baaki substrings count karo.

```java
public int numberOfSubstrings(String s) {
    int[] count = new int[3];
    int left = 0;
    int result = 0;
    
    for (int right = 0; right < s.length(); right++) {
        count[s.charAt(right) - 'a']++;
        
        while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
            result += s.length() - right;  // All substrings from left to end
            count[s.charAt(left) - 'a']--;
            left++;
        }
    }
    return result;
}
```

>  Jab window mein 'a','b','c' teeno aa jayein, tab left pointer ko aage badhate jao aur har step pe remaining substrings count karo (s.length() - right).

**Time:** O(n)

---

### 13. Count Number of Nice Subarrays
**Problem:** Exactly k odd numbers wale subarrays count karo.

**Approach:** Exactly k = atMost(k) - atMost(k-1).

```java
public int numberOfSubarrays(int[] nums, int k) {
    return atMostKOdd(nums, k) - atMostKOdd(nums, k - 1);
}

private int atMostKOdd(int[] nums, int k) {
    int left = 0;
    int oddCount = 0;
    int result = 0;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] % 2 == 1) oddCount++;
        
        while (oddCount > k) {
            if (nums[left] % 2 == 1) oddCount--;
            left++;
        }
        
        result += right - left + 1;
    }
    return result;
}
```

>  Exactly k odd numbers = atMost(k) - atMost(k-1). AtMost(k) function mein odd count track karo, k se zyada ho toh shrink karo.

**Time:** O(n)

---

### 14. Replace the Substring for Balanced String
**Problem:** String mein Q,W,E,R counts ko equal banana hai (n/4 each). Minimum substring replace?

**Approach:** Sliding window to find smallest substring jiske bahar ke characters ka count <= n/4.

```java
public int balancedString(String s) {
    int n = s.length();
    int target = n / 4;
    int[] count = new int[128];
    
    for (char c : s.toCharArray()) {
        count[c]++;
    }
    
    // Check if already balanced
    if (count['Q'] <= target && count['W'] <= target && 
        count['E'] <= target && count['R'] <= target) {
        return 0;
    }
    
    int left = 0;
    int minLen = n;
    
    for (int right = 0; right < n; right++) {
        count[s.charAt(right)]--;
        
        while (count['Q'] <= target && count['W'] <= target && 
               count['E'] <= target && count['R'] <= target) {
            minLen = Math.min(minLen, right - left + 1);
            count[s.charAt(left)]++;
            left++;
        }
    }
    return minLen;
}
```

>  Pehle total count nikaalo. Target = n/4. Window ke bahar wale characters ka count <= target hona chahiye. Window ke andar replace karna hai. Minimum such window find karo.

**Time:** O(n)

---

### 15. Maximum Erasure Value
**Problem:** Subarray with all unique elements ka maximum sum.

**Approach:** Unique elements wala sliding window + sum maintain karo.

```java
public int maximumUniqueSubarray(int[] nums) {
    Set<Integer> set = new HashSet<>();
    int left = 0;
    int sum = 0;
    int maxSum = 0;
    
    for (int right = 0; right < nums.length; right++) {
        while (set.contains(nums[right])) {
            set.remove(nums[left]);
            sum -= nums[left];
            left++;
        }
        
        set.add(nums[right]);
        sum += nums[right];
        maxSum = Math.max(maxSum, sum);
    }
    return maxSum;
}
```

>  Set maintain karo unique elements ke liye. Agar duplicate mile toh left se remove karo jab tak duplicate remove na ho jaye.

**Time:** O(n)

---

### 16. Grumpy Bookstore Owner
**Problem:** Grumpy array (1 means owner grumpy). Minutes X mein grumpy nahi rahega. Maximum satisfied customers?

**Approach:** Fixed window size X mein grumpy wale customers ka sum find karo.

```java
public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
    int n = customers.length;
    int alreadySatisfied = 0;
    
    // Customers already satisfied (when grumpy[i] == 0)
    for (int i = 0; i < n; i++) {
        if (grumpy[i] == 0) alreadySatisfied += customers[i];
    }
    
    // Find max additional customers from grumpy minutes window
    int extra = 0;
    int maxExtra = 0;
    
    for (int i = 0; i < n; i++) {
        if (grumpy[i] == 1) extra += customers[i];
        
        if (i >= minutes) {
            if (grumpy[i - minutes] == 1) extra -= customers[i - minutes];
        }
        
        maxExtra = Math.max(maxExtra, extra);
    }
    
    return alreadySatisfied + maxExtra;
}
```

>  Pehle already satisfied customers sum karo (jab grumpy=0). Phir fixed window size minutes mein grumpy=1 wale customers ka maximum sum find karo. Total = alreadySatisfied + maxExtra.

**Time:** O(n)

---

### 17. Subarrays with K Different Integers (Another variation)
**Problem:** Exactly k distinct integers. (Already covered above - Problem 6)

**Approach:** Same as problem 6.

---

### 18. Minimum Swaps to Group All 1's Together
**Problem:** Binary array mein all 1's ko group karne ke liye minimum swaps.

**Approach:** Fixed window of size = total ones. Window mein zeros count = swaps needed.

```java
public int minSwaps(int[] nums) {
    int totalOnes = 0;
    for (int num : nums) {
        if (num == 1) totalOnes++;
    }
    
    if (totalOnes == 0) return 0;
    
    int left = 0;
    int zerosInWindow = 0;
    int minSwaps = Integer.MAX_VALUE;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) zerosInWindow++;
        
        if (right >= totalOnes - 1) {
            minSwaps = Math.min(minSwaps, zerosInWindow);
            if (nums[left] == 0) zerosInWindow--;
            left++;
        }
    }
    return minSwaps;
}
```

>  Total ones count karo. Fixed window size = totalOnes. Har window mein zeros count karo - yahi swaps needed hain. Minimum zeros wali window answer hai.

**Time:** O(n)

---

### 19. Longest Substring with At Most Two Distinct Characters
**Problem:** At most 2 distinct characters wali longest substring.

**Approach:** Same as AtMostK with k=2.

```java
public int lengthOfLongestSubstringTwoDistinct(String s) {
    Map<Character, Integer> count = new HashMap<>();
    int left = 0;
    int maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        count.put(c, count.getOrDefault(c, 0) + 1);
        
        while (count.size() > 2) {
            char leftChar = s.charAt(left);
            count.put(leftChar, count.get(leftChar) - 1);
            if (count.get(leftChar) == 0) count.remove(leftChar);
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

>  Exactly AtMostK wala pattern with k=2. Map mein distinct characters count karo, 2 se zyada ho toh shrink karo.

**Time:** O(n)

---

### 20. Contains Duplicate III
**Problem:** Kya exist karta hai index i,j such that |i-j| <= k and |nums[i]-nums[j]| <= t?

**Approach:** Sliding window + TreeSet (binary search for values within range).

```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    if (k <= 0 || t < 0) return false;
    
    TreeSet<Long> window = new TreeSet<>();
    
    for (int i = 0; i < nums.length; i++) {
        long curr = (long) nums[i];
        
        // Find ceiling of curr - t
        Long ceiling = window.ceiling(curr - t);
        if (ceiling != null && ceiling <= curr + t) {
            return true;
        }
        
        window.add(curr);
        
        if (window.size() > k) {
            window.remove((long) nums[i - k]);
        }
    }
    return false;
}
```

>  Window size k maintain karo. TreeSet mein values store karo. Har naye element ke liye check karo ki curr-t se curr+t ke beech koi element exist karta hai ya nahi.

**Time:** O(n log k)

---


## 🎯 Hard Sliding Window Interview Tips

1. **Identify the problem type:**
   - **Fixed window:** Window size fixed hai (k given)
   - **Variable window:** Need to find min/max length satisfying condition
   - **AtMost/Exactly K:** Exactly K = atMost(K) - atMost(K-1)

2. **Template for Variable Window:**
```java
int left = 0;
for (int right = 0; right < n; right++) {
    // Add nums[right] to window
    while (window invalid) {
        // Remove nums[left] from window
        left++;
    }
    // Update answer
    ans = max(ans, right - left + 1);
}
```

3. **Template for Fixed Window:**
```java
for (int right = 0; right < n; right++) {
    // Add nums[right]
    if (right >= k - 1) {
        // Process window
        // Remove nums[right - k + 1]
    }
}
```

4. **Common Mistakes:**
   - Window ko valid condition pe shrink karna bhoolna
   - Exactly K wale problems mein atMost(K) - atMost(K-1) ka formula yaad rakhna
   - Left pointer ko right se aage jaane dena

5. **Optimization Techniques:**
   - Count arrays for small character sets (26 letters)
   - HashMap for larger alphabets
   - Pre-compute where possible

6. **When to use Sliding Window:**
   - **Subarray/Substring problems**
   - **Contiguous sequence requirements**
   - **Optimization with min/max length/sum**
   - **Frequency counting**

7. **The "AtMost K" Trick:**
   - Exactly K problems ko atMost(K) - atMost(K-1) mein convert karo
   - AtMost K ka implementation simple hota hai

