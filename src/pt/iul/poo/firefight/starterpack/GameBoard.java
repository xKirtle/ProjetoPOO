package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;

public class GameBoard {
	//TODO:Could just use an ArrayList<ArrayList<GameElement>> No need to define a max constant
	
	//2d array mapped onto a 1d array where each index has an array of GameElements. The objects tied to a coordinate
	
	private final int maxLayersPerTile = 5;
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
	
	public void setElement(int x, int y, GameElement[] element) {
		board[x * height + y] = element;
	}	
}
