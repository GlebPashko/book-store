package org.example.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final String ISBN_10_REGEX = "^\\d{9}[0-9X]$";
    private static final String ISBN_13_REGEX = "^97[89]\\d{10}$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }

        return Pattern.matches(ISBN_10_REGEX, isbn) || Pattern.matches(ISBN_13_REGEX, isbn);
    }
}
