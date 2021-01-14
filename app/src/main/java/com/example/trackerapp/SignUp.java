package com.example.trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    TextInputLayout full_name, username, email, password;
    Button signup_button, back_button;
    FirebaseAuth mAuth;
    private String emailString, passwordString, fullNameString, userNameString;
    private DatabaseReference rootRef;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance("https://trackerapp-emp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        signup_button = findViewById(R.id.signup_button);
        back_button = findViewById(R.id.back_button);
        full_name = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup_button.setOnClickListener(this::onClick);
        back_button.setOnClickListener(this::onClick);

    }


    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.signup_button){
            try {
                emailString = email.getEditText().getText().toString().trim();
                passwordString = password.getEditText().getText().toString().trim();
                fullNameString = full_name.getEditText().getText().toString().trim();
                userNameString = username.getEditText().getText().toString().trim();

            }
            catch (Exception e) {
                Toast.makeText(SignUp.this, "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
                return;
            }



            mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                assert user != null;
                                String userID = user.getUid();

                                rootRef.child(userID).child("fullName").setValue(fullNameString);
                                rootRef.child(userID).child("userName").setValue(userNameString);

                                Intent intent = new Intent(getApplicationContext(), CenterActivity.class);
                                intent.putExtra("EXTRA_STRING", "sleep");
                                SignUp.this.finish();
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        else if (i == R.id.back_button) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            this.finish();
            startActivity(intent);
        }
    }
}