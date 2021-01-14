package com.example.trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.MultiFactorResolver;



import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn, forgot_password_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout email, password;

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.login_image);
        sloganText = findViewById(R.id.login_slogan);
        logoText = findViewById(R.id.login_signin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login_btn = findViewById(R.id.login_button);
        forgot_password_btn = findViewById(R.id.forgot_password);



        mAuth = FirebaseAuth.getInstance();


        callSignUp.setOnClickListener(this::onClick);
        login_btn.setOnClickListener(this::onClick);
    }

    private void signIn(String email, String password) {

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this, CenterActivity.class);
                            intent.putExtra("EXTRA_STRING", "sleep");
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            // [START_EXCLUDE]
                            //checkForMultiFactorFailure(task.getException());
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //idk what
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
    }

    public void onClick(View v) {


        int i = v.getId();

        if (i == R.id.login_button) {
            TextView passwrdStr;
            TextView emailStr;
            try {
                emailStr = (TextView) email.getEditText();
                passwrdStr = (TextView) password.getEditText();

                signIn(emailStr.getText().toString(), passwrdStr.getText().toString());


            } catch (Exception e) {

                Toast.makeText(Login.this, "Email & password required.", Toast.LENGTH_LONG).show();
                return;
            }

        }
        else if (i == R.id.signup_screen) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"signup_image");
                pairs[1] = new Pair<View,String>(sloganText,"signup_slogan");
                pairs[2] = new Pair<View,String>(logoText,"signup_text");
                pairs[3] = new Pair<View,String>(email,"username");
                pairs[4] = new Pair<View,String>(password,"password");
                pairs[5] = new Pair<View,String>(login_btn,"signup_button");
                pairs[6] = new Pair<View,String>(callSignUp,"back_button");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                this.finish();
                startActivity(intent, options.toBundle());
            }

        else if (i == R.id.forgot_password) {
            try {
                String emailString = email.getEditText().getText().toString().trim();

                mAuth.sendPasswordResetEmail(emailString)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(Login.this, "Email sent", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Login.this, "Email failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            catch (Exception e) {
                Toast.makeText(Login.this, "Email required", Toast.LENGTH_SHORT).show();
            }


        }
    }
}