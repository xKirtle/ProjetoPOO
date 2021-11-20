package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
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

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final String levelPath = "levels/example.txt";
	
	private ImageMatrixGUI gui;
	private Fireman fireman;
	private IActiveElement activeElement; //?
	
	private GameBoard board;

	public GameEngine() {
		 
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
		board = new GameBoard(GRID_WIDTH, GRID_HEIGHT);
	}

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();              // obtem o codigo da tecla pressionada
		
		if (key == KeyEvent.VK_ENTER)            // se a tecla for ENTER, manda o bombeiro mover
			fireman.move();			
		
		gui.update();                            // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}

	public void start() {
		readLevelData();
		sendImagesToGUI();
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
//				else if (data[0].toLowerCase().equals("bulldozer"))
//					bulldozer = (Bulldozer)element;
//				else if (data[0].toLowerCase().equals("fire"))
//					fireList.add(element.getPosition());

//				tileList.add(element);
				
				board.setElement(p, element);
				
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void sendImagesToGUI() {
		gui.addImages(board.exportBoard());
	}
}
