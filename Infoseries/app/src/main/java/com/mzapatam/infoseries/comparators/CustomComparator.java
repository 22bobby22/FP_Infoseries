package com.mzapatam.infoseries.comparators;

import com.mzapatam.infoseries.models.Contenido;
import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Serie;

import java.util.Comparator;

public class CustomComparator implements Comparator<Contenido> {

    @Override
    public int compare(Contenido o1, Contenido o2) {
        return -Long.compare(o1.getFecha(), o2.getFecha());
    }
}
