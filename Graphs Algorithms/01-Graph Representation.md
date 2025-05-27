# Undirected Graph
## 1. **Adjacency Matrix Representation**

* Uses a 2D array.
* Good for dense graphs or fixed-size graphs.
* Easy to check if an edge exists between any two nodes.

```java
public class GraphAdjacencyMatrix {
    private int[][] adjMatrix;
    private int n; // Number of vertices

    // Initialize the matrix for n nodes
    public GraphAdjacencyMatrix(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Add undirected edge between u and v
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1; // Omit this line for directed graph
    }

    // Check if an edge exists between u and v
    public boolean hasEdge(int u, int v) {
        return adjMatrix[u][v] == 1;
    }

    // Print the adjacency matrix
    public void printGraph() {
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.printGraph();
        System.out.println("Edge between 0 and 2? " + graph.hasEdge(0, 2));
    }
}
```

---

## 2. **Adjacency List Representation**

* Uses an array (or list) of lists.
* Efficient for sparse graphs.
* Easy to iterate neighbors of a node.

```java
import java.util.*;

public class GraphAdjacencyList {
    private List<Integer>[] adjList;
    private int n;

    // Initialize adjacency list for n nodes
    public GraphAdjacencyList(int n) {
        this.n = n;
        adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    // Add undirected edge between u and v
    public void addEdge(int u, int v) {
        adjList[u].add(v);
        adjList[v].add(u); // Remove for directed graph
    }

    // Print the adjacency list
    public void printGraph() {
        System.out.println("Adjacency List:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": ");
            for (int neighbor : adjList[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        GraphAdjacencyList graph = new GraphAdjacencyList(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        graph.printGraph();
    }
}
```

---
# Directed Graph

## 1. Directed Graph — **Adjacency Matrix (Unweighted)**

```java
public class DirectedGraphAdjacencyMatrix {
    private int[][] adjMatrix;
    private int n;

    // Initialize adjacency matrix for directed graph with n nodes
    public DirectedGraphAdjacencyMatrix(int n) {
        this.n = n;
        adjMatrix = new int[n][n];
    }

    // Add a directed edge from 'from' to 'to'
    public void addEdge(int from, int to) {
        adjMatrix[from][to] = 1;  // Only one direction
    }

    // Check if edge exists from 'from' to 'to'
    public boolean hasEdge(int from, int to) {
        return adjMatrix[from][to] == 1;
    }

    // Print adjacency matrix
    public void printGraph() {
        System.out.println("Directed Graph Adjacency Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        DirectedGraphAdjacencyMatrix graph = new DirectedGraphAdjacencyMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 0);

        graph.printGraph();
        System.out.println("Edge from 0 to 2? " + graph.hasEdge(0, 2));
        System.out.println("Edge from 2 to 0? " + graph.hasEdge(2, 0));
    }
}
```

---

## 2. Directed Graph — **Adjacency List (Unweighted)**

```java
import java.util.*;

public class DirectedGraphAdjacencyList {
    private List<Integer>[] adjList;
    private int n;

    // Initialize adjacency list for directed graph
    public DirectedGraphAdjacencyList(int n) {
        this.n = n;
        adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    // Add directed edge from 'from' to 'to'
    public void addEdge(int from, int to) {
        adjList[from].add(to);  // Only one direction
    }

    // Print adjacency list
    public void printGraph() {
        System.out.println("Directed Graph Adjacency List:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": ");
            for (int neighbor : adjList[i]) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        DirectedGraphAdjacencyList graph = new DirectedGraphAdjacencyList(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 0);

        graph.printGraph();
    }
}
```

---

## 3. Directed Graph — **Adjacency Matrix (Weighted)**

```java
public class DirectedWeightedGraphAdjMatrix {
    private int[][] adjMatrix;
    private int n;
    private final int NO_EDGE = 0; // or use Integer.MAX_VALUE for no edge

    // Initialize adjacency matrix for weighted directed graph
    public DirectedWeightedGraphAdjMatrix(int n) {
        this.n = n;
        adjMatrix = new int[n][n];

        // Initialize matrix with NO_EDGE meaning no edge present
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMatrix[i][j] = NO_EDGE;
            }
        }
    }

    // Add weighted directed edge
    public void addEdge(int from, int to, int weight) {
        adjMatrix[from][to] = weight; // Weight of the edge
    }

    // Print adjacency matrix with weights
    public void printGraph() {
        System.out.println("Directed Weighted Graph Adjacency Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjMatrix[i][j] == NO_EDGE)
                    System.out.print("∞ "); // No edge
                else
                    System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        DirectedWeightedGraphAdjMatrix graph = new DirectedWeightedGraphAdjMatrix(4);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(3, 0, 4);

        graph.printGraph();
    }
}
```

---

## 4. Directed Graph — **Adjacency List (Weighted)**

```java
import java.util.*;

public class DirectedWeightedGraphAdjList {
    private List<Edge>[] adjList;
    private int n;

    // Edge class stores destination and weight
    private static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // Initialize adjacency list for weighted directed graph
    public DirectedWeightedGraphAdjList(int n) {
        this.n = n;
        adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    // Add weighted directed edge
    public void addEdge(int from, int to, int weight) {
        adjList[from].add(new Edge(to, weight));
    }

    // Print adjacency list with weights
    public void printGraph() {
        System.out.println("Directed Weighted Graph Adjacency List:");
        for (int i = 0; i < n; i++) {
            System.out.print(i + ": ");
            for (Edge e : adjList[i]) {
                System.out.print("(" + e.to + ", weight=" + e.weight + ") ");
            }
            System.out.println();
        }
    }

    // Sample usage
    public static void main(String[] args) {
        DirectedWeightedGraphAdjList graph = new DirectedWeightedGraphAdjList(4);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(3, 0, 4);

        graph.printGraph();
    }
}
```

---

