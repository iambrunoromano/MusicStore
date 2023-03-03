package com.musicstore.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
  private String mail;
  private String password;
}
