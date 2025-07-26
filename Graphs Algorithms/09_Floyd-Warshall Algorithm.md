# Floyd-Warshall Algorithm Kya Hai?

Floyd-Warshall algorithm ek **All-Pairs Shortest Path** algorithm hai. Iska matlab hai ki yeh graph ke **har vertex pair** ke beech shortest path find karta hai, na ki sirf ek single source se baaki sabhi tak. Yeh algorithm bhi negative edge weights ko handle kar sakta hai, lekin **negative weight cycles ko nahi** (ya यूं कहें कि, negative cycles ko detect karta hai lekin unhe handle nahi kar paata shortest path calculation ke liye).

**Short mein:**

  * **Goal:** Graph ke **har node pair (i, j)** ke beech shortest path find karna.
  * **Khas baat:** Handles negative edge weights.
  * **Limitation:** Negative weight cycles ko handle nahi karta (ya unki presence mein invalid results dega).
  * **Graph Type:** Directed ya Undirected.

### Algorithm Ka Basic Idea (Dynamic Programming):

Floyd-Warshall algorithm Dynamic Programming principle par based hai. Yeh intermediate vertices ko incrementally consider karta hai.

Algorithm har `(i, j)` pair ke liye shortest path find karta hai, jisme path ke saare intermediate vertices sirf `0` se `k-1` tak ke vertices mein se hi ho sakte hain. Phir `k` ko `0` se `V-1` tak iterate karta hai.

Har step `k` par, algorithm decide karta hai ki kya `i` se `j` tak jaane ka rasta `k` through jaakar chhota ho sakta hai.

  * `dist[i][j]` = `i` se `j` tak ka shortest distance (initially, direct edge weight).
  * `k` = intermediate vertex.

Iska core logic yeh hai:
`dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])`

Yeh `k` loop sabse outer loop hota hai.

### Step-by-Step Working:

1.  **Initialization:**

      * Ek 2D array `dist[V][V]` banao.
      * `dist[i][j]` ko `graph[i][j]` se initialize karo (yani direct edge weight).
      * Agar `i` se `j` tak direct edge nahi hai, toh `dist[i][j]` ko `infinity` (e.g., `Integer.MAX_VALUE` ya ek bahut bada number) se set karo.
      * `dist[i][i]` ko `0` set karo (self-loop ka cost zero hota hai).

2.  **Triple Nested Loop (Main Logic):**

      * Outer loop `k` (intermediate vertex) from `0` to `V-1`.

      * Middle loop `i` (source vertex) from `0` to `V-1`.

      * Inner loop `j` (destination vertex) from `0` to `V-1`.

      * Andar, update karein:

          * `if (dist[i][k] != infinity && dist[k][j] != infinity)` (important check to prevent overflow/invalid sums)
          * `dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])`

3.  **Negative Cycle Detection (Optional, but Good Practice):**

      * Algorithm complete hone ke baad, agar `dist[i][i]` (kisi bhi `i` ke liye) negative ho jaata hai, toh iska matlab hai ki graph mein **negative weight cycle** hai.
      * Jab negative cycle hoti hai, toh shortest path well-defined nahi hota, kyuki aap cycle mein baar-baar ghoom kar path ki length ko infinitely chhota kar sakte hain.

### Time Complexity:

  * **$O(V^3)$**
      * Teen nested loops, har loop `V` times chalta hai.
      * Ye Dijkstra's ($O(E \\log V)$) aur Bellman-Ford ($O(V \\cdot E)$) se slower hai, khaaskar sparse graphs ke liye. Lekin, "all-pairs" shortest path find karne ke liye, ye kaafi simple aur effective hai.

### Java Implementation Example:

```java
import java.util.Arrays;

public class FloydWarshallAlgorithm {

    final static int INF = 99999; // A large value to represent infinity

    /**
     * Computes the shortest paths between all pairs of vertices in a graph
     * using the Floyd-Warshall algorithm.
     *
     * @param graph The adjacency matrix of the graph.
     * graph[i][j] represents the weight of the edge from i to j.
     * INF if no direct edge. 0 if i == j.
     * @return A 2D array representing the shortest distances between all pairs.
     * Returns null if a negative cycle is detected.
     */
    public int[][] floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        // Step 1: Initialize dist array with direct edge weights
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Step 2: Main Floyd-Warshall logic - considering intermediate vertices
        // k is the intermediate vertex
        for (int k = 0; k < V; k++) {
            // i is the source vertex
            for (int i = 0; i < V; i++) {
                // j is the destination vertex
                for (int j = 0; j < V; j++) {
                    // Avoid overflow if dist[i][k] or dist[k][j] is INF
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        // If path from i to k and then k to j is shorter than direct path from i to j
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // Step 3: Check for negative cycles
        // If dist[i][i] becomes negative, it means there's a negative cycle
        // reachable from/to i.
        for (int i = 0; i < V; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Graph contains a negative weight cycle!");
                return null; // Indicate negative cycle detected
            }
        }

        return dist;
    }

    // Helper method to print the solution matrix
    public void printSolution(int[][] dist) {
        System.out.println("Shortest distances between all pairs of vertices:");
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(String.format("%3d ", dist[i][j]));
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        FloydWarshallAlgorithm solver = new FloydWarshallAlgorithm();

        // Example Graph 1: No negative cycle
        // Edges: (0,1,3), (0,3,7), (1,0,8), (1,2,2), (2,0,5), (2,3,1), (3,0,2)
        int V1 = 4;
        int[][] graph1 = {
            {0,   3,   INF, INF},
            {8,   0,   2,   INF},
            {5,   INF, 0,   1},
            {2,   INF, INF, 0}
        };

        System.out.println("--- Graph 1 (No negative cycle) ---");
        int[][] dist1 = solver.floydWarshall(graph1);
        if (dist1 != null) {
            solver.printSolution(dist1);
        }
        // Expected Output:
        // Shortest distances between all pairs of vertices:
        //   0   3   5   6
        //   7   0   2   3
        //   3   6   0   1
        //   0   3   5   0


        System.out.println("\n--- Graph 2 (With negative cycle) ---");
        // Example Graph 2: With negative cycle
        // A cycle (1, 2, 3) where sum of weights is negative
        // 0 --(1)--> 1
        //          ^  |
        //          |  (-2)
        //          |  |
        //          3 <-(1)-- 2 --(-1)--> 3
        int V2 = 4;
        int[][] graph2 = {
            {0, INF, INF, INF},
            {INF, 0,  -2, INF}, // Edge 1 -> 2, weight -2
            {INF, INF, 0,  -1}, // Edge 2 -> 3, weight -1
            {INF, 1, INF, 0}    // Edge 3 -> 1, weight 1. Cycle 1->2->3->1 with sum -2 + -1 + 1 = -2 (negative)
        };
        // Add an edge from 0 to 1 to make it reachable from source 0
        graph2[0][1] = 1;


        int[][] dist2 = solver.floydWarshall(graph2);
        if (dist2 == null) {
            System.out.println("As expected, negative cycle detected.");
        }
    }
}
```

### Floyd-Warshall Ke Use Cases:

1.  **All-Pairs Shortest Path:** Jab aapko graph mein har possible pair of nodes ke beech shortest path distance chahiye. Jaise, Google Maps mein agar aapko har do shehron ke beech minimum drive time ka database banana ho.
2.  **Transitive Closure:** Graph ke transitive closure ko compute karne mein bhi iska use hota hai (check karna ki kya ek node se doosre node tak koi path exist karta hai).
3.  **Arbitrage Detection:** Currency exchange markets mein, jahan negative cycle ka matlab profit kamaaya ja sakta hai by exchanging currencies in a loop.

### Limitations:

  * **Higher Time Complexity:** $O(V^3)$ hone ke kaaran, bade graphs ke liye ye Bellman-Ford ya Dijkstra's se slow ho sakta hai.
  * **Memory Usage:** Adjacency matrix representation ke liye `O(V^2)` memory ki zaroorat padti hai.
  * **Negative Cycles:** Negative cycles ko handle nahi kar paata, bas detect karta hai. Agar shortest path calculation mein negative cycles ko 'deal' karna hai, toh Bellman-Ford preferred hai jo "unreachable" ya "infinitely decreasing" path specify kar sakta hai.
