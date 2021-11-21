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

	//Should this be here?
	public static void trySpreadFire(Point2D position) {
		GameBoard board = GameEngine.getInstance().board;

		for (Point2D p : position.getNeighbourhoodPoints()) {
			if (!GameBoard.coordWithinBoard(p)) continue;
			GameElement[] arr = board.getElements(p);
			if (arr[GameLayers.Fire.toInt()] != null) continue;
			
			//TODO: CHANGE randomValue BACK TO 20
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
	
	public static void removeFire(Point2D position) {
		GameBoard board = GameEngine.getInstance().board;

		GameElement[] arr = board.getElements(position);
		GameElement elem = arr[GameLayers.Fire.toInt()];
		
		if (elem != null)
			board.removeElement(position, elem);
	}
}
