## Problem: Group Anagrams

Given an array of strings `strs`, group the anagrams together. You can return the answer in **any order**.

* An **anagram** is a word formed by rearranging the letters of another word, using all the original letters exactly once.

---

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

---

## Java Solution:

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // List to store the grouped anagrams
        List<List<String>> ans = new ArrayList<>();
        
        // HashMap to map sorted string -> list of anagrams
        HashMap<String, List<String>> map = new HashMap<>();
        
        // Loop through all strings
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            
            // Convert string to char array and sort it to get the key
            char[] tempS = s.toCharArray();
            Arrays.sort(tempS);
            String sortedS = new String(tempS);
            
            // If this sorted key is not present in map, add it with a new list
            if (!map.containsKey(sortedS)) {
                map.put(sortedS, new ArrayList<>());
            }
            
            // Add the original string to the corresponding list
            map.get(sortedS).add(s);
        }
        
        // Add all grouped anagram lists to the answer
        for (String key : map.keySet()) {
            ans.add(map.get(key));
        }
        
        return ans;
    }
}
```

---

### Explanation of the solution:

1. **Sort each string:**
   When you sort the characters in an anagram, they become the same. For example:

   * `"eat"` → `"aet"`
   * `"tea"` → `"aet"`
   * `"ate"` → `"aet"`

2. **Use a HashMap:**
   Use the sorted string as the key, and store all original strings that match this sorted key in a list.

3. **Group by keys:**
   After processing all strings, each key in the map corresponds to a group of anagrams.

4. **Return grouped anagrams:**
   Collect all the lists from the map and return them as the result.


---

## Problem: Top K Frequent Elements

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements in the array. You may return the answer in **any order**.

---

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

---

## Java Solution:

```java
import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Count frequency of each number using a HashMap
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // Increase frequency count or initialize to 1 if not present
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        // Step 2: Use a min-heap (priority queue) to keep track of top k frequent elements
        // The heap stores arrays of [number, frequency]
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[1], b[1]) // Compare by frequency (min-heap)
        );

        // Add elements to the heap; keep heap size <= k
        for (int num : map.keySet()) {
            int[] element = new int[]{num, map.get(num)};
            pq.offer(element);
            if (pq.size() > k) {
                pq.poll(); // Remove the element with lowest frequency if size exceeds k
            }
        }

        // Step 3: Extract the numbers from the heap into the result array
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            int[] top = pq.poll();
            ans[i] = top[0];
        }

        return ans;
    }
}
```

---

### Explanation of the solution:

1. **Frequency counting:**
   Use a `HashMap` to count how many times each element appears in `nums`.

2. **Min-heap to keep top k:**
   Use a priority queue (min-heap) that stores pairs `[number, frequency]`.

   * The heap size is kept at most `k`.
   * If adding a new element exceeds `k`, remove the smallest frequency element, so the heap always contains the k most frequent elements.

3. **Build output:**
   Extract all elements from the heap, which are the top `k` frequent numbers.

---

## Problem: Valid Sudoku

Determine if a 9 x 9 Sudoku board is valid. Only the **filled cells** need to be validated according to the following rules:

* Each row must contain the digits 1-9 **without repetition**.
* Each column must contain the digits 1-9 **without repetition**.
* Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 **without repetition**.

**Note:**

* The Sudoku board may be partially filled.
* The board could be valid but not necessarily solvable.
* Only check the validity of the current filled cells.

---

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

---

## Java Solution:

```java
import java.util.HashSet;
import java.util.Set;

class Solution {

    public boolean isValidSudoku(char[][] board) {
        Set<Character> rowSet = null; // To track numbers in each row
        Set<Character> colSet = null; // To track numbers in each column

        // Check each row and column for duplicates
        for (int i = 0; i < 9; i++) {
            rowSet = new HashSet<>();
            colSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                char r = board[i][j]; // character in row i, column j
                char c = board[j][i]; // character in column i, row j
                
                // Check row for duplicates excluding '.'
                if (r != '.') {
                    if (rowSet.contains(r)) {
                        return false; // Duplicate found in row
                    } else {
                        rowSet.add(r);
                    }
                }

                // Check column for duplicates excluding '.'
                if (c != '.') {
                    if (colSet.contains(c)) {
                        return false; // Duplicate found in column
                    } else {
                        colSet.add(c);
                    }
                }
            }
        }

        // Check each 3x3 sub-box for duplicates
        // i and j iterate over top-left corners of each 3x3 block
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!checkBlock(i, j, board)) {
                    return false; // Duplicate found in sub-box
                }
            }
        }

        // All checks passed; board is valid
        return true;
    }

    // Helper function to check 3x3 sub-box starting at (idxI, idxJ)
    public boolean checkBlock(int idxI, int idxJ, char[][] board) {
        Set<Character> blockSet = new HashSet<>();
        int rows = idxI + 3;
        int cols = idxJ + 3;

        // Iterate through the 3x3 block cells
        for (int i = idxI; i < rows; i++) {
            for (int j = idxJ; j < cols; j++) {
                char val = board[i][j];

                if (val == '.') {
                    continue; // Ignore empty cells
                }

                if (blockSet.contains(val)) {
                    return false; // Duplicate found in this block
                }

                blockSet.add(val);
            }
        }

        return true; // No duplicates found in this block
    }
}
```

---

### Explanation of the solution:

1. **Check rows and columns:**

   * For each row and each column, use a `Set` to detect duplicate numbers.
   * Skip empty cells (`'.'`).

2. **Check 3x3 sub-boxes:**

   * There are 9 sub-boxes, each 3x3 in size.
   * Iterate through each sub-box starting at `(0,0), (0,3), (0,6), (3,0), ...`
   * Use a `Set` to detect duplicates within the sub-box.

3. **Return validity:**

   * If any duplicate is found in a row, column, or sub-box, return `false`.
   * If no duplicates, return `true`.

---

