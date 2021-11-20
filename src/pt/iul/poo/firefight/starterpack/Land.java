package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Land extends GameElement {

	public Land(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return 0;
	}
}