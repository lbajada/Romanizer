package com.lukebajada.romanizer;

import com.lukebajada.romanizer.enums.RomanNumeral;

/**
 * Utility class used to convert roman numerals to arabic numerals
 * and vice versa.
 */
public final class Romanizer {

    /**
     * Converts an arabic numeral to a non-compact roman numeral.
     * <p>
     * Ex. 4 -> IIII
     * 9 -> VIIII
     *
     * @param arabic The arabic numeral to convert
     * @return Returns the non-compact roman numeral
     */
    public static String convertToRoman(int arabic) {

        StringBuilder romanString = new StringBuilder();

        // Start from the largest Roman numeral
        for (RomanNumeral roman : RomanNumeral.getSortedRomans()) {

            // Find out how many times the roman numeral goes into the arabic numeral
            double floor = Math.floor(arabic / roman.getValue());

            // Append the roman numeral to the roman string for the amount of times
            // it can divide the arabic numeral
            for (int i = 0; i < floor; i++) {
                romanString.append(roman.name());
            }

            // Use the remainder of the arabic value and repeat the process
            arabic = arabic % roman.getValue();
        }

        return romanString.toString();
    }

    /**
     * Takes an arabic numeral, converts it to a non-compact roman
     * numeral, then converts it again to a compact roman numeral.
     * <p>
     * Ex. 4 -> IIII -> IV
     * 9 -> VIIII -> IX
     *
     * @param arabic The arabic numeral to convert to a compact roman numeral
     * @return The compact roman numeral
     */
    public static String convertToCompactRoman(int arabic) {

        // Convert to a non-compact roman string first
        String romanString = convertToRoman(arabic);

        StringBuilder compactRoman = new StringBuilder();

        for (int i = romanString.length() - 1; i >= 0; ) {

            // Since M is the largest roman numeral, you can have an infinite amount of them
            // without converting them to a compact version. Also, always check that there are
            // at least always 4 roman numerals left and that at least 3 next to each other are
            // of the same letter.
            if (i >= 3 && romanString.charAt(i) != RomanNumeral.M.getName()
                    && romanString.charAt(i) == romanString.charAt(i - 1)
                    && romanString.charAt(i - 1) == romanString.charAt(i - 2)
                    && romanString.charAt(i - 2) == romanString.charAt(i - 3)) {

                // If there are at least 5 roman numerals left check if promoting the 4th one
                // will become exactly as the 5th one. If so, go ahead and promote it and add
                // one of the first 3 non promoted ones in front of the promoted roman numeral
                //
                // Ex. VIIII -> Start from the right and check if promoting the 4th I becomes a V.
                // It does, so promote the V to an X, and move one of the I's in front of the X.
                // We will get an IX.
                if (i >= 4 && promote(romanString.charAt(i - 3)) == romanString.charAt(i - 4)) {
                    compactRoman.insert(0, "" + romanString.charAt(i) + promote(romanString.charAt(i - 4)));
                    i -= 5;
                } else {
                    // Similar to above, but we have 4 characters instead of 5.
                    // Ex. IIII -> Promote I to V, and move an I in front of it, making it an IV.
                    compactRoman.insert(0, "" + romanString.charAt(i) + promote(romanString.charAt(i - 3)));
                    i -= 4;
                }
            } else {
                // No compacting of the numerals needed, so just add what is given
                compactRoman.insert(0, "" + romanString.charAt(i));
                i--;
            }
        }

        return compactRoman.toString().isEmpty() ? romanString : compactRoman.toString();

    }

    /**
     *
     * Converts a compact roman string into an arabic numeral.
     *
     * @param roman The compact roman numeral string
     * @return Returns the equivalent arabic numeral result
     */
    public static int convertToArabic(String roman) {
        int total = 0;
        int current;
        int next;

        for (int i = 0; i < roman.length(); i++) {
            current = RomanNumeral.getValueByName(roman.charAt(i));

            if (i != roman.length() - 1) {
                next = RomanNumeral.getValueByName(roman.charAt(i + 1));
                if (current < next) {
                    total += next - current;
                    i++;
                } else {
                    total += current;
                }
            } else {
                total += current;
            }

        }

        return total;

    }

    /**
     * Validates a string to check if it is made up of a correct roman numeral
     * format. The correct roman format is assumed to be the compact one.
     * <p>
     * Adapted from: https://stackoverflow.com/a/267405/5026036
     *
     * @param roman The compact roman numeral to validate
     * @return Returns true if it is a valid roman numeral
     */
    public static boolean isValidCompactRoman(String roman) {
        return roman.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    /**
     * When converting to a compact roman, this method is used
     * to promoted roman numeral to the next level.
     * <p>
     * Ex. When having IIII, the third I is promoted to a V
     * in order to become IV.
     *
     * @param roman The roman numeral to promote
     * @return Returns the promoted roman numeral
     */
    private static char promote(char roman) {
        switch (RomanNumeral.findByName(roman)) {
            case I:
                return RomanNumeral.V.getName();
            case V:
                return RomanNumeral.X.getName();
            case X:
                return RomanNumeral.L.getName();
            case L:
                return RomanNumeral.C.getName();
            case C:
                return RomanNumeral.D.getName();
            case D:
                return RomanNumeral.M.getName();
            default:
                throw new IllegalArgumentException("Illegal character provided");
        }
    }

    private static void validateRoman(String roman) {
        if (!isValidCompactRoman(roman)) {
            throw new IllegalArgumentException("Invalid roman numeral.");
        }
    }


}
