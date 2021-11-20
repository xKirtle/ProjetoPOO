package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Pine extends GameElement implements IBurnable {
	private int burnTime = 10;
	
	public Pine(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}
}