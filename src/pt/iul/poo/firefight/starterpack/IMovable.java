package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface IMovable {
	public default boolean canMoveTo(Point2D p) {
		if (p.getX() < 0 || p.getX() >= GameEngine.GRID_WIDTH || 
				p.getY() < 0 || p.getY() >= GameEngine.GRID_HEIGHT) 
			return false;
		
		return true;
	}
	
	public void move(Direction dir);
}
