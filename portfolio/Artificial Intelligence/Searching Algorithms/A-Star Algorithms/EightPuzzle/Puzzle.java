public class Puzzle {

    public static void main(String[] args) {

        int[][] initial = { {2, 8, 3}, {1, 6, 4}, {7, 0, 5} };

        int[][] goal = { {1, 2, 3}, {8, 0, 4}, {7, 6, 5} };

// White tile coordinate

        int x = 2, y = 1;

        Eight_Puzzle puzzle = new Eight_Puzzle();

        puzzle.solve(initial, goal, x, y);
    }
}
