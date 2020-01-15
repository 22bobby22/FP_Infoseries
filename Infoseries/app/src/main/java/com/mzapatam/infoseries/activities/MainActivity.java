package com.mzapatam.infoseries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.adapters.MainAdapter;
import com.mzapatam.infoseries.comparators.CustomComparator;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Serie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mainAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Object> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("series");
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

        databaseReference = firebaseDatabase.getReference("peliculas");
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        mainAdapter = new MainAdapter(this, dataList);
        recyclerView.setAdapter(mainAdapter);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (snapshot.getValue(Serie.class).getTemporadas() != 0) {
                        Serie serie = snapshot.getValue(Serie.class);
                        dataList.add(serie);
                    } else {
                        Pelicula pelicula = snapshot.getValue(Pelicula.class);
                        dataList.add(pelicula);
                    }
                }
                mainAdapter.notifyDataSetChanged();

                dataList.sort(new CustomComparator());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
