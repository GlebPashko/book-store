package org.example.bookstore.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.bookstore.validation.Email;
import org.example.bookstore.validation.FieldMatch;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Size(min = 6, max = 25)
    private String password;
    @Size(min = 6, max = 25)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
