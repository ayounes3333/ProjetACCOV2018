package com.aliyounes.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CameneonColor {
    BLEU,
    JAUNE,
    ROUGE;

    private static final List<CameneonColor> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static CameneonColor randomColor()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static CameneonColor getColor(String color) {
        if(color.equalsIgnoreCase(BLEU.name()))
            return BLEU;
        if(color.equalsIgnoreCase(JAUNE.name()))
            return JAUNE;
        return ROUGE;
    }
}
