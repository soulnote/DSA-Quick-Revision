## 🔹 **10. Bellman-Ford Algorithm**

### ✅ **Importance & Use**

* Finds the **shortest path from a single source** to all vertices.
* Handles **negative weight edges** (unlike Dijkstra).
* Can detect **negative weight cycles** reachable from the source.
* Used in routing protocols and financial arbitrage detection.

---

### 📘 **Example Problem**

> **Problem**: Given a graph representing currency exchange rates with some negative profit cycles, find the shortest paths from a source city and detect if any arbitrage (negative cycle) exists.

> **Why Bellman-Ford?**
> Because it handles negative edges and detects cycles that Dijkstra cannot.

---

### 🧠 **Intuition**

* Relax all edges up to **V-1 times**.
* If any edge can still be relaxed after that, a negative cycle exists.

---

### 📦 **Java Code (Bellman-Ford Algorithm)**

```java
import java.util.*;

class BellmanFord {

    static class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void bellmanFord(int V, List<Edge> edges, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges V-1 times
        for (int i = 1; i < V; i++) {
            for (Edge edge : edges) {
                if (dist[edge.src] != Integer.MAX_VALUE &&
                    dist[edge.src] + edge.weight < dist[edge.dest]) {
                    dist[edge.dest] = dist[edge.src] + edge.weight;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : edges) {
            if (dist[edge.src] != Integer.MAX_VALUE &&
                dist[edge.src] + edge.weight < dist[edge.dest]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }

        // Print shortest distances
        System.out.println("Vertex Distance from Source:");
        for (int i = 0; i < V; i++) {
            System.out.println(i + "\t\t" + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<Edge> edges = new ArrayList<>();

        // Add edges (src, dest, weight)
        edges.add(new Edge(0, 1, -1));
        edges.add(new Edge(0, 2, 4));
        edges.add(new Edge(1, 2, 3));
        edges.add(new Edge(1, 3, 2));
        edges.add(new Edge(1, 4, 2));
        edges.add(new Edge(3, 2, 5));
        edges.add(new Edge(3, 1, 1));
        edges.add(new Edge(4, 3, -3));

        bellmanFord(V, edges, 0);
    }
}
```

---

### 🧩 **Explanation**

* Relax edges repeatedly to update shortest distances.
* If after V-1 relaxations you can still update a distance, a negative cycle exists.

---

✅ **Real-World Use Cases**

* Network routing protocols (e.g., RIP)
* Currency arbitrage detection
* Any system with possible negative cost transitions

---
