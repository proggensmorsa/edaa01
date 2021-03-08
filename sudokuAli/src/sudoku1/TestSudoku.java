package sudoku1;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSudoku {
	private SudokuSolverClass s;

	@BeforeEach
	void setUp() {
		s = new SudokuSolverClass();
	}

	@AfterEach
	void tearDown() {
	}

	/**
	 * Test if solve() returnes False when getting an impossible sudoku
	 */
	@Test
	void testUnsolveable() {
		int[][] impossible = { { 0, 8, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		s.setMatrix(impossible);
		assertFalse(s.solve());
	}

	/**
	 * Test if solve() gives the correct solution for the sudoku specefied below
	 * (the sudoku from the course webpage) Also tests that getMatrix() and
	 * setMatrix() works as intended
	 */
	@Test
	void testManualSudoku() {

		int[][] manualSudoku = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
				{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 3, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };
		s.setMatrix(manualSudoku);

		int[][] manualSol = { { 5, 4, 8, 1, 7, 9, 3, 6, 2 }, { 3, 7, 6, 8, 2, 4, 9, 1, 5 },
				{ 1, 9, 2, 5, 6, 3, 8, 7, 4 }, { 7, 8, 4, 2, 1, 6, 5, 9, 3 }, { 2, 5, 9, 3, 8, 7, 6, 4, 1 },
				{ 6, 3, 1, 9, 4, 5, 7, 2, 8 }, { 4, 1, 5, 6, 9, 8, 2, 3, 7 }, { 8, 6, 7, 4, 3, 2, 1, 5, 9 },
				{ 9, 2, 3, 7, 5, 1, 4, 8, 6 } };
		s.solve();
		int[][] mySudokuSol = s.getMatrix();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// Checks that every value exactly the same as the correct solution.
				assertTrue(manualSol[i][j] == mySudokuSol[i][j], "Fel då vi stoppar in matrisen från manualen");

			}
		}
		// Checks so that true is returned.
		assertTrue(s.solve());

	}

	/**
	 * Test if it is possible for solve() to solve an empty sudoku.
	 */
	@Test
	void testEmptySudoku() {

		assertTrue(s.solve());

	}

	/**
	 * Test if IllegalArgumentException is thrown when inputing unallowed numbers
	 */
	@Test
	void testsUnAllowedNumber() {
		assertThrows(IllegalArgumentException.class, () -> s.setNumber(1, 1, -1));
	}

	/**
	 * Test if IllegalArgumentException is thrown when inputing unallowed indexs
	 */
	@Test
	void testsUnAllowedIndex() {
		assertThrows(IllegalArgumentException.class, () -> s.setNumber(1, 9, 1));
	}

	/**
	 * Test if the Clear and getNumber methods work as intended
	 */
	@Test
	void testClear() {
		Random rand = new Random();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				s.setNumber(i, j, rand.nextInt(9) + 1);
			}
		}
		s.clear();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				assertEquals(0, s.getNumber(i, j));
			}
		}
	}

}
