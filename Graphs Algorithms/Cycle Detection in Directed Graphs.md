# Through DFS
Cycle detection in **directed graphs** is different from undirected ones because direction matters. Here's how you can do it in Java using **DFS with recursion stack** (standard approach).

---

### ✅ Cycle Detection in Directed Graph using **DFS (Recursion Stack)**

```java
import java.util.*;

public class DirectedCycleDetection {
    private int V;
    private List<List<Integer>> adj;

    public DirectedCycleDetection(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v); // Directed graph
    }

    public boolean hasCycle() {
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V]; // Tracks nodes in the current DFS path

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (dfs(i, visited, recStack)) return true;
            }
        }
        return false;
    }

    private boolean dfs(int node, boolean[] visited, boolean[] recStack) {
        visited[node] = true;
        recStack[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, visited, recStack)) return true;
            } else if (recStack[neighbor]) {
                return true; // Back edge detected: cycle exists
            }
        }

        recStack[node] = false; // Backtrack
        return false;
    }

    public static void main(String[] args) {
        DirectedCycleDetection graph = new DirectedCycleDetection(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Cycle from 3 → 1 → 2 → 3

        System.out.println("Cycle Detected (Directed DFS): " + graph.hasCycle());
    }
}
```

---
# Through BFS
Here’s the **BFS-based cycle detection in a directed graph using Kahn’s Algorithm**, which is based on **topological sorting**. If a topological sort is not possible (i.e., not all nodes can be visited), then the graph **has a cycle**.

---

### ✅ Java Code: Cycle Detection using **Kahn's Algorithm (BFS)**

```java
import java.util.*;

public class CycleDetectionKahns {
    private int V;
    private List<List<Integer>> adj;

    public CycleDetectionKahns(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
    }

    // Add a directed edge from u to v
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }

    // Returns true if cycle exists using Kahn's Algorithm (BFS Topological Sort)
    public boolean hasCycle() {
        int[] inDegree = new int[V];

        // 1. Calculate in-degrees of all vertices
        for (int u = 0; u < V; u++) {
            for (int v : adj.get(u)) {
                inDegree[v]++;
            }
        }

        // 2. Add all vertices with in-degree 0 to the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0)
                queue.offer(i);
        }

        // 3. Process vertices in queue using BFS
        int count = 0; // To count visited nodes
        while (!queue.isEmpty()) {
            int u = queue.poll();
            count++; // Increment visited node count

            for (int v : adj.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0)
                    queue.offer(v);
            }
        }

        // 4. If count != V, there's a cycle
        return count != V;
    }

    public static void main(String[] args) {
        CycleDetectionKahns graph = new CycleDetectionKahns(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1); // Cycle: 1 → 2 → 3 → 1

        if (graph.hasCycle()) {
            System.out.println("Cycle Detected (Kahn's Algorithm)");
        } else {
            System.out.println("No Cycle (Graph is a DAG)");
        }
    }
}
```

---

### 🔍 **How Kahn’s Algorithm Detects Cycle**

* A **Directed Acyclic Graph (DAG)** has a valid **topological order** — every node gets processed exactly once.
* If there's a **cycle**, some nodes will always have **non-zero in-degree** and **never enter the queue**.
* So if the number of processed nodes (`count`) < total nodes `V`, it indicates a **cycle**.

