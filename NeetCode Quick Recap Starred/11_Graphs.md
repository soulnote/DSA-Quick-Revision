# ⭐ Clone Graph

Given a reference of a node in a connected undirected graph, return a **deep copy (clone)** of the graph.

Each node contains a value (`int val`) and a list of its neighbors (`List<Node>`).

### 📌 Example

Input:

```
adjList = [[2,4],[1,3],[2,4],[1,3]]

```

Output:

```
[[2,4],[1,3],[2,4],[1,3]]
```

Explanation:

  * There are 4 nodes in the graph.
  * Node 1’s neighbors are nodes 2 and 4.
  * Node 2’s neighbors are nodes 1 and 3.
  * Node 3’s neighbors are nodes 2 and 4.
  * Node 4’s neighbors are nodes 1 and 3.

-----

### Intuition (Samajh):

Graph cloning ka matlab hai ek naya graph banana jo original graph ki exact copy ho, jismein saare nodes aur edges replicate ho. Yeh shallow copy se alag hai, jahan sirf references copy hote hain. Deep copy mein, naye node objects aur unke naye neighbor lists bante hain.

Ek graph ko clone karne ke liye, humein har node ko visit karna hoga, uska clone banana hoga, aur phir uske neighbors ke clones ko uske clone ke neighbors list mein add karna hoga. Is process mein cycles (jaise `A -> B -> A`) ko handle karna zaroori hai, taaki hum infinite loop mein na phanse aur ek hi node ke multiple clones na banayein.

Is problem ko solve karne ke liye, hum **Graph Traversal (BFS ya DFS)** ka use karenge aur ek `Map` maintain karenge jo original nodes ko unke corresponding cloned nodes se map karega.

-----

### Approach (Tareeka): BFS with a HashMap

1.  **Handle Null Input**: Agar input `node` `null` hai (empty graph), toh `null` return karo.
2.  **`map` Initialization**: Ek `HashMap<Node, Node> map` banao. Yeh map original nodes ko unke newly created cloned nodes se store karega. Iska use duplicate cloning se bachne aur already cloned nodes ko reference karne ke liye hoga.
3.  **Clone Start Node**:
      * Original `node` ka ek clone banao: `Node clone = new Node(node.val);`.
      * Is mapping ko `map` mein store karo: `map.put(node, clone);`.
4.  **BFS Queue**: Ek `Queue<Node> queue` banao (BFS ke liye). Original `node` ko queue mein add karo.
5.  **BFS Traversal**: `while (!queue.isEmpty())` loop chalao.
      * `Node current = queue.poll();`: Queue se ek `current` original node nikalo.
      * **Iterate Neighbors**: `for (Node neighbor : current.neighbors)`: `current` node ke har `neighbor` par iterate karo.
          * **Check if Neighbor Cloned**: `if (!map.containsKey(neighbor))`: Agar `neighbor` ka clone abhi tak nahi bana hai:
              * `Node neighborClone = new Node(neighbor.val);`: `neighbor` ka naya clone banao.
              * `map.put(neighbor, neighborClone);`: Is mapping ko `map` mein store karo.
              * `queue.add(neighbor);`: `neighbor` ko queue mein add karo taaki uske neighbors ko bhi process kiya ja sake.
          * **Link Clones**: `map.get(current).neighbors.add(map.get(neighbor));`: `current` node ke clone (`map.get(current)`) ke neighbors list mein `neighbor` node ke clone (`map.get(neighbor)`) ko add karo. Yeh original graph mein `current` aur `neighbor` ke beech ke edge ko clone graph mein `current` ke clone aur `neighbor` ke clone ke beech replicate karta hai.
6.  **Return Cloned Start Node**: Loop khatam hone par, `clone` (jo starting node ka clone tha) return karo. Is `clone` se poora deep copied graph reachable hoga.

-----

### Java Code with Comments:

```java
import java.util.*;

// Definition for a Node (provided by LeetCode)
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

class Solution {
    public Node cloneGraph(Node node) {
        // Step 1: Agar graph empty hai (null node), toh null return karo.
        if (node == null) {
            return null; 
        }

        // Step 2: Ek HashMap banao jo original nodes ko unke corresponding cloned nodes se map karega.
        // Yeh duplicate cloning se bachne aur cycles ko sahi se handle karne ke liye zaroori hai.
        Map<Node, Node> map = new HashMap<>(); 

        // Step 3: Start node ka clone banao aur use map mein store karo.
        Node clone = new Node(node.val); 
        map.put(node, clone); // Original node -> Cloned node

        // Step 4: BFS traversal ke liye ek Queue banao aur original start node ko add karo.
        Queue<Node> queue = new LinkedList<>(); 
        queue.add(node);

        // Step 5: BFS loop. Jab tak queue empty nahi ho jati.
        while (!queue.isEmpty()) {
            Node current = queue.poll(); // Queue se current original node nikalo.

            // Step 5.1: current node ke har neighbor par iterate karo.
            for (Node neighbor : current.neighbors) {
                // Step 5.1.1: Check karo ki is neighbor ka clone abhi tak bana hai ya nahi.
                if (!map.containsKey(neighbor)) {
                    // Agar nahi bana hai, toh naya clone banao.
                    Node neighborClone = new Node(neighbor.val);
                    map.put(neighbor, neighborClone); // Mapping store karo.
                    queue.add(neighbor); // Neighbor ko queue mein add karo taaki uske neighbors bhi process ho sakein.
                }

                // Step 5.1.2: current node ke clone ko neighbor node ke clone se link karo.
                // map.get(current) -> current original node ka clone.
                // map.get(neighbor) -> neighbor original node ka clone.
                map.get(current).neighbors.add(map.get(neighbor));
            }
        }

        // Step 6: Cloned graph ka starting node return karo.
        return clone; 
    }
}
```

-----

# ⭐ Pacific Atlantic Water Flow

Ek `m x n` rectangular island hai jo Pacific Ocean aur Atlantic Ocean dono se border karta hai.

  * Pacific Ocean island ke **left aur top edges** ko touch karta hai.
  * Atlantic Ocean island ke **right aur bottom edges** ko touch karta hai.

Aapko ek `m x n` integer matrix `heights` di gayi hai jahan `heights[r][c]` coordinate `(r, c)` par cell ki sea level se height represent karti hai.

Baarish ka paani ek cell se uske neighboring cells (north, south, east, aur west) mein flow kar sakta hai agar neighbor ki height current cell ki height se **kam ya barabar** ho. Paani kisi bhi ocean ke adjacent cell se us ocean mein flow kar sakta hai.

Ek 2D list return karein un grid coordinates ki jahan paani **dono** Pacific aur Atlantic oceans mein flow kar sakta hai.

### 📌 Example

Input:

```
heights = [
  [1,2,2,3,5],
  [3,2,3,4,4],
  [2,4,5,3,1],
  [6,7,1,4,5],
  [5,1,1,2,4]
]
```

Output:

```
[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
```

Explanation:

Cells jaise `[0,4]`, `[1,3]`, `[1,4]` etc. height rules ko follow karte hue **dono** oceans mein paani flow kar sakte hain.

-----

### Intuition (Samajh):

Yeh problem thoda tricky lag sakta hai kyunki paani **high se low** flow karta hai. Lekin humein un cells ko dhundhna hai jahan se paani **dono** oceans tak pahunch sakta hai.

Is problem ko ulta sochna zyada aasan hai:

  * Hum yeh pata lagayenge ki kaun se cells **Pacific Ocean tak pahunch sakte hain** (yani, Pacific se un tak paani "chadh" sakta hai).
  * Aur kaun se cells **Atlantic Ocean tak pahunch sakte hain** (yani, Atlantic se un tak paani "chadh" sakta hai).

Agar koi cell dono conditions ko satisfy karta hai, toh wahan se paani dono oceans tak flow kar sakta hai.
Paani "chadh"ne ka matlab hai: hum ocean se start karenge aur un cells tak pahuchenge jinki height current cell se **zyada ya barabar** ho.

-----

### Approach (Tareeka): Two BFS Traversals

1.  **State Arrays**: Do `m x n` boolean arrays (ya `int[][]` arrays, jahan `0` unvisited, `1` visited) banao:
      * `pacificReachable[r][c]`: `true` agar cell `(r, c)` se paani Pacific Ocean tak flow kar sakta hai.
      * `atlanticReachable[r][c]`: `true` agar cell `(r, c)` se paani Atlantic Ocean tak flow kar sakta hai.
2.  **BFS for Pacific**:
      * Ek `Queue<int[]>` banao aur usmein saare cells add karo jo directly Pacific Ocean se touch karte hain (yani, `row = 0` ya `col = 0` wale cells).
      * In cells ko `pacificReachable` array mein `true` mark karo.
      * BFS chalao: Queue se ek cell `(r, c)` nikalo. Uske neighbors `(nr, nc)` ko explore karo.
          * Agar `(nr, nc)` valid boundary mein hai, abhi tak `pacificReachable` nahi hai, **aur `heights[nr][nc] >= heights[r][c]` hai** (kyunki hum paani ko "chadhate" hue ja rahe hain), toh `pacificReachable[nr][nc]` ko `true` mark karo aur `(nr, nc)` ko queue mein add karo.
3.  **BFS for Atlantic**:
      * Same process Atlantic Ocean ke liye repeat karo.
      * Queue mein saare cells add karo jo directly Atlantic Ocean se touch karte hain (yani, `row = m - 1` ya `col = n - 1` wale cells).
      * In cells ko `atlanticReachable` array mein `true` mark karo.
      * BFS chalao, `heights[nr][nc] >= heights[r][c]` condition ke saath.
4.  **Collect Results**:
      * Ek `List<List<Integer>> ans` banao.
      * Poori `heights` matrix ko iterate karo.
      * Agar kisi cell `(r, c)` ke liye `pacificReachable[r][c]` aur `atlanticReachable[r][c]` dono `true` hain, toh us `[r, c]` coordinate ko `ans` list mein add karo.
5.  **Return `ans`**.

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    // Directions for moving to neighbors: [row_change, col_change]
    // dR: down, up, right, left
    // dC: down, up, right, left
    int[] dR = new int[]{1, -1, 0, 0}; 
    int[] dC = new int[]{0, 0, 1, -1};

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        // pacific: boolean array to mark cells reachable from Pacific Ocean.
        // 0: not reachable, 1: reachable
        int[][] pacific = new int[m][n]; 
        
        // atlantic: boolean array to mark cells reachable from Atlantic Ocean.
        // 0: not reachable, 2: reachable (using 2 to differentiate from pacific's 1 for final check)
        int[][] atlantic = new int[m][n]; 
        
        // Queue for BFS traversal
        Queue<int[]> q = new LinkedList<>();

        // --- BFS for Pacific Ocean ---
        // Pacific Ocean touches the top edge (row 0) and left edge (col 0).
        // Add all cells on these borders to the queue and mark them as reachable from Pacific.
        for (int i = 0; i < m; i++) { // Left edge (col 0)
            pacific[i][0] = 1;
            q.add(new int[]{i, 0});
        }
        for (int j = 0; j < n; j++) { // Top edge (row 0)
            // Avoid adding [0,0] twice if it's already added by the previous loop
            if (pacific[0][j] == 0) { // Only add if not already marked
                pacific[0][j] = 1;
                q.add(new int[]{0, j});
            }
        }
        // Perform BFS starting from Pacific border cells
        bfs(q, heights, pacific); // 'P' character removed as it's not strictly needed with separate arrays

        // Clear the queue for the next BFS traversal
        q.clear();

        // --- BFS for Atlantic Ocean ---
        // Atlantic Ocean touches the bottom edge (row m-1) and right edge (col n-1).
        // Add all cells on these borders to the queue and mark them as reachable from Atlantic.
        for (int j = 0; j < n; j++) { // Bottom edge (row m-1)
            atlantic[m - 1][j] = 2;
            q.add(new int[]{m - 1, j});
        }
        for (int i = 0; i < m; i++) { // Right edge (col n-1)
            // Avoid adding [m-1, n-1] twice
            if (atlantic[i][n - 1] == 0) { // Only add if not already marked
                atlantic[i][n - 1] = 2;
                q.add(new int[]{i, n - 1});
            }
        }
        // Perform BFS starting from Atlantic border cells
        bfs(q, heights, atlantic); // 'A' character removed

        // --- Collect Results ---
        // Iterate through all cells and find those reachable from both Pacific and Atlantic.
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // If a cell is reachable from both oceans, add its coordinates to the result list.
                if (pacific[i][j] == 1 && atlantic[i][j] == 2) {
                    ans.add(Arrays.asList(i, j));
                }
            }
        }
        return ans;
    }

    /**
     * BFS function to traverse the grid and mark reachable cells for a given ocean.
     * We are flowing "upwards" from the ocean borders.
     *
     * @param q The queue containing starting cells (border cells of an ocean).
     * @param grid The heights matrix.
     * @param reachableMatrix The matrix (pacific or atlantic) to mark reachable cells.
     */
    public void bfs(Queue<int[]> q, int[][] grid, int[][] reachableMatrix) {
        int m = grid.length;
        int n = grid[0].length;
        int oceanMark = (reachableMatrix == pacific) ? 1 : 2; // Determine the mark based on which matrix is passed

        while (!q.isEmpty()) {
            int[] present = q.poll(); // Current cell [row, col]
            int row = present[0];
            int col = present[1];

            // Explore all 4 neighbors (up, down, left, right)
            for (int k = 0; k < 4; k++) {
                int nRow = row + dR[k]; // Neighbor row
                int nCol = col + dC[k]; // Neighbor column

                // Check boundary conditions and if neighbor is already visited for this ocean
                if (nRow >= 0 && nRow < m && nCol >= 0 && nCol < n && 
                    reachableMatrix[nRow][nCol] == 0) { // Check if not visited for this ocean
                    
                    // Crucial condition: Water can flow from neighbor (nRow, nCol) to current (row, col)
                    // if neighbor's height is GREATER THAN OR EQUAL TO current cell's height.
                    // This is because we are doing a reverse BFS (from ocean to island).
                    if (grid[nRow][nCol] >= grid[row][col]) { 
                        reachableMatrix[nRow][nCol] = oceanMark; // Mark neighbor as reachable
                        q.offer(new int[]{nRow, nCol}); // Add neighbor to queue for further exploration
                    }
                }
            }
        }
    }
}
```

-----

# ⭐ Course Schedule

Aapke paas `numCourses` courses hain jinka label `0` se `numCourses - 1` tak hai.
Aapko ek array `prerequisites` diya gaya hai jahan `prerequisites[i] = [a_i, b_i]` ka matlab hai ki aapko course `a_i` lene se pehle course `b_i` lena zaroori hai.

Return `true` agar aap saare courses finish kar sakte hain, otherwise return `false`.

### 📌 Example

Input:

```
numCourses = 2, prerequisites = [[1, 0]]
```

Output:

```
true
```

Explanation:

  * Course 1 lene ke liye, aapko pehle course 0 finish karna hoga.
  * Kyunki course 0 ki koi prerequisites nahi hain, saare courses finish karna possible hai.

-----

### Intuition (Samajh):

Yeh problem ek **graph cycle detection** problem hai. Courses ko nodes aur prerequisites ko directed edges (e.g., `b_i` se `a_i` tak ek edge, kyunki `b_i` pehle lena hai `a_i` se) ki tarah model kiya ja sakta hai.

Agar courses ke beech koi **cycle** hai (jaise `A -> B -> C -> A`), toh aap saare courses kabhi finish nahi kar sakte, kyunki aap hamesha ek infinite loop mein phanse rahenge prerequisites ko poora karne ki koshish mein. Agar koi cycle nahi hai, toh courses ko complete karna possible hai.

Is problem ko solve karne ka sabse common tareeka **Topological Sort** hai. Topological sort ek directed acyclic graph (DAG) ke nodes ko linear order mein arrange karta hai, jahan har edge `u -> v` ke liye, `u` order mein `v` se pehle aata hai. Agar graph mein cycle hai, toh topological sort possible nahi hai.

-----

### Approach (Tareeka): Kahn's Algorithm (BFS-based Topological Sort)

Kahn's algorithm indegrees (incoming edges) par based hai:

1.  **Graph Representation**:
      * Ek `adjacency list` (`List<Integer>[] adj`) banao courses ke graph ko represent karne ke liye. `adj[u]` mein woh saare courses honge jo `u` course complete hone ke baad liye ja sakte hain.
      * Ek `indegree` array (`int[] indegree`) banao. `indegree[i]` course `i` ke liye remaining prerequisites ki count store karega.
2.  **Build Graph and Indegrees**:
      * `prerequisites` array ko iterate karo. Har `[course, prerequisite]` pair ke liye:
          * `adj[prerequisite].add(course)`: `prerequisite` se `course` tak ek directed edge add karo.
          * `indegree[course]++`: `course` ki indegree badhao, kyunki `prerequisite` uska ek prerequisite hai.
3.  **Initialize Queue**:
      * Ek `Queue<Integer> queue` banao.
      * Saare courses ko queue mein add karo जिनकी `indegree` `0` hai (matlab unki koi prerequisites nahi hain aur unhe abhi liya ja sakta hai).
4.  **Process Courses (BFS)**:
      * Ek `List<Integer> ans` (ya `count`) banao jo successfully complete kiye gaye courses ko track karega.
      * `while (!queue.isEmpty())` loop chalao:
          * `int current = queue.poll();`: Queue se ek `current` course nikalo.
          * `ans.add(current);` (ya `count++`): Is course ko `ans` list mein add karo (ya count badhao).
          * **Reduce Indegrees of Neighbors**: `current` course complete ho gaya hai, iska matlab ab uske dependent courses ki prerequisites count kam ho jayegi.
              * `for (int next : adj[current])`: `current` ke har `next` course (jo `current` par dependent hai) ke liye:
                  * `indegree[next]--`: `next` course ki indegree kam karo.
                  * `if (indegree[next] == 0)`: Agar `next` course ki indegree `0` ho gayi hai (matlab uski saari prerequisites poori ho gayi hain), toh use `queue` mein add karo.
5.  **Check Result**:
      * Loop khatam hone ke baad, agar `ans.size()` (ya `count`) `numCourses` ke barabar hai, toh iska matlab saare courses successfully process ho gaye hain aur koi cycle nahi hai. Return `true`.
      * Agar `ans.size()` `numCourses` se kam hai, toh iska matlab graph mein ek cycle hai aur saare courses complete karna impossible hai. Return `false`.

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    public boolean canFinish(int n, int[][] prerequisites) {
        // Step 1: Adjacency list ko initialize karo.
        // adj[i] mein woh saare courses honge jo course 'i' complete hone ke baad liye ja sakte hain.
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>(); // Har course ke liye ek empty list banao
        }

        // Step 2: Indegree array initialize karo.
        // indegree[i] course 'i' ke liye remaining prerequisites ki count store karega.
        int[] indegree = new int[n];
        
        // Step 3: Graph (adjacency list) aur indegree array ko build karo.
        for (int[] pair : prerequisites) {
            int course = pair[0]; // Yeh course lene ke liye prerequisite chahiye
            int prerequisite = pair[1]; // Yeh course pehle lena hoga
            
            // prerequisite -> course ek directed edge hai.
            adj[prerequisite].add(course); 
            indegree[course]++; // course ki indegree badhao
        }

        // Step 4: Queue initialize karo un courses ke liye jinki koi prerequisites nahi hain (indegree = 0).
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i); // Queue mein add karo
            }
        }

        // Step 5: Courses ki count jo hum successfully finish kar paye hain.
        int coursesFinishedCount = 0;

        // Step 6: BFS topological sort process.
        while (!queue.isEmpty()) {
            int currentCourse = queue.poll(); // Queue se ek course nikalo
            coursesFinishedCount++; // Is course ko humne finish kar liya

            // Step 6.1: currentCourse ke saare dependent courses par iterate karo.
            // currentCourse ke complete hone se unki prerequisites count kam ho jayegi.
            for (int nextCourse : adj[currentCourse]) {
                indegree[nextCourse]--; // nextCourse ki indegree kam karo
                
                // Agar nextCourse ki indegree 0 ho gayi hai, matlab uski saari prerequisites poori ho gayi hain.
                // Ab use queue mein add kar sakte hain.
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // Step 7: Check result.
        // Agar humne saare 'n' courses finish kar liye hain, toh true return karo (no cycle).
        // Warna, false return karo (cycle exists).
        return coursesFinishedCount == n;
    }
}
```

-----

# ⭐ Course Schedule II

Aapke paas `numCourses` courses hain jinka label `0` se `numCourses - 1` tak hai.
Aapko ek array `prerequisites` diya gaya hai jahan `prerequisites[i] = [a_i, b_i]` ka matlab hai ki aapko course `a_i` lene se pehle course `b_i` lena zaroori hai.

Return **any valid ordering** of courses you should take to finish all courses.
Agar saare courses finish karna impossible hai (due to a cycle), toh ek empty array return karein.

### 📌 Example

Input:

```
numCourses = 2, prerequisites = [[1, 0]]
```

Output:

```
[0, 1]
```

Explanation:

  * Course 1 lene ke liye, aapko pehle course 0 finish karna hoga.
  * Toh valid course order hai `[0, 1]`.

-----

### Intuition (Samajh):

Yeh problem **Course Schedule** ka extension hai. Wahan humein bas yeh batana tha ki kya saare courses finish karna possible hai (`true`/`false`). Yahan humein **actual order** return karna hai.

Agar courses ke beech koi cycle hai, toh order impossible hai, aur humein empty array return karna hai. Agar cycle nahi hai, toh humein ek valid topological order return karna hai.

Is problem ko bhi **Topological Sort** se solve kiya ja sakta hai, specifically **Kahn's Algorithm (BFS-based approach)**. Kahn's algorithm naturally ek valid topological order generate karta hai.

-----

### Approach (Tareeka): Kahn's Algorithm (BFS-based Topological Sort)

Steps **Course Schedule** ke jaise hi hain, bas thoda sa change hai result collection mein:

1.  **Graph Representation**:
      * Ek `adjacency list` (`List<Integer>[] adj`) banao courses ke graph ko represent karne ke liye. `adj[u]` mein woh saare courses honge jo `u` course complete hone ke baad liye ja sakte hain.
      * Ek `indegree` array (`int[] indegree`) banao. `indegree[i]` course `i` ke liye remaining prerequisites ki count store karega.
2.  **Build Graph and Indegrees**:
      * `prerequisites` array ko iterate karo. Har `[course, prerequisite]` pair ke liye:
          * `adj[prerequisite].add(course)`: `prerequisite` se `course` tak ek directed edge add karo.
          * `indegree[course]++`: `course` ki indegree badhao.
3.  **Initialize Queue**:
      * Ek `Queue<Integer> queue` banao.
      * Saare courses ko queue mein add karo जिनकी `indegree` `0` hai.
4.  **Process Courses (BFS) and Collect Order**:
      * Ek `List<Integer> ans` banao jo courses ke **topological order** ko store karega.
      * `while (!queue.isEmpty())` loop chalao:
          * `int current = queue.poll();`: Queue se ek `current` course nikalo.
          * `ans.add(current);`: Is `current` course ko `ans` list mein add karo.
          * **Reduce Indegrees of Neighbors**: `current` course complete ho gaya hai, iska matlab ab uske dependent courses ki prerequisites count kam ho jayegi.
              * `for (int next : adj[current])`: `current` ke har `next` course ke liye:
                  * `indegree[next]--`: `next` course ki indegree kam karo.
                  * `if (indegree[next] == 0)`: Agar `next` course ki indegree `0` ho gayi hai, toh use `queue` mein add karo.
5.  **Final Result**:
      * Loop khatam hone ke baad, agar `ans.size()` `numCourses` ke barabar hai, toh iska matlab saare courses successfully process ho gaye hain aur `ans` list mein ek valid topological order hai. Is `ans` list ko `int[]` array mein convert karke return karo.
      * Agar `ans.size()` `numCourses` se kam hai, toh iska matlab graph mein ek cycle hai aur saare courses complete karna impossible hai. Is case mein, ek empty `int[]` array return karo.

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    public int[] findOrder(int n, int[][] prerequisites) {
        // Step 1: Adjacency list ko initialize karo.
        // adj[i] mein woh saare courses honge jo course 'i' complete hone ke baad liye ja sakte hain.
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>(); // Har course ke liye ek empty list banao
        }

        // Step 2: Indegree array initialize karo.
        // indegree[i] course 'i' ke liye remaining prerequisites ki count store karega.
        int[] indegree = new int[n];
        
        // Step 3: Graph (adjacency list) aur indegree array ko build karo.
        for (int[] pair : prerequisites) {
            int course = pair[0]; // Yeh course lene ke liye prerequisite chahiye
            int prerequisite = pair[1]; // Yeh course pehle lena hoga
            
            // prerequisite -> course ek directed edge hai.
            adj[prerequisite].add(course); 
            indegree[course]++; // course ki indegree badhao
        }

        // Step 4: Queue initialize karo un courses ke liye jinki koi prerequisites nahi hain (indegree = 0).
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i); // Queue mein add karo
            }
        }

        // Step 5: List to store the topological order of courses.
        List<Integer> ans = new ArrayList<>();

        // Step 6: BFS topological sort process.
        while (!queue.isEmpty()) {
            int currentCourse = queue.poll(); // Queue se ek course nikalo
            ans.add(currentCourse); // Is course ko order list mein add karo

            // Step 6.1: currentCourse ke saare dependent courses par iterate karo.
            for (int nextCourse : adj[currentCourse]) {
                indegree[nextCourse]--; // nextCourse ki indegree kam karo
                
                // Agar nextCourse ki indegree 0 ho gayi hai, matlab uski saari prerequisites poori ho gayi hain.
                // Ab use queue mein add kar sakte hain.
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // Step 7: Final Result.
        // Agar ans list ka size total courses ke barabar hai, toh matlab saare courses finish ho gaye hain (no cycle).
        if (ans.size() == n) {
            // List ko int array mein convert karo aur return karo.
            int[] resultArr = new int[n];
            for (int i = 0; i < n; i++) {
                resultArr[i] = ans.get(i);
            }
            return resultArr;
        } else {
            // Agar ans list ka size n se kam hai, toh matlab graph mein cycle hai.
            // Empty array return karo.
            return new int[0];
        }
    }
}
```

-----

# 🌲 Redundant Connection

Ek *tree* ek connected undirected graph hota hai jismein koi cycles nahi hote.
Aapko ek graph diya gaya hai jo `n` nodes (labeled `1` se `n` tak) ke saath **ek tree ke roop mein shuru hua tha**, aur **ek additional edge** add kiya gaya hai.

Woh edge return karein jise remove karne par graph phir se tree ban jaye.
Agar multiple answers exist karte hain, toh **woh return karein jo input mein sabse last mein aata hai**.

### 📌 Example

#### Input:

```java
edges = [[1,2],[1,3],[2,3]]
```

```
    1
   / \
  2 - 3
```

#### Output:

```java
[2,3]
```

Explanation:

  * Original tree ho sakta hai:

    ```
        1
       / \
      2   3
    ```

  * Edge `[2,3]` add karne par ek cycle banta hai: `2 → 1 → 3 → 2`

  * Toh `[2,3]` woh redundant edge hai jise remove karna hoga.

-----

### Intuition (Samajh):

Ek tree mein `N` nodes hote hain aur `N-1` edges hote hain, aur usmein koi cycle nahi hota. Problem statement kehta hai ki humein ek tree diya gaya tha aur usmein ek extra edge add kar diya gaya, jisse ek cycle ban gaya. Humein woh extra edge dhundhna hai.

Is problem ko solve karne ka sabse accha tareeka **Union-Find (Disjoint Set Union - DSU)** data structure ka use karna hai.

**Union-Find ka idea:**

  * Hum har node ko shuru mein ek alag set mein rakhte hain (har node apna parent khud hota hai).
  * Jab hum ek edge `(u, v)` ko process karte hain:
      * Hum `u` aur `v` ke **representatives (roots)** ko dhundhte hain.
      * Agar `u` aur `v` ke representatives **same** hain, toh iska matlab `u` aur `v` pehle se hi connected hain (unke beech ek path exist karta hai). Is edge `(u, v)` ko add karne se ek cycle ban jayega. Yahi hamara redundant edge hai.
      * Agar `u` aur `v` ke representatives **alag** hain, toh iska matlab `u` aur `v` abhi tak connected nahi hain. Hum unke sets ko `union` kar dete hain (ek ko doosre ka child bana dete hain).

Problem mein kaha gaya hai ki "return the one that occurs last in the input". Iska matlab hai ki hum edges ko unke input order mein process karenge, aur jaise hi humein pehla redundant edge milta hai (jo cycle banata hai), hum use return kar denge.

-----

### Approach (Tareeka): Union-Find

1.  **`parent` Array**: Ek `int[] parent` array banao size `n + 1` ka (nodes 1 se n tak hain). `parent[i]` store karega `i` ke parent ko. Initially, har node apna parent khud hoga: `parent[i] = i`.
2.  **`find` Function**: Ek helper function `find(i)` banao jo `i` ke set ka representative (root) dhundhega. Yeh function **path compression** bhi use karega optimization ke liye (root tak pahunchte hue saare nodes ko directly root ka child bana deta hai).
      * `if (parent[i] == i) return i;` (Base case: agar `i` khud ka parent hai, toh yahi root hai).
      * `return parent[i] = find(parent[i]);` (Recursive call aur path compression).
3.  **`union` Function**: Ek helper function `union(i, j)` banao jo `i` aur `j` ke sets ko merge karega.
      * `int rootI = find(i);`
      * `int rootJ = find(j);`
      * `if (rootI != rootJ)`: Agar roots alag hain, toh sets ko merge karo (`parent[rootI] = rootJ;` ya `parent[rootJ] = rootI;`).
      * `return rootI != rootJ;`: `true` return karo agar union hua (sets alag the), `false` agar nahi hua (sets same the, cycle detected).
4.  **Iterate Edges**: `edges` array ko iterate karo. Har `edge = [u, v]` ke liye:
      * `u` aur `v` ko 0-based index mein convert karne ki zaroorat nahi agar `parent` array 1-based index use karta hai (size `n+1`). Agar 0-based use kar rahe ho, toh `u-1, v-1` use karo.
      * `if (!union(u, v))`: `union` function ko call karo. Agar `union` `false` return karta hai, iska matlab `u` aur `v` already connected the (unke roots same the). Toh `[u, v]` hi redundant edge hai. Ise return kar do.
5.  **No Redundant Edge Found**: Problem constraints ke hisab se, ek redundant edge hamesha hoga. Agar loop khatam ho jata hai aur kuch return nahi hota, toh `new int[0]` return kar sakte hain (though it shouldn't happen per problem).

-----

### Java Code with Comments:

```java
import java.util.ArrayList; // Not strictly needed for this Union-Find solution, but good practice

class Solution {

    // Parent array for Union-Find. parent[i] stores the parent of element i.
    // Initially, each element is its own parent.
    private int[] parent;

    // find function with path compression.
    // Yeh element 'i' ke set ka representative (root) dhundhta hai.
    private int find(int i) {
        // Base case: Agar element khud ka parent hai, toh yahi root hai.
        if (parent[i] == i) {
            return i;
        }
        // Path Compression: Recursively find the root and set it as parent of 'i'.
        return parent[i] = find(parent[i]);
    }

    // union function.
    // Yeh do elements 'i' aur 'j' ke sets ko merge karta hai.
    // True return karta hai agar union successful hua (sets alag the),
    // False return karta hai agar sets pehle se hi same the (cycle detected).
    private boolean union(int i, int j) {
        int rootI = find(i); // i ka root dhundo
        int rootJ = find(j); // j ka root dhundo

        // Agar roots alag hain, toh sets ko merge karo.
        if (rootI != rootJ) {
            parent[rootI] = rootJ; // rootI ke set ko rootJ ke set ka child bana do.
            return true; // Union successful
        }
        return false; // Roots same hain, matlab cycle banega. Union nahi hua.
    }

    public int[] findRedundantConnection(int[][] edges) {
        // n nodes hain, labeled 1 se n tak.
        // edges.length = n, kyunki tree mein n-1 edges the aur ek extra add kiya gaya.
        int n = edges.length; 
        
        // parent array ko initialize karo. Size n+1 kyunki nodes 1-indexed hain.
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i; // Har node initially apna parent khud hai.
        }

        // Edges ko unke input order mein iterate karo.
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            // Try to union u aur v.
            // Agar union false return karta hai, iska matlab u aur v pehle se hi connected the.
            // Toh yeh current edge [u, v] hi redundant edge hai jo cycle bana raha hai.
            if (!union(u, v)) {
                return edge; // Is edge ko return karo.
            }
        }

        // Yeh line theoretically unreachable honi chahiye according to problem constraints
        // (kyunki ek redundant edge hamesha hoga).
        return new int[0]; 
    }
}
```

-----

# 🔤 Word Ladder

Given:

  * Ek `beginWord`
  * Ek `endWord`
  * Ek `wordList` containing valid intermediate words

`beginWord` ko `endWord` mein transform karein is tarah se ki:

1.  Ek time mein sirf ek letter change ho sakta hai.
2.  Har transformed word `wordList` mein exist karna chahiye.

**Shortest transformation sequence** mein words ki count return karein, jismein `beginWord` aur `endWord` bhi shamil hon.
Agar koi aisi sequence nahi milti, toh `0` return karein.

### 📌 Example

#### Input:

```java
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
```

#### Output:

```java
5
```

#### Explanation:

Ek shortest transformation hai:

```
"hit" → "hot" → "dot" → "dog" → "cog"
```

Is path mein 5 words hain.

-----

### Intuition (Samajh):

Yeh problem ek **shortest path problem** hai ek **unweighted graph** mein.

  * Har word ko graph ka ek **node** mana ja sakta hai.
  * Do words ke beech ek **edge** exist karta hai agar woh sirf ek letter se differ karte hain.

Humein `beginWord` se `endWord` tak ka shortest path dhundhna hai, aur path mein words ki count return karni hai.
Shortest path in an unweighted graph ko dhundhne ke liye **Breadth-First Search (BFS)** sabse efficient algorithm hai.

-----

### Approach (Tareeka): BFS

1.  **Preprocessing `wordList`**: `wordList` ko ek `HashSet<String> wordSet` mein convert karo. Isse words ko `O(1)` time mein check kar sakte hain.
2.  **`endWord` Check**: Agar `endWord` `wordSet` mein nahi hai, toh koi transformation possible nahi hai, `0` return karo.
3.  **BFS Initialization**:
      * Ek `Queue<String> q` banao aur `beginWord` ko usmein add karo.
      * Ek `HashSet<String> visited` banao jo visited words ko track karega, taaki hum cycles se bachein aur redundant processing na karein. `beginWord` ko `visited` mein add karo.
      * `int changes = 1`: Yeh current transformation level (ya path length) ko track karega. `beginWord` khud ek word hai, isliye 1 se start karte hain.
4.  **BFS Traversal**: `while (!q.isEmpty())` loop chalao.
      * `int size = q.size();`: Current level mein kitne words hain, yeh store karo. Yeh zaroori hai kyunki hum level by level traverse kar rahe hain.
      * `for (int i = 0; i < size; i++)`: Current level ke saare words ko process karo.
          * `String currentWord = q.poll();`: Queue se ek `currentWord` nikalo.
          * **Check `endWord`**: `if (currentWord.equals(endWord)) return changes;`: Agar `currentWord` `endWord` hai, toh hum target tak pahunch gaye hain. `changes` hi shortest path length hai, use return karo.
          * **Generate Neighbors**: `currentWord` se one-letter-difference wale saare possible words generate karo:
              * `char[] charArray = currentWord.toCharArray();`: `currentWord` ko `char` array mein convert karo taaki letters ko easily change kar sakein.
              * `for (int j = 0; j < charArray.length; j++)`: Har position par iterate karo.
                  * `char originalChar = charArray[j];`: Original character ko store karo.
                  * `for (char c = 'a'; c <= 'z'; c++)`: `a` se `z` tak har character se replace karke dekho.
                      * `charArray[j] = c;`: Character change karo.
                      * `String newWord = new String(charArray);`: Naya word banao.
                      * **Validation**: `if (wordSet.contains(newWord) && !visited.contains(newWord))`: Agar `newWord` `wordSet` mein hai (valid word) aur abhi tak visited nahi hai:
                          * `q.add(newWord);`: `newWord` ko queue mein add karo.
                          * `visited.add(newWord);`: `newWord` ko visited mark karo.
                  * `charArray[j] = originalChar;`: Character ko wapas original par set karo next position try karne ke liye.
      * `changes++;`: Current level ke saare words process hone ke baad, `changes` (path length) ko 1 se badhao, kyunki hum next level par ja rahe hain.
5.  **No Path Found**: Agar `while` loop khatam ho jata hai aur `endWord` tak nahi pahunchte, toh koi transformation sequence exist nahi karta. `0` return karo.

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Step 1: wordList ko HashSet mein convert karo for O(1) average time lookup.
        Set<String> wordSet = new HashSet<>(wordList);
        
        // Step 2: Agar endWord, wordList mein nahi hai, toh koi transformation possible nahi hai.
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // Step 3: BFS ke liye Queue aur visited Set initialize karo.
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord); // beginWord ko queue mein add karo.
        
        // visited set track karega ki kaun se words ko humne already process kar liya hai.
        // Isse cycles avoid honge aur redundant processing nahi hogi.
        Set<String> visited = new HashSet<>();
        visited.add(beginWord); // beginWord ko visited mark karo.

        // changes: Yeh transformation sequence ki length ko track karega.
        // beginWord khud ek word hai, isliye 1 se start karte hain.
        int changes = 1; 
        int wordLength = beginWord.length();

        // Step 4: BFS traversal loop.
        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Current level mein kitne words hain.

            // Current level ke saare words ko process karo.
            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll(); // Queue se ek word nikalo.

                // Agar currentWord, endWord ke barabar hai, toh hum target tak pahunch gaye hain.
                // changes hi shortest path length hai.
                if (currentWord.equals(endWord)) {
                    return changes;
                }

                // Current word se one-letter-difference wale saare possible words generate karo.
                char[] charArray = currentWord.toCharArray();
                for (int j = 0; j < wordLength; j++) { // Har character position par iterate karo.
                    char originalChar = charArray[j]; // Original character store karo.

                    // 'a' se 'z' tak har character se replace karke dekho.
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArray[j] = c; // Character change karo.
                        String newWord = new String(charArray); // Naya word banao.

                        // Agar naya word wordSet mein hai (valid word) AND abhi tak visited nahi hai.
                        if (wordSet.contains(newWord) && !visited.contains(newWord)) {
                            queue.add(newWord); // Naye valid word ko queue mein add karo.
                            visited.add(newWord); // Naye word ko visited mark karo.
                        }
                    }
                    charArray[j] = originalChar; // Character ko wapas original par set karo for next position.
                }
            }
            changes++; // Current level ke saare words process hone ke baad, length badhao.
        }

        // Step 5: Agar loop khatam ho jata hai aur endWord tak nahi pahunchte, toh koi path nahi mila.
        return 0; 
    }
}
```

-----

# 🔤 Word Ladder II

Given:

  * Ek `beginWord`
  * Ek `endWord`
  * Ek `wordList` containing valid intermediate words

`beginWord` ko `endWord` mein transform karein is tarah se ki:

1.  Ek time mein sirf ek letter change ho sakta hai.
2.  Har transformed word `wordList` mein exist karna chahiye.
3.  **Sabhi shortest transformation sequences** `beginWord` se `endWord` tak return karein.

Har sequence ko words ki list `[beginWord, s1, s2, ..., endWord]` ke roop mein return kiya jana chahiye.

Agar koi aisi sequence nahi milti, toh ek empty list return karein.

-----

### 📌 Example

#### Input:

```java
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
```

#### Output:

```java
[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
```

#### Explanation:

2 shortest transformation sequences hain:

```
"hit" → "hot" → "dot" → "dog" → "cog"
"hit" → "hot" → "lot" → "log" → "cog"
```

-----

### Intuition (Samajh):

`Word Ladder` problem mein humein sirf shortest path ki length chahiye thi. `Word Ladder II` mein humein **saare shortest paths** chahiye. Yeh problem thoda zyada complex hai kyunki BFS sirf length deta hai, path construct karne ke liye humein additional information store karni padti hai.

Is problem ko solve karne ke liye, hum do phases mein kaam karenge:

1.  **Phase 1: BFS (Build a Graph of Shortest Paths)**

      * Hum BFS ka use karenge `beginWord` se `endWord` tak ke shortest path ki length determine karne ke liye.
      * Lekin, sirf length hi nahi, humein har word ke liye uske **parents** (yani, woh words jo current word se just pehle shortest path mein aa sakte hain) ko bhi track karna hoga. Yeh ek **reverse graph** banayega jahan child se parent tak edges honge.
      * BFS ko level by level chalayenge. Jab `endWord` mil jaye, toh BFS ko rok denge, kyunki humein sirf shortest paths chahiye. Kisi bhi word ko agar woh already visited ho chuka hai ya current level se pehle ke level mein visit ho chuka hai, toh use dobara process nahi karenge.

2.  **Phase 2: DFS (Backtrack All Shortest Paths)**

      * Ek baar jab humare paas `parents` map ban jata hai (jo shortest paths ke edges ko represent karta hai), toh hum `endWord` se `beginWord` tak **DFS** (backtracking) ka use karke saare possible shortest paths ko reconstruct karenge.
      * Har path ko `LinkedList` mein store karenge aur use reverse order mein build karenge (end se start tak), phir jab `beginWord` tak pahunch jayein, toh path ko `result` list mein add kar denge.

-----

### Approach (Tareeka): BFS + DFS (Backtracking)

1.  **Initialization**:

      * `Set<String> wordSet = new HashSet<>(wordList);`: `wordList` ko `HashSet` mein convert karo for `O(1)` lookups.
      * `if (!wordSet.contains(endWord)) return new ArrayList<>();`: Agar `endWord` `wordSet` mein nahi hai, toh empty list return karo.
      * `Map<String, List<String>> parents = new HashMap<>();`: Yeh map `childWord` ko `List<parentWord>` se map karega.
      * `Set<String> currentLevel = new HashSet<>();`: BFS ke current level ke words.
      * `currentLevel.add(beginWord);`
      * `Set<String> visited = new HashSet<>();`: Globally visited words (jo previous levels mein process ho chuke hain).
      * `boolean found = false;`: Flag to indicate if `endWord` has been found in the current BFS level.

2.  **BFS Phase (Building `parents` map)**:

      * `while (!currentLevel.isEmpty() && !found)`: Loop jab tak current level empty nahi hota aur `endWord` nahi mil jata.
          * `Set<String> nextLevel = new HashSet<>();`: Next level ke words.
          * `visited.addAll(currentLevel);`: Current level ke saare words ko `visited` mark karo.
          * `for (String word : currentLevel)`: Current level ke har `word` ke liye:
              * Generate all one-letter-difference `nextWord`s (same as Word Ladder I).
              * `if (wordSet.contains(nextWord) && !visited.contains(nextWord))`: Agar `nextWord` valid hai aur abhi tak visited nahi hai (globally):
                  * `parents.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(word);`: `nextWord` ke parents list mein `word` ko add karo.
                  * `if (nextWord.equals(endWord)) found = true;`: Agar `endWord` mil gaya, flag set karo.
                  * `nextLevel.add(nextWord);`: `nextWord` ko next level mein add karo.
          * `currentLevel = nextLevel;`: Next level par move karo.

3.  **DFS Phase (Backtracking Paths)**:

      * `List<List<String>> result = new ArrayList<>();`
      * `if (found)`: Agar `endWord` mila tha BFS mein:
          * `List<String> path = new LinkedList<>();`: Ek `LinkedList` banao path store karne ke liye (front se add karna efficient hota hai).
          * `backtrack(endWord, beginWord, parents, path, result);`: `backtrack` helper function ko call karo.

4.  **`backtrack` Helper Function (DFS)**:

      * `backtrack(String word, String beginWord, Map<String, List<String>> parents, List<String> path, List<List<String>> result)`
      * **Base Case**: `if (word.equals(beginWord))`: Agar `beginWord` tak pahunch gaye:
          * `path.add(0, word);`: `beginWord` ko path ke shuru mein add karo.
          * `result.add(new ArrayList<>(path));`: Path ki copy `result` mein add karo.
          * `path.remove(0);`: `beginWord` ko path se remove karo (backtrack ke liye).
          * `return;`
      * `path.add(0, word);`: Current `word` ko path ke shuru mein add karo.
      * `for (String parent : parents.get(word))`: `word` ke har `parent` ke liye:
          * `backtrack(parent, beginWord, parents, path, result);`: Recursive call karo.
      * `path.remove(0);`: Current `word` ko path se remove karo (backtrack ke liye).

-----

### Java Code with Comments:

```java
import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // Step 1: wordList ko HashSet mein convert karo for O(1) average time lookup.
        Set<String> wordSet = new HashSet<>(wordList);

        // Step 2: Agar endWord, wordList mein nahi hai, toh empty result return karo.
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }

        // Step 3: Map to store reverse edges: child -> list of parent words.
        // Yeh BFS ke dauraan banega aur DFS mein paths reconstruct karne ke liye use hoga.
        Map<String, List<String>> parents = new HashMap<>();

        // Step 4: BFS ke liye current level ke words ka set.
        Set<String> currentLevel = new HashSet<>();
        currentLevel.add(beginWord);

        // Step 5: Globally visited words ka set. Yeh ensure karega ki hum har word ko shortest path mein sirf ek baar visit karein.
        Set<String> visited = new HashSet<>();
        
        // Flag to indicate if endWord has been found in the current BFS level.
        // Agar mil gaya toh BFS rok denge kyunki humein shortest paths hi chahiye.
        boolean found = false;

        // Step 6: BFS Phase - Building the 'parents' map.
        while (!currentLevel.isEmpty() && !found) {
            Set<String> nextLevel = new HashSet<>(); // Next level ke words.
            visited.addAll(currentLevel); // Current level ke saare words ko visited mark karo.

            for (String word : currentLevel) {
                char[] chars = word.toCharArray(); // Word ko char array mein convert karo.
                for (int i = 0; i < chars.length; i++) { // Har character position par iterate karo.
                    char original = chars[i]; // Original character store karo.

                    // 'a' se 'z' tak har character se replace karke dekho.
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue; // Agar character same hai, toh skip karo.
                        chars[i] = c; // Character change karo.
                        String nextWord = new String(chars); // Naya word banao.

                        // Agar naya word wordSet mein nahi hai YA already globally visited hai, toh skip karo.
                        if (!wordSet.contains(nextWord) || visited.contains(nextWord)) {
                            continue;
                        }

                        // Agar valid aur unvisited hai, toh nextWord ke parents list mein current 'word' ko add karo.
                        parents.computeIfAbsent(nextWord, k -> new ArrayList<>()).add(word);

                        // Agar nextWord, endWord ke barabar hai, toh 'found' flag set karo.
                        if (nextWord.equals(endWord)) {
                            found = true;
                        }
                        
                        // nextWord ko next level mein add karo.
                        nextLevel.add(nextWord);
                    }
                    chars[i] = original; // Character ko wapas original par set karo for next position.
                }
            }
            // Current level ko next level se replace karo.
            currentLevel = nextLevel; 
        }

        // Step 7: Result list to store all shortest paths.
        List<List<String>> result = new ArrayList<>();

        // Step 8: DFS Phase - Backtracking to find all paths.
        if (found) { // Agar endWord BFS mein mila tha.
            List<String> path = new LinkedList<>(); // Path store karne ke liye LinkedList (add(0, element) efficient hai).
            // Backtrack function ko endWord se beginWord tak call karo.
            backtrack(endWord, beginWord, parents, path, result);
        }

        return result;
    }

    /**
     * Helper DFS method to backtrack all shortest paths from endWord to beginWord.
     * @param word Current word in the path (starting from endWord, going backwards).
     * @param beginWord The starting word of the transformation sequence.
     * @param parents Map storing child -> list of parent words.
     * @param path Current path being built (in reverse order).
     * @param result List to store all complete shortest paths.
     */
    private void backtrack(String word, String beginWord, Map<String, List<String>> parents,
                           List<String> path, List<List<String>> result) {
        // Base case: Agar beginWord tak pahunch gaye hain.
        if (word.equals(beginWord)) {
            path.add(0, word); // beginWord ko path ke shuru mein add karo.
            result.add(new ArrayList<>(path)); // Path ki copy result mein add karo.
            path.remove(0); // Backtrack karte hue beginWord ko remove karo.
            return;
        }

        // Agar current word ke koi parents nahi hain (shouldn't happen in a valid shortest path).
        if (!parents.containsKey(word)) {
            return;
        }

        path.add(0, word); // Current word ko path ke shuru mein add karo.
        // Current word ke har parent par recursively call karo.
        for (String parent : parents.get(word)) {
            backtrack(parent, beginWord, parents, path, result);
        }
        path.remove(0); // Backtrack karte hue current word ko remove karo.
    }
}
```
