package com.mzapatam.infoseries.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.models.Productora;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText edNombre;
    private EditText edImagen;
    private EditText edDescripcion;
    private EditText edProducciones;
    private EditText edFecha;
    private Button btAdd;
    private Productora productora = new Productora();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNombre = (EditText) findViewById(R.id.nombre);
        edImagen = (EditText) findViewById(R.id.imagen);
        edDescripcion = (EditText) findViewById(R.id.descripcion);
        edProducciones = (EditText) findViewById(R.id.temporadas);
        edFecha = (EditText) findViewById(R.id.fecha);
        btAdd = (Button) findViewById(R.id.button_add);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productora.setNombre(edNombre.getText().toString());
                productora.setImagen(edImagen.getText().toString());
                productora.setDescripcion(edDescripcion.getText().toString());

                int i = 0;
                try {
                    i = Integer.parseInt(edProducciones.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productora.setProducciones(i);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                long milliseconds = 0;
                try {
                    date = dateFormat.parse(edFecha.getText().toString());
                    milliseconds = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("tag", date.toString());
                productora.setFecha(milliseconds);

                databaseReference.child("series").push().setValue(productora);
            }
        });
    }
}
