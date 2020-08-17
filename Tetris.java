
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Tetris {
	
	public static JFrame startFrame;
	public static JFrame tetris;
	public static int count = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Initialize all the frame needed for the game
		startFrame = new StartFrame();
		tetris = new JFrame("CS3B Tetris Final");
		TetrisPanel game = new TetrisPanel(tetris);
		
		// Setup the maingame Frame here
		tetris.add(game, BorderLayout.CENTER);
		tetris.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		tetris.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tetris.setResizable(false);
		tetris.setLocationRelativeTo(null);
		
		// Show the beginning 
		startFrame.setVisible(true);

		((StartFrame) startFrame).getPlayBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ((StartFrame) startFrame).getPlayBtn()) {
					count++;
					startFrame.setVisible(false);
					tetris.setVisible(true);
					if(count != 1) {
						JOptionPane.showMessageDialog(game, "Please press R to start game");
					}
					game.clock.start();
				}
			}
		});
	}
}
