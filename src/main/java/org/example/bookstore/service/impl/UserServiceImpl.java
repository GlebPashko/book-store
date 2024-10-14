package org.example.bookstore.service.impl;

import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.user.UserDto;
import org.example.bookstore.dto.user.UserRegistrationRequestDto;
import org.example.bookstore.exception.RegistrationException;
import org.example.bookstore.mapper.UserMapper;
import org.example.bookstore.model.Role;
import org.example.bookstore.model.User;
import org.example.bookstore.repository.role.RoleRepository;
import org.example.bookstore.repository.user.UserRepository;
import org.example.bookstore.service.ShoppingCartService;
import org.example.bookstore.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("This email address is already taken");
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = userMapper.toModel(requestDto);
        Role role = roleRepository.findByRole(Role.RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        userRepository.save(user);

        shoppingCartService.addShoppingCartToUser(user);

        return userMapper.toDto(user);
    }
}
