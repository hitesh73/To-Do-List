package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText teUserName;
    private TextInputEditText teUserEmail;
    private TextInputEditText teUserPassword;
    private TextInputEditText teUserConfirmPassword;
    private Button btnRegistration;
    private TextView tvLogin;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        teUserName = findViewById(R.id.et_to_do_name);
        teUserEmail = findViewById(R.id.et_to_do_email);
        teUserPassword = findViewById(R.id.et_to_do_password);
        teUserConfirmPassword = findViewById(R.id.et_to_do_confirm_password);

        btnRegistration = findViewById(R.id.btn_register);

        tvLogin = findViewById(R.id.txt_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setTitle("creating account");
        dialog.setMessage("please wait...");
        btnRegistration.setOnClickListener(v -> {
            String name = teUserName.getText().toString();
            String email = teUserEmail.getText().toString();
            String password = teUserPassword.getText().toString();
            String confirmPassword = teUserConfirmPassword.getText().toString();

            dialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Users users = new Users(name, email, password, confirmPassword);
                                String userId = task.getResult().getUser().getUid();
                                database.getReference().child("users").child(userId).setValue(users);
                                Intent in = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(in);
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}