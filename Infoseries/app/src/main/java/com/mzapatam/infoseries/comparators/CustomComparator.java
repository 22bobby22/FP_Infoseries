package com.mzapatam.infoseries.comparators;

import com.mzapatam.infoseries.models.Pelicula;
import com.mzapatam.infoseries.models.Serie;

import java.util.Comparator;

public class CustomComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Serie && o2 instanceof Serie)
            return -Long.compare(((Serie) o1).getFecha(), ((Serie) o2).getFecha());
        if (o1 instanceof Serie && o2 instanceof Pelicula)
            return -Long.compare(((Serie) o1).getFecha(), ((Pelicula) o2).getFecha());
        if (o1 instanceof Pelicula && o2 instanceof Serie)
            return -Long.compare(((Pelicula) o1).getFecha(), ((Serie) o2).getFecha());
        if (o1 instanceof Pelicula && o2 instanceof Pelicula)
            return -Long.compare(((Pelicula) o1).getFecha(), ((Pelicula) o2).getFecha());
        return 0;
    }
}
