import java.awt.Color;

public class IShape extends Shape{

	private int changePivot;

	public IShape(int x, int y){
		super(x,y, x-1, x+2, new Color(128,0,0));

		blocks.add(new Block((x - 1) , y ));
		blocks.add(new Block(x ,y ));
		blocks.add(new Block((x + 1) , y ));
		blocks.add(new Block((x + 2) , y ));

	}


	public void setup(int x, int y) {
		blocks.get(0).setX(x - 1);
		blocks.get(0).setY(y);

		blocks.get(1).setX(x);
		blocks.get(1).setY(y);

		blocks.get(2).setX(x + 1);
		blocks.get(2).setY(y);

		blocks.get(3).setX(x + 2);
		blocks.get(3).setY(y);

	}

	private int rotateNum = 0;
	@Override
	public void rotate() {
		if(rotateNum == 0) {
			super.rotate();

			if(CollisionDetection()) {
				super.rotateBack();
			}
			else {
				rotateNum++;

				nearMostCol = x;
				farMostCol = x;
			}
		}
		else if(rotateNum == 1) {
			super.rotateBack();

			if(CollisionDetection()) {
				super.rotate();
			}
			else {
				rotateNum = 0;

				nearMostCol = x - 1;
				farMostCol = x + 2;
			}
		}
	}
}
