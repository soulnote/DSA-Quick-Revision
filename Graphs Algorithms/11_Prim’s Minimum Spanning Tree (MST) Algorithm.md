## 🔹 **9. Prim’s Minimum Spanning Tree (MST) Algorithm**

### ✅ **Importance & Use**

* Finds a subset of edges connecting all vertices with the **minimum total edge weight** without cycles.
* Commonly used in:

  * Network design (cables, road networks)
  * Clustering algorithms
  * Approximation algorithms (e.g., for traveling salesman)
* Particularly efficient for **dense graphs** when implemented with adjacency matrices.

---

### 📘 **Example Problem**

> **Problem**: Given a weighted undirected graph representing cities connected by roads, find the minimal total cost to connect all cities.

> **Why Prim’s?**
> Because it builds the MST by **growing one vertex at a time**, always adding the cheapest edge connecting the MST to a new vertex.

---

### 🧠 **Intuition**

* Start from any node.
* Maintain a priority queue of edges crossing the MST frontier.
* At each step, pick the smallest edge that connects a vertex **outside** the MST to the MST.
* Repeat until all vertices included.

---

### 📦 **Java Code (Prim’s Algorithm with PriorityQueue)**

```java
import java.util.*;

class PrimsMST {

    static class Edge implements Comparable<Edge> {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    public static int primMST(List<List<Edge>> graph, int V) {
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int mstWeight = 0;

        // Start from vertex 0 (arbitrary)
        pq.add(new Edge(0, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            if (visited[current.to]) continue;

            visited[current.to] = true;
            mstWeight += current.weight;

            // Add all edges from current.to to pq if the vertex not visited
            for (Edge edge : graph.get(current.to)) {
                if (!visited[edge.to]) {
                    pq.add(edge);
                }
            }
        }

        return mstWeight;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges (undirected)
        graph.get(0).add(new Edge(1, 2));
        graph.get(1).add(new Edge(0, 2));

        graph.get(0).add(new Edge(3, 6));
        graph.get(3).add(new Edge(0, 6));

        graph.get(1).add(new Edge(2, 3));
        graph.get(2).add(new Edge(1, 3));

        graph.get(1).add(new Edge(3, 8));
        graph.get(3).add(new Edge(1, 8));

        graph.get(1).add(new Edge(4, 5));
        graph.get(4).add(new Edge(1, 5));

        graph.get(2).add(new Edge(4, 7));
        graph.get(4).add(new Edge(2, 7));

        int result = primMST(graph, V);
        System.out.println("Total weight of MST is: " + result);
    }
}
```

---

### 🧩 **Explanation**

* Start with a node, push edges to priority queue.
* Pick the smallest edge leading to an unvisited node.
* Keep adding edges until all nodes are visited.
* Returns total MST weight.

---

✅ **Real-World Use Cases**

* Designing electrical grids
* Road or fiber optic cable layouts
* Network design optimization

---
