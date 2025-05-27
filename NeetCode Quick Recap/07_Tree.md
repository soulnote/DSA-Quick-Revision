# ⭐ Diameter of Binary Tree

---

Given the `root` of a binary tree, return the **length of the diameter** of the tree.

* The **diameter** is the length of the **longest path** between any two nodes in the tree.
* This path **may or may not** pass through the root.
* The **length** is defined as the **number of edges** between the two nodes.

---

### 📌 Example

#### Input:

```
    1
   / \
  2   3
 / \
4   5
```

#### Output:

```
3
```

**Explanation**: The longest path can be either `4 → 2 → 1 → 3` or `5 → 2 → 1 → 3`.
Both have **3 edges**, so the answer is `3`.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    int diameter = 0; // To store the maximum diameter

    public int diameterOfBinaryTree(TreeNode root) {
        depthHeight(root); // Start DFS from root
        return diameter;   // Return the result
    }

    // This function returns the height of the tree
    public int depthHeight(TreeNode root) {
        if (root == null) return 0; // Base case: empty tree

        // Recursively get height of left and right subtrees
        int left = depthHeight(root.left);
        int right = depthHeight(root.right);

        // Update diameter at this node (sum of left + right heights)
        diameter = Math.max(diameter, left + right);

        // Return height of this node
        return 1 + Math.max(left, right);
    }
}
```
# ⭐ Subtree of Another Tree

---

Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a **subtree** of `root` with the **same structure and node values** as `subRoot`, and `false` otherwise.

* A **subtree** of a binary tree is a node and all of its descendants.
* A tree can be a subtree of itself.

---

### 📌 Example

#### Input:

```
root:        3
            / \
           4   5
          / \
         1   2

subRoot:     4
            / \
           1   2
```

#### Output:

```
true
```

**Explanation**: The subtree rooted at node `4` in `root` is identical to `subRoot`.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // If both trees are null, they match
        if (root == null && subRoot == null) return true;

        // If one is null, they can't match
        if (root == null || subRoot == null) return false;

        // If current root matches subRoot, check if trees are identical
        if (isSameTree(root, subRoot)) return true;

        // Otherwise, check left and right subtrees recursively
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // Helper to check if two trees are exactly the same
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Both null → match
        if (p == null && q == null) return true;

        // One null → mismatch
        if (p == null || q == null) return false;

        // Values and subtrees must match
        return (p.val == q.val) &&
               isSameTree(p.left, q.left) &&
               isSameTree(p.right, q.right);
    }
}
```

# ⭐ Lowest Common Ancestor of a Binary Search Tree

---

Given a **Binary Search Tree (BST)**, find the **Lowest Common Ancestor (LCA)** of two given nodes `p` and `q`.

* The **LCA** is defined as the **lowest node** in the BST that has both `p` and `q` as descendants.
* A node can be a descendant of itself.

---

### 📌 Example

#### Input:

```
BST:         6
           /   \
          2     8
         / \   / \
        0   4 7   9
           / \
          3   5

p = 2, q = 8
```

#### Output:

```
6
```

**Explanation**: Nodes `2` and `8` are on opposite sides of node `6`, so `6` is their lowest common ancestor.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Lists to store paths from root to p and q
        ArrayList<TreeNode> listP = new ArrayList<>();
        ArrayList<TreeNode> listQ = new ArrayList<>();

        // Traverse the tree to fill the paths
        pathTraverse(root, p, listP);
        pathTraverse(root, q, listQ);

        // Find the last common node in both paths
        int n = Math.min(listP.size(), listQ.size());
        int idx = 0;
        while (idx < n && listP.get(idx) == listQ.get(idx)) {
            idx++;
        }

        // Return the last common ancestor
        return listP.get(idx - 1);
    }

    // Helper to build path from root to target node
    public void pathTraverse(TreeNode root, TreeNode p, ArrayList<TreeNode> list) {
        if (root == null) return;

        list.add(root); // Add current node to path

        if (root.val == p.val) return;

        // Traverse according to BST property
        if (root.val < p.val) {
            pathTraverse(root.right, p, list);
        } else {
            pathTraverse(root.left, p, list);
        }
    }
}
```

# ⭐ Construct Binary Tree from Preorder and Inorder Traversal

---

Given two integer arrays `preorder` and `inorder` that represent the **preorder** and **inorder** traversals of a binary tree, construct and return the **binary tree**.

* **Preorder traversal**: Root → Left → Right
* **Inorder traversal**: Left → Root → Right
* The elements are **unique** and appear exactly once.

---

### 📌 Example

#### Input:

```
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]
```

#### Output (Tree Structure):

```
        3
       / \
      9  20
         / \
        15  7
```

**Explanation**: The constructed binary tree follows the given traversal orders.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        // Map to store inorder value -> index for quick lookup
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        // Index pointer to track root in preorder traversal
        int[] index = {0};

        // Build the tree recursively
        return helper(preorder, inorder, 0, preorder.length - 1, map, index);
    }

    private static TreeNode helper(int[] preorder, int[] inorder, int left, int right,
                                   HashMap<Integer, Integer> map, int[] index) {
        // Base case: no nodes to construct
        if (left > right) return null;

        // Get the current root value from preorder
        int curr = preorder[index[0]];
        index[0]++; // Move to next root

        // Create the root node
        TreeNode node = new TreeNode(curr);

        // If there's only one node, it's a leaf
        if (left == right) return node;

        // Get the index of the root in inorder array
        int inorderIndex = map.get(curr);

        // Recursively construct left and right subtrees
        node.left = helper(preorder, inorder, left, inorderIndex - 1, map, index);
        node.right = helper(preorder, inorder, inorderIndex + 1, right, map, index);

        return node;
    }
}
```

# ⭐ Binary Tree Maximum Path Sum

---

Given the `root` of a binary tree, return the **maximum path sum** of any **non-empty path**.

* A **path** is a sequence of nodes connected by edges.
* Each node in the path can appear **at most once**.
* The path **does not have to pass through the root**.
* The **path sum** is the total sum of all nodes in the path.

---

### 📌 Example

#### Input:

```
    1
   / \
  2   3
```

#### Output:

```
6
```

**Explanation**: The optimal path is `2 → 1 → 3` with a path sum of `2 + 1 + 3 = 6`.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    private int maxi = Integer.MIN_VALUE; // Global variable to store the max path sum

    public int maxPathSum(TreeNode root) {
        height(root); // Start DFS traversal
        return maxi;  // Return the highest path sum found
    }

    // Recursive function to calculate max path sum rooted at current node
    public int height(TreeNode root) {
        if (root == null) return 0; // Base case: empty node contributes 0

        // Compute max sum from left and right subtrees (ignore negative paths)
        int left = Math.max(0, height(root.left));
        int right = Math.max(0, height(root.right));

        // Update max path sum including both left and right with current node
        maxi = Math.max(maxi, left + right + root.val);

        // Return max path that includes current node and one of its subtrees
        return root.val + Math.max(left, right);
    }
}
```
# ⭐ Serialize and Deserialize Binary Tree

---

Design an algorithm to **serialize** a binary tree to a string and **deserialize** the string back to the original binary tree structure.

* Serialization converts the tree into a string.
* Deserialization reconstructs the tree from the string.
* The approach can be any valid method that ensures the original tree can be fully recovered.

---

### 📌 Example

#### Input:

```
    1
   / \
  2   3
     / \
    4   5
```

#### Serialized Output:

```
"1,2,N,N,3,4,N,N,5,N,N"
```

**Explanation**:

* `N` represents a null node.
* This preorder-based serialization stores root, then left subtree, then right subtree.

---

### ✅ Java Code with Comments

```java
// Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Codec {

    // Encodes a tree to a single string using preorder traversal
    public String serialize(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfsSerialize(root, res);
        return String.join(",", res);
    }

    private void dfsSerialize(TreeNode node, List<String> res) {
        if (node == null) {
            res.add("N");  // Null marker
            return;
        }
        res.add(String.valueOf(node.val)); // Add node value
        dfsSerialize(node.left, res);      // Serialize left subtree
        dfsSerialize(node.right, res);     // Serialize right subtree
    }

    // Decodes encoded data to tree
    public TreeNode deserialize(String data) {
        List<String> vals = Arrays.asList(data.split(","));
        Iterator<String> iterator = vals.iterator();
        return dfsDeserialize(iterator);
    }

    private TreeNode dfsDeserialize(Iterator<String> iterator) {
        if (!iterator.hasNext()) return null;

        String val = iterator.next();
        if (val.equals("N")) {
            return null;  // Null node found
        }

        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = dfsDeserialize(iterator);   // Reconstruct left subtree
        node.right = dfsDeserialize(iterator);  // Reconstruct right subtree
        return node;
    }
}
```


