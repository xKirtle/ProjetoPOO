package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static String levelPath = "levels/level1.txt";
	private static GameEngine instance;

	private ImageMatrixGUI gui;
	public Fireman fireman;
	public Bulldozer bulldozer;
	private Plane plane;
	public IMovable activeElement;

	public GameBoard board;
	public Scoreboard scoreboard; //public?

	private GameEngine() {
		gui = ImageMatrixGUI.getInstance();	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); 
		gui.registerObserver(this);
		board = new GameBoard(GRID_WIDTH, GRID_HEIGHT);
		scoreboard = Scoreboard.getInstance();
		gui.go();
	}

	public static GameEngine getInstance() {
		if (instance == null)
			instance = new GameEngine();

		return instance;
	}

	@Override
	public void update(Observed source) {		
		int key = gui.keyPressed();

		boolean validMove = false;

		if (GameElement.isMovementKey(key)) {
			if (activeElement.move(Direction.directionFor(key))) {

				validMove = true;			
			}
		}

		if (key == KeyEvent.VK_P) {
			if (plane == null) {
				List<Integer> firesPerColumn = board.firesPerColumn();
				Integer maxValue = Collections.max(firesPerColumn);
				Integer columnIndex = firesPerColumn.indexOf(maxValue);

				//Y is GRID_HEIGHT+1 because it'll move 2 positions right after spawning
				Point2D p = new Point2D(columnIndex, GRID_HEIGHT+1);
				plane = new Plane(p, "plane");
				board.setElement(p, plane);
				scoreboard.addScore(ScoreType.Plane_Summoned);

				validMove = true;
			}
		}

		if (key == KeyEvent.VK_ENTER && activeElement instanceof Bulldozer) {
			activeElement = fireman;
			fireman.setPosition(bulldozer.getPosition());
			board.setElement(fireman.getPosition(), fireman);

			validMove = true;
		}

		if (validMove) {
			if (plane != null) {
				if (plane.getPosition().getY() >= 2)
					plane.move(Direction.UP);
				else {
					board.removeElement(plane.getPosition(), plane);
					plane = null;
				}
			}

			board.updateElements();
			gui.setStatusMessage("Game Score: " + scoreboard.getScore());

			//gui.removeImage() was not displaying changes on gui.update()...?
			gui.clearImages();
			board.sendBoardToGUI();

			//Check if game ended
			if (board.isGameOver()) {

				int tilesIntact = board.tilesIntact();
				scoreboard.addScore(ScoreType.TileIntact.getValue() * tilesIntact);
				gui.setStatusMessage("Game Score: " + scoreboard.getScore());
				gui.setMessage("Level finished! \nLevel score: " + scoreboard.getScore());

				//Reiniciar jogo
				gui.clearImages();
				board = new GameBoard(GRID_WIDTH, GRID_HEIGHT);
				LevelGenerator.generateLevel(10, 10, "level1");
				start();
			}
		}
	}

	public void start() {
		readLevelData();
		activeElement = fireman;
		scoreboard.resetScore();
		gui.setStatusMessage("Game Score: " + scoreboard.getScore());
		board.sendBoardToGUI();
	}

	private void readLevelData() {
		File f = new File(levelPath);
		Scanner s;
		try {
			s = new Scanner(f);

			//Map Data
			int y = 0;
			while (s.hasNextLine() && y < GRID_HEIGHT) {
				String str = s.nextLine();

				for (int x = 0; x < GRID_WIDTH; x++) {
					char type = str.charAt(x);
					Point2D p = new Point2D(x, y);
					GameElement element = GameElement.newInstanceByType(type, p);
					if (element != null)
						board.setElement(p, element);
				}
				y++;
			}

			//Characters Data
			while (s.hasNextLine()) {
				String str = s.nextLine();
				String[] data = str.split(" ");
				Point2D p = new Point2D(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
				GameElement element = GameElement.newInstanceByType(data[0], p);
				if (element == null) break;

				if (element instanceof Fireman) {
					fireman = (Fireman)element;
				}
				else if (element instanceof Bulldozer) {
					bulldozer = (Bulldozer)element;
				}

				board.setElement(p, element);
			}

			s.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}