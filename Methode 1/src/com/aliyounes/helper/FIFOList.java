package com.aliyounes.helper;

import java.util.*;

public class FIFOList<T> {
    private List<T> items = new ArrayList<>();

    public synchronized boolean empiler(T object) {
        return items.add(object);
    }

    public synchronized T depiler() {
        return items.remove(0);
    }

    public synchronized T voirElementCourante() {
        return items.get(0);
    }
}
