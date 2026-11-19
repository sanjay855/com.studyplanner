# AI Based Study Planner and Productivity Tracker

An Android-based academic productivity application designed to help students organize their subjects, manage study schedules, track learning progress, and improve their study habits through intelligent planning.

## About the Project

The **AI Based Study Planner and Productivity Tracker** is an Android application developed to simplify academic planning and help students manage their studies more effectively.

Students often struggle with managing multiple subjects, remembering examination dates, allocating sufficient study time, and maintaining consistent study habits.

This application aims to solve these problems by allowing students to create personalized study plans based on their academic details, subject difficulty, credits, examination dates, and available daily study hours.

The long-term vision is to develop an adaptive AI-powered study assistant that can analyze student progress and recommend improvements to their study schedules.

## Project Objectives

- Provide a centralized platform for academic planning.
- Allow students to manage their university, course, and semester information.
- Provide dynamic subject selection based on academic details.
- Allow students to assign difficulty levels and examination dates to subjects.
- Automatically generate personalized study schedules.
- Provide an integrated study timer.
- Track completed study sessions and academic progress.
- Introduce AI-based recommendations and adaptive scheduling.

## Technology Stack

| Component | Technology |
|---|---|
| Frontend | Android |
| Programming Language | Java |
| UI Development | XML |
| Backend | PHP |
| Database | MySQL |
| API Communication | REST API |
| Networking Library | Retrofit |
| JSON Parsing | Gson |
| Local Development Server | XAMPP |
| Database Management | phpMyAdmin |
| Development Tunnelling | ngrok |
| IDE | Android Studio |

## System Architecture

The application follows a client-server architecture.

```text
             ANDROID APPLICATION
                     |
                     |
              Android Activities
                     |
                     |
                Retrofit
                     |
                     |
                REST APIs
                     |
                     |
                PHP Backend
                     |
                     |
               Business Logic
                     |
                     |
                MySQL Database
```

### Architecture Components

**1. Android Application**

Handles the user interface, navigation, authentication, subject management, study planning, and progress presentation.

**2. Retrofit**

Handles communication between the Android application and the PHP backend through HTTP requests.

**3. PHP Backend**

Processes API requests, performs validation, handles application logic, and communicates with the database.

**4. MySQL Database**

Stores student information, universities, courses, semesters, subjects, study preferences, and generated study plans.

## Application Workflow

```text
                 SPLASH SCREEN
                       |
                       |
                SESSION CHECK
                       |
              -----------------
              |               |
           Logged In       Not Logged In
              |               |
              |             LOGIN
              |               |
              |           REGISTER
              |               |
              |           DASHBOARD
              |
              |
           DASHBOARD
              |
              |
        SUBJECT SELECTION
              |
              |
       STUDENT PROFILE FETCH
              |
              |
        LOAD SUBJECTS
              |
              |
     SET STUDY PREFERENCES
              |
              |
       SAVE SELECTED SUBJECTS
              |
              |
       GENERATE STUDY PLAN
              |
              |
            PLANNER
              |
              |
         STUDY TIMER
              |
              |
       UPDATE COMPLETION
              |
              |
        TRACK PROGRESS
```

## Implemented Features

### 1. Student Authentication
- Student registration.
- Student login.
- Session management using SharedPreferences.
- Splash screen with session checking.
- Automatic navigation based on login status.

### 2. Academic Profile Management
- University selection.
- Course selection.
- Semester selection.
- Daily study hours.
- Student profile retrieval through backend APIs.

### 3. Subject Management
- Dynamic subject retrieval based on course and semester.
- Subject listing using RecyclerView.
- Subject difficulty selection.
- Examination date selection.
- Saving selected subjects to the database.

### 4. Study Plan Generation
- Backend-based study plan generation.
- Subject-wise study time allocation.
- Retrieval of generated study schedules.
- Displaying allocated study time.

### 5. Study Timer
- Countdown-based study sessions.
- Pause and resume functionality.
- Finish study session functionality.
- Integration with the study plan.

### 6. Progress Tracking
- Study session completion tracking.
- Backend integration for updating completion status.
- Planner refresh after study sessions.

## Android Project Structure

```text
com.example.comstudyplanner
│
├── activities
│   ├── SplashActivity.java
│   ├── LoginActivity.java
│   ├── RegisterActivity.java
│   ├── DashboardActivity.java
│   ├── SubjectSelectionActivity.java
│   ├── PlannerActivity.java
│   └── StudyTimerActivity.java
│
├── adapters
│   ├── SubjectAdapter.java
│   └── StudyPlanAdapter.java
│
├── models
│   ├── University.java
│   ├── Course.java
│   ├── Semester.java
│   ├── Subject.java
│   ├── StudyPlan.java
│   ├── RegisterResponse.java
│   ├── SaveSubjectResponse.java
│   ├── StudentProfileResponse.java
│   ├── StudyPlanResponse.java
│   └── ProgressResponse.java
│
├── network
│   ├── ApiService.java
│   └── RetrofitClient.java
│
└── utils
    └── SessionManager.java
```

*Note: This represents the documented project structure. Update it if additional packages or classes have been introduced.*

## Backend API Structure

The PHP backend provides REST endpoints for communication with the Android application.

| API | Purpose |
|---|---|
| register.php | Student registration |
| login.php | Student authentication |
| getUniversities.php | Retrieve universities |
| getCourses.php | Retrieve courses |
| getSemesters.php | Retrieve semesters |
| getSubjects.php | Retrieve subjects |
| saveUserSubjects.php | Save selected subjects |
| getStudentProfile.php | Retrieve student academic profile |
| generateStudyPlan.php | Generate study schedule |
| getStudyPlan.php | Retrieve generated study plan |
| updateStudyProgress.php | Update study completion status |

All endpoints are consumed by the Android application through Retrofit.

## Database Structure

The project uses MySQL as its relational database.

Main database tables:

| Table | Purpose |
|---|---|
| users | Stores student account and academic information |
| universities | Stores university information |
| courses | Stores courses associated with universities |
| semesters | Stores semester information |
| subjects | Stores academic subjects |
| user_subjects | Stores student-selected subjects and preferences |
| study_plan | Stores generated study schedules and completion status |

### Database Relationships

```text
UNIVERSITIES
     |
     |
   COURSES
     |
     |
  SUBJECTS
     |
     |
 USER_SUBJECTS
     |
     |
  STUDY_PLAN

USERS
  |
  |---- USER_SUBJECTS
  |
  |---- STUDY_PLAN
```

## Installation and Setup

### Prerequisites

Install the following software:

- Android Studio
- Java Development Kit
- XAMPP
- phpMyAdmin
- ngrok
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
```

Open the project in Android Studio.

### 2. Configure the Backend

Copy the PHP backend folder into:

```text
C:\xampp\htdocs\studyplanner\
```

Start Apache and MySQL using XAMPP.

### 3. Configure the Database

1. Open phpMyAdmin.
2. Create a database named `studyplanner_db`.
3. Import the project's SQL schema and seed data.
4. Configure the database connection in the PHP backend.

### 4. Configure ngrok

Start your local PHP server and expose it using ngrok:

```bash
ngrok http 80
```

Copy the generated HTTPS forwarding URL.

### 5. Configure Retrofit

Update the base URL in `RetrofitClient.java`:

```java
private static final String BASE_URL =
        "https://YOUR_NGROK_DOMAIN/studyplanner/";
```

Ensure the URL ends with `/`.

### 6. Run the Application

1. Open the project in Android Studio.
2. Allow Gradle synchronization to complete.
3. Connect an Android device or start an emulator.
4. Verify that the backend is accessible.
5. Run the application.

## Future Enhancements

The following features are planned for future development:

### AI-Based Adaptive Study Planner
- Analyze student study patterns.
- Prioritize subjects based on examination dates.
- Consider subject difficulty and credits.
- Adjust schedules based on completed and missed sessions.
- Generate personalized study recommendations.

### Productivity Analytics
- Daily study statistics.
- Weekly and monthly reports.
- Subject-wise progress.
- Study time analysis.
- Productivity charts.

### Notifications and Reminders
- Upcoming examination reminders.
- Daily study reminders.
- Scheduled study session notifications.
- Revision reminders.

### Additional Features
- Calendar integration.
- Revision planning.
- Study streaks and achievements.
- Dark mode.
- Offline support.
- Exportable progress reports.

## Development Status

This project is actively under development.

The authentication, academic profile, subject selection, backend integration, study plan, and study timer foundations have been developed.

Progress tracking and the remaining productivity features are being integrated and refined.

The adaptive AI planning engine and advanced analytics are planned enhancements, not yet completed features.

## Project Vision

The ultimate goal is to transform this application from a basic study scheduling tool into an intelligent academic productivity assistant.

Rather than simply telling students what to study, the application aims to understand their academic requirements, monitor their study habits, identify areas that need attention, and continuously improve their study schedules.

**Plan Smarter. Study Better. Achieve More.**

---

## Author

**Developer:** YOUR NAME

**Project:** AI Based Study Planner and Productivity Tracker

**Platform:** Android

## License

This project is currently developed for educational and academic purposes. A formal open-source license can be added when the repository is ready for public distribution.
