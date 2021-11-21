package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Pine extends GameElement implements IBurnable {
	private int burnTime = 10;
	
	public Pine(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.BaseElements.toInt();
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}
	
	//TODO: update and trySpreadFire are the same in Pine, Eucaliptus and Grass
	//Create an abstract class for "vegetation"?
	
	@Override
	public void update() {
		GameBoard board = GameEngine.getInstance().board;
		GameElement fire = board.objTypeInPosition(Fire.class, getPosition());
		if (fire != null) {
			burn();
			if (isBurnt()) {
				Burnt burnt = new Burnt(getPosition(), "burnt" + getName());
				
				board.removeElement(getPosition(), fire);
				board.removeElement(getPosition(), this);
				board.setElement(getPosition(), burnt);
			}
			else {
				trySpreadFire();
			}
		}
	}

	@Override
	public void trySpreadFire() {
		GameBoard board = GameEngine.getInstance().board;

		List<Point2D> points = getPosition().getNeighbourhoodPoints();
		
		for (Point2D p : points) {
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