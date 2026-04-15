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

## **Interview Tips (Hinglish)**

1. **Pehle brute force socho** - O(n²) solution, phir optimize karo stack se
2. **Stack mein index store karo** - value nahi, especially in histogram problems
3. **Empty check karna mat bhoolna** - `stack.isEmpty()` before `peek()/pop()`
4. **Monotonic stack** - sorted order maintain karo (increasing/decreasing)
5. **Edge cases** - empty input, single element, all same values
