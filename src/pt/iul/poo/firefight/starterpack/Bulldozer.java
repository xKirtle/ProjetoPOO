package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends GameElement implements IMovable {

	public Bulldozer(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.ControllableVehicles.toInt();
	}

	@Override
	public boolean move(Direction dir) {
		Point2D newPosition = getPosition().plus(dir.asVector());
		GameBoard board = GameEngine.getInstance().board;
		
		if (canMoveTo(newPosition) && board.objTypeInPosition(Fire.class, newPosition) == null) {
			board.moveElement(getPosition(), newPosition, this);
			setPosition(newPosition);
			setName(GameElement.updateSpriteWithDirection("bulldozer", dir));
			bulldozePosition(getPosition());
			
			return true;
		}
		return false;
	}
	
	private void bulldozePosition(Point2D p) {
		GameBoard board = GameEngine.getInstance().board;
		
		Land land = new Land(p, "land");
		board.setElement(p, land);
	}
}
