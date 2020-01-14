package com.mzapatam.infoseries.comparators;

import com.mzapatam.infoseries.models.Serie;

import java.util.Comparator;

public class CustomComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return Long.compare(((Serie)o1).getFecha(), ((Serie)o2).getFecha());
    }
}
