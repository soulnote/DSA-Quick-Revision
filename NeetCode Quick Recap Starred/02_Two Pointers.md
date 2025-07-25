## **Problem: Trapping Rain Water**

Aapko `n` non-negative integers diye gaye hain. Yeh ek **elevation map** ko represent karte hain, jahaan har bar ki `width` 1 hai. Aapka kaam hai yeh compute karna ki baarish ke baad **kitna paani trapped** ho sakta hai.

### **Example:**

**Input:**
`height = [0,1,0,2,1,0,1,3,2,1,2,1]`

**Output:**
`6`

**Explanation:**
Elevation map array se bars ki alag-alag height dikhati hai. Paani in bars ke beech mein jama ho jata hai. Is case mein, 6 units paani baarish ke baad jama hota hai.

-----

### **Intuition (Samajh):**

Imagine kijiye ki aapke paas bars hain aur baarish hui. Paani kahan jama hoga? Paani wahi jama hoga jahaan par uske dono sides par uski height se oonche bars honge, jo us paani ko bahar nikalne se rokenge.

Kisi bhi position `i` par kitna paani jam sakta hai, yeh is baat par depend karta hai ki us position ke **left side mein sabse ooncha bar** kitna hai (`leftMax`) aur **right side mein sabse ooncha bar** kitna hai (`rightMax`).

Agar `current bar ki height` `min(leftMax, rightMax)` se kam hai, toh paani jam jayega. Kitna? `min(leftMax, rightMax) - current bar ki height`.

-----

### **Approach (Tareeka):**

Hum do arrays banayenge: `left` aur `right`.

1.  **`left` array calculate karein:**

      * `left[i]` mein store karenge `height[i]` tak **left side se maximum height** (खुद `height[i]` ko include karte hue).
      * Iske liye, hum array ko left se right traverse karenge. Ek `leftMax` variable rakhenge jo abhi tak ka maximum height track karega.

2.  **`right` array calculate karein:**

      * `right[i]` mein store karenge `height[i]` tak **right side se maximum height** (खुद `height[i]` ko include karte hue).
      * Iske liye, hum array ko right se left traverse karenge. Ek `rightMax` variable rakhenge jo abhi tak ka maximum height track karega.

3.  **Trapped Water Calculate Karein:**

      * Ek `totalWater` variable ko `0` se initialize karein.
      * Array mein har position `i` par loop chalao.
      * Har position `i` par kitna paani jam sakta hai uske liye formula hai: `min(left[i], right[i]) - height[i]`.
      * Yeh value `water` variable mein store karo. Agar `water` negative aata hai (matlab current bar ki height side ke max heights se zyada hai), toh usko 0 maanenge (kyunki paani jamega hi nahi).
      * Is `water` ko `totalWater` mein add karte jao.

4.  **Return:** `totalWater` ko return kar do.

-----

### **Java Solution with Comments:**

```java
class Solution {
    public int trap(int[] height) {
        int totalWater = 0;             // Total jama hua paani
        int n = height.length;          // Elevation array ki length

        // Har position ke left aur right side mein maximum height store karne ke liye arrays
        int[] left = new int[n];
        int[] right = new int[n];

        int leftMax = 0;                // Left side se abhi tak ki max height track karne ke liye
        int rightMax = 0;               // Right side se abhi tak ki max height track karne ke liye

        // Har bar ke left side ki max height calculate karo
        // Left se right traverse kar rahe hain
        for (int i = 0; i < n; i++) {
            // left[i] current height aur pichle leftMax mein se jo max hai woh hoga
            left[i] = Math.max(leftMax, height[i]);
            // leftMax ko update kar do current left[i] se
            leftMax = left[i];
        }

        // Har bar ke right side ki max height calculate karo
        // Right se left traverse kar rahe hain
        for (int i = n - 1; i >= 0; i--) {
            // right[i] current height aur pichle rightMax mein se jo max hai woh hoga
            right[i] = Math.max(rightMax, height[i]);
            // rightMax ko update kar do current right[i] se
            rightMax = right[i];
        }

        // Har position par jama hue paani ko calculate karo
        for (int i = 0; i < n; i++) {
            // Current bar ke upar kitna paani jama hoga woh dono sides ke max heights ke minimum
            // aur current bar ki height ka difference hoga
            int water = Math.min(left[i], right[i]) - height[i];
            // Total paani mein is position ka paani add kar do
            totalWater += water;
        }

        return totalWater;  // Total jama hua paani return karo
    }
}
```

-----

## **Problem: Trapping Rain Water (Two-Pointer Optimized Approach)**

### **Intuition (Samajh):**

Pehle wale approach mein humne `left` aur `right` arrays banaye the, jisse extra space use ho raha tha. Kya hum isko bina extra space ke kar sakte hain?

Haan\! Hum ek two-pointer approach use kar sakte hain. Idea yeh hai ki paani kitna jama hoga, yeh **hamesha shorter boundary** par depend karta hai. Agar humein pata hai ki `leftMax` `rightMax` se chota hai, toh `left` pointer par jo paani jamega, woh `leftMax` se hi determine hoga (kyunki `leftMax` ek real boundary hai aur `rightMax` ya toh utna hi ya usse bada hai). Iska matlab hai ki hum `left` side se confident hain ki `leftMax` ka use kar sakte hain. Same logic `right` side ke liye bhi apply hoti hai.

Hum us side se move karenge jahan ki current height kam hai, kyunki wahi side paani ke level ko limit kar rahi hai.

-----

### **Approach (Tareeka):**

1.  **Pointers aur Max Variables Initialize Karein:**

      * `left = 0`, `right = height.length - 1` (do pointers, ek shuru mein, ek end mein).
      * `leftMax = 0`, `rightMax = 0` (left aur right se dekhi gayi abhi tak ki max heights).
      * `totalWater = 0` (total jama hua paani).

2.  **Pointers Jab Tak Milte Nahi Hain:**

      * Ek `while (left < right)` loop chalao.
      * **Condition Check:**
          * Agar `height[left]` `height[right]` se chota hai:
              * Iska matlab hai ki `left` side paani ke level ko limit kar rahi hai.
              * `height[left]` ko `leftMax` se compare karo:
                  * Agar `height[left]` `leftMax` se bada ya barabar hai, toh `leftMax` ko `height[left]` se update karo (naya, ooncha boundary mila).
                  * Warna (`height[left]` `leftMax` se chota hai), toh is position par paani jamega: `totalWater += leftMax - height[left]`.
              * `left` pointer ko `left++` karke aage badhao.
          * Else (`height[right]` `height[left]` se chota ya barabar hai):
              * Iska matlab hai ki `right` side paani ke level ko limit kar rahi hai.
              * `height[right]` ko `rightMax` se compare karo:
                  * Agar `height[right]` `rightMax` se bada ya barabar hai, toh `rightMax` ko `height[right]` se update karo.
                  * Warna (`height[right]` `rightMax` se chota hai), toh is position par paani jamega: `totalWater += rightMax - height[right]`.
              * `right` pointer ko `right--` karke peeche badhao.

3.  **Return:** Loop khatam hone ke baad `totalWater` return kar do.

-----

### **Java Solution with Comments:**

```java
class Solution {
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;    // Do pointers: ek left end par, ek right end par
        int leftMax = 0, rightMax = 0;              // Left aur Right se dekhi gayi abhi tak ki max heights
        int totalWater = 0;                         // Total jama hua paani

        // Loop tab tak chalega jab tak left pointer right pointer se aage nahi nikal jata
        while (left < right) {
            // Agar left side ki current height right side ki current height se kam hai
            if (height[left] < height[right]) {
                // Iska matlab hai ki left side paani ke level ko determine kar rahi hai
                // Agar current left height, pichli leftMax se zyada ya barabar hai
                if (height[left] >= leftMax) {
                    leftMax = height[left];     // Toh leftMax ko update karo
                } else {
                    // Warna, is left position par paani jama hoga
                    totalWater += leftMax - height[left]; // Jama hue paani ko total mein add karo
                }
                left++; // Left pointer ko aage badhao
            } else {
                // Agar right side ki current height left side ki current height se kam ya barabar hai
                // Iska matlab hai ki right side paani ke level ko determine kar rahi hai
                // Agar current right height, pichli rightMax se zyada ya barabar hai
                if (height[right] >= rightMax) {
                    rightMax = height[right];     // Toh rightMax ko update karo
                } else {
                    // Warna, is right position par paani jama hoga
                    totalWater += rightMax - height[right]; // Jama hue paani ko total mein add karo
                }
                right--; // Right pointer ko peeche karo
            }
        }

        return totalWater;  // Total jama hua paani return karo
    }
}
```

-----

## **Problem: 3Sum**

Aapko ek integer array `nums` diya gaya hai. Aapko **saare triplets** `[nums[i], nums[j], nums[k]]` return karne hain jismein `i != j`, `i != k`, aur `j != k` ho, aur `nums[i] + nums[j] + nums[k] == 0` ho.

**Note:** Solution set mein **duplicate triplets nahi** hone chahiye.

### **Example:**

**Input:**
`nums = [-1, 0, 1, 2, -1, -4]`

**Output:**
`[[-1, -1, 2], [-1, 0, 1]]`

**Explanation:**
Woh triplets jinka sum zero hai, woh hain:

  * `(-1) + 0 + 1 = 0`
  * `(-1) + (-1) + 2 = 0`
    Duplicates ko avoid kiya gaya hai, aur output ke order se koi matlab nahi hai.

-----

### **Intuition (Samajh):**

Hamein 3 numbers dhoondhne hain जिनका sum `0` ho. Agar array sorted ho, toh `two-pointer` approach kaafi useful hoti hai `sum == target` problems mein.

Is problem mein, target `0` hai. Hum ek number ko fix kar sakte hain (jaise `nums[i]`). Ab, hamein baaki ke do numbers dhoondhne hain (`nums[j]` aur `nums[k]`) jinka sum `(0 - nums[i])` ho. Yeh ab `Two Sum` problem jaisi ho gayi, jise hum sorted array mein two-pointers se solve kar sakte hain.

Duplicates ko handle karna important hai taaki final answer mein same triplets do baar na aayen. Sorting ismein bhi help karegi.

-----

### **Approach (Tareeka):**

1.  **Array Sort Karein:** Sabse pehle `nums` array ko **sort** kar do. Isse two-pointer technique use karna aur duplicates ko skip karna aasan ho jayega.

2.  **First Element Fix Karein:**

      * Ek loop chalao `i` ke liye, jo `0` se `nums.length - 2` tak jayega. `nums.length - 2` tak isliye kyunki hamein `j` aur `k` ke liye kam se kam do elements aur chahiye.
      * **Duplicate First Element Skip Karein:** `if (i > 0 && nums[i] == nums[i - 1]) continue;`
          * Yeh check karta hai ki agar current `nums[i]` pichle `nums[i-1]` ke barabar hai, toh is `i` ko skip kar do. Kyunki agar humne `nums[i-1]` ke saath triplets dhoondh liye hain, toh `nums[i]` ke saath dhoondhne par duplicate triplets milenge.

3.  **Two-Pointer Approach (Inner Loop):**

      * `j = i + 1` (second pointer, `i` ke just aage).
      * `k = nums.length - 1` (third pointer, array ke end par).
      * Ek `while (j < k)` loop chalao.
      * **Sum Calculate Karein:** `int sum = nums[i] + nums[j] + nums[k];`
      * **Conditions:**
          * If `sum == 0`:
              * Triplet `[nums[i], nums[j], nums[k]]` mil gaya. Isko `result list` mein add kar do.
              * `j++` aur `k--` (pointers ko andar move karo).
              * **Duplicate Second/Third Element Skip Karein:**
                  * `while (j < k && nums[j] == nums[j - 1]) j++;` (jab tak `j` valid hai aur current `nums[j]` pichle ke barabar hai, `j` ko aage badhao)
                  * `while (j < k && nums[k] == nums[k + 1]) k--;` (jab tak `k` valid hai aur current `nums[k]` agle ke barabar hai, `k` ko peeche karo)
          * If `sum > 0`:
              * Sum bahut zyada hai, iska matlab hamein sum ko kam karna hai. Iske liye, `k--` (third pointer ko left move karo).
          * If `sum < 0`:
              * Sum bahut kam hai, iska matlab hamein sum ko badhana hai. Iske liye, `j++` (second pointer ko right move karo).

4.  **Return:** `result list` return kar do.

-----

### **Java Solution with Comments:**

```java
import java.util.*; // Necessary imports

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // Step 1: Array ko sort karo taaki two-pointer technique use kar saken aur duplicates ko easily handle kar saken.
        Arrays.sort(nums);
        List<List<Integer>> list = new ArrayList<>(); // Final result list, jismein triplets store honge

        // Step 2: Array par iterate karo, triplet ke pehle element ko fix karte hue.
        // Loop nums.length - 2 tak hi chalega, kyunki hamein j aur k ke liye kam se kam do elements aur chahiye.
        for (int i = 0; i < nums.length - 2; i++) {
            // Duplicate elements ko skip karo taaki duplicate triplets na milen.
            // Agar current 'i' 0 se bada hai aur nums[i] pichle nums[i-1] ke barabar hai, toh is iteration ko skip karo.
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int j = i + 1;                  // Second pointer, 'i' ke just aage se shuru
            int k = nums.length - 1;        // Third pointer, array ke bilkul end par

            // Step 3: Two-pointer approach use karo baaki ke do numbers dhoondhne ke liye.
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k]; // Teenon numbers ka sum calculate karo

                if (sum == 0) {
                    // Agar sum 0 hai, toh hamein ek valid triplet mil gaya hai.
                    list.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++; // j ko aage badhao
                    k--; // k ko peeche karo

                    // Duplicates ko skip karo second element ke liye.
                    // Jab tak j, k se chota hai aur current nums[j] pichle nums[j-1] ke barabar hai, tab tak j ko aage badhao.
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    // Duplicates ko skip karo third element ke liye.
                    // Jab tak j, k se chota hai aur current nums[k] agle nums[k+1] ke barabar hai, tab tak k ko peeche karo.
                    while (j < k && nums[k] == nums[k + 1]) k--;
                }
                else if (sum > 0) {
                    // Agar sum 0 se bada hai (bahut zyada hai), toh sum ko kam karne ke liye k ko peeche karo.
                    k--;
                } else { // sum < 0
                    // Agar sum 0 se chota hai (bahut kam hai), toh sum ko badhane ke liye j ko aage badhao.
                    j++;
                }
            }
        }

        return list; // Triplet ki list return karo
    }
}
```
