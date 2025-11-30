package com.example.attendance_server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(unique = true, nullable = false)
    private String roll;   // PRIMARY KEY

    private String name;
    private String course;
    private String semester;
    private String email;
    private String year;

    public Student() {}

    public Student(String roll, String name, String course, String semester, String email, String year) {
        this.roll = roll;
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.email = email;
        this.year = year;
    }

    public String getRoll() {
        return roll;
    }
    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
}