## 🔹 **11. Kosaraju’s Algorithm**

### ✅ **Importance & Use**

* Finds all **Strongly Connected Components** in a directed graph.
* SCC = maximal set of vertices where each vertex is reachable from every other vertex in the set.
* Used in:

  * Analyzing components in social networks, web pages
  * Deadlock detection in operating systems
  * Optimizing compilers (detect cycles)

---

### 📘 **Example Problem**

> **Problem**: Find all groups of pages on the internet that are mutually reachable via hyperlinks.

> **Why Kosaraju’s?**
> Because it efficiently finds all SCCs using two DFS traversals.

---

### 🧠 **Intuition**

* Run DFS and track finish times.
* Reverse all edges.
* Run DFS on reversed graph in order of decreasing finish time.
* Each DFS tree in the reversed graph is an SCC.

---

### 📦 **Java Code (Kosaraju’s Algorithm)**

```java
import java.util.*;

class KosarajuSCC {
    private int V;
    private List<List<Integer>> graph;
    private List<List<Integer>> reverseGraph;

    KosarajuSCC(int V) {
        this.V = V;
        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v) {
        graph.get(u).add(v);
        reverseGraph.get(v).add(u);  // Reverse edge
    }

    void fillOrder(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int neigh : graph.get(v)) {
            if (!visited[neigh]) {
                fillOrder(neigh, visited, stack);
            }
        }
        stack.push(v);  // Add to stack on return
    }

    void dfs(int v, boolean[] visited, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int neigh : reverseGraph.get(v)) {
            if (!visited[neigh]) {
                dfs(neigh, visited, component);
            }
        }
    }

    void printSCCs() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        // Step 1: Fill stack with vertices by finish time
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                fillOrder(i, visited, stack);
            }
        }

        // Step 2: Clear visited for second DFS
        Arrays.fill(visited, false);

        // Step 3: Process all vertices in order defined by stack
        System.out.println("Strongly Connected Components:");
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfs(v, visited, component);
                System.out.println(component);
            }
        }
    }

    public static void main(String[] args) {
        KosarajuSCC g = new KosarajuSCC(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        g.printSCCs();
    }
}
```

---

### 🧩 **Explanation**

* First DFS orders vertices by finishing time.
* Reverse graph is used to explore SCCs via DFS in that order.
* Each DFS on reversed graph returns one SCC.

---

✅ **Real-World Use Cases**

* Web crawling and link analysis
* Deadlock detection
* Program analysis and optimization

---
