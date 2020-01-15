package com.mzapatam.infoseries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Serie;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ContentViewHolder> {
    private Context context;
    private List<Object> dataList;
    private FirebaseStorage storage;

    public MainAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        if (dataList.get(position) instanceof Serie) {
            Serie serie = (Serie) dataList.get(position);
            StorageReference storageReference = storage.getReferenceFromUrl(serie.getImagen());
            ImageView imageView = holder.imagen;

            holder.nombre.setText(serie.getNombre());
            holder.categorias.setText(serie.getCategorias().replaceAll(":", " | "));
            GlideApp.with(imageView.getContext()).load(storageReference).into(imageView);
            holder.tipo.setText("Serie");
        } else {
            Pelicula pelicula = (Pelicula) dataList.get(position);
            StorageReference storageReference = storage.getReferenceFromUrl(pelicula.getImagen());
            ImageView imageView = holder.imagen;

            holder.nombre.setText(pelicula.getNombre());
            holder.categorias.setText(pelicula.getCategorias().replaceAll(":", " | "));
            GlideApp.with(imageView.getContext()).load(storageReference).into(imageView);
            holder.tipo.setText("Pelicula");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imagen;
        TextView nombre;
        TextView categorias;
        TextView tipo;

        public ContentViewHolder(View view) {
            super(view);

            imagen = view.findViewById(R.id.imagen);
            nombre = view.findViewById(R.id.nombre);
            categorias = view.findViewById(R.id.categorias);
            tipo = view.findViewById(R.id.tipo);
        }
    }
}