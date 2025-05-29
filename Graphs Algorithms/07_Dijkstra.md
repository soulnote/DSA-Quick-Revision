
**_Dijkstra’s Algorithm Use:_** Finds the shortest path in weighted graphs (non-negative weights).  
**_Applications:_** GPS navigation, network routing, pathfinding in games.


# Dijkstra’s Algorithm – Using Priority Queue
Given a weighted, undirected, and connected graph of V vertices and an adjacency list adj where adj[i] is a list of lists containing two integers where the first integer of each list j denotes there is an edge between i and j, second integers corresponds to the weight of that edge. You are given the source vertex S and You have to Find the shortest distance of all the vertex from the source vertex S. You have to return a list of integers denoting the shortest distance between each node and the Source vertex S.

Note: The Graph doesn’t contain any negative weight cycle.
Examples: 
![image](https://github.com/soulnote/LeetCode/assets/71943647/153995ee-e20c-4811-98b7-4a7734c4aed4)

Input:

V = 2

adj [] = {{{1, 9}}, {{0, 9}}}

S = 0

Output:

0 9

Explanation: 
The source vertex is 0. Hence, the shortest distance of node 0 from the source is 0 and the shortest distance of node 1 from source will be 9.

**Intution 1 using set:** We want the shortest path from a source to all nodes, so we always explore the closest unvisited node first.
We use a Set to keep track of nodes sorted by current shortest distance, always processing the node with minimum distance first.
When visiting a node’s neighbors, if we find a better (shorter) path, we update the distance and push the new pair into the set.
The set ensures that we always deal with the most promising paths first and discard longer paths to the same node.
This approach works only with positive weights, as negative weights can lead to incorrect updates.
## **Intution 2 using queue:** 
We find the shortest path from a source node to all others using a min-heap priority queue to always process the node with the smallest distance first.
At each step, we pick the top node (lowest distance), then update its neighbors’ distances if a shorter path is found.
The updated neighbors are pushed back into the queue, and this process continues until all nodes are processed.
This way, we always prioritize the most optimal (shortest) paths.
Note: Works only for graphs with non-negative weights.

```java
import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
         int V = 3, E=3,S=2;
    ArrayList<Integer> node1 = new ArrayList<Integer>() {{add(1);add(1);}};
    ArrayList<Integer> node2 = new ArrayList<Integer>() {{add(2);add(6);}};
    ArrayList<Integer> node3 = new ArrayList<Integer>() {{add(2);add(3);}};
    ArrayList<Integer> node4 = new ArrayList<Integer>() {{add(0);add(1);}};
    ArrayList<Integer> node5 = new ArrayList<Integer>() {{add(1);add(3);}};
    ArrayList<Integer> node6 = new ArrayList<Integer>() {{add(0);add(6);}};
    
    ArrayList<ArrayList<Integer>> inter1 = new ArrayList<ArrayList<Integer>>(){
      {
          add(node1);
          add(node2);
      }  
    };
    ArrayList<ArrayList<Integer>> inter2 = new ArrayList<ArrayList<Integer>>(){
      {
          add(node3);
          add(node4);
      }  
    };
    ArrayList<ArrayList<Integer>> inter3 = new ArrayList<ArrayList<Integer>>(){
      {
          add(node5);
          add(node6);
      }  
    };
    ArrayList<ArrayList<ArrayList<Integer>>> adj= new ArrayList<ArrayList<ArrayList<Integer>>>(){
        {
            add(inter1); // for 1st node
            add(inter2); // for 2nd node
            add(inter3); // for 3rd node
        }
    };
    //add final values of adj here.
    Solution obj = new Solution();
    int[] res= obj.dijkstra(V,adj,S);
    
    for(int i=0;i<V;i++){
        System.out.print(res[i]+" ");
    }
    System.out.println();
    
    }
}

class Pair{
    int node;
    int distance;
    public Pair(int distance,int node){
        this.node = node;
        this.distance = distance;
    }
}
//User function Template for Java
class Solution
{
    //Function to find the shortest distance of all the vertices
    //from the source vertex S.
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        // Create a priority queue for storing the nodes as a pair {dist, node
        // where dist is the distance from source to the node.  
        PriorityQueue<Pair> pq = 
        new PriorityQueue<Pair>((x,y) -> x.distance - y.distance);
        
        int []dist = new int[V]; 
      
        // Initialising distTo list with a large number to
        // indicate the nodes are unvisited initially.
        // This list contains distance from source to the nodes.
        for(int i = 0;i<V;i++) dist[i] = (int)(1e9); 
        
        // Source initialised with dist=0.
        dist[S] = 0;
        pq.add(new Pair(0,S)); 
        
        // Now, pop the minimum distance node first from the min-heap
        // and traverse for all its adjacent nodes.
        while(pq.size() != 0) {
            int dis = pq.peek().distance; 
            int node = pq.peek().node; 
            pq.remove(); 
            
            // Check for all adjacent nodes of the popped out
            // element whether the prev dist is larger than current or not.
            for(int i = 0;i<adj.get(node).size();i++) {
                int edgeWeight = adj.get(node).get(i).get(1); 
                int adjNode = adj.get(node).get(i).get(0); 
                
                // If current distance is smaller,
                // push it into the queue.
                if(dis + edgeWeight < dist[adjNode]) {
                    dist[adjNode] = dis + edgeWeight; 
                    pq.add(new Pair(dist[adjNode], adjNode)); 
                }
            }
        }
        // Return the list containing shortest distances
        // from source to all the nodes.
        return dist; 
    }
}
```
