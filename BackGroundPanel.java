import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class BackGroundPanel extends JPanel{

	private Image background1;
	private Image title;

	public BackGroundPanel() {
		background1 = java.awt.Toolkit.getDefaultToolkit().getImage("/img/Background.jpg");
		title = java.awt.Toolkit.getDefaultToolkit().getImage("/img/tetris.png");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.drawImage(background1, 0,0, 800, 800, this);
		g.drawImage(title, 257, 50, 300, 200, this);
	}
}
