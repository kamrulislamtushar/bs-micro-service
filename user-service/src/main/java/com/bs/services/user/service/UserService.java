package com.bs.services.user.service;

import com.bs.services.user.dto.UserDTO;
import java.util.Optional;

public interface UserService {
  UserDTO createUser(UserDTO userDTO);
  Optional<UserDTO> findByUserId(Long userId);
  void deleteUser(Long userId);
  UserDTO updateUser(UserDTO userDTO, Long id);
}
