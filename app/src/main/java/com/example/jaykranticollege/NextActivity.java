package com.example.jaykranticollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NextActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    homeFragement homeFragment = new homeFragement();
    assignmentFragement assignmentFragement = new assignmentFragement();
    quizFragement quizFragement = new quizFragement();
    profileFragement profileFragement = new profileFragement();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();


        bottomNavigationView. setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.home:
                         getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                         return true;
                    case R.id.assignment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,assignmentFragement).commit();
                        return true;
                    case R.id.quiz:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,quizFragement).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragement).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent i1 = new Intent (this,aboutus.class);
                startActivity(i1);
                return true;

            case R.id.help:
                Intent i2 = new Intent (this,help.class);
                startActivity(i2);
                return true;

            case R.id.faq:
                Intent i3 = new Intent (this,faq.class);
                startActivity(i3);
                return true;

            case R.id.feedback:
                Intent i4 = new Intent (this,feedback.class);
                startActivity(i4);
                return true;

            case R.id.logout:
                Intent i5 = new Intent (this,logout.class);
                startActivity(i5);
                return true;

        }
        return true;
    }
}