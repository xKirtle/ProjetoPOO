package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final String levelPath = "levels/example.txt";
	private static GameEngine Instance;
	
	private ImageMatrixGUI gui;
	private Fireman fireman;
	private Bulldozer bulldozer;
	private IMovable activeElement;
	private Plane plane;
	
	public GameBoard board;

	public GameEngine() {
		 
		gui = ImageMatrixGUI.getInstance();	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); 
		gui.registerObserver(this);
		gui.go();
		
		Instance = this;
		board = new GameBoard(GRID_WIDTH, GRID_HEIGHT);
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
				
				validMove = true;
			}
		}
		
		if (key == KeyEvent.VK_ENTER && fireman.getPosition().equals(bulldozer.getPosition())) {
			if (activeElement instanceof Fireman) {
				activeElement = bulldozer;
				board.removeElement(fireman.getPosition(), fireman);
			}
			else if (activeElement instanceof Bulldozer) {
				activeElement = fireman;
				board.setElement(fireman.getPosition(), fireman);
			}
			
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
			
			//gui.removeImage() was not displaying changes on gui.update()...?
			gui.clearImages();
			board.sendBoardToGUI();
		}
	}

	public void start() {
		readLevelData();
		activeElement = fireman;
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
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static GameEngine getInstance() {
		return Instance;
	}
}