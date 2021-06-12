package com.rakshith.academy.subject;

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
import com.rakshith.academy.teacher.Teacher;

@Entity
@Table(name = "SUBJECTS")
public class Subject {

	@Id
	@Column(name = "SUBJECT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subjectid;
	@Column(name = "SUBJECT_NAME", length = 64, nullable = false)
	private String subjectname;
	@Column(name = "SUBJECT_DESC", length = 128)
	private String subjectdesc;

	@ManyToMany(cascade = CascadeType.ALL)
	@Column(insertable = false, updatable = false)
	@JoinTable(name = "CLASSROOMS_SUBJECTS", joinColumns = @JoinColumn(name = "SUBJECT_ID"), inverseJoinColumns = @JoinColumn(name = "CLASSROOM_ID")

	)
	private Set<ClassRoom> classroom = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TEACHERS_SUBJECTS", joinColumns = @JoinColumn(name = "SUBJECT_ID"), inverseJoinColumns = @JoinColumn(name = "TEACHER_ID")

	)
	private Set<Teacher> teacher = new HashSet<>();

	public Subject(Integer subjectid, String subjectname, String subjectdesc, Set<ClassRoom> classroom,
			Set<Teacher> teacher) {
		super();
		this.subjectid = subjectid;
		this.subjectname = subjectname;
		this.subjectdesc = subjectdesc;
		this.classroom = classroom;
		this.teacher = teacher;
	}

	public Set<ClassRoom> getClassroom() {
		return classroom;
	}

	public void setClassroom(Set<ClassRoom> classroom) {
		this.classroom = classroom;
	}

	public Set<Teacher> getTeacher() {
		return teacher;
	}

	public void setTeacher(Set<Teacher> teacher) {
		this.teacher = teacher;
	}

	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String subjectname, String subjectdesc) {
		super();
		this.subjectname = subjectname;
		this.subjectdesc = subjectdesc;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getSubjectdesc() {
		return subjectdesc;
	}

	public void setSubjectdesc(String subjectdesc) {
		this.subjectdesc = subjectdesc;
	}

	public void addClassRoom(ClassRoom classroom) {
		this.classroom.add(classroom);

	}

}
