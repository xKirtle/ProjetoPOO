package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fireman extends GameElement implements IMovable {
	public Fireman(Point2D position, String name) {
		super(position, name);
	}

	public boolean move(Direction dir) {
		Point2D newPosition = getPosition().plus(dir.asVector());
		GameBoard board = GameEngine.getInstance().board;

		if (canMoveTo(newPosition) && board.objTypeInPosition(Fire.class, newPosition) == null) {
			setPosition(newPosition);
			return true;
		}
		return false;
	}

	@Override
	public int getLayer() {
		return GameLayers.ControllablePlayers.toInt();
	}
}