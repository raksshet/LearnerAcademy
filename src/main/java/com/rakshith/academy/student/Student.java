package com.rakshith.academy.student;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.rakshith.academy.classroom.ClassRoom;

@Entity
public class Student {
	
	@Id
	@GeneratedValue
	private int student_id;
	private String student_fname;
	private String student_lname;
	@OneToOne
	private ClassRoom student_class;
	
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getStudent_fname() {
		return student_fname;
	}
	public void setStudent_fname(String student_fname) {
		this.student_fname = student_fname;
	}
	public String getStudent_lname() {
		return student_lname;
	}
	public void setStudent_lname(String student_lname) {
		this.student_lname = student_lname;
	}
	public ClassRoom getstudent_class() {
		return student_class;
	}
	public void setstudent_class(ClassRoom subject_class) {
		this.student_class = subject_class;
	}

}
