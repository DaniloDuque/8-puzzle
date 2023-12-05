from queue import PriorityQueue

# Coordinates of the objective position for each digit in the 8-puzzle
objective_indices = [[2, 2], [0, 0], [0, 1], [0, 2], [1, 0], [1, 1], [1, 2], [2, 0], [2, 1]]
all_moves = [3, 1, -1, -3]  # Possible moves: right, down, left, up
moves_mod_3 = [3, -1, -3]    # Moves for the rightmost column
moves_mod_3_plus_1 = [3, 1, -3]  # Moves for the leftmost column
GOAL = 123456780  # Goal state of the puzzle








# Function to calculate base^exponent in O(log(exponent))
def power(base, exponent):
    if not exponent:
        return 1
    if exponent & 1:
        return base * power(base * base, exponent >> 1)
    return power(base * base, exponent >> 1)








# Function to calculate Manhattan Distance heuristic
def manhattan_distance(curr):
    man = 0
    for i in range(8, 0, -1):
        obj = objective_indices[(curr // power(10, i)) % 10]
        man += abs((8 - i) // 3 - obj[0]) + abs((8 - i) % 3 - obj[1])
    return man





        
        
        
        
class Node:
    
    def __init__(self, path, last_move, zero_index, cost):
        self.path = path
        self.zero_index = zero_index
        self.cost = cost
        self.manhattan = manhattan_distance(last_move)
        self.path.append(last_move)

    def __lt__(self, other):
        # Define the comparison for the priority queue
        return (self.cost + self.manhattan) < (other.cost + other.manhattan)














# Function to swap two digits in the puzzle
def swap_digits(curr, i, j):
    i = 8 - i
    j = 8 - j
    digit_i = (curr // power(10, i)) % 10
    digit_j = (curr // power(10, j)) % 10
    new_num = curr - digit_i * power(10, i) - digit_j * power(10, j)
    new_num += digit_i * power(10, j) + digit_j * power(10, i)
    return new_num













# Check if the new zero index is valid
def is_valid(zero_index):
    return 0 <= zero_index < 9












# Choose set of moves based on the position of the zero
def choose_move(zero_index):
    if (zero_index + 1) % 3 == 0:
        return moves_mod_3
    if zero_index % 3 == 0:
        return moves_mod_3_plus_1
    return all_moves















# Generate next possible moves from the current state
def next_moves(curr_path, zero_index, cost):
    successors = []
    cost += 1
    moves = choose_move(zero_index)

    for i in moves:
        new_zero_index = zero_index + i

        if is_valid(new_zero_index):
            new_move = swap_digits(curr_path[-1], new_zero_index, zero_index)
            if new_move not in visited:
                successors.append(Node(curr_path.copy(), new_move, new_zero_index, cost))
                visited.add(new_move)

    return successors

















# Find the position of the zero in the puzzle
def find_zero(start, i=8):
    if start % 10 == 0:
        return i
    return find_zero(start // 10, i - 1)















# Function to solve the 8-puzzle using A*
def puzzle_solver(start):
    
    
    pq = PriorityQueue()
    pq.put(Node([], start, find_zero(start), 0))

    while not pq.empty():
        curr = pq.get()
        curr_last = curr.path[-1]

        if curr_last == GOAL:
            return curr.path

        for child in next_moves(curr.path, curr.zero_index, curr.cost):
            pq.put(child)

    return []












visited = set()
result = puzzle_solver(385742160)
for i in result:
    print(i)
print("\nLength of the solution path:", len(result))
