package com.mzapatam.infoseries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.adapters.MainAdapter;
import com.mzapatam.infoseries.comparators.CustomComparator;
import com.mzapatam.infoseries.models.Contenido;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Productora;
import com.mzapatam.infoseries.models.Serie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference seriesReference;
    private DatabaseReference peliculasReference;
    private DatabaseReference productorasReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Contenido> dataList = new ArrayList<>();
    private Serie serie;
    private Pelicula pelicula;
    private Productora productora;
    String filterPattern = "";
    String username;
    private static final int RC_SIGN_IN = 1;
    private BottomNavigationView bottomNavigationView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        bottomNavigationView = findViewById(R.id.nav_view);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Collections.singletonList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_bookmarks:
                        Intent intent = new Intent(context, BookmarksActivity.class);
                        intent.putExtra("username", username);
                        context.startActivity(intent);
                        return true;
                }

                return false;
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (snapshot.getValue(Serie.class).getTemporadas() != 0) {
                        serie = snapshot.getValue(Serie.class);
                        if (!dataList.contains(serie))
                            dataList.add(serie);
                    } else if (snapshot.getValue(Pelicula.class).getDuracion() != null){
                        pelicula = snapshot.getValue(Pelicula.class);
                        if (!dataList.contains(pelicula))
                            dataList.add(pelicula);
                    } else if (snapshot.getValue(Productora.class).getProducciones() != 0) {
                        productora = snapshot.getValue(Productora.class);
                        if (!dataList.contains(productora))
                            dataList.add(productora);
                    }
                }
                mainAdapter.populateFullList();
                dataList.sort(new CustomComparator());
                mainAdapter.getFilter().filter(filterPattern);
                mainAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.menu_sign_out, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productorasReference = firebaseDatabase.getReference("productoras");
                productorasReference.addValueEventListener(valueEventListener);
                filterPattern = query;
                mainAdapter.getFilter().filter(filterPattern);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPattern = newText;
                mainAdapter.getFilter().filter(filterPattern);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void createRecyclerView() {
        seriesReference = firebaseDatabase.getReference("series");
        seriesReference.addValueEventListener(valueEventListener);
        peliculasReference = firebaseDatabase.getReference("peliculas");
        peliculasReference.addValueEventListener(valueEventListener);
        mainAdapter = new MainAdapter(this, dataList, username);
        recyclerView.setAdapter(mainAdapter);
    }

    private void onSignedInInitialize(String username) {
        this.username = username;
        createRecyclerView();
    }

    private void onSignedOutCleanup() {
        username = null;
        dataList.clear();
        if (mainAdapter != null) {
            mainAdapter.clearData();
            mainAdapter.notifyDataSetChanged();
        }
        detachListeners();
    }

    private void detachListeners() {
        if (seriesReference != null)
            seriesReference.removeEventListener(valueEventListener);
        if (peliculasReference != null)
            peliculasReference.removeEventListener(valueEventListener);
        if (productorasReference != null)
            productorasReference.removeEventListener(valueEventListener);
    }
}
