package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
	private static final long serialVersionUID = -7765416065759641852L;
	private String nameStu;
	private String idStu;
	private String yearBirth;
	private String sex;
	private String city;
	private double averageScore;
	private Class className;
	private ArrayList<Subject> listSubject;
	


	public void updateSubject(int index, Subject sub) {
		if (index >= this.listSubject.size())
			this.listSubject.add(sub);
		else this.listSubject.set(index, sub);
	}
	
	public void delSubject(int index) {
				this.listSubject.remove(index);	
		}
	

	public Student(String nameStu, String idStu, String yearBirth, String sex, String city) {
		super();
		this.nameStu = nameStu;
		this.idStu = idStu;
		this.yearBirth = yearBirth;
		this.sex = sex;
		this.city = city;
		this.listSubject = new ArrayList<Subject>();
	}
		

	public Student() {
		super();
		this.listSubject = new ArrayList<Subject>();
	}
	
	public ArrayList<Subject> getListSubject() {
		return listSubject;
	} 		
	
	public String getNameStu() {
		return nameStu;
	}

	public void setNameStu(String nameStu) {
		this.nameStu = nameStu;
	}
	public String getIdStu() {
		return idStu;
	}
	public void setIdStu(String idStu) {
		this.idStu = idStu;
	}
	public String getYearBirth() {
		return yearBirth;
	}
	public void setYearBirth(String yearBirth) {
		this.yearBirth = yearBirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getAverageScore() {
		int i = 0;
		double sum = 0;
		for(Subject sub : this.listSubject) {
			if(sub.getStatus().equals("Chưa đạt")) sum+=0;
			else sum += sub.getRealScore();
			i++;
		}
		sum /= i;
		return sum;
	}
	public Class getClassName() {
		return className;
	}
	public void setClassName(Class className) {
		this.className = className;
	}
	
	
	@Override
	public String toString() {
		return this.nameStu;
	}
}
