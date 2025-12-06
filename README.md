# 📱Tuition Management Android Application

**Module of the Tuition-Management-Information-Android-App Repository**

This folder contains the complete Android application module **MADCW**,
developed for managing tuition classes, teachers, students, materials,
attendance, and academic records in one unified mobile app.

> Built with **Android**, **Gradle (Kotlin DSL)**, and a fully
> resource-driven UI.

------------------------------------------------------------------------

## 🚀 Tech Stack

-   **Android (Java/Kotlin source under `app/src/main/java/com`)**
-   **Gradle Build System** (with **Gradle Wrapper**)
-   **Kotlin DSL** build scripts (`build.gradle.kts`)
-   **XML-based UI layouts**
-   **Navigation Components** (`res/navigation/`)
-   **ProGuard** for release optimization
-   **Android Backup & Data Extraction rules**

------------------------------------------------------------------------

## 📂 Project Structure (Summary)
```
    MADCW/
    │
    ├── app/                     
    │   ├── src/
    │   │   ├── main/
    │   │   │   ├── AndroidManifest.xml
    │   │   │   ├── java/com/...  
    │   │   │   └── res/                
    │   │   ├── test/
    │   │   └── androidTest/
    │   ├── build.gradle.kts
    │   └── proguard-rules.pro
    │
    ├── build.gradle.kts
    ├── gradle.properties
    ├── gradlew / gradlew.bat
    └── gradle/ 
```
------------------------------------------------------------------------

## 🎯 App Overview

### 🔐 Authentication & User Onboarding

-   **LoginActivity** (Launcher)
-   **SignUpActivity**

### 👨‍🏫 Teacher Management

-   Register Teacher
-   Manage Teachers
-   Assign Teachers

### 👩‍🎓 Student Management

-   Register Students
-   Manage Students
-   Assign Students to Classes

### 🏫 Class & Course Handling

-   MyClassesActivity (Admin/Teacher)
-   StudentMyClassesActivity
-   Upload/View course materials

### 📘 Assignments & Materials

-   Upload Assignments
-   Upload Course Materials
-   Students view materials

### 📸 QR Attendance System

-   **QRAttendanceActivity** --- scan QR
-   **StudentQRCodeActivity** --- show student QR
-   **AttendanceReportActivity** --- attendance reports

### 📝 Results & Assessments

-   EnterResultsActivity
-   ViewResultsActivity
-   StudentsResultsActivity

------------------------------------------------------------------------

## 🧩 Key Manifest Insights

-   Launcher Activity → **LoginActivity**
-   App Theme → **Theme.MADCW**
-   RTL enabled
-   Backup rules included
-   Multiple screens declared

------------------------------------------------------------------------

## 🎨 UI & Resource System

Includes: - `layout/` - `drawable/` - `mipmap/` - `navigation/` -
`values/`, `values-night/`, `values-land/`, `values-w600dp/`,
`values-w1240dp/`, `values-v23/`

Supports: - Night mode\
- Landscape\
- Multiple screen widths\
- API-level overrides

------------------------------------------------------------------------

## 📌 Confirmed vs Not Confirmed

### ✔️ Confirmed

-   Activities & flows
-   Navigation resources
-   Kotlin DSL Gradle config
-   ProGuard presence
-   Backup rules

### ❓ Not Confirmed (requires code inspection)

-   Java vs Kotlin source
-   Actual libraries (Firebase, Room, Retrofit, ZXing, etc.)
-   Database or backend strategy
-   Networking logic
-   Specific QR scanning library

------------------------------------------------------------------------

## 📘 Conclusion

The **MADCW** module is a complete Android app implementing a full
tuition management workflow --- including authentication,
student/teacher management, class operations, materials, QR attendance,
and results.\
It is built with Gradle Kotlin DSL, structured with modern Android
resource patterns, and configured for secure deployment.
