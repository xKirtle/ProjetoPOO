package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fireman extends GameElement implements IMovable {
	public Fireman(Point2D position, String name) {
		super(position, name);
	}

	public void move(Direction dir) {
		Point2D newPosition = getPosition().plus(dir.asVector());

		if (canMoveTo(newPosition)) {
			setPosition(newPosition);
		}
	}

	@Override
	public int getLayer() {
		return 3;
	}
}