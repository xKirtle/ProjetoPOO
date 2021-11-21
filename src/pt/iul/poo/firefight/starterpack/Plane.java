package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Plane extends GameElement implements IMovable, IUpdatable {

	public Plane(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.Sky.toInt();
	}
	
	@Override
	public boolean move(Direction dir) {
		Point2D newPosition = getPosition().plus(new Vector2D(0, -2));
		GameBoard board = GameEngine.getInstance().board;

		if (canMoveTo(newPosition)) {
			board.moveElement(getPosition(), newPosition, this);
			setPosition(newPosition);
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canMoveTo(Point2D p) {
		return p.getY() >= 0;
	}

	@Override
	public void update() {
		Point2D p1 = getPosition();
		Point2D p2 = getPosition().plus(new Vector2D(0, 1));
		
		if (GameBoard.coordWithinBoard(p1))
			Fire.removeFire(p1);
		if (GameBoard.coordWithinBoard(p2))
			Fire.removeFire(p2);
	}
}
