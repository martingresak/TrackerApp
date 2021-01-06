package com.example.trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();

        logout_btn = findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(this::onClick);
    }

    private void signOut() {
        mAuth.signOut();
    }

    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.logout_btn) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            signOut();
            finishAffinity();
            startActivity(intent);

        }
    }
}