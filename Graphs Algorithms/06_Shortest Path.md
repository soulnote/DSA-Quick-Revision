
# Shortest Path in Undirected Graph with unit distance
Given an Undirected Graph having unit weight, find the shortest path from the source to all other nodes in this graph. In this problem statement, we have assumed the source vertex to be ‘0’. If a vertex is unreachable from the source node, then return -1 for that vertex.

Example 1:
Input:
n = 9, m = 10
edges = [[0,1],[0,3],[3,4],[4 ,5],[5, 6],[1,2],[2,6],[6,7],[7,8],[6,8]]
src=0 
![image](https://github.com/soulnote/LeetCode/assets/71943647/074fb182-0416-4a6e-afa6-43f47716218b)
Output: 0 1 2 1 2 3 3 4 4

Explanation:
The above output array shows the shortest path to all 
the nodes from the source vertex (0), Dist[0] = 0, 
Dist[1] = 1 , Dist[2] = 2 , …. Dist[8] = 4 
Where Dist[node] is the shortest path between src and 
the node. For a node, if the value of Dist[node]= -1, 
then we conclude that the node is unreachable from 
the src node.

**Intution:** We use BFS because it explores nodes level by level, making it ideal when all edge weights are 1 (unit weights).
Start from the source node and track distances in a distance array, initializing all distances to a large number (infinity).
As we move to adjacent nodes, we update (relax) their distance only if we found a shorter path through the current node.
Whenever a node's distance is updated, we push it into the queue to continue BFS from there.
By the end, the distance array gives the shortest path from the source to all reachable nodes; unreachable ones stay as -1.
```java
import java.util.*;
import java.lang.*;
import java.io.*;

class Main{
    
    public static void main(String[] args) throws IOException{
        int n=9, m=10;
        int[][] edge = {{0,1},{0,3},{3,4},{4,5},{5,6},{1,2},{2,6},{6,7},{7,8},{6,8}};
          
        Solution obj = new Solution();
        int res[] = obj.shortestPath(edge,n,m,0);
        for(int i=0;i<n;i++){
            System.out.print(res[i]+" ");
        }
        System.out.println();
    }
}

class Solution {
    
    public int[] shortestPath(int[][] edges,int n,int m ,int src) {
    //Create an adjacency list of size N for storing the undirected graph.
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>(); 
        for(int i = 0;i<n;i++) {
            adj.add(new ArrayList<>()); 
        }
        for(int i = 0;i<m;i++) {
            adj.get(edges[i][0]).add(edges[i][1]); 
            adj.get(edges[i][1]).add(edges[i][0]); 
        }
    //A dist array of size N initialised with a large number to 
    //indicate that initially all the nodes are untraversed. 
        int dist[] = new int[n];
        for(int i = 0;i<n;i++) dist[i] = (int)1e9;
        dist[src] = 0; 

    // BFS Implementation
        Queue<Integer> q = new LinkedList<>();
        q.add(src); 
        while(!q.isEmpty()) {
            int node = q.peek(); 
            q.remove(); 
            for(int it : adj.get(node)) {
                if(dist[node] + 1 < dist[it]) {
                    dist[it] = 1 + dist[node]; 
                    q.add(it); 
                }
            }
        }
        // Updated shortest distances are stored in the resultant array ‘ans’.
        // Unreachable nodes are marked as -1. 
        for(int i = 0;i<n;i++) {
            if(dist[i] == 1e9) {
                dist[i] = -1; 
            }
        }
        return dist; 
    }
}
```

# Shortest Path in Directed Acyclic Graph Topological Sort
Given a DAG, find the shortest path from the source to all other nodes in this DAG. In this problem statement, we have assumed the source vertex to be ‘0’. You will be given the weighted edges of the graph.

Note: What is a DAG ( Directed Acyclic Graph)?

A Directed Graph (containing one-sided edges) having no cycles is said to be a Directed Acyclic Graph.

Example :
![image](https://github.com/soulnote/LeetCode/assets/71943647/fcc12b35-a9b0-49e3-acb0-236be142f176)
Input: n = 6, m= 7
edges =[[0,1,2],[0,4,1],[4,5,4],[4,2,2],[1,2,3],[2,3,6],[5,3,1]]

Output: 0 2 3 6 1 5

Explanation:  The above output list shows the shortest path to all the nodes from the source vertex (0).

**Intution:** In a DAG, we can find shortest paths efficiently by processing nodes in topological order, so we always visit a node after all its dependencies.
Using DFS, we create this order and store it in a stack.
Starting from the source, we relax edges (update shortest distance) for each node using dist[u] + weight < dist[v].
Since there are no cycles, this ensures we never re-process a node unnecessarily.
The final dist[] array holds the shortest distance from the source to every node.

```java
import java.util.*;
import java.lang.*;
import java.io.*;

class Main {

  public static void main(String[] args) throws IOException {
    int n = 6, m = 7;
    int[][] edge = {{0,1,2},{0,4,1},{4,5,4},{4,2,2},{1,2,3},{2,3,6},{5,3,1}};
    Solution obj = new Solution();
    int res[] = obj.shortestPath(n, m, edge);
    for (int i = 0; i < n; i++) {
      System.out.print(res[i] + " ");
    }
    System.out.println();
  }
}

class Pair {
  int first, second;
  Pair(int _first, int _second) {
    this.first = _first;
    this.second = _second;
  }
}
//User function Template for Java
class Solution {
  private void topoSort(int node, ArrayList < ArrayList < Pair >> adj,
    int vis[], Stack < Integer > st) {
    //This is the function to implement Topological sort. 

    vis[node] = 1;
    for (int i = 0; i < adj.get(node).size(); i++) {
      int v = adj.get(node).get(i).first;
      if (vis[v] == 0) {
        topoSort(v, adj, vis, st);
      }
    }
    st.add(node);
  }
  public int[] shortestPath(int N, int M, int[][] edges) {
    ArrayList < ArrayList < Pair >> adj = new ArrayList < > ();
    for (int i = 0; i < N; i++) {
      ArrayList < Pair > temp = new ArrayList < Pair > ();
      adj.add(temp);
    }
    //We create a graph first in the form of an adjacency list.

    for (int i = 0; i < M; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      int wt = edges[i][2];
      adj.get(u).add(new Pair(v, wt));
    }
    int vis[] = new int[N];
    //Now, we perform topo sort using DFS technique 
    //and store the result in the stack st.

    Stack < Integer > st = new Stack < > ();
    for (int i = 0; i < N; i++) {
      if (vis[i] == 0) {
        topoSort(i, adj, vis, st);
      }
    }
    //Further, we declare a vector ‘dist’ in which we update the value of the nodes’
    //distance from the source vertex after relaxation of a particular node.
    int dist[] = new int[N];
    for (int i = 0; i < N; i++) {
      dist[i] = (int)(1e9);
    }

    dist[0] = 0;
    while (!st.isEmpty()) {
      int node = st.peek();
      st.pop();

      for (int i = 0; i < adj.get(node).size(); i++) {
        int v = adj.get(node).get(i).first;
        int wt = adj.get(node).get(i).second;

        if (dist[node] + wt < dist[v]) {
          dist[v] = wt + dist[node];
        }
      }
    }

    for (int i = 0; i < N; i++) {
      if (dist[i] == 1e9) dist[i] = -1;
    }
    return dist;
  }
}
```
