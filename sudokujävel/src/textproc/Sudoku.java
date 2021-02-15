package textproc;

import java.util.stream.IntStream;

public class Sudoku implements SudokuSolver {
	private int[][] board = null;
	private int r;
	private int c;
	
	public Sudoku() {
	board = new int [getDimension()][getDimension()];
	}

	@Override
	public void setNumber(int r, int c, int nbr) {
		board[r][c] = nbr;
	}

	@Override
	public int getNumber(int r, int c) {
		return board[r][c];
		
	}

	@Override
	public void clearNumber(int r, int c) {
		board[r][c] = 0;
	}

	private boolean rowOk(int[][] board, int r) {
	    boolean[] constraint = new boolean[8];
	    return IntStream.range(0, 8)
	      .allMatch(c -> checkOk(board, r, constraint, c));
	}
	
	private boolean ColumnOk(int[][] board, int c) {
	    boolean[] constraint = new boolean[8];
	    return IntStream.range(0, 8)
	      .allMatch(r -> checkOk(board, r, constraint, c));
	}
	private boolean subBoardOk(int[][] board, int r, int c) {
	    boolean[] constraint = new boolean[8];
	    int subsectionRowStart = r;
	    int subsectionRowEnd = r + 2;

	    int subsectionColumnStart = c;
	    int subsectionColumnEnd = c+2;

	    for (int r2 = subsectionRowStart; r2 < subsectionRowEnd; r++) {
	        for (int c2 = subsectionColumnStart; c2 < subsectionColumnEnd; c++) {
	            if (!checkOk(board, r2, constraint, c2)) return false;
	        }
	    }
	    return true;
	}

	private boolean checkOk(int[][] board, int row, boolean[] constraint, int column){
			    if (board[row][column] != 0) {
			        if (!constraint[board[row][column] - 1]) {
			            constraint[board[row][column] - 1] = true;
			        } else {
			            return false;
			        }
			    }
			    return true;
			}
	
	@Override

	public boolean isValid(int[][]board, int r, int c) {
		return (ColumnOk(board, r) && subBoardOk(board, r, c) && rowOk(board, c));
	}
	
	
	@Override
	public boolean isAllValid() {
		for (int m = 0; m < 9 ; m++) {
			for (int n = 0; n < 9; n++){
				int nbr = board[m][n];
				if(!isValid(m,n,nbr)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean solve() {
		return solve(0,0);
	}
	private boolean solve (int r, int c) {
		for (int row = 0; row<9; row++) {
			for (int col = 0; col<9; col++) {
				if (board[row][col] == 0) {
					for (int num = 1; num<=9; num++) {
						if (isValid(board, row, col)) {
							board[row][col] = num;
							
						if (solve()) {
							return true;
						} else {
							board[row][col] = 0;
						}
						}
					}
				return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public void clear() {
		board = null;
	}

	@Override
	public int[][] getMatrix() {
		return board;
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		board = nbrs;
	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		// TODO Auto-generated method stub
		return false;
	}


}