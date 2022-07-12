package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButton = findViewById(R.id.floatingAction);

        mAuth = FirebaseAuth.getInstance();

        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddNotesActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.option_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("yes", ((dialogInterface, i) -> {
                        mAuth.signOut();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }))
                    .setNegativeButton("no", ((dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }))
                    .create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}