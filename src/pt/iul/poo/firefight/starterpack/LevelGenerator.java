package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public class LevelGenerator {
	private static double Pine = 0.25;
	private static double Eucaliptus = 0.25;
	private static double Grass = 0.25;
	private static double Land = 0.25;
	
	public static void generateLevel(int width, int height) {
		
		int y = 0;
		//Each level will have at least 1 fireman, 1 bulldozer and [1, 4[ Fires
		String[] level = new String[height + 2 + (int)(1 + Math.random() * 4)];
		while (y < height) {
			String str = "";
			for (int x = 0; x < width; x++) {
				int value = (int)(Math.random() * 20);
				
				if (value < 5) {
					str += "p";
				}
				else if (value < 10) {
					str += "e";
				}
				else if (value < 15) {
					str += "m";
				}
				else if (value < 20) {
					str += "_";
				}
				
				level[y] = str;
			}
			y++;
		}
		
		List<Point2D> usedPoints = new ArrayList<Point2D>();
		
		Point2D fireman = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		level[y++] = "Fireman " + fireman.getX() + " " + fireman.getY();
		usedPoints.add(fireman);
		
		Point2D bulldozer = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		while (usedPoints.contains(bulldozer))
			bulldozer = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		level[y++] = "Bulldozer " + bulldozer.getX() + " " + bulldozer.getY();
		usedPoints.add(bulldozer);
		
		//for i ate nmr de fogos, fazer loop
	}
	
	private static void saveLevel(String[] levelData) {
		//IO to save file
	}
}
