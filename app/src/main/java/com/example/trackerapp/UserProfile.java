package com.example.trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Semaphore;

public class UserProfile extends AppCompatActivity{
    private FirebaseAuth mAuth;
    Button logout_btn, set_btn, edit_btn;
    ArrayAdapter<String> data_list;
    ArrayAdapter<String> goal_list;
    Spinner data_picker, goal_picker;
    private DatabaseReference mDatabase;
    String selected_data, fullName;
    FirebaseUser user;
    TextInputLayout full_name, user_email, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://trackerapp-emp-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child(user.getUid());





        //text fields fill
        full_name = findViewById(R.id.full_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);

        user_email.getEditText().setText(user.getEmail());
        full_name.getEditText().setText(fullName);

        //data_picker spinner
        data_picker = (Spinner) findViewById(R.id.data_picker);
        data_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"sleep", "mood", "exercise"});
        data_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        data_picker.setAdapter(data_list);


        //goal_picker spinner
        goal_picker = (Spinner) findViewById(R.id.goal_picker);
        goal_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"Sleep 8 hours daily for one week", "Exercise 30min daily for one week", "Sleep 8 hours daily for one month"});
        goal_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goal_picker.setAdapter(goal_list);


        logout_btn = findViewById(R.id.logout_btn);
        set_btn = findViewById(R.id.set_btn);
        edit_btn = findViewById(R.id.edit);

        set_btn.setOnClickListener(this::onClick);
        logout_btn.setOnClickListener(this::onClick);
        edit_btn.setOnClickListener(this::onClick);

    }

    @Override
    protected void onResume() {
        super.onResume();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullName = snapshot.child("fullName").getValue().toString();
                user_email.getEditText().setText(user.getEmail());
                full_name.getEditText().setText(fullName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fullName = "Database Error";
            }
        });

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
        else if (i == R.id.set_btn) {
           selected_data = data_picker.getSelectedItem().toString();

           Intent intent = new Intent(getApplicationContext(), CenterActivity.class);
           intent.putExtra("EXTRA_STRING", selected_data);
           this.finish();
           startActivity(intent);

        }
        else if (i == R.id.edit) {
            try {
                String tempName = full_name.getEditText().getText().toString().trim();
                mDatabase.child("fullName").setValue(tempName);
                full_name.getEditText().setText(tempName);
            } catch (Exception ignored) {
            }

            try {
                String tempEmail = user_email.getEditText().getText().toString().trim();
                user.updateEmail(tempEmail);
                user_email.getEditText().setText(tempEmail);
            } catch (Exception ignored) {
            }

            try {
                String tempPass = user_password.getEditText().getText().toString().trim();
                user.updatePassword(tempPass);
                user_password.getEditText().setText("");
            } catch (Exception ignored) {
            }
        }
    }

}