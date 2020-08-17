import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class StartFrame extends JFrame{

	private JButton playBtn;
	private JButton optionBtn;
	private JButton howToPlayBtn;
	private JButton highScoreBtn;

	private BackGroundPanel panel = new BackGroundPanel();

	public StartFrame() {
		createComponent();
		wireComponent();
	}

	public void createComponent() {
		// Setup the frame
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Setup the panel
		panel.setLayout(null);

		// Play Button
		playBtn = new JButton("PLAY");
		playBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		playBtn.setBackground(new Color(59,89,182));
		playBtn.setForeground(Color.WHITE);
		playBtn.setFocusPainted(false);
		playBtn.setBounds(new Rectangle(298,550,200,60));

		panel.add(playBtn);

		// Option Button
		ImageIcon option = new ImageIcon("/img/option.png");
		Image newOption = option.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);

		optionBtn = new JButton(new ImageIcon(newOption));
		optionBtn.setBackground(new Color(59,89,182));
		optionBtn.setForeground(Color.WHITE);
		optionBtn.setFocusPainted(false);
		optionBtn.setBounds(new Rectangle(270,639,60,60));

		panel.add(optionBtn);

		// How to play Button
		option = new ImageIcon("/img/question.png");
		newOption = option.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);

		howToPlayBtn = new JButton(new ImageIcon(newOption));
		howToPlayBtn.setBackground(new Color(59,89,182));
		howToPlayBtn.setForeground(Color.WHITE);
		howToPlayBtn.setFocusPainted(false);
		howToPlayBtn.setBounds(new Rectangle(468,639,60,60));

		panel.add(howToPlayBtn);

		// Play Button
		highScoreBtn = new JButton("Records");
		highScoreBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		highScoreBtn.setBackground(new Color(59,89,182));
		highScoreBtn.setForeground(Color.WHITE);
		highScoreBtn.setFocusPainted(false);
		highScoreBtn.setBounds(new Rectangle(349,639,100,60));

		panel.add(highScoreBtn);

		ActionListener myLitener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == highScoreBtn) {
					// Display the high score table out to the screen
					JFrame highScore = new JFrame();

					highScore.getContentPane().setBackground(Color.BLACK);
					highScore.setSize(400,400);;
					highScore.setLocationRelativeTo(null);
					highScore.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					highScore.setResizable(false);
					
					highScore.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {}
						
						@Override
						public void windowIconified(WindowEvent e) {}
						
						@Override
						public void windowDeiconified(WindowEvent e) {}
						
						@Override
						public void windowDeactivated(WindowEvent e) {}
						
						@Override
						public void windowClosing(WindowEvent e) {
							highScore.dispose();
						}
						
						@Override
						public void windowClosed(WindowEvent e) {}
						
						@Override
						public void windowActivated(WindowEvent e) {}
					});
					

					JPanel scorePanel = new JPanel(); 

					scorePanel.setLayout(new GridLayout(6,1));

					JLabel label = new JLabel("HIGH SCORES");
					label.setFont(new Font("Tahoma", Font.BOLD, 28));
					label.setVerticalAlignment(SwingConstants.CENTER);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setForeground(Color.WHITE);
					label.setBackground(Color.BLACK);
					label.setOpaque(true);
					scorePanel.add(label);

					String[] nameArr = new String[5];
					String[] scoreArr = new String[5];

					File openFile = new File("records.txt");
					Scanner input;
					try {
						input = new Scanner(openFile);

						int i = 0;
						while(input.hasNextLine()) {
							String[] temp = input.nextLine().split(" ");

							if(temp.length != 2) {
								System.out.println("Read the record file wrong");
							}
							else {
								nameArr[i] = temp[0];
								scoreArr[i] = temp[1]; 
							}

							i++;
						}

					} catch (FileNotFoundException e1) {
						try {
							PrintWriter pw = new PrintWriter("records.txt");

							for(int i = 0; i < 5; i++) {
								if(i == 4)
									pw.print("Noone 0");
								else
									pw.println("Noone 0");		
							}

							pw.close();
						} catch (FileNotFoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} 

						
						// Read in the file again
						try {
							input = new Scanner(openFile);
							int i = 0;
							while(input.hasNextLine()) {
								String[] temp = input.nextLine().split(" ");

								if(temp.length != 2) {
									System.out.println("Read the record file wrong");
								}
								else {
									nameArr[i] = temp[0];
									scoreArr[i] = temp[1]; 
								}

								i++;
							}
							
						} catch (FileNotFoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}

					JLabel[] scores = new JLabel[5];
					for(int i = 0; i < scores.length; i++) {
						scores[i] = new JLabel();
						if(i % 2 != 0)
							scores[i].setBackground(Color.GRAY);
						else
							scores[i].setBackground(Color.DARK_GRAY);
						scores[i].setForeground(new Color(245,245,245));
						scores[i].setOpaque(true);
						
						scores[i].setFont(new Font("Tahoma", Font.BOLD, 24));
						scores[i].setVerticalAlignment(SwingConstants.CENTER);
						scores[i].setHorizontalAlignment(SwingConstants.CENTER);
						scores[i].setText(nameArr[i] + "                                  " + scoreArr[i]);
						
						scorePanel.add(scores[i]);		
					}

					highScore.add(scorePanel);					

					highScore.setVisible(true);
				}
				if(e.getSource() == howToPlayBtn) {
					// Display the text instructs how to play

				}
				if(e.getSource() == optionBtn) {
					// Display the option frame

				}
			}
		};

		highScoreBtn.addActionListener(myLitener);
		howToPlayBtn.addActionListener(myLitener);
		optionBtn.addActionListener(myLitener);

		this.add(panel);
	}

	public JButton getPlayBtn() {
		return playBtn;
	}

	public void wireComponent() {

	}
}
