import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ConnectFour implements ActionListener {
	// variable to track whose turn it is
	boolean p1turn;
	// array of button objects for the grid
	private JButton buttons[];
	private String buttonColors[];
	private JLabel title_Label;
	private JFrame game_Frame;

	public ConnectFour() {
		initialize();
	}

	public void startingPlayer() {
		// disable the buttons on the starting row.
		for (int i = 0; i < 7; i++) {
			buttons[i].setEnabled(false);
		}
		// wait 2 seconds before displaying whose turn it is so that the user sees the
		// game's title
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// randomly pick which colour goes first
		Random random = new Random();
		int color = random.nextInt(2) + 1;

		if (color == 1) {
			p1turn = true;
			title_Label.setText("Red's Turn");
		} else {
			p1turn = false;
			title_Label.setText("Yellow's Turn");
		}
		// reenable the buttons on the starting row.
		for (int i = 0; i < 7; i++) {
			buttons[i].setEnabled(true);
		}

	}
	
	public void initialize() {
		buttonColors = new String[49];
		for (int i = 0; i < buttonColors.length; i++) {
			setButtonColor(i, "dark_grey");
		}

		// create the title bar
		game_Frame = new JFrame();
		game_Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game_Frame.setSize(800, 800);
		game_Frame.setVisible(true);
		game_Frame.setLayout(new BorderLayout());

		// Creating the title bar with header text
		JPanel title_Panel = new JPanel();
		title_Panel.setBackground(Color.BLACK);
		title_Panel.setPreferredSize(new Dimension(800, 100));
		title_Label = new JLabel();
		title_Label.setFont(new Font("Arial", Font.BOLD, 75));
		title_Label.setForeground(Color.WHITE);
		title_Label.setBackground(Color.BLACK);
		title_Label.setPreferredSize(new Dimension(800, 100));
		title_Label.setText("Connect Four");
		title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		title_Panel.add(title_Label);
		game_Frame.add(title_Panel, BorderLayout.NORTH);

		// Create a new panel made up of a grid of buttons
		JPanel button_Panel = new JPanel();
		button_Panel.setLayout(new GridLayout(7, 7, 0, 0));
		button_Panel.setPreferredSize(new Dimension(800, 600));
		buttons = new JButton[49];
		for (int i = 0; i < 49; i++) {
			buttons[i] = new JButton();
			button_Panel.add(buttons[i]);
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
			buttons[i].setBackground(Color.LIGHT_GRAY);
			// make the bottom 7 x 6 buttons unclickable -> can only place at the top
			if (i > 6) {
				buttons[i].setEnabled(false);
				buttons[i].setBackground(Color.DARK_GRAY);
			}
		}
		game_Frame.add(button_Panel);
		game_Frame.pack();

		startingPlayer();

	}

	public int[] getColumnList(int i) {
		int columnNumbers[] = { i + 42, i + 35, i + 28, i + 21, i + 14, i + 7, i };
		return columnNumbers;
	}

	public String getButtonColor(int i) {
		return buttonColors[i];
	}

	public void setButtonColor(int i, String color) {
		buttonColors[i] = color;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// find out which button was pressed
		for (int i = 0; i < 7; i++) {
			if (e.getSource() == buttons[i]) {
				// get the numbers of the buttons in the column
				int[] column = getColumnList(i);
				// for each button in the column, starting at the lowest point, check the
				// colour and, if unchanged, place the user's colour there instead
				for (int i1 : column) {
					if (buttons[i1].getBackground() == Color.DARK_GRAY) {
						if (p1turn) {
							buttons[i1].setBackground(Color.RED);
							setButtonColor(i1, "red");
							// next color's turn
							p1turn = false;
							title_Label.setText("Yellow's Turn");
							// check for a winner
							winChecker();
							break;
						} else {
							buttons[i1].setBackground(Color.YELLOW);
							setButtonColor(i1, "yellow");
							// next color's turn
							p1turn = true;
							title_Label.setText("Red's Turn");
							// check for a winner
							winChecker();
							break;
						}

					}
				}
			}

		}

	}

	public void horizontalWin(String color) {
		for (int i1 = 0; i1 < 7; i1++) {
			int i2 = i1 * 7;
			if ((getButtonColor(i2 + 0) == color) && (getButtonColor(i2 + 1) == color)
					&& (getButtonColor(i2 + 2) == color) && (getButtonColor(i2 + 3) == color)) {
				winner(color, i2, i2 + 1, i2 + 2, i2 + 3);
			}
			if ((getButtonColor(i2 + 1) == color) && (getButtonColor(i2 + 2) == color)
					&& (getButtonColor(i2 + 3) == color) && (getButtonColor(i2 + 4) == color)) {
				winner(color, i2 + 1, i2 + 2, i2 + 3, i2 + 4);
			}
			if ((getButtonColor(i2 + 2) == color) && (getButtonColor(i2 + 3) == color)
					&& (getButtonColor(i2 + 4) == color) && (getButtonColor(i2 + 5) == color)) {
				winner(color, i2 + 2, i2 + 3, i2 + 4, i2 + 5);
			}
			if ((getButtonColor(i2 + 3) == color) && (getButtonColor(i2 + 4) == color)
					&& (getButtonColor(i2 + 5) == color) && (getButtonColor(i2 + 6) == color)) {
				winner(color, i2 + 3, i2 + 4, i2 + 5, i2 + 6);
			}
		}
	}

	public void VerticalWin(String color) {
		for (int i1 = 0; i1 < 7; i1++) {
			if ((getButtonColor(i1 + 7) == color) && (getButtonColor(i1 + 14) == color)
					&& (getButtonColor(i1 + 21) == color) && (getButtonColor(i1 + 28) == color)) {
				winner(color, i1 + 7, i1 + 14, i1 + 21, i1 + 28);
			}
			if ((getButtonColor(i1 + 14) == color) && (getButtonColor(i1 + 21) == color)
					&& (getButtonColor(i1 + 28) == color) && (getButtonColor(i1 + 35) == color)) {
				winner(color, i1 + 14, i1 + 21, i1 + 28, i1 + 35);
			}
			if ((getButtonColor(i1 + 21) == color) && (getButtonColor(i1 + 28) == color)
					&& (getButtonColor(i1 + 35) == color) && (getButtonColor(i1 + 42) == color)) {
				winner(color, i1 + 21, i1 + 28, i1 + 35, i1 + 42);
			}
		}
	}

	public void diagonalWin(String color) {
		for (int i1 = 0; i1 < 5; i1++) {
			// up
			if ((getButtonColor(i1 + 28) == color) && (getButtonColor(i1 + 22) == color)
					&& (getButtonColor(i1 + 16) == color) && (getButtonColor(i1 + 10) == color)) {
				winner(color, i1 + 28, i1 + 22, i1 + 16, i1 + 10);
			}
			if ((getButtonColor(i1 + 35) == color) && (getButtonColor(i1 + 29) == color)
					&& (getButtonColor(i1 + 23) == color) && (getButtonColor(i1 + 17) == color)) {
				winner(color, i1 + 35, i1 + 29, i1 + 23, i1 + 17);
			}
			if ((getButtonColor(i1 + 42) == color) && (getButtonColor(i1 + 36) == color)
					&& (getButtonColor(i1 + 30) == color) && (getButtonColor(i1 + 24) == color)) {
				winner(color, i1 + 42, i1 + 36, i1 + 30, i1 + 24);
			}
			// down
			if ((getButtonColor(i1 + 7) == color) && (getButtonColor(i1 + 15) == color)
					&& (getButtonColor(i1 + 23) == color) && (getButtonColor(i1 + 31) == color)) {
				winner(color, i1 + 7, i1 + 15, i1 + 23, i1 + 31);
			}
			if ((getButtonColor(i1 + 14) == color) && (getButtonColor(i1 + 22) == color)
					&& (getButtonColor(i1 + 30) == color) && (getButtonColor(i1 + 38) == color)) {
				winner(color, i1 + 14, i1 + 22, i1 + 30, i1 + 38);
			}
			if ((getButtonColor(i1 + 21) == color) && (getButtonColor(i1 + 29) == color)
					&& (getButtonColor(i1 + 37) == color) && (getButtonColor(i1 + 45) == color)) {
				winner(color, i1 + 21, i1 + 29, i1 + 37, i1 + 45);
			}
		}
	}

	public void winChecker() {
		for (int i = 0; i < 2; i++) {
			String color;
			if (i == 0) {
				color = "red";
			} else {
				color = "yellow";
			}
			// check horizontal wins
			horizontalWin(color);
			// check vertical wins
			VerticalWin(color);
			// check diagonal wins
			diagonalWin(color);
		}

	}

	public void winner(String color, int a, int b, int c, int d) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		buttons[d].setBackground(Color.GREEN);
		if (color == "red") {
			title_Label.setText("The winner is red");
		} else {
			title_Label.setText("The winner is yellow");
		}

		for (int i = 0; i < 7; i++) {
			buttons[i].setEnabled(false);
		}
		String[] responses = { "Play again", "Quit", "Cancel" };
		int answer = JOptionPane.showOptionDialog(null, "Do you want to Play Again?", "Play Again Prompt", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses, responses[0]);
		if (answer == 0) {
			//play again
			game_Frame.dispose();
			initialize();
		} else if(answer == 1) {
			//quit
			System.exit(0);
		} else {
			//cancel 
			
		}
	}
}
