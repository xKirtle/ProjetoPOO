package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Error extends GameElement {

	public Error(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
