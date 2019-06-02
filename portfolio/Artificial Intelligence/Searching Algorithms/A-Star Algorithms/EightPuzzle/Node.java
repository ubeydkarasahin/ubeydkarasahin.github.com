public class Node {
    public Node grid;
    public int[][] puzzle;
    public int x, y; // The cordinate of 0
    public int cost; // Number of misplaced boxes
    public int level; // The number of moves so far

    public Node(int[][] matrix, int x, int y, int newX, int newY, int level, Node parent) {
        this.grid = parent;
        this.puzzle = new int[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {

            this.puzzle[i] = matrix[i].clone();
        }

// Swapping the values
        this.puzzle[x][y] = this.puzzle[x][y] + this.puzzle[newX][newY];
        this.puzzle[newX][newY] = this.puzzle[x][y] - this.puzzle[newX][newY];
        this.puzzle[x][y] = this.puzzle[x][y] - this.puzzle[newX][newY];
        this.cost = Integer.MAX_VALUE;
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}
