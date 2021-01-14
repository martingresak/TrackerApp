package com.example.trackerapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity{
    private FirebaseAuth mAuth;
    Button logout_btn, set_btn;
    ArrayAdapter<String> data_list;
    ArrayAdapter<String> goal_list;
    Spinner data_picker, goal_picker;
    private DatabaseReference mDatabase;
    String selected_data, cu;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


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

        set_btn.setOnClickListener(this::onClick);
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
        else if (i == R.id.set_btn) {
           selected_data = data_picker.getSelectedItem().toString();

           Intent intent = new Intent(getApplicationContext(), CenterActivity.class);
           intent.putExtra("EXTRA_STRING", selected_data);
           this.finish();
           startActivity(intent);

        }
        else if (i == R.id.edit) {

        }
    }

}