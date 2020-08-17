
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Shape {
	protected List<Block> blocks = new ArrayList<Block>(); 

	public int x;
	public int y;
	public boolean reachEnd;
	private Color color;

	//collision detection
	public int nearMostCol;
	public int farMostCol;
	public int numCol;

	public boolean collidedLeft;
	public boolean collidedRight;

	public Shape(int x, int y, int nearMostCol, int farMostCol, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		collidedLeft = false;
		collidedRight = false;
		
		this.nearMostCol = nearMostCol;
		this.farMostCol = farMostCol;
	}

	public Color getColor() {
		return color;
	}

	public void moveLeft() {
		if(checkMoveLeft() && !collidedLeft) {
			for(int i = 0; i < blocks.size(); i++) {
				Block temp = (Block) blocks.get(i);
				temp.setX(temp.getX() - 1);
			}
			nearMostCol--;
			farMostCol--;
			x--;
		}	
	}

	public void moveRight() {
		if(checkMoveRight() && !collidedRight) {
			for(int i = 0; i < blocks.size(); i++) {
				Block temp = (Block) blocks.get(i);
				temp.setX(temp.getX() + 1);
			}
			nearMostCol++;
			farMostCol++;
			x++;
		}
	}

	public void moveDown() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);
			temp.setY(temp.getY() + 1);
		}	
	}

	public void update() {
		// TODO Auto-generated method stub
		if(!checkMoveDown()) {
			reachEnd = true;
		}
	}

	public boolean checkMoveDown() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);
			if(temp.getY() >= Constants.ROW - 1) {
				return false;
			}
		}

		return true;
	}

	public boolean checkMoveLeft() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);
			if(temp.getX() <= 0) {
				return false;
			}
		}

		return true;
	}

	public boolean checkMoveRight() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);
			if(temp.getX() >= Constants.COL - 1 ) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Not used yet!!
	 */
	public void checkCollision() {
		if(nearMostCol == 0 ||
				TetrisPanel.colorArr[y][nearMostCol - 1].getRGB() != Constants.compareColor.getRGB()) {
			collidedLeft = true;
		}

		if(farMostCol == Constants.COL - 1 ||
				TetrisPanel.colorArr[y][farMostCol + 1].getRGB() != Constants.compareColor.getRGB()) {
			collidedRight = true;
		}
	}

	public boolean checkOutOfBound() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);
			if(temp.getX() > Constants.COL - 1 || temp.getY() > Constants.ROW - 1 ||
					temp.getX() < 0 || temp.getY() < 0) {
				return true;
			}
		}
		return false;
	}

	public boolean CollisionDetection() {
		for(int i = 0; i < blocks.size(); i++) {
			Block temp = (Block) blocks.get(i);	

			if(checkOutOfBound())
				return true;

			if(TetrisPanel.colorArr[temp.getY() + 1][temp.getX()].getRGB() !=
					Constants.compareColor.getRGB()) {
				return true;
			}

		}
		return false;
	}

	public void rotateBack() {
		for(int i = 0; i < blocks.size(); i++) {
			if(i != 1) {
				int vx = blocks.get(i).getX() - blocks.get(1).getX();
				int vy = blocks.get(i).getY() - blocks.get(1).getY();

				int vxRotate = 0 * vx + (1) * vy;
				int vyRotate = (-1) * vx + 0 * vy;

				blocks.get(i).setX(vxRotate + blocks.get(1).getX());
				blocks.get(i).setY(vyRotate + blocks.get(1).getY());
			}
		}
	}

	public void rotate() {
		for(int i = 0; i < blocks.size(); i++) {
			int vx = blocks.get(i).getX() - blocks.get(1).getX();
			int vy = blocks.get(i).getY() - blocks.get(1).getY();

			int vxRotate = 0 * vx + (-1) * vy;
			int vyRotate = 1 * vx + 0 * vy;

			blocks.get(i).setX(vxRotate + blocks.get(1).getX());
			blocks.get(i).setY(vyRotate + blocks.get(1).getY());
		}

		if(checkOutOfBound()) {
			rotateBack();
		}
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);

		for(int i = 0; i < blocks.size(); i++) {
			int xTemp = blocks.get(i).getX();
			int yTemp = blocks.get(i).getY();

			g.fillRect((xTemp + 7) * (Constants.BLOCK_SIZE + 2), 
					yTemp * (Constants.BLOCK_SIZE + 2) , 
					Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
		}
	}
}
