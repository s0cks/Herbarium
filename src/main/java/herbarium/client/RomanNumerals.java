package herbarium.client;

import java.util.LinkedHashMap;
import java.util.Map;

public final class RomanNumerals {
  private static final Map<String, Integer> ROMAN_NUMERALS = new LinkedHashMap<>();

  static {
    ROMAN_NUMERALS.put("M", 1000);
    ROMAN_NUMERALS.put("CM", 900);
    ROMAN_NUMERALS.put("D", 500);
    ROMAN_NUMERALS.put("CD", 400);
    ROMAN_NUMERALS.put("C", 100);
    ROMAN_NUMERALS.put("XC", 90);
    ROMAN_NUMERALS.put("L", 50);
    ROMAN_NUMERALS.put("XL", 40);
    ROMAN_NUMERALS.put("X", 10);
    ROMAN_NUMERALS.put("IX", 9);
    ROMAN_NUMERALS.put("V", 5);
    ROMAN_NUMERALS.put("IV", 4);
    ROMAN_NUMERALS.put("I", 1);
  }

  public static String get(int val) {
    String res = "";
    for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
      int matches = val / entry.getValue();
      res += repeat(entry.getKey(), matches);
      val = val % entry.getValue();
    }
    return res;
  }

  private static String repeat(String str, int n) {
    if (str == null) return null;
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < n; i++) {
      builder.append(str);
    }
    return builder.toString();
  }
}