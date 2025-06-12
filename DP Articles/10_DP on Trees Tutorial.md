
# DP on Trees Tutorial


## DP on Trees Kya Hai?
DP on Trees ek dynamic programming technique hai jisme hum tree ke nodes pe DFS (Depth-First Search) ke sath DP lagate hain. Har node ka result uske children (subtrees) ke results se compute hota hai, aur overlapping subproblems ko memoization ya state passing se handle karte hain.

**Examples:**
1. Diameter of Binary Tree
2. Maximum Path Sum
3. House Robber III (Tree version)
4. Count of BSTs with N nodes
5. Binary Tree Cameras

**Common Theme:**
- DFS use hota hai to traverse tree bottom-up.
- Har node ke liye states define hote hain jo subtree ke results capture karte hain.
- Memoization ya return values se overlapping subproblems handle hote hain.

---

## Intuition Kaise Build Kare?
DP on Trees problems ko samajhne ke liye ye socho:
- Tum ek tree ke node pe ho, aur tumhe us node ka result compute karna hai based on uske left aur right subtrees ke results.
- Har node ke liye decide karo: subtree ke results kaise combine honge, aur kya store karna hai (jaise max path, count, ya cameras).
- DFS se bottom-up traverse karo, taki leaf nodes se start ho aur root tak jaye.

For example:
- **Diameter of Binary Tree** mein tumhe longest path find karna hai jo kisi node se guzarta ho.
- **Binary Tree Cameras** mein minimum cameras place karne hain to cover all nodes.

**Key Intuition:**
- State define karo jo current node aur uske subtree ka result bataye.
- Transition formula socho: left aur right subtree ke results kaise combine honge.
- Base cases set karo jo leaf nodes ya null nodes handle kare.

---

## General Approach
DP on Trees problems ko solve karne ke steps:
1. **State Define Karo:**
   - Har node ke liye kya compute karna hai? For example, Maximum Path Sum mein node ke through max path sum.
2. **Transition Formula Likho:**
   - Current node ka result left aur right subtree se kaise banega? For example, Diameter mein `max(left_height + right_height, max(left_diameter, right_diameter))`.
3. **Base Cases Set Karo:**
   - Leaf nodes ya null nodes ke liye kya return hoga? For example, null node ka height = 0.
4. **Top-Down ya Bottom-Up Choose Karo:**
   - Top-Down: Recursive DFS with memoization
   - Bottom-Up: Post-order traversal (implicitly bottom-up)

---

## Top-Down vs Bottom-Up: Konsa Better Hai?
### Top-Down (Memoization with DFS)
- **Kab Use Karo?**
  - Jab states complex hon aur memoization se overlapping subproblems handle ho, jaise Count of BSTs.
  - Jab recursive DFS natural lage.
- **Pros:**
  - Code intuitive hota hai, tree traversal clear rahta hai.
  - Sirf needed subproblems solve hote hain with memoization.
- **Cons:**
  - Memoization array bada ho sakta hai.
  - Stack overflow ka risk bade trees pe.

### Bottom-Up (Post-Order Traversal)
- **Kab Use Karo?**
  - Jab states simple hon aur direct DFS se solve ho sake, jaise Diameter ya Maximum Path Sum.
  - Jab memoization ki zarurat na ho kyunki har node ek baar process hota hai.
- **Pros:**
  - No extra memory for memoization.
  - Faster kyunki function calls optimized hote hain.
- **Cons:**
  - Code thoda complex lag sakta hai if states complicated hain.
  - Less flexible for problems with many states.

**Recommendation for DP on Trees:**
- **Bottom-Up** zyada common hai kyunki tree problems mein DFS naturally bottom-up hota hai, aur states aksar simple hote hain.
- Top-Down use karo jab memoization se complex states (jaise Count of BSTs) handle karne hon.

---

## Problem 1: Diameter of Binary Tree
### Problem Statement:
Ek binary tree diya hai. Iske diameter (longest path between any two nodes) ki length find karo.

**Example:**
```
Input: root = [1,2,3,4,5]
Output: 4
Explanation: Path [4,2,1,3] or [5,2,1,3] has length 4.
```

### Intuition:
- Diameter ya to left subtree mein hai, ya right subtree mein, ya left aur right ke through root se guzarta hai.
- State: Har node ke liye height aur diameter compute karo.
- Transition: Diameter = max(left_height + right_height, max(left_diameter, right_diameter)).
- Base cases: Null node ka height = 0, diameter = 0.

### Tree Diagram:
```
       1
      / \
     2   3
    / \
   4   5
Node 2: height = 2, diameter = max(1+1, max(0,0)) = 2
Node 1: height = 3, diameter = max(2+1, max(2,0)) = 3
```

### Bottom-Up Code:
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    private int maxDiameter;
    
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        height(root);
        return maxDiameter;
    }
    
    private int height(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) return 0;
        
        // Compute heights of left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        
        // Update max diameter: path through current node or max of subtrees
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
        
        // Return height of current node
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```

### Top-Down Code:
Rarely used for this problem since bottom-up is more efficient, but possible with memoization for heights.

---

## Problem 2: Maximum Path Sum
### Problem Statement:
Ek binary tree diya hai. Max path sum find karo jo kisi node se start aur kisi node pe end ho.

**Example:**
```
Input: root = [1,2,3]
Output: 6
Explanation: Path [2,1,3] gives sum = 2 + 1 + 3 = 6.
```

### Intuition:
- Har node ke liye max path sum compute karo jo uske through jata ho.
- State: Node ke liye max single path (root to leaf) aur max path sum.
- Transition: Max path = max(left_path, 0) + max(right_path, 0) + node.val.
- Base cases: Null node ka path sum = 0.

### Tree Diagram:
```
       1
      / \
     2   3
Node 2: single_path = 2, max_path = 2
Node 3: single_path = 3, max_path = 3
Node 1: single_path = max(2,3) + 1 = 4, max_path = 2 + 3 + 1 = 6
```

### Bottom-Up Code:
```java
public class Solution {
    private int maxSum;
    
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxPathSumHelper(root);
        return maxSum;
    }
    
    private int maxPathSumHelper(TreeNode node) {
        // Base case: null node contributes 0
        if (node == null) return 0;
        
        // Compute max single paths from left and right
        int leftPath = Math.max(maxPathSumHelper(node.left), 0);
        int rightPath = Math.max(maxPathSumHelper(node.right), 0);
        
        // Update global max sum: path through current node
        maxSum = Math.max(maxSum, leftPath + rightPath + node.val);
        
        // Return max single path including current node
        return Math.max(leftPath, rightPath) + node.val;
    }
}
```

---

## Problem 3: House Robber III (Tree Version)
### Problem Statement:
Ek binary tree diya hai jisme har node ek house hai with value. Max sum find karo without robbing adjacent houses.

**Example:**
```
Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Rob houses [3,3,1] = 7.
```

### Intuition:
- Har node ke liye do options: rob ya skip.
- State: For each node, return pair (rob, skip) representing max sum.
- Transition: rob = node.val + left.skip + right.skip, skip = max(left.rob, left.skip) + max(right.rob, right.skip).
- Base cases: Null node ka rob = 0, skip = 0.

### Bottom-Up Code:
```java
public class Solution {
    public int rob(TreeNode root) {
        int[] result = robHelper(root);
        return Math.max(result[0], result[1]);
    }
    
    private int[] robHelper(TreeNode node) {
        // Base case: null node contributes nothing
        if (node == null) return new int[]{0, 0};
        
        // Get results from left and right subtrees
        int[] left = robHelper(node.left);
        int[] right = robHelper(node.right);
        
        // rob: include current node, skip children
        int rob = node.val + left[1] + right[1];
        // skip: take max of rob/skip for children
        int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        // Return pair [rob, skip]
        return new int[]{rob, skip};
    }
}
```

### Top-Down Code:
```java
public class Solution {
    private Map<TreeNode, Integer> robMemo;
    private Map<TreeNode, Integer> skipMemo;
    
    public int rob(TreeNode root) {
        robMemo = new HashMap<>();
        skipMemo = new HashMap<>();
        return Math.max(robHelper(root, true), robHelper(root, false));
    }
    
    private int robHelper(TreeNode node, boolean canRob) {
        // Base case: null node
        if (node == null) return 0;
        
        // Check memoized state
        Map<TreeNode, Integer> memo = canRob ? robMemo : skipMemo;
        if (memo.containsKey(node)) return memo.get(node);
        
        int result;
        if (canRob) {
            // Option to rob: include node, skip children
            int rob = node.val + robHelper(node.left, false) + robHelper(node.right, false);
            // Option to skip: allow children to rob or skip
            int skip = robHelper(node.left, true) + robHelper(node.right, true);
            result = Math.max(rob, skip);
        } else {
            // Must skip: children can rob or skip
            result = robHelper(node.left, true) + robHelper(node.right, true);
        }
        
        // Store in memo
        memo.put(node, result);
        return result;
    }
}
```

---

## Problem 4: Count of BSTs with N Nodes
### Problem Statement:
N nodes ke sath kitne unique BSTs ban sakte hain?

**Example:**
```
Input: n = 3
Output: 5
Explanation: 5 unique BSTs can be formed.
```

### Intuition:
- Har node ko root banao aur left/right subtrees ke combinations count karo.
- State: `dp[n]` = number of BSTs with n nodes.
- Transition: `dp[n] = sum(dp[i] * dp[n-1-i])` for i from 0 to n-1.
- Base cases: `dp[0] = 1`, `dp[1] = 1`.

### Top-Down Code:
```java
public class Solution {
    private Integer[] memo;
    
    public int numTrees(int n) {
        memo = new Integer[n+1];
        return numTreesHelper(n);
    }
    
    private int numTreesHelper(int n) {
        // Base cases: empty tree or single node
        if (n == 0 || n == 1) return 1;
        // Check memoized state
        if (memo[n] != null) return memo[n];
        
        int count = 0;
        // Try each node as root
        for (int i = 0; i < n; i++) {
            // Multiply counts of left and right subtrees
            count += numTreesHelper(i) * numTreesHelper(n-1-i);
        }
        
        return memo[n] = count;
    }
}
```

### Bottom-Up Code:
```java
public class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        
        // Base cases
        dp[0] = 1; // Empty tree
        dp[1] = 1; // Single node
        
        // Fill dp array
        for (int nodes = 2; nodes <= n; nodes++) {
            // Try each node as root
            for (int i = 0; i < nodes; i++) {
                // Left subtree: i nodes, Right subtree: nodes-1-i
                dp[nodes] += dp[i] * dp[nodes-1-i];
            }
        }
        
        return dp[n];
    }
}
```

---

## Problem 5: Binary Tree Cameras
### Problem Statement:
Ek binary tree diya hai. Minimum cameras find karo jo saare nodes ko cover karein (node, parent, ya child pe camera).

**Example:**
```
Input: root = [0,0,null,0,0]
Output: 1
Explanation: One camera at the root’s left child covers all nodes.
```

### Intuition:
- Har node ke liye 3 states: covered, has camera, needs camera.
- State: Return value indicates node’s status (0 = covered, 1 = has camera, 2 = needs camera).
- Transition: Based on children’s states, decide current node’s status.
- Base cases: Null node is covered (0).

### Bottom-Up Code:
```java
public class Solution {
    private int cameras;
    
    public int minCameraCover(TreeNode root) {
        cameras = 0;
        // If root needs camera, add one
        if (minCameraHelper(root) == 2) cameras++;
        return cameras;
    }
    
    private int minCameraHelper(TreeNode node) {
        // Base case: null node is covered
        if (node == null) return 0;
        
        // Get states of left and right children
        int left = minCameraHelper(node.left);
        int right = minCameraHelper(node.right);
        
        // If any child needs camera, place one here
        if (left == 2 || right == 2) {
            cameras++;
            return 1; // Node has camera
        }
        // If any child has camera, this node is covered
        if (left == 1 || right == 1) {
            return 0; // Node is covered
        }
        // If children are covered, this node needs camera
        return 2;
    }
}
```

### Top-Down Code:
Complex due to state dependencies; bottom-up is preferred.

---

## Conclusion
DP on Trees pattern bohot powerful hai jab nodes ke results subtrees pe depend karte hain. Har problem ka core idea alag hai, lekin approach same:
- State define karo, transition clear karo, base cases set karo.
- Bottom-Up (DFS post-order) zyada common aur efficient hai.
