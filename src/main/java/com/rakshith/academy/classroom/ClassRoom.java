package com.rakshith.academy.classroom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

import com.rakshith.academy.subject.Subject;
import com.rakshith.academy.teacher.Teacher;

@Entity
@Table(name = "CLASSROOMS")
public class ClassRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLASSROOM_ID")
	private int classroomid;
	@Column(name = "CLASSROOM_NAME", nullable = false, unique = true, length = 32)
	private String classname;
	@Column(name = "CLASSROOM_CAPEX")
	private int classcapacity;

	@ManyToMany(mappedBy="classroom")
	private List<Subject> subject = new ArrayList<Subject>();
	
	@ManyToMany(mappedBy="classroom")
	private Set<Teacher> teachers = new HashSet<>();

	public ClassRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ClassRoom(int classroomid, String classname, int classcapacity, List<Subject> subject,
			Set<Teacher> teachers) {
		super();
		this.classroomid = classroomid;
		this.classname = classname;
		this.classcapacity = classcapacity;
		this.subject = subject;
		this.teachers = teachers;
	}
	
	



	public ClassRoom(String classname, int classcapacity) {
		super();
		this.classname = classname;
		this.classcapacity = classcapacity;
		
	}



	public List<Subject> getSubject() {
		return subject;
	}

	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	public ClassRoom(int classroomid, String classname, int classcapacity) {
		super();
		this.classroomid = classroomid;
		this.classname = classname;
		this.classcapacity = classcapacity;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getClasscapacity() {
		return classcapacity;
	}

	public void setClasscapacity(int classcapacity) {
		this.classcapacity = classcapacity;
	}
	
	public void addSubject(Subject subject) {
		this.subject.add(subject);
		
	}



	@Override
	public String toString() {
		return "ClassRoom [classroomid=" + classroomid + ", classname=" + classname + ", classcapacity=" + classcapacity
				+ ", subject=" + subject + ", teachers=" + teachers + "]";
	}

	
}
