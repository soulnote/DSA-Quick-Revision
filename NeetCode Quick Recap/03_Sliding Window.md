# Problem: Longest Substring Without Repeating Characters

Given a string `s`, find the **length of the longest substring** without repeating characters.

---

### Example:

**Input:**
`s = "abcabcbb"`

**Output:**
`3`

**Explanation:**
The longest substring without repeating characters is `"abc"`, which has a length of `3`.

---

### Solution Code with Comments:

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // Map to store the most recent index of each character
        HashMap<Character, Integer> map = new HashMap<>();
        
        int left = 0, right = 0;   // Sliding window pointers
        int longest = 0;          // Track the length of the longest substring
        
        while (right < s.length() && left <= right) {
            char ch = s.charAt(right);
            
            if (!map.containsKey(ch)) {
                // If character not seen in current window, add it to map
                map.put(ch, right);
                right++;  // Expand window to the right
            } else {
                // If character is a duplicate, move left pointer ahead of its previous index
                left = Math.max(left, map.get(ch) + 1);
                map.remove(ch);  // Remove the character to allow re-insertion later
            }

            // Update the maximum length found so far
            int count = right - left;
            longest = Math.max(longest, count);
        }
        
        return longest;  // Return the length of the longest unique-character substring
    }
}
```

---

### Explanation:

* This is a **sliding window** approach using a HashMap to store characters and their indices.
* When a character is found that already exists in the current window, the `left` pointer jumps to the position **after** its last occurrence.
* The window always contains **unique characters** between `left` and `right`.
* The maximum length of such a window is tracked and returned.

---

# Problem: Longest Repeating Character Replacement

You are given a string `s` consisting of uppercase English letters and an integer `k`.
You can **replace at most `k` characters** in the string with any other uppercase letter.

Your goal is to **find the length of the longest substring** that contains the same letter after performing the replacements.

---

### Example:

**Input:**
`s = "ABAB", k = 2`

**Output:**
`4`

**Explanation:**
We can replace two `'A'`s with `'B'`s (or vice versa), resulting in `"BBBB"` or `"AAAA"`, which has length `4`.

---

### Solution Code with Comments:

```java
public class Solution {
    public int characterReplacement(String s, int k) {
        HashMap<Character, Integer> count = new HashMap<>();  // To track frequency of characters
        int res = 0;      // To store the length of longest valid window
        int l = 0;        // Left pointer of the window
        int maxf = 0;     // Max frequency of any character in the current window

        // Iterate with right pointer
        for (int r = 0; r < s.length(); r++) {
            // Increment count of the current character
            count.put(s.charAt(r), count.getOrDefault(s.charAt(r), 0) + 1);
            // Update max frequency in the window
            maxf = Math.max(maxf, count.get(s.charAt(r)));

            // If we need to replace more than k characters, shrink the window
            if ((r - l + 1) - maxf > k) {
                count.put(s.charAt(l), count.get(s.charAt(l)) - 1);  // Decrease count of left character
                l++;  // Shrink window from left
            }

            // Update the result with the size of the current valid window
            res = Math.max(res, r - l + 1);
        }

        return res;  // Return the length of the longest valid substring
    }
}
```

---

### Explanation:

* This uses a **sliding window** approach.
* `maxf` keeps track of the count of the most frequent character in the current window.
* `(window size - maxf)` tells us how many characters we need to replace to make all characters the same.
* If that number is more than `k`, we shrink the window from the left.
* We always keep track of the **longest valid window** seen so far.

---

# Problem: Permutation in String

Given two strings `s1` and `s2`, return `true` if **s2 contains a permutation of s1** — in other words, if any substring of `s2` is a rearrangement of `s1`.

---

### Example:

**Input:**
`s1 = "ab"`
`s2 = "eidbaooo"`

**Output:**
`true`

**Explanation:**
The substring `"ba"` in `s2` is a permutation of `s1`.

---

### Solution Code with Comments:

```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        // If s1 is longer than s2, no permutation is possible
        if (s1.length() > s2.length()) return false;
        
        // Arrays to store character frequencies for 'a' to 'z'
        int[] s1Freq = new int[26];
        int[] windowFreq = new int[26];
        
        // Initialize the frequency arrays for s1 and the first window in s2
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
            windowFreq[s2.charAt(i) - 'a']++;
        }
        
        // Slide the window over s2
        for (int i = s1.length(); i < s2.length(); i++) {
            // Check if current window is a permutation of s1
            if (Arrays.equals(s1Freq, windowFreq)) {
                return true;
            }
            
            // Slide the window:
            // Add the next character
            windowFreq[s2.charAt(i) - 'a']++;
            // Remove the first character of the previous window
            windowFreq[s2.charAt(i - s1.length()) - 'a']--;
        }
        
        // Check the last window
        return Arrays.equals(s1Freq, windowFreq);
    }
}
```

---

### Explanation:

* This approach uses a **fixed-size sliding window** of length `s1.length()` across `s2`.
* We use two frequency arrays:

  * One for `s1`
  * One for the current window in `s2`
* If the frequency arrays match, it means the current window is a permutation of `s1`.
* We compare at each step and slide the window one character to the right.

---


# Problem: Minimum Window Substring

Given two strings `s` and `t`, return the **minimum window substring** of `s` such that **every character in `t` (including duplicates)** is included in the window. If no such substring exists, return `""`.

The answer is guaranteed to be **unique**.

---

### Example:

**Input:**
`s = "ADOBECODEBANC"`
`t = "ABC"`

**Output:**
`"BANC"`

**Explanation:**
The substring `"BANC"` is the smallest window in `s` that contains all characters from `t`.

---

### Solution Code with Comments:

```java
class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length() || t.length() == 0 || s.length() == 0) return "";

        // Frequency arrays for ASCII characters (128)
        int[] tFq = new int[128]; // Frequency of characters in t
        int[] wFq = new int[128]; // Frequency of characters in current window of s

        // Fill frequency for t
        for (int i = 0; i < t.length(); i++) {
            tFq[t.charAt(i)]++;
        }

        int left = 0, right = 0;           // Window pointers
        int formed = 0;                    // How many t chars are matched in window
        int required = t.length();         // Total characters to match (with duplicates)
        int minLength = Integer.MAX_VALUE;
        int minStart = 0;                  // Start index of the smallest valid window

        // Expand window using right pointer
        while (right < s.length()) {
            char ch = s.charAt(right);
            wFq[ch]++; // Add current character to window frequency

            // Count it toward `formed` only if it's required and not extra
            if (tFq[ch] > 0 && wFq[ch] <= tFq[ch]) {
                formed++;
            }

            // Try shrinking window from the left as long as it's valid
            while (formed == required) {
                // Update min window if smaller found
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    minStart = left;
                }

                // Remove character at left from window
                char leftChar = s.charAt(left);
                wFq[leftChar]--;

                // If it was required and removing it makes the window invalid
                if (tFq[leftChar] > 0 && wFq[leftChar] < tFq[leftChar]) {
                    formed--;
                }

                left++; // Shrink from the left
            }

            right++; // Expand to the right
        }

        // Return result based on whether we found a valid window
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }
}
```

---

### Explanation:

* This is a **sliding window** problem optimized with **two pointers** (`left` and `right`) and **character frequency arrays**.
* We expand the window until all characters in `t` are matched (`formed == required`), then try shrinking from the left to find the minimum possible window.
* Each time the window becomes valid, we check if it's the smallest seen so far.
* Time complexity is **O(s.length + t.length)**, space is **O(1)** due to fixed array size for 128 ASCII characters.

---

# 🔹 Problem: Sliding Window Maximum

You are given an integer array `nums` and an integer `k`.
There is a sliding window of size `k` moving from left to right.
At each step, return the maximum value in the window.

---

### ✅ Example

**Input:**
`nums = [1,3,-1,-3,5,3,6,7]`, `k = 3`

**Output:**
`[3,3,5,5,6,7]`

**Explanation:**
We slide a window of size 3 and take the maximum:

```
[1  3 -1] -3  5  3  6  7 → max = 3  
 1 [3 -1 -3] 5  3  6  7 → max = 3  
 1  3 [-1 -3 5] 3  6  7 → max = 5  
 1  3 -1 [-3 5 3] 6  7 → max = 5  
 1  3 -1 -3 [5 3 6] 7 → max = 6  
 1  3 -1 -3  5 [3 6 7] → max = 7  
```

---

### 💡 Approach: Monotonic Queue (Deque)

* **Deque** stores indices in decreasing order of values.
* The **front** of the deque always has the index of the **maximum** element in the current window.
* Before adding a new index, remove:

  * Elements **outside** the window (left side)
  * Elements **smaller** than the current number (because they’ll never be max again)

---

### 🔧 Code (Java)

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // Result array
        int[] ans = new int[nums.length - k + 1];
        int j = 0;

        // Deque to store indices
        Deque<Integer> q = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            // Remove indices out of current window (i - k + 1 is the left boundary)
            if (!q.isEmpty() && q.peekFirst() < i - k + 1) {
                q.pollFirst();
            }

            // Remove elements smaller than current from the back of deque
            while (!q.isEmpty() && nums[i] > nums[q.peekLast()]) {
                q.pollLast();
            }

            // Add current index
            q.offerLast(i);

            // Once window has k elements, record the max (front of deque)
            if (i >= k - 1) {
                ans[j++] = nums[q.peekFirst()];
            }
        }

        return ans;
    }
}
```

---

### 🧠 Time & Space Complexity

* **Time:** `O(n)` — Each element is added and removed from deque at most once.
* **Space:** `O(k)` — For storing indices in the deque.

---


