package com.mzapatam.infoseries.Tools;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mzapatam.infoseries.models.Marcador;
import com.mzapatam.infoseries.models.Usuario;

public class DatabaseOperations {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usuariosReference;

    public DatabaseOperations() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");
    }

    public boolean userExists(String username) {
        //TODO: usuario existe? (GET)
        return true;
    }

    public void addUser(String username) {
        usuariosReference.push().setValue(new Usuario(username));
    }

    public boolean isBookmarked(String username, String nombre, String tipo) {
        Marcador marcador = new Marcador(nombre, tipo);

        if (userExists(username)) {
            //TODO: si el contenido está marcado (GET)
            return true;
        }
        return false;
    }

    public void addBookmark(final String username, String tipo, String nombre) {
        if (!userExists(username))
            addUser(username);

        Query query = usuariosReference;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        if (snapshot.child("usuario").getValue().equals(username)) {
                            String key = snapshot.getKey();
                            firebaseDatabase.getReference("usuarios/" + key + "/marcadores").push().setValue(new Marcador("Serie", "pew pew"));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteBookmark(String username, String nombre) {
        if (userExists(username)) {
            //TODO: delete bookmark (DELETE)
        }
    }
}
