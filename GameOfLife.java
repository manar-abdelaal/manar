package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        StdIn.setFile(file);
         int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        for (int i=0; i<r; i++){
            for (int j=0; j<c; j++){
                grid[i][j] = StdIn.readBoolean();
                if (grid[i][j]==ALIVE){
                    totalAliveCells++;
                }
            }
        }
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        if(totalAliveCells>0){
            return true;
        }else{
            return false;
        }  // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        int result = 0;
        int dr[] = {-1,-1,-1,0,0,1,1,1};
        int dc[] = {-1,0,1,-1,1,-1,0,1};
        for (int i=0; i< 8;i++){
            int r = row+dr[i];
            int c = col+dc[i];
            if (r < 0) {
                r = grid.length - 1;
            } else if (r >= grid.length) {
                r = 0;
            }
            if (c < 0) {
                c = grid[0].length - 1;
            } else if (c >= grid[0].length) {
                c = 0;
            }
            if (getCellState(r, c)==ALIVE) {
                result++;
            }
            
        }
        return result; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean [][] newGrid = new boolean [rows][cols];

        for ( int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                int AliveNeighbors = numOfAliveNeighbors(i,j);

                if (grid[i][j]==ALIVE && AliveNeighbors<=1){
                    newGrid[i][j]=DEAD;
                }

                if (grid[i][j] == ALIVE && AliveNeighbors == 2){
                    newGrid[i][j] = ALIVE;
                }
                if (grid[i][j] == ALIVE && AliveNeighbors == 3) {
                    newGrid[i][j] = ALIVE;
                }

                if (grid[i][j]==DEAD && AliveNeighbors==3){
                    newGrid[i][j] = ALIVE;
                }

                if (grid[i][j]==ALIVE&& AliveNeighbors>=4){
                    newGrid[i][j]=DEAD;
                }
            }
        }

        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        boolean [][] newGrid = computeNewGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i=0; i<rows;i++){
            for (int j=0; j<cols; j++){
                grid [i][j] = newGrid[i][j];
            }
        }
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for (int i=0; i<n;i++){
            nextGeneration();
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
     WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length, grid[0].length);
     int rows = grid.length;
     int cols = grid[0].length;
     for (int i=0; i<rows;i++){
        for (int j=0;j<cols;j++){
            //System.out.println(i+" " + j);
            if (grid[i][j]==ALIVE ){
                int dr[] = {-1,-1,-1,0,0,1,1,1};
                int dc[] = {-1,0,1,-1,1,-1,0,1};
                for (int k=0; k< 8;k++){
                    int r = i+dr[k];
                    int c = j+dc[k];
                    if (r < 0) {
                        r = grid.length - 1;
                    } else if (r >= grid.length) {
                        r = 0;
                    }
                    if (c < 0) {
                        c = grid[0].length - 1;
                    } else if (c >= grid[0].length) {
                        c = 0;
                    }
                    if (getCellState(r, c)==ALIVE) {
                        uf.union(i,j,r,c);
                        //System.out.println(i+" "+ j+" " + r + " " +c);
                    }
                    
                }
                

            }
            
        }
     }
        int count=0;
        int el []= new int [rows*cols+1];
        for (int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
           int c = uf.find(i,j);
           if (c!=(i*cols+j)){
            el[c]++;
           }
            }
        }
        /*for(int i=0;i<el.length;i++){
            System.out.println(i+" " + el[i]);
        }*/
        for (int k=0; k<el.length;k++){
            if (el[k]>0){
                count++;
            }
        }


        // WRITE YOUR CODE HERE
        return count; // update this line, provided so that code compiles
    }
}
