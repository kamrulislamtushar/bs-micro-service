package com.bs.services.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @Email
  @Size(min = 5, max = 254)
  private String email;
  private Boolean activated;
}
