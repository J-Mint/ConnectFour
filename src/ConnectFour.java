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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ConnectFour implements ActionListener {
	// variable to track whose turn it is
	boolean p1turn;
	// array of button objects for the grid
	private JButton buttons[];

	private JLabel title_Label;

	public ConnectFour() {
		// create the title bar
		JFrame game_Frame = new JFrame();
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
	
	public void startingPlayer(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random random = new Random();
		int player = random.nextInt(2)+1;
		if (player == 1) {
			p1turn = true;
			title_Label.setText("Red's turn");
		} else {
			p1turn = false;
			title_Label.setText("Yellow's turn");
		}
		
	}

	public int[] getColumnList(int i) {
		int columnNumbers[] = {i+42, i+35, i+28, i+21, i+14, i+7, i}; 
		return columnNumbers;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//find out which button was pressed
		for (int i = 0; i < 7; i++) {
			if (e.getSource()== buttons[i]) {
				//get the numbers of the buttons in the column
				int[] column = getColumnList(i);
				//for each button in the column, starting at the lowest point, check the colour, if unchanged, place the user's colour there instead
				for (int i1 : column) {
					if (buttons[i1].getBackground() == Color.DARK_GRAY) {
						if (p1turn) {
							buttons[i1].setBackground(Color.RED);
							p1turn = false;
							break;
						} else {
							buttons[i1].setBackground(Color.YELLOW);
							p1turn= true;
							break;
						}
						
					}
				}
			}
			
			//go through the column numbers
			//find the lowest vertical button and change it's color to match the user
		}
		
		
		// We only need to listen out for the first row of buttons
		//but we need to check that the column has space for a 
	}
}
