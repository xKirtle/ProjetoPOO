package pt.iul.poo.firefight.starterpack;

public enum GameLayers {
	BaseElements(0), //level elements
	Fire(1), //Fire
	Water(2), //Water
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
