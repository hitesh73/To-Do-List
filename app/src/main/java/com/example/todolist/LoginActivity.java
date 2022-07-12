package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText teUserEmail;
    private TextInputEditText teUserPassword;
    private TextView tvRegister;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        teUserEmail = findViewById(R.id.et_to_do_login_email);
        teUserPassword = findViewById(R.id.et_to_do_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.txt_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("please wait...");

        btnLogin.setOnClickListener(v -> {
            dialog.show();
            String email = teUserEmail.getText().toString();
            String password = teUserPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        });
        tvRegister.setOnClickListener(v -> {
            Intent in = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(in);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }
}