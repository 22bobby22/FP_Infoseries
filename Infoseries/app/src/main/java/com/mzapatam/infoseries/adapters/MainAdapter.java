package com.mzapatam.infoseries.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mzapatam.infoseries.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ContentViewHolder> {
    private String[] datos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ContentViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(String[] datos) {
        this.datos = datos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        ContentViewHolder vh = new ContentViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(datos[position]);

    }

    @Override
    public int getItemCount() {
        return datos.length;
    }
}