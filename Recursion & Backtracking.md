# Recursion and Backtracking 

**Recursion** is a programming concept where a function calls itself directly or indirectly to solve a problem by breaking it down into smaller, more manageable sub-problems. It's commonly used to solve problems that have a repetitive structure, like tree traversal, factorial calculation, or solving puzzles.

A **recursive function** typically has two main parts:
1. **Base Case**: This is the stopping condition, where the problem is simple enough to solve without further recursion.
2. **Recursive Case**: This is where the function calls itself with a modified version of the problem.

Example (Factorial Calculation):
```java
public class RecursionExample {
    public static int factorial(int n) {
        if (n == 0) {
            return 1;  // Base case: factorial(0) is 1
        }
        return n * factorial(n - 1);  // Recursive case
    }

    public static void main(String[] args) {
        int result = factorial(5);  // 5! = 5 * 4 * 3 * 2 * 1
        System.out.println(result);  // Output: 120
    }
}
```

### Backtracking
**Backtracking** is an algorithmic technique that uses recursion to solve problems incrementally, one step at a time. If the current step doesn't lead to a solution, the algorithm "backtracks" to a previous step and tries a different path. It is useful for problems like:
- Finding all solutions (or a valid one) to a puzzle (e.g., Sudoku).
- Combinatorial problems (e.g., generating permutations or subsets).
- Constraint satisfaction problems (e.g., N-Queens, Maze solving).

In backtracking, you try a possible solution, and if it doesn’t work, you go back and try another. It’s like exploring all possible paths in a decision tree but abandoning branches that are not feasible.

Example (N-Queens Problem):
```java
public class NQueens {
    private static boolean isSafe(char[][] board, int row, int col) {
        // Check the column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }
        // Check the left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        // Check the right diagonal
        for (int i = row, j = col; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    private static void solve(char[][] board, int row) {
        if (row == board.length) {
            printBoard(board);
            return;
        }
        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';
                solve(board, row + 1);  // Recursive case
                board[row][col] = '.';  // Backtrack
            }
        }
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 4;  // Board size (4x4)
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        solve(board, 0);  // Start solving from the first row
    }
}
```

### Key Differences:
- **Recursion** is a technique for breaking down a problem into smaller subproblems, while **backtracking** uses recursion to explore all possible solutions and "backtrack" when a solution is invalid.
- Recursion solves a problem top-down, whereas backtracking is about trying, failing, and revisiting different choices to find valid solutions.



# interview questions

### Recursion and Backtracking are two important topics in coding interviews. Below are some common interview questions related to **Recursion** and **Backtracking**, along with answers and code examples in Java.

---

### **1. Fibonacci Series Using Recursion**
   **Question**: Write a function to calculate the nth Fibonacci number using recursion.

   **Solution**:
   The Fibonacci series is defined as:  
   `F(0) = 0`, `F(1) = 1`, and `F(n) = F(n-1) + F(n-2)` for `n >= 2`.

   ```java
   public class FibonacciRecursion {
       public static int fibonacci(int n) {
           if (n <= 1) {
               return n;
           }
           return fibonacci(n - 1) + fibonacci(n - 2);
       }

       public static void main(String[] args) {
           int n = 10;  // Example input
           System.out.println("Fibonacci number at position " + n + " is " + fibonacci(n));
       }
   }
   ```

   **Explanation**: This code recursively computes the Fibonacci number by breaking the problem down into smaller subproblems.

---

### **2. Factorial Using Recursion**
   **Question**: Write a recursive function to calculate the factorial of a number.

   **Solution**:
   The factorial of `n` is defined as:  
   `n! = n * (n-1) * (n-2) * ... * 1`  
   Base case: `0! = 1`.

   ```java
   public class FactorialRecursion {
       public static int factorial(int n) {
           if (n == 0) {
               return 1;
           }
           return n * factorial(n - 1);
       }

       public static void main(String[] args) {
           int n = 5;  // Example input
           System.out.println("Factorial of " + n + " is " + factorial(n));
       }
   }
   ```

   **Explanation**: The recursion works by reducing `n` until it reaches the base case `0!`, then multiplying the results on the way back up.

---

### **3. Tower of Hanoi**
   **Question**: Solve the Tower of Hanoi problem using recursion for `n` disks.

   **Solution**:
   The objective is to move `n` disks from source (rod A) to destination (rod C) using an auxiliary rod (B).  
   The recurrence relation is:  
   Move `n-1` disks from A to B, move the nth disk from A to C, and finally move `n-1` disks from B to C.

   ```java
   public class TowerOfHanoi {
       public static void solveHanoi(int n, char source, char auxiliary, char destination) {
           if (n == 1) {
               System.out.println("Move disk 1 from " + source + " to " + destination);
               return;
           }

           solveHanoi(n - 1, source, destination, auxiliary);
           System.out.println("Move disk " + n + " from " + source + " to " + destination);
           solveHanoi(n - 1, auxiliary, source, destination);
       }

       public static void main(String[] args) {
           int n = 3;  // Example input
           solveHanoi(n, 'A', 'B', 'C');
       }
   }
   ```

   **Explanation**: The recursive process moves `n-1` disks to the auxiliary rod, then moves the nth disk to the destination rod, and then moves the `n-1` disks from the auxiliary rod to the destination rod.

---

### **4. N-Queens Problem**
   **Question**: Solve the N-Queens problem using backtracking.

   **Solution**:
   The N-Queens problem involves placing N queens on an `N x N` chessboard such that no two queens threaten each other (i.e., no two queens share the same row, column, or diagonal).

   ```java
   public class NQueens {
       private static boolean isSafe(int board[][], int row, int col, int n) {
           for (int i = 0; i < col; i++) {
               if (board[row][i] == 1) return false;
           }

           for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
               if (board[i][j] == 1) return false;
           }

           for (int i = row, j = col; i < n && j >= 0; i++, j--) {
               if (board[i][j] == 1) return false;
           }

           return true;
       }

       private static boolean solveNQueens(int board[][], int col, int n) {
           if (col >= n) return true;

           for (int i = 0; i < n; i++) {
               if (isSafe(board, i, col, n)) {
                   board[i][col] = 1;

                   if (solveNQueens(board, col + 1, n)) return true;

                   board[i][col] = 0;  // Backtrack
               }
           }

           return false;
       }

       public static void printSolution(int board[][], int n) {
           for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
                   System.out.print(" " + board[i][j] + " ");
               }
               System.out.println();
           }
       }

       public static void main(String[] args) {
           int n = 4;  // Example input
           int board[][] = new int[n][n];

           if (solveNQueens(board, 0, n)) {
               printSolution(board, n);
           } else {
               System.out.println("Solution does not exist");
           }
       }
   }
   ```

   **Explanation**: This is a backtracking solution where we try placing a queen in each column, check for conflicts, and backtrack if necessary.

---

### **5. Subset Sum Problem**
   **Question**: Find if there exists a subset of a given set with a sum equal to a given target using recursion.

   **Solution**:
   For each element, you either include it in the subset or exclude it, forming two branches for each decision. The recursion stops when the target becomes 0 (subset found) or the array is fully traversed.

   ```java
   public class SubsetSum {
       public static boolean isSubsetSum(int[] set, int n, int target) {
           if (target == 0) return true;
           if (n == 0) return false;

           if (set[n - 1] > target) {
               return isSubsetSum(set, n - 1, target);
           }

           return isSubsetSum(set, n - 1, target) || isSubsetSum(set, n - 1, target - set[n - 1]);
       }

       public static void main(String[] args) {
           int set[] = {3, 34, 4, 12, 5, 2};
           int target = 9;
           int n = set.length;
           if (isSubsetSum(set, n, target)) {
               System.out.println("Found a subset with the given sum");
           } else {
               System.out.println("No subset with the given sum");
           }
       }
   }
   ```

   **Explanation**: This approach explores both possibilities at each step, either including or excluding the current element in the subset.

---

### **6. Permutations of a String Using Backtracking**
   **Question**: Generate all permutations of a given string using recursion and backtracking.

   **Solution**:
   The problem involves swapping characters to generate all possible arrangements.

   ```java
   public class StringPermutations {
       public static void permute(String str, int left, int right) {
           if (left == right) {
               System.out.println(str);
           } else {
               for (int i = left; i <= right; i++) {
                   str = swap(str, left, i);
                   permute(str, left + 1, right);
                   str = swap(str, left, i);  // Backtrack
               }
           }
       }

       public static String swap(String str, int i, int j) {
           char[] charArray = str.toCharArray();
           char temp = charArray[i];
           charArray[i] = charArray[j];
           charArray[j] = temp;
           return String.valueOf(charArray);
       }

       public static void main(String[] args) {
           String str = "ABC";  // Example input
           permute(str, 0, str.length() - 1);
       }
   }
   ```

   **Explanation**: This recursive function generates all permutations by swapping each character and recursively permuting the rest of the string.

---

### **7. Rat in a Maze (Backtracking)**
   **Question**: Solve the "Rat in a Maze" problem where a rat must move from the top-left to the bottom-right corner of a maze, moving only in two directions: right and down.

   **Solution**:
   Use backtracking to explore all possible paths and print a solution if a path exists.

   ```java
   public class RatInMaze {
       private static boolean isSafe(int[][] maze, int x, int y) {
           return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] ==

 1;
       }

       private static boolean solveMaze(int[][] maze, int[][] sol, int x, int y) {
           if (x == maze.length - 1 && y == maze[0].length - 1) {
               sol[x][y] = 1;
               return true;
           }

           if (isSafe(maze, x, y)) {
               sol[x][y] = 1;

               if (solveMaze(maze, sol, x + 1, y)) return true;
               if (solveMaze(maze, sol, x, y + 1)) return true;

               sol[x][y] = 0;  // Backtrack
               return false;
           }

           return false;
       }

       public static void printSolution(int[][] sol) {
           for (int[] row : sol) {
               for (int cell : row) {
                   System.out.print(cell + " ");
               }
               System.out.println();
           }
       }

       public static void main(String[] args) {
           int[][] maze = {
               {1, 0, 0, 0},
               {1, 1, 0, 1},
               {0, 1, 0, 0},
               {1, 1, 1, 1}
           };

           int[][] sol = new int[maze.length][maze[0].length];

           if (solveMaze(maze, sol, 0, 0)) {
               printSolution(sol);
           } else {
               System.out.println("No solution exists");
           }
       }
   }
   ```

   **Explanation**: The rat explores all possible paths using backtracking. If it reaches the goal, the path is valid. Otherwise, it backtracks and tries a different path.
