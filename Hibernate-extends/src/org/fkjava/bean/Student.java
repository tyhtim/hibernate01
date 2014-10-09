package org.fkjava.bean;

public class Student extends Person {

	private int classId;
	
	private String className;
	
	private int course;


	@Override
	public String toString() {
		return "Student [classId=" + classId + ", className=" + className
				+ ", course=" + course + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getPassword()="
				+ getPassword() + ", getBirthday()=" + getBirthday() + "]";
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

}
