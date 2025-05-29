## 🔹 **12. Tarjan’s Algorithm for Strongly Connected Components**

### ✅ **Importance & Use**

* Finds all SCCs in a directed graph using **one DFS traversal**.
* More efficient in practice than Kosaraju’s since it doesn’t require graph reversal or stack filling separately.
* Used in the same scenarios as Kosaraju’s:

  * Deadlock detection
  * Program optimization
  * Network analysis

---

### 📘 **Example Problem**

> **Problem**: Detect circular dependencies in package/module import graphs.

> **Why Tarjan’s?**
> Because it finds strongly connected components in a single DFS, making it efficient for large graphs.

---

### 🧠 **Intuition**

* Track discovery time and the lowest reachable vertex (low-link value).
* When a node’s low-link equals its discovery time, it’s the root of an SCC.
* Use a stack to keep track of the current path.

---

### 📦 **Java Code (Tarjan’s Algorithm)**

```java
import java.util.*;

class TarjanSCC {
    private int V;
    private List<List<Integer>> graph;
    private int time;
    private int[] disc, low;
    private boolean[] inStack;
    private Deque<Integer> stack;

    TarjanSCC(int V) {
        this.V = V;
        graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }
        time = 0;
        disc = new int[V];
        low = new int[V];
        inStack = new boolean[V];
        stack = new ArrayDeque<>();
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
    }

    void addEdge(int u, int v) {
        graph.get(u).add(v);
    }

    void tarjanDFS(int u) {
        disc[u] = low[u] = time++;
        stack.push(u);
        inStack[u] = true;

        for (int v : graph.get(u)) {
            if (disc[v] == -1) {
                tarjanDFS(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        // If u is root of SCC
        if (low[u] == disc[u]) {
            System.out.print("SCC: ");
            int w;
            do {
                w = stack.pop();
                inStack[w] = false;
                System.out.print(w + " ");
            } while (w != u);
            System.out.println();
        }
    }

    void findSCCs() {
        for (int i = 0; i < V; i++) {
            if (disc[i] == -1) {
                tarjanDFS(i);
            }
        }
    }

    public static void main(String[] args) {
        TarjanSCC graph = new TarjanSCC(5);
        graph.addEdge(1, 0);
        graph.addEdge(0, 2);
        graph.addEdge(2, 1);
        graph.addEdge(0, 3);
        graph.addEdge(3, 4);

        graph.findSCCs();
    }
}
```

---

### 🧩 **Explanation**

* DFS visits each vertex.
* Maintains `disc[]` and `low[]` arrays.
* Detects SCC when a node's low-link equals discovery time.
* Pops nodes in SCC from stack.

---

✅ **Real-World Use Cases**

* Cycle detection in dependency graphs
* Identifying modules/components strongly connected
* Network and social network analysis

---
