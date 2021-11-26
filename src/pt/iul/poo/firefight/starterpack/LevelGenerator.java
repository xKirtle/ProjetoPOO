package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public class LevelGenerator {
	private static double Pine = 0.25;
	private static double Eucaliptus = 0.25;
	private static double Grass = 0.25;
	private static double Land = 0.25;

	public static void generateLevel(int width, int height, String levelName) {

		int y = 0;
		//Each level will have at least 1 fireman, 1 bulldozer and [1, 4[ Fires
		String[] level = new String[height + 2 + (int)(1 + Math.random() * 4)];
		while (y < height) {
			String str = "";

			double[] odds = {Pine, Eucaliptus, Grass, Land};
			Arrays.sort(odds);

			for (int x = 0; x < width; x++) {
				double value = Math.random();

				if (value < odds[0]) {
					str += "p";
				}
				else if (value < odds[0] + odds[1]) {
					str += "e";
				}
				else if (value < odds[0] + odds[1] + odds[2]) {
					str += "m";
				}
				else if (value < odds[0] + odds[1] + odds[2] + odds[3]) {
					str += "_";
				}

				level[y] = str + "\n";
			}
			y++;
		}

		List<Point2D> usedPoints = new ArrayList<Point2D>();

		Point2D fireman = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		level[y++] = "Fireman " + fireman.getX() + " " + fireman.getY() + "\n";
		usedPoints.add(fireman);

		Point2D bulldozer = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		while (usedPoints.contains(bulldozer))
			bulldozer = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
		level[y++] = "Bulldozer " + bulldozer.getX() + " " + bulldozer.getY() + "\n";
		usedPoints.add(bulldozer);

		int numberOfFires = level.length - y;
		for (int i = 0; i < numberOfFires; i++) {
			Point2D fire = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
			while (usedPoints.contains(fire) || level[fire.getY()].charAt(fire.getX()) == '_')
				fire = new Point2D((int)(Math.random() * width), (int)(Math.random() * height));
			
			level[y++] = "Fire " + fire.getX() + " " + fire.getY() + "\n";
			usedPoints.add(fire);
		}

		LevelGenerator.saveLevel(level, levelName);
	}

	private static void saveLevel(String[] levelData, String levelName) {
		File file = new File("levels/" + levelName + ".txt");
		try {
			if (!file.exists())
				file.createNewFile();
			
			FileWriter fw = new FileWriter(file);
			for (int i = 0; i < levelData.length; i++) {
				fw.write(levelData[i]);
			}
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
