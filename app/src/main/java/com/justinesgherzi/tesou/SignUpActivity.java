package com.justinesgherzi.tesou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    public EditText editTextSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextSignUp = findViewById(R.id.editTextSignUp);
    }

    public void signUpOnClick(View view) {
        Bdd bdd = new Bdd();
        bdd.signUp(editTextSignUp.getText().toString());
        Toast.makeText(this, "Inscription réussie", Toast.LENGTH_LONG).show();
        finish();
    }
}
