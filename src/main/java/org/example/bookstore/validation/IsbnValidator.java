package org.example.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }

        return switch (isbn.length()) {
            case 10 -> isValidIsbn10(isbn);
            case 13 -> isValidIsbn13(isbn);
            default -> false;
        };
    }

    private boolean isValidIsbn10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }

        char checkDigit = isbn.charAt(9);
        if (checkDigit != 'X' && !Character.isDigit(checkDigit)) {
            return false;
        }

        sum += (checkDigit == 'X') ? 10 : (checkDigit - '0');
        return sum % 11 == 0;
    }

    private boolean isValidIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = isbn.charAt(12) - '0';
        int calculatedCheckDigit = 10 - (sum % 10);
        if (calculatedCheckDigit == 10) {
            calculatedCheckDigit = 0;
        }

        return checkDigit == calculatedCheckDigit;
    }
}
