# 🔹 Problem: Min Stack

Design a stack that supports:

* `push(int val)`
* `pop()`
* `top()`
* `getMin()` → **in O(1) time**

---

### ✅ Example

**Input:**

```java
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
```

**Output:**

```java
[null,null,null,null,-3,null,0,-2]
```

---

### 💡 Approach

We use **two stacks**:

1. `stack1` — normal stack to store all values.
2. `stack2` — auxiliary stack that keeps track of the current **minimum** value at each level.

* On `push(val)`:
  → Push `val` into `stack1`.
  → If `val <= stack2.peek()`, also push it to `stack2`.

* On `pop()`:
  → If popped value equals `stack2.peek()`, pop from `stack2` too.

* `top()`: return top of `stack1`.

* `getMin()`: return top of `stack2` (current minimum).

---

### 🔧 Java Code

```java
class MinStack {
    Stack<Integer> stack1;
    Stack<Integer> stack2;

    public MinStack() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        stack2.push(Integer.MAX_VALUE); // Initialize with max value
    }

    public void push(int val) {
        stack1.push(val);
        if (val <= stack2.peek()) {
            stack2.push(val);
        }
    }

    public void pop() {
        int popped = stack1.pop();
        if (popped == stack2.peek()) {
            stack2.pop();
        }
    }

    public int top() {
        return stack1.peek();
    }

    public int getMin() {
        return stack2.peek();
    }
}
```

---

### 🧠 Complexity

* **Time:** O(1) for all operations.
* **Space:** O(n) for both stacks.
Here it is in the format you requested:

---

# Problem: Generate Parentheses

You are given an integer **n** representing the number of pairs of parentheses. Your task is to generate all combinations of well-formed (valid) parentheses consisting of **n** pairs.

---

### Example:

**Input:**
`n = 3`

**Output:**
`["((()))","(()())","(())()","()(())","()()()"]`

**Explanation:**
All possible strings with 3 pairs of parentheses that are properly opened and closed.

---

### Solution Code with Comments:

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        generate("", n, n, ans);  // Start with empty string and n open and close parentheses available
        return ans;
    }

    private void generate(String str, int open, int close, List<String> ans) {
        // Base case: when no more parentheses to add, add current string to answer list
        if (open == 0 && close == 0) {
            ans.add(str);
            return;
        }

        // If we still have open parentheses left, add '(' and recurse
        if (open > 0) {
            generate(str + "(", open - 1, close, ans);
        }

        // We can add ')' only if close > open to maintain valid sequence
        if (close > open) {
            generate(str + ")", open, close - 1, ans);
        }
    }
}
```

---
---

# Problem: Car Fleet

You are given **n** cars, each at a certain position on a one-dimensional road starting from mile 0, heading towards a target mile. Each car has its own speed.

**Rules:**

* Cars cannot pass each other.
* When a faster car catches up to a slower car, they form a "car fleet" and travel together at the slower car's speed.
* A car fleet is defined as one or more cars traveling together at the same speed.
* If a car catches up exactly at the target, it is considered part of the fleet.

**Goal:**
Return the number of car fleets that will arrive at the target.

---

### Example:

**Input:**
`target = 12`
`position = [10,8,0,5,3]`
`speed = [2,4,1,1,3]`

**Output:**
`3`

**Explanation:**

* Cars at positions 10 (speed 2) and 8 (speed 4) meet exactly at the target and form one fleet.
* Car at position 0 (speed 1) never catches up to others, forms its own fleet.
* Cars at 5 (speed 1) and 3 (speed 3) meet earlier at mile 6, and travel together as a fleet at speed 1.

---

### Solution Code with Comments:

```java
import java.util.Arrays;
import java.util.Stack;

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        
        // Array to hold pairs of (position, time_to_target)
        double[][] posTime = new double[n][2];
        
        // Calculate time required for each car to reach target
        for (int i = 0; i < n; i++) {
            double time = (double)(target - position[i]) / speed[i];
            posTime[i] = new double[]{position[i], time};
        }
        
        // Sort cars by their starting position in ascending order
        Arrays.sort(posTime, (a, b) -> Double.compare(a[0], b[0]));
        
        Stack<Double> stack = new Stack<>();
        
        // Traverse from the car closest to the target to the farthest
        for (int i = n - 1; i >= 0; i--) {
            // If stack is empty or current car's time is greater than the time at stack's top,
            // this car forms a new fleet, push its time on stack.
            if (stack.isEmpty() || posTime[i][1] > stack.peek()) {
                stack.push(posTime[i][1]);
            }
            // Else, this car joins the fleet represented by the top of the stack (do nothing)
        }
        
        // Number of fleets equals the number of distinct times in stack
        return stack.size();
    }
}
```

---

# Problem: Largest Rectangle in Histogram

Given an array of integers `heights` representing the heights of bars in a histogram, where each bar's width is 1, find the area of the largest rectangle that can be formed within the histogram.

---

### Example:

**Input:**
`heights = [2,1,5,6,2,3]`

**Output:**
`10`

**Explanation:**
The largest rectangle is formed by the bars with heights `[5,6]` spanning width 2, area = 5 \* 2 = 10.

---

### Approach: Using Stack to find Next Smaller Elements

* Find the **Next Smaller Element to the Left** for each bar — index of the closest smaller bar on the left.
* Find the **Next Smaller Element to the Right** for each bar — index of the closest smaller bar on the right.
* For each bar, the largest rectangle including that bar extends from the next smaller left +1 to the next smaller right -1.
* Calculate area for each bar and take the maximum.

---

### Java Code with Comments:

```java
import java.util.Stack;

class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        
        int[] nextsmallerLeft = new int[n];
        int[] nextsmallerRight = new int[n];
        
        Stack<Integer> st = new Stack<>();
        
        // Find next smaller element to the left for each bar
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            if (st.isEmpty()) {
                nextsmallerLeft[i] = -1;  // No smaller element on left
            } else {
                nextsmallerLeft[i] = st.peek();
            }
            st.push(i);
        }
        
        st.clear();  // Clear stack for next calculation
        
        // Find next smaller element to the right for each bar
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            if (st.isEmpty()) {
                nextsmallerRight[i] = n;  // No smaller element on right
            } else {
                nextsmallerRight[i] = st.peek();
            }
            st.push(i);
        }
        
        int maxArea = 0;
        
        // Calculate max area for each bar using next smaller left and right
        for (int i = 0; i < n; i++) {
            int height = heights[i];
            int width = nextsmallerRight[i] - nextsmallerLeft[i] - 1;  // Width of rectangle
            int area = height * width;
            maxArea = Math.max(maxArea, area);
        }
        
        return maxArea;
    }
}
```

---

