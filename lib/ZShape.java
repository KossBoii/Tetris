import java.awt.Color;

public class ZShape extends Shape{
	
	public ZShape(int x, int y){
		super(x,y,x-1,x+1, Color.BLUE);
		
		blocks.add(new Block(x - 1, y ));
		blocks.add(new Block(x , y ));
		blocks.add(new Block(x , y + 1));
		blocks.add(new Block(x + 1 , y + 1));
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
				nearMostCol = x - 1;
				farMostCol = x + 1;
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
				farMostCol = x;
			}
		}	
	}
}
