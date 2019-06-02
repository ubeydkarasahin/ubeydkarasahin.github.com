public class Cell {
    int hCost = 0; //Heuristic cost
    int fCost = 0; //G+H Final cost
    int i, j;
    Cell parent;

    Cell(int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString(){
        return "["+this.i+", "+this.j+"]";
    }
}
