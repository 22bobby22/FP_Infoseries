package com.mzapatam.infoseries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.adapters.MainAdapter;
import com.mzapatam.infoseries.comparators.CustomComparator;
import com.mzapatam.infoseries.models.Contenido;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Productora;
import com.mzapatam.infoseries.models.Serie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Contenido> dataList = new ArrayList<>();
    private Serie serie;
    private Pelicula pelicula;
    private Productora productora;

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
                        serie = snapshot.getValue(Serie.class);
                        dataList.add(serie);
                    } else if (snapshot.getValue(Pelicula.class).getDuracion() != null){
                        pelicula = snapshot.getValue(Pelicula.class);
                        dataList.add(pelicula);
                    } else if (snapshot.getValue(Productora.class).getProducciones() != 0) {
                        productora = snapshot.getValue(Productora.class);
                        dataList.add(productora);
                    }
                }
                mainAdapter.notifyDataSetChanged();
                mainAdapter.populateFullList();
                dataList.sort(new CustomComparator());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item  = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                databaseReference = firebaseDatabase.getReference("productoras");
                databaseReference.addListenerForSingleValueEvent(valueEventListener);
                mainAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
