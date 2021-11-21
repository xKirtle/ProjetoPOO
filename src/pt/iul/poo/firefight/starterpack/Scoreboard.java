package pt.iul.poo.firefight.starterpack;

enum ScoreType {
	Fireman_FireExtinguished(10),
	Plane_FireExtinguished(5),
	TileBurnt(-3), //Assign different values based on burnTime?
	TileBulldozed(-5),
	Fireman_Move(2),
	Bulldozer_Move(3),
	Plane_Summoned(-5);
	
	private final int scoreValue;
	private ScoreType(int scoreValue) {
		this.scoreValue = scoreValue;
	}
	
	public int getValue() {
		return scoreValue;
	}
}

public class Scoreboard {
	private static Scoreboard instance;
	private int score;
	
	private Scoreboard() {
		
	}
	
	public static Scoreboard getInstance() {
		if (instance == null)
			instance = new Scoreboard();
		
		return instance;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(ScoreType type) {
		score += type.getValue();
	}
}
