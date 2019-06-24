package TicTacToe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements ActionListener {
	public static int BOARD_SIZE = 3;

	public static enum GameStatus {
		// Various status in game are
		Incomplete, XWins, ZWins, Tie
	}

	// In-built class of java Swing framework
	// Which is used to implement functionalities
	// of a button.
	// 3x3 matrix of type JBUTTON and at each index,a button
	// will be present.
	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
	boolean crossTurn = true;

	public Game() {
		// Setting title for the frame
		super.setTitle("TicTacToe");
		// Setting width and height of the component
		super.setSize(800, 800);
		// Layout of type grid (3x3) on that frame
		GridLayout grid = new GridLayout(3, 3);
		// Setting above layout on the frame
		super.setLayout(grid);
		// Setting frame not to resize
		// Creating a new Font
		// 1-> bold
		// 150 -> font-size
		Font font = new Font("Comic Sans", 1, 150);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				// Text on every button -> ""
				JButton button = new JButton("");
				// Initializing each button element
				buttons[row][col] = button;
				// Setting font for each buttom
				button.setFont(font);
				// Attaching event listener to every button
				button.addActionListener(this);

				// Adding button to frame(each grid)
				super.add(button);
			}
		}
		super.setResizable(false);
		// Make the frame visible on desktop
		super.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handling the event

		// Finding out which button is clicked
		// ANd storing its reference in clickedButton variable
		JButton clickedButton = (JButton) e.getSource();
		makeMove(clickedButton);
		// After Making move, we need to check whether
		// someone has won game or not
		// Checking status of game
		GameStatus gs = this.getGameStatus();
		if(gs == GameStatus.Incomplete)
		{
			return;
		}
		declareWinner(gs);
	}

	private void makeMove(JButton clickedButton) {
		String btnText = clickedButton.getText();
		if (btnText.length() > 0) {
			// We need to display a message
			// Invalid move
			// Using class JOptionPane
			JOptionPane.showMessageDialog(this, "Invalid Move");
		} else {
			// Checking whose turn It was
			if (crossTurn) {
				clickedButton.setText("X");

			} else {
				clickedButton.setText("0");
			}
			crossTurn = !crossTurn;
		}
	}

	private GameStatus getGameStatus() {
		String text1 = "";
		String text2 = "";
		int row = 0;
		int col = 0;
		// Check for text inside rows whether someone
		// has won the game or not
		while(row<BOARD_SIZE)
		{
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (!text1.equals(text2) || !(text1.length() > 0)) {
					break;
				}
				col++;
			}
			// If all values are non empty and same values
			// along the row.
			if(col == BOARD_SIZE-1)
			{
				if(text1.equals("X"))
				{
					return GameStatus.XWins;
				}
				else
				{
					return GameStatus.ZWins;
				}
			}
		row++;
		}
		col = 0;
		row = 0;
		// Checking text along columns whether someOne has won or not
		while(col<BOARD_SIZE)
		{
			while(row<BOARD_SIZE-1)
			{
				text1 = buttons[row][col].getText();
				text2 = buttons[row+1][col].getText();
				// Loop will continue only if values are same
				// and non-empty
				if(!text1.equals(text2) || !(text1.length() > 0))
				{
					break;
				}
				row++;
			}
			if(row == BOARD_SIZE-1)
			{
				if(text1.equals("X"))
				{
					return GameStatus.XWins;
				}
				else
				{
					return GameStatus.ZWins;
				}
			}
			// Making row 0 for next loop
			row = 0;
			col++;
		}
		
		// Checking text along Diagonal 1
		row = 0;
		col = 0;
		while(row<BOARD_SIZE-1 && col<BOARD_SIZE-1)
		{
			text1 = buttons[row][col].getText();
			text2 = buttons[row+1][col+1].getText();
			if(!text1.equals(text2) || !(text1.length() > 0))
			{
				break;
			}
			row++;
			col++;
		}
		if(row == (BOARD_SIZE-1) && col == (BOARD_SIZE-1))
		{
			if(text1.equals("X"))
			{
				return GameStatus.XWins;
			}
			else
			{
				return GameStatus.ZWins;
			}
		}
		// Checking for Diagonal 2
		row = (0);
		col = (BOARD_SIZE-1);
		while(row < (BOARD_SIZE-1) && col > 0)
		{
			text1 = buttons[row][col].getText();
			text2 = buttons[row+1][col-1].getText();
			if(!text1.equals(text2) || !(text1.length()>0))
			{
				break;
			}
			row++;
			col--;
		}
		if(row == (BOARD_SIZE-1) && col == 0)
		{
			if(text1.equals("X"))
			{
				return GameStatus.XWins;
			}
			else
			{
				return GameStatus.ZWins;
			}
		}
		
		// Checking for incomplete
		String str = "";
		// Running loop for each row
		for(row = 0;row<BOARD_SIZE;row++)
		{
			// Traversing through each Column
			for(col=0;col<BOARD_SIZE;col++)
			{
				str = buttons[row][col].getText();
				if(str.length() == 0)
				{
					return GameStatus.Incomplete;
				}
			}
		}
		// If not incomplete and no one wins,
		// then tie
		return GameStatus.Tie;	
	}
	private void declareWinner(GameStatus gs) {
		if(gs == GameStatus.XWins)
		{
			JOptionPane.showMessageDialog(this, "X Wins!");
		}else if(gs == GameStatus.ZWins)
		{
			JOptionPane.showMessageDialog(this, "Zero Wins!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "It's a tie BITCHH!!!");
		}
		// Message dialog displays a message
		// While confirmation dialog returns the
		// choice made by user.
		// We will ask user whether to play again or not.
		int choice = JOptionPane.showConfirmDialog(this, "Play Again ");
		if(choice == JOptionPane.YES_OPTION)
		{
			// Setting entire buttons matrix to zero length
			for(int row = 0;row<BOARD_SIZE;row++)
			{
				for(int col = 0;col<BOARD_SIZE;col++)
				{
					buttons[row][col].setText("");
				}
			}
			crossTurn = true;
		}
		else
		{
			// Terminate the frame and program
			super.dispose();
		}
	}
}
