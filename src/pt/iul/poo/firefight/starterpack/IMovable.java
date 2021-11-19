package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface IMovable {
	public boolean canMoveTo(Point2D p);
	public void move(Direction dir);
}
