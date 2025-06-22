
# Below are common backtracking patterns (with code structure) categorized by problem type.

⸻

🧩 1. Subset / Combination Pattern

Use when choosing whether to include an item.

🔹 Pattern:

void backtrack(int start, List<Integer> path) {
    result.add(new ArrayList<>(path)); // Record current subset
    
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);               // Choose
        backtrack(i + 1, path);          // Explore
        path.remove(path.size() - 1);    // Unchoose (backtrack)
    }
}

📌 Examples:
	•	Subsets ([1,2,3] → all subsets)
	•	Combination Sum (pick elements multiple times)
	•	Combinations (pick k numbers out of n)

⸻

🔢 2. Permutation Pattern

Use when you’re arranging items in different orders.

🔹 Pattern:

void backtrack(List<Integer> path, boolean[] used) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;           // Skip if already used
        used[i] = true;                  // Mark used
        path.add(nums[i]);               // Choose
        backtrack(path, used);           // Explore
        path.remove(path.size() - 1);    // Unchoose
        used[i] = false;                 // Reset
    }
}

📌 Examples:
	•	Permutations ([1,2,3] → all orders)
	•	Next Permutation
	•	All Unique Permutations (add duplicate check)

⸻

🔗 3. Palindrome Partitioning Pattern

Use when dividing a string into parts based on a rule (like palindrome).

🔹 Pattern:

void backtrack(String s, int start, List<String> path) {
    if (start == s.length()) {
        result.add(new ArrayList<>(path));
        return;
    }

    for (int end = start + 1; end <= s.length(); end++) {
        String sub = s.substring(start, end);
        if (isPalindrome(sub)) {
            path.add(sub);
            backtrack(s, end, path);
            path.remove(path.size() - 1);
        }
    }
}

📌 Examples:
	•	Palindrome Partitioning
	•	Restore IP Addresses

⸻

🧩 4. N-Queens / Sudoku / Grid-style Constraint Problems

Place items (e.g., queens) on a grid with constraints.

🔹 Pattern:

void backtrack(int row) {
    if (row == n) {
        result.add(buildBoard()); // Add current valid configuration
        return;
    }

    for (int col = 0; col < n; col++) {
        if (isValid(row, col)) {
            placeQueen(row, col);
            backtrack(row + 1);
            removeQueen(row, col); // Backtrack
        }
    }
}

📌 Examples:
	•	N-Queens
	•	Sudoku Solver
	•	Word Search (grid path)

⸻

🧭 5. Maze / Grid Path Finding (DFS Path Style)

Used when navigating grid-based paths with direction.

🔹 Pattern:

void dfs(int row, int col, List<String> path) {
    if (out of bounds or invalid) return;

    if (at goal) {
        result.add(new ArrayList<>(path));
        return;
    }

    markVisited(row, col);
    
    for (dir in directions) {
        path.add(dir.name);
        dfs(newRow, newCol, path);
        path.remove(path.size() - 1);
    }

    unmarkVisited(row, col); // Backtrack
}

📌 Examples:
	•	Rat in a Maze
	•	All Paths from Source to Target in Grid
	•	Word Search (Backtracking + Grid)

⸻

🚀 Bonus: Bitmask Backtracking (for optimization problems)

Used in hard constraint-based problems (e.g., TSP, DP + Bitmask)

🔹 Pattern:

void backtrack(int stateMask, int depth) {
    if (goal) {
        result++;
        return;
    }

    for (int i = 0; i < n; i++) {
        if ((stateMask & (1 << i)) == 0) {
            backtrack(stateMask | (1 << i), depth + 1);
        }
    }
}


⸻
