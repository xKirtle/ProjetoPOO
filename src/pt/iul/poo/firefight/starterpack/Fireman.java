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
		GameElement fire = board.objTypeInPosition(Fire.class, newPosition);
		GameElement water = board.objTypeInPosition(Water.class, newPosition);

		if (canMoveTo(newPosition)) {
			if (fire == null || water != null) {
				board.moveElement(getPosition(), newPosition, this);				
				setPosition(newPosition);
			}
			else if (fire != null && water == null)
				extinguishFire(newPosition, dir);
			
			return true;
		}
		return false;
	}
	
	private void extinguishFire(Point2D p, Direction dir) {
		Water water = new Water(p, "water", dir);
		water.setName(GameElement.updateSpriteWithDirection("water", dir));
		
		GameBoard board = GameEngine.getInstance().board;
		board.setElement(p, water);
	}

	@Override
	public int getLayer() {
		return GameLayers.ControllablePlayers.toInt();
	}
}