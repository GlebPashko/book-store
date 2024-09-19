package org.example.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.user.UserDto;
import org.example.bookstore.dto.user.UserRegistrationRequestDto;
import org.example.bookstore.exception.RegistrationException;
import org.example.bookstore.mapper.UserMapper;
import org.example.bookstore.model.User;
import org.example.bookstore.repository.user.UserRepository;
import org.example.bookstore.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto register(UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("This email address is already taken");
        }

        User user = userRepository.save(userMapper.toModel(requestDto));
        return userMapper.toDto(user);
    }
}
