package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Burnt extends GameElement {

	public Burnt(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return GameLayers.BurntElements.toInt();
	}
}
