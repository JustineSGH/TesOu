package com.justinesgherzi.tesou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bdd extends AppCompatActivity {

    private String NomDeBaseFirestore = "android-fc313";
    private boolean estInscrit;

    public void ConnexionBdd(){
    }


    public boolean estInscrit(final String IdUser) {
        FirebaseFirestore.getInstance().collection(NomDeBaseFirestore)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("bdd", document.getId() + " " + IdUser);
                                if((document.getId()).equals(IdUser)) {
                                    Log.d("bdd.estInscrit ?", "oui");
                                    estInscrit=true;
                                } else {
                                    Log.d("bdd.estInscrit ?", "non");
                                }
                            }
                        } else {
                            Log.d("bdd.estInscrit ?", "Error getting documents.", task.getException());
                        }
                    }
                });

        if (estInscrit) {
            Log.d("bdd", "C'est bon ");
            return true;
        } else {
            Log.d("bdd", "C'est pas bon ");
            return false;
        }
    }

    public void PostDataInBdd(String IdUser, double longitude, double latitude, Date currentDate){
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(NomDeBaseFirestore).document(IdUser);
        Map<String, Object> maMap = new HashMap<>();
        maMap.put("longitude", longitude);
        maMap.put("latitude", latitude);
        maMap.put("date", currentDate);
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
