package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public class Eucaliptus extends GameElement implements IBurnable {
	private int burnTime = 5;

	public Eucaliptus(Point2D position, String name) {
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
	
	//TODO: update is the same in Pine, Eucaliptus and Grass
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
				Fire.trySpreadFire(getPosition());
			}
		}
	}
}