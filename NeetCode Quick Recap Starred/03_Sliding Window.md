## **Problem: Longest Substring Without Repeating Characters**

Aapko ek string `s` di gayi hai. Aapko us **sabse lambi substring ki length** dhundhni hai jismein koi bhi character **repeat na** ho.

### **Example:**

**Input:**
`s = "abcabcbb"`

**Output:**
`3`

**Explanation:**
Sabse lambi substring jismein characters repeat nahi ho rahe hain, woh `"abc"` hai, aur uski length `3` hai.

-----

### **Intuition (Samajh):**

Hamein ek "window" banani hai string ke andar. Yeh window aisi honi chahiye jismein koi bhi character repeat na ho. Hamein is window ki maximum possible length chahiye.

Jab hum window ko right side se badhate hain, aur ek aisa character milta hai jo **already window mein hai**, toh iska matlab hai ki humari current window ab invalid ho gayi. Isko valid banane ke liye, hamein window ko left side se chhota karna padega. Left pointer ko us repeating character ke **pichle occurrence ke ek aage** tak move karna hoga.

Ek `HashMap` ka use karke hum har character ka latest index store kar sakte hain. Isse hamein pata chal jayega ki agar koi character repeat hota hai, toh uski pichli occurrence kahan thi.

-----

### **Approach (Tareeka):**

Hum **sliding window** technique use karenge.

1.  **Variables Initialize Karein:**

      * `HashMap<Character, Integer> map`: Yeh har character aur uske **latest index** ko store karega.
      * `left = 0`, `right = 0`: Sliding window ke do pointers.
      * `longest = 0`: Sabse lambi unique substring ki length track karega.

2.  **Window Ko Slide Karein:**

      * `while (right < s.length())` loop chalao.
      * Current character `ch = s.charAt(right)` ko dekho.
      * **Condition Check:**
          * **Agar `ch` `map` mein nahi hai (yaani current window mein naya hai):**
              * `map.put(ch, right)`: `ch` ko uske index ke saath map mein daal do.
              * `right++`: Window ko right side se aage badhao.
          * **Agar `ch` `map` mein already hai (yaani duplicate mila):**
              * `left = Math.max(left, map.get(ch) + 1);`: `left` pointer ko update karo. Ab naya `left` ya toh current `left` hi rahega, ya phir `ch` ke **pichle occurrence ke just aage** wale index par chala jayega. Is `Math.max` se yeh ensure hota hai ki `left` hamesha rightward hi move karega (kabhi peeche nahi jayega) aur current duplicate ko effectively exclude kar dega.
              * `map.put(ch, right);`: Current character `ch` ka index `map` mein update kar do (kyunki hamein latest index chahiye).
              * `right++`: Window ko right side se aage badhao.
      * **Length Update Karein:** Har step par `longest` ko update karo: `longest = Math.max(longest, right - left);`. `right - left` current window ki length hai (jo hamesha unique characters wali hogi).

3.  **Return:** `longest` ko return kar do.

-----

### **Java Solution with Comments:**

```java
import java.util.HashMap; // HashMap use karne ke liye import

class Solution {
    public int lengthOfLongestSubstring(String s) {
        // Map jo har character ka sabse naya index store karega
        HashMap<Character, Integer> map = new HashMap<>();

        int left = 0, right = 0;   // Sliding window ke pointers: left shuruat, right aakhri
        int longest = 0;           // Abhi tak mili sabse lambi unique substring ki length

        // Jab tak right pointer string ke end tak nahi pahunchta
        while (right < s.length()) { // Loop 'right' pointer ko aage badhaega
            char ch = s.charAt(right); // Current character jahan right pointer hai

            // Agar current character 'ch' map mein pehle se hai (matlab duplicate mila)
            if (map.containsKey(ch)) {
                // 'left' pointer ko update karo. Naya 'left' ya toh current 'left' hi rahega,
                // ya phir duplicate character 'ch' ke pichle index + 1 par chala jayega.
                // Math.max isliye taaki 'left' kabhi peeche na jaye, hamesha aage hi badhe.
                left = Math.max(left, map.get(ch) + 1);
            }

            // Current character 'ch' aur uska index 'right' map mein store ya update karo.
            // Chahe 'ch' naya ho ya duplicate, hum uska latest index store karenge.
            map.put(ch, right);

            // Current window (left se right tak) ki length calculate karo: right - left + 1.
            // Aur 'longest' ko update karo agar current window ki length zyada hai.
            longest = Math.max(longest, right - left + 1);

            right++; // Right pointer ko ek step aage badhao, window ko expand karo
        }

        return longest; // Sabse lambi unique-character substring ki length return karo
    }
}
```

-----

## **Problem: Longest Repeating Character Replacement**

Aapko ek string `s` di gayi hai jismein sirf uppercase English letters hain, aur ek integer `k`.
Aap string mein **zyada se zyada `k` characters ko** kisi bhi doosre uppercase letter se **replace** kar sakte ho.

Aapka goal hai us **sabse lambi substring ki length** dhundhna jismein replacements karne ke baad **saare characters same letter** ke hon.

### **Example:**

**Input:**
`s = "ABAB", k = 2`

**Output:**
`4`

**Explanation:**
Hum do `'A'`s ko `'B'`s se (ya vice versa) replace kar sakte hain, jisse `"BBBB"` ya `"AAAA"` banega, jiski length `4` hai.

-----

### **Intuition (Samajh):**

Hamein ek window chahiye jismein hum `k` replacements karke us window ke saare characters ko same bana saken. Jis window mein aisa karne se sabse lambi substring milegi, wahi hamara answer hoga.

Ek sliding window approach use karenge. Jab hum window ko slide karte hain, toh hamein yeh pata hona chahiye ki current window mein kitne characters ko replace karna padega taaki saare characters same ho jayen.

Agar current window ki `total length` mein se `sabse zyada baar aane wale character ki frequency` ko hata dein, toh bache hue characters woh honge jinhe hamein replace karna padega. Agar yeh count `k` se zyada ho jata hai, toh matlab humari window bahut badi ho gayi hai aur usko chhota karna padega left side se.

-----

### **Approach (Tareeka):**

1.  **Variables Initialize Karein:**

      * `HashMap<Character, Integer> count`: Current window mein har character ki frequency track karega.
      * `res = 0`: Sabse lambi valid window ki length store karega.
      * `l = 0`: Left pointer of the window.
      * `maxf = 0`: Current window mein kisi bhi character ki maximum frequency.

2.  **Window Ko Slide Karein (`r` pointer se):**

      * `for (int r = 0; r < s.length(); r++)` loop chalao (right pointer).
      * Current character `s.charAt(r)` ki frequency `count` map mein badhao.
      * `maxf = Math.max(maxf, count.get(s.charAt(r)))`: `maxf` ko update karo, current character ki frequency ke base par.

3.  **Window Validity Check aur Shrink:**

      * Condition: `(r - l + 1) - maxf > k`
          * `r - l + 1` current window ki length hai.
          * `maxf` us window mein sabse zyada baar aane wale character ki frequency.
          * `window size - maxf` = un characters ki sankhya jinhein replace karna padega.
      * Agar `replace karne wale characters` ki sankhya `k` se zyada hai:
          * `count.put(s.charAt(l), count.get(s.charAt(l)) - 1);`: `left` pointer par jo character hai, uski frequency `count` map mein kam karo.
          * `l++`: `left` pointer ko aage badhao, window ko chhota karo.

4.  **Result Update Karein:**

      * `res = Math.max(res, r - l + 1);`: Har iteration mein, `res` ko current valid window ki length (`r - l + 1`) se update karo.

5.  **Return:** `res` ko return kar do.

-----

### **Java Solution with Comments:**

```java
import java.util.HashMap; // HashMap use karne ke liye import

public class Solution {
    public int characterReplacement(String s, int k) {
        // HashMap jo current window mein har character ki frequency track karega
        HashMap<Character, Integer> count = new HashMap<>();
        int res = 0;  // Sabse lambi valid window ki length store karega
        int l = 0;    // Window ka left pointer
        int maxf = 0; // Current window mein kisi bhi character ki maximum frequency

        // 'r' pointer se string par iterate karte hain, window ko right se expand karte hue
        for (int r = 0; r < s.length(); r++) {
            char currentChar = s.charAt(r); // Current character jahan 'r' pointer hai

            // Current character ki frequency 'count' map mein badhao
            count.put(currentChar, count.getOrDefault(currentChar, 0) + 1);

            // Current window mein kisi bhi character ki max frequency update karo
            maxf = Math.max(maxf, count.get(currentChar));

            // Check karo ki current window valid hai ya nahi (kya hamein k se zyada replacements chahiye?)
            // (Window ki size) - (sabse frequent character ki frequency) = Characters jo replace karne hain
            if ((r - l + 1) - maxf > k) {
                // Agar replace karne wale characters 'k' se zyada hain, toh window ko left se shrink karo
                char leftChar = s.charAt(l); // Left pointer par jo character hai
                count.put(leftChar, count.get(leftChar) - 1); // Uski frequency map mein kam karo
                l++; // Left pointer ko aage badhao, window choti karo
            }

            // Har step par 'res' ko current valid window ki size se update karo
            // Current window ki size = r - l + 1
            res = Math.max(res, r - l + 1);
        }

        return res; // Sabse lambi valid substring ki length return karo
    }
}
```

-----

## **Problem: Permutation in String**

Aapko do strings `s1` aur `s2` di gayi hain. Aapko `true` return karna hai agar **`s2` ke andar `s1` ka koi permutation** maujood hai — doosre shabdon mein, agar `s2` ki koi bhi substring `s1` ka rearrangement hai.

### **Example:**

**Input:**
`s1 = "ab"`
`s2 = "eidbaooo"`

**Output:**
`true`

**Explanation:**
`s2` mein substring `"ba"` `s1` ka permutation hai.

-----

### **Intuition (Samajh):**

Ek string doosri string ka permutation hai ya nahi, yeh check karne ka sabse accha tareeka unki **character frequencies** ko compare karna hai. Agar dono strings mein har character ki frequency same hai, toh woh ek doosre ke permutation hain.

Yahan, hamein `s2` ke andar `s1` ki length ki ek substring dhundhni hai jo `s1` ka permutation ho. Iske liye hum **fixed-size sliding window** technique use kar sakte hain. Window ka size `s1.length()` ke barabar hoga.

-----

### **Approach (Tareeka):**

1.  **Initial Checks:**

      * Agar `s1` ki length `s2` se zyada hai, toh permutation possible hi nahi. `false` return kar do.

2.  **Frequency Arrays Initialize Karein:**

      * Do `int` arrays (`size 26` for 'a' to 'z' characters) banao:
          * `s1Freq`: `s1` ke characters ki frequency store karega.
          * `windowFreq`: Current window of `s2` ke characters ki frequency store karega.

3.  **First Window Prepare Karein:**

      * `s1.length()` tak loop chalao.
      * `s1` ke characters ki frequency `s1Freq` mein bharo.
      * `s2` ke pehle `s1.length()` characters ki frequency `windowFreq` mein bharo (yeh aapki initial window hogi).

4.  **Window Ko Slide Karein (`i` pointer se):**

      * `i` ko `s1.length()` se `s2.length()` tak loop chalao.
      * **Permutation Check:** Har step par `Arrays.equals(s1Freq, windowFreq)` se check karo ki kya current `windowFreq` `s1Freq` ke barabar hai. Agar haan, toh `true` return kar do.
      * **Slide The Window:**
          * `windowFreq[s2.charAt(i) - 'a']++`: Naye character ko window mein add karo (jo `s2` ka `i`-th character hai).
          * `windowFreq[s2.charAt(i - s1.length()) - 'a']--`: Window ke left se bahar nikalne wale character ko remove karo. (Yeh `i - s1.length()` index par hoga).

5.  **Last Window Check:**

      * Loop khatam hone ke baad, last window ko bhi check karna zaroori hai, kyunki `if (Arrays.equals(...))` check loop ke *andar* aur `i` ke `increment` hone *se pehle* hota hai.
      * `return Arrays.equals(s1Freq, windowFreq);`

-----

### **Java Solution with Comments:**

```java
import java.util.Arrays; // Arrays.equals() use karne ke liye

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        // Agar s1 s2 se lambi hai, toh s2 mein s1 ka koi permutation possible nahi hai.
        if (s1.length() > s2.length()) return false;

        // Characters 'a' se 'z' tak ki frequency store karne ke liye arrays.
        // Size 26 hai kyunki English alphabet mein 26 letters hote hain.
        int[] s1Freq = new int[26];
        int[] windowFreq = new int[26];

        // s1 aur s2 ki pehli window (s1 ki length tak) ke liye frequency arrays ko initialize karo.
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++; // s1 ke char ki frequency badhao
            windowFreq[s2.charAt(i) - 'a']++; // s2 ki pehli window ke char ki frequency badhao
        }

        // Ab window ko s2 par slide karo.
        // 'i' pointer naye character ko window mein add karega.
        for (int i = s1.length(); i < s2.length(); i++) {
            // Check karo ki kya current window s1 ka permutation hai (frequencies match kar rahi hain?)
            if (Arrays.equals(s1Freq, windowFreq)) {
                return true; // Agar match karta hai, toh true return karo
            }

            // Window ko slide karo:
            // 1. Naye character ko window mein add karo.
            windowFreq[s2.charAt(i) - 'a']++;
            // 2. Pichli window ke pehle character ko remove karo.
            // s2.charAt(i - s1.length()) woh character hai jo ab window se bahar nikal raha hai.
            windowFreq[s2.charAt(i - s1.length()) - 'a']--;
        }

        // Loop khatam hone ke baad, aakhri window ko bhi check karna zaroori hai.
        return Arrays.equals(s1Freq, windowFreq);
    }
}
```

-----

## **Problem: Minimum Window Substring**

Aapko do strings `s` aur `t` di gayi hain. Aapko `s` ki **minimum window substring** return karni hai jismein `t` ke **saare characters (duplicates sahit)** maujood hon. Agar aisi koi substring nahi milti, toh `""` return karo.

Answer ke **unique** hone ki guarantee hai.

### **Example:**

**Input:**
`s = "ADOBECODEBANC"`
`t = "ABC"`

**Output:**
`"BANC"`

**Explanation:**
Substring `"BANC"` `s` mein sabse choti window hai jismein `t` ke saare characters hain.

-----

### **Intuition (Samajh):**

Yeh bhi ek **sliding window** problem hai, lekin yahan window ka size fixed nahi hai. Hamein `s` mein ek aisi window dhundhni hai jo `t` ke saare characters (unke counts ke saath) contain karti ho, aur phir unmein se **sabse choti** window ko return karna hai.

Hum do pointers (`left` aur `right`) use karenge. `right` pointer se window ko expand karenge jab tak `t` ke saare required characters mil nahi jaate. Jab mil jayenge, toh `left` pointer se window ko shrink karna shuru karenge, jab tak window valid rahe. Har valid window milne par uski length ko track karenge aur sabse choti length wali window ko store kar lenge.

`Frequency arrays` kaafi useful rahenge characters aur unke counts ko track karne ke liye.

-----

### **Approach (Tareeka):**

1.  **Edge Cases Handle Karein:**

      * Agar `s` `t` se choti hai, ya `t` ya `s` khaali hain, toh `""` return kar do.

2.  **Frequency Arrays Initialize Karein:**

      * `tFq = new int[128]`: `t` ke characters ki frequency store karega (ASCII values ke liye 128 size).
      * `wFq = new int[128]`: Current window of `s` ke characters ki frequency store karega.
      * `t` ke characters ki frequency `tFq` mein bharo.

3.  **Variables Initialize Karein:**

      * `left = 0, right = 0`: Window pointers.
      * `formed = 0`: Kitne `t` characters window mein successfully match ho gaye hain (counts ke saath).
      * `required = t.length()`: `t` mein total characters (duplicates sahit) jinko match karna hai.
      * `minLength = Integer.MAX_VALUE`: Sabse choti window ki current length.
      * `minStart = 0`: Sabse choti window ka starting index.

4.  **Window Expand Karein (`right` pointer se):**

      * `while (right < s.length())` loop chalao.
      * Current character `ch = s.charAt(right)` lo.
      * `wFq[ch]++`: `ch` ko `windowFreq` mein add karo.
      * **`formed` Update Karein:**
          * `if (tFq[ch] > 0 && wFq[ch] <= tFq[ch]) formed++;`
          * Iska matlab hai ki agar `ch` `t` mein required tha, aur uski current count `t` mein uski required count se zyada nahi hui hai, toh `formed` count badha do.

5.  **Window Shrink Karein (`left` pointer se) jab tak `formed == required` hai:**

      * `while (formed == required)` loop chalao.
      * **Min Window Update Karein:**
          * `if (right - left + 1 < minLength)`: Agar current window ki length `minLength` se choti hai, toh update karo `minLength` aur `minStart`.
      * **Character Remove Karein from Left:**
          * `char leftChar = s.charAt(left);`
          * `wFq[leftChar]--`: `leftChar` ki frequency `windowFreq` mein kam karo.
          * **`formed` Update Karein (agar window invalid hoti hai):**
              * `if (tFq[leftChar] > 0 && wFq[leftChar] < tFq[leftChar]) formed--;`
              * Iska matlab hai ki agar `leftChar` `t` mein required tha, aur usko remove karne ke baad uski count `t` ki required count se kam ho gayi, toh `formed` count kam karo (window ab valid nahi rahi).
      * `left++`: `left` pointer ko aage badhao.

6.  **`right++`:** `right` pointer ko har bar aage badhao.

7.  **Result Return Karein:**

      * Agar `minLength` `Integer.MAX_VALUE` hi raha, matlab koi valid window nahi mili. `""` return karo.
      * Warna, `s.substring(minStart, minStart + minLength)` return karo.

-----

### **Java Solution with Comments:**

```java
class Solution {
    public String minWindow(String s, String t) {
        // Edge cases handle karo: agar s t se chota hai, ya t/s khaali hain
        if (s.length() < t.length() || t.length() == 0 || s.length() == 0) return "";

        // Characters ki frequency store karne ke liye arrays (ASCII characters ke liye 128 size)
        int[] tFq = new int[128]; // 't' mein characters ki frequency
        int[] wFq = new int[128]; // Current window 's' mein characters ki frequency

        // 't' ke characters ki frequency 'tFq' mein bharo
        for (int i = 0; i < t.length(); i++) {
            tFq[t.charAt(i)]++;
        }

        int left = 0, right = 0;             // Window ke pointers
        int formed = 0;                      // Kitne 't' characters (counts ke saath) window mein match ho gaye hain
        int required = t.length();           // Total characters (duplicates sahit) jinhe match karna hai 't' mein

        int minLength = Integer.MAX_VALUE;   // Sabse choti window ki current length
        int minStart = 0;                    // Sabse choti valid window ka starting index

        // Window ko 'right' pointer se expand karo
        while (right < s.length()) {
            char ch = s.charAt(right); // Current character jahan 'right' pointer hai
            wFq[ch]++; // Current character ko window frequency mein add karo

            // Agar yeh character 't' mein required tha AND uski count abhi bhi 't' mein uski required count se zyada nahi hui hai
            if (tFq[ch] > 0 && wFq[ch] <= tFq[ch]) {
                formed++; // Toh 'formed' count badha do
            }

            // Window ko 'left' pointer se shrink karne ki koshish karo jab tak window valid hai (formed == required)
            while (formed == required) {
                // Agar current window pichli 'minLength' se choti hai, toh update karo
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    minStart = left; // Sabse choti window ka starting index store karo
                }

                // Window ke left se character hatao
                char leftChar = s.charAt(left);
                wFq[leftChar]--; // 'leftChar' ki frequency window se kam karo

                // Agar 'leftChar' 't' mein required tha AND usko remove karne ke baad uski count 't' ki required count se kam ho gayi
                if (tFq[leftChar] > 0 && wFq[leftChar] < tFq[leftChar]) {
                    formed--; // Toh 'formed' count kam karo (window ab valid nahi rahi)
                }

                left++; // Left pointer ko aage badhao (window ko shrink karo)
            }

            right++; // Right pointer ko aage badhao (window ko expand karo)
        }

        // Result return karo: agar koi valid window nahi mili (minLength MAX_VALUE hi raha), toh "" return karo
        // Warna, 's' ki substring return karo 'minStart' se 'minStart + minLength' tak
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }
}
```

-----

## 🔹 **Problem: Sliding Window Maximum**

Aapko ek integer array `nums` aur ek integer `k` diya gaya hai.
Ek **sliding window** hai jiska size `k` hai, aur woh array mein left se right move kar rahi hai.
Har step par, aapko **window mein maximum value** return karni hai.

### ✅ **Example**

**Input:**
`nums = [1,3,-1,-3,5,3,6,7]`, `k = 3`

**Output:**
`[3,3,5,5,6,7]`

**Explanation:**
Hum 3 size ki window slide karte hain aur max value lete hain:

```
[1   3  -1] -3   5   3   6   7 → max = 3
 1  [3  -1  -3]  5   3   6   7 → max = 3
 1   3  [-1  -3  5]  3   6   7 → max = 5
 1   3  -1  [-3  5  3]  6   7 → max = 5
 1   3  -1  -3  [5  3  6]  7 → max = 6
 1   3  -1  -3   5  [3  6  7] → max = 7
```

-----

### 💡 **Approach: Monotonic Queue (Deque)**

Yeh problem simple sliding window se thoda trickier hai kyunki hamein har baar window ka maximum chahiye. Agar hum har baar window ko iterate karein, toh yeh slow ho jayega. Iske liye hum ek special data structure use karte hain jise **Monotonic Deque (Double-Ended Queue)** kehte hain.

  * **Deque (Double-Ended Queue) kya hai?** Yeh ek queue hai jismein elements ko dono sides (front aur back) se add aur remove kiya ja sakta hai.
  * **Monotonic Deque kya hai?** Hum is deque mein **indices** store karenge, lekin is tarah se ki deque ke andar ke elements (un indices par `nums` ki values) hamesha **decreasing order** mein rahen.
  * **Front ka matlab:** Deque ke **front** par hamesha **current window ke maximum element ka index** hoga.
  * **Naye index ko add karne se pehle, yeh elements remove karo:**
      * Woh indices jo current **window se bahar** ho gaye hain (left side se).
      * Woh indices jinki values **current number se choti** hain (deque ke back se), kyunki agar naya number bada hai, toh purana chota number kabhi max nahi banega jab tak yeh bada number window mein hai.

-----

### **Approach (Tareeka):**

1.  **Variables Initialize Karein:**

      * `ans = new int[nums.length - k + 1]`: Result array jismein har window ka max store hoga.
      * `j = 0`: `ans` array ka index.
      * `Deque<Integer> q = new LinkedList<>()`: Monotonic deque (indices store karega).

2.  **Array Iterate Karein (`i` pointer se):**

      * `for (int i = 0; i < nums.length; i++)` loop chalao.
      * **Deque Cleanup (Left Side):**
          * `if (!q.isEmpty() && q.peekFirst() < i - k + 1)`: Agar deque khaali nahi hai aur deque ke front par jo index hai woh current window ki left boundary (`i - k + 1`) se chota hai, toh us index ko deque ke front se `pollFirst()` karke remove kar do (kyunki woh window se bahar ho gaya).
      * **Deque Cleanup (Right Side / Monotonicity):**
          * `while (!q.isEmpty() && nums[i] > nums[q.peekLast()])`: Jab tak deque khaali nahi hai aur current number `nums[i]` deque ke back par stored index ki value (`nums[q.peekLast()]`) se bada hai, tab tak deque ke back se `pollLast()` karke elements remove karte raho. (Kyunki agar `nums[i]` bada hai, toh woh pichle chote elements ko future max banne se rok dega).
      * **Add Current Index:**
          * `q.offerLast(i)`: Current index `i` ko deque ke back par add kar do.

3.  **Result Record Karein:**

      * `if (i >= k - 1)`: Jab `i` `k-1` ke barabar ya usse bada ho jaye (matlab jab window mein `k` elements aa jayen), tab:
          * `ans[j++] = nums[q.peekFirst()]`: Deque ke front par jo index hai, us par `nums` ki value hi current window ka maximum hogi. Use `ans` array mein store kar do.

4.  **Return:** `ans` array return kar do.

-----

### 🔧 **Code (Java) with Comments:**

```java
import java.util.Deque; // Deque interface ko import karo
import java.util.LinkedList; // LinkedList jo Deque interface ko implement karta hai

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // Result array banaya jismein har window ka maximum store hoga.
        // Total windows hongi: nums.length - k + 1
        int[] ans = new int[nums.length - k + 1];
        int j = 0; // 'ans' array mein elements dalne ke liye index

        // Monotonic Deque banaya jo indices ko store karega
        // Deque ke front par hamesha current window ka max element ka index hoga.
        // Elements decreasing order of values mein honge from front to back.
        Deque<Integer> q = new LinkedList<>();

        // Array par iterate karo 'i' (right pointer) se
        for (int i = 0; i < nums.length; i++) {
            // Step 1: Deque ke front se un indices ko hatao jo ab current window se bahar ho gaye hain.
            // Current window ki left boundary hai: i - k + 1
            if (!q.isEmpty() && q.peekFirst() < i - k + 1) {
                q.pollFirst(); // Front se element hatao
            }

            // Step 2: Deque ke back se un indices ko hatao jinki value current element (nums[i]) se choti hai.
            // Kyunki nums[i] bada hai, toh yeh chote elements kabhi max nahi ban payenge jab tak nums[i] window mein hai.
            while (!q.isEmpty() && nums[i] > nums[q.peekLast()]) {
                q.pollLast(); // Back se element hatao
            }

            // Step 3: Current element ka index deque ke back par add karo.
            q.offerLast(i);

            // Step 4: Jab window size 'k' tak pahunch jaye (yaani i >= k - 1),
            // tab current window ka maximum (jo deque ke front par hai) ko result array mein store karo.
            if (i >= k - 1) {
                ans[j++] = nums[q.peekFirst()];
            }
        }

        return ans; // Final result array return karo
    }
}
```

-----

### 🧠 **Time & Space Complexity**

  * **Time Complexity:** `O(n)` — Har element deque mein zyada se zyada ek baar add hota hai aur ek baar remove hota hai. Isliye total operations `2*n` ke order mein hote hain.
  * **Space Complexity:** `O(k)` — Deque mein zyada se zyada `k` elements (indices) store hote hain.

-----
