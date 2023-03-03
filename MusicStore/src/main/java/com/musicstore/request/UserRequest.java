package com.musicstore.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
  // TODO: add mail & password validation
  private String mail;
  private String password;
}
