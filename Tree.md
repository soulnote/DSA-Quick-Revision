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
