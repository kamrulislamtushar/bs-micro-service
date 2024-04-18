package com.bs.services.user.service.impl;

import com.bs.services.user.exception.ValidationFailureException;
import com.bs.services.user.repository.UserRepository;
import com.bs.services.user.domain.User;
import com.bs.services.user.dto.UserDTO;
import com.bs.services.user.service.UserService;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
  }
  @Override
  public UserDTO createUser(UserDTO userDTO) {
    User user = toUserEntity(userDTO);
    user = userRepository.save(user);
    return toUserDto(user);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "user", key = "#id")
  public Optional<UserDTO> findByUserId(Long id) {
    return userRepository.findById(id).map(this::toUserDto);
  }

  @Override
  @CacheEvict(cacheNames = "user", key = "#id", beforeInvocation = true)
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  @CachePut(cacheNames = "user", key = "#id")
  public UserDTO updateUser(UserDTO userDTO, Long id) {
    Optional<User> dbUser = userRepository.findById(id);
    if (dbUser.isEmpty()) {
      throw new ValidationFailureException("User Not found with provided ID");
    }
    User user = toUserEntity(userDTO);
    user = userRepository.save(user);
    return toUserDto(user);
  }


  public User toUserEntity(UserDTO userDTO) {
    return modelMapper.map(userDTO, User.class);
  }

  public UserDTO toUserDto(User user) {
    return modelMapper.map(user, UserDTO.class);
  }
}
