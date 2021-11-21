package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Water extends GameElement {
	private int waterTimer = 2;
	
	public Water(Point2D position, String name, Direction dir) {
		super(position, name);
		setName(GameElement.updateSpriteWithDirection("water", dir));
	}

	@Override
	public int getLayer() {
		return GameLayers.Water.toInt();
	}
	
	@Override
	public void update() {
		waterTimer--;
		
		if (waterTimer == 0) {
			GameBoard board = GameEngine.getInstance().board;
			GameElement[] arr = board.getElements(getPosition());
			
			board.removeElement(getPosition(), arr[GameLayers.Fire.toInt()]);
			board.removeElement(getPosition(), this);
		}
	}
}
