package sudoku1;

public class SudokuSolverClass implements SudokuSolver {
	private int[][] sudoku;

	public SudokuSolverClass() {
		sudoku = new int[9][9];
	}

	@Override
	public void setNumber(int r, int c, int nbr) {

		checkDimensions(r, c);
		checkNumber(nbr);
		sudoku[r][c] = nbr;

	}

	@Override
	public int getNumber(int r, int c) {

		checkDimensions(r, c);
		return sudoku[r][c];

	}

	@Override
	public void clearNumber(int r, int c) {
		checkDimensions(r, c);
		sudoku[r][c] = 0;

	}

	@Override
	public boolean isValid(int r, int c, int nbr) {
		checkDimensions(r, c);
		checkNumber(nbr);
		for (int i = 0; i < 9; i++) {
			if (i != c && sudoku[r][i] == nbr) { // Checks row
				return false;
			}
			if (i != r && sudoku[i][c] == nbr) { // Checks column
				return false;
			}
		}
		int rowBox = r / 3;
		int colBox = c / 3;
		for (int i = rowBox * 3; i < ((rowBox * 3) + 3); i++) {
			for (int j = colBox * 3; j < ((colBox * 3) + 3); j++) {
				if (i != r && j != c && sudoku[i][j] == nbr) { // kollar så att det går bra i boxen
					return false;
				}
			}

		}

		return true;
	}

	@Override
	public boolean isAllValid() {
		boolean a = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				a = isValid(i, j, sudoku[i][j]);
			}

		}
		return false;
	}

	@Override
	public boolean solve() {
		// TODO Auto-generated method stub //gör så som shack exempel på förel 8
		// backtracking och läs i manualen till inlämningen.
		return recSolve(0, 0);
	}

	private boolean recSolve(int r, int c) {
		if (r == 9) {
			return true;
		}

		if (sudoku[r][c] == 0) {
			for (int nbr = 1; nbr < 10; nbr++) {
				if (isValid(r, c, nbr)) {
					sudoku[r][c] = nbr;
					if (c < 8) {
						if (recSolve(r, c + 1))
							return true;

					} else {
						if (recSolve(r + 1, 0))
							return true;

					}

					sudoku[r][c] = 0;

				}
			}
			return false;

		} else {
			if (isValid(r, c, sudoku[r][c])) {
				if (c < 8) {
					return recSolve(r, c + 1);

				} else {
					return recSolve(r + 1, 0);

				}
			}
		}

		return false;
	}

	@Override
	public void clear() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				clearNumber(i, j);
			}
		}

	}

	@Override
	public int[][] getMatrix() {
		int[][] m = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				m[i][j] = sudoku[i][j];
			}
		}
		return m;
	}

	@Override
	public void setMatrix(int[][] nbrs) {
		if (nbrs.length > 9) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < nbrs.length; i++) {
			for (int j = 0; j < nbrs[i].length; j++) {
				if (nbrs[i].length > 9 || nbrs[i][j] < 0 || nbrs[i][j] > 9) {
					throw new IllegalArgumentException();
				} else {
					sudoku[i][j] = nbrs[i][j];
				}

			}
		}

	}

	/**
	 * Checks if the dimensions are correct.
	 * 
	 * @param r The row
	 * @param c The column
	 * @throws IllegalArgumentException if r or c is outside [0..8]
	 */
	private void checkDimensions(int r, int c) {
		if (r < 0 || r > 8 || c < 0 || c > 8) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Checks if the number is allowed.
	 * 
	 * @param nbr The number to insert to be checked.
	 * @throws IllegalArgumentException if nbr is outside [1..9]
	 */
	private void checkNumber(int nbr) {
		if (nbr < 1 || nbr > 9) {
			throw new IllegalArgumentException();
		}
	}

}
