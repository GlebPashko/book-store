package org.example.bookstore.service;

import org.example.bookstore.dto.user.UserDto;
import org.example.bookstore.dto.user.UserRegistrationRequestDto;
import org.example.bookstore.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userDto) throws RegistrationException;
}
