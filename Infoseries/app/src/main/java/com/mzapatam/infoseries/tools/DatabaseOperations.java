package com.mzapatam.infoseries.tools;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
    private boolean userExists;

    public DatabaseOperations() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");
    }

    public void addBookmark(final String username, final String tipo, final String nombre) {
        final Query query = usuariosReference;
        userExists = false;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        if (snapshot.child("usuario").getValue().equals(username)) {
                            String key = snapshot.getKey();
                            firebaseDatabase.getReference("usuarios/" + key + "/marcadores").push().setValue(new Marcador(nombre, tipo));
                            userExists = true;
                        }
                    }
                }
                if (!userExists) {
                    usuariosReference.push().setValue(new Usuario(username));
                    userExists = true;

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    if (snapshot.child("usuario").getValue().equals(username)) {
                                        String key = snapshot.getKey();
                                        firebaseDatabase.getReference("usuarios/" + key + "/marcadores").push().setValue(new Marcador(nombre, tipo));
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteBookmark(final String username, final String nombre, final String tipo) {
        Query query = usuariosReference;
        userExists = false;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        if (snapshot.child("usuario").getValue().equals(username)) {
                            for (DataSnapshot marcador: snapshot.child("marcadores").getChildren()) {
                                if (marcador.child("nombre").getValue().equals(nombre) && marcador.child("tipo").getValue().equals(tipo)) {
                                    String key = marcador.getKey();
                                    snapshot.child("marcadores").child(key).getRef().removeValue();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
