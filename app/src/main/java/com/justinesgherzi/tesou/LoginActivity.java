package com.justinesgherzi.tesou;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    Bdd bdd = new Bdd();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.idEditText);
    }

    public void onClickSeConnecter(View view) {
        Log.i("EditText", editText.getText().toString());


        // Instanciation de la classe base de données
        idUser = editText.getText().toString();
        Intent intent = new Intent(LoginActivity.this, GoogleMapActivity.class);
        intent.putExtra("IdUtilisateur", idUser);
        startActivity(intent);
        // bdd.ConnexionBdd(idUser);


        // Ajouter la vérification si l'utilisateur existe ou non
        // s'il existe -> lancer activité
        Intent monIntent = new Intent(this, GoogleMapActivity.class);
        startActivity(monIntent);
        // si pas -> afficher message d'erreur (Utilisateur inconnu)
    }
}
