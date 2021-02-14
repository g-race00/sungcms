package sungcms;

import java.time.LocalDate;
import java.util.Locale;

/** Validation utilities. */
public final class ValidationUtil {
    /** Prevent object creation. */
    private ValidationUtil() {
        // Empty
    }

    /** Make sure field is not empty. */
    public static void notEmpty(final String label, final String x) {
        if (x.isEmpty()) {
            throw new InvalidFieldException(label, String.format("%s is required.", 
                    capitalize(label)));
        }
    }

    /** Make sure field is not empty. */
    public static void notEmpty(final String label, final char[] x) {
        if (x.length == 0) {
            throw new InvalidFieldException(label, String.format("%s is required.", 
                    capitalize(label))); 
        }
    }

    /** Make sure x has minimum length of minLength. */
    public static String validMinLength(final String label, final String x, final int minLength) {
        if (x.length() < minLength) {
            throw new InvalidFieldException(label, String.format("%s length should be at least %d.",
                    capitalize(label), minLength));
        }
        return x;
    }

    /** Make sure x has username format. */
    public static String validUsernameFormat(final String label, final String x) {
        if (!x.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
            throw new InvalidFieldException(label, String.join("", capitalize(label),
                    " should start with alphabet, and can contain alphabets and numbers only."));
        }
        return x;
    }

    /** Make sure x has email format. */
    public static String validEmailFormat(final String label, final String x) {
        if (!x.matches(".+@.+\\..+")) {
            throw new InvalidFieldException(label, String.join("", capitalize(label),
                    " should have at least one '@' and '.' character."));
        }
        return x;
    }

    /** Make sure x has phone format. */
    public static String validPhoneFormat(final String label, final String x) {
        if (!x.matches("\\+?[0-9]{8,15}")) {
            throw new InvalidFieldException(label, String.join("", capitalize(label),
                    " should be 8 to 15 digits."));
        }
        return x;
    }

    /** Make sure x is not negative. */
    public static double notNegative(final String label, final double num) {
        if (num < 0) {
            throw new InvalidFieldException(label, capitalize(label) + " cannot be negative.");
        }
        return num;
    }

    /** Make sure d1 is less than or equals to d2. */
    public static void validDateRange(final String label, final LocalDate d1, final LocalDate d2) {
        if (d1.compareTo(d2) > 0) {
            // CHECKSTYLE:OFF
            throw new InvalidFieldException(label, capitalize(label) + " has incorrect range. The start date should be before or equal to end date.");
            // CHECKSTYLE:ON
        }
    }

    private static String capitalize(final String x) {
        return x.substring(0, 1).toUpperCase(Locale.US) + x.substring(1);
    }
}
