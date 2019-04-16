package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color.*;
import java.awt.image.BufferedImage;

public class ConnectFour extends JFrame implements ActionListener{
	private final int rows = 6;
	private final int cols = 7;
	
	//connect four grid board
	private JPanel pane = new JPanel(new GridLayout(rows, cols));
	private JButton[][] gridButtons = new JButton[rows][cols];
	private int[][] grid = new int[rows][cols];
	private int pTurn = 0; //keeps track of the player turns
	private boolean win = false; //starts game with no winner
	private JButton clear = new JButton("Clear Board");
	private JLabel winner = new JLabel();
	private JLabel fullColumn = new JLabel();
	
	//intro window items
	private JLabel heading = new JLabel("    Welcome to Connect 4!    ");
	private JButton start = new JButton("Start Game");
	
	//menu bar items
	private JMenuBar mainBar = new JMenuBar();
	private JMenu option = new JMenu("Options");
	private JMenuItem exit = new JMenuItem("Exit");
	
	//images
	ImageIcon player1 = new ImageIcon("C:/Users/KailieT/Pictures/myPics/redCircle.png");
	ImageIcon player2 = new ImageIcon("C:/Users/KailieT/Pictures/myPics/yellowCircle.png");
	ImageIcon gridCircles = new ImageIcon("C:/Users/KailieT/Pictures/myPics/circle.png");
	
	Image transform1 = player1.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	Image transform2 = player2.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	Image transform3 = gridCircles.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
	
	ImageIcon player = new ImageIcon();
	ImageIcon circles = new ImageIcon();
	
	//panels for the grid window
	private JPanel northButtons = new JPanel();
	private JPanel gamePanel = new JPanel();
	
	//panel for the intro window
	private JPanel intro = new JPanel();
	
	CardLayout card = new CardLayout();
	
	public ConnectFour()
	{
		//sets up the initial window
		super("Connect Four");
		setSize(400, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(card);
		
		//sets fonts
		heading.setFont(new Font("Arial", Font.BOLD, 30));
		winner.setFont(new Font("Arial", Font.BOLD, 16));
		fullColumn.setFont(new Font("Arial", Font.BOLD, 16));
		
		//sets menu bar
		setJMenuBar(mainBar);
		mainBar.add(option);
		option.add(exit);
		
		//intro panel
		intro.setLayout(new FlowLayout());
		intro.setBackground(Color.PINK);
		intro.add(heading);
		intro.add(start);
		add(intro, "Panel1");
		
		//grid panel
		//sets up grid board
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				gridButtons[i][j] = new JButton();
				circles = new ImageIcon(transform3);
				gridButtons[i][j].setIcon(circles);
				gridButtons[i][j].addActionListener(this);
				pane.add(gridButtons[i][j]);
			}
		}
		
		//SOURCE CODE: David Tan, https://www.dreamincode.net/forums/topic/234693-connect-four-gui/
		//-1 are empty slots(nothing under them) and make the pieces not allowed to go in
		//0 are empty slots that can be filled in(has a piece or pieces under them)
		//1 for player 1
		//2 for player 2
		for(int i = rows - 2; i >= 0; i--)
		{
			for(int j = cols - 1; j >= 0; j--)
			{
				grid[i][j] = -1; //starts game on the bottom row
								//makes the spots that are 2+ spaces above the player 
								//piece or spots above empty bottom row unable to be filled in 
			}
		}
		
		//sets the north buttons in grid panel
		northButtons.setLayout(new FlowLayout());
		northButtons.add(winner);
		northButtons.add(clear);
		northButtons.add(fullColumn);
		
		//grid panel layout
		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(pane, BorderLayout.CENTER);
		gamePanel.add(northButtons, BorderLayout.NORTH);
		add(gamePanel, "Panel2");
		
		//button listeners
		clear.addActionListener(this);
		start.addActionListener(this);
		exit.addActionListener(this);
	}

	//SOURCE CODE: David Tan, https://www.dreamincode.net/forums/topic/234693-connect-four-gui/
	//checkWin function checks the possible wins for each player
	public boolean checkWin()
	{
		//check for horizontal win
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(grid[i][j] != 0 && grid[i][j] != -1 && grid[i][j] == grid[i][j+1] &&
						grid[i][j] == grid[i][j+2] && grid[i][j] == grid[i][j+3])
				{
					win = true;
				}
			}
		}
		//check for vertical win
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				if(grid[i][j] != 0 && grid[i][j] != -1 && grid[i][j] == grid[i+1][j] &&
						grid[i][j] == grid[i+2][j] && grid[i][j] == grid[i+3][j])
				{
					win = true;
				}
			}
		}
		//check for negative slope diagonal win
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(grid[i][j] != 0 && grid[i][j] != -1 && grid[i][j] == grid[i+1][j+1] &&
						grid[i][j] == grid[i+2][j+2] && grid[i][j] == grid[i+3][j+3])
				{
					win = true;
				}
			}
		}
		//check for positive slope diagonal win
		for(int i = 3; i < 6; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(grid[i][j] != 0 && grid[i][j] != -1 && grid[i][j] == grid[i-1][j+1] &&
						grid[i][j] == grid[i-2][j+2] && grid[i][j] == grid[i-3][j+3])
				{
					win = true;
				}
			}
		}
		
		return win;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source == start)
		{
			//changes to the grid panel when start button is pressed
			setSize(500, 500);
			setLocationRelativeTo(null);
			card.show(getContentPane(), "Panel2");
		}
		
		//SOURCE CODE: David Tan, https://www.dreamincode.net/forums/topic/234693-connect-four-gui/
		for(int i = rows-1; i >= 0; i--)
		{
			for(int j = cols-1; j >= 0; j--)
			{
				if(source == gridButtons[i][j]) //if any of the grid buttons are pressed
				{
					//checks for player 1
					if(pTurn % 2 == 0 && grid[i][j] == 0)
					{
						player = new ImageIcon(transform1);
						gridButtons[i][j].setIcon(player);
						grid[i][j] = 1; //makes the grid box occupied by player 1
						
						try//checks if the upper row box is empty and has piece(s) under
						{
							grid[i - 1][j] = 0;
							fullColumn.setText("");
						}
						catch(ArrayIndexOutOfBoundsException a) //if it is not empty
						{
							fullColumn.setText("The column is full");
						}
						
						if(checkWin()) //if win is true for player 1
						{
							winner.setText("PLAYER 1 WINS!");
							for(int x = rows - 1; x >= 0; x--)
							{
								for(int y = cols - 1; y >= 0; y--)
								{
									grid[x][y] = -1; //stops the game
								}
							}
						}
						
						pTurn = pTurn+1; //switches player pieces
						break;
					}
					//checks for player 2
					if(pTurn % 2 == 1 && grid[i][j] == 0)
					{
						player = new ImageIcon(transform2);
						gridButtons[i][j].setIcon(player);
						grid[i][j] = 2; //makes grid box occupied by player 2
						
						try 
						{
							grid[i - 1][j] = 0;
							fullColumn.setText("");
						}
						catch(ArrayIndexOutOfBoundsException a)
						{
							fullColumn.setText("The column is full");
						}
						
						if(checkWin()) //if win is true for player 2
						{
							winner.setText("PLAYER 2 WINS!");
							for(int x = rows - 1; x >= 0; x--)
							{
								for(int y = cols - 1; y >= 0; y--)
								{
									grid[x][y] = -1; //stops the game
								}
							}
						}
						
						pTurn = pTurn + 1;
						break;
					}
				}
			}
		}
		
		//if exit is pressed
		if(source == exit)
		{
			System.exit(0);
		}
		
		//if the clear board button is pressed
		if(source == clear)
		{
			for(int i = rows - 1; i >= 0; i--)
			{
				for(int j = cols - 1; j >= 0; j--)
				{
					grid[i][j] = -1; //makes all other boxes unable to be filled
					gridButtons[i][j].setIcon(circles);
				}
			}
			for(int j = cols - 1; j >= 0; j--)
			{
				grid[5][j] = 0; //makes bottom row boxes able to be filled in
			}
			fullColumn.setText("");
			winner.setText("");
			win = false; //resets the winner 
		}
	}
}
