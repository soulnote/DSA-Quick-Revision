## 🔹 **8. Union-Find (Disjoint Set Union - DSU)**

### ✅ **Importance & Use**

* Efficiently tracks and merges **disjoint sets**.
* Commonly used for:

  * **Cycle detection** in undirected graphs.
  * **Kruskal’s Minimum Spanning Tree** algorithm.
  * Network connectivity queries.
  * Grouping and clustering problems.

---

### 📘 **Example Problem**

> **Problem**: Given an undirected graph, detect if adding an edge creates a cycle.

> **Why Union-Find?**
> Because it helps check if two vertices belong to the same connected component (set). If yes, adding an edge forms a cycle.

---

### 🧠 **Intuition**

* Each element belongs to a **set** with a representative called **parent**.
* Two main operations:

  * **Find:** Determine which set an element belongs to.
  * **Union:** Merge two sets.
* With **path compression** and **union by rank/size**, operations are almost constant time.

---

### 📦 **Java Code (Union-Find with Path Compression & Union by Rank)**

```java
class UnionFind {
    int[] parent, rank;

    // Initialize parent and rank arrays
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;  // Each element is initially its own parent
            rank[i] = 0;    // Rank initialized to 0
        }
    }

    // Find the set representative (with path compression)
    public int find(int x) {
        if(parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }

    // Union two sets by rank
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if(rootX == rootY) {
            // Already in same set, union not performed
            return false;
        }

        // Attach smaller rank tree under root of higher rank tree
        if(rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if(rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}
```

---

### 🧩 **Cycle Detection in Undirected Graph using Union-Find**

```java
public class CycleDetection {

    public static boolean hasCycle(int[][] edges, int V) {
        UnionFind uf = new UnionFind(V);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // If u and v are already in same set, cycle exists
            if (!uf.union(u, v)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int V = 4;
        int[][] edges = {
            {0, 1},
            {1, 2},
            {2, 3},
            {3, 0}  // This edge creates a cycle
        };

        System.out.println("Cycle present? " + hasCycle(edges, V));
    }
}
```

---

### 🧩 **Explanation**

* Each union merges two sets if they are disjoint.
* If they are already connected, adding an edge causes a cycle.

---

✅ **Real-World Use Cases**

* Network connectivity checks
* Image processing (connected components)
* Kruskal’s MST algorithm
* Dynamic connectivity queries

---
