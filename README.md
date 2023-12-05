# 8-Puzzle Solver using A* Algorithm with Manhattan Distance Heuristic

This repository contains a implementation of a solver for the 8-puzzle problem using the A* algorithm with the Manhattan Distance heuristic. The 8-puzzle is a classic problem where a 3x3 grid is filled with eight numbered tiles, and the goal is to rearrange the tiles from an initial state to a goal state by sliding the tiles into the empty space.

## Puzzle Representation

In this implementation, the puzzle is represented as a single integer of 9 digits. Each digit corresponds to a tile in the puzzle. The digits are arranged from left to right and top to bottom, excluding the empty space. For example, the initial state `385742160` represents the following 8-puzzle:

```
3 8 5
7 4 2
1 6 0
```

Here, '0' represents the empty space.

## Manhattan Distance Heuristic

The A* algorithm uses the Manhattan Distance as a heuristic to guide the search. The Manhattan Distance between two tiles is the sum of the horizontal and vertical distances between their current and goal positions. The heuristic is admissible, meaning it never overestimates the cost to reach the goal.

### Admissibility of Manhattan Distance

The Manhattan Distance is admissible because it satisfies the following condition:

$\[h(n) \leq h^*(n)\]$

where:
- $\(h(n)\)$ is the estimated cost from node $\(n\)$ to the goal (Manhattan Distance),
- $\(h^*(n)\)$ is the true cost from node $\(n\)$ to the goal.

In the context of the 8-puzzle, the Manhattan Distance is a lower bound on the true cost of reaching the goal. This is because each tile must move at least its Manhattan Distance to reach its goal position. The sum of these distances for all tiles provides a lower bound on the total cost.

### Consistency of Manhattan Distance

The Manhattan Distance is also a consistent (or monotonic) heuristic because it satisfies the triangle inequality:

$\[h(n) \leq c(n, a, b) + h(b) \]$

where:
- $\(c(n, a, b)\)$ is the cost of moving from node $\(a\)$ to node $\(b\)$,
- $\(h(n)\)$ is the heuristic estimate of the cost to reach the goal from node $\(n\)$.

In simpler terms, the estimated cost from the current node to the goal is less than or equal to the cost of reaching a successor node plus the estimated cost from the successor to the goal.

For the 8-puzzle, this means that the sum of the Manhattan Distances from the current state to the goal is always less than or equal to the actual cost of reaching the goal. Therefore, the Manhattan Distance heuristic is consistent.

## Algorithm Complexity

- **Time Complexity:** The time complexity of the A* algorithm depends on factors such as the heuristic's accuracy and the efficiency of the data structures used. In the worst case, it has an exponential time complexity, but with a good heuristic, it often performs well in practice.

- **Space Complexity:** The space complexity is influenced by the number of nodes stored in memory during the search. In the worst case, it can be exponential, but the use of pruning strategies and efficient data structures, such as priority queues, helps mitigate this.

## Why Guaranteed to Find the Shortest Path

The A* algorithm is guaranteed to find the shortest path when the following conditions are met:

1. **Admissible Heuristic:** The heuristic function (Manhattan Distance in this case) must be admissible, meaning it never overestimates the cost to reach the goal.

2. **Consistent Heuristic:** The heuristic must be consistent, which means the estimated cost from a node to its successor, plus the cost of reaching that successor, is less than or equal to the estimated cost from the initial node to the successor.

These conditions ensure that A* explores the state space in an efficient manner, prioritizing paths that are likely to lead to the optimal solution.

## Usage

To use the 8-puzzle solver, modify the `main` function in `8PuzzleSolver.cpp` with your desired initial state, and run the program. The solution path and its length will be displayed.

```cpp
int main() {
    vi result = PuzzleSolver(385742160);
    for (int i : result) cout << i << endl;
    cout << endl << "Length of the solution path: " << result.size() << endl;
    return 0;
}
```

Feel free to explore different initial states by changing the argument passed to `PuzzleSolver`.

## Contributions

Contributions and improvements to the code are welcome. Feel free to open issues or submit pull requests.

## Acknowledgments

This implementation is a demonstration of the A* algorithm applied to the 8-puzzle problem and is based on well-established principles in artificial intelligence and search algorithms.
