# 1. Generic Tree
This code defines a `GenericTree` class that constructs and displays a generic tree from an array representation. It performs the following tasks:

1. Defines a nested `Node` class to represent each node in the tree.
2. Implements a method `constructor` to construct the tree from an array representation using a stack-based approach.
3. Provides a method `display` to print the tree structure recursively.
4. In the `main` method, constructs a tree from a predefined array and displays its structure.
```java
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
    public static Node constructor(int[] arr) {
        Stack<Node> stack = new Stack<>(); // Stack to keep track of nodes
        Node root = new Node(arr[0]); // Create root node with first element of the array
        
        // Traverse the array to construct the tree
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1) {
                stack.pop(); // Pop the stack if encountering -1 (end of subtree)
            } else {
                Node treeNode = new Node(arr[i]); // Create a new node with data from the array
                if (!stack.isEmpty()) {
                    Node top = stack.peek();
                    top.children.add(treeNode); // Add the new node as a child of the top node in the stack
                } else {
                    root = treeNode; // If stack is empty, set the new node as root
                }
                stack.push(treeNode); // Push the new node to the stack
            }
        }
        return root; // Return the root of the constructed tree
    }

    // Method to display the tree structure
    public static void display(Node node) {
        String str = node.data + "->"; // String to represent the current node and its children
        for (Node child : node.children) {
            str += child.data + ","; // Add children data to the string
        }
        str += "."; // Add a dot to indicate end of children
        System.out.println(str); // Print the string
        for (Node child : node.children) {
            display(child); // Recursively display children nodes
        }
    }
    
    // Main method to test the tree construction and display
    public static void main(String[] args) {
        // Input array representing the tree structure
        int arr[] = {10,20,50,-1,60,-1,-1,30,70,-1,80,110,-1,120,-1,-1,90,-1,-1,40,100,-1,-1,-1};
        
        // Construct the tree
        Node root = constructor(arr);
        
        // Display the tree structure
        display(root);
    }
}
```

# 2. Binary Tree
This code implements a binary tree and performs an in-order traversal using an iterative approach with a stack. 

- The `Node` class represents each node in the binary tree.
- The `BinaryTree` class contains a method `inOrderTrav` to perform in-order traversal iteratively.
- The `main` method creates a binary tree and calls the `inOrderTrav` method to perform in-order traversal, printing the result.
```java
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
    
    // Method to perform in-order traversal of the binary tree
    static ArrayList<Integer> inOrderTrav(Node curr) {
        ArrayList<Integer> inOrder = new ArrayList<>(); // List to store in-order traversal
        
        Stack<Node> s = new Stack<>(); // Stack to store nodes during traversal
        
        while (true) {
            if (curr != null) {
                s.push(curr); // Push current node onto stack
                curr = curr.left; // Move to left child
            } else {
                if (s.isEmpty()) break; // If stack is empty, break the loop
                curr = s.peek(); // Set current node to top of stack
                inOrder.add(curr.data); // Add current node's data to inOrder list
                s.pop(); // Pop the current node from stack
                curr = curr.right; // Move to right child
            }
        }
        return inOrder; // Return in-order traversal list
    }

    // Main method to test the binary tree operations
    public static void main(String args[]) {
        // Creating a binary tree
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

        // Displaying in-order traversal
        System.out.println("The inOrder Traversal is : ");
        for (int i = 0; i < inOrder.size(); i++) {
            System.out.print(inOrder.get(i) + " ");
        }
    }
}
```
# 3. Binary search tree
This code defines a `BinarySearchTree` class that implements operations on a binary search tree:

1. It represents each node of the binary search tree with a nested `Node` class.
2. The `insert` method adds a new node to the binary search tree while maintaining the BST property.
3. The `minNode` method finds the minimum node in a subtree.
4. The `deleteNode` method deletes a given node from the binary search tree while preserving the BST property.
5. The `inorderTraversal` method performs an inorder traversal of the binary search tree.
6. In the `main` method, it creates a binary search tree, inserts nodes, deletes nodes, and performs inorder traversal to display the tree structure.
```java
package Tree;

public class BinarySearchTree {  
  
    //Represent a node of binary tree  
    public static class Node{  
        int data;  
        Node left;  
        Node right;  
  
        public Node(int data){  
            //Assign data to the new node, set left and right children to null  
            this.data = data;  
            this.left = null;  
            this.right = null;  
        }  
      }  
  
      //Represent the root of binary tree  
      public Node root;  
  
      public BinarySearchTree(){  
          root = null;  
      }  
  
      //insert() will add new node to the binary search tree  
      public void insert(int data) {  
          //Create a new node  
          Node newNode = new Node(data);  
  
          //Check whether tree is empty  
          if(root == null){  
              root = newNode;  
              return;  
            }  
          else {  
              //current node point to root of the tree  
              Node current = root, parent = null;  
  
              while(true) {  
                  //parent keep track of the parent node of current node.  
                  parent = current;  
  
                  //If data is less than current's data, node will be inserted to the left of tree  
                  if(data < current.data) {  
                      current = current.left;  
                      if(current == null) {  
                          parent.left = newNode;  
                          return;  
                      }  
                  }  
                  //If data is greater than current's data, node will be inserted to the right of tree  
                  else {  
                      current = current.right;  
                      if(current == null) {  
                          parent.right = newNode;  
                          return;  
                      }  
                  }  
              }  
          }  
      }  
  
      //minNode() will find out the minimum node  
      public Node minNode(Node root) {  
          if (root.left != null)  
              return minNode(root.left);  
          else  
              return root;  
      }  
  
      //deleteNode() will delete the given node from the binary search tree  
      public Node deleteNode(Node node, int value) {  
          if(node == null){  
              return null;  
           }  
          else {  
              //value is less than node's data then, search the value in left subtree  
              if(value < node.data)  
                  node.left = deleteNode(node.left, value);  
  
              //value is greater than node's data then, search the value in right subtree  
              else if(value > node.data)  
                  node.right = deleteNode(node.right, value);  
  
              //If value is equal to node's data that is, we have found the node to be deleted  
              else {  
                  //If node to be deleted has no child then, set the node to null  
                  if(node.left == null && node.right == null)  
                      node = null;  
  
                  //If node to be deleted has only one right child  
                  else if(node.left == null) {  
                      node = node.right;  
                  }  
  
                  //If node to be deleted has only one left child  
                  else if(node.right == null) {  
                      node = node.left;  
                  }  
  
                  //If node to be deleted has two children node  
                  else {  
                      //then find the minimum node from right subtree  
                      Node temp = minNode(node.right);  
                      //Exchange the data between node and temp  
                      node.data = temp.data;  
                      //Delete the node duplicate node from right subtree  
                      node.right = deleteNode(node.right, temp.data);  
                  }  
              }  
              return node;  
          }  
      }  
  
      //inorder() will perform inorder traversal on binary search tree  
      public void inorderTraversal(Node node) {  
  
          //Check whether tree is empty  
          if(root == null){  
              System.out.println("Tree is empty");  
              return;  
           }  
          else {  
  
              if(node.left!= null)  
                  inorderTraversal(node.left);  
              System.out.print(node.data + " ");  
              if(node.right!= null)  
                  inorderTraversal(node.right);  
  
          }  
      }  
  
      public static void main(String[] args) {  
  
          BinarySearchTree bt = new BinarySearchTree();  
          //Add nodes to the binary tree  
          bt.insert(50);  
          bt.insert(30);  
          bt.insert(70);  
          bt.insert(60);  
          bt.insert(10);  
          bt.insert(90);  
  
          System.out.println("Binary search tree after insertion:");  
          //Displays the binary tree  
          bt.inorderTraversal(bt.root);  
  
          Node deletedNode = null;  
          //Deletes node 90 which has no child  
          deletedNode = bt.deleteNode(bt.root, 90);  
          System.out.println("\nBinary search tree after deleting node 90:");  
          bt.inorderTraversal(bt.root);  
  
          //Deletes node 30 which has one child  
          deletedNode = bt.deleteNode(bt.root, 30);  
          System.out.println("\nBinary search tree after deleting node 30:");  
          bt.inorderTraversal(bt.root);  
  
          //Deletes node 50 which has two children  
          deletedNode = bt.deleteNode(bt.root, 50);  
          System.out.println("\nBinary search tree after deleting node 50:");  
          bt.inorderTraversal(bt.root);  
      }  
}  
```

# Tree Data Structure
A tree is a hierarchical data structure that consists of nodes connected by edges. It is widely used in computer science for representing hierarchical relationships and organizing data efficiently. Here’s an overview of key concepts related to trees:

### **Basic Terminology**

- **Node**: The fundamental part of a tree, which contains data and may have references to other nodes.
- **Root**: The top node of the tree from which all other nodes descend.
- **Leaf**: A node with no children.
- **Internal Node**: A node with at least one child.
- **Edge**: The connection between two nodes.
- **Subtree**: A tree consisting of a node and its descendants.
- **Depth**: The length of the path from the root to a node.
- **Height**: The length of the longest path from a node to a leaf.
- **Level**: The distance of a node from the root. The root is at level 0.
- **Degree**: The number of children a node has.

### **Types of Trees**

1. **Binary Tree**
   - **Definition**: Each node has at most two children, referred to as the left child and the right child.
   - **Types**:
     - **Full Binary Tree**: Every node other than the leaves has exactly two children.
     - **Complete Binary Tree**: All levels are fully filled except possibly the last level, which is filled from left to right.
     - **Perfect Binary Tree**: All internal nodes have two children and all leaves are at the same level.
     - **Balanced Binary Tree**: The height of the left and right subtrees of any node differ by at most one.
     - **Binary Search Tree (BST)**: For each node, the left subtree contains only nodes with values less than the node’s value, and the right subtree only nodes with values greater than the node’s value.

2. **N-ary Tree**
   - **Definition**: Each node can have at most `n` children. A common type is the ternary tree, where each node has up to three children.

3. **Trie**
   - **Definition**: A special type of tree used to store associative data structures, often used in implementing dictionaries.

4. **Heap**
   - **Definition**: A specialized tree-based structure that satisfies the heap property (e.g., a max-heap where the parent is greater than or equal to its children).

5. **AVL Tree**
   - **Definition**: A self-balancing binary search tree where the heights of the two child subtrees of any node differ by at most one.

6. **Red-Black Tree**
   - **Definition**: A self-balancing binary search tree where each node contains an extra bit for color (red or black) to ensure the tree remains balanced during insertions and deletions.

### **Basic Operations**

1. **Traversal**
   - **Inorder Traversal**: Visit left subtree, root, then right subtree.
   - **Preorder Traversal**: Visit root, then left subtree, then right subtree.
   - **Postorder Traversal**: Visit left subtree, right subtree, then root.
   - **Level Order Traversal**: Visit nodes level by level.

2. **Insertion**
   - **Binary Search Tree**: Insert nodes while maintaining the BST property.
   - **Heap**: Insert nodes and then perform heapify to maintain the heap property.

3. **Deletion**
   - **Binary Search Tree**: Remove nodes and rearrange to maintain BST property.
   - **Heap**: Remove the root and then perform heapify to maintain the heap property.

### **Example Implementations**

1. **Binary Tree Implementation**

```java
class TreeNode {
    int data;
    TreeNode left, right;

    TreeNode(int item) {
        data = item;
        left = right = null;
    }
}

public class BinaryTree {
    TreeNode root;

    void inorderTraversal(TreeNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.data + " ");
            inorderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);

        System.out.println("Inorder traversal:");
        tree.inorderTraversal(tree.root);
    }
}
```

2. **Binary Search Tree (BST) Implementation**

```java
class BSTNode {
    int data;
    BSTNode left, right;

    BSTNode(int item) {
        data = item;
        left = right = null;
    }
}

public class BinarySearchTree {
    BSTNode root;

    void insert(int key) {
        root = insertRec(root, key);
    }

    BSTNode insertRec(BSTNode root, int key) {
        if (root == null) {
            root = new BSTNode(key);
            return root;
        }
        if (key < root.data) {
            root.left = insertRec(root.left, key);
        } else if (key > root.data) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    void inorder() {
        inorderRec(root);
    }

    void inorderRec(BSTNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        System.out.println("Inorder traversal:");
        bst.inorder();
    }
}
```

### **Use Cases**

- **Binary Search Trees**: Efficient searching, insertion, and deletion operations.
- **Heaps**: Priority queues, heap sort.
- **Tries**: Efficient string retrieval and search operations.
- **AVL and Red-Black Trees**: Balanced tree structures for maintaining sorted data.


# All tree types implementation 
Sure, let's dive into the implementation of various tree data structures, along with explanations and use cases. Here's an overview and code for each:

### 1. **N-ary Tree**

#### **Definition:**
An N-ary tree is a tree data structure where each node can have up to `n` children. It is a generalization of a binary tree where the number of children is not limited to two.

#### **Use Cases:**
- Representing hierarchical data like organizational structures.
- Storing file systems where directories can contain multiple files and subdirectories.

#### **Implementation:**

```java
import java.util.ArrayList;
import java.util.List;

class NaryNode {
    int data;
    List<NaryNode> children;

    NaryNode(int item) {
        data = item;
        children = new ArrayList<>();
    }
}

public class NaryTree {
    NaryNode root;

    void printTree(NaryNode node, int level) {
        if (node == null) return;
        for (int i = 0; i < level; i++) System.out.print("  ");
        System.out.println(node.data);
        for (NaryNode child : node.children) {
            printTree(child, level + 1);
        }
    }

    public static void main(String[] args) {
        NaryTree tree = new NaryTree();
        tree.root = new NaryNode(1);
        NaryNode child1 = new NaryNode(2);
        NaryNode child2 = new NaryNode(3);
        NaryNode child3 = new NaryNode(4);
        tree.root.children.add(child1);
        tree.root.children.add(child2);
        tree.root.children.add(child3);
        child1.children.add(new NaryNode(5));
        child1.children.add(new NaryNode(6));
        child2.children.add(new NaryNode(7));
        
        System.out.println("N-ary Tree:");
        tree.printTree(tree.root, 0);
    }
}
```

### 2. **Trie**

#### **Definition:**
A Trie (pronounced as "try") is a tree-like data structure used to store a dynamic set of strings, where keys are usually strings. It is used to retrieve keys in a dataset efficiently.

#### **Use Cases:**
- Implementing dictionaries.
- Autocomplete systems and spell-checking.

#### **Implementation:**

```java
import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;

    public TrieNode() {
        isEndOfWord = false;
    }
}

public class Trie {
    private final TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        node.isEndOfWord = true;
    }

    boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return false;
        }
        return node.isEndOfWord;
    }

    boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            node = node.children.get(ch);
            if (node == null) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // true
        System.out.println(trie.search("app"));     // false
        System.out.println(trie.startsWith("app")); // true
    }
}
```

### 3. **Heap**

#### **Definition:**
A Heap is a specialized tree-based data structure that satisfies the heap property. In a max-heap, for any given node `i`, the value of `i` is greater than or equal to the values of its children. In a min-heap, the value of `i` is less than or equal to the values of its children.

#### **Use Cases:**
- Priority queues.
- Heap sort algorithm.

#### **Implementation:**

```java
import java.util.PriorityQueue;

public class MaxHeap {
    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

    void insert(int val) {
        maxHeap.add(val);
    }

    int extractMax() {
        return maxHeap.poll();
    }

    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);

        System.out.println("Max Heap Extract Max: " + heap.extractMax()); // Output: 20
    }
}
```

### 4. **AVL Tree**

#### **Definition:**
An AVL Tree is a self-balancing binary search tree where the difference between the heights of left and right subtrees of any node is at most one.

#### **Use Cases:**
- Maintaining sorted data with efficient insertion, deletion, and search operations.

#### **Implementation:**

```java
class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int item) {
        key = item;
        height = 1;
    }
}

public class AVLTree {
    private AVLNode root;

    int height(AVLNode N) {
        if (N == null) return 0;
        return N.height;
    }

    int getBalance(AVLNode N) {
        if (N == null) return 0;
        return height(N.left) - height(N.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int key) {
        if (node == null) return new AVLNode(key);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key) return rightRotate(node);
        if (balance < -1 && key > node.right.key) return leftRotate(node);
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void preOrder(AVLNode node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);

        System.out.println("Preorder traversal of the constructed AVL tree is:");
        tree.preOrder(tree.root);
    }
}
```

### 5. **Red-Black Tree**

#### **Definition:**
A Red-Black Tree is a balanced binary search tree with an additional property that each node has a color attribute (red or black). It maintains balance by ensuring that the tree remains approximately balanced after insertions and deletions.

#### **Use Cases:**
- Implementing associative arrays.
- Maintaining balanced search trees.

#### **Implementation:**

A full implementation of a Red-Black Tree is quite involved, so here’s a simplified version focusing on insertion and basic balancing:

```java
class RedBlackNode {
    int data;
    RedBlackNode left, right;
    boolean color; // True for red, false for black

    RedBlackNode(int data) {
        this.data = data;
        this.color = true; // new nodes are red
        left = right = null;
    }
}

public class RedBlackTree {
    private RedBlackNode root;

    // Helper methods for insertion, balancing, rotations, etc.
    // (A full implementation would include these methods)

    void insert(int data) {
        root = insert(root, data);
        root.color = false; // root is always black
    }

    RedBlackNode insert(RedBlackNode node, int data) {
        // Standard BST insertion
        if (node == null) return new RedBlackNode(data);

        if (data < node.data) {
            node.left =

 insert(node.left, data);
        } else if (data > node.data) {
            node.right = insert(node.right, data);
        }

        // Balancing operations would be performed here

        return node;
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);

        // Traversal methods would be implemented here
    }
}
```

### 6. **B-Tree**

#### **Definition:**
A B-Tree is a self-balancing tree data structure that maintains sorted data and allows searches, sequential access, insertions, and deletions in logarithmic time. It is designed to work efficiently on disk storage systems.

#### **Use Cases:**
- Database indexing.
- File systems.

#### **Implementation:**

A full B-Tree implementation is complex. Here’s a high-level view:

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BTreeNode {
    int t; // Minimum degree (defines the range for number of keys)
    List<Integer> keys;
    List<BTreeNode> children;
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    // Additional methods for insertion, deletion, searching would be needed
}

public class BTree {
    BTreeNode root;
    int t;

    BTree(int t) {
        this.root = new BTreeNode(t, true);
        this.t = t;
    }

    // Methods for insertion, traversal, etc., would be implemented here

    public static void main(String[] args) {
        BTree btree = new BTree(3); // Create a B-Tree with minimum degree 3
        // Operations such as insertion and traversal would be performed here
    }
}
```

### 7. **B+ Tree**

#### **Definition:**
A B+ Tree is an extension of the B-Tree where all values are stored in the leaf nodes and internal nodes store only keys. It maintains sorted data and supports efficient insertion, deletion, and search operations.

#### **Use Cases:**
- Database indexing.
- Filesystems and other applications requiring ordered data.

#### **Implementation:**

A full B+ Tree implementation is complex. Here’s a high-level view:

```java
import java.util.ArrayList;
import java.util.List;

class BPlusNode {
    int t; // Minimum degree
    List<Integer> keys;
    List<BPlusNode> children;
    boolean leaf;

    BPlusNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    // Additional methods for insertion, deletion, searching, etc., would be needed
}

public class BPlusTree {
    BPlusNode root;
    int t;

    BPlusTree(int t) {
        this.root = new BPlusNode(t, true);
        this.t = t;
    }

    // Methods for insertion, traversal, etc., would be implemented here

    public static void main(String[] args) {
        BPlusTree bplusTree = new BPlusTree(3); // Create a B+ Tree with minimum degree 3
        // Operations such as insertion and traversal would be performed here
    }
}
```

### Summary

- **N-ary Tree**: Useful for hierarchical data structures.
- **Trie**: Ideal for dictionary implementations and prefix-based searches.
- **Heap**: Used for priority queues and heap sort.
- **AVL Tree**: Maintains balanced binary search trees for efficient data operations.
- **Red-Black Tree**: Balances binary search trees with color properties for efficient operations.
- **B-Tree**: Efficiently handles large amounts of data in databases and file systems.
- **B+ Tree**: Extends B-Tree with improved performance for range queries and disk-based storage systems.

# Interview questions and answers
Here are some common tree-related interview questions along with their answers and sample Java implementations:

### 1. **What is the difference between a binary tree and a binary search tree (BST)?**

**Answer**: 
- **Binary Tree**: A tree where each node has at most two children, but there are no constraints on the values of the nodes.
- **Binary Search Tree (BST)**: A binary tree where the left child of a node contains values less than the node’s value, and the right child contains values greater than the node’s value.

### 2. **How do you traverse a binary tree?**

**Answer**: 
Traversal methods for binary trees include:
- **Inorder (Left, Root, Right)**: Visit left subtree, root node, then right subtree.
- **Preorder (Root, Left, Right)**: Visit root node, then left subtree, then right subtree.
- **Postorder (Left, Right, Root)**: Visit left subtree, then right subtree, then root node.
- **Level Order**: Visit nodes level by level from top to bottom.

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

### 3. **How do you find the height of a binary tree?**

**Answer**: The height of a binary tree is the length of the longest path from the root to a leaf. It can be computed recursively by finding the maximum height of the left and right subtrees and adding 1.

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

### 4. **What is a Balanced Tree?**

**Answer**: A balanced tree is a tree where the height of the two subtrees of any node differ by no more than one. AVL Trees and Red-Black Trees are examples of balanced trees.

### 5. **How do you check if a binary tree is a BST?**

**Answer**: A binary tree is a BST if, for every node, the value of all nodes in the left subtree is less than the node’s value, and the value of all nodes in the right subtree is greater than the node’s value. This can be checked recursively with range constraints.

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

### 6. **How do you find the Lowest Common Ancestor (LCA) of two nodes in a binary tree?**

**Answer**: The LCA of two nodes `n1` and `n2` is the deepest node that is an ancestor of both `n1` and `n2`. It can be found by traversing the tree and checking if both nodes are present in the left or right subtree.

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

### 7. **How do you find the diameter of a binary tree?**

**Answer**: The diameter of a binary tree is the length of the longest path between any two nodes. It can be found by recursively computing the height of left and right subtrees and updating the diameter.

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

### 8. **How do you check if a binary tree is symmetric?**

**Answer**: A binary tree is symmetric if its left subtree is a mirror image of its right subtree. This can be checked by recursively comparing the left subtree with the mirrored right subtree.

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

### 9. **How do you flatten a binary tree to a linked list?**

**Answer**: Flatten a binary tree to a linked list in-place by converting the tree into a list where each node’s right child points to the next node in the preorder traversal and left child is null.

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

### 10. **How do you find the Kth smallest element in a BST?**

**Answer**: Use an in-order traversal to get elements in ascending order and then return the Kth element.

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
