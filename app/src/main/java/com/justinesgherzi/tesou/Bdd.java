package com.justinesgherzi.tesou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Bdd extends AppCompatActivity {

    private String NomDeBaseFirestore = "android-fc313";
    private String IdUtilisateur;


    public void ConnexionBdd(String idUser){
        // IdUtilisateur = idUser;

        /*Map<String, Object> maMap = new HashMap<>();

        maMap.put("longitude", 123);
        maMap.put("latitude", 456);

        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection(NomDeBaseFirestore).document(idUser);
        documentReference1.set(maMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("just", "success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("just", "failure");
                    }
                });*/

    }

    public void PostDataInBdd(String IdUser, double longitude, double latitude){
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(NomDeBaseFirestore).document("test");
        Map<String, Object> maMap = new HashMap<>();
        maMap.put("longitude", longitude);
        maMap.put("latitude", latitude);
        documentReference.set(maMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("just", "success");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("just", "failure");
                    }
                });
    }
}
