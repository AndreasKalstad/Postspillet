package user;

public class User {
	private int points;
	private int level;
	private int[] range = {1};
	
	public User(){
		points = 0;
		level = 0;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPoints() {
		return points;
	}

	public int getLevel() {
		return level;
	}

	public int getRange() {
		return range[level];
	}
}
