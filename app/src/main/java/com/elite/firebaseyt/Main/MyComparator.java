package com.elite.firebaseyt.Main;

import java.util.Comparator;
import java.util.Map;

public class MyComparator implements Comparator {

    Map map;

    public MyComparator(Map map) {
        this.map = map;
    }

    public int compare(Object o1, Object o2) {

        return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));

    }
}