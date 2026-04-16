# Stack Data Structure - Complete Guide 

## 📚 Theory + Java Code for All Important Stack Questions

---

## **1. Stack Basics**

### Theory (Hinglish)
Stack ek **LIFO (Last In First Out)** data structure hai. Jaise plates ka stack - jo plate last mein rakho, wahi pehle nikalta hai.

**Operations:**
- `push()` - element add karna
- `pop()` - element remove karna
- `peek()` - top element dekhna
- `isEmpty()` - check empty hai ya nahi

### Java Implementation
```java
import java.util.*;

// Method 1: Using ArrayList
class StackUsingArrayList {
    private ArrayList<Integer> list = new ArrayList<>();
    
    void push(int x) {
        list.add(x);
    }
    
    int pop() {
        if(isEmpty()) {
            System.out.println("Stack Underflow");
            return -1;
        }
        return list.remove(list.size()-1);
    }
    
    int peek() {
        if(isEmpty()) return -1;
        return list.get(list.size()-1);
    }
    
    boolean isEmpty() {
        return list.size() == 0;
    }
}

// Method 2: Using LinkedList
class StackUsingLL {
    private class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }
    private Node top;
    
    void push(int x) {
        Node newNode = new Node(x);
        newNode.next = top;
        top = newNode;
    }
    
    int pop() {
        if(isEmpty()) return -1;
        int val = top.data;
        top = top.next;
        return val;
    }
    
    int peek() {
        return isEmpty() ? -1 : top.data;
    }
    
    boolean isEmpty() {
        return top == null;
    }
}
```

---

## **2. Valid Parentheses** ⭐ (Most Asked)

### Theory
**Problem:** Check karo ki brackets sahi tarah se close ho rahe hain ya nahi.

**Algorithm:**
1. Stack use karo
2. Opening bracket mile (`(`, `{`, `[`) to push karo
3. Closing bracket mile to stack se top nikalo aur check karo matching hai ya nahi
4. End mein stack empty hona chahiye

**Time Complexity:** O(n)  
**Space Complexity:** O(n)

### Java Code
```java
class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for(char c : s.toCharArray()) {
            if(c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            else {
                if(stack.isEmpty()) return false;
                
                char top = stack.pop();
                if(c == ')' && top != '(') return false;
                if(c == '}' && top != '{') return false;
                if(c == ']' && top != '[') return false;
            }
        }
        return stack.isEmpty();
    }
}
```

---

## **3. Next Greater Element** ⭐⭐

### Theory
**Problem:** Har element ke liye next greater element dhundho (right side mein).

**Algorithm (Optimized - O(n)):**
1. Stack mein indices store karo
2. Right se left traverse karo
3. Stack se chote elements pop karte jao
4. Top element hi next greater hoga

**Example:** [4,5,2,10] → [5,10,10,-1]

### Java Code
```java
class NextGreaterElement {
    public int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        // Right to left traverse
        for(int i = n-1; i >= 0; i--) {
            // Pop smaller elements
            while(!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        return result;
    }
}
```

---

## **4. Min Stack** ⭐⭐⭐

### Theory
**Problem:** Ek stack design karo jo push, pop, top, aur **getMin()** O(1) mein kare.

**Algorithm:**
1. Do stack use karo - main stack aur min stack
2. Min stack mein hamesha minimum value store rahegi
3. Push karte time check karo ki new value min se choti hai to min stack mein bhi push karo

### Java Code
```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
    
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        stack.push(val);
        if(minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    public void pop() {
        int val = stack.pop();
        if(val == minStack.peek()) {
            minStack.pop();
        }
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}
```

---

## **5. Evaluate Reverse Polish Notation** ⭐⭐

### Theory
**Problem:** Postfix expression (RPN) evaluate karo.

**Algorithm:**
1. Stack mein operands push karo
2. Operator mile to top 2 elements pop karo, operation karo, result push karo
3. End mein stack ka top hi answer hai

**Example:** ["2","1","+","3","*"] → ((2+1)*3) = 9

### Java Code
```java
class EvaluateRPN {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for(String token : tokens) {
            if(token.equals("+")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a + b);
            }
            else if(token.equals("-")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a - b);
            }
            else if(token.equals("*")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a * b);
            }
            else if(token.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(a / b);
            }
            else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}
```

---

## **6. Daily Temperatures** ⭐⭐

### Theory
**Problem:** Har din ke liye batao ki next warmer temperature kitne din baad aayega.

**Algorithm:**
1. Stack mein indices store karo
2. Current temperature > stack top wale index ki temperature hai to difference nikal lo
3. Ye **Next Greater Element** ka variation hai

**Example:** [73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]

### Java Code
```java
class DailyTemperatures {
    public int[] dailyTemperatures(int[] temps) {
        int n = temps.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for(int i = 0; i < n; i++) {
            while(!stack.isEmpty() && temps[i] > temps[stack.peek()]) {
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return result;
    }
}
```

---

## **7. Largest Rectangle in Histogram** ⭐⭐⭐ (Hard)

### Theory
**Problem:** Histogram mein sabse bada rectangle area dhundho.

**Algorithm:**
1. Har bar ke liye, uska **left boundary** (first smaller bar on left) aur **right boundary** (first smaller bar on right) dhundho
2. Width = rightBoundary - leftBoundary - 1
3. Area = height * width
4. Stack use karo to find boundaries in O(n)

### Java Code
```java
class LargestRectangle {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        
        for(int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];
            
            while(!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
```

---

## **8. Implement Queue using Stacks** ⭐⭐

### Theory
**Problem:** Stack ka use karke Queue implement karo (FIFO).

**Algorithm:**
- **Two stacks method:** inputStack aur outputStack
- Push: inputStack mein push karo
- Pop: outputStack empty hai to inputStack se saare elements outputStack mein transfer karo, phir pop karo

### Java Code
```java
class MyQueue {
    Stack<Integer> input;
    Stack<Integer> output;
    
    public MyQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }
    
    public void push(int x) {
        input.push(x);
    }
    
    public int pop() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.pop();
    }
    
    public int peek() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.peek();
    }
    
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}
```

---

## **9. Remove All Adjacent Duplicates** ⭐

### Theory
**Problem:** String mein adjacent duplicates remove karte jao.

**Algorithm:**
1. Stack use karo
2. Har character ko stack se compare karo
3. Agar same hai to pop karo, nahi to push karo

**Example:** "abbaca" → "ca"

### Java Code
```java
class RemoveDuplicates {
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        
        for(char c : s.toCharArray()) {
            if(!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(char c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }
}
```

---

## **10. Decode String** ⭐⭐

### Theory
**Problem:** Encoded string ko decode karo - "3[a]2[bc]" → "aaabcbc"

**Algorithm:**
1. Do stack use karo - countStack aur stringStack
2. Current number aur current string maintain karo
3. '[' mile to current count aur string stack mein push karo, reset karo
4. ']' mile to pop karo aur string repeat karo

### Java Code
```java
class DecodeString {
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        
        for(char c : s.toCharArray()) {
            if(Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            }
            else if(c == '[') {
                countStack.push(k);
                stringStack.push(current);
                current = new StringBuilder();
                k = 0;
            }
            else if(c == ']') {
                int count = countStack.pop();
                StringBuilder temp = current;
                current = stringStack.pop();
                for(int i = 0; i < count; i++) {
                    current.append(temp);
                }
            }
            else {
                current.append(c);
            }
        }
        return current.toString();
    }
}
```

---

## **11. Stock Span Problem** ⭐⭐

### Theory
**Problem:** Har day ka stock span - consecutive days ka count jahan price <= current price hai.

**Algorithm:**
1. Stack mein indices store karo
2. Current price se bade element tak ke indices stack se pop karo
3. Span = current_index - previous_greater_index

**Example:** [100,80,60,70,60,75,85] → [1,1,1,2,1,4,6]

### Java Code
```java
class StockSpanner {
    Stack<int[]> stack; // [price, span]
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        while(!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }
        
        stack.push(new int[]{price, span});
        return span;
    }
}
```

---

## **12. Infix to Postfix** ⭐⭐

### Theory
**Problem:** Infix expression (a+b*c) ko Postfix (abc*+) mein convert karo.

**Precedence:**  
1. `^` (highest)  
2. `* /`  
3. `+ -` (lowest)

**Algorithm (Shunting Yard):**
1. Operands directly output mein likho
2. Operators stack mein push karo
3. Higher precedence operator aaye to pop karo
4. '(' push, ')' pop till '('

### Java Code
```java
class InfixToPostfix {
    private static int precedence(char op) {
        switch(op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }
    
    public static String convert(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        for(char c : infix.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                result.append(c);
            }
            else if(c == '(') {
                stack.push(c);
            }
            else if(c == ')') {
                while(!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop();
            }
            else {
                while(!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while(!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }
}
```

---

## **Quick Revision Table**

| Problem | Time | Space | Key Concept |
|---------|------|-------|-------------|
| Valid Parentheses | O(n) | O(n) | Bracket matching |
| Next Greater Element | O(n) | O(n) | Monotonic stack |
| Min Stack | O(1) | O(n) | Two stacks |
| Largest Rectangle | O(n) | O(n) | Finding boundaries |
| Queue using Stacks | O(1) amortized | O(n) | Two stacks |
| Decode String | O(n) | O(n) | Nested encoding |

---
Below are **20 most asked hard stack problems** in coding interviews, with Java solutions. "Hard" typically means **O(n) with monotonic stack**, **multiple stacks**, **two-pass logic**, or **design with O(1) operations**.

---

## 🔥 Hard Stack Problems (LeetCode Hard / Medium-Hard)

### 1. Largest Rectangle in Histogram
**Problem:** Heights array → max area rectangle under histogram.  
**Approach:** Monotonic increasing stack. Har bar ke liye left aur right boundary find karo jaha tak wo extend ho sakta hai.

```java
public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0;
    for (int i = 0; i <= heights.length; i++) {
        int h = (i == heights.length) ? 0 : heights[i];
        while (!stack.isEmpty() && h < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}
```

>  Stack mein index store karo. Jab chota height mile, pop karo aur area calculate karo. Width = current index - left boundary - 1.

---

### 2. Trapping Rain Water II (3D)
**Problem:** 2D height map mein kitna pani trap hoga.  
**Approach:** Min-heap + BFS. Boundaries se andar ghuso.

```java
public int trapRainWater(int[][] heightMap) {
    if (heightMap.length == 0) return 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
    int m = heightMap.length, n = heightMap[0].length;
    boolean[][] visited = new boolean[m][n];
    
    // Add boundaries
    for (int i = 0; i < m; i++) {
        pq.offer(new int[]{i, 0, heightMap[i][0]});
        pq.offer(new int[]{i, n-1, heightMap[i][n-1]});
        visited[i][0] = visited[i][n-1] = true;
    }
    for (int j = 0; j < n; j++) {
        pq.offer(new int[]{0, j, heightMap[0][j]});
        pq.offer(new int[]{m-1, j, heightMap[m-1][j]});
        visited[0][j] = visited[m-1][j] = true;
    }
    
    int water = 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    while (!pq.isEmpty()) {
        int[] cell = pq.poll();
        for (int[] d : dirs) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x>=0 && x<m && y>=0 && y<n && !visited[x][y]) {
                water += Math.max(0, cell[2] - heightMap[x][y]);
                pq.offer(new int[]{x, y, Math.max(cell[2], heightMap[x][y])});
                visited[x][y] = true;
            }
        }
    }
    return water;
}
```

>  Boundary se start karo, min height wale cell ko pop karo, uske neighbors mein pani add karo agar neighbor chota hai.

---

### 3. Maximal Rectangle
**Problem:** Binary matrix mein 1's se bana maximum rectangle.  
**Approach:** Har row ko histogram treat karo, upar wala Largest Rectangle in Histogram apply karo.

```java
public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0) return 0;
    int[] heights = new int[matrix[0].length];
    int max = 0;
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            heights[j] = matrix[i][j] == '1' ? heights[j] + 1 : 0;
        }
        max = Math.max(max, largestRectangleArea(heights));
    }
    return max;
}
```

>    Har row pe jaake, upar se accumulate karo heights, fir histogram area nikaalo.

---

### 4. Remove K Digits
**Problem:** String number se k digits remove karke smallest possible number.  
**Approach:** Monotonic increasing stack. Jab current digit < stack top, pop karo (remove karo).

```java
public String removeKdigits(String num, int k) {
    Stack<Character> stack = new Stack<>();
    for (char c : num.toCharArray()) {
        while (!stack.isEmpty() && k > 0 && stack.peek() > c) {
            stack.pop();
            k--;
        }
        stack.push(c);
    }
    while (k-- > 0) stack.pop();
    
    StringBuilder sb = new StringBuilder();
    while (!stack.isEmpty()) sb.append(stack.pop());
    sb.reverse();
    
    // Remove leading zeros
    while (sb.length() > 0 && sb.charAt(0) == '0') sb.deleteCharAt(0);
    return sb.length() == 0 ? "0" : sb.toString();
}
```

>  Stack mein chhota character rakhna hai. Agar bada mila toh pop karo (remove karo). End mein leading zeros hatao.

---

### 5. Decode String
**Problem:** `"3[a2[c]]"` → `"accaccacc"`  
**Approach:** Two stacks - ek number ke liye, ek string ke liye.

```java
public String decodeString(String s) {
    Stack<Integer> countStack = new Stack<>();
    Stack<StringBuilder> strStack = new Stack<>();
    StringBuilder current = new StringBuilder();
    int k = 0;
    
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0');
        } else if (c == '[') {
            countStack.push(k);
            strStack.push(current);
            current = new StringBuilder();
            k = 0;
        } else if (c == ']') {
            StringBuilder decoded = strStack.pop();
            int repeat = countStack.pop();
            for (int i = 0; i < repeat; i++) decoded.append(current);
            current = decoded;
        } else {
            current.append(c);
        }
    }
    return current.toString();
}
```

> Number mila toh count store, `[` mila toh push kardo, `]` mila toh pop karke repeat karo.

---

### 6. Basic Calculator III
**Problem:** Expression with `+ - * / ( )` evaluate karo.  
**Approach:** Stack + sign tracking. Number aur sign ko stack mein push karo.

```java
public int calculate(String s) {
    Stack<Integer> stack = new Stack<>();
    int num = 0;
    char sign = '+';
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) num = num * 10 + (c - '0');
        if (c == '(') {
            // Find matching ')'
            int j = i, count = 0;
            for (; i < s.length(); i++) {
                if (s.charAt(i) == '(') count++;
                if (s.charAt(i) == ')') count--;
                if (count == 0) break;
            }
            num = calculate(s.substring(j + 1, i));
            continue;
        }
        if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {
            if (sign == '+') stack.push(num);
            else if (sign == '-') stack.push(-num);
            else if (sign == '*') stack.push(stack.pop() * num);
            else if (sign == '/') stack.push(stack.pop() / num);
            sign = c;
            num = 0;
        }
    }
    int res = 0;
    for (int n : stack) res += n;
    return res;
}
```

>    Sign track karo. Multiplication/division pe immediate calculate karo. Parenthesis ke liye recursion.

---

### 7. Longest Valid Parentheses
**Problem:** String mein longest valid `()` substring ka length.  
**Approach:** Stack mein index store karo. Base -1 rakhdo.

```java
public int longestValidParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    stack.push(-1);
    int max = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                max = Math.max(max, i - stack.peek());
            }
        }
    }
    return max;
}
```

>    `(` ka index push karo. `)` pe pop karo. Agar stack empty ho gaya toh current index push karo (new base). Length = current - stack top.

---

### 8. Max Stack (LeetCode 716)
**Problem:** Stack with `push`, `pop`, `top`, `peekMax`, `popMax` (O(log n) for max operations).  
**Approach:** TreeMap + DoubleLinkedList. TreeMap max find karega, DLL O(1) delete.

```java
class MaxStack {
    TreeMap<Integer, List<Node>> map = new TreeMap<>();
    Node head = new Node(0), tail = new Node(0);
    
    class Node {
        int val;
        Node prev, next;
        Node(int v) { val = v; }
    }
    
    public MaxStack() {
        head.next = tail;
        tail.prev = head;
    }
    
    public void push(int x) {
        Node node = new Node(x);
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
        map.computeIfAbsent(x, k -> new ArrayList<>()).add(node);
    }
    
    public int pop() {
        Node node = tail.prev;
        removeNode(node);
        List<Node> list = map.get(node.val);
        list.remove(list.size() - 1);
        if (list.isEmpty()) map.remove(node.val);
        return node.val;
    }
    
    public int top() { return tail.prev.val; }
    public int peekMax() { return map.lastKey(); }
    
    public int popMax() {
        int max = map.lastKey();
        List<Node> list = map.get(max);
        Node node = list.remove(list.size() - 1);
        if (list.isEmpty()) map.remove(max);
        removeNode(node);
        return max;
    }
    
    void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
```

>    Normal stack DLL se banai. Max track karne ke liye TreeMap use karo (sorted). PopMax O(log n).

---

### 9. Sum of Subarray Minimums
**Problem:** Har subarray ka minimum sum karo.  
**Approach:** Monotonic stack se har element ka left aur right boundary find karo jaha wo minimum hai.

```java
public int sumSubarrayMins(int[] arr) {
    Stack<Integer> stack = new Stack<>();
    int n = arr.length;
    int[] left = new int[n];  // distance to previous smaller
    int[] right = new int[n]; // distance to next smaller
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
            stack.pop();
        }
        left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
        stack.push(i);
    }
    
    stack.clear();
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
            stack.pop();
        }
        right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
        stack.push(i);
    }
    
    long res = 0;
    for (int i = 0; i < n; i++) {
        res = (res + (long) arr[i] * left[i] * right[i]) % 1_000_000_007;
    }
    return (int) res;
}
```

>    Har element kitni subarrays mein minimum hai = left_dist * right_dist. Sum kar do.

---

### 10. Exclusive Time of Functions
**Problem:** Logs `["0:start:0","1:start:2","1:end:5","0:end:6"]` → har function ka total time.  
**Approach:** Stack mein function id store karo. Time difference calculate karo.

```java
public int[] exclusiveTime(int n, List<String> logs) {
    int[] res = new int[n];
    Stack<Integer> stack = new Stack<>();
    int prevTime = 0;
    
    for (String log : logs) {
        String[] parts = log.split(":");
        int id = Integer.parseInt(parts[0]);
        int time = Integer.parseInt(parts[2]);
        
        if (parts[1].equals("start")) {
            if (!stack.isEmpty()) {
                res[stack.peek()] += time - prevTime;
            }
            stack.push(id);
            prevTime = time;
        } else {
            res[stack.pop()] += time - prevTime + 1;
            prevTime = time + 1;
        }
    }
    return res;
}
```

>  Start pe previous function ka time add karo. End pe current function ka time add karo.


## 💡 Pro Tips for Hard Stack Problems

1. **Monotonic Stack Pattern:** Jab bhi "next greater/smaller" ya "previous greater/smaller" ho, monotonic stack socho .
2. **Two-pass Technique:** Kabhi left se, kabhi right se boundary find karo.
3. **Stack with Index:** Value ke saath index bhi store karo, distance calculate karne ke liye.
4. **Multiple Stacks:** Ek extra stack for min/max, ya TreeMap for O(log n) max operations .
5. **Corner Cases:** Empty stack, duplicate values, negative numbers handle karna mat bhoolna.

---

### 11. Asteroid Collision
**Problem:** Asteroids array `[5,10,-5]` → positive right move, negative left move. Collision mein chota destroy ho jata hai.

**Approach:** Stack mein asteroids daalo. Agar current negative hai aur stack top positive, then collision.

```java
public int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();
    for (int ast : asteroids) {
        boolean destroyed = false;
        while (!stack.isEmpty() && ast < 0 && stack.peek() > 0) {
            if (stack.peek() < -ast) {  // stack wala chota, destroy ho jayega
                stack.pop();
                continue;
            } else if (stack.peek() == -ast) {  // dono destroy
                stack.pop();
                destroyed = true;
                break;
            } else {  // current asteroid destroy
                destroyed = true;
                break;
            }
        }
        if (!destroyed) stack.push(ast);
    }
    
    int[] res = new int[stack.size()];
    for (int i = res.length - 1; i >= 0; i--) {
        res[i] = stack.pop();
    }
    return res;
}
```

>    Positive wala right jayega, negative wala left. Jab opposite direction mein milenge tab collision. Jo chota destroy, equal dono destroy.

---

### 12. Next Greater Element II (Circular Array)
**Problem:** Circular array mein har element ka next greater element find karo.

**Approach:** 2n times traverse karo, modulo use karo.

```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    Arrays.fill(res, -1);
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < 2 * n; i++) {
        int num = nums[i % n];
        while (!stack.isEmpty() && nums[stack.peek()] < num) {
            res[stack.pop()] = num;
        }
        if (i < n) stack.push(i);
    }
    return res;
}
```

>    Do baar loop lagao taaki circular effect ho. Stack mein index store karo. Jab bada mile, answer set karo.

---

### 13. Car Fleet
**Problem:** Cars with position and speed. Target tak pahunchne mein fleets (groups) kitni banegi.

**Approach:** Sort by position. Time calculate karo. Stack se slower cars track karo.

```java
public int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    Car[] cars = new Car[n];
    for (int i = 0; i < n; i++) {
        cars[i] = new Car(position[i], (double)(target - position[i]) / speed[i]);
    }
    Arrays.sort(cars, (a, b) -> b.position - a.position);  // Nearest to target first
    
    Stack<Double> stack = new Stack<>();
    for (Car car : cars) {
        if (stack.isEmpty() || car.time > stack.peek()) {
            stack.push(car.time);
        }
    }
    return stack.size();
}

class Car {
    int position;
    double time;
    Car(int p, double t) { position = p; time = t; }
}
```

>    Target se nearest car se check karo. Agar current car ka time > stack top, naya fleet banega.

---

### 14. 132 Pattern
**Problem:** Array mein exist karta hai `nums[i] < nums[k] < nums[j]` with `i < j < k`.

**Approach:** Right se left traverse karo, stack mein potential "2" (nums[k]) store karo.

```java
public boolean find132pattern(int[] nums) {
    Stack<Integer> stack = new Stack<>();
    int third = Integer.MIN_VALUE;  // This is nums[k] (the '2')
    
    for (int i = nums.length - 1; i >= 0; i--) {
        if (nums[i] < third) return true;
        while (!stack.isEmpty() && stack.peek() < nums[i]) {
            third = stack.pop();
        }
        stack.push(nums[i]);
    }
    return false;
}
```

>    Right se left chalo. Stack mein increasing order maintain karo. Jab current nums[i] < third mila, toh pattern mil gaya.

---

### 15. Simplify Path
**Problem:** Unix path `/a/./b/../../c/` → `/c`

**Approach:** Split by `/`, stack mein directories daalo. `..` pe pop karo.

```java
public String simplifyPath(String path) {
    Stack<String> stack = new Stack<>();
    String[] parts = path.split("/");
    
    for (String part : parts) {
        if (part.equals("") || part.equals(".")) continue;
        if (part.equals("..")) {
            if (!stack.isEmpty()) stack.pop();
        } else {
            stack.push(part);
        }
    }
    
    StringBuilder res = new StringBuilder();
    for (String dir : stack) {
        res.append("/").append(dir);
    }
    return res.length() == 0 ? "/" : res.toString();
}
```

>    `"."` ignore, `".."` pe pop karo, normal name push karo. End mein `/` lagao.

---

### 16. Validate Stack Sequences
**Problem:** Given `pushed` and `popped` arrays, kya valid stack sequence hai?

**Approach:** Stack simulate karo. Push karte jao, jab stack top == popped[current] toh pop karo.

```java
public boolean validateStackSequences(int[] pushed, int[] popped) {
    Stack<Integer> stack = new Stack<>();
    int j = 0;
    for (int num : pushed) {
        stack.push(num);
        while (!stack.isEmpty() && stack.peek() == popped[j]) {
            stack.pop();
            j++;
        }
    }
    return stack.isEmpty();
}
```

>    Push karte jao. Jab top match kare popped se, pop karo aur j++. End mein stack empty hona chahiye.

---

### 17. Minimum Add to Make Parentheses Valid
**Problem:** Kitne parentheses add karne hain string valid banane ke liye?

**Approach:** Count open unmatched parentheses.

```java
public int minAddToMakeValid(String s) {
    int open = 0, close = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') {
            open++;
        } else {
            if (open > 0) open--;
            else close++;
        }
    }
    return open + close;
}
```

>    `(` mila toh open++, `)` mila agar open >0 toh open-- warna close++. Total = open + close.

---

### 18. Score of Parentheses
**Problem:** `()` = 1, `(A)` = 2*A, `AB` = A+B. Calculate score.

**Approach:** Stack mein scores store karo. `)` pe calculate karo.

```java
public int scoreOfParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(') {
            stack.push(-1);  // Marker for '('
        } else {
            if (stack.peek() == -1) {
                stack.pop();
                stack.push(1);
            } else {
                int sum = 0;
                while (stack.peek() != -1) {
                    sum += stack.pop();
                }
                stack.pop();  // Remove '('
                stack.push(sum * 2);
            }
        }
    }
    int total = 0;
    while (!stack.isEmpty()) total += stack.pop();
    return total;
}
```

>    `(` ko -1 se mark karo. `)` pe agar top -1 hai toh 1 push, warna sum karke 2*sum push karo.

---

### 19. Reverse Substrings Between Parentheses
**Problem:** `"(ed(et(oc))el)"` → `"leetcode"`

**Approach:** Stack mein indexes store karo, reverse karte jao.

```java
public String reverseParentheses(String s) {
    Stack<Integer> stack = new Stack<>();
    char[] arr = s.toCharArray();
    
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == '(') {
            stack.push(i);
        } else if (arr[i] == ')') {
            int start = stack.pop();
            reverse(arr, start + 1, i - 1);
        }
    }
    
    StringBuilder sb = new StringBuilder();
    for (char c : arr) {
        if (c != '(' && c != ')') sb.append(c);
    }
    return sb.toString();
}

private void reverse(char[] arr, int left, int right) {
    while (left < right) {
        char temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        left++;
        right--;
    }
}
```

>    `(` ka index stack mein daalo. `)` pe uske corresponding substring reverse kardo. End mein parentheses hata do.

---

### 20. Find the Most Competitive Subsequence
**Problem:** k length ka smallest subsequence (lexicographically).

**Approach:** Monotonic stack, but extra elements ko remove karne ki permission limited hai.

```java
public int[] mostCompetitive(int[] nums, int k) {
    Stack<Integer> stack = new Stack<>();
    int toRemove = nums.length - k;  // Kitne elements remove kar sakte hain
    
    for (int num : nums) {
        while (!stack.isEmpty() && stack.peek() > num && toRemove > 0) {
            stack.pop();
            toRemove--;
        }
        stack.push(num);
    }
    
    // Sirf first k elements chahiye
    while (stack.size() > k) stack.pop();
    
    int[] res = new int[k];
    for (int i = k - 1; i >= 0; i--) {
        res[i] = stack.pop();
    }
    return res;
}
```

>    Monotonic increasing stack. Jab bada element ho aur remove kar sakte ho toh pop karo. End mein sirf k elements rakhna hai.


## 🎯 Interview Tips for Hard Stack Problems

1. **Identify monotonic stack:** Jab "next greater/smaller", "previous greater/smaller", "histogram area" ho, monotonic stack use karo.

2. **Multiple passes:** Kabhi left se, kabhi right se, kabhi do baar traverse karo (circular cases mein).

3. **Stack mein kya store karein?**
   - Value only: Simple comparisons
   - Index: Distance calculate karne ke liye
   - Custom object: Multiple properties store karne ke liye

4. **Corner cases handle karo:**
   - Empty input
   - All same values
   - Negative numbers
   - k = 0 or k = n

5. **Space vs Time trade-off:** Kabhi extra stack use karo for O(n) time, warna O(n²) mein solve ho sakta hai.

