package com.musicstore.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  @Email @NotBlank private String mail;

  @NotBlank(message = "Password can't be blank")
  private String password;
}
