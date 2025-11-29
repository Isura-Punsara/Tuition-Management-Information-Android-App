package com.example.madcw;

public class ResultRecord {
    private String studentName;
    private String subject;
    private String teacherName;
    private String marks;

    public ResultRecord(String studentName, String subject, String teacherName, String marks) {
        this.studentName = studentName;
        this.subject = subject;
        this.teacherName = teacherName;
        this.marks = marks;
    }

    public String getStudentName() { return studentName; }
    public String getSubject() { return subject; }
    public String getTeacherName() { return teacherName; }
    public String getMarks() { return marks; }
}
