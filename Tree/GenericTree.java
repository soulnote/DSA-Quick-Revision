package Tree;

/* Generic tree construction */

import java.util.*;
import java.io.*;

public class GenericTree
{
    // Node representation
    private static class Node{
            int data;
            ArrayList<Node> children;
            public Node(int data){
                this.data = data;
                children = new ArrayList<>();
            }
    }
    
    public static Node constructor(int[]arr){    
        Stack<Node> stack = new Stack<>(); // creating the stack
        Node root = new Node(arr[0]);
        // Traversing the given array
        
        for(int i=0 ; i<arr.length ; i++){
            
            if(arr[i] == -1){
                stack.pop();
            } else {
                
                Node treeNode = new Node(arr[i]);//creating new node
                // treeNode.data = arr[i];
                // stack is not empty 
                if( stack.size() > 0 ){
                    Node top = stack.peek();
                    top.children.add(treeNode); // making the new node top node’s child
                    
                } else {
                    // Stack is empty
                    root = treeNode;
                }
                stack.push(treeNode); // pushing the new node in stack
            }
            
        }
        return root;
    }

    public static void display(Node node) {
        String str = node.data + "->";
        for(Node child : node.children){
            str+= child.data + ",";
        }
        str+=".";
        System.out.println(str);
        for(Node child : node.children){
            display(child);
        }
    }
    public static void main (String[] args) 
    {
        //Input
        int arr[] = {10,20,50,-1,60,-1,-1,30,70,-1,80,110,-1,120,-1,-1,90,-1,-1,40,100,-1,-1,-1};
        
        Node root = constructor(arr); // initializing root node
        display(root);
    }

    
}