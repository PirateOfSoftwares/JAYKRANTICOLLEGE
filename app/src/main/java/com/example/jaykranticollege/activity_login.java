package com.example.jaykranticollege;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login extends AppCompatActivity {

    public EditText loginEmailId, logInpasswd;
    Button btnLogIn;
    TextView signup,forgot_password;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailId = findViewById(R.id.Email);
        logInpasswd = findViewById(R.id.paswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        forgot_password =findViewById(R.id.forgot);
        signup = findViewById(R.id.link_signup);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String email1 = user.getEmail();
                Toast.makeText(activity_login.this ,email1 + "User logged in as customer ", Toast.LENGTH_SHORT).show();
                Intent I = new Intent(activity_login.this, NextActivity.class);
                startActivity(I);
            } else {
                Toast.makeText(activity_login.this, "Login to continue", Toast.LENGTH_SHORT).show();
            }
        };
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_login.this, MainActivity.class));

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_login.this, RegisterActivity.class));
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = loginEmailId.getText().toString();
                String userPaswd = logInpasswd.getText().toString();
                if (userEmail.isEmpty()) {
                    loginEmailId.setError("Provide your Email first!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("Enter Password!");
                    logInpasswd.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(activity_login.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(activity_login.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(activity_login.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(activity_login.this, NextActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(activity_login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

