## 🔹 **5. Floyd-Warshall Algorithm**

### ✅ **Importance & Use**

* Computes **shortest paths between all pairs** of nodes in a weighted graph.
* Handles **negative weights** (but **no negative weight cycles**).
* Time complexity: **O(V³)**, best for **dense graphs** or when all-pairs shortest path is required.

---

### 📘 **Example Problem**

> **Problem**: A company has multiple offices in different cities. You are given the travel time (in hours) between each pair of cities.
> You need to build a matrix showing the **shortest travel time** between every pair of cities (direct or indirect).

> **Why Floyd-Warshall?**
> Because we want **all-pairs shortest paths** (not just from one source), and some travel routes may be **indirect**.

---

### 🧠 **Intuition**

We try to improve `dist[i][j]` by seeing if going through an intermediate `k` gives a shorter path:

```
dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
```

---

### 📦 **Java Code (With Comments & INF Handling)**

```java
import java.util.*;

class FloydWarshallExample {

    final static int INF = 1000000000; // Large number used to represent "infinite distance"

    public static void floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        // Step 1: Copy the original graph weights to dist matrix
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // Step 2: Try all intermediate nodes k
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // Step 3: Print the final distance matrix
        System.out.println("All-pairs shortest travel times:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Example: Graph with 4 cities and travel times
        int[][] graph = {
            {0,   3,   INF, 5},
            {2,   0,   INF, 4},
            {INF, 1,   0,   INF},
            {INF, INF, 2,   0}
        };

        floydWarshall(graph);
    }
}
```

---

### 🧩 **Explanation**

* `graph[i][j]` holds direct travel time from city `i` to `j`.
* The algorithm tries every city as an **intermediate stop** and updates routes.

---

✅ **Real-World Use Cases**

* Logistics & delivery route optimization
* City traffic management
* Airline route planners (finding shortest connections between any pair of cities)

---
