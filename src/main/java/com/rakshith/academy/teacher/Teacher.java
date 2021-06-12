package com.rakshith.academy.teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import com.rakshith.academy.classroom.ClassRoom;
import com.rakshith.academy.subject.Subject;

@Entity
@Table(name = "TEACHERS")
public class Teacher {

	@Id
	@Column(name = "TEACHER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer teacherid;
	@Column(name = "TEACHER_FNAME", length = 64, nullable = false)
	private String teacherfname;
	@Column(name = "TEACHER_LNAME", length = 64)
	private String teacherlname;

	@ManyToMany(mappedBy="teacher")
	private Set<Subject> subject = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CLASSROOMS_TEACHERS", joinColumns = @JoinColumn(name = "TEACHER_ID"), inverseJoinColumns = @JoinColumn(name = "CLASSROOM_ID")

	)
	private Set<ClassRoom> classroom = new HashSet<>();

	public Teacher(Integer teacherid, String teacherfname, String teacherlname, Set<Subject> subject,
			Set<ClassRoom> classroom) {
		super();
		this.teacherid = teacherid;
		this.teacherfname = teacherfname;
		this.teacherlname = teacherlname;
		this.subject = subject;
		this.classroom = classroom;
	}

	public Set<Subject> getSubject() {
		return subject;
	}

	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}

	public String getTeacherfname() {
		return teacherfname;
	}

	public void setTeacherfname(String teacherfname) {
		this.teacherfname = teacherfname;
	}

	public String getTeacherlname() {
		return teacherlname;
	}

	public void setTeacherlname(String teacherlname) {
		this.teacherlname = teacherlname;
	}

	public Set<ClassRoom> getClassroom() {
		return classroom;
	}

	public void setClassroom(Set<ClassRoom> classroom) {
		this.classroom = classroom;
	}

}
