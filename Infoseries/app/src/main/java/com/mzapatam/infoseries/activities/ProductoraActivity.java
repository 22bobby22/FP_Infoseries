package com.mzapatam.infoseries.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Productora;

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

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_productora);

        imagen = findViewById(R.id.imagen);
        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fecha);
        producciones = findViewById(R.id.producciones);
        descripcion = findViewById(R.id.descripcion);

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
        }
    }

}
