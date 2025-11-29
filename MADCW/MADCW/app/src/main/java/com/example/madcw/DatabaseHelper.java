package com.example.madcw;

import android.content.Context;
import  java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DB_NAME = "TuitionApp.db";
    private static final int DB_VERSION = 15;

    // User Table
    public static final String TABLE_USERS = "Users";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";

    // CourseMaterials Table Constants
    public static final String TABLE_COURSE_MATERIALS = "CourseMaterials";
    public static final String COL_COURSE_MATERIAL_ID = "id";
    public static final String COL_COURSE_MATERIAL_TITLE = "title";
    public static final String COL_COURSE_MATERIAL_FILE_PATH = "file_path";
    public static final String COL_COURSE_MATERIAL_TEACHER = "teacher_name";

    // Add table constants
    public static final String TABLE_RESULTS = "Results";
    public static final String COL_RESULT_ID = "id";
    public static final String COL_RESULT_STUDENT = "student_name";
    public static final String COL_RESULT_SUBJECT = "subject";
    public static final String COL_RESULT_TEACHER = "teacher_name";
    public static final String COL_RESULT_MARKS = "marks";

    // New Attendance Table
    public static final String TABLE_ATTENDANCE = "Attendance";
    public static final String COL_ATTENDANCE_ID = "id";
    public static final String COL_ATTENDANCE_STUDENT = "student_name";
    public static final String COL_ATTENDANCE_TEACHER = "teacher_name";
    public static final String COL_ATTENDANCE_DATE = "date";
    public static final String COL_ATTENDANCE_TIME = "time";

    // AssignedStudents Table
    public static final String TABLE_ASSIGNED = "AssignedStudents";
    public static final String COL_ASSIGNED_ID = "id";
    public static final String COL_ASSIGNED_STUDENT = "student_name";
    public static final String COL_ASSIGNED_TEACHER = "teacher_name";


    // Assignment Table Constants
    public static final String TABLE_ASSIGNMENTS = "Assignments";
    public static final String COL_ASSIGNMENT_ID = "id";
    public static final String COL_ASSIGNMENT_TITLE = "title";
    public static final String COL_ASSIGNMENT_DESC = "description";
    public static final String COL_ASSIGNMENT_FILE_PATH = "file_path";
    public static final String COL_ASSIGNMENT_TEACHER = "teacher_name";


    // Student Table
    public static final String TABLE_STUDENTS = "Students";
    public static final String COL_STUDENT_ID = "id";
    public static final String COL_STUDENT_NAME = "name";
    public static final String COL_STUDENT_PASSWORD = "password";
    public static final String COL_STUDENT_COURSE = "course";
    public static final String COL_STUDENT_AGE = "age";
    public static final String COL_STUDENT_QR = "qr_code";

    // Teacher Table
    public static final String TABLE_TEACHERS = "Teachers";
    public static final String COL_TEACHER_ID = "id";
    public static final String COL_TEACHER_NAME = "name";
    public static final String COL_TEACHER_PASSWORD = "password";
    public static final String COL_TEACHER_SUBJECT = "subject";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);



    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_ROLE + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);

        // Create Students Table
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_NAME + " TEXT NOT NULL, " +
                COL_STUDENT_PASSWORD + " TEXT NOT NULL, " +
                COL_STUDENT_COURSE + " TEXT NOT NULL, " +
                COL_STUDENT_AGE + " INTEGER, " +
                COL_STUDENT_QR + " TEXT)";
        db.execSQL(createStudentsTable);

        //create ResultTable
        String createResultsTable = "CREATE TABLE " + TABLE_RESULTS + " (" +
                COL_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RESULT_STUDENT + " TEXT NOT NULL, " +
                COL_RESULT_SUBJECT + " TEXT NOT NULL, " +
                COL_RESULT_TEACHER + " TEXT NOT NULL, " +
                COL_RESULT_MARKS + " TEXT NOT NULL)";
        db.execSQL(createResultsTable);

        // Create CourseMaterials Table
        String createCourseMaterialsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSE_MATERIALS + " (" +
                COL_COURSE_MATERIAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COURSE_MATERIAL_TITLE + " TEXT NOT NULL, " +
                COL_COURSE_MATERIAL_FILE_PATH + " TEXT NOT NULL, " +
                COL_COURSE_MATERIAL_TEACHER + " TEXT NOT NULL)";
        db.execSQL(createCourseMaterialsTable);

        //create uploadassignment table
        String createAssignmentsTable = "CREATE TABLE IF NOT EXISTS Assignments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "file_path TEXT, " +
                "teacher_name TEXT)";
        db.execSQL(createAssignmentsTable);

        // Create Attendance Table
        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                COL_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ATTENDANCE_STUDENT + " TEXT NOT NULL, " +
                COL_ATTENDANCE_TEACHER + " TEXT NOT NULL, " +
                COL_ATTENDANCE_DATE + " TEXT NOT NULL, " +
                COL_ATTENDANCE_TIME + " TEXT NOT NULL)";
        db.execSQL(createAttendanceTable);


        // Create Teachers Table
        String createTeachersTable = "CREATE TABLE " + TABLE_TEACHERS + " (" +
                COL_TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEACHER_NAME + " TEXT NOT NULL, " +
                COL_TEACHER_PASSWORD + " TEXT NOT NULL, " +
                COL_TEACHER_SUBJECT + " TEXT NOT NULL)";
        db.execSQL(createTeachersTable);

        //createasinedstudenttable
        String createAssignedTable = "CREATE TABLE " + TABLE_ASSIGNED + " (" +
                COL_ASSIGNED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ASSIGNED_STUDENT + " TEXT NOT NULL, " +
                COL_ASSIGNED_TEACHER + " TEXT NOT NULL)";
        db.execSQL(createAssignedTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_MATERIALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);

        onCreate(db);
    }

    // ---------------------- USERS TABLE ----------------------

    // Insert Admin or Teacher
    public boolean insertUser(String name, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PASSWORD, password);
        values.put(COL_ROLE, role);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Login for Admin or Teacher
    public boolean loginUser(String name, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE name = ? AND password = ? AND role = ?", new String[]{name, password, role});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // ---------------------- STUDENTS TABLE ----------------------

    public boolean insertStudent(String name, String password, String course, int age, String qrCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, name);
        values.put(COL_STUDENT_PASSWORD, password);
        values.put(COL_STUDENT_COURSE, course);
        values.put(COL_STUDENT_AGE, age);
        values.put(COL_STUDENT_QR, qrCode);
        long result = db.insert(TABLE_STUDENTS, null, values);
        return result != -1;
    }

    public boolean loginStudent(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS +
                " WHERE name = ? AND password = ?", new String[]{name, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
    public ArrayList<CourseMaterial> getAllCourseMaterials() {
        ArrayList<CourseMaterial> materials = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSE_MATERIALS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_COURSE_MATERIAL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE_MATERIAL_TITLE));
            String filePath = cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE_MATERIAL_FILE_PATH));
            materials.add(new CourseMaterial(id, title, filePath));
        }
        cursor.close();
        return materials;
    }


    public ArrayList<HashMap<String, String>> getAllStudentsRaw() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, course, age FROM " + TABLE_STUDENTS, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> student = new HashMap<>();
            student.put("id", String.valueOf(cursor.getInt(0)));
            student.put("name", cursor.getString(1));
            student.put("course", cursor.getString(2));
            student.put("age", String.valueOf(cursor.getInt(3)));
            list.add(student);
        }
        cursor.close();
        return list;
    }

    public void deleteStudent(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COL_STUDENT_NAME + "=?", new String[]{name});
    }



    public boolean updateStudent(String oldName, String newName, String newCourse, int newAge) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, newName);
        values.put(COL_STUDENT_COURSE, newCourse);
        values.put(COL_STUDENT_AGE, newAge);
        int rows = db.update(TABLE_STUDENTS, values, COL_STUDENT_NAME + "=?", new String[]{oldName});
        return rows > 0;
    }

    public String getStudentQRCode(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_STUDENT_QR + " FROM " + TABLE_STUDENTS +
                " WHERE " + COL_STUDENT_NAME + " = ?", new String[]{name});
        String qrCode = null;
        if (cursor.moveToFirst()) {
            qrCode = cursor.getString(0);
        }
        cursor.close();
        return qrCode;
    }

    public ArrayList<HashMap<String, String>> getResultsByStudent(String studentName) {
        ArrayList<HashMap<String, String>> resultsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_RESULT_SUBJECT + ", " + COL_RESULT_TEACHER + ", " + COL_RESULT_MARKS +
                " FROM " + TABLE_RESULTS + " WHERE " + COL_RESULT_STUDENT + " = ?", new String[]{studentName});

        while (cursor.moveToNext()) {
            HashMap<String, String> result = new HashMap<>();
            result.put("subject", cursor.getString(0));
            result.put("teacher", cursor.getString(1));
            result.put("marks", cursor.getString(2));
            resultsList.add(result);
        }
        cursor.close();
        return resultsList;
    }


    // ---------------------- TEACHERS TABLE ----------------------

    public boolean insertTeacher(String name, String password, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check for duplicate teacher name
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEACHERS + " WHERE " + COL_TEACHER_NAME + " = ?", new String[]{name});
        boolean nameExists = cursor.getCount() > 0;
        cursor.close();

        if (nameExists) {
            return false; // Already exists
        }

        ContentValues values = new ContentValues();
        values.put(COL_TEACHER_NAME, name);
        values.put(COL_TEACHER_PASSWORD, password);
        values.put(COL_TEACHER_SUBJECT, subject);
        long result = db.insert(TABLE_TEACHERS, null, values);

        return result != -1;
    }
    //insert attendance to table
    public boolean insertAttendance(String studentName, String teacherName, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Prevent duplicate attendance for same student/teacher/date
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTENDANCE +
                        " WHERE " + COL_ATTENDANCE_STUDENT + " = ? AND " + COL_ATTENDANCE_TEACHER + " = ? AND " + COL_ATTENDANCE_DATE + " = ?",
                new String[]{studentName, teacherName, date});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        if (exists) {
            return false; // Attendance already recorded today
        }

        ContentValues values = new ContentValues();
        values.put(COL_ATTENDANCE_STUDENT, studentName);
        values.put(COL_ATTENDANCE_TEACHER, teacherName);
        values.put(COL_ATTENDANCE_DATE, date);
        values.put(COL_ATTENDANCE_TIME, time);

        long result = db.insert(TABLE_ATTENDANCE, null, values);
        return result != -1;
    }

    public boolean loginTeacher(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEACHERS + " WHERE name = ? AND password = ?", new String[]{name, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


    public ArrayList<HashMap<String, String>> getAllTeachersRaw() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, subject FROM " + TABLE_TEACHERS, null);

        while (cursor.moveToNext()) {
            HashMap<String, String> teacher = new HashMap<>();
            teacher.put("name", cursor.getString(0));
            teacher.put("subject", cursor.getString(1));
            list.add(teacher);
        }
        cursor.close();
        return list;
    }

    public void deleteTeacher(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEACHERS, COL_TEACHER_NAME + "=?", new String[]{name});
        db.delete(TABLE_USERS, COL_NAME + "=? AND " + COL_ROLE + "=?", new String[]{name, "Teacher"});
    }

    public boolean updateTeacher(String oldName, String newName, String newSubject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TEACHER_NAME, newName);
        values.put(COL_TEACHER_SUBJECT, newSubject);
        int updated = db.update(TABLE_TEACHERS, values, COL_TEACHER_NAME + "=?", new String[]{oldName});

        ContentValues userValues = new ContentValues();
        userValues.put(COL_NAME, newName);
        db.update(TABLE_USERS, userValues, COL_NAME + "=? AND " + COL_ROLE + "=?", new String[]{oldName, "Teacher"});

        return updated > 0;
    }

    //assignstudents to teachers
    public boolean assignStudentToTeacher(String studentName, String teacherName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Prevent duplicates
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ASSIGNED +
                " WHERE student_name = ? AND teacher_name = ?", new String[]{studentName, teacherName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        if (exists) return false;

        ContentValues values = new ContentValues();
        values.put(COL_ASSIGNED_STUDENT, studentName);
        values.put(COL_ASSIGNED_TEACHER, teacherName);

        long result = db.insert(TABLE_ASSIGNED, null, values);
        return result != -1;
    }

    // Assignment Insert
    public boolean insertAssignment(String title, String filePath, String teacherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("file_path", filePath);
        values.put("teacher_name", teacherName);
        long result = db.insert("Assignments", null, values);
        return result != -1;
    }

    // Insert Course Material
    public boolean insertCourseMaterial(String title, String filePath, String teacherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COURSE_MATERIAL_TITLE, title);
        values.put(COL_COURSE_MATERIAL_FILE_PATH, filePath);
        values.put(COL_COURSE_MATERIAL_TEACHER, teacherName);
        long result = db.insert(TABLE_COURSE_MATERIALS, null, values);
        return result != -1;
    }

    // Insert or update results for a student & subject
    public boolean insertOrUpdateResult(String studentName, String subject, String teacherName, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if result already exists for that student + subject + teacher
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESULTS +
                        " WHERE " + COL_RESULT_STUDENT + " = ? AND " +
                        COL_RESULT_SUBJECT + " = ? AND " +
                        COL_RESULT_TEACHER + " = ?",
                new String[]{studentName, subject, teacherName});

        ContentValues values = new ContentValues();
        values.put(COL_RESULT_STUDENT, studentName);
        values.put(COL_RESULT_SUBJECT, subject);
        values.put(COL_RESULT_TEACHER, teacherName);
        values.put(COL_RESULT_MARKS, marks);

        if (cursor.moveToFirst()) {
            // Update existing
            int rows = db.update(TABLE_RESULTS, values,
                    COL_RESULT_STUDENT + "=? AND " +
                            COL_RESULT_SUBJECT + "=? AND " +
                            COL_RESULT_TEACHER + "=?",
                    new String[]{studentName, subject, teacherName});
            cursor.close();
            return rows > 0;
        } else {
            // Insert new
            cursor.close();
            long id = db.insert(TABLE_RESULTS, null, values);
            return id != -1;
        }
    }

    // Get all course materials uploaded by a teacher
    public ArrayList<CourseMaterial> getCourseMaterialsByTeacher(String teacherName) {
        ArrayList<CourseMaterial> materials = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COL_COURSE_MATERIAL_ID + ", " + COL_COURSE_MATERIAL_TITLE + ", " + COL_COURSE_MATERIAL_FILE_PATH +
                " FROM " + TABLE_COURSE_MATERIALS + " WHERE " + COL_COURSE_MATERIAL_TEACHER + " = ?", new String[]{teacherName});

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String filePath = cursor.getString(2);
            materials.add(new CourseMaterial(id, title, filePath));
        }
        cursor.close();
        return materials;
    }



    //getassigned students
    public ArrayList<String> getAssignedStudents(String teacherName) {
        ArrayList<String> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT student_name FROM " + TABLE_ASSIGNED +
                " WHERE teacher_name = ?", new String[]{teacherName});
        while (cursor.moveToNext()) {
            students.add(cursor.getString(0));
        }
        cursor.close();
        return students;
    }

    public ArrayList<String> getTeachersAssignedToStudent(String studentName) {
        ArrayList<String> teachers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT teacher_name FROM " + TABLE_ASSIGNED +
                " WHERE student_name = ?", new String[]{studentName});

        while (cursor.moveToNext()) {
            teachers.add(cursor.getString(0));
        }
        cursor.close();
        return teachers;
    }

    public List<ResultRecord> getAllResults() {
        List<ResultRecord> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT student_name, subject, teacher_name, marks FROM Results", null);

        if (cursor.moveToFirst()) {
            do {
                String student = cursor.getString(0);
                String subject = cursor.getString(1);
                String teacher = cursor.getString(2);
                String marks = cursor.getString(3);
                results.add(new ResultRecord(student, subject, teacher, marks));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }


    public String getSubjectByTeacher(String teacherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String subject = "N/A";
        Cursor cursor = db.rawQuery("SELECT " + COL_TEACHER_SUBJECT + " FROM " + TABLE_TEACHERS + " WHERE " + COL_TEACHER_NAME + " = ?", new String[]{teacherName});
        if (cursor.moveToFirst()) {
            subject = cursor.getString(0);
        }
        cursor.close();
        return subject;
    }
    // Define a simple model class for CourseMaterial
    public static class CourseMaterial {
        public int id;
        public String title;
        public String filePath;

        public CourseMaterial(int id, String title, String filePath) {
            this.id = id;
            this.title = title;
            this.filePath = filePath;
        }
    }
}
