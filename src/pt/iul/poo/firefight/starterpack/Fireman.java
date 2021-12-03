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
		Scoreboard scoreboard = GameEngine.getInstance().scoreboard;

		if (!canMoveTo(newPosition)) return false;
		
		if (board.getElements(newPosition)[GameLayers.ControllableVehicles.toInt()] != null) {
			getInBulldozer();
			return true;
		}
		
		GameElement fire = board.fireAtPosition(newPosition);
		GameElement water = board.waterAtPosition(newPosition);
		
		if (fire == null || water != null) {
			board.moveElement(getPosition(), newPosition, this);				
			setPosition(newPosition);
			scoreboard.addScore(ScoreType.Fireman_Move);
		}
		else if (fire != null && water == null) {
			extinguishFire(newPosition, dir);
			scoreboard.addScore(ScoreType.Fireman_FireExtinguished);
		}

		return true;
	}

	private void extinguishFire(Point2D p, Direction dir) {
		Water water = new Water(p, "water", dir);
		water.setName(GameElement.updateSpriteWithDirection("water", dir));

		GameBoard board = GameEngine.getInstance().board;
		board.setElement(p, water);
	}
	
	private void getInBulldozer() {
		GameEngine ge = GameEngine.getInstance();
		GameBoard board = ge.board;
		
		board.removeElement(getPosition(), this);
		ge.activeElement = ge.bulldozer;
	}

	@Override
	public int getLayer() {
		return GameLayers.ControllablePlayers.toInt();
	}
}