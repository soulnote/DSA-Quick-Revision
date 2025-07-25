# Top BST Interview Questions aur Unke Solutions (Hinglish Mein)
Yahan BST ke top interview questions aur unke Java solutions hain, sabhi ek hi Markdown format mein. Maine har solution ke liye TreeNode class ko include kiya hai, taaki har code block self-contained rahe.
```java
// Ye TreeNode class sabhi solutions mein use hogi.

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```
# 1. Validate BST (Check karein ki valid BST hai ya nahi)
Problem: Diye gaye binary tree ka root check karein ki woh ek valid Binary Search Tree (BST) hai ya nahi.

Approach: Hum ek recursive helper function use karte hain jo har node ke liye ek minVal aur maxVal range pass karta hai. Har node ka value is range ke andar hona chahiye. Left child ke liye maxVal current node ka value ban jata hai, aur right child ke liye minVal current node ka value ban jata hai.
```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true;
        }

        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }

        boolean isLeftSubtreeValid = isValidBSTHelper(node.left, minVal, node.val);
        if (!isLeftSubtreeValid) {
            return false;
        }

        boolean isRightSubtreeValid = isValidBSTHelper(node.right, node.val, maxVal);
        if (!isRightSubtreeValid) {
            return false;
        }

        return true;
    }
}
```

# 2. Search in a BST (BST mein value dhundo)
Problem: BST ke root aur ek value di gayi hai, check karein ki value BST mein exist karti hai ya nahi.

Approach: BST ki ordering property ka use karte hain. Agar current node ka value search kiye jaane wale value se chhota hai, to right jao; agar bada hai, to left jao. Jab tak value na mil jaye ya null na ho jaye.
```java
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        while (root != null) {
            if (root.val == val) {
                return root;
            } else if (root.val < val) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return null;
    }
}
```
# 3. Insert into a BST (BST mein value daalo)
Problem: BST ke root aur ek value di gayi hai, value ko BST mein sahi position par insert karein.

Approach: Recursively tree ko traverse karte hain jab tak null pointer na mil jaye, jo naye node ke liye sahi insertion point hoga. Agar val current node se chhota hai, to left jao; agar bada hai, to right jao.
```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else { // val > root.val (assuming no duplicates, or duplicates go to right)
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }
}
```
# 4. Delete Node in a BST (BST se node delete karo)
Problem: BST ke root aur ek value di gayi hai, us value wale node ko BST se delete karein.

Approach: Ismein teen main cases hain:

No child / One child: Node ko uske child (ya null) se replace kar do.

Two children: Node ko uske in-order successor (right subtree ka sabse chhota element) se replace karo, phir successor ko uski original position se delete karo.
```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else { // key == root.val
            // Case 1: No child or one child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Case 2: Two children
            // Find the in-order successor (minimum node in the right subtree)
            TreeNode minNodeInRightSubtree = findMin(root.right);
            root.val = minNodeInRightSubtree.val; // Replace current node's value with successor's value

            // Delete the in-order successor from its original position
            root.right = deleteNode(root.right, minNodeInRightSubtree.val);
        }
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
```

# 5. Lowest Common Ancestor (LCA) of a BST (BST mein do nodes ka sabse chhota common ancestor)
Problem: BST ke root aur do nodes p aur q diye gaye hain, unka Lowest Common Ancestor (LCA) dhundo.

Approach: BST ki ordering property ka use karte hain. Agar p aur q dono current node se chhote hain, to LCA left subtree mein hoga. Agar dono current node se bade hain, to LCA right subtree mein hoga. Otherwise, current node hi LCA hai (kyunki p aur q current node ke opposite sides mein hain ya unmein se koi ek current node hi hai).
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }
}
```
# 6. Kth Smallest Element in a BST (BST mein Kth sabse chhota element)
Problem: BST ke root aur ek integer k diya gaya hai, BST mein Kth sabse chhota element dhundo.

Approach: BST ka in-order traversal hamesha elements ko sorted order mein deta hai. Hum iterative in-order traversal perform karte hain aur k elements ko count karte hain. Jab count k ke barabar ho jaye, to wahi element hamara answer hai.
```java
import java.util.Stack;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        int count = 0;

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            count++;

            if (count == k) {
                return root.val;
            }

            root = root.right;
        }
        return -1; // Should not be reached for valid k
    }
}
```
# 7. In-order Successor/Predecessor (BST mein In-order Successor/Predecessor)
Problem: BST ke root aur ek node p diya gaya hai, p ka in-order successor/predecessor dhundo.

Approach (Successor):

Agar p ka right child hai, to successor right subtree ka sabse chhota element hoga.

Agar p ka right child nahi hai, to successor uska ancestor hoga jiske left subtree mein p hai. Hum root se traverse karte hain, aur jab bhi left jate hain, current node ko potential successor mark karte hain.
```java
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode successor = null;

        while (root != null) {
            if (p.val < root.val) {
                successor = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return successor;
    }

    // In-order Predecessor ke liye similar logic
    public TreeNode inorderPredecessor(TreeNode root, TreeNode p) {
        TreeNode predecessor = null;
        TreeNode current = root;
        while (current != null) {
            if (p.val > current.val) {
                predecessor = current;
                current = current.right;
            } else if (p.val < current.val) {
                current = current.left;
            } else { // current.val == p.val, we found p
                break;
            }
        }
        return predecessor;
    }

    // Helper to find max in a subtree (for predecessor with left child)
    private TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
}
```
# 8. Convert Sorted Array to BST (Sorted Array ko BST mein badlo)
Problem: Ek sorted array diya gaya hai, use height-balanced Binary Search Tree (BST) mein convert karein.

Approach: Array ke middle element ko root banate hain. Phir recursively array ke left half se left subtree aur right half se right subtree banate hain.
```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return convertToBST(nums, 0, nums.length - 1);
    }

    private TreeNode convertToBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);

        node.left = convertToBST(nums, left, mid - 1);
        node.right = convertToBST(nums, mid + 1, right);

        return node;
    }
}
```
# 9. Two Sum IV - Input is a BST (BST mein Two Sum)
Problem: BST ke root aur ek target sum k diya gaya hai, check karein ki BST mein do aise elements hain jinka sum k ke barabar hai.

Approach: BST ka in-order traversal perform karke saare elements ko ek sorted ArrayList mein store karo. Phir us sorted ArrayList par standard two-pointer approach apply karo.
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public boolean findTarget(TreeNode root, int k) {
        List<Integer> nums = new ArrayList<>();
        inorderTraversal(root, nums);

        int left = 0;
        int right = nums.size() - 1;

        while (left < right) {
            int sum = nums.get(left) + nums.get(right);
            if (sum == k) {
                return true;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    private void inorderTraversal(TreeNode node, List<Integer> nums) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, nums);
        nums.add(node.val);
        inorderTraversal(node.right, nums);
    }
}
```
# 10. Range Sum of BST (BST mein Range Sum)
Problem: BST ke root aur do integers low aur high diye gaye hain, unke beech ke saare node values ka sum return karein (inclusive).

Approach: DFS traversal karte hain. Agar current node ka value low aur high ke beech mein hai, to use sum mein add karte hain. BST ki property ka use karke branches ko prune karte hain:

Agar node.val > low hai, to left subtree mein jao.

Agar node.val < high hai, to right subtree mein jao.
```java
class Solution {
    private int sum = 0;

    public int rangeSumBST(TreeNode root, int low, int high) {
        sum = 0;
        dfs(root, low, high);
        return sum;
    }

    private void dfs(TreeNode node, int low, int high) {
        if (node == null) {
            return;
        }

        if (node.val >= low && node.val <= high) {
            sum += node.val;
        }

        if (node.val > low) { // Pruning: Agar current node low se bada hai, to left mein bhi relevant values ho sakte hain.
            dfs(node.left, low, high);
        }

        if (node.val < high) { // Pruning: Agar current node high se chhota hai, to right mein bhi relevant values ho sakte hain.
            dfs(node.right, low, high);
        }
    }
}
```

# 11. Check if two trees are identical/same BSTs (Check karein ki do trees identical hain ya nahi)
Problem: Do binary trees p aur q ke roots diye gaye hain, check karein ki wo structurally identical hain aur unke corresponding nodes ki values bhi same hain.

Approach: Recursively dono trees ko traverse karte hain. Har step par check karte hain:

Agar dono nodes null hain, to true.

Agar ek null aur dusra nahi, to false.

Agar dono non-null hain lekin values alag hain, to false.

Otherwise, left subtrees aur right subtrees ko recursively check karo.
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
