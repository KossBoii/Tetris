import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class TetrisPanel extends JPanel implements ActionListener{
	// Current Block goes down
	public static Shape currentShape;
	public static int[] nextShapes;

	// Color array to hold the color for the whole grid
	public static Color[][] colorArr;

	// Variable to check if the game is finished or not
	public static boolean finished;
	public static boolean paused;
	public static int score = 0;
	public static int lineCount = 0;

	// Holding the JFrame information
	public static JFrame tetris;
	public Timer clock = new Timer(200, this);

	// UI components:
	private JLabel currentScore = new JLabel();
	private JLabel lineCountLabel = new JLabel();
	private JButton pauseBtn;

	/**
	 * Reset the color of the the board
	 */
	public static void resetGrid() {
		for(int i = 0; i < Constants.ROW; i++) {
			for(int j = 0; j < Constants.COL; j++){
				colorArr[i][j] = new Color(128,128,128);
			}
		}
	}

	/**
	 * CTOR for Tetris game panel
	 * @return 
	 */
	public TetrisPanel(JFrame tetris) {
		this.tetris = tetris;
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(new Color(0, 104, 139));

		this.createComponent();
		this.wireComponent();

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_LEFT ||
						e.getKeyCode() == KeyEvent.VK_A) {
					if(!paused) {
						if(currentShape.checkMoveLeft() && currentShape.checkMoveDown()) {
							currentShape.moveLeft();

							if(currentShape.CollisionDetection()) {
								currentShape.moveRight();
							}
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
						e.getKeyCode() == KeyEvent.VK_D) {
					if(!paused) {
						if(currentShape.checkMoveRight() && currentShape.checkMoveDown()) {
							currentShape.moveRight();

							if(currentShape.CollisionDetection()) {
								currentShape.moveLeft();
							}
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_W) {
					if(!paused) {
						currentShape.rotate();
						if(currentShape.CollisionDetection())
							currentShape.rotateBack();
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN ||
						e.getKeyCode() == KeyEvent.VK_S) {

				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(!paused) {
						while(currentShape.checkMoveDown() && !currentShape.CollisionDetection()) {
							currentShape.moveDown();
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_R) {
					TetrisPanel.resetGrid();
					finished = false;

					currentScore.setText("0");
					paused = false;
					score = 0;
					lineCount = 0;

					for(int i = 0; i < Constants.NUMB_SHAPES_SHOWN; i++) {
						int temp = (int) (Math.random() * 7);
						nextShapes[i] = temp;
					}

					for(int i = 1; i < Constants.NUMB_SHAPES_SHOWN; i++) {
						int posY = 0;
						if(i != 1) {
							posY = 3;
						}
						switch(nextShapes[i]) {
						case 0:
							nextShapeArr[i - 1] = new IShape(16, 5 + (i - 1) * posY);
							break;
						case 1:
							nextShapeArr[i - 1] = new JShape(17, 5 + (i - 1) * posY);
							break;
						case 2:
							nextShapeArr[i - 1] = new LShape(17, 5 + (i - 1) * posY);
							break;
						case 3:
							nextShapeArr[i - 1] = new OShape(17, 5 + (i - 1) * posY);
							break;
						case 4:
							nextShapeArr[i - 1] = new SShape(17, 5 + (i - 1) * posY);
							break;
						case 5:
							nextShapeArr[i - 1] = new TShape(17, 5 + (i - 1) * posY);
							break;
						case 6:
							nextShapeArr[i - 1] = new ZShape(17, 5 + (i - 1) * posY);
							break;
						default:
							break;
						}
					}

					switch (nextShapes[0]) {
					case 0:
						currentShape = new IShape(7,0);
						break;
					case 1:
						currentShape = new JShape(7,0);
						break;
					case 2:
						currentShape = new LShape(7,0);
						break;
					case 3:
						currentShape = new OShape(7,0);
						break;
					case 4:
						currentShape = new SShape(7,0);
						break;
					case 5:
						currentShape = new TShape(7,0);
						break;
					case 6:
						currentShape = new ZShape(7,0);
						break;
					default:
						break;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_P) {
					if(!finished)
						paused = !paused;
				}

				if(currentShape == null) {
					System.out.println("null shape!");
					System.exit(0);
				}
			}
		});		
	}

	/**
	 * Initialize the initial condition for the tetris game
	 */
	public void createComponent() {
		// Initialize the color of the grid to all GREY
		colorArr = new Color[Constants.ROW][Constants.COL];
		for(int i = 0; i < Constants.ROW; i++) {
			for(int j = 0; j < Constants.COL; j++){
				colorArr[i][j] = new Color(128,128,128);
			}
		}

		// Create the list of shapes
		nextShapes = new int[Constants.NUMB_SHAPES_SHOWN];
		for(int i = 0; i < Constants.NUMB_SHAPES_SHOWN; i++) {
			int temp = (int) (Math.random() * 7);
			nextShapes[i] = temp;
		}

		// Create next Blocks
		for(int i = 1; i < Constants.NUMB_SHAPES_SHOWN; i++) {
			int posY = 0;
			if(i != 1) {
				posY = 3;
			}
			switch(nextShapes[i]) {
			case 0:
				nextShapeArr[i - 1] = new IShape(16, 5 + (i - 1) * posY);
				break;
			case 1:
				nextShapeArr[i - 1] = new JShape(17, 5 + (i - 1) * posY);
				break;
			case 2:
				nextShapeArr[i - 1] = new LShape(17, 5 + (i - 1) * posY);
				break;
			case 3:
				nextShapeArr[i - 1] = new OShape(17, 5 + (i - 1) * posY);
				break;
			case 4:
				nextShapeArr[i - 1] = new SShape(17, 5 + (i - 1) * posY);
				break;
			case 5:
				nextShapeArr[i - 1] = new TShape(17, 5 + (i - 1) * posY);
				break;
			case 6:
				nextShapeArr[i - 1] = new ZShape(17, 5 + (i - 1) * posY);
				break;
			default:
				break;
			}
		}

		// Create the first block
		switch (nextShapes[0]) {
		case 0:
			currentShape = new IShape(7,0);
			break;
		case 1:
			currentShape = new JShape(7,0);
			break;
		case 2:
			currentShape = new LShape(7,0);
			break;
		case 3:
			currentShape = new OShape(7,0);
			break;
		case 4:
			currentShape = new SShape(7,0);
			break;
		case 5:
			currentShape = new TShape(7,0);
			break;
		case 6:
			currentShape = new ZShape(7,0);
			break;
		default:
			break;
		}

		// Create the components for UI here:
		this.setLayout(null);

		// Next Panel
		JPanel nextPanel = new JPanel();

		nextPanel.setLayout(new GridLayout(4, 1));

		JLabel nextLabel = new JLabel();
		nextLabel.setText("Next");
		nextLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		nextLabel.setVerticalAlignment(SwingConstants.CENTER);
		nextLabel.setHorizontalAlignment(SwingConstants.CENTER);

		nextLabel.setBackground(new Color(119,136,153));
		nextLabel.setForeground(Color.WHITE);

		nextLabel.setOpaque(true);

		nextPanel.add(nextLabel);

		nextPanel.setBounds(678, 50, 200, 400);

		this.add(nextPanel);


		// Score Panel
		JPanel scorePanel = new JPanel();
		scorePanel.setBounds(36,570,150,150);
		scorePanel.setLayout(new GridLayout(2, 1));

		JLabel scoreLabel = new JLabel();
		scoreLabel.setText("Score");
		scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		scoreLabel.setVerticalAlignment(SwingConstants.CENTER);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

		scoreLabel.setBackground(new Color(128,128,128));
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setOpaque(true);

		scorePanel.add(scoreLabel);

		currentScore.setText("0");
		currentScore.setFont(new Font("Tahoma", Font.BOLD, 24));
		currentScore.setVerticalAlignment(SwingConstants.CENTER);
		currentScore.setHorizontalAlignment(SwingConstants.CENTER);

		currentScore.setBackground(new Color(192,192,192));
		currentScore.setForeground(Color.BLACK);
		currentScore.setOpaque(true);

		scorePanel.add(currentScore);

		this.add(scorePanel);

		// Line Panel
		JPanel linePanel = new JPanel();
		linePanel.setBounds(36,100,150,150);
		linePanel.setLayout(new GridLayout(2, 1));

		JLabel lineLabel = new JLabel();
		lineLabel.setText("Lines Count");
		lineLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lineLabel.setVerticalAlignment(SwingConstants.CENTER);
		lineLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lineLabel.setBackground(new Color(128,128,128));
		lineLabel.setForeground(Color.BLACK);
		lineLabel.setOpaque(true);

		linePanel.add(lineLabel);


		lineCountLabel.setText("0");
		lineCountLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lineCountLabel.setVerticalAlignment(SwingConstants.CENTER);
		lineCountLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lineCountLabel.setBackground(new Color(192,192,192));
		lineCountLabel.setForeground(Color.BLACK);
		lineCountLabel.setOpaque(true);

		linePanel.add(lineCountLabel);

		this.add(linePanel);


		// Pause button:
		ImageIcon temp = new ImageIcon("pause.png");
		Image newTemp = temp.getImage().getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
		pauseBtn = new JButton(new ImageIcon(newTemp));

		pauseBtn.setBackground(new Color(128,128,128));
		pauseBtn.setForeground(Color.WHITE);
		pauseBtn.setFocusPainted(false);
		pauseBtn.setBounds(new Rectangle(824,702,60,60));

		this.add(pauseBtn);

	}

	/**
	 * Wire all the buttons and components to listeners
	 */
	public void wireComponent() {	
		pauseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!paused) {
					paused = !paused;
				}

				PauseFrame pauseFrame = new PauseFrame();
				pauseFrame.getJFrame().setVisible(true);

			}
		});	
	}

	/**
	 * Push the next shapes to the front of the Shape array and set the final value to be -1
	 * Note: -1 stands for not having any part yet
	 * @param shapesArr
	 * @return int array of the shape Array
	 */
	public int[] fix(int[] shapesArr) {
		for(int i = 0; i < shapesArr.length - 1; i++) {
			shapesArr[i] = shapesArr[i +1];
		}
		shapesArr[shapesArr.length - 1] = -1;			// Nothing inside

		return shapesArr;
	}

	public Shape[] fix(Shape[] shapesArr) {
		for(int i = 0; i < shapesArr.length - 1; i++) {
			shapesArr[i] = shapesArr[i + 1];

			for(int j = 0; j < shapesArr[i].blocks.size(); j++) {
				Block temp = (Block) shapesArr[i].blocks.get(j);
				temp.setY(temp.getY() - 3);
			}
			shapesArr[i].y = 5 + i * 3;
		}
		shapesArr[shapesArr.length - 1] = null;			// Nothing inside

		return shapesArr;
	}

	/**
	 * Function to move the currentShape
	 */
	public void move() {
		// If the block and still go down and not collide with anything
		if(currentShape.checkMoveDown() && !currentShape.CollisionDetection()) {
			currentShape.moveDown();			// Then go down
		}
		else {									// If can not
			// Set the ending condition for the currentShape to true 
			currentShape.reachEnd = true;

			// Take the color of the currentShape and color the grid at those positions
			Color temp = currentShape.getColor();
			for(Block i : currentShape.blocks) {
				colorArr[i.getY()][i.getX()] = temp;
			}

			// Generate a random new Shape
			nextShapes = fix(nextShapes);
			nextShapeArr = fix(nextShapeArr);

			int rand = (int) (Math.random() * 7);
			nextShapes[nextShapes.length - 1] = rand;

			switch (nextShapes[nextShapes.length - 1]) {
			case 0:
				nextShapeArr[nextShapeArr.length - 1] = new IShape(16, 5 + (3 - 1) * 3);
				break;
			case 1:
				nextShapeArr[nextShapeArr.length - 1] = new JShape(17, 5 + (3 - 1) * 3);
				break;
			case 2:
				nextShapeArr[nextShapeArr.length - 1] = new LShape(17, 5 + (3 - 1) * 3);
				break;
			case 3:
				nextShapeArr[nextShapeArr.length - 1] = new OShape(17, 5 + (3 - 1) * 3);
				break;
			case 4:
				nextShapeArr[nextShapeArr.length - 1] = new SShape(17, 5 + (3 - 1) * 3);
				break;
			case 5:
				nextShapeArr[nextShapeArr.length - 1] = new TShape(17, 5 + (3 - 1) * 3);
				break;
			case 6:
				nextShapeArr[nextShapeArr.length - 1] = new ZShape(17, 5 + (3 - 1) * 3);
				break;
			default:
				break;
			}

			switch (nextShapes[0]) {
			case 0:
				currentShape = new IShape(7,0);
				break;
			case 1:
				currentShape = new JShape(7,0);
				break;
			case 2:
				currentShape = new LShape(7,0);
				break;
			case 3:
				currentShape = new OShape(7,0);
				break;
			case 4:
				currentShape = new SShape(7,0);
				break;
			case 5:
				currentShape = new TShape(7,0);
				break;
			case 6:
				currentShape = new ZShape(7,0);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Check whether the game ends or not
	 * @return T/F-------> T: game ends
	 * 					   F: game not ends yet
	 */
	public boolean checkEndGame() {
		// Check the color of the grid in the first row
		// If there is a color different than GREY --> end the game
		for(int i = 0; i < Constants.COL; i++) {
			if(colorArr[0][i].getRGB() != Constants.compareColor.getRGB()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clear the row if it is fully filled
	 */
	public void clearRow() {
		for(int i = Constants.ROW - 1; i >= 1; i--) {
			if(checkRow(i)) {
				clearRowSP(i);

				// Increase the point here!
				score +=50;
				lineCount++;
				currentScore.setText(Integer.toString(score));
				lineCountLabel.setText(Integer.toString(lineCount));
			}
		}
	}

	/**
	 * Checking whether the input row is fully filled or not
	 * @param row: the row we want to check
	 * @return T/F-------> F: if there is at least 1 block with color GREY
	 * 					   T: if there is no block with GREY color 
	 * 
	 */
	private boolean checkRow(int row) {
		for(int i = 0; i < Constants.COL; i++) {
			if(colorArr[row][i].getRGB() == Constants.compareColor.getRGB()) {
				return false;
			}
		}	
		return true;
	}

	/**
	 * Clearing the row, which is fully filled
	 * @param row: the row we want to clear
	 */
	private void clearRowSP(int row) {
		for(int i = row; i >= 2; i--) {
			for(int j = 0; j < Constants.COL; j++) {
				// Move all the rows above this given row down 1 unit
				colorArr[i][j] = colorArr[i - 1][j];
			}
		}
	}

	public static Shape[] nextShapeArr = new Shape[3];

	/**
	 * Function to draw and render the grid and the UI out to the screen
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Draw the grid
		for(int i = 0; i < Constants.ROW; i++) {
			for(int j = 0; j < Constants.COL; j++) {
				g.setColor(colorArr[i][j]);
				g.fillRect((j + 7) * (Constants.BLOCK_SIZE + 2) , i * (Constants.BLOCK_SIZE + 2),
						Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
			}
		}

		if(currentShape != null)
			currentShape.render(g);

		// Draw the next shape
		if(nextShapes.length != Constants.NUMB_SHAPES_SHOWN) {
			System.out.println("There are not enough 4 shapes to show");
		}
		else {
			System.out.println("There are 4 shapes to shown");

			for(Shape s : nextShapeArr) {
				g.setColor(s.getColor());
				s.render(g);
			}
		}
	}

	public void run() {
		if(!finished) {
			if(!paused) {
				if(currentShape != null) {
					this.move();   
					this.repaint();
					this.clearRow();

					currentShape.collidedLeft = false;
					currentShape.collidedRight = false;
				}

				if(this.checkEndGame()) {
					currentShape = null;
					finished = true;
					TetrisPanel.resetGrid();

					this.currentScore.setText("0");
					this.lineCountLabel.setText("0");

					int response = JOptionPane.showConfirmDialog(tetris, 
							"Final score: " + score + 
							"\n\nDo you want to play again?"
							, "Game Over", JOptionPane.YES_NO_OPTION);

					if(response == JOptionPane.YES_OPTION) {
						finished = false;

						for(int i = 0; i < Constants.NUMB_SHAPES_SHOWN; i++) {
							int temp = (int) (Math.random() * 7);
							nextShapes[i] = temp;
						}

						for(int i = 1; i < Constants.NUMB_SHAPES_SHOWN; i++) {
							int posY = 0;
							if(i != 1) {
								posY = 3;
							}
							switch(nextShapes[i]) {
							case 0:
								nextShapeArr[i - 1] = new IShape(16, 5 + (i - 1) * posY);
								break;
							case 1:
								nextShapeArr[i - 1] = new JShape(17, 5 + (i - 1) * posY);
								break;
							case 2:
								nextShapeArr[i - 1] = new LShape(17, 5 + (i - 1) * posY);
								break;
							case 3:
								nextShapeArr[i - 1] = new OShape(17, 5 + (i - 1) * posY);
								break;
							case 4:
								nextShapeArr[i - 1] = new SShape(17, 5 + (i - 1) * posY);
								break;
							case 5:
								nextShapeArr[i - 1] = new TShape(17, 5 + (i - 1) * posY);
								break;
							case 6:
								nextShapeArr[i - 1] = new ZShape(17, 5 + (i - 1) * posY);
								break;
							default:
								break;
							}
						}

						switch (nextShapes[0]) {
						case 0:
							currentShape = new IShape(7,0);
							break;
						case 1:
							currentShape = new JShape(7,0);
							break;
						case 2:
							currentShape = new LShape(7,0);
							break;
						case 3:
							currentShape = new OShape(7,0);
							break;
						case 4:
							currentShape = new SShape(7,0);
							break;
						case 5:
							currentShape = new TShape(7,0);
							break;
						case 6:
							currentShape = new ZShape(7,0);
							break;
						default:
							break;
						}

					}
					else {
						tetris.dispose();
						Tetris.startFrame.setVisible(true);

					}

					score = 0;
					lineCount = 0;
				}		
			}
		}
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		run();
	}
}
