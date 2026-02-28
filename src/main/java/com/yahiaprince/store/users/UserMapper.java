package com.yahiaprince.store.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserDto toUserDto(User user);

   User toEntity(RegisterUserRequest request);

   void update(UpdateUserRequest request, @MappingTarget User user);
}
