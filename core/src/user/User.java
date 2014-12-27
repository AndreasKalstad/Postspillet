package user;

public class User {
	private int points;
	private int level;
	private int[] range = {5};
	
	public User(){
		points = 0;
		level = 0;
	}
	
	public void increasePoints() {
		this.points += 40;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPoints() {
		String pointsString = String.valueOf(points);
		return pointsString;
	}

	public int getLevel() {
		return level;
	}

	public int getRange() {
		return range[level];
	}
}
