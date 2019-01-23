package com.justinesgherzi.tesou;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bdd extends AppCompatActivity {

    public String NomDeBaseFirestore = "android-fc313";
    private boolean estInscrit;
    private ArrayList<ArrayListCustom> monArrayList = new ArrayList<>();
    private Callback callbackNotify;

    public void registerCallback(Callback callback){
        callbackNotify = callback;
    }

    public void compareUserId(final String IdUser) {
        FirebaseFirestore.getInstance().collection(NomDeBaseFirestore)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // getLocationOfUsers(document.getId());
                                Log.d("bdd", document.getId() + " " + IdUser);
                                if((document.getId()).equals(IdUser)) {
                                    Log.d("bdd.estInscrit ?", "oui");
                                    estInscrit = true;
                                    callbackNotify.call();
                                } else {
                                    Log.d("bdd.estInscrit ?", "non");
                                }
                            }
                        } else {
                            Log.d("bdd.estInscrit ?", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public boolean estInscrit(){
        Log.d("estInscrit", String.valueOf(estInscrit));
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

    public ArrayList<ArrayListCustom> getLocationOfUsers(){
        Date currentDate = Calendar.getInstance().getTime();
        currentDate.setTime(currentDate.getTime() - 60 *60000);
        FirebaseFirestore.getInstance().collection(NomDeBaseFirestore)
                .whereGreaterThanOrEqualTo("date", currentDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String idUser = document.getId();
                                double latitude = document.getDouble("latitude");
                                double longitude = document.getDouble("longitude");

                                ArrayListCustom monArrayListCustom = new ArrayListCustom(idUser, latitude, longitude);
                                monArrayList.add(monArrayListCustom);
                                // Log.d("informations", "IdUser :" + document.getId() + " Latitude : " + document.getDouble("latitude") + " Longitude : " + document.getDouble("longitude"));

                            }
                        }
                    }
                });
        return monArrayList;
    }

}
