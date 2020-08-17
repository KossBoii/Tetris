import java.awt.Color;

public class OShape extends Shape{
	
	public OShape(int x, int y){
		super(x,y,x-1,x, Color.YELLOW);
		blocks.add(new Block((x - 1) , y));
		blocks.add(new Block(x , y ));
		blocks.add(new Block(x , (y + 1) ));
		blocks.add(new Block((x - 1) , (y + 1) ));
	}
	
	@Override
	public void rotate() {
		// Do nothing
		
	}
	
}
