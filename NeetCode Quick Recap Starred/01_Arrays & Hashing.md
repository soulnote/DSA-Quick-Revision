## Problem: Group Anagrams

Given an array of strings `strs`, group the anagrams together. You can return the answer in **any order**.

  * An **anagram** is a word formed by rearranging the letters of another word, using all the original letters exactly once.

-----

## Example:

**Input:**

```
strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
```

**Output:**

```
[["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
```

**Explanation:**

  * "eat", "tea", and "ate" are anagrams of each other, so they are grouped together.
  * "tan" and "nat" are anagrams and grouped together.
  * "bat" has no anagram in the list, so it forms a group alone.

-----

## Java Solution:

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Grouped anagrams ko store karne ke liye list
        List<List<String>> ans = new ArrayList<>();

        // Sorted string ko anagrams ki list se map karne ke liye HashMap
        HashMap<String, List<String>> map = new HashMap<>();

        // Saare strings par loop chalao
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];

            // String ko char array mein convert karo aur sort karo taaki key mil sake
            char[] tempS = s.toCharArray();
            Arrays.sort(tempS);
            String sortedS = new String(tempS);

            // Agar yeh sorted key map mein nahi hai, toh ek nayi list ke saath add karo
            if (!map.containsKey(sortedS)) {
                map.put(sortedS, new ArrayList<>());
            }

            // Original string ko corresponding list mein add karo
            map.get(sortedS).add(s);
        }

        // Saari grouped anagram lists ko answer mein add karo
        for (String key : map.keySet()) {
            ans.add(map.get(key));
        }

        return ans;
    }
}
```

-----

### Explanation of the solution (Hinglish):

1.  **Har string ko sort karo:**
    Jab aap ek anagram ke characters ko sort karte ho, toh woh same ho jaate hain. Jaise:

      * `"eat"` → `"aet"`
      * `"tea"` → `"aet"`
      * `"ate"` → `"aet"`

2.  **HashMap use karo:**
    Sorted string ko key ki tarah use karo, aur woh saari original strings jo is sorted key se match karti hain, unko ek list mein store karo.

3.  **Keys ke hisab se group karo:**
    Saari strings process hone ke baad, map mein har key anagrams ke ek group ko represent karegi.

4.  **Grouped anagrams return karo:**
    Map se saari lists collect karo aur unko result ke roop mein return kar do.

-----

## Problem: Top K Frequent Elements

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements in the array. You may return the answer in **any order**.

-----

## Example:

**Input:**

```
nums = [1, 1, 1, 2, 2, 3], k = 2
```

**Output:**

```
[1, 2]
```

**Explanation:**

  * The number `1` appears 3 times.
  * The number `2` appears 2 times.
  * The number `3` appears 1 time.
  * The top 2 frequent elements are `[1, 2]`.

-----

## Java Solution:

```java
import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Har number ki frequency count karo HashMap use karke
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // Frequency count badhao ya agar present nahi hai toh 1 se initialize karo
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        // Step 2: Top k frequent elements ko track karne ke liye min-heap (priority queue) use karo
        // Heap mein [number, frequency] ke arrays store hote hain
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1]) // Frequency se compare karo (min-heap)
        );

        // Elements ko heap mein add karo; heap ka size k se zyada nahi hona chahiye
        for (int num : map.keySet()) {
            int[] element = new int[]{num, map.get(num)};
            pq.offer(element);
            if (pq.size() > k) {
                pq.poll(); // Agar size k se zyada ho gaya toh sabse kam frequency wala element hata do
            }
        }

        // Step 3: Heap se numbers ko result array mein extract karo
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            int[] top = pq.poll();
            ans[i] = top[0];
        }

        return ans;
    }
}
```

-----

### Explanation of the solution (Hinglish):

1.  **Frequency counting:**
    Ek `HashMap` use karo yeh count karne ke liye ki har element `nums` mein kitni baar aata hai.

2.  **Top k rakhne ke liye min-heap:**
    Ek priority queue (min-heap) use karo jo pairs `[number, frequency]` store karti hai.

      * Heap ka size zyada se zyada `k` rakha jata hai.
      * Agar naya element add karne se heap ka size `k` se zyada ho jata hai, toh sabse kam frequency wale element ko remove kar do, taaki heap mein hamesha top k most frequent elements rahen.

3.  **Output banao:**
    Heap se saare elements extract karo, jo top `k` frequent numbers hain.

-----

## Problem: Valid Sudoku

Determine if a 9 x 9 Sudoku board is valid. Only the **filled cells** need to be validated according to the following rules:

  * Each row must contain the digits 1-9 **without repetition**.
  * Each column must contain the digits 1-9 **without repetition**.
  * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 **without repetition**.

**Note:**

  * The Sudoku board may be partially filled.
  * The board could be valid but not necessarily solvable.
  * Only check the validity of the current filled cells.

-----

## Example:

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
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
```

**Output:**

```
true
```

-----

## Java Solution:

```java
import java.util.HashSet;
import java.util.Set;

class Solution {

    public boolean isValidSudoku(char[][] board) {
        Set<Character> rowSet = null; // Har row mein numbers track karne ke liye
        Set<Character> colSet = null; // Har column mein numbers track karne ke liye

        // Har row aur column ko duplicates ke liye check karo
        for (int i = 0; i < 9; i++) {
            rowSet = new HashSet<>(); // Har nayi row ke liye naya set
            colSet = new HashSet<>(); // Har naye column ke liye naya set
            for (int j = 0; j < 9; j++) {
                char r = board[i][j]; // Row 'i', column 'j' ka character
                char c = board[j][i]; // Column 'i' (actual column index 'j'), row 'j' (actual row index 'i') ka character

                // Row ko duplicates ke liye check karo, '.' ko chhodkar
                if (r != '.') {
                    if (rowSet.contains(r)) {
                        return false; // Row mein duplicate mil gaya
                    } else {
                        rowSet.add(r);
                    }
                }

                // Column ko duplicates ke liye check karo, '.' ko chhodkar
                if (c != '.') {
                    if (colSet.contains(c)) {
                        return false; // Column mein duplicate mil gaya
                    } else {
                        colSet.add(c);
                    }
                }
            }
        }

        // Har 3x3 sub-box ko duplicates ke liye check karo
        // i aur j har 3x3 block ke top-left corners par iterate karte hain
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!checkBlock(i, j, board)) {
                    return false; // Sub-box mein duplicate mil gaya
                }
            }
        }

        // Saare checks pass ho gaye; board valid hai
        return true;
    }

    // Helper function jo (idxI, idxJ) se shuru hone wale 3x3 sub-box ko check karti hai
    public boolean checkBlock(int idxI, int idxJ, char[][] board) {
        Set<Character> blockSet = new HashSet<>(); // Block ke numbers track karne ke liye
        int rows = idxI + 3; // Block ki ending row index
        int cols = idxJ + 3; // Block ki ending column index

        // 3x3 block cells par iterate karo
        for (int i = idxI; i < rows; i++) {
            for (int j = idxJ; j < cols; j++) {
                char val = board[i][j]; // Current cell ki value

                if (val == '.') {
                    continue; // Khali cells ko ignore karo
                }

                if (blockSet.contains(val)) {
                    return false; // Is block mein duplicate mil gaya
                }

                blockSet.add(val); // Value ko set mein add karo
            }
        }

        return true; // Is block mein koi duplicate nahi mila
    }
}
```

-----

### Explanation of the solution (Hinglish):

1.  **Rows aur columns check karo:**

      * Har row aur har column ke liye, duplicate numbers ko detect karne ke liye ek `Set` use karo.
      * Khali cells (`'.'`) ko skip kar do.

2.  **3x3 sub-boxes check karo:**

      * Total 9 sub-boxes hain, har ek 3x3 size ka.
      * Har sub-box ko `(0,0), (0,3), (0,6), (3,0), ...` se start karte hue iterate karo.
      * Sub-box ke andar duplicates detect karne ke liye ek `Set` use karo.

3.  **Validity return karo:**

      * Agar kisi bhi row, column, ya sub-box mein koi duplicate milta hai, toh `false` return karo.
      * Agar koi duplicate nahi milta, toh `true` return karo.
