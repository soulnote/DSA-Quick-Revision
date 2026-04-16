# Tree Data Structure

## **Tree Basics - Theory**

Tree ek **hierarchical data structure** hai jisme nodes hote hain. Ek root node hota hai aur uske children.

**Important Terms:**
- **Root** - Topmost node
- **Parent/Child** - Direct connection
- **Leaf** - Jiske children nahi hain
- **Height** - Root se deepest leaf tak ki distance
- **Depth** - Root se node tak ki distance

**Binary Tree:** Har node ke maximum 2 children (left, right)

**Binary Search Tree (BST):** Left < Root < Right

---

## **Basic Tree Implementation**

```java
// TreeNode Class
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

// Basic Operations
class BinaryTree {
    TreeNode root;
    
    // Insert in BST
    public TreeNode insert(TreeNode root, int val) {
        if(root == null) return new TreeNode(val);
        
        if(val < root.val) {
            root.left = insert(root.left, val);
        } else if(val > root.val) {
            root.right = insert(root.right, val);
        }
        return root;
    }
    
    // Search in BST
    public boolean search(TreeNode root, int val) {
        if(root == null) return false;
        if(root.val == val) return true;
        
        if(val < root.val) return search(root.left, val);
        else return search(root.right, val);
    }
}
```

---

## **1. Tree Traversals**

### Theory 
Tree ke nodes ko visit karne ke 3 tarike hain:

1. **Inorder (Left-Root-Right):** Sorted order mein nodes milte hain BST mein
2. **Preorder (Root-Left-Right):** Copy/create tree ke liye use hota hai
3. **Postorder (Left-Right-Root):** Delete tree ke liye use hota hai
4. **Level Order (BFS):** Level by level traverse karta hai

### Java Code - All Traversals

```java
class TreeTraversals {
    
    // 1. Inorder Traversal (Recursive)
    public void inorder(TreeNode root) {
        if(root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
    
    // 2. Preorder Traversal (Recursive)
    public void preorder(TreeNode root) {
        if(root == null) return;
        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }
    
    // 3. Postorder Traversal (Recursive)
    public void postorder(TreeNode root) {
        if(root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.val + " ");
    }
    
    // 4. Level Order Traversal (BFS - Using Queue)
    public void levelOrder(TreeNode root) {
        if(root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.val + " ");
            
            if(current.left != null) queue.add(current.left);
            if(current.right != null) queue.add(current.right);
        }
    }
    
    // 5. Inorder (Iterative using Stack) - Important for interviews
    public List<Integer> inorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while(current != null || !stack.isEmpty()) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }
        return result;
    }
    
    // 6. Preorder (Iterative)
    public List<Integer> preorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            if(node.right != null) stack.push(node.right);
            if(node.left != null) stack.push(node.left);
        }
        return result;
    }
    
    // 7. Postorder (Iterative - Two stacks)
    public List<Integer> postorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        
        while(!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            if(node.left != null) stack1.push(node.left);
            if(node.right != null) stack1.push(node.right);
        }
        
        while(!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        return result;
    }
}
```

---

## **2. Maximum Depth/Height of Binary Tree** 

### Theory
**Problem:** Tree ki maximum depth (root se leaf tak ki longest path) find karo.

**Algorithm:**
- Recursively: height = 1 + max(leftHeight, rightHeight)
- Base case: null node ki height 0

**Time:** O(n) | **Space:** O(h) where h = height

### Java Code
```java
class MaxDepth {
    // Recursive
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    // Iterative (Level Order)
    public int maxDepthIterative(TreeNode root) {
        if(root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;
        
        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            for(int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
        }
        return depth;
    }
}
```

---

## **3. Invert Binary Tree** 

### Theory
**Problem:** Tree ko mirror image mein convert karo (left ↔ right swap).

**Algorithm:**
1. Recursively left subtree invert karo
2. Recursively right subtree invert karo
3. Left aur right ko swap kardo

**Example:** 
```
    4         4
   / \   →   / \
  2   7     7   2
 / \ / \   / \ / \
1 3 6 9   9 6 3 1
```

### Java Code
```java
class InvertTree {
    // Recursive
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        
        root.left = right;
        root.right = left;
        
        return root;
    }
    
    // Iterative (Using Queue)
    public TreeNode invertTreeIterative(TreeNode root) {
        if(root == null) return null;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
        }
        return root;
    }
}
```

---

## **4. Lowest Common Ancestor (LCA)**

### Theory
**Problem:** Do nodes ka lowest common ancestor dhundho (jinka subtree dono nodes ko contain kare).

**Algorithm (BST):**
- Agar dono nodes root se chote hain → left mein search karo
- Agar dono bade hain → right mein search karo
- Nahi to root hi LCA hai (ek left mein, ek right mein)

**Algorithm (Binary Tree):**
- Base case: root == null ya root == p ya root == q → return root
- Left mein search karo, right mein search karo
- Agar left se null nahi aur right se null nahi → root hi LCA hai

### Java Code
```java
class LCA {
    // For BST (Optimized)
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        while(root != null) {
            if(p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if(p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
    
    // For Binary Tree (General)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if(left != null && right != null) return root;
        return left != null ? left : right;
    }
}
```

---

## **5. Binary Tree Level Order Traversal**

### Theory
**Problem:** Level wise nodes return karo (each level as separate list).

**Algorithm (BFS):**
1. Queue use karo
2. Har level ka size note karo
3. Saare nodes of that level process karo
4. Children ko queue mein add karo

### Java Code
```java
class LevelOrderTraversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for(int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
            result.add(currentLevel);
        }
        return result;
    }
    
    // Reverse Level Order (Bottom-up)
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = levelOrder(root);
        Collections.reverse(result);
        return result;
    }
    
    // Zig-Zag Level Order (Spiral)
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean leftToRight = true;
        
        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> currentLevel = new LinkedList<>();
            
            for(int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if(leftToRight) {
                    currentLevel.addLast(node.val);
                } else {
                    currentLevel.addFirst(node.val);
                }
                
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        return result;
    }
}
```

---

## **6. Validate Binary Search Tree**

### Theory
**Problem:** Check karo ki tree valid BST hai ya nahi.

**Properties of BST:**
- Left subtree ke saare nodes < root
- Right subtree ke saare nodes > root
- Left aur right subtree bhi BST hone chahiye

**Important:** Sirf left < root < right check karna sufficient nahi hai. Saare nodes range mein hone chahiye.

### Java Code
```java
class ValidateBST {
    // Method 1: Using Range (Min-Max)
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Integer min, Integer max) {
        if(node == null) return true;
        
        if((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }
        
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }
    
    // Method 2: Using Inorder Traversal
    // BST ka inorder hamesha sorted hota hai
    Integer prev = null;
    
    public boolean isValidBSTInorder(TreeNode root) {
        if(root == null) return true;
        
        if(!isValidBSTInorder(root.left)) return false;
        
        if(prev != null && root.val <= prev) return false;
        prev = root.val;
        
        return isValidBSTInorder(root.right);
    }
}
```

---

## **7. Symmetric Tree (Mirror Tree)**

### Theory
**Problem:** Check karo ki tree apna mirror image hai ya nahi.

**Algorithm:**
- Do trees compare karo: left subtree aur right subtree
- left.left compare with right.right
- left.right compare with right.left

### Java Code
```java
class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        return isMirror(root.left, root.right);
    }
    
    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return true;
        if(t1 == null || t2 == null) return false;
        
        return (t1.val == t2.val) && 
               isMirror(t1.left, t2.right) && 
               isMirror(t1.right, t2.left);
    }
    
    // Iterative using Queue
    public boolean isSymmetricIterative(TreeNode root) {
        if(root == null) return true;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        
        while(!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            
            if(t1 == null && t2 == null) continue;
            if(t1 == null || t2 == null) return false;
            if(t1.val != t2.val) return false;
            
            queue.add(t1.left);
            queue.add(t2.right);
            queue.add(t1.right);
            queue.add(t2.left);
        }
        return true;
    }
}
```

---

## **8. Diameter of Binary Tree**

### Theory
**Problem:** Tree ka diameter - do nodes ke beech ki longest path (edges count).

**Important:** Diameter necessarily root se nahi guzarti. Kisi bhi do nodes ke beech ki longest path.

**Algorithm:**
- Har node ke liye: leftHeight + rightHeight calculate karo
- Maximum lete jao
- Height recursively calculate karte waqt diameter update karo

### Java Code
```java
class DiameterOfTree {
    int diameter = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        calculateHeight(root);
        return diameter;
    }
    
    private int calculateHeight(TreeNode node) {
        if(node == null) return 0;
        
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);
        
        // Update diameter: left + right path through this node
        diameter = Math.max(diameter, leftHeight + rightHeight);
        
        // Return height
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

---

## **9. Path Sum**

### Theory
**Problem:** Check karo ki root se leaf tak koi path hai jiska sum targetSum ke equal ho.

**Types:**
1. **Path Sum I:** Root to leaf path sum equals target
2. **Path Sum II:** Saare paths return karo jinka sum target hai
3. **Path Sum III:** Koi bhi path (root to leaf necessary nahi)

### Java Code
```java
class PathSum {
    // Path Sum I - Check if exists
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null) return false;
        
        if(root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
    
    // Path Sum II - Return all paths
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        findPaths(root, targetSum, new ArrayList<>(), result);
        return result;
    }
    
    private void findPaths(TreeNode node, int sum, List<Integer> path, 
                          List<List<Integer>> result) {
        if(node == null) return;
        
        path.add(node.val);
        
        if(node.left == null && node.right == null && sum == node.val) {
            result.add(new ArrayList<>(path));
        } else {
            findPaths(node.left, sum - node.val, path, result);
            findPaths(node.right, sum - node.val, path, result);
        }
        
        path.remove(path.size() - 1); // Backtrack
    }
    
    // Path Sum III - Any path (not necessarily root to leaf)
    // Prefix sum method - O(n)
    int count = 0;
    Map<Integer, Integer> prefixSum = new HashMap<>();
    
    public int pathSumIII(TreeNode root, int targetSum) {
        prefixSum.put(0, 1);
        dfs(root, 0, targetSum);
        return count;
    }
    
    private void dfs(TreeNode node, int currSum, int target) {
        if(node == null) return;
        
        currSum += node.val;
        count += prefixSum.getOrDefault(currSum - target, 0);
        prefixSum.put(currSum, prefixSum.getOrDefault(currSum, 0) + 1);
        
        dfs(node.left, currSum, target);
        dfs(node.right, currSum, target);
        
        prefixSum.put(currSum, prefixSum.get(currSum) - 1);
    }
}
```

---

## **10. Construct Binary Tree from Traversals**

### Theory
**Problem:** Inorder aur Preorder/Postorder se tree construct karo.

**Key Points:**
- **Preorder + Inorder:** Preorder ka first element root hota hai
- **Postorder + Inorder:** Postorder ka last element root hota hai
- Inorder mein root find karo → left aur right subtree divide karo

### Java Code
```java
class BuildTree {
    // From Preorder and Inorder
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for(int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildPreIn(preorder, 0, preorder.length - 1, 
                         inorder, 0, inorder.length - 1, inorderMap);
    }
    
    private TreeNode buildPreIn(int[] preorder, int preStart, int preEnd,
                                int[] inorder, int inStart, int inEnd,
                                Map<Integer, Integer> inorderMap) {
        if(preStart > preEnd || inStart > inEnd) return null;
        
        TreeNode root = new TreeNode(preorder[preStart]);
        int inRoot = inorderMap.get(root.val);
        int leftSize = inRoot - inStart;
        
        root.left = buildPreIn(preorder, preStart + 1, preStart + leftSize,
                              inorder, inStart, inRoot - 1, inorderMap);
        root.right = buildPreIn(preorder, preStart + leftSize + 1, preEnd,
                               inorder, inRoot + 1, inEnd, inorderMap);
        
        return root;
    }
    
    // From Postorder and Inorder
    public TreeNode buildTreePostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for(int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildPostIn(postorder, 0, postorder.length - 1,
                          inorder, 0, inorder.length - 1, inorderMap);
    }
    
    private TreeNode buildPostIn(int[] postorder, int postStart, int postEnd,
                                 int[] inorder, int inStart, int inEnd,
                                 Map<Integer, Integer> inorderMap) {
        if(postStart > postEnd || inStart > inEnd) return null;
        
        TreeNode root = new TreeNode(postorder[postEnd]);
        int inRoot = inorderMap.get(root.val);
        int leftSize = inRoot - inStart;
        
        root.left = buildPostIn(postorder, postStart, postStart + leftSize - 1,
                               inorder, inStart, inRoot - 1, inorderMap);
        root.right = buildPostIn(postorder, postStart + leftSize, postEnd - 1,
                                inorder, inRoot + 1, inEnd, inorderMap);
        
        return root;
    }
}
```

---

## **11. Binary Tree Right Side View**

### Theory
**Problem:** Right side se dekho to sirf rightmost nodes dikhenge har level pe.

**Algorithm:**
- Level order traversal karo
- Har level ka last node add karo result mein

### Java Code
```java
class RightSideView {
    // BFS Approach
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for(int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if(i == levelSize - 1) { // Last node of level
                    result.add(node.val);
                }
                
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
        }
        return result;
    }
    
    // DFS Approach (More efficient)
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, result, 0);
        return result;
    }
    
    private void dfs(TreeNode node, List<Integer> result, int level) {
        if(node == null) return;
        
        if(level == result.size()) {
            result.add(node.val);
        }
        
        dfs(node.right, result, level + 1);
        dfs(node.left, result, level + 1);
    }
}
```

---

## **12. Flatten Binary Tree to Linked List**

### Theory
**Problem:** Binary tree ko linked list mein convert karo (in-place, preorder order mein).

**Algorithm (Morris-like approach):**
1. Root se start karo
2. Agar left exists karta hai to:
   - Left subtree ka rightmost node dhundho
   - Right subtree ko uske right mein attach karo
   - Left subtree ko right mein move karo
   - Left ko null karo

### Java Code
```java
class FlattenTree {
    // Method 1: Recursive
    TreeNode prev = null;
    
    public void flatten(TreeNode root) {
        if(root == null) return;
        
        flatten(root.right);
        flatten(root.left);
        
        root.right = prev;
        root.left = null;
        prev = root;
    }
    
    // Method 2: Iterative (Morris Traversal style)
    public void flattenIterative(TreeNode root) {
        TreeNode current = root;
        
        while(current != null) {
            if(current.left != null) {
                TreeNode rightmost = current.left;
                while(rightmost.right != null) {
                    rightmost = rightmost.right;
                }
                rightmost.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }
    
    // Method 3: Using Stack
    public void flattenStack(TreeNode root) {
        if(root == null) return;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            if(node.right != null) stack.push(node.right);
            if(node.left != null) stack.push(node.left);
            
            if(!stack.isEmpty()) {
                node.right = stack.peek();
            }
            node.left = null;
        }
    }
}
```

---

## **13. Kth Smallest Element in BST**

### Theory
**Problem:** BST mein kth smallest element find karo.

**Algorithm:**
- Inorder traversal (sorted order) karo
- Kth element return karo

**Optimization:** Agar tree frequently modify hota hai to augment tree with size

### Java Code
```java
class KthSmallest {
    // Method 1: Inorder Traversal
    int count = 0;
    int result = 0;
    
    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }
    
    private void inorder(TreeNode node, int k) {
        if(node == null) return;
        
        inorder(node.left, k);
        count++;
        if(count == k) {
            result = node.val;
            return;
        }
        inorder(node.right, k);
    }
    
    // Method 2: Iterative Inorder (More efficient - O(h + k))
    public int kthSmallestIterative(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        int count = 0;
        
        while(current != null || !stack.isEmpty()) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }
            
            current = stack.pop();
            count++;
            if(count == k) return current.val;
            
            current = current.right;
        }
        return -1;
    }
}
```

---

## **14. Populating Next Right Pointers**

### Theory
**Problem:** Har node mein next right pointer set karo jo uske immediate right neighbor ko point kare.

**Types:**
- **Perfect Binary Tree:** O(1) space possible
- **Any Binary Tree:** Queue use karo

### Java Code
```java
class Node {
    int val;
    Node left;
    Node right;
    Node next;
    
    Node(int val) { this.val = val; }
}

class ConnectNextRight {
    // For Perfect Binary Tree (O(1) space)
    public Node connectPerfect(Node root) {
        if(root == null) return null;
        
        Node leftmost = root;
        
        while(leftmost.left != null) {
            Node current = leftmost;
            
            while(current != null) {
                current.left.next = current.right;
                
                if(current.next != null) {
                    current.right.next = current.next.left;
                }
                
                current = current.next;
            }
            
            leftmost = leftmost.left;
        }
        return root;
    }
    
    // For Any Binary Tree (Using BFS)
    public Node connectAny(Node root) {
        if(root == null) return null;
        
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            int size = queue.size();
            
            for(int i = 0; i < size; i++) {
                Node node = queue.poll();
                
                if(i < size - 1) {
                    node.next = queue.peek();
                }
                
                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
        }
        return root;
    }
    
    // Optimized O(1) space for any tree
    public Node connectOptimized(Node root) {
        Node dummy = new Node(0);
        Node current = root;
        Node prev = dummy;
        
        while(current != null) {
            if(current.left != null) {
                prev.next = current.left;
                prev = prev.next;
            }
            if(current.right != null) {
                prev.next = current.right;
                prev = prev.next;
            }
            
            current = current.next;
            
            if(current == null) {
                current = dummy.next;
                dummy.next = null;
                prev = dummy;
            }
        }
        return root;
    }
}
```

---

## **15. Serialize and Deserialize Binary Tree**

### Theory
**Problem:** Tree ko string mein convert karo (serialize) aur wapas tree banao (deserialize).

**Algorithm:**
- **BFS Approach:** Level order traversal use karo, null ke liye special marker
- **DFS Approach:** Preorder traversal use karo

### Java Code
```java
class SerializeDeserialize {
    // BFS Approach
    public String serialize(TreeNode root) {
        if(root == null) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if(node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
    
    public TreeNode deserialize(String data) {
        if(data.equals("[]")) return null;
        
        String[] values = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        int i = 1;
        while(!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            if(!values[i].equals("null")) {
                node.left = new TreeNode(Integer.parseInt(values[i]));
                queue.add(node.left);
            }
            i++;
            
            if(i < values.length && !values[i].equals("null")) {
                node.right = new TreeNode(Integer.parseInt(values[i]));
                queue.add(node.right);
            }
            i++;
        }
        return root;
    }
    
    // DFS Preorder Approach
    public String serializeDFS(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if(node == null) {
            sb.append("null,");
            return;
        }
        
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    public TreeNode deserializeDFS(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(queue);
    }
    
    private TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if(val.equals("null")) return null;
        
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
    }
}
```

---

## **Quick Revision Table**

| Problem | Time | Space | Key Concept |
|---------|------|-------|-------------|
| Traversals | O(n) | O(h) | Recursion/Stack |
| Max Depth | O(n) | O(h) | Recursive height |
| Invert Tree | O(n) | O(h) | Swap children |
| LCA | O(n) | O(h) | Postorder traversal |
| Validate BST | O(n) | O(h) | Range checking |
| Diameter | O(n) | O(h) | Height + max |
| Path Sum | O(n) | O(h) | Backtracking |
| Build Tree | O(n) | O(n) | HashMap + recursion |
| Right Side View | O(n) | O(n) | Last node of level |
| Flatten | O(n) | O(1) | Morris style |
| Kth Smallest | O(h+k) | O(h) | Inorder traversal |
| Serialize | O(n) | O(n) | BFS/DFS + marker |

---

## **Interview Tips **

1. **Recursion pe pakad honi chahiye** - Trees ke 90% problems recursion se solve hoti hain
2. **Base case mat bhoolna** - `if(root == null) return` sabse important line hai
3. **Postorder traversal** - Jab children se parent mein information flow karna ho (diameter, LCA)
4. **Preorder traversal** - Jab parent se children mein flow ho (flatten, serialize)
5. **Inorder in BST** - Sorted order milta hai, bahut useful hai
6. **BFS vs DFS** - Level wise chahiye to BFS (queue), depth wise to DFS (stack/recursion)
7. **Null handling** - Null nodes ko handle karna mat bhoolna
8. **Space complexity** - Recursive ke liye O(h) stack space lagta hai, iterative try karo agar overflow ka dar ho

---
# Tree Questions - Time & Space Complexity Analysis Guide 

## 📊 Complexity Analysis Kaise Karte Hain - Step by Step

---

## **Basic Concepts Pehle Samajhte Hain**

### **Tree mein kya affect karta hai complexity?**

1. **n** = total number of nodes
2. **h** = height of tree (worst case: n, best case: log n)
3. **Recursion stack** - function calls jo memory mein store hoti hain

### **Different Trees ki Heights:**

```
Balanced Tree (BST):     h = O(log n)
Skewed Tree (Linked List): h = O(n)
Perfect Binary Tree:     h = O(log n)
```

---

## **Time Complexity - Kaise Calculate Karein?**

### **Rule 1: Har node ko exactly ek baar visit karte hain**

Most tree traversals mein har node ko **ek baar** process karte hain:

```java
void traverse(TreeNode node) {
    if(node == null) return;
    // O(1) kaam
    traverse(node.left);
    traverse(node.right);
}
```

**Time = O(n)** kyunki n nodes hain aur har node pe O(1) kaam ho raha hai.

---

### **Rule 2: Recursion mein kaam ka distribution**

```java
// Example: Max Depth
int maxDepth(TreeNode root) {
    if(root == null) return 0;
    int left = maxDepth(root.left);   // T(left subtree)
    int right = maxDepth(root.right); // T(right subtree)
    return 1 + Math.max(left, right); // O(1)
}
```

**Recurrence Relation:** T(n) = T(left) + T(right) + O(1)

**Case 1 - Balanced Tree:**
- left ≈ n/2, right ≈ n/2
- T(n) = 2T(n/2) + O(1)
- **Time = O(n)** (Master theorem se)

**Case 2 - Skewed Tree:**
- left = n-1, right = 0
- T(n) = T(n-1) + O(1)
- **Time = O(n)**

---

### **Rule 3: Har node pe O(k) kaam ho to**

```java
// Example: Level Order mein har level process karte hue
for(int i = 0; i < levelSize; i++) {  // levelSize = O(n) worst case
    // O(1) kaam
}
```

**Analysis:**
- Total nodes = n
- Har node ek baar queue mein aata hai
- Har node pe O(1) kaam
- **Time = O(n)**

---

## **Space Complexity - Kaise Calculate Karein?**

### **Space ke 3 sources:**

1. **Recursion Stack** - function calls ke liye
2. **Explicit Data Structures** - Queue, Stack, ArrayList
3. **Output Storage** - result store karne ke liye

---

### **Source 1: Recursion Stack Space**

```java
void inorder(TreeNode node) {
    if(node == null) return;
    inorder(node.left);   // Recursive call
    System.out.print(node.val);
    inorder(node.right);  // Recursive call
}
```

**Stack Space = Height of tree (h)**

- **Balanced Tree:** O(log n)
- **Skewed Tree:** O(n)

**Kyun?** Maximum kitne recursive calls stack mein ek saath reh sakte hain? = Tree ki height

```
Example Skewed Tree (1-2-3-4-5):
Call Stack:
inorder(5)
inorder(4)
inorder(3)
inorder(2)
inorder(1)  <- 5 calls simultaneously, height = 5 = n
```

---

### **Source 2: Explicit Data Structures**

```java
// Level Order Traversal
Queue<TreeNode> queue = new LinkedList<>();
queue.add(root);  // Queue mein nodes store hote hain
```

**Queue size kaise calculate karein?**

- Worst case: Last level ke saare nodes queue mein aa sakte hain
- Last level mein maximum nodes = n/2 (in binary tree)
- **Space = O(n)**

---

### **Source 3: Output Storage**

```java
List<List<Integer>> result = new ArrayList<>();  // Saare nodes store honge
result.add(currentLevel);  // Total O(n) space
```

**Output space usually O(n)** hota hai kyunki saare nodes store kar rahe hain.

---

## **Important Patterns with Examples**

### **Pattern 1: Simple Traversal**

```java
// Inorder Traversal
void inorder(TreeNode root) {
    if(root == null) return;
    inorder(root.left);
    System.out.print(root.val);
    inorder(root.right);
}
```

**Complexity:**
- **Time:** O(n) - har node ek baar visit
- **Space:** O(h) - recursion stack
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

### **Pattern 2: Divide and Conquer**

```java
// Diameter of Binary Tree
int diameter = 0;
int height(TreeNode node) {
    if(node == null) return 0;
    int left = height(node.left);   // Left subtree process
    int right = height(node.right); // Right subtree process
    diameter = Math.max(diameter, left + right);
    return 1 + Math.max(left, right);
}
```

**Complexity:**
- **Time:** O(n) - har node pe O(1) kaam + recursion
- **Space:** O(h) - recursion stack

**Kyun O(n) hai?** 
- Har node exactly ek baar visit ho raha hai
- Height function recursively call ho raha hai but total calls = 2n (approx)

---

### **Pattern 3: BFS (Level Order)**

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    
    while(!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
        }
        result.add(level);
    }
    return result;
}
```

**Complexity Breakdown:**

| Component | Complexity | Explanation |
|-----------|------------|-------------|
| **Time** | O(n) | Har node queue mein ek baar aata hai |
| **Space (Queue)** | O(w) where w = max width | Worst case last level: O(n) |
| **Space (Result)** | O(n) | Saare nodes store kar rahe hain |
| **Total Space** | O(n) | w ≤ n, so O(n) |

---

### **Pattern 4: Path Finding with Backtracking**

```java
// Path Sum II
void findPaths(TreeNode node, int sum, List<Integer> path, List<List<Integer>> result) {
    if(node == null) return;
    
    path.add(node.val);  // O(1)
    
    if(node.left == null && node.right == null && sum == node.val) {
        result.add(new ArrayList<>(path));  // O(path length)
    }
    
    findPaths(node.left, sum - node.val, path, result);
    findPaths(node.right, sum - node.val, path, result);
    
    path.remove(path.size() - 1);  // O(1)
}
```

**Complexity:**
- **Time:** O(n²) worst case?
  - Actually O(n) for traversal
  - But copying path for each leaf takes O(path length)
  - If tree is skewed, path length = O(n), leaves = O(n)
  - **Better analysis:** O(n²) worst case, O(n log n) average

- **Space:** O(h) for recursion + O(n) for output
  - Path length = O(h)
  - Result stores O(n) nodes

---

### **Pattern 5: Building Tree from Traversals**

```java
TreeNode build(int[] preorder, int preStart, int preEnd,
               int[] inorder, int inStart, int inEnd,
               Map<Integer, Integer> map) {
    if(preStart > preEnd) return null;
    
    TreeNode root = new TreeNode(preorder[preStart]);
    int inRoot = map.get(root.val);
    int leftSize = inRoot - inStart;
    
    root.left = build(preorder, preStart + 1, preStart + leftSize,
                     inorder, inStart, inRoot - 1, map);
    root.right = build(preorder, preStart + leftSize + 1, preEnd,
                      inorder, inRoot + 1, inEnd, map);
    
    return root;
}
```

**Complexity:**
- **Time:** O(n) - har node ek baar create ho raha hai
- **Space:** O(n) - recursion stack (skewed tree mein) + HashMap O(n)

**Kyun O(n) time?** 
- Recurrence: T(n) = T(k) + T(n-k-1) + O(1)
- Total calls = n, har call pe O(1) kaam

---

## **Common Mistakes in Complexity Analysis**

### **Mistake 1: Sirf loops dekhte hain, recursion bhool jaate hain**

```java
// GALAT Analysis
void printAllPaths(TreeNode root) {
    if(root == null) return;
    printAllPaths(root.left);
    printAllPaths(root.right);
    // Loop to print path - O(h)
}
```

**Galat:** "Loop hai to O(n*h)"  
**Sahi:** Har node pe loop chal raha hai of O(h), total O(n*h)

---

### **Mistake 2: Stack space bhool jaate hain**

```java
void inorder(TreeNode root) {
    if(root == null) return;
    inorder(root.left);
    inorder(root.right);
}
```

**Galat:** "Sirf O(1) extra space"  
**Sahi:** Recursion stack O(h) space leta hai

---

### **Mistake 3: Worst case vs Average case confuse karte hain**

**Balanced Tree:**
- Height = O(log n)
- Space = O(log n)

**Skewed Tree:**
- Height = O(n)
- Space = O(n)

**Interview mein dono batao!**

---

## **Quick Reference Table - Common Operations**

| Operation | Time Complexity | Space Complexity | Notes |
|-----------|----------------|------------------|-------|
| **Traversal (Recursive)** | O(n) | O(h) | h = height |
| **Traversal (Iterative)** | O(n) | O(h) | Explicit stack |
| **Level Order (BFS)** | O(n) | O(n) | Queue size = max width |
| **Search in BST** | O(h) | O(1) iterative | O(h) recursive |
| **Insert in BST** | O(h) | O(1) iterative | O(h) recursive |
| **Find Height** | O(n) | O(h) | Recursive |
| **LCA** | O(n) | O(h) | Recursive |
| **Diameter** | O(n) | O(h) | Single pass |
| **Serialize/Deserialize** | O(n) | O(n) | Queue/Stack used |
| **Build Tree from Traversals** | O(n) | O(n) | HashMap + recursion |

---

## **Complexity Cheat Sheet by Problem Type**

### **Type 1: Simple Recursion (Traversal, Search)**
```
Time: O(n)  - Har node ek baar
Space: O(h) - Recursion stack
```

### **Type 2: Divide & Conquer (Height, Diameter, LCA)**
```
Time: O(n)  - Har node ek baar process
Space: O(h) - Recursion stack
```

### **Type 3: BFS/Level Order**
```
Time: O(n)     - Har node ek baar
Space: O(width) - Queue mein last level
Width max = O(n) for skewed? No, width = O(n) for perfect tree's last level
```

### **Type 4: Backtracking (Path Sum II)**
```
Time: O(n²) worst - Copying paths
Space: O(h) + O(n) - Stack + output
```

### **Type 5: Morris Traversal (Constant Space)**
```
Time: O(n)  - No extra space
Space: O(1) - No stack or recursion!
```

---

## **Interview mein Kaise Explain Karein?**

### **Step-by-Step Explanation Format:**

**Example: Validate BST**

```java
public boolean isValidBST(TreeNode root) {
    return validate(root, null, null);
}
```

**Interview Answer:**

> "Time complexity **O(n)** hai because hum har node ko exactly ek baar visit kar rahe hain. Har node pe O(1) kaam ho raha hai - bas range check karna aur recursion call karna.

> Space complexity **O(h)** hai jahan h tree ki height hai. Yeh recursion stack ki wajah se hai. Best case mein balanced tree ke liye O(log n) hoga, worst case mein skewed tree ke liye O(n) ho sakta hai.

> Hum O(1) extra space mein bhi kar sakte hain iterative inorder traversal se, lekin woh bhi explicit stack use karega jo O(h) hi lega."

---

## **Practice Questions - Analyze Yourself**

Try to analyze these:

1. **Find if two trees are identical**
2. **Count all nodes in a tree**
3. **Find the maximum element in a tree**
4. **Print all root-to-leaf paths**
5. **Check if a tree is a subtree of another tree**

**Answers:**

| Problem | Time | Space | Reason |
|---------|------|-------|--------|
| Identical Trees | O(min(n1, n2)) | O(h) | Compare node by node |
| Count Nodes | O(n) | O(h) | Simple traversal |
| Max Element | O(n) | O(h) | Traversal with comparison |
| Root to Leaf Paths | O(n * h) | O(h * number of paths) | Printing each path |
| Subtree Check | O(m * n) worst | O(h) | For each node, check match |

---

## **Pro Tips for Interviews**

1. **Hamesha worst case batao** - Interviewer worst case expect karta hai

2. **Agar recursion hai to stack space mention karo** - "Recursion stack O(h) lega"

3. **Space mein output ko include karo?** - Usually nahi, unless specifically kaha jaye

4. **Agar iterative solution hai to compare karo** - "Iterative O(h) space lega same as recursion, but stack overflow ka risk nahi"

5. **Balanced vs Skewed dono discuss karo** - Shows depth of understanding

---

## **Memory Aid - Yaad Rakhne Ke Liye**

```
Recursion → O(h) space
BFS/Queue → O(width) space
Har node ek baar → O(n) time
Divide & Conquer → O(n) time
```

**Golden Rule:** 
> Agar har node pe constant kaam ho raha hai, time = O(n)
> Agar har node pe O(k) kaam ho raha hai, time = O(n * k)

## Most Asked 20 Hard Recursion Problems in Interview

Here are the **most frequently asked hard Recursion/Backtracking problems** in FAANG and tier-1 company interviews, with **Java solutions** and **explanations**.

---

### 1. N-Queens
**Problem:** Place N queens on N×N chessboard so no two attack each other.

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (int i = 0; i < n; i++) {
        Arrays.fill(board[i], '.');
    }
    solve(result, board, 0, n);
    return result;
}

private void solve(List<List<String>> result, char[][] board, int row, int n) {
    if (row == n) {
        result.add(construct(board));
        return;
    }
    
    for (int col = 0; col < n; col++) {
        if (isSafe(board, row, col, n)) {
            board[row][col] = 'Q';
            solve(result, board, row + 1, n);
            board[row][col] = '.'; // backtrack
        }
    }
}

private boolean isSafe(char[][] board, int row, int col, int n) {
    // Check column
    for (int i = 0; i < row; i++) {
        if (board[i][col] == 'Q') return false;
    }
    // Check diagonal (top-left)
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
        if (board[i][j] == 'Q') return false;
    }
    // Check diagonal (top-right)
    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
        if (board[i][j] == 'Q') return false;
    }
    return true;
}
```

 Har row mein ek queen rakhni hai. Har column try karo, agar safe hai toh queen rakh do, next row pe jao. Agar koi solution nahi milta toh backtrack karo (queen hata do).

---

### 2. Sudoku Solver
**Problem:** Fill empty cells ('.') with digits 1-9 following Sudoku rules.

```java
public void solveSudoku(char[][] board) {
    solve(board);
}

private boolean solve(char[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] == '.') {
                for (char c = '1'; c <= '9'; c++) {
                    if (isValid(board, i, j, c)) {
                        board[i][j] = c;
                        if (solve(board)) return true;
                        board[i][j] = '.'; // backtrack
                    }
                }
                return false; // No valid number found
            }
        }
    }
    return true; // Board is full
}

private boolean isValid(char[][] board, int row, int col, char c) {
    for (int i = 0; i < 9; i++) {
        if (board[row][i] == c) return false; // Check row
        if (board[i][col] == c) return false; // Check column
        int boxRow = 3 * (row / 3) + i / 3;
        int boxCol = 3 * (col / 3) + i % 3;
        if (board[boxRow][boxCol] == c) return false; // Check 3x3 box
    }
    return true;
}
```

 Pehla empty cell dhundho, usme 1 se 9 tak numbers try karo. Agar number valid hai toh daal do aur agle empty cell ke liye recursion call karo. Agar baad mein koi solution nahi milta toh backtrack karke next number try karo.

---

### 3. Generate Parentheses
**Problem:** Generate all combinations of n well-formed parentheses.

```java
public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, "", 0, 0, n);
    return result;
}

private void backtrack(List<String> result, String curr, int open, int close, int n) {
    if (curr.length() == 2 * n) {
        result.add(curr);
        return;
    }
    
    if (open < n) {
        backtrack(result, curr + "(", open + 1, close, n);
    }
    if (close < open) {
        backtrack(result, curr + ")", open, close + 1, n);
    }
}
```

 Open brackets ka count < n hai toh '(' add karo. Close brackets ka count < open count hai toh ')' add karo. Jab length 2n ho jaye toh result mein add karo.

---

### 4. Combination Sum
**Problem:** Find all unique combinations where numbers sum to target (can reuse same number).

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp, 
                       int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remaining - candidates[i], i); // Not i+1 (reuse allowed)
        temp.remove(temp.size() - 1);
    }
}
```

 Remaining target se current number subtract karte jao. Agar 0 ho jaye toh solution mil gaya. Agar negative ho jaye toh return. `i` pass karo because same element dobara use kar sakte ho.

---

### 5. Combination Sum II
**Problem:** Find combinations where sum = target, each number used once, no duplicates.

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] candidates, int remaining, int start) {
    if (remaining < 0) return;
    if (remaining == 0) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = start; i < candidates.length; i++) {
        if (i > start && candidates[i] == candidates[i - 1]) continue; // Skip duplicates
        temp.add(candidates[i]);
        backtrack(result, temp, candidates, remaining - candidates[i], i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Pehle sort karo. Agar same element consecutive hai toh skip karo (duplicate combination se bachne ke liye). `i+1` pass karo because same element dobaara use nahi kar sakte.

---

### 6. Permutations
**Problem:** Generate all permutations of distinct integers.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        temp.remove(temp.size() - 1);
        used[i] = false;
    }
}
```

 `used` array maintain karo ki kaun se elements already use ho chuke hain. Har unused element ko try karo, use mark karo, recursion karo, phir unmark karo (backtrack).

---

### 7. Permutations II (With Duplicates)
**Problem:** Generate all unique permutations with duplicate numbers.

```java
public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    backtrack(result, new ArrayList<>(), nums, used);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack(result, temp, nums, used);
        temp.remove(temp.size() - 1);
        used[i] = false;
    }
}
```

 Sort karo. Agar current element previous ke equal hai aur previous used nahi hai toh skip karo (duplicate permutations avoid karne ke liye).

---

### 8. Subsets
**Problem:** Generate all possible subsets (power set).

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    
    for (int i = start; i < nums.length; i++) {
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Har recursion call mein current subset result mein add karo. Fir remaining elements mein se ek element choose karo, add karo, recursion karo, phir remove karo.

---

### 9. Subsets II (With Duplicates)
**Problem:** Generate unique subsets with duplicate numbers.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int[] nums, int start) {
    result.add(new ArrayList<>(temp));
    
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;
        temp.add(nums[i]);
        backtrack(result, temp, nums, i + 1);
        temp.remove(temp.size() - 1);
    }
}
```

 Sort karo. Consecutive duplicates ko skip karo same level pe.

---

### 10. Palindrome Partitioning
**Problem:** Partition string into palindromic substrings.

```java
public List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<List<String>> result, List<String> temp,
                       String s, int start) {
    if (start == s.length()) {
        result.add(new ArrayList<>(temp));
        return;
    }
    
    for (int end = start + 1; end <= s.length(); end++) {
        String substring = s.substring(start, end);
        if (isPalindrome(substring)) {
            temp.add(substring);
            backtrack(result, temp, s, end);
            temp.remove(temp.size() - 1);
        }
    }
}

private boolean isPalindrome(String s) {
    int i = 0, j = s.length() - 1;
    while (i < j) {
        if (s.charAt(i++) != s.charAt(j--)) return false;
    }
    return true;
}
```

 String ko different lengths mein cut karo. Agar cut part palindrome hai toh temp mein add karo, baaki string ke liye recursion call karo.

---

### 11. Word Search
**Problem:** Check if word exists in 2D grid (adjacent cells).

```java
public boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

private boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (index == word.length()) return true;
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false;
    if (board[i][j] != word.charAt(index)) return false;
    
    char temp = board[i][j];
    board[i][j] = '#'; // Mark as visited
    
    boolean found = dfs(board, word, i + 1, j, index + 1) ||
                    dfs(board, word, i - 1, j, index + 1) ||
                    dfs(board, word, i, j + 1, index + 1) ||
                    dfs(board, word, i, j - 1, index + 1);
    
    board[i][j] = temp; // Backtrack
    return found;
}
```

 Har cell se start karo. 4 directions mein DFS karo. Current cell ko mark karo visited (temporarily) taki dobaara na use ho. Agar word complete ho jaye toh true return karo.

---

### 12. Restore IP Addresses
**Problem:** Generate all valid IP addresses from string.

```java
public List<String> restoreIpAddresses(String s) {
    List<String> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

private void backtrack(List<String> result, List<String> temp, 
                       String s, int start) {
    if (temp.size() == 4) {
        if (start == s.length()) {
            result.add(String.join(".", temp));
        }
        return;
    }
    
    for (int i = 1; i <= 3 && start + i <= s.length(); i++) {
        String segment = s.substring(start, start + i);
        if (isValid(segment)) {
            temp.add(segment);
            backtrack(result, temp, s, start + i);
            temp.remove(temp.size() - 1);
        }
    }
}

private boolean isValid(String segment) {
    if (segment.length() > 1 && segment.charAt(0) == '0') return false;
    int num = Integer.parseInt(segment);
    return num >= 0 && num <= 255;
}
```

 String ko 4 parts mein baantna hai. Har part length 1-3 ki ho sakti hai aur value 0-255 ke beech mein honi chahiye. Leading zero allowed nahi hai (except "0" itself).

---

### 13. Letter Combinations of a Phone Number
**Problem:** Return all letter combinations for phone digits.

```java
private static final String[] MAPPING = {
    "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
};

public List<String> letterCombinations(String digits) {
    if (digits.isEmpty()) return new ArrayList<>();
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), digits, 0);
    return result;
}

private void backtrack(List<String> result, StringBuilder sb, 
                       String digits, int index) {
    if (index == digits.length()) {
        result.add(sb.toString());
        return;
    }
    
    String letters = MAPPING[digits.charAt(index) - '0'];
    for (char c : letters.toCharArray()) {
        sb.append(c);
        backtrack(result, sb, digits, index + 1);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

 Har digit ke corresponding letters pe loop karo. Ek letter choose karo, aage badho, backtrack karo.

---

### 14. Factor Combinations
**Problem:** Find all combinations of factors of n (excluding 1 and n).

```java
public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), n, 2);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> temp,
                       int n, int start) {
    if (n == 1) {
        if (temp.size() > 1) {
            result.add(new ArrayList<>(temp));
        }
        return;
    }
    
    for (int i = start; i <= Math.sqrt(n); i++) {
        if (n % i == 0) {
            temp.add(i);
            backtrack(result, temp, n / i, i);
            temp.remove(temp.size() - 1);
        }
    }
    
    // Add the last factor
    if (n >= start) {
        temp.add(n);
        backtrack(result, temp, 1, start);
        temp.remove(temp.size() - 1);
    }
}
```

 `n` ke factors dhundho (i se sqrt(n) tak). Factor milne pe temp mein add karo, `n/i` ke liye recursion karo. End mein last factor add karo.

---

### 15. Gray Code
**Problem:** Generate n-bit Gray code sequence.

```java
public List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    result.add(0);
    backtrack(result, n, 1);
    return result;
}

private void backtrack(List<Integer> result, int n, int size) {
    if (size == 1 << n) return;
    
    int prev = result.get(size - 1);
    for (int i = 0; i < n; i++) {
        int next = prev ^ (1 << i);
        if (!result.contains(next)) {
            result.add(next);
            backtrack(result, n, size + 1);
            return; // Found valid next number
        }
    }
}
```

 Last number se ek bit flip karke next number generate karo. Agar woh number already sequence mein nahi hai toh use add karo aur recursion continue karo.

---

### 16. Remove Invalid Parentheses
**Problem:** Remove minimum parentheses to make string valid.

```java
public List<String> removeInvalidParentheses(String s) {
    List<String> result = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();
    
    queue.offer(s);
    visited.add(s);
    boolean found = false;
    
    while (!queue.isEmpty()) {
        String curr = queue.poll();
        
        if (isValid(curr)) {
            result.add(curr);
            found = true;
        }
        
        if (found) continue;
        
        for (int i = 0; i < curr.length(); i++) {
            if (curr.charAt(i) != '(' && curr.charAt(i) != ')') continue;
            String next = curr.substring(0, i) + curr.substring(i + 1);
            if (!visited.contains(next)) {
                visited.add(next);
                queue.offer(next);
            }
        }
    }
    return result;
}

private boolean isValid(String s) {
    int count = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') count++;
        else if (c == ')') {
            count--;
            if (count < 0) return false;
        }
    }
    return count == 0;
}
```

 BFS approach - har level pe ek character remove karo. Pehle level jahan valid string milegi wahi minimum removals honge. Duplicate strings avoid karne ke liye visited set use karo.

---

### 17. Word Break II
**Problem:** Add spaces to break string into dictionary words.

```java
public List<String> wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet<>(wordDict);
    Map<String, List<String>> memo = new HashMap<>();
    return dfs(s, dict, memo);
}

private List<String> dfs(String s, Set<String> dict, Map<String, List<String>> memo) {
    if (memo.containsKey(s)) return memo.get(s);
    
    List<String> result = new ArrayList<>();
    if (dict.contains(s)) result.add(s);
    
    for (int i = 1; i < s.length(); i++) {
        String prefix = s.substring(0, i);
        if (dict.contains(prefix)) {
            List<String> suffixes = dfs(s.substring(i), dict, memo);
            for (String suffix : suffixes) {
                result.add(prefix + " " + suffix);
            }
        }
    }
    
    memo.put(s, result);
    return result;
}
```

 String ko prefix aur suffix mein baanto. Agar prefix dictionary mein hai toh suffix ke liye recursion call karo. Memoization use karo taki same substring baar baar na compute ho.

---

### 18. Expression Add Operators
**Problem:** Add operators (+, -, *) to make expression equal target.

```java
public List<String> addOperators(String num, int target) {
    List<String> result = new ArrayList<>();
    if (num == null || num.length() == 0) return result;
    backtrack(result, new StringBuilder(), num, target, 0, 0, 0);
    return result;
}

private void backtrack(List<String> result, StringBuilder expr, String num,
                       int target, int index, long eval, long prev) {
    if (index == num.length()) {
        if (eval == target) result.add(expr.toString());
        return;
    }
    
    for (int i = index; i < num.length(); i++) {
        if (i != index && num.charAt(index) == '0') break; // No leading zero
        
        long curr = Long.parseLong(num.substring(index, i + 1));
        int len = expr.length();
        
        if (index == 0) {
            expr.append(curr);
            backtrack(result, expr, num, target, i + 1, curr, curr);
            expr.setLength(len);
        } else {
            // Try '+'
            expr.append("+").append(curr);
            backtrack(result, expr, num, target, i + 1, eval + curr, curr);
            expr.setLength(len);
            
            // Try '-'
            expr.append("-").append(curr);
            backtrack(result, expr, num, target, i + 1, eval - curr, -curr);
            expr.setLength(len);
            
            // Try '*'
            expr.append("*").append(curr);
            backtrack(result, expr, num, target, i + 1, 
                     eval - prev + prev * curr, prev * curr);
            expr.setLength(len);
        }
    }
}
```

 Multiplication ke liye special handling chahiye because operator precedence. `prev` variable store karo last term ko. Multiplication ke time pe `eval - prev + prev * curr` karo.

---

### 19. Partition to K Equal Sum Subsets
**Problem:** Partition array into k subsets with equal sum.

```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) return false;
    int target = sum / k;
    Arrays.sort(nums);
    boolean[] used = new boolean[nums.length];
    return backtrack(nums, used, k, 0, target, 0, nums.length - 1);
}

private boolean backtrack(int[] nums, boolean[] used, int k, int currSum,
                          int target, int start, int index) {
    if (k == 1) return true;
    if (currSum == target) {
        return backtrack(nums, used, k - 1, 0, target, 0, nums.length - 1);
    }
    
    for (int i = start; i >= 0; i--) {
        if (!used[i] && currSum + nums[i] <= target) {
            used[i] = true;
            if (backtrack(nums, used, k, currSum + nums[i], target, i - 1, index)) {
                return true;
            }
            used[i] = false;
        }
    }
    return false;
}
```

 Pehle total sum calculate karo, target = sum/k. Ek subset mein target sum achieve karne ke baad next subset ke liye recursion call karo. Backtracking se saare subsets fill karo.

---

### 20. Beautiful Arrangement
**Problem:** Count permutations where for each i, either i % nums[i] == 0 or nums[i] % i == 0.

```java
public int countArrangement(int n) {
    boolean[] used = new boolean[n + 1];
    return backtrack(used, 1, n);
}

private int backtrack(boolean[] used, int pos, int n) {
    if (pos > n) return 1;
    
    int count = 0;
    for (int i = 1; i <= n; i++) {
        if (!used[i] && (i % pos == 0 || pos % i == 0)) {
            used[i] = true;
            count += backtrack(used, pos + 1, n);
            used[i] = false;
        }
    }
    return count;
}
```

 Position `pos` pe, unused numbers mein se woh number select karo jo condition satisfy kare (`i % pos == 0` ya `pos % i == 0`). Phir next position pe jao.

---

## 📌 Key Recursion/Backtracking Patterns 

| Pattern | Key Insight | Example Problems |
|---------|-------------|------------------|
| **Choose/Not Choose** | Har element ko include/exclude karo | Subsets, Combination Sum |
| **Permutation** | `used` array, har unused element try karo | Permutations, N-Queens |
| **Decision Tree** | Har decision pe branch karo | Generate Parentheses, Letter Combinations |
| **Grid DFS** | 4 directions, mark visited | Word Search, Word Search II |
| **Partitioning** | String/Array ko different cuts pe break karo | Palindrome Partitioning, Restore IP |
| **Pruning** | Early termination for invalid branches | Sudoku Solver, N-Queens |

## ⚡ Quick Tips

1. **Base Case** - Recursion rokne ki condition (usually end of array/string)
2. **Backtrack** - State restore karo after recursion call (remove last element, unmark visited)
3. **Pruning** - Invalid paths ko early terminate karo (performance boost)
4. **Memoization** - Overlapping subproblems ke liye cache use karo
5. **Sorting** - Duplicates handle karne ke liye sort karo aur consecutive duplicates skip karo
