package pt.iul.poo.firefight.starterpack;

public enum GameLayers {
	BaseElements(0), //"static" level elements
	TemporaryStates(1), //Water, Fire..
	BurntElements(2), //burnt "static" level elements
	ControllableVehicles(3), //Bulldozer
	ControllablePlayers(4), //Fireman
	Sky(5); //Plane
	
	private final int layer;
	private GameLayers(int layer) {
		this.layer = layer;
	}
	
	public int toInt() {
		return layer;
	}
}
