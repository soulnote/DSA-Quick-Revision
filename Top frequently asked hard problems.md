# 1\. Minimum Window Substring

**Problem Statement:**

Aapko do strings `s` aur `t` diye gaye hain. Aapko `s` ka sabse chhota (minimum length) substring dhoondhna hai, jisme `t` ke saare characters (counting multiples, yaani frequencies) shamil hon. Agar aisa koi substring nahi milta, toh empty string `""` return kijiye.

**Example:**

```
s = "ADOBECODEBANC"
t = "ABC"

Output: "BANC"
```

**Key Concepts/Challenges:**

  * **Sliding Window:** Ye ek bahut hi common technique hai jahan hum ek "window" ko array ya string par move karte hain. Window ka size dynamically adjust ho sakta hai.
  * **Hash Map for frequency tracking:** Humein `t` ke characters ki frequencies aur current window mein un characters ki frequencies track karni hongi.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke liye hum **Sliding Window** approach use karenge.

1.  **Setup:**

      * Sabse pehle, hum `t` string ke characters ki frequencies ko ek hash map (`mapT`) mein store kar lenge. Jaise agar `t = "ABC"` hai, toh `mapT` hoga `{'A': 1, 'B': 1, 'C': 1}`. Ye humein batayega ki `t` mein kaun sa character kitni baar chahiye.
      * Hum ek aur hash map (`windowMap`) banayenge jo current sliding window mein characters ki frequencies track karega.
      * `matchedCount` naam ka ek variable rakhenge jo track karega ki `t` ke kitne unique characters humne apni current window mein successfully match kar liye hain. Jab `matchedCount` `mapT` ke unique characters ke count ke barabar ho jayega, toh matlab humne `t` ke saare characters apni window mein pa liye hain.
      * `minLength` ko `infinity` (ya `Integer.MAX_VALUE`) se initialize karenge aur `startIndex` ko `-1` (ya jo bhi invalid index ho) se initialize karenge. Ye hum best window ko store karne ke liye use karenge.
      * Do pointers lenge: `left` (window ka starting point) aur `right` (window ka ending point). Dono `0` se start honge.

2.  **Expanding the Window (Right Pointer Move Karega):**

      * `right` pointer ko `s` ke upar move karte jayenge (iterate karenge).
      * Har `right` pointer par, current character (`charR`) ko `windowMap` mein add karenge aur uski frequency badhaenge.
      * Agar `charR` `mapT` mein present hai (yaani `t` mein aata hai) aur `windowMap` mein uski frequency `mapT` mein uski frequency se kam ya barabar hai, toh `matchedCount` ko badha denge. (Ye important hai ki hum tabhi `matchedCount` badhaayein jab current character ki frequency `t` ki required frequency se exceed na kare. Basically, hum valid matches count kar rahe hain).

3.  **Shrinking the Window (Left Pointer Move Karega - Jab Window Valid Ho Jaye):**

      * Jaise hi `matchedCount` `mapT` ke unique characters ke count ke barabar ho jaye (yaani, humne `t` ke saare required characters apni window mein pa liye hain), toh ye ek "valid" window hai.
      * Ab hum is window ko optimize karne ki koshish karenge, yaani `left` pointer ko move karke window ko chhota karenge, jab tak ki window valid rahe.
      * Current window ki length calculate karenge (`right - left + 1`). Agar ye `minLength` se choti hai, toh `minLength` aur `startIndex` ko update karenge.
      * `left` pointer se character (`charL`) ko `windowMap` se remove karenge (uski frequency ghata denge).
      * Agar `charL` `mapT` mein present hai aur `windowMap` mein uski frequency `mapT` mein uski frequency se *kam* ho gayi hai (yaani, `t` ke liye required character ab window mein kam pad gaya hai), toh `matchedCount` ko ghata denge.
      * Is step ko tab tak repeat karenge jab tak `matchedCount` `mapT` ke unique characters ke count ke barabar na rahe. Jab `matchedCount` kam ho jayega, toh matlab window ab valid nahi rahi, aur hum fir se `right` pointer ko aage badhaenge.

4.  **Final Result:**

      * Jab `right` pointer `s` string ke end tak pahunch jayega, toh loop khatam ho jayega.
      * Agar `minLength` abhi bhi `infinity` hai, toh matlab koi valid window mili hi nahi, toh empty string `""` return karenge.
      * Warna, `s` ka substring `startIndex` se `minLength` length tak return karenge.


```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public String minWindow(String s, String t) {
        // Edge cases
        if (t.length() == 0 || s.length() == 0) {
            return "";
        }

        // mapT: t string ke characters ki frequencies store karega
        Map<Character, Integer> mapT = new HashMap<>();
        for (char c : t.toCharArray()) {
            mapT.put(c, mapT.getOrDefault(c, 0) + 1);
        }

        // windowMap: current window ke characters ki frequencies
        Map<Character, Integer> windowMap = new HashMap<>();

        int left = 0; // Left pointer of the window
        int matchedCount = 0; // Kitne characters t ke match ho chuke hain (unique count nahi, actual count based on frequency)
        int minLength = Integer.MAX_VALUE; // Smallest window length found so far
        int startIndex = -1; // Starting index of the smallest window

        // Total unique characters jo t mein chahiye
        int requiredMatches = mapT.size(); // Ye unique characters ka count hai, frequencies ka nahi

        // right pointer se window expand karenge
        for (int right = 0; right < s.length(); right++) {
            char charR = s.charAt(right);

            // charR ko window mein add karo
            windowMap.put(charR, windowMap.getOrDefault(charR, 0) + 1);

            // Check karo agar ye character t mein bhi hai aur iski frequency t ki required frequency se kam ya barabar hai
            if (mapT.containsKey(charR) && windowMap.get(charR).intValue() <= mapT.get(charR).intValue()) {
                matchedCount++; // Successfully matched one instance of a character from t
            }

            // Jab window valid ho jaye (yaani, t ke saare characters mil gaye)
            while (matchedCount == requiredMatches) {
                // Current window ki length check karo aur update karo agar choti hai
                int currentWindowLength = right - left + 1;
                if (currentWindowLength < minLength) {
                    minLength = currentWindowLength;
                    startIndex = left;
                }

                // Ab left pointer ko aage badha ke window ko shrink karo
                char charL = s.charAt(left);
                windowMap.put(charL, windowMap.get(charL) - 1);

                // Agar jo character remove kiya hai wo t mein tha aur ab uski frequency t ki required frequency se kam ho gayi hai
                if (mapT.containsKey(charL) && windowMap.get(charL).intValue() < mapT.get(charL).intValue()) {
                    matchedCount--; // Ek match kam ho gaya
                }
                left++; // Left pointer ko aage badhao
            }
        }

        // Result return karo
        if (minLength == Integer.MAX_VALUE) {
            return ""; // Koi valid window nahi mili
        } else {
            return s.substring(startIndex, startIndex + minLength);
        }
    }
}
```

**Time Complexity:**

  * `O(S + T)`: Jahan `S` string `s` ki length hai aur `T` string `t` ki length hai.
      * `mapT` banane mein `O(T)` time.
      * `right` pointer `s` par iterate karta hai, `O(S)` time.
      * `left` pointer bhi `s` par iterate karta hai (har character max. 1 baar `right` se add hota hai aur 1 baar `left` se remove). So overall `O(S)`.
      * Hash Map operations (put, get) `O(1)` average time.

**Space Complexity:**

  * `O(K)`: Jahan `K` unique characters ka count hai (alphabet size, usually 256 for ASCII). Maximum size of `mapT` and `windowMap` is bounded by the number of unique characters.

-----

# 2\. Merge k Sorted Lists

**Problem Statement:**

Aapko `k` sorted linked lists ka ek array diya gaya hai. Aapko un sabhi lists ko merge karke ek single sorted linked list banana hai aur use return karna hai.

**Example:**

```
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]

Explanation:
Merging them into one sorted list:
1->1->2->3->4->4->5->6
```

**Key Concepts/Challenges:**

  * **Min-Heap (Priority Queue):** Multiple sorted sources se data ko efficiently merge karne ke liye sabse best tareeka.
  * **Linked Lists:** Pointers ko sahi se manage karna.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke liye hum **Min-Heap (Priority Queue)** ka use karenge. Imagine karo ki aapke paas `k` alag-alag piles hain sorted cards ke, aur aapko un sab ko milake ek single sorted pile banani hai. Har pile ka sabse chhota card uske top par hoga.

1.  **Setup:**

      * Ek `PriorityQueue` banayenge. Ye ek `Min-Heap` ki tarah act karega, jisme hum `ListNode` objects ko store karenge. `PriorityQueue` apne elements ko unki value ke according sort karega (smallest value top par).
      * Ek `dummyHead` node banayenge (value `0` ya `null`). Ye merged list ka starting point hoga. Aur ek `tail` pointer banayenge jo current merged list ke last node ko point karega, initially `dummyHead` ko point karega.

2.  **Populating the Heap:**

      * Saari `k` lists ko iterate karenge. Har list ka jo pehla node hai (`head`), agar wo `null` nahi hai, toh us node ko `PriorityQueue` mein add kar denge.

3.  **Merging Process:**

      * Jab tak `PriorityQueue` empty nahi ho jaati:
          * `PriorityQueue` se sabse chhota node (`minNode`) nikalenge (`poll()` operation).
          * Is `minNode` ko `tail` ke `next` mein attach kar denge, aur `tail` ko `minNode` par move kar denge. (Yaani, `tail.next = minNode; tail = minNode;`)
          * Agar `minNode` ka `next` node exist karta hai (yaani, `minNode` jis list se aaya hai, usme abhi aur elements hain), toh `minNode.next` ko `PriorityQueue` mein add kar denge.

4.  **Final Result:**

      * Jab `PriorityQueue` empty ho jayegi, toh `dummyHead.next` hamari fully merged aur sorted list hogi. Use return kar denge.


```java
import java.util.PriorityQueue;
import java.util.Comparator;

// Definition for singly-linked list.
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        // Edge case: agar input lists empty ya null hain
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Min-Heap (PriorityQueue) banate hain
        // Ismein ListNode objects store honge, aur unko unki 'val' ke basis par sort kiya jayega
        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode a, ListNode b) {
                return a.val - b.val; // Smallest 'val' first
            }
        });

        // Dummy head node for the merged list
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead; // Tail pointer to build the merged list

        // Har list ka pehla node (agar null nahi hai) priority queue mein add karo
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }

        // Jab tak priority queue empty nahi ho jaati
        while (!pq.isEmpty()) {
            // Sabse chhota node nikalo heap se
            ListNode minNode = pq.poll();

            // Is node ko merged list mein add karo
            tail.next = minNode;
            tail = minNode; // Tail ko aage badhao

            // Agar is node ke baad aur nodes hain original list mein, toh uske next node ko heap mein add karo
            if (minNode.next != null) {
                pq.add(minNode.next);
            }
        }

        // Merged list ka head return karo (dummyHead ke next se)
        return dummyHead.next;
    }
}
```

**Time Complexity:**

  * `O(N log k)`: Jahan `N` total number of nodes hain saari lists mein (`N = n1 + n2 + ... + nk`), aur `k` number of lists hain.
      * Har node ko ek baar `PriorityQueue` mein add kiya jata hai (`log k` time for add).
      * Har node ko ek baar `PriorityQueue` se nikala jata hai (`log k` time for poll).
      * Total `N` nodes, so `N * log k`.
      * Initial population of heap takes `O(k log k)` in worst case if all lists have at least one element. But it's dominated by `N log k`.

**Space Complexity:**

  * `O(k)`: `PriorityQueue` mein at most `k` nodes store ho sakte hain (har list se ek node).

-----

# 3\. Trapping Rain Water

**Problem Statement:**

Aapko `n` non-negative integers ki ek array `height` di gayi hai jo ek elevation map ko represent karti hai, jahan `height[i]` har bar ki width `1` hai. Calculate kijiye ki kitna rain water trap ho sakta hai (yaani kitna paani ikattha ho sakta hai) bars ke beech mein.

**Example:**

```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
```

**Key Concepts/Challenges:**

  * **Two Pointers:** Ek efficient approach jahan do pointers array ke alag alag ends se start hote hain aur ek doosre ki taraf move karte hain.
  * **Boundary Handling:** Paani tabhi trap hota hai jab uske dono taraf bars hon jo uski height se zyada ya barabar hon.
  * **Max Height Tracking:** Har point par left aur right side ki maximum height track karna zaroori hai.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke kai tareeke hain: Brute Force, Dynamic Programming (Precomputing Max Heights), aur Two Pointers. Two Pointers approach sabse efficient aur elegant hai.

**Two Pointers Approach:**

Imagine karo ki ek particular index `i` par kitna paani trap hoga?
Ek bar `i` par jo paani trap hoga, uski height decide hoti hai uske left side ki maximum height (`maxLeft`) aur right side ki maximum height (`maxRight`) mein se jo minimum hai, usse.
`Trapped Water at i = min(maxLeft, maxRight) - height[i]`
Lekin ye formula tabhi kaam karega jab `min(maxLeft, maxRight) > height[i]` ho. Agar `min(maxLeft, maxRight) <= height[i]` hai, toh us point par 0 paani trap hoga.

Two Pointers approach mein hum ye `maxLeft` aur `maxRight` ko intelligently track karte hain:

1.  **Setup:**

      * `left = 0`, `right = n - 1` (pointers for array ends).
      * `leftMax = 0`, `rightMax = 0` (current maximum height from left and right side respectively).
      * `totalWater = 0`.

2.  **Logic:**

      * Jab tak `left <= right` hai (ya `left < right` bhi chalega, last element will be handled):
          * Agar `height[left] < height[right]` hai:
              * Iska matlab hai ki paani ki height `left` side ki `height[left]` se determine hogi. Right side ki wall pakka `height[left]` se badi ya barabar hogi (yaani `height[right]` will act as a stronger wall).
              * Agar `height[left]` current `leftMax` se zyada hai, toh `leftMax` ko update karo (`leftMax = height[left]`).
              * Warna, `height[left]` par kitna paani trap ho sakta hai: `leftMax - height[left]`. Ise `totalWater` mein add karo.
              * `left` pointer ko `1` se badhao (`left++`).
          * Else (`height[right] <= height[left]`):
              * Iska matlab hai ki paani ki height `right` side ki `height[right]` se determine hogi. Left side ki wall pakka `height[right]` se badi ya barabar hogi.
              * Agar `height[right]` current `rightMax` se zyada hai, toh `rightMax` ko update karo (`rightMax = height[right]`).
              * Warna, `height[right]` par kitna paani trap ho sakta hai: `rightMax - height[right]`. Ise `totalWater` mein add karo.
              * `right` pointer ko `1` se ghatao (`right--`).

3.  **Why this works (Intuition):**
    Jab hum `height[left] < height[right]` choose karte hain, toh humein pata hai ki `min(leftMax, rightMax)` jo hoga, wo `leftMax` ke barabar hoga. Kyun? Kyunki `height[left]` already `height[right]` se chhota hai, aur hum `leftMax` ko update kar rahe hain. Eventually, `rightMax` will always be *at least* as tall as `height[left]` (because `height[right]` is already taller). So, `min(leftMax, rightMax)` is effectively determined by `leftMax`. Same logic applies vice-versa for `height[right] <= height[left]`.


```java
class Solution {
    public int trap(int[] height) {
        // Edge case: agar array null ya empty hai
        if (height == null || height.length == 0) {
            return 0;
        }

        int left = 0; // Left pointer
        int right = height.length - 1; // Right pointer

        int leftMax = 0; // Maximum height encountered from left side so far
        int rightMax = 0; // Maximum height encountered from right side so far

        int totalWater = 0; // Total trapped water

        // Jab tak left pointer right pointer ko cross nahi karta
        while (left < right) {
            // Agar left side ki height right side se kam hai
            if (height[left] < height[right]) {
                // Check karo kya current left bar previous leftMax se bada hai
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // leftMax update karo
                } else {
                    // Agar current bar leftMax se chhota hai, toh paani trap hoga
                    totalWater += leftMax - height[left];
                }
                left++; // Left pointer ko aage badhao
            } else { // Agar right side ki height left side se kam ya barabar hai
                // Check karo kya current right bar previous rightMax se bada hai
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // rightMax update karo
                } else {
                    // Agar current bar rightMax se chhota hai, toh paani trap hoga
                    totalWater += rightMax - height[right];
                }
                right--; // Right pointer ko peeche badhao
            }
        }

        return totalWater;
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` `height` array ki length hai. Hum single pass mein array ko traverse kar rahe hain (left aur right pointers meet karte hain).

**Space Complexity:**

  * `O(1)`: Hum constant extra space use kar rahe hain variables store karne ke liye.

-----

# 4\. Longest Increasing Path in a Matrix

**Problem Statement:**

Aapko ek `m x n` integers ka matrix diya gaya hai. Aapko us matrix mein sabse lambi increasing path ki length dhoondhni hai.

Ek increasing path mein, aap current cell se sirf chaar directions mein move kar sakte hain: left, right, up, ya down. Diagonal moves allowed nahi hain. Path banate waqt, har next cell ki value previous cell se strictly badi honi chahiye.

**Example:**

```
Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
Output: 4
Explanation: Sabse lambi increasing path hai: [1, 2, 6, 9].
```

**Key Concepts/Challenges:**

  * **Dynamic Programming (DP) / Memoized DFS on DAG (Directed Acyclic Graph):** Har cell se kitni lambi path ban sakti hai, ye calculate karna. Aur overlapping subproblems ko avoid karne ke liye results ko store karna (memoization).
  * **DFS (Depth-First Search):** Path ko explore karne ke liye.
  * **DAG:** Kyunki increasing path hai, toh koi cycle nahi ban sakti (aap hamesha bade number ki taraf ja rahe ho), isliye ye ek Directed Acyclic Graph ki tarah hai.

**Intuition aur Approach (Hinglish Mein):**

Ye problem dekhne mein complex lagti hai, but isko **Dynamic Programming with Depth-First Search (DFS) aur Memoization** se solve karna kaafi elegant hai.

1.  **Fundamental Idea:**
    Har cell `(r, c)` ke liye, humein yeh pata karna hai ki agar hum us cell se start karein toh kitni lambi increasing path ban sakti hai.
    Agar humein ye pata chal jaaye, toh hum saare cells ke liye ye calculate kar lenge aur un sab mein se jo maximum length hogi, wahi hamara answer hoga.

2.  **DFS Function (`dfs(row, col, matrix, dp)`):**

      * Ye function `(row, col)` cell se shuru hone wali sabse lambi increasing path ki length return karega.
      * **Memoization:** Hum ek `dp` (dynamic programming) 2D array banayenge jo har cell `(r, c)` ke liye `dfs(r, c)` ka result store karega. Agar `dp[row][col]` already calculate ho chuka hai (yani non-zero hai), toh directly stored value return kar do. Isse redundant calculations bach jayengi.
      * **Base Case (Implicit):** Har cell se kam se kam 1 length ki path toh hogi hi (wo khud cell).
      * **Recursive Step:**
          * `currentPathLength = 1` (current cell ko count karte hue).
          * Hum current cell `(row, col)` se uske charon neighbors (up, down, left, right) par jayenge.
          * Har neighbor `(nRow, nCol)` ke liye:
              * Check karenge ki `(nRow, nCol)` matrix ke boundaries ke andar hai kya.
              * Check karenge ki `matrix[nRow][nCol]` ki value `matrix[row][col]` se strictly badi hai kya.
              * Agar dono conditions true hain, toh is valid neighbor ke liye `dfs(nRow, nCol, matrix, dp)` ko call karenge.
              * Is recursive call se jo length return hogi, usmein `1` (current cell ke liye) add karke, `currentPathLength` ko update karenge `currentPathLength = Math.max(currentPathLength, 1 + dfs(nRow, nCol, matrix, dp))`.
      * Jab saare valid neighbors explore ho jaayenge, toh `dp[row][col] = currentPathLength` store kar denge aur `currentPathLength` return kar denge.

3.  **Main Function (`longestIncreasingPath(matrix)`):**

      * Matrix ke dimensions `m` aur `n` nikalenge.
      * `dp` array ko initialize karenge sab zero se (indicates not calculated yet).
      * `maxLength = 0` initialize karenge.
      * Poore matrix ko iterate karenge, har cell `(r, c)` ke liye:
          * `maxLength = Math.max(maxLength, dfs(r, c, matrix, dp))`.
      * Finally, `maxLength` return kar denge.


```java
class Solution {
    // Directions for moving (up, down, left, right)
    int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int M, N; // Matrix dimensions

    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        M = matrix.length;
        N = matrix[0].length;

        // dp[r][c] stores the length of the longest increasing path starting from (r, c)
        int[][] dp = new int[M][N];

        int maxLength = 0;

        // Har cell se DFS shuru karo
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                maxLength = Math.max(maxLength, dfs(matrix, r, c, dp));
            }
        }
        return maxLength;
    }

    // DFS function with memoization
    private int dfs(int[][] matrix, int r, int c, int[][] dp) {
        // Agar result pehle se calculate ho chuka hai, toh return kar do
        if (dp[r][c] != 0) {
            return dp[r][c];
        }

        // Current cell khud hi ek path hai, length 1
        int maxPathFromCurrentCell = 1;

        // Charon directions mein explore karo
        for (int[] dir : DIRS) {
            int newR = r + dir[0];
            int newC = c + dir[1];

            // Check boundaries
            if (newR >= 0 && newR < M && newC >= 0 && newC < N) {
                // Check increasing condition
                if (matrix[newR][newC] > matrix[r][c]) {
                    // Recursive call aur max length update karo
                    maxPathFromCurrentCell = Math.max(maxPathFromCurrentCell, 1 + dfs(matrix, newR, newC, dp));
                }
            }
        }

        // Result store karo aur return karo
        dp[r][c] = maxPathFromCurrentCell;
        return maxPathFromCurrentCell;
    }
}
```

**Time Complexity:**

  * `O(M * N)`: Jahan `M` rows hain aur `N` columns. Har cell ke liye `dfs` function call hota hai. Lekin, `dp` array ki wajah se har cell ke liye `dfs` sirf ek baar fully compute hota hai. Har cell se 4 directions explore hoti hain. So, total operations are proportional to `M * N`.

**Space Complexity:**

  * `O(M * N)`: `dp` array store karne ke liye aur recursive call stack ke liye (worst-case mein depth `M * N` ho sakti hai).

-----

# 5\. Product of Array Except Self

**Problem Statement:**

Aapko integers ka ek array `nums` diya gaya hai. Aapko ek `answer` array return karna hai jahan `answer[i]` `nums` ke sabhi elements ka product hoga, `nums[i]` ko chhodkar.

Condition ye hai ki aapko `O(n)` time complexity mein solve karna hai aur division operator use nahi karna hai.

**Example:**

```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]

Explanation:
answer[0] = 2 * 3 * 4 = 24
answer[1] = 1 * 3 * 4 = 12
answer[2] = 1 * 2 * 4 = 8
answer[3] = 1 * 2 * 3 = 6
```

**Key Concepts/Challenges:**

  * **Array manipulation (prefix/suffix products):** Division use kiye bina product calculate karna.
  * **Space Optimization:** `O(1)` extra space (output array ko chhodkar) achieve karna.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko bina division ke solve karne ka trick hai **prefix products aur suffix products** ko alag-alag calculate karna.

Imagine karo ki `answer[i]` nikalne ke liye, humein do cheezein chahiye:

1.  `nums[i]` ke **left side** ke saare elements ka product.
2.  `nums[i]` ke **right side** ke saare elements ka product.

Agar hum in dono products ko multiply kar dein, toh `answer[i]` mil jayega.

**Two Pass Approach (O(1) extra space - output array ko exclude karte hue):**

1.  **First Pass (Left Products):**

      * `answer` array banao, same size as `nums`.
      * `answer[0]` ko `1` se initialize karo (kyunki `nums[0]` ke left mein koi element nahi hai, toh product 1 hoga).
      * `left` se `right` tak iterate karo (`i` from 1 to `n-1`).
      * Har `answer[i]` mein `nums[i-1]` aur `answer[i-1]` ka product store karo. Ye `answer[i]` mein `nums[i]` tak ke saare left products store karega.
      * **Example for `nums = [1,2,3,4]`:**
          * `answer = [?, ?, ?, ?]`
          * `answer[0] = 1`
          * `answer[1] = answer[0] * nums[0] = 1 * 1 = 1`
          * `answer[2] = answer[1] * nums[1] = 1 * 2 = 2`
          * `answer[3] = answer[2] * nums[2] = 2 * 3 = 6`
          * After first pass, `answer = [1, 1, 2, 6]`. (`answer[i]` mein `nums[i]` ke *left* ke saare products hain).

2.  **Second Pass (Right Products and Final Calculation):**

      * Ek variable `rightProduct` banaya, usko `1` se initialize karo. Ye current element ke right side ke cumulative product ko track karega.
      * `right` se `left` tak iterate karo (`i` from `n-1` down to 0).
      * Har `answer[i]` ko `answer[i]` (jo abhi left product hai) aur `rightProduct` se multiply karo.
      * Fir `rightProduct` ko update karo: `rightProduct = rightProduct * nums[i]`.
      * **Example for `nums = [1,2,3,4]` (with `answer = [1, 1, 2, 6]` from first pass):**
          * `rightProduct = 1`
          * `i = 3` (element `4`):
              * `answer[3] = answer[3] * rightProduct = 6 * 1 = 6`
              * `rightProduct = rightProduct * nums[3] = 1 * 4 = 4`
              * `answer = [1, 1, 2, 6]`
          * `i = 2` (element `3`):
              * `answer[2] = answer[2] * rightProduct = 2 * 4 = 8`
              * `rightProduct = rightProduct * nums[2] = 4 * 3 = 12`
              * `answer = [1, 1, 8, 6]`
          * `i = 1` (element `2`):
              * `answer[1] = answer[1] * rightProduct = 1 * 12 = 12`
              * `rightProduct = rightProduct * nums[1] = 12 * 2 = 24`
              * `answer = [1, 12, 8, 6]`
          * `i = 0` (element `1`):
              * `answer[0] = answer[0] * rightProduct = 1 * 24 = 24`
              * `rightProduct = rightProduct * nums[0] = 24 * 1 = 24`
              * `answer = [24, 12, 8, 6]`

3.  **Final Result:** `answer` array return karo.

**Code Structure (Java-like Pseudocode):**

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;

        // Result array jisme final products store honge
        // Isko hi hum left products ke liye use karenge pehle
        int[] answer = new int[n];

        // --- First Pass: Calculate left products ---
        // answer[0] mein nums[0] ke left ka product (jo ki 1 hai)
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            // answer[i] mein nums[i] ke left ke saare elements ka product
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // answer array will look like this after first pass for [1,2,3,4]:
        // [1, 1, 2, 6]
        // where answer[i] = product of elements to the left of nums[i]

        // --- Second Pass: Calculate right products and final result ---
        int rightProduct = 1; // Ye right side ke elements ka cumulative product store karega

        // Array ko right se left traverse karo
        for (int i = n - 1; i >= 0; i--) {
            // answer[i] already left product store karta hai.
            // Ab use right product se multiply karke final result milega.
            answer[i] = answer[i] * rightProduct;

            // rightProduct ko current element se multiply karke update karo
            rightProduct = rightProduct * nums[i];
        }

        return answer;
    }
}
```

**Time Complexity:**

  * `O(N)`: Do passes hain array par, each taking `O(N)` time. So total `O(N)`.

**Space Complexity:**

  * `O(1)`: Output array ko `O(1)` extra space mein count kiya jaata hai, kyunki problem statement mein explicitly "return an answer array" hai. Agar output array ko count karte, toh `O(N)` hota.

-----

### 6\. Find Median from Data Stream

**Problem Statement:**

Median is the middle value in an ordered integer list. Agar list ka size even hai, toh median do middle values ka average hota hai.

Ek data stream se integers aate ja rahe hain. Aapko do functions implement karne hain:

  * `addNum(int num)`: Data stream se ek integer add karta hai.
  * `findMedian()`: Ab tak jitne bhi elements add hue hain, unka median return karta hai.

**Example:**

```
MedianFinder mf = new MedianFinder();
mf.addNum(1);    // arr = [1]
mf.addNum(2);    // arr = [1, 2]
mf.findMedian(); // returns 1.5 ((1 + 2) / 2)
mf.addNum(3);    // arr = [1, 2, 3]
mf.findMedian(); // returns 2.0
```

**Key Concepts/Challenges:**

  * **Two Heaps (Max-Heap & Min-Heap):** Median ko efficiently track karne ke liye.
  * **Balancing Heaps:** Heaps ko aise balance karna ki median calculation always `O(1)` ho.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke liye hum **do heaps** ka clever use karte hain:

1.  **Ek Max-Heap (`maxHeap`)** jo stream ke **smaller half** of numbers ko store karegi. Is heap ka top element (root) smaller half ka sabse bada element hoga.
2.  **Ek Min-Heap (`minHeap`)** jo stream ke **larger half** of numbers ko store karegi. Is heap ka top element (root) larger half ka sabse chhota element hoga.

**Core Idea:**
Humein ye ensure karna hai ki `maxHeap.peek()` (largest element of smaller half) aur `minHeap.peek()` (smallest element of larger half) hi median ko determine karein.

**Balancing Rule:**

  * `maxHeap` ka size `minHeap` ke size ke barabar ho (`maxHeap.size() == minHeap.size()`).
  * Ya `maxHeap` ka size `minHeap` ke size se ek zyada ho (`maxHeap.size() == minHeap.size() + 1`).
    (Ye preference hai, aap `minHeap` ko ek bada bhi rakh sakte hain, bas consistent rehna hai).

**How `addNum(int num)` works:**

1.  **Add `num` to `maxHeap` (smaller half):** Sabse pehle, naye number `num` ko `maxHeap` mein add karo.

      * `maxHeap.add(num)`

2.  **Balance Heaps (Essential Step):**
    Ab ho sakta hai `maxHeap` mein ek aisa element aa gaya ho jo actually `minHeap` mein hona chahiye (yaani, `maxHeap` ka largest element `minHeap` ke smallest element se bada ho gaya ho). Isko correct karna hai:

      * `minHeap.add(maxHeap.poll())` (jo `maxHeap` ka largest element tha, use `minHeap` mein daal do).
      * Ab agar `minHeap` ka size `maxHeap` ke size se zyada ho gaya hai (yani `minHeap` kuch zyada hi elements le gayi hai), toh `minHeap` ka sabse chhota element `maxHeap` mein wapas bhej do:
          * `maxHeap.add(minHeap.poll())`


```java
import java.util.PriorityQueue;
import java.util.Collections; // For max-heap

class MedianFinder {

    // maxHeap: Stores the smaller half of numbers.
    // Use Collections.reverseOrder() to make it a max-heap.
    private PriorityQueue<Integer> maxHeap;

    // minHeap: Stores the larger half of numbers.
    // Default PriorityQueue is a min-heap.
    private PriorityQueue<Integer> minHeap;

    public MedianFinder() {
        // Max-heap for the lower half
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // Min-heap for the upper half
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // Step 1: Add the number to the maxHeap (lower half) first
        maxHeap.add(num);

        // Step 2: Balance the heaps.
        // Rule 1: maxHeap's largest element must be less than or equal to minHeap's smallest.
        // If maxHeap's top > minHeap's top, then move from maxHeap to minHeap.
        if (!maxHeap.isEmpty() && !minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
            minHeap.add(maxHeap.poll());
        }

        // Rule 2: Maintain size balance.
        // maxHeap can have 0 or 1 more element than minHeap.
        // If maxHeap is too small (e.g., minHeap has more elements than maxHeap + 1)
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
        // If maxHeap is too big (e.g., maxHeap has more than 1 extra element than minHeap)
        else if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.add(maxHeap.poll());
        }
    }

    public double findMedian() {
        // If total elements are even
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else { // If total elements are odd
            return maxHeap.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```

**Time Complexity:**

  * `addNum(int num)`: `O(log N)`. Har `add` aur `poll` operation heap size `N/2` par hote hain, jo `log(N/2)` yaani `O(log N)` time leta hai. `N` total numbers in the stream.
  * `findMedian()`: `O(1)`. Simply `peek()` operation, which is constant time.

**Space Complexity:**

  * `O(N)`: Both heaps together store all `N` numbers from the stream.

-----
# 7\. Regular Expression Matching

**Problem Statement:**

Aapko do strings `s` (jise text kah sakte hain) aur `p` (jise pattern kah sakte hain) diye gaye hain. Aapko implement karna hai regular expression matching jisme support ho `.` (dot) aur `*` (asterisk) characters ka.

  * `.` (dot) kisi bhi single character se match karta hai.
  * `*` (asterisk) zero ya usse zyada (zero or more) preceding element se match karta hai.

Matching poore `s` string par honi chahiye, na ki partial.

**Examples:**

```
s = "aa", p = "a"  -> Output: false (pattern match nahi hua poora)
s = "aa", p = "a*" -> Output: true ('a*' matches zero or more 'a's, toh "aa" match kar gaya)
s = "ab", p = ".*" -> Output: true ('.*' matches zero or more of any character)
s = "aab", p = "c*a*b" -> Output: true ('c*' zero 'c's, 'a*' one or more 'a's)
s = "mississippi", p = "mis*is*p*." -> Output: false (careful matching needed)
```

**Key Concepts/Challenges:**

  * **Dynamic Programming (DP):** Overlapping subproblems aur optimal substructure. Ek 2D DP table banate hain.
  * **Recursion/Backtracking:** Recursive calls ke through saare possible matches explore karna.
  * **Complex Pattern Matching Logic:** `.` aur `*` ke special rules ko handle karna.

**Intuition aur Approach (Hinglish Mein):**

Ye problem kaafi tricky hai, especially `*` operator ki wajah se. Ise solve karne ka sabse accha tareeka **Dynamic Programming** hai. Hum ek 2D DP table `dp[i][j]` banayenge, jahan `dp[i][j]` `true` hoga agar `s` ka first `i` characters `p` ke first `j` characters se match karte hain.

**DP Table Definition:**
`dp[i][j]` = `true` if `s[0...i-1]` matches `p[0...j-1]`, `false` otherwise.

**DP Table Dimensions:**
`rows = s.length() + 1`
`cols = p.length() + 1`
`dp[0][0]` `true` hoga (empty string empty pattern se match karta hai).

**Filling the DP Table (Iteration):**

1.  **`dp[0][0] = true`**: Base case, empty string aur empty pattern match karte hain.

2.  **First Row (`i=0`, empty string `s`):**

      * `dp[0][j]` ke liye: sirf `*` hi empty string se match kar sakta hai.
      * Agar `p[j-1]` `*` hai, toh `dp[0][j]` `dp[0][j-2]` ke barabar hoga. Matlab, `X*` ko empty string se match karne ke liye, `X*` ko zero occurrences (`X` ko ignore) se match karna padega, isliye `p` mein do characters peeche (like `c*` becomes empty).
      * Example: `p = "a*b*"`
          * `dp[0][0] = true`
          * `dp[0][1]` (for "a"): false
          * `dp[0][2]` (for "a\*"): `dp[0][0]` (empty string matches empty string) = `true`
          * `dp[0][3]` (for "a\*b"): false
          * `dp[0][4]` (for "a*b*"): `dp[0][2]` (matching `b*` as empty) = `true`

3.  **Remaining Cells (`i > 0`, `j > 0`):**

      * **Case 1: `p[j-1]` simple character hai (not `.` ya `*`)**

          * `dp[i][j]` tabhi `true` hoga jab:
              * `s[i-1]` aur `p[j-1]` same character hon.
              * Aur `s` ka preceding part `s[0...i-2]` `p` ke preceding part `p[0...j-2]` se match karta ho (yaani `dp[i-1][j-1]` `true` ho).

      * **Case 2: `p[j-1]` `.` (dot) hai**

          * `.` kisi bhi character se match karta hai. Toh, `dp[i][j]` `dp[i-1][j-1]` ke barabar hoga (jaise `s[i-1]` ne `p[j-1]` (`.`) ko consume kar liya).

      * **Case 3: `p[j-1]` `*` (asterisk) hai**
        Ye sabse complex case hai. Jab `*` aata hai, toh uske do interpretations hote hain:

          * **Zero occurrences (`0` times):** `X*` ko ignore kar do. Matlab, `p` ke `X*` portion ko remove kar do (`p[j-2]` tak dekho).
              * Is case mein `dp[i][j] = dp[i][j-2]` hoga. (e.g., "a" matches "ab\*" -\> "a" matches "a")
          * **One or more occurrences (`1+` times):** `X*` ne `s[i-1]` ko match kiya. Matlab `s[i-1]` previous character `X` se match karta hai (ya `.` se match karta hai).
              * Agar `s[i-1]` `p[j-2]` (`*` se pehle wala character) se match karta hai (ya `p[j-2]` `.` hai):
                  * Toh `dp[i][j] = dp[i-1][j]` hoga. (e.g., "aaa" matches "a\*" -\> "aa" matches "a\*")
              * Important: Yahan `dp[i-1][j]` use karte hain kyunki `s[i-1]` `X` se match kar gaya, aur `X*` abhi bhi `s` ke remaining parts ko match kar sakta hai.

        Toh, combined `*` logic:

          * `dp[i][j]` is `true` if `dp[i][j-2]` is `true` (zero occurrences case).
          * OR `dp[i][j]` is `true` if `s[i-1]` match karta hai `p[j-2]` se (ya `p[j-2]` `.` hai), AND `dp[i-1][j]` `true` hai (one or more occurrences case).

        `dp[i][j] = dp[i][j-2] || (isCharMatch(s[i-1], p[j-2]) && dp[i-1][j]);`
        `isCharMatch(charS, charP)` ek helper function hoga jo check karega ki `charS` `charP` se match karta hai ya `charP` `.` hai.

**Helper Function `isCharMatch(char sChar, char pChar)`:**

  * Returns `true` if `sChar == pChar` OR `pChar == '.'`.

**Final Result:**
`dp[s.length()][p.length()]` return karo.

**Dry Run Example:**

`s = "aa"`, `p = "a*"`

`s.length = 2`, `p.length = 2`
`dp` table will be `(2+1) x (2+1)` =\> `3x3`

Initialize `dp` with `false`:

```
      ""  a   *
    ----------------
"" | T | F | T
a  | F | F | F
a  | F | F | F
```

1.  `dp[0][0] = true` (Base Case)
2.  `dp[0][1]` (for `p="a"`): `p[0]` is 'a'. Not `*`. `false`.
3.  `dp[0][2]` (for `p="a*"`): `p[1]` is '\*'.
      * Zero occurrences: `dp[0][2] = dp[0][0]` (for "a\*"). `dp[0][0]` is `true`. So `dp[0][2]` becomes `true`.
      * (One or more occurrences not applicable for `s=""`).

Now, fill for `s="a"` (`i=1`):

4.  `dp[1][0]` (for `s="a"`, `p=""`): `false`.

5.  `dp[1][1]` (for `s="a"`, `p="a"`):

      * `s[0] == p[0]` ('a' == 'a') is `true`.
      * And `dp[0][0]` is `true`.
      * So, `dp[1][1] = true`.

6.  `dp[1][2]` (for `s="a"`, `p="a*"`): `p[1]` is '\*'. `p[0]` is 'a'. `s[0]` is 'a'.

      * Zero occurrences: `dp[1][0]` (for `s="a"`, `p=""`). Which is `false`.
      * One or more occurrences: `isCharMatch(s[0], p[0])` (is 'a' == 'a' or '.'?). Yes, `true`.
          * AND `dp[0][2]` (for `s=""`, `p="a*"`). Which is `true`.
          * So, `true && true` is `true`.
      * Result: `false || true` = `true`. `dp[1][2] = true`.

Now, fill for `s="aa"` (`i=2`):

7.  `dp[2][0]` (for `s="aa"`, `p=""`): `false`.

8.  `dp[2][1]` (for `s="aa"`, `p="a"`):

      * `s[1]` ('a') == `p[0]` ('a') is `true`.
      * AND `dp[1][0]` is `false`.
      * So, `dp[2][1] = false`.

9.  `dp[2][2]` (for `s="aa"`, `p="a*"`): `p[1]` is '\*'. `p[0]` is 'a'. `s[1]` is 'a'.

      * Zero occurrences: `dp[2][0]` (for `s="aa"`, `p=""`). Which is `false`.
      * One or more occurrences: `isCharMatch(s[1], p[0])` (is 'a' == 'a' or '.'?). Yes, `true`.
          * AND `dp[1][2]` (for `s="a"`, `p="a*"`). Which is `true`.
          * So, `true && true` is `true`.
      * Result: `false || true` = `true`. `dp[2][2] = true`.

Final `dp` table:

```
      ""  a   *
    ----------------
"" | T | F | T
a  | F | T | T
a  | F | F | T
```

The answer `dp[2][2]` is `true`. Matches example output.

**Code Structure (Java-like Pseudocode):**

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] will be true if s[0...i-1] matches p[0...j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Base case: empty string matches empty pattern
        dp[0][0] = true;

        // Fill the first row (s is empty)
        // Only '*' can match an empty string, by matching zero times
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2]; // X* matches empty by matching 0 X's
            }
        }

        // Fill the rest of the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);

                if (pChar == '.') {
                    // '.' matches any single character
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pChar == '*') {
                    // '*' can mean zero occurrences or one or more occurrences
                    // Case 1: Zero occurrences of the preceding element (p[j-2])
                    // Example: "a" vs "ab*". 'b*' is treated as empty. So match "a" vs "a".
                    dp[i][j] = dp[i][j - 2];

                    // Case 2: One or more occurrences of the preceding element (p[j-2])
                    // If sChar matches p[j-2] (or p[j-2] is '.'), then consider current match
                    // and previous state where pattern part X* still matches.
                    // Example: "aa" vs "a*". 'a*' matches 'a' at s[1] and still matches previous 'a'.
                    if (isCharMatch(sChar, p.charAt(j - 2))) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    // Regular character match
                    if (sChar == pChar) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = false; // Characters don't match
                    }
                }
            }
        }

        return dp[m][n];
    }

    // Helper function to check if a character from s matches a character from p
    // (considering '.' wildcard)
    private boolean isCharMatch(char sChar, char pChar) {
        return (pChar == '.' || sChar == pChar);
    }
}
```

**Time Complexity:**

  * `O(M * N)`: Jahan `M` `s` string ki length hai aur `N` `p` string ki length hai. Hum `M * N` cells fill kar rahe hain, aur har cell ko fill karne mein `O(1)` time lagta hai.

**Space Complexity:**

  * `O(M * N)`: `dp` 2D array ke liye.

-----

# 8\. The Skyline Problem

**Problem Statement:**

Aapko rectangular buildings ki ek list di gayi hai. Har building ko `[left, right, height]` format mein diya gaya hai. `left` aur `right` building ke x-coordinates hain aur `height` uski height hai. Aapko buildings ke outlines ko represent karne wala "skyline" return karna hai.

Skyline ek list of `[x, y]` coordinates mein return kiya jayega, jahan `x` horizontal position hai aur `y` corresponding height. Points ko x-coordinate ke hisaab se sorted hona chahiye. Consecutive horizontal segments ki height same nahi honi chahiye (redundant points nahi hone chahiye).

**Example:**

```
Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
```

**Key Concepts/Challenges:**

  * **Sweep Line Algorithm:** Problem ko solve karne ke liye ek vertical "sweep line" ko x-axis par move karna.
  * **Max-Heap (Priority Queue):** Current height ko track karne ke liye, particularly jab multiple buildings overlap kar rahe hon.
  * **Event Handling:** `left` (start of building) aur `right` (end of building) points ko events ki tarah handle karna.

**Intuition aur Approach (Hinglish Mein):**

Ye problem kafi challenging hai aur iske liye **Sweep Line Algorithm** aur **Max-Heap** ka combination use hota hai.

**Core Idea:**
Imagine karo ki ek vertical line hai jo x-axis par left se right move kar rahi hai. Jab ye line kisi building ke **start** point (left edge) par pahunchti hai, toh wo building ab "active" ho jati hai. Jab ye line kisi building ke **end** point (right edge) par pahunchti hai, toh wo building ab "inactive" ho jati hai.

Har point par jahan koi building start ya end ho rahi hai, humein current maximum height change ko calculate karna hai. Ye change hi skyline points create karega.

**Steps:**

1.  **Event Points Generate Karo:**

      * Har building `[L, R, H]` ke liye, do "event points" generate karo:
          * `[L, -H]` (Start point): Negative height indicate karti hai ki ye building ka start point hai. Negative isliye because hum max-heap mein heights store karenge, aur jab start point aayega, hum height add karenge. Sorting mein pehle start points aayenge.
          * `[R, H]` (End point): Positive height indicate karti hai ki ye building ka end point hai.
      * Saare event points ko ek list mein collect karo.

2.  **Event Points Sort Karo:**

      * Event points ko `x`-coordinate ke hisaab se sort karo.
      * Agar `x`-coordinates same hain:
          * Agar dono start points hain (`-H1`, `-H2`), toh bade height wala point pehle aayega (taaki bade height change ko pehle process kar sakein).
          * Agar dono end points hain (`+H1`, `+H2`), toh chhote height wala point pehle aayega (taaki chhota height drop pehle process ho).
          * Agar ek start point (`-H1`) aur ek end point (`+H2`) hai, toh start point pehle aayega (height increase drop se pehle process ho, e.g., `[0,0]` se `[2,10]` point). Ek edge case ye hai ki agar ek building ka end point aur dusri building ka start point exact same x-coordinate par hain, aur current height is 0, toh end points pehle process honge taaki height correctly drop ho (e.g. `[0,1,1]` and `[1,2,2]` at `x=1` first remove 1, then add 2). Simple rule: `-H` comes before `+H`.

3.  **Max-Heap Initialize Karo:**

      * Ek `PriorityQueue` (Max-Heap) banao jo current active buildings ki heights ko store karegi. `Collections.reverseOrder()` use karke isko max-heap banate hain.
      * Heap mein initially `0` height daal do (ye ground level ko represent karega).
      * `prevMaxHeight = 0` initialize karo.

4.  **Sweep Line Simulation:**

      * Sorted event points par iterate karo:
          * Har event point `[currentX, currentHeight]` ko lo.

          * Agar `currentHeight < 0` (yaani ye start point hai, `-H` tha):

              * Building ki actual height `abs(currentHeight)` ko `maxHeap` mein add karo.

          * Agar `currentHeight > 0` (yaani ye end point hai, `+H` tha):

              * Building ki actual height `currentHeight` ko `maxHeap` se remove karo.

          * **Current Max Height Check:** `currentMaxHeight = maxHeap.peek()`.

          * **Skyline Point Add Karo:** Agar `currentMaxHeight` `prevMaxHeight` se alag hai, toh ek naya skyline point mila hai.

              * `[currentX, currentMaxHeight]` ko result list mein add karo.
              * `prevMaxHeight = currentMaxHeight` ko update karo.

5.  **Result Return Karo:**

      * Collect kiye hue skyline points ki list return karo.

**Dry Run Example (Small one):**

`buildings = [[2,9,10], [3,7,15]]`

1.  **Event Points:**

      * `[2, -10]` (start of building 1)
      * `[9, 10]` (end of building 1)
      * `[3, -15]` (start of building 2)
      * `[7, 15]` (end of building 2)

2.  **Sorted Event Points:**
    `[[2, -10], [3, -15], [7, 15], [9, 10]]`

3.  **Initialize:**

      * `maxHeap = {0}`
      * `prevMaxHeight = 0`
      * `result = []`

4.  **Sweep Line:**

      * **Event 1: `[2, -10]`**

          * `maxHeap.add(10)` -\> `maxHeap = {10, 0}`
          * `currentMaxHeight = maxHeap.peek() = 10`
          * `currentMaxHeight (10)` \!= `prevMaxHeight (0)`. Add `[2, 10]` to result.
          * `result = [[2, 10]]`
          * `prevMaxHeight = 10`

      * **Event 2: `[3, -15]`**

          * `maxHeap.add(15)` -\> `maxHeap = {15, 10, 0}`
          * `currentMaxHeight = maxHeap.peek() = 15`
          * `currentMaxHeight (15)` \!= `prevMaxHeight (10)`. Add `[3, 15]` to result.
          * `result = [[2, 10], [3, 15]]`
          * `prevMaxHeight = 15`

      * **Event 3: `[7, 15]`**

          * `maxHeap.remove(15)` -\> `maxHeap = {10, 0}` (15 nikal gaya)
          * `currentMaxHeight = maxHeap.peek() = 10`
          * `currentMaxHeight (10)` \!= `prevMaxHeight (15)`. Add `[7, 10]` to result.
          * `result = [[2, 10], [3, 15], [7, 10]]`
          * `prevMaxHeight = 10`

      * **Event 4: `[9, 10]`**

          * `maxHeap.remove(10)` -\> `maxHeap = {0}` (10 nikal gaya)
          * `currentMaxHeight = maxHeap.peek() = 0`
          * `currentMaxHeight (0)` \!= `prevMaxHeight (10)`. Add `[9, 0]` to result.
          * `result = [[2, 10], [3, 15], [7, 10], [9, 0]]`
          * `prevMaxHeight = 0`

**Final Result:** `[[2, 10], [3, 15], [7, 10], [9, 0]]`
This seems correct for these two buildings.


```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> result = new ArrayList<>();
        List<int[]> events = new ArrayList<>();

        // Step 1: Generate event points
        // For each building [left, right, height]
        // Add a start event: [left, -height] (negative height means start)
        // Add an end event: [right, height] (positive height means end)
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]}); // Start point
            events.add(new int[]{b[1], b[2]});  // End point
        }

        // Step 2: Sort event points
        // Sort by x-coordinate.
        // If x-coordinates are same, handle tie-breaking:
        // - If both are start points (-h1, -h2), higher building comes first (e.g., [[0,0,10],[0,0,5]] should process 10 first)
        // - If both are end points (+h1, +h2), lower building comes first (e.g., to ensure proper height drop)
        // - If one is start (-h) and one is end (+h'), start comes first (unless start.height == end.height and end point is '0', then special rule applies for 0 height)
        // Simpler rule for same x-coordinate: start events (-height) come before end events (+height)
        // If two start events, higher height comes first. If two end events, lower height comes first.
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) { // Sort by x-coordinate
                return a[0] - b[0];
            }
            // If x-coordinates are same, tricky tie-breaking:
            // - Higher height start points come first: This ensures we capture the maximum height before any drops.
            // - Lower height end points come first: This ensures proper height drops when multiple buildings end at same x.
            // Simplified: Events with smaller height values (negative for start, positive for end) come first.
            // This means -10 (start) comes before -5 (start).
            // This means 5 (end) comes before 10 (end).
            // This means -10 (start) comes before +10 (end).
            return a[1] - b[1]; // Smaller value (more negative start or smaller positive end) first
        });

        // Step 3: Initialize Max-Heap
        // This heap will store the heights of all active buildings.
        // Max-heap so that peek() gives the current highest active building.
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(0); // Add ground level (0 height) to the heap

        int prevMaxHeight = 0; // Tracks the max height before current event

        // Step 4: Sweep Line Simulation
        for (int[] event : events) {
            int currentX = event[0];
            int currentHeight = event[1]; // This is -H for start, +H for end

            if (currentHeight < 0) { // It's a start point
                maxHeap.add(-currentHeight); // Add the actual positive height
            } else { // It's an end point
                maxHeap.remove(currentHeight); // Remove the height from active buildings
            }

            int currentMaxHeight = maxHeap.peek(); // Get the current maximum height from active buildings

            // If the current maximum height is different from the previous maximum height,
            // then we've found a new skyline point.
            if (currentMaxHeight != prevMaxHeight) {
                result.add(List.of(currentX, currentMaxHeight));
                prevMaxHeight = currentMaxHeight;
            }
        }

        return result;
    }
}
```

**Time Complexity:**

  * `O(N log N)`: Jahan `N` number of buildings hain.
      * Creating event points: `O(N)`.
      * Sorting event points: `O(N log N)` because there are `2N` events.
      * Iterating through events: `O(N)`.
      * Heap operations (add/remove/peek): Each takes `O(log K)` time, where `K` is the number of active buildings in the heap (at most `N`). In the worst case, heap operations can be `O(log N)`. Total `O(N log N)`.
      * Removing an element from a `PriorityQueue` takes `O(K)` in Java if the element is not the root, because it might need to iterate to find it. However, if the `PriorityQueue` implementation is smart about handling removal (e.g., using a HashMap to store positions of elements in the heap), it can be `O(log K)`. For this problem, competitive programming setups often assume `O(log K)` for remove. If strictly `O(K)`, then the complexity would be `O(N*N)` in worst case. A custom heap or a `TreeMap` can guarantee `O(log N)` removal. In competitive programming context, usually `PriorityQueue` is considered `logN` for all operations in this type of problem.

**Space Complexity:**

  * `O(N)`: For storing event points and the `PriorityQueue`.

-----

# 9\. Copy List with Random Pointers

**Problem Statement:**

Aapko ek linked list di gayi hai jismein `n` nodes hain. Har node mein teen cheezein hain: `val` (integer value), `next` pointer (next node ko point karta hai), aur `random` pointer (list ke kisi bhi node ko ya `null` ko point kar sakta hai).

Aapko is list ki ek **deep copy** banani hai. Deep copy matlab, naye nodes create hone chahiye aur naye nodes ke `next` aur `random` pointers original list ke corresponding nodes ki copy ko point karne chahiye. Aapko copied list ka head return karna hai.

**Example:**

```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
Explanation: The output is a deep copy of the input list.
```

*Note: The random pointer values in the input/output are indices of nodes, not actual node values.*

**Key Concepts/Challenges:**

  * **Hash Map:** Original node se copied node ki mapping store karna.
  * **Two-Pass Approach:** Pointers ko correctly copy karne ke liye multiple iterations.
  * **Complex Pointer Copying:** `random` pointers ko handle karna tricky hai kyunki wo aage ya peeche (ya `null`) point kar sakte hain.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke kai tareeke hain, lekin **Hash Map approach** sabse straightforward aur clear hai.

**Two-Pass Hash Map Approach:**

1.  **First Pass (Nodes Creation and `next` Pointers):**

      * Ek `HashMap` banao (`oldToNewMap`) jo original `Node` objects ko unke corresponding copied `Node` objects se map karega.
      * Original list ko traverse karo (`curr` pointer use karke).
      * Har `curr` node ke liye:
          * Ek naya `Node` banao, uski `val` original `curr.val` ke barabar rakho.
          * Is naye node ko `oldToNewMap` mein store karo: `oldToNewMap.put(curr, newNode)`.
      * Is pass ke end mein, aapke paas saare original nodes ke liye unki copies `oldToNewMap` mein hongi. `next` aur `random` pointers abhi tak set nahi kiye hain.

2.  **Second Pass (`next` aur `random` Pointers ki Setting):**

      * Original list ko fir se traverse karo (fir se `curr` pointer use karke).
      * Har `curr` node ke liye:
          * Uski copied `newNode` ko `oldToNewMap.get(curr)` se retrieve karo.
          * `newNode.next` ko set karo: `oldToNewMap.get(curr.next)`. (Agar `curr.next` `null` hai, toh `oldToNewMap.get(null)` `null` hi return karega).
          * `newNode.random` ko set karo: `oldToNewMap.get(curr.random)`. (Agar `curr.random` `null` hai, toh `oldToNewMap.get(null)` `null` hi return karega).

3.  **Result Return Karo:**

      * `oldToNewMap.get(head)` return karo. Ye original list ke head node ki copied version ka reference hoga, jo hamari deep copy list ka head hai.

**Dry Run Example:**

`head = [[7,null],[13,0]]` (Original list: 7 -\> 13 -\> null; 7's random: null, 13's random: 7)

`Node` class assumption: `Node {int val; Node next; Node random;}`

1.  **First Pass:**

      * `oldToNewMap = {}`

      * `curr` starts at Node `7`

          * Create `newNode7` (val: 7).
          * `oldToNewMap.put(Node7, newNode7)`

      * `curr` moves to Node `13`

          * Create `newNode13` (val: 13).
          * `oldToNewMap.put(Node13, newNode13)`

      * `curr` becomes `null`. Loop ends.

      * `oldToNewMap` now: `{Node7: newNode7, Node13: newNode13}`

2.  **Second Pass:**

      * `curr` starts at Node `7`
          * `newNode7 = oldToNewMap.get(Node7)`
          * `newNode7.next = oldToNewMap.get(Node7.next)` (i.e., `oldToNewMap.get(Node13)`) which is `newNode13`.
              * So, `newNode7.next = newNode13`.
          * `newNode7.random = oldToNewMap.get(Node7.random)` (i.e., `oldToNewMap.get(null)`) which is `null`.
              * So, `newNode7.random = null`.
      * `curr` moves to Node `13`
          * `newNode13 = oldToNewMap.get(Node13)`
          * `newNode13.next = oldToNewMap.get(Node13.next)` (i.e., `oldToNewMap.get(null)`) which is `null`.
              * So, `newNode13.next = null`.
          * `newNode13.random = oldToNewMap.get(Node13.random)` (i.e., `oldToNewMap.get(Node7)`) which is `newNode7`.
              * So, `newNode13.random = newNode7`.
      * `curr` becomes `null`. Loop ends.

3.  **Result:** `oldToNewMap.get(head)` (i.e., `oldToNewMap.get(Node7)`) jo ki `newNode7` hai.

The new list looks like: `newNode7` (val 7, next `newNode13`, random `null`) -\> `newNode13` (val 13, next `null`, random `newNode7`). This is a correct deep copy.

**Code Structure (Java-like Pseudocode):**

```java
import java.util.HashMap;
import java.util.Map;

// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

class Solution {
    public Node copyRandomList(Node head) {
        // Edge case: empty list
        if (head == null) {
            return null;
        }

        // Step 1: Create a HashMap to store mappings from original nodes to new copied nodes.
        // This is crucial for handling random pointers correctly, as they might point to
        // nodes that haven't been copied yet during a single pass.
        Map<Node, Node> oldToNewMap = new HashMap<>();

        // First pass: Create all new nodes and store them in the map.
        // We only copy 'val' here. 'next' and 'random' pointers will be set in the second pass.
        Node current = head;
        while (current != null) {
            oldToNewMap.put(current, new Node(current.val));
            current = current.next;
        }

        // Second pass: Iterate through the original list again to set 'next' and 'random' pointers
        // for the newly created nodes using the mapping.
        current = head;
        while (current != null) {
            Node newNode = oldToNewMap.get(current); // Get the copied node for the current original node

            // Set the 'next' pointer of the new node
            // If current.next is null, oldToNewMap.get(null) will return null, which is correct.
            newNode.next = oldToNewMap.get(current.next);

            // Set the 'random' pointer of the new node
            // If current.random is null, oldToNewMap.get(null) will return null, which is correct.
            newNode.random = oldToNewMap.get(current.random);

            current = current.next; // Move to the next original node
        }

        // Return the head of the copied list.
        // This is the copied node that corresponds to the original head.
        return oldToNewMap.get(head);
    }
}
```

**Alternative Approach (O(1) Space - Tricky\!):**

Ek aur solution hai jo `O(1)` extra space use karta hai (HashMap use nahi karta). Usmein teen passes hote hain:

1.  **First Pass:** Har original node ke theek baad uski copy banao aur `next` link set karo. (`A -> B` banega `A -> A' -> B -> B'`).
2.  **Second Pass:** Ab `random` pointers set karo. Agar `A.random` `B` ko point karta hai, toh `A'.random` `B'` ko point karega. (`A'.random = A.random.next`).
3.  **Third Pass:** Original aur copied list ko alag karo. (`A -> B` aur `A' -> B'`).

Ye approach code mein thodi complex hoti hai but space efficient hai.

**Time Complexity (for Hash Map approach):**

  * `O(N)`: Jahan `N` number of nodes hain list mein.
      * First pass: `O(N)` for creating nodes and putting into map.
      * Second pass: `O(N)` for setting pointers.
      * HashMap operations (put, get) take `O(1)` on average.

**Space Complexity (for Hash Map approach):**

  * `O(N)`: `HashMap` store karta hai `N` key-value pairs (`N` original nodes aur `N` copied nodes).

-----
# 10\. Binary Tree Maximum Path Sum

**Problem Statement:**

Ek non-empty binary tree diya gaya hai. Aapko usmein **maximum path sum** dhoondhna hai.

Ek path tree ke kisi bhi node se start ho sakta hai aur kisi bhi doosre node tak ja sakta hai. Path mein har node ko at most ek baar include kiya jayega. Path mein kam se kam ek node hona chahiye aur usko downward ya upward move karne ki zaroorat nahi hai (yaani, parent se child ya child se parent, kisi bhi direction mein ho sakta hai).

**Example:**

```
Input: root = [1,2,3]
Output: 6
Explanation: Path 2 -> 1 -> 3 (sum = 2 + 1 + 3 = 6)
```

**Key Concepts/Challenges:**

  * **Recursion / DFS (Depth-First Search):** Tree traversal ka use karna.
  * **Path Sum Tracking:** Sirf ek branch se aane wala maximum sum aur complete path ka maximum sum, dono ko track karna.
  * **Global Maximum:** Poore tree mein sabse bade path sum ko ek global variable mein update karna.

**Intuition aur Approach (Hinglish Mein):**

Ye problem thodi tricky hai kyunki path kisi bhi direction mein jaa sakta hai (upar bhi aur neeche bhi). Isko solve karne ke liye hum **recursion (DFS)** ka use karenge, aur do cheezon ko track karenge:

1.  **Branch Sum:** Ek node se start hokar *sirf ek branch* (ya toh left child ya right child) mein jaate hue maximum sum kya ho sakta hai. Ye sum negative nahi hona chahiye (agar negative hai toh zero le lenge, kyunki negative sum path ko chhota karega). Ye wo value hai jo node apne parent ko "pass up" karega.
2.  **Global Max Path Sum:** Poore tree mein ab tak ka sabse bada path sum jo humne dekha hai. Ye path node ke root se shuru ho sakta hai, left subtree se aa sakta hai, ya right subtree se aa sakta hai, ya fir left subtree -\> node -\> right subtree tak bhi jaa sakta hai.

**DFS Function (`dfs(node)`):**

  * Ye function ek node `node` ko input lega aur us node se start hokar *sirf ek branch* mein (child ki taraf) jaate hue maximum sum return karega.

  * **Base Case:** Agar `node` `null` hai, toh `0` return karo (kyunki null node se koi sum nahi aata).

  * **Recursive Calls:**

      * Left child ke liye `dfs(node.left)` call karo. Isse `leftSum` milega (left branch ka max sum).
      * Right child ke liye `dfs(node.right)` call karo. Isse `rightSum` milega (right branch ka max sum).

  * **Handle Negative Sums:** `leftSum` aur `rightSum` agar negative hain, toh unhe `0` maan lo. Kyunki humein path sum maximize karna hai, toh negative branch ko include karna faaydemand nahi hoga.

      * `leftSum = Math.max(0, dfs(node.left))`
      * `rightSum = Math.max(0, dfs(node.right))`

  * **Update Global Max Path Sum:**

      * Har node par hum ek potential maximum path sum calculate kar sakte hain jo **current node ke through** jaata hai. Ye path aisa hoga: `left branch sum + current node's value + right branch sum`.
      * Is sum ko hamare **global `maxPathSum`** variable se compare karo aur `maxPathSum` ko update karo:
          * `currentPathThroughNode = node.val + leftSum + rightSum`
          * `maxPathSum = Math.max(maxPathSum, currentPathThroughNode)`

  * **Return Value (Branch Sum):**

      * Ab ye function apne **parent** ko kya return karega? Ye node apne parent ke path ka hissa ban sakta hai, toh isko apni value aur apne kisii ek (left ya right) branch ke maximum sum ko return karna hoga.
      * `return node.val + Math.max(leftSum, rightSum)` (Hum `node.val` ko add karte hain kyunki path mein current node included hai).

**Main Function (`maxPathSum(root)`):**

  * Ek **global variable** `maxPathSum` initialize karo `Integer.MIN_VALUE` se (ya `root.val` se, agar tree mein sirf ek node ho toh).
  * `dfs(root)` function ko call karo.
  * `maxPathSum` return karo.

**Dry Run Example:**

`root = [1,2,3]` (Tree structure: 1 is root, 2 is left child, 3 is right child)

1.  **Initialize:** `maxPathSum = Integer.MIN_VALUE`

2.  **Call `dfs(node_1)`:**

      * `leftSum = dfs(node_2)`
          * Inside `dfs(node_2)`:
              * `leftSum_2 = dfs(null)` returns `0`.
              * `rightSum_2 = dfs(null)` returns `0`.
              * `currentPathThroughNode_2 = 2 + 0 + 0 = 2`.
              * `maxPathSum = Math.max(MIN_VALUE, 2) = 2`.
              * Return `2 + Math.max(0, 0) = 2`. (This is `leftSum` for node 1).
      * `leftSum` is `2`.
      * `rightSum = dfs(node_3)`
          * Inside `dfs(node_3)`:
              * `leftSum_3 = dfs(null)` returns `0`.
              * `rightSum_3 = dfs(null)` returns `0`.
              * `currentPathThroughNode_3 = 3 + 0 + 0 = 3`.
              * `maxPathSum = Math.max(2, 3) = 3`.
              * Return `3 + Math.max(0, 0) = 3`. (This is `rightSum` for node 1).
      * `rightSum` is `3`.
      * `currentPathThroughNode_1 = node_1.val + leftSum + rightSum = 1 + 2 + 3 = 6`.
      * `maxPathSum = Math.max(3, 6) = 6`.
      * Return `1 + Math.max(2, 3) = 1 + 3 = 4`. (This value is returned to parent, though here node 1 is root, so no parent).

3.  **Final Result:** `maxPathSum = 6`. Correct.


```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    // Global variable to store the maximum path sum found so far
    private int maxPathSum;

    public int maxPathSum(TreeNode root) {
        // Initialize maxPathSum to the smallest possible integer value
        // or to root.val if you are sure root is not null and there's at least one node.
        maxPathSum = Integer.MIN_VALUE;
        
        // Call the recursive helper function
        dfs(root);
        
        return maxPathSum;
    }

    // This DFS function returns the maximum path sum *starting from the current node
    // and going downwards into one of its branches*.
    private int dfs(TreeNode node) {
        // Base case: If node is null, no path exists, return 0 sum.
        if (node == null) {
            return 0;
        }

        // Recursively calculate the maximum path sum from left and right children.
        // If a branch sum is negative, it's better to not include it, so take 0 instead.
        int leftGain = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        // Calculate the path sum if it includes the current node AND goes through both left and right branches.
        // This is a candidate for the global maximum path sum.
        // It's like an 'inverted V' shape path: left_child_path -> current_node -> right_child_path.
        int currentPathThroughNode = node.val + leftGain + rightGain;
        
        // Update the global maximum path sum.
        maxPathSum = Math.max(maxPathSum, currentPathThroughNode);

        // This function returns the maximum path sum *starting from the current node
        // and extending upwards to its parent through only ONE of its children*.
        // We choose the branch (left or right) that gives the maximum gain.
        return node.val + Math.max(leftGain, rightGain);
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` tree mein nodes ki sankhya hai. Har node ko hum exactly ek baar visit karte hain `dfs` function mein.

**Space Complexity:**

  * `O(H)`: Jahan `H` tree ki height hai. Ye recursion stack ki depth ki wajah se hota hai. Worst case mein (skewed tree) `H` `N` ke barabar ho sakti hai, toh `O(N)`. Best case mein (balanced tree) `O(log N)`.

-----

# 11\. Word Ladder

**Problem Statement:**

Aapko do words, `beginWord` aur `endWord`, aur ek word list `wordList` di gayi hai. Aapko `beginWord` se `endWord` tak ki shortest transformation sequence ki length dhoondhni hai.

Transformation rules:

1.  Har transformation mein sirf ek letter change ho sakta hai.
2.  Har transformed word `wordList` mein hona chahiye. `beginWord` `wordList` mein ho sakta hai ya nahi.

Agar koi transformation sequence exist nahi karta, toh `0` return karo.

**Example:**

```
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5
Explanation: Shortest sequence is "hit" -> "hot" -> "dot" -> "dog" -> "cog". Length 5.
```

**Key Concepts/Challenges:**

  * **BFS (Breadth-First Search):** Shortest path problems ke liye BFS sabse accha hai, kyunki BFS level-by-level explore karta hai, jisse pehli baar `endWord` milne par wo shortest path hi hogi.
  * **Implicit Graph:** Words aur unki single-letter-difference relations ek graph banate hain. Nodes words hain aur edges tab hain jab do words mein ek letter ka difference ho. Ye graph explicitly diya nahi hai.
  * **Efficient Neighbor Finding:** Har word ke possible next transformations ko efficiently find karna.

**Intuition aur Approach (Hinglish Mein):**

Ye problem ek **shortest path problem** hai ek **unweighted graph** mein. Toh, **BFS** bilkul perfect algorithm hai iske liye.

**Graph ki tarah kaise dekhein:**

  * Har word ek **node** hai.
  * Do words ke beech ek **edge** hai agar unmein sirf ek character ka difference hai.

**BFS Steps:**

1.  **Setup:**

      * Ek `Set` banao (`wordSet`) `wordList` se saare words ko store karne ke liye. Set se lookup `O(1)` mein hota hai.
      * Agar `endWord` `wordSet` mein nahi hai, toh seedhe `0` return kar do, kyunki `endWord` tak pahunch hi nahi sakte.
      * Ek `Queue` banao (`queue`) aur usmein `beginWord` ko add karo.
      * Ek `Set` banao (`visited`) jo `queue` mein add kiye gaye words ko track karega, taaki hum cycles avoid kar sakein aur redundancy na ho. `beginWord` ko `visited` mein add karo.
      * `level = 1` se initialize karo. Ye transformation sequence ki current length track karega.

2.  **BFS Loop:**

      * Jab tak `queue` empty nahi ho jaati:
          * Current `level` ke saare words ko process karne ke liye loop chalao (`size = queue.size()`).
          * Is `level` mein, `size` times loop chalao:
              * `currentWord = queue.poll()`
              * Agar `currentWord` `endWord` ke barabar hai, toh `level` return kar do (ye shortest path ki length hai).
              * **Find Neighbors:** `currentWord` se single character change karke kitne valid words ban sakte hain, wo dhoondho:
                  * `currentWord` ko character array mein convert karo (`charArray`).
                  * Har position (`i`) par, 'a' se 'z' tak har possible character (`c`) ko try karo:
                      * `originalChar = charArray[i]`
                      * `charArray[i] = c`
                      * `newWord = new String(charArray)`
                      * `charArray[i] = originalChar` (reset for next iteration)
                      * Agar `newWord` `wordSet` mein hai AND `visited` nahi hai:
                          * `visited.add(newWord)`
                          * `queue.add(newWord)`
          * Jab current `level` ke saare words process ho jayenge, `level++` karo (next level par move kar rahe hain).

3.  **No Path Found:**

      * Agar `queue` empty ho gayi aur `endWord` tak nahi pahunche, toh `0` return karo.

**Dry Run Example:**

`beginWord = "hit"`, `endWord = "cog"`, `wordList = ["hot","dot","dog","lot","log","cog"]`

1.  **Setup:**

      * `wordSet = {"hot", "dot", "dog", "lot", "log", "cog"}`
      * `endWord ("cog")` is in `wordSet`. OK.
      * `queue = ["hit"]`
      * `visited = {"hit"}`
      * `level = 1`

2.  **BFS Loop:**

      * **Level 1 (`currentWord = "hit"`):**

          * `size = 1`. Pop "hit".
          * Generate neighbors of "hit":
              * "ait", "bit", ... "hot" (change 'h' to 'o') -\> `wordSet` mein hai, `visited` nahi.
                  * `visited.add("hot")` -\> `visited = {"hit", "hot"}`
                  * `queue.add("hot")` -\> `queue = ["hot"]`
              * "hat", "hbt", ... "hit" (no change) ... "hut" ...
              * "hia", "hib", ... "hix" ...
          * End of level 1. `level = 2`.

      * **Level 2 (`currentWord = "hot"`):**

          * `size = 1`. Pop "hot".
          * Generate neighbors of "hot":
              * "dot" (change 'h' to 'd') -\> `wordSet` mein hai, `visited` nahi.
                  * `visited.add("dot")` -\> `visited = {"hit", "hot", "dot"}`
                  * `queue.add("dot")` -\> `queue = ["dot"]`
              * "lot" (change 'h' to 'l') -\> `wordSet` mein hai, `visited` nahi.
                  * `visited.add("lot")` -\> `visited = {"hit", "hot", "dot", "lot"}`
                  * `queue.add("lot")` -\> `queue = ["dot", "lot"]`
              * ...
          * End of level 2. `level = 3`.

      * **Level 3 (`currentWord = "dot"`, then `"lot"`):**

          * `size = 2`.
          * Pop "dot".
              * Generate neighbors of "dot":
                  * "dog" (change 't' to 'g') -\> `wordSet` mein hai, `visited` nahi.
                      * `visited.add("dog")` -\> `visited = {"hit", "hot", "dot", "lot", "dog"}`
                      * `queue.add("dog")` -\> `queue = ["lot", "dog"]`
                  * ...
          * Pop "lot".
              * Generate neighbors of "lot":
                  * "log" (change 't' to 'g') -\> `wordSet` mein hai, `visited` nahi.
                      * `visited.add("log")` -\> `visited = {"...", "dog", "log"}`
                      * `queue.add("log")` -\> `queue = ["dog", "log"]`
                  * ...
          * End of level 3. `level = 4`.

      * **Level 4 (`currentWord = "dog"`, then `"log"`):**

          * `size = 2`.
          * Pop "dog".
              * Generate neighbors of "dog":
                  * "cog" (change 'd' to 'c') -\> `wordSet` mein hai, `visited` nahi.
                      * `visited.add("cog")` -\> `visited = {"...", "dog", "log", "cog"}`
                      * `queue.add("cog")` -\> `queue = ["log", "cog"]`
                  * **"cog" == `endWord`\! Return `level` (which is 4).**

\*Wait, example output is 5. What went wrong in dry run? Ah, the problem defines "length" as number of words in the sequence. My `level` variable starts at 1 and increments. So, if `beginWord` is "hit" (level 1), and `hot` is level 2, `dot` is level 3, `dog` is level 4, `cog` is level 5. My `level` variable tracks the number of transformations + 1. So if "cog" is found at `level` 4, it means 4 steps from begin word, which makes 5 words in sequence. Yes, the example output 5 means 5 words. So my `level` variable is correct, it should be 5.
Let's trace the level again:
hit (level 1)
hot (level 2)
dot, lot (level 3)
dog, log (level 4)
cog (level 5 - when it's *pulled* from the queue)

My `level` variable increments *after* processing a full level. So if `cog` is found when `currentWord` is popped at `level` 4, that means it's the 4th transformation, making 5 words. The solution would return `level + 1` if it processes *before* incrementing, or just `level` if it increments *after*. Let's stick to the code where `level` is incremented after the loop. If `endWord` is popped, the current `level` value *is* the answer.

Let's re-verify the `level` logic in the code. If `level` starts at 1 for `beginWord`, then `hit -> hot` is 2 levels (2 words). If "cog" is found while processing words at `level` 4 (meaning, it was added to queue at level 3, making it the 4th word in sequence), then the result should be `level+1`. Ah, the standard BFS for shortest path usually starts `steps = 0` and then `steps++` when pushing to queue or after processing level. If `beginWord` is level 1, and `cog` is found after 4 transformations, it means 5 words are involved. So the last `level` when `cog` is found should be the result.

Let's adjust. If `level` starts at `1` for the `beginWord`, and we return `level` when `endWord` is found.
hit -\> hot (level 2)
hot -\> dot (level 3)
dot -\> dog (level 4)
dog -\> cog (level 5)
So, when `cog` is popped, `level` would be 5. This aligns. My dry run had a slight miscalculation on what `level` would be when `cog` is *popped*. It would be `4` in my dry run's current setup. So, I would need `level + 1`.

Let's adjust the `level` logic slightly for clarity:
`level` represents the number of transformations done so far. `beginWord` itself is `0` transformations.
`level = 0`. Add `(beginWord, 0)` to queue.
When `currentWord` is popped, its length is `current_length`. Neighbors are pushed with `current_length + 1`.
If `endWord` is found, return its `current_length + 1`.

Or, easier for this problem: `level` is the number of words in the sequence.
`level = 1`. Add `beginWord` to queue.
When `endWord` is found (popped), return `level`. Increment `level` after each layer.

My `level` variable in the dry run (`level = 1` initially, `level++` at end of inner loop) should work.
When "hit" is popped, `level = 1`. Its neighbors are pushed.
When "hot" is popped, `level = 2`. Its neighbors "dot", "lot" are pushed.
When "dot" is popped, `level = 3`. Its neighbors "dog" is pushed.
When "dog" is popped, `level = 4`. Its neighbors "cog" is pushed.
When "cog" is popped, `level = 5`. It's `endWord`. Return `level` (which is 5).

Okay, my dry run result 4 was an error, the code would actually produce 5. This is because `level` increments *after* the inner loop, so when `cog` is extracted, `level` would correctly reflect the sequence length.

**Code Structure (Java-like Pseudocode):**

```java
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Create a Set for O(1) average time lookups for wordList
        Set<String> wordSet = new HashSet<>(wordList);

        // If endWord is not in the wordList, it's impossible to reach it.
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // BFS Queue: Stores words to visit
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);

        // Visited Set: To avoid cycles and re-processing words
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        // Level represents the length of the transformation sequence (number of words).
        // beginWord itself is length 1.
        int level = 1;

        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();

            // Process all words at the current level
            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = queue.poll();

                // If we reached the endWord, return the current level.
                if (currentWord.equals(endWord)) {
                    return level;
                }

                // Find all possible next words by changing one character at a time
                char[] charArray = currentWord.toCharArray();
                for (int j = 0; j < charArray.length; j++) {
                    char originalChar = charArray[j]; // Store original char to backtrack

                    // Try changing current character to 'a' through 'z'
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c;
                        String newWord = new String(charArray);

                        // If the new word is in wordSet and hasn't been visited yet
                        if (wordSet.contains(newWord) && !visited.contains(newWord)) {
                            visited.add(newWord);
                            queue.add(newWord);
                        }
                    }
                    charArray[j] = originalChar; // Restore original char for next position
                }
            }
            // Move to the next level
            level++;
        }

        // If the queue becomes empty and endWord was not reached
        return 0;
    }
}
```

**Time Complexity:**

  * `O(L * N * 26)`:
      * `N`: Number of words in `wordList` (effectively, number of nodes in graph).
      * `L`: Length of each word.
      * `26`: Number of possible alphabet characters (constant).
      * In the worst case, we might visit all `N` words. For each word, we iterate through its `L` characters. For each character, we try 26 possible changes. `Set.contains()` and `Set.add()` are `O(L)` (for string hashing) on average. So, `N * L * 26 * L` (approx), simplified to `O(N * L^2)` average.
      * However, if we consider word hashing/comparisons as O(L), the overall time complexity for generating and checking neighbors dominates. The number of edges can be up to `N * L * 26`. BFS complexity is `O(V + E)`. Here `V` is `N`, `E` is `N * L * 26`. So `O(N + N * L * 26)` which simplifies to `O(N * L * 26)`.

**Space Complexity:**

  * `O(N * L)`: `wordSet`, `queue`, aur `visited` set mein total `N` words store ho sakte hain, each of length `L`.

-----

### 12\. Longest Substring with At Most K Distinct Characters

**Problem Statement:**

Aapko ek string `s` aur ek integer `k` diya gaya hai. Aapko `s` ka sabse lamba substring dhoondhna hai jismein `k` se zyada distinct characters na hon.

**Example:**

```
Input: s = "eceba", k = 2
Output: 3
Explanation: The longest substring with at most 2 distinct characters is "ece".
```

**Key Concepts/Challenges:**

  * **Sliding Window:** Dynamic window size ko adjust karna.
  * **Hash Map for Distinct Character Count:** Window mein distinct characters ki frequencies aur total count track karna.

**Intuition aur Approach (Hinglish Mein):**

Ye problem **Sliding Window** technique ka classic example hai. Hum ek window (`left` aur `right` pointers se defined) ko string `s` par move karenge.

**Algorithm Steps:**

1.  **Setup:**

      * `left = 0`, `right = 0` (window pointers).
      * `maxLength = 0` (to store the answer).
      * Ek `HashMap` banao (`charFrequencies`) jo current window mein har character ki frequency store karega.

2.  **Expand the Window (Right Pointer Move Karega):**

      * `right` pointer ko `s` ke upar move karte jayenge (iterate karenge from `0` to `s.length() - 1`).
      * Har `right` pointer par, `charR = s.charAt(right)` ko `charFrequencies` mein add karo ya uski frequency badhao.

3.  **Shrink the Window (Left Pointer Move Karega - Jab Condition Violate Ho Jaye):**

      * Har baar `right` pointer ko move karne ke baad, check karo ki `charFrequencies.size()` (`distinct characters ka count`) `k` se zyada toh nahi ho gaya.
      * Agar `charFrequencies.size() > k` hai, toh window ko left se shrink karna shuru karo:
          * `charL = s.charAt(left)` ko `charFrequencies` se remove karo (uski frequency ghatao).
          * Agar `charL` ki frequency `0` ho jaati hai `charFrequencies` mein, toh us character ko `HashMap` se completely remove kar do (kyunki ab wo distinct count mein nahi aayega).
          * `left++` (left pointer ko aage badhao).
          * Ye shrinking process tab tak chalao jab tak `charFrequencies.size() <= k` na ho jaaye.

4.  **Update Max Length:**

      * Har baar jab window valid hoti hai (`charFrequencies.size() <= k`), current window ki length calculate karo (`right - left + 1`).
      * `maxLength = Math.max(maxLength, currentWindowLength)` se `maxLength` ko update karo.

5.  **Final Result:**

      * Loop ke end mein `maxLength` return karo.



```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // Edge cases
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        }

        int left = 0; // Left pointer of the sliding window
        int maxLength = 0; // Stores the maximum length found

        // HashMap to store frequencies of characters in the current window
        Map<Character, Integer> charFrequencies = new HashMap<>();

        // Right pointer iterates through the string
        for (int right = 0; right < s.length(); right++) {
            char charR = s.charAt(right);
            // Add or increment frequency of the character at right pointer
            charFrequencies.put(charR, charFrequencies.getOrDefault(charR, 0) + 1);

            // While the number of distinct characters in the window is greater than k
            while (charFrequencies.size() > k) {
                char charL = s.charAt(left);
                // Decrement frequency of the character at left pointer
                charFrequencies.put(charL, charFrequencies.get(charL) - 1);

                // If frequency becomes 0, remove the character from the map (it's no longer distinct in window)
                if (charFrequencies.get(charL) == 0) {
                    charFrequencies.remove(charL);
                }
                left++; // Shrink the window from the left
            }

            // After adjusting the window (if needed), update the maximum length
            // current window length is (right - left + 1)
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` string `s` ki length hai. Both `left` aur `right` pointers `N` times traverse karte hain. Har character `left` se ek baar add aur `right` se ek baar remove hota hai. HashMap operations `O(1)` average time lete hain.

**Space Complexity:**

  * `O(K)`: `HashMap` mein at most `K` distinct characters store honge. In worst case, `K` can be `26` (for English alphabet) or `128/256` (for ASCII). So it's essentially `O(1)` if `K` is bounded by alphabet size.

-----
# 13\. Sliding Window Maximum

**Problem Statement:**

Aapko integers ka ek array `nums` aur ek integer `k` diya gaya hai. Aapko ek *sliding window* define karna hai jo array ke left se right move karta hai. Har bar jab window move karti hai, aapko us window mein sabse bade element ki value return karni hai. Aapke final output mein saare sliding windows ke maximum values honge.

**Example:**

```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]

Explanation:
Window position                  Max
---------------                  -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

**Key Concepts/Challenges:**

  * **Sliding Window:** Window ko efficiently manage karna.
  * **Deque (Double-Ended Queue):** Window ke maximum element ko `O(1)` mein track karne ke liye. Specifically, ek `Monotonic Decreasing Deque` (elements left to right decreasing order mein honge) use karte hain.

**Intuition aur Approach (Hinglish Mein):**

Ye problem ek common **sliding window** pattern hai jise solve karne ke liye **Deque (Double-Ended Queue)** ka use karna sabse efficient tareeka hai. Deque mein hum window ke potential maximum elements ko store karte hain in decreasing order.

**Deque ka Role (Monotonic Decreasing Deque):**

Hum Deque mein `nums` array ke **indices** store karenge, na ki actual values. Ye indices is tarah se store honge ki:

1.  Deque ka front (left end) hamesha current window ke largest element ka index rakhega.
2.  Deque ke andar elements ke corresponding values decreasing order mein honge from front to back.

**Algorithm Steps:**

1.  **Setup:**

      * Ek `Deque<Integer>` banao (Java mein `LinkedList` ko `Deque` ki tarah use kar sakte hain).
      * Ek `List<Integer>` banao (`result`) jismein hum har window ke maximum values store karenge.
      * `n = nums.length`.

2.  **Iterate through `nums` array (using `right` pointer):**

      * `right` pointer `0` se `n-1` tak move karega.

      * **Step 1: Clean (Remove smaller elements from Deque's back):**

          * Jab tak `deque` empty nahi hai aur `nums[right]` Deque ke `last` element (`nums[deque.peekLast()]`) se bada ya barabar hai:
              * `deque.removeLast()` (kyunki `nums[right]` ab in purane chote elements ko future mein maximum nahi banne dega).
          * `deque.addLast(right)` (current `right` index ko Deque mein add karo).

      * **Step 2: Clean (Remove out-of-window elements from Deque's front):**

          * Agar `deque` ka `first` element (`deque.peekFirst()`) current window ke `left` boundary se bahar chala gaya hai (`deque.peekFirst() <= right - k`):
              * `deque.removeFirst()` (use hata do).

      * **Step 3: Record Maximum (Jab window fully form ho jaye):**

          * Jab `right` pointer `k-1` ya usse zyada index par pahunch jaye (matlab, window fully form ho gayi hai), tab current window ka maximum element Deque ke `first` index (`nums[deque.peekFirst()]`) par milega.
          * Is value ko `result` list mein add karo.

3.  **Return Result:**

      * `result` list ko `int[]` array mein convert karke return karo.

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        // Deque to store indices of elements in a decreasing order of their values.
        // The front of the deque will always hold the index of the largest element in the current window.
        Deque<Integer> dq = new ArrayDeque<>();
        
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            // Step 1: Remove elements from the front of the deque that are outside the current window.
            // The element at dq.peekFirst() is out of the window if its index is <= i - k.
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.removeFirst();
            }

            // Step 2: Remove elements from the back of the deque that are smaller than the current element (nums[i]).
            // These smaller elements can no longer be the maximum in any future window that includes nums[i].
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.removeLast();
            }

            // Step 3: Add the current element's index to the back of the deque.
            dq.addLast(i);

            // Step 4: If the window has formed (i.e., we have processed at least 'k' elements),
            // the maximum element for the current window is at the front of the deque.
            if (i >= k - 1) {
                result.add(nums[dq.peekFirst()]);
            }
        }

        // Convert List<Integer> to int[]
        int[] finalResult = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            finalResult[i] = result.get(i);
        }
        return finalResult;
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` `nums` array ki length hai. Har element array mein ek baar `dq.addLast()` hota hai aur at most ek baar `dq.removeFirst()` ya `dq.removeLast()` hota hai. Deque operations `O(1)` time lete hain.

**Space Complexity:**

  * `O(K)`: `Deque` mein at most `k` elements store honge (current window ke elements). `result` array `O(N-K+1)` size ka hoga. Overall `O(N)` if `k` is close to `N`, otherwise `O(K)`.

-----

# 14\. Word Break

**Problem Statement:**

Aapko ek non-empty string `s` aur ek dictionary of words `wordDict` diya gaya hai. Aapko determine karna hai ki `s` ko space-separated sequence of one or more dictionary words mein segment kiya ja sakta hai ya nahi.

`wordDict` mein same word multiple times aa sakta hai. `wordDict` mein duplicate words nahi hain.

**Example:**

```
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
```

**Key Concepts/Challenges:**

  * **Dynamic Programming (DP):** Overlapping subproblems aur optimal substructure.
  * **String Manipulation / Substring Checking:** Substrings ko check karna dictionary mein.
  * **Set for Dictionary Lookup:** Dictionary words ko `HashSet` mein store karna for `O(1)` average time lookup.

**Intuition aur Approach (Hinglish Mein):**

Ye problem ek classic **Dynamic Programming** problem hai. Hum ek boolean array `dp` banayenge jahan `dp[i]` `true` hoga agar `s` ka `0` se `i-1` tak ka substring (matlab, `s.substring(0, i)`) dictionary words mein segment kiya ja sakta hai.

**DP Table Definition:**
`dp[i]` = `true` if `s[0...i-1]` (prefix of length `i`) can be segmented into dictionary words.

**DP Table Dimensions:**
`dp` array ki size `s.length() + 1` hogi. `dp[0]` represents an empty string, which can always be segmented (considered true as a base case for building solutions).

**Filling the DP Table (Iteration):**

1.  **Setup:**

      * `wordDict` ko `HashSet<String>` mein convert karo (`wordSet`) for efficient `O(1)` lookups.
      * `dp` boolean array banao size `s.length() + 1` ka.
      * `dp[0] = true` set karo (empty string ko segment kiya ja sakta hai).

2.  **Iterate `i` from `1` to `s.length()`:** (`i` current prefix ki length hai)

      * Har `i` ke liye, hum check karenge ki `s[0...i-1]` segment ho sakta hai ya nahi.
      * Iske liye, hum `i` se pehle ke saare possible split points `j` (from `0` to `i-1`) ko explore karenge.
      * **Inner Loop (iterate `j` from `0` to `i-1`):**
          * Agar `dp[j]` `true` hai (matlab `s[0...j-1]` segment ho sakta hai).
          * AND `s.substring(j, i)` (jo `s` ka `j` index se `i-1` index tak ka substring hai) `wordSet` mein hai:
              * Toh `dp[i] = true` set karo aur inner loop se `break` kar do (kyunki agar ek tarika mil gaya segment karne ka toh bas, `true` set ho gaya).

3.  **Final Result:**

      * `dp[s.length()]` return karo.

**Dry Run Example:**

`s = "leetcode"`, `wordDict = ["leet", "code"]`

`s.length() = 8`
`wordSet = {"leet", "code"}`
`dp` array of size `9` (indices 0 to 8)
Initialize `dp` with `false`.
`dp[0] = true`.

```
Index:    0  1  2  3  4  5  6  7  8
Char:     (empty) l  e  e  t  c  o  d  e
dp:       T  F  F  F  F  F  F  F  F
```

**Outer Loop (`i` from 1 to 8):**

  * **`i = 1` (substring "l"):**
      * `j = 0`: `dp[0]` is `T`. `s.substring(0, 1)` is "l". "l" in `wordSet`? `F`.
      * `dp[1]` remains `F`.
  * **`i = 2` (substring "le"):**
      * `j = 0`: `dp[0]` is `T`. `s.substring(0, 2)` is "le". "le" in `wordSet`? `F`.
      * `j = 1`: `dp[1]` is `F`. Skip.
      * `dp[2]` remains `F`.
  * **`i = 3` (substring "lee"):** ... `dp[3]` remains `F`.
  * **`i = 4` (substring "leet"):**
      * `j = 0`: `dp[0]` is `T`. `s.substring(0, 4)` is "leet". "leet" in `wordSet`? `T`.
          * `dp[4] = true`. Break inner loop.
          * `dp = [T, F, F, F, T, F, F, F, F]`
  * **`i = 5` (substring "leetc"):**
      * `j = 0`: `dp[0]` is `T`. `s.substring(0, 5)` is "leetc". `F`.
      * `j = 1`: `dp[1]` is `F`. Skip.
      * `j = 2`: `dp[2]` is `F`. Skip.
      * `j = 3`: `dp[3]` is `F`. Skip.
      * `j = 4`: `dp[4]` is `T`. `s.substring(4, 5)` is "c". "c" in `wordSet`? `F`.
      * `dp[5]` remains `F`.
  * **`i = 6` (substring "leetco"):** ... `dp[6]` remains `F`.
  * **`i = 7` (substring "leetcoded"):** ... `dp[7]` remains `F`.
  * **`i = 8` (substring "leetcode"):**
      * `j = 0`: `dp[0]` is `T`. `s.substring(0, 8)` is "leetcode". `F`.
      * ... (skip `j=1` to `j=3` because `dp[j]` is false)
      * `j = 4`: `dp[4]` is `T`. `s.substring(4, 8)` is "code". "code" in `wordSet`? `T`.
          * `dp[8] = true`. Break inner loop.
          * `dp = [T, F, F, F, T, F, F, F, T]`

**End of Outer Loop.**

**Final Result:** `dp[8]` which is `true`. Matches example.

**Code Structure (Java-like Pseudocode):**

```java
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // For O(1) average time lookup
        Set<String> wordSet = new HashSet<>(wordDict);

        // dp[i] will be true if s.substring(0, i) can be segmented
        boolean[] dp = new boolean[s.length() + 1];

        // Base case: empty string can be segmented
        dp[0] = true;

        // Iterate through the string to fill up the dp array
        // 'i' represents the current length of the prefix (or end index + 1)
        for (int i = 1; i <= s.length(); i++) {
            // 'j' represents the split point. We check if s[0...j-1] is segmentable (dp[j] is true)
            // and s[j...i-1] is a word in the dictionary.
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true; // Found a valid segmentation
                    break; // Once true, no need to check other split points for this 'i'
                }
            }
        }

        // The final result is whether the entire string s can be segmented
        return dp[s.length()];
    }
}
```

**Time Complexity:**

  * `O(N^2 * L)`:
      * `N`: `s.length()`. Outer loop `i` runs `N` times, inner loop `j` runs `N` times.
      * `L`: Average length of words in `wordDict` and substrings of `s`. `s.substring(j, i)` operation takes `O(i-j)` which is `O(L)` in worst case. `wordSet.contains()` also takes `O(L)` on average for string hashing.
      * So, `N * N * L` -\> `O(N^2 * L)`.

**Space Complexity:**

  * `O(N + M * L)`:
      * `O(N)` for `dp` array.
      * `O(M * L)` for `wordSet`, where `M` is the number of words in `wordDict` and `L` is their average length.

-----

# 15\. Serialize and Deserialize Binary Tree

**Problem Statement:**

Aapko binary tree ko **serialize** (tree ko ek string mein convert karna) aur **deserialize** (string ko original tree structure mein wapas convert karna) karne ke functions implement karne hain.

Is problem ke liye koi specific serialization format ki constraint nahi hai, bas itna hai ki serialized string ko deserialize karke original tree ko recover kiya jaa sake.

**Example:**

```
Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]
Explanation:
Serialization: "1,2,3,null,null,4,5,null,null,null,null"
Deserialization: The tree is reconstructed.
```

**Key Concepts/Challenges:**

  * **Tree Traversal (Pre-order/BFS):** Serialization ke liye ek consistent traversal order.
  * **Null Node Handling:** Empty child nodes ko properly represent karna (e.g., "null" string se).
  * **Delimiter:** Values ko alag karne ke liye ek delimiter (e.g., comma `,`) use karna.
  * **String to Tree Reconstruction:** Deserialization mein string ko parse karke nodes create karna aur unke connections banana.
  * **Recursion:** Both serialization and deserialization can be elegantly done using recursion.

**Intuition aur Approach (Hinglish Mein):**

Is problem ko solve karne ke kai tareeke hain. Sabse common aur robust approach **Pre-order Traversal (DFS)** ya **Level-order Traversal (BFS)** ka use karna hai, jismein `null` nodes ko bhi explicitly record kiya jaata hai.

Hum **Pre-order Traversal (DFS)** approach discuss karenge, kyunki ye recursive nature ki wajah se kaafi concise hota hai.

**Serialization (Tree to String):**

1.  **Function Signature:** `serialize(TreeNode root)` jo `String` return karega.
2.  **Base Case:** Agar `root` `null` hai, toh "null" return karo aur uske baad ek delimiter (e.g., ",").
3.  **Recursive Step:**
      * Current `root.val` ko string mein append karo.
      * Uske baad ek delimiter (`comma`) lagao.
      * Left subtree ko recursively serialize karo: `serialize(root.left)`. Iska result current string mein append karo.
      * Right subtree ko recursively serialize karo: `serialize(root.right)`. Iska result current string mein append karo.
4.  **Helper Function:** Ek private helper function banao jo `StringBuilder` use karega, jisse efficient string building ho.


**Deserialization (String to Tree):**

1.  **Function Signature:** `deserialize(String data)` jo `TreeNode` return karega.
2.  **String Parsing:** Input `data` string ko delimiter (`comma`) se split karke ek array of strings (`nodesArray`) banao.
3.  **Index Pointer:** Ek `index` variable rakho jo `nodesArray` mein current position ko track karega. Use `0` se initialize karo.
4.  **Helper Function:** Ek private recursive helper function `deserializeHelper()` banao:
      * **Base Case:** Agar `nodesArray[index]` "null" hai:
          * `index` ko increment karo.
          * `null` return karo.
      * **Recursive Step:**
          * Current string value (`nodesArray[index]`) ko integer mein parse karo aur ek naya `TreeNode` banao.
          * `index` ko increment karo.
          * Naye node ka `left` child recursively `deserializeHelper()` ko call karke banao.
          * Naye node ka `right` child recursively `deserializeHelper()` ko call karke banao.
          * Naya node return karo.


```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Codec {

    // Global variable to keep track of the current index during deserialization
    // A better approach would be to pass it recursively or use a mutable wrapper
    private int deserializeIndex;

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        // Remove trailing comma if any, though not strictly necessary for this problem.
        // if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
        //     sb.deleteCharAt(sb.length() - 1);
        // }
        return sb.toString();
    }

    // Helper for serialization (Pre-order traversal)
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null").append(",");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // Split the string by comma to get individual node values/null indicators
        String[] nodes = data.split(",");
        deserializeIndex = 0; // Reset index for each deserialization call
        return deserializeHelper(nodes);
    }

    // Helper for deserialization (reconstruct tree from pre-order sequence)
    private TreeNode deserializeHelper(String[] nodes) {
        // If current index is out of bounds or current value is "null"
        if (deserializeIndex >= nodes.length || nodes[deserializeIndex].equals("null")) {
            deserializeIndex++; // Move to next position (consume the "null")
            return null;
        }

        // Create current node
        TreeNode node = new TreeNode(Integer.parseInt(nodes[deserializeIndex]));
        deserializeIndex++; // Move to next position

        // Recursively build left and right children
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);

        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
```

**Time Complexity:**

  * **Serialize:** `O(N)`: Jahan `N` tree mein nodes ki sankhya hai. Har node ko exactly ek baar visit kiya jaata hai. String building mein `StringBuilder` ka use karne se concatenation ka overhead `O(N)` ho jaata hai.
  * **Deserialize:** `O(N)`: `data.split(",")` `O(N)` leta hai (string length `N`). Recursive `deserializeHelper` bhi har node ko exactly ek baar visit karta hai.

**Space Complexity:**

  * **Serialize:** `O(N)`: `StringBuilder` mein `N` nodes ki values store hoti hain (plus "null" markers). Recursion stack ki depth `O(H)` hoti hai (H = height of tree), worst case `O(N)`.
  * **Deserialize:** `O(N)`: `nodes` array mein `N` string representations hote hain. Recursion stack `O(H)` leta hai.

-----
# 16\. Largest Rectangle in Histogram

**Problem Statement:**

Aapko non-negative integers ki ek array `heights` di gayi hai jo ek histogram ki bar heights ko represent karti hai. Har bar ki width `1` hai. Aapko histogram mein sabse bade rectangle ka area find karna hai.

**Example:**

```
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle has area = 10 unit. (The rectangle formed by bars with heights 5 and 6)
```

**Key Concepts/Challenges:**

  * **Stack (Monotonic Stack):** Yeh problem ko efficiently solve karne ka sabse popular tareeka hai. Ek stack jo heights ko increasing order mein maintain karta hai (ya indices).
  * **Next Smaller Element / Previous Smaller Element:** Stack ka use karke har bar ke liye uske left aur right mein pehle smaller bar ka index find karna.
  * **Divide and Conquer:** Yeh bhi ek approach hai, lekin stack-based solution zyada common aur efficient hai.

**Intuition aur Approach (Hinglish Mein):**

Yeh problem thodi mushkil lag sakti hai, lekin **Monotonic Stack** ka use karke ise kaafi elegant tarike se solve kiya ja sakta hai.

**Core Idea:**
Kisi bhi bar `i` ko agar hum consider karein, toh us bar ko height maan kar hum kitna bada rectangle bana sakte hain? Woh rectangle `i` ke left mein us point tak extend hoga jahan tak koi bar `heights[i]` se chhota nahi milta, aur `i` ke right mein bhi us point tak extend hoga jahan tak koi bar `heights[i]` se chhota nahi milta.

Iska matlab hai ki har bar `heights[i]` ke liye, humein do cheezein pata honi chahiye:

1.  **Left boundary:** `heights[i]` se chhota, left side mein, sabse pehla bar kaun sa hai (`left_smaller[i]`).
2.  **Right boundary:** `heights[i]` se chhota, right side mein, sabse pehla bar kaun sa hai (`right_smaller[i]`).

Agar humein yeh dono indices mil jayein, toh `heights[i]` ko height maan kar bane wale rectangle ki width hogi `(right_smaller[i] - left_smaller[i] - 1)`. Aur area hoga `heights[i] * width`. Maximum area ko track karte raho.

**Monotonic Stack ka Use:**

Monotonic stack (yahan **increasing monotonic stack**) indices store karega. Jab hum array ko traverse karte hain:

  * Agar current bar ki height stack ke top (yani `stack.peek()`) par rakhe index wale bar ki height se **zyada** hai, toh current index ko stack mein push kar do. Stack increasing order maintain karta rahega.
  * Agar current bar ki height stack ke top (index) wale bar ki height se **kam ya barabar** hai, toh iska matlab hai ki stack top wala element, `heights[current_index]` ke liye **right smaller** element mil gaya hai.
      * Stack se elements pop karte raho jab tak stack empty na ho jaye ya current bar ki height `stack.peek()` par rakhe index wale bar ki height se zyada na ho jaye.
      * Jab ek element pop hota hai (`popped_index`), toh uske liye `right_smaller[popped_index]` current `current_index` ho jayega.
      * Aur `left_smaller[popped_index]` kya hoga? Jo element stack mein ab `popped_index` ke neeche hai (`stack.peek()`) woh uska left smaller hoga. Agar stack empty ho gaya, toh `left_smaller[popped_index]` `-1` hoga.

Yeh approach thodi complex ho sakti hai single pass mein. Easier to understand hai do separate passes:

**Approach with Two Passes (Using Stack):**

1.  **Find `left_smaller` for all bars:**

      * Ek `left_smaller` array banao, size `n` ka.
      * Ek empty stack banao.
      * Loop `i` from `0` to `n-1`:
          * Jab tak stack empty nahi hai aur `heights[stack.peek()] >= heights[i]` hai, `stack.pop()` karo.
          * `left_smaller[i]` = `stack.isEmpty() ? -1 : stack.peek()`.
          * `stack.push(i)`.

2.  **Find `right_smaller` for all bars:**

      * Ek `right_smaller` array banao, size `n` ka. Initially sabko `n` (ya `n-1` as last possible right boundary) set kar do (default value agar koi right smaller nahi milta).
      * Stack ko clear karo.
      * Loop `i` from `n-1` down to `0`:
          * Jab tak stack empty nahi hai aur `heights[stack.peek()] >= heights[i]` hai, `stack.pop()` karo.
          * `right_smaller[i]` = `stack.isEmpty() ? n : stack.peek()`.
          * `stack.push(i)`.

3.  **Calculate Max Area:**

      * `max_area = 0`.
      * Loop `i` from `0` to `n-1`:
          * `current_width = right_smaller[i] - left_smaller[i] - 1`.
          * `current_area = heights[i] * current_width`.
          * `max_area = Math.max(max_area, current_area)`.

4.  **Return `max_area`.**


```java
import java.util.Stack;
import java.util.Arrays; // For Arrays.fill

class Solution {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int n = heights.length;
        int[] leftSmaller = new int[n];
        int[] rightSmaller = new int[n];
        
        // Initialize rightSmaller with n (default value indicating no smaller element to the right)
        Arrays.fill(rightSmaller, n); 

        Stack<Integer> stack = new Stack<>();

        // Step 1: Find leftSmaller for each bar
        // Iterate from left to right
        for (int i = 0; i < n; i++) {
            // Pop elements from stack that are greater than or equal to current height
            // because they cannot extend further left than current 'i'
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            // If stack is empty, it means no smaller element to the left (boundary is -1)
            // Otherwise, the element at stack.peek() is the first smaller to the left
            leftSmaller[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i); // Push current index onto stack
        }

        // Clear the stack for the next pass
        stack.clear();

        // Step 2: Find rightSmaller for each bar
        // Iterate from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Pop elements from stack that are greater than or equal to current height
            // because they cannot extend further right than current 'i'
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            // If stack is empty, it means no smaller element to the right (boundary is n)
            // Otherwise, the element at stack.peek() is the first smaller to the right
            rightSmaller[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i); // Push current index onto stack
        }

        // Step 3: Calculate the maximum area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            // Width of the rectangle if heights[i] is the smallest bar
            // Formula: right_smaller[i] - left_smaller[i] - 1
            int currentWidth = rightSmaller[i] - leftSmaller[i] - 1;
            int currentArea = heights[i] * currentWidth;
            maxArea = Math.max(maxArea, currentArea);
        }

        return maxArea;
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` `heights` array ki length hai. Hum array ko do baar traverse karte hain, aur stack mein har element `O(1)` amortized time mein push aur pop hota hai.

**Space Complexity:**

  * `O(N)`: `leftSmaller` aur `rightSmaller` arrays ke liye, aur `Stack` ke liye (worst case mein stack `N` elements tak hold kar sakta hai).

-----

# 17\. Word Search II

**Problem Statement:**

Aapko characters ka ek `m x n` `board` aur strings ki ek list `words` di gayi hai. Aapko `board` mein saare `words` find karne hain.

Word ko find karne ke rules:

  * Word adjacent (horizontal ya vertical) cells mein letters se ban sakta hai.
  * Har letter cell ko ek word mein sirf ek baar use kiya ja sakta hai.

**Example:**

```
Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]
```

**Key Concepts/Challenges:**

  * **Trie (Prefix Tree):** Saare `words` ko efficiently store karne aur prefix matching karne ke liye.
  * **Backtracking / DFS (Depth-First Search):** `board` par words search karne ke liye.
  * **Optimization:** Repeatedly visiting same paths ko avoid karna, aur jab koi prefix `Trie` mein exist na kare toh search ko prune karna.

**Intuition aur Approach (Hinglish Mein):**

Yeh problem **Trie** aur **Backtracking (DFS)** ka ek classic combination hai. Agar hum board par har cell se standard DFS karte har word ke liye, toh woh `O(M*N*4^L * W)` ho jayega (L = max word length, W = num words), jo bohot slow hoga.

**Core Idea:**
Instead of searching for each word in the board, hum **board se hi words generate** karne ki koshish karenge aur check karenge ki kya generated word `words` list mein hai ya nahi. Is process ko efficient banane ke liye, hum `words` list se ek `Trie` banayenge.

**Algorithm Steps:**

1.  **Build Trie:**

      * Saare `words` ko `Trie` mein insert karo. `TrieNode` mein ek flag bhi hona chahiye `isEndOfWord` aur `word` field (ya bas `isEndOfWord` aur value store karke baad mein `Trie` se word reconstruct karein) to mark word endings.

2.  **DFS on Board with Trie:**

      * Ek `Set<String>` banao (`foundWords`) jo saare unique words store karega jo board mein mil gaye hain.
      * `m = board.length`, `n = board[0].length`.
      * Board ke har cell `(r, c)` ke liye:
          * `dfs(board, r, c, trieRoot, foundWords)` function call karo.

3.  **`dfs(board, r, c, trieNode, foundWords)` Function:**

      * **Base Cases / Constraints:**

          * Agar `r` ya `c` board boundaries se bahar hain, return.
          * Agar `board[r][c]` pehle hi `visited` mark kiya gaya hai (ya koi special character, jaise '\#', use karke mark kar sakte hain), return.
          * `char ch = board[r][c]`. Agar `trieNode.children` mein `ch` nahi hai (yani yeh path `Trie` mein exist hi nahi karta), return (pruning).

      * **Recursive Step:**

          * `trieNode = trieNode.children[ch - 'a']`. `trieNode` ko update karo to match current character.

          * Agar `trieNode.isEndOfWord` `true` hai (matlab, humein ek complete word mil gaya hai):

              * `foundWords.add(trieNode.word)` (assuming `TrieNode` stores the word).
              * **Optional Optimization:** `trieNode.isEndOfWord = false` (word ko mark as found taaki duplicate add na ho aur ek word ek se zyada bar na find ho).
              * **Important Optimization (Pruning Trie):** If `trieNode` has no more children (or no other words pass through it after finding current word), you can remove this node from the trie to prune search paths. This is advanced but significantly improves performance for many test cases. For competitive programming, this is often implemented.

          * Current cell `board[r][c]` ko `visited` mark karo (e.g., `board[r][c] = '#'`).

          * DFS ko 4 directions mein call karo:

              * `dfs(board, r+1, c, trieNode, foundWords)` (down)
              * `dfs(board, r-1, c, trieNode, foundWords)` (up)
              * `dfs(board, r, c+1, trieNode, foundWords)` (right)
              * `dfs(board, r, c-1, trieNode, foundWords)` (left)

          * **Backtrack:** `board[r][c]` ko uski original character mein restore karo (`board[r][c] = ch`).

4.  **Return `new ArrayList<>(foundWords)`.**

**Trie Node Structure:**

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null; // Stores the word if this node represents the end of a word
    // Optional: int numChildren for optimization (if 0, prune)
}
```

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {

    // TrieNode definition
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null; // Stores the word if this node marks the end of a word
    }

    // Builds the Trie from the given words
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                int idx = ch - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.word = word; // Mark the end of a word
        }
        return root;
    }

    // Set to store the found words (uses Set to avoid duplicates)
    Set<String> foundWords = new HashSet<>();
    int M, N; // Board dimensions

    public List<String> findWords(char[][] board, String[] words) {
        M = board.length;
        N = board[0].length;

        // Step 1: Build the Trie from the dictionary words
        TrieNode root = buildTrie(words);

        // Step 2: Iterate through each cell of the board and start DFS from there
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                // Only start DFS if the character exists as a child in the Trie root
                // This is a basic pruning step
                if (root.children[board[r][c] - 'a'] != null) {
                    dfs(board, r, c, root);
                }
            }
        }

        return new ArrayList<>(foundWords);
    }

    // DFS function for searching words on the board
    private void dfs(char[][] board, int r, int c, TrieNode parentTrieNode) {
        // Current character at (r, c)
        char ch = board[r][c];
        
        // Get the TrieNode corresponding to the current character
        TrieNode currentTrieNode = parentTrieNode.children[ch - 'a'];

        // If this node marks the end of a word, add it to foundWords
        if (currentTrieNode.word != null) {
            foundWords.add(currentTrieNode.word);
            // Optimization: Set word to null to avoid adding the same word multiple times
            // if it can be formed by different paths or from different starting points.
            // Also, this helps prune paths if no other words share this prefix.
            currentTrieNode.word = null; 
        }

        // Mark the current cell as visited to avoid using it again in the same word path
        board[r][c] = '#'; // Any non-alphabet character will do

        // Define directions for movement (up, down, left, right)
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Explore all 4 adjacent cells
        for (int i = 0; i < 4; i++) {
            int newR = r + dr[i];
            int newC = c + dc[i];

            // Check boundary conditions
            if (newR >= 0 && newR < M && newC >= 0 && newC < N && board[newR][newC] != '#') {
                // Check if the next character exists in the Trie
                if (currentTrieNode.children[board[newR][newC] - 'a'] != null) {
                    dfs(board, newR, newC, currentTrieNode);
                }
            }
        }

        // Backtrack: Restore the character in the board
        board[r][c] = ch;

        // Optimization: If current TrieNode has no more children (i.e., no other words
        // branch off from this point), we can remove it from its parent's children.
        // This prunes the Trie and prevents exploring dead-end paths repeatedly.
        // This is a more advanced optimization, often added for efficiency.
        // This can be done by counting children or checking if all children are null.
        // A simple way is to check if it's no longer part of any other word.
        // For simplicity, it's often skipped in initial implementations,
        // but can be crucial for large test cases.
        // Example: if (currentTrieNode.numChildren == 0 && currentTrieNode.word == null) {
        //   parentTrieNode.children[ch - 'a'] = null;
        // }
    }
}
```

**Time Complexity:**

  * **Building Trie:** `O(W * L)`, where `W` is number of words and `L` is max length of a word.
  * **DFS on Board:** `O(M * N * 4^L)` in worst case without Trie pruning. With Trie and pruning, it's more efficient. Each cell `(M*N)` can be a starting point. From each cell, we explore up to `L` depth (max word length) in 4 directions. But because of Trie pruning, we don't explore paths that don't form valid prefixes. The actual complexity depends on how many common prefixes words share. Roughly, it's `O(M * N * (4^L))` but limited by `W * L` due to Trie's efficiency. A tighter bound is often `O(M * N * 3^L)` (since one direction is where we came from).

**Space Complexity:**

  * `O(Total characters in all words)` for Trie storage.
  * `O(L)` for recursion stack depth during DFS.
  * `O(W)` for `foundWords` set.

-----

# 18\. Longest Consecutive Sequence

**Problem Statement:**

Aapko unsorted integers ka ek array `nums` diya gaya hai. Aapko longest consecutive elements sequence ki length find karni hai.

Algorithm ko `O(n)` time complexity mein run karna chahiye.

**Example:**

```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Its length is 4.
```

**Key Concepts/Challenges:**

  * **Hash Set:** `O(1)` average time complexity mein elements ki presence check karne ke liye.
  * **Optimized Traversal:** Duplicate checks ko avoid karna.

**Intuition aur Approach (Hinglish Mein):**

Agar hum array ko sort karein toh `O(N log N)` mein easily solve kar sakte hain. Lekin problem ki constraint `O(N)` hai. Iske liye hum **HashSet** ka use karenge.

**Core Idea:**
Hum saare numbers ko pehle ek `HashSet` mein daal denge. Phir, har number `num` ko check karenge. Agar `num` kisi consecutive sequence ka **starting point** ho sakta hai (yani `num-1` `HashSet` mein exist nahi karta), toh hum us sequence ko explore karna shuru kar denge aur uski length count karenge.

**Algorithm Steps:**

1.  **Setup:**

      * `nums` array ko `HashSet<Integer>` mein convert karo (`numSet`) for `O(1)` average time lookups.
      * `longestStreak = 0` initialize karo.
      * Agar `nums` empty hai, `0` return kar do.

2.  **Iterate through `nums` (ya `numSet`):**

      * Har `num` in `numSet` ke liye:
          * **Check if `num` is a starting point:** `if (!numSet.contains(num - 1))`
              * Agar `num - 1` `numSet` mein nahi hai, toh `num` ek potential consecutive sequence ka start hai.
              * `currentNum = num`
              * `currentStreak = 1`
              * **Explore the sequence:**
                  * Jab tak `numSet` mein `currentNum + 1` exist karta hai:
                      * `currentNum++`
                      * `currentStreak++`
              * `longestStreak = Math.max(longestStreak, currentStreak)`

3.  **Return `longestStreak`.**

**Why `O(N)`?**
Har number ko `HashSet` mein daalne mein `O(N)` lagta hai. Loop `numSet` ke elements par chalta hai (`N` times). Inner `while` loop bhi chalta hai. Lekin, **important** baat yeh hai ki har number `numSet` mein **sirf ek baar** `currentNum++` ke zariye visited hota hai (tabhi jab woh kisi sequence ka part ho, aur us sequence ka start humne `if (!numSet.contains(num - 1))` se identify kiya ho). Jab `num` kisi sequence ka start nahi hota (yani `num-1` exist karta hai), toh hum us `num` ko `while` loop mein already visit kar chuke honge jab hum `num-1` se start hone wala sequence explore kar rahe honge. Isliye, overall time complexity `O(N)` average case mein hoti hai.

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Step 1: Add all numbers to a HashSet for O(1) average time lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;

        // Step 2: Iterate through each number in the HashSet
        for (int num : numSet) {
            // Check if 'num' is the starting point of a consecutive sequence
            // A number 'num' is a starting point if 'num - 1' does not exist in the set.
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // Step 3: Explore the consecutive sequence
                // Keep incrementing 'currentNum' and 'currentStreak' as long as the next number exists in the set.
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                // Update the overall longest streak found so far
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
```

**Time Complexity:**

  * `O(N)`: Jahan `N` `nums` array ki length hai.
      * Putting all `N` elements into `HashSet`: `O(N)` on average.
      * Iterating through `N` elements in `numSet`: Outer loop runs `N` times. Crucially, the inner `while` loop (exploring `currentNum + 1`) will only cause each number in `numSet` to be visited at most once across all outer loop iterations. So, the total number of operations involving `numSet.contains()` across all iterations will sum up to `O(N)`.

**Space Complexity:**

  * `O(N)`: For the `HashSet` to store all `N` numbers.

-----
# 19\. Course Schedule

**Problem Statement:**

Aapko `n` courses diye gaye hain, jinko `0` se `n-1` tak label kiya gaya hai. Aapko ek array `prerequisites` bhi di gayi hai, jahan `prerequisites[i] = [ai, bi]` ka matlab hai ki course `ai` lene ke liye aapko pehle course `bi` complete karna hoga.

Aapko yeh determine karna hai ki kya saare courses poore kiye ja sakte hain ya nahi.

**Example:**

```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.

Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. This is a cyclic dependency.
```

**Key Concepts/Challenges:**

  * **Graph:** Courses aur prerequisites ek Directed Graph banate hain. Courses nodes hain aur prerequisites directed edges hain (`bi` se `ai` tak).
  * **Cycle Detection:** Agar graph mein koi cycle hai, toh courses complete karna impossible hai.
  * **Topological Sort (Implicit):** Problem cycle detection par based hai, jo topological sort ka core concept hai.
  * **DFS (Depth-First Search) or BFS (Breadth-First Search - Kahn's Algorithm):** Cycle detection ke liye dono use kiye ja sakte hain.

**Intuition aur Approach (Hinglish Mein):**

Yeh problem basically ek **graph mein cycle detection** ki problem hai. Agar courses aur unke prerequisites ke beech koi cyclic dependency hai (jaise Course A requires B, B requires C, C requires A), toh un courses ko poora karna impossible hai. Agar koi cycle nahi hai, toh saare courses poore kiye ja sakte hain.

Hum do common approaches discuss karenge: **DFS** aur **Kahn's Algorithm (BFS based Topological Sort)**. BFS approach generally beginners ke liye thoda easier to implement hota hai.

-----

#### Approach 1: DFS (Depth-First Search) for Cycle Detection

**Idea:** DFS mein hum har node ko visit karte hain aur track karte hain ki kaun se nodes currently visited path mein hain. Agar hum ek node par wapas aate hain jo already current path mein hai, toh cycle hai.

**State Tracking (Coloring Nodes):**
Hum nodes ko teen states mein classify kar sakte hain:

  * **0 (Unvisited):** Node abhi tak visit nahi hua hai.
  * **1 (Visiting / In Recursion Stack):** Node abhi current DFS path mein hai (processing ho raha hai).
  * **2 (Visited / Processed):** Node aur uske saare descendants process ho chuke hain, aur us branch mein koi cycle nahi mila.

**Algorithm Steps:**

1.  **Graph Representation:**

      * Ek **Adjacency List** (`List<List<Integer>> graph`) banao. `graph[i]` mein saare courses honge jo `i` course ke baad liye ja sakte hain. Prerequisites `[a, b]` ka matlab `b -> a` edge hai. So, `graph[b].add(a)`.
      * Ek `int[] visited` array banao, size `numCourses` ka, sabko `0` se initialize karo.

2.  **DFS Function (`hasCycleDFS(course, graph, visited)`):**

      * Agar `visited[course] == 1` hai (currently visiting), toh **cycle detected**, `true` return karo.
      * Agar `visited[course] == 2` hai (already fully processed), toh is path mein koi cycle nahi hai, `false` return karo.
      * **Mark current course as visiting:** `visited[course] = 1`.
      * **Traverse neighbors:** Har `neighbor` (jo `graph[course]` mein hai) ke liye:
          * Agar `hasCycleDFS(neighbor, graph, visited)` `true` return karta hai, toh **cycle mila**, `true` return karo.
      * **Mark current course as processed:** `visited[course] = 2`.
      * Koi cycle nahi mila is path mein, `false` return karo.

3.  **Main Function (`canFinish(numCourses, prerequisites)`):**

      * Graph build karo.
      * Har course `i` from `0` to `numCourses-1` ke liye:
          * Agar `visited[i] == 0` hai (unvisited), toh `hasCycleDFS(i, graph, visited)` call karo.
          * Agar ye `true` return karta hai, toh `false` return karo (cycle mila).
      * Agar saare courses explore ho gaye aur koi cycle nahi mila, toh `true` return karo.


```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    // 0: unvisited, 1: visiting (in current DFS path), 2: visited (processed)
    private int[] visited; 
    private List<List<Integer>> adj; // Adjacency list representation of the graph

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        // Build the graph: prerequisites[i] = [a, b] means b -> a
        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        visited = new int[numCourses]; // All initialized to 0 (unvisited)

        // Iterate through each course. If it's unvisited, start DFS from it.
        // We need to do this for all courses because the graph might be disconnected.
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) { // If unvisited
                if (hasCycleDFS(i)) {
                    return false; // Cycle detected
                }
            }
        }

        return true; // No cycle found, all courses can be finished
    }

    private boolean hasCycleDFS(int course) {
        // If course is currently being visited (i.e., it's in the current DFS path), a cycle is detected.
        if (visited[course] == 1) {
            return true;
        }
        // If course has been fully visited (processed and no cycle found in its subtree), no need to re-process.
        if (visited[course] == 2) {
            return false;
        }

        // Mark the current course as 'visiting' (in the recursion stack)
        visited[course] = 1;

        // Recursively visit all neighbors
        for (int neighbor : adj.get(course)) {
            if (hasCycleDFS(neighbor)) {
                return true; // If a cycle is detected in any neighbor's path, propagate up.
            }
        }

        // Mark the current course as 'visited' (finished processing this course and its subtree)
        visited[course] = 2;
        return false; // No cycle detected from this course's path
    }
}
```


## Approach 2: Kahn's Algorithm (BFS based Topological Sort)

**Idea:** Topological sort sirf Directed Acyclic Graphs (DAGs) par possible hai. Agar hum successfully `numCourses` jitne nodes ko topological order mein sort kar sakte hain, toh koi cycle nahi hai. Agar nahi kar paate, toh cycle hai. Kahn's algorithm incoming edges (in-degree) par based hai.

**Algorithm Steps:**

1.  **Graph Representation & In-degrees:**

      * Ek **Adjacency List** (`List<List<Integer>> graph`) banao, same as DFS.
      * Ek `int[] inDegree` array banao, size `numCourses` ka. `inDegree[i]` store karega kitne prerequisites `i` course ke liye hain (incoming edges `i` par).

2.  **Initialization:**

      * Graph build karo aur `inDegree` array populate karo: `[a, b]` means `b -> a`, so `inDegree[a]++`.
      * Ek `Queue<Integer>` (`q`) banao.
      * Saare courses jinki `inDegree` `0` hai (yani koi prerequisites nahi hain) unhein `q` mein add karo.

3.  **BFS Loop:**

      * Ek `count = 0` variable rakho jo successfully complete kiye gaye courses ko track karega.
      * Jab tak `q` empty nahi hai:
          * `course = q.poll()`.
          * `count++`.
          * `course` ke saare `neighbor` (jo `graph[course]` mein hain) ke liye:
              * `inDegree[neighbor]--`.
              * Agar `inDegree[neighbor]` ab `0` ho gaya hai, toh `neighbor` ko `q` mein add karo.

4.  **Result:**

      * Agar `count == numCourses` hai, toh saare courses complete ho gaye (koi cycle nahi), `true` return karo.
      * Else (agar `count < numCourses` hai, matlab kuch courses remain kar gaye because of cycle), `false` return karo.

**Dry Run Example:** `numCourses = 2, prerequisites = [[1,0],[0,1]]`

Graph: `0 -> 1` and `1 -> 0`

1.  Graph building:

      * `adj = [[1], [0]]`
      * `inDegree = [1, 1]` (`inDegree[0]` because of `1->0`, `inDegree[1]` because of `0->1`)

2.  Initialization:

      * `q = []` (no courses with in-degree 0)

3.  BFS Loop:

      * `count = 0`
      * `q` is empty. Loop does not run.

4.  Result: `count (0) == numCourses (2)`? `false`. Correct.

**Dry Run Example 2:** `numCourses = 2, prerequisites = [[1,0]]`

Graph: `0 -> 1`

1.  Graph building:

      * `adj = [[1], []]`
      * `inDegree = [0, 1]` (`inDegree[1]` because of `0->1`)

2.  Initialization:

      * `q = [0]` (course 0 has in-degree 0)

3.  BFS Loop:

      * `count = 0`
      * **Iteration 1:**
          * `course = q.poll()` (0)
          * `count = 1`
          * Neighbors of 0: `[1]`
          * `inDegree[1]--` becomes `0`.
          * `inDegree[1]` is `0`, so `q.add(1)`. `q = [1]`
      * **Iteration 2:**
          * `course = q.poll()` (1)
          * `count = 2`
          * Neighbors of 1: `[]`
      * `q` is empty. Loop ends.

4.  Result: `count (2) == numCourses (2)`? `true`. Correct.

**Code Structure (Java-like Pseudocode - Kahn's):**

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        int[] inDegree = new int[numCourses];

        // Build graph and calculate in-degrees
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prerequisite = pre[1];
            adj.get(prerequisite).add(course); // Edge from prerequisite to course
            inDegree[course]++; // Increment in-degree of the course
        }

        Queue<Integer> q = new LinkedList<>();
        // Add all courses with 0 in-degree (no prerequisites) to the queue
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                q.add(i);
            }
        }

        int coursesCompleted = 0;
        while (!q.isEmpty()) {
            int currentCourse = q.poll();
            coursesCompleted++;

            // For all courses that depend on the currentCourse
            for (int neighbor : adj.get(currentCourse)) {
                inDegree[neighbor]--; // Decrement their in-degree
                // If a neighbor's in-degree becomes 0, it means all its prerequisites are met.
                // Add it to the queue to be processed.
                if (inDegree[neighbor] == 0) {
                    q.add(neighbor);
                }
            }
        }

        // If the number of completed courses equals the total number of courses,
        // it means no cycles were found and all courses can be finished.
        return coursesCompleted == numCourses;
    }
}
```

**Time Complexity:**

  * **Both DFS and Kahn's:** `O(V + E)`, Jahan `V` number of courses (vertices) hai aur `E` number of prerequisites (edges) hai. Graph traversal operations `O(V+E)` hote hain.

**Space Complexity:**

  * **Both DFS and Kahn's:** `O(V + E)`: Adjacency list storage ke liye, aur DFS recursion stack (worst case `O(V)`) ya BFS queue (`O(V)`) ke liye. `inDegree` aur `visited` arrays `O(V)` space lete hain.

-----

# 20\. Alien Dictionary (Hard)

**Problem Statement:**

Aapko ek sorted list of words (`words`) di gayi hai jo ek **alien dictionary** ke rules ke according lexicographically sorted hain. Aapko us alien language ke alphabets ka order find karna hai. Agar multiple valid orders hain, toh koi bhi ek return karo. Agar koi valid order nahi hai (cyclic dependency), toh empty string return karo.

**Example:**

```
Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
Explanation:
From "wrt" and "wrf", we know 't' comes before 'f'.
From "wrt" and "er", we know 'w' comes before 'e'.
From "er" and "ett", we know 'r' comes before 't'.
From "ett" and "rftt", we know 'e' comes before 'r'.
Combining them, we get "wertf".
```

**Key Concepts/Challenges:**

  * **Graph (Directed):** Characters nodes hain aur unke order relations directed edges hain.
  * **Topological Sort:** Character order find karna essentially ek topological sort problem hai.
  * **Cycle Detection:** Agar cycle hai, toh invalid order (empty string return).
  * **Edge Case Handling:** Same prefix, different length words; duplicate words; words with no relation.

**Intuition aur Approach (Hinglish Mein):**

Yeh problem bhi **graph aur topological sort** par based hai, similar to Course Schedule, but yahan graph ko humein pehle build karna padega words comparison se.

**Core Idea:**
Humein `words` list ke adjacent pairs ko compare karke characters ke order (dependencies) nikalne hain. Har pair `word1`, `word2` ke liye, pehla character jahan ye dissimilar hote hain, woh humein dependency dega (e.g., agar `word1[i]` aur `word2[i]` pehle different characters hain, toh `word1[i]` comes before `word2[i]`). Yeh dependencies ek Directed Graph banayengi. Phir hum us graph par **Topological Sort** apply karenge.

**Algorithm Steps (Using Kahn's Algorithm - BFS for Topological Sort):**

1.  **Build Graph and In-degrees:**

      * Saare unique characters ka track rakho jo words mein present hain (`Set<Character> allChars`).

      * Ek **Adjacency List** (`Map<Character, List<Character>> adj`) banao.

      * Ek `Map<Character, Integer> inDegree` banao.

      * Initialize `inDegree` for all `allChars` to `0`.

      * **Edges (Dependencies) Create Karna:**

          * `words` array ko iterate karo `i` from `0` to `words.length - 2`.
          * Compare `word1 = words[i]` aur `word2 = words[i+1]`.
          * Loop `j` from `0` to `min(word1.length(), word2.length()) - 1`:
              * `char1 = word1.charAt(j)`, `char2 = word2.charAt(j)`.
              * Agar `char1 != char2` hai:
                  * Edge `char1 -> char2` banegi. `adj.get(char1).add(char2)`.
                  * `inDegree.put(char2, inDegree.get(char2) + 1)`.
                  * `break` kar do (pehle dissimilar character hi order decide karta hai).
              * **Edge Case:** Agar `word1` `word2` ka prefix hai lekin `word1` `word2` se lamba hai (e.g., ["abc", "ab"]), toh yeh invalid order hai (`word1` should not be lexicographically before its prefix). Is case mein empty string return karo.

2.  **Initialize Queue for BFS:**

      * Ek `Queue<Character>` (`q`) banao.
      * `inDegree` map mein har character ke liye check karo. Agar `inDegree.get(char) == 0` hai, use `q` mein add karo.

3.  **BFS Loop (Topological Sort):**

      * Ek `StringBuilder` (`result`) banao topological order store karne ke liye.
      * Jab tak `q` empty nahi hai:
          * `char current = q.poll()`.
          * `result.append(current)`.
          * `current` ke saare `neighbor` (jo `adj.get(current)` mein hain) ke liye:
              * `inDegree.put(neighbor, inDegree.get(neighbor) - 1)`.
              * Agar `inDegree.get(neighbor)` ab `0` ho gaya hai, use `q` mein add karo.

4.  **Result:**

      * Agar `result.length()` `allChars.size()` ke barabar hai, toh `result.toString()` return karo.
      * Else (agar `result.length()` `allChars.size()` se kam hai, matlab cycle hai), empty string `""` return karo.

**Dry Run Example:** `words = ["wrt","wrf","er","ett","rftt"]`

1.  **Build Graph & In-degrees:**

      * `allChars = {'w', 'r', 't', 'f', 'e'}`

      * `inDegree = {'w':0, 'r':0, 't':0, 'f':0, 'e':0}` (initially all 0)

      * `adj = {}` (initially empty lists for all chars)

      * Comparisons:

          * `"wrt"` vs `"wrf"`: `t` vs `f` -\> `t -> f`. `adj['t'].add('f')`. `inDegree['f'] = 1`.
          * `"wrf"` vs `"er"`: `w` vs `e` -\> `w -> e`. `adj['w'].add('e')`. `inDegree['e'] = 1`.
          * `"er"` vs `"ett"`: `r` vs `t` -\> `r -> t`. `adj['r'].add('t')`. `inDegree['t'] = 1`.
          * `"ett"` vs `"rftt"`: `e` vs `r` -\> `e -> r`. `adj['e'].add('r')`. `inDegree['r'] = 1`.

      * Final `inDegree`:

          * `w: 0`
          * `r: 1` (from 'e')
          * `t: 1` (from 'r')
          * `f: 1` (from 't')
          * `e: 1` (from 'w')

      * Final `adj`:

          * `t: [f]`
          * `w: [e]`
          * `r: [t]`
          * `e: [r]`

2.  **Initialize Queue:**

      * `q = ['w']` (only 'w' has in-degree 0)

3.  **BFS Loop:**

      * `result = ""`
      * **Iteration 1:**
          * `current = 'w'`. `q = []`.
          * `result = "w"`.
          * Neighbors of 'w': `['e']`.
          * `inDegree['e']` becomes `0`. `q.add('e')`. `q = ['e']`.
      * **Iteration 2:**
          * `current = 'e'`. `q = []`.
          * `result = "we"`.
          * Neighbors of 'e': `['r']`.
          * `inDegree['r']` becomes `0`. `q.add('r')`. `q = ['r']`.
      * **Iteration 3:**
          * `current = 'r'`. `q = []`.
          * `result = "wer"`.
          * Neighbors of 'r': `['t']`.
          * `inDegree['t']` becomes `0`. `q.add('t')`. `q = ['t']`.
      * **Iteration 4:**
          * `current = 't'`. `q = []`.
          * `result = "wert"`.
          * Neighbors of 't': `['f']`.
          * `inDegree['f']` becomes `0`. `q.add('f')`. `q = ['f']`.
      * **Iteration 5:**
          * `current = 'f'`. `q = []`.
          * `result = "wertf"`.
          * Neighbors of 'f': `[]`.
      * `q` is empty. Loop ends.

4.  **Result:** `result.length() (5) == allChars.size() (5)`. True. Return `"wertf"`. Correct.

**Code Structure (Java-like Pseudocode):**

```java
import java.util.*;

class Solution {
    public String alienOrder(String[] words) {
        // Step 1: Build the graph (adjacency list) and calculate in-degrees
        Map<Character, List<Character>> adj = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        Set<Character> allChars = new HashSet<>();

        // Initialize all unique characters found in words with in-degree 0
        for (String word : words) {
            for (char c : word.toCharArray()) {
                allChars.add(c);
                inDegree.put(c, 0); // Initialize in-degree
                adj.put(c, new ArrayList<>()); // Initialize adjacency list
            }
        }

        // Compare adjacent words to build dependencies (edges)
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i+1];

            // Edge case: "abc" before "ab" is invalid
            if (w1.length() > w2.length() && w1.startsWith(w2)) {
                return "";
            }

            // Find the first differing character to establish order
            int minLen = Math.min(w1.length(), w2.length());
            for (int j = 0; j < minLen; j++) {
                char char1 = w1.charAt(j);
                char char2 = w2.charAt(j);
                if (char1 != char2) {
                    // Add edge char1 -> char2
                    adj.get(char1).add(char2);
                    inDegree.put(char2, inDegree.get(char2) + 1);
                    break; // Only the first differing character matters for order
                }
            }
        }

        // Step 2: Initialize BFS queue with characters having in-degree 0
        Queue<Character> q = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                q.add(c);
            }
        }

        // Step 3: Perform BFS (Kahn's algorithm for topological sort)
        StringBuilder result = new StringBuilder();
        while (!q.isEmpty()) {
            char current = q.poll();
            result.append(current);

            // For each neighbor of the current character
            for (char neighbor : adj.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    q.add(neighbor);
                }
            }
        }

        // Step 4: Check for cycles (if the result length is less than total unique chars)
        if (result.length() == allChars.size()) {
            return result.toString();
        } else {
            return ""; // Cycle detected or disconnected graph not fully processed
        }
    }
}
```

**Time Complexity:**

  * `O(C + N*L)`:
      * `C`: Total number of unique characters across all words (at most 26 for English alphabet).
      * `N`: Number of words in `words` array.
      * `L`: Average length of words.
      * Building the graph: Iterating through `N` words and comparing adjacent pairs, each comparison up to `L` characters. `O(N*L)`.
      * BFS (Topological sort): `O(V + E)`, where `V` is the number of unique characters (`C`) and `E` is the number of dependencies (edges). `E` can be at most `C * C`. So, `O(C + E)`.
      * Overall, `O(N*L + C + E)`. Since `E` is at most `C*C`, and `C` is max 26, it's dominated by `N*L`.

**Space Complexity:**

  * `O(C + E)`: For adjacency list, in-degree map, and queue. `C` is number of unique chars, `E` is number of dependencies.

-----

# 21\. Word Search

**Problem Statement:**

Aapko characters ka ek `m x n` `grid` (`board`) aur ek `string` `word` di gayi hai. Aapko return karna hai ki kya `word` `board` mein exist karta hai ya nahi.

Word adjacent (horizontal ya vertical) cells mein letters se ban sakta hai. Har letter cell ko ek word mein sirf ek baar use kiya ja sakta hai.

**Example:**

```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
Explanation: The word "ABCCED" can be found in the board.
```

**Key Concepts/Challenges:**

  * **Backtracking / DFS (Depth-First Search):** Board par word search karne ke liye.
  * **Visited State:** Cells ko mark karna taaki same letter ko ek word mein multiple times use na kiya ja sake.

**Intuition aur Approach (Hinglish Mein):**

Yeh problem ek classic **Backtracking** (ya DFS) problem hai. Hum `board` ke har cell ko potential starting point maan kar `word` ko search karne ki koshish karenge.

**Algorithm Steps:**

1.  **Main Function (`exist(board, word)`):**

      * `m = board.length`, `n = board[0].length`.
      * `wordLength = word.length()`.
      * Board ke har cell `(r, c)` ke liye:
          * Agar `board[r][c]` `word` ke pehle character (`word.charAt(0)`) ke barabar hai:
              * `dfs(board, r, c, word, 0)` function call karo (0 means `word` ka pehla character search kar rahe hain).
              * Agar ye `true` return karta hai, toh `true` return kar do (word mil gaya).
      * Agar saare cells explore ho gaye aur word nahi mila, toh `false` return karo.

2.  **DFS Function (`dfs(board, r, c, word, wordIndex)`):**

      * **Base Case 1 (Success):** Agar `wordIndex == word.length()` hai, toh iska matlab hai ki humne `word` ke saare characters ko board mein successfully match kar liya hai. `true` return karo.

      * **Base Case 2 (Failure - Boundary Check):** Agar `r` ya `c` board boundaries se bahar hain (`r < 0 || r >= m || c < 0 || c >= n`), return `false`.

      * **Base Case 3 (Failure - Mismatch):** Agar `board[r][c]` `word.charAt(wordIndex)` ke barabar nahi hai, return `false`.

      * **Base Case 4 (Failure - Already Visited):** Agar `board[r][c]` ko pehle hi `visited` mark kiya gaya hai (e.g., kisi special character, jaise '\#', se), return `false`.

      * **Recursive Step:**

          * **Mark Current Cell as Visited:** `board[r][c]` ko `visited` mark karo (e.g., `char temp = board[r][c]; board[r][c] = '#';`).
          * **Explore Neighbors:**
              * Recursively `dfs` call karo current cell ke 4 adjacent neighbors (up, down, left, right) par. `wordIndex` ko `wordIndex + 1` karke pass karo.
              * `boolean found = dfs(board, r+1, c, word, wordIndex+1) ||`
              * `dfs(board, r-1, c, word, wordIndex+1) ||`
              * `dfs(board, r, c+1, word, wordIndex+1) ||`
              * `dfs(board, r, c-1, word, wordIndex+1);`
          * **Backtrack:** `board[r][c]` ko uski original character mein restore karo (`board[r][c] = temp;`). Ye step bohot crucial hai taaki doosre DFS paths is cell ko use kar sakein.
          * `found` return karo.


```java
class Solution {
    private char[][] board;
    private String word;
    private int M, N; // Dimensions of the board

    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        M = board.length;
        N = board[0].length;

        // Iterate through each cell of the board
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                // If the first character of the word matches the current cell, start DFS
                if (board[r][c] == word.charAt(0)) {
                    if (dfs(r, c, 0)) { // 0 is the starting index in the word
                        return true; // Word found
                    }
                }
            }
        }
        return false; // Word not found after checking all possible starting points
    }

    private boolean dfs(int r, int c, int wordIndex) {
        // Base case: If we have matched all characters of the word
        if (wordIndex == word.length()) {
            return true;
        }

        // Base cases for invalid moves or mismatches:
        // 1. Out of board boundaries
        // 2. Current cell character does not match the required character in word
        // 3. Cell has already been visited (marked with '#')
        if (r < 0 || r >= M || c < 0 || c >= N || board[r][c] != word.charAt(wordIndex) || board[r][c] == '#') {
            return false;
        }

        // Mark the current cell as visited by changing its character
        // Store original char for backtracking
        char originalChar = board[r][c];
        board[r][c] = '#'; 

        // Explore all 4 adjacent directions (up, down, left, right)
        // If any of the recursive calls returns true, it means the word is found.
        boolean found = dfs(r + 1, c, wordIndex + 1) || // Down
                        dfs(r - 1, c, wordIndex + 1) || // Up
                        dfs(r, c + 1, wordIndex + 1) || // Right
                        dfs(r, c - 1, wordIndex + 1);   // Left

        // Backtrack: Restore the original character to the cell
        // This is crucial for other paths to be able to use this cell.
        board[r][c] = originalChar;

        return found; // Return whether the word was found starting from this path
    }
}
```

**Time Complexity:**

  * `O(M * N * 3^L)`:
      * `M * N`: Board ke har cell ko starting point maan kar check karte hain.
      * `L`: Length of the `word`.
      * `3^L`: Har step par, hum 4 directions explore karte hain, lekin backtracking ki wajah se jis direction se aaye hain wahan wapas nahi jaate. So, effectively 3 directions per step for `L` steps.
      * Worst case mein, agar board characters se bhara ho jo `word` ke first character se match karte hain, toh har possible path ko explore karna padega.

**Space Complexity:**

  * `O(L)`: Recursion stack ki depth, jo `word` ki length (`L`) tak jaa sakti hai. Board modification (marking visited with '\#') in-place hai, so no extra space for visited array.

-----
