package com.letgo.auth.persistence.user;

import com.letgo.auth.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface UserConverter {
  UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

  User convert(UserEntity entity);
  UserEntity convert(User user);
}
