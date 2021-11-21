package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElement {

	public Fire(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.TemporaryStates.toInt();
	}

	//Should this be here?
	public static void trySpreadFire(Point2D position) {
		GameBoard board = GameEngine.getInstance().board;

		for (Point2D p : position.getNeighbourhoodPoints()) {
			if (!GameBoard.coordWithinBoard(p)) continue;
			GameElement[] arr = board.getElements(p);
			
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
}
