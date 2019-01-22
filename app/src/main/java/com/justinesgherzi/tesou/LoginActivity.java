package com.justinesgherzi.tesou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity implements Callback {
    private String idUser;
    public EditText editText;
    private Bdd bdd = new Bdd();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = findViewById(R.id.idEditText);

        String idUser = RecupererInfoFichier();
        if (idUser != null) {
            Intent monIntent = new Intent(LoginActivity.this, GoogleMapActivity.class);
            monIntent.putExtra("IdUtilisateur", idUser);
            startActivity(monIntent);
        }
    }

    public void onClickSeConnecter(View view) {
        Log.i("EditText", editText.getText().toString());
        idUser = editText.getText().toString();

        bdd.registerCallback(this);
        bdd.compareUserId(idUser);
    }


    public void ecritureDansFichier(String idUser) {
        //Déclaration des variables
        File file = new File(getFilesDir(), "estConnecte.txt");
        String retourALALigne = System.getProperty("line.separator");


        //Ouverture du fichier en ecriture, et écriture à la fin de celui-ci (true)
        try (FileOutputStream monFileOutputStream = new FileOutputStream(file, true)) {


            //Transformation de la valeur des variables en octet
            byte[] IdUserBytes = idUser.getBytes();

            //Ecriture dans le fichier
            monFileOutputStream.write(IdUserBytes);
            monFileOutputStream.write(retourALALigne.getBytes());
            monFileOutputStream.flush();

            //fermeture du fichier
            monFileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String RecupererInfoFichier(){
        //Déclaration des variables
        String idUser = null;
        int i = 0;
        //Ouverture du fichier en lecture
        try {
            FileInputStream monFileInputStream = openFileInput("estConnecte.txt");
            InputStreamReader monInputStreamReader = new InputStreamReader(monFileInputStream);
            BufferedReader monBufferedReader = new BufferedReader(monInputStreamReader);

            do {
                i++;
                //Enregistrement des lignes du fichier dans les variables
                idUser = monBufferedReader.readLine();

                //Fermeture du fichier
                monFileInputStream.close();

            } while (i<1);
        } catch (Exception e) {}

        return idUser;
    }

    public void supprimerFichier() {
        File dir = getFilesDir();
        File file = new File(dir, "estConnecte.txt");
        boolean deleted = file.delete();
        Log.d("supression fichier" , String.valueOf(deleted));
    }

    @Override
    public void call() {
        ecritureDansFichier(idUser);

        bdd.getLocationOfUsers();

        Intent monIntent = new Intent(LoginActivity.this, GoogleMapActivity.class);
        monIntent.putExtra("IdUtilisateur", idUser);
        startActivity(monIntent);

    }

    public void logoutUser(){
        finish();
    }
}