package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElement {

	public Fire(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.Fire.toInt();
	}

	//Should there be an "invicibility" cooldown where the tile can't be on fire after being extinguished?
	public void trySpreadFire() {
		GameBoard board = GameEngine.getInstance().board;

		for (Point2D p : getPosition().getNeighbourhoodPoints()) {
			if (!board.coordWithinBoard(p)) continue;
			GameElement[] arr = board.getElements(p);
			if (arr[GameLayers.Fire.toInt()] != null) continue;
			
			int randomValue = (int)(Math.random() * 20); //[0, 20[			
			boolean spreadFire = (arr[GameLayers.BaseElements.toInt()] instanceof Pine && randomValue <= 1) ||
					(arr[GameLayers.BaseElements.toInt()] instanceof Eucaliptus && randomValue <= 2) ||
					(arr[GameLayers.BaseElements.toInt()] instanceof Grass && randomValue <= 3);
			
			if (spreadFire) {
				Fire fire = new Fire(p, "fire");
				board.setElement(p, fire);
			}
		}
	}
	
	@Override
	public void update() {
		trySpreadFire();
	}
}
