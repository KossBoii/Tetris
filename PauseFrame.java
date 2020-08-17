import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PauseFrame{

	private JFrame pauseFrame;
	private JPanel pausePanel;
	
	JButton quitBtn = new JButton();
	JButton resumeBtn = new JButton();
	JButton howToPlayBtn = new JButton();
	
	public PauseFrame() {
		pauseFrame = new JFrame();
		
		pauseFrame.getContentPane().setBackground(Color.BLACK);
		pauseFrame.setSize(260,370);;
		pauseFrame.setLocationRelativeTo(null);
		pauseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pauseFrame.setResizable(false);
		
		
		createComponents();
		wireComponents();
	}
	
	public JFrame getJFrame() {
		return pauseFrame;
	}
	
	public void createComponents() {
		pausePanel = new JPanel();
		
		pausePanel.setLayout(null);
		
		JLabel pauseLabel = new JLabel();
		pauseLabel.setText("PAUSED");
		pauseLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		pauseLabel.setVerticalAlignment(SwingConstants.CENTER);
		pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);

		pauseLabel.setForeground(Color.WHITE);
		pauseLabel.setBackground(new Color(105,105,105));
		pauseLabel.setBounds(0, 0, 260, 60);
		pauseLabel.setOpaque(true);
		pausePanel.add(pauseLabel);
		
		//JButton Resume
		resumeBtn.setText("RESUME");
		resumeBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		resumeBtn.setVerticalAlignment(SwingConstants.CENTER);
		resumeBtn.setHorizontalAlignment(SwingConstants.CENTER);

		resumeBtn.setForeground(Color.WHITE);
		resumeBtn.setBackground(new Color(0,100,0));
		resumeBtn.setFocusPainted(false);
		resumeBtn.setBounds(65, 70, 120, 60);
		pausePanel.add(resumeBtn);
		
		// JButton HTP
		howToPlayBtn.setText("HOW TO PLAY");
		resumeBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		resumeBtn.setVerticalAlignment(SwingConstants.CENTER);
		resumeBtn.setHorizontalAlignment(SwingConstants.CENTER);
		
		howToPlayBtn.setBackground(new Color(169,169,169));
		howToPlayBtn.setForeground(Color.WHITE);
		howToPlayBtn.setFocusPainted(false);
		howToPlayBtn.setBounds(new Rectangle(65,140,120,60));
		pausePanel.add(howToPlayBtn);
		
		// JButton Quit
		quitBtn.setText("QUIT");
		quitBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		quitBtn.setVerticalAlignment(SwingConstants.CENTER);
		quitBtn.setHorizontalAlignment(SwingConstants.CENTER);
		
		quitBtn.setBackground(new Color(169,169,169));
		quitBtn.setForeground(Color.WHITE);
		quitBtn.setFocusPainted(false);
		quitBtn.setBounds(new Rectangle(65,210,120,60));
		pausePanel.add(quitBtn);
		
		pauseFrame.add(pausePanel);
		
	}
	
	public void wireComponents() {
		ActionListener myListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == resumeBtn) {
					TetrisPanel.paused = !TetrisPanel.paused;
					Tetris.tetris.setFocusable(true);
					pauseFrame.dispose();
					
				}
				if(e.getSource() == howToPlayBtn) {
					
				}
				if(e.getSource() == quitBtn) {
					pauseFrame.setVisible(false);
					Tetris.tetris.setVisible(false);
					Tetris.startFrame.setVisible(true);
					
					
				}
			}
		};
		
		howToPlayBtn.addActionListener(myListener);
		quitBtn.addActionListener(myListener);
		resumeBtn.addActionListener(myListener);
		
		
		
	}
	
}
