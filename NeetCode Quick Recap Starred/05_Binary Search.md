# Problem: Koko Eating Bananas

Koko ko kele khana bahut pasand hai. Aapko `n` kele ke dher (`piles`) diye gaye hain, jahan `piles[i]` i-th dher mein kelon ki sankhya batata hai, aur `h` ghante baad guards wapas aa jayenge. Koko saare kele `h` ghanton ke andar khana chahti hai.

  * Koko ek khane ki speed `k` (kele प्रति ghanta) chunti hai.
  * Har ghante, Koko ek dher se `k` kele khati hai. Agar dher mein `k` se kam kele hain, toh woh saare khati hai aur us ghante mein aage nahi khati.
  * Koko sabse dheere khana chahti hai, lekin phir bhi guards ke wapas aane se pehle saare kele khatam karna chahti hai.

**Minimum integer `k` return karo jisse Koko `h` ghanton ke andar saare kele kha sake.**

-----

### Example:

**Input:**
`piles = [3, 6, 7, 11], h = 8`

**Output:**
`4`

-----

### Intuition (Samajh):

Koko ko saare kele `h` ghanton mein khane hain, aur woh chahti hai ki uski speed `k` sabse kam ho.
Agar Koko bahut tez khati hai (badi `k` value), toh use kam ghante lagenge. Agar woh dheere khati hai (choti `k` value), toh use zyada ghante lagenge.

Yeh ek classic problem hai jahan hum ek `k` value dhundh rahe hain jo ek certain condition (`totalHours <= h`) ko satisfy karti ho, aur humein sabse choti aisi `k` value chahiye. Jab humein ek sorted range mein se koi specific value dhundhni ho (ya least/greatest value jo condition satisfy kare), toh **Binary Search** ek accha option hota hai.

**Hum kis par Binary Search karenge?**
Hum speed `k` par Binary Search karenge. `k` ki possible range kya ho sakti hai?

  * Minimum `k`: `1` (Koko ko kam se kam 1 banana per hour toh khana hi hoga, warna kabhi khatam hi nahi hoga).
  * Maximum `k`: `max(piles)` (agar Koko sabse bade dher ke barabar speed se khati hai, toh woh har dher ko 1 ghante mein khatam kar degi, jo sabse tez speed hai).

-----

### Approach (Tareeka): Binary Search on Eating Speed

1.  **Possible Speeds ki Range Dhundo:**

      * Minimum speed (`left`): `1`.
      * Maximum speed (`right`): Saare piles mein se sabse bade pile mein jitne kele hain (`maxPile`). Is speed se Koko har pile ko 1 ghante mein kha legi.

2.  **Binary Search Lagao:**

      * `left` aur `right` ke beech `midSpeed` calculate karo.
      * Is `midSpeed` par Koko ko saare kele khane mein kitne total ghante lagenge, woh calculate karo.

3.  **`midSpeed` par Total Ghante Calculate Karna:**

      * Har pile `bananas` ke liye:
          * Required hours = `ceil(bananas / midSpeed)`.
          * Ceiling division `(A / B)` ko integer division mein `(A + B - 1) / B` aise kiya ja sakta hai.
      * Saare piles ke liye required hours ko `totalHours` mein sum kar lo.

4.  **Binary Search Range Adjust Karna:**

      * **Agar `totalHours <= h`:**
          * Iska matlab `midSpeed` par Koko time par saare kele kha sakti hai.
          * Toh `midSpeed` ek possible answer ho sakta hai. Hum isse bhi choti speed dhundhne ki koshish karenge.
          * `result = midSpeed` (current possible answer store karo).
          * `right = midSpeed - 1` (left half mein search karo).
      * **Agar `totalHours > h`:**
          * Iska matlab `midSpeed` bahut dheemi hai, Koko time par nahi kha paayegi.
          * Humein `midSpeed` badhani padegi.
          * `left = midSpeed + 1` (right half mein search karo).

5.  Loop khatam hone par, `result` mein minimum `k` value hogi.

-----

### Java Code with Comments:

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // Step 1: MaxPile dhundo. Yeh 'right' boundary hoga hamari binary search ka.
        // Kyunki Koko ki speed maxPile se zyada nahi ho sakti (agar woh maxPile ke barabar speed se khayegi,
        // toh har pile 1 ghante mein khatam ho jayega, jo minimum possible time hai).
        int maxPile = 0;
        for (int bananas : piles) {
            maxPile = Math.max(maxPile, bananas);
        }

        // Step 2: Binary search ke liye range set karo.
        // 'left' sabse kam possible speed (1 banana per hour).
        // 'right' sabse zyada possible speed (largest pile ke barabar).
        int left = 1;
        int right = maxPile;
        int result = maxPile;  // Result ko max possible speed se initialize karo, in case 1-banana/hr is too slow.

        while (left <= right) {
            // Mid speed calculate karo. Avoid overflow: left + (right - left) / 2
            int midSpeed = left + (right - left) / 2;

            // Step 3: Current midSpeed par saare kele khane mein kitne total ghante lagenge, calculate karo.
            long totalHours = 0; // totalHours long rakho, kyunki bahut saare piles ho sakte hain aur ghante badh sakte hain.
            for (int bananas : piles) {
                // Ceiling division: (bananas / midSpeed) ko upar round up karna.
                // Example: 7 bananas, speed 3 -> (7+3-1)/3 = 9/3 = 3 hours
                // 6 bananas, speed 3 -> (6+3-1)/3 = 8/3 = 2 hours (correct: 6/3=2)
                totalHours += (bananas + midSpeed - 1) / midSpeed;
            }

            // Step 4: Check karo ki Koko given 'h' ghanton ke andar kha sakti hai ya nahi.
            if (totalHours <= h) {
                // Agar kha sakti hai, toh midSpeed ek possible answer hai.
                // Hum aur bhi choti speed dhundhne ki koshish karenge left half mein.
                result = midSpeed;       // Is speed ko store karo as a potential answer.
                right = midSpeed - 1;    // Right boundary ko adjust karo, smaller speeds ke liye search karo.
            } else {
                // Agar nahi kha sakti (totalHours > h), toh midSpeed bahut dheemi hai.
                // Humein apni speed badhani padegi.
                left = midSpeed + 1;     // Left boundary ko adjust karo, larger speeds ke liye search karo.
            }
        }

        return result; // Minimum speed 'k' return karo
    }
}
```

-----

# Problem: Find Minimum in Rotated Sorted Array

Aapko unique elements ka ek sorted array `nums` diya gaya hai jo 1 se `n` baar rotate kiya gaya hai.
Aapka task hai is rotated array mein minimum element ko **O(log n)** time mein dhundhna.

-----

### Example:

**Input:**
`nums = [3,4,5,1,2]`

**Output:**
`1`

**Explanation:**
Original array `[1,2,3,4,5]` ko 3 baar rotate kiya gaya jisse `[3,4,5,1,2]` ban gaya. Minimum element `1` hai.

-----

### Intuition (Samajh):

Normal sorted array mein minimum element hamesha pehla element hota hai. Lekin yahan array rotated hai. Rotation ka matlab hai ki array ko do sorted parts mein divide kiya gaya hai. Minimum element hamesha us point par hoga jahan rotation hua hai (ya fir array puri tarah sorted hai toh pehla element).

Example: `[3,4,5,1,2]`
Left sorted part: `[3,4,5]`
Right sorted part: `[1,2]`
Minimum element `1` rotation point par hai.

Is problem ko `O(log n)` time mein solve karne ka matlab hai **Binary Search** use karna.
Key idea yeh hai ki hum `mid` element ko compare karenge `left` element se (ya `right` element se) yeh pata lagane ke liye ki array ka kaunsa hissa sorted hai aur minimum element kis hisse mein ho sakta hai.

-----

### Approach (Tareeka): Modified Binary Search

1.  **Pointers Initialize Karo:** `left = 0`, `right = nums.length - 1`. `minValue = Integer.MAX_VALUE` (ek high value se initialize karo result ko store karne ke liye).

2.  **Binary Search Loop:** Jab tak `left <= right` hai, loop chalao.

      * `mid` calculate karo: `mid = left + (right - left) / 2`.
      * **Case 1: Left half sorted hai (`nums[left] <= nums[mid]`)**
          * Iska matlab `left` se `mid` tak ka segment sorted order mein hai.
          * Toh is sorted segment ka minimum value `nums[left]` ho sakta hai (yaani rotation right half mein hai).
          * `minValue = Math.min(minValue, nums[left])`. Humne `nums[left]` ko consider kiya hai as a candidate for minimum.
          * Ab hum minimum value right half mein dhundenge, kyunki left half sorted hai aur usme `nums[left]` hi sabse chota ho sakta tha.
          * `left = mid + 1`. (Search space ko right half mein shift karo).
      * **Case 2: Right half sorted hai (or rotation point left half mein hai, `nums[mid] < nums[left]`)**
          * Iska matlab `mid` element `left` element se chota hai, jo indicate karta hai ki rotation point `mid` ya `mid` ke left mein hai.
          * `nums[mid]` is segment ka sabse chota element ho sakta hai.
          * `minValue = Math.min(minValue, nums[mid])`. Humne `nums[mid]` ko consider kiya hai as a candidate for minimum.
          * Ab hum minimum value left half mein dhundenge, kyunki rotation point left half mein hai.
          * `right = mid - 1`. (Search space ko left half mein shift karo).

3.  Loop khatam hone par `minValue` mein array ka minimum element hoga.

-----

### Java Code with Comments:

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        // minValue ko ek bahut bade number se initialize karo
        // Isse sure hoga ki array ka pehla valid minimum isse chota hoga.
        int minValue = Integer.MAX_VALUE;

        // Binary search loop
        while (left <= right) {
            int mid = left + (right - left) / 2; // Mid index calculate karo

            // Sabse pehle, nums[mid] ko potential minimum value ke roop mein consider karo.
            // Isko loop ke andar hi har iteration mein update karte raho.
            minValue = Math.min(minValue, nums[mid]);

            // Determine which half is sorted to narrow down the search
            // Case 1: Left half sorted hai (nums[left] <= nums[mid])
            // Example: [3,4,5,1,2] -> left=0, mid=2. nums[0]=3, nums[2]=5. 3 <= 5. Left half sorted [3,4,5].
            // Example: [1,2,3,4,5] -> left=0, mid=2. nums[0]=1, nums[2]=3. 1 <= 3. Left half sorted [1,2,3].
            if (nums[left] <= nums[mid]) {
                // Left half sorted hai. Toh is sorted part ka sabse chota element nums[left] hoga.
                // Isko bhi minValue se compare karo.
                minValue = Math.min(minValue, nums[left]);
                // Rotation point right half mein ho sakta hai. Toh search space ko right half mein shift karo.
                left = mid + 1;
            } else {
                // Case 2: Right half sorted hai (nums[mid] < nums[left])
                // Iska matlab rotation point left half mein hai, ya mid hi minimum hai.
                // Example: [3,4,5,1,2] -> left=0, mid=2, nums[mid]=5. Next iteration: left=3, mid=3, nums[3]=1.
                // nums[3]=1, nums[mid]=1. Now left=3, right=4, mid=3. nums[3]=1, nums[4]=2.
                // Here, mid=3, nums[mid]=1. nums[left]=3 (from previous iter). Now left=3, right=4, mid=3.
                // Current left=3, mid=3. nums[mid] (1) < nums[left] (3, this is wrong, should be nums[left] from current iteration)
                // Correct: If nums[mid] < nums[left], it means the rotation happened *before or at* mid.
                // So, the minimum element must be in the left half (including mid).
                // Example trace: [3,4,5,1,2]
                // L=0, R=4, mid=2 (5). nums[2]=5, nums[0]=3. 5 >= 3. Left half sorted. minValue=min(INF, 3)=3. L=3.
                // L=3, R=4, mid=3 (1). nums[3]=1, nums[3]=1. 1 >= 1. Left half sorted. minValue=min(3, 1)=1. L=4.
                // L=4, R=4, mid=4 (2). nums[4]=2, nums[4]=2. 2 >= 2. Left half sorted. minValue=min(1, 2)=1. L=5.
                // L=5, R=4. Loop ends. Return minValue (1).

                // The condition `nums[mid] < nums[left]` correctly identifies that the minimum is in the left segment (including mid)
                // because mid is smaller than the start of what should be a sorted segment.
                // So, we update minValue with nums[mid] (already done at start of loop)
                // And search in the left half (mid to left).
                right = mid - 1; // Search space ko left half mein shift karo.
            }
        }

        return minValue; // Minimum value return karo
    }
}
```

-----

# Problem: Search in Rotated Sorted Array

Aapko ek rotated sorted array `nums` (distinct integers ke saath) aur ek `target` value di gayi hai.
`target` ka index return karo agar woh array mein मौजूद hai; otherwise, `-1` return karo.

Aapka solution **O(log n)** time mein chalna chahiye.

-----

### Example:

**Input:**
`nums = [4,5,6,7,0,1,2]`, `target = 0`

**Output:**
`4`

**Explanation:**
`0` rotated array mein index 4 par mila.

-----

### Intuition (Samajh):

Yeh problem bhi modified binary search ka use karti hai. Array sorted hai, lekin ek point par rotate kiya gaya hai. Iska matlab hai ki array ko do sorted parts mein divide kiya gaya hai.
Example: `[4,5,6,7,0,1,2]`
Left sorted part: `[4,5,6,7]`
Right sorted part: `[0,1,2]`

Har step par, humein yeh pehchanana hoga ki `mid` element ke respect mein kaunsa half (`left` ya `right`) sorted hai. Ek baar jab humein sorted half pata chal jaye, toh hum check kar sakte hain ki `target` us sorted half mein hai ya nahi.

  * Agar `target` sorted half mein hai, toh hum search space ko us sorted half tak restrict kar denge.
  * Agar `target` sorted half mein nahi hai, toh woh doosre (unsorted/rotated) half mein hona chahiye, toh hum search space ko doosre half tak restrict kar denge.

-----

### Approach (Tareeka): Modified Binary Search

1.  **Pointers Initialize Karo:** `left = 0`, `right = nums.length - 1`.

2.  **Binary Search Loop:** Jab tak `left <= right` hai, loop chalao.

      * `mid` calculate karo: `mid = left + (right - left) / 2`.

      * **Check `mid` for `target`:** Agar `nums[mid] == target`, toh `mid` index return kar do.

      * **Identify Sorted Half:**

          * **If `nums[left] <= nums[mid]`:** Left half sorted hai (e.g., `[4,5,6,7]` from `[4,5,6,7,0,1,2]`).
              * **Check if `target` left sorted half mein hai:** `nums[left] <= target` AND `target < nums[mid]`.
                  * Agar haan, toh `target` left half mein hai, `right = mid - 1` set karo.
                  * Agar nahi (target left sorted half mein nahi hai), toh `target` right half mein hoga (jo unsorted hai ya rotation ke baad ka part hai), `left = mid + 1` set karo.
          * **Else (`nums[left] > nums[mid]`):** Right half sorted hai (e.g., `[0,1,2]` from `[4,5,6,7,0,1,2]`).
              * **Check if `target` right sorted half mein hai:** `nums[mid] < target` AND `target <= nums[right]`.
                  * Agar haan, toh `target` right half mein hai, `left = mid + 1` set karo.
                  * Agar nahi (target right sorted half mein nahi hai), toh `target` left half mein hoga (jo unsorted hai ya rotation ke pehle ka part hai), `right = mid - 1` set karo.

3.  Loop khatam hone par, agar `target` nahi mila, toh `-1` return kar do.

-----

### Java Code with Comments:

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // Left aur right pointers initialize karo
        
        // Binary search loop: Jab tak search space valid hai
        while (left <= right) {
            int mid = left + (right - left) / 2; // Mid index calculate karo

            // Agar target mid element hi hai, toh index return karo
            if (nums[mid] == target) return mid;

            // Pata lagao kaunsa half sorted hai
            // Case 1: Left half sorted hai (yaani, nums[left] se nums[mid] tak badhta hua order hai)
            // Example: nums = [4,5,6,7,0,1,2], left=0, mid=3. nums[0]=4, nums[3]=7. 4 <= 7. So [4,5,6,7] is sorted.
            if (nums[left] <= nums[mid]) {
                // Check karo ki target is sorted left half mein hai ya nahi
                if (nums[left] <= target && target < nums[mid]) {
                    // Target left half mein hai, toh right boundary ko adjust karo
                    right = mid - 1; // Search left half
                } else {
                    // Target left half mein nahi hai, toh woh right (unsorted) half mein hoga
                    left = mid + 1;  // Search right half
                }
            } 
            // Case 2: Right half sorted hai (yaani, nums[mid] se nums[right] tak badhta hua order hai)
            // Example: nums = [6,7,0,1,2,3,4], left=0, mid=3. nums[0]=6, nums[3]=1. 6 > 1. So [1,2,3,4] is sorted.
            else {
                // Check karo ki target is sorted right half mein hai ya nahi
                if (nums[mid] < target && target <= nums[right]) {
                    // Target right half mein hai, toh left boundary ko adjust karo
                    left = mid + 1;  // Search right half
                } else {
                    // Target right half mein nahi hai, toh woh left (unsorted) half mein hoga
                    right = mid - 1; // Search left half
                }
            }
        }

        // Agar loop khatam ho gaya aur target nahi mila
        return -1;
    }
}
```

-----

# Problem: Time Based Key-Value Store

Ek data structure design karo jo same key ke liye multiple values ko alag-alag timestamps ke saath store kare,
aur ek diye gaye timestamp ke barabar ya usse kam sabse bade timestamp ke corresponding value ko retrieve kare.

-----

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

-----

### Intuition (Samajh):

Humein key-value pairs store karne hain, lekin har value ke saath ek timestamp bhi hoga. Jab `get` call hoga, toh humein ek specific timestamp diya jayega aur humein us key ki woh value chahiye jiska timestamp diye gaye timestamp ke *barabar ya usse kam* ho, aur unme se *sabse bada* ho.

`O(1)` time mein `set` aur `get` karna mushkil hoga kyunki `get` mein timestamp search karna hai. Lekin `O(log n)` mein `get` achieve kiya ja sakta hai agar values ko timestamp ke order mein rakha jaye.

**Data Structure Choice:**

1.  **HashMap**: Keys ko track karne ke liye, `key -> list_of_entries` mapping.
2.  **List of Entries**: Har `key` ke liye, hum `(value, timestamp)` pairs ki ek `List` maintain karenge. Important: yeh `List` **timestamp ke ascending order mein sorted** honi chahiye. Jab hum `set` karte hain, toh naya entry hamesha end mein add hoga (assuming timestamps are increasing).
3.  **Binary Search**: `get` operation mein, jab humein ek specific timestamp tak ki value dhundhni ho, toh is sorted `List` par **Binary Search** lagayenge. Yeh efficient `O(log N)` retrieval dega.

-----

### Approach (Tareeka):

1.  **Inner `Entry` Class:** Ek simple class banao `value` (`String`) aur `timestamp` (`int`) ko store karne ke liye.

2.  **`TimeMap` Class:**

      * `private Map<String, List<Entry>> map;`: Ek `HashMap` banao jahan `String` key hogi aur value `Entry` objects ki `List` hogi.

3.  **Constructor `TimeMap()`:** `map` ko initialize karo `new HashMap<>()` se.

4.  **`set(String key, String value, int timestamp)` Method:**

      * `map.putIfAbsent(key, new ArrayList<>());`: Agar `key` pehle se `map` mein nahi hai, toh uske liye ek empty `ArrayList` create karo.
      * `map.get(key).add(new Entry(value, timestamp));`: `key` ki `List` mein naya `Entry` (`value`, `timestamp`) add karo. Since new timestamps are always greater than previous ones for the same key, the list will remain sorted by timestamp.

5.  **`get(String key, int timestamp)` Method:**

      * `if (!map.containsKey(key)) return "";`: Agar `key` `map` mein hai hi nahi, toh empty string return karo.
      * `List<Entry> entries = map.get(key);`: `key` se associated `List` of `Entry` objects ko retrieve karo.
      * **Binary Search on `entries` list:**
          * `left = 0`, `right = entries.size() - 1`.
          * `result = ""`: Ek variable jismein hum apna best possible value store karenge.
          * **Loop `while (left <= right)`:**
              * `mid = left + (right - left) / 2;`
              * `Entry current = entries.get(mid);`
              * **Condition Check:**
                  * `if (current.timestamp == timestamp)`: Exact match mil gaya\! Iski `value` return kar do.
                  * `else if (current.timestamp < timestamp)`: `current` entry ka timestamp diye gaye timestamp se chota hai. Yeh ek possible answer ho sakta hai. Hum aur bade timestamp ke liye (jo given timestamp se chote ya barabar hon) right half mein search karenge.
                      * `result = current.value;` (Yeh potential answer hai)
                      * `left = mid + 1;` (Right half mein search karo)
                  * `else (current.timestamp > timestamp)`: `current` entry ka timestamp diye gaye timestamp se bada hai. Toh yeh valid answer nahi hai. Humein chote timestamp wali entries ke liye left half mein search karna hoga.
                      * `right = mid - 1;` (Left half mein search karo)
          * Loop khatam hone par, `result` mein sabse bada timestamp wali value hogi jo given timestamp se choti ya barabar thi.

-----

### Java Code with Comments:

```java
import java.util.*;

class TimeMap {

    // Inner class jo value aur uske timestamp ko store karegi
    class Entry {
        String value;
        int timestamp;

        Entry(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    // HashMap jo har key ko Entry objects ki sorted List se map karega
    private Map<String, List<Entry>> map;

    // Constructor: Data structure ko initialize karo
    public TimeMap() {
        map = new HashMap<>();
    }

    // `set` method: Key, value, aur timestamp store karo
    public void set(String key, String value, int timestamp) {
        // Agar key map mein nahi hai, toh uske liye ek nayi ArrayList banao
        map.putIfAbsent(key, new ArrayList<>());
        // Key se associated List mein naya Entry add karo.
        // Kyunki timestamps strictly increasing order mein aate hain, list sorted hi rahegi.
        map.get(key).add(new Entry(value, timestamp));
    }

    // `get` method: Largest timestamp <= given timestamp ke corresponding value retrieve karo
    public String get(String key, int timestamp) {
        // Agar key map mein nahi hai, toh koi value nahi hai
        if (!map.containsKey(key)) {
            return ""; // Empty string return karo
        }

        // Key se associated Entry objects ki list retrieve karo
        List<Entry> entries = map.get(key);

        int left = 0, right = entries.size() - 1; // Binary search ke liye pointers
        String result = ""; // Default result empty string

        // Binary search loop
        // Hum us entry ko dhundh rahe hain jiska timestamp <= given timestamp ho, aur sabse bada ho.
        while (left <= right) {
            int mid = left + (right - left) / 2; // Mid index
            Entry current = entries.get(mid);    // Mid par current entry

            if (current.timestamp == timestamp) {
                // Exact match mil gaya, yahi best result hai
                return current.value;
            } else if (current.timestamp < timestamp) {
                // Current timestamp diye gaye timestamp se chota hai.
                // Yeh ek possible answer hai. Hum right half mein aur bade (lekin <= timestamp)
                // timestamps ke liye search karenge.
                result = current.value; // Isko potential answer store karo
                left = mid + 1;         // Right half mein search karo
            } else { // current.timestamp > timestamp
                // Current timestamp diye gaye timestamp se bada hai.
                // Toh yeh valid answer nahi hai. Humein left half mein search karna hoga.
                right = mid - 1;        // Left half mein search karo
            }
        }

        return result; // Closest valid value (ya empty string agar koi valid value nahi mili)
    }
}
```

-----

# Problem: Median of Two Sorted Arrays

Aapko do sorted arrays `nums1` aur `nums2` diye gaye hain, jinki sizes `m` aur `n` hain.
Combined sorted array ka median **O(log(m+n))** time complexity mein dhundho.

-----

### Example:

```
Input: nums1 = [1, 3], nums2 = [2]
Output: 2.0

Explanation: The merged sorted array is [1, 2, 3].
The median is the middle element 2.
```

-----

### Intuition (Samajh):

Agar hum dono arrays ko merge karke sort karein, toh median dhundhna aasan hai:

  * Agar total length odd hai, toh median middle element hoga.
  * Agar total length even hai, toh median do middle elements ka average hoga.

Lekin merging aur sorting mein `O(m+n)` time lagega, jo efficient nahi hai kyunki humein `O(log(m+n))` mein karna hai.

**Optimal Approach: Binary Search on Smaller Array**

Hum is problem ko `O(log(min(m, n)))` time mein solve kar sakte hain, binary search ka use karke.

-----

### How? (Kaise?)

Imagine karo ki hum dono arrays ko do halves mein split kar rahe hain:

  * **Left halves combined**: Saare elements jo median ke left mein honge.
  * **Right halves combined**: Saare elements jo median ke right mein honge.

Humein `nums1` aur `nums2` mein ek aisa partition dhundhna hai jisse:

1.  **Elements ki Sankhya Sahi Ho:** Left side mein elements ki total sankhya, right side mein elements ki total sankhya ke barabar ho (agar total length even hai) ya left side mein 1 extra element ho (agar total length odd hai). Total elements `(m+n)` honge, toh left side mein `(m+n+1)/2` elements hone chahiye.
2.  **Order Sahi Ho:** Left side ka har element, right side ke har element se chota ya barabar ho.

Agar hum yeh conditions satisfy karne wala partition dhundh lete hain, toh median left sides ke maximum element aur right sides ke minimum element ke beech hoga.

-----

### Detailed Steps:

1.  **Smaller Array Par Binary Search:** Hamesha `nums1` ko chota array (`n1 <= n2`) rakho. Agar `nums1` bada hai, toh arguments swap karke function ko recursively call kar do. Isse binary search `min(m, n)` par chalega jo efficient hai.

2.  **Total Length aur Half Calculation:**

      * `totalLength = n1 + n2`
      * `half = (totalLength + 1) / 2` (Yeh left side mein kitne elements hone chahiye, woh batayega)

3.  **Binary Search Loop (`low = 0`, `high = n1`):**

      * `mid1 = low + (high - low) / 2;` (यह `nums1` में partition point है)
      * `mid2 = half - mid1;` (यह `nums2` में partition point है)

4.  **Partition ke Border Elements Define Karo:**

      * `l1`: `nums1` ke left partition ka last element. (`mid1 > 0` toh `nums1[mid1 - 1]`, warna `-∞`). `-∞` (Integer.MIN\_VALUE) use karte hain taki boundary cases mein yeh kisi bhi number se chota mana jaye.
      * `r1`: `nums1` ke right partition ka pehla element. (`mid1 < n1` toh `nums1[mid1]`, warna `+∞`). `+∞` (Integer.MAX\_VALUE) use karte hain taki boundary cases mein yeh kisi bhi number se bada mana jaye.
      * `l2`: `nums2` ke left partition ka last element. (`mid2 > 0` toh `nums2[mid2 - 1]`, warna `-∞`).
      * `r2`: `nums2` ke right partition ka pehla element. (`mid2 < n2` toh `nums2[mid2]`, warna `+∞`).

5.  **Conditions Check Karo:**

      * **Agar `l1 <= r2` AND `l2 <= r1`:**

          * Yeh correct partition hai\! Left halves ke saare elements right halves ke saare elements se chote ya barabar hain.
          * **Agar `totalLength` odd hai:** Median hoga `max(l1, l2)`. (Kyunki left side mein ek extra element hai).
          * **Agar `totalLength` even hai:** Median hoga `(max(l1, l2) + min(r1, r2)) / 2.0`. (Do middle elements ka average).
          * Is case mein, hum `return` kar denge.

      * **Else if `l1 > r2`:**

          * Iska matlab `nums1` ke left partition mein bahut bade elements hain, ya `nums2` ke right partition mein bahut chote elements hain.
          * Humein `nums1` mein partition point ko left side shift karna hoga (`high = mid1 - 1`).

      * **Else (`l2 > r1`):**

          * Iska matlab `nums2` ke left partition mein bahut bade elements hain, ya `nums1` ke right partition mein bahut chote elements hain.
          * Humein `nums1` mein partition point ko right side shift karna hoga (`low = mid1 + 1`).

6.  Loop tab tak repeat hoga jab tak correct partition nahi mil jaata.

-----

### Code with detailed comments:

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        // Step 1: Hamesha chote array par binary search perform karo.
        // Isse Time Complexity O(log(min(n1, n2))) ho jayegi.
        if (n1 > n2) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int totalLength = n1 + n2;
        // half: Combined sorted array ke left half mein kitne elements hone chahiye.
        // Agar totalLength odd hai, toh left half mein ek extra element hoga.
        // Example: [1,2,3,4,5], totalLength=5. half=(5+1)/2=3. Left half: [1,2,3]. Median is 3rd element.
        // Example: [1,2,3,4], totalLength=4. half=(4+1)/2=2. Left half: [1,2]. Median is (2+3)/2.
        int half = (totalLength + 1) / 2;

        int low = 0, high = n1; // nums1 par binary search ki range (0 se n1 tak inclusive, for partition points)

        while (low <= high) {
            // mid1: nums1 mein partition point ka index.
            // Yani, nums1 ke mid1 elements left half mein jayenge.
            int mid1 = low + (high - low) / 2;

            // mid2: nums2 mein partition point ka index.
            // Total 'half' elements left side mein hone chahiye, toh mid2 = half - mid1.
            int mid2 = half - mid1;

            // l1, r1, l2, r2 define karo. Yeh boundary elements hain partitions ke.
            // Integer.MIN_VALUE aur Integer.MAX_VALUE ka use edge cases ko handle karta hai
            // jab koi partition array ki boundary par ho (yaani, koi element left/right mein na ho).
            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1]; // nums1 ke left partition ka last element
            int r1 = (mid1 == n1) ? Integer.MAX_VALUE : nums1[mid1];   // nums1 ke right partition ka pehla element

            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1]; // nums2 ke left partition ka last element
            int r2 = (mid2 == n2) ? Integer.MAX_VALUE : nums2[mid2];   // nums2 ke right partition ka pehla element

            // Check karo ki humne correct partition dhundh liya hai.
            // Conditions:
            // 1. Left part ke saare elements right part ke saare elements se chhote ya barabar hon.
            // 2. l1 <= r2: nums1 ka left part ka last element nums2 ke right part ke pehle element se chota/barabar ho.
            // 3. l2 <= r1: nums2 ka left part ka last element nums1 ke right part ke pehle element se chota/barabar ho.
            if (l1 <= r2 && l2 <= r1) {
                // Correct partition mil gaya! Median calculate karo.
                if (totalLength % 2 == 1) {
                    // Agar totalLength odd hai, toh median left side ka sabse bada element hoga.
                    return Math.max(l1, l2);
                } else {
                    // Agar totalLength even hai, toh median do middle elements ka average hoga.
                    // Middle elements hain (max of left parts) aur (min of right parts).
                    return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
                }
            } else if (l1 > r2) {
                // l1 r2 se bada hai. Iska matlab nums1 ka left partition bahut bada hai.
                // Humein nums1 mein partition ko left ki taraf shift karna hoga.
                high = mid1 - 1;
            } else { // l2 > r1
                // l2 r1 se bada hai. Iska matlab nums2 ka left partition bahut bada hai.
                // Humein nums1 mein partition ko right ki taraf shift karna hoga.
                low = mid1 + 1;
            }
        }

        // Yeh line ideally kabhi hit nahi honi chahiye agar input valid hai (sorted arrays).
        // Ek fallback ke roop mein, ya error handling ke liye.
        return 0.0;
    }
}
```

-----

### Why this works? (Yeh kaam kyun karta hai?)

  * Hum ek partition dhundhte hain jahan combined arrays ke left side ke elements right side ke elements se chote ya barabar hon.
  * Kyuki arrays sorted hain, binary search jaldi se sahi partition dhundh leta hai.
  * `Integer.MIN_VALUE` aur `Integer.MAX_VALUE` ka use un edge cases ko handle karta hai jahan partition array boundaries par aata hai.

### Summary:

  * **Time Complexity:** `O(log(min(m, n)))`. Binary search chote array par hota hai.
  * **Space Complexity:** `O(1)`. Koi extra data structure use nahi ki gayi hai (recursion stack for swapping arguments is minimal).
  * Yeh approach poori list ko merge kiye bina efficiently median dhundhti hai, partition technique ka use karke.
