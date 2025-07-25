
# Tree Data Structures: Implementations in Java


### 1\. Generic Tree

* **Definition:** Ek tree jahaan har **node ke kitne bhi children** ho sakte hain (do ki limit nahi hoti). Isliye ise "N-ary" tree bhi kehte hain, jahaan 'N' children ki koi bhi sankhya ho sakti hai.
* **Use Cases:**
    * **File Systems:** Computer mein folders aur files ka structure dikhane ke liye.
    * **Organizational Charts:** Kisi company ya sanstha ka hierarchy (pad-anukram) show karne ke liye.
    * **XML/HTML Parsers:** Web pages aur documents ke structure ko represent karne ke liye.


```java
import java.util.ArrayList;
import java.util.Stack;

public class GenericTree {

    // Node representation for the tree
    private static class Node {
        int data; // Value stored in the node
        ArrayList<Node> children; // List of children nodes

        // Constructor to initialize a node with data
        public Node(int data) {
            this.data = data;
            children = new ArrayList<>(); // Initialize children list
        }
    }

    // Method to construct the tree from an array representation
    // Array format: [parent_data, child1_data, child2_data, -1 (end_of_children), grandChild_data, -1, -1, ...]
    public static Node constructor(int[] arr) {
        Stack<Node> stack = new Stack<>(); // Stack to keep track of parent nodes
        Node root = null; // Initialize root

        // Traverse the array to construct the tree
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1) {
                // If -1 is encountered, it means the current subtree is complete.
                // Pop the last parent from the stack.
                stack.pop();
            } else {
                // Create a new node with data from the array
                Node treeNode = new Node(arr[i]);
                if (!stack.isEmpty()) {
                    // If stack is not empty, the new node is a child of the node at the top of the stack
                    Node top = stack.peek();
                    top.children.add(treeNode);
                } else {
                    // If stack is empty, this is the first node, making it the root
                    root = treeNode;
                }
                // Push the new node to the stack, as it could be a parent for subsequent nodes
                stack.push(treeNode);
            }
        }
        return root; // Return the root of the constructed tree
    }

    // Method to display the tree structure recursively
    public static void display(Node node) {
        // Prepare a string to represent the current node and its children
        String str = node.data + " -> ";
        for (Node child : node.children) {
            str += child.data + ", "; // Add children data to the string
        }
        str += "."; // Add a dot to indicate end of children for this node
        System.out.println(str); // Print the string for the current node

        // Recursively call display for each child node
        for (Node child : node.children) {
            display(child);
        }
    }

    // Main method to test the tree construction and display
    public static void main(String[] args) {
        // Input array representing the tree structure
        // 10 is root. 20, 30, 40 are children of 10.
        // 50, 60 are children of 20. -1 after 50 means 50 has no more children.
        // -1 after 60 means 60 has no more children and 20's children are done.
        int arr[] = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};

        // Construct the tree
        Node root = constructor(arr);

        // Display the tree structure
        System.out.println("Generic Tree Structure:");
        display(root);
    }
}
```

-----

### 2\. Binary Tree

* **Definition:** Ek tree jahaan har **node ke zyada se zyada do children** hote hain: ek **left child** aur ek **right child**. In children ka order fixed hota hai.
* **Use Cases:**
    * **Expression Trees:** Mathematical expressions ko represent karne ke liye.
    * **Data Compression:** Huffman coding jaise algorithms mein.
    * **Routing Algorithms:** Network routing mein bhi use hota hai.
    * **Heaps aur Binary Search Trees** (jo khud tree ke types hain) ki building block hai.


```java
import java.util.ArrayList;
import java.util.Stack;

// Node class representing each node in the binary tree
class Node {
    int data; // Data stored in the node
    Node left, right; // Left and right children of the node

    // Constructor to initialize a node with data
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

// BinaryTree class to perform operations on the binary tree
class BinaryTree {

    // Method to perform in-order traversal of the binary tree (Iterative approach)
    static ArrayList<Integer> inOrderTrav(Node curr) {
        ArrayList<Integer> inOrder = new ArrayList<>(); // List to store in-order traversal result
        Stack<Node> s = new Stack<>(); // Stack to store nodes during traversal

        // Loop continues until all nodes are visited and stack is empty
        while (true) {
            if (curr != null) {
                // If current node is not null, push it to stack and move to its left child
                s.push(curr);
                curr = curr.left;
            } else {
                // If current node is null (reached end of left subtree)
                if (s.isEmpty()) {
                    // If stack is also empty, traversal is complete
                    break;
                }
                // Pop node from stack, process it (add its data to list), then move to its right child
                curr = s.peek();
                inOrder.add(curr.data);
                s.pop();
                curr = curr.right;
            }
        }
        return inOrder; // Return the list containing in-order traversal
    }

    // Main method to test the binary tree operations
    public static void main(String args[]) {
        // Creating a sample binary tree
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.left.right.left = new Node(8);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        root.right.right.left = new Node(9);
        root.right.right.right = new Node(10);

        // Performing in-order traversal
        ArrayList<Integer> inOrder;
        inOrder = inOrderTrav(root);

        // Displaying in-order traversal result
        System.out.println("The InOrder Traversal is: ");
        for (int i = 0; i < inOrder.size(); i++) {
            System.out.print(inOrder.get(i) + " ");
        }
        System.out.println(); // For new line
    }
}
```

-----

### 3\. Binary Search Tree (BST)

* **Definition:** Yeh ek khaas tarah ka Binary Tree hai jahaan **ordering** ka ek niyam hota hai:
    * Har **node ke left subtree mein saari values us node se choti** hoti hain.
    * Har **node ke right subtree mein saari values us node se badi** hoti hain.
    * Is property se data ko dhundhna, daalna aur hatana bahut tez ho jaata hai.
* **Use Cases:**
    * **Efficient Searching:** Jab aapko data ko bahut jaldi search karna ho.
    * **Databases:** Indexing mein iska istemal hota hai.
    * **Sorted Data Storage:** Jab data ko hamesha sorted order mein maintain karna ho, jaise `TreeSet` ya `TreeMap` mein.

```java
// Note: This class is not in a package named 'Tree' in this context
// If you have a package, ensure it's correctly handled or remove the line 'package Tree;'

public class BinarySearchTree {

    //Represent a node of binary tree
    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            //Assign data to the new node, set left and right children to null
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    //Represent the root of binary tree
    public Node root;

    public BinarySearchTree() {
        root = null;
    }

    // insert() will add new node to the binary search tree
    public void insert(int data) {
        // Create a new node
        Node newNode = new Node(data);

        // Check whether tree is empty
        if (root == null) {
            root = newNode;
            return;
        } else {
            // current node points to root of the tree
            Node current = root, parent = null;

            while (true) {
                // parent keeps track of the parent node of current node.
                parent = current;

                // If data is less than current's data, node will be inserted to the left of tree
                if (data < current.data) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        return;
                    }
                }
                // If data is greater than current's data, node will be inserted to the right of tree
                else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    // minNode() will find out the minimum node in a given subtree
    public Node minNode(Node node) {
        if (node.left != null)
            return minNode(node.left);
        else
            return node;
    }

    // deleteNode() will delete the given node from the binary search tree
    public Node deleteNode(Node node, int value) {
        if (node == null) {
            return null;
        } else {
            // If value is less than node's data, then search the value in left subtree
            if (value < node.data)
                node.left = deleteNode(node.left, value);

            // If value is greater than node's data, then search the value in right subtree
            else if (value > node.data)
                node.right = deleteNode(node.right, value);

            // If value is equal to node's data that is, we have found the node to be deleted
            else {
                // Case 1: If node to be deleted has no child (leaf node)
                if (node.left == null && node.right == null)
                    node = null;

                // Case 2: If node to be deleted has only one right child
                else if (node.left == null) {
                    node = node.right;
                }

                // Case 3: If node to be deleted has only one left child
                else if (node.right == null) {
                    node = node.left;
                }

                // Case 4: If node to be deleted has two children
                else {
                    // Then find the minimum node from right subtree (in-order successor)
                    Node temp = minNode(node.right);
                    // Exchange the data between node and temp
                    node.data = temp.data;
                    // Delete the duplicate node from right subtree
                    node.right = deleteNode(node.right, temp.data);
                }
            }
            return node;
        }
    }

    // inorderTraversal() will perform inorder traversal on binary search tree
    public void inorderTraversal(Node node) {

        // Check whether tree is empty
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        } else {
            if (node.left != null)
                inorderTraversal(node.left);
            System.out.print(node.data + " ");
            if (node.right != null)
                inorderTraversal(node.right);
        }
    }

    public static void main(String[] args) {

        BinarySearchTree bt = new BinarySearchTree();
        // Add nodes to the binary tree
        bt.insert(50);
        bt.insert(30);
        bt.insert(70);
        bt.insert(60);
        bt.insert(10);
        bt.insert(90);

        System.out.println("Binary search tree after insertion:");
        // Displays the binary tree
        bt.inorderTraversal(bt.root);

        Node deletedNode = null;
        // Deletes node 90 which has no child
        deletedNode = bt.deleteNode(bt.root, 90);
        System.out.println("\nBinary search tree after deleting node 90:");
        bt.inorderTraversal(bt.root);

        // Deletes node 30 which has one child
        deletedNode = bt.deleteNode(bt.root, 30);
        System.out.println("\nBinary search tree after deleting node 30:");
        bt.inorderTraversal(bt.root);

        // Deletes node 50 which has two children
        deletedNode = bt.deleteNode(bt.root, 50);
        System.out.println("\nBinary search tree after deleting node 50:");
        bt.inorderTraversal(bt.root);
    }
}
```

-----

#### 1\. N-ary Tree (Detailed)

Generic Tree hi **N-ary Tree** hai. Upar Generic Tree ka code isi concept ko demonstrate karta hai. Fir bhi, main ek aur simpler representation de raha hu `List` of children ke saath.

```java
import java.util.ArrayList;
import java.util.List;

class NaryNode {
    int data;
    List<NaryNode> children; // List to hold any number of children

    NaryNode(int item) {
        data = item;
        children = new ArrayList<>(); // Initialize the list of children
    }
}

public class NaryTree {
    NaryNode root;

    // Method to print the N-ary tree structure (Pre-order traversal like)
    void printTree(NaryNode node, int level) {
        if (node == null) return; // Base case: if node is null, return

        // Print indentation based on level for visual representation of hierarchy
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(node.data); // Print the current node's data

        // Recursively call printTree for each child
        for (NaryNode child : node.children) {
            printTree(child, level + 1); // Increment level for children
        }
    }

    public static void main(String[] args) {
        NaryTree tree = new NaryTree();
        tree.root = new NaryNode(1); // Root node

        // Adding children to the root
        NaryNode child1 = new NaryNode(2);
        NaryNode child2 = new NaryNode(3);
        NaryNode child3 = new NaryNode(4);
        tree.root.children.add(child1);
        tree.root.children.add(child2);
        tree.root.children.add(child3);

        // Adding children to child1
        child1.children.add(new NaryNode(5));
        child1.children.add(new NaryNode(6));

        // Adding children to child2
        child2.children.add(new NaryNode(7));

        System.out.println("N-ary Tree Structure:");
        tree.printTree(tree.root, 0); // Start printing from root at level 0
    }
}
```

-----

#### 2\. Trie (Prefix Tree)

* **Definition:** Yeh ek tree-like data structure hai jo khas taur par **strings ko store aur search** karne ke liye design kiya gaya hai. Har node ek character represent karta hai, aur root se kisi node tak ka path ek **prefix** banata hai. Ismein ek `isEndOfWord` flag hota hai jo batata hai ki kya wahaan ek pura word khatam hota hai.
* **Use Cases:**
    * **Autocomplete/Autosuggestion:** Jaise Google search bar mein ya mobile keyboard par type karte waqt suggestions aana.
    * **Spell Checker:** Galat spelling ko theek karne mein.
    * **Dictionary Implementation:** Words ko efficiently store aur retrieve karne ke liye.

```java
import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>(); // Maps character to next TrieNode
    boolean isEndOfWord; // True if this node marks the end of a word

    public TrieNode() {
        isEndOfWord = false;
    }
}

public class Trie {
    private final TrieNode root; // The root of the Trie

    public Trie() {
        root = new TrieNode(); // Initialize the root node
    }

    // Inserts a word into the trie
    void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            // If character not in children, create a new TrieNode
            // computeIfAbsent is a concise way to do this
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        node.isEndOfWord = true; // Mark the end of the word
    }

    // Searches for a word in the trie
    boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch); // Move to the next node
            if (node == null) {
                return false; // Character not found in the path
            }
        }
        return node.isEndOfWord; // Return true only if it's a complete word
    }

    // Checks if there is any word in the trie that starts with the given prefix
    boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return false; // Prefix character not found
            }
        }
        return true; // All characters of the prefix were found
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app"); // "app" is also a word now

        System.out.println("Search 'apple': " + trie.search("apple")); // true
        System.out.println("Search 'app': " + trie.search("app"));     // true
        System.out.println("Search 'ap': " + trie.search("ap"));       // false (not marked as end of word)
        System.out.println("Starts with 'app': " + trie.startsWith("app")); // true
        System.out.println("Starts with 'bat': " + trie.startsWith("bat")); // false
    }
}
```

-----

#### 3\. Heap (Max-Heap Example)


* **Definition:** Yeh ek **tree-based data structure** hai jo ek khaas **"heap property"** ko follow karti hai:
    * **Max-Heap:** Parent node ki value hamesha uske children se **badi ya barabar** hoti hai (root sabse bada hota hai).
    * **Min-Heap:** Parent node ki value hamesha uske children se **choti ya barabar** hoti hai (root sabse chota hota hai).
    * Is structure se sabse bade ya sabse chote element ko jaldi se access karna aasan ho jaata hai.
* **Use Cases:**
    * **Priority Queues:** Tasks ko unki priority ke hisaab se process karne ke liye.
    * **Heap Sort Algorithm:** Data ko efficiently sort karne ke liye.
    * **Kth Largest/Smallest Element:** Ek collection mein se Kth largest ya smallest element dhundhne ke liye.


```java
import java.util.PriorityQueue;

public class MaxHeap {
    // PriorityQueue by default is a Min-Heap.
    // To make it a Max-Heap, we provide a custom Comparator.
    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

    // Inserts a value into the Max-Heap
    void insert(int val) {
        maxHeap.add(val);
    }

    // Extracts (removes) the maximum value from the Max-Heap
    int extractMax() {
        if (maxHeap.isEmpty()) {
            throw new IllegalStateException("Heap is empty, cannot extract max.");
        }
        return maxHeap.poll(); // poll() retrieves and removes the head of this queue (the max element)
    }

    // Returns the maximum value without removing it
    int peekMax() {
        if (maxHeap.isEmpty()) {
            throw new IllegalStateException("Heap is empty, cannot peek max.");
        }
        return maxHeap.peek();
    }

    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.insert(5);
        heap.insert(25);

        System.out.println("Max element (peek): " + heap.peekMax()); // Expected: 25

        System.out.println("Extract Max: " + heap.extractMax()); // Output: 25
        System.out.println("Extract Max: " + heap.extractMax()); // Output: 20
        System.out.println("Extract Max: " + heap.extractMax()); // Output: 15
        System.out.println("Max element (peek): " + heap.peekMax()); // Expected: 10
    }
}
```

-----

#### 4\. AVL Tree

* **Definition:** Yeh ek **self-balancing Binary Search Tree** hai. Matlab, jab bhi aap ismein koi element daalte ya nikalte hain, toh yeh tree khud ko **ghuma kar (rotations)** balance rakhta hai. Isse yeh ensure hota hai ki tree ki height hamesha log N ke kareeb rahe, jiski wajah se search, insert aur delete operations hamesha `O(log N)` time mein hote hain, even in the worst case.
* **Use Cases:**
    * **Databases:** High-performance databases mein indexing ke liye jahaan data ko hamesha balanced aur sorted rakhna zaroori hota hai.
    * **In-memory Databases:** Un applications mein jahaan bahut zyada read aur write operations hote hain.

```java
class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int item) {
        key = item;
        height = 1; // New node is initially at height 1
    }
}

public class AVLTree {
    private AVLNode root;

    // Helper function to get height of a node
    int height(AVLNode N) {
        if (N == null) return 0;
        return N.height;
    }

    // Helper function to get balance factor of a node (height of left - height of right)
    int getBalance(AVLNode N) {
        if (N == null) return 0;
        return height(N.left) - height(N.right);
    }

    // Right rotate subtree rooted with y
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Inserts a key into the AVL tree
    AVLNode insert(AVLNode node, int key) {
        // 1. Perform standard BST insertion
        if (node == null) return new AVLNode(key);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else { // Duplicate keys are not allowed in this implementation
            return node;
        }

        // 2. Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get the balance factor of this ancestor node to check if it became unbalanced
        int balance = getBalance(node);

        // 4. If the node becomes unbalanced, then there are 4 cases

        // Left Left Case (LL)
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Right Right Case (RR)
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Left Right Case (LR)
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case (RL)
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // A utility function to print preorder traversal of the tree.
    // The tree structure will be visible in the output.
    void preOrder(AVLNode node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);

        System.out.println("Preorder traversal of the constructed AVL tree is:");
        tree.preOrder(tree.root);
        System.out.println();
    }
}
```

-----

#### 5\. Red-Black Tree

* **Definition:** Yeh bhi ek **self-balancing Binary Search Tree** hai. Yeh AVL tree ki tarah strict balancing rules follow nahi karta, balki har node ko **"red" ya "black" color** dekar balance maintain karta hai. Iske balancing rules (jaise root ka black hona, red node ke children ka black hona) insertions aur deletions ke dauran tree ko approximate balanced rakhte hain.
* **Use Cases:**
    * **Java's `TreeMap` aur `TreeSet`:** Java Collection Framework mein sorted maps aur sets ko implement karne ke liye.
    * **Operating Systems:** Linux kernel mein process scheduling aur memory management mein.
    * **Database Management Systems:** Kayi databases mein indexing ke liye.

```java
class RedBlackNode {
    int data;
    RedBlackNode left, right, parent; // Added parent for easier navigation during balancing
    boolean color; // True for red, false for black

    RedBlackNode(int data) {
        this.data = data;
        this.color = true; // New nodes are typically red by convention
        left = right = parent = null;
    }
}

public class RedBlackTree {
    private RedBlackNode root;

    // Helper function to check if a node is red (handles null nodes as black)
    private boolean isRed(RedBlackNode node) {
        return node != null && node.color == true;
    }

    // Helper function to perform left rotation
    private RedBlackNode leftRotate(RedBlackNode h) {
        RedBlackNode x = h.right;
        h.right = x.left;
        if (x.left != null) {
            x.left.parent = h;
        }
        x.parent = h.parent;
        if (h.parent == null) {
            root = x;
        } else if (h == h.parent.left) {
            h.parent.left = x;
        } else {
            h.parent.right = x;
        }
        x.left = h;
        h.parent = x;
        return x;
    }

    // Helper function to perform right rotation
    private RedBlackNode rightRotate(RedBlackNode h) {
        RedBlackNode x = h.left;
        h.left = x.right;
        if (x.right != null) {
            x.right.parent = h;
        }
        x.parent = h.parent;
        if (h.parent == null) {
            root = x;
        } else if (h == h.parent.left) {
            h.parent.left = x;
        } else {
            h.parent.right = x;
        }
        x.right = h;
        h.parent = x;
        return x;
    }

    // Helper function to flip colors
    private void flipColors(RedBlackNode h) {
        h.color = true; // Parent becomes red
        h.left.color = false; // Children become black
        h.right.color = false;
    }

    // Public insert method
    public void insert(int data) {
        root = insert(root, data);
        root.color = false; // Root is always black
    }

    // Recursive insert helper method (simplified for demonstration)
    private RedBlackNode insert(RedBlackNode node, int data) {
        if (node == null) {
            return new RedBlackNode(data);
        }

        if (data < node.data) {
            node.left = insert(node.left, data);
            node.left.parent = node; // Set parent pointer
        } else if (data > node.data) {
            node.right = insert(node.right, data);
            node.right.parent = node; // Set parent pointer
        } else {
            // Handle duplicates (or ignore, depending on requirements)
            return node;
        }

        // --- Red-Black Tree balancing rules (simplified logic) ---
        // Rotate left when right child is red and left child is black
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        // Rotate right when left child and its left child are red
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        // Flip colors when both children are red
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    // A simple in-order traversal to verify BST property
    public void inorderTraversal(RedBlackNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.data + (node.color ? "(R)" : "(B)") + " ");
            inorderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(5);
        tree.insert(25);

        System.out.println("Inorder traversal of Red-Black Tree (R=Red, B=Black):");
        tree.inorderTraversal(tree.root);
        System.out.println();
    }
}
```

-----

#### 6\. B-Tree

* **Definition:** Yeh ek **self-balancing, multi-way tree** hai, jahaan har **node mein multiple keys aur multiple children** ho sakte hain. Ise khas taur par **disk-based storage systems** ke liye design kiya gaya hai, jahaan disk se data read karna (I/O operations) bahut mehenga hota hai. Yeh data ko bade blocks mein store karke disk access ko kam karta hai.
* **Use Cases:**
    * **Database Indexing:** Almost sabhi relational databases (jaise MySQL, PostgreSQL) indexes ko B-Trees ya B+Trees se implement karte hain.
    * **File Systems:** Bade file systems (jaise NTFS, HFS+) files aur directories ko organize karne ke liye B-Trees ka istemal karte hain.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a node in the B-Tree
class BTreeNode {
    int t; // Minimum degree (defines the range for number of keys and children)
    List<Integer> keys; // List of keys in the node
    List<BTreeNode> children; // List of child pointers
    boolean leaf; // True if this node is a leaf node

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    // A method to search a key in this node
    // (Actual search logic would be more involved, traversing children)
    public boolean search(int k) {
        return keys.contains(k);
    }
    // Other methods like insertNonFull, splitChild, delete etc. would be here
}

// Represents the B-Tree
public class BTree {
    BTreeNode root; // Root of the B-Tree
    int t; // Minimum degree of the B-Tree

    public BTree(int t) {
        this.t = t;
        // Initially, the root is a leaf node
        root = new BTreeNode(t, true);
    }

    // A simple insertion method (very high-level, actual implementation is complex)
    public void insert(int key) {
        // If root is full, then tree grows in height
        if (root.keys.size() == (2 * t - 1)) {
            BTreeNode s = new BTreeNode(t, false); // New root
            s.children.add(root); // Old root becomes child of new root
            // Logic to split the old root and promote median key to new root
            // This is complex and beyond a simple snippet
            // s.splitChild(0, root);
            // int i = 0;
            // if (s.keys.get(0) < key) i++;
            // s.children.get(i).insertNonFull(key);
            root = s; // Update root
            System.out.println("Warning: B-Tree insertion logic is highly simplified. Full implementation is complex.");
        }
        // If root is not full, insert into root (or its children if not leaf)
        // root.insertNonFull(key);
        System.out.println("Inserted (conceptually): " + key);
        root.keys.add(key); // Simplified: just add to root for demonstration
        Collections.sort(root.keys); // Keep sorted for simple demo
    }


    // A simple method to print keys of the root for demonstration
    public void printRootKeys() {
        if (root != null) {
            System.out.println("Root keys: " + root.keys);
        }
    }


    public static void main(String[] args) {
        BTree btree = new BTree(2); // Create a B-Tree with minimum degree 2 (means 2*2-1=3 keys max per node)

        // Very simplified insertion for demonstration, actual B-Tree insertion is complex
        btree.insert(10);
        btree.insert(20);
        btree.insert(5);
        btree.insert(6);
        btree.insert(12);
        btree.insert(30);

        btree.printRootKeys(); // Will show only root keys in this simplified demo
    }
}
```

-----

#### 7\. B+ Tree

* **Definition:** Yeh **B-Tree ka ek extension** hai, jahaan ek khaas antar hota hai:
    * **Saari data values sirf leaf nodes mein store** hoti hain.
    * **Internal nodes sirf keys** store karte hain, jo sirf navigation (path dhundhne) ke liye use hoti hain, asli data nahi.
    * Sabse aakhri leaf nodes ek **doubly linked list** ki tarah jude hote hain, jisse sequential access aur range queries (ek range ke andar ke sabhi data ko nikalna) bahut efficient ho jaate hain.
* **Use Cases:**
    * **Primary Indexing in Databases:** Database mein data ko index karne ka yeh sabse common method hai.
    * **File Systems:** Modern file systems mein data organization ke liye bhi istemal hota hai.


```java
import java.util.ArrayList;
import java.util.List;

// Represents a generic node in B+ Tree (can be internal or leaf)
class BPlusNode {
    int t; // Minimum degree
    List<Integer> keys; // Keys in this node
    List<BPlusNode> children; // Pointers to children (for internal nodes)
    boolean leaf; // True if this is a leaf node
    BPlusNode nextLeaf; // Pointer to next leaf node (for leaf nodes)

    BPlusNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
        this.nextLeaf = null; // Only relevant for leaf nodes
    }

    // A simple method to check if a key is present in this node
    public boolean containsKey(int key) {
        return keys.contains(key);
    }
    // Full implementation would involve complex insertion, splitting, deletion logic
}

public class BPlusTree {
    BPlusNode root;
    int t; // Minimum degree of the B+ Tree

    public BPlusTree(int t) {
        this.t = t;
        // Initially, the root is also a leaf node
        root = new BPlusNode(t, true);
    }

    // Simplified insertion for conceptual understanding
    public void insert(int key) {
        // Actual B+ tree insertion is complex involving
        // finding the correct leaf, splitting if full, promoting keys to parent, etc.
        System.out.println("Inserting (conceptually): " + key);
        if (root.leaf) {
            // For a very simple demo, just add to root if it's a leaf
            root.keys.add(key);
            Collections.sort(root.keys);
            // In real B+ tree, you'd handle splits here if root.keys.size() > 2*t - 1
        } else {
            // Traverse to the appropriate leaf node and insert
            System.out.println("Warning: B+ Tree insertion for non-leaf root is highly simplified.");
            // This part would involve traversing children to find the correct leaf node
        }
    }

    // A simple method to print keys of the root node
    public void printRootKeys() {
        if (root != null) {
            System.out.println("Root keys: " + root.keys + (root.leaf ? " (Leaf)" : " (Internal)"));
        }
    }

    public static void main(String[] args) {
        BPlusTree bplusTree = new BPlusTree(2); // Minimum degree 2

        bplusTree.insert(10);
        bplusTree.insert(20);
        bplusTree.insert(5);
        bplusTree.insert(15);
        bplusTree.insert(25); // This might cause a split in a real B+ Tree

        bplusTree.printRootKeys(); // Output will be simplified
    }
}
```

-----

# Summary

## Tree Data Structures: A Quick Summary

Here's a concise summary of the tree data structures we've discussed, focusing on their core definitions without their specific use cases:

---

### 1. Generic Tree (N-ary Tree)

A **Generic Tree** is a tree where each **node can have any number of children** (not just limited to two). It's also often called an **N-ary Tree**, indicating that 'N' can represent any quantity of children a node might have.

---

### 2. Binary Tree

A **Binary Tree** is a tree where each **node has a maximum of two children**: a **left child** and a **right child**. The order of these two children is always fixed.

---

### 3. Binary Search Tree (BST)

A **Binary Search Tree (BST)** is a specialized type of Binary Tree that maintains an **ordered structure**. For every node, all **values in its left subtree are less than** the node's value, and all **values in its right subtree are greater than** the node's value. This ordering rule optimizes search operations.

---

### 4. Trie (Prefix Tree)

A **Trie**, also known as a **Prefix Tree**, is a tree-like data structure designed specifically for **storing and efficiently searching strings**. Each node in a Trie typically represents a character, and the path from the root to any node forms a **prefix**. A special flag on a node indicates if it marks the end of a complete word.

---

### 5. Heap

A **Heap** is a **tree-based data structure** that always satisfies a specific **"heap property."** In a **Max-Heap**, the parent node's value is always greater than or equal to its children's values (making the root the largest element). Conversely, in a **Min-Heap**, the parent node's value is always less than or equal to its children's values (making the root the smallest element).

---

### 6. AVL Tree

An **AVL Tree** is a **self-balancing Binary Search Tree**. It automatically adjusts its structure through **rotations** whenever elements are inserted or deleted. This mechanism ensures that the tree's height remains balanced, which guarantees logarithmic time complexity for core operations even in worst-case scenarios.

---

### 7. Red-Black Tree

A **Red-Black Tree** is another type of **self-balancing Binary Search Tree**. It maintains balance by assigning a **"red" or "black" color** to each node and adhering to a strict set of rules. These coloring rules and associated operations help to keep the tree approximately balanced during insertions and deletions.

---

### 8. B-Tree

A **B-Tree** is a **self-balancing, multi-way tree** where each **node can contain multiple keys and multiple child pointers**. It's primarily designed for **disk-based storage systems** to minimize disk I/O operations by storing data in larger blocks within nodes.

---

### 9. B+ Tree

A **B+ Tree** is an **extension of the B-Tree** with a key distinction: **all actual data values are stored exclusively in the leaf nodes**. The internal nodes only contain **keys for navigation purposes**, guiding the search to the correct leaf node. Additionally, all leaf nodes are typically linked together, forming a sequential list for efficient range queries and sequential access.

---
-----

# Interview Questions and Answers: Tree Data Structures (Hinglish Version)

Yahaan kuch common tree-related interview questions unke answers aur sample Java implementations ke saath diye gaye hain:

### 1\. **What is the difference between a binary tree and a binary search tree (BST)?**

**Answer**:

  * **Binary Tree**: Ek tree jahaan har node ke zyada se zyada do children hote hain, par nodes ki values par koi constraints nahi hoti.
  * **Binary Search Tree (BST)**: Ek binary tree jahaan ek node ke **left child** mein us node ki value se **kam values** hoti hain, aur **right child** mein us node ki value se **zyada values** hoti hain.

### 2\. **How do you traverse a binary tree?**

**Answer**:
Binary trees ke traversal methods mein shamil hain:

  * **Inorder (Left, Root, Right)**: Pehle left subtree visit karo, fir root node, aur uske baad right subtree.
  * **Preorder (Root, Left, Right)**: Pehle root node visit karo, fir left subtree, aur uske baad right subtree.
  * **Postorder (Left, Right, Root)**: Pehle left subtree visit karo, fir right subtree, aur uske baad root node.
  * **Level Order**: Nodes ko level by level, top se bottom tak visit karo.

**Java Implementation**:

```java
class TreeNode {
    int data;
    TreeNode left, right;

    TreeNode(int data) {
        this.data = data;
        this.left = this.right = null;
    }
}

public class TreeTraversal {
    public void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public void preorder(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public void postorder(TreeNode root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public void levelOrder(TreeNode root) {
        if (root == null) return;
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.data + " ");
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        TreeTraversal traversal = new TreeTraversal();
        System.out.print("Inorder: ");
        traversal.inorder(root);
        System.out.println();

        System.out.print("Preorder: ");
        traversal.preorder(root);
        System.out.println();

        System.out.print("Postorder: ");
        traversal.postorder(root);
        System.out.println();

        System.out.print("Level Order: ");
        traversal.levelOrder(root);
    }
}
```

### 3\. **How do you find the height of a binary tree?**

**Answer**: Ek binary tree ki **height** root se kisi leaf tak ke longest path ki length hoti hai. Ise recursively calculate kiya ja sakta hai by finding left aur right subtrees ki maximum height, aur usmein 1 add karke.

**Java Implementation**:

```java
public class TreeHeight {
    public int height(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        TreeHeight treeHeight = new TreeHeight();
        System.out.println("Height of the tree: " + treeHeight.height(root));
    }
}
```

### 4\. **What is a Balanced Tree?**

**Answer**: Ek **balanced tree** woh tree hota hai jahaan kisi bhi node ke do subtrees ki height mein 1 se zyada ka difference nahi hota. **AVL Trees** aur **Red-Black Trees** balanced trees ke examples hain.

### 5\. **How do you check if a binary tree is a BST?**

**Answer**: Ek binary tree tab BST hota hai jab har node ke liye, uske **left subtree** ke sabhi nodes ki value node ki value se **kam** ho, aur **right subtree** ke sabhi nodes ki value node ki value se **zyada** ho. Ise range constraints ke saath recursively check kiya ja sakta hai.

**Java Implementation**:

```java
public class CheckBST {
    public boolean isBST(TreeNode root) {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTUtil(TreeNode node, int min, int max) {
        if (node == null) return true;
        if (node.data < min || node.data > max) return false;
        return isBSTUtil(node.left, min, node.data - 1) && isBSTUtil(node.right, node.data + 1, max);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(20);

        CheckBST checkBST = new CheckBST();
        System.out.println("Is BST: " + checkBST.isBST(root));
    }
}
```

### 6\. **How do you find the Lowest Common Ancestor (LCA) of two nodes in a binary tree?**

**Answer**: Do nodes `n1` aur `n2` ka **LCA** (Lowest Common Ancestor) sabse deepest node hota hai jo `n1` aur `n2` dono ka ancestor ho. Ise tree ko traverse karke aur yeh check karke find kiya ja sakta hai ki kya dono nodes left ya right subtree mein मौजूद hain.

**Java Implementation**:

```java
public class LowestCommonAncestor {
    public TreeNode findLCA(TreeNode root, TreeNode n1, TreeNode n2) {
        if (root == null) return null;
        if (root == n1 || root == n2) return root;

        TreeNode leftLCA = findLCA(root.left, n1, n2);
        TreeNode rightLCA = findLCA(root.right, n1, n2);

        if (leftLCA != null && rightLCA != null) return root;
        return (leftLCA != null) ? leftLCA : rightLCA;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        LowestCommonAncestor lcaFinder = new LowestCommonAncestor();
        TreeNode lca = lcaFinder.findLCA(root, root.left.left, root.left.right);
        System.out.println("LCA of 4 and 5: " + lca.data);
    }
}
```

### 7\. **How do you find the diameter of a binary tree?**

**Answer**: Ek binary tree ka **diameter** kisi bhi do nodes ke beech ke longest path ki length hoti hai. Ise recursively left aur right subtrees ki height compute karke aur diameter ko update karke find kiya ja sakta hai.

**Java Implementation**:

```java
public class TreeDiameter {
    int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        diameter = Math.max(diameter, leftHeight + rightHeight);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        TreeDiameter treeDiameter = new TreeDiameter();
        System.out.println("Diameter of the tree: " + treeDiameter.diameterOfBinaryTree(root));
    }
}
```

### 8\. **How do you check if a binary tree is symmetric?**

**Answer**: Ek binary tree symmetric tab hota hai jab uska **left subtree** uske **right subtree ka mirror image** ho. Ise recursively left subtree ko mirrored right subtree se compare karke check kiya ja sakta hai.

**Java Implementation**:

```java
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        return root == null || isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return (left.data == right.data)
               && isMirror(left.right, right.left)
               && isMirror(left.left, right.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);

        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        SymmetricTree symmetricTree = new SymmetricTree();
        System.out.println("Is symmetric: " + symmetricTree.isSymmetric(root));
    }
}
```

### 9\. **How do you flatten a binary tree to a linked list?**

**Answer**: Ek binary tree ko in-place linked list mein flatten karne ke liye, tree ko ek aise list mein convert kiya jaata hai jahaan har node ka **right child** preorder traversal mein next node ko point karta hai aur **left child null** hota hai.

**Java Implementation**:

```java
public class FlattenBinaryTree {
    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;

        TreeNode right = root.right;
        if (prev != null) {
            prev.right = root;
            prev.left = null;
        }
        prev = root;

        flatten(root.left);
        flatten(right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        FlattenBinaryTree flatten = new FlattenBinaryTree();
        flatten.flatten(root);

        // Print the flattened tree
        TreeNode current = root;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.right;
        }
    }
}
```

### 10\. **How do you find the Kth smallest element in a BST?**

**Answer**: Elements ko ascending order mein pane ke liye **in-order traversal** use karo, aur fir Kth element return kar do.

**Java Implementation**:

```java
public class KthSmallestInBST {
    private int count = 0;
    private int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode node, int k) {
        if (node == null) return;

        inorder(node.left, k);
        count++;
        if (count == k) result = node.data;
        inorder(node.right, k);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);

        KthSmallestInBST kthFinder = new KthSmallestInBST();
        System.out.println("2nd smallest element: " + kthFinder.kthSmallest(root, 2));
    }
}
```

-----

# Top View and Bottom View of a Binary Tree

**Top View** aur **Bottom View** find karne ke liye, humein un nodes ko consider karna padta hai jo tree ko top ya bottom se dekhne par dikhte hain. Yahaan **vertical distance (HD - horizontal distance)** ka concept useful hai. Hum nodes ko horizontal distances (HD) assign karte hain, jahaan root node HD = 0 par hota hai, left child ka HD 1 se kam hota hai, aur right child ka HD 1 se zyada hota hai. Tree ko level by level traverse karne ke liye hum **Breadth-First Search (BFS)** ko queue ke saath aur har horizontal distance ke liye nodes ko store karne ke liye map ka use kar sakte hain.

### Top View of a Binary Tree:

**Top view** ke liye, hum har horizontal distance par BFS (level by level traversal) karte waqt **first encountered node** ko store karte hain.

### Bottom View of a Binary Tree:

**Bottom view** ke liye, hum har horizontal distance par **last encountered node** ko store karte hain, yani, har level par sabse aakhir mein dikhne wala node.

### Top View Code:

```java
import java.util.*;

// Assuming TreeNode class is defined as:
// class TreeNode { int val; TreeNode left; TreeNode right; TreeNode(int x) { val = x; } }

class Solution {
    class Pair {
        TreeNode node;
        int hd; // horizontal distance
        public Pair(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    public List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        // Map to store first node at each horizontal distance
        Map<Integer, Integer> topViewMap = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();

        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            TreeNode node = current.node;
            int hd = current.hd;

            // If no node is recorded for this horizontal distance, store it
            if (!topViewMap.containsKey(hd)) {
                topViewMap.put(hd, node.val);
            }

            // Add the left and right children to the queue with updated HDs
            if (node.left != null) {
                queue.add(new Pair(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.add(new Pair(node.right, hd + 1));
            }
        }

        // Add values from the map to the result list
        for (Map.Entry<Integer, Integer> entry : topViewMap.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }
}
```

### Bottom View Code:

```java
import java.util.*;

// Assuming TreeNode class is defined as:
// class TreeNode { int val; TreeNode left; TreeNode right; TreeNode(int x) { val = x; } }

class Solution {
    class Pair {
        TreeNode node;
        int hd; // horizontal distance
        public Pair(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    public List<Integer> bottomView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        // Map to store last node at each horizontal distance
        Map<Integer, Integer> bottomViewMap = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();

        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            TreeNode node = current.node;
            int hd = current.hd;

            // Update the map to store the last node for this horizontal distance
            bottomViewMap.put(hd, node.val); // This overwrites earlier nodes at same HD

            // Add the left and right children to the queue with updated HDs
            if (node.left != null) {
                queue.add(new Pair(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.add(new Pair(node.right, hd + 1));
            }
        }

        // Add values from the map to the result list
        for (Map.Entry<Integer, Integer> entry : bottomViewMap.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }
}
```

### Explanation:

1.  **Pair Class**: Har node ko uske **horizontal distance (HD)** ke saath pair kiya jaata hai aur queue mein add kiya jaata hai.
2.  **BFS Traversal**: **BFS** ka use tree ko level by level traverse karne ke liye kiya jaata hai, yeh ensure karte hue ki nodes top-to-bottom fashion mein process hon.
3.  **Top View Logic**: Top view ke liye, hum har horizontal distance par encounter hone wale **first node** ko `TreeMap` (HD ke hisaab se sorted) mein store karte hain.
4.  **Bottom View Logic**: Bottom view ke liye, hum har horizontal distance ke liye node ko **update** karte rehte hain, taki last encountered node map mein rahe.
5.  **TreeMap**: Map ka use har horizontal distance par nodes ko store karne ke liye kiya jaata hai, aur kyunki yeh `TreeMap` hai, yeh automatically horizontal distance ke hisaab se sort ho jaata hai.

### Example Tree:

```
        3
      /   \
     1     4
    /     / \
   6     1   5
```

Is tree ke liye:

  * **Top view**: `6 1 3 4 5`
  * **Bottom view**: `6 1 1 4 5`

### Time and Space Complexity:

  * **Time Complexity**: O(n) jahaan `n` nodes ki sankhya hai, kyunki har node ko ek baar visit kiya jaata hai.
  * **Space Complexity**: O(n), queue aur horizontal distances store karne ke liye use kiye gaye map ke liye required space.

-----

# Tree Algorithms, Organized by Category

-----

🌳 1. Tree Traversals

| Type        | Description                                       |
| :---------- | :------------------------------------------------ |
| Inorder     | Left → Root → Right (BST → sorted order)          |
| Preorder    | Root → Left → Right                               |
| Postorder   | Left → Right → Root                               |
| Level Order | BFS (left to right level by level)                |
| Zigzag Order| Level order with alternating directions           |
| Morris Traversal | Inorder traversal with O(1) space           |

-----

🧱 2. Tree Construction

| Algorithm                       | Description                               |
| :------------------------------ | :---------------------------------------- |
| Build Tree from Inorder + Preorder | Recursive, split using preorder + inorder |
| Build Tree from Inorder + Postorder | Similar to above                        |
| Build Binary Tree from Level Order | Queue-based                             |
| Build BST from Sorted Array    | Middle element as root                    |
| Deserialize/Serialize Binary Tree | String ↔ Tree conversions (for network)   |
| BST from Preorder Traversal    | Use bounds while constructing             |

-----

🔍 3. Tree Search and Queries

| Algorithm                    | Description                                     |
| :--------------------------- | :---------------------------------------------- |
| Search in BST                | O(h), left/right traversal                      |
| Lowest Common Ancestor (LCA) | DFS + backtracking or Binary Lifting            |
| Tree Diameter                | Longest path between two nodes (DFS x2)         |
| Root-to-Leaf Path Sum        | DFS / Backtracking                              |
| All Paths from Root to Leaf  | DFS path tracking                               |
| Find Path Between Two Nodes  | DFS + LCA                                       |

-----

📐 4. Tree Properties & Calculations

| Property            | Algorithm                                      |
| :------------------ | :--------------------------------------------- |
| Height / Depth of Tree | DFS / BFS                                    |
| Check if Tree is Balanced | Bottom-up DFS with height check            |
| Validate BST        | Inorder traversal or bounds method             |
| Count Total Nodes   | DFS / Recursion                                |
| Count Leaf / Internal Nodes | DFS                                      |
| Max Path Sum        | DFS returning max gain from children           |
| Invert / Mirror Tree | Swap left and right children recursively     |

-----

🔄 5. Tree Transformations

| Transformation             | Description                                     |
| :------------------------- | :---------------------------------------------- |
| Flatten Binary Tree to Linked List | Right-skewed tree using preorder              |
| Convert BST to Greater Tree | Reverse inorder + carry sum                     |
| BST to Doubly Linked List  | Inorder traversal + pointer adjustments         |

-----

📚 6. Advanced Tree Algorithms

| Algorithm                           | Use Case / Description                                |
| :---------------------------------- | :---------------------------------------------------- |
| Binary Lifting / LCA (sparse table) | LCA in O(logN) queries                                |
| Euler Tour Technique                | Flatten tree for RMQ and segment tree                 |
| Heavy-Light Decomposition (HLD)     | Fast path queries on trees (advanced)                 |
| Centroid Decomposition              | Divide-and-conquer on trees                           |
| Tree DP (Dynamic Programming)       | DP on children to compute tree-wide states            |
| Tarjan’s Offline LCA                | Multiple LCA queries in one DFS pass                  |

-----

🧠 7. Tree Problems by Pattern

| Pattern                  | Examples                                     |
| :----------------------- | :------------------------------------------- |
| DFS Path Tracking        | Root-to-leaf sum, all paths                  |
| Backtracking on Tree     | Path Sum II, N-ary paths                     |
| BFS Layer Processing     | Level order, zigzag, right view              |
| Recursion + State Return | Diameter, max path, LCA                      |
| Divide & Conquer         | LCA, Tree Merge, Tree Compare                |

-----

📦 8. Binary Indexed Tree (Fenwick) & Segment Tree

Technically not classic trees, but tree-based data structures:

| Structure      | Use Case                                  |
| :------------- | :---------------------------------------- |
| Segment Tree   | Range queries and updates (min, sum, max) |
| Fenwick Tree (BIT) | Efficient prefix sum, frequency queries   |

-----

# 🌳 TREE ALGORITHM CODE PATTERNS

# // === 1. TREE TRAVERSALS ===

```java
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    System.out.print(node.val + " ");
    inorder(node.right);
}

void preorder(TreeNode node) {
    if (node == null) return;
    System.out.print(node.val + " ");
    preorder(node.left);
    preorder(node.right);
}

void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);
    postorder(node.right);
    System.out.print(node.val + " ");
}

List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    boolean leftToRight = true;
    while (!queue.isEmpty()) {
        int size = queue.size();
        LinkedList<Integer> level = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if (leftToRight) level.addLast(node.val);
            else level.addFirst(node.val);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        result.add(level);
        leftToRight = !leftToRight;
    }
    return result;
}
```

# // Morris Inorder Traversal

```java
void morrisInorder(TreeNode root) {
    TreeNode current = root;
    while (current != null) {
        if (current.left == null) {
            System.out.print(current.val + " ");
            current = current.right;
        } else {
            TreeNode pre = current.left;
            while (pre.right != null && pre.right != current) pre = pre.right;
            if (pre.right == null) {
                pre.right = current;
                current = current.left;
            } else {
                pre.right = null;
                System.out.print(current.val + " ");
                current = current.right;
            }
        }
    }
}
```

# // === 2. TREE CONSTRUCTION ===

```java
import java.util.HashMap;
import java.util.Map;

// Assuming TreeNode class with `val` instead of `data` for consistency with provided code patterns
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

TreeNode buildTreeFromInPost(int[] inorder, int[] postorder) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) map.put(inorder[i], i);
    return buildIP(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
}
TreeNode buildIP(int[] in, int is, int ie, int[] post, int ps, int pe, Map<Integer, Integer> map) {
    if (is > ie || ps > pe) return null;
    TreeNode root = new TreeNode(post[pe]);
    int inRoot = map.get(post[pe]);
    int leftSize = inRoot - is;
    root.left = buildIP(in, is, inRoot - 1, post, ps, ps + leftSize - 1, map);
    root.right = buildIP(in, inRoot + 1, ie, post, ps + leftSize, pe - 1, map);
    return root;
}

TreeNode buildBSTFromSortedArray(int[] nums, int l, int r) {
    if (l > r) return null;
    int mid = (l + r) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    root.left = buildBSTFromSortedArray(nums, l, mid - 1);
    root.right = buildBSTFromSortedArray(nums, mid + 1, r);
    return root;
}

TreeNode bstFromPreorder(int[] preorder) {
    return bstPre(preorder, new int[]{0}, Integer.MIN_VALUE, Integer.MAX_VALUE);
}
TreeNode bstPre(int[] pre, int[] index, int min, int max) {
    if (index[0] == pre.length) return null;
    int val = pre[index[0]];
    if (val < min || val > max) return null;
    TreeNode root = new TreeNode(val);
    index[0]++;
    root.left = bstPre(pre, index, min, val);
    root.right = bstPre(pre, index, val, max);
    return root;
}
```

# // === 3. TREE SEARCH & QUERIES ===

```java
// Assuming TreeNode class with `val` instead of `data` for consistency
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

TreeNode searchBST(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
}

TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    return left != null ? left : right;
}

// Global variable for diameter - common pattern
private int maxDiameter = 0; // Initialize somewhere accessible to diameterOfBinaryTree

public int diameterOfBinaryTree(TreeNode root) {
    maxDiameter = 0; // Reset for each call if this is a public method
    depth(root); // depth function will update maxDiameter
    return maxDiameter;
}

// Helper for diameterOfBinaryTree (calculates height and updates diameter)
private int depth(TreeNode node) {
    if (node == null) return 0;
    int leftHeight = depth(node.left);
    int rightHeight = depth(node.right);
    maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight); // Path through current node
    return Math.max(leftHeight, rightHeight) + 1; // Height of current node's subtree
}
```

# // path sum

```java
import java.util.ArrayList;
import java.util.List;

// Assuming TreeNode class with `val` instead of `data`
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) return false;
    if (root.left == null && root.right == null) return root.val == sum;
    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
}

void allPaths(TreeNode root, List<Integer> path, List<List<Integer>> res) {
    if (root == null) return;
    path.add(root.val);
    if (root.left == null && root.right == null) res.add(new ArrayList<>(path));
    allPaths(root.left, path, res);
    allPaths(root.right, path, res);
    path.remove(path.size() - 1); // Backtrack
}
```

# // === 4. TREE PROPERTIES ===

```java
// Assuming TreeNode class with `val` instead of `data`
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

int height(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(height(root.left), height(root.right));
}

boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}
int checkHeight(TreeNode root) {
    if (root == null) return 0;
    int lh = checkHeight(root.left);
    int rh = checkHeight(root.right);
    if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1) return -1;
    return 1 + Math.max(lh, rh);
}

int countNodes(TreeNode root) {
    if (root == null) return 0;
    return 1 + countNodes(root.left) + countNodes(root.right);
}

int leafCount(TreeNode root) {
    if (root == null) return 0;
    if (root.left == null && root.right == null) return 1;
    return leafCount(root.left) + leafCount(root.right);
}

// Global variable for maxPath - common pattern
private int maxPath = Integer.MIN_VALUE; // Initialize somewhere accessible to maxPathSum

public int maxPathSum(TreeNode root) {
    maxPath = Integer.MIN_VALUE; // Reset for each call if this is a public method
    getMaxPath(root); // getMaxPath will update maxPath
    return maxPath;
}
private int getMaxPath(TreeNode root) {
    if (root == null) return 0;
    int l = Math.max(0, getMaxPath(root.left)); // Max gain from left child (0 if negative)
    int r = Math.max(0, getMaxPath(root.right)); // Max gain from right child (0 if negative)
    maxPath = Math.max(maxPath, l + r + root.val); // Update global max path through current node
    return root.val + Math.max(l, r); // Max gain from current node to its parent
}

TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    root.left = right;
    root.right = left;
    return root;
}
```

# // === 5. TREE TRANSFORMATIONS ===

```java
// Assuming TreeNode class with `val` instead of `data`
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

// Global variable for prevNode - common pattern
private TreeNode prevNode = null; // Initialize somewhere accessible to flatten

public void flatten(TreeNode root) {
    if (root == null) return;
    // Process right subtree first (post-order traversal like for this specific flattening)
    flatten(root.right);
    // Then process left subtree
    flatten(root.left);

    // Current node's right points to the previously processed node (which is flattened right subtree)
    root.right = prevNode;
    // Current node's left is set to null
    root.left = null;
    // Update prevNode to the current node for the next iteration
    prevNode = root;
}

// Global variable for sum - common pattern
private int sum = 0; // Initialize somewhere accessible to convertBST

public TreeNode convertBST(TreeNode root) {
    if (root == null) return null;
    convertBST(root.right); // Traverse right (larger values first)
    sum += root.val; // Add current node's value to cumulative sum
    root.val = sum; // Update current node's value
    convertBST(root.left); // Traverse left
    return root;
}
```
