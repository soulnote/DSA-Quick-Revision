
# Problem: Min Stack

Ek stack design karna hai jo in operations ko support kare:

  * `push(int val)`: Stack mein `val` add kare.
  * `pop()`: Stack ke top se element hataye.
  * `top()`: Stack ke top element ko return kare.
  * `getMin()`: Stack mein current **minimum** element ko return kare, **O(1) time** mein.

-----

### Example:

**Input:**

```java
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
```

**Output:**

```java
[null,null,null,null,-3,null,0,-2]
```

-----

### Intuition (Samajh):

Normal stack operations `push`, `pop`, `top` O(1) time mein ho jaati hain. Lekin `getMin()` ko O(1) mein karna challenge hai, kyunki minimum value dhundhne ke liye poore stack ko iterate karna padega jo O(N) hoga.

Is problem ko solve karne ke liye, hum do stacks ka istemal karenge:

1.  **Ek regular stack (`stack1`)**: Yeh saare elements ko normal tarike se store karega.
2.  **Ek auxiliary stack (`stack2`)**: Yeh stack har point par current minimum value ko track karega. Jab bhi koi naya element push karte hain, `stack2` apne top par current minimum rakhega.

-----

### Approach (Tareeka):

1.  **`MinStack` Constructor:**

      * Do `Stack<Integer>` objects banao: `stack1` aur `stack2`.
      * `stack2` mein shuruat mein `Integer.MAX_VALUE` push kar do. Yeh ek dummy value hai jo ensure karegi ki `stack2` kabhi khaali na ho jab hum `peek()` karein, aur pehla real number hamesha isse chota hoga, toh woh `stack2` mein push ho jayega.

2.  **`push(int val)` Operation:**

      * `val` ko `stack1` mein `push` kar do.
      * Ab `val` ko `stack2` mein push karne ki baari. Condition yeh hai ki `val` ko `stack2` mein tabhi push karo jab `val` current `stack2.peek()` (yani current minimum) se **chota ya barabar** ho. `Barabar` isliye taaki agar same minimum value do baar aaye, toh dono baar store ho aur `pop` karte waqt sahi se handle ho.

3.  **`pop()` Operation:**

      * `stack1` se top element ko `pop` karo.
      * Check karo ki kya `popped` value `stack2.peek()` ke barabar hai. Agar haan, toh iska matlab hai ki jo minimum value abhi tak thi, woh ab stack se nikal rahi hai. Isliye, `stack2` se bhi us element ko `pop` kar do.

4.  **`top()` Operation:**

      * Simply `stack1.peek()` return kar do.

5.  **`getMin()` Operation:**

      * Simply `stack2.peek()` return kar do. Kyunki `stack2` hamesha apne top par current minimum value rakhta hai.

-----

### 🔧 Java Code with Comments:

```java
import java.util.Stack; // Stack class use karne ke liye import

class MinStack {
    Stack<Integer> stack1; // Normal stack: saare elements store karega
    Stack<Integer> stack2; // Auxiliary stack: har level par minimum element track karega

    public MinStack() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        // stack2 ko Integer.MAX_VALUE se initialize karte hain
        // taaki pehla element jo push ho woh hamesha isse chota ho
        // aur stack2.peek() karte waqt kabhi EmptyStackException na aaye.
        stack2.push(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack1.push(val); // Value ko normal stack mein push karo

        // Agar 'val' current minimum (stack2 ke top par) se chota ya barabar hai,
        // toh usko stack2 mein bhi push karo.
        // Barabar isliye taaki duplicate minimums bhi track hon.
        if (val <= stack2.peek()) {
            stack2.push(val);
        }
    }

    public void pop() {
        // Normal stack se top element nikal lo
        int popped = stack1.pop();

        // Agar nikla hua element stack2 ke top (current minimum) ke barabar hai,
        // toh stack2 se bhi pop karo, kyunki woh minimum ab remove ho gaya hai.
        if (popped == stack2.peek()) {
            stack2.pop();
        }
    }

    public int top() {
        // Normal stack ka top element return karo
        return stack1.peek();
    }

    public int getMin() {
        // stack2 ka top element return karo, jo current minimum hai
        return stack2.peek();
    }
}
```

-----

### 🧠 Complexity

  * **Time Complexity:** `O(1)` for all operations (`push`, `pop`, `top`, `getMin`). Har operation mein constant time lagta hai kyunki stack operations inherently O(1) hoti hain.
  * **Space Complexity:** `O(N)` for both stacks. Worst-case mein, agar elements descending order mein push kiye jaayen (e.g., 5, 4, 3, 2, 1), toh `stack2` bhi `N` elements store karega. Best-case mein, agar elements ascending order mein push kiye jaayen (e.g., 1, 2, 3, 4, 5), toh `stack2` mein sirf ek element (1) rahega. Average case mein yeh `N` se kam hoga, but maximum `N` tak ja sakta hai.

-----

# Problem: Generate Parentheses

Aapko ek integer **n** diya gaya hai jo parentheses ke pairs ki sankhya ko represent karta hai. Aapka kaam hai **n** pairs se banne wale **well-formed (valid) parentheses** ke saare combinations ko generate karna.

-----

### Example:

**Input:**
`n = 3`

**Output:**
`["((()))","(()())","(())()","()(())","()()()"]`

**Explanation:**
3 pairs of parentheses ke saath banne wale saare possible strings jo sahi tarah se open aur close ho rahe hain.

-----

### Intuition (Samajh):

Yeh problem recursion aur backtracking se solve hoti hai. Hamein valid parentheses sequence banana hai. Iska matlab hai ki:

1.  Kabhi bhi `open` parentheses ka count `close` parentheses ke count se kam nahi hona chahiye ek point par.
2.  Total `open` parentheses aur total `close` parentheses `n` ke barabar hone chahiye.
3.  `close` parentheses ka count kabhi `open` parentheses ke count se zyada nahi hona chahiye, kyunki iska matlab hoga ki hum bina open kiye hi close kar rahe hain.

Is logic ko follow karte hue, hum ek-ek karke `(` ya `)` add karte jayenge aur backtrack karte jayenge.

-----

### Approach (Tareeka):

Hum ek recursive helper function (`generate`) banayenge jo current string, bache hue `open` parentheses ka count, aur bache hue `close` parentheses ka count lega.

1.  **Base Case:**

      * Agar `open == 0` aur `close == 0` (matlab saare parentheses use ho gaye hain), toh current string `str` ko answer `List` mein add kar do aur `return` ho jao.

2.  **Recursive Steps:**

      * **`(` add karne ki condition:**
          * Agar `open > 0` hai (matlab abhi bhi `open` parentheses add kar sakte hain), toh `str + "("` ke saath `generate` function ko call karo, `open` ko `1` se kam karke (`open - 1`). `close` same rahega.
      * **`)` add karne ki condition:**
          * Agar `close > open` hai (matlab abhi bhi `close` parentheses add kar sakte hain **aur** `close` ka count `open` se zyada hai, jo ensures karta hai ki sequence valid rahega, yani humne pehle koi `(` open kiya hai jise abhi close karna hai), toh `str + ")"` ke saath `generate` function ko call karo, `close` ko `1` se kam karke (`close - 1`). `open` same rahega.

3.  **Main Function:**

      * `List<String> ans` banao.
      * `generate("", n, n, ans)` ko call karo (shuruat mein khaali string, `n` `open` aur `n` `close` parentheses available).
      * `ans` ko return kar do.

-----

### 🔧 Java Code with Comments:

```java
import java.util.ArrayList; // ArrayList use karne ke liye import
import java.util.List;     // List interface use karne ke liye import

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>(); // Final list jismein saare valid combinations honge
        // Helper function ko call karo:
        // "" -> abhi tak ki string
        // n -> bache hue '(' parentheses ki sankhya
        // n -> bache hue ')' parentheses ki sankhya
        // ans -> jismein results store honge
        generate("", n, n, ans);
        return ans;
    }

    // Recursive helper function
    // str: abhi tak banayi gayi parentheses string
    // open: kitne '(' parentheses abhi bhi add kar sakte hain
    // close: kitne ')' parentheses abhi bhi add kar sakte hain
    // ans: list jismein final valid strings add honge
    private void generate(String str, int open, int close, List<String> ans) {
        // Base case: Jab saare '(' aur ')' parentheses use ho chuke hain
        if (open == 0 && close == 0) {
            ans.add(str); // Current valid string ko answer list mein add karo
            return;        // Recursion khatam
        }

        // Condition 1: Agar abhi bhi '(' parentheses add kar sakte hain
        if (open > 0) {
            // '(' add karo aur open count ko 1 kam karo
            generate(str + "(", open - 1, close, ans);
        }

        // Condition 2: Agar abhi bhi ')' parentheses add kar sakte hain AND
        // ')' ka count '(' ke count se zyada hai.
        // Yeh condition ensure karti hai ki hum hamesha ek '(' ko close kar rahe hain.
        // Matbal, string mein kabhi bhi ')' '(' se zyada nahi honge.
        if (close > open) {
            // ')' add karo aur close count ko 1 kam karo
            generate(str + ")", open, close - 1, ans);
        }
    }
}
```

-----

# Problem: Car Fleet

Aapko **n** cars di gayi hain. Har car ek one-dimensional road par ek certain **position** par hai, mile 0 se shuru hokar ek **target** mile ki taraf jaa rahi hai. Har car ki apni **speed** hai.

**Rules:**

  * Cars ek doosre ko **pass nahi** kar sakti.
  * Jab ek **tez car ek dheemi car ko pakad leti hai**, toh woh dono milkar ek "**car fleet**" bana lete hain aur **dheemi car ki speed** se aage badhte hain.
  * Ek car fleet ek ya ek se zyada cars ka group hai jo same speed se ek saath travel kar rahe hain.
  * Agar ek car theek **target par hi pakad leti hai**, toh use bhi us fleet ka part mana jata hai.

**Goal:**
Target par pahunchne wale car fleets ki sankhya return karo.

-----

### Example:

**Input:**
`target = 12`
`position = [10,8,0,5,3]`
`speed = [2,4,1,1,3]`

**Output:**
`3`

**Explanation:**

  * Positions 10 (speed 2) aur 8 (speed 4) par cars theek target par milti hain aur ek fleet banati hain.
  * Position 0 (speed 1) par car kabhi doosron ko nahi pakad paati, apni khud ki fleet banati hai.
  * Positions 5 (speed 1) aur 3 (speed 3) par cars mile 6 par pehle hi mil jaati hain, aur speed 1 par ek fleet ki tarah travel karti hain.

-----

### Intuition (Samajh):

Cars ek doosre ko pass nahi kar sakti. Iska matlab hai ki agar ek faster car kisi slower car ko pakadti hai, toh woh faster car slower car ki speed se chalna shuru kar degi. Iska implication yeh hai ki **fleet ki speed hamesha slowest car ki speed hogi jo us fleet ko bana rahi hai**.

Agar hum cars ko unki **target se duri** ke hisab se dekhein, toh jo car **target ke sabse paas** hai, woh pehle pahunchegi (ya fleet banayegi). Agar koi usse peeche wali car us car ko target se pehle pakad sakti hai, toh woh dono ek fleet bana lenge.

Is problem ko solve karne ka sabse behtar tareeka hai cars ko unki **starting position ke hisab se sort** karna, aur phir **target ki taraf se (reverse order mein)** check karna.

-----

### Approach (Tareeka):

1.  **Car Data Prepare Karein:**

      * Har car ke liye uski `position` aur `time_to_target` calculate karo. `time_to_target = (target - position) / speed`.
      * Ek `double[][]` array `posTime` banao jismein `[position, time_to_target]` ke pairs honge.

2.  **Sort Karein:**

      * `posTime` array ko `position` ke hisab se **ascending order** mein sort kar do. Ab cars sabse peeche se sabse aage tak arrange ho gayi hain.

3.  **Fleets Count Karein (Stack ka Use):**

      * Ek `Stack<Double>` banao jo fleets ke **arrival times at target** ko store karega. Stack mein hum sirf un fleets ke times store karenge jo `target` par alag se pahunchenge.
      * `posTime` array ko **reverse order** mein traverse karo (matlab target ke sabse paas wali car se shuru karke).
      * Har car `i` ke liye:
          * Agar `stack` **khaali hai** (matlab yeh pehli car hai jo hum dekh rahe hain, ya koi fleet abhi tak stack mein nahi hai), toh is car ka `time_to_target` (`posTime[i][1]`) stack mein `push` kar do. Yeh ek nayi fleet banati hai.
          * Else (`stack` khaali nahi hai):
              * Compare karo current car ka `time_to_target` (`posTime[i][1]`) stack ke `peek()` (yani current fleet ke arrival time) se.
              * Agar current car ka `time_to_target` **zyada hai** `stack.peek()` se, toh iska matlab hai ki yeh car pichli fleet ko pakad nahi paayegi aur apni khud ki ek nayi fleet banayegi. Toh, is car ka `time_to_target` stack mein `push` kar do.
              * Agar current car ka `time_to_target` **kam ya barabar hai** `stack.peek()` se, toh iska matlab hai ki yeh car pichli fleet ko target par ya usse pehle pakad legi aur usi fleet ka hissa ban jayegi. Is case mein, stack mein kuch bhi add karne ki zaroorat nahi hai (kyunki woh already existing fleet mein merge ho jayegi).

4.  **Result Return Karein:**

      * `stack.size()` return kar do. Stack mein jitne elements honge, utni hi fleets target par pahunchengi.

-----

### 🔧 Java Code with Comments:

```java
import java.util.Arrays; // Arrays.sort() use karne ke liye
import java.util.Stack;  // Stack use karne ke liye

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length; // Total cars ki sankhya

        // double[][] array banaya (position, time_to_target) pairs store karne ke liye
        double[][] posTime = new double[n][2];

        // Har car ke liye target tak pahunchne ka time calculate karo
        for (int i = 0; i < n; i++) {
            double time = (double)(target - position[i]) / speed[i]; // Time = Distance / Speed
            posTime[i] = new double[]{position[i], time}; // Pair ko store karo
        }

        // Cars ko unki starting position ke hisab se ascending order mein sort karo.
        // Matlab, sabse peeche wali car pehle, sabse aage wali car baad mein.
        Arrays.sort(posTime, (a, b) -> Double.compare(a[0], b[0]));

        Stack<Double> stack = new Stack<>(); // Stack jo fleets ke arrival times ko store karega

        // Ab cars ko reverse order mein traverse karo (target ke sabse paas wali car se shuru karke).
        // Is order mein hum check kar sakte hain ki peeche wali car aage wali fleet ko pakad payegi ya nahi.
        for (int i = n - 1; i >= 0; i--) {
            // Agar stack khaali hai (matlab yeh pehli fleet hai jo hum observe kar rahe hain)
            // Ya current car ka target tak pahunchne ka time stack ke top (pichli fleet ke arrival time) se zyada hai.
            // Zyada time ka matlab hai ki yeh car pichli fleet ko pakad nahi payegi.
            if (stack.isEmpty() || posTime[i][1] > stack.peek()) {
                stack.push(posTime[i][1]); // Toh yeh car ek nayi fleet banati hai, iska time stack mein push karo.
            }
            // Else (agar current car ka time stack.peek() se kam ya barabar hai),
            // Toh yeh car pichli fleet ko pakad legi aur usi fleet ka hissa ban jayegi.
            // Stack mein kuch bhi add karne ki zaroorat nahi hai, kyunki fleet already represent ho rahi hai.
        }

        // Stack mein jitne elements hain, utni hi fleets target par pahunchengi.
        return stack.size();
    }
}
```

-----

# Problem: Largest Rectangle in Histogram

Aapko integers ka ek array `heights` diya gaya hai. Yeh histogram mein bars ki heights ko represent karta hai, jahaan har bar ki width 1 hai. Aapko us **sabse bade rectangle ka area** dhundhna hai jo is histogram ke andar ban sakta hai.

-----

### Example:

**Input:**
`heights = [2,1,5,6,2,3]`

**Output:**
`10`

**Explanation:**
Sabse bada rectangle heights `[5,6]` wale bars se banta hai, jiski width 2 hai, area = 5 \* 2 = 10.

-----

### Intuition (Samajh):

Agar hum kisi bhi bar `heights[i]` ko rectangle ki height maante hain, toh us rectangle ki width kitni hogi? Uski width utni hogi jab tak us height `heights[i]` se chota koi bar uske left ya right mein nahi aa jata. Agar koi chota bar aa jata hai, toh current bar se bana rectangle wahan tak hi extend kar payega.

Iska matlab hai ki har bar ke liye, hamein do cheezein dhundhni hain:

1.  **Next Smaller Element to the Left (NSL):** Left side mein sabse pehla bar jo current bar se chota hai, uska index.
2.  **Next Smaller Element to the Right (NSR):** Right side mein sabse pehla bar jo current bar se chota hai, uska index.

Jab hamein NSL aur NSR mil jaayenge, toh current bar `heights[i]` se banne wale rectangle ki width hogi: `(NSR[i] - NSL[i] - 1)`. Aur area hoga `heights[i] * width`. Hamein saare bars ke liye yeh calculate karke maximum area dhundhna hai.

**Stack** ek bahut useful data structure hai NSL aur NSR ko efficiently (O(N) time) dhundhne ke liye. Stack mein hum indices ko increasing order of heights mein rakhenge.

-----

### Approach (Tareeka):

1.  **NSL aur NSR Arrays:**

      * Do arrays `nextsmallerLeft` aur `nextsmallerRight` banao, size `n` (heights.length) ka.
      * `nextsmallerLeft[i]` mein `i` ke left mein pehle smaller element ka index store hoga. Agar koi nahi hai, toh `-1` store karo.
      * `nextsmallerRight[i]` mein `i` ke right mein pehle smaller element ka index store hoga. Agar koi nahi hai, toh `n` (array length) store karo.

2.  **NSL Calculate Karein (Left se Right Traverse):**

      * Ek `Stack<Integer>` `st` banao.
      * `for (int i = 0; i < n; i++)` loop chalao.
      * **Pop from Stack:** Jab tak stack khaali nahi hai aur `heights[st.peek()]` current `heights[i]` se bada ya barabar hai, tab tak stack se elements `pop()` karte raho. (Kyunki yeh elements ab `heights[i]` ke liye left smaller nahi ban sakte, aur `heights[i]` unse chota hai toh woh future mein kisi aur ke liye `nextsmaller` ban sakte hain).
      * **Assign NSL:**
          * Agar `st` khaali ho gaya, toh `nextsmallerLeft[i] = -1` (koi smaller element nahi mila left mein).
          * Warna, `nextsmallerLeft[i] = st.peek()` (stack ke top par jo index hai, wahi `nextsmallerLeft` hai).
      * **Push to Stack:** Current index `i` ko `st.push(i)` kar do.

3.  **NSR Calculate Karein (Right se Left Traverse):**

      * `st` ko `clear()` kar do.
      * `for (int i = n - 1; i >= 0; i--)` loop chalao.
      * **Pop from Stack:** Jab tak stack khaali nahi hai aur `heights[st.peek()]` current `heights[i]` se bada ya barabar hai, tab tak stack se elements `pop()` karte raho.
      * **Assign NSR:**
          * Agar `st` khaali ho gaya, toh `nextsmallerRight[i] = n` (koi smaller element nahi mila right mein, `n` ka matlab array ke bahar).
          * Warna, `nextsmallerRight[i] = st.peek()`.
      * **Push to Stack:** Current index `i` ko `st.push(i)` kar do.

4.  **Max Area Calculate Karein:**

      * `maxArea = 0` initialize karo.
      * `for (int i = 0; i < n; i++)` loop chalao.
      * `height = heights[i]`.
      * `width = nextsmallerRight[i] - nextsmallerLeft[i] - 1`. (Formula ko samjho: `nextsmallerRight[i]` exclusive boundary hai, `nextsmallerLeft[i]` exclusive boundary hai. Inke beech ki distance hi width hai, minus 1 kyunki indices 0-based hote hain.)
      * `area = height * width`.
      * `maxArea = Math.max(maxArea, area)`.

5.  **Return:** `maxArea` return kar do.

-----

### 🔧 Java Code with Comments:

```java
import java.util.Stack; // Stack class use karne ke liye import

class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length; // Heights array ki length

        // nextsmallerLeft[i]: heights[i] ke left mein sabse pehle chote element ka index
        int[] nextsmallerLeft = new int[n];
        // nextsmallerRight[i]: heights[i] ke right mein sabse pehle chote element ka index
        int[] nextsmallerRight = new int[n];

        Stack<Integer> st = new Stack<>(); // Indices store karne ke liye stack

        // Step 1: Har bar ke liye left mein next smaller element dhundo
        // Loop left se right chalega
        for (int i = 0; i < n; i++) {
            // Jab tak stack khaali nahi hai aur stack ke top par jo index hai uski height
            // current bar heights[i] se badi ya barabar hai, tab tak stack se pop karte raho.
            // Kyunki current heights[i] inko (stack ke elements ko) ignore kar dega as smaller element,
            // aur future mein ye stack ke elements kabhi max area ke liye border nahi banenge.
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            // Agar stack khaali hai, matlab current bar ke left mein koi chota element nahi hai.
            if (st.isEmpty()) {
                nextsmallerLeft[i] = -1; // -1 assign karo
            } else {
                // Warna, stack ke top par jo index hai, wahi next smaller element hai.
                nextsmallerLeft[i] = st.peek();
            }
            st.push(i); // Current index ko stack mein push karo
        }

        st.clear(); // Stack ko clear karo next calculation ke liye

        // Step 2: Har bar ke liye right mein next smaller element dhundo
        // Loop right se left chalega
        for (int i = n - 1; i >= 0; i--) {
            // Same logic, bas direction alag hai
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            // Agar stack khaali hai, matlab current bar ke right mein koi chota element nahi hai.
            // 'n' assign karo (array bounds ke bahar)
            if (st.isEmpty()) {
                nextsmallerRight[i] = n;
            } else {
                nextsmallerRight[i] = st.peek();
            }
            st.push(i); // Current index ko stack mein push karo
        }

        int maxArea = 0; // Maximum area store karne ke liye variable

        // Step 3: Har bar ke liye possible max area calculate karo
        for (int i = 0; i < n; i++) {
            int height = heights[i]; // Current bar ki height
            // Rectangle ki width: (right smaller index) - (left smaller index) - 1
            // (-1 because indices are 0-based and boundaries are exclusive)
            int width = nextsmallerRight[i] - nextsmallerLeft[i] - 1;
            int area = height * width; // Current bar se banne wala area
            maxArea = Math.max(maxArea, area); // maxArea ko update karo
        }

        return maxArea; // Sabse bada rectangle area return karo
    }
}
```
