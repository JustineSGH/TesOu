package com.justinesgherzi.tesou;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private String idUser;

    public Button boutonConnexion;
    public EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.idEditText);

        // Instanciation de la classe base de données
        Bdd bdd = new Bdd();
        bdd.ConnexionBdd(idUser);
    }

    public void onClickSeConnecter(View view) {
        Log.i("EditText", editText.getText().toString());
    }
}
