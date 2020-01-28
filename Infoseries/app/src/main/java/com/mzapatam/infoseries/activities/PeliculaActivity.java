package com.mzapatam.infoseries.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.Tools.DatabaseOperations;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Pelicula;

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
    private boolean isBookmarked;
    private DatabaseOperations databaseOperations;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_pelicula);

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        categorias = findViewById(R.id.categorias);
        productora = findViewById(R.id.productora);
        fecha = findViewById(R.id.fecha);
        duracion = findViewById(R.id.duracion);
        descripcion = findViewById(R.id.descripcion);
        botonDeMarcado = findViewById(R.id.bookmark);
        databaseOperations = new DatabaseOperations();
        isBookmarked = false;

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

            if (databaseOperations.isBookmarked(username, pelicula.getNombre())) {
                isBookmarked = true;
                botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
            }

            botonDeMarcado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBookmarked) {
                        botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
                        isBookmarked = false;
                        Toast.makeText(PeliculaActivity.this, "Bookmark deleted", Toast.LENGTH_SHORT).show();
                        databaseOperations.deleteBookmark(username, pelicula.getNombre());
                    } else {
                        botonDeMarcado.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                        isBookmarked = true;
                        Toast.makeText(PeliculaActivity.this, "Bookmark added", Toast.LENGTH_SHORT).show();
                        databaseOperations.addBookmark(username, "Pelicula", pelicula.getNombre());
                    }
                }
            });
        }
    }
}
