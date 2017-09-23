package com.lukebajada.romanizer.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Enum class used to represent roman numerals along
 * with their corresponding arabic value.
 *
 */
public enum RomanNumeral {

    I('I', 1),
    V('V', 5),
    X('X', 10),
    L('L', 50),
    C('C', 100),
    D('D', 500),
    M('M', 1000);

    private char name;
    private int value;

    // Sort RomanNumeral once at initialization
    private static final List<RomanNumeral> sortedRomans = new ArrayList<>();

    static {
        Collections.addAll(sortedRomans, values());
        sortedRomans.sort((o1, o2) -> (o1.value - o2.value) * -1);
    }

    RomanNumeral(char name, int value) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public char getName() {
        return name;
    }

    public static RomanNumeral findByName(char romanNumeral){
        return sortedRomans.stream().filter(r -> r.getName() == romanNumeral).findFirst().get();
    }

    public static int getValueByName(char romanNumeral){
        return sortedRomans.stream().filter(r -> r.getName() == romanNumeral).findFirst().get().getValue();
    }

    public static List<RomanNumeral> getSortedRomans() {
        return sortedRomans;
    }
}
