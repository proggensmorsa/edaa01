 package sudoku1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.text.AttributeSet;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;

public class Controller {
	JTextField[][] textGrid;
	SudokuSolverClass solver;

	public Controller() {
		SwingUtilities.invokeLater(() -> createSudoku("Sudoku", 585, 635));
	}

	private void createSudoku(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		Container pane = frame.getContentPane(); // Creates the Jframe and the container pane.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		solver = new SudokuSolverClass();

		JPanel buttonPanel = new JPanel(); // Creates Jpanel for the buttons with size(100,50) and light gray color.
		buttonPanel.setPreferredSize(new Dimension(0, 50));
		buttonPanel.setBackground(new Color(204, 204, 204));

		JButton clearButton = new JButton("Clear"); // creates the clear button and adds its action
		clearButton.addActionListener(event -> {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {

					textGrid[i][j].setText("");
					solver.clear();
				}
			}
		});
		JButton solveButton = new JButton("Solve");// creates the solve button and adds its action
		solveButton.addActionListener(event -> {
			for (int i = 0; i < textGrid.length; i++) {
				for (int j = 0; j < textGrid[i].length; j++) {
					if (!textGrid[i][j].getText().equals("")) {
						solver.setNumber(i, j, Integer.parseInt(textGrid[i][j].getText()));
					}

				}
			}
			if (solver.solve()) {
				for (int i = 0; i < textGrid.length; i++) {
					for (int j = 0; j < textGrid[i].length; j++) {
						textGrid[i][j].setText(String.valueOf(solver.getNumber(i, j)));
					}
				}
			} else {
				JOptionPane m = new JOptionPane();
				clearButton.getActionListeners()[0].actionPerformed(event);
				m.showMessageDialog(null, "Finns inte en lösning!");

			}

		});
		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(400, 550)); // Creates a grid for the 9x9 pattern
		gridPanel.setLayout(new GridLayout(9, 9));
		textGrid = new JTextField[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				textGrid[i][j] = new JTextField();
				textGrid[i][j].setDocument(new OneDigitLimit());
				textGrid[i][j].setPreferredSize(new Dimension(10, 10));
				textGrid[i][j].setFont(textGrid[i][j].getFont().deriveFont(60.0f));
				if (((0 <= i && i < 3) && (0 <= j && j < 3)) || ((6 <= i && i < 9) && (0 <= j && j < 3))
						|| ((0 <= i && i < 3) && (6 <= j && j < 9)) || ((6 <= i && i < 9) && (6 <= j && j < 9))
						|| ((3 <= i && i < 6) && (3 <= j && j < 6))) {

					textGrid[i][j].setBackground(new Color(180,255 , 180));
				}

				gridPanel.add(textGrid[i][j]);

			}
		}

		buttonPanel.add(clearButton);
		buttonPanel.add(solveButton);

		pane.add(gridPanel);

		// pane.add(gridPanel,BorderLayout.NORTH);
		pane.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	private class OneDigitLimit extends PlainDocument {

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

			if (str == null)
				return;
			if ((getLength() + str.length()) <= 1 && Character.isDigit(str.charAt(0)) && str.charAt(0) != '0') {
				super.insertString(offset, str, attr);
			}
		}
	}

}
