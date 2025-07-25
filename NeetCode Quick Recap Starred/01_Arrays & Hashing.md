## **Problem: Group Anagrams**

Aapko `strs` naam ka ek array diya gaya hai jismein strings hain. Aapko un strings ko group karna hai jo ek doosre ke **anagrams** hain. Result aap **kisi bhi order** mein return kar sakte hain.

  * **Anagram** ka matlab hai ki ek aisa shabd (word) jo doosre shabd ke aksharon (letters) ko idhar-udhar karke banaya gaya ho, aur saare original aksharon ka bilkul ek baar istemal kiya gaya ho.

### **Example:**

**Input:**

```
strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
```

**Output:**

```
[["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
```

**Explanation:**

  * "eat", "tea", aur "ate" ek doosre ke anagrams hain, toh inko ek saath group kiya gaya hai.
  * "tan" aur "nat" anagrams hain aur ek saath grouped hain.
  * "bat" ka koi anagram list mein nahi hai, toh woh akela ek group banata hai.

-----

### **Intuition (Samajh):**

Ek Anagram ko pehchanne ka sabse simple tareeka kya hai? Agar do words ke paas exactly same characters hain, bas unka order alag hai, toh woh anagrams hain. Jaise 'listen' aur 'silent'. Dono mein 'l', 'i', 's', 't', 'e', 'n' hain. Agar hum in characters ko **alphabetical order mein sort kar dein**, toh 'listen' bhi 'eilnst' banega aur 'silent' bhi 'eilnst' banega.

Bas yahi idea hai\! Har string ko sort kar do. Jo strings sort hone ke baad same dikhengi, woh anagrams hain.

-----

### **Approach (Tareeka):**

1.  **Ek Map Banayein:** Hamein ek `HashMap` chahiye. Iska `key` sorted string hogi (jaise "aet" ya "abt") aur `value` un original strings ki `List` hogi jo us sorted key se match karti hain (jaise ["eat", "tea", "ate"]).

2.  **Har String Ko Process Karein:**

      * `strs` array mein har `string` ko uthao.
      * Us `string` ko `character array` mein badlo.
      * `character array` ko **sort** kar do.
      * Sorted `character array` ko wapas `string` mein convert kar lo. Yehi humari **key** hogi.

3.  **Map Mein Store Karein:**

      * Check karo ki kya yeh **sorted key** (jo abhi banayi hai) `HashMap` mein pehle se maujood hai?
      * Agar nahi hai, toh us **sorted key** ke liye `HashMap` mein ek **nayi empty List** banao.
      * Ab, original `string` ko us **sorted key** se associated `List` mein add kar do.

4.  **Result Collect Karein:**

      * Jab saari strings process ho jayengi, toh `HashMap` ki saari `values` (jo ki `List<String>` hain) ko ek `List<List<String>>` mein collect karke return kar do.

-----

### **Java Solution:**

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Yeh final list hai jismein saare anagram groups store honge.
        List<List<String>> ans = new ArrayList<>();

        // Yeh HashMap sorted string (key) ko original strings ki list (value) se map karega.
        // Matlab, agar "eat", "tea", "ate" teenon ka sorted form "aet" hai, toh "aet" key ke liye
        // value ["eat", "tea", "ate"] wali list hogi.
        HashMap<String, List<String>> map = new HashMap<>();

        // Ab, input array 'strs' ki har string par iterate karte hain.
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i]; // Current original string

            // String ko character array mein badla taaki sort kar sakein.
            char[] tempS = s.toCharArray();
            // Character array ko alphabetically sort kiya.
            Arrays.sort(tempS);
            // Sorted character array ko wapas String mein convert kiya. Yeh hamari "key" banegi.
            String sortedS = new String(tempS);

            // Check karte hain ki kya yeh sorted key (e.g., "aet") map mein pehle se hai?
            if (!map.containsKey(sortedS)) {
                // Agar nahi hai, toh is sorted key ke liye map mein ek nayi khaali list bana do.
                map.put(sortedS, new ArrayList<>());
            }

            // Ab, original string 's' ko us sorted key 'sortedS' se associated list mein add kar do.
            // Example: map.get("aet").add("eat");
            map.get(sortedS).add(s);
        }

        // Jab saari strings process ho jayengi, toh map mein har key ek anagram group ko represent karegi.
        // Hum bas map ki saari values (jo ki lists hain) ko 'ans' list mein add kar denge.
        for (String key : map.keySet()) {
            ans.add(map.get(key));
        }

        // Final grouped anagrams return kar do.
        return ans;
    }
}
```

-----

## **Problem: Top K Frequent Elements**

Aapko ek integer array `nums` aur ek integer `k` diya gaya hai. Aapko array mein se **`k` sabse zyada baar aane wale (most frequent) elements** return karne hain. Result aap **kisi bhi order** mein return kar sakte hain.

### **Example:**

**Input:**

```
nums = [1, 1, 1, 2, 2, 3], k = 2
```

**Output:**

```
[1, 2]
```

**Explanation:**

  * Number `1` 3 baar aaya hai.
  * Number `2` 2 baar aaya hai.
  * Number `3` 1 baar aaya hai.
  * Toh, top 2 sabse zyada baar aane wale elements `[1, 2]` hain.

-----

### **Intuition (Samajh):**

Hamein har number ki **frequency (kitni baar aaya hai)** pata karni hai, aur phir unmein se top `k` wale numbers ko select karna hai.

1.  **Frequency kaise pata karein?** Ek `HashMap` ka istemal karke hum har number aur uski frequency ko store kar sakte hain. Jaise `key` number hoga aur `value` uski frequency.
2.  **Top `k` kaise nikalein?** Jab hamare paas saare numbers ki frequencies hongi, toh hamein unko frequency ke hisab se sort karna padega aur top `k` uthane padenge. Sort karne ke liye hum `PriorityQueue` (heap) ka use kar sakte hain, khaas kar ke jab hamein sirf top `k` chahiye hon. `Min-heap` use karne se hum efficiently top `k` elements rakh sakte hain.

-----

### **Approach (Tareeka):**

1.  **Frequency Count Karein:**

      * Ek `HashMap<Integer, Integer>` banao (jismein `number -> frequency` store hoga).
      * `nums` array ke har element par loop chalao aur uski frequency ko map mein update karo.

2.  **Min-Heap Ka Use Karein:**

      * Ek `PriorityQueue` (min-heap) banao. Yeh `int[]` store karegi jismein `[number, frequency]` hoga.
      * `PriorityQueue` ka `comparator` aisa set karo ki woh `frequency` ke base par elements ko sort kare (sabse kam frequency wala element top par ho).
      * `HashMap` se har `number` aur uski `frequency` ko uthao.
      * Is `[number, frequency]` pair ko heap mein `offer` karo.
      * Agar `heap` ka `size` `k` se zyada ho jaata hai, toh `pq.poll()` (sabse kam frequency wala element) ko `remove` kar do. Is tarah heap mein hamesha top `k` frequent elements rahenge.

3.  **Result Banayein:**

      * Ek `int` array banao jiska size `k` ho.
      * `heap` se elements ko ek-ek karke `poll` karo aur un numbers ko result array mein store karte jao.

-----

### **Java Solution:**

```java
import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Har number ki frequency count karne ke liye HashMap ka istemal karein.
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // Agar number pehle se map mein hai, toh uski frequency badha do,
            // warna uski frequency 1 se initialize kar do.
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        // Step 2: Top k frequent elements ko track karne ke liye min-heap (PriorityQueue) ka istemal karein.
        // Yeh heap [number, frequency] ke arrays ko store karega.
        // Hum yahan ek custom comparator de rahe hain jo frequency (a[1]) ke hisab se compare karega.
        // Min-heap hone ka matlab hai ki sabse kam frequency wala element top par rahega.
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1]) // Frequency (dusre index) ke hisab se compare karo
        );

        // Har number aur uski frequency ko map se lo aur heap mein add karo.
        // Heap ka size hamesha k ya usse kam rakho.
        for (int num : map.keySet()) {
            int[] element = new int[]{num, map.get(num)}; // [number, frequency] ka pair banaya
            pq.offer(element); // Element ko heap mein add kiya

            // Agar heap ka size k se zyada ho gaya hai,
            if (pq.size() > k) {
                pq.poll(); // Toh sabse kam frequency wale element ko hata do (min-heap se)
            }
        }

        // Step 3: Heap se numbers ko nikal kar result array mein store karein.
        int[] ans = new int[k]; // K size ka answer array banaya
        for (int i = 0; i < k; i++) {
            int[] top = pq.poll(); // Heap se top element nikala (jo abhi tak ka sabse kam frequent wala hai among top k)
            ans[i] = top[0]; // Us element ka number (pehla index) result array mein store kiya
        }

        return ans; // Final answer array return karo
    }
}
```

-----

## **Problem: Valid Sudoku**

Ek 9 x 9 Sudoku board diya gaya hai. Check karna hai ki yeh valid hai ya nahi. Sirf **bhare hue cells (filled cells)** ko hi in rules ke hisab se validate karna hai:

  * Har row mein digits 1-9 **bina repetition** ke hone chahiye.
  * Har column mein digits 1-9 **bina repetition** ke hone chahiye.
  * Grid ke har 3 x 3 sub-box mein digits 1-9 **bina repetition** ke hone chahiye.

**Note:**

  * Sudoku board partially bhara ho sakta hai.
  * Board valid ho sakta hai, lekin zaroori nahi ki solvable ho.
  * Sirf current bhare hue cells ki validity check karni hai.

-----

### **Example:**

**Input:**

```
board =
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".".".","."4","1","9",".",".","5"]
,[".".".","."."8",".",".","7","9"]]
```

**Output:**

```
true
```

-----

### **Intuition (Samajh):**

Sudoku validity ka matlab hai ki koi bhi number (1-9) ek hi row, ek hi column, ya ek hi 3x3 box mein do baar nahi aana chahiye. Aur hamein sirf bhare hue cells ko check karna hai, '.' (empty cells) ko ignore karna hai.

Iske liye hum `HashSet` ka use kar sakte hain. `HashSet` mein duplicates add nahi hote. Agar hum koi number add karne ki koshish karein aur woh pehle se set mein ho, toh matlab duplicate hai.

-----

### **Approach (Tareeka):**

1.  **Rows aur Columns Check Karein:**

      * Ek loop chalao `i` (0 se 8 tak) rows ke liye.
      * Har `i` ke liye, do naye `HashSet<Character>` banao: ek `rowSet` current row ke liye aur ek `colSet` current column ke liye.
      * Ek aur inner loop chalao `j` (0 se 8 tak) cells ke liye.
      * Current row cell `board[i][j]` ko `rowSet` mein check karo. Agar `'.'` nahi hai aur `rowSet.contains()` true deta hai, toh `false` return karo (duplicate hai). Warna `rowSet.add()` kar do.
      * Current column cell `board[j][i]` ko `colSet` mein check karo. Same logic follow karo.
      * **Note:** `board[j][i]` se hum column check kar rahe hain. Jab `i` fixed hai (e.g., `i=0`), `j` vary karega (0 se 8), toh `board[j][0]` (0,0), (1,0), ..., (8,0) first column check karega.

2.  **3x3 Sub-Boxes Check Karein:**

      * Do nested loops chalao, `i` aur `j` (0, 3, 6 ke values ke liye) jo har 3x3 block ke **top-left corner** ko represent karengi.
      * Har block ke liye ek separate helper function `checkBlock(idxI, idxJ, board)` banao.
      * Is helper function ke andar, current 3x3 block ke andar ke cells par iterate karo.
      * Ek `HashSet<Character>` banao `blockSet`.
      * Har cell `val` (`.` ko chhodkar) ko `blockSet` mein check karo. Agar duplicate milta hai, toh `false` return karo. Warna add kar do.
      * Agar `checkBlock` function `false` return karta hai, toh `isValidSudoku` function bhi `false` return kar dega.

3.  **Validity Return Karein:**

      * Agar saare checks pass ho jaate hain (koi duplicate nahi milta), toh `true` return karo.

-----

### **Java Solution:**

```java
import java.util.HashSet;
import java.util.Set;

class Solution {

    public boolean isValidSudoku(char[][] board) {
        // Har row mein numbers track karne ke liye HashSet
        // Har column mein numbers track karne ke liye HashSet
        Set<Character> rowSet = null;
        Set<Character> colSet = null;

        // Har row aur har column ko duplicates ke liye check karo
        // Outer loop rows (0 se 8) ke liye
        for (int i = 0; i < 9; i++) {
            // Har nayi row/column check ke liye naye Sets banao
            rowSet = new HashSet<>();
            colSet = new HashSet<>();
            // Inner loop cells (0 se 8) ke liye
            for (int j = 0; j < 9; j++) {
                // 'r' current row 'i' aur column 'j' ka character hai
                char r = board[i][j];
                // 'c' current column 'i' aur row 'j' ka character hai (column check ke liye)
                char c = board[j][i];

                // Row ko duplicates ke liye check karo ('.' ko chhodkar)
                if (r != '.') {
                    // Agar 'rowSet' mein 'r' pehle se hai, toh duplicate hai
                    if (rowSet.contains(r)) {
                        return false; // Invalid Sudoku, duplicate mila
                    } else {
                        rowSet.add(r); // Warna 'r' ko set mein add karo
                    }
                }

                // Column ko duplicates ke liye check karo ('.' ko chhodkar)
                if (c != '.') {
                    // Agar 'colSet' mein 'c' pehle se hai, toh duplicate hai
                    if (colSet.contains(c)) {
                        return false; // Invalid Sudoku, duplicate mila
                    } else {
                        colSet.add(c); // Warna 'c' ko set mein add karo
                    }
                }
            }
        }

        // Ab har 3x3 sub-box ko duplicates ke liye check karo
        // Outer loops har 3x3 block ke top-left corners (0,0), (0,3), (0,6), (3,0), etc. par iterate karte hain
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                // checkBlock helper function ko call karo. Agar wahan duplicate milta hai, toh return false.
                if (!checkBlock(i, j, board)) {
                    return false; // Sub-box mein duplicate mila
                }
            }
        }

        // Agar saare checks pass ho gaye (koi duplicate nahi mila), toh board valid hai
        return true;
    }

    // Yeh helper function ek 3x3 sub-box ko check karta hai jo (idxI, idxJ) se shuru hota hai
    public boolean checkBlock(int idxI, int idxJ, char[][] board) {
        Set<Character> blockSet = new HashSet<>(); // Is block ke numbers track karne ke liye set
        int rowsEnd = idxI + 3; // Is block ki ending row index
        int colsEnd = idxJ + 3; // Is block ki ending column index

        // 3x3 block ke andar ke cells par iterate karo
        for (int i = idxI; i < rowsEnd; i++) {
            for (int j = idxJ; j < colsEnd; j++) {
                char val = board[i][j]; // Current cell ki value

                if (val == '.') {
                    continue; // Khali cells ko ignore karo (unhe check karne ki zaroorat nahi)
                }

                // Agar 'blockSet' mein 'val' pehle se hai, toh duplicate hai
                if (blockSet.contains(val)) {
                    return false; // Is block mein duplicate mila
                }

                blockSet.add(val); // 'val' ko set mein add karo
            }
        }

        return true; // Is block mein koi duplicate nahi mila
    }
}
```

-----

Kaisa laga yeh detailed Hinglish explanation? Kya ab aapko concepts aur bhi clear ho gaye hain?
