package pt.iul.poo.firefight.starterpack;

public class Main {
	public static void main(String[] args) {
		LevelGenerator.generateLevel(10, 10, "level1");
		GameEngine game = GameEngine.getInstance();
		game.start();
		
	}
}