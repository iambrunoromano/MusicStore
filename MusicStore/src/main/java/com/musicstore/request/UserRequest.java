package com.musicstore.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserRequest {
  @Email @NotBlank private String mail;

  @NotBlank(message = "Password can't be blank")
  private String password;
}
