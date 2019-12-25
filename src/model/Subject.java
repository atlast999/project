package model;

import java.io.Serializable;

public class Subject implements Serializable {
	private String nameSub;
	private double midScore;
	private double finalScore;
	private String status;
	private double realScore;
	
	public Subject(String subName, double midScore, double finalScore) {
		super();
		this.nameSub = subName;
		this.midScore = midScore;
		this.finalScore = finalScore;
	}
	
	public Subject() {
		super();
	}

	public String getSubName() {
		return nameSub;
	}
	public void setSubName(String subName) {
		this.nameSub = subName;
	}
	public double getMidScore() {
		return midScore;
	}
	public void setMidScore(double midScore) {
		this.midScore = midScore;
	}
	public double getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
	
	public double getRealScore() {
		return (this.midScore*0.3 + this.finalScore*0.7);
	}
	public String getStatus() {
		if(this.getRealScore() < 4 || this.midScore < 3 || this.finalScore < 3) return "Chưa đạt";
		else return "Đạt";
	}
	
	@Override
	public String toString() {
		return this.nameSub;
	}
}
