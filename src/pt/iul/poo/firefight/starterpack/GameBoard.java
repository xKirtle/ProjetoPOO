package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class GameBoard {	
	//2d array mapped onto a 1d array where each index has an array of GameElements. The objects tied to a coordinate
	
	private final int maxLayersPerTile = GameLayers.NumberOfLayers.toInt();
	private int width;
	private int height;
	private GameElement[][] board;
	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
		board = new GameElement[width * height][];
		
		for (int i = 0; i < board.length; i++) {
			GameElement[] tempArr = new GameElement[maxLayersPerTile];
			board[i] = tempArr;
		}
	}
	
	public GameElement[] getElements(int x, int y) {
		return board[x * height + y];
	}
	
	public GameElement[] getElements(Point2D p) {
		return getElements(p.getX(), p.getY());
	}
	
	public void setElement(int x, int y, GameElement element) {
		board[x * height + y][element.getLayer()] = element;
	}
	
	public void setElement(Point2D p, GameElement element) {
		setElement(p.getX(), p.getY(), element);
	}
	
	public void removeElement(int x, int y, GameElement element) {
		board[x * height + y][element.getLayer()] = null;
	}
	
	public void removeElement(Point2D p, GameElement element) {
		removeElement(p.getX(), p.getY(), element);
	}
	
	public void removeElement(int x, int y, GameLayers layer) {
		board[x * height + y][layer.toInt()] = null;
	}
	
	public void removeElement(Point2D p, GameLayers layer) {
		removeElement(p.getX(), p.getY(), layer);
	}
	
	public boolean coordWithinBoard(int x, int y) {
		if (x < 0 || x >= width || 
				y < 0 || y >= height) 
			return false;
		
		return true;
	}
	
	public boolean coordWithinBoard(Point2D p) {
		return coordWithinBoard(p.getX(), p.getY());
	}
	
	public void moveElement(Point2D oldPos, Point2D newPos, GameElement element) {
		board[oldPos.getX() * height + oldPos.getY()][element.getLayer()] = null;
		board[newPos.getX() * height + newPos.getY()][element.getLayer()] = element;
	}

	public void updateElements() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				GameElement elem = board[i][j];
				if (elem != null)
					elem.update();
			}
		}
	}
	
	public void sendBoardToGUI() {
		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null)
					gui.addImage(board[i][j]);
			}
		}
	}
	
	//TODO: Criar metodo generico..
	public GameElement fireAtPosition(Point2D p) {
		GameElement[] arr = getElements(p);
		for (GameElement elem : arr) {
			if (elem != null && elem instanceof Fire)
				return elem;
		}
		
		return null;
	}
	
	public GameElement waterAtPosition(Point2D p) {
		GameElement[] arr = getElements(p);
		for (GameElement elem : arr) {
			if (elem != null && elem instanceof Water)
				return elem;
		}
		
		return null;
	}
	
	public List<Integer> firesPerColumn() {
		Integer[] arr = new Integer[width];
		for	(int i = 0; i < width; i++) {
			arr[i] = 0;
			for	(int j = 0; j < height; j++) {
				Point2D p = new Point2D(i, j);
				if (fireAtPosition(p) != null)
					arr[i] += 1;
			}
		}

		return new ArrayList<Integer>(Arrays.asList(arr));
	}
	
	public boolean isGameOver() {
		List<Integer> arr = firesPerColumn();
		Integer maxValue = Collections.max(arr);
		return maxValue == 0;
	}
}