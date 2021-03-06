package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface IMovable {
	public default boolean canMoveTo(Point2D p) {
		GameBoard board = GameEngine.getInstance().board;
		return board.coordWithinBoard(p);
	}
	
	public boolean move(Direction dir);
}