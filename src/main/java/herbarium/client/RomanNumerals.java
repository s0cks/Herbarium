package herbarium.client;

public final class RomanNumerals{
    private static final String[] ROMAN_NUMERALS = new String[]{
            "I", "II", "III", "IV", "V",
            "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV",
            "XVI", "XVII", "XVIII", "XIX", "XX"
    };

    public static String get(int number){
        return ROMAN_NUMERALS[number];
    }
}