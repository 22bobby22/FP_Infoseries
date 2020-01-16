package com.mzapatam.infoseries.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Serie;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerieActivity extends AppCompatActivity {
    private Serie serie;
    private FirebaseStorage storage;
    private ImageView imagen;
    private TextView nombre;
    private TextView categorias;
    private TextView productora;
    private TextView fecha;
    private TextView temporadas;
    private TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_serie);

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        categorias = findViewById(R.id.categorias);
        productora = findViewById(R.id.productora);
        fecha = findViewById(R.id.fecha);
        temporadas = findViewById(R.id.temporadas);
        descripcion = findViewById(R.id.descripcion);

        if (getIntent().hasExtra("Serie")) {
            serie = (Serie) getIntent().getSerializableExtra("Serie");
            storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReferenceFromUrl(serie.getImagen());
            GlideApp.with(imagen.getContext()).load(storageReference).into(imagen);
            nombre.setText(serie.getNombre());

            String categoriasString = "";
            for (String string: serie.getCategorias().split(":"))
                categoriasString += string + " ";
            categorias.setText("Categorías: " + categoriasString);

            productora.setText(serie.getProductora());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(serie.getFecha());
            String fechaString = simpleDateFormat.format(date);
            fecha.setText("Fecha de estreno: " + fechaString);

            temporadas.setText("Temporadas: " + Integer.toString(serie.getTemporadas()));
            descripcion.setText("Descripción\n" + serie.getDescripcion());
        }
    }
}
