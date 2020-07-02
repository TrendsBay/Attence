package com.trendsbay.atence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText StudentID, StudentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Don't know Application SKipping some frames in Start up so Just thing it needed
        Initialise(); // Initialising Variables with UI

    }
    private void Initialise(){
        StudentID = findViewById(R.id.StudentID);
        StudentPassword = findViewById(R.id.studentPassword);
    }

    private boolean compare(final String actual,final String Value) { return ( actual.equals(Value) ); }

    /*
    *   OnClick Method OF Give Attendance Button in activity_give_attendance
    */
    public void LogIn (View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean IsStudent = compare(StudentID.getText().toString(), "student") && compare(StudentPassword.getText().toString(), "123");
                boolean IsTeacher = compare(StudentID.getText().toString(), "teacher") && compare(StudentPassword.getText().toString(), "123");
                if ( IsStudent )   startActivity( new Intent(Login.this, GiveAttendance.class) ); // Started Next Activity For Student
                else if ( IsTeacher ) startActivity( new Intent(Login.this, TakeAttendance.class) ); // Started Next Activity For Teacher
                else Toast.makeText(Login.this, "You entered Wrong ID or password", Toast.LENGTH_SHORT).show();
            }
        }).start();

    }
}