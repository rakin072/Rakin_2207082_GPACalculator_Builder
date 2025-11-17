package com.example.cgpacalculator.model;

public class {
    private String name;
    private String code;
    private double credit;
    private String teacher1;
    private String teacher2;
    private String grade;

    public Course(String name, String code, double credit, String teacher1, String teacher2, String grade) {
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.grade = grade;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public double getCredit() { return credit; }
    public String getTeacher1() { return teacher1; }
    public String getTeacher2() { return teacher2; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setCredit(double credit) { this.credit = credit; }
    public void setTeacher1(String teacher1) { this.teacher1 = teacher1; }
    public void setTeacher2(String teacher2) { this.teacher2 = teacher2; }
    public void setGrade(String grade) { this.grade = grade; }
}
