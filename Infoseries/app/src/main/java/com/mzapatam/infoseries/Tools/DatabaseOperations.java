package com.mzapatam.infoseries.Tools;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseOperations {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usuariosReference;

    public DatabaseOperations() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");
    }

    public boolean userExists(String username) {
        //TODO: si el usuario existe (GET)
        return false;
    }

    public void addUser(String username) {
        //TODO: insert user (INSERT)
    }

    public boolean isBookmarked(String username, String nombre) {
        if (userExists(username)) {
            //TODO: si el contenido está marcado (GET)
            return true;
        }
        return false;
    }

    public void addBookmark(String username, String tipo, String nombre) {
        if (!userExists(username))
            addUser(username);

        //TODO: insert bookmark (INSERT)
    }

    public void deleteBookmark(String username, String nombre) {
        //TODO: delete bookmark (DELETE)
    }
}
