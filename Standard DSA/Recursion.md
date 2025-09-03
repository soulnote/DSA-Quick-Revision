# Recursion Kya Hai? 🤔

**Recursion** ek programming technique hai jahan ek function apne aap ko call karta hai. Yeh tab tak chalta hai jab tak ek specific condition (jise **base case** kehte hain) poori nahi ho jaati. Agar base case na ho, to function **infinite loop** mein chala jayega, jisse **Stack Overflow Error** aayega.

Har recursive function mein do parts hote hain:

1.  **Base Case:** Yeh condition function ko rokne ka kaam karti hai. Iske bina recursion khatam nahi hoga.
2.  **Recursive Step:** Yeh woh step hai jahan function apne aap ko call karta hai, lekin ek smaller ya simpler input ke saath.

Yeh technique 'Divide and Conquer' (baanto aur jeeto) principle par based hai.

-----

### Basic Recursion Problems

#### Problem 1: Factorial Calculation

**Goal:** Ek number ka factorial nikaalna. Factorial of `n` is `1 * 2 * 3 * ... * n`.
**Example:** `factorial(5) = 5 * 4 * 3 * 2 * 1 = 120`.

```java
// Java Solution
class Factorial {
    public static int factorial(int n) {
        // Base Case: jab n 0 ho to 1 return karo
        if (n == 0) {
            return 1;
        }
        // Recursive Step: n * (n-1) ka factorial
        return n * factorial(n - 1);
    }
    public static void main(String[] args) {
        System.out.println(factorial(5)); // Output: 120
    }
}
```

**Explanation:** `factorial(5)` calls `factorial(4)`, jo `factorial(3)` ko call karta hai, aur yeh silsila `factorial(0)` tak chalta hai. Jab `n` 0 hota hai, to `1` return hota hai, aur phir saari calls `5 * 4 * 3 * 2 * 1` ke roop mein calculate hokar final result deti hain.

-----

#### Problem 2: Fibonacci Sequence

**Goal:** Fibonacci series ka `n`-th term find karna. Fibonacci series: `0, 1, 1, 2, 3, 5, 8, ...`
**Formula:** `fib(n) = fib(n-1) + fib(n-2)`.

```java
// Java Solution
class Fibonacci {
    public static int fib(int n) {
        // Base Case: 0 aur 1 ke liye result fixed hai
        if (n <= 1) {
            return n;
        }
        // Recursive Step: pichle do terms ka sum
        return fib(n - 1) + fib(n - 2);
    }
    public static void main(String[] args) {
        System.out.println(fib(6)); // Output: 8
    }
}
```

**Explanation:** `fib(6)` calculate karne ke liye, `fib(5)` aur `fib(4)` calculate karna padega. Har ek call further divide hoti hai jab tak `n` 0 ya 1 na ho jaye.

-----

#### Problem 3: Sum of Digits of a Number

**Goal:** Ek integer ke saare digits ka sum nikaalna.
**Example:** `sumOfDigits(123) = 1 + 2 + 3 = 6`.

```java
// Java Solution
class SumOfDigits {
    public static int sumOfDigits(int n) {
        // Base Case: jab number single digit ho
        if (n < 10) {
            return n;
        }
        // Recursive Step: last digit + remaining digits ka sum
        return (n % 10) + sumOfDigits(n / 10);
    }
    public static void main(String[] args) {
        System.out.println(sumOfDigits(123)); // Output: 6
    }
}
```

**Explanation:** `sumOfDigits(123)` mein, `3` (`123 % 10`) ko `sumOfDigits(12)` ke result mein add kiya jaata hai. Aise hi, `sumOfDigits(12)` mein `2` ko `sumOfDigits(1)` ke result mein add kiya jaata hai. `sumOfDigits(1)` ka base case hit hota hai aur `1` return hota hai.

-----

### Array and String Recursion Problems

#### Problem 4: Power of a Number

**Goal:** `x` raise to the power of `n` calculate karna.
**Example:** `power(2, 3) = 2 * 2 * 2 = 8`.

```java
// Java Solution
class Power {
    public static int power(int x, int n) {
        // Base Case: x^0 = 1
        if (n == 0) {
            return 1;
        }
        // Recursive Step: x * x^(n-1)
        return x * power(x, n - 1);
    }
    public static void main(String[] args) {
        System.out.println(power(2, 3)); // Output: 8
    }
}
```

**Explanation:** `power(2, 3)` call hoti hai, jo `2 * power(2, 2)` return karti hai. Aise hi `power(2, 2)` calls `2 * power(2, 1)` and so on, jab tak `n` 0 na ho jaye.

-----

#### Problem 5: Print an Array

**Goal:** Ek array ke elements ko recursively print karna.
**Example:** `printArray({1, 2, 3})` -\> `1 2 3`.

```java
// Java Solution
class PrintArray {
    public static void printArray(int[] arr, int index) {
        // Base Case: jab index array ke size ke barabar ho
        if (index >= arr.length) {
            return;
        }
        // Print current element
        System.out.print(arr[index] + " ");
        // Recursive Step: next element ko print karo
        printArray(arr, index + 1);
    }
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        printArray(arr, 0); // Output: 1 2 3
    }
}
```

**Explanation:** `printArray(arr, 0)` se shuru hota hai. Har call mein `index` ko increment kiya jaata hai, aur next element print hota hai, jab tak `index` array ki length se zyada na ho jaye.

-----

#### Problem 6: Reverse a String

**Goal:** Ek string ko recursively reverse karna.
**Example:** `reverseString("hello")` -\> `"olleh"`.

```java
// Java Solution
class ReverseString {
    public static String reverseString(String s) {
        // Base Case: empty ya single character string
        if (s.isEmpty() || s.length() == 1) {
            return s;
        }
        // Recursive Step: last character + baaki string ka reverse
        return s.charAt(s.length() - 1) + reverseString(s.substring(0, s.length() - 1));
    }
    public static void main(String[] args) {
        System.out.println(reverseString("hello")); // Output: olleh
    }
}
```

**Explanation:** `reverseString("hello")` `o` ko return karta hai aur `reverseString("hell")` ko call karta hai. Aise hi, yeh `l`, `l`, `e`, aur `h` ko jodta hai.

-----

### Advanced Recursion Problems

#### Problem 7: Palindrome Check

**Goal:** Check karna ki ek string palindrome hai ya nahi (yaani aage aur peeche se same hai).
**Example:** `"racecar"` is a palindrome.

```java
// Java Solution
class Palindrome {
    public static boolean isPalindrome(String s) {
        // Base Case: empty ya single character string palindrome hai
        if (s.length() <= 1) {
            return true;
        }
        // Pehla aur last character compare karo
        if (s.charAt(0) != s.charAt(s.length() - 1)) {
            return false;
        }
        // Recursive Step: beech wali string ko check karo
        return isPalindrome(s.substring(1, s.length() - 1));
    }
    public static void main(String[] args) {
        System.out.println(isPalindrome("racecar")); // Output: true
        System.out.println(isPalindrome("hello")); // Output: false
    }
}
```

**Explanation:** Har call mein, function pehla aur last character compare karta hai aur phir string ke beech ke hisse ko check karta hai.

-----

#### Problem 8: Tower of Hanoi

**Goal:** `n` disks ko ek rod se dusri rod par transfer karna, rules ke saath.
**Rules:**

1.  Ek baar mein ek hi disk move ho sakta hai.
2.  Bade disk par chhota disk nahi rakha ja sakta.
3.  Teesri rod temporary use ho sakti hai.
    **Logic:**
4.  `n-1` disks ko A se B par move karo, C ka use karke.
5.  `n`-th disk ko A se C par move karo.
6.  `n-1` disks ko B se C par move karo, A ka use karke.

<!-- end list -->

```java
// Java Solution
class TowerOfHanoi {
    public static void towerOfHanoi(int n, char from, char to, char aux) {
        // Base Case: ek bhi disk na ho
        if (n == 0) {
            return;
        }
        // n-1 disks ko from se aux par move karo
        towerOfHanoi(n - 1, from, aux, to);
        // last disk ko from se to par move karo
        System.out.println("Move disk " + n + " from " + from + " to " + to);
        // n-1 disks ko aux se to par move karo
        towerOfHanoi(n - 1, aux, to, from);
    }
    public static void main(String[] args) {
        towerOfHanoi(3, 'A', 'C', 'B');
    }
}
```

**Explanation:** Yeh ek classic example hai jo recursive thinking ko demonstrate karta hai. Hum badi problem (n disks) ko choti problems (n-1 disks) mein todte hain.

-----

#### Problem 9: Permutations of a String

**Goal:** Ek string ke saare possible permutations (saare possible arrangements) print karna.
**Example:** `"abc"` ke permutations `"abc", "acb", "bac", "bca", "cab", "cba"` hain.

```java
// Java Solution
class Permutations {
    public static void printPermutations(String str, String ans) {
        // Base Case: jab string empty ho
        if (str.length() == 0) {
            System.out.println(ans);
            return;
        }
        // String ke har character ko pehle position par rakh ke permutation banao
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String remStr = str.substring(0, i) + str.substring(i + 1);
            printPermutations(remStr, ans + ch);
        }
    }
    public static void main(String[] args) {
        printPermutations("abc", "");
    }
}
```

**Explanation:** Ismein, hum har character ko pehle position par fix karte hain aur baaki ke characters ke permutations recursively nikaalte hain.

-----

### More Advanced Problems

#### Problem 10: N-Queens Problem

**Goal:** `N x N` chessboard par `N` queens ko aise place karna ki koi bhi queen ek dusre ko attack na kare (same row, column, ya diagonal par na ho).

```java
// Java Solution
class NQueens {
    public static void solveNQueens(int n) {
        int[][] board = new int[n][n];
        solveNQueensUtil(board, 0, n);
    }
    private static void solveNQueensUtil(int[][] board, int col, int n) {
        if (col >= n) { // Base case: saari queens place ho gayi
            printSolution(board, n);
            return;
        }
        for (int row = 0; row < n; row++) {
            if (isSafe(board, row, col, n)) {
                board[row][col] = 1; // Queen place karo
                solveNQueensUtil(board, col + 1, n);
                board[row][col] = 0; // Backtrack
            }
        }
    }
    private static boolean isSafe(int[][] board, int row, int col, int n) {
        // Check row and diagonals
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 1) return false;
        }
        return true;
    }
    private static void printSolution(int[][] board, int n) {
        // Code to print the board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {
        solveNQueens(4);
    }
}
```

**Explanation:** Yeh ek **Backtracking** problem hai, jo recursion ka ek sub-type hai. Har column mein queen place karne ki koshish ki jaati hai. Agar placement safe hai, to next column mein jate hain. Agar nahi, to backtrack (pichle state par waapis aana) karte hain aur dusri position try karte hain.

-----

#### Problem 11: Binary Search

**Goal:** Sorted array mein ek element ko recursively search karna.
**Logic:**

1.  Array ke middle element ko check karo.
2.  Agar middle element target hai, to mil gaya.
3.  Agar target middle se chhota hai, to left half mein search karo.
4.  Agar target middle se bada hai, to right half mein search karo.

<!-- end list -->

```java
// Java Solution
class BinarySearch {
    public static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: element nahi mila
        }
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) {
            return mid; // Base case: element mil gaya
        }
        if (arr[mid] > target) {
            return binarySearch(arr, target, left, mid - 1); // Left half
        }
        return binarySearch(arr, target, mid + 1, right); // Right half
    }
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(binarySearch(arr, 5, 0, arr.length - 1)); // Output: 4 (index)
    }
}
```

**Explanation:** Binary search mein har step par search space ko half kar diya jata hai. Yeh ek efficient approach hai.

-----

#### Problem 12: Merge Sort

**Goal:** Ek array ko recursively sort karna.
**Logic:**

1.  Array ko do halves mein divide karo.
2.  Har half ko recursively sort karo.
3.  Sorted halves ko merge karo.

<!-- end list -->

```java
// Java Solution
class MergeSort {
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }
    private static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
    private static void merge(int[] arr, int l, int m, int r) {
        // Merging logic... (yeh non-recursive part hai)
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++; k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++; k++;
        }
    }
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        mergeSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        } // Output: 5 6 7 11 12 13
    }
}
```

**Explanation:** Yeh ek classic **Divide and Conquer** algorithm hai. Isme array ko tab tak divide kiya jata hai jab tak single element na reh jaye, aur phir unhe sorted order mein merge kiya jata hai.

-----

#### Problem 13: Subsets of a String

**Goal:** Ek string ke saare subsets (ya subsequences) print karna.
**Example:** `"abc"` ke subsets `"", "a", "b", "c", "ab", "ac", "bc", "abc"` hain.

```java
// Java Solution
class Subsets {
    public static void findSubsets(String str, String currentSubset, int index) {
        // Base Case: jab string khatam ho jaye
        if (index == str.length()) {
            System.out.println(currentSubset);
            return;
        }
        // Case 1: current character ko subset mein include karo
        findSubsets(str, currentSubset + str.charAt(index), index + 1);
        // Case 2: current character ko subset mein include mat karo
        findSubsets(str, currentSubset, index + 1);
    }
    public static void main(String[] args) {
        findSubsets("abc", "", 0);
    }
}
```

**Explanation:** Har character ke liye do possibilities hain: use subset mein include karna ya na karna. `findSubsets` function dono possibilities ke liye recursive calls karta hai.

-----

#### Problem 14: Palindrome Partitioning

**Goal:** Ek string ko substrings mein partition karna, jahan har substring ek palindrome ho.
**Example:** `"aab"` ke partitions `{"a", "a", "b"}` aur `{"aa", "b"}` hain.

```java
// Java Solution
import java.util.ArrayList;
import java.util.List;

class PalindromePartitioning {
    public static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), s, 0);
        return result;
    }
    private static void backtrack(List<List<String>> result, List<String> temp, String s, int start) {
        if (start == s.length()) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < s.length(); i++) {
            String substring = s.substring(start, i + 1);
            if (isPalindrome(substring)) {
                temp.add(substring);
                backtrack(result, temp, s, i + 1);
                temp.remove(temp.size() - 1); // Backtrack
            }
        }
    }
    private static boolean isPalindrome(String s) {
        // Implementation similar to Problem 7, but iterative is simpler here
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(partition("aab"));
    }
}
```

**Explanation:** Yeh bhi ek **backtracking** problem hai. Hum string ke saare prefixes ko check karte hain. Agar ek prefix palindrome hai, to use current partition mein add karte hain aur baaki string par recursively call karte hain.

-----

#### Problem 15: Subset Sum Problem

**Goal:** Check karna ki kya ek array ke kisi subset ka sum ek given value ke barabar hai ya nahi.

```java
// Java Solution
class SubsetSum {
    public static boolean isSubsetSum(int[] arr, int n, int sum) {
        // Base Case: agar sum 0 ho gaya, to true
        if (sum == 0) {
            return true;
        }
        // Base Case: agar array khatam ho gaya aur sum abhi bhi bacha hai, to false
        if (n == 0) {
            return false;
        }
        // Recursive Step: do cases hain
        // 1. Last element ko include na karein
        // 2. Last element ko include karein, agar woh sum se chhota hai
        return isSubsetSum(arr, n - 1, sum) || (arr[n - 1] <= sum && isSubsetSum(arr, n - 1, sum - arr[n - 1]));
    }
    public static void main(String[] args) {
        int[] arr = {3, 34, 4, 12, 5, 2};
        int sum = 9;
        System.out.println(isSubsetSum(arr, arr.length, sum)); // Output: true (4 + 5 = 9)
    }
}
```
# Array-based more Recursion Problems

#### Problem 1: Find Max Element in Array

**Goal:** Ek array mein sabse bada element recursively find karna.
**Logic:** `max(arr, n)` ko `arr[n-1]` aur `max(arr, n-1)` ke beech compare karke find kiya ja sakta hai.

```java
// Java Solution
public int findMax(int[] arr, int n) {
    if (n == 1) {
        return arr[0];
    }
    return Math.max(arr[n-1], findMax(arr, n-1));
}
```

#### Problem 2: Check if Array is Sorted

**Goal:** Check karna ki ek array recursively sorted hai ya nahi.
**Logic:** `isSorted(arr, n)` tabhi `true` hoga agar `arr[n-1] >= arr[n-2]` ho aur `isSorted(arr, n-1)` bhi `true` ho.

```java
// Java Solution
public boolean isSorted(int[] arr, int n) {
    if (n == 1 || n == 0) {
        return true;
    }
    return arr[n-1] >= arr[n-2] && isSorted(arr, n-1);
}
```

#### Problem 3: Linear Search

**Goal:** Ek array mein ek element ko recursively search karna.
**Logic:** `search(arr, n, key)` `arr[n-1] == key` check karega. Agar `true` hai to `n-1` return, warna `search(arr, n-1, key)`.

```java
// Java Solution
public int linearSearch(int[] arr, int n, int key) {
    if (n == 0) {
        return -1;
    }
    if (arr[n-1] == key) {
        return n-1;
    }
    return linearSearch(arr, n-1, key);
}
```

-----

### String-based Recursion Problems

#### Problem 4: Print All Subsequences

**Goal:** Ek string ke saare subsequences print karna. (Subsequences non-contiguous bhi ho sakte hain).
**Logic:** Har character ke liye do choice hai - use include karein ya na karein.

```java
// Java Solution
public void printSubsequences(String str, String ans) {
    if (str.length() == 0) {
        System.out.println(ans);
        return;
    }
    char ch = str.charAt(0);
    String rem = str.substring(1);
    printSubsequences(rem, ans + ch); // Include
    printSubsequences(rem, ans);      // Exclude
}
```

#### Problem 5: Remove All 'a's from a String

**Goal:** Ek string se saare 'a' characters recursively remove karna.
**Logic:** Agar current character 'a' hai, to usse skip karo. Warna use answer mein jod do.

```java
// Java Solution
public String removeA(String str) {
    if (str.length() == 0) {
        return "";
    }
    char ch = str.charAt(0);
    String ans = removeA(str.substring(1));
    if (ch == 'a') {
        return ans;
    } else {
        return ch + ans;
    }
}
```

#### Problem 6: Print all Permutations of a String with duplicates

**Goal:** Permutations print karna, jisme duplicate characters ho sakte hain.
**Logic:** Set ka use karke duplicate calls ko avoid kiya ja sakta hai.

```java
// Java Solution
import java.util.HashSet;
import java.util.Set;

public void printPermutationsWithDup(String str, String ans) {
    if (str.length() == 0) {
        System.out.println(ans);
        return;
    }
    Set<Character> used = new HashSet<>();
    for (int i = 0; i < str.length(); i++) {
        char ch = str.charAt(i);
        if (used.contains(ch)) {
            continue;
        }
        used.add(ch);
        String rem = str.substring(0, i) + str.substring(i + 1);
        printPermutationsWithDup(rem, ans + ch);
    }
}
```

-----

### Number-based Recursion Problems

#### Problem 7: Check if a Number is Prime

**Goal:** Recursively check karna ki ek number prime hai ya nahi.
**Logic:** `isPrime(n, i)` check karta hai ki `n` `i` se `n/2` tak divisible hai ya nahi.

```java
// Java Solution
public boolean isPrime(int n, int i) {
    if (n <= 2) {
        return (n == 2);
    }
    if (n % i == 0) {
        return false;
    }
    if (i * i > n) {
        return true;
    }
    return isPrime(n, i + 1);
}
```

#### Problem 8: Sum of Natural Numbers

**Goal:** `1` se `n` tak ke numbers ka sum recursively nikalna.
**Logic:** `sum(n)` ko `n + sum(n-1)` se represent kiya ja sakta hai.

```java
// Java Solution
public int sumOfN(int n) {
    if (n == 1) {
        return 1;
    }
    return n + sumOfN(n-1);
}
```

-----

### Array and Number Mix Problems

#### Problem 9: Check if a number exists in an array

**Goal:** Ek number array mein hai ya nahi, recursively check karna.

```java
// Java Solution
public boolean isPresent(int[] arr, int n, int target) {
    if (n == 0) {
        return false;
    }
    if (arr[n-1] == target) {
        return true;
    }
    return isPresent(arr, n-1, target);
}
```

#### Problem 10: Count occurrences of a number in an array

**Goal:** Ek number array mein kitni baar aata hai, recursively count karna.

```java
// Java Solution
public int countOccurrences(int[] arr, int n, int target) {
    if (n == 0) {
        return 0;
    }
    int count = countOccurrences(arr, n-1, target);
    if (arr[n-1] == target) {
        return count + 1;
    } else {
        return count;
    }
}
```

-----

### Tree-based Recursion Problems

Trees, Binary Trees, aur Graphs mein recursion kaafi zaroori hota hai.

#### Problem 11: Inorder Traversal of a Binary Tree

**Goal:** Binary Tree ko Inorder (Left -\> Root -\> Right) traverse karna.
**Logic:** Left subtree par call, phir root node ko process karo, phir right subtree par call.

```java
// Java Solution
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
}
public void inorder(TreeNode root) {
    if (root == null) {
        return;
    }
    inorder(root.left);
    System.out.print(root.val + " ");
    inorder(root.right);
}
```

#### Problem 12: Preorder Traversal

**Goal:** Binary Tree ko Preorder (Root -\> Left -\> Right) traverse karna.
**Logic:** Root node ko process karo, phir left subtree par, phir right subtree par.

```java
// Java Solution
public void preorder(TreeNode root) {
    if (root == null) {
        return;
    }
    System.out.print(root.val + " ");
    preorder(root.left);
    preorder(root.right);
}
```

#### Problem 13: Postorder Traversal

**Goal:** Binary Tree ko Postorder (Left -\> Right -\> Root) traverse karna.
**Logic:** Left subtree par call, phir right subtree par, phir root node ko process.

```java
// Java Solution
public void postorder(TreeNode root) {
    if (root == null) {
        return;
    }
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.val + " ");
}
```

-----

### More Complex Recursion Problems

#### Problem 14: Combinations Sum

**Goal:** Ek array se numbers ka combination find karna jinka sum ek target value ho.

```java
// Java Solution
public void combinationSum(int[] arr, int target, List<Integer> current, List<List<Integer>> result) {
    if (target < 0) return;
    if (target == 0) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] <= target) {
            current.add(arr[i]);
            combinationSum(arr, target - arr[i], current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
```

#### Problem 15: Generate Parentheses

**Goal:** `n` pairs of parentheses ke saare valid combinations generate karna.
**Example:** `n=3` ke liye `((()))`, `(()())`, `(())()`, `()(())`, `()()()`.

```java
// Java Solution
public void generateParentheses(int n, String ans, int open, int close, List<String> result) {
    if (open == n && close == n) {
        result.add(ans);
        return;
    }
    if (open < n) {
        generateParentheses(n, ans + "(", open + 1, close, result);
    }
    if (close < open) {
        generateParentheses(n, ans + ")", open, close + 1, result);
    }
}
```

-----

### Backtracking Problems

#### Problem 16: Sudoku Solver

**Goal:** Ek incomplete Sudoku board ko solve karna.
**Logic:** `solve(board)` function har empty cell par `1-9` numbers try karta hai. Agar number valid hai, to next cell par recursive call, warna backtrack.

```java
// Java Solution
public boolean solveSudoku(int[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == 0) {
                for (int k = 1; k <= 9; k++) {
                    if (isValid(board, i, j, k)) {
                        board[i][j] = k;
                        if (solveSudoku(board)) {
                            return true;
                        } else {
                            board[i][j] = 0; // Backtrack
                        }
                    }
                }
                return false;
            }
        }
    }
    return true; // Board is full
}
```

#### Problem 17: M-Coloring Problem

**Goal:** Graph ke vertices ko `m` colors se color karna, taaki koi bhi do adjacent vertices ka same color na ho.
**Logic:** `colorGraph(graph, m, node, colorArr)` har node ko color karta hai. Agar current color valid nahi hai, to dusra color try karta hai.

```java
// Java Solution (partial, main logic)
public boolean graphColoring(int[][] graph, int m, int node, int[] color) {
    if (node == graph.length) {
        return true;
    }
    for (int c = 1; c <= m; c++) {
        if (isSafe(node, graph, color, c)) {
            color[node] = c;
            if (graphColoring(graph, m, node + 1, color)) {
                return true;
            }
            color[node] = 0; // Backtrack
        }
    }
    return false;
}
```

-----

### More Miscellaneous Problems

#### Problem 18: Combination (nCr)

**Goal:** `n` items mein se `r` items ko select karne ke kitne tareeke hain.
**Formula:** `C(n, r) = C(n-1, r-1) + C(n-1, r)`
**Base cases:** `C(n, 0) = 1` aur `C(n, n) = 1`.

```java
// Java Solution
public int combinations(int n, int r) {
    if (r == 0 || r == n) {
        return 1;
    }
    return combinations(n-1, r-1) + combinations(n-1, r);
}
```

#### Problem 19: Josephus Problem

**Goal:** Circular array mein `k`-th person ko remove karte hue last surviving person ko find karna.
**Logic:** `josephus(n, k)` ko `(josephus(n-1, k) + k) % n` se solve kiya ja sakta hai.

```java
// Java Solution
public int josephus(int n, int k) {
    if (n == 1) {
        return 1;
    }
    return (josephus(n-1, k) + k - 1) % n + 1;
}
```

#### Problem 20: Print All Paths in a Matrix

**Goal:** `(0, 0)` se `(R-1, C-1)` tak saare possible paths find karna.
**Logic:** `(i, j)` se ya to `(i+1, j)` (down) ya `(i, j+1)` (right) ja sakte hain.

```java
// Java Solution
public void printPaths(int[][] matrix, int r, int c, String path) {
    if (r == matrix.length - 1 && c == matrix[0].length - 1) {
        System.out.println(path);
        return;
    }
    if (r < matrix.length - 1) {
        printPaths(matrix, r + 1, c, path + "D");
    }
    if (c < matrix[0].length - 1) {
        printPaths(matrix, r, c + 1, path + "R");
    }
}
```

-----

### Even More Problems

#### Problem 21: `L.C.M` of two numbers using `G.C.D`

**Goal:** `L.C.M` find karna using `gcd(a, b)`. `lcm(a,b) = (a*b)/gcd(a,b)`.

```java
// Java Solution
public int gcd(int a, int b) {
    if (b == 0) return a;
    return gcd(b, a % b);
}
public int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}
```

#### Problem 22: Count Paths with Obstacles

**Goal:** `(0,0)` se `(R-1, C-1)` tak kitne paths hain obstacles ko avoid karte hue.
**Logic:** Similar to Problem 20, but obstacles (value `0`) ko skip karte hain.

```java
// Java Solution (partial)
public int countPathsWithObstacles(int[][] grid, int r, int c) {
    if (r >= grid.length || c >= grid[0].length || grid[r][c] == 0) {
        return 0;
    }
    if (r == grid.length - 1 && c == grid[0].length - 1) {
        return 1;
    }
    return countPathsWithObstacles(grid, r + 1, c) + countPathsWithObstacles(grid, r, c + 1);
}
```

#### Problem 23: Tiling Problem

**Goal:** `2 x N` board ko `2 x 1` ya `1 x 2` tiles se cover karne ke tarike find karna.
**Logic:** `T(n) = T(n-1) + T(n-2)`. Fibonacci sequence ki tarah.

```java
// Java Solution
public int tiling(int n) {
    if (n <= 2) {
        return n;
    }
    return tiling(n-1) + tiling(n-2);
}
```

#### Problem 24: Pow(x, n)

**Goal:** `x` raise to the power `n` recursively, but with `O(log n)` complexity.
**Logic:** `x^n = (x^2)^(n/2)` if `n` is even.

```java
// Java Solution
public double myPow(double x, int n) {
    if (n == 0) return 1;
    if (n < 0) {
        x = 1/x;
        n = -n;
    }
    if (n % 2 == 0) {
        return myPow(x*x, n/2);
    } else {
        return x * myPow(x*x, (n-1)/2);
    }
}
```

#### Problem 25: Flatten a Nested List Iterator

**Goal:** Nested list ke integers ko print karna.
**Logic:** List ke har element ko check karo. Agar integer hai to print karo, agar list hai to uspar recursive call karo.

```java
// Java Solution (Conceptual, not full class)
public void flatten(List<Object> list) {
    for (Object o : list) {
        if (o instanceof Integer) {
            System.out.println(o);
        } else if (o instanceof List) {
            flatten((List<Object>) o);
        }
    }
}
```

#### Problem 26: Word Break

**Goal:** String ko dictionary words ke space-separated sequence mein break karna.
**Logic:** Har prefix ko dictionary mein check karo. Agar valid hai to bachi hui string par recursive call karo.

```java
// Java Solution (partial)
public boolean wordBreak(String s, List<String> wordDict) {
    return wordBreakUtil(s, new HashSet<>(wordDict));
}
private boolean wordBreakUtil(String s, Set<String> dict) {
    if (s.isEmpty()) {
        return true;
    }
    for (int i = 1; i <= s.length(); i++) {
        String prefix = s.substring(0, i);
        if (dict.contains(prefix) && wordBreakUtil(s.substring(i), dict)) {
            return true;
        }
    }
    return false;
}
```

#### Problem 27: Gray Code

**Goal:** `n` bits ka Gray Code sequence generate karna.
**Logic:** `G(n) = G(n-1) + reverse(G(n-1))` (with prefix '1').

```java
// Java Solution (partial)
public List<Integer> grayCode(int n) {
    if (n == 0) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        return result;
    }
    List<Integer> prev = grayCode(n - 1);
    List<Integer> result = new ArrayList<>(prev);
    int add = 1 << (n - 1);
    for (int i = prev.size() - 1; i >= 0; i--) {
        result.add(prev.get(i) + add);
    }
    return result;
}
```

#### Problem 28: K-th Symbol in Grammar

**Goal:** Ek grammar `F(n)` mein `k`-th symbol find karna. `F(n) = F(n-1) + invert(F(n-1))`.
**Logic:** `k` ki position ko observe karte hue problem size ko half karte hain.

```java
// Java Solution
public int kthGrammar(int n, int k) {
    if (n == 1) {
        return 0;
    }
    int mid = (1 << (n-2));
    if (k <= mid) {
        return kthGrammar(n-1, k);
    } else {
        return 1 - kthGrammar(n-1, k-mid);
    }
}
```

#### Problem 29: Count Inversions

**Goal:** Array mein inversions count karna (jahan `i < j` aur `arr[i] > arr[j]`).
**Logic:** `Merge Sort` ke dauran count kiya jata hai.

```java
// Java Solution (Conceptual)
public int countInversions(int[] arr, int left, int right) {
    int inv_count = 0;
    if (left < right) {
        int mid = (left + right) / 2;
        inv_count += countInversions(arr, left, mid);
        inv_count += countInversions(arr, mid + 1, right);
        inv_count += mergeAndCount(arr, left, mid, right);
    }
    return inv_count;
}
```

#### Problem 30: Palindromic Partitioning II (Minimum Cuts)

**Goal:** Minimum cuts find karna, jisse string ko palindromic partitions mein divide kar sakein.
**Logic:** `minCut(s)` har possible cut point `i` par recursive call karta hai.

```java
// Java Solution (Conceptual)
public int minCut(String s) {
    return minCut(s, 0) - 1; // -1 for first cut
}
private int minCut(String s, int start) {
    if (start >= s.length() || isPalindrome(s.substring(start))) {
        return 1;
    }
    int min = Integer.MAX_VALUE;
    for (int i = start; i < s.length(); i++) {
        if (isPalindrome(s.substring(start, i + 1))) {
            min = Math.min(min, 1 + minCut(s, i + 1));
        }
    }
    return min;
}
```
