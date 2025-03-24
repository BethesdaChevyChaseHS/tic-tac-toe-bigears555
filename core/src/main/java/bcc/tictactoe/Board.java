
package bcc.tictactoe;
public class Board {
    private Mark[][] grid;
    public Board() {
        //initialize grid to be 3x3 
        grid = new Mark[3][3];
        reset();
    }

    public void reset() {
        //should restart the game - set all cells to empty
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grid[i][j] = Mark.EMPTY;
            }
        }

    }

    public boolean makeMove(Move move, Mark mark) {//make move on the grid
        
        return makeMove(move.row, move.col, mark) ;
    }

    public boolean makeMove(int row, int col, Mark mark) {
        //make a move on the grid
        if(grid[row][col] == Mark.EMPTY) {
            grid[row][col] = mark;
            return true;
        }
        else {
            return false;
        }
    }

    public void clearCell(int row, int col) {
       grid[row][col] = Mark.EMPTY;
    }
    public boolean isFull() {
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[0].length; col++) {
            if(grid[row][col] == Mark.EMPTY) {
                return false;
            }
            }
           }
           return true;
    }

    public Mark[][] getGrid() {
        return grid;
    }

    /**
     * return 'Mark.X' if X wins, 'Mark.O' if O wins, 'Mark.Tie' if tie, or 'null' if still in progress
     */
    public Mark checkWin() {//return null if game not over
        // Check rows

        // X's Rows
        if(grid[0][0] == Mark.X && grid[0][1] == Mark.X && grid[0][2] == Mark.X) {
            return Mark.X;
        }
        else if(grid[1][0] == Mark.X && grid[1][1] == Mark.X && grid[1][2] == Mark.X) {
            return Mark.X;
        }
        else if(grid[2][0] == Mark.X && grid[2][1] == Mark.X && grid[2][2] == Mark.X) {
            return Mark.X;
        }

        // O's rows
        else if(grid[0][0] == Mark.O && grid[0][1] == Mark.O && grid[0][2] == Mark.O) {
            return Mark.O;
        }
        else if(grid[1][0] == Mark.O && grid[1][1] == Mark.O && grid[1][2] == Mark.O) {
            return Mark.O;
        }
        else if(grid[2][0] == Mark.O && grid[2][1] == Mark.O && grid[2][2] == Mark.O) {
            return Mark.O;
        }
       
        // Check columns

         // X's Columns
        else if(grid[0][0] == Mark.X && grid[1][0] == Mark.X && grid[2][0] == Mark.X) {
            return Mark.X;
        }
        else if(grid[0][1] == Mark.X && grid[1][1] == Mark.X && grid[2][1] == Mark.X) {
            return Mark.X;
        }
        else if(grid[0][2] == Mark.X && grid[1][2] == Mark.X && grid[2][2] == Mark.X) {
            return Mark.X;
        }

        // O's Columns
        else if(grid[0][0] == Mark.O && grid[1][0] == Mark.O && grid[2][0] == Mark.O) {
            return Mark.O;
        }
        else if(grid[0][1] == Mark.O && grid[1][1] == Mark.O && grid[2][1] == Mark.O) {
            return Mark.O;
        }
        else if(grid[0][2] == Mark.O && grid[1][2] == Mark.O && grid[2][2] == Mark.O) {
            return Mark.O;
        }
       

       
        // Check diagonals
        else if(grid[0][0] == Mark.X && grid[1][1] == Mark.X && grid[2][2] == Mark.X) {
            return Mark.X;
        }

        else if(grid[0][0] == Mark.O && grid[1][1] == Mark.O && grid[2][2] == Mark.O) {
            return Mark.O;
        }

        else if(grid[2][0] == Mark.X && grid[1][1] == Mark.X && grid[0][2] == Mark.X) {
            return Mark.X;
        }

        else if(grid[2][0] == Mark.O && grid[1][1] == Mark.O && grid[0][2] == Mark.O) {
            return Mark.O;
        }

        else if(isFull() == true) {
            return Mark.TIE;
        }
        // Check tie
        else {
            return null;
        }
     // Game not over
    }

    public Board clone() {
       return null;
    }
}