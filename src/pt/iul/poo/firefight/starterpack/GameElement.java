package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile, IUpdatable{
	private Point2D position;
	private String name;

	public GameElement(Point2D position, String name) {
		this.position = position;
		this.name = name;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static GameElement newInstanceByType(char type, Point2D p) {
		switch (type) {
			case 'p': return new Pine(p, "pine");
			case 'e': return new Eucaliptus(p, "eucaliptus");
			case 'm': return new Grass(p, "grass");
			case '_': return new Land(p, "land");
			default: return new Error(p, "error");
		}
	}
	
	public static GameElement newInstanceByType(String type, Point2D p) {
		switch (type) {
			case "Fireman": return new Fireman(p, "fireman");
			case "Bulldozer": return new Bulldozer(p, "bulldozer_down");
			case "Fire": return new Fire(p, "fire");
			default: return new Error(p, "error");
		}
	}
	
	public static boolean isMovementKey(int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_LEFT:
				return true;
			default: return false;
		}
	}
	
	public static String updateSpriteWithDirection(String name, Direction dir) {
		String newName = name;
		switch(dir) {
			case UP: newName += "_up"; break;
			case RIGHT: newName += "_right"; break;
			case DOWN: newName += "_down"; break;
			case LEFT: newName += "_left"; break;
		}
		return newName;
	}
	
	public void update() {
		
	}
}