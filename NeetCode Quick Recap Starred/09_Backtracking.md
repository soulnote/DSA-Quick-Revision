# ⭐ Combination Sum

Aapko **distinct** integers ka ek array `candidates` aur ek **target integer `target`** diya gaya hai. Aapko `candidates` ke un sabhi **unique combinations** ko dhundhna hai jahan chune gaye numbers ka sum `target` ke barabar ho.

  * Aap ek hi number ko **multiple times** use kar sakte hain.
  * Combinations numbers ki frequency ke basis par unique hain.
  * Sabhi valid combinations ko kisi bhi order mein return karein.

-----

### 📌 Example

#### Input:

```
candidates = [2,3,6,7], target = 7
```

#### Output:

```
[[2,2,3],[7]]
```

**Explanation:**

  * `2 + 2 + 3 = 7` (yahan '2' do baar use hua hai)
  * `7 = 7`
  * Yehi do valid unique combinations hain.

-----

### Intuition (Samajh):

Yeh problem **backtracking** ka ek classic example hai. Humein `candidates` array se numbers chunne hain takay unka sum `target` ban jaye. Kyunki hum ek hi number ko **multiple times** use kar sakte hain aur order matter nahi karta (combinations chahiye, permutations nahi), hum ek recursive approach use karenge.

Har number ke liye, hamare paas do options hain:

1.  **Include the current number**: Hum current number ko apne combination mein shamil karte hain aur phir se usi number (index) se search continue karte hain, lekin `target` ko current number se ghata dete hain.
2.  **Exclude the current number**: Hum current number ko apne combination mein shamil nahi karte hain aur agle number (index) par jaate hain, `target` ko unchanged rakhte hain.

Base cases:

  * Agar `target` 0 ho jata hai, toh humein ek valid combination mil gaya hai. Use apne result list mein add kar do.
  * Agar `target` negative ho jata hai (yaani, humne target se zyada add kar diya), ya agar hum array ke end tak pahunch gaye hain aur `target` abhi bhi 0 nahi hai, toh yeh path invalid hai, wapas chale jao.

-----

### Approach (Tareeka): Backtracking

1.  **Result List Initialize Karo**: Ek `List<List<Integer>> ans` banao jismein saare valid combinations store honge.
2.  **Recursive Function `solve`**:
      * Parameters: `idx` (current index in `candidates`), `nums` (candidates array), `target` (remaining target sum), `ans` (final result list), `list` (current combination being built).
      * **Base Cases**:
          * `if (target == 0)`: Agar `target` exactly 0 ho gaya, toh `list` mein ek valid combination hai. Iski ek copy `ans` mein add karo aur return kar jao.
          * `if (idx >= nums.length || target < 0)`: Agar `idx` array bounds se bahar nikal gaya, ya `target` negative ho gaya, toh return kar jao, kyunki yeh path valid nahi hai.
      * **Include Current Number**:
          * `list.add(nums[idx])`: `candidates[idx]` ko current combination `list` mein add karo.
          * `solve(idx, nums, target - nums[idx], ans, list)`: Phir se `solve` call karo, **usi `idx`** ke saath (kyunki hum ek hi number ko multiple times use kar sakte hain) aur `target` ko `nums[idx]` se ghata kar.
          * `list.remove(list.size() - 1)`: **Backtrack\!** Recursive call return hone ke baad, `nums[idx]` ko `list` se remove kar do. Yeh step zaroori hai takay agle recursion path ke liye `list` clean rahe.
      * **Exclude Current Number**:
          * `solve(idx + 1, nums, target, ans, list)`: `nums[idx]` ko shamil kiye bina agle number (`idx + 1`) par move karo, aur `target` ko unchanged rakho.
3.  **Main Function**: `combinationSum` se `solve` function ko `(0, candidates, target, ans, new ArrayList<>())` ke saath call karo.

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>(); // Saare valid combinations store karne ke liye list.
        // Recursive helper function ko call karte hain.
        // Shuruat index 0 se, target ke saath, empty current combination list ke saath.
        solve(0, candidates, target, ans, new ArrayList<>());
        return ans;
    }

    // Recursive backtracking method
    // idx: Current index in candidates array.
    // nums: Candidates array.
    // target: Remaining sum jo humein chahiye.
    // ans: Final list of combinations.
    // list: Current combination jo hum bana rahe hain.
    private void solve(int idx, int[] nums, int target, List<List<Integer>> ans, List<Integer> list) {
        // Base Case 1: Agar target 0 ho gaya, iska matlab humein ek valid combination mil gaya hai.
        if (target == 0) {
            // Current list ki ek copy bana kar answer mein add karte hain.
            // Copy banana zaroori hai kyunki 'list' object changes hotey rahenge backtracking ki wajah se.
            ans.add(new ArrayList<>(list));
            return; // Is path se wapas chale jao.
        }

        // Base Case 2: Agar index array bounds ke bahar chala gaya (saare numbers consider kar liye)
        // ya agar target negative ho gaya (target se zyada sum ho gaya), toh yeh path invalid hai.
        if (idx >= nums.length || target < 0) {
            return; // Wapas chale jao.
        }

        // Option 1: Current number (nums[idx]) ko combination mein shamil karo.
        // Kyunki hum ek hi number ko multiple times use kar sakte hain,
        // hum is number ko dobara consider karne ke liye 'idx' ko change nahi karte.
        list.add(nums[idx]); // Number ko current list mein add karo.
        solve(idx, nums, target - nums[idx], ans, list); // Recursive call: target kam karo.

        // Backtrack: Pichli recursive call se wapas aane ke baad,
        // current number ko list se remove karo takay agle options explore kar sakein.
        list.remove(list.size() - 1);

        // Option 2: Current number (nums[idx]) ko combination mein shamil mat karo.
        // Agle number (idx + 1) par move karo aur target ko unchanged rakho.
        solve(idx + 1, nums, target, ans, list);
    }
}
```

-----

# ⭐ Combination Sum II

Aapko candidate numbers (`candidates`) ka ek collection aur ek target number (`target`) diya gaya hai. Un sabhi **unique combinations** ko dhundho jahan candidate numbers ka sum `target` ho.

  * `candidates` mein har number ko har combination mein **sirf ek baar** use kiya ja sakta hai.
  * Solution set mein **duplicate combinations nahi hone chahiye**.

-----

### 📌 Example

#### Input:

```
candidates = [10,1,2,7,6,1,5], target = 8
```

#### Output:

```
[
  [1,1,6],
  [1,2,5],
  [1,7],
  [2,6]
]
```

### Intuition (Samajh):

यह "Combination Sum" का एक variation है, जिसमें दो मुख्य बदलाव हैं:

1.  **Each number can be used only once**: इसका मतलब है कि जब हम `candidates[i]` को एक combination में शामिल करते हैं, तो अगली recursive call में हमें `i + 1` पर जाना होगा, `i` पर नहीं।
2.  **No duplicate combinations**: यह सबसे tricky पार्ट है, क्योंकि `candidates` array में duplicate numbers हो सकते हैं (जैसे `[10,1,2,7,6,1,5]` में दो `1` हैं)। अगर हम इसे सही से हैंडल नहीं करेंगे, तो हमें `[1,1,6]` जैसी duplicate combinations मिल सकती हैं अगर `1` को अलग-अलग तरीके से चुना गया हो।

इस duplicate combination issue को हैंडल करने का सबसे अच्छा तरीका है array को **sort** करना। Sorting से duplicate numbers एक साथ आ जाते हैं। फिर, जब हम `candidates[i]` को skip करने का निर्णय लेते हैं, तो हमें `candidates[i]` के सभी duplicates को भी skip कर देना चाहिए ताकि वे एक ही position से start होने वाले duplicate combinations न बनाएं।

-----

### Approach (Tareeka): Backtracking with Duplicate Handling

1.  **Result List Initialize Karo**: `List<List<Integer>> res` final combinations ke liye.
2.  **Sort the Candidates**: `Arrays.sort(candidates)` karo. Yeh duplicates ko group karta hai, jisse unhe skip karna aasan ho jata hai.
3.  **Recursive Function `dfs`**:
      * Parameters: `candidates` array, `target` (remaining sum), `i` (current index), `cur` (current combination), `total` (current sum of `cur`).
      * **Base Cases**:
          * `if (total == target)`: Agar `total` `target` ke barabar hai, toh `cur` ek valid combination hai. Iski copy `res` mein add karo aur return.
          * `if (total > target || i == candidates.length)`: Agar `total` `target` se zyada ho gaya, ya `i` array ke end tak pahunch gaya, toh return karo.
      * **Include Current Number (`candidates[i]`)**:
          * `cur.add(candidates[i])`: `candidates[i]` ko current combination mein add karo.
          * `dfs(candidates, target, i + 1, cur, total + candidates[i])`: Agli recursive call `i + 1` par karo (kyunki har number ek baar hi use ho sakta hai) aur `total` ko `candidates[i]` se badha do.
          * `cur.remove(cur.size() - 1)`: **Backtrack\!** Number ko `cur` se remove karo.
      * **Skip Duplicates (Crucial Step for Uniqueness)**:
          * `while (i + 1 < candidates.length && candidates[i] == candidates[i + 1])`: Jab tak hum array ke bounds mein hain aur current element agle element ke barabar hai, `i` ko badhate raho. Yeh ensure karta hai ki hum **same element ke duplicates ko skip kar rahe hain** taaki woh naye combinations na banayein.
          * `i++` is used to move `i` past all duplicates of `candidates[i]`.
      * **Exclude Current Number (`candidates[i]`)**:
          * `dfs(candidates, target, i + 1, cur, total)`: Agle (unique) number (`i + 1`) par move karo, current number ko skip karte hue, aur `total` ko unchanged rakho.

-----

### Java Code with Comments:

```java
import java.util.*;

public class Solution {
    private List<List<Integer>> res; // Sabhi unique combinations store karne ke liye.

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        res = new ArrayList<>();
        // Step 1: Candidates array ko sort karna bohot zaroori hai.
        // Isse duplicate numbers ek saath group ho jayenge, jise hum baad mein skip kar payenge.
        Arrays.sort(candidates);
        // Recursive DFS (Depth-First Search) function ko call karte hain.
        // Parameters: candidates array, target sum, current index (0 se shuru),
        // current combination list (empty), current total sum (0).
        dfs(candidates, target, 0, new ArrayList<>(), 0);
        return res;
    }

    // Backtracking method
    // candidates: Input array of numbers.
    // target: Desired sum.
    // i: Current index in the candidates array.
    // cur: Current combination list being built.
    // total: Current sum of elements in 'cur'.
    private void dfs(int[] candidates, int target, int i, List<Integer> cur, int total) {
        // Base Case 1: Agar current total sum target ke barabar hai.
        if (total == target) {
            // Toh humein ek valid unique combination mil gaya hai.
            // Current combination ki ek copy final result list mein add karo.
            res.add(new ArrayList<>(cur));
            return; // Is path se wapas chale jao.
        }

        // Base Case 2: Pruning conditions.
        // Agar total sum target se zyada ho gaya (over-sum)
        // ya agar hum array ke end tak pahunch gaye (no more candidates to pick).
        if (total > target || i == candidates.length) {
            return; // Is path se aage explore karne ka koi fayda nahi, wapas chale jao.
        }

        // Option 1: Current candidate (candidates[i]) ko current combination mein shamil karo.
        cur.add(candidates[i]); // Number ko current list mein add karo.
        // Recursive call: Ab agle index (i + 1) par jao kyunki har number ek baar hi use ho sakta hai.
        // Target aur total sum ko update karo.
        dfs(candidates, target, i + 1, cur, total + candidates[i]);
        cur.remove(cur.size() - 1); // Backtrack: Pichli recursive call se wapas aane par, number ko remove karo.

        // Option 2: Current candidate (candidates[i]) ko current combination mein shamil mat karo.
        // Is option mein, humein duplicates ko skip karna hoga.
        // Example: [1,1,6], target=8.
        // Jab pehla 1 pick kiya -> [1], next dfs from index 2.
        // Jab pehla 1 skip kiya, toh doosre 1 ko bhi skip karna chahiye (agar wo same index se start hota hai).
        while (i + 1 < candidates.length && candidates[i] == candidates[i + 1]) {
            // Is loop se hum 'i' ko tab tak badhate rahenge jab tak hum current element ke
            // saare duplicates ko bypass nahi kar lete.
            i++;
        }

        // Ab 'i' us first non-duplicate element par point kar raha hai
        // ya array ke end par pahunch gaya hai (agar saare remaining duplicates the).
        // Isliye, agle unique element ke liye recursive call karo.
        dfs(candidates, target, i + 1, cur, total);
    }
}
```

-----

# ⭐ Subsets II

Aapko ek integer array `nums` diya gaya hai jismein **duplicates ho sakte hain**. Aapko uske **sabhi possible subsets** (power set) return karne hain.

  * Solution set mein **duplicate subsets nahi hone chahiye**.
  * Solution ko **kisi bhi order** mein return karein.

-----

### 📌 Example

#### Input:

```
nums = [1, 2, 2]
```

#### Output:

```
[[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
```

### Intuition (Samajh):

Yeh problem "Subsets" ka variation hai, jahan input array `nums` mein **duplicates** ho sakte hain. Humare solution set mein duplicate subsets nahi hone chahiye (jaise `[1,2]` aur `[1,2]` agar do alag '2' se bane hon).

Is problem ko solve karne ke liye bhi **backtracking** ek effective tareeka hai. Duplicate subsets ko avoid karne ke liye, hum `candidates` array ko pehle **sort** karenge. Sorting se duplicate numbers ek saath aa jate hain, jisse hum unhe handle kar sakte hain.

**Key Idea for Duplicates**:
Jab hum recursion mein aage badhte hain (ek element ko include ya exclude karne ke baad), agar current element duplicate hai apne previous element ka (aur humne previous instance ko already consider kar liya hai), toh hum is duplicate ko skip kar sakte hain agar hum `exclude` path le rahe hain.

Lekin is code mein ek slightly different approach use ki gayi hai jahan ek `HashSet` ka upyog karke duplicate subsets ko filter kiya gaya hai. Jabki iterative approach ya "Combination Sum II" jaisi duplicate skipping logic zyada efficient ho sakti hai, `HashSet` simpler hai.

-----

### Approach (Tareeka): Backtracking with HashSet for Uniqueness

1.  **Result Set Initialize Karo**: Ek `HashSet<List<Integer>> ans` banao. `HashSet` automatically duplicate `List` objects ko store nahi karega, jo uniqueness ensure karega.
2.  **Sort the Array**: `Arrays.sort(nums)` karo. Yeh duplicates ko group karta hai, jisse unki order consistency maintain hoti hai jo `HashSet` mein `List` comparison ke liye helpful ho sakti hai.
3.  **Recursive Function `solve`**:
      * Parameters: `idx` (current index in `nums`), `nums` array, `list` (current subset being built), `ans` (final result set).
      * **Base Case**:
          * `if (idx == nums.length)`: Jab hum array ke end tak pahunch gaye hain, iska matlab `list` mein ek complete subset hai.
          * `ans.add(new ArrayList<>(list))`: `list` ki ek copy `ans` (HashSet) mein add karo. `HashSet` automatically handle karega agar yeh subset pehle se मौजूद hai.
          * `return`: Wapas chale jao.
      * **Include Current Number**:
          * `list.add(nums[idx])`: `nums[idx]` ko current subset `list` mein add karo.
          * `solve(idx + 1, nums, list, ans)`: Agle number (`idx + 1`) ke liye recursive call karo.
          * `list.remove(list.size() - 1)`: **Backtrack\!** Number ko `list` se remove karo.
      * **Exclude Current Number**:
          * `solve(idx + 1, nums, list, ans)`: Current number `nums[idx]` ko shamil kiye bina agle number (`idx + 1`) par move karo.
4.  **Main Function**: `subsetsWithDup` se `solve` function ko `(0, nums, new ArrayList<>(), ans)` ke saath call karo.
5.  **Return Result**: `new ArrayList<>(ans)` (HashSet ko List mein convert karke) return karo.

-----

### Java Code with Comments:

```java
import java.util.*;

public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // HashSet ka use karte hain duplicate subsets ko automatically avoid karne ke liye.
        // List<Integer> hashable nahi hoti directly, isliye List<List<Integer>> ans banakar,
        // har List<Integer> ki copy ko HashSet mein add karte hain.
        // Note: HashSet mein List<Integer> ko store karne par, List ke contents ki equality check hoti hai.
        HashSet<List<Integer>> ans = new HashSet<>();
        List<Integer> list = new ArrayList<>(); // Current subset jo hum bana rahe hain.

        // Array ko sort karna bohot zaroori hai.
        // Isse duplicate elements ek saath aate hain, jo HashSet ko unique subsets pehchanne mein help karta hai.
        // Example: [1,2,2]. Agar sorted nahi hota toh [2,1,2] bhi ho sakta tha. Sorting se consistency aati hai.
        Arrays.sort(nums);
        
        // Recursive backtracking method ko call karte hain.
        // Shuruat index 0 se, empty list ke saath.
        solve(0, nums, list, ans);
        
        // HashSet se saare unique subsets ko ek ArrayList mein convert karke return karte hain.
        return new ArrayList<>(ans);
    }

    // Recursive backtracking method to generate subsets
    // idx: Current index in nums array.
    // nums: Input array.
    // list: Current subset being built.
    // ans: HashSet to store unique subsets.
    public void solve(int idx, int[] nums, List<Integer> list, HashSet<List<Integer>> ans) {
        // Base Case: Jab hum array ke end tak pahunch jaate hain.
        if (idx == nums.length) {
            // Current 'list' ek complete subset ko represent karta hai.
            // Iski ek copy HashSet mein add karo. HashSet automatically duplicates ko filter kar dega.
            ans.add(new ArrayList<>(list));
            return; // Is path se wapas chale jao.
        }

        // Option 1: nums[idx] ko current subset mein shamil karo (Include).
        list.add(nums[idx]);
        solve(idx + 1, nums, list, ans); // Agle element ke liye recursive call.

        // Backtrack: Pichli recursive call se wapas aane ke baad,
        // nums[idx] ko list se remove karo takay 'exclude' option explore kar sakein.
        list.remove(list.size() - 1);

        // Option 2: nums[idx] ko current subset mein shamil mat karo (Exclude).
        solve(idx + 1, nums, list, ans); // Agle element ke liye recursive call.
    }
}
```

-----

# ⭐ Palindrome Partitioning

Aapko ek string `s` di gayi hai. `s` ko is tarah se partition karo ki partition ka **har substring** ek **palindrome** ho. `s` ke **sabhi possible palindrome partitioning** ko return karo.

### 📌 Example

#### Input:

```
s = "aab"
```

#### Output:

```
[["a", "a", "b"], ["aa", "b"]]
```

### Intuition (Samajh):

Yeh problem bhi **backtracking** ka ek perfect use case hai. Humein ek string `s` di gayi hai aur humein ise substrings mein split karna hai. Condition yeh hai ki har split kiya gaya substring ek palindrome hona chahiye. Humein saare aise tareeke dhundhne hain.

Har step par, hum current position `idx` se shuru karte hain aur string `s` ke end tak har possible substring `sub` ko consider karte hain.

  * Agar `sub` ek palindrome hai, toh hum use apne current partition mein shamil karte hain aur baki string (`s` ka remaining part `sub` ke baad) ke liye recursively solve karte hain.
  * Agar `sub` palindrome nahi hai, toh hum use skip kar dete hain.

Base case: Jab hum string ke end tak pahunch jate hain (yaani, `idx == s.length()`), toh iska matlab hai ki humne string ko successfully palindrome substrings mein partition kar diya hai. Current partition ko result list mein add kar do.

-----

### Approach (Tareeka): Backtracking

1.  **Result List Initialize Karo**: `List<List<String>> ans` final partitions ke liye.
2.  **Current Partition List Initialize Karo**: `List<String> list` current partition ko store karegi.
3.  **Recursive Function `solve`**:
      * Parameters: `idx` (current starting index for substring), `s` (original string), `list` (current partition), `ans` (final result list).
      * **Base Case**:
          * `if (idx == s.length())`: Agar `idx` string ke length ke barabar ho gaya, iska matlab poori string partition ho gayi hai.
          * `ans.add(new ArrayList<>(list))`: `list` ki ek copy `ans` mein add karo aur return.
      * **Explore Substrings**: `for (int i = idx; i < s.length(); i++)` loop chalao.
          * `String sub = s.substring(idx, i + 1)`: `idx` se `i` tak ka substring extract karo.
          * **Check Palindrome**: `if (isPalindrome(sub))` call karo.
              * Agar `sub` palindrome hai:
                  * `list.add(sub)`: `sub` ko current partition mein add karo.
                  * `solve(i + 1, s, list, ans)`: `i + 1` se recursion call karo (kyunki humne `sub` ko `idx` se `i` tak use kar liya hai).
                  * `list.remove(list.size() - 1)`: **Backtrack\!** `sub` ko `list` se remove karo, agle iteration ke liye.
4.  **Helper Function `isPalindrome`**:
      * Parameters: `str` (string to check).
      * Two pointers `left` and `right` use karo. `left` ko start se aur `right` ko end se move karo.
      * `while (left <= right)`: Agar `str.charAt(left) != str.charAt(right)` toh `false` return karo. Otherwise, `left++` aur `right--`.
      * Agar loop complete ho jaye, toh `true` return karo.
5.  **Main Function**: `partition` se `solve` function ko `(0, s, new ArrayList<>(), ans)` ke saath call karo.

-----

### Java Code with Comments:

```java
import java.util.*;

public class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>(); // Saare valid palindrome partitions store karne ke liye list.
        List<String> list = new ArrayList<>();    // Current partition jo hum bana rahe hain.
        
        // Recursive helper method ko call karte hain.
        // Shuruat index 0 se (string ke beginning se), empty current partition list ke saath.
        solve(0, s, list, ans);
        return ans;
    }

    // Backtracking helper method to generate palindrome partitions
    // idx: Current starting index in the string 's' jahan se hum naye substring ko consider karenge.
    // s: Original input string.
    // list: Current list of palindrome substrings jo is path mein bane hain.
    // ans: Final list of all valid partitions.
    public void solve(int idx, String s, List<String> list, List<List<String>> ans) {
        // Base Case: Agar 'idx' string ki length ke barabar ho gaya hai,
        // iska matlab humne poori string ko successfuly partition kar diya hai.
        if (idx == s.length()) {
            // Current 'list' ek valid partition hai.
            // Iski ek copy bana kar final answer list mein add karo.
            ans.add(new ArrayList<>(list));
            return; // Is path se wapas chale jao.
        }

        // Loop: 'idx' se shuru karte hue string ke end tak har possible substring ko consider karo.
        for (int i = idx; i < s.length(); i++) {
            // Substring ko extract karo: 'idx' se 'i' tak (inclusive).
            String sub = s.substring(idx, i + 1);

            // Check karo ki kya yeh 'sub' palindrome hai.
            if (isPalindrome(sub)) {
                // Agar 'sub' palindrome hai, toh use current partition mein shamil karo.
                list.add(sub);
                // Recursive call: Ab next partition 'i + 1' index se shuru hoga.
                solve(i + 1, s, list, ans);
                // Backtrack: Pichli recursive call se wapas aane ke baad,
                // 'sub' ko current list se remove karo takay loop doosre options explore kar sake.
                list.remove(list.size() - 1);
            }
        }
    }

    // Utility method to check if a string is a palindrome
    // s: String jise check karna hai.
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1; // Two pointers, ek start se, ek end se.
        while (left <= right) {
            // Agar characters match nahi karte, toh palindrome nahi hai.
            if (s.charAt(left) != s.charAt(right))
                return false;
            // Pointers ko andar ki taraf move karo.
            left++;
            right--;
        }
        return true; // Agar loop complete ho gaya, toh palindrome hai.
    }
}
```

-----

# ⭐ N-Queens

N-queens puzzle ek problem hai jahan **n** queens ko **n x n** chessboard par is tarah se place karna hai takay koi bhi do queens ek doosre par attack na karein.

Aapko ek integer `n` diya gaya hai. N-queens puzzle ke **sabhi distinct solutions** return karein.

Har solution mein n-queens ke placement ka ek distinct board configuration hota hai, jahan `'Q'` queen ko aur `'.'` empty space ko indicate karta hai.

### 📌 Example

#### Input:

```
n = 4
```

#### Output:

```
[
  [".Q..","...Q","Q...","..Q."],
  ["..Q.","Q...","...Q",".Q.."]
]
```

**Explanation:**
4-queens puzzle ke do distinct solutions hain jaisa upar dikhaya gaya hai.

-----

### Intuition (Samajh):

N-Queens problem ek classic **backtracking** problem hai. Humein `n` queens ko `n x n` board par is tarah rakhna hai ki koi bhi do queens attack na karein. Queens ek doosre ko **horizontally, vertically, ya diagonally** attack karti hain.

Hum is problem ko **row by row** approach se solve kar sakte hain. Hum har row mein ek queen rakhne ki koshish karenge.

  * **Base Case**: Agar hum `n` queens ko successfully place kar paate hain (yaani, `row == n` ho jata hai), toh humein ek valid solution mil gaya hai. Is board configuration ko apne result list mein add kar do.
  * **Recursive Step**: Har row `r` ke liye, hum har column `c` mein queen rakhne ki koshish karenge.
      * Queen rakhne se pehle, hum `isSafe` function se check karenge ki kya (`r`, `c`) position par queen rakhna safe hai (yaani, koi doosri queen is position ko attack nahi kar rahi).
      * Agar safe hai:
          * Board par `board[r][c] = 'Q'` set karo.
          * Agli row (`r + 1`) ke liye recursively `placeQueens` call karo.
          * **Backtrack**: Recursive call return hone ke baad, `board[r][c] = '.'` set karo (queen ko hata do), takay hum is row mein doosre column options explore kar sakein.

-----

### Approach (Tareeka): Backtracking with Safety Checks

1.  **Initialize Board**: `char[][] board` banayein `n x n` size ka aur use `'.'` se fill karein.
2.  **Result List**: `List<List<String>> ans` banao saare valid solutions store karne ke liye.
3.  **Recursive Function `placeQueens`**:
      * Parameters: `board` (current chessboard state), `row` (current row jismein queen rakhni hai), `ans` (final result list).
      * **Base Case**:
          * `if (row == board.length)`: Agar `row` `n` ke barabar ho gaya hai, iska matlab saari `n` queens placed ho gayi hain.
          * `ans.add(constructBoard(board))`: `board` ke current configuration ko `List<String>` format mein convert karke `ans` mein add karo.
          * `return`: Wapas chale jao.
      * **Iterate Columns**: `for (int col = 0; col < board.length; col++)` loop chalao.
          * **Check Safety**: `if (isSafe(board, row, col))` call karo.
              * Agar safe hai:
                  * `board[row][col] = 'Q'`: Queen ko current position par rakho.
                  * `placeQueens(board, row + 1, ans)`: Agli row ke liye recursive call karo.
                  * `board[row][col] = '.'`: **Backtrack\!** Queen ko hata do takay doosre columns ko explore kar sakein.
4.  **Helper Function `constructBoard`**:
      * Parameters: `board` (current `char[][]`).
      * `char[][]` ko `List<String>` mein convert karta hai. Har row array ko `String` mein badal kar list mein add karta hai.
5.  **Helper Function `isSafe`**:
      * Parameters: `board`, `row`, `col` (position jahan queen rakhni hai).
      * **Checks**:
          * **Vertical Upwards**: Check karo current column mein current `row` se upar koi queen hai kya.
          * **Upper-Left Diagonal**: Check karo upper-left diagonal mein koi queen hai kya.
          * **Upper-Right Diagonal**: Check karo upper-right diagonal mein koi queen hai kya.
      * **Important**: Hum sirf upar ki taraf (rows `0` se `row-1` tak) check karte hain kyunki current `row` se neeche abhi tak koi queen rakhi hi nahi gayi hai.
      * Agar koi conflicting queen nahi milti, `true` return karo, varna `false`.
6.  **Main Function**: `solveNQueens` se `placeQueens` function ko `(board, 0, ans)` ke saath call karo.

-----

### Java Code with Comments:

```java
import java.util.*;

public class Solution {
    public List<List<String>> solveNQueens(int n) {
        // Step 1: Chessboard ko initialize karo '.' (empty space) se.
        // Yeh ek 2D character array hai jo board ko represent karega.
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.'); // Har row ko '.' se fill karo.
        }
        
        List<List<String>> ans = new ArrayList<>(); // Sabhi valid solutions store karne ke liye list.
        
        // Recursive function call. Queens ko row 0 se rakhna shuru karo.
        placeQueens(board, 0, ans);
        return ans;
    }

    /**
     * Recursive function jo queens ko row by row board par place karti hai.
     * Yeh backtracking ka core hai.
     *
     * @param board Current state of the chessboard.
     * @param row   Current row jahan hum queen rakhne ki koshish kar rahe hain.
     * @param ans   Final list of solutions.
     */
    private void placeQueens(char[][] board, int row, List<List<String>> ans) {
        // Base Case: Agar humari current 'row' board ki total rows (n) ke barabar ho gayi hai.
        // Iska matlab humne saari 'n' queens ko successfully place kar diya hai.
        if (row == board.length) {
            // Board ke current configuration ko List<String> format mein convert karo
            // aur final answer list mein add karo.
            ans.add(constructBoard(board));
            return; // Is path se wapas chale jao.
        }

        // Loop: Current row 'row' ke har column ko explore karo.
        for (int col = 0; col < board.length; col++) {
            // Check karo ki kya current position (row, col) par queen rakhna safe hai.
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';       // Queen ko current position par rakho.
                // Recursive call: Agle row (row + 1) ke liye queens rakhne ki koshish karo.
                placeQueens(board, row + 1, ans);
                board[row][col] = '.';       // Backtrack: Pichli recursive call se wapas aane ke baad,
                                             // queen ko hata do takay hum is row mein doosre columns explore kar sakein.
            }
        }
    }

    /**
     * Board ke char[][] configuration ko List<String> format mein convert karta hai.
     * Har row char array ko ek String mein convert karta hai.
     *
     * @param board The solved chessboard in char[][].
     * @return A List of Strings representing the board.
     */
    private List<String> constructBoard(char[][] board) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            res.add(new String(board[i])); // Har char array row ko String mein convert karke add karo.
        }
        return res;
    }

    /**
     * Check karta hai ki kya (row, col) position par queen rakhna safe hai.
     * Safety check sirf upar ki rows mein hota hai, kyunki current row ke neeche abhi tak koi queen nahi rakhi hai.
     *
     * @param board Current state of the board.
     * @param row   Row index to check.
     * @param col   Column index to check.
     * @return True agar safe hai, varna false.
     */
    private boolean isSafe(char[][] board, int row, int col) {
        // 1. Check Vertical Upwards: Current column mein upar ki taraf koi queen hai kya.
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // 2. Check Upper-Left Diagonal: Upar-left diagonal mein koi queen hai kya.
        // 'i' row ko ghtata hai, 'j' col ko ghtata hai.
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // 3. Check Upper-Right Diagonal: Upar-right diagonal mein koi queen hai kya.
        // 'i' row ko ghtata hai, 'j' col ko badhata hai.
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true; // Agar koi conflict nahi mila, toh queen rakhna safe hai.
    }
}
```
