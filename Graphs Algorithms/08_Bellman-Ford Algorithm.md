
## 🔹 **4. Bellman-Ford Algorithm**

### ✅ **Importance & Use**

* Computes **shortest paths** from a source to all vertices in a **weighted graph**, even with **negative weights**.
* Can **detect negative weight cycles**.
* Used in:

  * Currency arbitrage (finance)
  * Routing protocols like **RIP** in networking

---

### 🧠 **Intuition**

Instead of greedily picking the minimum like Dijkstra, Bellman-Ford **relaxes all edges** `V-1` times (where `V` is the number of vertices). If we can still relax edges in the `V`th iteration, it means there’s a **negative cycle**.

---

### 📦 **Java Code (with Comments)**

```java
import java.util.*;

class BellmanFordExample {

    // Class to represent an edge with source, destination, and weight
    static class Edge {
        int src, dest, weight;
        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void bellmanFord(List<Edge> edges, int V, int source) {
        // Step 1: Initialize distances
        int[] distance = new int[V];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        // Step 2: Relax all edges V-1 times
        for (int i = 1; i < V; i++) {
            for (Edge edge : edges) {
                int u = edge.src;
                int v = edge.dest;
                int wt = edge.weight;

                // Relax the edge if possible
                if (distance[u] != Integer.MAX_VALUE && distance[u] + wt < distance[v]) {
                    distance[v] = distance[u] + wt;
                }
            }
        }

        // Step 3: Check for negative weight cycles
        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;
            int wt = edge.weight;
            if (distance[u] != Integer.MAX_VALUE && distance[u] + wt < distance[v]) {
                System.out.println("Graph contains a negative weight cycle!");
                return;
            }
        }

        // Output shortest distances
        System.out.println("Shortest distances from source " + source + ":");
        for (int i = 0; i < V; i++) {
            System.out.println("To node " + i + " = " + distance[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5; // number of vertices

        // List of edges
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 6));
        edges.add(new Edge(0, 2, 7));
        edges.add(new Edge(1, 2, 8));
        edges.add(new Edge(1, 3, 5));
        edges.add(new Edge(1, 4, -4));
        edges.add(new Edge(2, 3, -3));
        edges.add(new Edge(2, 4, 9));
        edges.add(new Edge(3, 1, -2));
        edges.add(new Edge(4, 0, 2));
        edges.add(new Edge(4, 3, 7));

        // Run Bellman-Ford from source vertex 0
        bellmanFord(edges, V, 0);
    }
}
```

---

### 🧩 **Explanation**

* Each edge is explicitly listed.
* `V-1` relaxations ensure all shortest paths are found.
* The last check detects if any edge still reduces a distance → **negative cycle** exists.

---
