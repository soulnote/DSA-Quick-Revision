# Prim's Algorithm Kya Hai?

Prim's algorithm ek **greedy algorithm** hai jo ek connected, undirected graph ke liye **Minimum Spanning Tree (MST)** find karta hai. MST ek subgraph hota hai jo saare vertices ko connect karta hai, usme koi cycle nahi hota, aur saare edges ke weights ka sum minimum hota hai.

**Short mein:**

  * **Goal:** Ek graph ke saare nodes ko minimum possible total edge weight se connect karna.
  * **Output:** Ek tree (jisme N nodes aur N-1 edges honge, aur koi cycle nahi hoga).
  * **Constraint:** Graph connected aur undirected hona chahiye. Edges ke weights positive ya zero ho sakte hain.

### Algorithm Ka Basic Idea (Intuition):

Socho aap ek network bana rahe ho aur aapko sabse kam cost mein saare shehron ko jodna hai. Prim's algorithm aise kaam karta hai:

1.  **Ek starting point chuno:** Kisi bhi node se shuru karo. Use apne MST mein shamil kar lo.
2.  **Sabse sasta rasta dhundo:** Jo nodes already MST mein hain, unse un nodes tak jaane wale edges mein se sabse kam weight wala edge chuno jo abhi tak MST mein nahi hain aur uss naye node ko MST mein shamil kar lo.
3.  **Dohrao:** Step 2 ko tab tak repeat karo jab tak saare nodes MST mein shamil na ho jayen.

### Step-by-Step Working:

1.  **Initialization:**

      * `minWeight[]`: Ek array jise `minWeight[i]` mein store hoga minimum weight jo `i` ko MST se connect karta hai. Isko `infinity` (e.g., `Integer.MAX_VALUE`) se initialize karein.
      * `inMST[]`: Ek boolean array jo track karega ki kaun se node already MST mein shamil ho chuke hain. Sabko `false` se initialize karein.
      * `parent[]`: Ek array jo MST mein har node ke parent ko store karega (path reconstruction ke liye useful).
      * Kisi bhi `startNode` ke liye, `minWeight[startNode] = 0` set karein.

2.  **Priority Queue:**

      * Ek `PriorityQueue` (min-heap) ka use karein jo `Pair(weight, node)` store karegi. `weight` ke basis par sorted hogi.
      * Initial mein `Pair(0, startNode)` ko PQ mein add karein.

3.  **Traversal/Selection:**

      * Jab tak PQ empty na ho jaye (ya `V` nodes process na ho jayen):
          * PQ se minimum weight wala `Pair(w, u)` extract karo (yani sabse sasta edge jo `u` tak le ja raha hai).
          * Agar `u` already `inMST` hai, toh continue karo (iski lowest weight already process ho chuki hai).
          * `inMST[u] = true` set karo.
          * Ab `u` ke saare adjacent nodes `v` par iterate karo:
              * Agar `v` `inMST` nahi hai (yani abhi tak MST mein shamil nahi hua) aur `weight(u, v) < minWeight[v]` hai (yani `u` se `v` tak ek naya, sasta rasta mila hai), toh:
                  * `minWeight[v] = weight(u, v)` update karo.
                  * `parent[v] = u` set karo.
                  * `Pair(minWeight[v], v)` ko PQ mein add karo.

4.  **Result:** Jab algorithm complete ho jaye, toh `minWeight` array mein sabhi edges ka total sum aapko MST ka total weight dega. `parent` array se aap actual MST ke edges reconstruct kar sakte hain.

### Time Complexity:

  * **Adjacency Matrix:** $O(V^2)$ (Jab graph dense ho)
  * **Adjacency List + Binary Heap (Priority Queue):** $O(E \\log V)$ ya $O(E + V \\log V)$. Ye sparse graphs ke liye zyada efficient hai.

### Java Implementation Example:

```java
import java.util.*;

// Helper class to store edge information (weight, destination node)
// For Prim's, we can use (weight, node) in PQ, where weight is the edge weight
// and node is the destination node of that edge.
class Edge implements Comparable<Edge> {
    int weight;
    int node; // The node connected by this edge

    public Edge(int weight, int node) {
        this.weight = weight;
        this.node = node;
    }

    // For PriorityQueue to sort by weight
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class PrimsMinimumSpanningTree {

    /**
     * Finds the sum of weights of the Minimum Spanning Tree (MST)
     * using Prim's algorithm.
     *
     * @param V   Number of vertices in the graph.
     * @param adj Adjacency list representation of the graph.
     * adj[u] contains a list of Edge objects (neighbor, weight).
     * @return The total minimum cost of the MST.
     */
    public int primsAlgo(int V, ArrayList<ArrayList<Edge>> adj) {
        // minWeight[i] will store the minimum weight to connect node i to the MST
        int[] minWeight = new int[V];
        Arrays.fill(minWeight, Integer.MAX_VALUE); // Initialize with infinity

        // inMST[i] tracks whether node i is already included in MST
        boolean[] inMST = new boolean[V];
        // By default, parent array is not needed for just sum of weights,
        // but useful for reconstructing the MST.
        // int[] parent = new int[V];
        // Arrays.fill(parent, -1);

        // PriorityQueue stores edges (weight, destination node)
        // It always extracts the edge with the smallest weight.
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Start from node 0 (can be any node)
        minWeight[0] = 0; // Cost to include the starting node in MST is 0
        pq.add(new Edge(0, 0)); // Add (weight=0, node=0) to PQ

        int totalMSTWeight = 0; // Sum of weights of edges in the MST
        int edgesInMSTCount = 0; // To track if all V-1 edges are added

        while (!pq.isEmpty() && edgesInMSTCount < V) {
            Edge current = pq.poll();
            int uWeight = current.weight; // Weight of edge to current node
            int u = current.node;         // Current node

            // If this node is already in MST, skip
            if (inMST[u]) {
                continue;
            }

            // Include 'u' in MST
            inMST[u] = true;
            totalMSTWeight += uWeight;
            edgesInMSTCount++; // Increment count of nodes/edges added to MST

            // Explore neighbors of 'u'
            for (Edge neighbor : adj.get(u)) {
                int v = neighbor.node;
                int weightUV = neighbor.weight;

                // If 'v' is not in MST and a shorter edge to 'v' is found
                if (!inMST[v] && weightUV < minWeight[v]) {
                    minWeight[v] = weightUV; // Update min weight to reach 'v'
                    // parent[v] = u; // If you need to reconstruct the path
                    pq.add(new Edge(minWeight[v], v)); // Add this potential edge to PQ
                }
            }
        }
        
        // After loop, check if all nodes were reached (graph was connected)
        // If edgesInMSTCount != V, then the graph was not connected
        // For connected graphs, totalMSTWeight will have the correct sum.
        return totalMSTWeight;
    }

    public static void main(String[] args) {
        PrimsMinimumSpanningTree solver = new PrimsMinimumSpanningTree();

        // Example Graph:
        // 0 -- 1 (2)
        // | \  / |
        // 6  8 5  10
        // |   / |
        // 2 -- 3 (4)
        // |    |
        // 7 -- 4 (9)
        int V = 5;
        ArrayList<ArrayList<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Add edges: (node1, node2, weight)
        addEdge(adj, 0, 1, 2);
        addEdge(adj, 0, 3, 6);
        addEdge(adj, 1, 2, 3);
        addEdge(adj, 1, 3, 8);
        addEdge(adj, 1, 4, 5);
        addEdge(adj, 2, 4, 7);
        addEdge(adj, 3, 4, 9);
        
        // Expected MST edges and total weight (starting from 0):
        // (0,1,2) -> MST: {0,1}, Cost: 2
        // (1,2,3) -> MST: {0,1,2}, Cost: 2+3=5
        // (1,4,5) -> MST: {0,1,2,4}, Cost: 5+5=10
        // (0,3,6) -> MST: {0,1,2,4,3}, Cost: 10+6=16
        // Total MST Weight: 16

        int mstWeight = solver.primsAlgo(V, adj);
        System.out.println("Minimum Spanning Tree Weight: " + mstWeight); // Expected: 16
        
        // Another example graph
        int V2 = 4;
        ArrayList<ArrayList<Edge>> adj2 = new ArrayList<>();
        for (int i = 0; i < V2; i++) {
            adj2.add(new ArrayList<>());
        }
        addEdge(adj2, 0, 1, 10);
        addEdge(adj2, 0, 2, 6);
        addEdge(adj2, 0, 3, 5);
        addEdge(adj2, 1, 3, 15);
        addEdge(adj2, 2, 3, 4);

        // Expected MST (e.g., starting from 0):
        // (0,3,5) -> MST: {0,3}, Cost: 5
        // (3,2,4) -> MST: {0,3,2}, Cost: 5+4=9
        // (0,1,10) -> MST: {0,3,2,1}, Cost: 9+10=19
        // Total MST Weight: 19
        int mstWeight2 = solver.primsAlgo(V2, adj2);
        System.out.println("Minimum Spanning Tree Weight for Graph 2: " + mstWeight2); // Expected: 19
    }

    // Helper method to add an undirected edge between u and v with given weight
    private static void addEdge(ArrayList<ArrayList<Edge>> adj, int u, int v, int weight) {
        adj.get(u).add(new Edge(weight, v));
        adj.get(v).add(new Edge(weight, u)); // For undirected graph
    }
}
```

### Dijkstra's vs. Prim's (Similarity & Difference):

Dijkstra's aur Prim's dono hi greedy algorithms hain aur Priority Queue ka use karte hain, isliye inki structure similar lag sakti hai.

  * **Dijkstra's (Shortest Path):**

      * **Goal:** Single source se baaki saare nodes tak **shortest path** find karna.
      * **What it minimizes:** Path ka **total accumulated weight** from the source.
      * **PQ stores:** `(current_total_distance, node)`
      * **Edge Relaxation:** `dist[u] + weight(u,v) < dist[v]`

  * **Prim's (MST):**

      * **Goal:** Poore graph ke saare nodes ko connect karne ke liye **minimum total edge weight** wala tree banana.
      * **What it minimizes:** Next edge ka **individual weight** jo MST ko expand kare.
      * **PQ stores:** `(edge_weight_to_connect_to_MST, node_to_be_connected)`
      * **Edge Relaxation:** `weight(u,v) < minWeight[v]`

Basically, Dijkstra's "how to reach this node from the source in the cheapest way" sochta hai, jabki Prim's "how to connect this new node to the existing MST in the cheapest way" sochta hai.
