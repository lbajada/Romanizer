package com.lukebajada.romanizer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RomanizerTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/conversion-test-cases.csv")
    void testConvertToCompactRoman(int arabic, String roman) {
        assertEquals(roman, Romanizer.convertToCompactRoman(arabic));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/conversion-test-cases.csv")
    void testConvertToArabic(int arabic, String roman) {
        assertEquals(arabic, Romanizer.convertToArabic(roman));
    }

}
