Here's Java code to detect a **cycle in an undirected graph** using both **DFS** and **BFS** approaches.

---

### ✅ 1. Cycle Detection using **DFS** (Undirected Graph):

```java
import java.util.*;

public class CycleDetectionDFS {
    private int V;
    private List<List<Integer>> adj;

    public CycleDetectionDFS(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // Undirected graph
    }

    public boolean hasCycle() {
        boolean[] visited = new boolean[V];
        for (int u = 0; u < V; u++) {
            if (!visited[u]) {
                if (dfs(u, -1, visited)) return true;
            }
        }
        return false;
    }

    private boolean dfs(int node, int parent, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, node, visited)) return true;
            } else if (neighbor != parent) {
                return true; // Found a back edge (cycle)
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CycleDetectionDFS graph = new CycleDetectionDFS(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0); // Cycle

        System.out.println("Cycle Detected (DFS): " + graph.hasCycle());
    }
}
```

---

### ✅ 2. Cycle Detection using **BFS** (Undirected Graph):

```java
import java.util.*;

public class CycleDetectionBFS {
    private int V;
    private List<List<Integer>> adj;

    public CycleDetectionBFS(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // Undirected graph
    }

    public boolean hasCycle() {
        boolean[] visited = new boolean[V];

        for (int u = 0; u < V; u++) {
            if (!visited[u]) {
                if (bfs(u, visited)) return true;
            }
        }
        return false;
    }

    private boolean bfs(int start, boolean[] visited) {
        Queue<int[]> queue = new LinkedList<>(); // [node, parent]
        queue.offer(new int[]{start, -1});
        visited[start] = true;

        while (!queue.isEmpty()) {
            int[] pair = queue.poll();
            int node = pair[0], parent = pair[1];

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(new int[]{neighbor, node});
                } else if (neighbor != parent) {
                    return true; // Found a cycle
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CycleDetectionBFS graph = new CycleDetectionBFS(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0); // Cycle

        System.out.println("Cycle Detected (BFS): " + graph.hasCycle());
    }
}
```

---
