package com.mzapatam.infoseries.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Productora;
import com.mzapatam.infoseries.tools.DatabaseOperations;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductoraActivity extends AppCompatActivity {
    private Productora productora;
    private FirebaseStorage storage;
    private ImageView imagen;
    private TextView nombre;
    private TextView fecha;
    private TextView producciones;
    private TextView descripcion;
    private String username;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usuariosReference;
    private DatabaseOperations databaseOperations;
    private ImageButton botonDeMarcado;
    private boolean isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_productora);
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fecha);
        producciones = findViewById(R.id.producciones);
        descripcion = findViewById(R.id.descripcion);
        botonDeMarcado = findViewById(R.id.bookmark);
        databaseOperations = new DatabaseOperations();
        isBookmarked = false;

        if (getIntent().hasExtra("username"))
            username = getIntent().getStringExtra("username");

        if (getIntent().hasExtra("Productora")) {
            productora = (Productora) getIntent().getSerializableExtra("Productora");
            storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl(productora.getImagen());
            GlideApp.with(imagen.getContext()).load(storageReference).into(imagen);
            nombre.setText(productora.getNombre());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(productora.getFecha());
            String fechaString = simpleDateFormat.format(date);
            fecha.setText("Fecha de creación: " + fechaString);

            producciones.setText("Producciones: " + Integer.toString(productora.getProducciones()));
            descripcion.setText("Descripción\n" + productora.getDescripcion());

            Query query = usuariosReference;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if (snapshot.child("usuario").getValue().equals(username)) {
                                for (DataSnapshot marcador: snapshot.child("marcadores").getChildren()) {
                                    if (marcador.child("nombre").getValue().equals(productora.getNombre()) && marcador.child("tipo").getValue().equals("Productora")) {
                                        isBookmarked = true;
                                        botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                                    }
                                }
                            }
                        }
                    }
                    botonDeMarcado.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isBookmarked) {
                                botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                                isBookmarked = false;
                                Toast.makeText(ProductoraActivity.this, "Bookmark deleted", Toast.LENGTH_SHORT).show();
                                databaseOperations.deleteBookmark(username, productora.getNombre(), "Productora");
                            } else {
                                botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                                isBookmarked = true;
                                Toast.makeText(ProductoraActivity.this, "Bookmark added", Toast.LENGTH_SHORT).show();
                                databaseOperations.addBookmark(username, "Productora", productora.getNombre());
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
