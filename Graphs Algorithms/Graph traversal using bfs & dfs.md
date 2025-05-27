# ✅ 1. **BFS in Undirected Graph**

```java
import java.util.*;

public class BFSUndirected {
    private int[][] adjMatrix;
    private int n;

    // Constructor initializes adjacency matrix for n nodes
    public BFSUndirected(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Adds an undirected edge between u and v
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1; // Both directions for undirected graph
    }

    // Breadth-First Search starting from 'start' node
    public void bfs(int start) {
        boolean[] visited = new boolean[n]; // Track visited nodes
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.print("BFS (Undirected): ");
        while (!queue.isEmpty()) {
            int node = queue.poll(); // Dequeue current node
            System.out.print(node + " ");

            // Explore all neighbors
            for (int i = 0; i < n; i++) {
                if (adjMatrix[node][i] == 1 && !visited[i]) {
                    visited[i] = true;   // Mark as visited
                    queue.add(i);        // Enqueue neighbor
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BFSUndirected graph = new BFSUndirected(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.bfs(0); // Start BFS from node 0
    }
}
```

---

# ✅ 2. **DFS in Undirected Graph**

```java
public class DFSUndirected {
    private int[][] adjMatrix;
    private int n;

    // Initialize adjacency matrix for n nodes
    public DFSUndirected(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Add undirected edge between u and v
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;
    }

    // Depth-First Search starting from 'start' node
    public void dfs(int start) {
        boolean[] visited = new boolean[n]; // Track visited nodes
        System.out.print("DFS (Undirected): ");
        dfsHelper(start, visited);
        System.out.println();
    }

    // Recursive DFS traversal
    private void dfsHelper(int node, boolean[] visited) {
        visited[node] = true; // Mark node as visited
        System.out.print(node + " ");

        // Visit all unvisited neighbors
        for (int i = 0; i < n; i++) {
            if (adjMatrix[node][i] == 1 && !visited[i]) {
                dfsHelper(i, visited);
            }
        }
    }

    public static void main(String[] args) {
        DFSUndirected graph = new DFSUndirected(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.dfs(0); // Start DFS from node 0
    }
}
```

---

# ✅ 3. **BFS in Directed Graph**

```java
import java.util.*;

public class BFSDirected {
    private int[][] adjMatrix;
    private int n;

    // Initialize directed graph with n nodes
    public BFSDirected(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Add a directed edge from 'from' to 'to'
    public void addEdge(int from, int to) {
        adjMatrix[from][to] = 1; // Only one direction
    }

    // BFS starting from 'start' node
    public void bfs(int start) {
        boolean[] visited = new boolean[n]; // Track visited nodes
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.print("BFS (Directed): ");
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            // Visit all unvisited neighbors
            for (int i = 0; i < n; i++) {
                if (adjMatrix[node][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BFSDirected graph = new BFSDirected(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.bfs(0); // Start BFS from node 0
    }
}
```

---

# ✅ 4. **DFS in Directed Graph**

```java
public class DFSDirected {
    private int[][] adjMatrix;
    private int n;

    // Constructor for directed graph with n nodes
    public DFSDirected(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Add a directed edge from 'from' to 'to'
    public void addEdge(int from, int to) {
        adjMatrix[from][to] = 1;
    }

    // DFS traversal starting from 'start' node
    public void dfs(int start) {
        boolean[] visited = new boolean[n];
        System.out.print("DFS (Directed): ");
        dfsHelper(start, visited);
        System.out.println();
    }

    // Recursive helper for DFS
    private void dfsHelper(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        // Explore all unvisited neighbors
        for (int i = 0; i < n; i++) {
            if (adjMatrix[node][i] == 1 && !visited[i]) {
                dfsHelper(i, visited);
            }
        }
    }

    public static void main(String[] args) {
        DFSDirected graph = new DFSDirected(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.dfs(0); // Start DFS from node 0
    }
}
```

---

