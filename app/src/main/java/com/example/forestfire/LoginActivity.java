package com.example.forestfire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginActivity extends AppCompatActivity {

    EditText username, pass;
    Button login, signup, forget_pass;
    ACProgressFlower dialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.btn_lgn);
        signup = findViewById(R.id.btn_sign_up);
        forget_pass = findViewById(R.id.btn_forgotPass);
//        https://github.com/Cloudist/ACProgressLite
        dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.GRAY)
                .text("please wait")
                .fadeColor(Color.DKGRAY).build();

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentSignup);
                finish();
            }
        });

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResetPass = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intentResetPass.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentResetPass);
                finish();
            }
        });
    }

    private void userLogin() {
        final String email = username.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (email.isEmpty()) {
            username.setError("Email is required");
            username.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        if (password.length() < 6 || password.length() > 15) {
            pass.setError("Password should be of 6-15 characters");
            pass.requestFocus();
            return;
        }

        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                        finish();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("user", email);
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        pass.setText("");
                        Toast.makeText(LoginActivity.this, "Verify your email to login", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
