package com.glow.sortit.user;

public class User {
	private int points;
	private int level;
	private int[] range = {5};
	private boolean scoreChanged;
	
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
	
	public boolean isScoreChanged() {
		return scoreChanged;
	}
	
	public void setScoreChanged(boolean scoreChanged) {
		this.scoreChanged = scoreChanged;
	}
}
