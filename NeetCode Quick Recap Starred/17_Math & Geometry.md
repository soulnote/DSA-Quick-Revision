
## 48\. Rotate Image

**Rotate Image** problem mein aapko ek square 2D matrix ko 90 degrees clockwise rotate karna hota hai.

-----

### Description/Overview

Aapko ek `n x n` 2D matrix `image` diya gaya hai jo ek image ko represent karta hai. Aapko image ko **90 degrees clockwise** rotate karna hai. Yeh rotation **in-place** honi chahiye, matlab aapko ek naya 2D matrix use nahi karna hai. Aapko existing matrix ko hi modify karna hai.

For example:

  * `image = [[1,2,3],[4,5,6],[7,8,9]]`
      * Output: `[[7,4,1],[8,5,2],[9,6,3]]`
      * Explanation:
          * 1st row becomes last column.
          * 2nd row becomes 2nd last column.
          * 3rd row becomes 1st column.
  * `image = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]`
      * Output: `[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]`

### Approach (How to do it)

Rotate image ko in-place karne ke do common steps hote hain:

1.  **Transpose the matrix:** Har element `image[i][j]` ko `image[j][i]` se swap karo. Yeh matrix ko uske main diagonal ke across flip kar deta hai.
2.  **Reverse each row:** Transposed matrix ki har row ko reverse kar do.

**Algorithm:**

1.  **Transpose:**
      * Matrix ke dimensions `n` ko get karo (`n = image.length`).
      * Nested loops use karo: `i` from `0` to `n-1`, `j` from `i` to `n-1`. (Note: `j` `i` se shuru hota hai taaki har pair (`i,j`) sirf ek baar swap ho).
      * `swap(image[i][j], image[j][i])`.
2.  **Reverse Rows:**
      * Loop `i` from `0` to `n-1` (har row ke liye).
      * Har row `image[i]` ko reverse karo. Yeh `two-pointer` approach se ho sakta hai:
          * `left = 0`, `right = n-1`.
          * Jab tak `left < right`: `swap(image[i][left], image[i][right])`, `left++`, `right--`.

### Solution (The Way to Solve)

Hum do-step process follow karte hain. Pehla step hai **matrix ko transpose karna**. Ismein, `image[row][col]` ko `image[col][row]` se swap karte hain. Hum sirf upper triangle ya lower triangle ko traverse karte hain (e.g., `j` outer loop `i` se shuru ho) taaki ek hi pair (e.g., `(1,2)` aur `(2,1)`) do baar swap na ho.
Dusra step hai **har row ko reverse karna**. Transpose karne ke baad, har row ko uski position par reverse kar dete hain. Example ke liye, `[7,4,1]` first row ban jayegi.
Yeh do operations combinedly 90 degree clockwise rotation achieve karte hain.

### Code

```java
class Solution {
    public void rotate(int[][] image) {
        int n = image.length;

        // Step 1: Transpose the matrix
        // Matrix ko transpose karo (elements ko main diagonal ke across swap karke)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) { // J ko i se start karo taaki elements do baar swap na ho
                int temp = image[i][j];
                image[i][j] = image[j][i];
                image[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        // Har row ko reverse kar do
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = image[i][left];
                image[i][left] = image[i][right];
                image[i][right] = temp;
                left++;
                right--;
            }
        }
    }
}
```

-----

## 59\. Spiral Matrix II

**Spiral Matrix II** problem mein aapko ek `n x n` matrix ko 1 se `n^2` tak numbers se fill karna hota hai spiral order mein.

-----

### Description/Overview

Aapko ek integer `n` diya gaya hai. Aapko ek `n x n` square matrix generate karna hai jo elements ko 1 se `n^2` tak **spiral order** mein fill kare.

For example:

  * `n = 3`
      * Output: `[[1,2,3],[8,9,4],[7,6,5]]`
  * `n = 1`
      * Output: `[[1]]`

### Approach (How to do it)

Spiral matrix generation ko simulate karne ke liye, hum boundary variables use karte hain jo current "layer" ko define karte hain jismein hum fill kar rahe hain. Har layer ke liye, hum char directions mein move karte hain: right, down, left, up.

**Algorithm:**

1.  **Initialize:**
      * `matrix = new int[n][n]`.
      * `rowStart = 0`, `rowEnd = n - 1`.
      * `colStart = 0`, `colEnd = n - 1`.
      * `num = 1` (number to fill).
2.  **Spiral Fill:** Loop `while (rowStart <= rowEnd && colStart <= colEnd)`:
      * **Fill Right:** `j` from `colStart` to `colEnd`: `matrix[rowStart][j] = num++`. `rowStart++`.
      * **Fill Down:** `i` from `rowStart` to `rowEnd`: `matrix[i][colEnd] = num++`. `colEnd--`.
      * **Fill Left (if applicable):** If `rowStart <= rowEnd`: `j` from `colEnd` to `colStart` (reverse): `matrix[rowEnd][j] = num++`. `rowEnd--`.
      * **Fill Up (if applicable):** If `colStart <= colEnd`: `i` from `rowEnd` to `rowStart` (reverse): `matrix[i][colStart] = num++`. `colStart++`.
3.  **Return:** `matrix`.

### Solution (The Way to Solve)

Hum ek `n x n` size ka 2D integer array `matrix` banate hain. Char variables (`rowStart`, `rowEnd`, `colStart`, `colEnd`) define karte hain jo current filling boundary ko represent karte hain, aur ek `num` variable jo 1 se start hota hai.
Jab tak `rowStart <= rowEnd` aur `colStart <= colEnd` hai, hum spiral order mein fill karte hain:

1.  **Right:** `rowStart` row ko `colStart` se `colEnd` tak fill karte hain, aur `rowStart` ko increment karte hain.
2.  **Down:** `colEnd` column ko `rowStart` se `rowEnd` tak fill karte hain, aur `colEnd` ko decrement karte hain.
3.  **Left:** Agar `rowStart <= rowEnd` hai (single row left ho sakti hai), toh `rowEnd` row ko `colEnd` se `colStart` tak reverse mein fill karte hain, aur `rowEnd` ko decrement karte hain.
4.  **Up:** Agar `colStart <= colEnd` hai (single column left ho sakti hai), toh `colStart` column ko `rowEnd` se `rowStart` tak reverse mein fill karte hain, aur `colStart` ko increment karte hain.
    Yeh process tab tak chalta hai jab tak saare cells fill na ho jayein. Aakhir mein `matrix` return karte hain.

### Code

```java
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n]; // N x N size ki ek matrix banayi

        int rowStart = 0; // Topmost row ka index
        int rowEnd = n - 1; // Bottommost row ka index
        int colStart = 0; // Leftmost column ka index
        int colEnd = n - 1; // Rightmost column ka index

        int num = 1; // Woh number jo matrix mein fill karna hai, 1 se shuru hoga

        // Loop tab tak chalega jab tak saare cells fill na ho jaayein
        // Jab tak sabhi cells bhare na hon, tab tak loop karte rahenge
        while (rowStart <= rowEnd && colStart <= colEnd) {
            // Right fill karo: current rowStart mein colStart se colEnd tak
            // Right mein bharo: Abhi ki rowStart mein colStart se colEnd tak numbers daalo
            for (int j = colStart; j <= colEnd; j++) {
                matrix[rowStart][j] = num++; // Number fill karo aur increment karo
            }
            rowStart++; // Agli row par jaao (andar ki taraf)

            // Down fill karo: current colEnd mein rowStart se rowEnd tak
            // Neeche bharo: Abhi ki colEnd mein rowStart se rowEnd tak numbers daalo
            for (int i = rowStart; i <= rowEnd; i++) {
                matrix[i][colEnd] = num++; // Number fill karo aur increment karo
            }
            colEnd--; // Pichle column par jaao (andar ki taraf)

            // Left fill karo: current rowEnd mein colEnd se colStart tak
            // (Sirf tab jab koi row fill karne ke liye bachi ho, yani single row remaining na ho)
            // Left mein bharo: Abhi ki rowEnd mein colEnd se colStart tak numbers daalo
            // (Yeh check karna zaroori hai ki rowStart abhi bhi rowEnd se kam ya barabar ho, nahi toh duplicate filling ho sakti hai odd-sized matrices mein)
            if (rowStart <= rowEnd) {
                for (int j = colEnd; j >= colStart; j--) {
                    matrix[rowEnd][j] = num++; // Number fill karo aur increment karo
                }
                rowEnd--; // Pichli row par jaao (andar ki taraf)
            }

            // Up fill karo: current colStart mein rowEnd se rowStart tak
            // (Sirf tab jab koi column fill karne ke liye bacha ho, yani single column remaining na ho)
            // Upar bharo: Abhi ki colStart mein rowEnd se rowStart tak numbers daalo
            // (Yeh check karna zaroori hai ki colStart abhi bhi colEnd se kam ya barabar ho, nahi toh duplicate filling ho sakti hai odd-sized matrices mein)
            if (colStart <= colEnd) {
                for (int i = rowEnd; i >= rowStart; i--) {
                    matrix[i][colStart] = num++; // Number fill karo aur increment karo
                }
                colStart++; // Agle column par jaao (andar ki taraf)
            }
        }

        return matrix; // Final filled matrix return karo
    }
}
```

-----

## 73\. Set Matrix Zeroes

**Set Matrix Zeroes** problem mein aapko ek `m x n` matrix mein agar koi element 0 hai, toh uski poori row aur column ko 0 set karna hota hai.

-----

### Description/Overview

Aapko ek `m x n` integer matrix diya gaya hai. Agar koi element `matrix[i][j] == 0` hai, toh uski **poori row `i` aur poori column `j` ko 0 set karna hai**. Is problem ko **in-place** solve karna hai.

For example:

  * `matrix = [[1,1,1],[1,0,1],[1,1,1]]`
      * Output: `[[1,0,1],[0,0,0],[1,0,1]]`
      * Explanation: `matrix[1][1]` is 0, so row 1 and column 1 become 0.
  * `matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]`
      * Output: `[[0,0,0,0],[0,4,5,0],[0,3,1,0]]`

### Approach (How to do it)

Is problem ko in-place solve karne ke liye, hum additional space (apart from output storage) ka use na karne ki koshish karte hain.

**Optimal Approach (Using First Row and First Column as Markers):**

1.  **Identify First Row/Col Zero Status:**

      * Ek boolean variable `isFirstRowZero` set karo `true` agar first row mein koi 0 hai.
      * Ek boolean variable `isFirstColZero` set करो `true` agar first column mein koi 0 hai.
        (Yeh step zaroori hai kyunki hum first row aur first column ko markers ke taur par use karenge, aur unke original status ko save karna hoga.)

2.  **Mark Rows/Cols with Zeros (Using First Row/Col):**

      * Loop `i` from `1` to `m-1` (rows, excluding first).
      * Loop `j` from `1` to `n-1` (cols, excluding first).
      * Agar `matrix[i][j] == 0` hai, toh `matrix[i][0] = 0` (mark row `i` to be zeroed) aur `matrix[0][j] = 0` (mark col `j` to be zeroed).

3.  **Zero Out Marked Rows/Cols (Excluding First Row/Col):**

      * Loop `i` from `1` to `m-1`.
      * Loop `j` from `1` to `n-1`.
      * Agar `matrix[i][0] == 0` (row `i` marked zero) ya `matrix[0][j] == 0` (col `j` marked zero) hai, toh `matrix[i][j] = 0`.

4.  **Zero Out First Row/Col (Based on Original Status):**

      * Agar `isFirstRowZero` `true` tha, toh poori first row ko 0 set kar do.
      * Agar `isFirstColZero` `true` tha, toh poori first column ko 0 set kar do.

### Solution (The Way to Solve)

In-place solution ke liye, hum first row aur first column ko marker ke taur par use karte hain.
Pehle, do boolean flags (`isFirstRowZero`, `isFirstColZero`) se check karte hain ki kya original matrix mein first row ya first column mein koi 0 hai.
Fir, `matrix[1][1]` se shuru karke baaki ke cells (`i` from `1` to `m-1`, `j` from `1` to `n-1`) ko iterate karte hain. Agar `matrix[i][j]` 0 hai, toh uski corresponding first row cell (`matrix[i][0]`) aur first column cell (`matrix[0][j]`) ko 0 set karte hain.
Is marking ke baad, `matrix[1][1]` se dobara iterate karte hain. Agar `matrix[i][0]` ya `matrix[0][j]` 0 hai, toh `matrix[i][j]` ko 0 set karte hain.
Aakhir mein, agar `isFirstRowZero` `true` tha, toh poori first row ko 0 set karte hain. Aur agar `isFirstColZero` `true` tha, toh poori first column ko 0 set karte hain.

### Code

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length; // Matrix ki rows kitni hain
        int n = matrix[0].length; // Matrix ki columns kitni hain

        boolean isFirstRowZero = false; // Check karne ke liye ki pehli row mein koi zero hai ya nahi
        boolean isFirstColZero = false; // Check karne ke liye ki pehle column mein koi zero hai ya nahi

        // Step 1: Check karo ki kya pehli row ko zero karna hai
        // Ye dekho ki pehli row mein kahin koi zero toh nahi hai
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                isFirstRowZero = true; // Agar zero mil gaya toh flag ko true kar do
                break; // Aage check karne ki zaroorat nahi
            }
        }

        // Step 1: Check karo ki kya pehle column ko zero karna hai
        // Ye dekho ki pehle column mein kahin koi zero toh nahi hai
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                isFirstColZero = true; // Agar zero mil gaya toh flag ko true kar do
                break; // Aage check karne ki zaroorat nahi
            }
        }

        // Step 2: Pehli row aur column ko markers ki tarah use karo
        // (1,1 se shuru karke) agar matrix[i][j] 0 hai, toh matrix[i][0] aur matrix[0][j] ko 0 mark kar do
        // First row aur first column ko markers (nishani) ki tarah use karte hain
        // (start karenge 1,1 se) agar matrix[i][j] zero hai, toh matrix[i][0] aur matrix[0][j] ko bhi zero kar denge
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // Row 'i' ko mark kiya
                    matrix[0][j] = 0; // Column 'j' ko mark kiya
                }
            }
        }

        // Step 3: Pehli row/column mein markers ke base par cells ko zero karo
        // (abhi pehli row/column ko include nahi karenge)
        // Ab, pehli row aur column mein jo markers hain unke hisab se baaki cells ko zero kar do
        // (pehli row aur column ko abhi chhod do)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) { // Agar corresponding row ya col marker zero hai
                    matrix[i][j] = 0; // Toh current cell ko zero kar do
                }
            }
        }

        // Step 4: Agar isFirstRowZero true tha toh pehli row ko zero kar do
        // Agar shuru mein pehli row mein koi zero mila tha, toh us poori row ko zero kar do
        if (isFirstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        // Step 4: Agar isFirstColZero true tha toh pehle column ko zero kar do
        // Agar shuru mein pehle column mein koi zero mila tha, toh us poore column ko zero kar do
        if (isFirstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
```

-----

## 202\. Happy Number

**Happy Number** problem mein aapko check karna hota hai ki kya ek number "happy" hai.

-----

### Description/Overview

Ek "happy number" woh number hota hai jise is process se define kiya jata hai:

  * Kisi bhi positive integer se shuru karo.
  * Us number ke digits ke squares ka sum calculate karo.
  * Naye number ke saath process repeat karo jab tak number 1 na ho jaye (jahan yeh happy number hoga), ya ek cycle mein loop kare jismein 1 kabhi nahi pahunchta.
    Agar number 1 ho jaye, toh woh "happy number" hai. Return `true` agar `n` happy number hai, `false` otherwise.

For example:

  * `n = 19`
      * Output: `true`
      * Explanation:
          * $1^2 + 9^2 = 1 + 81 = 82$
          * $8^2 + 2^2 = 64 + 4 = 68$
          * $6^2 + 8^2 = 36 + 64 = 100$
          * $1^2 + 0^2 + 0^2 = 1 + 0 + 0 = 1$
  * `n = 2`
      * Output: `false`
      * Explanation: `2 -> 4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4` (cycle detected, never reaches 1)

### Approach (How to do it)

Is problem mein cycle detection ki zaroorat padegi. Iske liye do main approaches hain:

1.  **Using a HashSet:** Track all numbers encountered during the process. Agar koi number repeat hota hai aur woh 1 nahi hai, toh matlab ek cycle detect ho gaya hai aur number happy nahi hai.
2.  **Floyd's Cycle-Finding Algorithm (Fast and Slow Pointers):** Tortoise and Hare algorithm ki tarah, do pointers use karo - one moving "fast" (two steps at a time) and one "slow" (one step at a time). Agar yeh pointers milte hain (collision), toh cycle hai. Agar collision 1 par hota hai, toh happy.

**Algorithm (Using HashSet):**

1.  Ek `Set<Integer> visited` banao.
2.  Loop `while (n != 1 && !visited.contains(n))`:
      * `visited.add(n)`.
      * `n = getNext(n)` (A helper function to calculate the sum of squares of digits).
3.  `return n == 1`.

**Helper Function `getNext(int num)`:**

  * `sum = 0`.
  * Loop `while (num > 0)`:
      * `digit = num % 10`.
      * `sum += digit * digit`.
      * `num /= 10`.
  * `return sum`.

### Solution (The Way to Solve)

Hum ek `HashSet` ka use karte hain jo un numbers ko store karta hai jinhein humne calculate kiya hai.
Ek `while` loop chalate hain jab tak current number `n` 1 nahi ho jata aur jab tak `n` `HashSet` mein present nahi hai. Loop ke andar, `n` ko `HashSet` mein add karte hain, aur phir `n` ko uske digits ke squares ke sum se replace karte hain. Is sum ko calculate karne ke liye ek separate helper function `getNext` banate hain.
Loop khatam hone par, agar `n` 1 hai, toh `true` return karte hain (happy number). Agar `n` 1 nahi hai (iska matlab loop `HashSet.contains(n)` condition ki wajah se terminate hua), toh `false` return karte hain (cycle detected, not a happy number).

### Code

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    // Helper function jo digits ke squares ka sum calculate karti hai
    // Yeh helper function numbers ke digits ka square karke unka sum nikalta hai
    private int getNext(int n) {
        int sum = 0; // Sum ko initialize kiya zero se
        while (n > 0) { // Jab tak number zero se bada hai
            int digit = n % 10; // Last digit nikalo
            sum += digit * digit; // Digit ka square karke sum mein add karo
            n /= 10; // Last digit ko remove kar do
        }
        return sum; // Calculated sum return karo
    }

    public boolean isHappy(int n) {
        // Cycles detect karne ke liye HashSet use karo. Agar humein koi aisa number milta hai
        // jo pehle hi dekha ja chuka hai, toh iska matlab hai ki hum ek cycle mein hain aur 1 ki taraf nahi badh rahe.
        // Cycles ko pakadne ke liye ek HashSet use karenge. Agar koi number pehle aa chuka hai,
        // toh matlab hum ek loop mein phas gaye hain aur 1 tak nahi pahunchenge.
        Set<Integer> visitedNumbers = new HashSet<>();

        // Process ko continue karo jab tak number 1 (happy) na ho jaaye ya ek cycle detect na ho jaaye.
        // Jab tak number 1 nahi ho jata ya koi cycle nahi pakad mein aata, tab tak process chalate raho.
        while (n != 1 && !visitedNumbers.contains(n)) {
            visitedNumbers.add(n); // Current number ko visited numbers ke set mein add karo
            n = getNext(n);        // Sequence mein next number calculate karo
        }

        // Agar n 1 hai, toh yeh happy number hai. Nahi toh, yeh ek cycle mein phansa hua hai.
        // Agar n 1 hua, toh samjho number happy hai. Warna, woh ek loop mein ghoom raha hai.
        return n == 1;
    }
}
```

-----

## 66\. Plus One

**Plus One** problem mein aapko ek non-negative integer ko ek array of digits ke roop mein diya jata hai aur aapko us number mein 1 add karke digits ke roop mein hi return karna hota hai.

-----

### Description/Overview

Aapko ek non-negative integer `digits` array ke roop mein diya gaya hai. Array ke digits most significant digit se least significant digit tak store kiye gaye hain. Example ke liye, integer 123 ko `[1,2,3]` represent karta hai. Aapko us number mein 1 add karke digits ke roop mein return karna hai.
Assume karo ki integer mein koi leading zero nahi hoga, except for the number 0 itself (e.g., `[0]`).

For example:

  * `digits = [1,2,3]`
      * Output: `[1,2,4]` (123 + 1 = 124)
  * `digits = [4,3,2,1]`
      * Output: `[4,3,2,2]` (4321 + 1 = 4322)
  * `digits = [9]`
      * Output: `[1,0]` (9 + 1 = 10)
  * `digits = [9,9,9]`
      * Output: `[1,0,0,0]` (999 + 1 = 1000)

### Approach (How to do it)

Number mein 1 add karne ke liye, hum array ko right-to-left (least significant digit se) traverse karte hain.

**Algorithm:**

1.  **Iterate from Right:** Loop `i` from `digits.length - 1` down to `0`.
2.  **Add 1 and Handle Carry:**
      * If `digits[i] < 9`:
          * `digits[i]++` (Digit increment karo).
          * `return digits` (No carry, so we are done).
      * If `digits[i] == 9`:
          * `digits[i] = 0` (Set to 0, carry will be propagated).
3.  **Handle All Nines Case:** Agar loop khatam ho gaya aur hum `return` nahi hue, iska matlab hai ki saare digits 9 the (e.g., `[9,9,9]`). Is case mein, ek naya array banana hoga jo ek digit bada hoga, aur uske pehle element ko 1 set karna hoga aur baaki sab ko 0.
      * `newDigits = new int[digits.length + 1]`.
      * `newDigits[0] = 1`.
      * `return newDigits`.

### Solution (The Way to Solve)

Hum array `digits` ko right se left (least significant digit se) iterate karte hain.
Har digit par, agar woh 9 se chhota hai, toh us digit mein 1 add karte hain aur wahi array return kar dete hain, kyunki koi carry-over nahi hoga.
Agar digit 9 hai, toh usko 0 set karte hain aur loop continue karte hain, matlab carry-over aage propagate hoga.
Agar loop poora ho jata hai aur humne return nahi kiya (jaise `[9,9,9]` ke case mein), toh iska matlab hai ki saare digits 9 the. Is situation mein, humein ek naya array banana padega jiski length original array se ek zyada hogi. Naye array ke pehle element ko 1 set karte hain (representing the carry-over) aur baaki sab ko 0. Phir naya array return kar dete hain.

### Code

```java
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length; // 'digits' array ki length nikalo

        // Rightmost digit (sabse chota place value) se leftmost digit tak loop karo.
        // Array ke bilkul right (end) se shuru karke left ki taraf jaao.
        for (int i = n - 1; i >= 0; i--) {
            // Agar current digit 9 se kam hai, toh simply use badha do
            // aur humara kaam ho gaya, kyunki koi carry-over nahi hoga.
            // Agar current digit 9 se chota hai, toh usmein 1 add karke wahi digits array return kar do.
            // Carry aage nahi badhega.
            if (digits[i] < 9) {
                digits[i]++; // Digit ko ek se badha do
                return digits; // Updated digits array return kar do
            } else {
                // Agar current digit 9 hai, toh use 0 kar do aur ek carry-over
                // agle (left) digit tak jayega.
                // Agar current digit 9 hai, toh usko 0 kar do. Iska matlab hai ki 1 aage wale digit par carry ho gaya.
                digits[i] = 0; // Digit ko 0 par set kar do
            }
        }

        // Agar loop poora ho jata hai, toh iska matlab hai ki saare digits 9 the (jaise [9,9,9]).
        // Is case mein, humein ek naya array banana hoga jismein shuru mein ek extra digit ho
        // taaki carry-over adjust ho sake (jaise [1,0,0,0]).
        // Agar yeh loop poora chal gaya, toh iska matlab hai ki input mein sab 9 the (e.g., [9,9,9]).
        // Aise mein, humein ek naya aur bada array banana padega jismein pehla digit 1 hoga
        // aur baaki sab 0 (e.g., [1,0,0,0]).
        int[] newDigits = new int[n + 1]; // Naye array ka size original se ek zyada hoga
        newDigits[0] = 1; // Pehla digit carry-over ke karan 1 hoga
        // Naye digits array mein baaki elements default 0 honge, jo [1,0,0,0] ke liye sahi hai.

        return newDigits; // Naya array return kar do
    }
}
```

-----

## 50\. Pow(x, n)

**Pow(x, n)** problem mein aapko $x^n$ calculate karna hota hai.

-----

### Description/Overview

Aapko ek `double` `x` aur ek `long` `n` diya gaya hai. Aapko `x` raised to the power `n` (`x^n`) calculate karna hai.

For example:

  * `x = 2.0, n = 10`
      * Output: `1024.00000` ($2^{10} = 1024$)
  * `x = 2.1, n = 3`
      * Output: `9.26100` ($2.1^3 = 9.261$)
  * `x = 2.0, n = -2`
      * Output: `0.25000` ($2^{-2} = 1/2^2 = 1/4 = 0.25$)

### Approach (How to do it)

Is problem ko efficient tarike se solve karne ke liye **Binary Exponentiation** (ya Exponentiation by Squaring) method use karte hain. Yeh method power ko `O(log N)` time mein calculate karta hai.

**Key Idea:**

  * $x^n = (x^2)^{n/2}$ if $n$ is even.
  * $x^n = x \\cdot x^{n-1} = x \\cdot (x^2)^{(n-1)/2}$ if $n$ is odd.
  * Negative powers: $x^{-n} = 1 / x^n$.

**Algorithm (Recursive or Iterative):**

Let's use the iterative approach for better performance (no recursion stack overhead).

1.  **Handle Negative `n`:**
      * Agar `n < 0` hai, toh `x = 1/x` kar do aur `n = -n` kar do. (Ya `long nn = n; if (nn < 0) { x = 1/x; nn = -nn; }`)
2.  **Initialize Result:** `ans = 1.0`.
3.  **Iterative Binary Exponentiation:**
      * Loop `while (nn > 0)`:
          * Agar `nn` odd hai (`nn % 2 == 1`), toh `ans = ans * x`.
          * `x = x * x` (Base ko square karo).
          * `nn = nn / 2` (Power ko half karo).
4.  **Return:** `ans`.

**Important Note on `n` as `long`:**
`n` can be `Integer.MIN_VALUE`. If `n = -2^31`, then `-n` would be `2^31`, which is `Integer.MAX_VALUE + 1`. This value fits in `long`. So, it's safer to cast `n` to `long` at the start to handle `-n` correctly.

### Solution (The Way to Solve)

Hum binary exponentiation (ya exponentiation by squaring) method ka use karte hain.
Pehle, `n` ko `long` type mein store karte hain (`nn`) taaki `Integer.MIN_VALUE` jaise edge cases ko handle kar sakein. Agar `nn` negative hai, toh `x` ko `1/x` se replace karte hain aur `nn` ko positive banate hain.
Ek `ans` variable ko 1.0 se initialize karte hain.
Ek `while` loop chalate hain jab tak `nn` 0 se bada hai. Har iteration mein:

  * Agar `nn` odd hai (iska matlab us position par bit 1 hai), toh `ans` ko `ans * x` se update karte hain.
  * `x` ko `x * x` se square karte hain.
  * `nn` ko `nn / 2` se half karte hain.
    Loop khatam hone par, `ans` mein final result hoga jise return karte hain.

### Code

```java
class Solution {
    public double myPow(double x, int n) {
        // 'n' ke liye 'long' use karein, taaki Integer.MIN_VALUE waali case handle ho sake.
        // Agar n = Integer.MIN_VALUE (-2^31) ho, toh -n 2^31 ban jayega,
        // jo Integer.MAX_VALUE + 1 hai aur ek normal 'int' mein overflow ho jayega.
        long nn = n; // 'n' ko 'long' type ke 'nn' mein store kiya

        double ans = 1.0; // Result store karne ke liye 'ans' variable ko 1.0 se initialize kiya

        // Agar 'n' negative hai, toh 'x' ko 1/x mein badal do aur 'n' ko positive bana do.
        if (nn < 0) {
            x = 1 / x; // x ko reciprocal (ulta) kar diya
            nn = -nn; // nn ko positive bana diya
        }

        // Yeh Binary exponentiation (ya exponentiation by squaring) method hai.
        while (nn > 0) { // Jab tak 'nn' zero se bada hai loop chalao
            // Agar 'nn' odd hai (yani 'nn' ka last bit 1 hai)
            if (nn % 2 == 1) { // Check karo ki 'nn' odd hai ya even
                ans = ans * x; // Current 'x' ko result mein multiply karo
            }
            // Agli iteration ke liye 'x' ko square karo (yeh x^(2k) ko effectively handle karta hai)
            x = x * x; // 'x' ko khud se multiply karke square banao
            // 'nn' ko aadha karo (yeh n/2 ko effectively handle karta hai)
            nn /= 2; // 'nn' ko 2 se divide karke aadha karo
        }

        return ans; // Final calculated answer return karo
    }
}
```

-----

## 43\. Multiply Strings

**Multiply Strings** problem mein aapko do non-negative integers jo strings ke roop mein diye gaye hain, unka product calculate karna hota hai aur result ko string ke roop mein return karna hota hai.

-----

### Description/Overview

Aapko do non-negative integers `num1` aur `num2` diye gaye hain jo strings ke roop mein hain. Aapko unka product return karna hai, bhi string ke roop mein.
Aapko built-in BigInteger library ya numbers ko directly integers mein convert karke handle nahi karna hai. `num1` aur `num2` sirf digits contain karenge aur koi leading zeros nahi honge (except the number 0 itself).

For example:

  * `num1 = "2", num2 = "3"`
      * Output: `"6"`
  * `num1 = "123", num2 = "456"`
      * Output: `"56088"`

### Approach (How to do it)

Hum is problem ko long multiplication (school method) simulate karke solve kar sakte hain. Jab hum two numbers multiply karte hain, toh har digit `num1[i]` ko `num2[j]` se multiply karte hain aur uske result ko sahi position par add karte hain.

**Key Idea:**
Agar `num1` ki length `L1` hai aur `num2` ki length `L2` hai, toh unka product ki maximum length `L1 + L2` ho sakti hai.
Let's take `num1 = "123"` and `num2 = "45"`.
`pos` array of size `L1 + L2` will store the digits of the result.
`pos[i + j + 1]` corresponds to the current digit, and `pos[i + j]` corresponds to the carry.

`123 * 45`
Indices:
`num1`: `i` from `L1-1` down to `0`
`num2`: `j` from `L2-1` down to `0`

When `num1[i]` and `num2[j]` are multiplied, their product contributes to positions `i+j` and `i+j+1` in the result.

**Algorithm:**

1.  **Handle Zero Case:** Agar `num1` "0" hai ya `num2` "0" hai, toh "0" return karo.
2.  **Initialize `pos` array:** `int[] pos = new int[m + n]` where `m = num1.length(), n = num2.length()`.
3.  **Iterate and Multiply:**
      * Loop `i` from `m - 1` down to `0`.
      * Loop `j` from `n - 1` down to `0`.
          * `mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0')`.
          * `p1 = i + j`, `p2 = i + j + 1`.
          * `sum = mul + pos[p2]`. (Add `mul` to any carry from previous calculations at `p2`).
          * `pos[p2] = sum % 10`. (Current digit).
          * `pos[p1] += sum / 10`. (Carry to the left).
4.  **Construct Result String:**
      * `StringBuilder sb = new StringBuilder()`.
      * Loop `digit` in `pos`:
          * Agar `sb.length() == 0` aur `digit == 0` (leading zero), toh skip karo.
          * `sb.append(digit)`.
      * `return sb.toString()`.

### Solution (The Way to Solve)

Yeh problem standard long multiplication algorithm ko simulate karta hai.
Pehle, `num1` ya `num2` mein se koi "0" hai toh seedhe "0" return karte hain.
Fir, ek `int` array `pos` banate hain jiski length `num1.length() + num2.length()` hogi. Yeh array final product ke digits store karega.
Hum `num1` aur `num2` ke digits ko right se left iterate karte hain. `num1[i]` aur `num2[j]` ka product calculate karte hain. Yeh product `pos` array mein `i+j` aur `i+j+1` positions par affect karta hai. `pos[i+j+1]` par unit digit aur `pos[i+j]` par carry add karte hain. Ismein already present value `pos[i+j+1]` ko bhi add karte hain (`sum = mul + pos[p2]`) taaki previous calculations ka carry bhi include ho jaye.
Aakhir mein, `pos` array se result string build karte hain. Leading zeros ko skip karte hain. Agar result "0" hi hai toh usko handle karne ke liye special check lagate hain.

### Code

```java
class Solution {
    public String multiply(String num1, String num2) {
        // Edge cases handle karo jahaan koi ek number "0" ho
        // Agar koi bhi number "0" hai, toh result "0" hi hoga
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int m = num1.length(); // num1 ki length
        int n = num2.length(); // num2 ki length
        // Do numbers ko multiply karne par, jinki lengths m aur n hain,
        // result ki maximum length m + n hogi.
        // M aur N length wale do numbers ko multiply karne par,
        // result ki maximum length M + N ho sakti hai.
        int[] pos = new int[m + n]; // Result ke digits store karne ke liye array

        // Dono numbers ke liye right se left iterate karo
        // Right se left ki taraf loop chalao dono numbers ke liye
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // Characters ko integers mein convert karo
                // Characters ko numbers mein badlo
                int digit1 = num1.charAt(i) - '0';
                int digit2 = num2.charAt(j) - '0';

                // Current digits ka product calculate karo
                // Abhi wale digits ka guna (multiplication) karo
                int mul = digit1 * digit2;

                // p1 carry ke liye position hai, p2 current digit ke liye position hai.
                // p1 carry ki jagah hai, p2 abhi wale digit ki jagah hai.
                int p1 = i + j;
                int p2 = i + j + 1;

                // Product ko pos[p2] par current sum mein add karo (jismein pichli calculations se carry ho sakta hai)
                // Product ko pos[p2] par current sum mein add karo (ho sakta hai ismein pehle se koi carry ho)
                int sum = mul + pos[p2];

                // pos[p2] ke liye current digit sum % 10 hai
                // pos[p2] ke liye abhi wala digit sum % 10 hoga
                pos[p2] = sum % 10;
                // Carry-over sum / 10 hai, jise pos[p1] mein add karna chahiye
                // Carry-over sum / 10 hai, jisko pos[p1] mein add karna hai
                pos[p1] += sum / 10;
            }
        }

        // pos array se result string banao
        // Pos array se final string banao
        StringBuilder sb = new StringBuilder();
        // Leading zeros skip karo.
        // Shuru ke zeros ko chhod do.
        for (int digit : pos) {
            // Agar StringBuilder empty hai aur current digit 0 hai, toh use skip kar do
            // (jaise "0" * "0" case ke liye, ya agar product leading zeros se start hota hai)
            // Agar StringBuilder khaali hai aur abhi wala digit 0 hai, toh skip karo
            // (jaise "0" * "0" ke case mein, ya agar result mein shuru mein extra zeros hain)
            if (sb.length() == 0 && digit == 0) {
                continue;
            }
            sb.append(digit); // Digit ko StringBuilder mein add karo
        }

        return sb.toString(); // Final string result return karo
    }
}
```

-----

## 2013\. Detect Squares

**Detect Squares** problem mein aapko ek data structure design karna hota hai jo points ko add kar sake aur phir query kar sake ki kitne squares ban sakte hain given ek point se.

-----

### Description/Overview

Aapko `DetectSquares` class implement karni hai.

  * `DetectSquares()`: Constructor, object ko initialize karta hai.
  * `void add(int[] point)`: Ek `point = [x, y]` ko data structure mein add karta hai.
  * `int count(int[] point)`: Ek `point = [x, y]` query karta hai aur return karta hai ki kitne ways hain jisse `point` ek square ka corner ban sakta hai, jiske edges axis-aligned honge. Square ke charon corners data structure mein hone chahiye.

For example:

```
DetectSquares ds = new DetectSquares();
ds.add([3, 10]);
ds.add([11, 2]);
ds.add([3, 2]);
ds.count([11, 10]); // Output: 1 (The square is (3,10), (11,10), (3,2), (11,2))
ds.count([14, 8]);  // Output: 0
ds.add([11, 2]);    // Duplicate point can be added
ds.count([11, 10]); // Output: 2 (Now two squares are possible with (11,10) as a corner)
```

### Approach (How to do it)

Is problem ko efficiently solve karne ke liye, hum do data structures ka combination use karenge:

1.  **`Map<Integer, Map<Integer, Integer>> pointsCount`**:

      * Outer map stores `x` coordinates.
      * Inner map stores `y` coordinates and their counts for that specific `x`.
      * Example: `pointsCount.get(x).get(y)` gives count of point `(x,y)`.
      * This allows efficient lookup of points by `x` or `y` coordinates.

2.  **`List<int[]> addedPoints`**:

      * All added points ki simple list. Yeh `count` method mein iteration ke liye useful hoga, jab hum `point` (query point) ko square ka ek corner मानेंगे, aur `addedPoints` list se possible दूसरे corners ढूंढेंगे.

**Algorithm (`count` method for `queryPoint = [x1, y1]`):**

Assume `queryPoint = (x1, y1)` is one corner of the square. We need to find three other points.
Since the square is axis-aligned, if `(x1, y1)` is one corner, and another corner is `(x2, y2)`, then the side length of the square would be `|x1 - x2|` and `|y1 - y2|`. For a square, these must be equal and non-zero. Let this side length be `side`.

1.  Initialize `count = 0`.
2.  Iterate through all previously `addedPoints` (let's say `p2 = [x2, y2]`):
      * **Skip queryPoint itself:** If `x1 == x2` and `y1 == y2`, skip this `p2`.
      * **Check for Same X or Same Y:** If `x1 == x2` or `y1 == y2`, `p2` cannot be diagonally opposite `queryPoint` (for an axis-aligned square). It could be an adjacent corner. In that case, `side = abs(x1-x2)` or `abs(y1-y2)`.
          * Calculate `side = Math.abs(x1 - x2)`. If `side == 0`, try `side = Math.abs(y1 - y2)`. If `side == 0`, skip (same point).
          * If `Math.abs(y1 - y2) != side`, skip (not a square).
          * If `side > 0` and `Math.abs(x1 - x2) == side` and `Math.abs(y1 - y2) == side`:
              * Now `p2` is a potential **adjacent corner** to `(x1, y1)`.
              * The two other potential corners would be `(x1, y2)` and `(x2, y1)`.
              * Check `pointsCount` for both `(x1, y2)` and `(x2, y1)`.
              * If `(x1, y2)` exists and `(x2, y1)` exists, then `count += pointsCount.get(x1).get(y2) * pointsCount.get(x2).get(y1)`.
              * **Correction:** The `count` method iterates through `addedPoints`. If `p2 = (x2,y2)` is found from `addedPoints`, and `x1 != x2` and `y1 != y2`, then `p2` can only be a diagonally opposite corner.
                  * `side = Math.abs(x1 - x2)`.
                  * If `side == 0` or `side != Math.abs(y1 - y2)`, then `p2` cannot form a square with `(x1,y1)`. Continue.
                  * If `side > 0` and `side == Math.abs(y1 - y2)`:
                      * The other two corners would be `(x1, y2)` and `(x2, y1)`.
                      * Check if `(x1, y2)` exists: `pointsCount.getOrDefault(x1, new HashMap<>()).getOrDefault(y2, 0)`.
                      * Check if `(x2, y1)` exists: `pointsCount.getOrDefault(x2, new HashMap<>()).getOrDefault(y1, 0)`.
                      * If both exist, `count += pointsCount.get(x1).get(y2) * pointsCount.get(x2).get(y1)`.
3.  Return `count`.

### Solution (The Way to Solve)

Hum `DetectSquares` class mein do data structures use karte hain:

1.  `pointsCount`: `Map<Integer, Map<Integer, Integer>>` to store `(x, y)` coordinates ki frequency. Yeh `add` method mein point add karne aur `count` method mein lookup ke liye use hota hai.
2.  `addedPoints`: `List<int[]>` to store saare `[x, y]` points jo `add` kiye gaye hain. Yeh `count` method mein iterate karne ke liye use hota hai.

**`add(int[] point)`:** Simple hai, `pointsCount` mein point ki frequency badha do, aur `addedPoints` list mein point ko add kar do.

**`count(int[] queryPoint)`:**
`queryPoint` ko `(x1, y1)` mante hain. Phir `addedPoints` list mein har `point = (x2, y2)` ko iterate karte hain.
Agar `x1 == x2` ya `y1 == y2` hai, toh `queryPoint` aur `point` diagonally opposite corners nahi ho sakte axis-aligned square mein (unka distance 0 nahi hona chahiye in both axes). Is case mein hum `continue` karte hain.
`side = Math.abs(x1 - x2)` calculate karte hain. Agar `side` 0 hai, ya `side` `Math.abs(y1 - y2)` ke barabar nahi hai, toh yeh square nahi ban sakta, `continue` karte hain.
Agar `side > 0` aur `side == Math.abs(y1 - y2)` hai, toh `(x2, y2)` `(x1, y1)` ka diagonally opposite corner hai. Ab humein baaki ke do corners ढूंढने hain: `(x1, y2)` aur `(x2, y1)`.
`pointsCount` map se check karte hain ki `(x1, y2)` aur `(x2, y1)` exist karte hain aur unki frequencies ko multiply karke `count` mein add karte hain. Example: agar `(x1, y2)` 3 baar aur `(x2, y1)` 2 baar add kiya gaya hai, toh $3 \\times 2 = 6$ ways hain.
Aakhir mein total `count` return karte hain.

### Code

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DetectSquares {

    // Points ko efficiently search karne ke liye HashMap of HashMaps use karenge.
    // Outer map: x-coordinate -> Inner Map
    // Inner map: y-coordinate -> Us (x, y) par kitne points hain uska count
    // Points store karne ke liye Map ke andar Map banaya hai, taaki jaldi se search kar sakein.
    // Baahar waala Map: X-coordinate ke liye -> Andar waala Map
    // Andar waala Map: Y-coordinate ke liye -> Us (X, Y) point par kitne points hain
    private Map<Integer, Map<Integer, Integer>> pointsCount;

    // Saare add kiye gaye points ko ek List mein store karenge. Yeh squares count karte samay
    // existing points par iterate karne ke liye useful hai.
    // Jitne bhi points add kiye hain, unko ek list mein rakhenge. Jab square count karenge,
    // tab is list se points check kar sakte hain.
    private List<int[]> addedPoints;

    public DetectSquares() {
        pointsCount = new HashMap<>(); // pointsCount ka naya instance banaya
        addedPoints = new ArrayList<>(); // addedPoints ka naya instance banaya
    }

    public void add(int[] point) {
        int x = point[0]; // Point ka x-coordinate
        int y = point[1]; // Point ka y-coordinate

        // HashMap of HashMaps mein count update karo
        // Is nested HashMap mein point ka count badhao
        pointsCount.computeIfAbsent(x, k -> new HashMap<>()).put(y, pointsCount.get(x).getOrDefault(y, 0) + 1);

        // Point ko saare added points ki list mein add karo
        // Is point ko apni list mein add kar lo
        addedPoints.add(point);
    }

    public int count(int[] queryPoint) {
        int x1 = queryPoint[0]; // Query point ka x1-coordinate
        int y1 = queryPoint[1]; // Query point ka y1-coordinate
        int count = 0; // Squares ka total count

        // Pehle se add kiye gaye saare points par iterate karo.
        // Agar queryPoint = (x1, y1) ek corner hai, toh hum dusre corner (x2, y2) ko dhundhte hain.
        // Agar (x2, y2) mil jaata hai, toh baaki do corners (x1, y2) aur (x2, y1) hone chahiye.
        // Jitne bhi points pehle se add kiye hain, un sab par ek-ek karke check karo.
        // Agar queryPoint (x1, y1) ek kona hai, toh hum (x2, y2) dusra kona dhoondhenge.
        // Agar (x2, y2) mil gaya, toh baaki ke do kone (x1, y2) aur (x2, y1) hone chahiye.
        for (int[] p2 : addedPoints) {
            int x2 = p2[0]; // Dusre point ka x2-coordinate
            int y2 = p2[1]; // Dusre point ka y2-coordinate

            // Agar points ka X ya Y coordinate same hai, toh wo axis-aligned square ka diagonal nahi bana sakte
            // (jab tak woh same point na hon, jo ki invalid hai).
            // Aur agar side 0 hai, toh iska matlab same point hai.
            // Agar points ka X ya Y coordinate same hai, toh wo square ka diagonal nahi banayenge.
            // (Agar same points hain, toh bhi invalid hai.)
            // Side ki length 0 hone ka matlab bhi same point hai.
            if (x1 == x2 || y1 == y2 || Math.abs(x1 - x2) != Math.abs(y1 - y2)) {
                continue; // Skip karo agar valid diagonal nahi hai
            }

            // Is point par, (x1, y1) aur (x2, y2) diagonally opposite corners hain.
            // Square ki side length |x1 - x2| hai.
            // Ab, (x1, y1) aur (x2, y2) ek square ke tirche (diagonal) opposite points hain.
            // Square ki side length |x1 - x2| hogi.

            // Baaki do chahiye corners (x1, y2) aur (x2, y1) hain.
            // Square ke baaki do corners (x1, y2) aur (x2, y1) honge.
            int p3x = x1;
            int p3y = y2;
            int p4x = x2;
            int p4y = y1;

            // In do required points ka count nikalo. Agar wo exist nahi karte, toh count 0 hoga.
            // Yeh do baaki ke points (p3, p4) ka count dekho. Agar nahi mile, toh count 0 hoga.
            int countP3 = pointsCount.getOrDefault(p3x, new HashMap<>()).getOrDefault(p3y, 0);
            int countP4 = pointsCount.getOrDefault(p4x, new HashMap<>()).getOrDefault(p4y, 0);

            // Unke counts ke product ko total mein add karo.
            // Iska reason yeh hai ki agar p3 'c3' times aur p4 'c4' times aata hai,
            // toh wo (x1,y1) aur (x2,y2) ke saath 'c3 * c4' squares bana sakte hain.
            // Unke counts ko multiply karke total count mein add karo.
            // Kyunki agar p3 'c3' baar hai aur p4 'c4' baar, toh (x1,y1) aur (x2,y2) ke saath
            // 'c3 * c4' squares ban sakte hain.
            count += (countP3 * countP4);
        }

        return count; // Total squares ka count return karo
    }
}
```
