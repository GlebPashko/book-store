package org.example.bookstore.mapper;

import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.user.UserDto;
import org.example.bookstore.dto.user.UserRegistrationRequestDto;
import org.example.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}