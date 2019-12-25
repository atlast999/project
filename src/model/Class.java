package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Class implements Serializable {
	private static final long serialVersionUID = 8151301559707168324L;
	private String nameClass;
	private ArrayList<Student> listStudent;

	public Class(String nameClass) {
		super();
		this.nameClass = nameClass;
		this.listStudent = new ArrayList<Student>();
	}
	public Class() {
		super();
		this.listStudent = new ArrayList<Student>();
	}

	public void delStudent(int index) {
			this.listStudent.remove(index);
		}
	public void addStudent(Student stu) {
		stu.setClassName(this);
		this.listStudent.add(stu);
	}
	public void updateStudent(int index, Student stud)
	{
		this.listStudent.set(index, stud);
	}
	public void sortStudByScore() {
		//this.listStudent.sort(comparing);
		
		Collections.sort(this.listStudent, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
			String scoreA = Double.toString(o1.getAverageScore());
			String scoreB = Double.toString(o2.getAverageScore());
			return scoreA.compareTo(scoreB);
			}
		});
		//Collections.reverse(this.listStudent);
	}
	
	public String getNameClass() {
		return nameClass;
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public ArrayList<Student> getListStudent() {
		return listStudent;
	}
	public void setListStudent(ArrayList<Student> listStudent) {
		this.listStudent = listStudent;
	}
	
	@Override
	public String toString() {
		return this.nameClass;
	}
}
