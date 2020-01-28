package com.mzapatam.infoseries.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Pelicula;

import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeliculaActivity extends AppCompatActivity{
    private Pelicula pelicula;
    private FirebaseStorage storage;
    private String username;
    private ImageView imagen;
    private TextView nombre;
    private TextView categorias;
    private TextView productora;
    private TextView fecha;
    private TextView duracion;
    private TextView descripcion;
    private ImageButton botonDeMarcado;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usuariosReference;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_pelicula);
        firebaseDatabase = FirebaseDatabase.getInstance();
        usuariosReference = firebaseDatabase.getReference("usuarios");

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        categorias = findViewById(R.id.categorias);
        productora = findViewById(R.id.productora);
        fecha = findViewById(R.id.fecha);
        duracion = findViewById(R.id.duracion);
        descripcion = findViewById(R.id.descripcion);
        botonDeMarcado = findViewById(R.id.bookmark);

        if (getIntent().hasExtra("username"))
            username = getIntent().getStringExtra("username");

        if (getIntent().hasExtra("Pelicula")) {
            pelicula = (Pelicula) getIntent().getSerializableExtra("Pelicula");

            storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl(pelicula.getImagen());
            GlideApp.with(imagen.getContext()).load(storageReference).into(imagen);
            nombre.setText(pelicula.getNombre());

            String categoriasString = "";
            for (String string: pelicula.getCategorias().split(":"))
                categoriasString += string + " ";
            categorias.setText("Categorías: " + categoriasString);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(pelicula.getFecha());
            String fechaString = simpleDateFormat.format(date);
            fecha.setText("Fecha de estreno: " + fechaString);

            productora.setText(pelicula.getProductora());
            duracion.setText("Duración: " + (pelicula.getDuracion()));
            descripcion.setText("Descripción\n" + pelicula.getDescripcion());
        }

        //TODO: si el contenido ya está marcado, clicar el botón

        botonDeMarcado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: si el botón está clicado
                deleteBookmark();
                //TODO: si el botón no está clicado
                addBookmark();
            }
        });
    }

    private void addBookmark() {
        //TODO: si el usuario no existe
        addUser();

        //TODO: insert bookmark
    }

    private void deleteBookmark() {
        //TODO: delete bookmark
    }

    private void addUser() {
        //TODO: insert user
    }
}
