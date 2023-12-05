import java.util.*;

public class PuzzleSolver {

    static Set<Integer> visited;




    // Coordinates of the objective position for each digit in the 8-puzzle
    static int[][] objectiveIndices = {{2, 2}, {0, 0}, {0, 1}, {0 ,2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}};
    static int[] allMoves = {3, 1, -1, -3};  // Possible moves: right, down, left, up
    static int[] movesMod3 = {3, -1, -3};    // Moves for the rightmost column
    static int[] movesMod3Plus1 = {3, 1, -3}; // Moves for the leftmost column
    static int GOAL = 123456780;  // Goal state of the puzzle












    // Function to calculate base^exponent in O(log(exponent))
    static int pow(int base, int exponent) {
        if (exponent == 0) return 1;
        if ((exponent & 1) == 1) return base * pow(base * base, exponent >> 1);
        return pow(base * base, exponent >> 1);
    }













    // Function to calculate Manhattan Distance heuristic
    static int manhattanDistance(int curr) {
        int man = 0;
        for (int i = 8; i > 0; --i) {
            int[] obj = objectiveIndices[(curr / pow(10, i)) % 10];
            man += Math.abs((8 - i) / 3 - obj[0]) + Math.abs((8 - i) % 3 - obj[1]);
        }
        return man;
    }














    // Structure to represent a node in the search space
    static class Node {
        List<Integer> path;
        int zeroIndex, cost, manhattan;

        Node(List<Integer> newPath, int lastMove, int newZeroIndex, int newCost) {
            path = new ArrayList<>(newPath);
            zeroIndex = newZeroIndex;
            cost = newCost;
            manhattan = manhattanDistance(lastMove);
            path.add(lastMove);
        }
    }















    // Function to swap two digits in the puzzle
    static int swapDigits(int curr, int i, int j) {
        i = 8 - i;
        j = 8 - j;
        int digitI = (curr / pow(10, i)) % 10;
        int digitJ = (curr / pow(10, j)) % 10;
        int newNum = curr - digitI * pow(10, i) - digitJ * pow(10, j);
        newNum += digitI * pow(10, j) + digitJ * pow(10, i);
        return newNum;
    }













    // Check if the new zero index is valid
    static boolean isValid(int zeroIndex) {
        return zeroIndex >= 0 && zeroIndex < 9;
    }














    // Choose set of moves based on the position of the zero
    static AbstractMap.SimpleEntry<int[], Integer> chooseMove(int zeroIndex) {
        if ((zeroIndex + 1) % 3 == 0) return new AbstractMap.SimpleEntry<>(movesMod3, 3);
        if (zeroIndex % 3 == 0) return new AbstractMap.SimpleEntry<>(movesMod3Plus1, 3);
        return new AbstractMap.SimpleEntry<>(allMoves, 4);
    }













    // Generate next possible moves from the current state
    static List<Node> nextMoves(List<Integer> currPath, int zeroIndex, int cost) {
        List<Node> successors = new ArrayList<>();
        cost++;
        AbstractMap.SimpleEntry<int[], Integer> moves = chooseMove(zeroIndex);

        for (int i = 0; i < moves.getValue(); i++) {
            int newZeroIndex = zeroIndex + moves.getKey()[i];

            if (isValid(newZeroIndex)) {
                int newMove = swapDigits(currPath.get(currPath.size() - 1), newZeroIndex, zeroIndex);
                if (!visited.contains(newMove)) {
                    successors.add(new Node(currPath, newMove, newZeroIndex, cost));
                    visited.add(newMove);
                }
            }
        }
        return successors;
    }













    // Find the position of the zero in the puzzle
    static int findZero(int start, int i) {
        if (start % 10 == 0) return i;
        return findZero(start / 10, i - 1);
    }













    // Custom comparator for the priority queue in A* search
    static class NodeComparator implements Comparator<Node> {
        public int compare(Node p1, Node p2) {
            return Integer.compare(p1.cost + p1.manhattan, p2.cost + p2.manhattan);
        }
    }














    // Function to solve the 8-puzzle using A*
    static List<Integer> puzzleSolver(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        visited = new HashSet<>();

        pq.add(new Node(new ArrayList<>(), start, findZero(start, 8), 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            int currLast = curr.path.get(curr.path.size() - 1);

            if (currLast == GOAL) return curr.path;

            for (Node child : nextMoves(curr.path, curr.zeroIndex, curr.cost)) {
                pq.add(child);
            }
        }
        return new ArrayList<>();
    }













    // Main function to test the puzzle solver
    public static void main(String[] args){

        List<Integer> result = puzzleSolver(385742160);

        for (int i : result) System.out.println(i);
        
        System.out.println("\nLength of the solution path: " + result.size());

    }
}
