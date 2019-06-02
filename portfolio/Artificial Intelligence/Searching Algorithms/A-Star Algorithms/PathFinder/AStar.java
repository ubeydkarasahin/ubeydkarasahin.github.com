import java.util.PriorityQueue;

public class AStar {
    public static final int DIAGONAL_COST = 14; // Cost of going diagonally
    public static final int V_H_COST = 10; // Cost of going vertically and horizontally
    static Cell [][] grid = new Cell[5][5]; //Blocked cells are just null Cell values in grid
    static PriorityQueue<Cell> open;
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }

    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    static void cuCost(Cell current, Cell t, int cost){ //Checks and updates the code
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.hCost+cost;
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.fCost){
            t.fCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }

    public static void AStar(){
        open.add(grid[startI][startJ]); //add the start location to open list.
        Cell current;

        while(true){
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true;
            if(current.equals(grid[endI][endJ])){
                return;
            }
            Cell t;
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                cuCost(current, t, current.fCost+V_H_COST);
                if(current.j-1>=0){
                    t = grid[current.i-1][current.j-1];
                    cuCost(current, t, current.fCost+DIAGONAL_COST);
                }
                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    cuCost(current, t, current.fCost+DIAGONAL_COST);
                }
            }
            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                cuCost(current, t, current.fCost+V_H_COST);
            }
            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                cuCost(current, t, current.fCost+V_H_COST);
            }
            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                cuCost(current, t, current.fCost+V_H_COST);
                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    cuCost(current, t, current.fCost+DIAGONAL_COST);
                }
                if(current.j+1<grid[0].length){
                    t = grid[current.i+1][current.j+1];
                    cuCost(current, t, current.fCost+DIAGONAL_COST);
                }
            }
        }
    }

    public static void test(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
        System.out.println("\n\nTesting Case #"+tCase);
        grid = new Cell[x][y];         //Reset
        closed = new boolean[x][y];
        open = new PriorityQueue<>(AStar::compare);
        setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
        setEndCell(ei, ej);         //Set End Location

        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                grid[i][j] = new Cell(i, j);
                grid[i][j].hCost = Math.abs(i-endI)+Math.abs(j-endJ);
            }
        }
        grid[si][sj].fCost = 0;

        for(int i=0;i<blocked.length;++i){ //Set blocked cells. Simply set the cell values to null for blocked cells.
            setBlocked(blocked[i][0], blocked[i][1]);
        }

        System.out.println("Grid: "); //Display initial map
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                if(i==si&&j==sj)System.out.print("SO  "); //Source
                else if(i==ei && j==ej)System.out.print("DE  ");  //Destination
                else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();

        AStar();
        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].fCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();

        if(closed[endI][endJ]){
            System.out.println("Path: ");
            Cell current = grid[endI][endJ]; //Trace back the path
            System.out.print(current);
            while(current.parent!=null){
                System.out.print(" -> "+current.parent);
                current = current.parent;
            }
            System.out.println();
        }else System.out.println("No possible path");
    }

    private static int compare(Object o1, Object o2) {
        Cell c1 = (Cell) o1;
        Cell c2 = (Cell) o2;
        return Integer.compare(c1.fCost, c2.fCost);
    }
}
