package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fireman extends GameElement {
	public Fireman(Point2D position, String name) {
		super(position, name);
	}

	// Move numa direcao aleatoria 
	public void move() {
		
		boolean hasMoved = false;
		
		while (hasMoved == false)  {
		
			Direction randDir = Direction.random();
			Point2D newPosition = getPosition().plus(randDir.asVector());
			
			if (canMoveTo(newPosition) ) {
				setPosition(newPosition);
				hasMoved = true;
			}
		}
	}

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}

	@Override
	public int getLayer() {
		return 3;
	}
}
