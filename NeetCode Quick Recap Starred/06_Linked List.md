# ⭐ Diameter of Binary Tree

-----

Aapko ek binary tree ka `root` diya gaya hai. Aapko tree ke **diameter ki length** return karni hai.

  * **Diameter** tree mein **kisi bhi do nodes ke beech ke sabse lambe path** ki length hoti hai.
  * Yeh path `root` se hokar **guzar bhi sakta hai ya nahi bhi** guzarta hai.
  * **Length** ko do nodes ke beech ke **edges ki sankhya** ke roop mein define kiya gaya hai.

-----

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

**Explanation**: Sabse lamba path ya toh `4 → 2 → 1 → 3` ya `5 → 2 → 1 → 3` ho sakta hai. Dono mein **3 edges** hain, isliye answer `3` hai.

-----

### Intuition (Samajh):

Diameter of a binary tree ka matlab hai, tree ke kisi bhi do nodes ke beech ka sabse lamba rasta. Yeh rasta root node se guzre, zaroori nahi.
Har node par, diameter do possibilities mein se ek ho sakta hai:

1.  **Jo current node se guzarta hai:** Is case mein, diameter hoga `left_subtree_height + right_subtree_height`.
2.  **Jo current node se nahi guzarta:** Is case mein, diameter left subtree ke andar ya right subtree ke andar hi hoga (iska matlab hai ki yeh upar ke nodes se nahi judega).

Hum ek single DFS traversal se height aur diameter dono calculate kar sakte hain. Jab hum ek node ki height calculate karte hain (yani `1 + Math.max(left_child_height, right_child_height)`), tab hum us node par "diameter through this node" ko bhi update kar sakte hain (`left_child_height + right_child_height`). Global variable ka use karke hum maximum diameter ko track kar sakte hain.

-----

### Approach (Tareeka):

Hum ek recursive function `depthHeight(TreeNode root)` banayenge jo tree ki height return karega aur side-by-side global `diameter` variable ko update karega.

1.  **Global Variable:** `int diameter = 0;` ko class level par declare karo. Yeh final result store karega.

2.  **`depthHeight(TreeNode root)` Function:**

      * **Base Case:** `if (root == null) return 0;` (Ek khaali tree ki height 0 hoti hai).
      * **Recursive Calls:**
          * `int left = depthHeight(root.left);` (Left subtree ki height lao)
          * `int right = depthHeight(root.right);` (Right subtree ki height lao)
      * **Diameter Update:** Current node par jab hum left aur right subtree ki heights jaan chuke hote hain, toh is node se guzarte hue maximum path ki length `left + right` hogi. Is value ko `diameter` global variable se compare karke update kar do: `diameter = Math.max(diameter, left + right);`
      * **Return Height:** Current node ki height return karo. Height ka formula hai `1 + Math.max(left, right);` (1 current node ke liye + max height of its children).

3.  **`diameterOfBinaryTree(TreeNode root)` Function:**

      * `depthHeight(root)` ko call karo.
      * `diameter` global variable ko return kar do.

-----

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
    int diameter = 0; // Class-level variable to store the maximum diameter found so far

    public int diameterOfBinaryTree(TreeNode root) {
        // depthHeight function ko call karte hain. Yeh function har node ki height return karega,
        // aur side-by-side 'diameter' variable ko update karega jab bhi ek naya
        // lamba path milega jo current node se guzarta hai.
        depthHeight(root);
        return diameter; // Final maximum diameter return karo
    }

    // Yeh function tree ki height return karta hai.
    // Jab yeh function har node par operate karta hai, toh yeh us node se guzarte hue
    // potential diameter ko bhi calculate aur update karta hai.
    public int depthHeight(TreeNode root) {
        // Base case: Agar node null hai, toh uski height 0 hai.
        if (root == null) return 0;

        // Recursively left aur right subtrees ki height calculate karo.
        // Left variable mein left subtree ki height hogi.
        int left = depthHeight(root.left);
        // Right variable mein right subtree ki height hogi.
        int right = depthHeight(root.right);

        // Current node se guzarte hue path ka diameter calculate karo.
        // Yeh left subtree ki height + right subtree ki height hogi.
        // Phir global 'diameter' variable ko update karo agar yeh naya path lamba hai.
        diameter = Math.max(diameter, left + right);

        // Current node ki height return karo. Height = 1 (current node ke liye) +
        // left aur right subtrees mein se jo bhi lamba hai uski height.
        return 1 + Math.max(left, right);
    }
}
```

-----

# ⭐ Subtree of Another Tree

-----

Aapko do binary trees ke `root` (`root` aur `subRoot`) diye gaye hain. Aapko `true` return karna hai agar `root` ka koi **subtree** `subRoot` ke **same structure aur node values** ke saath maujood hai, aur `false` anyatha.

  * Ek binary tree ka **subtree** ek node aur uske saare descendants hote hain.
  * Ek tree khud ka bhi subtree ho sakta hai.

-----

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

**Explanation**: `root` mein node `4` par rooted subtree `subRoot` jaisa hi hai.

-----

### Intuition (Samajh):

Is problem ko solve karne ke liye hamein do cheezein karni hongi:

1.  **Har node par check karna:** `root` tree ke har node `X` par jaana padega.
2.  **Identity check:** Har node `X` par, check karna padega ki kya `X` se shuru hone wala subtree `subRoot` ke bilkul `identical` hai.

Iske liye hum do recursive functions ka use karenge:

  * `isSubtree(TreeNode root, TreeNode subRoot)`: Yeh main function hai jo `root` tree ke har node par iterate karega.
  * `isSameTree(TreeNode p, TreeNode q)`: Yeh ek helper function hai jo check karega ki do trees `p` aur `q` bilkul same hain ya nahi (same structure aur same node values).

-----

### Approach (Tareeka):

1.  **`isSubtree(TreeNode root, TreeNode subRoot)` Function:**

      * **Base Case 1:** `if (root == null && subRoot == null) return true;` (Agar dono trees null hain, toh woh match karte hain).
      * **Base Case 2:** `if (root == null || subRoot == null) return false;` (Agar ek null hai aur doosra nahi, toh woh match nahi karte).
      * **Check for Identity:** `if (isSameTree(root, subRoot)) return true;` (Agar current `root` node se shuru hone wala subtree `subRoot` ke identical hai, toh `true` return kar do).
      * **Recursive Calls:** `return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);` (Agar current `root` node par match nahi mila, toh `root` ke left subtree mein ya `root` ke right subtree mein `subRoot` ko search karo). `||` ka matlab hai, agar kisi bhi side mil gaya toh `true` return karo.

2.  **`isSameTree(TreeNode p, TreeNode q)` Helper Function:**

      * **Base Case 1:** `if (p == null && q == null) return true;` (Dono null hain, identical).
      * **Base Case 2:** `if (p == null || q == null) return false;` (Ek null hai, mismatch).
      * **Check Value and Children:** `return (p.val == q.val) && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);` (Nodes ki values same honi chahiye, aur unke left subtrees bhi identical hone chahiye, aur right subtrees bhi).

-----

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
        // Pehle check karte hain agar 'root' tree hi null hai.
        // Agar 'root' null hai, toh 'subRoot' uska subtree nahi ho sakta
        // jab tak 'subRoot' bhi null na ho.
        if (root == null) {
            // Agar root null hai, aur subRoot bhi null hai, toh woh match karte hain (empty tree is subtree of empty tree)
            return subRoot == null;
        }

        // Ab, 'root' tree ke current node par check karte hain ki kya yeh 'subRoot' ke bilkul identical hai.
        // isSameTree helper function yeh kaam karega.
        if (isSameTree(root, subRoot)) {
            return true; // Agar identical mil gaya, toh true return karo.
        }

        // Agar current 'root' node se 'subRoot' identical nahi mila,
        // toh ab 'root' ke left subtree aur right subtree mein recursively check karo.
        // '||' (OR operator) ka matlab hai ki agar left ya right kisi bhi side mil gaya, toh true return karo.
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // Helper function jo check karta hai ki do trees 'p' aur 'q' bilkul identical hain ya nahi
    // (structure aur node values dono same honi chahiye).
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Base case 1: Agar dono nodes null hain, toh woh match karte hain (identical).
        if (p == null && q == null) return true;

        // Base case 2: Agar ek node null hai aur doosra nahi, toh woh match nahi karte (not identical).
        if (p == null || q == null) return false;

        // Check karo ki current nodes ki values same hain AND
        // unke left subtrees identical hain AND
        // unke right subtrees identical hain.
        return (p.val == q.val) &&
               isSameTree(p.left, q.left) &&
               isSameTree(p.right, q.right);
    }
}
```

-----

# ⭐ Lowest Common Ancestor of a Binary Search Tree

-----

Aapko ek **Binary Search Tree (BST)** diya gaya hai. Aapko do diye gaye nodes `p` aur `q` ka **Lowest Common Ancestor (LCA)** dhundhna hai.

  * **LCA** BST mein woh **sabse neecha node** hai jiske `p` aur `q` dono descendants hain.
  * Ek node khud ka bhi descendant ho sakta hai.

-----

### Intuition (Samajh):

Lowest Common Ancestor (LCA) ek aisa node hota hai jo `p` aur `q` dono ka ancestor ho, aur woh `p` aur `q` ke liye sabse "neecha" (farthest from root) common ancestor ho.

**BST ki property** yahan bahut helpful hai:

  * Left child ki value root se kam hoti hai.
  * Right child ki value root se zyada hoti hai.

Is property ka use karke, jab hum root se traverse karte hain:

  * Agar `p` aur `q` dono root ke **left side** mein hain (`p.val < root.val` AND `q.val < root.val`), toh LCA left subtree mein hoga.
  * Agar `p` aur `q` dono root ke **right side** mein hain (`p.val > root.val` AND `q.val > root.val`), toh LCA right subtree mein hoga.
  * Agar `p` aur `q` **root ke opposite sides** mein hain (ek left mein, ek right mein) **YA** koi ek node khud root hai, toh **current root hi LCA hoga**.

-----

### Approach (Tareeka):

Recursive approach ka use karenge jo BST ki properties ka fayda uthata hai.

1.  **`lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)` Function:**
      * **Base Case:** `if (root == null) return null;` (Agar root null hai, toh koi LCA nahi).
      * **Check Conditions:**
          * **Case 1: Both `p` and `q` are in the right subtree:**
            `if (root.val < p.val && root.val < q.val)`: Agar current root ki value `p` aur `q` dono se kam hai, toh LCA right subtree mein hoga. `return lowestCommonAncestor(root.right, p, q);`
          * **Case 2: Both `p` and `q` are in the left subtree:**
            `if (root.val > p.val && root.val > q.val)`: Agar current root ki value `p` aur `q` dono se zyada hai, toh LCA left subtree mein hoga. `return lowestCommonAncestor(root.left, p, q);`
          * **Case 3: `p` and `q` are on opposite sides OR one of them is the root itself:**
            `else`: Is case mein, current `root` hi `p` aur `q` ka LCA hoga. `return root;`

**Note:** Jo code provide kiya gaya hai woh general binary tree ke liye bhi kaam karega (paths find karke) lekin BST ki property ka use nahi karta. BST ke liye, upar wala optimized approach better hai. Main BST optimized approach ko explain kar raha hu, aur code mein bhi wahi use karunga.

-----

### ✅ Java Code with Comments (Optimized for BST):

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
        // Agar current node null hai, toh koi LCA nahi mil sakta is path se.
        if (root == null) {
            return null;
        }

        // Case 1: Agar p aur q dono current root ke right subtree mein hain.
        // Matlab, root.val dono p.val aur q.val se choti hai.
        if (root.val < p.val && root.val < q.val) {
            // Toh LCA right subtree mein hoga, right child par recurse karo.
            return lowestCommonAncestor(root.right, p, q);
        }
        // Case 2: Agar p aur q dono current root ke left subtree mein hain.
        // Matlab, root.val dono p.val aur q.val se badi hai.
        else if (root.val > p.val && root.val > q.val) {
            // Toh LCA left subtree mein hoga, left child par recurse karo.
            return lowestCommonAncestor(root.left, p, q);
        }
        // Case 3: Agar p aur q current root ke opposite sides par hain,
        // YA unmein se koi ek current root khud hai.
        // Is case mein, current root hi unka Lowest Common Ancestor hai.
        // Example: p=2, q=8, root=6 -> root=6 is LCA.
        // Example: p=2, q=6, root=6 -> root=6 is LCA.
        else {
            return root; // Current root ko LCA return karo.
        }
    }
}
```

-----

# ⭐ Construct Binary Tree from Preorder and Inorder Traversal

-----

Aapko do integer arrays `preorder` aur `inorder` diye gaye hain jo ek binary tree ke **preorder** aur **inorder** traversals ko represent karte hain. Aapko **binary tree ko construct** karke return karna hai.

  * **Preorder traversal**: Root → Left → Right
  * **Inorder traversal**: Left → Root → Right
  * Elements **unique** hain aur theek ek baar appear hote hain.

-----

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

**Explanation**: Construct kiya gaya binary tree diye gaye traversal orders ko follow karta hai.

-----

### Intuition (Samajh):

Preorder traversal hamesha list ke **pehle element ko root** ke roop mein deta hai. Inorder traversal root ke **left mein left subtree** aur **right mein right subtree** ke elements ko deta hai.

Is property ka use karke, hum recursive approach se tree construct kar sakte hain:

1.  **Preorder se Root:** `preorder` array ka pehla element current subtree ka root hoga.
2.  **Inorder se Subtrees:** Is root element ko `inorder` array mein dhundo. Jo elements root ke left mein hain woh left subtree ke elements hain, aur jo right mein hain woh right subtree ke elements hain.
3.  **Recursive Construction:** Ab left subtree aur right subtree ko banane ke liye same steps ko recursively apply karo, unke corresponding `preorder` aur `inorder` portions ka use karke.

Ek `HashMap` ka use kar sakte hain `inorder` array mein values ke indices ko store karne ke liye, taaki root ko `inorder` mein dhundhne mein O(1) time lage (warna O(N) lagega).

-----

### Approach (Tareeka):

1.  **Helper Map:**

      * Ek `HashMap<Integer, Integer> map` banao jo `inorder` array ke har value ka index store karega. (e.g., `inorder[i] -> i`).

2.  **`preorderIndex` Pointer:**

      * Ek `int[] index = {0};` (ya `AtomicInteger` ya simple `int preorderIndex = 0;` aur use `final` banao aur helper function mein pass karte raho) global ya mutable pointer banao jo `preorder` array mein current root ke index ko track karega. Har root node use hone ke baad isse increment karenge.

3.  **`helper(preorder, inorder, left, right, map, index)` Function:**

      * `preorder`: Poora preorder array.

      * `inorder`: Poora inorder array.

      * `left`: Current inorder subtree ka starting index.

      * `right`: Current inorder subtree ka ending index.

      * `map`: Inorder value-to-index map.

      * `index`: `preorder` ka current index pointer.

      * **Base Case:** `if (left > right) return null;` (Agar inorder range invalid hai, matlab koi node nahi hai).

      * **Current Root:**

          * `int curr = preorder[index[0]];` (Preorder ke current index par value hi root hai).
          * `index[0]++;` (Next root ke liye preorder index ko increment karo).
          * `TreeNode node = new TreeNode(curr);` (Naya root node banao).

      * **Leaf Node Check:** `if (left == right) return node;` (Agar current inorder range mein sirf ek hi element hai, toh yeh ek leaf node hai).

      * **Find `inorderIndex`:** `int inorderIndex = map.get(curr);` (Current root ki value `inorder` array mein kahan hai, uska index pata karo).

      * **Recursive Calls for Children:**

          * `node.left = helper(preorder, inorder, left, inorderIndex - 1, map, index);` (Left subtree ke liye, `inorder` range `left` se `inorderIndex - 1` tak hogi).
          * `node.right = helper(preorder, inorder, inorderIndex + 1, right, map, index);` (Right subtree ke liye, `inorder` range `inorderIndex + 1` se `right` tak hogi).

      * `return node;`

4.  **`buildTree(preorder, inorder)` Function:**

      * `map` ko initialize karo.
      * `index` pointer ko `{0}` se initialize karo.
      * `helper(preorder, inorder, 0, preorder.length - 1, map, index)` ko call karo.

-----

### ✅ Java Code with Comments:

```java
import java.util.HashMap; // HashMap use karne ke liye
import java.util.Arrays;  // Arrays.asList() for example but not directly used in helper

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
        // Step 1: Inorder array mein har value ka index store karne ke liye HashMap banao.
        // Yeh O(1) time mein value ka index dhundhne mein help karega.
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        // Step 2: Ek array banaya jo preorder array mein current root ke index ko track karega.
        // Kyunki hum int pass by value karte hain Java mein, isliye ek array use kiya hai
        // taaki reference pass ho aur value update ho sake recursive calls mein.
        int[] index = {0}; // index[0] will point to the current root in preorder array

        // Step 3: Helper function ko call karo jo recursively tree construct karega.
        // preorder: poora preorder traversal array
        // inorder: poora inorder traversal array
        // 0: current inorder segment ka starting index
        // preorder.length - 1: current inorder segment ka ending index
        // map: inorder value-to-index map
        // index: preorder index pointer
        return helper(preorder, inorder, 0, preorder.length - 1, map, index);
    }

    // Recursive helper function jo binary tree construct karta hai
    private static TreeNode helper(int[] preorder, int[] inorder, int left, int right,
                                   HashMap<Integer, Integer> map, int[] index) {
        // Base case: Agar current inorder segment invalid hai (left pointer right se aage nikal gaya),
        // matlab yahan koi node construct karne ko nahi hai. Null return karo.
        if (left > right) return null;

        // Step 4: Preorder array mein current index par jo value hai, wahi current subtree ka root hai.
        int curr = preorder[index[0]];
        index[0]++; // Preorder index ko aage badhao agle root ke liye

        // Step 5: Root node banao
        TreeNode node = new TreeNode(curr);

        // Step 6: Agar current inorder segment mein sirf ek hi element hai (left == right),
        // toh yeh ek leaf node hai, iske koi children nahi honge. Node return karo.
        if (left == right) return node;

        // Step 7: Current root value ka inorder array mein index pata karo.
        // Yeh index left aur right subtrees ke elements ko split karne mein help karega.
        int inorderIndex = map.get(curr);

        // Step 8: Recursively left subtree construct karo.
        // Left subtree ka inorder segment: 'left' se 'inorderIndex - 1' tak.
        node.left = helper(preorder, inorder, left, inorderIndex - 1, map, index);

        // Step 9: Recursively right subtree construct karo.
        // Right subtree ka inorder segment: 'inorderIndex + 1' se 'right' tak.
        node.right = helper(preorder, inorder, inorderIndex + 1, right, map, index);

        return node; // Construct kiya hua current root node return karo
    }
}
```

-----

# ⭐ Binary Tree Maximum Path Sum

-----

Aapko ek binary tree ka `root` diya gaya hai. Aapko kisi bhi **non-empty path** ka **maximum path sum** return karna hai.

  * Ek **path** nodes ka ek sequence hai jo edges se connected hote hain.
  * Path mein har node **zyada se zyada ek baar** appear ho sakta hai.
  * Path ko `root` se hokar **guzarna zaroori nahi** hai.
  * **Path sum** path mein saare nodes ka total sum hai.

-----

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

**Explanation**: Optimal path `2 → 1 → 3` hai jiska path sum `2 + 1 + 3 = 6` hai.

-----

### Intuition (Samajh):

Yeh `Diameter of Binary Tree` jaisa hi hai, bas yahan edges ki sankhya ke bajaye nodes ki values ka sum chahiye. Path kahin se bhi shuru aur kahin bhi khatam ho sakta hai.

Har node par, do cheezein hoti hain:

1.  **Ek `path sum` jo current node ko include karta hai aur uske kisi ek child path se extend hota hai** (yaani, ya toh left child ke path se ya right child ke path se, jo zyada sum de). Yeh value current node se `upward` pass ki jayegi parent ko. Is path mein ek node aur uske ek hi branch ke descendants honge. Agar left/right path sum negative hai, toh use 0 mana ja sakta hai (ignore kar do), kyunki negative value sum ko kam kar degi.
2.  **Ek `maximum path sum` jo current node se guzarta hai aur uske dono children paths ko combine karta hai** (left child path + current node + right child path). Yeh ek potential answer path ho sakta hai, jise hum globally `maxi` variable mein track karenge.

-----

### Approach (Tareeka):

Hum ek recursive function `height(TreeNode root)` banayenge jo node se "upward path sum" return karega, aur side-by-side global `maxi` variable ko update karega jab bhi ek complete path (jo current node se guzarta hai) ka sum maximum milega.

1.  **Global Variable:** `private int maxi = Integer.MIN_VALUE;` ko class level par declare karo. Yeh final maximum path sum store karega. Initial value `Integer.MIN_VALUE` rakhi hai kyunki node values negative bhi ho sakti hain.

2.  **`height(TreeNode root)` Function (is function ka naam `maxPathDown` jaisa kuch zyada appropriate hoga, but let's stick to the given `height`):**

      * **Base Case:** `if (root == null) return 0;` (Ek null node ka path sum 0 hota hai).
      * **Recursive Calls for Children's Path Sums:**
          * `int left = Math.max(0, height(root.left));` (Left subtree se aane wala maximum path sum lo. Agar negative hai, toh 0 lo, kyunki hum us path ko avoid karna chahenge jo sum ko kam karta hai).
          * `int right = Math.max(0, height(root.right));` (Same logic for right subtree).
      * **Update `maxi` (Potential Answer Path):** Current node par, ek potential answer path woh ho sakta hai jo `left_path_sum + current_node.val + right_path_sum` ko combine kare. Is value ko `maxi` se compare karke update kar do: `maxi = Math.max(maxi, left + right + root.val);`
      * **Return Upward Path Sum:** Current node se upar ki taraf (parent ko) kitna path sum pass kiya ja sakta hai? `root.val + Math.max(left, right)`. (Kyunki ek path sirf ek branch se hi extend ho sakta hai, dono branches se nahi).

3.  **`maxPathSum(TreeNode root)` Function:**

      * `height(root)` ko call karo.
      * `maxi` global variable ko return kar do.

-----

### ✅ Java Code with Comments:

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
    // Global variable jo sabse bade path sum ko track karega.
    // Integer.MIN_VALUE se initialize kiya hai kyunki path sum negative bhi ho sakta hai.
    private int maxi = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        // Recursive function 'height' ko call karte hain.
        // Yeh function har node se "max path down" sum calculate karega
        // aur side-by-side 'maxi' (global max path sum) ko update karega.
        height(root);
        return maxi; // Final maximum path sum return karo
    }

    // Yeh recursive function do kaam karta hai:
    // 1. Current node se neeche ki taraf (ek branch mein) max path sum return karta hai.
    // 2. 'maxi' (global max path sum) ko update karta hai agar current node se guzarte hue
    //    ek naya max path milta hai (jo dono branches ko join kar sakta hai).
    public int height(TreeNode root) { // Naming 'height' might be confusing, think of it as 'maxPathDown'
        // Base case: Agar node null hai, toh path sum 0 hai (iska matlab path mein kuch add nahi kiya).
        if (root == null) return 0;

        // Left subtree se max path sum calculate karo.
        // Agar left subtree ka max path sum negative hai, toh use 0 bana do.
        // Kyunki hum negative sum ko path mein include karke total sum ko kam nahi karna chahte.
        int left = Math.max(0, height(root.left));
        // Right subtree se max path sum calculate karo (same logic).
        int right = Math.max(0, height(root.right));

        // Step 1: Global maximum path sum 'maxi' ko update karo.
        // Yahan hum consider kar rahe hain woh path jo current node ko include karta hai
        // aur uske left aur right dono subtrees se extend ho sakta hai.
        // Path = (max sum from left) + (current node's value) + (max sum from right)
        maxi = Math.max(maxi, left + right + root.val);

        // Step 2: Current node se upar (parent node ko) kitna max path sum pass kiya ja sakta hai, woh return karo.
        // Ek path sirf ek branch se hi continue ho sakta hai, dono se nahi.
        // Toh, current node ki value + left ya right subtree mein se jo zyada max sum de raha hai.
        return root.val + Math.max(left, right);
    }
}
```

-----

# ⭐ Serialize and Deserialize Binary Tree

-----

Ek algorithm design karna hai jo binary tree ko string mein **serialize** kare aur string ko original binary tree structure mein **deserialize** kare.

  * Serialization tree ko string mein convert karta hai.
  * Deserialization string se tree ko reconstruct karta hai.
  * Approach koi bhi valid method ho sakta hai jo original tree ko poori tarah se recover kar sake.

-----

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

  * `N` null node ko represent karta hai.
  * Yeh preorder-based serialization root, phir left subtree, phir right subtree ko store karta hai.

-----

### Intuition (Samajh):

Binary tree ko string mein store karne ke kai tareeke hain (preorder, inorder, postorder, level order). Lekin sirf `preorder + inorder` ya `postorder + inorder` se hi tree ko uniquely reconstruct kiya ja sakta hai. Single traversal (jaise sirf preorder) se tree ko uniquely reconstruct karne ke liye, hamein **null nodes ko bhi explicitly mark** karna padega.

Agar hum **preorder traversal** ka use karein aur null children ko `N` (ya koi aur marker) se denote karein, toh hum tree ko uniquely serialize aur deserialize kar sakte hain.

**Serialization (Tree -\> String):**

  * Root ko store karo.
  * Phir recursively left subtree ko serialize karo.
  * Phir recursively right subtree ko serialize karo.
  * Agar node null hai, toh `N` store karo.

**Deserialization (String -\> Tree):**

  * Serialized string ko values (aur `N` markers) mein split karo.
  * Ek pointer/iterator rakho jo in values par move kare.
  * Preorder sequence mein, pehla value hamesha root hogi.
  * Recursively left child banao, phir right child banao.
  * Agar `N` milta hai, toh null node banao.

-----

### Approach (Tareeka):

1.  **`serialize(TreeNode root)` Function:**

      * Ek `List<String> res` banao result store karne ke liye.
      * Ek helper function `dfsSerialize(TreeNode node, List<String> res)` call karo.
      * `String.join(",", res)` ka use karke `res` list ko comma-separated string mein convert karke return kar do.

2.  **`dfsSerialize(TreeNode node, List<String> res)` Helper Function (Serialization):**

      * **Base Case:** `if (node == null)`: Agar current node null hai, `res.add("N");` (null marker) aur `return`.
      * **Recursive Step:**
          * `res.add(String.valueOf(node.val));` (Node ki value string mein convert karke add karo).
          * `dfsSerialize(node.left, res);` (Left subtree ko serialize karo).
          * `dfsSerialize(node.right, res);` (Right subtree ko serialize karo).

3.  **`deserialize(String data)` Function:**

      * Input `data` string ko commas `,` se split karke `List<String> vals` banao.
      * Ek `Iterator<String> iterator = vals.iterator();` banao taaki hum values ko sequentially access kar saken.
      * Ek helper function `dfsDeserialize(Iterator<String> iterator)` call karo.

4.  **`dfsDeserialize(Iterator<String> iterator)` Helper Function (Deserialization):**

      * **Base Case:** `if (!iterator.hasNext()) return null;` (Agar iterator mein koi element nahi hai, toh return null).
      * **Get Current Value:** `String val = iterator.next();` (Iterator se next value lo).
      * **Check for Null:** `if (val.equals("N")) return null;` (Agar value "N" hai, toh yeh ek null node hai).
      * **Create Node:** `TreeNode node = new TreeNode(Integer.parseInt(val));` (Value ko integer mein parse karke node banao).
      * **Recursive Calls for Children:**
          * `node.left = dfsDeserialize(iterator);` (Left child ko reconstruct karo).
          * `node.right = dfsDeserialize(iterator);` (Right child ko reconstruct karo).
      * `return node;`

-----

### ✅ Java Code with Comments:

```java
import java.util.ArrayList; // ArrayList use karne ke liye
import java.util.Arrays;  // Arrays.asList() use karne ke liye
import java.util.Iterator; // Iterator use karne ke liye
import java.util.List;     // List interface use karne ke liye

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

    // Encodes (serialize) ek tree ko ek single string mein preorder traversal ka use karke.
    public String serialize(TreeNode root) {
        List<String> res = new ArrayList<>(); // String segments store karne ke liye list
        dfsSerialize(root, res); // DFS traversal shuru karo serialization ke liye
        return String.join(",", res); // List ke saare strings ko comma se join karke ek single string banao
    }

    // Helper function (DFS) serialization ke liye
    private void dfsSerialize(TreeNode node, List<String> res) {
        // Base case: Agar current node null hai
        if (node == null) {
            res.add("N"); // 'N' (Null) marker add karo
            return;
        }
        res.add(String.valueOf(node.val)); // Node ki value ko string mein convert karke add karo
        dfsSerialize(node.left, res);     // Left subtree ko serialize karo recursively
        dfsSerialize(node.right, res);    // Right subtree ko serialize karo recursively
    }

    // Decodes (deserialize) encoded string data ko tree mein wapis convert karta hai.
    public TreeNode deserialize(String data) {
        // Input string ko commas ',' se split karke values ki list banao
        List<String> vals = Arrays.asList(data.split(","));
        // Iterator banaya list par taaki hum values ko sequentially process kar saken
        Iterator<String> iterator = vals.iterator();
        // DFS deserialization shuru karo
        return dfsDeserialize(iterator);
    }

    // Helper function (DFS) deserialization ke liye
    private TreeNode dfsDeserialize(Iterator<String> iterator) {
        // Base case: Agar iterator mein aur koi element nahi hai, matlab tree khatam.
        if (!iterator.hasNext()) return null;

        String val = iterator.next(); // Iterator se agla value string lo
        // Agar value "N" hai, toh yeh ek null node hai
        if (val.equals("N")) {
            return null; // Null return karo
        }

        // Value ko integer mein parse karke naya TreeNode banao
        TreeNode node = new TreeNode(Integer.parseInt(val));
        // Recursively left subtree ko reconstruct karo
        node.left = dfsDeserialize(iterator);
        // Recursively right subtree ko reconstruct karo
        node.right = dfsDeserialize(iterator);
        return node; // Reconstruct kiya hua current node return karo
    }
}
```
