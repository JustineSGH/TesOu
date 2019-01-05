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

    private FirebaseFirestore firebaseFirestore;
    private String NomDeBaseFirestore = "android-fc313";


    public void ConnexionBdd(String idUser){

        firebaseFirestore = FirebaseFirestore.getInstance();

        Map<String, Object> maMap = new HashMap<>();

        maMap.put("premierclef", 123);
        maMap.put("secondeclef", "catherine");


        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection(NomDeBaseFirestore).document();
        documentReference1.set(maMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("just", "success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("just", "failure");
                    }
                });

    }
}
