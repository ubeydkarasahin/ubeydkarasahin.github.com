import java.util.PriorityQueue;

public class Eight_Puzzle {
    public int dimension = 3;
    int[] row = { 1, 0, -1, 0 }; // Bottom, left, top, right
    int[] col = { 0, -1, 0, 1 }; // Bottom, left, top, right

    public int calculateCost(int[][] initial, int[][] goal) { // Calculates the moves
        int count = 0;
        int n = initial.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isSafe(int x, int y) { //Checks the movement is safe or not
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public void printGrid(Node root) {
        if (root == null) {
            return;
        }
        printGrid(root.grid);
        printMatrix(root.puzzle);
        System.out.println();
    }

    public void solve(int[][] initial, int[][] goal, int x, int y) {
        PriorityQueue<Node> pq = new PriorityQueue<>(1000, (a, b) -> (a.cost + a.level) - (b.cost + b.level));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = calculateCost(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
            if (min.cost == 0) {
                printGrid(min);
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.puzzle, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.puzzle, goal);
                    pq.add(child);
                }
            }
        }
    }
}