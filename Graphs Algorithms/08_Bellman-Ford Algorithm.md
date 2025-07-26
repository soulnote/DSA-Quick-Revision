# Bellman-Ford Algorithm Kya Hai?

Bellman-Ford algorithm ek **single-source shortest path** algorithm hai jo ek directed graph mein source vertex se baaki saare vertices tak shortest paths find karta hai. Iski sabse khaas baat yeh hai ki yeh **negative edge weights** ko handle kar sakta hai, jo ki Dijkstra's algorithm nahi kar pata.

**Lekin, iski ek shart hai:** Agar graph mein **negative weight cycle** hai (ek cycle jiska total weight negative hai), toh Bellman-Ford use detect kar sakta hai aur shortest path calculate nahi kar paata (kyuki negative cycle hone se path ki length infinitely reduce ho sakti hai).

**Short mein:**

  * **Goal:** Single source se saare doosre nodes tak shortest path find karna.
  * **Khas baat:** Handles negative edge weights.
  * **Limitation/Feature:** Detects negative weight cycles.
  * **Graph Type:** Directed (undirected graph ko directed edges ke pair mein convert karke bhi use kar sakte hain).

### Algorithm Ka Basic Idea:

Bellman-Ford algorithm Dynamic Programming ke concept par based hai. Yeh path lengths ko `V-1` iterations mein relax karta hai, jahan `V` vertices ki sankhya hai. Har iteration mein, yeh har edge ko examine karta hai aur agar kisi node tak existing path ko ek edge ke through aur chhota kiya ja sakta hai, toh use update karta hai.

### Step-by-Step Working:

1.  **Initialization:**

      * `dist[]`: Ek array jise `dist[i]` mein source se `i` tak ka shortest distance store hoga. Isko `infinity` (e.g., `Integer.MAX_VALUE`) se initialize karein.
      * `dist[source] = 0` set karein.
      * `parent[]`: Optional array path reconstruction ke liye.

2.  **Relaxation (V-1 Iterations):**

      * Graph mein total `V` vertices hain, toh shortest path mein zyada se zyada `V-1` edges ho sakte hain. Isliye, algorithm `V-1` baar saare edges ko `relax` karta hai.
      * Har iteration (outer loop from `1` to `V-1`) mein:
          * Graph ke **har edge (u, v) with weight `w`** ko iterate karein.
          * **Relaxation Condition:** If `dist[u] != Integer.MAX_VALUE` (meaning `u` reachable hai) **AND** `dist[u] + w < dist[v]` hai, toh:
              * `dist[v] = dist[u] + w` update karein.
              * (`parent[v] = u` set karein, agar path reconstruct karna hai).

3.  **Negative Cycle Detection (V-th Iteration):**

      * `V-1` iterations ke baad, `dist[]` array mein source se sabhi reachable nodes tak shortest distances hone chahiye, provided koi negative cycle na ho.
      * Negative cycle detect karne ke liye, **ek aur (V-th) baar** saare edges ko iterate karein:
          * Agar kisi edge `(u, v)` with weight `w` ke liye `dist[u] != Integer.MAX_VALUE` **AND** `dist[u] + w < dist[v]` condition **still holds true**, toh iska matlab hai ki graph mein ek **negative weight cycle** hai jo `dist[]` values ko infinitely kam kar sakta hai. Is case mein, algorithm return kar sakta hai ki "Negative Cycle Detected".

### Time Complexity:

  * **$O(V \\cdot E)$**
      * `V-1` (ya `V`) iterations.
      * Har iteration mein, saare `E` edges par iterate karna padta hai.
      * Ye Dijkstra's ($O(E \\log V)$) se slower hai, lekin negative weights handle karne ki capability iski strength hai.

### Java Implementation Example:

`Edge` class define karte hain jisme `source`, `destination`, aur `weight` honge.

```java
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

// Helper class to represent an Edge in the graph
class Edge {
    int source;
    int destination;
    int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class BellmanFordAlgorithm {

    /**
     * Finds the shortest path from a source node to all other nodes
     * in a graph that may contain negative edge weights.
     * It can also detect negative cycles.
     *
     * @param V       Number of vertices.
     * @param edges   List of all edges in the graph.
     * @param source  The starting node for shortest paths.
     * @return An array of shortest distances. If Integer.MAX_VALUE, node is unreachable.
     * Returns null if a negative cycle is detected.
     */
    public int[] bellmanFord(int V, List<Edge> edges, int source) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize all distances to infinity
        dist[source] = 0; // Distance to source is 0

        // Step 2: Relax all edges V - 1 times
        // A path can have at most V-1 edges. So, after V-1 relaxations,
        // all shortest paths should be finalized if no negative cycle exists.
        for (int i = 1; i < V; i++) { // V-1 iterations
            boolean relaxedInThisIteration = false; // Optimization: if no relaxation, can stop early
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;

                // Relaxation condition
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    relaxedInThisIteration = true;
                }
            }
            // If no distance was updated in this iteration, means paths are finalized
            if (!relaxedInThisIteration) {
                break;
            }
        }

        // Step 3: Check for negative cycles
        // Perform one more iteration. If any distance can still be reduced,
        // it means there's a negative cycle reachable from the source.
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;

            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains a negative weight cycle!");
                return null; // Indicates negative cycle detected
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        BellmanFordAlgorithm solver = new BellmanFordAlgorithm();

        // Example Graph 1: No negative cycle
        // 0 --(6)--> 1 --(-1)--> 2
        // |          ^            |
        // (7)        |            (-2)
        // |          |            |
        // v          |            v
        // 3 --(9)--> 4 <---(4)---- 5
        // ^          |
        // |          (-3)
        // |          |
        // 6 <--------
        int V1 = 6; // Nodes 0 to 5
        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(0, 1, 6));
        edges1.add(new Edge(0, 2, 7));
        edges1.add(new Edge(1, 2, -1));
        edges1.add(new Edge(1, 3, 5));
        edges1.add(new Edge(2, 3, -4)); // Negative edge
        edges1.add(new Edge(2, 4, -2)); // Negative edge
        edges1.add(new Edge(3, 4, 9));
        edges1.add(new Edge(4, 5, 0));
        edges1.add(new Edge(5, 1, 4));

        int source1 = 0;
        System.out.println("--- Graph 1 (No negative cycle) ---");
        int[] dist1 = solver.bellmanFord(V1, edges1, source1);
        if (dist1 != null) {
            System.out.println("Shortest distances from source " + source1 + ":");
            for (int i = 0; i < V1; i++) {
                System.out.println("Node " + i + ": " + (dist1[i] == Integer.MAX_VALUE ? "Unreachable" : dist1[i]));
            }
        }
        // Expected distances from source 0:
        // Node 0: 0
        // Node 1: 6
        // Node 2: 5 (0->1->2: 6-1=5, 0->2: 7)
        // Node 3: 1 (0->1->2->3: 6-1-4=1)
        // Node 4: 3 (0->1->2->4: 6-1-2=3)
        // Node 5: 3 (0->1->2->4->5: 6-1-2+0=3)


        System.out.println("\n--- Graph 2 (With negative cycle) ---");
        // Example Graph 2: With negative cycle
        // 0 --(1)--> 1
        // ^          |
        // |          (-3)
        // |          |
        // 3 <--(2)--- 2 --(4)--> 0 (This creates a negative cycle 0-1-2-3-0 with sum 1-3+2+4=4, if 0->3 exists)
        // Let's create a cycle: 1 -> 2 -> 3 -> 1 (sum = -3 + 2 + -1 = -2)
        int V2 = 4;
        List<Edge> edges2 = new ArrayList<>();
        edges2.add(new Edge(0, 1, 1));
        edges2.add(new Edge(1, 2, -3)); // Negative edge
        edges2.add(new Edge(2, 3, 2));
        edges2.add(new Edge(3, 1, -1)); // Negative edge, creates cycle 1-2-3-1 (1 + 2 + -3 = 0, ah, make 3-1: -1 to make negative)
                                      // Cycle: 1 -> 2 -> 3 -> 1. Total weight = -3 + 2 + (-1) = -2 (negative cycle)
        edges2.add(new Edge(0, 3, 10)); // Another edge from source

        int source2 = 0;
        int[] dist2 = solver.bellmanFord(V2, edges2, source2);
        if (dist2 == null) {
            System.out.println("As expected, negative cycle detected.");
        }
    }
}
```

### Bellman-Ford Ke Use Cases:

1.  **Graphs with Negative Edge Weights:** Jab shortest path find karna ho aur graph mein negative edge weights allowed hon.
2.  **Negative Cycle Detection:** Jab explicitly check karna ho ki graph mein koi negative cycle hai ya nahi. Yeh financial arbitrage detection (jahan currency exchange rates ek loop mein negative profit dein) jaise scenarios mein useful ho sakta hai.
3.  **Distributed Algorithms:** Kuch distributed routing algorithms mein bhi Bellman-Ford ka principle use hota hai.

### Limitations:

  * **Slower than Dijkstra's:** Dijkstra's `O(E log V)` hota hai, Bellman-Ford `O(V * E)` hai, isliye bade graphs ke liye jahan negative weights nahi hain, Dijkstra's prefer kiya jaata hai.
  * **No guarantee of path if negative cycle:** Agar negative cycle hai, toh shortest path (well-defined) exist nahi karta, aur algorithm `null` return karta hai.
