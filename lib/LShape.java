import java.awt.Color;

public class LShape extends Shape{

	public LShape(int x, int y){
		super(x,y,x-1,x, new Color(128,0,128));

		blocks.add(new Block(x + 1, y));
		blocks.add(new Block(x ,y ));
		blocks.add(new Block((x - 1) , y));
		blocks.add(new Block((x - 1) , y + 1 ));
	}

	private int rotateNum = 0;
	@Override
	public void rotate() {
		super.rotate();
		if(CollisionDetection()) {
			super.rotateBack();
		}
		else {
			if(rotateNum == 0) {
				rotateNum++;			
				nearMostCol = x - 1;
				farMostCol = x;
			}
			else if(rotateNum == 1) {
				rotateNum++;
				nearMostCol = x - 1;
				farMostCol = x + 1;
			}
			else if(rotateNum == 2) {
				rotateNum++;
				nearMostCol = x;
				farMostCol = x + 1;
			}
			else if(rotateNum == 3) {
				rotateNum++;
				nearMostCol = x - 1;
				farMostCol = x + 1;
			}
		}
	}

}
