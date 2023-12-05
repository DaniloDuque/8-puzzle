#include <bits/stdc++.h>
using namespace std;

#define vi vector<int>

unordered_set<int> visited;






// Coordinates of the objective position for each digit in the 8-puzzle
int objectiveIndices[9][2] = {{2, 2}, {0, 0}, {0, 1}, {0 ,2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}};
int allMoves[4] = {3, 1, -1, -3};  // Possible moves: right, down, left, up
int movesMod3[3] = {3, -1, -3};    // Moves for the rightmost column
int movesMod3Plus1[3] = {3, 1, -3}; // Moves for the leftmost column
int GOAL = 123456780;  //goal state of the puzzle












// Function to calculate base^exponent  in O(log(exponent))
int Pow(int base, int exponent) {

    if (!exponent) return 1;
    if (exponent & 1) return base * Pow(base * base, exponent >> 1);
    return Pow(base * base, exponent >> 1);
}












// Function to calculate Manhattan Distance heuristic
int manhattanDistance(int curr) {

    int man = 0;
    for (int i = 8; i; --i) {
        int* obj = objectiveIndices[(curr / Pow(10, i)) % 10];
        man += abs((8 - i) / 3 - obj[0]) + abs((8 - i) % 3 - obj[1]);

    }return man;

}











// Structure to represent a node in the search space
struct node {

    vi path;
    int zeroIndex, cost, manhattan;

    node(vi newPath, int lastMove, int newZeroIndex, int newCost)
        : path(newPath), zeroIndex(newZeroIndex), cost(newCost), manhattan(manhattanDistance(lastMove)) {
        path.push_back(lastMove);
    }
};













// Function to swap two digits in the puzzle
int swapDigits(int curr, int i, int j) {
    i = 8 - i; j = 8 - j;
    int digitI = (curr / Pow(10, i)) % 10;
    int digitJ = (curr / Pow(10, j)) % 10;
    int newNum = curr - digitI * Pow(10, i) - digitJ * Pow(10, j);
    newNum += digitI * Pow(10, j) + digitJ * Pow(10, i);
    return newNum;
}











// Check if the new zero index is valid
bool isValid(int zeroIndex) {
    return zeroIndex >= 0 && zeroIndex < 9;
}












// Choose set of moves based on the position of the zero
pair<int*, int> chooseMove(int zeroIndex) {
    if (!((zeroIndex + 1) % 3)) return make_pair(movesMod3, 3);
    if (!(zeroIndex % 3)) return make_pair(movesMod3Plus1, 3);
    return make_pair(allMoves, 4);
}











// Generate next possible moves from the current state
vector<node*> nextMoves(vi& currPath, int zeroIndex, int cost) {

    vector<node*> successors; cost++;
    pair<int*, int> moves = chooseMove(zeroIndex);

    for (int i = 0; i < moves.second; i++) {
        int newZeroIndex = zeroIndex + moves.first[i];

        if (isValid(newZeroIndex)) {

            int newMove = swapDigits(currPath.back(), newZeroIndex, zeroIndex);
            if (visited.find(newMove) == visited.end()) {
                successors.push_back(new node(currPath, newMove, newZeroIndex, cost));
                visited.insert(newMove);
            }
        }
    }return successors;

}
















// Find the position of the zero in the puzzle
int findZero(int start, int i = 8) {
    if (!(start % 10)) return i;
    return findZero(start / 10, i - 1);
}












// Custom comparator for the priority queue in A* search
struct compare {
    bool operator()(const node* p1, const node* p2) {
        return p1->cost + p1->manhattan > p2->cost + p2->manhattan;
    }
};













// Function to solve the 8-puzzle using A*
vi PuzzleSolver(int start) {

    priority_queue<node*, vector<node*>, compare> pq;

    pq.push(new node({}, start, findZero(start), 0));

    while (!pq.empty()) {
        node* curr = pq.top();
        pq.pop();

        int currLast = curr->path.back();

        if (currLast == GOAL) return curr->path;

        for (node*& child : nextMoves(curr->path, curr->zeroIndex, curr->cost)) pq.push(child);

    }return {};
}












// Main function to test the puzzle solver
int main() {

    vi result = PuzzleSolver(385742160);
    for (int i : result) cout << i << endl;
    cout << endl << "Length of the solution path: " << result.size() << endl;
    return 0;

}
