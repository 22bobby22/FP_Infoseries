package com.mzapatam.infoseries.activities;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.adapters.MainAdapter;
import com.mzapatam.infoseries.models.Contenido;
import com.mzapatam.infoseries.models.Marcador;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Productora;
import com.mzapatam.infoseries.models.Serie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private List<Contenido> dataList = new ArrayList<>();
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private LinearLayoutManager layoutManager;
    private DatabaseReference usuariosReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        username = getIntent().getStringExtra("username");
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");

        usuariosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        if (snapshot.child("usuario").getValue().equals(username)) {
                            if (snapshot.child("marcadores").exists()) {
                                for (DataSnapshot marcadorSnapshot : snapshot.child("marcadores").getChildren()) {
                                    Marcador marcador = new Marcador((String) marcadorSnapshot.child("nombre").getValue(), (String) marcadorSnapshot.child("tipo").getValue());
                                    addMarcadorToDataList(marcador);
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

        mainAdapter = new MainAdapter(this, dataList, username);
        recyclerView.setAdapter(mainAdapter);
    }

    private void addMarcadorToDataList(final Marcador marcador) {
        DatabaseReference reference = null;
        Query query;
        switch (marcador.getTipo()) {
            case "Serie":
                reference = firebaseDatabase.getReference("series");
                query = reference;

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if (snapshot.child("nombre").getValue().equals(marcador.getNombre())) {
                                    Serie serie = snapshot.getValue(Serie.class);
                                    dataList.add(serie);
                                    mainAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Pelicula":
                reference = firebaseDatabase.getReference("peliculas");
                query = reference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if (snapshot.child("nombre").getValue().equals(marcador.getNombre())) {
                                    Pelicula pelicula = snapshot.getValue(Pelicula.class);
                                    dataList.add(pelicula);
                                    mainAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Productora":
                reference = firebaseDatabase.getReference("productoras");
                query = reference;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if (snapshot.child("nombre").getValue().equals(marcador.getNombre())) {
                                    Productora productora = snapshot.getValue(Productora.class);
                                    dataList.add(productora);
                                    mainAdapter.notifyDataSetChanged();
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
    protected void onPause() {
        super.onPause();
        mainAdapter.clearData();
    }
}
