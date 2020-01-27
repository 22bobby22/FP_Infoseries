package com.mzapatam.infoseries.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mzapatam.infoseries.R;
import com.mzapatam.infoseries.activities.PeliculaActivity;
import com.mzapatam.infoseries.activities.ProductoraActivity;
import com.mzapatam.infoseries.activities.SerieActivity;
import com.mzapatam.infoseries.comparators.CustomComparator;
import com.mzapatam.infoseries.glide.GlideApp;
import com.mzapatam.infoseries.models.Contenido;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Productora;
import com.mzapatam.infoseries.models.Serie;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ContentViewHolder> implements Filterable {
    private Context context;
    private List<Contenido> dataList;
    private FirebaseStorage storage;
    private Contenido contenido;
    private Productora productora;
    private Serie serie;
    private Pelicula pelicula;
    private List<Contenido> filteredList;
    private List<Contenido> dataListFull;

    public MainAdapter(Context context, List<Contenido> dataList) {
        this.context = context;
        this.dataList = dataList;
        dataListFull = new ArrayList<>();
        filteredList = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
    }

    public void populateFullList() {
        for (Contenido c: dataList) {
            if (!dataListFull.contains(c))
                dataListFull.add(c);
        }
        dataListFull.sort(new CustomComparator());
    }

    public void clearFullList() {
        dataListFull.clear();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        contenido = dataList.get(position);
        StorageReference storageReference = storage.getReferenceFromUrl(contenido.getImagen());
        ImageView imageView = holder.imagen;
        holder.nombre.setText(contenido.getNombre());
        GlideApp.with(imageView.getContext()).load(storageReference).into(imageView);

        if (contenido instanceof Serie) {
            serie = (Serie) contenido;
            holder.tipo.setText("Serie");
            holder.categorias.setText(serie.getCategorias().replaceAll(":", " | "));
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SerieActivity.class);
                    intent.putExtra("Serie", (Serie) dataList.get(position));
                    context.startActivity(intent);
                }
            });
        } else if (contenido instanceof Pelicula) {
            pelicula = (Pelicula) contenido;
            holder.tipo.setText("Pelicula");
            holder.categorias.setText(pelicula.getCategorias().replaceAll(":", " | "));
            GlideApp.with(imageView.getContext()).load(storageReference).into(imageView);

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PeliculaActivity.class);
                    intent.putExtra("Pelicula", (Pelicula) dataList.get(position));
                    context.startActivity(intent);
                }
            });
        } else if (contenido instanceof Productora) {
            productora = (Productora) contenido;
            holder.tipo.setText("Productora");
            GlideApp.with(imageView.getContext()).load(storageReference).into(imageView);

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductoraActivity.class);
                    intent.putExtra("Productora", (Productora) dataList.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
             filteredList.clear();

            if (constraint == null || constraint.length() == 0)
                filteredList.addAll(dataListFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Contenido contenido: dataListFull)
                    if (contenido.getNombre().toLowerCase().contains(filterPattern))
                        filteredList.add(contenido);
            }

            FilterResults results = new FilterResults();
            filteredList.sort(new CustomComparator());
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parentLayout;
        ImageView imagen;
        TextView nombre;
        TextView categorias;
        TextView tipo;

        public ContentViewHolder(View view) {
            super(view);

            parentLayout = view.findViewById(R.id.parentLayout);
            imagen = view.findViewById(R.id.imagen);
            nombre = view.findViewById(R.id.nombre);
            categorias = view.findViewById(R.id.categorias);
            tipo = view.findViewById(R.id.tipo);
        }
    }
}